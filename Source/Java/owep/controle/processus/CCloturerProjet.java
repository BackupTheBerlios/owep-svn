package owep.controle.processus ;


import java.text.ParseException ;
import java.text.SimpleDateFormat ;
import java.util.Date ;
import java.util.Iterator ;
import java.util.ResourceBundle ;

import javax.servlet.ServletException ;

import org.exolab.castor.jdo.OQLQuery ;
import org.exolab.castor.jdo.QueryResults ;

import owep.controle.CConstante ;
import owep.controle.CControleurBase ;
import owep.infrastructure.Session ;
import owep.modele.execution.MIteration ;
import owep.modele.execution.MProjet ;
import owep.modele.execution.MTache ;
import owep.modele.execution.MTacheImprevue ;


/**
 * Cloture le projet.
 */
public class CCloturerProjet extends CControleurBase
{

  private MProjet mProjet ;                // Projet actuellement ouvert.
  private String  mDateDebutReelle = null ;
  private String  mDateFinReelle ;
  private String  mBilan ;


  /**
   * Récupère les données nécessaire au controleur dans la base de données.
   * 
   * @throws ServletException Si une erreur survient durant la connexion à la base.
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    Session lSession ; // Session actuelle de l'utilisateur.
    OQLQuery lRequete ; // Requête à réaliser sur la base.
    QueryResults lResultat ; // Résultat de la requête sur la base.

    lSession = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
    int idProjet = lSession.getIdProjet () ;

    try
    {
      getBaseDonnees ().begin () ;

      // Récupère le projet actuellement ouvert.
      lRequete = getBaseDonnees ()
        .getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
      lRequete.bind (idProjet) ;
      lResultat = lRequete.execute () ;
      // Si on récupère correctement le projet dans la base,
      if (lResultat.hasMore ())
      {
        mProjet = (MProjet) lResultat.next () ;
      }
      // Si le projet n'existe pas,
      else
      {
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
      
      getBaseDonnees ().commit () ;

    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
  }

  /**
   * Récupère les paramètres passés au contrôleur.
   * 
   * @throws ServletException -
   */
  public void initialiserParametres () throws ServletException
  {
    mDateDebutReelle = getRequete ().getParameter ("mDateDebutReelle") ;
    mDateFinReelle = getRequete ().getParameter ("mDateFinReelle") ;
    mBilan = getRequete ().getParameter ("mBilan") ;
  }

  /**
   * Transmet le problème à la JSP d'affichage si aucune données n'est soumise. Sinon, met à jour ou
   * insère le problème.
   * 
   * @return URL de la page vers laquelle doit être redirigé le client.
   * @throws ServletException Si une erreur survient dans le controleur
   */
  public String traiter () throws ServletException
  {
    if (mDateDebutReelle == null)
    {
      getRequete ().setAttribute (CConstante.PAR_PROJET, mProjet) ;
      return "/JSP/Processus/TCloturerProjet.jsp" ;
    }

    try
    {
      Session lSession ; // Session actuelle de l'utilisateur.
      OQLQuery lRequete ; // Requête à réaliser sur la base.
      QueryResults lResultat ; // Résultat de la requête sur la base.

      // changement de format des dates
      SimpleDateFormat dateFormat = new SimpleDateFormat ("dd/MM/yyyy") ;
      Date dateDebutReelle = null ;
      Date dateFinReelle = null ;
      try
      {
        dateDebutReelle = dateFormat.parse (mDateDebutReelle) ;
        dateFinReelle = dateFormat.parse (mDateFinReelle) ;
      }
      catch (ParseException e)
      {
        //mErreur = mMessage.getString ("projetErreurDate") ;
        //e.printStackTrace () ;
      }

      lSession = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
      //mProjet = lSession.getProjet () ;
      int idProjet = lSession.getIdProjet () ;

      getBaseDonnees().begin();
      // Récupère le projet actuellement ouvert.
      lRequete = getBaseDonnees ()
        .getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
      lRequete.bind (idProjet) ;
      lResultat = lRequete.execute () ;
      // Si on récupère correctement le projet dans la base,
      if (lResultat.hasMore ())
      {
        mProjet = (MProjet) lResultat.next () ;
      }
      // Si le projet n'existe pas,
      else
      {
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
      
      mProjet.setDateDebutReelle(dateDebutReelle);
      mProjet.setBilan(mBilan);
      mProjet.setDateFinReelle(dateFinReelle);
      mProjet.setEtat(MTache.ETAT_TERMINE);

      getBaseDonnees ().commit () ;
      
      Iterator itIteration = mProjet.getListeIterations ().iterator () ;
      while (itIteration.hasNext ())
      {
        getBaseDonnees().begin();
        MIteration lIteration = (MIteration) itIteration.next () ;
        lRequete = getBaseDonnees ()
          .getOQLQuery ("select IT from owep.modele.execution.MIteration IT where mId=$1") ;
        lRequete.bind (lIteration.getId ()) ;
        lResultat = lRequete.execute () ;
        lIteration = (MIteration) lResultat.next () ;

        lIteration.setEtat(MTache.ETAT_TERMINE);
        if(lIteration.getDateDebutReelle() == null)
          lIteration.setDateDebutReelle(dateFinReelle);
        lIteration.setDateFinReelle(dateFinReelle);
        
        getBaseDonnees().commit();
        
        Iterator itTache = lIteration.getListeTaches ().iterator () ;
        while (itTache.hasNext ())
        {
          getBaseDonnees().begin();
          MTache lTache = (MTache) itTache.next () ;
          lRequete = getBaseDonnees ()
            .getOQLQuery ("select TAC from owep.modele.execution.MTache TAC where mId=$1") ;
          lRequete.bind (lTache.getId ()) ;
          lResultat = lRequete.execute () ;
          lTache = (MTache) lResultat.next () ;

          lTache.setEtat (MTache.ETAT_TERMINE) ;
          if (lTache.getDateDebutReelle () == null)
            lTache.setDateDebutReelle (dateFinReelle) ;
          lTache.setDateFinReelle (dateFinReelle) ;
          getBaseDonnees().commit();
        }

        Iterator itTacheImp = lIteration.getListeTachesImprevues().iterator () ;
        while (itTacheImp.hasNext ())
        {
          getBaseDonnees().begin();
          MTacheImprevue lTacheImp = (MTacheImprevue) itTacheImp.next () ;
          lRequete = getBaseDonnees ()
            .getOQLQuery ("select TAC from owep.modele.execution.MTacheImprevue TAC where mId=$1") ;
          lRequete.bind (lTacheImp.getId ()) ;
          lResultat = lRequete.execute () ;
          lTacheImp = (MTacheImprevue) lResultat.next () ;

          lTacheImp.setEtat (MTache.ETAT_TERMINE) ;
          if (lTacheImp.getDateDebutReelle () == null)
            lTacheImp.setDateDebutReelle (dateFinReelle) ;
          lTacheImp.setDateFinReelle (dateFinReelle) ;
          
          getBaseDonnees().commit();
        }
        
        getSession().setIteration(null);
      }

    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
    }
    finally
    {
      try
      {
        getBaseDonnees ().close () ;
      }
      catch (Exception eException)
      {
        eException.printStackTrace () ;
      }
    }

    ResourceBundle messages = java.util.ResourceBundle.getBundle ("MessagesBundle") ;
    String lMessage = messages.getString ("cloturerProjetConfirmation") ;
    getRequete ().setAttribute (CConstante.PAR_MESSAGE, lMessage) ;

    // Affiche la page de visualisation des taches à réaliser dans la nouvelle itération.
    return "/Projet/OuvrirProjet" ;
  }
}