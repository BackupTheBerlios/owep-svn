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
 * Controleur pour l'affichage de la liste des t�ches de l'utilisateur.
 */
public class CListeTacheVisu extends CControleurBase
{
  private int mIterationNum ; // Num�ro d'it�ration dont on liste les t�ches
  private MCollaborateur mCollaborateur ; // Collaborateur ayant ouvert la session
  private Session mSession ; // Session associ� � la connexion
  private ArrayList lListeTaches ; // liste des t�ches � afficher
  private ArrayList lListeTachesImprevues ; // liste des t�ches impr�vues � afficher
  private MProjet lProjet ; // projet pour lequel est connect� le collaborateur


  /**
   * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es.
   * 
   * @throws ServletException Si une erreur survient durant la connexion
   * @see owep.controle.CControleurBase#initialiserBaseDonnees()
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    OQLQuery lRequete ; // Requ�te � r�aliser sur la base
    QueryResults lResultat ; // R�sultat de la requ�te sur la base

    try
    {

      //    R�cup�re le collaborateur connect�
      HttpSession session = getRequete ().getSession (true) ;
      mSession = (Session) session.getAttribute ("SESSION") ;
      mCollaborateur = mSession.getCollaborateur () ;
      getBaseDonnees ().begin () ;

      int idCollab = mCollaborateur.getId () ;
      // R�cup�re la liste des t�ches du collaborateur.
      lRequete = getBaseDonnees ()
        .getOQLQuery (
                      "select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mId = $1") ;
      lRequete.bind (idCollab) ;
      lResultat = lRequete.execute () ;
      mCollaborateur = (MCollaborateur) lResultat.next () ;
      
      lProjet = mSession.getProjet() ;
      int idProjet = lProjet.getId() ;
      // R�cup�re le projet dans la BD
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
   * R�cup�re les param�tres pass�s au controleur.
   * 
   * @throws ServletException -
   * @see owep.controle.CControleurBase#initialiserParametres()
   */
  public void initialiserParametres () throws ServletException
  {
    OQLQuery lRequete ; // Requ�te � r�aliser sur la base
    QueryResults lResultat ; // R�sultat de la requ�te sur la base
 
    try
    {
      lListeTaches = new ArrayList() ;
      lListeTachesImprevues = new ArrayList() ;
      if (mSession.getIteration () != null)
      {
        // pour chaque tache
        for (int i = 0 ; i < mCollaborateur.getNbTaches () ; i++)
        {
          // on regarde si toutes les conditions pour que la tache soit prete sont v�rifi�es
          MTache lTache = mCollaborateur.getTache (i) ;
          int lIdTache = lTache.getId() ;
          // R�cup�re la liste des t�ches du collaborateur.
          lRequete = getBaseDonnees ()
            .getOQLQuery (
                          "select TACHE from owep.modele.execution.MTache TACHE where mId = $1") ;
          lRequete.bind (lIdTache) ;
          lResultat = lRequete.execute () ;
          lTache = (MTache) lResultat.next () ;
          
          // on v�rifie la t�che
          verifierTache (lTache) ;
        }
        
        // pour chaque t�che impr�vue
        for (int i = 0 ; i < mCollaborateur.getNbTachesImprevues() ; i++)
        {
          // on regarde si toutes les conditions pour que la tache impr�vue soit prete sont v�rifi�es
          MTacheImprevue lTacheImprevue = mCollaborateur.getTacheImprevue(i) ;
          int lIdTacheImprevue = lTacheImprevue.getId() ;
          // R�cup�re la liste des t�ches du collaborateur.
          lRequete = getBaseDonnees ()
            .getOQLQuery (
                          "select TACHEIMPREVUE from owep.modele.execution.MTacheImprevue TACHEIMPREVUE where mId = $1") ;
          lRequete.bind (lIdTacheImprevue) ;
          lResultat = lRequete.execute () ;
          lTacheImprevue = (MTacheImprevue) lResultat.next () ;
          
          // Si la tache correspond � l'it�ration s�lectionn�e
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
   * V�rifie si la t�che pass�e en param�tre appartient bien � l'it�ration en cours
   * V�rifie si la t�che pass�e en param�tre peut passer � l'�tat pr�te
   * Ajoute la t�che � la liste des t�ches � afficher si elle appartient � l'it�ration en cours
   * 
   * @throws ServletException -
   */
  public void verifierTache (MTache pTache) throws ServletException
  {
    int cond ; // bool�en de validit� de la condition
    int ltacheSansCondition ; // bool�en pour savoir si on est sur une tache sans condition
    try
    {
      // initialisation du bool�en de validit� de la condition
      cond = 1 ;
      //par defaut on est sur une tache sans conditions
      ltacheSansCondition = 1 ;
      //Si la tache correspond � l'it�ration s�lectionn�e
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
          // si les conditions sont v�rifi�es, on met l'�tat � pr�t
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
   * R�cup�re la liste des t�ches d'un collaborateur pour l'it�ration choisie, et la transmet � la
   * JSP.
   * 
   * @return URL de la page vers laquelle doit �tre redirig� le client.
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
    // Transmet les donn�es � la JSP d'affichage.
    getRequete ().setAttribute (CConstante.PAR_COLLABORATEUR, mCollaborateur) ;
    getRequete ().setAttribute (CConstante.PAR_PROJET, lProjet) ;

    //Sauvegarde de l'URL en session pour la liste de it�rations
    mSession.setURLPagePrecedente ("/Tache/ListeTacheVisu") ;
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