package owep.modele.execution ;


import owep.modele.MModeleBase ;


/**
 * Une m�trique repr�sente une mesure effectu� sur le projet associ�.
 */
public class MMetrique extends MModeleBase
{
  private int     mId ;          // Identifiant de la metrique
  private String  mNom ;         // Nom de la metrique
  private int     mType ;        // Type de la metrique
  private int     mValeur ;      // Valeur de la metrique
  private String  mDescription ; // Description de la metrique
  private MProjet mProjet ;      // Projet associ� � la metrique


  /**
   * Cr�� une instance vide de m�trique.
   */
  public MMetrique ()
  {
    super () ;
  }

  /**
   * R�cup�re la description de la m�trique.
   * 
   * @return Retourne la valeur de l'attribut description.
   */
  public String getDescription ()
  {
    return mDescription ;
  }

  /**
   * Initialise la description de la metrique
   * 
   * @param initialse description avec pDescription.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }

  /**
   * R�cup�re l'identifiant de la m�trique.
   * 
   * @return Retourne la valeur de l'attribut id.
   */
  public int getId ()
  {
    return mId ;
  }

  /**
   * Initialise l'identifiant de la m�trique.
   * 
   * @param initialse id avec pId.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }

  /**
   * R�cup�re le nom de la metrique.
   * 
   * @return Retourne la valeur de l'attribut nom.
   */
  public String getNom ()
  {
    return mNom ;
  }

  /**
   * Initialise le nom de la metrique.
   * 
   * @param initialse nom avec pNom.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }

  /**
   * R�cup�re le projet associ� � la metrique.
   * 
   * @return Retourne la valeur de l'attribut projet.
   */
  public MProjet getProjet ()
  {
    return mProjet ;
  }

  /**
   * Associe un projet � la m�trique.
   * 
   * @param initialse projet avec pProjet.
   */
  public void setProjet (MProjet pProjet)
  {
    mProjet = pProjet ;
  }

  /**
   * R�cup�re le type de la m�trique.
   * 
   * @return Retourne la valeur de l'attribut type.
   */
  public int getType ()
  {
    return mType ;
  }

  /**
   * Initialise le type de la metrique.
   * 
   * @param initialse type avec pType.
   */
  public void setType (int pType)
  {
    mType = pType ;
  }

  /**
   * R�cup�re la valeur de la metrique.
   * 
   * @return Retourne la valeur de l'attribut valeur.
   */
  public int getValeur ()
  {
    return mValeur ;
  }

  /**
   * Initialise la valeur de la metrique.
   * 
   * @param initialse valeur avec pValeur.
   */
  public void setValeur (int pValeur)
  {
    mValeur = pValeur ;
  }
}