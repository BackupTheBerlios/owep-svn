package owep.modele.execution;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import owep.modele.MModeleBase;


/**
 * Description de la classe.
 */
public class MTacheImprevue extends MModeleBase
{
  public static final int ETAT_CREEE = -1 ; // Tâche créée
  public static final int ETAT_NON_DEMARRE = 0 ; // Tâche prête
  public static final int ETAT_EN_COURS    = 1 ;
  public static final int ETAT_SUSPENDU = 2 ;
  public static final int ETAT_TERMINE     = 3 ;


  private int                mId ;                // Identifie la tâche de manière unique.
  private String             mNom ;               // Nom de la tâche.
  private String             mDescription ;       // Description de la tâche.
  private int                mEtat ;              // Etat de la tâche.
  private double             mChargeInitiale ;    // Charge prévue par le chef de projet.
  private double             mTempsPasse ;        // Temps passé sur la tâche.
  private double             mResteAPasser ;      // Temps restant à passer sur la tâche.
  private Date               mDateDebutPrevue ;   // Date de début prévue par le chef de projet.
  private Date               mDateFinPrevue ;     // Date de fin prévue par le chef de projet.
  private Date               mDateDebutReelle ;   // Date de début réelle de la tâche.
  private Date               mDateFinReelle ;     // Date de fin réelle de la tâche.
  private ArrayList          mArtefactsImprevuesEntrees ;  // Liste des artefacts nécessaires à la tâche.
  private ArrayList          mArtefactsImprevuesSorties ;  // Liste des artefacts à produire durant la tâche.
  private MActiviteImprevue  mActiviteImprevue ;  // Activité imprévue que la tâche instancie. 
  private MCollaborateur     mCollaborateur ;     // Collaborateur réalisant la tâche.
  private MIteration         mIteration ;         // Itération durant laquelle est effectuée la tâche.
  private long               mDateDebutChrono ;   // Date a laquelle on demarre le chrono pour le temps passé sur une tache
  private ArrayList          mConditions ;        // Liste de conditions nécessaires pour que la tâche soit dans l'état prêt
  private ArrayList          mProblemesEntrees ;  // Liste des problèmes résultant de la tâche.
  private ArrayList          mProblemesSorties ;  // Liste des problèmes que résout la tâche.

  /**
   * Crée une instance vide de MTache.
   */
  public MTacheImprevue ()
  {
    super () ;
    
    mEtat = ETAT_NON_DEMARRE ;
    
    mArtefactsImprevuesEntrees  = new ArrayList () ;
    mArtefactsImprevuesSorties  = new ArrayList () ;
    mProblemesEntrees  = new ArrayList () ;
    mProblemesSorties  = new ArrayList () ;
    mConditions        = new ArrayList () ;
  }


  /**
   * Crée une instance de MTacheImprevue initialisée avec les données du chef de projet.
   * @param pId Identifiant unique de la tâche imprévue.
   * @param pNom Nom de la tâche.
   * @param pDescription Description de la tâche.
   * @param pChargeInitiale Charge prévue par le chef de projet.
   * @param pDateDebutPrevue Date de début prévue par le chef de projet.
   * @param pDateFinPrevue Date de fin prévue par le chef de projet.
   */
  public MTacheImprevue (int pId, String pNom, String pDescription, double pChargeInitiale,
                 Date pDateDebutPrevue, Date pDateFinPrevue)
  {
    super () ;
    
    mId              = pId ;
    mNom             = pNom ;
    mDescription     = pDescription ;
    mEtat            = ETAT_NON_DEMARRE ;
    mChargeInitiale  = pChargeInitiale ;
    mDateDebutPrevue = pDateDebutPrevue ;
    mDateFinPrevue   = pDateFinPrevue ;
    
    mArtefactsImprevuesEntrees  = new ArrayList () ;
    mArtefactsImprevuesSorties  = new ArrayList () ;
    mProblemesEntrees  = new ArrayList () ;
    mProblemesSorties  = new ArrayList () ;
    mConditions        = new ArrayList () ;
  }


