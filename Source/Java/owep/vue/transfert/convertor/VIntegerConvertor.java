package owep.vue.transfert.convertor ;



/**
 * Cette classe est utilis� par le syst�me de transfert pour r�cup�rer et 
 * convertir en integer.
 */
public class VIntegerConvertor
{
  /**
   * Retourner la classe Integer.
   * @return Classe Integer
   */
  public static Class getType ()
  {
    return Integer.TYPE;  
  }
  
  
  /**
   * Permet de convetir la valeur pass�e en Integer.
   * @param pValeur Valeur � convertir.
   * @return Valeur convertit en Integer.
   */
  public static Object getObject (String pValeur)
  {
    return new Integer (pValeur) ; 
  }
  
  
  /**
   * R�cup�re la fonction javascript permettant de valider ce type. Celle-ci retourne un bool�en.
   * @return Fonction javascript permettant de valider ce type.
   */
  public static String getValidation ()
  {
    return "isInteger" ; 
  }
}
