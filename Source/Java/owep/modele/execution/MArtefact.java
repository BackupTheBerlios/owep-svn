package owep.modele.execution ;


/**
 * Un artefact représente un sous ensemble de produit, réalisé durant l'exécution d'un processus.
 */
public class MArtefact extends MModeleBase
{
  private int    mId ;          // Identifie l'artefact de manière unique
  private String mNom ;         // Nom de l'artefact
  private String mDescription ; // Description de l'artefact
  private MTache mTache ;       // Tâche au cours de laquelle est produit l'artefact
  
  
  /**
   * Crée une instance vide de MArtefact.
   */
  public MArtefact ()
  {
  }
  
  
  /**
   * Crée une instance initialisée de MArtefact.
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
   * Récupère la description de l'artefact.
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
   * Récupère l'identifiant de l'artefact.
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
   * Récupère le nom de l'artefact.
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
   * Récupère la tâche dont est issu l'artefact.
   * @return Tâche dont est issu l'artefact
   */
  public MTache getTache ()
  {
    return mTache ;
  }
  
  
  /**
   * Initialise la tâche dont est issu l'artefact.
   * @param pTache Tâche dont est issu l'artefact
   */
  public void setTache (MTache pTache)
  {
    mTache = pTache ;
  }
}