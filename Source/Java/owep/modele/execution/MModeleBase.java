package owep.modele.execution ;


import org.exolab.castor.jdo.TimeStampable ;


/**
 * Classe de base dont tous le éléments du modèle doivent hériter.
 */
public class MModeleBase implements TimeStampable
{
  private long mTimeStamp ;


  public void jdoSetTimeStamp (long pTimeStamp)
  {
    mTimeStamp = pTimeStamp ;
  }


  public long jdoGetTimeStamp ()
  {
    return mTimeStamp ;
  }
}