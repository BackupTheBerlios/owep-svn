package owep.controle.processus ;


import javax.servlet.ServletException ;
import javax.servlet.http.HttpSession ;
import org.exolab.castor.jdo.ClassNotPersistenceCapableException ;
import org.exolab.castor.jdo.DuplicateIdentityException ;
import org.exolab.castor.jdo.OQLQuery ;
import org.exolab.castor.jdo.PersistenceException ;
import org.exolab.castor.jdo.QueryResults ;
import org.exolab.castor.jdo.TransactionNotInProgressException ;
import owep.controle.CConstante ;
import owep.controle.CControleurBase ;
import owep.infrastructure.Session ;
import owep.modele.execution.MArtefact ;
import owep.modele.execution.MCollaborateur ;
import owep.modele.execution.MCondition ;
import owep.modele.execution.MIteration ;
import owep.modele.execution.MProjet ;
import owep.modele.execution.MTache ;
import owep.modele.processus.MActivite ;
import owep.modele.processus.MComposant ;
import owep.modele.processus.MDefinitionTravail ;
import owep.modele.processus.MProcessus ;
import owep.modele.processus.MProduit ;
import owep.modele.processus.MRole ;
import owep.vue.transfert.VTransfert ;
import java.util.ResourceBundle ;


/**
 * Controleur pour la modification d'une it�ration (ajout de t�ches, d'artefact, ...) conform�ment �
 * un processus.
 */
public class CIterationModif extends CControleurBase
{
  private MProjet        mProjet ;   // Projet actuellement ouvert par l'utilisateur.
  private MIteration     mIteration ; // It�ration modifi�e ou ajout�e.
  private String         mMessage ;  // Message qui sera affich� dans la jsp suite � une op�ration
  private ResourceBundle bundle ;
  boolean                collab ;


