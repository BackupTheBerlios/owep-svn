package owep.modele.execution ;


/**
 * Un artefact repr�sente un sous ensemble de produit, r�alis� durant l'ex�cution d'un processus.
 */
public class MArtefact extends MModeleBase
{
  private int    mId ;          // Identifie l'artefact de mani�re unique
  private String mNom ;         // Nom de l'artefact
  private String mDescription ; // Description de l'artefact
  private MTache mTache ;       // T�che au cours de laquelle est produit l'artefact
  
  
  /**
   * Cr�e une instance vide de MArtefact.
   */
  public MArtefact ()
  {
  }
  
  
  /**
   * Cr�e une instance initialis�e de MArtefact.
   * @param pId Identifiant unique de l'artefact
   * @param pNom Nom de l'artefact
   * @param pDescription Description de l'artefact
   */
  public MArtefact (int pId, String pNom, String pDescription)
  {
    mId          = pId ;
    mNom         = pNom ;
    mDescription = pDescription ;
  }
  
  
  /**
   * R�cup�re la description de l'artefact.
   * @return Description de l'artefact
   */
  public String getDescription ()
  {
    return mDescription ;
  }
  
  
  /**
   * Initialise la description de l'artefact.
   * @param pDescription Description de l'artefact
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }
  
  
  /**
   * R�cup�re l'identifiant de l'artefact.
   * @return Identifiant unique de l'artefact
   */
  public int getId ()
  {
    return mId ;
  }
  
  
  /**
   * Initialise l'identifiant de l'artefact.
   * @param pId Identifiant unique de l'artefact
   */
  public void setId (int pId)
  {
    mId = pId ;
  }
  
  
  /**
   * R�cup�re le nom de l'artefact.
   * @return Nom de l'artefact
   */
  public String getNom ()
  {
    return mNom ;
  }
  
  
  /**
   * Initialise le nom de l'artefact.
   * @param pNom Nom de l'artefact
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }
  
  
  /**
   * R�cup�re la t�che dont est issu l'artefact.
   * @return T�che dont est issu l'artefact
   */
  public MTache getTache ()
  {
    return mTache ;
  }
  
  
  /**
   * Initialise la t�che dont est issu l'artefact.
   * @param pTache T�che dont est issu l'artefact
   */
  public void setTache (MTache pTache)
  {
    mTache = pTache ;
  }
}