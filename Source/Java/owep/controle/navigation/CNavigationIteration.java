/*
 * Created on 25 janv. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package owep.controle.navigation;

import java.util.ArrayList;

import javax.servlet.ServletException;
import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.jdo.QueryResults;

import owep.controle.CConstante;
import owep.controle.CControleurBase;
import owep.modele.execution.MIteration;
import owep.modele.execution.MProjet;

/**
 * @author Victor Nancy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CNavigationIteration extends CControleurBase
{   
  	private ArrayList lListeIteration;  
  
    /**
     * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es. 
     * @throws ServletException Si une erreur survient durant la connexion
     * @see owep.controle.CControleurBase#initialiserBaseDonnees()
     */
    public void initialiserBaseDonnees () throws ServletException
    {
      OQLQuery       lRequete ;       // Requ�te � r�aliser sur la base
      QueryResults   lResultat ;      // R�sultat de la requ�te sur la base
      
      int idIteration = Integer.parseInt(getRequete().getParameter(CConstante.PAR_ITERATION));
      try
      {
        getBaseDonnees ().begin () ;

        lRequete = getBaseDonnees ()
          .getOQLQuery ("select ITERATION from owep.modele.execution.MIteration ITERATION where mId = $1") ;
        lRequete.bind (idIteration) ;
        lResultat = lRequete.execute () ;

        MIteration lIteration = (MIteration) lResultat.next () ;

        lRequete = getBaseDonnees ()
        .getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
        lRequete.bind (getSession().getProjet().getId()) ;
        lResultat = lRequete.execute () ;

        MProjet lProjet = (MProjet) lResultat.next () ;
        lListeIteration = lProjet.getListeIterations() ;
        
        getBaseDonnees ().commit () ;

        // Enregistre l'it�ration � ouvrir dans la session
        getSession ().setIteration(lIteration) ;
      }
      catch (PersistenceException e1)
      {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      // Ferme la connexion � la base de donn�es.
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
     * R�cup�re les param�tres pass�s au controleur. 
     * @throws ServletException -
     * @see owep.controle.CControleurBase#initialiserParametres()
     */
    public void initialiserParametres () throws ServletException
    {
      
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
      getRequete ().setAttribute (CConstante.PAR_LISTEITERATIONS, lListeIteration) ;
      return getSession().getURLPagePrecedente();
    }
}
