package owep.vue.template ;


import java.io.IOException ;
import javax.servlet.jsp.JspException ;
import javax.servlet.jsp.PageContext ;
import javax.servlet.jsp.tagext.TagSupport ;


/**
 * Tag � ins�rer dans le template d'une JSP pour d�finir une r�gion. Cette r�gion sera remplac�e,
 * lors du tra�tement de la page, par la section d�finie dans un tag VSectionTag.
 */
public class VRegionTag extends TagSupport
{
  private String mNom ; // Nom de la r�gion, � partir de laquelle la section est r�cup�r�e


  /**
   * R�cup�re la section correspondant � la r�gion, dans  la pile de template, puis l'affiche dans
   * la page.
   * @return Statut du tag.
   * @throws JspException S'il est impossible d'�crire la section sur le flux de sortie
   */
  public int doStartTag () throws JspException
  {
    // V�rifie que la pile de template a bien �t� cr��e.
    assert pageContext.getAttribute ("TEMPLATE_PILE", PageContext.REQUEST_SCOPE) != null ;
    
    // R�cup�re la section � inclure.
    VSection lSection = ((VPileTemplate) pageContext
      .getAttribute ("TEMPLATE_PILE", PageContext.REQUEST_SCOPE)).getSection (mNom) ;
    
    // Si la section � inclure a �t� d�finie
    if (lSection != null)
    {
      // Si la section est de type page, ins�re le contenu de celle-ci dans la r�gion.
      if (lSection.isTypePage ())
      {
        // Affiche le texte
        try
        {
          pageContext.getOut ().flush () ;
          pageContext.include (lSection.getContenu ()) ;
        }
        catch (Exception lException)
        {
          throw new JspException (lException.getMessage ()) ;
        }
      }
      else
      // Si la section est de type texte, ins�re la valeur de celui-ci dans la r�gion.
      {
        try
        {
          pageContext.getOut ().print (lSection.getContenu ()) ;
          pageContext.getOut ().flush () ;
        }
        catch (IOException lException)
        {
          throw new JspException (lException.getMessage ()) ;
        }
      }
    }
    return SKIP_BODY ;
  }


  /**
   * Initialise le nom de la r�gion d�finie dans le template.
   * @param pNom Nom de la r�gion d�finie dans le template
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }
}