package owep.modele.execution ;


import java.util.ArrayList ;
import java.util.Iterator ;

import owep.modele.MModeleBase ;
import owep.modele.processus.MRole ;


/**
 * Un collaborateur repr�sente une personne travaillant sur un ou plusieurs projet.
 */
public class MCollaborateur extends MModeleBase
{

  private int       mId ;                // Identifie le collaborateur de mani�re unique.
  private String    mPrenom ;            // Pr�nom du collaborateur.
  private String    mNom ;               // Nom du collaborateur.
  private String    mAdresse ;           // Adresse du collaborateur.
  private String    mTelephone ;         // T�l�phone du collaborateur.
  private String    mPortable ;          // Portable du collaborateur.
  private String    mEmail ;             // Email du collaborateur.
  private String    mCommentaires ;      // Commentaires du chef de projet sur le collaborateur.
  private String    mUtilisateur ;       // Nom d'utilisateur pour la connexion.
  private String    mMotDePasse ;        // Mot de passe lors de la connexion.
  private ArrayList mArtefacts ;         // Artefacts dont le collaborateur est responsable.
  private ArrayList mRoles ;             // R�les tenues par le collaborateur.
  private ArrayList mTaches ;            // Listes des t�ches que doient r�aliser le collaborateur.
  private ArrayList mProjets ;           // Listes des projets sur lesquels travaille le
                                         // collaborateur.
  private ArrayList mProjetsChef ;       // Listes des projets pour lesquels le collaborateur est
                                         // chef.
  private int       mTacheEnCours ;      // identifiant de la t�che en cours de r�alisation
  private int       mDroit ;             // Droits du collaborateur (collaborateur = 0 ; chef de
                                         // projet = 1)
  private ArrayList mTachesImprevues ;   // Listes des t�ches impr�vues que doient r�aliser le
                                         // collaborateur.
  private ArrayList mArtefactsImprevues ; // Artefacts impr�vues dont le collaborateur est
                                          // responsable.
  private ArrayList mMesures ;           // Liste des mesures associ�es au collaborateur.


  /**
   * Cr�e une instance vide de MCollaborateur.
   */
  public MCollaborateur ()
  {
    mTaches = new ArrayList () ;
    mArtefacts = new ArrayList () ;
    mRoles = new ArrayList () ;
    mProjets = new ArrayList () ;
    mProjetsChef = new ArrayList () ;
    mTachesImprevues = new ArrayList () ;
    mArtefactsImprevues = new ArrayList () ;
    mMesures = new ArrayList () ;
    mTacheEnCours = -1 ;
  }

  /**
   * Cr�e une instance initialis�e de MArtefact.
   * 
   * @param pId Identifiant unique de l'artefact.
   * @param pPrenom Pr�nom du collaborateur.
   * @param pNom Nom du collaborateur.
   * @param pAdresse Adresse du collaborateur.
   * @param pTelephone T�l�phone du collaborateur.
   * @param pPortable Portable du collaborateur.
   * @param pEmail Email du collaborateur.
   * @param pCommentaires Commentaires du chef de projet sur le collaborateur.
   * @param pUtilisateur Nom d'utilidateur du collaborateur.
   * @param pMotDePasse Mot de passe du collaborateur.
   */
  public MCollaborateur (String pPrenom, String pNom, String pAdresse, String pTelephone,
                         String pPortable, String pEmail, String pCommentaire, String pUtilisateur,
                         String pMotDePasse, int pDroit)
  {
    mPrenom = pPrenom ;
    mNom = pNom ;
    mAdresse = pAdresse ;
    mTelephone = pTelephone ;
    mPortable = pPortable ;
    mEmail = pEmail ;
    mCommentaires = pCommentaire ;
    mUtilisateur = pUtilisateur ;
    mMotDePasse = pMotDePasse ;
    mDroit = pDroit ;

    mTaches = new ArrayList () ;
    mArtefacts = new ArrayList () ;
    mRoles = new ArrayList () ;
    mProjets = new ArrayList () ;
    mProjetsChef = new ArrayList () ;
    mTachesImprevues = new ArrayList () ;
    mArtefactsImprevues = new ArrayList () ;
    mMesures = new ArrayList () ;
    mTacheEnCours = -1 ;
  }

  /**
   * R�cup�re l'adresse du collaborateur.
   * 
   * @return Adresse du collaborateur.
   */
  public String getAdresse ()
  {
    return mAdresse ;
  }

  /**
   * Initialise l'adresse du collaborateur.
   * 
   * @param pAdresse Adresse du collaborateur.
   */
  public void setAdresse (String pAdresse)
  {
    mAdresse = pAdresse ;
  }

