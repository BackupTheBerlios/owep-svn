package owep.vue.transfert.convertor ;



/**
 * Cette classe est utilis� par le syst�me de transfert pour r�cup�rer et 
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
   * Cette classe permet de retourner la valeur pass�e en param�tre en String.
   * @param pValeur Valeur que l'on doit convertir.
   * @return Valeur convertit en cha�ne.
   */
  public static Object getObject (String pValeur)
  {
    return pValeur ; 
  }
  
  
  /**
   * Convertit une cha�ne potentiellement nulle.
   * @param pChaine Cha�ne potentiellement nulle.
   * @param pVide Si vrai, renvoie une cha�ne vide, sinon renvoie un espace ins�cable HTML.
   * @return Cha�ne initialis� � la valeur de pChaine ou � la cha�ne vide si pChaine est null.
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
   * R�cup�re la fonction javascript permettant de valider ce type. Celle-ci retourne un bool�en.
   * @return Fonction javascript permettant de valider ce type.
   */
  public static String getValidation ()
  {
    return "" ; 
  }
}
