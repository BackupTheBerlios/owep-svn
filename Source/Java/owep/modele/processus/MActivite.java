package owep.modele.processus ;


import java.util.ArrayList ;
import owep.modele.MModeleBase ;
import owep.modele.execution.MTache;
import owep.modele.processus.MDefinitionTravail ;


/**
 * Une activit� repr�sente une unit� de travail, c'est � dire un ensemble d'actions, effectu�e par
 * un r�le lors de l'ex�cution du processus. Une activit� peut n�cessiter des produits en entr�es
 * pour sa r�alisation et g�n�re des produits en sorties.
 */
public class MActivite extends MModeleBase
{
  private int                mId ;                // Identifie l'activit� de mani�re unique.
  private String             mNom ;               // Nom d�signant l'activit�.
  private String             mDescription ;       // Description de l'activit�.
  private ArrayList          mProduitsEntrees ;   // Liste des produits n�cessaires � l'activit�.
  private ArrayList          mProduitsSorties ;   // Liste des produits r�alis�s durant l'activit�.
  private ArrayList          mRoles ;             // Liste des r�les participant � l'activit�.
  private ArrayList          mTaches ;            // Liste des t�ches qui instancie l'activit�.
  private MDefinitionTravail mDefinitionTravail ; // D�finitions de travail contenant l'activit�.


  /**
   * Construit une instance vide de MActivite.
   */
  public MActivite ()
  {
    super () ;
    
    mProduitsEntrees = new ArrayList () ;
    mProduitsSorties = new ArrayList () ;
    mRoles           = new ArrayList () ;
  }


  /**
   * Construit une instance initialis�e de MActivite.
   * @param pId Identifiant unqiue de l'activit�.
   * @param pNom Nom d�signant l'activit�.
   * @param pDescription Description de l'activit�.
   * @param pDefinitionTravail �finition de travail � laquelle appartient l'activit�.
   */
  public MActivite (int pId, String pNom, String pDescription,
                    MDefinitionTravail pDefinitionTravail)
  {
    super () ;
    
    mId                 = pId ;
    mNom                = pNom ;
    mDescription        = pDescription ;
    mDefinitionTravail = pDefinitionTravail ;
    
    mProduitsEntrees = new ArrayList () ;
    mProduitsSorties = new ArrayList () ;
    mRoles           = new ArrayList () ;
  }


  /**
   * R�cup�re la d�finition de travail � laquelle appartient l'activit�.
   * @return D�finition de travail � laquelle appartient l'activit�.
   */
  public MDefinitionTravail getDefinitionsTravail ()
  {
    return mDefinitionTravail ;
  }


  /**
   * Associe la d�finition de travail � laquelle appartient l'activit�.
   * @param pDefinitionTravail D�finition de travail � laquelle appartient l'activit�.
   */
  public void setDefinitionsTravail (MDefinitionTravail pDefinitionTravail)
  {
    mDefinitionTravail = pDefinitionTravail ;
  }


  /**
   * R�cup�re la description de l'activit�.
   * @return Description de l'activit�.
   */
  public String getDescription ()
  {
    return mDescription ;
  }


  /**
   * Initialise la description de l'activit�.
   * @param pDescription Description de l'activit�.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }


  /**
   * R�cup�re l'identifiant de l'activit�.
   * @return Identifiant unique de l'activit�.
   */
  public int getId ()
  {
    return mId ;
  }


  /**
   * Initialise l'identifiant de l'activit�.
   * @param pId Identifiant unique de l'activit�.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }


  /**
   * R�cup�re le nom de l'activit�.
   * @return Nom d�signant l'activit�.
   */
  public String getNom ()
  {
    return mNom ;
  }


  /**
   * Initialise le nom de l'activit�.
   * @param pNom Nom d�signant l'activit�.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }


  /**
   * R�cup�re la liste des produits en entr�es.
   * @return Liste des produits n�cessaires � l'activit�.
   */
  public ArrayList getListeProduitsEntrees ()
  {
    return mProduitsEntrees ;
  }


  /**
   * Initialise la liste des produits en entr�es.
   * @param pProduitsEntrees Liste des produits n�cessaires � l'activit�.
   */
  public void setListeProduitsEntrees (ArrayList pProduitsEntrees)
  {
    mProduitsEntrees = pProduitsEntrees ;
  }


  /**
   * R�cup�re le nombre de produits en entr�es.
   * @return Nombre de produits n�cessaires � l'activit�.
   */
  public int getNbProduitsEntrees ()
  {
    return mProduitsEntrees.size () ;
  }


