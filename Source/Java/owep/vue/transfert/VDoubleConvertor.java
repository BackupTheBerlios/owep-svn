package owep.vue.transfert;


/**
 * Cette classe est utilisé par le système de transfert pour récupérer et 
 * convertir en double.
 */
public class VDoubleConvertor
{
  /**
   * Permet de retourner la classe Double.
   * @return la classe Double.
   */
  public static Class getType ()
  {
    return Double.TYPE;  
  }
  
  
  /**
   * Permet de convetir la valeur passer en Double.
   * @param pValeur la valeur que l'on doit convertir.
   * @return la valeur convertit au format Double.
   */
  public static Object getObject (String pValeur)
  {
    return new Double (pValeur) ; 
  }
}
