package owep.modele.processus ;


import java.util.ArrayList ;
import owep.modele.MModeleBase ;
import owep.modele.execution.MCollaborateur;


/**
 * Un r�le repr�sente un "m�tier" (tel que architecte, etc.). Un collaborateur peut avoir plusieurs
 * r�le et inversement.
 */
public class MRole extends MModeleBase
{
  private int        mId ;             // Identifie le r�le de mani�re unique.
  private String     mNom ;            // Nom d�signant le r�le.
  private String     mDescription ;    // Description du r�le.
  private ArrayList  mActivites ;      // Liste des activit�s auxquelles participent le r�le.
  private ArrayList  mProduits ;       // Liste des produits dont est responsable le r�le.
  private ArrayList  mCollaborateurs ; // Liste des collaborateurs qui tiennent le r�le.
  private MComposant mComposant ;      // Composant incluant le r�le.


  /**
   * Construit une nouvelle instance de MRole.
   */
  public MRole ()
  {
    super () ;
    
    mActivites = new ArrayList () ;
    mProduits  = new ArrayList () ;
  }


  /**
   * Construit une nouvelle instance de MRole.
   * @param pId Identifiant unique r�le.
   * @param pNom Nom d�signant le r�le.
   * @param pDescription Description du r�le.
   * @param pComposant Composant incluant le r�le.
   */
  public MRole (int pId, String pNom, String pDescription, MComposant pComposant)
  {
    super () ;
    
    mId          = pId ;
    mNom         = pNom ;
    mDescription = pDescription ;
    mComposant   = pComposant ;
    
    mActivites = new ArrayList () ;
    mProduits  = new ArrayList () ;
  }


  /**
   * R�cup�re la liste des activit�s auxquelles participent le r�le.
   * @return Liste des activit�s auxquelles participent le r�le.
   */
  public ArrayList getListeActivites ()
  {
    return mActivites ;
  }


  /**
   * Initialise la liste des activit�s auxquelles participent le r�le.
   * @param pActivites Liste des activit�s auxquelles participent le r�le.
   */
  public void setListeActivites (ArrayList pActivites)
  {
    mActivites = pActivites ;
  }


  /**
   * R�cup�re le nombre d'activit�s auxquelles participent le r�le.
   * @return Nombre d'activit�s auxquelles participent le r�le.
   */
  public int getNbActivites ()
  {
    return mActivites.size () ;
  }


  /**
   * R�cup�re l'activit� d'indice sp�cifi� � laquelle participent le r�le.
   * @param pIndice Indice de l'activit� dans la liste.
   */
  public void getActivite (int pIndice)
  {
    mActivites.get (pIndice) ;
  }


  /**
   * Ajoute l'activit� sp�cifi� au r�le.
   * @param pActivite Activit� � laquelle participe le r�le.
   */
  public void addActivite (MActivite pActivite)
  {
    mActivites.add (pActivite) ;
  }


  /**
   * R�cup�re la liste des collaborateurs qui tiennent le r�le.
   * @return Liste des collaborateurs qui tiennent le r�le.
   */
  public ArrayList getListeCollaborateurs ()
  {
    return mCollaborateurs ;
  }


  /**
   * Initialise la liste des collaborateurs qui tiennent le r�le.
   * @param pCollaborateurs Liste des collaborateurs qui tiennent le r�le.
   */
  public void setListeCollaborateurs (ArrayList pCollaborateurs)
  {
    mCollaborateurs = pCollaborateurs ;
  }


  /**
   * R�cup�re le nombre de collaborateurs qui tiennent le r�le.
   * @return Nombre de collaborateurs qui tiennent le r�le.
   */
  public int getNbCollaborateurs ()
  {
    return mCollaborateurs.size () ;
  }


  /**
   * R�cup�re le collaborateur d'indice sp�cifi� qui tient le r�le.
   * @param pIndice Indice du collaborateur dans la liste.
   */
  public MCollaborateur getCollaborateur (int pIndice)
  {
    return (MCollaborateur) mCollaborateurs.get (pIndice) ;
  }


  /**
   * Ajoute le collaborateur sp�cifi� pour ce r�le.
   * @param pCollaborateur Collaborateur qui tient le r�le.
   */
  public void addCollaborateur (MCollaborateur pCollaborateur)
  {
    mCollaborateurs.add (pCollaborateur) ;
  }


  /**
   * R�cup�re l'activit� d'indice sp�cifi� � laquelle participe le r�le.
   * @param pIndice Indice de l'activit� dans la liste.
   * @return Activit� � laquelle participe le r�le.
   */
  public MActivite getComposant (int pIndice)
  {
    return (MActivite) mActivites.get (pIndice) ;
  }


  /**
   * R�cup�re le composant incluant le r�le.
   * @return Composant incluant le r�le.
   */
  public MComposant getComposant ()
  {
    return mComposant ;
  }


  /**
   * Associe le composant incluant le r�le.
   * @param pComposant Composant incluant le r�le.
   */
  public void setComposant (MComposant pComposant)
  {
    mComposant = pComposant ;
  }


  /**
   * R�cup�re la description du r�le.
   * @return Description du r�le.
   */
  public String getDescription ()
  {
    return mDescription ;
  }


  /**
   * Initialise la description du r�le.
   * @param pDescription Description du r�le.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }


  /**
   * R�cup�re l'identifiant du r�le.
   * @return Identifiant unique du r�le.
   */
  public int getId ()
  {
    return mId ;
  }


  /**
   * Initialise l'identifiant du r�le.
   * @param pId Identifiant unique du r�le.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }


  /**
   * R�cup�re le nom du r�le.
   * @return Nom d�signant le r�le.
   */
  public String getNom ()
  {
    return mNom ;
  }


  /**
   * Initialise le nom du r�le.
   * @param pNom Nom d�signant le r�le.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }


  /**
   * R�cup�re la liste des produits dont est responsable le r�le.
   * @return Liste des produits dont est responsable le r�le.
   */
  public ArrayList getListeProduits ()
  {
    return mProduits ;
  }


  /**
   * Initialise la liste des produits dont est responsable le r�le.
   * @param pProduits Liste des produits dont est responsable le r�le.
   */
  public void setListeProduits (ArrayList pProduits)
  {
    mProduits = pProduits ;
  }


  /**
   * R�cup�re le nombre des produits dont est responsable le r�le.
   * @return Nombre des produits dont est responsable le r�le.
   */
  public int getNbProduits ()
  {
    return mProduits.size () ;
  }


  /**
   * R�cup�re le produit d'indice sp�cifi� dont est responsable le r�le.
   * @param pIndice Indice du produit dans la liste.
   * @return Produit dont est responsable le r�le.
   */
  public MProduit getProduit (int pIndice)
  {
    return (MProduit) mProduits.get (pIndice) ;
  }


  /**
   * Ajoute le produit sp�cifi� au le r�le.
   * @param pProduit roduit dont est responsable le r�le.
   */
  public void addProduit (MProduit pProduit)
  {
    mProduits.add (pProduit) ;
  }
}