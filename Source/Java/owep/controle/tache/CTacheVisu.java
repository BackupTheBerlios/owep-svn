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
 * @author Emmanuelle et R�mi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CTacheVisu extends CControleurBase{
    private MTache mTache ; // Tache � visualiser
    private MTacheImprevue mTacheImprevue ; // Tache Impr�vue � visualiser
    
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
        getBaseDonnees ().begin () ;
        
        if(getRequete().getParameter(("pTacheAVisualiser")) != null)
        {
          //r�cup�ration de l'id de la tache donc on veut afficher le d�tail
          int idTache = Integer.parseInt(getRequete().getParameter(("pTacheAVisualiser")));
          // R�cup�re la t�che � visualiser.
          lRequete = getBaseDonnees ().getOQLQuery ("select TACHE from owep.modele.execution.MTache TACHE where mId = $1") ;
          lRequete.bind (idTache) ;
          lResultat      = lRequete.execute () ;
          mTache = (MTache) lResultat.next () ;
        }
        //Tache impr�vue
        else
        {
          //r�cup�ration de l'id de la tache donc on veut afficher le d�tail
          int idTache = Integer.parseInt(getRequete().getParameter(("pTacheImprevueAVisualiser")));
          // R�cup�re la t�che � visualiser.
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
     * Effectue tout le tra�tement du controleur puis retourne l'URL vers laquelle le client doit
     * �tre redirig�. 
     * @return URL de la page vers laquelle doit �tre redirig� le client.
     * @throws ServletException Si une erreur survient dans le controleur
     */
    public String traiter () throws ServletException
    {
      // Transmet les donn�es � la JSP d'affichage.
      if(getRequete().getParameter(("pTacheAVisualiser")) != null)
      {
        getRequete ().setAttribute (CConstante.PAR_TACHE, mTache) ;
        getRequete ().setAttribute (CConstante.PAR_TACHE_IMPREVUE, null) ;
      }
      //Tache impr�vue
      else
      {
        getRequete ().setAttribute (CConstante.PAR_TACHE, null) ;
        getRequete ().setAttribute (CConstante.PAR_TACHE_IMPREVUE, mTacheImprevue) ;        
      }
      getRequete ().setAttribute (CConstante.SES_SESSION, getSession()) ;
      
      return "..\\JSP\\Tache\\TTacheVisu.jsp" ;
    }
}

