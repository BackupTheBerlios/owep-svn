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

/**
 * @author Emmanuelle et Rémi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CTacheVisu extends CControleurBase{
    private MTache mTache ; // Tache à visualiser
    
    
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
        
        //récupération de l'id de la tache donc on veut afficher le détail
        int idTache = Integer.parseInt(getRequete().getParameter(("pTacheAVisualiser")));
        System.out.println(idTache);
        // Récupère la tâche à visualiser.
        lRequete = getBaseDonnees ().getOQLQuery ("select TACHE from owep.modele.execution.MTache TACHE where mId = $1") ;
        lRequete.bind (idTache) ;
        lResultat      = lRequete.execute () ;
        mTache = (MTache) lResultat.next () ;
        
        getBaseDonnees ().commit () ;
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
      getRequete ().setAttribute (CConstante.PAR_TACHE, mTache) ;
      
      getRequete ().setAttribute (CConstante.SES_SESSION, getSession()) ;
      
      return "..\\JSP\\Tache\\TTacheVisu.jsp" ;
    }
}

