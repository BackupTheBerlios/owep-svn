package owep.modele.processus ;


import java.util.ArrayList ;
import java.util.Iterator ;

import owep.modele.MModeleBase ;
import owep.modele.execution.MProjet ;


/**
 * Un processus représente un regroupement cohérent d’activités, alimenté par des produits en
 * entrée, qui sont transformés en produits de sortie, en y apportant une valeur ajoutée pour le
 * client. MProcessus est la classe centrale du paquetage processus.
 */
public class MProcessus extends MModeleBase
{
  private int       mId ;         // Identifie le processus de manière unique.
  private String    mNom ;        // Nom désignant le processus.
  private String    mDescription ; // Description du processus.
  private String    mNomAuteur ;  // Nom de l'auteur du processus.
  private String    mEmailAuteur ; // Email de l'auteur du processus.
  private ArrayList mComposants ; // Liste des composants définissant le processus.
  private ArrayList mProjets ;    // Liste des projets pour lesquels est appliqué le processus.
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
   * Construit une initialisée de MProcessus.
   * 
   * @param pId Identifiant unique du processus.
   * @param pNom Nom désignant le processus.
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
   * Récupère la liste de composants.
   * 
   * @return Liste des composants définissant le processus.
   */
  public ArrayList getListeComposants ()
  {
    return mComposants ;
  }

  /**
   * Initialise la liste de composants.
   * 
   * @param pComposants Liste des composants définissant le processus.
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
   * Récupère le nombre de composants définissant le processus.
   * 
   * @return Nombre de composants définissant le processus.
   */
  public int getNbComposants ()
  {
    return mComposants.size () ;
  }

  /**
   * Récupère le composant d'indice spécifié définissant le processus.
   * 
   * @param pIndice Indice du composant dans la liste.
   * @return Composant définissant le processus.
   */
  public MComposant getComposant (int pIndice)
  {
    return (MComposant) mComposants.get (pIndice) ;
  }

  /**
   * Ajoute le composant spécifié au processus.
   * 
   * @param pComposant Composant définissant le processus.
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
   * Récupère la description du processus.
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
   * Récupère l'email de l'auteur du processus.
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
   * Récupère l'identifiant du processus.
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
   * Récupère le nom du processus.
   * 
   * @return Nom désignant le processus.
   */
  public String getNom ()
  {
    return mNom ;
  }

  /**
   * Initialise le nom du processus.
   * 
   * @param pNom Nom désignant le processus.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }

  /**
   * Récupère le nom de l'auteur du processus.
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
   * Récupère la liste des projets pour lesquels est appliqué le processus.
   * 
   * @return Liste des projets pour lesquels est appliqué le processus.
   */
  public ArrayList getListeProjets ()
  {
    return mProjets ;
  }

  /**
   * Initialise la liste des projets pour lesquels est appliqué le processus.
   * 
   * @param pProjets Liste des projets pour lesquels est appliqué le processus.
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
   * Récupère le nombre de projets pour lesquels est appliqué le processus.
   * 
   * @return Nombre de projets pour lesquels est appliqué le processus.
   */
  public int getNbProjets ()
  {
    return mProjets.size () ;
  }

  /**
   * Récupère le projet d'indice spécifié pour lesquels est appliqué le processus.
   * 
   * @param pIndice Indice du projet dans la liste.
   * @return Projet pour lequel est appliqué le processus.
   */
  public MProjet getProjet (int pIndice)
  {
    return (MProjet) mProjets.get (pIndice) ;
  }

  /**
   * Ajoute le projet spécifié qui applique le processus.
   * 
   * @param pProjet Projet pour lequel est appliqué le processus.
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
   * Récupère l'identifiant du DPE.
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