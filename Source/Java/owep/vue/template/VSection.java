package owep.vue.template ;


/**
 * Classe contenant les données décrivant la section.
 * @see owep.vue.template.VPileTemplate
 */
public class VSection
{
  private String  mContenu ;  // Nom du fichier ou texte à inclure dans la région
  private boolean mTypePage ; // Indique si la section à afficher est une page ou un simple texte
  
  
  /**
   * Crée une nouvelle instance vide de VSection.
   * @param pContenu Nom du fichier ou texte à inclure dans la région
   * @param pTypePage Indique si la section à afficher est une page ou un simple texte
   */
  public VSection (String pContenu, boolean pTypePage)
  {
    super () ;
    
    mContenu  = pContenu ;
    mTypePage = pTypePage ;
  }
  
  
  /**
   * Indique si la section à inclure est une page JSP ou du texte.
   * @return Vrai si la section correspond à une page à inclure et Faux s'il s'agit de texte
   */
  public boolean isTypePage ()
  {
    return mTypePage ;
  }
  
  
  /**
   * @return Contenu à insérer (texte ou page selon le cas).
   */
  public String getContenu ()
  {
    return mContenu ;
  }
}