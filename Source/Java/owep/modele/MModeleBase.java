package owep.modele ;


import org.exolab.castor.jdo.TimeStampable ;


/**
 * Classe de base dont tous le �l�ments du mod�le doivent h�riter.
 */
public class MModeleBase implements TimeStampable
{
  private long mTimeStamp ;


  /**
   * R�cup�re le time stamp.
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