package owep.modele.processus ;


import java.util.ArrayList ;
import owep.modele.MModeleBase ;
import owep.modele.execution.MArtefact;


/**
 * Un produit repr�sente un �l�ment tangible produit au cours du processus. Ils sont sous la
 * responsabilit� d'un et un seul r�le. Les r�les utilisent des produits pour effectuer des
 * activit�s.
 */
public class MProduit extends MModeleBase
{
  private int        mId ;               // Identifie le produit de mani�re unique.
  private String     mNom ;              // Nom d�signant le produit.
  private String     mDescription ;      // Description du produit.
  private ArrayList  mActivitesEntrees ; // Liste des activit�s qui n�cessite le produit.
  private ArrayList  mActivitesSorties ; // Liste des activit�s qui r�alisant le produit.
  private ArrayList  mArtefacts ;        // Liste des artefacts qui instancie le produit.
  private MComposant mComposant ;        // Composant incluant le produit.
  private MRole      mResponsable ;      // R�le responsable du produit.


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
   * Construit une instance initialis�e de MProduit.
   * @param pId Identifiant unique du produit.
   * @param pNom Nom d�signant le produit.
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
   * R�cup�re la liste des activit�s en entr�es.
   * @return Liste des activit�s qui n�cessite le produit.
   */
  public ArrayList getListeActivitesEntrees ()
  {
    return mActivitesEntrees ;
  }


  /**
   * Initialise la liste des activit�s en entr�es.
   * @param pActivitesEntrees Liste des activit�s qui n�cessite le produit.
   */
  public void setListeActivitesEntrees (ArrayList pActivitesEntrees)
  {
    mActivitesEntrees = pActivitesEntrees ;
  }


  /**
   * R�cup�re le nombre des activit�s pour lesquelles le produit est en entr�e.
   * @return Nombre des activit�s qui n�cessite le produit.
   */
  public int getNbActivitesEntrees ()
  {
    return mActivitesEntrees.size () ;
  }


  /**
   * R�cup�re l'activit� d'indice sp�cifi� pour laquelle le produit est en entr�e.
   * @param pIndice Indice de l'activit� qui n�cessite le produit.
   * @return Activit� qui n�cessite le produit.
   */
  public MActivite getActiviteEntree (int pIndice)
  {
    return (MActivite) mActivitesEntrees.get (pIndice) ;
  }


  /**
   * Ajoute l'activit� sp�cifi� en entr�e.
   * @param pActivite Activit� qui n�cessite le produit.
   */
  public void addActiviteEntree (MActivite pActivite)
  {
    mActivitesEntrees.add (pActivite) ;
  }


 /**
   * R�cup�re la liste des activit�s en sorties.
   * @return Liste des activit�s qui r�alisent le produit.
   */
  public ArrayList getListeActivitesSorties ()
  {
    return mActivitesSorties ;
  }


  /**
   * Initialise la liste des activit�s en sorties.
   * @param pActivitesSorties Liste des activit�s qui r�alisent le produit.
   */
  public void setListeActivitesSorties (ArrayList pActivitesSorties)
  {
    mActivitesSorties = pActivitesSorties ;
  }


  /**
   * R�cup�re le nombre des activit�s pour lesquelles le produit est en sortie.
   * @return Nombre des activit�s qui r�alisent le produit.
   */
  public int getNbActivitesSorties ()
  {
    return mActivitesEntrees.size () ;
  }


  /**
   * R�cup�re l'activit� d'indice sp�cifi� pour laquelle le produit est en sortie.
   * @param pIndice Indice de l'activit� dans la liste.
   * @return Activit� qui r�alise le produit.
   */
  public MActivite getActiviteSortie (int pIndice)
  {
    return (MActivite) mActivitesEntrees.get (pIndice) ;
  }


  /**
   * Ajoute l'activit� sp�cifi� en sortie.
   * @param pActivite Activit� qui r�alise le produit.
   */
  public void addActiviteSortie (MActivite pActivite)
  {
    mActivitesEntrees.add (pActivite) ;
  }


  /**
   * R�cup�re le composant incluant le produit.
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
   * R�cup�re la description du produit.
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
   * R�cup�re l'identifiant du produit.
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
   * R�cup�re le nom du produit.
   * @return Nom d�signant le produit.
   */
  public String getNom ()
  {
    return mNom ;
  }


  /**
   * Initialise le nom du produit.
   * @param pNom Nom d�signant le produit.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }
  
  
  /**
   * R�cup�re la liste des artefacts qui instancie le produit.
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
   * R�cup�re le nombre des artefacts qui instancie le produit.
   * @return Nombre des artefacts qui instancie le produit.
   */
  public int getNbArtefacts ()
  {
    return mArtefacts.size () ;
  }


  /**
   * R�cup�re l'artefact d'indice sp�cifi� qui instancie le produit.
   * @param pIndice Indice de l'artefact dans la liste.
   * @return Artefact qui instancie le produit.
   */
  public MArtefact getArtefact (int pIndice)
  {
    return (MArtefact) mArtefacts.get (pIndice) ;
  }


  /**
   * Ajoute l'artefact sp�cifi� qui instancie le produit.
   * @param pArtefact Artefact qui instancie le produit.
   */
  public void addArtefact (MArtefact pArtefact)
  {
    mArtefacts.add (pArtefact) ;
  }


  /**
   * Supprime l'artefact sp�cifi� qui instancie le produit.
   * @param pArtefact Artefact qui instancie le produit.
   */
  public void supprimerArtefact (MArtefact pArtefact)
  {
    mArtefacts.remove (pArtefact) ;
  }


  /**
   * R�cup�re le r�le responsable du produit.
   * @return R�le responsable du produit.
   */
  public MRole getResponsable ()
  {
    return mResponsable ;
  }
  
  
  /**
   * Initialise le r�le responsable du produit.
   * @param pResponsable R�le responsable du produit.
   */
  public void setResponsable (MRole pResponsable)
  {
    mResponsable = pResponsable ;
  }
}