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
   * Retourner la classe de Date.
   * @return Classe Date.
   */
  public static Class getType () 
  {
    return java.util.Date.class ;
  }
  
  
  /**
   * Permet de convetir la valeur passée en date.
   * @param pValeur la valeur que l'on doit convertir.
   * @return la valeur convertit au format date.
   * @throws ParseException si une erreur survient lors de la convertion.
   */
  public static Object getObject (String pValeur) throws ParseException
  {
    if (! pValeur.equals (""))
    {
      return new SimpleDateFormat ("dd/MM/yyyy").parse (pValeur) ;
    }
    else
    {
      return null ;
    }
  }

  
  
  /**
   * Convertit la date en chaîne.
   * @param pDate Date à convertir en chaîne.
   * @param pVide Si vrai, renvoie une chaîne vide, sinon renvoie un espace insécable HTML.
   * @return Date formatée sous forme de chaîne.
   */
  public static String getString (Date pDate, boolean pVide)
  {
    if (pDate != null)
    {
      SimpleDateFormat lDateFormat = new SimpleDateFormat ("dd/MM/yyyy") ;
      return lDateFormat.format (pDate) ;
    }
    else
    {
      if (pVide)
      {
        return "" ;
      }
      else
      {
        return "&nbsp;" ;
      }
    }
  }

  
  public static String getString (Date pDate)
  {
    if (pDate != null)
    {
      SimpleDateFormat lDateFormat = new SimpleDateFormat ("dd/MM/yyyy") ;
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
