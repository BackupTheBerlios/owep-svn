package owep.vue.transfert ;


import java.util.ArrayList ;
import java.util.Stack ;


/**
 * TODO Update these comments
 */
public class VArbreBeans
{
  public String      mNom ;          // Nom de l'arbre (champ initialisé que pour la racine).
  public String      mScope ;
  public String      mBean ;         //    
  public String      mTypes ;        //
  private boolean    mSoumission ;   // Indique si un bouton de soumission a été créé.
  public ArrayList   mEnfants ;      //
  public ArrayList   mAssociations ; //
  private VChampBean mBalise ;       //
  
  
  /**
   * TODO Update these comments
   */
  public VArbreBeans (String pScope, String pBean, String pType)
  {
    super () ;
    
    mScope        = pScope ;
    mBean         = pBean ;
    mTypes        = pType ;
    mSoumission   = false ;
    mEnfants      = new ArrayList () ;
    mAssociations = new ArrayList () ;
    mBalise       = null ;
  }


  public VArbreBeans (String pNom, String pScope, String pBean, String pType)
  {
    super () ;
    
    mNom          = pNom ;
    mScope        = pScope ;
    mBean         = pBean ;
    mTypes        = pType ;
    mSoumission   = false ;
    mEnfants      = new ArrayList () ;
    mAssociations = new ArrayList () ;
    mBalise       = null ;
  }


  public VArbreBeans (String pScope, String pBean, String pType, VChampBean pBalise)
  {
    super () ;
    
    mScope        = pScope ;
    mBean         = pBean ;
    mTypes        = pType ;
    mSoumission   = false ;
    mEnfants      = new ArrayList () ;
    mAssociations = new ArrayList () ;
    mBalise       = pBalise ;
  }


  /**
   * TODO Update these comments
   */
  public VChampBean getAssociation (int pIndice)
  {
    return (VChampBean) mAssociations.get (pIndice) ;
  }


  public int getNbAssociations ()
  {
    return mAssociations.size () ;
  }


  public void ajouterAssociation (VChampBean pBalise)
  {
    mAssociations.add (pBalise) ;
  }


  /**
   * TODO Update these comments
   */
  public String getBean ()
  {
    return mBean ;
  }


  /**
   * TODO Update these comments
   */
  public void setBean (String pBean)
  {
    mBean = pBean ;
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
  public int getNbEnfant ()
  {
    return mEnfants.size () ;
  }


  /**
   * TODO Update these comments
   */
  public VArbreBeans getEnfant (int pIndice)
  {
    return (VArbreBeans) mEnfants.get (pIndice) ;
  }


  /**
   * TODO Update these comments
   */
  public void ajouterEnfant (VArbreBeans pEnfants)
  {
    mEnfants.add (pEnfants) ;
  }


  /**
   * Récupère le nom de l'arbre.
   * @return Nom de l'arbre.
   */
  public String getNom ()
  {
    return mNom ;
  }


  /**
   * Initialise le nom de l'arbre.
   * @param pNom Nom de l'arbre.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }


  /**
   * TODO Update these comments
   */
  public class IterateurArbre
  {
    private VArbreBeans mParent ;
    private Stack       mPile ;


    /**
     * TODO Update these comments
     */
    IterateurArbre ()
    {
      mParent = null ;
      mPile = new Stack () ;
      mPile.push (VArbreBeans.this) ;
    }


    /**
     * TODO Update these comments
     */
    VArbreBeans suivant ()
    {
      VArbreBeans lHautPile = (VArbreBeans) mPile.peek () ;
      if (lHautPile.getNbEnfant () > 0)
      {
        mParent = lHautPile ;
        mPile.addAll (lHautPile.mEnfants) ;
        return suivant () ;
      }
      else
      {
        mParent.mEnfants.remove (mPile.peek ()) ;
        return (VArbreBeans) mPile.pop () ;
      }
    }
  }


  /**
   * Récupère la valeur indiquant si un bouton de soumission a été créé.
   * @return Valeur indiquant si un bouton de soumission a été créé.
   */
  protected boolean isSoumission ()
  {
    return mSoumission ;
  }
  
  
  /**
   * Initialise la valeur indiquant si un bouton de soumission a été créé.
   * @param pSoumission Valeur indiquant si un bouton de soumission a été créé.
   */
  protected void setSoumission (boolean pSoumission)
  {
    mSoumission = pSoumission ;
  }


  /**
   * TODO Update these comments
   */
  public String getType ()
  {
    return mTypes ;
  }


  /**
   * TODO Update these comments
   */
  public void setType (String pType)
  {
    mTypes = pType ;
  }


  /**
   * @return Retourne la valeur de l'attribut balise.
   */
  public VChampBean getBalise ()
  {
    return mBalise ;
  }


  /**
   * @param initialise balise avec pBalise.
   */
  public void setBalise (VChampBean pBalise)
  {
    mBalise = pBalise ;
  }
}