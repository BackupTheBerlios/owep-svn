package owep.vue.template ;


import java.util.HashMap ;
import java.util.Stack ;


/**
 * Classe contenant les données d'un template (région, section associée) sous forme d'une pile.
 * Cette structure permet de définir un template qui en contient plusieurs autres.
 */
public class VPileTemplate
{
  private Stack mPileTemplate ; // Pile contenant les templates et sous-template
  
  
  /**
   * Crée une instance vide de MArtefact.
   */
  public VPileTemplate ()
  {
    super () ;
    mPileTemplate = new Stack () ;
  }
  
  
  /**
   * Ajoute un nouveau template dans la pile. Celui-ci devra être concrètement défini par un
   * ou plusieurs appel à la méthode ajouterSection ().
   */
  public void empilerTemplate ()
  {
    mPileTemplate.push (new HashMap ()) ;
  }
  
  
  /**
   * Supprime le template en haut de pile.
   */
  public void depilerTemplate ()
  {
    mPileTemplate.pop () ;
  }
  
  
  /**
   * Définie une nouvelle section, associée à une région, dans le template courant.
   * @param pNomRegion Nom de la région dans laquelle doit être insérée la section
   * @param pSection Section qui doit être insérée dans la région
   */
  public void ajouterSection (String pNomRegion, VSection pSection)
  {
    ((HashMap) mPileTemplate.peek ()).put (pNomRegion, pSection) ;
  }
  
  
  /**
   * Récupère la section du template courant associée à la région pNomRegion.
   * @param pNomRegion Nom de la région dont on doit récupérer la section à insérer
   * @return Section à insérer
   */
  public VSection getSection (String pNomRegion)
  {
    return (VSection) ((HashMap) mPileTemplate.peek ()).get (pNomRegion) ;
  }
}