  /**
   * R�cup�re le produit d'indice sp�cifi� n�cessaire � l'activit�.
   * @param pIndice Indice du produit en entr�e dans la liste.
   * @return Produit n�cessaire � l'activit�.
   */
  public MProduit getProduitEntree (int pIndice)
  {
    return (MProduit) mProduitsEntrees.get (pIndice) ;
  }


  /**
   * Ajoute le produit sp�cifi� en entr�e de l'activit�.
   * @param pProduit Produit n�cessaire � l'activit�.
   */
  public void addProduitEntree (MProduit pProduit)
  {
    mProduitsEntrees.add (pProduit) ;
  }


  /**
   * R�cup�re la liste des produits en sorties.
   * @return Liste des produits r�alis�s au cours de l'activit�.
   */
  public ArrayList getListeProduitsSorties ()
  {
    return mProduitsSorties ;
  }


  /**
   * Initialise la liste des produits en sorties.
   * @param pProduitsSorties Liste des produits r�alis�s durant l'activit�.
   */
  public void setListeProduitsSorties (ArrayList pProduitsSorties)
  {
    mProduitsSorties = pProduitsSorties ;
  }


  /**
   * R�cup�re le nombre de produits en sorties.
   * @return Nombre de produits r�alis�s durant l'activit�.
   */
  public int getNbProduitsSorties ()
  {
    return mProduitsSorties.size () ;
  }


  /**
   * R�cup�re le produit d'indice sp�cifi� n�cessaire � l'activit�.
   * @param pIndice Indice du produit en sortie dans la liste.
   * @return Produit n�cessaire � l'activit�.
   */
  public MProduit getProduitSortie (int pIndice)
  {
    return (MProduit) mProduitsSorties.get (pIndice) ;
  }


  /**
   * Ajoute le produit sp�cifi� en sortie de l'activit�.
   * @param pProduit Produit n�cessaire � l'activit�.
   */
  public void addProduitSortie (MProduit pProduit)
  {
    mProduitsEntrees.add (pProduit) ;
  }


  /**
   * R�cup�re la liste des r�les.
   * @return Liste des r�les participant � l'activit�.
   */
  public ArrayList getListeRoles ()
  {
    return mRoles ;
  }


  /**
   * Initialise la liste des r�les.
   * @param pRoles Liste des r�les participant � l'activit�.
   */
  public void setListeRoles (ArrayList pRoles)
  {
    mRoles = pRoles ;
  }


  /**
   * R�cup�re le nombre de r�les participant � l'activit�.
   * @return Nombre de r�les participant � l'activit�.
   */
  public int getNbRolesSorties ()
  {
    return mProduitsEntrees.size () ;
  }


  /**
   * R�cup�re le r�le d'indice sp�cifi� participant � l'activit�.
   * @param pIndice Indice du r�le dans la liste.
   * @return R�le participant � l'activit�.
   */
  public MRole getRoleSortie (int pIndice)
  {
    return (MRole) mProduitsEntrees.get (pIndice) ;
  }


  /**
   * Ajoute le r�le sp�cifi� � l'activit�.
   * @param pRole R�le participant � l'activit�.
   */
  public void addRoleSortie (MRole pRole)
  {
    mProduitsEntrees.add (pRole) ;
  }


  /**
   * R�cup�re la liste des t�ches qui instancie l'activit�.
   * @return Liste des t�ches qui instancie l'activit�.
   */
  public ArrayList getListeTaches ()
  {
    return mTaches ;
  }


  /**
   * Initialise la liste des t�ches qui instancie l'activit�.
   * @param pTaches Liste des t�ches qui instancie l'activit�.
   */
  public void setListeTaches (ArrayList pTaches)
  {
    mTaches = pTaches ;
  }


  /**
   * R�cup�re le nombre de t�ches qui instancie l'activit�.
   * @return Nombre de t�ches qui instancie l'activit�.
   */
  public int getNbTaches ()
  {
    return mTaches.size () ;
  }


  /**
   * R�cup�re la t�che d'indice sp�cifi� qui instancie l'activit�.
   * @param pIndice Indice de la t�che dans la liste.
   * @return T�che qui instancie l'activit�.
   */
  public MTache getTache (int pIndice)
  {
    return (MTache) mTaches.get (pIndice) ;
  }


  /**
   * Ajoute la t�che sp�cifi�e qui instancie l'activit�.
   * @param pTache T�che qui instancie l'activit�.
   */
  public void addTache (MTache pTache)
  {
    mTaches.add (pTache) ;
  }
  
  
  /**
   * Supprime la t�che sp�cifi�e qui instancie l'activit�.
   * @param pTache T�che qui instancie l'activit�.
   */
  public void supprimerTache (MTache pTache)
  {
    mTaches.remove (pTache) ;
  }
}