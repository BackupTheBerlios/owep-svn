package owep.vue.transfert;



/**
 * Cette classe est utilis� par le syst�me de transfert pour r�cup�rer et 
 * convertir en integer.
 */
public class VIntegerConvertor
{
  /**
   * Permet de retourner la classe Integer.
   * @return la classe Integer
   */
  public static Class getType ()
  {
    return Integer.TYPE;  
  }
  
  /**
   * Permet de convetir la valeur passer en Integer.
   * @param pValeur la valeur � convertir.
   * @return la valeur convertit en Integer.
   */
  public static Object getObject (String pValeur)
  {
    return new Integer (pValeur) ; 
  }
}
