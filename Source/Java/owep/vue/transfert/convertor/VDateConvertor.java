package owep.vue.transfert.convertor ;


import java.text.ParseException ;
import java.text.SimpleDateFormat ;
import java.util.Date ;


/**
 * Cette classe est utilisé par le système de transfert pour récupérer et 
 * convertir en date.
 */
public class VDateConvertor
{
  /**
   * Permet de retourner la classe Data.
   * @return la classe Date.
   */
  public static Class getType () 
  {
    return java.util.Date.class ;
  }
  
  /**
   * Permet de convetir la valeur passer en date.
   * @param pValeur la valeur que l'on doit convertir.
   * @return la valeur convertit au format date.
   * @throws ParseException si une erreur survient lors de la convertion.
   */
  public static Object getObject (String pValeur) throws ParseException
  {
    if (! pValeur.equals (""))
    {
      return new SimpleDateFormat ("yyyy-MM-dd").parse (pValeur) ;
    }
    else
    {
      return null ;
    }
  }
  
  
  public static String getString (Date pDate)
  {
    if (pDate != null)
    {
      SimpleDateFormat lDateFormat = new SimpleDateFormat ("yyyy-MM-dd") ;
      return lDateFormat.format (pDate) ;
    }
    else
    {
      return "" ;
    }
  }


  /**
   * Récupère la fonction javascript permettant de valider ce type. Celle-ci retourne un booléen.
   * @return Fonction javascript permettant de valider ce type.
   */
  public static String getValidation ()
  {
    return "isDate" ; 
  }
}
