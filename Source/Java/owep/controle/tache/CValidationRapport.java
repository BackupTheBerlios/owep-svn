package owep.controle.tache;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.jdo.QueryResults;

import owep.controle.CConstante;
import owep.controle.CControleurBase;
import owep.infrastructure.Session;
import owep.modele.execution.MCollaborateur;
import owep.modele.execution.MTache;
import owep.modele.execution.MTacheImprevue;


/**
 * @author Emmanuelle et R�mi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CValidationRapport extends CControleurBase{
  private int mIterationNum ;  // Num�ro d'it�ration
  private MTache mTache ;      // Tache concern�e par le changement d'�tat
  private MTacheImprevue mTacheImprevue ; // Tache impr�vue concern�e par le changement d'�tat
  private MCollaborateur mCollaborateur ; // Collaborateur ayant ouvert la session
  private Session mSession ;              // Session associ� � la connexion 
  private double tps ; //temps pass� sur la tache
  private double rps ; //reste a passer sur la tache
  private int ET ; //etat de la tache
  private Date finReest ; //date de fin reestimee de la tache
  private Date debutReel ; //date de debut r�elle de la tache
  private String lTypeTache ; // type de la tache (tache imprevue ou tache)
  
  
  /**
   * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es. 
   * @throws ServletException Si une erreur survient durant la connexion
   * @see owep.controle.CControleurBase#initialiserBaseDonnees()
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    OQLQuery       lRequete ;       // Requ�te � r�aliser sur la base
    QueryResults   lResultat ;      // R�sultat de la requ�te sur la base
    
    try
    {
//    R�cup�re le collaborateur connect�
      HttpSession session = getRequete ().getSession (true) ;
      mSession = (Session)session.getAttribute("SESSION") ;
      mCollaborateur = mSession.getCollaborateur() ;
      
      getBaseDonnees ().begin () ;
      
      int idCollab = mCollaborateur.getId() ;
      // R�cup�re la liste des t�ches du collaborateur.
      lRequete = getBaseDonnees ().getOQLQuery ("select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mId = $1") ;
      lRequete.bind (idCollab) ;
      lResultat      = lRequete.execute () ;
      mCollaborateur = (MCollaborateur) lResultat.next () ;
      
      // r�cup�ration du type de la t�che
      lTypeTache = (String)getRequete().getParameter(CConstante.PAR_TYPE_TACHE);
      
      if (lTypeTache.equals("tache"))
      {
	    // r�cup�ration de l'id de la tache
	    int idTache = Integer.parseInt(getRequete().getParameter(CConstante.PAR_TACHE));
	
	    // R�cup�re la t�che
	    String req = "select TACHE from owep.modele.execution.MTache TACHE where mId = $1";
	    lRequete = getBaseDonnees ().getOQLQuery (req) ;
	    lRequete.bind (idTache) ;
	    lResultat      = lRequete.execute () ;
	    mTache = (MTache) lResultat.next () ;
      }
      else if (lTypeTache.equals("tacheImprevue"))
      {
        // r�cup�ration de l'id de la tache imprevue
	    int idTacheImprevue = Integer.parseInt(getRequete().getParameter(CConstante.PAR_TACHE));
	
	    // R�cup�re la t�che
	    String req = "select TACHEIMPREVUE from owep.modele.execution.MTacheImprevue TACHEIMPREVUE where mId = $1";
	    lRequete = getBaseDonnees ().getOQLQuery (req) ;
	    lRequete.bind (idTacheImprevue) ;
	    lResultat      = lRequete.execute () ;
	    mTacheImprevue = (MTacheImprevue) lResultat.next () ;
      }

    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
  }
  
  /**
   * R�cup�re les param�tres pass�s au controleur. 
   * @throws ServletException -
   * @see owep.controle.CControleurBase#initialiserParametres()
   */
  public void initialiserParametres () throws ServletException
  {
    try
    {
      // r�cup�ration du temps pass�
      tps = Double.parseDouble(getRequete().getParameter(CConstante.PAR_TEMPSPASSE));
      
      // r�cup�ration du reste � passer
      rps = Double.parseDouble(getRequete().getParameter(CConstante.PAR_RESTEAPASSER));
      
      // r�cup�ration de l'�tat
      ET = Integer.parseInt(getRequete().getParameter(CConstante.PAR_ETAT));
      
      // r�cup�ration de la date de fin reestim�e
      finReest = new SimpleDateFormat ("yyyy-MM-dd").parse(getRequete().getParameter(CConstante.PAR_DATEFINREELLE));

      // r�cup�ration de la date de debut r�elle
      debutReel = new SimpleDateFormat ("yyyy-MM-dd").parse(getRequete().getParameter(CConstante.PAR_DATEDEBUTREELLE));
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
  }
  
  
  /**
   * R�cup�re la liste des t�ches d'un collaborateur pour l'it�ration choisie, et la transmet � la
   * JSP. 
   * @return URL de la page vers laquelle doit �tre redirig� le client.
   * @throws ServletException Si une erreur survient dans le controleur
   * @see owep.controle.CControleurBase#traiter()
   */
  public String traiter () throws ServletException
  {  
    String lMessage = "" ;
    java.util.ResourceBundle messages;
    messages = java.util.ResourceBundle.getBundle("MessagesBundle");
    try
    {
      if (lTypeTache.equals("tache"))
      {
	    // mise � jour de la t�che avec les valeurs valid�es pr�c�demment
	    mTache.setTempsPasse(tps) ;
	    mTache.setResteAPasser(rps) ; 
	    mTache.setEtat(ET) ; 
	    mTache.setDateFinReelle(finReest) ;
	    mTache.setDateDebutReelle(debutReel);
	    mTache.setDateDebutChrono(0) ;
	    // Le collaborateur n'a d�sormais plus de t�che en cours
	    mCollaborateur.setTacheEnCours(-1) ;
        
	    if (ET == 2)
	      lMessage = messages.getString("cEtatTache")+" \""+mTache.getNom()+"\" "+messages.getString("cValidationRapportTacheSuspendue");
	    else if (ET == 3)
	      lMessage = messages.getString("cEtatTache")+" \""+mTache.getNom()+"\" "+messages.getString("cValidationRapportTacheTerminee");
      }
      else if (lTypeTache.equals("tacheImprevue"))
      {
        // mise � jour de la t�che avec les valeurs valid�es pr�c�demment
	    mTacheImprevue.setTempsPasse(tps) ;
	    mTacheImprevue.setResteAPasser(rps) ; 
	    mTacheImprevue.setEtat(ET) ; 
	    mTacheImprevue.setDateFinReelle(finReest) ;
	    mTacheImprevue.setDateDebutReelle(debutReel);
	    mTacheImprevue.setDateDebutChrono(0) ;
	    // Le collaborateur n'a d�sormais plus de t�che en cours
	    mCollaborateur.setTacheEnCours(-1) ;
	    
	    if (ET == 2)
	      lMessage = messages.getString("cEtatTacheImprevue")+" \""+mTacheImprevue.getNom()+"\" "+messages.getString("cValidationRapportTacheSuspendue");
	    else if (ET == 3)
	      lMessage = messages.getString("cEtatTacheImprevue")+" \""+mTacheImprevue.getNom()+"\" "+messages.getString("cValidationRapportTacheTerminee");
      }
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
    // Ferme la connexion � la base de donn�es.
    finally
    {
      try
      {
        getBaseDonnees ().commit () ;
        getBaseDonnees ().close () ;
      }
      catch (PersistenceException eException)
      {
        eException.printStackTrace () ;
        throw new ServletException (CConstante.EXC_DECONNEXION) ;
      }
    }
 
    // Transmet les donn�es � la JSP d'affichage.
    getRequete ().setAttribute (CConstante.PAR_MESSAGE, lMessage) ;
    return "ListeTacheVisu" ;
  }
}
