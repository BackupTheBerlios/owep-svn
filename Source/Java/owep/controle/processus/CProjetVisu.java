package owep.controle.processus ;


import javax.servlet.ServletException ;
import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.jdo.QueryResults;
import owep.controle.CConstante;
import owep.controle.CControleurBase;
import owep.infrastructure.Session;
import owep.modele.execution.MProjet;


/**
 * Controleur pour l'affichage de la fen�tre principale pour le suivi de projet.
 */
public class CProjetVisu extends CControleurBase
{
  MProjet mProjet; // Projet dont l'it�ration fait partie.
  
  /**
   * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es. 
   * @throws ServletException Si une erreur survient durant la connexion
   * @see owep.controle.CControleurBase#initialiserBaseDonnees()
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    Session          lSession ;  // Session actuelle de l'utilisateur.
    OQLQuery         lRequete ;  // Requ�te � r�aliser sur la base
    QueryResults     lResultat ; // R�sultat de la requ�te sur la base
    
    lSession = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
    mProjet  = lSession.getProjet () ;
    
    // Charge une copie du projet ouvert.
    try
    {
      getBaseDonnees ().begin () ;
      
      // R�cup�re la liste des t�ches du collaborateur.
      lRequete = getBaseDonnees ().getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
      lRequete.bind (mProjet.getId ()) ;
      lResultat  = lRequete.execute () ;
      mProjet = (MProjet) lResultat.next () ;
      
      getRequete ().setAttribute(CConstante.PAR_PROJET, mProjet);
      getBaseDonnees ().commit () ;
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
    finally
    {        
      try
      {
        getBaseDonnees ().close () ;
      }
      catch (PersistenceException e)
      {
        e.printStackTrace () ;
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
    }
  }
  
  
  /**
   * R�cup�re les param�tres pass�s au controleur. 
   * @throws ServletException -
   * @see owep.controle.CControleurBase#initialiserParametres()
   */
  public void initialiserParametres () throws ServletException
  {
  }
  
  
  /**
   * R�cup�re la liste des t�ches d'un collaborateur pour l'it�ration choisie, et la transmet � la
   * JSP. 
   * @return URL de la page vers laquelle doit �tre redirig� le client.
   * @throws ServletException Si une erreur survient dans le controleur
   * @see owep.controle.CControleurBase#traiter()
   */
  public String traiter () throws ServletException
  {
    // Transmet les donn�es � la JSP d'affichage.
    return "..\\JSP\\Processus\\TProjetVisu.jsp" ;
  }
}