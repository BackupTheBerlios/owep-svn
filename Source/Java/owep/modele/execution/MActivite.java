package owep.modele.execution ;


public class MActivite extends MModeleBase
{
  private String mNom ;
  
  
  public String getNom ()
  {
    return mNom ;
  }
  
  
  /**
   * @param pNom The nom to set.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }
}