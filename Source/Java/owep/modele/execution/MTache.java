package owep.modele.execution ;


import java.util.ArrayList ;
import java.util.Date ;
import owep.modele.MModeleBase ;
import owep.modele.processus.MActivite ;
import java.sql.Connection ;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 * Une tâche représente le travail d'une personne sur un ou plusieurs artefact. La tâche contient
 * les estimations et mesures de charges et de dates.
 */
public class MTache extends MModeleBase
{
  public static final int ETAT_CREEE = -1 ; // Tâche créée
  public static final int ETAT_NON_DEMARRE = 0 ; // Tâche prête
  public static final int ETAT_EN_COURS    = 1 ;
  public static final int ETAT_SUSPENDU = 2 ;
  public static final int ETAT_TERMINE     = 3 ;


  private int            mId ;               // Identifie la tâche de manière unique.
  private String         mNom ;              // Nom de la tâche.
  private String         mDescription ;      // Description de la tâche.
  private int            mEtat ;             // Etat de la tâche.
  private double         mChargeInitiale ;   // Charge prévue par le chef de projet.
  private double         mTempsPasse ;       // Temps passé sur la tâche.
  private double         mResteAPasser ;     // Temps restant à passer sur la tâche.
  private Date           mDateDebutPrevue ;  // Date de début prévue par le chef de projet.
  private Date           mDateFinPrevue ;    // Date de fin prévue par le chef de projet.
  private Date           mDateDebutReelle ;  // Date de début réelle de la tâche.
  private Date           mDateFinReelle ;    // Date de fin réelle de la tâche.
  private ArrayList      mArtefactsEntrees ; // Liste des artefacts nécessaires à la tâche.
  private ArrayList      mArtefactsSorties ; // Liste des artefacts à produire durant la tâche.
  private MActivite      mActivite ;         // Activité que la tâche instancie. 
  private MCollaborateur mCollaborateur ;    // Collaborateur réalisant la tâche.
  private MIteration     mIteration ;        // Itération durant laquelle est effectuée la tâche.
  private long           mDateDebutChrono ;  // Date a laquelle on demarre le chrono pour le temps passé sur une tache.
  private ArrayList      mConditions ;       // Liste de conditions nécessaires pour que la tâche soit dans l'état prêt.
  private ArrayList      mProblemesEntrees ; // Liste des problèmes résultant de la tâche.
  private ArrayList      mProblemesSorties ; // Liste des problèmes que résout la tâche.



  /**
   * Crée une instance vide de MTache.
   */
  public MTache ()
  {
    super () ;
    
    mEtat = ETAT_CREEE ;
    
    mArtefactsEntrees  = new ArrayList () ;
    mArtefactsSorties  = new ArrayList () ;
    mConditions        = new ArrayList () ;
    mProblemesEntrees  = new ArrayList () ;
    mProblemesSorties  = new ArrayList () ;
  }


  /**
   * Crée une instance de MArtefact initialisée avec les données du chef de projet.
   * @param pId Identifiant unique de l'artefact.
   * @param pNom Nom de la tâche.
   * @param pDescription Description de la tâche.
   * @param pChargeInitiale Charge prévue par le chef de projet.
   * @param pDateDebutPrevue Date de début prévue par le chef de projet.
   * @param pDateFinPrevue Date de fin prévue par le chef de projet.
   */
  public MTache (int pId, String pNom, String pDescription, double pChargeInitiale,
                 Date pDateDebutPrevue, Date pDateFinPrevue)
  {
    super () ;
    
    mId              = pId ;
    mNom             = pNom ;
    mDescription     = pDescription ;
    mEtat            = ETAT_CREEE ;
    mChargeInitiale  = pChargeInitiale ;
    mDateDebutPrevue = pDateDebutPrevue ;
    mDateFinPrevue   = pDateFinPrevue ;
    
    mArtefactsEntrees  = new ArrayList () ;
    mArtefactsSorties  = new ArrayList () ;
    mConditions        = new ArrayList () ;
    mProblemesEntrees  = new ArrayList () ;
    mProblemesSorties  = new ArrayList () ;
  }


