package owep.modele.processus ;


import java.util.ArrayList ;
import java.util.Iterator ;

import owep.modele.MModeleBase ;
import owep.modele.execution.MProjet ;


/**
 * Un processus repr�sente un regroupement coh�rent d�activit�s, aliment� par des produits en
 * entr�e, qui sont transform�s en produits de sortie, en y apportant une valeur ajout�e pour le
 * client. MProcessus est la classe centrale du paquetage processus.
 */
public class MProcessus extends MModeleBase
{
  private int       mId ;         // Identifie le processus de mani�re unique.
  private String    mNom ;        // Nom d�signant le processus.
  private String    mDescription ; // Description du processus.
  private String    mNomAuteur ;  // Nom de l'auteur du processus.
  private String    mEmailAuteur ; // Email de l'auteur du processus.
  private ArrayList mComposants ; // Liste des composants d�finissant le processus.
  private ArrayList mProjets ;    // Liste des projets pour lesquels est appliqu� le processus.
  private String    mIdDpe ;      // Identifiant du dpe


  /**
   * Construit une instance vide de MProcessus.
   */
  public MProcessus ()
  {
    super () ;

    mComposants = new ArrayList () ;
    mProjets = new ArrayList () ;
  }

  /**
   * Construit une instance de MProcessus avec l'id.
   */
  public MProcessus (int pId)
  {
    super () ;

    mId = pId;
    
    mComposants = new ArrayList () ;
    mProjets = new ArrayList () ;
  }

  /**
   * Construit une initialis�e de MProcessus.
   * 
   * @param pId Identifiant unique du processus.
   * @param pNom Nom d�signant le processus.
   * @param pDescription Description du processus.
   * @param pNomAuteur Nom de l'auteur du processus.
   * @param pEmailAuteur Email de l'auteur du processus.
   */
  public MProcessus (int pId, String pNom, String pDescription, String pNomAuteur,
                     String pEmailAuteur)
  {
    super () ;

    mId = pId ;
    mNom = pNom ;
    mDescription = pDescription ;
    mNomAuteur = pNomAuteur ;
    mEmailAuteur = pEmailAuteur ;

    mComposants = new ArrayList () ;
    mProjets = new ArrayList () ;
  }

  /**
   * R�cup�re la liste de composants.
   * 
   * @return Liste des composants d�finissant le processus.
   */
  public ArrayList getListeComposants ()
  {
    return mComposants ;
  }

  /**
   * Initialise la liste de composants.
   * 
   * @param pComposants Liste des composants d�finissant le processus.
   */
  public void setListeComposants (ArrayList pComposants)
  {
    mComposants = pComposants ;
    Iterator it = pComposants.iterator () ;
    MComposant lComposant ;
    while (it.hasNext ())
    {
      lComposant = (MComposant) it.next () ;
      MProcessus lProcessus = lComposant.getProcessus () ;
      if (lProcessus != this)
      {
        lComposant.setProcessus (this) ;
      }
    }
  }

  /**
   * R�cup�re le nombre de composants d�finissant le processus.
   * 
   * @return Nombre de composants d�finissant le processus.
   */
  public int getNbComposants ()
  {
    return mComposants.size () ;
  }

  /**
   * R�cup�re le composant d'indice sp�cifi� d�finissant le processus.
   * 
   * @param pIndice Indice du composant dans la liste.
   * @return Composant d�finissant le processus.
   */
  public MComposant getComposant (int pIndice)
  {
    return (MComposant) mComposants.get (pIndice) ;
  }

  /**
   * Ajoute le composant sp�cifi� au processus.
   * 
   * @param pComposant Composant d�finissant le processus.
   */
  public void addComposant (MComposant pComposant)
  {
    if (!mComposants.contains (pComposant))
    {
      mComposants.add (pComposant) ;
    }
    MProcessus lProcessus = pComposant.getProcessus () ;
    if (lProcessus != this)
    {
      pComposant.setProcessus (this) ;
    }
  }

