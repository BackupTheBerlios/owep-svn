package owep.modele.execution ;


/**
 * MCondition permet de stocker une d�pendance entre deux t�ches. Si la t�che � l'origine de la
 * d�pendance est dans l'�tat souhait�, la condition est consid�r�e comme v�rifi�e.
 */
public class MCondition
{
  private int    mId ;              // Identifie la condition de mani�re unique
  private MTache mTache ;           // T�che ayant une d�pendance.
  private MTache mTachePrecedente ; // T�che vers laquelle il y a une d�pendance.
  private int    mEtat ;            // Etat de la t�che n�cessaire pour que la condition soit vrai.


  /**
   * Construit une instance vide de MCondition.
   */
  public MCondition ()
  {
    super () ;
  }


  /**
   * Construit une instance initialis�e de MCondition.
   * @param pTache Identifiant de la t�che ayant une d�pendance.
   * @param pTachePrecedente T�che vers laquelle il y a une d�pendance.
   * @param pEtat Etat de la t�che n�cessaire pour que la condition soit vrai.
   */
  public MCondition (int pId, MTache pTache, MTache pTachePrecedente, int pEtat)
  {
    super () ;
    
    mId              = pId ;
    mTache           = pTache ;
    mTachePrecedente = pTachePrecedente ;
    mEtat            = pEtat ;
  }

  /**
   * R�cup�re l'identifiant de la condition.
   * @return Identifiant unique de la condition
   */
  public int getId ()
  {
    return mId ;
  }


  /**
   * Initialise l'identifiant de la condition.
   * @param pId Identifiant unique de la condition
   */
  public void setId (int pId)
  {
    mId = pId ;
  }
  
  
  /**
   * R�cup�re l'�tat de la t�che n�cessaire pour que la condition soit vrai.
   * @return Etat de la t�che n�cessaire pour que la condition soit vrai.
   */
  public int getEtat ()
  {
    return mEtat ;
  }


  /**
   * Initialise l'�tat de la t�che n�cessaire pour que la condition soit vrai.
   * @param pEtat Etat de la t�che n�cessaire pour que la condition soit vrai.
   */
  public void setEtat (int pEtat)
  {
    mEtat = pEtat ;
  }


  /**
   * R�cup�re la t�che ayant une d�pendance.
   * @return T�che ayant une d�pendance.
   */
  public MTache getTache ()
  {
    return mTache ;
  }


  /**
   * Initialise la t�che ayant une d�pendance.
   * @param pTache T�che ayant une d�pendance.
   */
  public void setTache (MTache pTache)
  {
    mTache = pTache ;
  }


  /**
   * R�cup�re la t�che vers laquelle il y a une d�pendance.
   * @return T�che vers laquelle il y a une d�pendance.
   */
  public MTache getTachePrecedente ()
  {
    return mTachePrecedente ;
  }


  /**
   * Associe la t�che vers laquelle il y a une d�pendance.
   * @param pTachePrecedente T�che vers laquelle il y a une d�pendance.
   */
  public void setTachePrecedente (MTache pTachePrecedente)
  {
    mTachePrecedente = pTachePrecedente ;
  }
  
  
  /**
   * Indique si la condition est v�rifi�e, c'est � dire si la t�che � l'origine de la d�pendance est
   * dans l'�tat souhait�.
   * @return True si la condition est v�rifi�e et False sinon.
   */
  boolean estVerifiee ()
  {
    return mTachePrecedente.getEtat () == mEtat ;
  }
}
