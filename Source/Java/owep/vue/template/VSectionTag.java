package owep.vue.template ;


import javax.servlet.jsp.JspException ;
import javax.servlet.jsp.PageContext ;
import javax.servlet.jsp.tagext.TagSupport ;


/**
 * Tag définissant une page ou du texte à inclure. Cette section fait référence à une région où
 * sera effectivement incluse la section.
 */
public class VSectionTag extends TagSupport
{
  private boolean  mTypePage ; // Indique si la section à afficher est une page ou un simple texte
  private String   mRegion ;   // Nom de la région dans laquelle doit être incluse la section
  private String   mContenu ;  // Référence vers la page ou valeur du texte à inclure
  
  
  /**
   * Ajoute la nouvelle section dans la pile de template.
   * @throws JspException -
   * @return Statut du tag.
   */
  public int doStartTag () throws JspException
  {
    // Vérifie que la balise "section" courante est incluse dans une balise "template".
    assert findAncestorWithClass (this, VTemplateTag.class) != null ;
    // Vérifie que la pile de template a bien été créée.
    assert pageContext.getAttribute ("TEMPLATE_PILE", PageContext.REQUEST_SCOPE) != null ;
    
    // Récupère la pile de template et y insère la nouvelle section.
    VPileTemplate lPileTemplate = (VPileTemplate) pageContext
      .getAttribute ("TEMPLATE_PILE", PageContext.REQUEST_SCOPE) ;
    lPileTemplate.ajouterSection (mRegion, new VSection (mContenu, mTypePage)) ;
    
    return SKIP_BODY ;
  }


  /**
   * Indique si la section à afficher est une page ou un simple texte
   * @param pTypePage Vrai si la section à afficher est une page ou Faux s'il s'agit de texte simple
   */
  public void setTypePage (String pTypePage)
  {
    mTypePage = pTypePage.equals ("true") ;
  }


  /**
   * Spécifie la région ou doit être insérée la section.
   * @param pRegion Région ou doit être insérée la section
   */
  public void setRegion (String pRegion)
  {
    mRegion = pRegion ;
  }


  /**
   * Spécifie la page ou le texte qui doit être inséré.
   * @param pContenu Page ou texte à insérer
   */
  public void setContenu (String pContenu)
  {
    mContenu = pContenu ;
  }
}