package owep.controle.installation ;


import javax.servlet.ServletException ;

import org.exolab.castor.jdo.Database ;
import org.exolab.castor.jdo.JDO ;
import org.exolab.castor.jdo.PersistenceException ;

import owep.infrastructure.localisation.LocalisateurIdentifiant ;
import owep.modele.execution.MCollaborateur ;


/**
 * Cr�er un utilisateur superviseur;
 */
public class CSuperviseur extends CControleurBaseInstallation
{

  private Database mBaseDonnees ; // Connexion � la base de donn�es

  private String   mNom ;        // Nom du collaborateur � cr�er
  private String   mPrenom ;     // Prenom du collaborateur � cr�er
  private String   mLogin ;      // Login du collaborateur � cr�er
  private String   mMail ;       // Email du collaborateur � cr�er
  private String   mAdresse ;    // Adresse du collaborateur � cr�er
  private String   mTelephone ;  // Num�ro du telephone du collaborateur � cr�er
  private String   mPortable ;   // Num�ro du portable du collaborateur � cr�er
  private String   mCommentaire ; // Commentaire du collaborateur � cr�er
  private String   mMdp ;        // Mot de passe du collaborateur � cr�er
  private int      mDroit ;      //Droit du collaboratur (superviseur = 1)


  /**
   * R�cup�re les param�tres pass�s au controleur.
   */
  public void initialiserParametres ()
  {
    mNom = getRequete ().getParameter ("mNom") ;
    mPrenom = getRequete ().getParameter ("mPrenom") ;
    mLogin = getRequete ().getParameter ("mLogin") ;
    mMail = getRequete ().getParameter ("mMail") ;
    mAdresse = getRequete ().getParameter ("mAdresse") ;
    mTelephone = getRequete ().getParameter ("mTelephone") ;
    mPortable = getRequete ().getParameter ("mPortable") ;
    mCommentaire = getRequete ().getParameter ("mCommentaire") ;
    mDroit = 1 ;
    mMdp = MCollaborateur.encode (mLogin) ;
  }

  /**
   * Enregistre le collaborateur en temps que superviseur.
   * 
   * @see owep.controle.installation.CControleurBaseInstallation#traiter()
   */
  public String traiter () throws ServletException
  {
    JDO lJdo ; // Charge le syst�me de persistence avec la base de donn�es
    String mErreur = "";

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

    initialiserParametres () ;

    try
    {
      mBaseDonnees.begin () ;

      MCollaborateur lCollaborateur = new MCollaborateur (mPrenom, mNom, mAdresse, mTelephone,
                                                          mPortable, mMail, mCommentaire, mLogin,
                                                          mMdp, mDroit) ;
      mBaseDonnees.create(lCollaborateur);

      mBaseDonnees.commit () ;
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
      return "..\\JSP\\Installation\\TSuperviseur.jsp" ;
    }
    
    return "..\\JSP\\Installation\\TConfigurationSite.jsp" ;
  }

}