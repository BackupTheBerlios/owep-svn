package owep.controle.navigation ;


import java.util.ArrayList ;
import javax.servlet.ServletException ;
import org.exolab.castor.jdo.OQLQuery ;
import org.exolab.castor.jdo.PersistenceException ;
import org.exolab.castor.jdo.QueryResults ;
import owep.controle.CConstante ;
import owep.controle.CControleurLight ;
import owep.infrastructure.Session;
import owep.modele.execution.MProjet ;


/**
 * Met � jour la list box contenant la liste des it�rations. Ce controleur est appel�
 * syst�matiquement avant de l'afficher.
 */
public class CRefreshIteration extends CControleurLight
{
  private ArrayList mListeIteration ;


  /**
   * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es. 
   * @throws ServletException Si une erreur survient durant la connexion
   * @see owep.controle.CControleurBase#initialiserBaseDonnees()
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    Session          lSession ;  // Session actuelle de l'utilisateur.
    OQLQuery lRequete ;      // Requ�te � r�aliser sur la base.
    QueryResults lResultat ; // R�sultat de la requ�te sur la base.
    
    
    lSession = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
    int lIdProjet  = lSession.getIdProjet () ;
    
    try
    {
      getBaseDonnees ().begin () ;
      
      lRequete = getBaseDonnees ().getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
      lRequete.bind (lIdProjet) ;
      lResultat = lRequete.execute () ;
      
      MProjet lProjet = (MProjet) lResultat.next () ;
      mListeIteration = lProjet.getListeIterations () ;
      getBaseDonnees ().commit () ;
    }
    catch (PersistenceException eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
    // Ferme la connexion � la base de donn�es.
    finally
    {
      try
      {
        getBaseDonnees ().close () ;
      }
      catch (PersistenceException eException)
      {
        eException.printStackTrace () ;
        throw new ServletException (CConstante.EXC_DECONNEXION) ;
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
    // Passe la liste des it�rations � la JSP.
    getRequete ().setAttribute (CConstante.PAR_LISTEITERATIONSMENU, mListeIteration) ;
    
    return "" ;
  }
}