package owep.modele.execution ;


import owep.modele.MModeleBase ;


/**
 * Un risque repr�sente un risque qui peut apparaitre au cours d'un projet
 */
public class MRisque extends MModeleBase
{
  private int     mId ;          // Id du risque
  private String  mNom ;         // Nom du risque
  private String  mDescription ; // Description du risque
  private int     mPriorite ;    // Priorit� du risque
  private String  mImpact ;      // Impact du risque
  private String  mEtat ;        // Etat du risque
  private MProjet mProjet ;      // Projet auquel le risque est associ�


  /**
   * Cr�� une instance vide de risque.
   */
  public MRisque ()
  {
  }

  /**
   * R�cup�re la description du risque.
   * 
   * @return Retourne la description du risque.
   */
  public String getDescription ()
  {
    return mDescription ;
  }

  /**
   * Initialise la description du risque.
   * 
   * @param Initialse la description du risque avec pDescription.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }

  /**
   * R�cup�re l'�tat du risque.
   * 
   * @return Retourne la valeur de l'attribut etat.
   */
  public String getEtat ()
  {
    return mEtat ;
  }

  /**
   * Initialise l'�tat du risque.
   * 
   * @param initialse etat avec pEtat.
   */
  public void setEtat (String pEtat)
  {
    mEtat = pEtat ;
  }

  /**
   * Retourne l'impact associ� au risque.
   * 
   * @return Retourne la valeur de l'attribut impact.
   */
  public String getImpact ()
  {
    return mImpact ;
  }

  /**
   * D�finit l'impact du risque.
   * 
   * @param initialse impact avec pImpact.
   */
  public void setImpact (String pImpact)
  {
    mImpact = pImpact ;
  }

  /**
   * R�cup�re le nom du risque.
   * 
   * @return Retourne la valeur de l'attribut nom.
   */
  public String getNom ()
  {
    return mNom ;
  }

  /**
   * Initialise le nom du risque.
   * 
   * @param initialse nom avec pNom.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }

  /**
   * R�cup�re la priorit� du risque
   * 
   * @return Retourne la valeur de l'attribut priorite.
   */
  public int getPriorite ()
  {
    return mPriorite ;
  }

  /**
   * Initialise la priorit� du risque.
   * 
   * @param initialse priorite avec pPriorite.
   */
  public void setPriorite (int pPriorite)
  {
    mPriorite = pPriorite ;
  }

  /**
   * R�cup�re le proje associ� au risque.
   * 
   * @return Retourne la valeur de l'attribut projet.
   */
  public MProjet getProjet ()
  {
    return mProjet ;
  }

  /**
   * Associe un projet au risque.
   * 
   * @param initialse projet avec pProjet.
   */
  public void setProjet (MProjet pProjet)
  {
    mProjet = pProjet ;
  }

  /**
   * R�cup�re l'id du risque.
   * 
   * @return Retourne la valeur de l'attribut id.
   */
  public int getId ()
  {
    return mId ;
  }

  /**
   * Initialise l'id du risque.
   * 
   * @param initialse id avec pId.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }
}