package owep.controle.iteration;

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
 * Controleur pour la mise de la liste des indicateurs pour l'iteration en cours
 */
public class CValidationIndicateurs extends CControleurBase
{
  private MProjet mProjet ; // Projet ayant �t� ouvert dans la session
  private MIteration mIteration ;  // Iteration en cours
  private Session mSession ; // Session associ� � la connexion

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
      // R�cup�re le projet ouvert dans la session
      HttpSession session = getRequete ().getSession (true) ;
      mSession = (Session) session.getAttribute ("SESSION") ;
      mProjet = mSession.getProjet() ;
      getBaseDonnees ().begin () ;

      int lIdProjet = mProjet.getId () ;
      // R�cup�re le projet dans la base de donn�es
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
   * R�cup�re les param�tres pass�s au controleur.
   * 
   * @throws ServletException -
   * @see owep.controle.CControleurBase#initialiserParametres()
   */
  public void initialiserParametres () throws ServletException
  {
    OQLQuery lRequete ; // Requ�te � r�aliser sur la base
    QueryResults lResultat ; // R�sultat de la requ�te sur la base
    MMesureIndicateur lMesureIndicateur ; // mesure en cours de mise � jour dans la base de donn�es
    
    try
    {
      // pour chaque indicateur
      for (int i = 0; i < mProjet.getNbIndicateurs(); i ++)
      {
        // r�cup�ration des identifiants de l indicateur, du collaborateur et de l it�ration
        MIndicateur lIndicateur = (MIndicateur)mProjet.getIndicateur(i) ;
        int lIdIndicateur = lIndicateur.getId() ;
        
        MCollaborateur lCollaborateur = mSession.getCollaborateur () ;
        int lIdCollab = lCollaborateur.getId () ;
        
        // recherche de l iteration en cours
        for (int j = 0; j < mProjet.getNbIterations() ; j++)
        {
          if (mProjet.getIteration(j).getEtat()==1)
            mIteration = mProjet.getIteration(j);
        }
        int lIdIteration = mIteration.getId() ;
          
        // R�cup�re la mesure dans la base de donn�es
        lRequete = getBaseDonnees ()
          .getOQLQuery ("select MESUREINDICATEUR from owep.modele.execution.MMesureIndicateur MESUREINDICATEUR where mIndicateur = $1 and mCollaborateur = $2 and mIteration = $3") ;
        lRequete.bind (lIdIndicateur) ;
        lRequete.bind (lIdCollab) ;
        lRequete.bind (lIdIteration) ;
        lResultat = lRequete.execute () ;
  
        lMesureIndicateur = (MMesureIndicateur) lResultat.next () ;
        
        // r�cup�ration des donn�es du formulaire
        double lValeur ;
        String lTypeIndicateur = (String)(getRequete().getParameter(CConstante.PAR_TYPEINDICATEUR+i)) ; 
        if (lTypeIndicateur.equals("valeur"))
        {
          // r�cup�ration du champ valeur
          lValeur = Double.parseDouble(getRequete().getParameter(CConstante.PAR_VALEURMESURE+i));
        }
        else
        {
          lValeur = 0.0 ; 
        }
        lMesureIndicateur.setValeur(lValeur) ;
        
        // r�cup�ration du champ commentaire
        String lCommentaire = (String)(getRequete().getParameter(CConstante.PAR_COMMENTAIREMESURE+i));
        lMesureIndicateur.setCommentaire(lCommentaire) ;
      }
    }
    catch (PersistenceException e)
    {
      // TODO Ecrire le bloc try-catch.
      e.printStackTrace () ;
    }
    // Ferme la connexion � la base de donn�es.
    finally
    {
      try
      {
        getBaseDonnees ().commit() ;
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
    // Transmet les donn�es � la JSP d'affichage.
    String lMessage = messages.getString("cValidationIndicateursOK") ;
    getRequete ().setAttribute (CConstante.PAR_MESSAGE, lMessage) ;

    return "/Tache/ListeTacheVisu" ;
  }
}
