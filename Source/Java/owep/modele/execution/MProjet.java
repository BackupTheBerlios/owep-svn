package owep.modele.execution ;


import java.util.Date ;
import java.util.ArrayList ;
import java.util.Iterator ;

import owep.modele.MModeleBase ;
import owep.modele.processus.MProcessus ;


/**
 * Un projet est constitué d'un ensemble de tâches, de produits et de collaborateurs. MProjet est la
 * classe centrale du paquetage execution.
 */
public class MProjet extends MModeleBase
{
  private int            mId ;                // Identifie le projet de manière unique.
  private String         mNom ;               // Nom du projet.
  private String         mDescription ;       // Description du projet.
  private String         mBilan ;             // Bilan du projet.
  private Date           mDateDebutPrevue ;   // Date de début prévue pour le projet.
  private Date           mDateFinPrevue ;     // Date de fin prévue pour le projet.
  private Date           mDateDebutReelle ;   // Date de début réelle du projet.
  private Date           mDateFinReelle ;     // Date de fin réelle du projet.
  private int            mEtat ;              // Etat de l iteration
  private double         mBudget ;            // Budget alloué au projet
  private ArrayList      mArtefacts ;         // Liste des artefacts réalisés durant le projet.
  private ArrayList      mCollaborateurs ;    // Liste des collaborateurs travaillant sur le projet.
  private ArrayList      mIterations ;        // Liste des itérations réalisées durant le projet.
  private MProcessus     mProcessus ;         // Processus utilisé par le projet.
  private MCollaborateur mChefProjet ;        // Chef de projet.
  private ArrayList      mActivitesImprevues ; // Listes des activités imprévues de ce projet
  private ArrayList      mArtefactsImprevues ; // Liste des artefacts imprévues réalisés durant le
  // projet.
  private ArrayList      mRisques ;           // Liste des risques qui doivent être gérer sur le
  // projet.
  private ArrayList      mIndicateurs ;       // Liste des indicateurs associés au projet.


  /**
   * Crée une instance vide de MProjet.
   */
  public MProjet ()
  {
    super () ;
    mIterations = new ArrayList () ;
    mArtefacts = new ArrayList () ;
    mCollaborateurs = new ArrayList () ;
    mActivitesImprevues = new ArrayList () ;
    mArtefactsImprevues = new ArrayList () ;
    mRisques = new ArrayList () ;
    mIndicateurs = new ArrayList () ;
  }

  /**
   * @param pId
   */
  public MProjet (int pId)
  {
    super () ;
    mId = pId ;

    mIterations = new ArrayList () ;
    mArtefacts = new ArrayList () ;
    mCollaborateurs = new ArrayList () ;
    mActivitesImprevues = new ArrayList () ;
    mArtefactsImprevues = new ArrayList () ;
    mRisques = new ArrayList () ;
    mIndicateurs = new ArrayList () ;
  }

  /**
   * Crée une instance initialisée de MProjet.
   * 
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
    mId = pId ;
    mNom = pNom ;
    mDescription = pDescription ;
    mDateDebutPrevue = pDateDebutPrevue ;
    mDateFinPrevue = pDateFinPrevue ;

    mIterations = new ArrayList () ;
    mArtefacts = new ArrayList () ;
    mCollaborateurs = new ArrayList () ;
    mActivitesImprevues = new ArrayList () ;
    mArtefactsImprevues = new ArrayList () ;
    mRisques = new ArrayList () ;
    mIndicateurs = new ArrayList () ;
  }

  /**
   * Permet de récupèrer l'itération en cours du projet.
   * 
   * @return l'itération en cours.
   */
  public MIteration getIterationEnCours ()
  {
    for (int i = 0 ; i < getNbIterations () ; i++)
    {
      MIteration lIteration = getIteration (i) ;
      if (lIteration.getEtat () == 1)
      {
        return lIteration ;
      }
    }
    return null ;
  }

