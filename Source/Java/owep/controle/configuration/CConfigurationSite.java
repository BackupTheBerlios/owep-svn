/*
 * Created on 23 févr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package owep.controle.configuration;

import java.io.File;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.jdo.QueryResults;
import org.exolab.castor.jdo.TransactionAbortedException;
import org.exolab.castor.jdo.TransactionNotInProgressException;
import org.omg.CORBA.Request;

import owep.controle.CConstante;
import owep.controle.CControleurBase;
import owep.infrastructure.Session;
import owep.modele.configuration.MConfigurationSite;

/**
 * @author Victor Nancy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CConfigurationSite extends CControleurBase
{   
  private MConfigurationSite mConfigurationSite ; // Données de la configuration du site
  private String mLangue ;                    // Langue choisie pour le site
  private String mApparence ;                 // Apparence choisie pour le site
  
    /**
     * Récupère les données nécessaire au controleur dans la base de données. 
     * @throws ServletException Si une erreur survient durant la connexion
     * @see owep.controle.CControleurBase#initialiserBaseDonnees()
     */
    public void initialiserBaseDonnees () throws ServletException
    {
      OQLQuery       lRequete ;       // Requête à réaliser sur la base
      QueryResults   lResultat ;      // Résultat de la requête sur la base
       
      try {
      getBaseDonnees ().begin () ;
      // Récupère l'artefact concerné par l'upload.
      lRequete = getBaseDonnees ().getOQLQuery ("select CONFIGURATION from owep.modele.configuration.MConfigurationSite CONFIGURATION where mId = $1") ;
      lRequete.bind (1) ;
      lResultat = lRequete.execute () ;
      mConfigurationSite = (MConfigurationSite) lResultat.next () ;    
      

      }
      catch (Exception eException)
      {
        eException.printStackTrace () ;
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
    }
    
    
    /**
     * Initialise le controleur et récupère les paramètres. 
     * @throws ServletException Si une erreur sur les paramètres survient
     * @see owep.controle.CControleurBase#initialiserParametres()
     */
    public void initialiserParametres () throws ServletException
    { 
      // Si l'utilisateur valide les données, récupère les données.
      if(getRequete ().getParameter (CConstante.PAR_LANGUE)!=null){
        mLangue = getRequete ().getParameter (CConstante.PAR_LANGUE) ;
        mApparence = getRequete ().getParameter (CConstante.PAR_APPARENCE) ;
      }  
    }
    
    /**
     * Redirige vers la JSP d'affichage du rapport d'activité si aucune donnée n'est postée ou valide
     * les données dans la base sinon. 
     * @return URL de la page vers laquelle doit être redirigé le client.
     * @throws ServletException Si une erreur survient dans le controleur
     * @see owep.controle.CControleurBase#traiter()
     */
    public String traiter () throws ServletException
    {
      // Si l'utilisateur valide les données, récupère les données.
      if(mLangue!=null)
      {

          mConfigurationSite.setLangue(mLangue);
          mConfigurationSite.setApparence(mApparence);  
          
          //On récupère le nouveau bundle et la nouvelle css
          File nouveauBundel = new File(getServletContext().getRealPath("/")+"/"+"Ressources/", "MessagesBundle_"+mLangue+".properties");
          File ancienBundel = new File(getServletContext().getRealPath("/")+"/"+"WEB-INF/classes/", "MessagesBundle.properties");
          copier(nouveauBundel, ancienBundel);
          ancienBundel = new File(getServletContext().getRealPath("/")+"/"+"Java/", "MessagesBundle.properties");
          copier(nouveauBundel, ancienBundel);
          
          File nouvelleCSS = new File(getServletContext().getRealPath("/")+"/"+"CSS/", mApparence);
          File ancienneCSS = new File(getServletContext().getRealPath("/")+"/"+"CSS/", "Apparence.css");
          copier(nouvelleCSS, ancienneCSS);
          
          //message de confirmation
          ResourceBundle messages = java.util.ResourceBundle.getBundle("MessagesBundle");
          String lMessage = messages.getString("configurationSiteMessageConfirmation");
          getRequete ().setAttribute (CConstante.PAR_MESSAGE, lMessage) ;
      }
      
      try
      {
        getBaseDonnees().commit();
        getBaseDonnees ().close () ;
      }
      catch (TransactionNotInProgressException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      catch (TransactionAbortedException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      catch (PersistenceException e1)
      {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      
      //Affiche le formulaire de configuration du site.
      getRequete ().setAttribute (CConstante.PAR_CONFIGURATION, mConfigurationSite) ;
      getRequete ().setAttribute (CConstante.SES_SESSION, getSession()) ;
          
      return "..\\JSP\\Configuration\\TConfigurationSite.jsp"  ;
    }
    
    /**
     * Méthode permettant de copier le fichier source dans le fichier destination
     * @param source
     * @param destination
     * @return
     * boolean
     */
    public static boolean copier( File source, File destination )
    {
            boolean resultat = false;
            
            // Declaration des flux
            java.io.FileInputStream sourceFile=null;
            java.io.FileOutputStream destinationFile=null;
            
            try {
                    
                    // Ouverture des flux
                    sourceFile = new java.io.FileInputStream(source);
                    destinationFile = new java.io.FileOutputStream(destination);
                    
                    // Lecture par segment de 0.5Mo 
                    byte buffer[]=new byte[512*1024];
                    int nbLecture;
                    
                    while( (nbLecture = sourceFile.read(buffer)) != -1 ) {
                            destinationFile.write(buffer, 0, nbLecture);
                    } 
                    
                    // Copie réussie
                    resultat = true;
            } catch( java.io.FileNotFoundException f ) {
                    
            } catch( java.io.IOException e ) {
                    
            } finally {
                    // Quoi qu'il arrive, on ferme les flux
                    try {
                            sourceFile.close();
                    } catch(Exception e) { }
                    try {
                            destinationFile.close();
                    } catch(Exception e) { }
            } 
            return( resultat );
    }
    
  }

