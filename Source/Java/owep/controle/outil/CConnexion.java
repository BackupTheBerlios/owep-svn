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
  private HttpServletRequest mRequete ; // Requ�te HTTP � l'origine de l'appel du controleur
  private HttpServletResponse mReponse ; // R�ponse HTTP du controleur � la requ�te
  private Database mBaseDonnees ; // Connexion � la base de donn�es
  private MCollaborateur mCollaborateur = null ; // Collaborateur correspondant au login et mot de
  // passe
  private String mLogin = null ; // Login saisi
  private String mPassword = null ; // mot de passe saisi
  // configuration
  private MConfiguration mConfiguration = null ; //�l�ments de configuration

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
    JDO lJdo ; // Charge le syst�me de persistence avec la base de donn�es

    // Initialise les variables membres.
    mRequete = pRequete ;
    mReponse = pReponse ;

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
   * R�cup�re une connexion � la base de donn�es.
   * 
   * @return Connexion � la base de donn�es.
   */
  public Database getBaseDonnees ()
  {
    return mBaseDonnees ;
  }

  /**
   * R�cup�re la r�ponse HTTP que le controleur va fournir au client.
   * 
   * @return R�ponse HTTP du controleur.
   */
  public HttpServletResponse getReponse ()
  {
    return mReponse ;
  }

  /**
   * R�cup�re la requ�te HTTP � l'origine de l'appel du controleur.
   * 
   * @return Requ�te HTTP � l'origine de l'appel du controleur.
   */
  public HttpServletRequest getRequete ()
  {
    return mRequete ;
  }

  private void initialiserBaseDonnees () throws ServletException
  {
    OQLQuery lRequete ; // Requ�te � r�aliser sur la base
    QueryResults lResultat ; // R�sultat de la requ�te sur la base

    try
    {
      getBaseDonnees ().begin () ;

      // R�cup�re le collaborateur connect�.
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
      
      // R�cup�re les �l�ments de configuration
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
   * R�cup�re les attributs pass�s au controleur.
   */
  public void initialiserParametres ()
  {
    mLogin = getRequete ().getParameter ("login") ;
    mPassword = getRequete ().getParameter ("pwd") ;
  }

  /**
   * Effectue tout le tra�tement du controleur puis retourne l'URL vers laquelle le client doit �tre
   * redirig�.
   * 
   * @return URL de la page vers laquelle doit �tre redirig� le client.
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
      // Cr�ation de la classe Session
      Session mSession = new Session () ;
      mSession.ouvertureSession (mCollaborateur) ;

      
      //traitement de la localisation
        //S�lection de la langue dans la BD
        String langue = new String(mConfiguration.getLangue());
        String pays = new String(mConfiguration.getPays());
      
        //Cr�ation du ressource bundle
        Locale currentLocale;
        ResourceBundle messages;
        currentLocale = new Locale(langue, pays);
        messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
     
        //ajout dans la session du resource bundle
        mSession.setMessages(messages);
        
        //ajout en session de l'objet configuration
        mSession.setConfiguration(mConfiguration);
      
      
      // Cr�ation de la session HTTP
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