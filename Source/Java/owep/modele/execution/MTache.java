package owep.modele.execution ;


import java.util.ArrayList ;
import java.util.Date ;


/**
 * Une tâche représente le travail d'une personne sur un ou plusieurs artefact. La tâche contient
 * les estimations et mesures de charges et de dates.
 */
public class MTache extends MModeleBase
{
  // Indique l'état de la tâche
  public static final int ETAT_EN_COURS    = 0 ;
  public static final int ETAT_TERMINE     = 1 ;
  public static final int ETAT_NON_DEMARRE = 2 ;
  
  
  private int             mId ;                  // Identifie la tâche de manière unique
  private String          mNom ;                 // Nom de la tâche
  private String          mDescription ;         // Description de la tâche
  private double          mChargeInitiale ;      // Charge prévue par le chef de projet
  private double          mTempsPasse ;          // Temps passé sur la tâche
  private double          mResteAPasser ;        // Temps restant à passer sur la tâche
  private Date            mDateDebutPrevue ;     // Date de début prévue par le chef de projet
  private Date            mDateDebutReelle ;     // Date de début réelle de la tâche
  private Date            mDateFinPrevue ;       // Date de fin prévue par le chef de projet
  private Date            mDateFinReelle ;       // Date de fin réelle de la tâche
  private ArrayList       mListeArtefactEntree ; // Liste des artefacts nécessaires à la réalisation
  private ArrayList       mListeArtefactSortie ; // Liste des artefacts à produire durant la tâche
  private MActivite       mActivite ;            // Activité du processus correspondant à la tâche
  private MCollaborateur  mCollaborateur ;       // Collaborateur réalisant la tâche
  
  
  /**
   * Crée une instance vide de MTache.
   */
  public MTache ()
  {
    mListeArtefactEntree = new ArrayList () ;
    mListeArtefactSortie = new ArrayList () ;
  }
  
  
  /**
   * Crée une instance de MArtefact initialisée avec les données du chef de projet.
   * @param pId Identifiant unique de l'artefact
   * @param pNom Nom de la tâche
   * @param pDescription Description de la tâche
   * @param pChargeInitiale Charge prévue par le chef de projet
   * @param pDateDebutPrevue Date de début prévue par le chef de projet
   * @param pDateFinPrevue Date de fin prévue par le chef de projet
   * @param pActivite Activité du processus correspondant à la tâche
   */
  public MTache (int pId, String pNom, String pDescription, double pChargeInitiale,
                 Date pDateDebutPrevue, Date pDateFinPrevue, MActivite pActivite)
  {
    mId                  = pId ;
    mNom                 = pNom ;
    mDescription         = pDescription ;
    mChargeInitiale      = pChargeInitiale ;
    mDateDebutPrevue     = pDateDebutPrevue ;
    mDateFinPrevue       = pDateFinPrevue ;
    mActivite            = pActivite ;
    mListeArtefactEntree = new ArrayList () ;
    mListeArtefactSortie = new ArrayList () ;
  }
  
  
  /**
   * Crée une instance de MArtefact initialisée avec les données du chef de projet.
   * @param pId Identifiant unique de l'artefact
   * @param pNom Nom de la tâche
   * @param pDescription Description de la tâche
   * @param pChargeInitiale Charge prévue par le chef de projet
   * @param pTempsPasse Temps passé sur la tâche
   * @param pDateDebutPrevue Date de début prévue par le chef de projet
   * @param pDateDebutReelle Date de début réelle de la tâche
   * @param pDateFinPrevue Date de fin prévue par le chef de projet
   * @param pDateFinReelle Date de fin réelle de la tâche
   * @param pActivite Activité du processus correspondant à la tâche
   */
  public MTache (int pId, String pNom, String pDescription, double pChargeInitiale,
                 double pTempsPasse, double pResteAPasser, Date pDateDebutPrevue,
                 Date pDateDebutReelle, Date pDateFinPrevue, Date pDateFinReelle,
                 MActivite pActivite)
  {
    mId                  = pId ;
    mNom                 = pNom ;
    mDescription         = pDescription ;
    mChargeInitiale      = pChargeInitiale ;
    mTempsPasse          = pTempsPasse ;
    mResteAPasser        = pResteAPasser ;
    mDateDebutPrevue     = pDateDebutPrevue ;
    mDateDebutReelle     = pDateDebutReelle ;
    mDateFinPrevue       = pDateFinPrevue ;
    mDateFinReelle       = pDateFinReelle ;
    mActivite            = pActivite ;
    mListeArtefactEntree = new ArrayList () ;
    mListeArtefactSortie = new ArrayList () ;
  }
  
  
  /**
   * Récupère l'activité du processus correspondant à la tâche.
   * @return Activité du processus correspondant à la tâche
   */
  public MActivite getActivite ()
  {
    return mActivite ;
  }
  
  
  /**
   * Associe l'activité du processus correspondant à la tâche.
   * @param pActivite Activité du processus correspondant à la tâche
   */
  public void setActivite (MActivite pActivite)
  {
    mActivite = pActivite ;
  }
  
  
  /**
   * Ajoute un nouvel artefact en entrée.
   * @param pArtefactEntree Artefact en entrée
   */
  public void ajouterArtefactEntree (MArtefact pArtefactEntree)
  {
    mListeArtefactEntree.add (pArtefactEntree) ;
  }
  
  
  /**
   * Récupère un artefact en entrée à partir de son indice dans la liste.
   * @param pIndice Indice de l'artefact en entrée
   * @return Artefact en entrée
   */
  public MArtefact getArtefactEntree (int pIndice)
  {
    return (MArtefact) mListeArtefactEntree.get (pIndice) ;
  }
  
  
  /**
   * Récupère le nombre d'artefacts en entrées.
   * @return Nombre d'artefacts en entrées.
   */
  public int getNbArtefactEntree ()
  {
    return mListeArtefactEntree.size () ;
  }
  
  
  /**
   * Récupère la liste des artefacts en entrées
   * @return Liste des artefacts en entrées.
   */
  public ArrayList getListeArtefactEntree ()
  {
    return mListeArtefactEntree ;
  }
  
  
  /**
   * Initialise la liste des artefacts en entrées
   * @param pListeArtefactEntree Liste des artefacts en entrées.
   */
  public void setListeArtefactEntree (ArrayList pListeArtefactEntree)
  {
    mListeArtefactEntree = pListeArtefactEntree ;
  }
  
  
  /**
   * Ajoute un nouvel artefact en sortie.
   * @param pArtefactEntree Artefact en sortie
   */
  public void ajouterArtefactSortie (MArtefact pArtefactSortie)
  {
    mListeArtefactSortie.add (pArtefactSortie) ;
  }
  
  
  /**
   * Récupère un artefact en sortie à partir de son indice dans la liste.
   * @param pIndice Indice de l'artefact en sortie
   * @return Artefact en entrée
   */
  public MArtefact getArtefactSortie (int pIndice)
  {
    return (MArtefact) mListeArtefactSortie.get (pIndice) ;
  }


