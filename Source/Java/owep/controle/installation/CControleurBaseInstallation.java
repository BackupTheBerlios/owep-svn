package owep.controle.installation ;


import java.io.IOException ;

import javax.servlet.RequestDispatcher ;
import javax.servlet.ServletException ;
import javax.servlet.http.HttpServlet ;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;

import owep.controle.CConstante;

/**
 * Controleur de base pour tous les controleurs de l'installation.
 */
public abstract class CControleurBaseInstallation extends HttpServlet
{

  private HttpServletRequest  mRequete ;
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
   * Récupère la réponse HTTP que le controleur va fournir au client.
   * @return Réponse HTTP du controleur.
   */
  public HttpServletResponse getReponse ()
  {
    return mReponse ;
  }
  
  
  /**
   * Récupère la requête HTTP à l'origine de l'appel du controleur.
   * @return Requête HTTP à l'origine de l'appel du controleur.
   */
  public HttpServletRequest getRequete ()
  {
    return mRequete ;
  }
  
  /**
   * Effectue tout le traîtement du controleur puis retourne l'URL vers laquelle le client doit être
   * redirigé.
   * 
   * @return URL de la page vers laquelle doit être redirigé le client.
   * @throws ServletException Si une erreur survient dans le controleur
   */
  public abstract String traiter () throws ServletException ;

}