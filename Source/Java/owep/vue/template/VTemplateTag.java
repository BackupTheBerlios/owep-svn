package owep.vue.template ;


import javax.servlet.jsp.JspException ;
import javax.servlet.jsp.PageContext ;
import javax.servlet.jsp.tagext.TagSupport ;


/**
 * Tag d�finissant une impl�mentation de template, qui sont des associations r�gion/section. La
 * r�gion indique o� doit �tre inclu la section (qui peut �tre une page ou du texte). Une pile est
 * utilis�e afin de pouvoir ins�rer des templates � l'int�rieur d'autres templates. VTemplateTag
 * doit �tre utilis� en conjonction avec VSectionTag.
 */
public class VTemplateTag extends TagSupport
{
  private String        mNomFichier ;   // Nom du fichier d�finissant le template
  private VPileTemplate mPileTemplate ; // Pile contenant les d�finitions de templates
  
  
  /**
   * Cr�e un nouveau template (en l'ins�rant dans la pile).
   * @return Statut du tag.
   * @throws JspException -
   */
  public int doStartTag () throws JspException
  {
    // R�cup�re la pile de template ou l'initialise si elle n'existe pas.
    mPileTemplate = (VPileTemplate) pageContext.getAttribute ("TEMPLATE_PILE",
                                                              PageContext.REQUEST_SCOPE) ;
    if (mPileTemplate == null)
    {
      mPileTemplate = new VPileTemplate () ;
      pageContext.setAttribute ("TEMPLATE_PILE", mPileTemplate, PageContext.REQUEST_SCOPE) ;
    }
    // Met la table qui contiendra la d�finition du template courant dans la pile.
    mPileTemplate.empilerTemplate () ;
    
    return EVAL_BODY_INCLUDE ;
  }
  
  
  /**
   * Inclus la page d�finissant les r�gions pour g�n�rer la page, puis supprime le template de la
   * pile (puisque le tra�tement sur celui-ci est termin�).
   * @return Statut du tag.
   * @throws JspException Si le fichier ne peut �tre inclu
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
    
    // Supprime la d�finition de template de la pile.
    mPileTemplate.depilerTemplate () ;
    return EVAL_PAGE ;
  }
  
  
  /**
   * Initialise le nom du fichier impl�mentant le template (association de r�gions et de sections).
   * @param pNomFichier Nom du fichier impl�mentant le template
   */
  public void setNom (String pNomFichier)
  {
    mNomFichier = pNomFichier ;
  }
}