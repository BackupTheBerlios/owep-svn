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
 * @author Emmanuelle et Rémi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CAvancementProjet extends CControleurBase{ 
    private MIteration mIteration ; // Iteration choisie
    private Session mSession ; // Session associé à la connexion
    private MProjet mProjet ; //projet ouvert par le collaborateur
    private int mIterationNum ; // Numéro d'itération dont on suit l avancement
    private int mIterationEnCours = -1 ; // Numero de l iteration en cours
        
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
        
        getBaseDonnees ().begin () ;
        
        int idProjet = mProjet.getId() ;
        // Récupère le projet dans la BD
        lRequete = getBaseDonnees ().getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
        lRequete.bind (idProjet) ;
        lResultat      = lRequete.execute () ;
        mProjet = (MProjet) lResultat.next () ;
        
        // recuperation du numero de l iteration choisie
        // si l'iteration est dans la session (c est a dire si on a choisi l'itération par le menu deroulant)
        if(getRequete().getParameter(CConstante.PAR_ITERATION) == null)
        {
          mIteration = mSession.getIteration() ;
          mIterationNum = mIteration.getNumero();
        }
        // si l'itération n'est pas dans la session (c est a dire si on a choisi l'iteration en cliquant dans le tableau de suivi de projet)
        // on la met dans la session
        else
        {
          mIterationNum = Integer.parseInt(getRequete().getParameter(CConstante.PAR_ITERATION)) ;
          // Récupère l'itération dans la BD
          lRequete = getBaseDonnees ().getOQLQuery ("select ITERATION from owep.modele.execution.MIteration ITERATION where mId = $1") ;
          lRequete.bind (mIterationNum) ;
          lResultat      = lRequete.execute () ;
          mIteration = (MIteration) lResultat.next () ;
          mSession.setIteration(mIteration) ;
        }
        // recherche de l iteration en cours
        for (int m = 0; m < mProjet.getNbIterations() ; m++)
        {
          if (mProjet.getIteration(m).getEtat()==1)
            mIterationEnCours = mProjet.getIteration(m).getId();
        }

        // si on a demandé l affichage du suivi de l iteration en cours
        // on recharge les taches au cas ou les collaborateurs aient modifiés 
        // des propriétés de la tache
        if (mIterationNum == mIterationEnCours)
        {
          // creation de la liste des taches en cours des collaborateurs
          MTache lTache ;
          MTacheImprevue lTacheImprevue ;
          // on recupere l'iteration du projet rechargé
          mIteration = mProjet.getIteration(mIterationNum - 1) ;
          ArrayList lListeTache = new ArrayList() ;
          // pour chaque tache de l iteration
          for (int i = 0 ; i < mIteration.getNbTaches() ; i++)
          {
            // on recupere l id de la tache a recharger
            int idTache = mIteration.getTache(i).getId() ;
              
            // Récupère la tache a recharger dans la BD
            lRequete = getBaseDonnees ().getOQLQuery ("select TACHE from owep.modele.execution.MTache TACHE where mId = $1") ;
            lRequete.bind (idTache) ;
            lResultat      = lRequete.execute () ;
            lTache = (MTache) lResultat.next () ;
            
            // ajout de la tache rechargée a la liste des taches rechargées
            lListeTache.add(lTache) ;
          }
          // mise a jour de la liste des taches de l iteration choisie
          mIteration.setListeTaches(lListeTache) ;
          ArrayList lListeTacheImprevues = new ArrayList() ;
          // pour chaque tache imprevues de l iteration
          for (int i = 0 ; i < mIteration.getNbTachesImprevues() ; i++)
          {
            // on recupere l id de la tache imprevue a recharger
            int idTacheImprevue = mIteration.getTacheImprevue(i).getId() ;
              
            // Récupère la tache imprevue a recharger dans la BD
            lRequete = getBaseDonnees ().getOQLQuery ("select TACHEIMPREVUE from owep.modele.execution.MTacheImprevue TACHEIMPREVUE where mId = $1") ;
            lRequete.bind (idTacheImprevue) ;
            lResultat      = lRequete.execute () ;
            lTacheImprevue = (MTacheImprevue) lResultat.next () ;
            
            // ajout de la tache rechargée a la liste des taches rechargées
            lListeTacheImprevues.add(lTacheImprevue) ;
          }
          // mise a jour de la liste des taches de l iteration choisie
          mIteration.setListeTachesImprevues(lListeTacheImprevues) ;
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
      getRequete ().setAttribute (CConstante.PAR_ITERATION, mIteration) ;
      
      // Sauvegarde de l'URL en session pour la liste de itérations
      mSession.setURLPagePrecedente("/Avancement/AvancementProjet");
      
      return "..\\JSP\\Avancement\\TAvancementProjet.jsp" ; 
    }
}