  /**
   * Récupère le nombre d'artefacts en sorties.
   * @return Nombre d'artefacts en sorties.
   */
  public int getNbArtefactSortie ()
  {
    return mListeArtefactSortie.size () ;
  }
  
  
  /**
   * Récupère la liste des artefacts en sorties
   * @return Liste des artefacts en sorties.
   */
  public ArrayList getListeArtefactSortie ()
  {
    return mListeArtefactSortie ;
  }
  
  
  /**
   * Initialise la liste des artefacts en sorties
   * @param pListeArtefactSortie Liste des artefacts en sorties.
   */
  public void setListeArtefactSortie (ArrayList pListeArtefactSortie)
  {
    mListeArtefactSortie = pListeArtefactSortie ;
  }
  
  
  /**
   * Récupère la charge prévue par le chef de projet.
   * @return Charge prévue par le chef de projet.
   */
  public double getChargeInitiale ()
  {
    return mChargeInitiale ;
  }
  
  
  /**
   * Initialise la charge prévue par le chef de projet.
   * @param pChargeInitiale Charge prévue par le chef de projet.
   */
  public void setChargeInitiale (double pChargeInitiale)
  {
    assert pChargeInitiale > 0.0 ;
    
    mChargeInitiale = pChargeInitiale ;
  }
  
  
  /**
   * Récupère le collaborateur réalisant la tâche.
   * @return Collaborateur réalisant la tâche
   */
  public MCollaborateur getCollaborateur ()
  {
    return mCollaborateur ;
  }
  
  
  /**
   * Associe le collaborateur réalisant la tâche.
   * @param pCollaborateur Collaborateur réalisant la tâche
   */
  public void setCollaborateur (MCollaborateur pCollaborateur)
  {
    mCollaborateur = pCollaborateur ;
  }
  
  
  /**
   * Récupère la date de début prévue par le chef de projet.
   * @return Date de début prévue par le chef de projet
   */
  public Date getDateDebutPrevue ()
  {
    return mDateDebutPrevue ;
  }
  
  
  /**
   * Récupère la date de début prévue par le chef de projet.
   * @param pDateDebutPrevue The mDateDebutPrevue to set.
   */
  public void setDateDebutPrevue (Date pDateDebutPrevue)
  {
    assert mDateFinPrevue != null ? pDateDebutPrevue.before (mDateFinPrevue) : true ;
    
    mDateDebutPrevue = pDateDebutPrevue ;
  }
  
  
  /**
   * Récupère la date de début réelle de la tâche.
   * @return Date de début réelle de la tâche
   */
  public Date getDateDebutReelle ()
  {
    return mDateDebutReelle ;
  }


