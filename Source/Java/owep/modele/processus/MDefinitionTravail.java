package owep.modele.processus ;


import java.util.ArrayList ;
import java.util.Iterator ;

import owep.modele.MModeleBase ;


/**
 * Une d�finition de travail repr�sente un ensemble d'activit�. Elle permet de les regrouper par
 * discipline.
 */
public class MDefinitionTravail extends MModeleBase
{
  private int        mId ;         // Identifie la d�finition de travail de mani�re unique.
  private String     mNom ;        // Nom d�signant la d�finition de travail.
  private String     mDescription ; // Description de la d�finition de travail.
  private ArrayList  mActivites ;  // Liste des activit�s composant la d�finition de travail.
  private MComposant mComposant ;  // Composant incluant la d�finition de travail.
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
   * Construit une instance initialis�e de MDefinitionTravail.
   * 
   * @param pId Identifiant unique de la d�finition de travail.
   * @param pNom Nom d�signant la d�finition de travail.
   * @param pDescription Description de la d�finition de travail.
   * @param pComposant Composant incluant la d�finition de travail.
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
   * R�cup�re la liste des activit�s.
   * 
   * @return Liste des activit�s composant la d�finition de travail.
   */
  public ArrayList getListeActivites ()
  {
    return mActivites ;
  }

  /**
   * Initialise la liste des activit�s.
   * 
   * @param pActivites Liste des activit�s composant la d�finition de travail.
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
   * R�cup�re le nombre d'activit�s composant la d�finition de travail.
   * 
   * @return Nombre d'activit�s composant la d�finition de travail.
   */
  public int getNbActivites ()
  {
    return mActivites.size () ;
  }

  /**
   * R�cup�re l'activit� d'indice sp�cifi� composant la d�finition de travail.
   * 
   * @param pIndice Indice de l'activit� dans la liste.
   * @return Activit� composant la d�finition de travail.
   */
  public MActivite getActivite (int pIndice)
  {
    return (MActivite) mActivites.get (pIndice) ;
  }

  /**
   * Ajoute l'activit� sp�cifi� � la d�finition de travail.
   * 
   * @param pActivite composant la d�finition de travail.
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
   * R�cup�re le composant incluant la d�finition de travail.
   * 
   * @return Composant incluant la d�finition de travail.
   */
  public MComposant getComposant ()
  {
    return mComposant ;
  }

  /**
   * Associe le composant incluant la d�finition de travail.
   * 
   * @param pComposant Composant incluant la d�finition de travail.
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
   * R�cup�re la description de la d�finition de travail.
   * 
   * @return Description de la d�finition de travail.
   */
  public String getDescription ()
  {
    return mDescription ;
  }

  /**
   * Initialise la description de la d�finition de travail.
   * 
   * @param pDescription Description de la d�finition de travail.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }

  /**
   * R�cup�re l'identifiant de la d�finition de travail.
   * 
   * @return Identifiant unique de la d�finition de travail.
   */
  public int getId ()
  {
    return mId ;
  }

  /**
   * Initialise l'identifiant de la d�finition de travail.
   * 
   * @param pId Identifiant unique de la d�finition de travail.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }

  /**
   * R�cup�re le nom de la d�finition de travail.
   * 
   * @return Nom d�signant la d�finition de travail.
   */
  public String getNom ()
  {
    return mNom ;
  }

  /**
   * Initialise le nom de la d�finition de travail.
   * 
   * @param pNom Nom d�signant la d�finition de travail.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }

  /**
   * R�cup�re l'identifiant du DPE.
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