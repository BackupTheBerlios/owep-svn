package owep.controle.avancement;

import java.util.ArrayList;
import java.util.NoSuchElementException;
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
public class CAvancementCollaborateur extends CControleurBase{ 
    private MProjet mProjet ; //projet ouvert par le collaborateur
    private Session mSession ; // Session associ� � la connexion
    private MIteration mIteration ; // num�ro d'it�ration dont on veut voir le suivi d'avancement
    private int mIterationNum ; // Num�ro d'it�ration dont on suit l avancement des collaborateurs
    private int mNumIterationEnCours = -1 ; // Numero de l iteration en cours
    
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
        MIteration mIterationEnCours = null ;
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
        
        if (mProjet.getNbIterations() > 0) 
        {
          // recuperation du numero de l iteration choisie dans le menu deroulant
          mIteration = mSession.getIteration() ;
          mIterationNum = mIteration.getNumero();
        }
          
        // recherche de l iteration en cours
        for (int m = 0; m < mProjet.getNbIterations() ; m++)
        {
          if (mProjet.getIteration(m).getEtat()==1)
          {
            mIterationEnCours = mProjet.getIteration(m) ;
            mNumIterationEnCours = mProjet.getIteration(m).getId();
          }
        }

        // si on a demand� l affichage du suivi des collaborateurs pour l iteration en cours
        if (mIterationNum == mNumIterationEnCours)
        {
          // creation de la liste des taches en cours des collaborateurs
          for (int i = 0 ; i < mProjet.getNbCollaborateurs() ; i++)
          {
            // liste contenant le type de la tache en cours (imprevue ou normale) et la tache en cours
            ArrayList listTache = new ArrayList () ;
            
            // si le collabarateur n a pas de tache en cours, inutile d aller plus loin
            if (mProjet.getCollaborateur(i).getTacheEnCours()==-1)
            {
              // si le collaborateur n'a pas de tache en cours, on met null a l indice
              // du collaborateur dans le tableau des taches en cours
              mProjet.setListe(new Integer(i) , listTache) ;
            }
            else
            {
	          // on recherche la tache en cours dans la BD
	          // recherche de la tache en cours dans la table tache
	          try
	          {
	            // R�cup�re la tache en cours du collaborateur dans la BD
	            lRequete = getBaseDonnees ().getOQLQuery ("select TACHE from owep.modele.execution.MTache TACHE where mCollaborateur = $1 and mEtat = $2 and mIteration = $3") ;
	            lRequete.bind (mProjet.getCollaborateur(i)) ;
	            lRequete.bind (MTache.ETAT_EN_COURS) ;
	            lRequete.bind (mIterationEnCours) ;
	            lResultat      = lRequete.execute () ;
	            MTache lTacheEnCours = (MTache) lResultat.next () ;
	            listTache.add("tache") ;
	            listTache.add(lTacheEnCours) ;
	            mProjet.setListe(new Integer(i) , listTache) ;
	          }
	          // si pas de tache en cours dans la table tache pour le collaborateur concern�
	          catch (NoSuchElementException eException)
	          {
	            // recherche de la tache en cours dans la table tache imprevue
	            try
	            {
	              // R�cup�re la tache en cours du collaborateur dans la BD
	              lRequete = getBaseDonnees ().getOQLQuery ("select TACHEIMPREVUE from owep.modele.execution.MTacheImprevue TACHEIMPREVUE where mCollaborateur = $1 and mEtat = $2 and mIteration = $3") ;
	              lRequete.bind (mProjet.getCollaborateur(i)) ;
	              lRequete.bind (MTacheImprevue.ETAT_EN_COURS) ;
	              lRequete.bind (mIterationEnCours) ;
	              lResultat      = lRequete.execute () ;
	              MTacheImprevue lTacheImprevueEnCours = (MTacheImprevue) lResultat.next () ;
	              listTache.add("tacheImprevue") ;
	              listTache.add(lTacheImprevueEnCours) ;
	              mProjet.setListe(new Integer(i) , listTache) ;
	            }
	            catch (NoSuchElementException eEx)
	            {
	              // si le collaborateur n'a pas de tache en cours, on met null a l indice
	              // du collaborateur dans le tableau des taches en cours
	              mProjet.setListe(new Integer(i) , listTache) ;
	            }
	          }
            }
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
      // si on veut voir l avancement pour l'it�ration en cours
      if (mIterationNum == mNumIterationEnCours)
      {
        // Transmet les donn�es � la JSP d'affichage.
        getRequete ().setAttribute (CConstante.PAR_PROJET, mProjet) ;
        
        // Sauvegarde de l'URL en session pour la liste des it�rations
        mSession.setURLPagePrecedente("/Avancement/AvancementCollaborateur");
        
        return "/JSP/Avancement/TAvancementCollaborateur.jsp" ; 
      }
      // si on veut voir l avancement pour une it�ration autre que celle qui est en cours
      else
      {
        // Transmet les donn�es � la JSP d'affichage.
        getRequete ().setAttribute (CConstante.PAR_ITERATION, getRequete ().getParameter (CConstante.PAR_ITERATION)) ;
        
        return "/Avancement/AvancementTachesCollabs" ; 
      }
    }
}
