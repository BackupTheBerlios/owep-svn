package owep.controle.gestion ;


import java.util.ResourceBundle ;
import java.util.StringTokenizer;
import javax.servlet.ServletException ;
import owep.controle.CConstante ;
import owep.controle.CControleurBase ;
import owep.infrastructure.Session ;
import owep.modele.execution.MIteration ;
import owep.modele.execution.MProbleme ;
import owep.modele.execution.MProjet ;
import owep.modele.execution.MTache ;
import owep.modele.execution.MTacheImprevue ;
import owep.vue.transfert.VTransfert ;


/**
 * Controleur pour la modification d'un probleme.
 */
public class CProblemeModif extends CControleurBase
{
  private MProjet   mProjet ;          // Projet actuellement ouvert.
  private MProbleme mProbleme ;        // Problème à créer ou modifier.
  
  
  /**
   * Récupère les données nécessaire au controleur dans la base de données. 
   * @throws ServletException Si une erreur survient durant la connexion à la base.
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    Session      lSession ;    // Session actuelle de l'utilisateur.
    String       lIdProbleme ; // Identifiant du problème.
    
    lSession    = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
    mProjet     = lSession.getProjet () ;
    lIdProbleme = getRequete ().getParameter (CConstante.PAR_PROBLEME) ;
    
    
    begin () ;
    
    // Récupère le projet actuellement ouvert.
    creerRequete ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
    parametreRequete (mProjet.getId ()) ;
    executer () ;
    // Si on récupère correctement le projet dans la base,
    if (contientResultat ())
    {
      mProjet = (MProjet) getResultat () ;
    }
    // Si le projet n'existe pas,
    else
    {
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
    
    
    // Si un problème est passé en paramètre,
    if (lIdProbleme != null)
    {
      // Récupère le problème choisi par l'utilisateur.
      creerRequete ("select PROBLEME from owep.modele.execution.MProbleme PROBLEME where mId = $1 AND mTacheProvoque.mIteration.mProjet.mId = $2") ;
      parametreRequete (Integer.parseInt (lIdProbleme)) ;
      parametreRequete (mProjet.getId ()) ;
      executer () ;
      // Si on récupère correctement le problème dans la base,
      if (contientResultat ())
      {
        mProbleme = (MProbleme) getResultat () ;
      }
      // Si le problème n'existe pas ou n'appartient pas au projet ouvert,
      else
      {
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
    }
    // Si aucun problème existant n'est passé en paramètre,
    else
    {
      mProbleme = new MProbleme () ;
    }
  }
  
  
  /**
   * Récupère les paramètres passés au contrôleur. 
   * @throws ServletException -
   */
  public void initialiserParametres () throws ServletException
  {
    // Si l'utilisateur valide les données, récupère les données.
    if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMIT))
    {
      VTransfert.transferer (getRequete (), mProbleme, CConstante.PAR_ARBREPROBLEME) ;
    
      // Récupère la liste des tâches provoquant le problème.
      mProbleme.getListeTacheProvoque ().clear () ;
      mProbleme.getListeTacheImprevueProvoque ().clear () ;
      StringTokenizer lTokenizer = new StringTokenizer (getRequete ().getParameter (CConstante.PAR_LISTETACHESPROVOQUE), "-") ;
      while (lTokenizer.hasMoreTokens ())
      {
        if (lTokenizer.nextToken ().equals ("i"))
        {
          MTacheImprevue lTache = chercherTacheImprevue (Integer.parseInt (lTokenizer.nextToken ())) ;
          mProbleme.addTacheImprevueProvoque (lTache) ;
          lTache.addProblemeEntree (mProbleme) ;
        }
        else
        {
          MTache lTache = chercherTache (Integer.parseInt (lTokenizer.nextToken ())) ;
          mProbleme.addTacheProvoque (lTache) ;
          lTache.addProblemeEntree (mProbleme) ;
        }
      }
      
      // Récupère la liste des tâches résolvant le problème.
      mProbleme.getListeTacheResout ().clear () ;
      mProbleme.getListeTacheImprevueResout ().clear () ;
      lTokenizer = new StringTokenizer (getRequete ().getParameter (CConstante.PAR_LISTETACHESRESOUT), "-") ;
      while (lTokenizer.hasMoreTokens ())
      {
        if (lTokenizer.nextToken ().equals ("i"))
        {
          MTacheImprevue lTache = chercherTacheImprevue (Integer.parseInt (lTokenizer.nextToken ())) ;
          mProbleme.addTacheImprevueResout (lTache) ;
          lTache.addProblemeSortie (mProbleme) ;
        }
        else
        {
          MTache lTache = chercherTache (Integer.parseInt (lTokenizer.nextToken ())) ;
          mProbleme.addTacheResout (lTache) ;
          lTache.addProblemeSortie (mProbleme) ;
        }
      }
    }
  }
  
  
  /**
   * Transmet le problème à la JSP d'affichage si aucune données n'est soumise. Sinon, met à jour ou
   * insère le problème.
   * @return URL de la page vers laquelle doit être redirigé le client.
   * @throws ServletException Si une erreur survient dans le controleur
   */
  public String traiter () throws ServletException
  {
    // Si l'utilisateur affiche la page pour la première fois,
    if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_VIDE))
    {
      // Si l'utilisateur accède à la page d'ajout/modification, transmet les données à la page.
      getRequete ().setAttribute (CConstante.PAR_PROBLEME, mProbleme) ;
      getRequete ().setAttribute (CConstante.PAR_PROJET, mProjet) ;
      
      commit () ;
      close () ;
      
      // Affiche la page de modification de problème.
      return "/JSP/Gestion/TProblemeModif.jsp" ;
    }
    else
    {
      ResourceBundle lMessages = ResourceBundle.getBundle("MessagesBundle") ;
      
      // Si l'utilisateur valide les données, alors on les enregistre dans la base.
      String lMessage = "" ;
      if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMIT))
      {
        // Crée l'objet ou le met à jour s'il existe déjà.
        if (mProbleme.getId () == 0)
        {
          creer (mProbleme) ;
          lMessage = lMessages.getString ("problemeModifMsgCreation1") + mProbleme.getNom () + lMessages.getString ("problemeModifMsgCreation2") ;
        }
        else
        {
          lMessage = lMessages.getString ("problemeModifMsgModification1") + mProbleme.getNom () + lMessages.getString ("problemeModifMsgModification2") ;
        }
      }
      // Valide les données.
      commit () ;
      close () ;
      
      // Affiche la page de visualisation de la liste des problèmes.
      getRequete ().setAttribute (CConstante.PAR_MESSAGE, lMessage) ;
      return "/Gestion/ListeProblemeVisu" ;
    }
  }
  
  
  /**
   * Récupère une tâche à partir de son identifiant.
   * @param pId Identifiant de la tâche
   * @return Tache d'identifiant pId.
   * @throws ServletException Si la tâche recherché n'est pas trouvée.
   */
  private MTache chercherTache (int pId) throws ServletException
  {
    for (int i = 0; i < mProjet.getNbIterations (); i ++)
    {
      MIteration lIteration = mProjet.getIteration (i) ;
      for (int j = 0; j < lIteration.getNbTaches (); j ++)
      {
        if (lIteration.getTache (j).getId () == pId)
        {
          return lIteration.getTache (j) ;
        }
      }
    }
    throw new ServletException (CConstante.EXC_TRAITEMENT) ;
  }
  
  
  /**
   * Récupère une tâche à partir de son identifiant.
   * @param pId Identifiant de la tâche
   * @return Tache d'identifiant pId.
   * @throws ServletException Si la tâche recherché n'est pas trouvée.
   */
  private MTacheImprevue chercherTacheImprevue (int pId) throws ServletException
  {
    for (int i = 0; i < mProjet.getNbIterations (); i ++)
    {
      MIteration lIteration = mProjet.getIteration (i) ;
      for (int j = 0; j < lIteration.getNbTachesImprevues (); j ++)
      {
        if (lIteration.getTacheImprevue (j).getId () == pId)
        {
          return lIteration.getTacheImprevue (j) ;
        }
      }
    }
    throw new ServletException (CConstante.EXC_TRAITEMENT) ;
  }
}