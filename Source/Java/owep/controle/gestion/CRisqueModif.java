package owep.controle.gestion ;


import java.util.ResourceBundle;
import javax.servlet.ServletException ;
import owep.controle.CConstante ;
import owep.controle.CControleurBase ;
import owep.infrastructure.Session ;
import owep.modele.execution.MRisque ;
import owep.modele.execution.MProjet ;
import owep.vue.transfert.VTransfert ;


/**
 * Controleur pour la modification d'un probleme.
 */
public class CRisqueModif extends CControleurBase
{
  private MProjet mProjet ; // Projet actuellement ouvert.
  private MRisque mRisque ; // Problème à créer ou modifier.
  
  
  /**
   * Récupère les données nécessaire au controleur dans la base de données. 
   * @throws ServletException Si une erreur survient durant la connexion à la base.
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    Session      lSession ;  // Session actuelle de l'utilisateur.
    String       lIdRisque ; // Identifiant du risque.
    
    lSession    = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
    mProjet     = lSession.getProjet () ;
    lIdRisque = getRequete ().getParameter (CConstante.PAR_RISQUE) ;
    
    
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
    if (lIdRisque != null)
    {
      // Charge le problème passé en paramètre.
      creerRequete ("select RISQUE from owep.modele.execution.MRisque RISQUE where mId = $1 AND mProjet.mId = $2") ;
      parametreRequete (Integer.parseInt (lIdRisque)) ;
      parametreRequete (mProjet.getId ()) ;
      executer () ;
      
      // Si on récupère correctement le problème dans la base,
      if (contientResultat ())
      {
        mRisque = (MRisque) getResultat () ;
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
      mRisque = new MRisque () ;
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
      VTransfert.transferer (getRequete (), mRisque, CConstante.PAR_ARBRERISQUE) ;
      mRisque.setProjet (mProjet) ;
      if (mRisque.getId () == 0)
      {
        mProjet.addRisque (mRisque) ;
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
      getRequete ().setAttribute (CConstante.PAR_RISQUE, mRisque) ;
      getRequete ().setAttribute (CConstante.PAR_PROJET, mProjet) ;
      // Valide les données.
      commit () ;
      close () ;
      
      // Affiche la page de modification de problème.
      return "/JSP/Gestion/TRisqueModif.jsp" ;
    }
    else
    {
      // Si l'utilisateur valide les données, alors on les enregistre dans la base.
      String lMessage = "" ;
      if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMIT))
      {
        ResourceBundle lMessages = ResourceBundle.getBundle("MessagesBundle") ;
        
        // Crée l'objet ou le met à jour s'il existe déjà.
        if (mRisque.getId () == 0)
        {
          creer (mRisque) ;
          lMessage = lMessages.getString ("risqueModifMsgCreation1") + mRisque.getNom () + lMessages.getString ("risqueModifMsgCreation2") ;
        }
        else
        {
          lMessage = lMessages.getString ("risqueModifMsgModification1") + mRisque.getNom () + lMessages.getString ("risqueModifMsgModification2") ;
        }
      }
      // Valide les données.
      commit () ;
      close () ;
      
      // Affiche la page de visualisation de la liste des problèmes.
      getRequete ().setAttribute (CConstante.PAR_MESSAGE, lMessage) ;
      return "/Gestion/ListeRisqueVisu" ;
    }
  }
}