  /**
   * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es.
   * @throws ServletException Si une erreur survient durant la connexion
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    Session lSession ; // Session actuelle de l'utilisateur.
    OQLQuery lRequete ; // Requ�te � r�aliser sur la base
    QueryResults lResultat ; // R�sultat de la requ�te sur la base
    collab = false ;
    mMessage = new String ("") ;
    lSession = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
    mProjet = lSession.getProjet () ;
    //  Recuperation de la session
    HttpSession httpSession = getRequete ().getSession (true) ;
    //R�cup�ration du ressource bundle
    bundle = ((Session) httpSession.getAttribute ("SESSION")).getMessages () ;
    try
    {
      getBaseDonnees ().begin () ;
      // R�cup�re la liste des t�ches du collaborateur.
      lRequete = getBaseDonnees ()
        .getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
      lRequete.bind (mProjet.getId ()) ;
      lResultat = lRequete.execute () ;
      mProjet = (MProjet) lResultat.next () ;
    }
    catch (PersistenceException e1)
    {
      e1.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
    for (int i = 0; ! collab && i < mProjet.getProcessus ().getNbComposants (); i ++ )
    {
      MComposant lComposanttmp = mProjet.getProcessus ().getComposant (i) ;
      for (int j = 0; ! collab && j < lComposanttmp.getNbRoles (); j ++ )
      {
        MRole lRoleTmp = lComposanttmp.getRole (j) ;
        if (lRoleTmp.getNbCollaborateurs () != 0)
        {
          collab = true ;
        }
      }
    }
    // Charge une copie du projet ouvert.
    if (collab)
    {
      try
      {
        getRequete ().setAttribute (CConstante.PAR_PROJET, mProjet) ;
        if ( (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_VIDE))
            && (getRequete ().getParameter (CConstante.PAR_MODIFIER) == null))
        {
          // Initialise une nouvelle it�ration.
          mIteration = new MIteration () ;
          mIteration.setNom ("") ;
          mIteration.setNumero (mProjet.getNbIterations () + 1) ;
          mIteration.setProjet (mProjet) ;
          mProjet.addIteration (mIteration) ;
          try
          {
            // cr�ation de l'it�ration dans la base de donn�es.
            getBaseDonnees ().create (mIteration) ;
          }
          catch (ClassNotPersistenceCapableException e)
          {
            e.printStackTrace () ;
            throw new ServletException (CConstante.EXC_TRAITEMENT) ;
          }
          catch (DuplicateIdentityException e)
          {
            e.printStackTrace () ;
            throw new ServletException (CConstante.EXC_TRAITEMENT) ;
          }
          catch (TransactionNotInProgressException e)
          {
            e.printStackTrace () ;
            throw new ServletException (CConstante.EXC_TRAITEMENT) ;
          }
          catch (PersistenceException e)
          {
            e.printStackTrace () ;
            throw new ServletException (CConstante.EXC_TRAITEMENT) ;
          }
        }
        int lIdIteration ;
        // si c'est une modification, r�cup�ration de l'identifiant de l'it�ration � modifier
        if (getRequete ().getParameter (CConstante.PAR_MODIFIER) != null)
        {
          lIdIteration = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_ITERATION)) ;
        }
        // sinon on r�cup�re l'identifiant de l'it�ration que l'on vient de rajouter dans la BD.
        else
        {
          lIdIteration = mIteration.getId () ;
        }
        lRequete = getBaseDonnees ()
          .getOQLQuery (
                        "select ITERATION from owep.modele.execution.MIteration ITERATION where mId = $1") ;
        lRequete.bind (lIdIteration) ;
        lResultat = lRequete.execute () ;
        mIteration = (MIteration) lResultat.next () ;
        getRequete ().getSession ().setAttribute (CConstante.PAR_ITERATION, mIteration) ;
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
    Session lSession ; // Session actuelle de l'utilisateur.
    OQLQuery lRequete ; // Requ�te � r�aliser sur la base
    QueryResults lResultat ; // R�sultat de la requ�te sur la base
    if (collab)
    {
      try
      {
        MProcessus lProcessus = mProjet.getProcessus () ;
        // Modification d'une it�ration.
        if (getRequete ().getParameter (CConstante.PAR_MODIFIER) != null)
        {
          // R�cup�re l'it�ration � modifier et la met dans la session.
          int lIdIteration = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_ITERATION)) ;
          lRequete = getBaseDonnees ()
            .getOQLQuery (
                          "select ITERATION from owep.modele.execution.MIteration ITERATION where mId = $1") ;
          lRequete.bind (lIdIteration) ;
          lResultat = lRequete.execute () ;
          mIteration = (MIteration) lResultat.next () ;
          getRequete ().getSession ().setAttribute (CConstante.PAR_ITERATION, mIteration) ;
        }
        // Ajout d'une t�che.
        if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITAJOUTER))
        {
          boolean trouve = false ;
          MTache lTache = new MTache () ;
          MCollaborateur lCollaborateur = null ;
          MActivite lActivite = null ;
          int lIdCollaborateur = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTECOLLABORATEURS)) ;
          int lIdActivite = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTEACTIVITES)) ;
          // Recherche du collaborateur effectuant cette t�che.
          for (int i = 0; ! trouve && i < mProjet.getNbCollaborateurs (); i ++ )
          {
            if (mProjet.getCollaborateur (i).getId () == lIdCollaborateur)
            {
              lCollaborateur = mProjet.getCollaborateur (i) ;
              trouve = true ;
            }
          }
          // Recherche de l'activit� � laquelle est li�e la t�che
          trouve = false ;
          for (int i = 0; ! trouve && i < lProcessus.getNbComposants (); i ++ )
          {
            MComposant lComposant = lProcessus.getComposant (i) ;
            for (int j = 0; ! trouve && j < lComposant.getNbDefinitionsTravail (); j ++ )
            {
              MDefinitionTravail lDefTrav = lComposant.getDefinitionTravail (j) ;
              for (int k = 0; ! trouve && k < lDefTrav.getNbActivites (); k ++ )
              {
                if (lDefTrav.getActivite (k).getId () == lIdActivite)
                {
                  lActivite = lDefTrav.getActivite (k) ;
                  trouve = true ;
                }
              }
            }
          }
          if (mIteration.getNom () == "")
          {
            MIteration lIterationTmp = new MIteration () ;
            // R�cup�ration des donn�es de l'it�ration ainsi que celle pour la t�che.
            VTransfert.transferer (getRequete (), lIterationTmp, CConstante.PAR_ARBREITERATION) ;
            mIteration.setDateDebutPrevue (lIterationTmp.getDateDebutPrevue ()) ;
            mIteration.setDateFinPrevue (lIterationTmp.getDateFinPrevue ()) ;
            mIteration.setNom (lIterationTmp.getNom ()) ;
          }
          // R�cup�ration de la t�che saisie par l'utilisateur.
          VTransfert.transferer (getRequete (), lTache, CConstante.PAR_ARBRETACHES) ;
          lTache.setResteAPasser (lTache.getChargeInitiale ()) ;
          // Met � jour le mod�le.
          lTache.setIteration (mIteration) ;
          lTache.setCollaborateur (lCollaborateur) ;
          lTache.setActivite (lActivite) ;
          mIteration.addTache (lTache) ;
          lCollaborateur.addTache (lTache) ;
          lActivite.addTache (lTache) ;
          // ajout de la t�che dans la base de donn�es.
          getBaseDonnees ().create (lTache) ;
          mMessage = bundle.getString ("clTache") + " " + lTache.getNom () + " "
                     + bundle.getString ("cBienCreee") ;
        }
        // Modification d'une t�che si on la modifie directement ou si on ajoute, modifie ou
        // supprime un artefact.
        else if ( (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITMODIFIER))
                 || (VTransfert.getValeurTransmise (getRequete (),
                                                    CConstante.PAR_SUBMITAJOUTER_ARTSORTIES))
                 || (VTransfert.getValeurTransmise (getRequete (),
                                                    CConstante.PAR_SUBMITMODIFIER_ARTSORTIES))
                 || (VTransfert.getValeurTransmise (getRequete (),
                                                    CConstante.PAR_SUBMITSUPPRIMER_ARTSORTIES)))
        {
          boolean trouve = false ;
          MTache lTacheTmp = new MTache () ;
          MCollaborateur lCollaborateur = null ;
          MActivite lActivite = null ;
          int lIdCollaborateur = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTECOLLABORATEURS)) ;
          int lIdActivite = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTEACTIVITES)) ;
          // Recherche du collaborateur effectuant la t�che.
          for (int i = 0; ! trouve && i < mProjet.getNbCollaborateurs (); i ++ )
          {
            if (mProjet.getCollaborateur (i).getId () == lIdCollaborateur)
            {
              lCollaborateur = mProjet.getCollaborateur (i) ;
              trouve = true ;
            }
          }
          // Recherche de l'activit� � laquelle est li�e la t�che.
          trouve = false ;
          for (int i = 0; ! trouve && i < lProcessus.getNbComposants (); i ++ )
          {
            MComposant lComposant = lProcessus.getComposant (i) ;
            for (int j = 0; j < lComposant.getNbDefinitionsTravail (); j ++ )
            {
              MDefinitionTravail lDefTrav = lComposant.getDefinitionTravail (j) ;
              for (int k = 0; k < lDefTrav.getNbActivites (); k ++ )
              {
                if (lDefTrav.getActivite (k).getId () == lIdActivite)
                {
                  lActivite = lDefTrav.getActivite (k) ;
                  trouve = true ;
                }
              }
            }
          }
          // Indice de la t�che et de l'artefact dans leur liste respective.
          int lIndiceTache = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHES)) ;
          //VTransfert.transferer (getRequete (), mIteration, CConstante.PAR_ARBREITERATION) ;
          VTransfert.transferer (getRequete (), lTacheTmp, CConstante.PAR_ARBRETACHES) ;
          // R�cup�ration de l'identifiant de la t�che impr�vue que l'on souhaite modifier.
          int lIdTache = mIteration.getTache (lIndiceTache).getId () ;
          // On r�cup�re la tache impr�vue que l'on souhaite modifier dans la base.
          lRequete = getBaseDonnees ()
            .getOQLQuery ("select TACHE from owep.modele.execution.MTache TACHE where mId = $1") ;
          lRequete.bind (lIdTache) ;
          lResultat = lRequete.execute () ;
          MTache lTache = (MTache) lResultat.next () ;
          // Mise � jour de la t�che.
          lTache.setNom (lTacheTmp.getNom ()) ;
          lTache.setChargeInitiale (lTacheTmp.getChargeInitiale ()) ;
          lTache.setDescription (lTacheTmp.getDescription ()) ;
          lTache.setDateDebutPrevue (lTacheTmp.getDateDebutPrevue ()) ;
          lTache.setDateFinPrevue (lTacheTmp.getDateDebutPrevue ()) ;
          // Mise � jour du mod�le.
          lTache.getCollaborateur ().supprimerTache (lTache) ;
          lTache.setCollaborateur (lCollaborateur) ;
          lCollaborateur.addTache (lTache) ;
          lTache.getActivite ().supprimerTache (lTache) ;
          lTache.setActivite (lActivite) ;
          lActivite.addTache (lTache) ;
          mMessage = bundle.getString ("clTache") + " " + lTache.getNom () + " "
                     + bundle.getString ("cBienModifiee") ;
        }
        // Supprimer une t�che.
        else if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITSUPPRIMER))
        {
          MActivite lActivite ;
          MTache lTache ;
          MCollaborateur lCollaborateur ;
          MArtefact lArtefactSortie ;
          MArtefact lArtefactEntree ;
          MIteration lIteration ;
          int lIndiceTache = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHES)) ;
          int lIdTache = mIteration.getTache (lIndiceTache).getId () ;
          // r�cup�ration de la t�che dans la bd.
          lRequete = getBaseDonnees ()
            .getOQLQuery ("select TACHE from owep.modele.execution.MTache TACHE where mId = $1") ;
          lRequete.bind (lIdTache) ;
          lResultat = lRequete.execute () ;
          lTache = (MTache) lResultat.next () ;
          // r�cup�ration de l'activit� dont la t�che est li�e.
          lRequete = getBaseDonnees ()
            .getOQLQuery (
                          "select ACTIVITE from owep.modele.processus.MActivite ACTIVITE where mId = $1") ;
          lRequete.bind (lTache.getActivite ().getId ()) ;
          lResultat = lRequete.execute () ;
          lActivite = (MActivite) lResultat.next () ;
          // r�cup�ration de l'it�ration dont fait partie cette t�che.
          lRequete = getBaseDonnees ()
            .getOQLQuery (
                          "select ITERATION from owep.modele.execution.MIteration ITERATION where mId = $1") ;
          lRequete.bind (lTache.getIteration ().getId ()) ;
          lResultat = lRequete.execute () ;
          lIteration = (MIteration) lResultat.next () ;
          // r�cup�ration du collaborateur qui doit effectu� cette t�che.
          lRequete = getBaseDonnees ()
            .getOQLQuery (
                          "select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mId = $1") ;
          lRequete.bind (lTache.getCollaborateur ().getId ()) ;
          lResultat = lRequete.execute () ;
          lCollaborateur = (MCollaborateur) lResultat.next () ;
          // Mise � jour du mod�le.
          lCollaborateur.supprimerTache (lTache) ;
          lIteration.supprimerTache (lTache) ;
          lActivite.supprimerTache (lTache) ;
          // On supprime les artefacts en entr�es de la t�che impr�vue courante.
          for (int j = lTache.getNbArtefactsEntrees () - 1; j >= 0; j -- )
          {
            lRequete = getBaseDonnees ()
              .getOQLQuery (
                            "select ARTEFACT from owep.modele.execution.MArtefact ARTEFACT where mId = $1") ;
            lRequete.bind (lTache.getArtefactEntree (j).getId ()) ;
            lResultat = lRequete.execute () ;
            lArtefactEntree = (MArtefact) lResultat.next () ;
            lArtefactEntree.setTacheEntree (null) ;
            lTache.supprimerArtefactEntree (j) ;
          }
          // On supprime les artefacts en sorties de la t�che impr�vue courante.
          for (int j = lTache.getNbArtefactsSorties () - 1; j >= 0; j -- )
          {
            int lIdArtefact = lTache.getArtefactSortie (j).getId () ;
            lRequete = getBaseDonnees ()
              .getOQLQuery (
                            "select ARTEFACT from owep.modele.execution.MArtefact ARTEFACT where mId = $1") ;
            lRequete.bind (lIdArtefact) ;
            lResultat = lRequete.execute () ;
            lArtefactSortie = (MArtefact) lResultat.next () ;
            lTache.supprimerArtefactSortie (lArtefactSortie) ;
            getBaseDonnees ().remove (lArtefactSortie) ;
          }
          // On supprime toutes les conditions de la t�ches
          for (int i = lTache.getNbConditions () - 1; i >= 0; i -- )
          {
            // on r�cup�re la condition de la t�che
            lRequete = getBaseDonnees ()
              .getOQLQuery (
                            "select CONDITION from owep.modele.execution.MCondition CONDITION where mId = $1") ;
            lRequete.bind (lTache.getCondition (i).getId ()) ;
            lResultat = lRequete.execute () ;
            MCondition lCondition = (MCondition) lResultat.next () ;
            // on met � jour le mod�le
            lTache.supprimerCondition (lCondition) ;
            // on supprime la condition de la bd.
            getBaseDonnees ().remove (lCondition) ;
          }
          // On supprime toutes les d�pendances qu'ils pouvaient avoir avec cette t�che.
          for (int i = 0; i < lIteration.getNbTaches (); i ++ )
          {
            MTache lTacheTmp = lIteration.getTache (i) ;
            if (lTacheTmp.getId () != lTache.getId ())
            {
              // on r�cup�re une t�che de l'it�ration afin de voir si elle est
              // en relation avec la t�che que l'on supprime.
              lRequete = getBaseDonnees ()
                .getOQLQuery ("select TACHE from owep.modele.execution.MTache TACHE where mId = $1") ;
              lRequete.bind (lTacheTmp.getId ()) ;
              lResultat = lRequete.execute () ;
              lTacheTmp = (MTache) lResultat.next () ;
              for (int j = lTacheTmp.getNbConditions () - 1; j >= 0; j -- )
              {
                MCondition lConditionTmp = lTacheTmp.getCondition (j) ;
                if (lConditionTmp.getTachePrecedente ().getId () == lTache.getId ())
                {
                  lRequete = getBaseDonnees ()
                    .getOQLQuery (
                                  "select CONDITION from owep.modele.execution.MCondition CONDITION where mId = $1") ;
                  lRequete.bind (lConditionTmp.getId ()) ;
                  lResultat = lRequete.execute () ;
                  lConditionTmp = (MCondition) lResultat.next () ;
                  lTacheTmp.supprimerCondition (lConditionTmp) ;
                  getBaseDonnees ().remove (lConditionTmp) ;
                }
              }
            }
          }
          getBaseDonnees ().remove (lTache) ;
          mMessage = bundle.getString ("clTache") + " " + lTache.getNom () + " "
                     + bundle.getString ("cBienSupprimee") ;
        }
        // Ajout des d�pendances entre t�ches
        if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITAJOUTER_TACDEPEND))
        {
          MCondition lCondition = new MCondition () ;
          // r�cup�ration des donn�es de la jsp
          int lIndiceTache = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHES)) ;
          int lIndiceTachePre = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHESPOSSIBLES)) ;
          int lIndiceCondition = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHESCONDITION)) ;
          MTache lTache = mIteration.getTache (lIndiceTache) ;
          MTache lTachePre = mIteration.getTache (lIndiceTachePre) ;
          // Mise � jour du mod�le
          lCondition.setTache (lTache) ;
          lCondition.setTachePrecedente (lTachePre) ;
          // si l'utilisateur a choisi en cours
          if (lIndiceCondition == 0)
          {
            lCondition.setEtat (1) ;
          }
          // sinon il a choisi termin�
          else
          {
            lCondition.setEtat (3) ;
          }
          lTache.addCondition (lCondition) ;
          // ajout de la condition dans la base de donn�es.
          getBaseDonnees ().create (lCondition) ;
          mMessage = bundle.getString ("cDependance") + " " + lTache.getNom () + " - "
                     + lTachePre.getNom () + " " + bundle.getString ("cBienCreee") ;
        }
        // Supprimer d�pendance entre t�che.
        else if (VTransfert.getValeurTransmise (getRequete (),
                                                CConstante.PAR_SUBMITSUPPRIMER_TACDEPEND))
        {
          int lIndiceTache = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHES)) ;
          int lIndiceTacheDep = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHESDEPENDANTES)) ;
          int lIdTache = mIteration.getTache (lIndiceTache).getId () ;
          // r�cup�ration de la t�che dans la bd.
          lRequete = getBaseDonnees ()
            .getOQLQuery ("select TACHE from owep.modele.execution.MTache TACHE where mId = $1") ;
          lRequete.bind (lIdTache) ;
          lResultat = lRequete.execute () ;
          MTache lTache = (MTache) lResultat.next () ;
          int lIdCondition = lTache.getCondition (lIndiceTacheDep).getId () ;
          // r�cup�ration de la condition dans la bd.
          lRequete = getBaseDonnees ()
            .getOQLQuery (
                          "select CONDITION from owep.modele.execution.MCondition CONDITION where mId = $1") ;
          lRequete.bind (lIdCondition) ;
          lResultat = lRequete.execute () ;
          MCondition lCondition = (MCondition) lResultat.next () ;
          lTache.supprimerCondition (lCondition) ;
          getBaseDonnees ().remove (lCondition) ;
          mMessage = bundle.getString ("cDependance") + " " + lTache.getNom () + " - "
                     + lCondition.getTachePrecedente ().getNom () + " "
                     + bundle.getString ("cBienSupprimee") ;
        }
        // Ajout d'un artefact en sortie.
        if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITAJOUTER_ARTSORTIES))
        {
          MArtefact lArtefactSortie = new MArtefact () ;
          VTransfert.transferer (getRequete (), lArtefactSortie,
                                 CConstante.PAR_ARBREARTEFACTSORTIES) ;
          // Indice de la t�che et de l'artefact dans leur liste respective.
          int lIndiceTache = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHES)) ;
          int lIndiceProduit = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTEPRODUITS)) ;
          int lIndiceResponsable = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTERESPONSABLES)) ;
          MTache lTache = mIteration.getTache (lIndiceTache) ;
          MActivite lActivite = lTache.getActivite () ;
          MProduit lProduit = lActivite.getProduitSortie (lIndiceProduit) ;
          MCollaborateur lResponsable = lProduit.getResponsable ()
            .getCollaborateur (lIndiceResponsable) ;
          // Mise � jour du mod�le.
          lArtefactSortie.setTacheSortie (lTache) ;
          lTache.addArtefactSortie (lArtefactSortie) ;
          lArtefactSortie.setProjet (mProjet) ;
          mProjet.addArtefact (lArtefactSortie) ;
          lArtefactSortie.setProduit (lProduit) ;
          lProduit.addArtefact (lArtefactSortie) ;
          lArtefactSortie.setResponsable (lResponsable) ;
          lResponsable.addArtefact (lArtefactSortie) ;
          // ajout de l'artefact dans la base de donn�es.
          getBaseDonnees ().create (lArtefactSortie) ;
          mMessage = bundle.getString ("clArtefact") + " " + lArtefactSortie.getNom () + " "
                     + bundle.getString ("cBienCree") ;
        }
        // Modification d'un artefact.
        else if (VTransfert.getValeurTransmise (getRequete (),
                                                CConstante.PAR_SUBMITMODIFIER_ARTSORTIES))
        {
          // Indice de la t�che et de l'artefact dans leur liste respective.
          int lIndiceTache = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHES)) ;
          int lIndiceArtSortie = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTEARTEFACTSSORTIES)) ;
          int lIndiceProduit = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTEPRODUITS)) ;
          int lIndiceResponsable = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTERESPONSABLES)) ;
          int lIdArtefactSortie = mIteration.getTache (lIndiceTache)
            .getArtefactSortie (lIndiceArtSortie).getId () ;
          // r�cup�ration des modifications de l'artefact depuis la jsp.
          MArtefact lArtefactSortieTmp = new MArtefact () ;
          VTransfert.transferer (getRequete (), lArtefactSortieTmp,
                                 CConstante.PAR_ARBREARTEFACTSORTIES) ;
          // s�lection de l'artefact depuis la bd.
          lRequete = getBaseDonnees ()
            .getOQLQuery (
                          "select ARTEFACT from owep.modele.execution.MArtefact ARTEFACT where mId = $1") ;
          lRequete.bind (lIdArtefactSortie) ;
          lResultat = lRequete.execute () ;
          MArtefact lArtefactSortie = (MArtefact) lResultat.next () ;
          MTache lTache = mIteration.getTache (lIndiceTache) ;
          MActivite lActivite = lTache.getActivite () ;
          MProduit lProduit = lActivite.getProduitSortie (lIndiceProduit) ;
          MCollaborateur lResponsable = lProduit.getResponsable ()
            .getCollaborateur (lIndiceResponsable) ;
          // Mise � jour de l'artefact.
          lArtefactSortie.setNom (lArtefactSortieTmp.getNom ()) ;
          lArtefactSortie.setDescription (lArtefactSortieTmp.getDescription ()) ;
          lArtefactSortie.setProduit (lActivite.getProduitSortie (lIndiceProduit)) ;
          lArtefactSortie.setResponsable (lProduit.getResponsable ()
            .getCollaborateur (lIndiceResponsable)) ;
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
          mMessage = bundle.getString ("clArtefact") + " " + lArtefactSortie.getNom () + " "
                     + bundle.getString ("cBienModifie") ;
        }
        // Supprimer un artefact en sortie.
        else if (VTransfert.getValeurTransmise (getRequete (),
                                                CConstante.PAR_SUBMITSUPPRIMER_ARTSORTIES))
        {
          MTache lTache ;
          MArtefact lArtefactSortie ;
          // R�cup�ration de la position de la t�che et de l'identifiant de l'artefact impr�vue.
          int lIndiceTache = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHES)) ;
          int lIndiceArtSortie = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTEARTEFACTSSORTIES)) ;
          int lIdArtSortie = mIteration.getTache (lIndiceTache)
            .getArtefactSortie (lIndiceArtSortie).getId () ;
          // R�cup�ration de l'artefact impr�vue depuis la base de donn�es.
          lRequete = getBaseDonnees ()
            .getOQLQuery (
                          "select ARTEFACT from owep.modele.execution.MArtefact ARTEFACT where mId = $1") ;
          lRequete.bind (lIdArtSortie) ;
          lResultat = lRequete.execute () ;
          lArtefactSortie = (MArtefact) lResultat.next () ;
          // R�cup�ration de la t�che impr�vue depuis la base de donn�es.
          lRequete = getBaseDonnees ()
            .getOQLQuery ("select TACHE from owep.modele.execution.MTache TACHE where mId = $1") ;
          lRequete.bind (lArtefactSortie.getTacheSortie ().getId ()) ;
          lResultat = lRequete.execute () ;
          lTache = (MTache) lResultat.next () ;
          // Mise � jour de mod�le
          lTache.supprimerArtefactSortie (lArtefactSortie) ;
          // Suppression de l'artefact dans la base de donn�es.
          getBaseDonnees ().remove (lArtefactSortie) ;
          mMessage = bundle.getString ("clArtefact") + " " + lArtefactSortie.getNom () + " "
                     + bundle.getString ("cBienSupprime") ;
        }
        // Ajouter un artefact en entr�e
        else if (VTransfert.getValeurTransmise (getRequete (),
                                                CConstante.PAR_SUBMITAJOUTER_ARTENTREES))
        {
          int lIndiceTache = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHES)) ;
          int lIdArtEntree = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTEARTEFACTSPOSSIBLES)) ;
          MTache lTache = mIteration.getTache (lIndiceTache) ;
          MActivite lActivite = lTache.getActivite () ;
          // r�cup�ration de l'artefact depuis la bd.
          lRequete = getBaseDonnees ()
            .getOQLQuery (
                          "select ARTEFACT from owep.modele.execution.MArtefact ARTEFACT where mId = $1") ;
          lRequete.bind (lIdArtEntree) ;
          lResultat = lRequete.execute () ;
          MArtefact lArtEntree = (MArtefact) lResultat.next () ;
          // mise � jour du mod�le.
          lTache.addArtefactEntrees (lArtEntree) ;
          lArtEntree.setTacheEntree (lTache) ;
          mMessage = bundle.getString ("clArtefact") + " " + lArtEntree.getNom () + " "
                     + bundle.getString ("cBienAjoute") ;
        }
        // Supprimer un artefact en entr�e
        else if (VTransfert.getValeurTransmise (getRequete (),
                                                CConstante.PAR_SUBMITSUPPRIMER_ARTENTREES))
        {
          int lIndiceTache = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHES)) ;
          int lIdArtEntree = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTEARTEFACTSENTREES)) ;
          // r�cup�ration de l'artefact depuis la bd.
          lRequete = getBaseDonnees ()
            .getOQLQuery (
                          "select ARTEFACT from owep.modele.execution.MArtefact ARTEFACT where mId = $1") ;
          lRequete.bind (lIdArtEntree) ;
          lResultat = lRequete.execute () ;
          MArtefact lArtEntree = (MArtefact) lResultat.next () ;
          // mise � jour du mod�le.
          mIteration.getTache (lIndiceTache).supprimerArtefactEntree (lArtEntree) ;
          lArtEntree.setTacheEntree (null) ;
          mMessage = bundle.getString ("clArtefact") + " " + lArtEntree.getNom () + " "
                     + bundle.getString ("cBienRetire") ;
        }
      }
      catch (ClassNotPersistenceCapableException e)
      {
        e.printStackTrace () ;
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
      catch (DuplicateIdentityException e)
      {
        e.printStackTrace () ;
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
      catch (TransactionNotInProgressException e)
      {
        e.printStackTrace () ;
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
      catch (PersistenceException e)
      {
        e.printStackTrace () ;
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
      // Validation de l'it�ration.
      if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMIT))
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
    try
    {
      getBaseDonnees ().commit () ;
      getBaseDonnees ().close () ;
    }
    catch (PersistenceException e)
    {
      e.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
    // Ferme la connexion � la base de donn�es.
    if ( ! collab)
    {
      mMessage = "Vous ne pouvez cr�er d'it�ration tant qu'aucun r�le n'est affect� � un collaborateur" ;
      getRequete ().setAttribute (CConstante.PAR_MESSAGE, mMessage) ;
      return "/Processus/CreationCollaborateur" ;
    }

    getRequete ().getSession ().setAttribute (CConstante.PAR_ITERATION, mIteration) ;

    if ( ! VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMIT))
    {
      getRequete ().setAttribute (CConstante.PAR_MESSAGE, mMessage) ;
      return "..\\JSP\\Processus\\TIterationModif.jsp" ;
    }
    else
    {
      if ( (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_VIDE))
          && (getRequete ().getParameter (CConstante.PAR_MODIFIER) == null))
      {
        mMessage = bundle.getString ("clIteration") + " " + mIteration.getNumero () + " "
                   + bundle.getString ("cBienCree") ;
      }
      else
      {
        mMessage = bundle.getString ("clIteration") + " " + mIteration.getNumero () + " "
                   + bundle.getString ("cBienModifiee") ;
      }
      getRequete ().setAttribute (CConstante.PAR_MESSAGE, mMessage) ;
      return "..\\JSP\\Processus\\TProjetVisu.jsp" ;
    }
  }
}