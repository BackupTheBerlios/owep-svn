package owep.vue.transfert.convertor ;



/**
 * Cette classe est utilis� par le syst�me de transfert pour r�cup�rer et 
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
   * Cette classe permet de retourner la valeur passer en param�tre en String.
   * @param pValeur La valeur que l'on doit con
   * @return la valeur en String
   */
  public static Object getObject (String pValeur)
  {
    return pValeur ; 
  }


  /**
   * R�cup�re la fonction javascript permettant de valider ce type. Celle-ci retourne un bool�en.
   * @return Fonction javascript permettant de valider ce type.
   */
  public static String getValidation ()
  {
    return "" ; 
  }
}