  public MTache (int pId, String pNom, String pDescription, double pChargeInitiale,
                 double pTempsPasse, double pResteAPasser, Date pDateDebutPrevue,
                 Date pDateFinPrevue, Date pDateDebutReelle, Date pDateFinReelle)
  {
    super () ;
    
    mId              = pId ;
    mNom             = pNom ;
    mDescription     = pDescription ;
    mEtat            = ETAT_CREEE ;
    mChargeInitiale  = pChargeInitiale ;
    mTempsPasse      = pTempsPasse ;
    mResteAPasser    = pResteAPasser ;
    mDateDebutPrevue = pDateDebutPrevue ;
    mDateDebutReelle = pDateDebutReelle ;
    mDateFinPrevue   = pDateFinPrevue ;
    mDateFinReelle   = pDateFinReelle ;
    
    mArtefactsEntrees  = new ArrayList () ;
    mArtefactsSorties  = new ArrayList () ;
    mConditions        = new ArrayList () ;
    mProblemesEntrees  = new ArrayList () ;
    mProblemesSorties  = new ArrayList () ;
  }
  

  /**
   * Constructeur par copie.
   * @param pTache Tache à recopier.
   */
  public MTache (MTache pTache)
  {
    super () ;
    
    mNom             = pTache.getNom() ;
    mDescription     = pTache.getDescription() ;
    mEtat            = pTache.getEtat() ;
    mChargeInitiale  = pTache.getChargeInitiale() ;
    mTempsPasse      = pTache.getTempsPasse() ;
    mResteAPasser    = pTache.getResteAPasser() ;
    mDateDebutPrevue = pTache.getDateDebutPrevue() ;
    mDateDebutReelle = pTache.getDateDebutReelle() ;
    mDateFinPrevue   = pTache.getDateFinPrevue() ;
    mDateFinReelle   = pTache.getDateFinReelle() ;
    mCollaborateur   = pTache.getCollaborateur() ;
    mActivite        = pTache.getActivite() ;
    
    mArtefactsEntrees  = pTache.getListeArtefactsEntrees() ;
    mArtefactsSorties  = pTache.getListeArtefactsSorties() ;
  }
  
  
  /**
   * Insertion de la tâche courante dans la base de données.
   * @param pConnection Connexion avec la base de données
   * @throws SQLException Si une erreur survient durant l'insertion dans la BD.
   */
  public void create (Connection pConnection) throws SQLException 
  {
    assert getActivite () != null ;
    
    // Préparation de la requête d'insertion
    /*String lRequete = "INSERT INTO TAC_TACHE (TAC_NOM, TAC_DESCRIPTION, TAC_CHARGEINITIALE, TAC_TEMPSPASSE, TAC_RESTEAPASSER, TAC_ETAT, TAC_DATEDEBUTPREVUE, TAC_DATEFINPREVUE, TAC_DATEDEBUTREELLE, TAC_DATEFINREELLE, TAC_ITE_ID, TAC_COL_ID, TAC_ACT_ID) VALUES (' " ;
    lRequete += getNom () + ", '" ;
    lRequete += getDescription () + "', " ;
    lRequete += getChargeInitiale () + ", " ;
    lRequete += getTempsPasse () + ", " ;
    lRequete += getResteAPasser () + ", " ;
    lRequete += getEtat () + ", '" ;
    lRequete += getDateDebutPrevue () + "', '" ;
    lRequete += getDateFinPrevue () + "', '" ;
    lRequete += getDateDebutReelle () + "', '" ;
    lRequete += getDateFinReelle () + "', " ;
    lRequete += getIteration ().getId () + ", " ;
    lRequete += getCollaborateur ().getId () + ", " ;
    if (getActivite () != null)
    {
      lRequete += getActivite ().getId () + ") " ;
    }
    else
    {
      lRequete += "NULL) " ;
    }
    Statement lRequest = pConnection.createStatement () ;
    lRequest.executeQuery (lRequete) ;*/
    
    Statement lRequest = pConnection.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE) ;
    ResultSet curseurTache = lRequest.executeQuery ("SELECT * FROM TAC_TACHE") ;
    curseurTache.moveToInsertRow () ;
    curseurTache.updateString (2, getNom ()) ;
    curseurTache.updateString (3, getDescription ()) ;
    curseurTache.updateDouble (4,  getChargeInitiale ()) ;
    curseurTache.updateDouble (5, getTempsPasse ()) ;
    curseurTache.updateDouble (6, getResteAPasser ()) ;
    curseurTache.updateInt (7, getEtat ()) ;
    curseurTache.updateDate (8, new java.sql.Date (getDateDebutPrevue ().getTime())) ;
    curseurTache.updateDate (9, new java.sql.Date (getDateFinPrevue ().getTime ())) ;
    //curseurArtefact.updateDate (10, new java.sql.Date (getDateFinPrevue ().getTime ())) ;
    //curseurArtefact.updateDate (11 , new java.sql.Date (getDateFinPrevue ().getTime ())) ;
    curseurTache.updateInt (12, getIteration ().getId ()) ;
    curseurTache.updateInt (13, getCollaborateur ().getId ()) ;
    if (getActivite () != null)
    {
      curseurTache.updateInt (14, getActivite ().getId ()) ;
    }
    curseurTache.insertRow () ;
    curseurTache.close () ;
    //pConnection.commit () ;
    
//  Préparation de la requête permettant d'obtenir l'id de la tâche
    String lRequete = "SELECT MAX(TAC_ID) FROM TAC_TACHE" ;
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
    assert getActivite () != null ;
    
