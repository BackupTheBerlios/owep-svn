package owep.vue.transfert;


/**
 * Constantes utilis�es dans la paquetage transfert.
 */
public class VTransfertConstante
{
  // Identifie le champ de validation transmis par le formulaire.
  public static final String TRANSFERT_SOUMISSION       = "OWEPCHAMP_SOUMISSION" ;
  // Valeur identifiant la pile d'arbres de transfert dans les attributs de sessions.
  public static final String TRANSFERT_PILE             = "TRANSFERT_PILE" ;
  // Paquetage ou se situe les classes de conversions de types.
  public static final String TRANSFERT_CONVERTORPACKAGE = "owep.vue.transfert.convertor." ;
  // Valeur identifiant la liste d'arbres de transfert dans les attributs de la requ�te.
  public static final String TRANSFERT_LISTEARBRES      = "TRANSFERT_LISTEARBRES" ;
  // Prefixe de la fonction javascript de validation de champ.
  public static final String TRANSFERT_VERIFICATION     = "verifier" ;
  
  /**
   * Retourne la fonction javascript de v�rification de champs de l'arbre sp�cifi�.
   * @param pIdArbre Identifiant de l'arbre dont on veut la fonction de v�rification.
   * @return la fonction javascript de v�rification de champs
   */
  public static String getVerification (String pIdArbre)
  {
    return TRANSFERT_VERIFICATION + pIdArbre ;
  }
}
