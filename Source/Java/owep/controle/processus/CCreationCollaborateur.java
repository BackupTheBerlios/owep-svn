package owep.controle.processus ;


import java.util.ArrayList ;

import javax.servlet.ServletException ;

import org.exolab.castor.jdo.OQLQuery ;
import org.exolab.castor.jdo.PersistenceException ;
import org.exolab.castor.jdo.QueryResults ;

import owep.controle.CConstante ;
import owep.controle.CControleurBase ;
import owep.modele.execution.MCollaborateur ;
import owep.modele.execution.MProjet ;
import owep.modele.processus.MComposant ;
import owep.modele.processus.MProcessus ;
import owep.modele.processus.MRole ;


/**
 * Controleur pour la création d'un collaborateur
 */
public class CCreationCollaborateur extends CControleurBase
{
  private String mPageSource ; // Page appelant la création d'un collaborateur

  private String mNom ; // Nom du collaborateur à créer
  private String mPrenom ; // Prenom du collaborateur à créer
  private String mLogin ; // Login du collaborateur à créer
  private String mMail ; // Email du collaborateur à créer
  private String mAdresse ; // Adresse du collaborateur à créer
  private String mTelephone ; // Numéro du telephone du collaborateur à créer
  private String mPortable ; // Numéro du portable du collaborateur à créer
  private String mCommentaire ; // Commentaire du collaborateur à créer
  private String mMdp ; // Mot de passe du collaborateur à créer
  private int mDroit ; //Droit du collaboratur (collaborateur = 0 ; chef de
  // projet = 1)

  private ArrayList mListeRole ;

  private MCollaborateur mCollaborateur ; // Collaborateur à créer

  private ArrayList mSelectRole ; // Liste des roles selectionné pour


  // l'utilisateur qu'on souhaite créer

  /**
   * Récupère les données nécessaire au controleur dans la base de données.
   * 
   * @throws ServletException Si une erreur survient durant la connexion
   * @see owep.controle.CControleurBase#initialiserBaseDonnees()
   */
  public void initialiserBaseDonnees () throws ServletException
  {
  }

  /**
   * Initialise le controleur et récupère les paramètres.
   * 
   * @throws ServletException Si une erreur sur les paramètres survient
   * @see owep.controle.CControleurBase#initialiserParametres()
   */
  public void initialiserParametres () throws ServletException
  {
    mPageSource = getRequete ().getParameter ("mPageSource") ;

    mNom = getRequete ().getParameter ("mNom") ;
    mPrenom = getRequete ().getParameter ("mPrenom") ;
    mLogin = getRequete ().getParameter ("mLogin") ;
    mMail = getRequete ().getParameter ("mMail") ;
    mAdresse = getRequete ().getParameter ("mAdresse") ;
    mTelephone = getRequete ().getParameter ("mTelephone") ;
    mPortable = getRequete ().getParameter ("mPortable") ;
    mCommentaire = getRequete ().getParameter ("mCommentaire") ;
    mDroit = 0 ;
    mMdp = mLogin ;

    mSelectRole = new ArrayList () ;
    getListeRole () ;
    for (int i = 0 ; i < mListeRole.size () ; i++)
    {
      int role = ((MRole) mListeRole.get (i)).getId () ;
      String idRoleSelect = getRequete ().getParameter ("mRoleSelect" + role) ;
      if (idRoleSelect != null)
      {
        mSelectRole.add (idRoleSelect) ;
      }
    }
  }

