package owep.controle.processus ;


import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.Iterator ;
import java.util.ResourceBundle;

import javax.servlet.ServletException ;
import org.exolab.castor.jdo.OQLQuery ;
import org.exolab.castor.jdo.PersistenceException ;
import org.exolab.castor.jdo.QueryResults ;
import org.exolab.castor.jdo.TransactionAbortedException;
import org.exolab.castor.jdo.TransactionNotInProgressException;
import owep.controle.CConstante;
import owep.controle.CControleurBase ;
import owep.modele.execution.MCollaborateur ;
import owep.modele.execution.MProjet ;
import owep.modele.processus.MComposant ;
import owep.modele.processus.MProcessus ;
import owep.modele.processus.MRole ;


/**
 * Controleur pour la création d'un collaborateur
 */
// TODO ne pas pouvoir enleve un collab ni un role sur la jsp
public class CCreationCollaborateur extends CControleurBase
{
  private String         mNom ;               // Nom du collaborateur à créer
  private String         mPrenom ;            // Prenom du collaborateur à créer
  private String         mLogin ;             // Login du collaborateur à créer
  private String         mMail ;              // Email du collaborateur à créer
  private String         mAdresse ;           // Adresse du collaborateur à créer
  private String         mTelephone ;         // Numéro du telephone du collaborateur à créer
  private String         mPortable ;          // Numéro du portable du collaborateur à créer
  private String         mCommentaire ;       // Commentaire du collaborateur à créer
  private String         mMdp ;               // Mot de passe du collaborateur à créer
  private int            mDroit ;             //Droit du collaboratur (collaborateur = 0 ;
  // superviseur = 1)
  private String         mNumPageSuivante ;
  private int            mNumPage ;
  private ArrayList      mListeRole ;
  private ArrayList      mListeCollaborateur ;
  private HashMap        mSelectRole ;        // Liste des roles (list id = value) selectionné pour
  // chaque utilisateur (id = key)
  private MCollaborateur mCollaborateur ;     // Collaborateur à créer


  /**
   * Récupère les données nécessaire au controleur dans la base de données.
   * @throws ServletException Si une erreur survient durant la connexion
   * @see owep.controle.CControleurBase#initialiserBaseDonnees()
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    OQLQuery lRequete ; // Requête à réaliser sur la base
    QueryResults lResultat ; // Résultat de la requête sur la base
    mListeRole = new ArrayList () ;
    mListeCollaborateur = new ArrayList () ;
    MProjet lProjet = null ;//getSession ().getProjet () ;
    int idProjet = getSession ().getIdProjet () ;
    getRequete ().setAttribute ("idProjet", String.valueOf (idProjet)) ;
    // liste Role
    try
    {
      getBaseDonnees ().begin () ;
      lRequete = getBaseDonnees ()
        .getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mId=$1") ;
      lRequete.bind (idProjet) ;
      lResultat = lRequete.execute () ;
      lProjet = (MProjet) lResultat.next () ;
      MProcessus lProcessus = lProjet.getProcessus () ;
      ArrayList lListComposant = lProcessus.getListeComposants () ;
      Iterator it = lListComposant.iterator () ;
      while (it.hasNext ())
      {
        MComposant lComposant = (MComposant) it.next () ;
        mListeRole.addAll (lComposant.getListeRoles ()) ;
      }
      getRequete ().setAttribute ("listeRole", mListeRole) ;
    }
    catch (PersistenceException e)
    {
      e.printStackTrace () ;
    }
    // liste Collaborateur
    try
    {
      lRequete = getBaseDonnees ()
        .getOQLQuery (
                      "select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR") ;
      lResultat = lRequete.execute () ;
      MCollaborateur lCollaborateur ;
      while (lResultat.hasMore ())
      {
        lCollaborateur = (MCollaborateur) lResultat.next () ;
        mListeCollaborateur.add (lCollaborateur) ;
      }
      getRequete ().setAttribute ("listeCollaborateur", mListeCollaborateur) ;
    }
    catch (PersistenceException e1)
    {
      e1.printStackTrace () ;
    }
  }


  /**
   * Initialise le controleur et récupère les paramètres.
   * @throws ServletException Si une erreur sur les paramètres survient
   * @see owep.controle.CControleurBase#initialiserParametres()
   */
  public void initialiserParametres () throws ServletException
  {
    mNumPageSuivante = getRequete ().getParameter ("numPageSuivante") ;
    String numPage = getRequete ().getParameter ("numPage") ;
    if (numPage == null)
    {
      if (mNumPageSuivante == null)
        mNumPage = 0 ;
      else
        mNumPage = 1 ;
    }
    else
      mNumPage = Integer.parseInt (numPage) ;
    switch (mNumPage)
    {
      case 1 : // page où l'on associe des roles aux collaborateurs qu'on veut associer au projet
        if ( ! mNumPageSuivante.equals ("2"))
        {
          mSelectRole = new HashMap () ;
          for (int i = 1; i <= mListeCollaborateur.size (); i ++ )
          {
            String mRoleSelected = getRequete ().getParameter ("mRoleSelected" + i) ;
            String[] idRole = mRoleSelected.split ("_") ;
            ArrayList listIdRole = new ArrayList () ;
            for (int j = 0; j < idRole.length; j ++ )
            {
              if ( ! idRole[j].equals (""))
                listIdRole.add (idRole[j]) ;
            }
            mSelectRole.put (String.valueOf (i), listIdRole) ;
          }
        }
        break ;
      case 2 : // page où on créer un collaborateur
        mNom = getRequete ().getParameter ("mNom") ;
        mPrenom = getRequete ().getParameter ("mPrenom") ;
        mLogin = getRequete ().getParameter ("mLogin") ;
        mMail = getRequete ().getParameter ("mMail") ;
        mAdresse = getRequete ().getParameter ("mAdresse") ;
        mTelephone = getRequete ().getParameter ("mTelephone") ;
        mPortable = getRequete ().getParameter ("mPortable") ;
        mCommentaire = getRequete ().getParameter ("mCommentaire") ;
        mDroit = 0 ;
        mMdp = MCollaborateur.encode (mLogin) ;
        break ;
    }
  }


