package owep.controle.gestion ;


import java.util.ResourceBundle;
import javax.servlet.ServletException ;
import owep.controle.CConstante ;
import owep.controle.CControleurBase ;
import owep.infrastructure.Session ;
import owep.modele.execution.MRisque ;
import owep.modele.execution.MProjet ;


/**
 * Controleur pour la suppression d'un probl�me.
 */
public class CRisqueSuppr extends CControleurBase
{
  private MRisque mRisque ; // Risque � supprimer.
  
  
  /**
   * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es. 
   * @throws ServletException Si une erreur survient durant la connexion � la base.
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    Session      lSession ;  // Session actuelle de l'utilisateur.
    MProjet      lProjet ;   // Projet en cours.
    String       lIdRisque ; // Identifiant du risque.
    
    lSession  = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
    lProjet   = lSession.getProjet () ;
    lIdRisque = getRequete ().getParameter (CConstante.PAR_RISQUE) ;
    
    
    // Si un probl�me est pass� en param�tre,
    if (lIdRisque != null)
    {
      // Charge le probl�me pass� en param�tre.
      begin () ;
      
      // R�cup�re la liste des probl�mes survenus sur le projet en cours.
      creerRequete ("select RISQUE from owep.modele.execution.MRisque RISQUE where mId = $1 AND mProjet.mId = $2") ;
      parametreRequete (Integer.parseInt (lIdRisque)) ;
      parametreRequete (lProjet.getId ()) ;
      executer () ;
      
      // Si on r�cup�re correctement le probl�me dans la base,
      if (contientResultat ())
      {
        mRisque = (MRisque) getResultat () ;
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
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
  }
  
  
  /**
   * R�cup�re les param�tres pass�s au contr�leur. 
   * @throws ServletException -
   */
  public void initialiserParametres () throws ServletException
  {
  }
  
  
  /**
   * Transmet le probl�me � la JSP d'affichage si aucune donn�es n'est soumise. Sinon, met � jour ou
   * ins�re le probl�me.
   * @return URL de la page vers laquelle doit �tre redirig� le client.
   * @throws ServletException Si une erreur survient dans le controleur
   */
  public String traiter () throws ServletException
  {
    ResourceBundle lMessages = ResourceBundle.getBundle("MessagesBundle") ;
    
    // Supprime le probl�me.
    supprimer (mRisque) ;
    commit () ;
    close () ;
    
    // Affiche la page de visualisation de la liste des probl�mes.
    String lMessage = lMessages.getString ("risqueModifMsgSuppression1") + mRisque.getNom () + lMessages.getString ("risqueModifMsgSuppression2") ;
    getRequete ().setAttribute (CConstante.PAR_MESSAGE, lMessage) ;
    
    return "/Gestion/ListeRisqueVisu" ;
  }
}