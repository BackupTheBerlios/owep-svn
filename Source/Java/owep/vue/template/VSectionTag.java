package owep.vue.template ;


import javax.servlet.jsp.JspException ;
import javax.servlet.jsp.PageContext ;
import javax.servlet.jsp.tagext.TagSupport ;


/**
 * Tag d�finissant une page ou du texte � inclure. Cette section fait r�f�rence � une r�gion o�
 * sera effectivement incluse la section.
 */
public class VSectionTag extends TagSupport
{
  private boolean  mTypePage ; // Indique si la section � afficher est une page ou un simple texte
  private String   mRegion ;   // Nom de la r�gion dans laquelle doit �tre incluse la section
  private String   mContenu ;  // R�f�rence vers la page ou valeur du texte � inclure
  
  
  /**
   * Ajoute la nouvelle section dans la pile de template.
   * @throws JspException -
   * @return Statut du tag.
   */
  public int doStartTag () throws JspException
  {
    // V�rifie que la balise "section" courante est incluse dans une balise "template".
    assert findAncestorWithClass (this, VTemplateTag.class) != null ;
    // V�rifie que la pile de template a bien �t� cr��e.
    assert pageContext.getAttribute ("TEMPLATE_PILE", PageContext.REQUEST_SCOPE) != null ;
    
    // R�cup�re la pile de template et y ins�re la nouvelle section.
    VPileTemplate lPileTemplate = (VPileTemplate) pageContext
      .getAttribute ("TEMPLATE_PILE", PageContext.REQUEST_SCOPE) ;
    lPileTemplate.ajouterSection (mRegion, new VSection (mContenu, mTypePage)) ;
    
    return SKIP_BODY ;
  }


  /**
   * Indique si la section � afficher est une page ou un simple texte
   * @param pTypePage Vrai si la section � afficher est une page ou Faux s'il s'agit de texte simple
   */
  public void setTypePage (String pTypePage)
  {
    mTypePage = pTypePage.equals ("true") ;
  }


  /**
   * Sp�cifie la r�gion ou doit �tre ins�r�e la section.
   * @param pRegion R�gion ou doit �tre ins�r�e la section
   */
  public void setRegion (String pRegion)
  {
    mRegion = pRegion ;
  }


  /**
   * Sp�cifie la page ou le texte qui doit �tre ins�r�.
   * @param pContenu Page ou texte � ins�rer
   */
  public void setContenu (String pContenu)
  {
    mContenu = pContenu ;
  }
}