  /**
   * Crée une instance de MTacheImprevue initialisée avec les données du chef de projet.
   * @param pId Identifiant unique de la tâche imprévue.
   * @param pNom Nom de la tâche.
   * @param pDescription Description de la tâche.
   * @param pChargeInitiale Charge prévue par le chef de projet.
   * @param pTempsPasse Temps passé sur la tâche.
   * @param pResteAPasser Temps restant à passer sur la tâche.
   * @param pDateDebutPrevue Date de début prévue par le chef de projet.
   * @param pDateDebutReelle Date de début réelle de la tâche.
   * @param pDateFinPrevue Date de fin prévue par le chef de projet.
   * @param pDateFinReelle Date de fin réelle de la tâche.
   */
  public MTacheImprevue (int pId, String pNom, String pDescription, double pChargeInitiale,
                 double pTempsPasse, double pResteAPasser, Date pDateDebutPrevue,
                 Date pDateFinPrevue, Date pDateDebutReelle, Date pDateFinReelle)
  {
    super () ;
    
    mId              = pId ;
    mNom             = pNom ;
    mDescription     = pDescription ;
    mEtat            = ETAT_NON_DEMARRE ;
    mChargeInitiale  = pChargeInitiale ;
    mTempsPasse      = pTempsPasse ;
    mResteAPasser    = pResteAPasser ;
    mDateDebutPrevue = pDateDebutPrevue ;
    mDateDebutReelle = pDateDebutReelle ;
    mDateFinPrevue   = pDateFinPrevue ;
    mDateFinReelle   = pDateFinReelle ;
    
    mArtefactsImprevuesEntrees  = new ArrayList () ;
    mArtefactsImprevuesSorties  = new ArrayList () ;
    mProblemesEntrees  = new ArrayList () ;
    mProblemesSorties  = new ArrayList () ;
    mConditions        = new ArrayList () ;
  }


  /**
   * Constructeur par copie.
   * @param pTache Tache à recopier.
   */
  public MTacheImprevue (MTacheImprevue pTache)
  {
    super () ;
    
    mNom              = pTache.getNom() ;
    mDescription      = pTache.getDescription() ;
    mEtat             = pTache.getEtat() ;
    mChargeInitiale   = pTache.getChargeInitiale() ;
    mTempsPasse       = pTache.getTempsPasse() ;
    mResteAPasser     = pTache.getResteAPasser() ;
    mDateDebutPrevue  = pTache.getDateDebutPrevue() ;
    mDateDebutReelle  = pTache.getDateDebutReelle() ;
    mDateFinPrevue    = pTache.getDateFinPrevue() ;
    mDateFinReelle    = pTache.getDateFinReelle() ;
    mCollaborateur    = pTache.getCollaborateur() ;
    mActiviteImprevue = pTache.getActiviteImprevue() ;
    
    mArtefactsImprevuesEntrees  = pTache.getListeArtefactsImprevuesEntrees() ;
    mArtefactsImprevuesSorties  = pTache.getListeArtefactsImprevuesSorties() ;
  }
  
  
  /**
   * Insertion de la tâche imprévue courante dans la base de données.
   * @param pConnection Connexion avec la base de données
   * @throws SQLException Si une erreur survient durant l'insertion dans la BD.
   */
  public void create (Connection pConnection) throws SQLException 
  {
    assert getActiviteImprevue () != null ;
    
    
    Statement lRequest = pConnection.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE) ;
    ResultSet curseurTache = lRequest.executeQuery ("SELECT * FROM TIM_TACHEIMPREVUE") ;
    curseurTache.moveToInsertRow () ;
    curseurTache.updateString (2, getNom ()) ;
    curseurTache.updateString (3, getDescription ()) ;
    curseurTache.updateDouble (4,  getChargeInitiale ()) ;
    curseurTache.updateDouble (5, getTempsPasse ()) ;
    curseurTache.updateDouble (6, getResteAPasser ()) ;
    curseurTache.updateInt (7, getEtat ()) ;
    curseurTache.updateDate (8, new java.sql.Date (getDateDebutPrevue ().getTime())) ;
    curseurTache.updateDate (9, new java.sql.Date (getDateFinPrevue ().getTime ())) ;
    curseurTache.updateInt (12, getIteration ().getId ()) ;
    curseurTache.updateInt (13, getCollaborateur ().getId ()) ;
    curseurTache.updateInt (14, getActiviteImprevue ().getId ()) ;
    
