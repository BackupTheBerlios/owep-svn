package owep.modele.execution ;


import java.util.Date ;
import java.util.ArrayList ;
import owep.modele.MModeleBase;
import owep.modele.processus.MProcessus ;


/**
 * Un projet est constitué d'un ensemble de tâches, de produits et de collaborateurs. MProjet est la
 * classe centrale du paquetage execution.
 */
public class MProjet extends MModeleBase
{
  private int            mId ;              // Identifie le projet de manière unique.
  private String         mNom ;             // Nom du projet.
  private String         mDescription ;     // Description du projet.
  private Date           mDateDebutPrevue ; // Date de début prévue pour le projet.
  private Date           mDateFinPrevue ;   // Date de fin prévue pour le projet.
  private ArrayList      mArtefacts ;       // Liste des artefacts réalisés durant le projet.
  private ArrayList      mCollaborateurs ;  // Liste des collaborateurs travaillant sur le projet.
  private ArrayList      mIterations ;      // Liste des itérations réalisées durant le projet.
  private MProcessus     mProcessus ;       // Processus utilisé par le projet.
  private MCollaborateur mChefProjet ;      // Chef de projet.


  /**
   * Crée une instance vide de MProjet.
   */
  public MProjet ()
  {
    super () ;
    
    mIterations = new ArrayList () ;
  }


  /**
   * Crée une instance initialisée de MProjet.
   * @param pId Identifiant du projet.
   * @param pNom Nom du projet.
   * @param pDescription Description du projet.
   * @param pDateDebutPrevue Date de début prévue du projet.
   * @param pDateFinPrevue Date de fin prévue du projet.
   */
  public MProjet (int pId, String pNom, String pDescription, Date pDateDebutPrevue,
                  Date pDateFinPrevue)
  {
    super () ;
    
    mId              = pId ;
    mNom             = pNom ;
    mDescription     = pDescription ;
    mDateDebutPrevue = pDateDebutPrevue ;
    mDateFinPrevue   = pDateFinPrevue ;
    
    mIterations = new ArrayList () ;
  }


  /**
   * Récupère la liste des artefacts réalisés durant le projet.
   * @return Liste des artefacts réalisés durant le projet.
   */
  public ArrayList getListeArtefacts ()
  {
    return mArtefacts ;
  }


  /**
   * Initialise la liste des artefacts réalisés durant le projet.
   * @param pArtefacts Liste des artefacts réalisés durant le projet.
   */
  public void setListeArtefacts (ArrayList pArtefacts)
  {
    mArtefacts = pArtefacts ;
  }


  /**
   * Récupère le nombre d'artefacts réalisés durant le projet.
   * @return Nombre d'artefacts réalisées durant le projet.
   */
  public int getNbArtefacts ()
  {
    return mArtefacts.size () ;
  }


  /**
   * Récupère l'artefact d'indice spécifié réalisée durant le projet.
   * @param pIndice Indice de l'artefact dans la liste.
   * @return Artefact réalisé durant le projet.
   */
  public MArtefact getArtefact (int pIndice)
  {
    return (MArtefact) mArtefacts.get (pIndice) ;
  }


  /**
   * Ajoute l'artefact spécifié au projet.
   * @param pArtefact Artefact réalisé durant le projet.
   */
  public void addArtefact (MArtefact pArtefact)
  {
    mArtefacts.add (pArtefact) ;
  }


  /**
   * Supprime l'artefact spécifié au projet.
   * @param pArtefact Artefact réalisé durant le projet.
   */
  public void supprimerArtefact (MArtefact pArtefact)
  {
    mArtefacts.remove (pArtefact) ;
  }


  /**
   * Récupère le chef de projet.
   * @return Chef de projet.
   */
  public MCollaborateur getChefProjet ()
  {
    return mChefProjet ;
  }


  /**
   * Initialise le chef de projet.
   * @param pChefProjet Chef de projet.
   */
  public void setChefProjet (MCollaborateur pChefProjet)
  {
    mChefProjet = pChefProjet ;
  }


  /**
   * Récupère la liste des collaborateurs sur le projet.
   * @return Liste des collaborateurs sur le projet.
   */
  public ArrayList getListeCollaborateurs ()
  {
    return mCollaborateurs ;
  }


