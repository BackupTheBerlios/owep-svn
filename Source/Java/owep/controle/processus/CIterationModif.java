package owep.controle.processus ;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException ;
import org.exolab.castor.jdo.OQLQuery ;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.jdo.QueryResults ;
import com.mysql.jdbc.Driver;
import owep.controle.CConstante ;
import owep.controle.CControleurBase ;
import owep.infrastructure.Session ;
import owep.modele.execution.MArtefact ;
import owep.modele.execution.MCollaborateur ;
import owep.modele.execution.MIteration ;
import owep.modele.execution.MProjet ;
import owep.modele.execution.MTache ;
import owep.modele.processus.MActivite ;
import owep.modele.processus.MComposant ;
import owep.modele.processus.MDefinitionTravail ;
import owep.modele.processus.MProcessus ;
import owep.modele.processus.MProduit ;
import owep.vue.transfert.VTransfert ;


/**
 * Controleur pour la modification d'une itération (ajout de tâches, d'artefact, ...) conformément
 * à un processus.
 */
public class CIterationModif extends CControleurBase
{
  private MProjet    mProjet ;    // Projet actuellement ouvert par l'utilisateur.
  private MIteration mIteration ; // Itération modifiée ou ajoutée.
  
  
  /**
   * Récupère les données nécessaire au controleur dans la base de données. 
   * @throws ServletException Si une erreur survient durant la connexion
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    // Si on accède pour la première fois au controleur (ajout ou modification d'une itération).
    if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_VIDE) ||
        getRequete ().getParameter (CConstante.PAR_MODIFIER) != null)
    {
      Session          lSession ;  // Session actuelle de l'utilisateur.
      OQLQuery         lRequete ;  // Requête à réaliser sur la base
      QueryResults     lResultat ; // Résultat de la requête sur la base
      
      lSession = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
      mProjet  = lSession.getProjet () ;
      
      // Charge une copie du projet ouvert.
      try
      {
        getBaseDonnees ().begin () ;
        
        // Récupère la liste des tâches du collaborateur.
        lRequete = getBaseDonnees ().getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
        lRequete.bind (mProjet.getId ()) ;
        lResultat  = lRequete.execute () ;
        mProjet = (MProjet) lResultat.next () ;
        
        getBaseDonnees ().commit () ;
      }
      catch (Exception eException)
      {
        eException.printStackTrace () ;
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
    }
  }
  
  
  /**
   * Récupère les paramètres passés au controleur. 
   * @throws ServletException -
   */
  public void initialiserParametres () throws ServletException
  {
    // Modification d'une itération.
    if (getRequete ().getParameter (CConstante.PAR_MODIFIER) != null)
    {
      // Récupère l'itération à modifier et la met dans la session.
      int lIdIteration = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_ITERATION)) ;
      
