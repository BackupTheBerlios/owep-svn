package owep.modele ;


import java.util.HashMap;
import org.exolab.castor.jdo.TimeStampable ;


/**
 * Classe de base dont tous le �l�ments du mod�le doivent h�riter.
 */
public class MModeleBase implements TimeStampable
{
  private long mTimeStamp ;
  private HashMap liste ;


  /**
   * Cr�e une instance vide de MCollaborateur.
   */
  public MModeleBase ()
  {
    liste = new HashMap () ;
  }
  
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
  /**
   * TODO R�cup�re liste.
   * @return liste.
   */
  public HashMap getListe ()
  {
    return liste ;
  }
  
  /**
   * TODO R�cup�re �l�ment correspondant 
   * � la cl� pass�e en param�tre
   * @return objet
   */
  public Object getListe (Object pCle)
  {
    return liste.get(pCle) ;
  }
  
  /**
   * TODO R�cup�re la taille de la liste 
   * @return int
   */
  public int getTailleListe ()
  {
    return liste.size() ;
  }
  
  /**
   * TODO Initialise liste.
   * @param liste liste.
   */
  public void setListe (HashMap liste)
  {
    this.liste = liste ;
  }
  
  /**
   * TODO Ins�re dans la liste la cl� et sa valeur associ�e
   * pass�es en param�tre
   * @param cl�, valeur
   */
  public void setListe (Object pCle, Object pValeur)
  {
    this.liste.put(pCle, pValeur) ;
  }
}