  /**
   * R�cup�re les artefacts dont le collaborateur est responsable.
   * 
   * @return Artefacts dont le collaborateur est responsable.
   */
  public ArrayList getListeArtefacts ()
  {
    return mArtefacts ;
  }

  /**
   * Initialise les artefacts dont le collaborateur est responsable.
   * 
   * @param pArtefacts Artefacts dont le collaborateur est responsable.
   */
  public void setListeArtefacts (ArrayList pArtefacts)
  {
    mArtefacts = pArtefacts ;
  }

  /**
   * R�cup�re le nombre d'artefacts dont le collaborateur est responsable.
   * 
   * @return Nombre d'artefacts dont le collaborateur est responsable.
   */
  public int getNbArtefacts ()
  {
    return mArtefacts.size () ;
  }

  /**
   * R�cup�re l'artefact d'indice sp�cifi� dont le collaborateur est responsable.
   * 
   * @param pIndice Indice de l'artefact dont le collaborateur est responsable.
   * @return Artefact dont le collaborateur est responsable.
   */
  public MArtefact getArtefact (int pIndice)
  {
    return (MArtefact) mArtefacts.get (pIndice) ;
  }

  /**
   * Ajoute un artefact dont le collaborateur est responsable.
   * 
   * @param pArtefact Artefact dont le collaborateur est responsable.
   */
  public void addArtefact (MArtefact pArtefact)
  {
    mArtefacts.add (pArtefact) ;
  }

  /**
   * Supprime un artefact dont le collaborateur est responsable.
   * 
   * @param pArtefact Artefact dont le collaborateur est responsable.
   */
  public void supprimerArtefact (MArtefact pArtefact)
  {
    mArtefacts.remove (pArtefact) ;
  }

  /**
   * R�cup�re les commentaires du chef de projet.
   * 
   * @return Commentaires du chef de projet sur le collaborateur.
   */
  public String getCommentaires ()
  {
    return mCommentaires ;
  }

  /**
   * Initialise les commentaires du chef de projet.
   * 
   * @param pCommentaires Commentaires du chef de projet sur le collaborateur.
   */
  public void setCommentaires (String pCommentaires)
  {
    mCommentaires = pCommentaires ;
  }

  /**
   * R�cup�re l'email du collaborateur.
   * 
   * @return Email du collaborateur.
   */
  public String getEmail ()
  {
    return mEmail ;
  }

  /**
   * Initialise l'email du collaborateur.
   * 
   * @param pEmail Email du collaborateur.
   */
  public void setEmail (String pEmail)
  {
    mEmail = pEmail ;
  }

  /**
   * R�cup�re l'identifiant du collaborateur.
   * 
   * @return Identifiant unique du collaborateur.
   */
  public int getId ()
  {
    return mId ;
  }

  /**
   * Initialise l'identifiant du collaborateur.
   * 
   * @param pId Identifiant unique du collaborateur.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }

  /**
   * R�cup�re le mot de passe.
   * 
   * @return Mot de passe lors de la connexion.
   */
  public String getMotDePasse ()
  {
    return mMotDePasse ;
  }

  /**
   * Initialise le mot de passe.
   * 
   * @param pMotDePasse Mot de passe lors de la connexion.
   */
  public void setMotDePasse (String pMotDePasse)
  {
    mMotDePasse = pMotDePasse ;
  }

  /**
   * R�cup�re le nom du collaborateur.
   * 
   * @return Nom du collaborateur.
   */
  public String getNom ()
  {
    return mNom ;
  }

  /**
   * Initialise le nom du collaborateur.
   * 
   * @param pNom Nom du collaborateur.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }

  /**
   * R�cup�re le portable du collaborateur.
   * 
   * @return Portable du collaborateur.
   */
  public String getPortable ()
  {
    return mPortable ;
  }

  /**
   * Initialise le portable du collaborateur.
   * 
   * @param pPortable Portable du collaborateur.
   */
  public void setPortable (String pPortable)
  {
    mPortable = pPortable ;
  }

  /**
   * R�cup�re le pr�nom du collaborateur.
   * 
   * @return Pr�nom du collaborateur.
   */
  public String getPrenom ()
  {
    return mPrenom ;
  }

  /**
   * Initialise le pr�nom du collaborateur.
   * 
   * @param pPrenom Pr�nom du collaborateur.
   */
  public void setPrenom (String pPrenom)
  {
    mPrenom = pPrenom ;
  }

  /**
   * R�cup�re la listes des projets sur lesquels travaille le collaborateur.
   * 
   * @return Listes des projets sur lesquels travaille le collaborateur.
   */
  public ArrayList getListeProjets ()
  {
    return mProjets ;
  }

