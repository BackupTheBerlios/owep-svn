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
 * @author Emmanuelle et Rémi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CValidationRapport extends CControleurBase{
  private int mIterationNum ;  // Numéro d'itération
  private MTache mTache ;      // Tache concernée par le changement d'état
  private MTacheImprevue mTacheImprevue ; // Tache imprévue concernée par le changement d'état
  private MCollaborateur mCollaborateur ; // Collaborateur ayant ouvert la session
  private Session mSession ;              // Session associé à la connexion 
  private double tps ; //temps passé sur la tache
  private double rps ; //reste a passer sur la tache
  private int ET ; //etat de la tache
  private Date finReest ; //date de fin reestimee de la tache
  private Date debutReel ; //date de debut réelle de la tache
  private String lTypeTache ; // type de la tache (tache imprevue ou tache)
  
  
  /**
   * Récupère les données nécessaire au controleur dans la base de données. 
   * @throws ServletException Si une erreur survient durant la connexion
   * @see owep.controle.CControleurBase#initialiserBaseDonnees()
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    OQLQuery       lRequete ;       // Requête à réaliser sur la base
    QueryResults   lResultat ;      // Résultat de la requête sur la base
    
    try
    {
//    Récupère le collaborateur connecté
      HttpSession session = getRequete ().getSession (true) ;
      mSession = (Session)session.getAttribute("SESSION") ;
      mCollaborateur = mSession.getCollaborateur() ;
      
      getBaseDonnees ().begin () ;
      
      int idCollab = mCollaborateur.getId() ;
      // Récupère la liste des tâches du collaborateur.
      lRequete = getBaseDonnees ().getOQLQuery ("select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mId = $1") ;
      lRequete.bind (idCollab) ;
      lResultat      = lRequete.execute () ;
      mCollaborateur = (MCollaborateur) lResultat.next () ;
      
      // récupération du type de la tâche
      lTypeTache = (String)getRequete().getParameter(CConstante.PAR_TYPE_TACHE);
      
      if (lTypeTache.equals("tache"))
      {
	    // récupération de l'id de la tache
	    int idTache = Integer.parseInt(getRequete().getParameter(CConstante.PAR_TACHE));
	
	    // Récupère la tâche
	    String req = "select TACHE from owep.modele.execution.MTache TACHE where mId = $1";
	    lRequete = getBaseDonnees ().getOQLQuery (req) ;
	    lRequete.bind (idTache) ;
	    lResultat      = lRequete.execute () ;
	    mTache = (MTache) lResultat.next () ;
      }
      else if (lTypeTache.equals("tacheImprevue"))
      {
        // récupération de l'id de la tache imprevue
	    int idTacheImprevue = Integer.parseInt(getRequete().getParameter(CConstante.PAR_TACHE));
	
	    // Récupère la tâche
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
   * Récupère les paramètres passés au controleur. 
   * @throws ServletException -
   * @see owep.controle.CControleurBase#initialiserParametres()
   */
  public void initialiserParametres () throws ServletException
  {
    try
    {
      // récupération du temps passé
      tps = Double.parseDouble(getRequete().getParameter(CConstante.PAR_TEMPSPASSE));
      
      // récupération du reste à passer
      rps = Double.parseDouble(getRequete().getParameter(CConstante.PAR_RESTEAPASSER));
      
      // récupération de l'état
      ET = Integer.parseInt(getRequete().getParameter(CConstante.PAR_ETAT));
      
      // récupération de la date de fin reestimée
      finReest = new SimpleDateFormat ("yyyy-MM-dd").parse(getRequete().getParameter(CConstante.PAR_DATEFINREELLE));

      // récupération de la date de debut réelle
      debutReel = new SimpleDateFormat ("yyyy-MM-dd").parse(getRequete().getParameter(CConstante.PAR_DATEDEBUTREELLE));
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
  }
  
  
  /**
   * Récupère la liste des tâches d'un collaborateur pour l'itération choisie, et la transmet à la
   * JSP. 
   * @return URL de la page vers laquelle doit être redirigé le client.
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
	    // mise à jour de la tâche avec les valeurs validées précédemment
	    mTache.setTempsPasse(tps) ;
	    mTache.setResteAPasser(rps) ; 
	    mTache.setEtat(ET) ; 
	    mTache.setDateFinReelle(finReest) ;
	    mTache.setDateDebutReelle(debutReel);
	    mTache.setDateDebutChrono(0) ;
	    // Le collaborateur n'a désormais plus de tâche en cours
	    mCollaborateur.setTacheEnCours(-1) ;
        
	    if (ET == 2)
	      lMessage = messages.getString("cEtatTache")+" \""+mTache.getNom()+"\" "+messages.getString("cValidationRapportTacheSuspendue");
	    else if (ET == 3)
	      lMessage = messages.getString("cEtatTache")+" \""+mTache.getNom()+"\" "+messages.getString("cValidationRapportTacheTerminee");
      }
      else if (lTypeTache.equals("tacheImprevue"))
      {
        // mise à jour de la tâche avec les valeurs validées précédemment
	    mTacheImprevue.setTempsPasse(tps) ;
	    mTacheImprevue.setResteAPasser(rps) ; 
	    mTacheImprevue.setEtat(ET) ; 
	    mTacheImprevue.setDateFinReelle(finReest) ;
	    mTacheImprevue.setDateDebutReelle(debutReel);
	    mTacheImprevue.setDateDebutChrono(0) ;
	    // Le collaborateur n'a désormais plus de tâche en cours
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
    // Ferme la connexion à la base de données.
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
 
    // Transmet les données à la JSP d'affichage.
    getRequete ().setAttribute (CConstante.PAR_MESSAGE, lMessage) ;
    return "ListeTacheVisu" ;
  }
}
