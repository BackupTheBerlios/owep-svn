package owep.controle.installation;


import java.io.File ;

import javax.servlet.ServletException ;

import org.exolab.castor.jdo.Database ;
import org.exolab.castor.jdo.JDO ;
import org.exolab.castor.jdo.PersistenceException ;

import owep.infrastructure.localisation.LocalisateurIdentifiant ;
import owep.modele.configuration.MConfigurationSite ;


/**
 * Cr�ation de la configuration du site.
 */
public class CSite extends CControleurBaseInstallation
{
  private String   mLangue ;     // Langue choisi pour le site
  private String   mApparence ;  // Apparence choisi pour le site
  private Database mBaseDonnees ;
  private String mErreur ;


  /**
   * Enregistre la configuration du site.
   * 
   * @see owep.controle.installation.CControleurBaseInstallation#traiter()
   */
  public String traiter () throws ServletException
  {
    JDO lJdo ; // Charge le syst�me de persistence avec la base de donn�es
    mErreur = "";

    // Initie la connexion � la base de donn�es avec Castor.
    try
    {
      JDO.loadConfiguration (getServletContext ().getRealPath ("/")
                             + LocalisateurIdentifiant.LID_BDCONFIGURATION) ;
      lJdo = new JDO (LocalisateurIdentifiant.LID_BDNOM) ;

      mBaseDonnees = lJdo.getDatabase () ;
      mBaseDonnees.setAutoStore (false) ;
    }
    catch (Exception eException)
    {
      mErreur = "Une erreur s'est produite lors de la connexion � la base de donn�e.";
      eException.printStackTrace () ;
    }

    mLangue = getRequete ().getParameter ("mLangue") ;
    mApparence = getRequete ().getParameter ("mApparence") ;

    try
    {
      mBaseDonnees.begin () ;

      MConfigurationSite conf = new MConfigurationSite () ;
      conf.setLangue (mLangue) ;
      conf.setApparence (mApparence) ;
      conf.setPathArtefact ("/Artefacts/") ;
      conf.setPathExport ("/owep/Processus/Export/") ;
      mBaseDonnees.create (conf) ;

      mBaseDonnees.commit () ;

      //On r�cup�re le nouveau bundle et la nouvelle css
      File nouveauBundel = new File (getServletContext ().getRealPath ("/") + "/" + "Ressources/",
                                     "MessagesBundle_" + mLangue + ".properties") ;
      File ancienBundel = new File (getServletContext ().getRealPath ("/") + "/"
                                    + "WEB-INF/classes/", "MessagesBundle.properties") ;
      copier (nouveauBundel, ancienBundel) ;

      File nouvelleCSS = new File (getServletContext ().getRealPath ("/") + "/" + "CSS/",
                                   mApparence) ;
      File ancienneCSS = new File (getServletContext ().getRealPath ("/") + "/" + "CSS/",
                                   "Apparence.css") ;
      copier (nouvelleCSS, ancienneCSS) ;
    }
    catch (PersistenceException e)
    {
      mErreur = "Une erreur s'est produite lors de la connexion � la base de donn�e.";
      e.printStackTrace () ;
    }
    finally
    {
      try
      {
        mBaseDonnees.close () ;
      }
      catch (PersistenceException e1)
      {
        mErreur = "Une erreur s'est produite lors de la connexion � la base de donn�e.";
        e1.printStackTrace () ;
      }
    }

    if(!mErreur.equals("")){
      getRequete().setAttribute("mErreur",mErreur);
      return "..\\JSP\\Installation\\TConfigurationSite.jsp" ;
    }
    
    return "..\\JSP\\Installation\\TFinInstallation.jsp" ;
  }

  /**
   * M�thode permettant de copier le fichier source dans le fichier destination
   * 
   * @param source
   * @param destination
   * @return boolean
   */
  private boolean copier (File source, File destination)
  {
    boolean resultat = false ;

    // Declaration des flux
    java.io.FileInputStream sourceFile = null ;
    java.io.FileOutputStream destinationFile = null ;

    try
    {

      // Ouverture des flux
      sourceFile = new java.io.FileInputStream (source) ;
      destinationFile = new java.io.FileOutputStream (destination) ;

      // Lecture par segment de 0.5Mo
      byte buffer[] = new byte [512 * 1024] ;
      int nbLecture ;

      while ((nbLecture = sourceFile.read (buffer)) != -1)
      {
        destinationFile.write (buffer, 0, nbLecture) ;
      }

      // Copie r�ussie
      resultat = true ;
    }
    catch (java.io.FileNotFoundException f)
    {
      mErreur = "Une erreur s'est produite lors de la connexion � la base de donn�e.";
    }
    catch (java.io.IOException e)
    {
      mErreur = "Une erreur s'est produite lors de la connexion � la base de donn�e.";
    }
    finally
    {
      // Quoi qu'il arrive, on ferme les flux
      try
      {
        sourceFile.close () ;
      }
      catch (Exception e)
      {
        mErreur = "Une erreur s'est produite lors de la connexion � la base de donn�e.";
      }
      try
      {
        destinationFile.close () ;
      }
      catch (Exception e)
      {
        mErreur = "Une erreur s'est produite lors de la connexion � la base de donn�e.";
      }
    }
    return (resultat) ;
  }
}