  /**
   * Récupère la liste des artefacts réalisés durant le projet.
   * 
   * @return Liste des artefacts réalisés durant le projet.
   */
  public ArrayList getListeArtefacts ()
  {
    return mArtefacts ;
  }

  /**
   * Initialise la liste des artefacts réalisés durant le projet.
   * 
   * @param pArtefacts Liste des artefacts réalisés durant le projet.
   */
  public void setListeArtefacts (ArrayList pArtefacts)
  {
    mArtefacts = pArtefacts ;
  }

  /**
   * Récupère le nombre d'artefacts réalisés durant le projet.
   * 
   * @return Nombre d'artefacts réalisées durant le projet.
   */
  public int getNbArtefacts ()
  {
    return mArtefacts.size () ;
  }

  /**
   * Récupère l'artefact d'indice spécifié réalisée durant le projet.
   * 
   * @param pIndice Indice de l'artefact dans la liste.
   * @return Artefact réalisé durant le projet.
   */
  public MArtefact getArtefact (int pIndice)
  {
    return (MArtefact) mArtefacts.get (pIndice) ;
  }

  /**
   * Ajoute l'artefact spécifié au projet.
   * 
   * @param pArtefact Artefact réalisé durant le projet.
   */
  public void addArtefact (MArtefact pArtefact)
  {
    mArtefacts.add (pArtefact) ;
  }

  /**
   * Supprime l'artefact spécifié au projet.
   * 
   * @param pArtefact Artefact réalisé durant le projet.
   */
  public void supprimerArtefact (MArtefact pArtefact)
  {
    mArtefacts.remove (pArtefact) ;
  }

  /**
   * Récupère le chef de projet.
   * 
   * @return Chef de projet.
   */
  public MCollaborateur getChefProjet ()
  {
    return mChefProjet ;
  }

  /**
   * Initialise le chef de projet et l'ajoute à la liste des collaborateurs du projet.
   * 
   * @param pChefProjet Chef de projet.
   */
  public void setChefProjet (MCollaborateur pChefProjet)
  {
    mChefProjet = pChefProjet ;
    if (!pChefProjet.getListeProjetsChef ().contains (this))
      pChefProjet.addProjetChef (this) ;
    addCollaborateur (pChefProjet) ;
  }

  /**
   * Récupère la liste des collaborateurs sur le projet.
   * 
   * @return Liste des collaborateurs sur le projet.
   */
  public ArrayList getListeCollaborateurs ()
  {
    return mCollaborateurs ;
  }

  /**
   * Initialise la liste des collaborateurs sur le projet.
   * 
   * @param pCollaborateurs Liste des collaborateurs sur le projet.
   */
  public void setListeCollaborateurs (ArrayList pCollaborateurs)
  {
    mCollaborateurs = pCollaborateurs ;
    Iterator it = pCollaborateurs.iterator () ;
    while (it.hasNext ())
    {
      MCollaborateur lCollaborateur = (MCollaborateur) it.next () ;
      if (!lCollaborateur.getListeProjets ().contains (this))
        lCollaborateur.addProjet (this) ;
    }
  }

  /**
   * Récupère le nombre de collaborateurs sur le projet.
   * 
   * @return Nombre de collaborateurs sur le projet.
   */
  public int getNbCollaborateurs ()
  {
    return mCollaborateurs.size () ;
  }

  /**
   * Récupère le collaborateur d'indice spécifié sur le projet.
   * 
   * @param pIndice Indice du collaborateur dans la liste.
   * @return Collaborateur sur le projet.
   */
  public MCollaborateur getCollaborateur (int pIndice)
  {
    return (MCollaborateur) mCollaborateurs.get (pIndice) ;
  }

  /**
   * Ajoute le collaborateur spécifié au projet.
   * 
   * @param pCollaborateur Collaborateur sur le projet.
   */
  public void addCollaborateur (MCollaborateur pCollaborateur)
  {
    if (!mCollaborateurs.contains (pCollaborateur))
      mCollaborateurs.add (pCollaborateur) ;
    if (!pCollaborateur.getListeProjets ().contains (this))
      pCollaborateur.addProjet (this) ;
  }

