package owep.controle.gestion ;


import java.util.ResourceBundle;
import javax.servlet.ServletException ;
import owep.controle.CConstante ;
import owep.controle.CControleurBase ;
import owep.infrastructure.Session ;
import owep.modele.execution.MRisque ;
import owep.modele.execution.MProjet ;


/**
 * Controleur pour la suppression d'un problème.
 */
public class CRisqueSuppr extends CControleurBase
{
  private MRisque mRisque ; // Risque à supprimer.
  
  
  /**
   * Récupère les données nécessaire au controleur dans la base de données. 
   * @throws ServletException Si une erreur survient durant la connexion à la base.
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    Session      lSession ;  // Session actuelle de l'utilisateur.
    MProjet      lProjet ;   // Projet en cours.
    String       lIdRisque ; // Identifiant du risque.
    
    lSession  = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
    lProjet   = lSession.getProjet () ;
    lIdRisque = getRequete ().getParameter (CConstante.PAR_RISQUE) ;
    
    
    // Si un problème est passé en paramètre,
    if (lIdRisque != null)
    {
      // Charge le problème passé en paramètre.
      begin () ;
      
      // Récupère la liste des problèmes survenus sur le projet en cours.
      creerRequete ("select RISQUE from owep.modele.execution.MRisque RISQUE where mId = $1 AND mProjet.mId = $2") ;
      parametreRequete (Integer.parseInt (lIdRisque)) ;
      parametreRequete (lProjet.getId ()) ;
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
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
  }
  
  
  /**
   * Récupère les paramètres passés au contrôleur. 
   * @throws ServletException -
   */
  public void initialiserParametres () throws ServletException
  {
  }
  
  
  /**
   * Transmet le problème à la JSP d'affichage si aucune données n'est soumise. Sinon, met à jour ou
   * insère le problème.
   * @return URL de la page vers laquelle doit être redirigé le client.
   * @throws ServletException Si une erreur survient dans le controleur
   */
  public String traiter () throws ServletException
  {
    ResourceBundle lMessages = ResourceBundle.getBundle("MessagesBundle") ;
    
    // Supprime le problème.
    supprimer (mRisque) ;
    commit () ;
    close () ;
    
    // Affiche la page de visualisation de la liste des problèmes.
    String lMessage = lMessages.getString ("risqueModifMsgSuppression1") + mRisque.getNom () + lMessages.getString ("risqueModifMsgSuppression2") ;
    getRequete ().setAttribute (CConstante.PAR_MESSAGE, lMessage) ;
    
    return "/Gestion/ListeRisqueVisu" ;
  }
}