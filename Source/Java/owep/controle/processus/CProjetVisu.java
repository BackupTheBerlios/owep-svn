package owep.controle.processus ;


import javax.servlet.ServletException ;
import owep.controle.CControleurBase;


/**
 * Controleur pour l'affichage de la fenêtre principale pour le suivi de projet.
 */
public class CProjetVisu extends CControleurBase
{
  /**
   * Récupère les données nécessaire au controleur dans la base de données. 
   * @throws ServletException Si une erreur survient durant la connexion
   * @see owep.controle.CControleurBase#initialiserBaseDonnees()
   */
  public void initialiserBaseDonnees () throws ServletException
  {
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
    // Transmet les données à la JSP d'affichage.
    return "..\\JSP\\Processus\\TProjetVisu.jsp" ;
  }
}