package owep.modele.processus ;


import java.util.ArrayList ;
import java.util.Iterator ;

import owep.modele.MModeleBase ;


/**
 * Une définition de travail représente un ensemble d'activité. Elle permet de les regrouper par
 * discipline.
 */
public class MDefinitionTravail extends MModeleBase
{
  private int        mId ;         // Identifie la définition de travail de manière unique.
  private String     mNom ;        // Nom désignant la définition de travail.
  private String     mDescription ; // Description de la définition de travail.
  private ArrayList  mActivites ;  // Liste des activités composant la définition de travail.
  private MComposant mComposant ;  // Composant incluant la définition de travail.
  private String     mIdDpe = "" ;      // Identifiant du dpe


  /**
   * Construit une instance vide de MDefinitionTravail.
   */
  public MDefinitionTravail ()
  {
    super () ;

    mActivites = new ArrayList () ;
  }

  /**
   * Construit une instance de MDefinitionTravail avec l'id.
   */
  public MDefinitionTravail (int pId)
  {
    super () ;
    
    mId = pId;

    mActivites = new ArrayList () ;
  }

  /**
   * Construit une instance initialisée de MDefinitionTravail.
   * 
   * @param pId Identifiant unique de la définition de travail.
   * @param pNom Nom désignant la définition de travail.
   * @param pDescription Description de la définition de travail.
   * @param pComposant Composant incluant la définition de travail.
   */
  public MDefinitionTravail (int pId, String pNom, String pDescription, MComposant pComposant)
  {
    super () ;

    mId = pId ;
    mNom = pNom ;
    mDescription = pDescription ;
    mComposant = pComposant ;
    mActivites = new ArrayList () ;
  }

  /**
   * Récupère la liste des activités.
   * 
   * @return Liste des activités composant la définition de travail.
   */
  public ArrayList getListeActivites ()
  {
    return mActivites ;
  }

  /**
   * Initialise la liste des activités.
   * 
   * @param pActivites Liste des activités composant la définition de travail.
   */
  public void setListeActivites (ArrayList pActivites)
  {
    mActivites = pActivites ;
    Iterator it = pActivites.iterator () ;
    MActivite lActivite ;
    while (it.hasNext ())
    {
      lActivite = (MActivite) it.next () ;
      MDefinitionTravail lDefinitionTravail = lActivite.getDefinitionsTravail () ;
      if (lDefinitionTravail != this)
      {
        lActivite.setDefinitionsTravail (this) ;
      }
    }
  }

  /**
   * Récupère le nombre d'activités composant la définition de travail.
   * 
   * @return Nombre d'activités composant la définition de travail.
   */
  public int getNbActivites ()
  {
    return mActivites.size () ;
  }

  /**
   * Récupère l'activité d'indice spécifié composant la définition de travail.
   * 
   * @param pIndice Indice de l'activité dans la liste.
   * @return Activité composant la définition de travail.
   */
  public MActivite getActivite (int pIndice)
  {
    return (MActivite) mActivites.get (pIndice) ;
  }

  /**
   * Ajoute l'activité spécifié à la définition de travail.
   * 
   * @param pActivite composant la définition de travail.
   */
  public void addActivite (MActivite pActivite)
  {
    if (!mActivites.contains (pActivite))
    {
      mActivites.add (pActivite) ;
    }
    MDefinitionTravail lDefinitionTravail = pActivite.getDefinitionsTravail () ;
    if (lDefinitionTravail != this)
    {
      pActivite.setDefinitionsTravail (this) ;
    }
  }

  /**
   * Récupère le composant incluant la définition de travail.
   * 
   * @return Composant incluant la définition de travail.
   */
  public MComposant getComposant ()
  {
    return mComposant ;
  }

  /**
   * Associe le composant incluant la définition de travail.
   * 
   * @param pComposant Composant incluant la définition de travail.
   */
  public void setComposant (MComposant pComposant)
  {
    if(pComposant != null){
    mComposant = pComposant ;
    ArrayList lListDefinitionTravail = pComposant.getListeDefinitionsTravail () ;
    if (!lListDefinitionTravail.contains (this))
    {
      pComposant.addDefinitionTravail (this) ;
    }
    }
  }

  /**
   * Récupère la description de la définition de travail.
   * 
   * @return Description de la définition de travail.
   */
  public String getDescription ()
  {
    return mDescription ;
  }

  /**
   * Initialise la description de la définition de travail.
   * 
   * @param pDescription Description de la définition de travail.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }

  /**
   * Récupère l'identifiant de la définition de travail.
   * 
   * @return Identifiant unique de la définition de travail.
   */
  public int getId ()
  {
    return mId ;
  }

  /**
   * Initialise l'identifiant de la définition de travail.
   * 
   * @param pId Identifiant unique de la définition de travail.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }

  /**
   * Récupère le nom de la définition de travail.
   * 
   * @return Nom désignant la définition de travail.
   */
  public String getNom ()
  {
    return mNom ;
  }

  /**
   * Initialise le nom de la définition de travail.
   * 
   * @param pNom Nom désignant la définition de travail.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
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
   * Initialise avec l'identifiant.
   * 
   * @param initialse idDpe avec pIdDpe.
   */
  public void setIdDpe (String pIdDpe)
  {
    mIdDpe = pIdDpe ;
  }
}