package owep.modele ;


import java.util.HashMap;
import org.exolab.castor.jdo.TimeStampable ;


/**
 * Classe de base dont tous le éléments du modèle doivent hériter.
 */
public class MModeleBase implements TimeStampable
{
  private long mTimeStamp ;
  private HashMap liste ;


  /**
   * Crée une instance vide de MCollaborateur.
   */
  public MModeleBase ()
  {
    liste = new HashMap () ;
  }
  
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
  /**
   * TODO Récupère liste.
   * @return liste.
   */
  public HashMap getListe ()
  {
    return liste ;
  }
  
  /**
   * TODO Récupère élément correspondant 
   * à la clé passée en paramètre
   * @return objet
   */
  public Object getListe (Object pCle)
  {
    return liste.get(pCle) ;
  }
  
  /**
   * TODO Récupère la taille de la liste 
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
   * TODO Insère dans la liste la clé et sa valeur associée
   * passées en paramètre
   * @param clé, valeur
   */
  public void setListe (Object pCle, Object pValeur)
  {
    this.liste.put(pCle, pValeur) ;
  }
}