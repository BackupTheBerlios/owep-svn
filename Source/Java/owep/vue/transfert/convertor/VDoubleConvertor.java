package owep.vue.transfert.convertor ;


/**
 * Cette classe est utilisé par le système de transfert pour récupérer et 
 * convertir en double.
 */
public class VDoubleConvertor
{
  /**
   * Retourne la classe Double.
   * @return Classe Double.
   */
  public static Class getType ()
  {
    return Double.TYPE;  
  }
  
  
  /**
   * Permet de convetir la valeur passée en Double.
   * @param pValeur Valeur que l'on doit convertir.
   * @return Valeur convertit au format Double.
   */
  public static Object getObject (String pValeur)
  {
    return new Double (pValeur) ; 
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