  /**
   * Redirige vers la JSP d'affichage de la page de création d'un collaborateur.
   * @return URL de la page qui doit être affichée.
   * @throws ServletException si une erreur survient dans le controleur.
   * @see owep.controle.CControleurBase#traiter()
   */
  public String traiter () throws ServletException
  {
    OQLQuery lRequete ; // Requête à réaliser sur la base
    QueryResults lResultat ; // Résultat de la requête sur la base
    try
    {
      switch (mNumPage)
      {
        case 0 :
          getRequete ().setAttribute ("numPage", "1") ;
          // Permier appel
          break ;
        case 1 :
          if (mNumPageSuivante.equals ("2"))
          {
            // redirection vers la page 2
          }
          else
          {
            // ajout des collaborateurs selectionné au projet
            try
            {
              // recup données collab role
              ArrayList listCollab = new ArrayList () ;
              Iterator itCollab = mListeCollaborateur.iterator () ;
              while (itCollab.hasNext ())
              {
                MCollaborateur lCollaborateur = (MCollaborateur) itCollab.next () ;
                ArrayList listIdRole = (ArrayList) mSelectRole.get (String.valueOf (lCollaborateur
                  .getId ())) ;
                if (listIdRole.size () > 0)
                {
                  lRequete = getBaseDonnees ()
                    .getOQLQuery (
                                  "select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mId=$1") ;
                  lRequete.bind (lCollaborateur.getId ()) ;
                  lResultat = lRequete.execute () ;
                  lCollaborateur = (MCollaborateur) lResultat.next () ;
                  // Récupération projet
                  lRequete = getBaseDonnees ()
                    .getOQLQuery (
                                  "select PROJET from owep.modele.execution.MProjet PROJET where mId=$1") ;
                  lRequete.bind (getSession ().getIdProjet ()) ;
                  lResultat = lRequete.execute () ;
                  MProjet lProjetOuvert = (MProjet) lResultat.next () ;
                  // Ajout du collaborateur au projet
                  ArrayList listeProjet = lCollaborateur.getListeProjets () ;
                  Iterator itProjet = listeProjet.iterator () ;
                  boolean b = false ;
                  while (itProjet.hasNext () && ! b)
                  {
                    MProjet lProjet = (MProjet) itProjet.next () ;
                    if (lProjet.getId () == lProjetOuvert.getId ())
                    {
                      b = true ;
                    }
                  }
                  if ( ! b)
                  {
                    lCollaborateur.addProjet (lProjetOuvert) ;
                  }
                  // Ajout du role au collaborateur
                  Iterator itIdRole = listIdRole.iterator () ;
                  while (itIdRole.hasNext ())
                  {
                    String idRole = (String) itIdRole.next () ;
                    lRequete = getBaseDonnees ()
                      .getOQLQuery (
                                    "select ROLE from owep.modele.processus.MRole ROLE where mId=$1") ;
                    lRequete.bind (idRole) ;
                    lResultat = lRequete.execute () ;
                    MRole lRole = (MRole) lResultat.next () ;
                    Iterator itRole = lCollaborateur.getListeRoles ().iterator () ;
                    b = false ;
                    while (itRole.hasNext () && ! b)
                    {
                      MRole role = (MRole) itRole.next () ;
                      b = (role.getId () == lRole.getId ()) ;
                    }
                    if ( ! b)
                      lCollaborateur.addRole (lRole) ;
                  }
                }
                listCollab.add (lCollaborateur) ;
              }
              mListeCollaborateur = listCollab ;
            }
            catch (PersistenceException e1)
            {
              e1.printStackTrace () ;
            }
            getRequete ().setAttribute ("listeCollaborateur", mListeCollaborateur) ;

            ResourceBundle messages = java.util.ResourceBundle.getBundle ("MessagesBundle") ;
            String lMessage = messages.getString ("collaborateurRoleConfirmation") ;
            getRequete ().setAttribute (CConstante.PAR_MESSAGE, lMessage) ;

            // Affiche la page de visualisation des taches à réaliser dans la nouvelle itération.
            if(getSession().getIteration() != null)
              return "/Tache/ListeTacheVisu" ;
            
            //else
            getRequete ().setAttribute (CConstante.PAR_COLLABORATEUR, getSession().getCollaborateur()) ;
            return "/JSP/Iteration/TAucuneIteration.jsp" ;
          }
          getRequete ().setAttribute ("numPage", mNumPageSuivante) ;
          break ;
        case 2 :
          // creation du nouveau collaborateur
          try
          {
            lRequete = getBaseDonnees ()
              .getOQLQuery (
                            "select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mUtilisateur = $1") ;
            lRequete.bind (mLogin) ;
            lResultat = lRequete.execute () ;
            // Vérifie que le compte utilisateur n'est pas déjà présent
            if (lResultat.size () != 0)
            {
              getRequete ().setAttribute (
                                          "mProbleme",
                                          getSession ().getMessages ()
                                            .getString ("collaborateurMessageLogin")) ;
              getRequete ().setAttribute ("numPage", "2") ;
              renvoieAttribut () ;
            }
            else
            {
              // Creation du nouveau collaborateur
              mCollaborateur = new MCollaborateur (mPrenom, mNom, mAdresse, mTelephone, mPortable,
                                                   mMail, mCommentaire, mLogin, mMdp, mDroit) ;
              // Enregistre le nouveau collaborateur dans la base de données
              getBaseDonnees ().create (mCollaborateur) ;
              // liste Collaborateur
              lRequete = getBaseDonnees ()
                .getOQLQuery (
                              "select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR") ;
              lResultat = lRequete.execute () ;
              mListeCollaborateur = new ArrayList () ;
              MCollaborateur lCollaborateur ;
              while (lResultat.hasMore ())
              {
                lCollaborateur = (MCollaborateur) lResultat.next () ;
                mListeCollaborateur.add (lCollaborateur) ;
              }
              getRequete ().setAttribute ("listeCollaborateur", mListeCollaborateur) ;
              getRequete ().setAttribute ("numPage", "1") ;
            }
          }
          catch (PersistenceException e1)
          {
            e1.printStackTrace () ;
          }
          break ;
      }
    }
    finally
    {
      try
      {
        getBaseDonnees ().commit () ;
        getBaseDonnees ().close () ;
      }
      catch (Exception eException)
      {
        eException.printStackTrace () ;
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
    }
    return "..\\JSP\\Processus\\CreationCollaborateur.jsp" ;
  }


  /**
   * Transmet les attributs à la page jsp
   */
  private void renvoieAttribut ()
  {
    getRequete ().setAttribute ("mNom", (mNom == null ? "" : mNom)) ;
    getRequete ().setAttribute ("mPrenom", (mPrenom == null ? "" : mPrenom)) ;
    getRequete ().setAttribute ("mLogin", (mLogin == null ? "" : mLogin)) ;
    getRequete ().setAttribute ("mMail", (mMail == null ? "" : mMail)) ;
    getRequete ().setAttribute ("mAdresse", (mAdresse == null ? "" : mAdresse)) ;
    getRequete ().setAttribute ("mTelephone", (mTelephone == null ? "" : mTelephone)) ;
    getRequete ().setAttribute ("mPortable", (mPortable == null ? "" : mPortable)) ;
    getRequete ().setAttribute ("mCommentaire", (mCommentaire == null ? "" : mCommentaire)) ;
  }
}