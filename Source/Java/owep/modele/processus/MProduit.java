package owep.modele.processus ;


import java.util.ArrayList ;
import owep.modele.MModeleBase ;
import owep.modele.execution.MArtefact;


/**
 * Un produit représente un élément tangible produit au cours du processus. Ils sont sous la
 * responsabilité d'un et un seul rôle. Les rôles utilisent des produits pour effectuer des
 * activités.
 */
public class MProduit extends MModeleBase
{
  private int        mId ;               // Identifie le produit de manière unique.
  private String     mNom ;              // Nom désignant le produit.
  private String     mDescription ;      // Description du produit.
  private ArrayList  mActivitesEntrees ; // Liste des activités qui nécessite le produit.
  private ArrayList  mActivitesSorties ; // Liste des activités qui réalisant le produit.
  private ArrayList  mArtefacts ;        // Liste des artefacts qui instancie le produit.
  private MComposant mComposant ;        // Composant incluant le produit.
  private MRole      mResponsable ;      // Rôle responsable du produit.


  /**
   * Construit une instance vide de MProduit.
   */
  public MProduit ()
  {
    super () ;
    
    mActivitesEntrees = new ArrayList () ;
    mActivitesSorties = new ArrayList () ;
  }


  /**
   * Construit une instance initialisée de MProduit.
   * @param pId Identifiant unique du produit.
   * @param pNom Nom désignant le produit.
   * @param pDescription Description du produit.
   * @param pComposant Composant incluant le produit.
   */
  public MProduit (int pId, String pNom, String pDescription, MComposant pComposant)
  {
    super () ;
    mId          = pId ;
    mNom         = pNom ;
    mDescription = pDescription ;
    mComposant   = pComposant ;
    
    mActivitesEntrees = new ArrayList () ;
    mActivitesSorties = new ArrayList () ;
  }


  /**
   * Récupère la liste des activités en entrées.
   * @return Liste des activités qui nécessite le produit.
   */
  public ArrayList getListeActivitesEntrees ()
  {
    return mActivitesEntrees ;
  }


  /**
   * Initialise la liste des activités en entrées.
   * @param pActivitesEntrees Liste des activités qui nécessite le produit.
   */
  public void setListeActivitesEntrees (ArrayList pActivitesEntrees)
  {
    mActivitesEntrees = pActivitesEntrees ;
  }


  /**
   * Récupère le nombre des activités pour lesquelles le produit est en entrée.
   * @return Nombre des activités qui nécessite le produit.
   */
  public int getNbActivitesEntrees ()
  {
    return mActivitesEntrees.size () ;
  }


  /**
   * Récupère l'activité d'indice spécifié pour laquelle le produit est en entrée.
   * @param pIndice Indice de l'activité qui nécessite le produit.
   * @return Activité qui nécessite le produit.
   */
  public MActivite getActiviteEntree (int pIndice)
  {
    return (MActivite) mActivitesEntrees.get (pIndice) ;
  }


  /**
   * Ajoute l'activité spécifié en entrée.
   * @param pActivite Activité qui nécessite le produit.
   */
  public void addActiviteEntree (MActivite pActivite)
  {
    mActivitesEntrees.add (pActivite) ;
  }


 /**
   * Récupère la liste des activités en sorties.
   * @return Liste des activités qui réalisent le produit.
   */
  public ArrayList getListeActivitesSorties ()
  {
    return mActivitesSorties ;
  }


  /**
   * Initialise la liste des activités en sorties.
   * @param pActivitesSorties Liste des activités qui réalisent le produit.
   */
  public void setListeActivitesSorties (ArrayList pActivitesSorties)
  {
    mActivitesSorties = pActivitesSorties ;
  }


  /**
   * Récupère le nombre des activités pour lesquelles le produit est en sortie.
   * @return Nombre des activités qui réalisent le produit.
   */
  public int getNbActivitesSorties ()
  {
    return mActivitesEntrees.size () ;
  }


  /**
   * Récupère l'activité d'indice spécifié pour laquelle le produit est en sortie.
   * @param pIndice Indice de l'activité dans la liste.
   * @return Activité qui réalise le produit.
   */
  public MActivite getActiviteSortie (int pIndice)
  {
    return (MActivite) mActivitesEntrees.get (pIndice) ;
  }


  /**
   * Ajoute l'activité spécifié en sortie.
   * @param pActivite Activité qui réalise le produit.
   */
  public void addActiviteSortie (MActivite pActivite)
  {
    mActivitesEntrees.add (pActivite) ;
  }


  /**
   * Récupère le composant incluant le produit.
   * @return Composant incluant le produit.
   */
  public MComposant getComposant ()
  {
    return mComposant ;
  }


  /**
   * Associe le composant incluant le produit.
   * @param pComposant Composant incluant le produit.
   */
  public void setComposant (MComposant pComposant)
  {
    mComposant = pComposant ;
  }


  /**
   * Récupère la description du produit.
   * @return Description du produit.
   */
  public String getDescription ()
  {
    return mDescription ;
  }


  /**
   * Initialise la description du produit.
   * @param pDescription Description du produit.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }


  /**
   * Récupère l'identifiant du produit.
   * @return Identifiant unique du produit.
   */
  public int getId ()
  {
    return mId ;
  }


  /**
   * Initialise l'identifiant du produit.
   * @param pId Identifiant unique du produit.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }


  /**
   * Récupère le nom du produit.
   * @return Nom désignant le produit.
   */
  public String getNom ()
  {
    return mNom ;
  }


  /**
   * Initialise le nom du produit.
   * @param pNom Nom désignant le produit.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }
  
  
  /**
   * Récupère la liste des artefacts qui instancie le produit.
   * @return Liste des artefacts qui instancie le produit.
   */
  public ArrayList getListeArtefacts ()
  {
    return mArtefacts ;
  }


  /**
   * Initialise la liste des artefacts qui instancie le produit.
   * @param pArtefacts Liste des artefacts qui instancie le produit.
   */
  public void setListeArtefacts (ArrayList pArtefacts)
  {
    mArtefacts = pArtefacts ;
  }


  /**
   * Récupère le nombre des artefacts qui instancie le produit.
   * @return Nombre des artefacts qui instancie le produit.
   */
  public int getNbArtefacts ()
  {
    return mArtefacts.size () ;
  }


  /**
   * Récupère l'artefact d'indice spécifié qui instancie le produit.
   * @param pIndice Indice de l'artefact dans la liste.
   * @return Artefact qui instancie le produit.
   */
  public MArtefact getArtefact (int pIndice)
  {
    return (MArtefact) mArtefacts.get (pIndice) ;
  }


  /**
   * Ajoute l'artefact spécifié qui instancie le produit.
   * @param pArtefact Artefact qui instancie le produit.
   */
  public void addArtefact (MArtefact pArtefact)
  {
    mArtefacts.add (pArtefact) ;
  }


  /**
   * Supprime l'artefact spécifié qui instancie le produit.
   * @param pArtefact Artefact qui instancie le produit.
   */
  public void supprimerArtefact (MArtefact pArtefact)
  {
    mArtefacts.remove (pArtefact) ;
  }


  /**
   * Récupère le rôle responsable du produit.
   * @return Rôle responsable du produit.
   */
  public MRole getResponsable ()
  {
    return mResponsable ;
  }
  
  
  /**
   * Initialise le rôle responsable du produit.
   * @param pResponsable Rôle responsable du produit.
   */
  public void setResponsable (MRole pResponsable)
  {
    mResponsable = pResponsable ;
  }
}