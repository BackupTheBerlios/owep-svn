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
 * Controleur pour l'affichage de la liste des t�ches de l'utilisateur.
 */
public class CListeTacheVisu extends CControleurBase
{
  private int mIterationNum ;             // Num�ro d'it�ration dont on liste les t�ches
  private MCollaborateur mCollaborateur ; // Collaborateur ayant ouvert la session
  private Session mSession ;              // Session associ� � la connexion
  
  
  /**
   * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es. 
   * @throws ServletException Si une erreur survient durant la connexion
   * @see owep.controle.CControleurBase#initialiserBaseDonnees()
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    OQLQuery       lRequete ;       // Requ�te � r�aliser sur la base
    QueryResults   lResultat ;      // R�sultat de la requ�te sur la base
    
    try
    {
      
//    R�cup�re le collaborateur connect�
      HttpSession session = getRequete ().getSession (true) ;
      mSession = (Session)session.getAttribute("SESSION") ;
      mCollaborateur = mSession.getCollaborateur() ;
      getBaseDonnees ().begin () ;
      
      int idCollab = mCollaborateur.getId() ;
      // R�cup�re la liste des t�ches du collaborateur.
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
   * R�cup�re les param�tres pass�s au controleur. 
   * @throws ServletException -
   * @see owep.controle.CControleurBase#initialiserParametres()
   */
  public void initialiserParametres () throws ServletException
  {
    ArrayList lListeTaches = new ArrayList () ;    // Liste des taches dont l '�tat a �t� mis � jour
    int cond ; // bool�en de validit� de la condition
    int ltacheSansCondition ; // bool�en pour savoir si on est sur une tache sans condition
    // initialisation de l'�tat de chaque tache
    // pour chaque tache
    try
    {
      for(int i = 0; i < mCollaborateur.getNbTaches();i++)
      {
        //initialisation du bool�en de validit� de la condition
        cond = 1 ;
        //par defaut on est sur une tache sans conditions
        ltacheSansCondition = 1 ; 
        // on regarde si toutes les conditions pour que la tache soit prete sont v�rifi�es
        MTache lTache = mCollaborateur.getTache(i);
        
        //Si la tache corespond � l'it�ration s�lectionn�e
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
          // si les conditions sont v�rifi�es, on met l'�tat � pr�t
          if (cond == 1)
            lTache.setEtat(0) ;
          else lTache.setEtat(-1) ;
        }
        
        
        // Met � jour l'�tat de la t�che dans la base de donn�es
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
//  Ferme la connexion � la base de donn�es.
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

    // R�cup�re le num�ro d'it�ration.
    if (getRequete ().getParameter (CConstante.PAR_ITERATION) == null)
    {
      // TODO Requ�te recup it en cours.
    }
    else
    {
      mIterationNum = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_ITERATION)) ;
    }
    mIterationNum = 1 ;
  }
  
  
  /**
   * R�cup�re la liste des t�ches d'un collaborateur pour l'it�ration choisie, et la transmet � la
   * JSP. 
   * @return URL de la page vers laquelle doit �tre redirig� le client.
   * @throws ServletException Si une erreur survient dans le controleur
   * @see owep.controle.CControleurBase#traiter()
   */
  public String traiter () throws ServletException
  {
    // Transmet les donn�es � la JSP d'affichage.
    getRequete ().setAttribute (CConstante.PAR_COLLABORATEUR, mCollaborateur) ;
    getRequete ().setAttribute (CConstante.PAR_ITERATION,     new Integer (mIterationNum)) ;
    
    return "..\\JSP\\Tache\\TListeTacheVisu.jsp" ;
  }
}