package owep.modele.processus ;


import java.util.ArrayList ;
import java.util.Iterator ;

import owep.modele.MModeleBase ;


/**
 * Un composant représente un morceau de processus réutilisable et remplaçable.
 */
public class MComposant extends MModeleBase
{
  private int        mId ;                // Identifie le composant de manière unique.
  private String     mNom         = "" ;  // Nom désignant le composant.
  private String     mDescription = "" ;  // Description du composant.
  private String     mNomAuteur   = "" ;  // Nom de l'auteur du composant.
  private String     mEmailAuteur = "" ;  // Email de l'auteur du composant.
  private ArrayList  mDefinitionsTravail ; // Liste des définitions de travail du composant.
  private ArrayList  mProduits ;          // Liste des produits définis dans le composant.
  private ArrayList  mRoles ;             // Liste des rôles définis dans le composant.
  private MProcessus mProcessus ;         // Processus possédant le composant.
  private String     mIdDpe       = "" ;  // Identifiant du dpe


  /**
   * Construit une instance vide de MComposant.
   */
  public MComposant ()
  {
    super () ;

    mDefinitionsTravail = new ArrayList () ;
    mProduits = new ArrayList () ;
    mRoles = new ArrayList () ;
  }

  /**
   * Construit une instance de MComposant avec l'id.
   */
  public MComposant (int pId)
  {
    super () ;

    mId = pId ;

    mDefinitionsTravail = new ArrayList () ;
    mProduits = new ArrayList () ;
    mRoles = new ArrayList () ;
  }

  /**
   * Construit une instance initialisée de MComposant.
   * 
   * @param pId Identifiant unique du composant.
   * @param pNom Nom désignant le composant.
   * @param pDescription Description du composant.
   * @param pNomAuteur Nom de l'auteur du composant.
   * @param pEmailAuteur Email de l'auteur du composant.
   * @param pProcessus Processus possédant le composant.
   */
  public MComposant (int pId, String pNom, String pDescription, String pNomAuteur,
                     String pEmailAuteur, MProcessus pProcessus)
  {
    super () ;

    mId = pId ;
    mNom = pNom ;
    mDescription = pDescription ;
    mNomAuteur = pNomAuteur ;
    mEmailAuteur = pEmailAuteur ;
    mProcessus = pProcessus ;

    mDefinitionsTravail = new ArrayList () ;
    mProduits = new ArrayList () ;
    mRoles = new ArrayList () ;
  }

  /**
   * Récupère la liste des definitions de travail.
   * 
   * @return Liste des définitions de travail du composant.
   */
  public ArrayList getListeDefinitionsTravail ()
  {
    return mDefinitionsTravail ;
  }

  /**
   * Initialise la liste des definitions de travail.
   * 
   * @param pDefinitionsTravail Liste des définitions de travail du composant.
   */
  public void setListeDefinitionsTravail (ArrayList pDefinitionsTravail)
  {
    mDefinitionsTravail = pDefinitionsTravail ;
    Iterator it = pDefinitionsTravail.iterator () ;
    MDefinitionTravail lDefinitionTravail ;
    while (it.hasNext ())
    {
      lDefinitionTravail = (MDefinitionTravail) it.next () ;
      MComposant lComposant = lDefinitionTravail.getComposant () ;
      if (lComposant != this)
      {
        lDefinitionTravail.setComposant (this) ;
      }
    }
  }

  /**
   * Récupère le nombre de définitions de travail du composant.
   * 
   * @return Nombre de définitions de travail du composant.
   */
  public int getNbDefinitionsTravail ()
  {
    return mDefinitionsTravail.size () ;
  }

  /**
   * Récupère la définition de travail d'indice spécifiée.
   * 
   * @param pIndice Indice de la définition de travail dans la liste.
   * @return Définition de travail du composant.
   */
  public MDefinitionTravail getDefinitionTravail (int pIndice)
  {
    return (MDefinitionTravail) mDefinitionsTravail.get (pIndice) ;
  }

  /**
   * Ajoute la définition de travail au composant.
   * 
   * @param pDefinitionTravail Définition de travail du composant.
   */
  public void addDefinitionTravail (MDefinitionTravail pDefinitionTravail)
  {
    if (!mDefinitionsTravail.contains (pDefinitionTravail))
    {
      mDefinitionsTravail.add (pDefinitionTravail) ;
    }
    MComposant lComposant = pDefinitionTravail.getComposant () ;
    if (lComposant != this)
    {
      pDefinitionTravail.setComposant (this) ;
    }
  }

  /**
   * Récupère la description du composant.
   * 
   * @return Description du composant.
   */
  public String getDescription ()
  {
    return mDescription ;
  }

  /**
   * Initialise la description du composant.
   * 
   * @param pDescription Description du composant.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }

  /**
   * Récupère l'email de l'auteur du composant.
   * 
   * @return Email de l'auteur du composant.
   */
  public String getEmailAuteur ()
  {
    return mEmailAuteur ;
  }

