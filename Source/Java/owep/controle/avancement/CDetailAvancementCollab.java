package owep.controle.avancement;


import java.util.ArrayList;
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
public class CDetailAvancementCollab extends CControleurBase{ 
    private MCollaborateur mCollaborateur ; // collaborateur dont on veut visualiser l'avancement
    private MIteration mIteration ; // num�ro d'it�ration dont on veut voir le suivi d'avancement
    private int mIterationNum ; // Num�ro d'it�ration dont on suit l avancement des collaborateurs
    private int mIterationEnCours = -1 ; // Numero de l iteration en cours
    private Session mSession ; // Session associ� � la connexion  
    private int lIdCollab ; // id du collab dont on veut visualiser les taches
    
    /**
     * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es. 
     * @throws ServletException Si une erreur survient durant la connexion
     * @see owep.controle.CControleurBase#initialiserBaseDonnees()
     */
    public void initialiserBaseDonnees () throws ServletException
    {
      OQLQuery       lRequete ;       // Requ�te � r�aliser sur la base
      QueryResults   lResultat ;      // R�sultat de la requ�te sur la base
      ArrayList lListeTaches = new ArrayList () ;
      ArrayList lListeTachesImprevues = new ArrayList () ;
      
      try
      {
        HttpSession session = getRequete ().getSession (true) ;
        mSession = (Session)session.getAttribute("SESSION") ;
        
        getBaseDonnees ().begin () ;
        
        // si on est arriv� a cette page sans passer par le menu deroulant des iterations
        // alors on a l id collab dans la requete
        if (getRequete().getParameter(CConstante.PAR_COLLABORATEUR)!=null)
        {
          lIdCollab = Integer.parseInt(getRequete().getParameter((CConstante.PAR_COLLABORATEUR)));
        }
        // on recupere l id collab dans la session
        else
        {
          lIdCollab = mSession.getIdCollabAVisualiser() ;
        }
        // R�cup�re la liste des t�ches du collaborateur.
        lRequete = getBaseDonnees ().getOQLQuery ("select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mId = $1") ;
        lRequete.bind (lIdCollab) ;
        lResultat      = lRequete.execute () ;
        mCollaborateur = (MCollaborateur) lResultat.next () ;
        
        // recuperation du numero de l iteration choisie dans le menu deroulant
        mIteration = mSession.getIteration() ;
        mIterationNum = mIteration.getNumero();
        
        // recuperation du projet pour lequel on est connecte
        MProjet lProjet = mSession.getProjet() ;
        
        // recherche de l iteration en cours
        for (int m = 0; m < lProjet.getNbIterations() ; m++)
        {
          if (lProjet.getIteration(m).getEtat()==1)
            mIterationEnCours = lProjet.getIteration(m).getId();
        }
        
        int lTacheId ;
        MTache lTache ;
        
        // pour chaque tache du collaborateur
        for (int i = 0 ; i < mCollaborateur.getNbTaches() ; i++) 
        {
          // si on a demand� de regarder l avancement de l it�ration en cours
          // alors on recharge les taches de l iteration du collab pour etre a jour a 
          // chaque fois que l etat d une tache est modifi�
          if (mIterationNum == mIterationEnCours)
          {
            //  identifiant de la tache � recharger
            lTacheId = mCollaborateur.getTache(i).getId();
            // R�cup�re la tache dans la BD
            lRequete = getBaseDonnees ().getOQLQuery ("select TACHE from owep.modele.execution.MTache TACHE where mId = $1") ;
            lRequete.bind (lTacheId) ;
            lResultat      = lRequete.execute () ;
            lTache = (MTache) lResultat.next () ;
          }
          // si on veut regarder l avancement d une iteration autre que celle en cours
          // inutile de recharger a partir de la bd
          else
          {
            lTache = mCollaborateur.getTache(i) ;
          }
          // on n'insere dans la liste des taches que les taches faisant 
          // partie de l'iteration choisie dans le menu d�roulant
          for(int k = 0; k<mIteration.getNbTaches();k++)
          {
            if (mIteration.getTache(k).getId() == lTache.getId()) 
            {
              lListeTaches.add(lTache) ;
            }
          } 
        }
        
        int lTacheImprevueId ;
        MTacheImprevue lTacheImprevue ;
        
        // pour chaque tache imprevue du collaborateur
        for (int i = 0 ; i < mCollaborateur.getNbTachesImprevues() ; i++) 
        {
          // si on a demand� de regarder l avancement de l it�ration en cours
          // alors on recharge les taches de l iteration du collab pour etre a jour a 
          // chaque fois que l etat d une tache est modifi�
          if (mIterationNum == mIterationEnCours)
          {
            //  identifiant de la tache � recharger
            lTacheImprevueId = mCollaborateur.getTacheImprevue(i).getId();
            // R�cup�re la tache dans la BD
            lRequete = getBaseDonnees ().getOQLQuery ("select TACHEIMPREVUE from owep.modele.execution.MTacheImprevue TACHEIMPREVUE where mId = $1") ;
            lRequete.bind (lTacheImprevueId) ;
            lResultat      = lRequete.execute () ;
            lTacheImprevue = (MTacheImprevue) lResultat.next () ;
          }
          // si on veut regarder l avancement d une iteration autre que celle en cours
          // inutile de recharger a partir de la bd
          else
          {
            lTacheImprevue = mCollaborateur.getTacheImprevue(i) ;
          }
          // on n'insere dans la liste des taches que les taches faisant 
          // partie de l'iteration choisie dans le menu d�roulant
          for(int k = 0; k<mIteration.getNbTachesImprevues();k++)
          {
            if (mIteration.getTacheImprevue(k).getId() == lTacheImprevue.getId()) 
            {
              lListeTachesImprevues.add(lTacheImprevue) ;
            }
          } 
        }
        
        mCollaborateur.setListe(new Integer(0), lListeTaches) ;
        mCollaborateur.setListe(new Integer(1), lListeTachesImprevues) ;
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
          getBaseDonnees ().commit() ;
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
     * R�cup�re la liste des t�ches du collaborateur pour l'it�ration en cours, et la transmet � la
     * JSP. 
     * @return URL de la page vers laquelle doit �tre redirig� le client.
     * @throws ServletException Si une erreur survient dans le controleur
     * @see owep.controle.CControleurBase#traiter()
     */
    public String traiter () throws ServletException
    {  
      // Transmet les donn�es � la JSP d'affichage.
      getRequete ().setAttribute (CConstante.PAR_COLLABORATEUR, mCollaborateur) ;

      // Sauvegarde de l'URL en session pour la liste de it�rations
      mSession.setURLPagePrecedente("/Avancement/DetailAvancementCollab");
      
      // Sauvegarde de l'identifiant du collaborateur a visualiser
      mSession.setIdCollabAVisualiser(lIdCollab) ;
      
      return "/JSP/Avancement/TDetailAvancementCollab.jsp" ; 
    }
}
