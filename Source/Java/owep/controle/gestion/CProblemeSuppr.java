package owep.controle.gestion ;


import java.util.ResourceBundle;
import javax.servlet.ServletException ;
import owep.controle.CConstante ;
import owep.controle.CControleurBase ;
import owep.infrastructure.Session ;
import owep.modele.execution.MProbleme ;
import owep.modele.execution.MProjet ;


/**
 * Controleur pour la suppression d'un problème.
 */
public class CProblemeSuppr extends CControleurBase
{
  private MProbleme mProbleme ; // Problème à supprimer.
  
  
  /**
   * Récupère les données nécessaire au controleur dans la base de données. 
   * @throws ServletException Si une erreur survient durant la connexion à la base.
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    Session      lSession ;    // Session actuelle de l'utilisateur.
    MProjet      lProjet ;     // Projet en cours.
    String       lIdProbleme ; // Identifiant du problème.
    
    lSession    = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
    lProjet     = lSession.getProjet () ;
    lIdProbleme = getRequete ().getParameter (CConstante.PAR_PROBLEME) ;
    
    
    // Si un problème est passé en paramètre,
    if (lIdProbleme != null)
    {
      // Charge le problème passé en paramètre.
      begin () ;
      
      // Récupère la liste des problèmes survenus sur le projet en cours.
      creerRequete ("select PROBLEME from owep.modele.execution.MProbleme PROBLEME where mId = $1 AND mTacheProvoque.mIteration.mProjet.mId = $2") ;
      parametreRequete (Integer.parseInt (lIdProbleme)) ;
      parametreRequete (lProjet.getId ()) ;
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
    supprimer (mProbleme) ;
    commit () ;
    close () ;
    
    // Affiche la page de visualisation de la liste des problèmes.
    String lMessage = lMessages.getString ("problemeModifMsgSuppression1") + mProbleme.getNom () + lMessages.getString ("problemeModifMsgSuppression2") ;
    getRequete ().setAttribute (CConstante.PAR_MESSAGE, lMessage) ;
    
    return "/Gestion/ListeProblemeVisu" ;
  }
}