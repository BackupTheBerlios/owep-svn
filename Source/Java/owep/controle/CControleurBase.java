package owep.controle ;


import java.io.IOException ;
import javax.servlet.RequestDispatcher ;
import javax.servlet.ServletException ;
import javax.servlet.http.HttpServlet ;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;
import javax.servlet.http.HttpSession;
import org.exolab.castor.jdo.Database ;
import org.exolab.castor.jdo.JDO ;
import owep.infrastructure.Session ;
import owep.infrastructure.localisation.LocalisateurIdentifiant ;


/**
 * Classe de base pour tous les controleurs, simplifiant la connexion à la base de données et la
 * redirection.
 */
public abstract class CControleurBase extends HttpServlet
{
  private HttpServletRequest  mRequete ; // Requête HTTP à l'origine de l'appel du controleur
  private HttpServletResponse mReponse ; // Réponse HTTP du controleur à la requête
  private Database mBaseDonnees ;        // Connexion à la base de données
  private Session mSession ;             // Session associé à la connexion

  
  /**
   * Appellé lors d'une requête d'un client. Redirige le client vers la page retourné par la méthode
   * traiter.
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
   * @param pRequete Requête HTTP à l'origine de l'appel du controleur
   * @param pReponse Réponse HTTP du controleur à la requête
   * @throws ServletException Si une erreur survient durant le traitement de la page.
   * @throws IOException Si une erreur survient durant le traitement de la page.
   */
  protected void doPost (HttpServletRequest pRequete, HttpServletResponse pReponse)
    throws ServletException, IOException
  {
    RequestDispatcher lRequeteDispatcher ; // Permet d'appeler la JSP d'affichage.
    JDO lJdo ;                             // Charge le système de persistence avec la base de données
    
    // Initialise les variables membres.
    mRequete = pRequete ;
    mReponse = pReponse ;
    
    // Vérifie qu'une session a été ouverte
    HttpSession lSession = mRequete.getSession (true) ;
    mSession = (Session) lSession.getAttribute (CConstante.SES_SESSION) ;
    if(mSession == null)
    {
      lSession.invalidate();
      lRequeteDispatcher = pRequete.getRequestDispatcher ("..\\JSP\\index.jsp") ;
      lRequeteDispatcher.forward (mRequete, mReponse) ;
    }
    else
    {
    
      // Initie la connexion à la base de données.
      try
      {
        JDO.loadConfiguration (LocalisateurIdentifiant.LID_BDCONFIGURATION) ;
        lJdo = new JDO (LocalisateurIdentifiant.LID_BDNOM) ;

        mBaseDonnees = lJdo.getDatabase () ;
        mBaseDonnees.setAutoStore (false) ;
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_CONNEXION) ;
    }
      
    // Appelle la JSP d'affichage retournée par traiter.
    initialiserBaseDonnees () ;
    initialiserParametres () ;
    lRequeteDispatcher = pRequete.getRequestDispatcher (traiter ()) ;
    if (lRequeteDispatcher == null)
    {
      throw new ServletException (CConstante.EXC_FORWARD) ;
    }
    else
    {
      // mis à jour de la session
      lSession.setAttribute ("SESSION", mSession) ;
      
      // redirection vers la page convenue
      lRequeteDispatcher.forward (mRequete, mReponse) ;
    }
    }
  }
  
  
  /**
   * Récupère une connexion à la base de données.
   * @return Connexion à la base de données.
   */
  public Database getBaseDonnees ()
  {
    return mBaseDonnees ;
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
   * Récupère la session ouverte.
   * @return Session ouverte.
   */
  public Session getSession()
  {
    return mSession;
  }
  
  
  /**
   * Récupère les données nécessaire au controleur dans la base de données. 
   * @throws ServletException Si une erreur survient lors de l'initialisation
   */
  public abstract void initialiserBaseDonnees () throws ServletException ;
  
  
  /**
   * Récupère les paramètres passés au controleur. 
   * @throws ServletException Si une erreur survient lors de l'initialisation
   */
  public abstract void initialiserParametres () throws ServletException ;
  
  
  /**
   * Effectue tout le traîtement du controleur puis retourne l'URL vers laquelle le client doit
   * être redirigé. 
   * @return URL de la page vers laquelle doit être redirigé le client.
   * @throws ServletException Si une erreur survient dans le controleur
   */
  public abstract String traiter () throws ServletException ;
}
