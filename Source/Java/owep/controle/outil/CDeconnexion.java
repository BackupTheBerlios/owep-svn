package owep.controle.outil ;


import java.io.IOException ;
import javax.servlet.RequestDispatcher ;
import javax.servlet.ServletException ;
import javax.servlet.http.HttpServlet ;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;
import javax.servlet.http.HttpSession ;


/**
 * Controleur permettant la fermeture de la session d'un client.
 */
public class CDeconnexion extends HttpServlet
{
  private HttpServletRequest mRequete ; // Requête HTTP à l'origine de l'appel du controleur
  private HttpServletResponse mReponse ; // Réponse HTTP du controleur à la requête


  /**
   * Appellé lors d'une requête d'un client.
   * 
   * @param pRequete Requête HTTP à l'origine de l'appel du controleur
   * @param pReponse Réponse HTTP du controleur à la requête
   * @throws ServletException Si une erreur survient durant le traitement de la page.
   * @throws IOException Si une erreur survient durant le traitement de la page.
   */
  protected void doGet (HttpServletRequest pRequete, HttpServletResponse pReponse)
    throws ServletException, IOException
  {
    doPost (pRequete, pReponse) ;
  }

  /**
   * Appellé lors d'une requête d'un client contenant des données transmises.
   * 
   * @param pRequete Requête HTTP à l'origine de l'appel du controleur
   * @param pReponse Réponse HTTP du controleur à la requête
   * @throws ServletException Si une erreur survient durant le traitement de la page.
   * @throws IOException Si une erreur survient durant le traitement de la page.
   */
  protected void doPost (HttpServletRequest pRequete, HttpServletResponse pReponse)
    throws ServletException, IOException
  {
    RequestDispatcher lRequeteDispatcher ; // Permet d'appeler la JSP d'affichage.

    // Initialise les variables membres.
    mRequete = pRequete ;
    mReponse = pReponse ;

    // Invalide la session ouverte
    HttpSession lSession = mRequete.getSession (true) ;
    lSession.removeAttribute ("SESSION") ;
    lSession.invalidate () ;

    // Redirige vers l'index
    lRequeteDispatcher = pRequete.getRequestDispatcher ("..\\JSP\\index.jsp") ;
    lRequeteDispatcher.forward (mRequete, mReponse) ;
  }
}