package owep.controle.outil ;


import java.io.IOException ;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher ;
import javax.servlet.ServletException ;
import javax.servlet.http.HttpServlet ;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;
import javax.servlet.http.HttpSession ;
import org.exolab.castor.jdo.Database ;
import org.exolab.castor.jdo.JDO ;
import org.exolab.castor.jdo.OQLQuery ;
import org.exolab.castor.jdo.QueryResults ;
import owep.controle.CConstante ;
import owep.infrastructure.Session ;
import owep.infrastructure.localisation.LocalisateurIdentifiant ;
import owep.modele.configuration.MConfiguration;
import owep.modele.execution.MCollaborateur ;


/**
 * Controleur permettant l'ouverture d'une session utilisateur
 */
public class CConnexion extends HttpServlet
{
  private HttpServletRequest mRequete ; // Requête HTTP à l'origine de l'appel du controleur
  private HttpServletResponse mReponse ; // Réponse HTTP du controleur à la requête
  private Database mBaseDonnees ; // Connexion à la base de données
  private MCollaborateur mCollaborateur = null ; // Collaborateur correspondant au login et mot de
  // passe
  private String mLogin = null ; // Login saisi
  private String mPassword = null ; // mot de passe saisi
  // configuration
  private MConfiguration mConfiguration = null ; //éléments de configuration

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
    JDO lJdo ; // Charge le système de persistence avec la base de données

    // Initialise les variables membres.
    mRequete = pRequete ;
    mReponse = pReponse ;

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
    initialiserParametres () ;
    initialiserBaseDonnees () ;
    lRequeteDispatcher = pRequete.getRequestDispatcher (traiter ()) ;
    if (lRequeteDispatcher == null)
    {
      throw new ServletException (CConstante.EXC_FORWARD) ;
    }
    else
    {
      lRequeteDispatcher.forward (mRequete, mReponse) ;
    }
  }

  /**
   * Récupère une connexion à la base de données.
   * 
   * @return Connexion à la base de données.
   */
  public Database getBaseDonnees ()
  {
    return mBaseDonnees ;
  }

  /**
   * Récupère la réponse HTTP que le controleur va fournir au client.
   * 
   * @return Réponse HTTP du controleur.
   */
  public HttpServletResponse getReponse ()
  {
    return mReponse ;
  }

  /**
   * Récupère la requête HTTP à l'origine de l'appel du controleur.
   * 
   * @return Requête HTTP à l'origine de l'appel du controleur.
   */
  public HttpServletRequest getRequete ()
  {
    return mRequete ;
  }

  private void initialiserBaseDonnees () throws ServletException
  {
    OQLQuery lRequete ; // Requête à réaliser sur la base
    QueryResults lResultat ; // Résultat de la requête sur la base

    try
    {
      getBaseDonnees ().begin () ;

      // Récupère le collaborateur connecté.
      lRequete = getBaseDonnees ()
        .getOQLQuery (
                      "select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mUtilisateur = $1") ;
      lRequete.bind (mLogin) ;
      lResultat = lRequete.execute () ;

      if (lResultat.size () > 0)
      {
        // On suppose que le nom est unique
        mCollaborateur = (MCollaborateur) lResultat.next () ;
      }
      
      // Récupère les éléments de configuration
      lRequete = getBaseDonnees ()
        .getOQLQuery (
                      "select CONFIGURATION from owep.modele.configuration.MConfiguration CONFIGURATION where mId = $1") ;
      lRequete.bind (1) ;
      lResultat = lRequete.execute () ;

      if (lResultat.size () > 0)
      {
        mConfiguration = (MConfiguration) lResultat.next () ;
      }
      getBaseDonnees ().commit () ;
      
      getBaseDonnees ().close () ;
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
  }

  /**
   * Récupère les attributs passés au controleur.
   */
  public void initialiserParametres ()
  {
    mLogin = getRequete ().getParameter ("login") ;
    mPassword = getRequete ().getParameter ("pwd") ;
  }

  /**
   * Effectue tout le traîtement du controleur puis retourne l'URL vers laquelle le client doit être
   * redirigé.
   * 
   * @return URL de la page vers laquelle doit être redirigé le client.
   */
  public String traiter ()
  {    
    if (mLogin == null)
    {
      return "..\\JSP\\index.jsp" ;
    }

    if (mLogin.equals (""))
    {
      getRequete ().setAttribute ("mProbleme", "true") ;
      return "..\\JSP\\index.jsp" ;
    }

    if (mCollaborateur != null && mPassword.equals (mCollaborateur.getMotDePasse ()))
    {
      // Création de la classe Session
      Session mSession = new Session () ;
      mSession.ouvertureSession (mCollaborateur) ;

      
      //traitement de la localisation
        //Sélection de la langue dans la BD
        String langue = new String(mConfiguration.getLangue());
        String pays = new String(mConfiguration.getPays());
      
        //Création du ressource bundle
        Locale currentLocale;
        ResourceBundle messages;
        currentLocale = new Locale(langue, pays);
        messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
     
        //ajout dans la session du resource bundle
        mSession.setMessages(messages);
        
        //ajout en session de l'objet configuration
        mSession.setConfiguration(mConfiguration);
      
      
      // Création de la session HTTP
      HttpSession session = getRequete ().getSession (true) ;
      session.setAttribute ("SESSION", mSession) ;
      
      return "..\\Projet\\OuvrirProjet";
    }
    else
    {
      getRequete ().getSession ().invalidate () ;
      getRequete ().setAttribute ("mProbleme", "true") ;
      return "..\\JSP\\index.jsp" ;
    }
  }
}