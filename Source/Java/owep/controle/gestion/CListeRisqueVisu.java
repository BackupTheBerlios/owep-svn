package owep.controle.gestion ;


import javax.servlet.ServletException ;
import owep.controle.CConstante ;
import owep.controle.CControleurBase ;
import owep.infrastructure.Session ;
import owep.modele.execution.MProjet ;


/**
 * Controleur pour la visualisation  de la liste des probl�mes.
 */
public class CListeRisqueVisu extends CControleurBase
{
  private MProjet mProjet ; // Contient la liste des risques.
  
  
  /**
   * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es. 
   * @throws ServletException Si une erreur survient durant la connexion � la base.
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    Session      lSession ;  // Session actuelle de l'utilisateur.
    
    lSession = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
    mProjet  = lSession.getProjet () ;
    
    // Charge la liste des probl�mes pour le projet ouvert.
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
    
    commit () ;
    close () ;
  }
  
  
  /**
   * R�cup�re les param�tres pass�s au controleur. 
   * @throws ServletException -
   */
  public void initialiserParametres () throws ServletException
  {
  }
  
  
  /**
   * R�cup�re la liste des t�ches d'un collaborateur pour l'it�ration choisie, et la transmet � la
   * JSP. 
   * @return URL de la page vers laquelle doit �tre redirig� le client.
   * @throws ServletException Si une erreur survient dans le controleur
   */
  public String traiter () throws ServletException
  {
    getRequete ().setAttribute (CConstante.PAR_PROJET, mProjet) ;
    
    // Transmet les donn�es � la JSP d'affichage.
    return "/JSP/Gestion/TListeRisqueVisu.jsp" ;
  }
}