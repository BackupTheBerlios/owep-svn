package owep.modele.execution ;


import owep.modele.MModeleBase ;


/**
 * Activit� impr�vue survenue au cours du projet.
 */
public class MActiviteImprevue extends MModeleBase
{
  private int mId ; // Identifiant d'activit� impr�vue
  private String mNom ; // Nom de l'activit� impr�vue
  private String mDescription ; // Description de l'activit� impr�vue

  /**
   * Cr�� une instance vide d'activit� impr�vu.
   */
  public MActiviteImprevue ()
  {
  }

  /**
   * R�cup�re la description de l'activit� impr�vue.
   * @return Retourne la valeur de l'attribut description.
   */
  public String getDescription ()
  {
    return mDescription ;
  }

  /**
   * Initialise la description de l'activit� impr�vue.
   * @param initialse description avec pDescription.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }

  /**
   * R�cup�re l'identifiant de l'activit� impr�vue.
   * @return Retourne la valeur de l'attribut id.
   */
  public int getId ()
  {
    return mId ;
  }

  /**
   * Initialise l'identifiant de l'activit� impr�vue.
   * @param initialse id avec pId.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }

  /**
   * R�cup�re le nom de l'activit� impr�vue.
   * @return Retourne la valeur de l'attribut nom.
   */
  public String getNom ()
  {
    return mNom ;
  }

  /**
   * Initialise le nom de l'activit� impr�vue.
   * @param initialse nom avec pNom.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }
}