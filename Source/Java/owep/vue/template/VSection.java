package owep.vue.template ;


/**
 * Classe contenant les donn�es d�crivant la section.
 * @see owep.vue.template.VPileTemplate
 */
public class VSection
{
  private String  mContenu ;  // Nom du fichier ou texte � inclure dans la r�gion
  private boolean mTypePage ; // Indique si la section � afficher est une page ou un simple texte
  
  
  /**
   * Cr�e une nouvelle instance vide de VSection.
   * @param pContenu Nom du fichier ou texte � inclure dans la r�gion
   * @param pTypePage Indique si la section � afficher est une page ou un simple texte
   */
  public VSection (String pContenu, boolean pTypePage)
  {
    super () ;
    
    mContenu  = pContenu ;
    mTypePage = pTypePage ;
  }
  
  
  /**
   * Indique si la section � inclure est une page JSP ou du texte.
   * @return Vrai si la section correspond � une page � inclure et Faux s'il s'agit de texte
   */
  public boolean isTypePage ()
  {
    return mTypePage ;
  }
  
  
  /**
   * @return Contenu � ins�rer (texte ou page selon le cas).
   */
  public String getContenu ()
  {
    return mContenu ;
  }
}