      int lIndiceIteration = 0 ;
      while (mProjet.getIteration (lIndiceIteration).getId () != lIdIteration)
      {
        lIndiceIteration ++ ;
      }
      getRequete ().getSession ().setAttribute (CConstante.SES_ITERATION, mProjet.getIteration (lIndiceIteration)) ;
    }
    else
    // Ajout d'une nouvelle itération.
    if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_VIDE))
    {
      // Initialise une nouvelle itération.
      mIteration = new MIteration () ;
      mIteration.setNom ("") ;
      mIteration.setNumero (mProjet.getNbIterations () + 1) ;
      mIteration.setProjet (mProjet) ;

      getRequete ().getSession ().setAttribute (CConstante.SES_ITERATION, mIteration) ;
    }
    else
    {
      // Projet ouvert par l'utilisateur.
      mIteration      = (MIteration) getRequete ().getSession ().getAttribute (CConstante.SES_ITERATION) ;
      MProjet lProjet = mIteration.getProjet () ;
      
      // Ajout d'une tâche.
      if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITAJOUTER))
      {
        MTache lTache = new MTache () ;
        MCollaborateur lCollaborateur = null;
        MActivite      lActivite = null ;
        
        int lIdCollaborateur = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTECOLLABORATEURS)) ;
        int lIdActivite      = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTEACTIVITES)) ;
        
        // TODO : faire une méthode de recherche avec classe de base contenant getId
        for (int i = 0 ; i < lProjet.getNbCollaborateurs () ; i ++) 
        {
          if (lProjet.getCollaborateur (i).getId () == lIdCollaborateur)
          {
            lCollaborateur = lProjet.getCollaborateur (i) ;
          }  
        }  
        // TODO : fin
        // TODO : idem
        MProcessus lProcessus = lProjet.getProcessus () ; 
        for (int i = 0 ; i <  lProcessus.getNbComposants () ; i++)
        {
          MComposant lComposant = lProcessus.getComposant (i) ;
          for (int j = 0 ; j < lComposant.getNbDefinitionsTravail (); j++)
          {
            MDefinitionTravail lDefTrav = lComposant.getDefinitionTravail (j) ;
            for (int k = 0 ; k < lDefTrav.getNbActivites (); k++)
            {
              if (lDefTrav.getActivite(k).getId() == lIdActivite)
              {
                lActivite = lDefTrav.getActivite(k) ;
              }  
            }
          }  
        }  
        // TODO : idem fin
        
        VTransfert.transferer (getRequete (), mIteration, CConstante.PAR_ARBREITERATION) ;
        VTransfert.transferer (getRequete (), lTache, CConstante.PAR_ARBRETACHES) ;
        
        // Met à jour le modèle.
        lTache.setIteration (mIteration) ;
        lTache.setCollaborateur (lCollaborateur) ;
        lTache.setActivite (lActivite) ;
        mIteration.addTache (lTache) ;
        lCollaborateur.addTache (lTache) ;
        lActivite.addTache (lTache) ;
      }
      // Modification d'une tâche si on la modifie directement ou si on ajoute, modifie ou supprime un artefact.
      else if ((VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITMODIFIER)) ||
        (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITAJOUTER_ARTSORTIES)) ||
        (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITMODIFIER_ARTSORTIES)) ||
        (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITSUPPRIMER_ARTSORTIES)))
      {
        MTache lTacheTmp    = new MTache () ;
        
        MCollaborateur lCollaborateur = null ;
        MActivite      lActivite = null ;
        
        int lIdCollaborateur = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTECOLLABORATEURS)) ;
        int lIdActivite      = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTEACTIVITES)) ;
        
        // TODO : faire une méthode de recherche avec classe de base contenant getId
        // TODO : ne faire le parcours que si l'activité ou le collaborateur a changé
        for (int i = 0 ; i < lProjet.getNbCollaborateurs() ; i ++) 
        {
          if (lProjet.getCollaborateur (i).getId () == lIdCollaborateur)
          {
            lCollaborateur = lProjet.getCollaborateur (i) ;
          }  
        }  
        // TODO : fin
        // TODO : idem
        MProcessus lProcessus = lProjet.getProcessus () ; 
        for (int i = 0 ; i <  lProcessus.getNbComposants () ; i++)
        {
          MComposant lComposant = lProcessus.getComposant (i) ;
          for (int j = 0 ; j < lComposant.getNbDefinitionsTravail (); j++)
          {
            MDefinitionTravail lDefTrav = lComposant.getDefinitionTravail (j) ;
            for (int k = 0 ; k < lDefTrav.getNbActivites (); k++)
            {
              if (lDefTrav.getActivite(k).getId() == lIdActivite)
              {
                lActivite = lDefTrav.getActivite(k) ;
              }  
            }
          }  
        }  
        // TODO : idem fin

        // Indice de la tâche et de l'artefact dans leur liste respective.
        int lIndiceTache     = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTETACHES)) ;
        
        VTransfert.transferer (getRequete (), mIteration, CConstante.PAR_ARBREITERATION) ;
        VTransfert.transferer (getRequete (), lTacheTmp, CConstante.PAR_ARBRETACHES) ;
        
        // Met à jour le modèle.
        // Remarque : On ne met pas à jour la liste des artefacts car l'activité ne peut être
        // modifiée si un artefact est présent (cf. FIterationModif.jsp)
        MTache lTache = mIteration.getTache (lIndiceTache) ;
        lTache.setNom (lTacheTmp.getNom ()) ;
        lTache.setChargeInitiale (lTacheTmp.getChargeInitiale ()) ;
        lTache.setDescription (lTacheTmp.getDescription ()) ;
        lTache.setDateDebutPrevue (lTacheTmp.getDateDebutPrevue ()) ;
        lTache.setDateFinPrevue (lTacheTmp.getDateDebutPrevue ()) ;
        
        lTache.getCollaborateur ().supprimerTache (lTache) ;
        lTache.setCollaborateur (lCollaborateur) ;
        lCollaborateur.addTache (lTache) ;
        lTache.getActivite ().supprimerTache (lTache) ;
        lTache.setActivite (lActivite) ;
        lActivite.addTache (lTache) ;
      }
      // Supprimer une tâche.
      else if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITSUPPRIMER))
      {
        // Indice de la tâche et de l'artefact dans leur liste respective.
        int lIndiceTache     = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTETACHES)) ;
        
        VTransfert.transferer (getRequete (), mIteration, CConstante.PAR_ARBREITERATION) ;
        MTache lTache = mIteration.getTache (lIndiceTache) ;
        
        // Met à jour le modèle.
        mIteration.supprimerTache (lIndiceTache) ;
        lTache.getCollaborateur ().supprimerTache (lTache) ;
        lTache.getActivite ().supprimerTache (lTache) ;
        
        // Supprime la liste des artefacts en entrées.
        for (int i = 0; i < lTache.getNbArtefactsEntrees (); i ++)
        {
          MArtefact lArtefactEntree = lTache.getArtefactEntree (i) ;
          lTache.supprimerArtefactEntree (i) ;
          lArtefactEntree.setTacheEntree (null) ;
        }
        // Supprime la liste des artefacts en sorties.
        for (int i = 0; i < lTache.getNbArtefactsSorties (); i ++)
        {
          MArtefact lArtefactSortie = lTache.getArtefactSortie (i) ;
          
          lTache.supprimerArtefactSortie (i) ;
          lArtefactSortie.setTacheSortie (null) ;
          lArtefactSortie.getTacheEntree ().supprimerArtefactEntree (lArtefactSortie) ;
          lArtefactSortie.setTacheEntree (null) ;
          lArtefactSortie.getProduit ().supprimerArtefact (lArtefactSortie) ;
          lArtefactSortie.setProduit (null) ;
          lArtefactSortie.getResponsable ().supprimerArtefact (lArtefactSortie) ;
          lArtefactSortie.setResponsable (null) ;
          lArtefactSortie.getProjet ().supprimerArtefact (lArtefactSortie) ;
          lArtefactSortie.setProjet (null) ;
        }
      }
      
      
      // Ajout d'un artefact.
      if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITAJOUTER_ARTSORTIES))
      {
        MArtefact lArtefactSortie = new MArtefact () ;
        VTransfert.transferer (getRequete (), lArtefactSortie, CConstante.PAR_ARBREARTEFACTSORTIES) ;
        
        // Indice de la tâche et de l'artefact dans leur liste respective.
        int lIndiceTache       = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTETACHES)) ;
        int lIndiceProduit     = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTEPRODUITS)) ;
        int lIndiceResponsable = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTERESPONSABLES)) ;
        
        MTache         lTache       = mIteration.getTache (lIndiceTache) ;
        MActivite      lActivite    = lTache.getActivite () ;
        MProduit       lProduit     = lActivite.getProduitSortie (lIndiceProduit) ;
        MCollaborateur lResponsable = lProduit.getResponsable ().getCollaborateur (lIndiceResponsable) ;
        
        // Mise à jour du modèle.
        lArtefactSortie.setTacheSortie (lTache) ;
        lTache.addArtefactSortie (lArtefactSortie) ;
        lArtefactSortie.setProjet (lProjet) ;
        lProjet.addArtefact (lArtefactSortie) ;
        lArtefactSortie.setProduit (lProduit) ;
        lProduit.addArtefact (lArtefactSortie) ;
        lArtefactSortie.setResponsable (lResponsable) ;
        lResponsable.addArtefact (lArtefactSortie) ;
      }
      // Modification d'un artefact.
      else if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITMODIFIER_ARTSORTIES))
      {
        // Indice de la tâche et de l'artefact dans leur liste respective.
        int lIndiceTache     = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTETACHES)) ;
        int lIndiceArtSortie = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTEARTEFACTSSORTIES)) ;
        int lIndiceProduit     = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTEPRODUITS)) ;
        int lIndiceResponsable = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTERESPONSABLES)) ;
        
        MArtefact lArtefactSortieTmp = new MArtefact () ;
        MArtefact lArtefactSortie = mIteration.getTache (lIndiceTache).getArtefactSortie (lIndiceArtSortie) ;
        
        VTransfert.transferer (getRequete (), lArtefactSortieTmp, CConstante.PAR_ARBREARTEFACTSORTIES) ;
        
        MTache         lTache       = mIteration.getTache (lIndiceTache) ;
        MActivite      lActivite    = lTache.getActivite () ;
        MProduit       lProduit     = lActivite.getProduitSortie (lIndiceProduit) ;
        MCollaborateur lResponsable = lProduit.getResponsable ().getCollaborateur (lIndiceResponsable) ;
        
        lArtefactSortie.setNom (lArtefactSortieTmp.getNom ()) ;
        lArtefactSortie.setDescription (lArtefactSortieTmp.getDescription ()) ;
        lArtefactSortie.setProduit (lActivite.getProduitSortie (lIndiceProduit)) ;
        lArtefactSortie.setResponsable (lProduit.getResponsable ().getCollaborateur (lIndiceResponsable)) ;
        
        // Mise à jour du modèle.
        lArtefactSortie.getTacheSortie ().supprimerArtefactSortie (lArtefactSortie) ;
        lArtefactSortie.setTacheSortie (lTache) ;
        lTache.addArtefactSortie (lArtefactSortie) ;
        lArtefactSortie.getProduit ().supprimerArtefact (lArtefactSortie) ;
        lArtefactSortie.setProduit (lProduit) ;
        lProduit.addArtefact (lArtefactSortie) ;
        lArtefactSortie.getResponsable ().supprimerArtefact (lArtefactSortie) ;
        lArtefactSortie.setResponsable (lResponsable) ;
        lResponsable.addArtefact (lArtefactSortie) ;
      }
      // Supprimer un artefact en sortie.
      else if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITSUPPRIMER_ARTSORTIES))
      {
        // Indice de la tâche et de l'artefact dans leur liste respective.
        int lIndiceTache     = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTETACHES)) ;
        int lIndiceArtSortie = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTEARTEFACTSSORTIES)) ;
        
        MArtefact lArtefactSortie = mIteration.getTache (lIndiceTache).getArtefactSortie (lIndiceArtSortie) ;
        
        // Mise à jour du modèle.
        lArtefactSortie.getTacheSortie ().supprimerArtefactSortie (lArtefactSortie) ;
        lArtefactSortie.setTacheSortie (null) ;
        if (lArtefactSortie.getTacheEntree () != null)
        {
          lArtefactSortie.getTacheEntree ().supprimerArtefactEntree (lArtefactSortie) ;
          lArtefactSortie.setTacheEntree (null) ;
        }
        lArtefactSortie.getProduit ().supprimerArtefact (lArtefactSortie) ;
        lArtefactSortie.setProduit (null) ;
        lArtefactSortie.getResponsable ().supprimerArtefact (lArtefactSortie) ;
        lArtefactSortie.setResponsable (null) ;
        lArtefactSortie.getProjet ().supprimerArtefact (lArtefactSortie) ;
        lArtefactSortie.setProjet (null) ;
      }
      // Ajouter un artefact en entrée
      else if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITAJOUTER_ARTENTREES))
      {
       
        int lIndiceTache = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTETACHES)) ;
        int lIdArtEntree = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTEARTEFACTSPOSSIBLES)) ;
        
        MTache    lTache          = mIteration.getTache (lIndiceTache) ;
        MActivite lActivite = lTache.getActivite () ;
        MArtefact lArtEntree = null;
        
        for (int i = 0; i <  lActivite.getNbProduitsEntrees() ; i ++)
        {
          MProduit lProduitEntree = lActivite.getProduitEntree(i) ;
          for (int j = 0; j < lProduitEntree.getNbActivitesEntrees (); j ++) 
          {
            
            if (lProduitEntree.getArtefact (j).getId () == lIdArtEntree) 
            {
              lArtEntree = lProduitEntree.getArtefact (j) ;   
            }
          }
        }
        lTache.addArtefactEntrees (lArtEntree) ;
        lArtEntree.setTacheEntree (lTache) ;
      }
      // Supprimer un artefact en entrée
      else if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITSUPPRIMER_ARTENTREES)) 
      {
        int lIndiceTache     = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTETACHES)) ;
        int lIdArtEntree = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTEARTEFACTSENTREES)) ;
        
        for (int i = 0; i < mIteration.getTache (lIndiceTache).getNbArtefactsEntrees (); i++)
        {
          MArtefact lArtEntTmp =  mIteration.getTache (lIndiceTache).getArtefactEntree (i) ;
          if (lArtEntTmp.getId () == lIdArtEntree) 
          {
            mIteration.getTache (lIndiceTache).supprimerArtefactEntree (i) ;
            lArtEntTmp.setTacheEntree (null) ;
          }
        }
      }
      // Validation de l'itération.
      else if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMIT))
      {
        VTransfert.transferer (getRequete (), mIteration, CConstante.PAR_ARBREITERATION) ;
      }
    }
  }
  
  
  /**
   * Récupère la liste des tâches d'un collaborateur pour l'itération choisie, et la transmet à la
   * JSP. 
   * @return URL de la page vers laquelle doit être redirigé le client.
   * @throws ServletException Si une erreur survient dans le controleur.
   */
  public String traiter () throws ServletException
  {
    // Si l'itération est toujours en cours de modification.
    if (! VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMIT))
    {
      // Transmet les données à la JSP d'affichage.
      return "..\\JSP\\Processus\\TIterationModif.jsp" ;
    }
    else
    {
      boolean nouvelIt = false ; // modif tmp
      Connection lConnection = null ;
      try
      {
        DriverManager.registerDriver (new Driver ()) ;
        lConnection = DriverManager.getConnection ("jdbc:mysql://localhost/owep", "root", "6431") ;
        lConnection.setAutoCommit(false);
        if (mIteration.getId () == 0)
        {
          mIteration.create (lConnection) ;
          nouvelIt = true; // modif tmp
        }
        else
        {
          mIteration.update (lConnection) ;
        }
        
        // Mise à jour de toutes les tâches dans la BD.
        for (int i = 0; i < mIteration.getNbTaches (); i++)
        {
          MTache lTache = mIteration.getTache (i) ;
          
          if (lTache.getId () == 0)
          {
            lTache.create (lConnection) ;
          }
          else
          {
            lTache.update (lConnection) ;
          }
        }
          
        // Mise à jour de tous les artefacts en sorties dans la BD.
        for (int i = 0; i < mIteration.getNbTaches (); i++)
        {
          MTache lTache = mIteration.getTache (i) ;
          
          // Mise à jour des artefacts en sorties de la tache
          for (int j = 0; j < lTache.getNbArtefactsSorties (); j ++)
          {
            MArtefact lArtefact = lTache.getArtefactSortie (j) ;
            if (lArtefact.getId () == 0)
            {
              lArtefact.create (lConnection) ;
            }
            else
            {
              lArtefact.update (lConnection) ;
            }
          }
          
          // Mise à jour de tous les artefacts en entrées dans la BD.
          for (int j = 0; j < lTache.getNbArtefactsEntrees (); j ++)
          {
            MArtefact lArtefact = lTache.getArtefactEntree (j) ;
            if (lArtefact.getId () == 0)
            {
              lArtefact.create (lConnection) ;
            }
            else
            {
              lArtefact.update (lConnection) ;
            }
          }
        }

        lConnection.commit () ;
        
        
        
        
        Session lSession ;
        lSession = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
        mProjet  = lSession.getProjet () ;
        int mIdProjet = mProjet.getId ();
        OQLQuery lRequete ; // Requête à réaliser sur la base
        QueryResults lResultat ; // Résultat de la requête sur la base
        MProjet lProjet ; // Projet à ouvrir
        getBaseDonnees ().begin () ;
        // Cherche le projet à ouvrir
        lRequete = getBaseDonnees ()
          .getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
        lRequete.bind (mIdProjet) ;
        lResultat = lRequete.execute () ;

        lProjet = (MProjet) lResultat.next () ;

        getBaseDonnees ().commit () ;
        
        
        // modif tmp
        if (nouvelIt)
          lProjet.addIteration (mIteration); // Temporaire car sinon on ne voit pas l'itération dans la fene^tre de visuprojet

        // Enregistre le projet à ouvrir dans la session
        getSession ().ouvrirProjet (lProjet) ;
        
        // MODIF YANN : ajout de l'itération au projet en cours et retour sur la page affichant la liste des itérations
        //Session lSession ;
        //lSession = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
        //mProjet  = lSession.getProjet () ;
        //mProjet.addIteration(mIteration) ;
        //lSession.setProjet(mProjet) ;
        
        return "..\\Processus\\ProjetVisu" ;
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
          lConnection.close () ;
        }
        catch (SQLException eException)
        {
          eException.printStackTrace () ;
          throw new ServletException (CConstante.EXC_DECONNEXION) ;
        } 
      }
    }
  }
}