package owep.vue.transfert;

/**
 * Cette classe permet de d�finir les attributs d'une balise tranfertchamp
 * de la jsp.
 */
public class VChampBean
{
  private String  mChamp ;       // 
  private String  mMembre ;      // Nom de la m�thode � appeler.
  private String  mType ;        // Type du champ.
  private String  mLibelle ;     // Libell� du champ � afficher en cas d'erreur.
  private String  mConvertor ;   // Nom de la classe permettant la convertion.
  private boolean mObligatoire ; // Indique si le champ est obligatoire.


  /**
   * R�cup�re la valeur indiquant si le champ est obligatoire.
   * @return Valeur indiquant si le champ est obligatoire.
   */
  public boolean isObligatoire ()
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
   * Construit une instance initialis�e de VChampBean.
   * @param pChamp
   * @param pMembre Nom de la m�thode � appeler.
   * @param pType Type du champ.
   * @param pLibelle Libell� du champ � afficher en cas d'erreur.
   * @param pConvertor Nom de la classe permettant la convertion.
   */
  public VChampBean (String pChamp, String pMembre, String pType, String pLibelle, String pConvertor, boolean pObligatoire)
  {
    mChamp       = pChamp ;
    mMembre      = pMembre ;
    mType        = pType ;
    mLibelle     = pLibelle ;
    mConvertor   = pConvertor ;
    mObligatoire = pObligatoire ;
  }


  /**
   * 
   * TODO Description de getChamp.
   * @return
   */
  public String getChamp ()
  {
    return mChamp ;
  }
  
  
  /**
   * R�cup�re le nom de la m�thode � appeler. 
   * @return le nom de la m�thode � appeler.
   */
  public String getMembre ()
  {
    return mMembre ;
  }
  
  
  /**
   * R�cup�re le type du champ.
   * @return le type du champ.
   */
  public String getType ()
  {
    return mType ;
  }
  
  /**
   * R�cup�re le nom de la classe permettant la convertion.
   * @return le nom de la classe permettant la convertion.
   */
  public String getConvertor ()
  {
    return mConvertor ;
  }
  
  
  /**
   * @param pChamp initialise champ avec pChamp.
   */
  public void setChamp (String pChamp)
  {
    mChamp = pChamp ;
  }
  
  
  /**
   * Initialise le nom de la m�thode � appeler.
   * @param pMembre nom de la m�thode � appeler.
   */
  public void setMembre (String pMembre)
  {
    mMembre = pMembre ;
  }
  
  
  /**
   * Initialise le type du champ.
   * @param pType type du champ.
   */
  public void setType (String pType)
  {
    mType = pType ;
  }
  
    
  /**
   * Initialise le nom de la classe permettant la convertion.
   * @param pConvertor nom de la classe permettant la convertion.
   */
  public void setConvertor (String pConvertor)
  {
    mConvertor = pConvertor ;
  }
}
