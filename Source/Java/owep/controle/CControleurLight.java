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
 * Classe "l�ger" simplifiant la connexion � la base de donn�es utilis� uniquement pour une
 * inclusion dans une page (et non un forward).
 */
public abstract class CControleurLight extends HttpServlet
{
  private HttpServletRequest  mRequete ;     // Requ�te HTTP � l'origine de l'appel du controleur
  private HttpServletResponse mReponse ;     // R�ponse HTTP du controleur � la requ�te
  private Database            mBaseDonnees ; // Connexion � la base de donn�es
  private Session             mSession ;     // Session associ� � la connexion
  private OQLQuery            mRequeteSQL ;  // Requ�te � r�aliser sur la base.
  private QueryResults        mResultatSQL ; // R�sultat de la requ�te sur la base.

  
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
    JDO lJdo ;                             // Charge le syst�me de persistence avec la base de donn�es
    
    // Initialise les variables membres.
    mRequete = pRequete ;
    mReponse = pReponse ;
    
    // Initie la connexion � la base de donn�es.
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
    
    // Lib�re la m�moire.
    mRequeteSQL  = null ;
    mResultatSQL = null ;
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
   * D�marre une transaction vers la base de donn�es.
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
   * Valide une transaction dans la base de donn�es.
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
   * Ferme la connexion vers la base de donn�es.
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
   * Cr�e une nouvelle requ�te pour interroger la base de donn�es.
   * @param pRequete Requ�te d'interrogation de la base de donn�es.
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
   * Cr�e une nouvelle requ�te pour interroger la base de donn�es.
   * @param pParametre Param�tre � passer � la requ�te (� passer dans l'ordre de leur d�finition).
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
   * Ex�cute une requ�te sur la base de donn�es.
   * @throws ServletException Si une erreur survient lors de l'ex�cution.
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
   * Indique si le r�sultat de l'ex�cution de la requ�te contient encore une donn�e.
   * @return Vrai si le r�sultat contient encore un r�sultat et faux sinon.
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
   * R�cup�re une ligne de r�sultats issu de l'ex�cution de la requ�te.
   * @return Ligne de r�sultat.
   * @throws ServletException S'il n'y a plus de lignes de r�sultats.
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
   * Cr�e un nouvel objet dans la base.
   * @param pObjet Objet � cr�er.
   * @throws ServletException Si une erreur survient lors de la cr�ation.
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
   * @param pObjet Objet � supprimer.
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