  /**
   * Redirige vers la JSP d'affichage de la page de création d'un collaborateur.
   * 
   * @return URL de la page qui doit être affichée.
   * @throws ServletException si une erreur survient dans le controleur.
   * @see owep.controle.CControleurBase#traiter()
   */
  public String traiter () throws ServletException
  {
    OQLQuery lRequete ; // Requête à réaliser sur la base
    QueryResults lResultat ; // Résultat de la requête sur la base

    if (mNom == null)
    {
      getRequete ().setAttribute ("mPageSource", getRequete ().getHeader ("referer")) ;
      getRequete ().setAttribute ("listeRole", mListeRole) ;
      return "..\\JSP\\Processus\\CreationCollaborateur.jsp" ;
    }

    if (mNom.equals (""))
    {
      renvoieAttribut () ;
      getRequete ().setAttribute ("mProbleme", "nom") ;
      return "..\\JSP\\Processus\\CreationCollaborateur.jsp" ;
    }

    if (mLogin.equals (""))
    {
      renvoieAttribut () ;
      getRequete ().setAttribute ("mProbleme", "login") ;
      return "..\\JSP\\Processus\\CreationCollaborateur.jsp" ;
    }

    if (mMail.equals (""))
    {
      renvoieAttribut () ;
      getRequete ().setAttribute ("mProbleme", "mail") ;
      return "..\\JSP\\Processus\\CreationCollaborateur.jsp" ;
    }

    try
    {
      getBaseDonnees ().begin () ;

      // Cherche la présence de collaborateur ayant le login demandé par
      // le nouveau
      lRequete = getBaseDonnees ()
        .getOQLQuery (
                      "select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mUtilisateur = $1") ;
      lRequete.bind (mLogin) ;
      lResultat = lRequete.execute () ;

      // Vérifie que le compte utilisateur n'est pas déjà présent
      if (lResultat.size () != 0)
      {
        getBaseDonnees ().commit () ;
        getBaseDonnees ().close () ;

        getRequete ().setAttribute ("mProbleme", "unique") ;
        renvoieAttribut () ;
        return "..\\JSP\\Processus\\CreationCollaborateur.jsp" ;
      }

      // Créé le nouveau collaborateur
      mCollaborateur = new MCollaborateur (mPrenom, mNom, mAdresse, mTelephone, mPortable, mMail,
                                           mCommentaire, mLogin, mMdp, mDroit) ;

      // Enregistre le nouveau collaborateur dans la base de données
      getBaseDonnees ().create (mCollaborateur) ;
      getBaseDonnees ().commit () ;

      getBaseDonnees().begin();

      // Recupére le nouveau collaborateur enregistré
      lRequete = getBaseDonnees ()
        .getOQLQuery (
                      "select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mUtilisateur = $1") ;
      lRequete.bind (mLogin) ;
      lResultat = lRequete.execute () ;
      mCollaborateur = (MCollaborateur) lResultat.next();
      
      // Attribution des roles
      for (int i = 0 ; i < mSelectRole.size () ; i++)
      {
        lRequete = getBaseDonnees ()
          .getOQLQuery ("select ROLE from owep.modele.processus.MRole ROLE where mId = $1") ;
        lRequete.bind (mSelectRole.get (i)) ;
        lResultat = lRequete.execute () ;

        MRole roleSelect = (MRole) lResultat.next () ;

        mCollaborateur.addRole (roleSelect) ;
      }

      getBaseDonnees().commit();
      getBaseDonnees ().close () ;

      getRequete ().setAttribute ("mProbleme", "false") ;
      getRequete ().setAttribute ("mPageSource", mPageSource) ;
      return "..\\JSP\\Processus\\CreationCollaborateur.jsp" ;
    }
    catch (PersistenceException e)
    {
      e.printStackTrace () ;
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
   * Transmet les attributs à la page jsp
   */
  private void renvoieAttribut ()
  {
    getRequete ().setAttribute ("mPageSource", mPageSource) ;
    getRequete ().setAttribute ("listeRole", mListeRole) ;

    getRequete ().setAttribute ("mNom", (mNom == null ? "" : mNom)) ;
    getRequete ().setAttribute ("mPrenom", (mPrenom == null ? "" : mPrenom)) ;
    getRequete ().setAttribute ("mLogin", (mLogin == null ? "" : mLogin)) ;
    getRequete ().setAttribute ("mMail", (mMail == null ? "" : mMail)) ;
    getRequete ().setAttribute ("mAdresse", (mAdresse == null ? "" : mAdresse)) ;
    getRequete ().setAttribute ("mTelephone", (mTelephone == null ? "" : mTelephone)) ;
    getRequete ().setAttribute ("mPortable", (mPortable == null ? "" : mPortable)) ;
    getRequete ().setAttribute ("mCommentaire", (mCommentaire == null ? "" : mCommentaire)) ;
  }

  /**
   * Cherche tous les roles possibles pour le processus associé au projet ouvert
   */
  private void getListeRole ()
  {
    MProjet lProjet = getSession ().getProjet () ;
    MProcessus lProcessus = lProjet.getProcessus () ;
    ArrayList lListeComposants = lProcessus.getListeComposants () ;
    MComposant lComposant ;

    mListeRole = new ArrayList () ;
    for (int i = 0 ; i < lListeComposants.size () ; i++)
    {
      lComposant = (MComposant) lListeComposants.get (i) ;
      ArrayList lListeRole = lComposant.getListeRoles () ;
      for (int j = 0 ; j < lListeRole.size () ; j++)
      {
        if (!mListeRole.contains (lListeRole.get (j)))
        {
          mListeRole.add (lListeRole.get (j)) ;
        }
      }
    }

  }

}