  /**
   * Initialise l'email de l'auteur du composant.
   * 
   * @param pEmailAuteur Email de l'auteur du composant.
   */
  public void setEmailAuteur (String pEmailAuteur)
  {
    mEmailAuteur = pEmailAuteur ;
  }

  /**
   * Récupère l'identifiant du composant.
   * 
   * @return Identifiant unique du composant.
   */
  public int getId ()
  {
    return mId ;
  }

  /**
   * Initialise l'identifiant du composant.
   * 
   * @param pId Identifiant unique du composant.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }

  /**
   * Récupère le nom du composant.
   * 
   * @return Nom désignant le composant.
   */
  public String getNom ()
  {
    return mNom ;
  }

  /**
   * Initialise le nom du composant.
   * 
   * @param pNom Nom désignant le composant.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }

  /**
   * Récupère le nom de l'auteur du composant.
   * 
   * @return Nom de l'auteur du composant.
   */
  public String getNomAuteur ()
  {
    return mNomAuteur ;
  }

  /**
   * Initialise le nom de l'auteur du composant.
   * 
   * @param pNomAuteur Nom de l'auteur du composant.
   */
  public void setNomAuteur (String pNomAuteur)
  {
    mNomAuteur = pNomAuteur ;
  }

  /**
   * Récupère le processus possédant le composant.
   * 
   * @return Processus possédant le composant.
   */
  public MProcessus getProcessus ()
  {
    return mProcessus ;
  }

  /**
   * Associe le processus possédant le composant.
   * 
   * @param pProcessus Processus possédant le composant.
   */
  public void setProcessus (MProcessus pProcessus)
  {
    mProcessus = pProcessus ;
    ArrayList lListComposant = pProcessus.getListeComposants () ;
    if (!lListComposant.contains (this))
    {
      pProcessus.addComposant (this) ;
    }
  }

  /**
   * Récupère la liste des produits.
   * 
   * @return Liste des produits définis dans le composant.
   */
  public ArrayList getListeProduits ()
  {
    return mProduits ;
  }

  /**
   * Initialise la liste des produits.
   * 
   * @param pProduits Liste des produits définis dans le composant.
   */
  public void setListeProduits (ArrayList pProduits)
  {
    mProduits = pProduits ;
    Iterator it = pProduits.iterator () ;
    MProduit lProduit ;
    while (it.hasNext ())
    {
      lProduit = (MProduit) it.next () ;
      MComposant lComposant = lProduit.getComposant () ;
      if (lComposant != this)
      {
        lProduit.setComposant (this) ;
      }
    }
  }

  /**
   * Récupère le nombre de produits définis dans le composant.
   * 
   * @return Nombre de produits définis dans le composant.
   */
  public int getNbProduits ()
  {
    return mProduits.size () ;
  }

  /**
   * Récupère le produit d'indice spécifié définis dans le composant.
   * 
   * @param pIndice Indice du produit dans la liste.
   * @return Produit définis dans le composant.
   */
  public MProduit getProduit (int pIndice)
  {
    return (MProduit) mProduits.get (pIndice) ;
  }

  /**
   * Ajoute le produit spécifié au composant.
   * 
   * @param pProduit Produit définis dans le composant.
   */
  public void addProduit (MProduit pProduit)
  {
    if (!mProduits.contains (pProduit))
    {
      mProduits.add (pProduit) ;
    }
    MComposant lComposant = pProduit.getComposant () ;
    if (lComposant != this)
    {
      pProduit.setComposant (this) ;
    }
  }

  /**
   * Récupère la liste des rôles.
   * 
   * @return Liste des rôles définis dans le composant.
   */
  public ArrayList getListeRoles ()
  {
    return mRoles ;
  }

  /**
   * Initialise la liste des rôles.
   * 
   * @param pRoles Liste des rôles définis dans le composant.
   */
  public void setListeRoles (ArrayList pRoles)
  {
    mRoles = pRoles ;
    Iterator it = pRoles.iterator () ;
    MRole lRole ;
    while (it.hasNext ())
    {
      lRole = (MRole) it.next () ;
      MComposant lComposant = lRole.getComposant () ;
      if (lComposant != this)
      {
        lRole.setComposant (this) ;
      }
    }
  }

  /**
   * Récupère le nombre de rôles définis dans le composant.
   * 
   * @return Nombre de rôles définis dans le composant.
   */
  public int getNbRoles ()
  {
    return mRoles.size () ;
  }

  /**
   * Récupère le rôle d'indice spécifié définis dans le composant.
   * 
   * @param pIndice Indice du rôle dans la liste.
   * @return Rôle définis dans le composant.
   */
  public MRole getRole (int pIndice)
  {
    return (MRole) mRoles.get (pIndice) ;
  }

  /**
   * Ajoute le rôle spécifié au composant.
   * 
   * @param pRole Rôle définis dans le composant.
   */
  public void addRole (MRole pRole)
  {
    if (!mRoles.contains (pRole))
    {
      mRoles.add (pRole) ;
    }
    MComposant lComposant = pRole.getComposant () ;
    if (lComposant != null)
    {
      if (lComposant != this)
      {
        pRole.setComposant (this) ;
      }
    }
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