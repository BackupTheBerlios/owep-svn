package owep.vue.template ;


import java.util.HashMap ;
import java.util.Stack ;


/**
 * Classe contenant les donn�es d'un template (r�gion, section associ�e) sous forme d'une pile.
 * Cette structure permet de d�finir un template qui en contient plusieurs autres.
 */
public class VPileTemplate
{
  private Stack mPileTemplate ; // Pile contenant les templates et sous-template
  
  
  /**
   * Cr�e une instance vide de MArtefact.
   */
  public VPileTemplate ()
  {
    super () ;
    mPileTemplate = new Stack () ;
  }
  
  
  /**
   * Ajoute un nouveau template dans la pile. Celui-ci devra �tre concr�tement d�fini par un
   * ou plusieurs appel � la m�thode ajouterSection ().
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
   * D�finie une nouvelle section, associ�e � une r�gion, dans le template courant.
   * @param pNomRegion Nom de la r�gion dans laquelle doit �tre ins�r�e la section
   * @param pSection Section qui doit �tre ins�r�e dans la r�gion
   */
  public void ajouterSection (String pNomRegion, VSection pSection)
  {
    ((HashMap) mPileTemplate.peek ()).put (pNomRegion, pSection) ;
  }
  
  
  /**
   * R�cup�re la section du template courant associ�e � la r�gion pNomRegion.
   * @param pNomRegion Nom de la r�gion dont on doit r�cup�rer la section � ins�rer
   * @return Section � ins�rer
   */
  public VSection getSection (String pNomRegion)
  {
    return (VSection) ((HashMap) mPileTemplate.peek ()).get (pNomRegion) ;
  }
}