  /**
   * Récupère la date de début prévue pour le projet.
   * 
   * @return Date de début prévue pour le projet.
   */
  public Date getDateDebutPrevue ()
  {
    return mDateDebutPrevue ;
  }

  /**
   * Initialise la date de début prévue pour le projet.
   * 
   * @param pDateDebutPrevue Date de début prévue pour le projet.
   */
  public void setDateDebutPrevue (Date pDateDebutPrevue)
  {
    mDateDebutPrevue = pDateDebutPrevue ;
  }

  /**
   * Récupère la date de fin prévue pour le projet.
   * 
   * @return Date de fin prévue pour le projet.
   */
  public Date getDateFinPrevue ()
  {
    return mDateFinPrevue ;
  }

  /**
   * Initialise la date de fin prévue pour le projet.
   * 
   * @param pDateFinPrevue Date de fin prévue pour le projet.
   */
  public void setDateFinPrevue (Date pDateFinPrevue)
  {
    mDateFinPrevue = pDateFinPrevue ;
  }

  /**
   * Récupère mBudget.
   * 
   * @return mBudget.
   */
  public double getBudget ()
  {
    return mBudget ;
  }

  /**
   * Initialise mBudget.
   * 
   * @param budget mBudget.
   */
  public void setBudget (double pBudget)
  {
    mBudget = pBudget ;
  }

  /**
   * Récupère la description du projet.
   * 
   * @return Description du projet.
   */
  public String getDescription ()
  {
    return mDescription ;
  }

  /**
   * Initialise la description du projet.
   * 
   * @param pDescription Description du projet.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }

  /**
   * Récupère la liste des itérations réalisées durant le projet.
   * 
   * @return Liste des itérations réalisées durant le projet.
   */
  public ArrayList getListeIterations ()
  {
    return mIterations ;
  }

  /**
   * Initialise la liste des itérations réalisées durant le projet.
   * 
   * @param pIterations Liste des itérations réalisées durant le projet.
   */
  public void setListeIterations (ArrayList pIterations)
  {
    mIterations = pIterations ;
  }

  /**
   * Récupère le nombre d'itérations réalisées durant le projet.
   * 
   * @return Nombre d'itérations réalisées durant le projet.
   */
  public int getNbIterations ()
  {
    return mIterations.size () ;
  }

  /**
   * Récupère l'itération d'indice spécifié réalisée durant le projet.
   * 
   * @param pIndice Indice de l'itération dans la liste.
   * @return Itération réalisée durant le projet.
   */
  public MIteration getIteration (int pIndice)
  {
    return (MIteration) mIterations.get (pIndice) ;
  }

  /**
   * Ajoute l'itération spécifiée au projet.
   * 
   * @param pTache Itération réalisée durant le projet.
   */
  public void addIteration (MIteration pIteration)
  {
    mIterations.add (pIteration) ;
  }

  /**
   * Récupère l'identifiant du projet.
   * 
   * @return Identifie le projet de manière unique.
   */
  public int getId ()
  {
    return mId ;
  }

  /**
   * Initialise l'identifiant du projet.
   * 
   * @param pId Identifie le projet de manière unique.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }

  /**
   * Récupère le nom du projet.
   * 
   * @return Nom du projet.
   */
  public String getNom ()
  {
    return mNom ;
  }

  /**
   * Initialise le nom du projet.
   * 
   * @param pNom Nom du projet.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }

  /**
   * Récupère le processus utilisé par le projet.
   * 
   * @return Processus utilisé par le projet.
   */
  public MProcessus getProcessus ()
  {
    return mProcessus ;
  }

  /**
   * Associe le processus utilisé par le projet.
   * 
   * @param pProcessus Processus utilisé par le projet.
   */
  public void setProcessus (MProcessus pProcessus)
  {
    mProcessus = pProcessus ;
  }

