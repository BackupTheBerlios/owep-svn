package owep.modele.execution ;


import java.util.ArrayList ;


/**
 * Un collaborateur représente une personne travaillant sur un ou plusieurs projet.
 */
public class MCollaborateur extends MModeleBase
{
  private int       mId ;           // Identifie le collaborateur de manière unique
  private String    mPrenom ;       // Prénom du collaborateur
  private String    mNom ;          // Nom du collaborateur
  private String    mAdresse ;      // Adresse du collaborateur
  private String    mTelephone ;    // Téléphone du collaborateur
  private String    mPortable ;     // Portable du collaborateur
  private String    mEmail ;        // Email du collaborateur
  private String    mCommentaires ; // Commentaires du chef de projet sur le collaborateur
  private ArrayList mListeTaches ;  // Listes des tâches que doient réaliser le collaborateur
  
  
  /**
   * Crée une instance vide de MArtefact.
   */
  public MCollaborateur ()
  {
    mListeTaches = new ArrayList () ;
  }
  
  
  /**
   * Crée une instance initialisée de MArtefact.
   * @param pId Identifiant unique de l'artefact
   * @param pPrenom Prénom du collaborateur
   * @param pNom Nom du collaborateur
   * @param pAdresse Adresse du collaborateur
   * @param pTelephone Téléphone du collaborateur
   * @param pPortable Portable du collaborateur
   * @param pEmail Email du collaborateur
   * @param pCommentaires Commentaires du chef de projet sur le collaborateur
   */
  public MCollaborateur (int pId, String pPrenom, String pNom, String pAdresse, String pTelephone,
                         String pPortable, String pEmail, String pCommentaires)
  {
    mId           = pId ;
    mPrenom       = pPrenom ;
    mNom          = pNom ;
    mAdresse      = pAdresse ;
    mTelephone    = pTelephone ;
    mPortable     = pPortable ;
    mEmail        = pEmail ;
    mCommentaires = pCommentaires ;
    mListeTaches  = new ArrayList () ;
  }
  
  
  /**
   * Ajoute une nouvelle tâche à réaliser au collaborateur.
   * @param pTache Nouvelle tâche à réaliser
   */
  public void ajouterTache (MTache pTache)
  {
    mListeTaches.add (pTache) ;
  }
  
  
  /**
   * Récupère l'adresse du collaborateur
   * @return Adresse du collaborateur
   */
  public String getAdresse ()
  {
    return mAdresse ;
  }
  
  
  /**
   * Initialise l'adresse du collaborateur
   * @param pAdresse Adresse du collaborateur
   */
  public void setAdresse (String pAdresse)
  {
    mAdresse = pAdresse ;
  }
  
  
  /**
   * Récupère les commentaires du chef de projet sur le collaborateur.
   * @return Commentaires du chef de projet sur le collaborateur
   */
  public String getCommentaires ()
  {
    return mCommentaires ;
  }
  
  
  /**
   * Initialise les commentaires du chef de projet sur le collaborateur.
   * @param pCommentaires Commentaires du chef de projet sur le collaborateur
   */
  public void setCommentaires (String pCommentaires)
  {
    mCommentaires = pCommentaires ;
  }
  
  
  /**
   * Récupère l'email du collaborateur.
   * @return Email du collaborateur
   */
  public String getEmail ()
  {
    return mEmail ;
  }
  
  
  /**
   * Initialise l'email du collaborateur.
   * @param pEmail Email du collaborateur
   */
  public void setEmail (String pEmail)
  {
    mEmail = pEmail ;
  }
  
  
  /**
   * Récupère l'identifiant du collaborateur.
   * @return Identifiant unique du collaborateur
   */
  public int getId ()
  {
    return mId ;
  }
  
  
  /**
   * Initialise l'identifiant du collaborateur.
   * @param pId Identifiant unique du collaborateur
   */
  public void setId (int pId)
  {
    mId = pId ;
  }
  
  
  /**
   * Récupère le nom du collaborateur.
   * @return Nom du collaborateur
   */
  public String getNom ()
  {
    return mNom ;
  }
  
  
  /**
   * Initialise le nom du collaborateur.
   * @param pNom Nom du collaborateur
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }
  
  
  /**
   * Récupère le portable du collaborateur.
   * @return Portable du collaborateur
   */
  public String getPortable ()
  {
    return mPortable ;
  }
  
  
  /**
   * Initialise le portable du collaborateur.
   * @param pPortable Portable du collaborateur
   */
  public void setPortable (String pPortable)
  {
    mPortable = pPortable ;
  }
  
  
  /**
   * Récupère le prénom du collaborateur.
   * @return Prénom du collaborateur
   */
  public String getPrenom ()
  {
    return mPrenom ;
  }
  
  
  /**
   * Initialise le prénom du collaborateur.
   * @param pPrenom Prénom du collaborateur
   */
  public void setPrenom (String pPrenom)
  {
    mPrenom = pPrenom ;
  }
  
  
  /**
   * Récupère une tâche à réaliser à partir de son indice dans la liste.
   * @param pIndice Indice de la tâche dans la liste
   * @return Tâche à la position pIndice
   */
  public MTache getTache (int pIndice)
  {
    return (MTache) mListeTaches.get (pIndice) ;
  }
  
  
  /**
   * Récupère le nombre de tâche que doit réaliser le collaborateur.
   * @return Nombre de tâche du collaborateur
   */
  public int getNbTache ()
  {
    return mListeTaches.size () ;
  }
  
  
  /**
   * Récupère la liste des tâches que doit réaliser le collaborateur.
   * @return Liste des tâches que doit réaliser le collaborateur
   */
  public ArrayList getListeTaches ()
  {
    return mListeTaches ;
  }
  
  
  /**
   * Initialise la liste des tâches que doit réaliser le collaborateur.
   * @param pListeTaches Liste des tâches que doit réaliser le collaborateur
   */
  public void setListeTaches (ArrayList pListeTaches)
  {
    mListeTaches = pListeTaches ;
  }
  
  
  /**
   * Récupère le télépone du collaborateur.
   * @return Télépone du collaborateur
   */
  public String getTelephone ()
  {
    return mTelephone ;
  }
  
  
  /**
   * Initialise le télépone du collaborateur.
   * @param pTelephone Téléphone du collaborateur
   */
  public void setTelephone (String pTelephone)
  {
    mTelephone = pTelephone ;
  }
}