package owep.modele ;


import org.exolab.castor.jdo.TimeStampable ;


/**
 * Classe de base dont tous le éléments du modèle doivent hériter.
 */
public class MModeleBase implements TimeStampable
{
  private long mTimeStamp ;


  /**
   * Récupère le time stamp.
   * @return time stamp.
   * @see org.exolab.castor.jdo.TimeStampable#jdoSetTimeStamp(long)
   */
  public long jdoGetTimeStamp ()
  {
    return mTimeStamp ;
  }


  /**
   * Initialise le time stamp.
   * @param pTimeStamp time stamp.
   * @see org.exolab.castor.jdo.TimeStampable#jdoSetTimeStamp(long)
   */
  public void jdoSetTimeStamp (long pTimeStamp)
  {
    mTimeStamp = pTimeStamp ;
  }
}