  /**
   * Initialise la listes des projets sur lesquels travaille le collaborateur.
   * 
   * @param pProjets Listes des projets sur lesquels travaille le collaborateur.
   */
  public void setListeProjets (ArrayList pProjets)
  {
    mProjets = pProjets ;
    Iterator it = pProjets.iterator () ;
    while (it.hasNext ())
    {
      MProjet lProjet = (MProjet) it.next () ;
      if (!lProjet.getListeCollaborateurs ().contains (this))
        lProjet.addCollaborateur (this) ;
    }
  }

  /**
   * R�cup�re le nombre de projets sur lesquels travaille le collaborateur.</br> <b>Post : Valeur
   * retourn� > 0 </b>
   * 
   * @return Nombre de projets sur lesquels travaille le collaborateur.
   */
  public int getNbProjets ()
  {
    return mProjets.size () ;
  }

  /**
   * R�cup�re le projet d'indice sp�cifi� sur lequel travaille le collaborateur.
   * 
   * @param pIndice Indice du projet dans la liste.
   * @return Projet sur lequel travaille le collaborateur.
   */
  public MProjet getProjet (int pIndice)
  {
    return (MProjet) mProjets.get (pIndice) ;
  }

  /**
   * Ajoute le projet sp�cifi� au collaborateur.
   * 
   * @param pProjet Projet sur lequel travaille le collaborateur.
   */
  public void addProjet (MProjet pProjet)
  {
    if (!mProjets.contains (pProjet))
      mProjets.add (pProjet) ;
    if (!pProjet.getListeCollaborateurs ().contains (this))
      pProjet.addCollaborateur (this) ;
  }

  /**
   * R�cup�re la listes des projets pour lesquels le collaborateur est chef.
   * 
   * @return Listes des projets pour lesquels le collaborateur est chef.
   */
  public ArrayList getListeProjetsChef ()
  {
    return mProjetsChef ;
  }

  /**
   * Initialise la listes des projets pour lesquels le collaborateur est chef.
   * 
   * @param pProjetsChef Listes des projets pour lesquels le collaborateur est chef.
   */
  public void setListeProjetsChef (ArrayList pProjetsChef)
  {
    mProjetsChef = pProjetsChef ;
  }

  /**
   * R�cup�re le nombre de projets pour lesquels le collaborateur est chef.
   * 
   * @return Nombre de projets pour lesquels le collaborateur est chef.
   */
  public int getNbProjetsChef ()
  {
    return mProjetsChef.size () ;
  }

  /**
   * R�cup�re le projet d'indice sp�cifi� pour lequel le collaborateur est chef.
   * 
   * @param pIndice Indice du projet dans la liste.
   * @return Projet pour lequel le collaborateur est chef.
   */
  public MProjet getProjetChef (int pIndice)
  {
    return (MProjet) mProjetsChef.get (pIndice) ;
  }

  /**
   * Ajoute le projet sp�cifi� au chef de projet.
   * 
   * @param pProjetChef Projet pour lequel le collaborateur est chef.
   */
  public void addProjetChef (MProjet pProjetChef)
  {
    mProjetsChef.add (pProjetChef) ;
  }

  /**
   * R�cup�re les r�les tenues par le collaborateur.
   * 
   * @return R�les tenues par le collaborateur.
   */
  public ArrayList getListeRoles ()
  {
    return mRoles ;
  }

  /**
   * R�cup�re les r�les tenues par le collaborateur.
   * 
   * @param pRoles R�les tenues par le collaborateur.
   */
  public void setListeRoles (ArrayList pRoles)
  {
    mRoles = pRoles ;
    Iterator it = pRoles.iterator () ;
    while (it.hasNext ())
    {
      MRole lRole = (MRole) it.next () ;
      if (!lRole.getListeCollaborateurs ().contains (this))
        lRole.addCollaborateur (this) ;
    }
  }

  /**
   * R�cup�re le nombre de Roles que doit r�aliser le collaborateur.
   * 
   * @return Nombre de r�les tenus par le collaborateur.
   */
  public int getNbRoles ()
  {
    return mRoles.size () ;
  }

  /**
   * R�cup�re le r�le d'indice sp�cifi� tenu par le collaborateur.
   * 
   * @param pIndice Indice du r�le dans la liste.
   * @return R�le tenu par le collaborateur.
   */
  public MRole getRole (int pIndice)
  {
    return (MRole) mRoles.get (pIndice) ;
  }

