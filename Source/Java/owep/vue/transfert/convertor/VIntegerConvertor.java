package owep.vue.transfert.convertor ;



/**
 * Cette classe est utilisé par le système de transfert pour récupérer et 
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
    return Integer.TYPE ;  
  }
  
  
  /**
   * Permet de convetir la valeur passer en Integer.
   * @param pValeur la valeur à convertir.
   * @return la valeur convertit en Integer.
   */
  public static Object getObject (String pValeur)
  {
    return new Integer (pValeur) ; 
  }




  /**
   * Récupère la fonction javascript permettant de valider ce type. Celle-ci retourne un booléen.
   * @return Fonction javascript permettant de valider ce type.
   */
  public static String getValidation ()
  {
    return "isInteger" ; 
  }
}
