package owep.controle.processus ;


import javax.servlet.ServletException ;
import owep.controle.CControleurBase;


/**
 * Controleur pour l'affichage de la fen�tre principale pour le suivi de projet.
 */
public class CProjetVisu extends CControleurBase
{
  /**
   * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es. 
   * @throws ServletException Si une erreur survient durant la connexion
   * @see owep.controle.CControleurBase#initialiserBaseDonnees()
   */
  public void initialiserBaseDonnees () throws ServletException
  {
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