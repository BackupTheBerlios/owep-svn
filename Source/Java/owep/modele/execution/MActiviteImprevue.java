package owep.modele.execution ;


import owep.modele.MModeleBase ;


/**
 * Activité imprévue survenue au cours du projet.
 */
public class MActiviteImprevue extends MModeleBase
{
  private int mId ; // Identifiant d'activité imprévue
  private String mNom ; // Nom de l'activité imprévue
  private String mDescription ; // Description de l'activité imprévue

  /**
   * Créé une instance vide d'activité imprévu.
   */
  public MActiviteImprevue ()
  {
  }

  /**
   * Récupère la description de l'activité imprévue.
   * @return Retourne la valeur de l'attribut description.
   */
  public String getDescription ()
  {
    return mDescription ;
  }

  /**
   * Initialise la description de l'activité imprévue.
   * @param initialse description avec pDescription.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }

  /**
   * Récupère l'identifiant de l'activité imprévue.
   * @return Retourne la valeur de l'attribut id.
   */
  public int getId ()
  {
    return mId ;
  }

  /**
   * Initialise l'identifiant de l'activité imprévue.
   * @param initialse id avec pId.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }

  /**
   * Récupère le nom de l'activité imprévue.
   * @return Retourne la valeur de l'attribut nom.
   */
  public String getNom ()
  {
    return mNom ;
  }

  /**
   * Initialise le nom de l'activité imprévue.
   * @param initialse nom avec pNom.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }
}