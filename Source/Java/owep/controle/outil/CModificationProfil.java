package owep.controle.outil ;


import javax.servlet.ServletException ;

import org.exolab.castor.jdo.OQLQuery ;
import org.exolab.castor.jdo.PersistenceException ;
import org.exolab.castor.jdo.QueryResults ;

import owep.controle.CControleurBase ;
import owep.modele.execution.MCollaborateur ;


/**
 * Controleur permettant de modifier le profil du collaborateur connecté.
 */
public class CModificationProfil extends CControleurBase
{
  private String mNom ;            // Nom du collaborateur
  private String mPrenom ;         // Prenom du collaborateur
  private String mLogin ;          // Login du collaborateur
  private String mMail ;           // Mail du collaborateur
  private String mAdresse ;        // Adresse du collaborateur
  private String mTelephone ;      // Telephone du collaborateur
  private String mPortable ;       // Portable du collaborateur
  private String mCommentaire ;    // COmmentaire sur le collaborateur
  private String mAncienMdp ;      // Ancien mot de passe du collaborateur
  private String mNouveauMdp ;     // Nouveau mot de passe du collaborateur
  private String mConfirmationMdp ; // Confirmation du mot de pass du collaborateur

  private String mModifLogin ;     // 0 si le login n'a pas été modifié, sinon 1
  private String mModifMdp ;       // 0 si le mdp n'a pas ete changé, 1 sinon
  private String mErreur ;         // Message à destination du collaborateur

  MCollaborateur lCollaborateur = null;//getSession ().getCollaborateur () ;

  /**
   * Initialise les variables en fonction de la base de données.
   * 
   * @see owep.controle.CControleurBase#initialiserBaseDonnees()
   */
  public void initialiserBaseDonnees () throws ServletException
  {
  }

  /**
   * Récupére les parametres de la requete et initialise les variables.
   * 
   * @see owep.controle.CControleurBase#initialiserParametres()
   */
  public void initialiserParametres () throws ServletException
  {
    mNom = getRequete ().getParameter ("mNom") ;
    mPrenom = getRequete ().getParameter ("mPrenom") ;
    mLogin = getRequete ().getParameter ("mLogin") ;
    mMail = getRequete ().getParameter ("mMail") ;
    mAdresse = getRequete ().getParameter ("mAdresse") ;
    mTelephone = getRequete ().getParameter ("mTelephone") ;
    mPortable = getRequete ().getParameter ("mPortable") ;
    mCommentaire = getRequete ().getParameter ("mCommentaire") ;

    mAncienMdp = getRequete ().getParameter ("mAncienMdp") ;
    mNouveauMdp = getRequete ().getParameter ("mNouveauMdp") ;
    mConfirmationMdp = getRequete ().getParameter ("mConfirmationMdp") ;

    mModifLogin = getRequete ().getParameter ("mModifLogin") ;
    mModifMdp = getRequete ().getParameter ("mModifMdp") ;
    mErreur = "" ;

    if (mAncienMdp != null)
      mAncienMdp = MCollaborateur.encode (mAncienMdp) ;
    if (mNouveauMdp != null)
      mNouveauMdp = MCollaborateur.encode (mNouveauMdp) ;
    if (mConfirmationMdp != null)
      mConfirmationMdp = MCollaborateur.encode (mConfirmationMdp) ;
    if (mModifLogin == null)
      mModifLogin = "0" ;
    if (mModifMdp == null)
      mModifMdp = "0" ;
  }

  /**
   * Effectue le traitement du controleur.
   * 
   * @see owep.controle.CControleurBase#traiter()
   */
  public String traiter () throws ServletException
  {
    OQLQuery lRequete ;
    QueryResults lResultat ;

    try
    {
      getBaseDonnees ().begin () ;

      lRequete = getBaseDonnees ()
        .getOQLQuery (
                      "select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mId=$1") ;
      lRequete.bind (getSession().getIdCollaborateur()) ;
      lResultat = lRequete.execute () ;
      lCollaborateur = (MCollaborateur) lResultat.next () ;

      // verification unicité login
      if (mModifLogin.equals ("1"))
      {
        lRequete = getBaseDonnees ()
          .getOQLQuery (
                        "select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mUtilisateur=$1") ;
        lRequete.bind (mLogin) ;
        lResultat = lRequete.execute () ;
        if (lResultat.size () != 0)
          mErreur = getSession ().getMessages ().getString ("collaborateurMessageLogin") ;
      }

      // verif mdp
      if (mModifMdp.equals("1") && (mAncienMdp != null || mNouveauMdp != null))
      {
        if (!mAncienMdp.equals (lCollaborateur.getMotDePasse ()))
          mErreur += "<BR>" + getSession ().getMessages ().getString ("profilErreurMdp") ;
      }

      // modif profil
      if (mErreur.equals ("") && mNom != null)
      {
        lCollaborateur.setNom (mNom) ;
        lCollaborateur.setPrenom (mPrenom) ;
        lCollaborateur.setEmail (mMail) ;
        lCollaborateur.setAdresse (mAdresse) ;
        lCollaborateur.setTelephone (mTelephone) ;
        lCollaborateur.setPortable (mPortable) ;
        lCollaborateur.setCommentaires (mCommentaire) ;

        lCollaborateur.setUtilisateur (mLogin) ;
        lCollaborateur.setMotDePasse (mNouveauMdp) ;

        mErreur = getSession ().getMessages ().getString ("profilMessageOk") ;
      }

      getBaseDonnees ().commit () ;
    }
    catch (PersistenceException e)
    {
      e.printStackTrace () ;
    }
    finally
    {
      try
      {
        getBaseDonnees ().close () ;
      }
      catch (PersistenceException e1)
      {
        e1.printStackTrace () ;
      }
    }

    getSession ().ouvertureSession (lCollaborateur) ;
    passageParametre () ;
    return "..\\JSP\\Outil\\TModificationProfil.jsp" ;
  }

  /**
   * Gere le passage des parametres (donnees du collaborateur)
   */
  private void passageParametre ()
  {
//    MCollaborateur lCollaborateur = getSession ().getCollaborateur () ;

    getRequete ().setAttribute ("mNom", lCollaborateur.getNom ()) ;
    getRequete ().setAttribute ("mPrenom", lCollaborateur.getPrenom ()) ;
    getRequete ().setAttribute ("mMail", lCollaborateur.getEmail ()) ;
    getRequete ().setAttribute ("mAdresse", lCollaborateur.getAdresse ()) ;
    getRequete ().setAttribute ("mTelephone", lCollaborateur.getTelephone ()) ;
    getRequete ().setAttribute ("mPortable", lCollaborateur.getPortable ()) ;
    getRequete ().setAttribute ("mCommentaire", lCollaborateur.getCommentaires ()) ;
    getRequete ().setAttribute ("mLogin", lCollaborateur.getUtilisateur ()) ;

    getRequete ().setAttribute ("mErreur", mErreur) ;
  }

}