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
 * Controleur pour la modification d'une itération (ajout de tâches, d'artefact, ...) conformément à
 * un processus.
 */
public class CIterationModif extends CControleurBase
{
  private MProjet        mProjet ;   // Projet actuellement ouvert par l'utilisateur.
  private MIteration     mIteration ; // Itération modifiée ou ajoutée.
  private String         mMessage ;  // Message qui sera affiché dans la jsp suite à une opération
  private ResourceBundle bundle ;
  boolean                collab ;


  /**
   * Récupère les données nécessaire au controleur dans la base de données.
   * @throws ServletException Si une erreur survient durant la connexion
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    Session lSession ; // Session actuelle de l'utilisateur.
    OQLQuery lRequete ; // Requête à réaliser sur la base
    QueryResults lResultat ; // Résultat de la requête sur la base
    collab = false ;
    mMessage = new String ("") ;
    lSession = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
    mProjet = lSession.getProjet () ;
    //  Recuperation de la session
    HttpSession httpSession = getRequete ().getSession (true) ;
    //Récupération du ressource bundle
    bundle = ((Session) httpSession.getAttribute ("SESSION")).getMessages () ;
    try
    {
      getBaseDonnees ().begin () ;
      // Récupère la liste des tâches du collaborateur.
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
          // Initialise une nouvelle itération.
          mIteration = new MIteration () ;
          mIteration.setNom ("") ;
          mIteration.setNumero (mProjet.getNbIterations () + 1) ;
          mIteration.setProjet (mProjet) ;
          mProjet.addIteration (mIteration) ;
          try
          {
            // création de l'itération dans la base de données.
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
        // si c'est une modification, récupération de l'identifiant de l'itération à modifier
        if (getRequete ().getParameter (CConstante.PAR_MODIFIER) != null)
        {
          lIdIteration = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_ITERATION)) ;
        }
        // sinon on récupère l'identifiant de l'itération que l'on vient de rajouter dans la BD.
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
   * Récupère les paramètres passés au controleur.
   * @throws ServletException -
   */
  public void initialiserParametres () throws ServletException
  {
    Session lSession ; // Session actuelle de l'utilisateur.
    OQLQuery lRequete ; // Requête à réaliser sur la base
    QueryResults lResultat ; // Résultat de la requête sur la base
    if (collab)
    {
      try
      {
        MProcessus lProcessus = mProjet.getProcessus () ;
        // Modification d'une itération.
        if (getRequete ().getParameter (CConstante.PAR_MODIFIER) != null)
        {
          // Récupère l'itération à modifier et la met dans la session.
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
        // Ajout d'une tâche.
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
          // Recherche du collaborateur effectuant cette tâche.
          for (int i = 0; ! trouve && i < mProjet.getNbCollaborateurs (); i ++ )
          {
            if (mProjet.getCollaborateur (i).getId () == lIdCollaborateur)
            {
              lCollaborateur = mProjet.getCollaborateur (i) ;
              trouve = true ;
            }
          }
          // Recherche de l'activité à laquelle est liée la tâche
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
            // Récupération des données de l'itération ainsi que celle pour la tâche.
            VTransfert.transferer (getRequete (), lIterationTmp, CConstante.PAR_ARBREITERATION) ;
            mIteration.setDateDebutPrevue (lIterationTmp.getDateDebutPrevue ()) ;
            mIteration.setDateFinPrevue (lIterationTmp.getDateFinPrevue ()) ;
            mIteration.setNom (lIterationTmp.getNom ()) ;
          }
          // Récupération de la tâche saisie par l'utilisateur.
          VTransfert.transferer (getRequete (), lTache, CConstante.PAR_ARBRETACHES) ;
          lTache.setResteAPasser (lTache.getChargeInitiale ()) ;
          // Met à jour le modèle.
          lTache.setIteration (mIteration) ;
          lTache.setCollaborateur (lCollaborateur) ;
          lTache.setActivite (lActivite) ;
          mIteration.addTache (lTache) ;
          lCollaborateur.addTache (lTache) ;
          lActivite.addTache (lTache) ;
          // ajout de la tâche dans la base de données.
          getBaseDonnees ().create (lTache) ;
          mMessage = bundle.getString ("clTache") + " " + lTache.getNom () + " "
                     + bundle.getString ("cBienCreee") ;
        }
        // Modification d'une tâche si on la modifie directement ou si on ajoute, modifie ou
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
          // Recherche du collaborateur effectuant la tâche.
          for (int i = 0; ! trouve && i < mProjet.getNbCollaborateurs (); i ++ )
          {
            if (mProjet.getCollaborateur (i).getId () == lIdCollaborateur)
            {
              lCollaborateur = mProjet.getCollaborateur (i) ;
              trouve = true ;
            }
          }
          // Recherche de l'activité à laquelle est liée la tâche.
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
          // Indice de la tâche et de l'artefact dans leur liste respective.
          int lIndiceTache = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHES)) ;
          //VTransfert.transferer (getRequete (), mIteration, CConstante.PAR_ARBREITERATION) ;
          VTransfert.transferer (getRequete (), lTacheTmp, CConstante.PAR_ARBRETACHES) ;
          // Récupération de l'identifiant de la tâche imprévue que l'on souhaite modifier.
          int lIdTache = mIteration.getTache (lIndiceTache).getId () ;
          // On récupère la tache imprévue que l'on souhaite modifier dans la base.
          lRequete = getBaseDonnees ()
            .getOQLQuery ("select TACHE from owep.modele.execution.MTache TACHE where mId = $1") ;
          lRequete.bind (lIdTache) ;
          lResultat = lRequete.execute () ;
          MTache lTache = (MTache) lResultat.next () ;
          // Mise à jour de la tâche.
          lTache.setNom (lTacheTmp.getNom ()) ;
          lTache.setChargeInitiale (lTacheTmp.getChargeInitiale ()) ;
          lTache.setDescription (lTacheTmp.getDescription ()) ;
          lTache.setDateDebutPrevue (lTacheTmp.getDateDebutPrevue ()) ;
          lTache.setDateFinPrevue (lTacheTmp.getDateDebutPrevue ()) ;
          // Mise à jour du modèle.
          lTache.getCollaborateur ().supprimerTache (lTache) ;
          lTache.setCollaborateur (lCollaborateur) ;
          lCollaborateur.addTache (lTache) ;
          lTache.getActivite ().supprimerTache (lTache) ;
          lTache.setActivite (lActivite) ;
          lActivite.addTache (lTache) ;
          mMessage = bundle.getString ("clTache") + " " + lTache.getNom () + " "
                     + bundle.getString ("cBienModifiee") ;
        }
        // Supprimer une tâche.
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
          // récupération de la tâche dans la bd.
          lRequete = getBaseDonnees ()
            .getOQLQuery ("select TACHE from owep.modele.execution.MTache TACHE where mId = $1") ;
          lRequete.bind (lIdTache) ;
          lResultat = lRequete.execute () ;
          lTache = (MTache) lResultat.next () ;
          // récupération de l'activité dont la tâche est liée.
          lRequete = getBaseDonnees ()
            .getOQLQuery (
                          "select ACTIVITE from owep.modele.processus.MActivite ACTIVITE where mId = $1") ;
          lRequete.bind (lTache.getActivite ().getId ()) ;
          lResultat = lRequete.execute () ;
          lActivite = (MActivite) lResultat.next () ;
          // récupération de l'itération dont fait partie cette tâche.
          lRequete = getBaseDonnees ()
            .getOQLQuery (
                          "select ITERATION from owep.modele.execution.MIteration ITERATION where mId = $1") ;
          lRequete.bind (lTache.getIteration ().getId ()) ;
          lResultat = lRequete.execute () ;
          lIteration = (MIteration) lResultat.next () ;
          // récupération du collaborateur qui doit effectué cette tâche.
          lRequete = getBaseDonnees ()
            .getOQLQuery (
                          "select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mId = $1") ;
          lRequete.bind (lTache.getCollaborateur ().getId ()) ;
          lResultat = lRequete.execute () ;
          lCollaborateur = (MCollaborateur) lResultat.next () ;
          // Mise à jour du modèle.
          lCollaborateur.supprimerTache (lTache) ;
          lIteration.supprimerTache (lTache) ;
          lActivite.supprimerTache (lTache) ;
          // On supprime les artefacts en entrées de la tâche imprévue courante.
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
          // On supprime les artefacts en sorties de la tâche imprévue courante.
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
          // On supprime toutes les conditions de la tâches
          for (int i = lTache.getNbConditions () - 1; i >= 0; i -- )
          {
            // on récupère la condition de la tâche
            lRequete = getBaseDonnees ()
              .getOQLQuery (
                            "select CONDITION from owep.modele.execution.MCondition CONDITION where mId = $1") ;
            lRequete.bind (lTache.getCondition (i).getId ()) ;
            lResultat = lRequete.execute () ;
            MCondition lCondition = (MCondition) lResultat.next () ;
            // on met à jour le modèle
            lTache.supprimerCondition (lCondition) ;
            // on supprime la condition de la bd.
            getBaseDonnees ().remove (lCondition) ;
          }
          // On supprime toutes les dépendances qu'ils pouvaient avoir avec cette tâche.
          for (int i = 0; i < lIteration.getNbTaches (); i ++ )
          {
            MTache lTacheTmp = lIteration.getTache (i) ;
            if (lTacheTmp.getId () != lTache.getId ())
            {
              // on récupère une tâche de l'itération afin de voir si elle est
              // en relation avec la tâche que l'on supprime.
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
        // Ajout des dépendances entre tâches
        if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITAJOUTER_TACDEPEND))
        {
          MCondition lCondition = new MCondition () ;
          // récupération des données de la jsp
          int lIndiceTache = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHES)) ;
          int lIndiceTachePre = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHESPOSSIBLES)) ;
          int lIndiceCondition = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHESCONDITION)) ;
          MTache lTache = mIteration.getTache (lIndiceTache) ;
          MTache lTachePre = mIteration.getTache (lIndiceTachePre) ;
          // Mise à jour du modèle
          lCondition.setTache (lTache) ;
          lCondition.setTachePrecedente (lTachePre) ;
          // si l'utilisateur a choisi en cours
          if (lIndiceCondition == 0)
          {
            lCondition.setEtat (1) ;
          }
          // sinon il a choisi terminé
          else
          {
            lCondition.setEtat (3) ;
          }
          lTache.addCondition (lCondition) ;
          // ajout de la condition dans la base de données.
          getBaseDonnees ().create (lCondition) ;
          mMessage = bundle.getString ("cDependance") + " " + lTache.getNom () + " - "
                     + lTachePre.getNom () + " " + bundle.getString ("cBienCreee") ;
        }
        // Supprimer dépendance entre tâche.
        else if (VTransfert.getValeurTransmise (getRequete (),
                                                CConstante.PAR_SUBMITSUPPRIMER_TACDEPEND))
        {
          int lIndiceTache = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHES)) ;
          int lIndiceTacheDep = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHESDEPENDANTES)) ;
          int lIdTache = mIteration.getTache (lIndiceTache).getId () ;
          // récupération de la tâche dans la bd.
          lRequete = getBaseDonnees ()
            .getOQLQuery ("select TACHE from owep.modele.execution.MTache TACHE where mId = $1") ;
          lRequete.bind (lIdTache) ;
          lResultat = lRequete.execute () ;
          MTache lTache = (MTache) lResultat.next () ;
          int lIdCondition = lTache.getCondition (lIndiceTacheDep).getId () ;
          // récupération de la condition dans la bd.
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
          // Indice de la tâche et de l'artefact dans leur liste respective.
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
          // Mise à jour du modèle.
          lArtefactSortie.setTacheSortie (lTache) ;
          lTache.addArtefactSortie (lArtefactSortie) ;
          lArtefactSortie.setProjet (mProjet) ;
          mProjet.addArtefact (lArtefactSortie) ;
          lArtefactSortie.setProduit (lProduit) ;
          lProduit.addArtefact (lArtefactSortie) ;
          lArtefactSortie.setResponsable (lResponsable) ;
          lResponsable.addArtefact (lArtefactSortie) ;
          // ajout de l'artefact dans la base de données.
          getBaseDonnees ().create (lArtefactSortie) ;
          mMessage = bundle.getString ("clArtefact") + " " + lArtefactSortie.getNom () + " "
                     + bundle.getString ("cBienCree") ;
        }
        // Modification d'un artefact.
        else if (VTransfert.getValeurTransmise (getRequete (),
                                                CConstante.PAR_SUBMITMODIFIER_ARTSORTIES))
        {
          // Indice de la tâche et de l'artefact dans leur liste respective.
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
          // récupération des modifications de l'artefact depuis la jsp.
          MArtefact lArtefactSortieTmp = new MArtefact () ;
          VTransfert.transferer (getRequete (), lArtefactSortieTmp,
                                 CConstante.PAR_ARBREARTEFACTSORTIES) ;
          // sélection de l'artefact depuis la bd.
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
          // Mise à jour de l'artefact.
          lArtefactSortie.setNom (lArtefactSortieTmp.getNom ()) ;
          lArtefactSortie.setDescription (lArtefactSortieTmp.getDescription ()) ;
          lArtefactSortie.setProduit (lActivite.getProduitSortie (lIndiceProduit)) ;
          lArtefactSortie.setResponsable (lProduit.getResponsable ()
            .getCollaborateur (lIndiceResponsable)) ;
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
          mMessage = bundle.getString ("clArtefact") + " " + lArtefactSortie.getNom () + " "
                     + bundle.getString ("cBienModifie") ;
        }
        // Supprimer un artefact en sortie.
        else if (VTransfert.getValeurTransmise (getRequete (),
                                                CConstante.PAR_SUBMITSUPPRIMER_ARTSORTIES))
        {
          MTache lTache ;
          MArtefact lArtefactSortie ;
          // Récupération de la position de la tâche et de l'identifiant de l'artefact imprévue.
          int lIndiceTache = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHES)) ;
          int lIndiceArtSortie = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTEARTEFACTSSORTIES)) ;
          int lIdArtSortie = mIteration.getTache (lIndiceTache)
            .getArtefactSortie (lIndiceArtSortie).getId () ;
          // Récupération de l'artefact imprévue depuis la base de données.
          lRequete = getBaseDonnees ()
            .getOQLQuery (
                          "select ARTEFACT from owep.modele.execution.MArtefact ARTEFACT where mId = $1") ;
          lRequete.bind (lIdArtSortie) ;
          lResultat = lRequete.execute () ;
          lArtefactSortie = (MArtefact) lResultat.next () ;
          // Récupération de la tâche imprévue depuis la base de données.
          lRequete = getBaseDonnees ()
            .getOQLQuery ("select TACHE from owep.modele.execution.MTache TACHE where mId = $1") ;
          lRequete.bind (lArtefactSortie.getTacheSortie ().getId ()) ;
          lResultat = lRequete.execute () ;
          lTache = (MTache) lResultat.next () ;
          // Mise à jour de modèle
          lTache.supprimerArtefactSortie (lArtefactSortie) ;
          // Suppression de l'artefact dans la base de données.
          getBaseDonnees ().remove (lArtefactSortie) ;
          mMessage = bundle.getString ("clArtefact") + " " + lArtefactSortie.getNom () + " "
                     + bundle.getString ("cBienSupprime") ;
        }
        // Ajouter un artefact en entrée
        else if (VTransfert.getValeurTransmise (getRequete (),
                                                CConstante.PAR_SUBMITAJOUTER_ARTENTREES))
        {
          int lIndiceTache = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHES)) ;
          int lIdArtEntree = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTEARTEFACTSPOSSIBLES)) ;
          MTache lTache = mIteration.getTache (lIndiceTache) ;
          MActivite lActivite = lTache.getActivite () ;
          // récupération de l'artefact depuis la bd.
          lRequete = getBaseDonnees ()
            .getOQLQuery (
                          "select ARTEFACT from owep.modele.execution.MArtefact ARTEFACT where mId = $1") ;
          lRequete.bind (lIdArtEntree) ;
          lResultat = lRequete.execute () ;
          MArtefact lArtEntree = (MArtefact) lResultat.next () ;
          // mise à jour du modèle.
          lTache.addArtefactEntrees (lArtEntree) ;
          lArtEntree.setTacheEntree (lTache) ;
          mMessage = bundle.getString ("clArtefact") + " " + lArtEntree.getNom () + " "
                     + bundle.getString ("cBienAjoute") ;
        }
        // Supprimer un artefact en entrée
        else if (VTransfert.getValeurTransmise (getRequete (),
                                                CConstante.PAR_SUBMITSUPPRIMER_ARTENTREES))
        {
          int lIndiceTache = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTETACHES)) ;
          int lIdArtEntree = Integer.parseInt (getRequete ()
            .getParameter (CConstante.PAR_LISTEARTEFACTSENTREES)) ;
          // récupération de l'artefact depuis la bd.
          lRequete = getBaseDonnees ()
            .getOQLQuery (
                          "select ARTEFACT from owep.modele.execution.MArtefact ARTEFACT where mId = $1") ;
          lRequete.bind (lIdArtEntree) ;
          lResultat = lRequete.execute () ;
          MArtefact lArtEntree = (MArtefact) lResultat.next () ;
          // mise à jour du modèle.
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
      // Validation de l'itération.
      if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMIT))
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
    // Ferme la connexion à la base de données.
    if ( ! collab)
    {
      mMessage = "Vous ne pouvez créer d'itération tant qu'aucun rôle n'est affecté à un collaborateur" ;
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