/*
 * Created on 5 janv. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package owep.controle.tache;

import javax.servlet.ServletException;

import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.jdo.QueryResults;

import owep.controle.CConstante;
import owep.controle.CControleurBase;
import owep.modele.execution.MTache;
import owep.modele.execution.MTacheImprevue;

/**
 * @author Emmanuelle et Rémi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CTacheVisu extends CControleurBase{
    private MTache mTache ; // Tache à visualiser
    private MTacheImprevue mTacheImprevue ; // Tache Imprévue à visualiser
    
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
        getBaseDonnees ().begin () ;
        
        if(getRequete().getParameter(("pTacheAVisualiser")) != null)
        {
          //récupération de l'id de la tache donc on veut afficher le détail
          int idTache = Integer.parseInt(getRequete().getParameter(("pTacheAVisualiser")));
          // Récupère la tâche à visualiser.
          lRequete = getBaseDonnees ().getOQLQuery ("select TACHE from owep.modele.execution.MTache TACHE where mId = $1") ;
          lRequete.bind (idTache) ;
          lResultat      = lRequete.execute () ;
          mTache = (MTache) lResultat.next () ;
        }
        //Tache imprévue
        else
        {
          //récupération de l'id de la tache donc on veut afficher le détail
          int idTache = Integer.parseInt(getRequete().getParameter(("pTacheImprevueAVisualiser")));
          // Récupère la tâche à visualiser.
          lRequete = getBaseDonnees ().getOQLQuery ("select TACHEIMPREVUE from owep.modele.execution.MTacheImprevue TACHEIMPREVUE where mId = $1") ;
          lRequete.bind (idTache) ;
          lResultat      = lRequete.execute () ;
          mTacheImprevue = (MTacheImprevue) lResultat.next () ;
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
     * Récupère les paramètres passés au controleur. 
     * @throws ServletException -
     * @see owep.controle.CControleurBase#initialiserParametres()
     */
    public void initialiserParametres () throws ServletException
    {
    }
    
    
    /**
     * Effectue tout le traîtement du controleur puis retourne l'URL vers laquelle le client doit
     * être redirigé. 
     * @return URL de la page vers laquelle doit être redirigé le client.
     * @throws ServletException Si une erreur survient dans le controleur
     */
    public String traiter () throws ServletException
    {
      // Transmet les données à la JSP d'affichage.
      if(getRequete().getParameter(("pTacheAVisualiser")) != null)
      {
        getRequete ().setAttribute (CConstante.PAR_TACHE, mTache) ;
        getRequete ().setAttribute (CConstante.PAR_TACHE_IMPREVUE, null) ;
      }
      //Tache imprévue
      else
      {
        getRequete ().setAttribute (CConstante.PAR_TACHE, null) ;
        getRequete ().setAttribute (CConstante.PAR_TACHE_IMPREVUE, mTacheImprevue) ;        
      }
      getRequete ().setAttribute (CConstante.SES_SESSION, getSession()) ;
      
      return "..\\JSP\\Tache\\TTacheVisu.jsp" ;
    }
}

