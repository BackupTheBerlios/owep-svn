package owep.vue.transfert ;


import java.io.IOException;
import java.util.Stack;
import javax.servlet.jsp.JspException ;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport ;


/**
 * TODO Update these comments
 */
public class VTransfertChampTag extends TagSupport
{
  private static int COMPTEUR = 0 ;
  
  private String  mChamp ;
  private String  mMembre ;
  private String  mType ;
  private String  mLibelle ;     // Libell� du champ � afficher en cas d'erreur.
  private boolean mObligatoire ; // Indique si le champ est obligatoire.
  private String  mConvertor ;
  private String  mIdArbre ;     // Identifiant de l'arbre auquel le champ doit �tre ajout�.


  /**
   * TODO Update these comments
   */
  public int doStartTag () throws JspException
  {
    assert findAncestorWithClass (this, VTransfertBeanTag.class) != null ;
    assert pageContext.getAttribute (mIdArbre + VTransfertConstante.TRANSFERT_PILE, PageContext.REQUEST_SCOPE) != null ;
    
    Stack lPile = (Stack) pageContext.getAttribute (mIdArbre + VTransfertConstante.TRANSFERT_PILE, PageContext.REQUEST_SCOPE) ;
    ((VArbreBeans) lPile.peek ()).ajouterAssociation (new VChampBean (mChamp, mMembre, mType, mLibelle, mConvertor, mObligatoire)) ;
    
    try
    {
      VTransfert.setDernierChamp (mChamp) ; 
      pageContext.getOut ().print (" name=\"" + mChamp + "\" ") ;
      pageContext.getOut ().flush () ;
    }
    catch (IOException lException)
    {
      throw new JspException (lException.getMessage ()) ;
    }
    
    return SKIP_BODY ;
  }
  
  
  /**
   * TODO Update these comments
   */
  public String getChamp ()
  {
    return mChamp ;
  }
  
  
  /**
   * TODO Update these comments
   */
  public void setChamp (String pChamp)
  {
    mChamp = Integer.toString (pChamp.hashCode ()) ;
  }
  
  
  /**
   * R�cup�re l'identifiant de l'arbre auquel le champ doit �tre ajout�.
   * @return Identifiant de l'arbre auquel le champ doit �tre ajout�.
   */
  public String getIdArbre ()
  {
    return mIdArbre ;
  }


  /**
   * Initialise l'identifiant de l'arbre auquel le champ doit �tre ajout�.
   * @param pIdArbre Identifiant de l'arbre auquel le champ doit �tre ajout�.
   */
  public void setIdArbre (String pIdArbre)
  {
    mIdArbre = pIdArbre ;
  }


  /**
   * R�cup�re le libell� du champ � afficher en cas d'erreur.
   * @return Libell� du champ � afficher en cas d'erreur.
   */
  public String getLibelle ()
  {
    return mLibelle ;
  }


  /**
   * Initialise le libell� du champ � afficher en cas d'erreur.
   * @param pLibelle Libell� du champ � afficher en cas d'erreur.
   */
  public void setLibelle (String pLibelle)
  {
    mLibelle = pLibelle ;
  }


  /**
   * TODO Update these comments
   */
  public String getMembre ()
  {
    return mMembre ;
  }
  
  
  /**
   * TODO Update these comments
   */
  public void setMembre (String pMembre)
  {
    mMembre = pMembre ;
    mChamp  = "OWEPCHAMP_" + Integer.toString (COMPTEUR) ;
    COMPTEUR ++ ;
  }

  
  /**
   * R�cup�re la valeur indiquant si le champ est obligatoire.
   * @return Valeur indiquant si le champ est obligatoire.
   */
  public boolean getObligatoire ()
  {
    return mObligatoire ;
  }


  /**
   * Initialise la valeur indiquant si le champ est obligatoire.
   * @param pObligatoire Valeur indiquant si le champ est obligatoire.
   */
  public void setObligatoire (boolean pObligatoire)
  {
    mObligatoire = pObligatoire ;
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
  /**
   * TODO R�cup�re convertor.
   * @return convertor.
   */
  public String getConvertor ()
  {
    return mConvertor ;
  }
  /**
   * TODO Initialise convertor.
   * @param pConvertor convertor.
   */
  public void setConvertor (String pConvertor)
  {
    mConvertor = pConvertor ;
  }
}