  /**
   * R�cup�re la description du processus.
   * 
   * @return Description du processus.
   */
  public String getDescription ()
  {
    return mDescription ;
  }

  /**
   * Initialise la description du processus.
   * 
   * @param pDescription Description du processus.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }

  /**
   * R�cup�re l'email de l'auteur du processus.
   * 
   * @return Email de l'auteur du processus.
   */
  public String getEmailAuteur ()
  {
    return mEmailAuteur ;
  }

  /**
   * Initialise l'email de l'auteur du processus.
   * 
   * @param pEmailAuteur Email de l'auteur du processus.
   */
  public void setEmailAuteur (String pEmailAuteur)
  {
    mEmailAuteur = pEmailAuteur ;
  }

  /**
   * R�cup�re l'identifiant du processus.
   * 
   * @return Identifiant unique du processus.
   */
  public int getId ()
  {
    return mId ;
  }

  /**
   * Initialise l'identifiant du processus.
   * 
   * @param pId Identifiant unique du processus.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }

  /**
   * R�cup�re le nom du processus.
   * 
   * @return Nom d�signant le processus.
   */
  public String getNom ()
  {
    return mNom ;
  }

  /**
   * Initialise le nom du processus.
   * 
   * @param pNom Nom d�signant le processus.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }

  /**
   * R�cup�re le nom de l'auteur du processus.
   * 
   * @return Nom de l'auteur du processus.
   */
  public String getNomAuteur ()
  {
    return mNomAuteur ;
  }

  /**
   * Initialise le nom de l'auteur du processus.
   * 
   * @param pNomAuteur Nom de l'auteur du processus.
   */
  public void setNomAuteur (String pNomAuteur)
  {
    mNomAuteur = pNomAuteur ;
  }

  /**
   * R�cup�re la liste des projets pour lesquels est appliqu� le processus.
   * 
   * @return Liste des projets pour lesquels est appliqu� le processus.
   */
  public ArrayList getListeProjets ()
  {
    return mProjets ;
  }

  /**
   * Initialise la liste des projets pour lesquels est appliqu� le processus.
   * 
   * @param pProjets Liste des projets pour lesquels est appliqu� le processus.
   */
  public void setListeProjets (ArrayList pProjets)
  {
    mProjets = pProjets ;
    Iterator it = pProjets.iterator () ;
    MProjet lProjet ;
    while (it.hasNext ())
    {
      lProjet = (MProjet) it.next () ;
      MProcessus lProcessus = lProjet.getProcessus () ;
      if (lProcessus != this)
      {
        lProjet.setProcessus (this) ;
      }
    }
  }

  /**
   * R�cup�re le nombre de projets pour lesquels est appliqu� le processus.
   * 
   * @return Nombre de projets pour lesquels est appliqu� le processus.
   */
  public int getNbProjets ()
  {
    return mProjets.size () ;
  }

  /**
   * R�cup�re le projet d'indice sp�cifi� pour lesquels est appliqu� le processus.
   * 
   * @param pIndice Indice du projet dans la liste.
   * @return Projet pour lequel est appliqu� le processus.
   */
  public MProjet getProjet (int pIndice)
  {
    return (MProjet) mProjets.get (pIndice) ;
  }

  /**
   * Ajoute le projet sp�cifi� qui applique le processus.
   * 
   * @param pProjet Projet pour lequel est appliqu� le processus.
   */
  public void addProjet (MProjet pProjet)
  {
    if (!mProjets.contains (pProjet))
    {
      mProjets.add (pProjet) ;
    }
    MProcessus lProcessus = pProjet.getProcessus () ;
    if (lProcessus != this)
    {
      pProjet.setProcessus (this) ;
    }
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
   * Initialise avec l'identifant du DPE.
   * 
   * @param initialse idDpe avec pIdDpe.
   */
  public void setIdDpe (String pIdDpe)
  {
    mIdDpe = pIdDpe ;
  }
}