/*
 * Created on 25 janv. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package owep.controle.navigation;

import java.util.StringTokenizer;

import javax.servlet.ServletException;
import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.jdo.QueryResults;

import owep.controle.CConstante;
import owep.controle.CControleurBase;
import owep.modele.execution.MIteration;

/**
 * @author Victor Nancy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CNavigationIteration extends CControleurBase
{   
    
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
      System.out.println (idIteration ) ;
      try
      {
        getBaseDonnees ().begin () ;

        lRequete = getBaseDonnees ()
          .getOQLQuery ("select ITERATION from owep.modele.execution.MIteration ITERATION where mId = $1") ;
        lRequete.bind (idIteration) ;
        lResultat = lRequete.execute () ;

        MIteration lIteration = (MIteration) lResultat.next () ;

        getBaseDonnees ().commit () ;

        // Enregistre l'it�ration � ouvrir dans la session
        getSession ().setIteration(lIteration) ;
      }
      catch (PersistenceException e1)
      {
        // TODO Auto-generated catch block
        e1.printStackTrace();
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
      // Transmet les donn�es � la JSP d'affichage.
      
      /*//Recherche de l'url d'origine pour retourner � la bonne page
      String pageOrigine="/"; //url recherch�e 
      boolean debutPageOrigine=false; //qd true alors on lit les composant de l'url
      int i=0;
      
      //On r�cup�re l'url dans la requete; chaque �l�ment de l'url est s�par� par %2F
      StringTokenizer st = new StringTokenizer(getRequete().getParameter("url"),"%2F");// s�parateur %2F
     
      //On parcourt les �l�ments de l'url et on les r�cup�re � partir d'owep.
      while (st.hasMoreTokens()){  //boucle de lecture
        
        if(debutPageOrigine)
          pageOrigine += "/" + st.nextToken();
        
        if(st.nextToken() == "owep")
          debutPageOrigine=true;
        
        i++;
      }
    return pageOrigine;  */
      return "/Tache/ListeTacheVisu";
    }

  
}