    int lId = getId () ;
    
    String lRequete = "UPDATE TAC_TACHE SET " ;
    lRequete += "TAC_NOM = '" + getNom () + "', " ;
    lRequete += "TAC_DESCRIPTION = '" + getDescription () + "', " ;
    lRequete += "TAC_CHARGEINITIALE = " + getChargeInitiale () + ", " ;
    lRequete += "TAC_TEMPSPASSE = " + getTempsPasse () + ", " ;
    lRequete += "TAC_RESTEAPASSER = " + getResteAPasser () + ", " ;
    lRequete += "TAC_ETAT = " + getEtat () + ", " ;
    lRequete += "TAC_DATEDEBUTPREVUE = '" + getDateDebutPrevue () + "', " ;
    lRequete += "TAC_DATEFINPREVUE = '" + getDateFinPrevue () + "', " ;
    lRequete += "TAC_DATEDEBUTREELLE = '" + getDateDebutReelle () + "', " ;
    lRequete += "TAC_DATEFINREELLE = '" + getDateFinReelle () + "', " ;
    lRequete += "TAC_ITE_ID = " + getIteration ().getId () + ", " ;
    lRequete += "TAC_COL_ID = " + getCollaborateur ().getId () + ", " ;
    lRequete += "TAC_ACT_ID = " + getActivite ().getId () + " " ;
    lRequete += "WHERE TAC_ID = " + lId ;
    
