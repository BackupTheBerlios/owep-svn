package owep.modele.execution ;


import java.util.ArrayList ;


/**
 * Un collaborateur repr�sente une personne travaillant sur un ou plusieurs projet.
 */
public class MCollaborateur extends MModeleBase
{
  private int       mId ;           // Identifie le collaborateur de mani�re unique
  private String    mPrenom ;       // Pr�nom du collaborateur
  private String    mNom ;          // Nom du collaborateur
  private String    mAdresse ;      // Adresse du collaborateur
  private String    mTelephone ;    // T�l�phone du collaborateur
  private String    mPortable ;     // Portable du collaborateur
  private String    mEmail ;        // Email du collaborateur
  private String    mCommentaires ; // Commentaires du chef de projet sur le collaborateur
  private ArrayList mListeTaches ;  // Listes des t�ches que doient r�aliser le collaborateur
  
  
  /**
   * Cr�e une instance vide de MArtefact.
   */
  public MCollaborateur ()
  {
    mListeTaches = new ArrayList () ;
  }
  
  
  /**
   * Cr�e une instance initialis�e de MArtefact.
   * @param pId Identifiant unique de l'artefact
   * @param pPrenom Pr�nom du collaborateur
   * @param pNom Nom du collaborateur
   * @param pAdresse Adresse du collaborateur
   * @param pTelephone T�l�phone du collaborateur
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
   * Ajoute une nouvelle t�che � r�aliser au collaborateur.
   * @param pTache Nouvelle t�che � r�aliser
   */
  public void ajouterTache (MTache pTache)
  {
    mListeTaches.add (pTache) ;
  }
  
  
  /**
   * R�cup�re l'adresse du collaborateur
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
   * R�cup�re les commentaires du chef de projet sur le collaborateur.
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
   * R�cup�re l'email du collaborateur.
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
   * R�cup�re l'identifiant du collaborateur.
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
   * R�cup�re le nom du collaborateur.
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
   * R�cup�re le portable du collaborateur.
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
   * R�cup�re le pr�nom du collaborateur.
   * @return Pr�nom du collaborateur
   */
  public String getPrenom ()
  {
    return mPrenom ;
  }
  
  
  /**
   * Initialise le pr�nom du collaborateur.
   * @param pPrenom Pr�nom du collaborateur
   */
  public void setPrenom (String pPrenom)
  {
    mPrenom = pPrenom ;
  }
  
  
  /**
   * R�cup�re une t�che � r�aliser � partir de son indice dans la liste.
   * @param pIndice Indice de la t�che dans la liste
   * @return T�che � la position pIndice
   */
  public MTache getTache (int pIndice)
  {
    return (MTache) mListeTaches.get (pIndice) ;
  }
  
  
  /**
   * R�cup�re le nombre de t�che que doit r�aliser le collaborateur.
   * @return Nombre de t�che du collaborateur
   */
  public int getNbTache ()
  {
    return mListeTaches.size () ;
  }
  
  
  /**
   * R�cup�re la liste des t�ches que doit r�aliser le collaborateur.
   * @return Liste des t�ches que doit r�aliser le collaborateur
   */
  public ArrayList getListeTaches ()
  {
    return mListeTaches ;
  }
  
  
  /**
   * Initialise la liste des t�ches que doit r�aliser le collaborateur.
   * @param pListeTaches Liste des t�ches que doit r�aliser le collaborateur
   */
  public void setListeTaches (ArrayList pListeTaches)
  {
    mListeTaches = pListeTaches ;
  }
  
  
  /**
   * R�cup�re le t�l�pone du collaborateur.
   * @return T�l�pone du collaborateur
   */
  public String getTelephone ()
  {
    return mTelephone ;
  }
  
  
  /**
   * Initialise le t�l�pone du collaborateur.
   * @param pTelephone T�l�phone du collaborateur
   */
  public void setTelephone (String pTelephone)
  {
    mTelephone = pTelephone ;
  }
}