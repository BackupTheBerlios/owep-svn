package owep.controle.avancement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import owep.modele.execution.MIteration;
import owep.modele.execution.MProjet;
import owep.modele.execution.MTache;
import owep.modele.execution.MTacheImprevue;

/*
 * Created on 25 janv. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Emmanuelle et R�mi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CAvancementTachesCollabs extends CControleurBase{ 
    private MProjet mProjet ; //projet ouvert par le collaborateur
    private Session mSession ; // Session associ� � la connexion
    private MIteration mIteration ; // num�ro d'it�ration dont on veut voir le suivi d'avancement
    private int mIterationNum ; // Num�ro d'it�ration dont on suit l avancement des collaborateurs
    private int mIterationEnCours = -1 ; // Numero de l iteration en cours
        
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
        // R�cup�re le projet pour lequel le chef de projet s 'est connect�
        HttpSession session = getRequete ().getSession (true) ;
        mSession = (Session)session.getAttribute("SESSION") ;
        mProjet = mSession.getProjet() ;
        
        getBaseDonnees ().begin () ;
        
        int idProjet = mProjet.getId() ;
        // R�cup�re le projet dans la BD
        lRequete = getBaseDonnees ().getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
        lRequete.bind (idProjet) ;
        lResultat      = lRequete.execute () ;
        mProjet = (MProjet) lResultat.next () ;
        getBaseDonnees ().commit () ;
        
        // recuperation du numero de l iteration choisie dans le menu deroulant
        mIteration = mSession.getIteration() ;
        mIterationNum = mIteration.getNumero();
        
        // recherche de l iteration en cours
        for (int m = 0; m < mProjet.getNbIterations() ; m++)
        {
          if (mProjet.getIteration(m).getEtat()==1)
            mIterationEnCours = mProjet.getIteration(m).getId();
        }

        // si on a demand� l affichage du suivi des collaborateurs pour une
        // autre iteration que celle en cours
        if (mIterationNum != mIterationEnCours)
        {
          // creation de la liste des taches des collaborateurs
          MCollaborateur lCollaborateur ;

          // pour chaque collaborateur
          for (int i = 0 ; i < mProjet.getNbCollaborateurs() ; i++)
          {
            MTache lTache ; 
            MTacheImprevue lTacheImprevue ; 
            ArrayList lListeTaches = new ArrayList ();
            ArrayList lListeTachesImprevues = new ArrayList ();
            lCollaborateur = mProjet.getCollaborateur(i) ;
            int lTempsPrevu = 0 ;
            int lTempsPasse = 0 ;
            int lResteAPasser = 0 ;
            int lNbTaches = 0 ;
            Date lDateDebutPrevue = new SimpleDateFormat ("yyyy-MM-dd").parse("2100-01-01") ;
            Date lDateFinPrevue = new SimpleDateFormat ("yyyy-MM-dd").parse("1900-01-01") ;
            Date lAuxDateDebutReelle = new SimpleDateFormat ("yyyy-MM-dd").parse("2100-01-01") ;
            Date lAuxDateFinReelle = new SimpleDateFormat ("yyyy-MM-dd").parse("1900-01-01") ;
            Date lDateDebutReelle = null ;
            Date lDateFinReelle = null ;
            double lPrcDepassementCharge ;
            double lHDepassementCharge ;
            double lBudgetConsomme ;
            double lPrcAvancement ;
            
            // pour chaque tache du collaborateur
            for (int j = 0 ; j < lCollaborateur.getNbTaches() ; j++)
            {
              lTache = lCollaborateur.getTache(j) ;
              // on ne retient que les taches de l iteration selectionn�e
              boolean tacheOK = false ;
              // on regarde si la tache en cours fait partie de l iteration selectionn�e
              for(int k = 0; k<mIteration.getNbTaches();k++)
              {
                if (mIteration.getTache(k).getId() == lTache.getId()) 
                {
                  tacheOK = true ;
                }
              }
              // si la tache appartient a l iteration selectionn�e
              // on l ajoute a la liste des taches du collaborateur a afficher
              if (tacheOK == true)
              {
                lListeTaches.add(lTache) ;
              }
            }
            
            // pour chaque tache impr�vue du collaborateur
            for (int j = 0 ; j < lCollaborateur.getNbTachesImprevues() ; j++)
            {
              lTacheImprevue = lCollaborateur.getTacheImprevue(j) ;
              // on ne retient que les taches de l iteration selectionn�e
              boolean tacheOK = false ;
              // on regarde si la tache en cours fait partie de l iteration selectionn�e
              for(int k = 0; k<mIteration.getNbTachesImprevues();k++)
              {
                if (mIteration.getTacheImprevue(k).getId() == lTacheImprevue.getId()) 
                {
                  tacheOK = true ;
                }
              }
              // si la tache impr�vue appartient a l iteration selectionn�e
              // on l ajoute a la liste des taches du collaborateur a afficher
              if (tacheOK == true)
              {
                lListeTachesImprevues.add(lTacheImprevue) ;
              }
            }
            
            // calcul de la somme des t�ches du collaborateur
            for (int j = 0 ; j < lListeTaches.size() ; j++)
            {
              lTache = (MTache)lListeTaches.get(j) ; 
              lTempsPrevu += lTache.getChargeInitiale() ;
              lTempsPasse += lTache.getTempsPasse() ;
              lResteAPasser += lTache.getResteAPasser() ;
              lNbTaches++ ;
              if (lDateDebutPrevue.after(lTache.getDateDebutPrevue()))
                lDateDebutPrevue = lTache.getDateDebutPrevue() ;
              if (lDateFinPrevue.before(lTache.getDateFinPrevue()))
                lDateFinPrevue = lTache.getDateFinPrevue() ;
              if (lTache.getDateDebutReelle()!=null)
              {
                if (lAuxDateDebutReelle.after(lTache.getDateDebutReelle()))
                {  
                  lAuxDateDebutReelle = lTache.getDateDebutReelle() ;
                  lDateDebutReelle = lTache.getDateDebutReelle() ;
                }  
              }
              if (lTache.getDateFinReelle()!=null)
              {
                if (lAuxDateFinReelle.before(lTache.getDateFinReelle()))
                {  
                  lAuxDateFinReelle = lTache.getDateFinReelle() ;
                  lDateFinReelle = lTache.getDateFinReelle() ;
                }  
              }
            }
            
            // calcul de la somme des t�ches impr�vues du collaborateur
            for (int j = 0 ; j < lListeTachesImprevues.size() ; j++)
            {
              lTacheImprevue = (MTacheImprevue)lListeTachesImprevues.get(j) ; 
              lTempsPrevu += lTacheImprevue.getChargeInitiale() ;
              lTempsPasse += lTacheImprevue.getTempsPasse() ;
              lResteAPasser += lTacheImprevue.getResteAPasser() ;
              lNbTaches++ ;
              if (lDateDebutPrevue.after(lTacheImprevue.getDateDebutPrevue()))
                lDateDebutPrevue = lTacheImprevue.getDateDebutPrevue() ;
              if (lDateFinPrevue.before(lTacheImprevue.getDateFinPrevue()))
                lDateFinPrevue = lTacheImprevue.getDateFinPrevue() ;
              if (lTacheImprevue.getDateDebutReelle()!=null)
              {
                if (lAuxDateDebutReelle.after(lTacheImprevue.getDateDebutReelle()))
                {  
                  lAuxDateDebutReelle = lTacheImprevue.getDateDebutReelle() ;
                  lDateDebutReelle = lTacheImprevue.getDateDebutReelle() ;
                }  
              }
              if (lTacheImprevue.getDateFinReelle()!=null)
              {
                if (lAuxDateFinReelle.before(lTacheImprevue.getDateFinReelle()))
                {  
                  lAuxDateFinReelle = lTacheImprevue.getDateFinReelle() ;
                  lDateFinReelle = lTacheImprevue.getDateFinReelle() ;
                }  
              }
            }
            
            if (lNbTaches > 0)
            {
	          lPrcDepassementCharge = (lTempsPasse + lResteAPasser - lTempsPrevu) / lTempsPrevu ;
	          lHDepassementCharge = lTempsPasse + lResteAPasser - lTempsPrevu ;
	          lBudgetConsomme = lTempsPasse / lTempsPrevu ;
	          lPrcAvancement = lTempsPasse / (lTempsPasse + lResteAPasser) ;
	          
	          lCollaborateur.setListe(new Integer(1), new Integer(lTempsPrevu)) ;
	          lCollaborateur.setListe(new Integer(2), new Integer(lTempsPasse)) ;
	          lCollaborateur.setListe(new Integer(3), new Integer(lResteAPasser)) ;
	          lCollaborateur.setListe(new Integer(4), lDateDebutPrevue) ;
	          lCollaborateur.setListe(new Integer(5), lDateDebutReelle) ;
	          lCollaborateur.setListe(new Integer(6), lDateFinPrevue) ;
	          lCollaborateur.setListe(new Integer(7), lDateFinReelle) ;
	          lCollaborateur.setListe(new Integer(8), new Double(lPrcDepassementCharge)) ;
	          lCollaborateur.setListe(new Integer(9), new Double(lHDepassementCharge)) ;
	          lCollaborateur.setListe(new Integer(10), new Double(lBudgetConsomme)) ;
	          lCollaborateur.setListe(new Integer(11), new Double(lPrcAvancement)) ;
            }
            
            lCollaborateur.setListe(new Integer(0), new Integer(lNbTaches)) ;
            // on ajoute le collaborateur a la liste a afficher
            mProjet.setListe(new Integer(i), lCollaborateur) ;
          }
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
     * R�cup�re les param�tres pass�s au controleur. 
     * @throws ServletException -
     * @see owep.controle.CControleurBase#initialiserParametres()
     */
    public void initialiserParametres () throws ServletException
    {

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
      // si on veut voir l avancement pour une it�ration autre que celle qui est en cours
      if (mIterationNum != mIterationEnCours)
      {
        // Transmet les donn�es � la JSP d'affichage.
        getRequete ().setAttribute (CConstante.PAR_PROJET, mProjet) ;
        
        // Sauvegarde de l'URL en session pour la liste de it�rations
        mSession.setURLPagePrecedente("/Avancement/AvancementTachesCollabs");
        
        return "/JSP/Avancement/TAvancementTachesCollabs.jsp" ; 
      }
      // si on veut voir l avancement pour l'it�ration en cours
      else
      {
        return "/Avancement/AvancementCollaborateur" ; 
      }
    }
}
