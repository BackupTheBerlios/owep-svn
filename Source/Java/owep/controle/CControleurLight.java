package owep.controle;


import java.io.IOException ;
import javax.servlet.ServletException ;
import javax.servlet.http.HttpServlet ;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;
import org.exolab.castor.jdo.Database ;
import org.exolab.castor.jdo.JDO ;
import org.exolab.castor.jdo.OQLQuery ;
import org.exolab.castor.jdo.QueryResults ;
import owep.infrastructure.Session ;
import owep.infrastructure.localisation.LocalisateurIdentifiant ;
import owep.modele.MModeleBase;


/**
 * Classe "léger" simplifiant la connexion à la base de données utilisé uniquement pour une
 * inclusion dans une page (et non un forward).
 */
public abstract class CControleurLight extends HttpServlet
{
  private HttpServletRequest  mRequete ;     // Requête HTTP à l'origine de l'appel du controleur
  private HttpServletResponse mReponse ;     // Réponse HTTP du controleur à la requête
  private Database            mBaseDonnees ; // Connexion à la base de données
  private Session             mSession ;     // Session associé à la connexion
  private OQLQuery            mRequeteSQL ;  // Requête à réaliser sur la base.
  private QueryResults        mResultatSQL ; // Résultat de la requête sur la base.

  
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
    JDO lJdo ;                             // Charge le système de persistence avec la base de données
    
    // Initialise les variables membres.
    mRequete = pRequete ;
    mReponse = pReponse ;
    
    // Initie la connexion à la base de données.
    try
    {
      JDO.loadConfiguration (getServletContext ().getRealPath ("/")+LocalisateurIdentifiant.LID_BDCONFIGURATION) ;
      lJdo = new JDO (LocalisateurIdentifiant.LID_BDNOM) ;
      
      mBaseDonnees = lJdo.getDatabase () ;
      mBaseDonnees.setAutoStore (false) ;
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_CONNEXION) ;
    }
      
    initialiserBaseDonnees () ;
    initialiserParametres () ;
    
    traiter () ;
    
    // Libère la mémoire.
    mRequeteSQL  = null ;
    mResultatSQL = null ;
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
   * Démarre une transaction vers la base de données.
   * @throws ServletException Si une erreur survient lors de la connexion.
   */
  public void begin () throws ServletException
  {
    try
    {
      mBaseDonnees.begin () ;
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
  }
  
  
  /**
   * Valide une transaction dans la base de données.
   * @throws ServletException Si une erreur survient lors de la validation.
   */
  public void commit () throws ServletException
  {
    try
    {
      mBaseDonnees.commit () ;
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
  }
  
  
  /**
   * Ferme la connexion vers la base de données.
   * @throws ServletException Si une erreur survient lors de la fermeture.
   */
  public void close () throws ServletException
  {
    try
    {
      mBaseDonnees.close () ;
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
  }

  
  /**
   * Crée une nouvelle requête pour interroger la base de données.
   * @param pRequete Requête d'interrogation de la base de données.
   * @throws ServletException Si une erreur survient lors de l'interrogation.
   */
  public void creerRequete (String pRequete) throws ServletException
  {
    try
    {
      mRequeteSQL = mBaseDonnees.getOQLQuery (pRequete) ;
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
  }
  
  
  /**
   * Crée une nouvelle requête pour interroger la base de données.
   * @param pParametre Paramètre à passer à la requête (à passer dans l'ordre de leur définition).
   * @throws ServletException Si une erreur survient lors de l'interrogation.
   */
  public void parametreRequete (int pParametre) throws ServletException
  {
    try
    {
      mRequeteSQL.bind (pParametre) ;
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
  }
  
  
  /**
   * Exécute une requête sur la base de données.
   * @throws ServletException Si une erreur survient lors de l'exécution.
   */
  public void executer () throws ServletException
  {
    try
    {
      mResultatSQL = mRequeteSQL.execute () ;
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
  }
  
  
  /**
   * Indique si le résultat de l'exécution de la requête contient encore une donnée.
   * @return Vrai si le résultat contient encore un résultat et faux sinon.
   * @throws ServletException Si une erreur survient lors de la consultation.
   */
  public boolean contientResultat () throws ServletException
  {
    try
    {
      return mResultatSQL.hasMore () ;
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
  }
  
  
  /**
   * Récupère une ligne de résultats issu de l'exécution de la requête.
   * @return Ligne de résultat.
   * @throws ServletException S'il n'y a plus de lignes de résultats.
   */
  public Object getResultat () throws ServletException
  {
    try
    {
      return mResultatSQL.next () ;
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
  }
  
  
  /**
   * Crée un nouvel objet dans la base.
   * @param pObjet Objet à créer.
   * @throws ServletException Si une erreur survient lors de la création.
   */
  public void creer (MModeleBase pObjet) throws ServletException
  {
    try
    {
      mBaseDonnees.create (pObjet) ;
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
  }
  
  
  /**
   * Supprime un objet la base.
   * @param pObjet Objet à supprimer.
   * @throws ServletException Si une erreur survient lors de la suppression.
   */
  public void supprimer (MModeleBase pObjet) throws ServletException
  {
    try
    {
      mBaseDonnees.remove (pObjet) ;
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
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