  /**
   * Ajoute un r�le tenu par le collaborateur.
   * 
   * @param pRole R�le tenu par le collaborateur.
   */
  public void addRole (MRole pRole)
  {
    if (!mRoles.contains (pRole))
      mRoles.add (pRole) ;
    if(!pRole.getListeCollaborateurs().contains(this))
      pRole.addCollaborateur(this);
  }

  /**
   * R�cup�re la liste des t�ches que doit r�aliser le collaborateur.
   * 
   * @return Liste des t�ches que doit r�aliser le collaborateur.
   */
  public ArrayList getListeTaches ()
  {
    return mTaches ;
  }

  /**
   * Initialise la liste des t�ches que doit r�aliser le collaborateur.
   * 
   * @param pTaches Liste des t�ches que doit r�aliser le collaborateur.
   */
  public void setListeTaches (ArrayList pTaches)
  {
    mTaches = pTaches ;
  }

  /**
   * R�cup�re le nombre de t�ches que doit r�aliser le collaborateur.
   * 
   * @return Nombre de t�ches que doit r�aliser le collaborateur.
   */
  public int getNbTaches ()
  {
    return mTaches.size () ;
  }

  /**
   * R�cup�re la t�che d'indice sp�cifi� que doit r�aliser le collaborateur.
   * 
   * @param pIndice Indice de la t�che dans la liste.
   * @return T�che que doit r�aliser le collaborateur.
   */
  public MTache getTache (int pIndice)
  {
    return (MTache) mTaches.get (pIndice) ;
  }

  /**
   * Ajoute la t�che sp�cifi�e au collaborateur.
   * 
   * @param pTache T�che que doit r�aliser le collaborateur.
   */
  public void addTache (MTache pTache)
  {
    mTaches.add (pTache) ;
  }

  /**
   * Supprime la t�che sp�cifi�e assign�e au collaborateur.
   * 
   * @param pTache T�che que doit r�aliser le collaborateur.
   */
  public void supprimerTache (MTache pTache)
  {
    mTaches.remove (pTache) ;
  }

  /**
   * R�cup�re le t�l�pone du collaborateur.
   * 
   * @return T�l�pone du collaborateur.
   */
  public String getTelephone ()
  {
    return mTelephone ;
  }

  /**
   * Initialise le t�l�pone du collaborateur.
   * 
   * @param pTelephone T�l�phone du collaborateur.
   */
  public void setTelephone (String pTelephone)
  {
    mTelephone = pTelephone ;
  }

  /**
   * R�cup�re le nom d'utilisateur pour la connexion.
   * 
   * @return Nom d'utilisateur pour la connexion.
   */
  public String getUtilisateur ()
  {
    return mUtilisateur ;
  }

  /**
   * Initialise le nom d'utilisateur pour la connexion.
   * 
   * @param pUtilisateur Nom d'utilisateur pour la connexion.
   */
  public void setUtilisateur (String pUtilisateur)
  {
    mUtilisateur = pUtilisateur ;
  }

  /**
   * R�cup�re le bool�en indicateur de tache en cours.
   * 
   * @return Bool�en (0 ou 1)
   */
  public int getTacheEnCours ()
  {
    return mTacheEnCours ;
  }

  /**
   * Initialise le bool�en indicateur de tache en cours.
   * 
   * @param pUtilisateur Bool�en indicateur de tache en cours.
   */
  public void setTacheEnCours (int pTacheEnCours)
  {
    mTacheEnCours = pTacheEnCours ;
  }

  /**
   * R�cup�re la variable Droit du collaborateur.
   * 
   * @return la variable Droit du collaborateur.
   */
  public int getDroit ()
  {
    return mDroit ;
  }

  /**
   * Initialise la variable Droit du collaborateur.
   * 
   * @param pDroit variable Droit du collaborateur.
   */
  public void setDroit (int pDroit)
  {
    mDroit = pDroit ;
  }

  /**
   * R�cup�re la liste des t�ches impr�vues que doit r�aliser le collaborateur.
   * 
   * @return Liste des t�ches impr�vues que doit r�aliser le collaborateur.
   */
  public ArrayList getListeTachesImprevues ()
  {
    return mTachesImprevues ;
  }

  /**
   * Initialise la liste des t�ches impr�vues que doit r�aliser le collaborateur.
   * 
   * @param pTachesImprevues Liste des t�ches impr�vues que doit r�aliser le collaborateur.
   */
  public void setListeTachesImprevues (ArrayList pTachesImprevues)
  {
    mTachesImprevues = pTachesImprevues ;
  }

