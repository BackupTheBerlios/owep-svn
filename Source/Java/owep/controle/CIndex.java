package owep.controle ;


import java.io.File;
import java.io.IOException ;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException ;
import javax.servlet.http.HttpServlet ;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;

/**
 * Controleur permettant de diriger la premiére page appelé vers la page d'installtion ou vers l'index.
 */
public class CIndex extends HttpServlet
{

  private HttpServletRequest mRequete ;
  private HttpServletResponse mReponse ;


  /**
   * Appellé lors d'une requête d'un client. Redirige le client vers la page retourné par la méthode
   * traiter.
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
   * Appellé lors d'une requête d'un client contenant des données transmises. Redirige le client
   * vers la page retourné par la méthode traiter.
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

    // Appelle la JSP d'affichage retournée par traiter.
    lRequeteDispatcher = pRequete.getRequestDispatcher (traiter ()) ;
    if (lRequeteDispatcher != null)
    {
      lRequeteDispatcher.forward (mRequete, mReponse) ;
    }
    else
    {
      throw new ServletException (CConstante.EXC_FORWARD) ;
    }
  }


  /**
   * @return
   * String
   */
  private String traiter ()
  {
    File f = new File(getServletContext ().getRealPath ("/")+"/WEB-INF/Database.xml");
    
    if(f.exists()){
      return "JSP\\index.jsp" ;
    }

    return "JSP\\Installation\\Bienvenue.jsp";
  }

}