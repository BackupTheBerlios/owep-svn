package owep.vue.transfert.convertor ;



/**
 * Cette classe est utilisé par le système de transfert pour récupérer et 
 * convertir en string.
 */
public class VStringConvertor
{
  /**
   * Retourner la classe String.
   * @return Classe String
   */
  public static Class getType ()
  {
    return String.class ;  
  }
  
  
  /**
   * Cette classe permet de retourner la valeur passée en paramètre en String.
   * @param pValeur Valeur que l'on doit convertir.
   * @return Valeur convertit en chaîne.
   */
  public static Object getObject (String pValeur)
  {
    return pValeur ; 
  }
  
  
  /**
   * Convertit une chaîne potentiellement nulle.
   * @param pChaine Chaîne potentiellement nulle.
   * @param pVide Si vrai, renvoie une chaîne vide, sinon renvoie un espace insécable HTML.
   * @return Chaîne initialisé à la valeur de pChaine ou à la chaîne vide si pChaine est null.
   */
  public static String getString (String pChaine, boolean pVide)
  {
    if (pChaine != null && (! pChaine.trim ().equals ("")))
    {
      return pChaine ;
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
  
  
  /**
   * Récupère la fonction javascript permettant de valider ce type. Celle-ci retourne un booléen.
   * @return Fonction javascript permettant de valider ce type.
   */
  public static String getValidation ()
  {
    return "" ; 
  }
}