  /**
   * Récupère la liste des tâches imprévues.
   * 
   * @return mActivitesImprevues la liste des tâches imprévues.
   */
  public ArrayList getListeActivitesImprevues ()
  {
    return mActivitesImprevues ;
  }

  /**
   * Initialise la liste des tâches imprévues.
   * 
   * @param pActivitesImprevues la liste des tâches imprévues.
   */
  public void setListeActivitesImprevues (ArrayList pActivitesImprevues)
  {
    mActivitesImprevues = pActivitesImprevues ;
  }

  /**
   * Récupère l'activite imprévue d'indice spécifié nécessaire au projet.
   * 
   * @param pIndice Indice de l'activité imprevue dans la liste.
   * @return Activité imprévue nécessaire au projet.
   */
  public MActiviteImprevue getActiviteImprevue (int pIndice)
  {
    return (MActiviteImprevue) mActivitesImprevues.get (pIndice) ;
  }

  /**
   * Ajoute l'activité imprévue spécifié au projet.
   * 
   * @param pActiviteImprevue Activité imprévue au projet.
   */
  public void addActiviteImprevue (MActiviteImprevue pActiviteImprevue)
  {
    mActivitesImprevues.add (pActiviteImprevue) ;
  }

  /**
   * Supprime l'activité imprévue spécifié au projet.
   * 
   * @param pIndice Indice de l'activité imprévue dans la liste.
   */
  public void supprimerActiviteImprevue (int pIndice)
  {
    mActivitesImprevues.remove (pIndice) ;
  }

  /**
   * Supprime l'activité imprévue spécifié au projet.
   * 
   * @param pActiviteImprevue Activité imprévue au projet.
   */
  public void supprimerActiviteImprevue (MActiviteImprevue pActiviteImprevue)
  {
    mActivitesImprevues.remove (pActiviteImprevue) ;
  }

  /**
   * Récupère le nombre d'activités imprévues du projet.
   * 
   * @return le nombre d'activités imprévues du projet.
   */
  public int getNbActivitesImprevues ()
  {
    return mActivitesImprevues.size () ;
  }

  /**
   * Récupère la liste des artefacts réalisés durant le projet.
   * 
   * @return Liste des artefacts réalisés durant le projet.
   */
  public ArrayList getListeArtefactsImprevues ()
  {
    return mArtefactsImprevues ;
  }

  /**
   * Initialise la liste des artefacts réalisés durant le projet.
   * 
   * @param pArtefacts Liste des artefacts réalisés durant le projet.
   */
  public void setListeArtefactsImprevues (ArrayList pArtefactsImprevues)
  {
    mArtefactsImprevues = pArtefactsImprevues ;
  }

  /**
   * Récupère le nombre d'artefacts réalisés durant le projet.
   * 
   * @return Nombre d'artefacts réalisées durant le projet.
   */
  public int getNbArtefactsImprevues ()
  {
    return mArtefactsImprevues.size () ;
  }

  /**
   * Récupère l'artefact d'indice spécifié réalisée durant le projet.
   * 
   * @param pIndice Indice de l'artefact dans la liste.
   * @return Artefact réalisé durant le projet.
   */
  public MArtefactImprevue getArtefactImprevue (int pIndice)
  {
    return (MArtefactImprevue) mArtefactsImprevues.get (pIndice) ;
  }

  /**
   * Ajoute l'artefact spécifié au projet.
   * 
   * @param pArtefact Artefact réalisé durant le projet.
   */
  public void addArtefactImprevue (MArtefactImprevue pArtefactImprevue)
  {
    mArtefactsImprevues.add (pArtefactImprevue) ;
  }

  /**
   * Supprime l'artefact spécifié au projet.
   * 
   * @param pArtefact Artefact réalisé durant le projet.
   */
  public void supprimerArtefactImprevue (MArtefactImprevue pArtefactImprevue)
  {
    mArtefactsImprevues.remove (pArtefactImprevue) ;
  }

  /**
   * Récupère la liste des indicateurs associés au projet.
   * 
   * @return Liste des indicateurs associés au projet.
   */
  public ArrayList getListeIndicateurs ()
  {
    return mIndicateurs ;
  }

