package owep.vue.template ;


import java.io.IOException ;
import javax.servlet.jsp.JspException ;
import javax.servlet.jsp.PageContext ;
import javax.servlet.jsp.tagext.TagSupport ;


/**
 * Tag à insérer dans le template d'une JSP pour définir une région. Cette région sera remplacée,
 * lors du traîtement de la page, par la section définie dans un tag VSectionTag.
 */
public class VRegionTag extends TagSupport
{
  private String mNom ; // Nom de la région, à partir de laquelle la section est récupérée


  /**
   * Récupère la section correspondant à la région, dans  la pile de template, puis l'affiche dans
   * la page.
   * @return Statut du tag.
   * @throws JspException S'il est impossible d'écrire la section sur le flux de sortie
   */
  public int doStartTag () throws JspException
  {
    // Vérifie que la pile de template a bien été créée.
    assert pageContext.getAttribute ("TEMPLATE_PILE", PageContext.REQUEST_SCOPE) != null ;
    
    // Récupère la section à inclure.
    VSection lSection = ((VPileTemplate) pageContext
      .getAttribute ("TEMPLATE_PILE", PageContext.REQUEST_SCOPE)).getSection (mNom) ;
    
    // Si la section à inclure a été définie
    if (lSection != null)
    {
      // Si la section est de type page, insère le contenu de celle-ci dans la région.
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
      // Si la section est de type texte, insère la valeur de celui-ci dans la région.
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
   * Initialise le nom de la région définie dans le template.
   * @param pNom Nom de la région définie dans le template
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }
}