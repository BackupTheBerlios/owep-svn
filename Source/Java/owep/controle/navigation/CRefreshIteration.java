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
 * Met à jour la list box contenant la liste des itérations. Ce controleur est appelé
 * systématiquement avant de l'afficher.
 */
public class CRefreshIteration extends CControleurLight
{
  private ArrayList mListeIteration ;


  /**
   * Récupère les données nécessaire au controleur dans la base de données. 
   * @throws ServletException Si une erreur survient durant la connexion
   * @see owep.controle.CControleurBase#initialiserBaseDonnees()
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    Session          lSession ;  // Session actuelle de l'utilisateur.
    OQLQuery lRequete ;      // Requête à réaliser sur la base.
    QueryResults lResultat ; // Résultat de la requête sur la base.
    
    
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
    // Ferme la connexion à la base de données.
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
   * Récupère les paramètres passés au controleur. 
   * @throws ServletException -
   * @see owep.controle.CControleurBase#initialiserParametres()
   */
  public void initialiserParametres () throws ServletException
  {
  }


  /**
   * Récupère la liste des tâches d'un collaborateur pour l'itération choisie, et la transmet à la
   * JSP. 
   * @return URL de la page vers laquelle doit être redirigé le client.
   * @throws ServletException Si une erreur survient dans le controleur
   * @see owep.controle.CControleurBase#traiter()
   */
  public String traiter () throws ServletException
  {
    // Passe la liste des itérations à la JSP.
    getRequete ().setAttribute (CConstante.PAR_LISTEITERATIONSMENU, mListeIteration) ;
    
    return "" ;
  }
}