package owep.modele.processus ;


import java.util.ArrayList ;
import java.util.Iterator ;

import owep.modele.MModeleBase ;
import owep.modele.execution.MTache ;
import owep.modele.processus.MDefinitionTravail ;


/**
 * Une activité représente une unité de travail, c'est à dire un ensemble d'actions, effectuée par
 * un rôle lors de l'exécution du processus. Une activité peut nécessiter des produits en entrées
 * pour sa réalisation et génère des produits en sorties.
 */
public class MActivite extends MModeleBase
{
  private int                mId ;               // Identifie l'activité de manière unique.
  private String             mNom ;              // Nom désignant l'activité.
  private String             mDescription ;      // Description de l'activité.
  private ArrayList          mProduitsEntrees ;  // Liste des produits nécessaires à l'activité.
  private ArrayList          mProduitsSorties ;  // Liste des produits réalisés durant l'activité.
  private ArrayList          mRoles ;            // Liste des rôles participant à l'activité.
  private ArrayList          mTaches ;           // Liste des tâches qui instancie l'activité.
  private MDefinitionTravail mDefinitionTravail ; // Définitions de travail contenant l'activité.
  private String             mIdDpe ;            // Identifiant du dpe


  /**
   * Construit une instance vide de MActivite.
   */
  public MActivite ()
  {
    super () ;

    mProduitsEntrees = new ArrayList () ;
    mProduitsSorties = new ArrayList () ;
    mRoles = new ArrayList () ;
    mTaches = new ArrayList () ;
  }

  /**
   * Construit une instance de MActivite avec l'id.
   */
  public MActivite (int pId)
  {
    super () ;
    
    mId = pId;

    mProduitsEntrees = new ArrayList () ;
    mProduitsSorties = new ArrayList () ;
    mRoles = new ArrayList () ;
    mTaches = new ArrayList () ;
  }

  /**
   * Construit une instance initialisée de MActivite.
   * 
   * @param pId Identifiant unqiue de l'activité.
   * @param pNom Nom désignant l'activité.
   * @param pDescription Description de l'activité.
   * @param pDefinitionTravail éfinition de travail à laquelle appartient l'activité.
   */
  public MActivite (int pId, String pNom, String pDescription, MDefinitionTravail pDefinitionTravail)
  {
    super () ;

    mId = pId ;
    mNom = pNom ;
    mDescription = pDescription ;
    mDefinitionTravail = pDefinitionTravail ;

    mProduitsEntrees = new ArrayList () ;
    mProduitsSorties = new ArrayList () ;
    mRoles = new ArrayList () ;
    mTaches = new ArrayList () ;
  }

  /**
   * Récupère la définition de travail à laquelle appartient l'activité.
   * 
   * @return Définition de travail à laquelle appartient l'activité.
   */
  public MDefinitionTravail getDefinitionsTravail ()
  {
    return mDefinitionTravail ;
  }

  /**
   * Associe la définition de travail à laquelle appartient l'activité.
   * 
   * @param pDefinitionTravail Définition de travail à laquelle appartient l'activité.
   */
  public void setDefinitionsTravail (MDefinitionTravail pDefinitionTravail)
  {
    mDefinitionTravail = pDefinitionTravail ;
    ArrayList lActivite = pDefinitionTravail.getListeActivites () ;
    if (!lActivite.contains (this))
    {
      pDefinitionTravail.addActivite (this) ;
    }
  }

  /**
   * Récupère la description de l'activité.
   * 
   * @return Description de l'activité.
   */
  public String getDescription ()
  {
    return mDescription ;
  }

  /**
   * Initialise la description de l'activité.
   * 
   * @param pDescription Description de l'activité.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }

  /**
   * Récupère l'identifiant de l'activité.
   * 
   * @return Identifiant unique de l'activité.
   */
  public int getId ()
  {
    return mId ;
  }

  /**
   * Initialise l'identifiant de l'activité.
   * 
   * @param pId Identifiant unique de l'activité.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }

  /**
   * Récupère le nom de l'activité.
   * 
   * @return Nom désignant l'activité.
   */
  public String getNom ()
  {
    return mNom ;
  }