    curseurTache.insertRow () ;
    curseurTache.close () ;
    
//  Préparation de la requête permettant d'obtenir l'id de la tâche
    String lRequete = "SELECT MAX(TIM_ID) FROM TIM_TACHEIMPREVUE" ;
    ResultSet result = lRequest.executeQuery (lRequete) ;
    if (result.next ())
    {
      setId (result.getInt (1)) ;
    }
    result.close () ;
  }

  
  /**
   * Permet de mettre à jour la tâche courant dans la base de données
   * @param pConnection Connexion avec la BD.
   * @throws SQLException Si une erreur survient durant la mise à jour.
   */
  public void update (Connection pConnection) throws SQLException
  {
    assert getActiviteImprevue () != null ;
    
    int lId = getId () ;
    
    String lRequete = "UPDATE TIM_TACHEIMPREVUE SET " ;
    lRequete += "TIM_NOM = '" + getNom () + "', " ;
    lRequete += "TIM_DESCRIPTION = '" + getDescription () + "', " ;
    lRequete += "TIM_CHARGEINITIALE = " + getChargeInitiale () + ", " ;
    lRequete += "TIM_TEMPSPASSE = " + getTempsPasse () + ", " ;
    lRequete += "TIM_RESTEAPASSER = " + getResteAPasser () + ", " ;
    lRequete += "TIM_ETAT = " + getEtat () + ", " ;
    lRequete += "TIM_DATEDEBUTPREVUE = '" + getDateDebutPrevue () + "', " ;
    lRequete += "TIM_DATEFINPREVUE = '" + getDateFinPrevue () + "', " ;
    lRequete += "TIM_DATEDEBUTREELLE = '" + getDateDebutReelle () + "', " ;
    lRequete += "TIM_DATEFINREELLE = '" + getDateFinReelle () + "', " ;
    lRequete += "TIM_ITE_ID = " + getIteration ().getId () + ", " ;
    lRequete += "TIM_COL_ID = " + getCollaborateur ().getId () + ", " ;
    lRequete += "TIM_AIM_ID = " + getActiviteImprevue ().getId () + " " ;
    lRequete += "WHERE TIM_ID = " + lId ;
    
    Statement lRequest = pConnection.createStatement () ;
    lRequest.executeUpdate (lRequete) ;
  }
  
  
  /**
   * Récupère l'activité imprévue que la tâche instancie.
   * @return Activité imprévue que la tâche instancie.
   */
  public MActiviteImprevue getActiviteImprevue ()
  {
    return mActiviteImprevue ;
  }


  /**
   * Associe l'activité imprévue que la tâche instancie.
   * @param pActiviteImprevue Activité imprévue que la tâche instancie.
   */
  public void setActiviteImprevue (MActiviteImprevue pActiviteImprevue)
  {
    mActiviteImprevue = pActiviteImprevue ;
  }


  /**
   * Récupère la liste des artefacts imprévues en entrées de la tâche.
   * @return Liste des artefacts imprévues nécessaires à la tâche.
   */
  public ArrayList getListeArtefactsImprevuesEntrees ()
  {
    return mArtefactsImprevuesEntrees ;
  }


  /**
   * Initialise la liste des artefacts imprévues en entrées de la tâche.
   * @param pArtefactsImprevuesEntrees Liste des artefacts imprévues nécessaires à la tâche.
   */
  public void setListeArtefactsImprevuesEntrees (ArrayList pArtefactsImprevuesEntrees)
  {
    mArtefactsImprevuesEntrees = pArtefactsImprevuesEntrees ;
  }


  /**
   * Récupère le nombre d'artefacts imprévues en entrées.
   * @return Nombre d'artefacts imprévues en entrées.
   */
  public int getNbArtefactsImprevuesEntrees ()
  {
    return mArtefactsImprevuesEntrees.size () ;
  }


  /**
   * Récupère l'artefact imprévue d'indice spécifié nécessaire à la tâche.
   * @param pIndice Indice de l'artefact imprévue en entrée dans la liste.
   * @return Artefact imprévue nécessaire à la tâche.
   */
  public MArtefactImprevue getArtefactImprevueEntree (int pIndice)
  {
    return (MArtefactImprevue) mArtefactsImprevuesEntrees.get (pIndice) ;
  }


  /**
   * Ajoute l'artefact imprévue spécifié en entrée de la tâche.
   * @param pArtefactImprevue Artefact imprévue nécessaire à la tâche.
   */
  public void addArtefactImprevueEntrees (MArtefactImprevue pArtefactImprevue)
  {
    mArtefactsImprevuesEntrees.add (pArtefactImprevue) ;
  }


  /**
   * Supprime l'artefact imprévue spécifié en entrée de la tâche.
   * @param pIndice Indice de l'artefact imprévue dans la liste.
   */
  public void supprimerArtefactImprevueEntree (int pIndice)
  {
    mArtefactsImprevuesEntrees.remove (pIndice) ;
  }


  /**
   * Supprime l'artefact imprévue spécifié en entrée de la tâche.
   * @param pArtefactImprevue Artefact imprévue nécessaire à la tâche. 
   */
  public void supprimerArtefactImprevueEntree (MArtefactImprevue pArtefactImprevue)
  {
    mArtefactsImprevuesEntrees.remove (pArtefactImprevue) ;
  }


  /**
   * Récupère la liste des artefacts imprévues en sorties de la tâche.
   * @return Liste des artefacts imprévues à produire durant la tâche.
   */
  public ArrayList getListeArtefactsImprevuesSorties ()
  {
    return mArtefactsImprevuesSorties ;
  }


  /**
   * Initialise la liste des artefacts imprévues en sorties de la tâche.
   * @param pArtefactsImprevuesSorties Liste des artefacts imprévues à produire durant la tâche.
   */
  public void setListeArtefactsImprevuesSorties (ArrayList pArtefactsImprevuesSorties)
  {
    mArtefactsImprevuesSorties = pArtefactsImprevuesSorties ;
  }


  /**
   * Récupère le nombre d'artefacts imprévues en sorties.
   * @return Nombre d'artefacts imprévues en sorties.
   */
  public int getNbArtefactsImprevuesSorties ()
  {
    return mArtefactsImprevuesSorties.size () ;
  }


  /**
   * Récupère l'artefact imprévue d'indice spécifié à produire durant la tâche.
   * @param pIndice Indice de l'artefact imprévue en sortie dans la liste.
   * @return Artefact imprévue à produire durant la tâche.
   */
  public MArtefactImprevue getArtefactImprevueSortie (int pIndice)
  {
    return (MArtefactImprevue) mArtefactsImprevuesSorties.get (pIndice) ;
  }


  /**
   * Ajoute l'artefact imprévue spécifié en sortie de la tâche.
   * @param pArtefactImprevue Artefact imprévue à produire durant la tâche.
   */
  public void addArtefactImprevueSortie (MArtefactImprevue pArtefactImprevue)
  {
    mArtefactsImprevuesSorties.add (pArtefactImprevue) ;
  }


  /**
   * Supprime l'artefact imprévue spécifié en sortie de la tâche.
   * @param pIndice Indice de l'artefact imprévue dans la liste.
   */
  public void supprimerArtefactImprevueSortie (int pIndice)
  {
    mArtefactsImprevuesSorties.remove (pIndice) ;
  }


  /**
   * Supprime l'artefact imprévue spécifié en sortie de la tâche.
   * @param pArtefactImprevue Artefact imprévue à produire durant la tâche.
   */
  public void supprimerArtefactImprevueSortie (MArtefactImprevue pArtefactImprevue)
  {
    mArtefactsImprevuesSorties.remove (pArtefactImprevue) ;
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
   * @return Collaborateur réalisant la tâche.
   */
  public MCollaborateur getCollaborateur ()
  {
    return mCollaborateur ;
  }


  /**
   * Associe le collaborateur réalisant la tâche.
   * @param pCollaborateur Collaborateur réalisant la tâche.
   */
  public void setCollaborateur (MCollaborateur pCollaborateur)
  {
    mCollaborateur = pCollaborateur ;
  }


  /**
   * Récupère la date de début prévue par le chef de projet.
   * @return Date de début prévue par le chef de projet.
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
   * @return Date de début réelle de la tâche.
   */
  public Date getDateDebutReelle ()
  {
    return mDateDebutReelle ;
  }


  /**
   * Initialise la date de début réelle de la tâche.
   * @param pDateDebutReelle Date de début réelle de la tâche.
   */
  public void setDateDebutReelle (Date pDateDebutReelle)
  {
    assert mDateFinReelle != null ? pDateDebutReelle.before (mDateFinReelle) : true ;
    mDateDebutReelle = pDateDebutReelle ;
  }


  /**
   * Récupère la date de fin prévue par le chef de projet.
   * @return Date de fin prévue par le chef de projet.
   */
  public Date getDateFinPrevue ()
  {
    return mDateFinPrevue ;
  }


  /**
   * Initialise la date de fin prévue par le chef de projet.
   * @param pDateFinPrevue Date de fin prévue par le chef de projet.
   */
  public void setDateFinPrevue (Date pDateFinPrevue)
  {
    assert mDateDebutPrevue != null ? pDateFinPrevue.after (mDateDebutPrevue) : true ;
    mDateFinPrevue = pDateFinPrevue ;
  }


  /**
   * Récupère la daate de fin réelle de la tâche.
   * @return Date de fin réelle de la tâche.
   */
  public Date getDateFinReelle ()
  {
    return mDateFinReelle ;
  }


  /**
   * Récupère la date de fin prévue par le chef de projet.
   * @param pDateFinReelle Date de fin réelle de la tâche.
   */
  public void setDateFinReelle (Date pDateFinReelle)
  {
    assert mDateDebutReelle != null ? pDateFinReelle.after (mDateDebutReelle) : true ;
    mDateFinReelle = pDateFinReelle ;
  }


  /**
   * Récupère la description de la tâche.
   * @return Description de la tâche.
   */
  public String getDescription ()
  {
    return mDescription ;
  }


  /**
   * Initialise la description de la tâche.
   * @param pDescription Description de la tâche.
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
    return mEtat ;
  }


  /**
   * Initialise l'état de réalisation de la tâche.
   * @param pEtat Etat de réalisation de la tâche.
   */
  public void setEtat (int pEtat)
  {
    mEtat = pEtat ;
    if (mEtat == ETAT_TERMINE)
    {
      mResteAPasser = 0 ;
    }
    mEtat = pEtat ;
  }


  /**
   * Récupère la liste des conditions pour le passage de la tâche à l'état prêt
   * @return Liste des conditions pour le passage de la tâche à l'état prêt
   */
  public ArrayList getListeConditions ()
  {
    return mConditions ;
  }


  /**
   * Initialise la liste des conditions pour le passage de la tâche à l'état prêt
   * @param pArtefactsEntrees Liste des conditions pour le passage de la tâche à l'état prêt
   */
  public void setListeConditions (ArrayList pConditions)
  {
    mConditions = pConditions ;
  }


  /**
   * Récupère le nombre de conditions.
   * @return Nombre de conditions.
   */
  public int getNbConditions ()
  {
    return mConditions.size () ;
  }


  /**
   * Récupère la condition de la tache d'indice spécifié
   * @param pIndice Indice de la condition de la tache
   * @return Condition de la tâche.
   */
  public MCondition getCondition (int pIndice)
  {
    return (MCondition) mConditions.get (pIndice) ;
  }


  /**
   * Ajoute la condition spécifiée à la tâche.
   * @param pCondition condition de la tâche.
   */
  public void addCondition (MCondition pCondition)
  {
    mConditions.add (pCondition) ;
  }


  /**
   * Supprime la condition.
   * @param pIndice Indice de la condition dans la liste.
   */
  public void supprimerCondition (int pIndice)
  {
    mConditions.remove (pIndice) ;
  }


  /**
   * Supprime la condition.
   * @param pCondition Condition de la tâche.
   */
  public void supprimerConditions (MCondition pCondition)
  {
    mConditions.remove (pCondition) ;
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
   * Récupère l'itération durant laquelle est effectuée la tâche.
   * @return Itération durant laquelle est effectuée la tâche.
   */
  public MIteration getIteration ()
  {
    return mIteration ;
  }


  /**
   * Initialise l'itération durant laquelle est effectuée la tâche.
   * @param pIteration Itération durant laquelle est effectuée la tâche.
   */
  public void setIteration (MIteration pIteration)
  {
    mIteration = pIteration ;
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
   * Initialise le temps restant à passer sur la tâche.
   * @param pResteAPasser Temps restant à passer sur la tâche
   */
  public void setResteAPasser (double pResteAPasser)
  {
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
   * Calcule le nombre d'hommes x jours de dépassement de charges. Formule : tempsPasse +
   * resteAPasser - chargeInitiale.
   * @return Nombre d'hommes x jours de dépassement de charges.
   */
  public double getHJDepassementCharge ()
  {
    return mTempsPasse + mResteAPasser - mChargeInitiale ;
  }


  /**
   * Calcule le pourcentage de dépassement de charges. Formule : (tempsPasse + resteAPasser -
   * chargeInitiale) / chargeInitiale.
   * @return Pourcentage de dépassement de charges.
   */
  public double getPrcDepassementCharge ()
  {
    return (mTempsPasse + mResteAPasser - mChargeInitiale) / mChargeInitiale ;
  }


  /**
   * Calcule le pourcentage de dépassement de charges. Formule : tempsPasse / (tempsPasse +
   * resteAPasser).
   * @return Pourcentage de dépassement de charges.
   */
  public double getPrcAvancement ()
  {
    return mTempsPasse / (mTempsPasse + mResteAPasser) ;
  }

  
   /**
   * Recupere la date de lancement du chronometre.
   * @return Date de lancement du chronometre
   */
  public long getDateDebutChrono ()
  {
    return mDateDebutChrono ;
  }


  /**
   * Initialise la date de lancement du chronometre.
   * @param pDateDebutChrono Date de lancement du chronometre.
   */
  public void setDateDebutChrono (long pDateDebutChrono)
  {
    mDateDebutChrono = pDateDebutChrono ;
  }
  
  
  /**
   * Récupère la liste des problèmes résultant de la tâche.
   * @return Liste des problèmes résultant de la tâche.
   */
  public ArrayList getListeProblemesEntrees ()
  {
    return mProblemesEntrees ;
  }


  /**
   * Initialise la liste des problèmes résultant de la tâche.
   * @param pProblemesEntrees Liste des problèmes résultant de la tâche.
   */
  public void setListeProblemesEntrees (ArrayList pProblemesEntrees)
  {
    mProblemesEntrees = pProblemesEntrees ;
  }


  /**
   * Ajoute un problème résultant de la tâche.
   * @param pProblemeEntree Problème résultant de la tâche.
   */
  public void addProblemeEntree (MProbleme pProblemeEntree)
  {
    mProblemesEntrees.add (pProblemeEntree) ;
  }


  /**
   * Récupère la liste des problèmes que résout la tâche.
   * @return Liste des problèmes que résout la tâche.
   */
  public ArrayList getListeProblemesSorties ()
  {
    return mProblemesSorties ;
  }


  /**
   * Initialise la liste des problèmes que résout la tâche.
   * @param pProblemesSorties Liste des problèmes que résout la tâche.
   */
  public void setListeProblemesSorties (ArrayList pProblemesSorties)
  {
    mProblemesSorties = pProblemesSorties ;
  }
 

  /**
   * Ajoute un problème que résout la tâche.
   * @param pProblemeSortie Problème que résout la tâche.
   */
  public void addProblemeSortie (MProbleme pProblemeSortie)
  {
    mProblemesSorties.add (pProblemeSortie) ;
  }
}
