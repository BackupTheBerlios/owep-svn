package owep.modele.execution ;


import java.util.Date ;
import java.util.ArrayList ;
import owep.modele.MModeleBase;
import owep.modele.processus.MProcessus ;


/**
 * Un projet est constitu� d'un ensemble de t�ches, de produits et de collaborateurs. MProjet est la
 * classe centrale du paquetage execution.
 */
public class MProjet extends MModeleBase
{
  private int            mId ;              // Identifie le projet de mani�re unique.
  private String         mNom ;             // Nom du projet.
  private String         mDescription ;     // Description du projet.
  private Date           mDateDebutPrevue ; // Date de d�but pr�vue pour le projet.
  private Date           mDateFinPrevue ;   // Date de fin pr�vue pour le projet.
  private ArrayList      mArtefacts ;       // Liste des artefacts r�alis�s durant le projet.
  private ArrayList      mCollaborateurs ;  // Liste des collaborateurs travaillant sur le projet.
  private ArrayList      mIterations ;      // Liste des it�rations r�alis�es durant le projet.
  private MProcessus     mProcessus ;       // Processus utilis� par le projet.
  private MCollaborateur mChefProjet ;      // Chef de projet.


  /**
   * Cr�e une instance vide de MProjet.
   */
  public MProjet ()
  {
    super () ;
    
    mIterations = new ArrayList () ;
  }


  /**
   * Cr�e une instance initialis�e de MProjet.
   * @param pId Identifiant du projet.
   * @param pNom Nom du projet.
   * @param pDescription Description du projet.
   * @param pDateDebutPrevue Date de d�but pr�vue du projet.
   * @param pDateFinPrevue Date de fin pr�vue du projet.
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
   * R�cup�re la liste des artefacts r�alis�s durant le projet.
   * @return Liste des artefacts r�alis�s durant le projet.
   */
  public ArrayList getListeArtefacts ()
  {
    return mArtefacts ;
  }


  /**
   * Initialise la liste des artefacts r�alis�s durant le projet.
   * @param pArtefacts Liste des artefacts r�alis�s durant le projet.
   */
  public void setListeArtefacts (ArrayList pArtefacts)
  {
    mArtefacts = pArtefacts ;
  }


  /**
   * R�cup�re le nombre d'artefacts r�alis�s durant le projet.
   * @return Nombre d'artefacts r�alis�es durant le projet.
   */
  public int getNbArtefacts ()
  {
    return mArtefacts.size () ;
  }


  /**
   * R�cup�re l'artefact d'indice sp�cifi� r�alis�e durant le projet.
   * @param pIndice Indice de l'artefact dans la liste.
   * @return Artefact r�alis� durant le projet.
   */
  public MArtefact getArtefact (int pIndice)
  {
    return (MArtefact) mArtefacts.get (pIndice) ;
  }


  /**
   * Ajoute l'artefact sp�cifi� au projet.
   * @param pArtefact Artefact r�alis� durant le projet.
   */
  public void addArtefact (MArtefact pArtefact)
  {
    mArtefacts.add (pArtefact) ;
  }


  /**
   * Supprime l'artefact sp�cifi� au projet.
   * @param pArtefact Artefact r�alis� durant le projet.
   */
  public void supprimerArtefact (MArtefact pArtefact)
  {
    mArtefacts.remove (pArtefact) ;
  }


  /**
   * R�cup�re le chef de projet.
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
   * R�cup�re la liste des collaborateurs sur le projet.
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
   * R�cup�re le nombre de collaborateurs sur le projet.
   * @return Nombre de collaborateurs sur le projet.
   */
  public int getNbCollaborateurs ()
  {
    return mCollaborateurs.size () ;
  }


  /**
   * R�cup�re le collaborateur d'indice sp�cifi� sur le projet.
   * @param pIndice Indice du collaborateur dans la liste.
   * @return Collaborateur sur le projet.
   */
  public MCollaborateur getCollaborateur (int pIndice)
  {
    return (MCollaborateur) mCollaborateurs.get (pIndice) ;
  }


  /**
   * Ajoute le collaborateur sp�cifi� au projet.
   * @param pCollaborateur Collaborateur sur le projet.
   */
  public void addCollaborateur (MCollaborateur pCollaborateur)
  {
    mCollaborateurs.add (pCollaborateur) ;
  }


  /**
   * R�cup�re la date de d�but pr�vue pour le projet.
   * @return Date de d�but pr�vue pour le projet.
   */
  public Date getDateDebutPrevue ()
  {
    return mDateDebutPrevue ;
  }


  /**
   * Initialise la date de d�but pr�vue pour le projet.
   * @param pDateDebutPrevue Date de d�but pr�vue pour le projet.
   */
  public void setDateDebutPrevue (Date pDateDebutPrevue)
  {
    mDateDebutPrevue = pDateDebutPrevue ;
  }


  /**
   * R�cup�re la date de fin pr�vue pour le projet.
   * @return Date de fin pr�vue pour le projet.
   */
  public Date getDateFinPrevue ()
  {
    return mDateFinPrevue ;
  }


  /**
   * Initialise la date de fin pr�vue pour le projet.
   * @param pDateFinPrevue Date de fin pr�vue pour le projet.
   */
  public void setDateFinPrevue (Date pDateFinPrevue)
  {
    mDateFinPrevue = pDateFinPrevue ;
  }


  /**
   * R�cup�re la description du projet.
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
   * R�cup�re la liste des it�rations r�alis�es durant le projet.
   * @return Liste des it�rations r�alis�es durant le projet.
   */
  public ArrayList getListeIterations ()
  {
    return mIterations ;
  }


  /**
   * Initialise la liste des it�rations r�alis�es durant le projet.
   * @param pIterations Liste des it�rations r�alis�es durant le projet.
   */
  public void setListeIterations (ArrayList pIterations)
  {
    mIterations = pIterations ;
  }


  /**
   * R�cup�re le nombre d'it�rations r�alis�es durant le projet.
   * @return Nombre d'it�rations r�alis�es durant le projet.
   */
  public int getNbIterations ()
  {
    return mIterations.size () ;
  }


  /**
   * R�cup�re l'it�ration d'indice sp�cifi� r�alis�e durant le projet.
   * @param pIndice Indice de l'it�ration dans la liste.
   * @return It�ration r�alis�e durant le projet.
   */
  public MIteration getIteration (int pIndice)
  {
    return (MIteration) mIterations.get (pIndice) ;
  }


  /**
   * Ajoute l'it�ration sp�cifi�e au projet.
   * @param pTache It�ration r�alis�e durant le projet.
   */
  public void addIteration (MIteration pIteration)
  {
    mIterations.add (pIteration) ;
  }


  /**
   * R�cup�re l'identifiant du projet.
   * @return Identifie le projet de mani�re unique.
   */
  public int getId ()
  {
    return mId ;
  }


  /**
   * Initialise l'identifiant du projet.
   * @param pId Identifie le projet de mani�re unique.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }


  /**
   * R�cup�re le nom du projet.
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
   * R�cup�re le processus utilis� par le projet.
   * @return Processus utilis� par le projet.
   */
  public MProcessus getProcessus ()
  {
    return mProcessus ;
  }


  /**
   * Associe le processus utilis� par le projet.
   * @param pProcessus Processus utilis� par le projet.
   */
  public void setProcessus (MProcessus pProcessus)
  {
    mProcessus = pProcessus ;
  }
}