  /**
   * Initialise la liste des indicateurs associés au projet.
   * 
   * @param pArtefacts Liste des indicateurs associés au projet.
   */
  public void setListeIndicateurs (ArrayList pIndicateurs)
  {
    mIndicateurs = pIndicateurs ;
  }

  /**
   * Récupère le nombre de risques qui doivent être gérer sur le projet.
   * 
   * @return Nombre de risques qui doivent être gérer sur le projet.
   */
  public int getNbRisques ()
  {
    return mRisques.size () ;
  }

  /**
   * Récupère le risque d'indice spécifié pour le projet.
   * 
   * @param pIndice Indice du risque dans la liste.
   * @return Risque à gérer.
   */
  public MRisque getRisque (int pIndice)
  {
    return (MRisque) mRisques.get (pIndice) ;
  }

  /**
   * Récupère le risque d'indice spécifié pour le projet.
   * 
   * @param pRisque Risque à ajouter.
   */
  public void addRisque (MRisque pRisque)
  {
    mRisques.add (pRisque) ;
  }

  /**
   * Récupère la liste des risques qui doivent être gérer sur le projet.
   * 
   * @return Liste des risques qui doivent être gérer sur le projet.
   */
  public ArrayList getListeRisques ()
  {
    return mRisques ;
  }

  /**
   * Initialise la liste des risques qui doivent être gérer sur le projet.
   * 
   * @param pRisques Liste des risques qui doivent être gérer sur le projet.
   */
  public void setListeRisques (ArrayList pRisques)
  {
    mRisques = pRisques ;
  }

  /**
   * Récupère le nombre d'indicateurs sur le projet.
   * 
   * @return Nombre d'indicateurs sur le projet.
   */
  public int getNbIndicateurs ()
  {
    return mIndicateurs.size () ;
  }

  /**
   * Récupère l'indicateur d'indice spécifié pour le projet.
   * 
   * @param pIndice Indice de l'indicateur dans la liste.
   * @return Indicateur du projet.
   */
  public MIndicateur getIndicateur (int pIndice)
  {
    return (MIndicateur) mIndicateurs.get (pIndice) ;
  }

  /**
   * Ajoute l'indicateur spécifié au projet.
   * 
   * @param pIndicateur Indicateur à ajouter au projet.
   */
  public void addIndicateur (MIndicateur pIndicateur)
  {
    if (!mIndicateurs.contains (pIndicateur))
      mIndicateurs.add (pIndicateur) ;
    if (pIndicateur.getProjet () != this)
      pIndicateur.setProjet (this) ;
  }

  /**
   * @return Retourne la valeur de l'attribut bilan.
   */
  public String getBilan ()
  {
    return mBilan ;
  }

  /**
   * @param initialse bilan avec pBilan.
   */
  public void setBilan (String pBilan)
  {
    mBilan = pBilan ;
  }

  /**
   * @return Retourne la valeur de l'attribut dateDebutReelle.
   */
  public Date getDateDebutReelle ()
  {
    return mDateDebutReelle ;
  }

  /**
   * @param initialse dateDebutReelle avec pDateDebutReelle.
   */
  public void setDateDebutReelle (Date pDateDebutReelle)
  {
    mDateDebutReelle = pDateDebutReelle ;
  }

  /**
   * @return Retourne la valeur de l'attribut dateFinReelle.
   */
  public Date getDateFinReelle ()
  {
    return mDateFinReelle ;
  }

  /**
   * @param initialse dateFinReelle avec pDateFinReelle.
   */
  public void setDateFinReelle (Date pDateFinReelle)
  {
    mDateFinReelle = pDateFinReelle ;
  }
  
  /**
   * @return Retourne la valeur de l'attribut etat.
   */
  public int getEtat ()
  {
    return mEtat ;
  }
  
  /**
   * @param initialse etat avec pEtat.
   */
  public void setEtat (int pEtat)
  {
    mEtat = pEtat ;
  }
}