  /**
   * Initialise la liste des collaborateurs sur le projet.
   * @param pCollaborateurs Liste des collaborateurs sur le projet.
   */
  public void setListeCollaborateurs (ArrayList pCollaborateurs)
  {
    mCollaborateurs = pCollaborateurs ;
  }


  /**
   * Récupère le nombre de collaborateurs sur le projet.
   * @return Nombre de collaborateurs sur le projet.
   */
  public int getNbCollaborateurs ()
  {
    return mCollaborateurs.size () ;
  }


  /**
   * Récupère le collaborateur d'indice spécifié sur le projet.
   * @param pIndice Indice du collaborateur dans la liste.
   * @return Collaborateur sur le projet.
   */
  public MCollaborateur getCollaborateur (int pIndice)
  {
    return (MCollaborateur) mCollaborateurs.get (pIndice) ;
  }


  /**
   * Ajoute le collaborateur spécifié au projet.
   * @param pCollaborateur Collaborateur sur le projet.
   */
  public void addCollaborateur (MCollaborateur pCollaborateur)
  {
    mCollaborateurs.add (pCollaborateur) ;
  }


  /**
   * Récupère la date de début prévue pour le projet.
   * @return Date de début prévue pour le projet.
   */
  public Date getDateDebutPrevue ()
  {
    return mDateDebutPrevue ;
  }


  /**
   * Initialise la date de début prévue pour le projet.
   * @param pDateDebutPrevue Date de début prévue pour le projet.
   */
  public void setDateDebutPrevue (Date pDateDebutPrevue)
  {
    mDateDebutPrevue = pDateDebutPrevue ;
  }


  /**
   * Récupère la date de fin prévue pour le projet.
   * @return Date de fin prévue pour le projet.
   */
  public Date getDateFinPrevue ()
  {
    return mDateFinPrevue ;
  }


  /**
   * Initialise la date de fin prévue pour le projet.
   * @param pDateFinPrevue Date de fin prévue pour le projet.
   */
  public void setDateFinPrevue (Date pDateFinPrevue)
  {
    mDateFinPrevue = pDateFinPrevue ;
  }


  /**
   * Récupère la description du projet.
   * @return Description du projet.
   */
  public String getDescription ()
  {
    return mDescription ;
  }


  /**
   * Initialise la description du projet.
   * @param pDescription Description du projet.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }


  /**
   * Récupère la liste des itérations réalisées durant le projet.
   * @return Liste des itérations réalisées durant le projet.
   */
  public ArrayList getListeIterations ()
  {
    return mIterations ;
  }


  /**
   * Initialise la liste des itérations réalisées durant le projet.
   * @param pIterations Liste des itérations réalisées durant le projet.
   */
  public void setListeIterations (ArrayList pIterations)
  {
    mIterations = pIterations ;
  }


  /**
   * Récupère le nombre d'itérations réalisées durant le projet.
   * @return Nombre d'itérations réalisées durant le projet.
   */
  public int getNbIterations ()
  {
    return mIterations.size () ;
  }


  /**
   * Récupère l'itération d'indice spécifié réalisée durant le projet.
   * @param pIndice Indice de l'itération dans la liste.
   * @return Itération réalisée durant le projet.
   */
  public MIteration getIteration (int pIndice)
  {
    return (MIteration) mIterations.get (pIndice) ;
  }


  /**
   * Ajoute l'itération spécifiée au projet.
   * @param pTache Itération réalisée durant le projet.
   */
  public void addIteration (MIteration pIteration)
  {
    mIterations.add (pIteration) ;
  }


  /**
   * Récupère l'identifiant du projet.
   * @return Identifie le projet de manière unique.
   */
  public int getId ()
  {
    return mId ;
  }


  /**
   * Initialise l'identifiant du projet.
   * @param pId Identifie le projet de manière unique.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }


  /**
   * Récupère le nom du projet.
   * @return Nom du projet.
   */
  public String getNom ()
  {
    return mNom ;
  }


  /**
   * Initialise le nom du projet.
   * @param pNom Nom du projet.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }


  /**
   * Récupère le processus utilisé par le projet.
   * @return Processus utilisé par le projet.
   */
  public MProcessus getProcessus ()
  {
    return mProcessus ;
  }


  /**
   * Associe le processus utilisé par le projet.
   * @param pProcessus Processus utilisé par le projet.
   */
  public void setProcessus (MProcessus pProcessus)
  {
    mProcessus = pProcessus ;
  }
}
