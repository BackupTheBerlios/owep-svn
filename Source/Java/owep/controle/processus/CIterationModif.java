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
 * Controleur pour la modification d'une it�ration (ajout de t�ches, d'artefact, ...) conform�ment
 * � un processus.
 */
public class CIterationModif extends CControleurBase
{
  private MProjet    mProjet ;    // Projet actuellement ouvert par l'utilisateur.
  private MIteration mIteration ; // It�ration modifi�e ou ajout�e.
  
  
  /**
   * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es. 
   * @throws ServletException Si une erreur survient durant la connexion
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    // Si on acc�de pour la premi�re fois au controleur (ajout ou modification d'une it�ration).
    if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_VIDE) ||
        getRequete ().getParameter (CConstante.PAR_MODIFIER) != null)
    {
      Session          lSession ;  // Session actuelle de l'utilisateur.
      OQLQuery         lRequete ;  // Requ�te � r�aliser sur la base
      QueryResults     lResultat ; // R�sultat de la requ�te sur la base
      
      lSession = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
      mProjet  = lSession.getProjet () ;
      
      // Charge une copie du projet ouvert.
      try
      {
        getBaseDonnees ().begin () ;
        
        // R�cup�re la liste des t�ches du collaborateur.
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
   * R�cup�re les param�tres pass�s au controleur. 
   * @throws ServletException -
   */
  public void initialiserParametres () throws ServletException
  {
    // Modification d'une it�ration.
    if (getRequete ().getParameter (CConstante.PAR_MODIFIER) != null)
    {
      // R�cup�re l'it�ration � modifier et la met dans la session.
      int lIdIteration = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_ITERATION)) ;
      
