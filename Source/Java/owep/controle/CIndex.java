package owep.controle ;


import java.io.File;
import java.io.IOException ;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException ;
import javax.servlet.http.HttpServlet ;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;

/**
 * Controleur permettant de diriger la premi�re page appel� vers la page d'installtion ou vers l'index.
 */
public class CIndex extends HttpServlet
{

  private HttpServletRequest mRequete ;
  private HttpServletResponse mReponse ;


  /**
   * Appell� lors d'une requ�te d'un client. Redirige le client vers la page retourn� par la m�thode
   * traiter.
   * 
   * @param pRequete Requ�te HTTP � l'origine de l'appel du controleur
   * @param pReponse R�ponse HTTP du controleur � la requ�te
   * @throws ServletException Si une erreur survient durant le traitement de la page.
   * @throws IOException Si une erreur survient durant le traitement de la page.
   */
  protected void doGet (HttpServletRequest pRequete, HttpServletResponse pReponse)
    throws ServletException, IOException
  {
    doPost (pRequete, pReponse) ;
  }


  /**
   * Appell� lors d'une requ�te d'un client contenant des donn�es transmises. Redirige le client
   * vers la page retourn� par la m�thode traiter.
   * 
   * @param pRequete Requ�te HTTP � l'origine de l'appel du controleur
   * @param pReponse R�ponse HTTP du controleur � la requ�te
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

    // Appelle la JSP d'affichage retourn�e par traiter.
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