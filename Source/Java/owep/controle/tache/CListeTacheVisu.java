package owep.controle.tache;


import java.util.ArrayList;
import javax.servlet.ServletException ;
import javax.servlet.http.HttpSession;
import org.exolab.castor.jdo.OQLQuery ;
import org.exolab.castor.jdo.PersistenceException ;
import org.exolab.castor.jdo.QueryResults ;

import owep.controle.CConstante;
import owep.controle.CControleurBase;
import owep.infrastructure.Session;
import owep.modele.execution.MCollaborateur ;
import owep.modele.execution.MCondition;
import owep.modele.execution.MTache;


/**
 * Controleur pour l'affichage de la liste des tâches de l'utilisateur.
 */
public class CListeTacheVisu extends CControleurBase
{
  private int mIterationNum ;             // Numéro d'itération dont on liste les tâches
  private MCollaborateur mCollaborateur ; // Collaborateur ayant ouvert la session
  private Session mSession ;              // Session associé à la connexion
  
  
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
      
//    Récupère le collaborateur connecté
      HttpSession session = getRequete ().getSession (true) ;
      mSession = (Session)session.getAttribute("SESSION") ;
      mCollaborateur = mSession.getCollaborateur() ;
      getBaseDonnees ().begin () ;
      
      int idCollab = mCollaborateur.getId() ;
      // Récupère la liste des tâches du collaborateur.
      lRequete = getBaseDonnees ().getOQLQuery ("select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mId = $1") ;
      lRequete.bind (idCollab) ;
      lResultat      = lRequete.execute () ;
      mCollaborateur = (MCollaborateur) lResultat.next () ;
      
      getBaseDonnees ().commit () ;
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
  }
  
  
  /**
   * Récupère les paramètres passés au controleur. 
   * @throws ServletException -
   * @see owep.controle.CControleurBase#initialiserParametres()
   */
  public void initialiserParametres () throws ServletException
  {
    ArrayList lListeTaches = new ArrayList () ;    // Liste des taches dont l 'état a été mis à jour
    int cond ; // booléen de validité de la condition
    int ltacheSansCondition ; // booléen pour savoir si on est sur une tache sans condition
    // initialisation de l'état de chaque tache
    // pour chaque tache
    try
    {
      for(int i = 0; i < mCollaborateur.getNbTaches();i++)
      {
        //initialisation du booléen de validité de la condition
        cond = 1 ;
        //par defaut on est sur une tache sans conditions
        ltacheSansCondition = 1 ; 
        // on regarde si toutes les conditions pour que la tache soit prete sont vérifiées
        MTache lTache = mCollaborateur.getTache(i);
        
        //Si la tache corespond à l'itération sélectionnée
        if(lTache.getIteration().getId() == mSession.getIteration().getId())
        {
        
        // si la tache n'est pas encore prete on verifie si on ne peut pas la faire passer a prete
        if (lTache.getEtat()==-1)
        {
          // pour chaque condition
          for (int j = 0; j<lTache.getNbConditions(); j++)
          {
            ltacheSansCondition = 0 ; //on est sur une tache avec condition
            MCondition lCondition = lTache.getCondition(j);
          
            // si la condition est fausse
            if (lCondition.getTachePrecedente().getEtat()<lCondition.getEtat())
            {
             cond = 0;
            }
          }
        }
        if (ltacheSansCondition == 0)
        {
          // si les conditions sont vérifiées, on met l'état à prêt
          if (cond == 1)
            lTache.setEtat(0) ;
          else lTache.setEtat(-1) ;
        }
        
        
        // Met à jour l'état de la tâche dans la base de données
        getBaseDonnees ().begin () ;
        getBaseDonnees ().update (lTache) ;
        getBaseDonnees ().commit () ; 
        
        lListeTaches.add(lTache);
        }  
      }
      mCollaborateur.setListeTaches(lListeTaches);
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
//  Ferme la connexion à la base de données.
    finally
    {
      try
      {
        getBaseDonnees ().close () ;
      }
      catch (PersistenceException eException)
      {
        eException.printStackTrace () ;
        throw new ServletException (CConstante.EXC_DECONNEXION) ;
      }
    }

    // Récupère le numéro d'itération.
    if (getRequete ().getParameter (CConstante.PAR_ITERATION) == null)
    {
      // TODO Requête recup it en cours.
    }
    else
    {
      mIterationNum = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_ITERATION)) ;
    }
    mIterationNum = 1 ;
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
    getRequete ().setAttribute (CConstante.PAR_COLLABORATEUR, mCollaborateur) ;
    getRequete ().setAttribute (CConstante.PAR_ITERATION,     new Integer (mIterationNum)) ;
    
    return "..\\JSP\\Tache\\TListeTacheVisu.jsp" ;
  }
}