      int lIndiceIteration = 0 ;
      while (mProjet.getIteration (lIndiceIteration).getId () != lIdIteration)
      {
        lIndiceIteration ++ ;
      }
      getRequete ().getSession ().setAttribute (CConstante.SES_ITERATION, mProjet.getIteration (lIndiceIteration)) ;
    }
    else
    // Ajout d'une nouvelle it�ration.
    if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_VIDE))
    {
      // Initialise une nouvelle it�ration.
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
      
      // Ajout d'une t�che.
      if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITAJOUTER))
      {
        MTache lTache = new MTache () ;
        MCollaborateur lCollaborateur = null;
        MActivite      lActivite = null ;
        
        int lIdCollaborateur = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTECOLLABORATEURS)) ;
        int lIdActivite      = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTEACTIVITES)) ;
        
        // TODO : faire une m�thode de recherche avec classe de base contenant getId
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
        
        // Met � jour le mod�le.
        lTache.setIteration (mIteration) ;
        lTache.setCollaborateur (lCollaborateur) ;
        lTache.setActivite (lActivite) ;
        mIteration.addTache (lTache) ;
        lCollaborateur.addTache (lTache) ;
        lActivite.addTache (lTache) ;
      }
      // Modification d'une t�che si on la modifie directement ou si on ajoute, modifie ou supprime un artefact.
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
        
        // TODO : faire une m�thode de recherche avec classe de base contenant getId
        // TODO : ne faire le parcours que si l'activit� ou le collaborateur a chang�
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

        // Indice de la t�che et de l'artefact dans leur liste respective.
        int lIndiceTache     = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTETACHES)) ;
        
        VTransfert.transferer (getRequete (), mIteration, CConstante.PAR_ARBREITERATION) ;
        VTransfert.transferer (getRequete (), lTacheTmp, CConstante.PAR_ARBRETACHES) ;
        
        // Met � jour le mod�le.
        // Remarque : On ne met pas � jour la liste des artefacts car l'activit� ne peut �tre
        // modifi�e si un artefact est pr�sent (cf. FIterationModif.jsp)
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
      // Supprimer une t�che.
      else if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITSUPPRIMER))
      {
        // Indice de la t�che et de l'artefact dans leur liste respective.
        int lIndiceTache     = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTETACHES)) ;
        
        VTransfert.transferer (getRequete (), mIteration, CConstante.PAR_ARBREITERATION) ;
        MTache lTache = mIteration.getTache (lIndiceTache) ;
        
        // Met � jour le mod�le.
        mIteration.supprimerTache (lIndiceTache) ;
        lTache.getCollaborateur ().supprimerTache (lTache) ;
        lTache.getActivite ().supprimerTache (lTache) ;
        
        // Supprime la liste des artefacts en entr�es.
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
        
        // Indice de la t�che et de l'artefact dans leur liste respective.
        int lIndiceTache       = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTETACHES)) ;
        int lIndiceProduit     = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTEPRODUITS)) ;
        int lIndiceResponsable = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTERESPONSABLES)) ;
        
        MTache         lTache       = mIteration.getTache (lIndiceTache) ;
        MActivite      lActivite    = lTache.getActivite () ;
        MProduit       lProduit     = lActivite.getProduitSortie (lIndiceProduit) ;
        MCollaborateur lResponsable = lProduit.getResponsable ().getCollaborateur (lIndiceResponsable) ;
        
        // Mise � jour du mod�le.
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
        // Indice de la t�che et de l'artefact dans leur liste respective.
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
        
        // Mise � jour du mod�le.
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
        // Indice de la t�che et de l'artefact dans leur liste respective.
        int lIndiceTache     = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTETACHES)) ;
        int lIndiceArtSortie = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTEARTEFACTSSORTIES)) ;
        
        MArtefact lArtefactSortie = mIteration.getTache (lIndiceTache).getArtefactSortie (lIndiceArtSortie) ;
        
        // Mise � jour du mod�le.
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
      // Ajouter un artefact en entr�e
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
      // Supprimer un artefact en entr�e
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
      // Validation de l'it�ration.
      else if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMIT))
      {
        VTransfert.transferer (getRequete (), mIteration, CConstante.PAR_ARBREITERATION) ;
      }
    }
  }
  
  
  /**
   * R�cup�re la liste des t�ches d'un collaborateur pour l'it�ration choisie, et la transmet � la
   * JSP. 
   * @return URL de la page vers laquelle doit �tre redirig� le client.
   * @throws ServletException Si une erreur survient dans le controleur.
   */
  public String traiter () throws ServletException
  {
    // Si l'it�ration est toujours en cours de modification.
    if (! VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMIT))
    {
      // Transmet les donn�es � la JSP d'affichage.
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
        
        // Mise � jour de toutes les t�ches dans la BD.
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
          
        // Mise � jour de tous les artefacts en sorties dans la BD.
        for (int i = 0; i < mIteration.getNbTaches (); i++)
        {
          MTache lTache = mIteration.getTache (i) ;
          
          // Mise � jour des artefacts en sorties de la tache
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
          
          // Mise � jour de tous les artefacts en entr�es dans la BD.
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
        OQLQuery lRequete ; // Requ�te � r�aliser sur la base
        QueryResults lResultat ; // R�sultat de la requ�te sur la base
        MProjet lProjet ; // Projet � ouvrir
        getBaseDonnees ().begin () ;
        // Cherche le projet � ouvrir
        lRequete = getBaseDonnees ()
          .getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
        lRequete.bind (mIdProjet) ;
        lResultat = lRequete.execute () ;

        lProjet = (MProjet) lResultat.next () ;

        getBaseDonnees ().commit () ;
        
        
        // modif tmp
        if (nouvelIt)
          lProjet.addIteration (mIteration); // Temporaire car sinon on ne voit pas l'it�ration dans la fene^tre de visuprojet

        // Enregistre le projet � ouvrir dans la session
        getSession ().ouvrirProjet (lProjet) ;
        
        // MODIF YANN : ajout de l'it�ration au projet en cours et retour sur la page affichant la liste des it�rations
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
      // Ferme la connexion � la base de donn�es.
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