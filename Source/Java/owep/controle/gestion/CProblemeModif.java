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
  private MProbleme mProbleme ;        // Probl�me � cr�er ou modifier.
  
  
  /**
   * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es. 
   * @throws ServletException Si une erreur survient durant la connexion � la base.
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    Session      lSession ;    // Session actuelle de l'utilisateur.
    String       lIdProbleme ; // Identifiant du probl�me.
    
    lSession    = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
    mProjet     = lSession.getProjet () ;
    lIdProbleme = getRequete ().getParameter (CConstante.PAR_PROBLEME) ;
    
    
    begin () ;
    
    // R�cup�re le projet actuellement ouvert.
    creerRequete ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
    parametreRequete (mProjet.getId ()) ;
    executer () ;
    // Si on r�cup�re correctement le projet dans la base,
    if (contientResultat ())
    {
      mProjet = (MProjet) getResultat () ;
    }
    // Si le projet n'existe pas,
    else
    {
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
    
    
    // Si un probl�me est pass� en param�tre,
    if (lIdProbleme != null)
    {
      // R�cup�re le probl�me choisi par l'utilisateur.
      creerRequete ("select PROBLEME from owep.modele.execution.MProbleme PROBLEME where mId = $1 AND mTacheProvoque.mIteration.mProjet.mId = $2") ;
      parametreRequete (Integer.parseInt (lIdProbleme)) ;
      parametreRequete (mProjet.getId ()) ;
      executer () ;
      // Si on r�cup�re correctement le probl�me dans la base,
      if (contientResultat ())
      {
        mProbleme = (MProbleme) getResultat () ;
      }
      // Si le probl�me n'existe pas ou n'appartient pas au projet ouvert,
      else
      {
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
    }
    // Si aucun probl�me existant n'est pass� en param�tre,
    else
    {
      mProbleme = new MProbleme () ;
    }
  }
  
  
  /**
   * R�cup�re les param�tres pass�s au contr�leur. 
   * @throws ServletException -
   */
  public void initialiserParametres () throws ServletException
  {
    // Si l'utilisateur valide les donn�es, r�cup�re les donn�es.
    if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMIT))
    {
      VTransfert.transferer (getRequete (), mProbleme, CConstante.PAR_ARBREPROBLEME) ;
    
      // R�cup�re la liste des t�ches provoquant le probl�me.
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
      
      // R�cup�re la liste des t�ches r�solvant le probl�me.
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
   * Transmet le probl�me � la JSP d'affichage si aucune donn�es n'est soumise. Sinon, met � jour ou
   * ins�re le probl�me.
   * @return URL de la page vers laquelle doit �tre redirig� le client.
   * @throws ServletException Si une erreur survient dans le controleur
   */
  public String traiter () throws ServletException
  {
    // Si l'utilisateur affiche la page pour la premi�re fois,
    if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_VIDE))
    {
      // Si l'utilisateur acc�de � la page d'ajout/modification, transmet les donn�es � la page.
      getRequete ().setAttribute (CConstante.PAR_PROBLEME, mProbleme) ;
      getRequete ().setAttribute (CConstante.PAR_PROJET, mProjet) ;
      
      commit () ;
      close () ;
      
      // Affiche la page de modification de probl�me.
      return "/JSP/Gestion/TProblemeModif.jsp" ;
    }
    else
    {
      ResourceBundle lMessages = ResourceBundle.getBundle("MessagesBundle") ;
      
      // Si l'utilisateur valide les donn�es, alors on les enregistre dans la base.
      String lMessage = "" ;
      if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMIT))
      {
        // Cr�e l'objet ou le met � jour s'il existe d�j�.
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
      // Valide les donn�es.
      commit () ;
      close () ;
      
      // Affiche la page de visualisation de la liste des probl�mes.
      getRequete ().setAttribute (CConstante.PAR_MESSAGE, lMessage) ;
      return "/Gestion/ListeProblemeVisu" ;
    }
  }
  
  
  /**
   * R�cup�re une t�che � partir de son identifiant.
   * @param pId Identifiant de la t�che
   * @return Tache d'identifiant pId.
   * @throws ServletException Si la t�che recherch� n'est pas trouv�e.
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
   * R�cup�re une t�che � partir de son identifiant.
   * @param pId Identifiant de la t�che
   * @return Tache d'identifiant pId.
   * @throws ServletException Si la t�che recherch� n'est pas trouv�e.
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