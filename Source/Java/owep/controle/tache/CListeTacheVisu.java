package owep.controle.tache ;

import java.util.ArrayList;
import javax.servlet.ServletException ;
import javax.servlet.http.HttpSession ;
import org.exolab.castor.jdo.OQLQuery ;
import org.exolab.castor.jdo.PersistenceException ;
import org.exolab.castor.jdo.QueryResults ;

import owep.controle.CConstante ;
import owep.controle.CControleurBase ;
import owep.infrastructure.Session ;
import owep.modele.execution.MCollaborateur ;
import owep.modele.execution.MCondition ;
import owep.modele.execution.MProjet;
import owep.modele.execution.MTache ;
import owep.modele.execution.MTacheImprevue;


/**
 * Controleur pour l'affichage de la liste des tâches de l'utilisateur.
 */
public class CListeTacheVisu extends CControleurBase
{
  private int mIterationNum ; // Numéro d'itération dont on liste les tâches
  private MCollaborateur mCollaborateur ; // Collaborateur ayant ouvert la session
  private Session mSession ; // Session associé à la connexion
  private ArrayList lListeTaches ; // liste des tâches à afficher
  private ArrayList lListeTachesImprevues ; // liste des tâches imprévues à afficher
  private MProjet lProjet ; // projet pour lequel est connecté le collaborateur


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

      //    Récupère le collaborateur connecté
      HttpSession session = getRequete ().getSession (true) ;
      mSession = (Session) session.getAttribute ("SESSION") ;
      mCollaborateur = mSession.getCollaborateur () ;
      getBaseDonnees ().begin () ;

      int idCollab = mCollaborateur.getId () ;
      // Récupère la liste des tâches du collaborateur.
      lRequete = getBaseDonnees ()
        .getOQLQuery (
                      "select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mId = $1") ;
      lRequete.bind (idCollab) ;
      lResultat = lRequete.execute () ;
      mCollaborateur = (MCollaborateur) lResultat.next () ;
      
      lProjet = mSession.getProjet() ;
      int idProjet = lProjet.getId() ;
      // Récupère le projet dans la BD
      lRequete = getBaseDonnees ().getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
      lRequete.bind (idProjet) ;
      lResultat      = lRequete.execute () ;
      lProjet = (MProjet) lResultat.next () ;
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
 
    try
    {
      lListeTaches = new ArrayList() ;
      lListeTachesImprevues = new ArrayList() ;
      if (mSession.getIteration () != null)
      {
        // pour chaque tache
        for (int i = 0 ; i < mCollaborateur.getNbTaches () ; i++)
        {
          // on regarde si toutes les conditions pour que la tache soit prete sont vérifiées
          MTache lTache = mCollaborateur.getTache (i) ;
          int lIdTache = lTache.getId() ;
          // Récupère la liste des tâches du collaborateur.
          lRequete = getBaseDonnees ()
            .getOQLQuery (
                          "select TACHE from owep.modele.execution.MTache TACHE where mId = $1") ;
          lRequete.bind (lIdTache) ;
          lResultat = lRequete.execute () ;
          lTache = (MTache) lResultat.next () ;
          
          // on vérifie la tâche
          verifierTache (lTache) ;
        }
        
        // pour chaque tâche imprévue
        for (int i = 0 ; i < mCollaborateur.getNbTachesImprevues() ; i++)
        {
          // on regarde si toutes les conditions pour que la tache imprévue soit prete sont vérifiées
          MTacheImprevue lTacheImprevue = mCollaborateur.getTacheImprevue(i) ;
          int lIdTacheImprevue = lTacheImprevue.getId() ;
          // Récupère la liste des tâches du collaborateur.
          lRequete = getBaseDonnees ()
            .getOQLQuery (
                          "select TACHEIMPREVUE from owep.modele.execution.MTacheImprevue TACHEIMPREVUE where mId = $1") ;
          lRequete.bind (lIdTacheImprevue) ;
          lResultat = lRequete.execute () ;
          lTacheImprevue = (MTacheImprevue) lResultat.next () ;
          
          // Si la tache correspond à l'itération sélectionnée
          if (lTacheImprevue.getIteration ().getId () == mSession.getIteration ().getId ())
          {
            lListeTachesImprevues.add(lTacheImprevue) ;
          }
        }
        
        mCollaborateur.setListe (new Integer(0), lListeTaches) ;
        mCollaborateur.setListe (new Integer(1), lListeTachesImprevues) ;
      }
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
  }
  
  
  /**
   * Vérifie si la tâche passée en paramètre appartient bien à l'itération en cours
   * Vérifie si la tâche passée en paramètre peut passer à l'état prête
   * Ajoute la tâche à la liste des tâches à afficher si elle appartient à l'itération en cours
   * 
   * @throws ServletException -
   */
  public void verifierTache (MTache pTache) throws ServletException
  {
    int cond ; // booléen de validité de la condition
    int ltacheSansCondition ; // booléen pour savoir si on est sur une tache sans condition
    try
    {
      // initialisation du booléen de validité de la condition
      cond = 1 ;
      //par defaut on est sur une tache sans conditions
      ltacheSansCondition = 1 ;
      //Si la tache correspond à l'itération sélectionnée
      if (pTache.getIteration ().getId () == mSession.getIteration ().getId ())
      {

        // si la tache n'est pas encore prete on verifie si on ne peut pas la faire passer a
        // prete
        if (pTache.getEtat () == -1)
        {
          // pour chaque condition
          for (int j = 0 ; j < pTache.getNbConditions () ; j++)
          {
            ltacheSansCondition = 0 ; //on est sur une tache avec condition
            MCondition lCondition = pTache.getCondition (j) ;

            // si la condition est fausse
            if (lCondition.getTachePrecedente ().getEtat () < lCondition.getEtat ())
            {
              cond = 0 ;
            }
          }
        }
        if (ltacheSansCondition == 0)
        {
          // si les conditions sont vérifiées, on met l'état à prêt
          if (cond == 1)
            pTache.setEtat (0) ;
          else
            pTache.setEtat (-1) ;
        }
        lListeTaches.add(pTache) ;
      }
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
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
    java.util.ResourceBundle messages;
    messages = java.util.ResourceBundle.getBundle("MessagesBundle");
    if (lProjet.getNbIterations() == 0) 
    {
      String lMessage = messages.getString("AucuneIteration") ;
      getRequete ().setAttribute (CConstante.PAR_MESSAGE, lMessage) ;
    }
    
    try
    {
    // Transmet les données à la JSP d'affichage.
    getRequete ().setAttribute (CConstante.PAR_COLLABORATEUR, mCollaborateur) ;
    getRequete ().setAttribute (CConstante.PAR_PROJET, lProjet) ;

    //Sauvegarde de l'URL en session pour la liste de itérations
    mSession.setURLPagePrecedente ("/Tache/ListeTacheVisu") ;
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
    return "..\\JSP\\Tache\\TListeTacheVisu.jsp" ;
  }
}