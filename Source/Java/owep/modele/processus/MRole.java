package owep.modele.processus ;


import java.util.ArrayList ;
import owep.modele.MModeleBase ;
import owep.modele.execution.MCollaborateur;


/**
 * Un rôle représente un "métier" (tel que architecte, etc.). Un collaborateur peut avoir plusieurs
 * rôle et inversement.
 */
public class MRole extends MModeleBase
{
  private int        mId ;             // Identifie le rôle de manière unique.
  private String     mNom ;            // Nom désignant le rôle.
  private String     mDescription ;    // Description du rôle.
  private ArrayList  mActivites ;      // Liste des activités auxquelles participent le rôle.
  private ArrayList  mProduits ;       // Liste des produits dont est responsable le rôle.
  private ArrayList  mCollaborateurs ; // Liste des collaborateurs qui tiennent le rôle.
  private MComposant mComposant ;      // Composant incluant le rôle.


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
   * @param pId Identifiant unique rôle.
   * @param pNom Nom désignant le rôle.
   * @param pDescription Description du rôle.
   * @param pComposant Composant incluant le rôle.
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
   * Récupère la liste des activités auxquelles participent le rôle.
   * @return Liste des activités auxquelles participent le rôle.
   */
  public ArrayList getListeActivites ()
  {
    return mActivites ;
  }


  /**
   * Initialise la liste des activités auxquelles participent le rôle.
   * @param pActivites Liste des activités auxquelles participent le rôle.
   */
  public void setListeActivites (ArrayList pActivites)
  {
    mActivites = pActivites ;
  }


  /**
   * Récupère le nombre d'activités auxquelles participent le rôle.
   * @return Nombre d'activités auxquelles participent le rôle.
   */
  public int getNbActivites ()
  {
    return mActivites.size () ;
  }


  /**
   * Récupère l'activité d'indice spécifié à laquelle participent le rôle.
   * @param pIndice Indice de l'activité dans la liste.
   */
  public void getActivite (int pIndice)
  {
    mActivites.get (pIndice) ;
  }


  /**
   * Ajoute l'activité spécifié au rôle.
   * @param pActivite Activité à laquelle participe le rôle.
   */
  public void addActivite (MActivite pActivite)
  {
    mActivites.add (pActivite) ;
  }


  /**
   * Récupère la liste des collaborateurs qui tiennent le rôle.
   * @return Liste des collaborateurs qui tiennent le rôle.
   */
  public ArrayList getListeCollaborateurs ()
  {
    return mCollaborateurs ;
  }


  /**
   * Initialise la liste des collaborateurs qui tiennent le rôle.
   * @param pCollaborateurs Liste des collaborateurs qui tiennent le rôle.
   */
  public void setListeCollaborateurs (ArrayList pCollaborateurs)
  {
    mCollaborateurs = pCollaborateurs ;
  }


  /**
   * Récupère le nombre de collaborateurs qui tiennent le rôle.
   * @return Nombre de collaborateurs qui tiennent le rôle.
   */
  public int getNbCollaborateurs ()
  {
    return mCollaborateurs.size () ;
  }


  /**
   * Récupère le collaborateur d'indice spécifié qui tient le rôle.
   * @param pIndice Indice du collaborateur dans la liste.
   */
  public MCollaborateur getCollaborateur (int pIndice)
  {
    return (MCollaborateur) mCollaborateurs.get (pIndice) ;
  }


  /**
   * Ajoute le collaborateur spécifié pour ce rôle.
   * @param pCollaborateur Collaborateur qui tient le rôle.
   */
  public void addCollaborateur (MCollaborateur pCollaborateur)
  {
    mCollaborateurs.add (pCollaborateur) ;
  }


  /**
   * Récupère l'activité d'indice spécifié à laquelle participe le rôle.
   * @param pIndice Indice de l'activité dans la liste.
   * @return Activité à laquelle participe le rôle.
   */
  public MActivite getComposant (int pIndice)
  {
    return (MActivite) mActivites.get (pIndice) ;
  }


  /**
   * Récupère le composant incluant le rôle.
   * @return Composant incluant le rôle.
   */
  public MComposant getComposant ()
  {
    return mComposant ;
  }


  /**
   * Associe le composant incluant le rôle.
   * @param pComposant Composant incluant le rôle.
   */
  public void setComposant (MComposant pComposant)
  {
    mComposant = pComposant ;
  }


  /**
   * Récupère la description du rôle.
   * @return Description du rôle.
   */
  public String getDescription ()
  {
    return mDescription ;
  }


  /**
   * Initialise la description du rôle.
   * @param pDescription Description du rôle.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }


  /**
   * Récupère l'identifiant du rôle.
   * @return Identifiant unique du rôle.
   */
  public int getId ()
  {
    return mId ;
  }


  /**
   * Initialise l'identifiant du rôle.
   * @param pId Identifiant unique du rôle.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }


  /**
   * Récupère le nom du rôle.
   * @return Nom désignant le rôle.
   */
  public String getNom ()
  {
    return mNom ;
  }


  /**
   * Initialise le nom du rôle.
   * @param pNom Nom désignant le rôle.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }


  /**
   * Récupère la liste des produits dont est responsable le rôle.
   * @return Liste des produits dont est responsable le rôle.
   */
  public ArrayList getListeProduits ()
  {
    return mProduits ;
  }


  /**
   * Initialise la liste des produits dont est responsable le rôle.
   * @param pProduits Liste des produits dont est responsable le rôle.
   */
  public void setListeProduits (ArrayList pProduits)
  {
    mProduits = pProduits ;
  }


  /**
   * Récupère le nombre des produits dont est responsable le rôle.
   * @return Nombre des produits dont est responsable le rôle.
   */
  public int getNbProduits ()
  {
    return mProduits.size () ;
  }


  /**
   * Récupère le produit d'indice spécifié dont est responsable le rôle.
   * @param pIndice Indice du produit dans la liste.
   * @return Produit dont est responsable le rôle.
   */
  public MProduit getProduit (int pIndice)
  {
    return (MProduit) mProduits.get (pIndice) ;
  }


  /**
   * Ajoute le produit spécifié au le rôle.
   * @param pProduit roduit dont est responsable le rôle.
   */
  public void addProduit (MProduit pProduit)
  {
    mProduits.add (pProduit) ;
  }
}