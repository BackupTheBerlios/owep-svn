package owep.vue.transfert.convertor ;



/**
 * Cette classe est utilisé par le système de transfert pour récupérer et 
 * convertir en string.
 */
public class VStringConvertor
{
  /**
   * Permet de retourner la classe String.
   * @return la classe String
   */
  public static Class getType ()
  {
    return String.class ;  
  }


  /**
   * Cette classe permet de retourner la valeur passer en paramètre en String.
   * @param pValeur La valeur que l'on doit con
   * @return la valeur en String
   */
  public static Object getObject (String pValeur)
  {
    return pValeur ; 
  }


  /**
   * Récupère la fonction javascript permettant de valider ce type. Celle-ci retourne un booléen.
   * @return Fonction javascript permettant de valider ce type.
   */
  public static String getValidation ()
  {
    return "" ; 
  }
}