    Statement lRequest = pConnection.createStatement () ;
    lRequest.executeUpdate (lRequete) ;
  }
  
  
  /**
   * Récupère l'activité que la tâche instancie.
   * @return Activité que la tâche instancie.
   */
  public MActivite getActivite ()
  {
    return mActivite ;
  }


  /**
   * Associe l'activité que la tâche instancie.
   * @param pActivite Activité que la tâche instancie.
   */
  public void setActivite (MActivite pActivite)
  {
    mActivite = pActivite ;
  }


  /**
   * Récupère la liste des artefacts en entrées de la tâche.
   * @return Liste des artefacts nécessaires à la tâche.
   */
  public ArrayList getListeArtefactsEntrees ()
  {
    return mArtefactsEntrees ;
  }


  /**
   * Initialise la liste des artefacts en entrées de la tâche.
   * @param pArtefactsEntrees Liste des artefacts nécessaires à la tâche.
   */
  public void setListeArtefactsEntrees (ArrayList pArtefactsEntrees)
  {
    mArtefactsEntrees = pArtefactsEntrees ;
  }


  /**
   * Récupère le nombre d'artefacts en entrées.
   * @return Nombre d'artefacts en entrées.
   */
  public int getNbArtefactsEntrees ()
  {
    return mArtefactsEntrees.size () ;
  }


  /**
   * Récupère l'artefact d'indice spécifié nécessaire à la tâche.
   * @param pIndice Indice de l'artefact en entrée dans la liste.
   * @return Artefact nécessaire à la tâche.
   */
  public MArtefact getArtefactEntree (int pIndice)
  {
    return (MArtefact) mArtefactsEntrees.get (pIndice) ;
  }


  /**
   * Ajoute l'artefact spécifié en entrée de la tâche.
   * @param pArtefact Artefact nécessaire à la tâche.
   */
  public void addArtefactEntrees (MArtefact pArtefact)
  {
    mArtefactsEntrees.add (pArtefact) ;
  }


  /**
   * Supprime l'artefact spécifié en entrée de la tâche.
   * @param pIndice Indice de l'artefact dans la liste.
   */
  public void supprimerArtefactEntree (int pIndice)
  {
    mArtefactsEntrees.remove (pIndice) ;
  }


  /**
   * Supprime l'artefact spécifié en entrée de la tâche.
   * @param pArtefact Artefact nécessaire à la tâche. Indice de l'artefact dans la liste.
   */
  public void supprimerArtefactEntree (MArtefact pArtefact)
  {
    mArtefactsEntrees.remove (pArtefact) ;
  }


  /**
   * Récupère la liste des artefacts en sorties de la tâche.
   * @return Liste des artefacts à produire durant la tâche.
   */
  public ArrayList getListeArtefactsSorties ()
  {
    return mArtefactsSorties ;
  }


  /**
   * Initialise la liste des artefacts en sorties de la tâche.
   * @param pArtefactsSorties Liste des artefacts à produire durant la tâche.
   */
  public void setListeArtefactsSorties (ArrayList pArtefactsSorties)
  {
    mArtefactsSorties = pArtefactsSorties ;
  }


  /**
   * Récupère le nombre d'artefacts en sorties.
   * @return Nombre d'artefacts en sorties.
   */
  public int getNbArtefactsSorties ()
  {
    return mArtefactsSorties.size () ;
  }


  /**
   * Récupère l'artefact d'indice spécifié à produire durant la tâche.
   * @param pIndice Indice de l'artefact en sortie dans la liste.
   * @return Artefact à produire durant la tâche.
   */
  public MArtefact getArtefactSortie (int pIndice)
  {
    return (MArtefact) mArtefactsSorties.get (pIndice) ;
  }


  /**
   * Ajoute l'artefact spécifié en sortie de la tâche.
   * @param pArtefact Artefact à produire durant la tâche.
   */
  public void addArtefactSortie (MArtefact pArtefact)
  {
    mArtefactsSorties.add (pArtefact) ;
  }


  /**
   * Supprime l'artefact spécifié en sortie de la tâche.
   * @param pIndice Indice de l'artefact dans la liste.
   */
  public void supprimerArtefactSortie (int pIndice)
  {
    mArtefactsSorties.remove (pIndice) ;
  }


  /**
   * Supprime l'artefact spécifié en sortie de la tâche.
   * @param pArtefact Artefact à produire durant la tâche.
   */
  public void supprimerArtefactSortie (MArtefact pArtefact)
  {
    mArtefactsSorties.remove (pArtefact) ;
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
   * @param pConditions Liste des conditions pour le passage de la tâche à l'état prêt
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
  public void supprimerCondition (MCondition pCondition)
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
}