  /**
   * R�cup�re le nombre de t�ches impr�vues que doit r�aliser le collaborateur.
   * 
   * @return Nombre de t�ches impr�vues que doit r�aliser le collaborateur.
   */
  public int getNbTachesImprevues ()
  {
    return mTachesImprevues.size () ;
  }

  /**
   * R�cup�re la t�che impr�vue d'indice sp�cifi� que doit r�aliser le collaborateur.
   * 
   * @param pIndice Indice de la t�che impr�vue dans la liste.
   * @return T�che impr�vue que doit r�aliser le collaborateur.
   */
  public MTacheImprevue getTacheImprevue (int pIndice)
  {
    return (MTacheImprevue) mTachesImprevues.get (pIndice) ;
  }

  /**
   * Ajoute la t�che impr�vue sp�cifi�e au collaborateur.
   * 
   * @param pTacheImprevue T�che impr�vue que doit r�aliser le collaborateur.
   */
  public void addTacheImprevue (MTacheImprevue pTacheImprevue)
  {
    mTachesImprevues.add (pTacheImprevue) ;
  }

  /**
   * Supprime la t�che impr�vue sp�cifi�e assign�e au collaborateur.
   * 
   * @param pTacheImprevue T�che impr�vue que doit r�aliser le collaborateur.
   */
  public void supprimerTacheImprevue (MTacheImprevue pTacheImprevue)
  {
    mTachesImprevues.remove (pTacheImprevue) ;
  }

  /**
   * R�cup�re les artefacts impr�vues dont le collaborateur est responsable.
   * 
   * @return Artefacts impr�vues dont le collaborateur est responsable.
   */
  public ArrayList getListeArtefactsImprevues ()
  {
    return mArtefactsImprevues ;
  }

  /**
   * Initialise les artefacts impr�vues dont le collaborateur est responsable.
   * 
   * @param pArtefactsImprevues Artefacts impr�vues dont le collaborateur est responsable.
   */
  public void setListeArtefactsImprevues (ArrayList pArtefactsImprevues)
  {
    mArtefactsImprevues = pArtefactsImprevues ;
  }

  /**
   * R�cup�re le nombre d'artefacts impr�vues dont le collaborateur est responsable.
   * 
   * @return Nombre d'artefacts impr�vues dont le collaborateur est responsable.
   */
  public int getNbArtefactsImprevues ()
  {
    return mArtefactsImprevues.size () ;
  }

  /**
   * R�cup�re l'artefact impr�vue d'indice sp�cifi� dont le collaborateur est responsable.
   * 
   * @param pIndice Indice de l'artefact impr�vue dont le collaborateur est responsable.
   * @return Artefact impr�vue dont le collaborateur est responsable.
   */
  public MArtefactImprevue getArtefactImprevue (int pIndice)
  {
    return (MArtefactImprevue) mArtefactsImprevues.get (pIndice) ;
  }

  /**
   * Ajoute un artefact dont le collaborateur est responsable.
   * 
   * @param pArtefactImprevue Artefact dont le collaborateur est responsable.
   */
  public void addArtefactImprevue (MArtefactImprevue pArtefactImprevue)
  {
    mArtefactsImprevues.add (pArtefactImprevue) ;
  }

  /**
   * Supprime un artefact impr�vue dont le collaborateur est responsable.
   * 
   * @param pArtefactImprevue Artefact impr�vue dont le collaborateur est responsable.
   */
  public void supprimerArtefactImprevue (MArtefactImprevue pArtefactImprevue)
  {
    mArtefactsImprevues.remove (pArtefactImprevue) ;
  }

  /**
   * Retourne le parametre encode.
   * 
   * @param pMdp Parametre a encode.
   * @return Chaine code
   */
  public static String encode (String pMdp)
  {
    String code = ""; 
    for(int i = 0 ; i < pMdp.length() ; i++)
    {
      int j = pMdp.charAt(i)+10;
      code += j;
    }
    return code;
  }

  /**
   * R�cup�re la liste des mesures associ�es au collaborateur.
   * 
   * @return Liste des mesures associ�es au collaborateur.
   */
  public ArrayList getListeMesures ()
  {
    return mMesures ;
  }

  /**
   * Initialise la liste des mesures associ�es au collaborateur.
   * 
   * @param pArtefacts Liste des mesures associ�es au collaborateur.
   */
  public void setListeMesures (ArrayList pMesures)
  {
    mMesures = pMesures ;
  }

  
  /**
   * Ajoute une mesure pour le collaborateur.
   * @param pMesure Mesure � ajouter au collaborateur.
   */
  public void addMesure (MMesureIndicateur pMesure)
  {
    mMesures.add (pMesure) ;
  }
}
