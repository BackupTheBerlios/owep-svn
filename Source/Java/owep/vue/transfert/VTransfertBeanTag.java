package owep.vue.transfert ;


import java.util.ArrayList;
import java.util.Stack;
import javax.servlet.jsp.JspException ;
import javax.servlet.jsp.PageContext ;
import javax.servlet.jsp.tagext.TagSupport ;


/**
 * TODO Update these comments
 */
public class VTransfertBeanTag extends TagSupport
{
  private Stack  mPile ;    // Pile qui contiendra les différents ArbreBeans
  private String mScope ;   // Portée du tag.
  private String mBean ;    // Nom du bean utilisé dans la jsp.
  private String mType ;    // Type du bean.
  private String mIdArbre ; // Identifiant de l'arbre à tranférer.


  /**
   * Cette méthode permet de rajouter un nouvel arbre suite à une nouvelle balise dans la jsp.
   * @return Statut du tag.
   * @throws JspException si une erreur survient lors du traitemnt sur la balise
   */
  public int doStartTag () throws JspException
  {
    mPile = (Stack) pageContext.getAttribute (mIdArbre + VTransfertConstante.TRANSFERT_PILE, PageContext.REQUEST_SCOPE) ;
    
    if (mPile == null || mPile.size () == 0)
    {
      ArrayList lListeArbres = (ArrayList) pageContext.getAttribute (VTransfertConstante.TRANSFERT_LISTEARBRES, PageContext.REQUEST_SCOPE) ;
      
      if (lListeArbres == null) 
      {
        lListeArbres = new ArrayList () ;
        pageContext.setAttribute (VTransfertConstante.TRANSFERT_LISTEARBRES, lListeArbres, PageContext.REQUEST_SCOPE) ;
      }
      mPile = new Stack () ;
      mPile.push (new VArbreBeans (mIdArbre, mScope, mBean, mType)) ;
      lListeArbres.add (mPile.peek ()) ;
      pageContext.setAttribute (mIdArbre + VTransfertConstante.TRANSFERT_PILE, mPile, PageContext.REQUEST_SCOPE) ;
      pageContext.setAttribute (mIdArbre, mPile.peek (), PageContext.SESSION_SCOPE) ;
    }
    else
    {
      VArbreBeans lBeanCourant = new VArbreBeans (mScope, mBean, mType) ;
      ((VArbreBeans) mPile.peek ()).ajouterEnfant (lBeanCourant) ;
      mPile.push (lBeanCourant) ;
    }
    
    return EVAL_BODY_INCLUDE ;
  }


  /**
   * Supprime l'arbre correspondant à la balise fermente.
   * @return Statut du tag.
   * @throws JspException si une erreur survient lors du traitement de la balise.
   */
  public int doEndTag () throws JspException
  {
    mPile.pop () ;
    
    return EVAL_PAGE ;
  }


  /**
   * Récupère le nom du bean utilisé dans la jsp.
   * @return le nom du bean utilisé dans la jsp.
   */
  public String getBean ()
  {
    return mBean ;
  }
  
  
  /**
   * Initialise le nom du bean utilisé dans la jsp.
   * @param pBean le nom du bean utilisé dans la jsp.
   */
  public void setBean (String pBean)
  {
    mBean = pBean ;
  }
  
  
  /**
   * Récupère l'identifiant de l'arbre à tranférer.
   * @return Identifiant de l'arbre à tranférer.
   */
  public String getIdArbre ()
  {
    return mIdArbre ;
  }


  /**
   * Initialise l'identifiant de l'arbre à tranférer.
   * @param pIdArbre Identifiant de l'arbre à tranférer.
   */
  public void setIdArbre (String pIdArbre)
  {
    mIdArbre = pIdArbre ;
  }


  /**
   * TODO Update these comments
   */
  public String getScope ()
  {
    return mScope ;
  }
  
  
  /**
   * TODO Update these comments
   */
  public void setScope (String pScope)
  {
    mScope = pScope ;
  }
  
  
  /**
   * TODO Update these comments
   */
  public String getType ()
  {
    return mType ;
  }
  
  
  /**
   * TODO Update these comments
   */
  public void setType (String pType)
  {
    mType = pType ;
  }
}