  /**
   * Initialise le nom de l'activité.
   * 
   * @param pNom Nom désignant l'activité.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }

  /**
   * Récupère la liste des produits en entrées.
   * 
   * @return Liste des produits nécessaires à l'activité.
   */
  public ArrayList getListeProduitsEntrees ()
  {
    return mProduitsEntrees ;
  }

  /**
   * Initialise la liste des produits en entrées.
   * 
   * @param pProduitsEntrees Liste des produits nécessaires à l'activité.
   */
  public void setListeProduitsEntrees (ArrayList pProduitsEntrees)
  {
    mProduitsEntrees = pProduitsEntrees ;
    MProduit lProduitEntree ;
    Iterator it = pProduitsEntrees.iterator () ;
    while (it.hasNext ())
    {
      lProduitEntree = (MProduit) it.next () ;
      ArrayList lListActivite = lProduitEntree.getListeActivitesSorties () ;
      if (!lListActivite.contains (this))
      {
        lProduitEntree.addActiviteSortie (this) ;
      }
    }
  }

  /**
   * Récupère le nombre de produits en entrées.
   * 
   * @return Nombre de produits nécessaires à l'activité.
   */
  public int getNbProduitsEntrees ()
  {
    return mProduitsEntrees.size () ;
  }

  /**
   * Récupère le produit d'indice spécifié nécessaire à l'activité.
   * 
   * @param pIndice Indice du produit en entrée dans la liste.
   * @return Produit nécessaire à l'activité.
   */
  public MProduit getProduitEntree (int pIndice)
  {
    return (MProduit) mProduitsEntrees.get (pIndice) ;
  }

  /**
   * Ajoute le produit spécifié en entrée de l'activité.
   * 
   * @param pProduit Produit nécessaire à l'activité.
   */
  public void addProduitEntree (MProduit pProduit)
  {
    if (!mProduitsEntrees.contains (pProduit))
    {
      mProduitsEntrees.add (pProduit) ;
    }
    ArrayList lListActivite = pProduit.getListeActivitesSorties () ;
    if (!lListActivite.contains (this))
    {
      pProduit.addActiviteSortie (this) ;
    }
  }

  /**
   * Récupère la liste des produits en sorties.
   * 
   * @return Liste des produits réalisés au cours de l'activité.
   */
  public ArrayList getListeProduitsSorties ()
  {
    return mProduitsSorties ;
  }

  /**
   * Initialise la liste des produits en sorties.
   * 
   * @param pProduitsSorties Liste des produits réalisés durant l'activité.
   */
  public void setListeProduitsSorties (ArrayList pProduitsSorties)
  {
    mProduitsSorties = pProduitsSorties ;
    ArrayList lListActivite ;
    Iterator it = pProduitsSorties.iterator () ;
    MProduit lProduit ;
    while (it.hasNext ())
    {
      lProduit = (MProduit) it.next () ;
      lListActivite = lProduit.getListeActivitesEntrees () ;
      if (!lListActivite.contains (this))
      {
        lProduit.addActiviteEntree (this) ;
      }
    }
  }

  /**
   * Récupère le nombre de produits en sorties.
   * 
   * @return Nombre de produits réalisés durant l'activité.
   */
  public int getNbProduitsSorties ()
  {
    return mProduitsSorties.size () ;
  }

  /**
   * Récupère le produit d'indice spécifié nécessaire à l'activité.
   * 
   * @param pIndice Indice du produit en sortie dans la liste.
   * @return Produit nécessaire à l'activité.
   */
  public MProduit getProduitSortie (int pIndice)
  {
    return (MProduit) mProduitsSorties.get (pIndice) ;
  }

  /**
   * Ajoute le produit spécifié en sortie de l'activité.
   * 
   * @param pProduit Produit nécessaire à l'activité.
   */
  public void addProduitSortie (MProduit pProduit)
  {
    if (!mProduitsSorties.contains (pProduit))
    {
      mProduitsSorties.add (pProduit) ;
    }
    ArrayList lListActivite = pProduit.getListeActivitesEntrees () ;
    if (!lListActivite.contains (this))
    {
      pProduit.addActiviteEntree (this) ;
    }
  }

