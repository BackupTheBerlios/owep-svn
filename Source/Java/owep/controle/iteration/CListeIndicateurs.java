package owep.controle.iteration;

import java.util.NoSuchElementException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.jdo.QueryResults;
import owep.controle.CConstante;
import owep.controle.CControleurBase;
import owep.infrastructure.Session;
import owep.modele.execution.MCollaborateur;
import owep.modele.execution.MIndicateur;
import owep.modele.execution.MIteration;
import owep.modele.execution.MMesureIndicateur;
import owep.modele.execution.MProjet;


/**
 * Controleur pour l'affichage de la liste des indicateurs pour l'iteration en cours
 */
public class CListeIndicateurs extends CControleurBase
{
  private MProjet mProjet ; // Projet ayant été ouvert dans la session
  private MIteration mIteration ;  // Iteration en cours
  private Session mSession ; // Session associé à la connexion

  /**
   * Récupère les données nécessaire au controleur dans la base de données.
   * 
   * @throws ServletException Si une erreur survient durant la connexion
   * @see owep.controle.CControleurBase#initialiserBaseDonnees()
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    OQLQuery lRequete ; // Requête à réaliser sur la base
    QueryResults lResultat ; // Résultat de la requête sur la base

    try
    {
      //    Récupère le projet ouvert dans la session
      HttpSession session = getRequete ().getSession (true) ;
      mSession = (Session) session.getAttribute ("SESSION") ;
      mProjet = mSession.getProjet() ;
      getBaseDonnees ().begin () ;

      int lIdProjet = mProjet.getId () ;
      // Récupère le projet dans la base de données
      lRequete = getBaseDonnees ()
        .getOQLQuery (
                      "select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
      lRequete.bind (lIdProjet) ;
      lResultat = lRequete.execute () ;
      mProjet = (MProjet) lResultat.next () ;
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
  }

  /**
   * Récupère les paramètres passés au controleur.
   * 
   * @throws ServletException -
   * @see owep.controle.CControleurBase#initialiserParametres()
   */
  public void initialiserParametres () throws ServletException
  {
    OQLQuery lRequete ; // Requête à réaliser sur la base
    QueryResults lResultat ; // Résultat de la requête sur la base
    MMesureIndicateur lMesureIndicateur ;
    
    try
    { 
      // pour chaque indicateur du projet
      for (int i = 0 ; i < mProjet.getNbIndicateurs() ; i++)
      {
        // récupération des identifiants de l indicateur, du collaborateur et de l itération
        MIndicateur lIndicateur = (MIndicateur)mProjet.getIndicateur(i) ;
        int lIdIndicateur = lIndicateur.getId() ;
        // Récupère l'indicateur dans la base de données
        lRequete = getBaseDonnees ()
          .getOQLQuery (
                        "select INDICATEUR from owep.modele.execution.MIndicateur INDICATEUR where mId = $1") ;
        lRequete.bind (lIdIndicateur) ;
        lResultat = lRequete.execute () ;
        lIndicateur = (MIndicateur) lResultat.next () ; 
        
        
        MCollaborateur lCollaborateur = mSession.getCollaborateur () ;
        int lIdCollab = lCollaborateur.getId () ;
        // Récupère le collaborateur dans la base de données
        lRequete = getBaseDonnees ()
          .getOQLQuery (
                        "select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mId = $1") ;
        lRequete.bind (lIdCollab) ;
        lResultat = lRequete.execute () ;
        lCollaborateur = (MCollaborateur) lResultat.next () ;
        
        // recherche de l iteration en cours
        for (int j = 0; j < mProjet.getNbIterations() ; j++)
        {
          if (mProjet.getIteration(j).getEtat()==1)
            mIteration = mProjet.getIteration(j);
        }
        int lIdIteration = mIteration.getId() ;
        // Récupère l'itération dans la base de données
        lRequete = getBaseDonnees ()
          .getOQLQuery (
                        "select ITERATION from owep.modele.execution.MIteration ITERATION where mId = $1") ;
        lRequete.bind (lIdIteration) ;
        lResultat = lRequete.execute () ;
        mIteration = (MIteration) lResultat.next () ;
        
        // Récupère la mesure dans la base de données
        lRequete = getBaseDonnees ()
          .getOQLQuery ("select MESUREINDICATEUR from owep.modele.execution.MMesureIndicateur MESUREINDICATEUR where mIndicateur = $1 and mCollaborateur = $2 and mIteration = $3") ;
        lRequete.bind (lIdIndicateur) ;
        lRequete.bind (lIdCollab) ;
        lRequete.bind (lIdIteration) ;
        lResultat = lRequete.execute () ;
        try
        {
          lMesureIndicateur = (MMesureIndicateur) lResultat.next () ;
        }
        // si la mesure associé à l indicateur n'a pas encore été créé, on la crée
        catch (NoSuchElementException eException)
        {
          // creation dans la bd de la mesure 
          lMesureIndicateur = new MMesureIndicateur() ;
          lMesureIndicateur.setCollaborateur(lCollaborateur) ;
          lMesureIndicateur.setIndicateur(lIndicateur) ;
          lMesureIndicateur.setIteration(mIteration) ;
          lIndicateur.addMesure(lMesureIndicateur) ;
          mIteration.addMesure(lMesureIndicateur) ;
          lCollaborateur.addMesure(lMesureIndicateur) ;
          
          getBaseDonnees ().create(lMesureIndicateur) ;
          
          // ajout de l indicateur a la liste des indicateurs du projet
          mProjet.setListe(new Integer(i),lMesureIndicateur) ;
        }
        
        // ajout de l indicateur a la liste des indicateurs du projet
        mProjet.setListe(new Integer(i),lMesureIndicateur) ;
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
   * Récupère la liste des tâches d'un collaborateur pour l'itération choisie, et la transmet à la
   * JSP.
   * 
   * @return URL de la page vers laquelle doit être redirigé le client.
   * @throws ServletException Si une erreur survient dans le controleur
   * @see owep.controle.CControleurBase#traiter()
   */
  public String traiter () throws ServletException
  {
  	// Transmet les données à la JSP d'affichage.
    getRequete ().setAttribute (CConstante.PAR_PROJET, mProjet) ;
    if(mProjet.getNbIndicateurs()>0)
      getRequete ().setAttribute (CConstante.PAR_ITERATION, mIteration) ;
    return "/JSP/Iteration/TListeIndicateurs.jsp" ;
  }
}

