package owep.controle.avancement;

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
import owep.modele.execution.MIteration;
import owep.modele.execution.MProjet;


/**
 * @author Emmanuelle et Rémi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CAvancementGlobalProjet extends CControleurBase{ 
    private Session mSession ; // Session associé à la connexion
    private MProjet mProjet ; //projet ouvert par le collaborateur
        
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
        // Récupère le projet pour lequel le chef de projet s 'est connecté
        HttpSession session = getRequete ().getSession (true) ;
        mSession = (Session)session.getAttribute("SESSION") ;
        mProjet = mSession.getProjet() ;
        double lTempsPrevu = 0 ;
        double lTempsPasse = 0 ;
        double lResteAPasser = 0 ;
        Date lAuxDateDebutReelle = new SimpleDateFormat ("yyyy-MM-dd").parse("2100-01-01") ;
        Date lDateDebutReelle = null ;
        
        getBaseDonnees ().begin () ;
        
        int idProjet = mProjet.getId() ;
        // Récupère le projet dans la BD
        lRequete = getBaseDonnees ().getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
        lRequete.bind (idProjet) ;
        lResultat      = lRequete.execute () ;
        mProjet = (MProjet) lResultat.next () ;
        
        int nbIteration = 0 ;
        // on recupere chaque iteration
        for (int i = 0 ; i < mProjet.getNbIterations(); i++)
        {
	      int lIdIteration = mProjet.getIteration(i).getId() ;
	        
	      // recuperation des iterations du projet
	      // on recupere les iterations dans la BD
	
	      lRequete = getBaseDonnees ().getOQLQuery ("select ITERATION from owep.modele.execution.MIteration ITERATION where mId = $1") ;
	      lRequete.bind (lIdIteration) ;
	      lResultat      = lRequete.execute () ;
          MIteration lIteration = (MIteration) lResultat.next () ;
          lTempsPrevu += lIteration.getChargeInitiale() ;
          lTempsPasse += lIteration.getTempsPasse() ;
          lResteAPasser += lIteration.getResteAPasser() ;
          if (lIteration.getDateDebutReelle()!=null)
          {
            if (lAuxDateDebutReelle.after(lIteration.getDateDebutReelle()))
            {
              lAuxDateDebutReelle = lIteration.getDateDebutReelle() ;
              lDateDebutReelle = lIteration.getDateDebutReelle() ;
            }
          }
        
          // ajout de la tache rechargée a la liste des taches rechargées
          mProjet.setListe(new Integer(nbIteration+1), lIteration) ;
          nbIteration++ ;
        }
        if (nbIteration > 0)
        {
	      mProjet.setListe(new Integer(0), new Integer(nbIteration)) ;
	      mProjet.setListe(new Integer(nbIteration+1), new Double(lTempsPrevu)) ;
	      mProjet.setListe(new Integer(nbIteration+2), new Double(lTempsPasse)) ;
	      mProjet.setListe(new Integer(nbIteration+3), new Double(lResteAPasser)) ;
	      mProjet.setListe(new Integer(nbIteration+4), mProjet.getDateDebutPrevue()) ;
	      mProjet.setListe(new Integer(nbIteration+5), lDateDebutReelle) ;
	      mProjet.setListe(new Integer(nbIteration+6), mProjet.getDateFinPrevue()) ;
	      mProjet.setListe(new Integer(nbIteration+7), new Double((lTempsPasse + lResteAPasser - lTempsPrevu) / lTempsPrevu)) ;
	      mProjet.setListe(new Integer(nbIteration+8), new Double(lTempsPasse + lResteAPasser - lTempsPrevu)) ;
	      mProjet.setListe(new Integer(nbIteration+9), new Double(lTempsPasse / lTempsPrevu)) ;
	      mProjet.setListe(new Integer(nbIteration+10), new Double(lTempsPasse / (lTempsPasse + lResteAPasser))) ;
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
    }
    
    /**
     * Récupère les paramètres passés au controleur. 
     * @throws ServletException -
     * @see owep.controle.CControleurBase#initialiserParametres()
     */
    public void initialiserParametres () throws ServletException
    {

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
      // Transmet les données à la JSP d'affichage.
      getRequete ().setAttribute (CConstante.PAR_PROJET, mProjet) ;
      
      return "..\\JSP\\Avancement\\TAvancementGlobalProjet.jsp" ; 
    }
}