  /**
   * Récupère la liste des rôles.
   * 
   * @return Liste des rôles participant à l'activité.
   */
  public ArrayList getListeRoles ()
  {
    return mRoles ;
  }

  /**
   * Initialise la liste des rôles.
   * 
   * @param pRoles Liste des rôles participant à l'activité.
   */
  public void setListeRoles (ArrayList pRoles)
  {
    mRoles = pRoles ;
    Iterator it = pRoles.iterator () ;
    MRole lRole ;
    while (it.hasNext ())
    {
      lRole = (MRole) it.next () ;
      ArrayList lListActivite = lRole.getListeActivites () ;
      if (!lListActivite.contains (this))
      {
        lRole.addActivite (this) ;
      }
    }
  }

  /**
   * Récupère le nombre de rôles participant à l'activité.
   * 
   * @return Nombre de rôles participant à l'activité.
   */
  public int getNbRoles ()
  {
    return mRoles.size () ;
  }

  /**
   * Récupère le rôle d'indice spécifié participant à l'activité.
   * 
   * @param pIndice Indice du rôle dans la liste.
   * @return Rôle participant à l'activité.
   */
  public MRole getRole (int pIndice)
  {
    return (MRole) mRoles.get (pIndice) ;
  }

  /**
   * Ajoute le rôle spécifié à l'activité.
   * 
   * @param pRole Rôle participant à l'activité.
   */
  public void addRole (MRole pRole)
  {
    if (!mRoles.contains (pRole))
    {
      mRoles.add (pRole) ;
    }
    ArrayList lListActivite = pRole.getListeActivites () ;
    if (!lListActivite.contains (this))
    {
      pRole.addActivite (this) ;
    }
  }

  /**
   * Récupère la liste des tâches qui instancie l'activité.
   * 
   * @return Liste des tâches qui instancie l'activité.
   */
  public ArrayList getListeTaches ()
  {
    return mTaches ;
  }

  /**
   * Initialise la liste des tâches qui instancie l'activité.
   * 
   * @param pTaches Liste des tâches qui instancie l'activité.
   */
  public void setListeTaches (ArrayList pTaches)
  {
    mTaches = pTaches ;
    Iterator it = pTaches.iterator () ;
    MTache lTache ;
    while (it.hasNext ())
    {
      lTache = (MTache) it.next () ;
      MActivite lActivite = lTache.getActivite () ;
      if (lActivite != this)
      {
        lTache.setActivite (this) ;
      }
    }
  }

  /**
   * Récupère le nombre de tâches qui instancie l'activité.
   * 
   * @return Nombre de tâches qui instancie l'activité.
   */
  public int getNbTaches ()
  {
    return mTaches.size () ;
  }

  /**
   * Récupère la tâche d'indice spécifié qui instancie l'activité.
   * 
   * @param pIndice Indice de la tâche dans la liste.
   * @return Tâche qui instancie l'activité.
   */
  public MTache getTache (int pIndice)
  {
    return (MTache) mTaches.get (pIndice) ;
  }

  /**
   * Ajoute la tâche spécifiée qui instancie l'activité.
   * 
   * @param pTache Tâche qui instancie l'activité.
   */
  public void addTache (MTache pTache)
  {
    if (!mTaches.contains (pTache))
    {
      mTaches.add (pTache) ;
    }
    MActivite lActivite = pTache.getActivite () ;
    if (lActivite != this)
    {
      pTache.setActivite (this) ;
    }
  }

  /**
   * Supprime la tâche spécifiée qui instancie l'activité.
   * 
   * @param pTache Tâche qui instancie l'activité.
   */
  public void supprimerTache (MTache pTache)
  {
    mTaches.remove (pTache) ;
  }

  /**
   * Récupère l'identifiant du DPE.
   * 
   * @return Retourne la valeur de l'attribut idDpe.
   */
  public String getIdDpe ()
  {
    return mIdDpe ;
  }

  /**
   * Initialise avec l'identifiant du DPE.
   * 
   * @param initialse idDpe avec pIdDpe.
   */
  public void setIdDpe (String pIdDpe)
  {
    mIdDpe = pIdDpe ;
  }
}