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
 * Classe de base pour tous les controleurs, simplifiant la connexion � la base de donn�es et la
 * redirection.
 */
public abstract class CControleurBase extends HttpServlet
{
  private HttpServletRequest  mRequete ; // Requ�te HTTP � l'origine de l'appel du controleur
  private HttpServletResponse mReponse ; // R�ponse HTTP du controleur � la requ�te
  private Database mBaseDonnees ;        // Connexion � la base de donn�es
  private Session mSession ;             // Session associ� � la connexion

  
  /**
   * Appell� lors d'une requ�te d'un client. Redirige le client vers la page retourn� par la m�thode
   * traiter.
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
   * @param pRequete Requ�te HTTP � l'origine de l'appel du controleur
   * @param pReponse R�ponse HTTP du controleur � la requ�te
   * @throws ServletException Si une erreur survient durant le traitement de la page.
   * @throws IOException Si une erreur survient durant le traitement de la page.
   */
  protected void doPost (HttpServletRequest pRequete, HttpServletResponse pReponse)
    throws ServletException, IOException
  {
    RequestDispatcher lRequeteDispatcher ; // Permet d'appeler la JSP d'affichage.
    JDO lJdo ;                             // Charge le syst�me de persistence avec la base de donn�es
    
    // Initialise les variables membres.
    mRequete = pRequete ;
    mReponse = pReponse ;
    
    // V�rifie qu'une session a �t� ouverte
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
    
      // Initie la connexion � la base de donn�es.
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
      
    // Appelle la JSP d'affichage retourn�e par traiter.
    initialiserBaseDonnees () ;
    initialiserParametres () ;
    lRequeteDispatcher = pRequete.getRequestDispatcher (traiter ()) ;
    if (lRequeteDispatcher == null)
    {
      throw new ServletException (CConstante.EXC_FORWARD) ;
    }
    else
    {
      // mis � jour de la session
      lSession.setAttribute ("SESSION", mSession) ;
      
      // redirection vers la page convenue
      lRequeteDispatcher.forward (mRequete, mReponse) ;
    }
    }
  }
  
  
  /**
   * R�cup�re une connexion � la base de donn�es.
   * @return Connexion � la base de donn�es.
   */
  public Database getBaseDonnees ()
  {
    return mBaseDonnees ;
  }
  
  
  /**
   * R�cup�re la r�ponse HTTP que le controleur va fournir au client.
   * @return R�ponse HTTP du controleur.
   */
  public HttpServletResponse getReponse ()
  {
    return mReponse ;
  }
  
  
  /**
   * R�cup�re la requ�te HTTP � l'origine de l'appel du controleur.
   * @return Requ�te HTTP � l'origine de l'appel du controleur.
   */
  public HttpServletRequest getRequete ()
  {
    return mRequete ;
  }
  
  
  /**
   * R�cup�re la session ouverte.
   * @return Session ouverte.
   */
  public Session getSession()
  {
    return mSession;
  }
  
  
  /**
   * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es. 
   * @throws ServletException Si une erreur survient lors de l'initialisation
   */
  public abstract void initialiserBaseDonnees () throws ServletException ;
  
  
  /**
   * R�cup�re les param�tres pass�s au controleur. 
   * @throws ServletException Si une erreur survient lors de l'initialisation
   */
  public abstract void initialiserParametres () throws ServletException ;
  
  
  /**
   * Effectue tout le tra�tement du controleur puis retourne l'URL vers laquelle le client doit
   * �tre redirig�. 
   * @return URL de la page vers laquelle doit �tre redirig� le client.
   * @throws ServletException Si une erreur survient dans le controleur
   */
  public abstract String traiter () throws ServletException ;
}