  /**
   * Initialise la date de début réelle de la tâche.
   * @param pDateDebutReelle Date de début réelle de la tâche
   */
  public void setDateDebutReelle (Date pDateDebutReelle)
  {
    assert mDateFinReelle != null ? pDateDebutReelle.before (mDateFinReelle) : true ;
    
    mDateDebutReelle = pDateDebutReelle ;
  }
  
  
  /**
   * Récupère la date de fin prévue par le chef de projet.
   * @return Date de fin prévue par le chef de projet
   */
  public Date getDateFinPrevue ()
  {
    return mDateFinPrevue ;
  }
  
  
  /**
   * Initialise la date de fin prévue par le chef de projet.
   * @return Date de fin prévue par le chef de projet
   */
  public void setDateFinPrevue (Date pDateFinPrevue)
  {
    assert mDateDebutPrevue != null ? pDateFinPrevue.after (mDateDebutPrevue) : true ;
    
    mDateFinPrevue = pDateFinPrevue ;
  }
  
  
  /**
   * Récupère la daate de fin réelle de la tâche.
   * @return Date de fin réelle de la tâche
   */
  public Date getDateFinReelle ()
  {
    return mDateFinReelle ;
  }
  
  
  /**
   * Récupère la date de fin prévue par le chef de projet.
   * @return Date de fin réelle de la tâche
   */
  public void setDateFinReelle (Date pDateFinReelle)
  {
    assert mDateDebutReelle != null ? pDateFinReelle.after (mDateDebutReelle) : true ;
    
    mDateFinReelle = pDateFinReelle ;
  }
  
  
  /**
   * Récupère la description de la tâche.
   * @return Description de la tâche
   */
  public String getDescription ()
  {
    return mDescription ;
  }
  
  
  /**
   * Initialise la description de la tâche.
   * @param pDescription Description de la tâche
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }
  
  
  /**
   * Récupère l'état de réalisation de la tâche.
   * @return Etat de réalisation de la tâche.
   */
  public int getEtat ()
  {
    if (mTempsPasse == 0)
    {
      return ETAT_NON_DEMARRE ;
    }
    else if (mResteAPasser == 0)
    {
      return ETAT_TERMINE ;
    }
    else
    {
      return ETAT_EN_COURS ;
    }
  }
  
  
  /**
   * Récupère l'identifiant de la tâche.
   * @return Identifiant unique de la tâche
   */
  public int getId ()
  {
    return mId ;
  }
  
  
  /**
   * Initialise l'identifiant de la tâche.
   * @param pId Identifiant unique de la tâche
   */
  public void setId (int pId)
  {
    mId = pId ;
  }
  
  
  /**
   * Récupère le nom de la tâche.
   * @return Nom de la tâche
   */
  public String getNom ()
  {
    return mNom ;
  }
  
  
  /**
   * Initialise le nom de la tâche.
   * @param pNom Nom de la tâche
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }
  
  
  /**
   * Récupère le temps restant à passer sur la tâche.
   * @return Temps restant à passer sur la tâche
   */
  public double getResteAPasser ()
  {
    return mResteAPasser ;
  }
  
  
  /**
   * Récupère le temps restant à passer sur la tâche.
   * @param pResteAPasser Temps restant à passer sur la tâche
   */
  public void setResteAPasser (double pResteAPasser)
  {
    assert pResteAPasser > 0 ;
    
    mResteAPasser = pResteAPasser ;
  }
  
  
  /**
   * Récupère le temps passé sur la tâche.
   * @return Temps passé sur la tâche
   */
  public double getTempsPasse ()
  {
    return mTempsPasse ;
  }
  
  
  /**
   * Initialise le temps passé sur la tâche.
   * @param pTempsPasse Temps passé sur la tâche
   */
  public void setTempsPasse (double pTempsPasse)
  {
    assert pTempsPasse > 0 ;
    
    mTempsPasse = pTempsPasse ;
  }
  
  
  /**
   * Calcule le budget consommé pour la tâche. Formule : tempsPasse / chargeInitiale).
   * @return Budget consommé pour la tâche.
   */
  public double getBudgetConsomme ()
  {
    return mTempsPasse / mChargeInitiale ;
  }
  
  
  /**
   * Calcule le nombre d'hommes x jours de dépassement de charges.
   * Formule : tempsPasse + resteAPasser - chargeInitiale.
   * @return Nombre d'hommes x jours de dépassement de charges.
   */
  public double getHJDepassementCharge ()
  {
    return mTempsPasse + mResteAPasser - mChargeInitiale ;
  }
  
  
  /**
   * Calcule le pourcentage de dépassement de charges.
   * Formule : (tempsPasse + resteAPasser - chargeInitiale) / chargeInitiale.
   * @return Pourcentage de dépassement de charges.
   */
  public double getPrcDepassementCharge ()
  {
    return (mTempsPasse + mResteAPasser - mChargeInitiale) / mChargeInitiale ;
  }
  
  
  /**
   * Calcule le pourcentage de dépassement de charges.
   * Formule : tempsPasse / (tempsPasse + resteAPasser).
   * @return Pourcentage de dépassement de charges.
   */
  public double getPrcAvancement ()
  {
    return mTempsPasse / (mTempsPasse + mResteAPasser) ;
  }
}