package owep.vue.template ;


import javax.servlet.jsp.JspException ;
import javax.servlet.jsp.PageContext ;
import javax.servlet.jsp.tagext.TagSupport ;


/**
 * Tag définissant une implémentation de template, qui sont des associations région/section. La
 * région indique où doit être inclu la section (qui peut être une page ou du texte). Une pile est
 * utilisée afin de pouvoir insérer des templates à l'intérieur d'autres templates. VTemplateTag
 * doit être utilisé en conjonction avec VSectionTag.
 */
public class VTemplateTag extends TagSupport
{
  private String        mNomFichier ;   // Nom du fichier définissant le template
  private VPileTemplate mPileTemplate ; // Pile contenant les définitions de templates
  
  
  /**
   * Crée un nouveau template (en l'insérant dans la pile).
   * @return Statut du tag.
   * @throws JspException -
   */
  public int doStartTag () throws JspException
  {
    // Récupère la pile de template ou l'initialise si elle n'existe pas.
    mPileTemplate = (VPileTemplate) pageContext.getAttribute ("TEMPLATE_PILE",
                                                              PageContext.REQUEST_SCOPE) ;
    if (mPileTemplate == null)
    {
      mPileTemplate = new VPileTemplate () ;
      pageContext.setAttribute ("TEMPLATE_PILE", mPileTemplate, PageContext.REQUEST_SCOPE) ;
    }
    // Met la table qui contiendra la définition du template courant dans la pile.
    mPileTemplate.empilerTemplate () ;
    
    return EVAL_BODY_INCLUDE ;
  }
  
  
  /**
   * Inclus la page définissant les régions pour générer la page, puis supprime le template de la
   * pile (puisque le traîtement sur celui-ci est terminé).
   * @return Statut du tag.
   * @throws JspException Si le fichier ne peut être inclu
   */
  public int doEndTag () throws JspException
  {
    // Inclu le template.
    try
    {
      pageContext.include (mNomFichier) ;
    }
    catch (Exception lException)
    {
      throw new JspException (lException.getMessage ()) ;
    }
    
    // Supprime la définition de template de la pile.
    mPileTemplate.depilerTemplate () ;
    return EVAL_PAGE ;
  }
  
  
  /**
   * Initialise le nom du fichier implémentant le template (association de régions et de sections).
   * @param pNomFichier Nom du fichier implémentant le template
   */
  public void setNom (String pNomFichier)
  {
    mNomFichier = pNomFichier ;
  }
}