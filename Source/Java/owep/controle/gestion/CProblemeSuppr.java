package owep.controle.gestion ;


import java.util.ResourceBundle;
import javax.servlet.ServletException ;
import owep.controle.CConstante ;
import owep.controle.CControleurBase ;
import owep.infrastructure.Session ;
import owep.modele.execution.MProbleme ;
import owep.modele.execution.MProjet ;


/**
 * Controleur pour la suppression d'un probl�me.
 */
public class CProblemeSuppr extends CControleurBase
{
  private MProbleme mProbleme ; // Probl�me � supprimer.
  
  
  /**
   * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es. 
   * @throws ServletException Si une erreur survient durant la connexion � la base.
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    Session      lSession ;    // Session actuelle de l'utilisateur.
    MProjet      lProjet ;     // Projet en cours.
    String       lIdProbleme ; // Identifiant du probl�me.
    
    lSession    = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
    lProjet     = lSession.getProjet () ;
    lIdProbleme = getRequete ().getParameter (CConstante.PAR_PROBLEME) ;
    
    
    // Si un probl�me est pass� en param�tre,
    if (lIdProbleme != null)
    {
      // Charge le probl�me pass� en param�tre.
      begin () ;
      
      // R�cup�re la liste des probl�mes survenus sur le projet en cours.
      creerRequete ("select PROBLEME from owep.modele.execution.MProbleme PROBLEME where mId = $1 AND mTacheProvoque.mIteration.mProjet.mId = $2") ;
      parametreRequete (Integer.parseInt (lIdProbleme)) ;
      parametreRequete (lProjet.getId ()) ;
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
    supprimer (mProbleme) ;
    commit () ;
    close () ;
    
    // Affiche la page de visualisation de la liste des probl�mes.
    String lMessage = lMessages.getString ("problemeModifMsgSuppression1") + mProbleme.getNom () + lMessages.getString ("problemeModifMsgSuppression2") ;
    getRequete ().setAttribute (CConstante.PAR_MESSAGE, lMessage) ;
    
    return "/Gestion/ListeProblemeVisu" ;
  }
}