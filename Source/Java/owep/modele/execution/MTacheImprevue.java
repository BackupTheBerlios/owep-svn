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
  public static final int ETAT_CREEE = -1 ; // T�che cr��e
  public static final int ETAT_NON_DEMARRE = 0 ; // T�che pr�te
  public static final int ETAT_EN_COURS    = 1 ;
  public static final int ETAT_SUSPENDU = 2 ;
  public static final int ETAT_TERMINE     = 3 ;


  private int                mId ;                // Identifie la t�che de mani�re unique.
  private String             mNom ;               // Nom de la t�che.
  private String             mDescription ;       // Description de la t�che.
  private int                mEtat ;              // Etat de la t�che.
  private double             mChargeInitiale ;    // Charge pr�vue par le chef de projet.
  private double             mTempsPasse ;        // Temps pass� sur la t�che.
  private double             mResteAPasser ;      // Temps restant � passer sur la t�che.
  private Date               mDateDebutPrevue ;   // Date de d�but pr�vue par le chef de projet.
  private Date               mDateFinPrevue ;     // Date de fin pr�vue par le chef de projet.
  private Date               mDateDebutReelle ;   // Date de d�but r�elle de la t�che.
  private Date               mDateFinReelle ;     // Date de fin r�elle de la t�che.
  private ArrayList          mArtefactsImprevuesEntrees ;  // Liste des artefacts n�cessaires � la t�che.
  private ArrayList          mArtefactsImprevuesSorties ;  // Liste des artefacts � produire durant la t�che.
  private MActiviteImprevue  mActiviteImprevue ;  // Activit� impr�vue que la t�che instancie. 
  private MCollaborateur     mCollaborateur ;     // Collaborateur r�alisant la t�che.
  private MIteration         mIteration ;         // It�ration durant laquelle est effectu�e la t�che.
  private long               mDateDebutChrono ;   // Date a laquelle on demarre le chrono pour le temps pass� sur une tache
  private ArrayList          mConditions ;        // Liste de conditions n�cessaires pour que la t�che soit dans l'�tat pr�t
  private ArrayList          mProblemesEntrees ;  // Liste des probl�mes r�sultant de la t�che.
  private ArrayList          mProblemesSorties ;  // Liste des probl�mes que r�sout la t�che.

  /**
   * Cr�e une instance vide de MTache.
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
   * Cr�e une instance de MTacheImprevue initialis�e avec les donn�es du chef de projet.
   * @param pId Identifiant unique de la t�che impr�vue.
   * @param pNom Nom de la t�che.
   * @param pDescription Description de la t�che.
   * @param pChargeInitiale Charge pr�vue par le chef de projet.
   * @param pDateDebutPrevue Date de d�but pr�vue par le chef de projet.
   * @param pDateFinPrevue Date de fin pr�vue par le chef de projet.
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
   * Cr�e une instance de MTacheImprevue initialis�e avec les donn�es du chef de projet.
   * @param pId Identifiant unique de la t�che impr�vue.
   * @param pNom Nom de la t�che.
   * @param pDescription Description de la t�che.
   * @param pChargeInitiale Charge pr�vue par le chef de projet.
   * @param pTempsPasse Temps pass� sur la t�che.
   * @param pResteAPasser Temps restant � passer sur la t�che.
   * @param pDateDebutPrevue Date de d�but pr�vue par le chef de projet.
   * @param pDateDebutReelle Date de d�but r�elle de la t�che.
   * @param pDateFinPrevue Date de fin pr�vue par le chef de projet.
   * @param pDateFinReelle Date de fin r�elle de la t�che.
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
   * @param pTache Tache � recopier.
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
   * Insertion de la t�che impr�vue courante dans la base de donn�es.
   * @param pConnection Connexion avec la base de donn�es
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
    
//  Pr�paration de la requ�te permettant d'obtenir l'id de la t�che
    String lRequete = "SELECT MAX(TIM_ID) FROM TIM_TACHEIMPREVUE" ;
    ResultSet result = lRequest.executeQuery (lRequete) ;
    if (result.next ())
    {
      setId (result.getInt (1)) ;
    }
    result.close () ;
  }

  
  /**
   * Permet de mettre � jour la t�che courant dans la base de donn�es
   * @param pConnection Connexion avec la BD.
   * @throws SQLException Si une erreur survient durant la mise � jour.
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
   * R�cup�re l'activit� impr�vue que la t�che instancie.
   * @return Activit� impr�vue que la t�che instancie.
   */
  public MActiviteImprevue getActiviteImprevue ()
  {
    return mActiviteImprevue ;
  }


  /**
   * Associe l'activit� impr�vue que la t�che instancie.
   * @param pActiviteImprevue Activit� impr�vue que la t�che instancie.
   */
  public void setActiviteImprevue (MActiviteImprevue pActiviteImprevue)
  {
    mActiviteImprevue = pActiviteImprevue ;
  }


  /**
   * R�cup�re la liste des artefacts impr�vues en entr�es de la t�che.
   * @return Liste des artefacts impr�vues n�cessaires � la t�che.
   */
  public ArrayList getListeArtefactsImprevuesEntrees ()
  {
    return mArtefactsImprevuesEntrees ;
  }


  /**
   * Initialise la liste des artefacts impr�vues en entr�es de la t�che.
   * @param pArtefactsImprevuesEntrees Liste des artefacts impr�vues n�cessaires � la t�che.
   */
  public void setListeArtefactsImprevuesEntrees (ArrayList pArtefactsImprevuesEntrees)
  {
    mArtefactsImprevuesEntrees = pArtefactsImprevuesEntrees ;
  }


  /**
   * R�cup�re le nombre d'artefacts impr�vues en entr�es.
   * @return Nombre d'artefacts impr�vues en entr�es.
   */
  public int getNbArtefactsImprevuesEntrees ()
  {
    return mArtefactsImprevuesEntrees.size () ;
  }


  /**
   * R�cup�re l'artefact impr�vue d'indice sp�cifi� n�cessaire � la t�che.
   * @param pIndice Indice de l'artefact impr�vue en entr�e dans la liste.
   * @return Artefact impr�vue n�cessaire � la t�che.
   */
  public MArtefactImprevue getArtefactImprevueEntree (int pIndice)
  {
    return (MArtefactImprevue) mArtefactsImprevuesEntrees.get (pIndice) ;
  }


  /**
   * Ajoute l'artefact impr�vue sp�cifi� en entr�e de la t�che.
   * @param pArtefactImprevue Artefact impr�vue n�cessaire � la t�che.
   */
  public void addArtefactImprevueEntrees (MArtefactImprevue pArtefactImprevue)
  {
    mArtefactsImprevuesEntrees.add (pArtefactImprevue) ;
  }


  /**
   * Supprime l'artefact impr�vue sp�cifi� en entr�e de la t�che.
   * @param pIndice Indice de l'artefact impr�vue dans la liste.
   */
  public void supprimerArtefactImprevueEntree (int pIndice)
  {
    mArtefactsImprevuesEntrees.remove (pIndice) ;
  }


  /**
   * Supprime l'artefact impr�vue sp�cifi� en entr�e de la t�che.
   * @param pArtefactImprevue Artefact impr�vue n�cessaire � la t�che. 
   */
  public void supprimerArtefactImprevueEntree (MArtefactImprevue pArtefactImprevue)
  {
    mArtefactsImprevuesEntrees.remove (pArtefactImprevue) ;
  }


  /**
   * R�cup�re la liste des artefacts impr�vues en sorties de la t�che.
   * @return Liste des artefacts impr�vues � produire durant la t�che.
   */
  public ArrayList getListeArtefactsImprevuesSorties ()
  {
    return mArtefactsImprevuesSorties ;
  }


  /**
   * Initialise la liste des artefacts impr�vues en sorties de la t�che.
   * @param pArtefactsImprevuesSorties Liste des artefacts impr�vues � produire durant la t�che.
   */
  public void setListeArtefactsImprevuesSorties (ArrayList pArtefactsImprevuesSorties)
  {
    mArtefactsImprevuesSorties = pArtefactsImprevuesSorties ;
  }


  /**
   * R�cup�re le nombre d'artefacts impr�vues en sorties.
   * @return Nombre d'artefacts impr�vues en sorties.
   */
  public int getNbArtefactsImprevuesSorties ()
  {
    return mArtefactsImprevuesSorties.size () ;
  }


  /**
   * R�cup�re l'artefact impr�vue d'indice sp�cifi� � produire durant la t�che.
   * @param pIndice Indice de l'artefact impr�vue en sortie dans la liste.
   * @return Artefact impr�vue � produire durant la t�che.
   */
  public MArtefactImprevue getArtefactImprevueSortie (int pIndice)
  {
    return (MArtefactImprevue) mArtefactsImprevuesSorties.get (pIndice) ;
  }


  /**
   * Ajoute l'artefact impr�vue sp�cifi� en sortie de la t�che.
   * @param pArtefactImprevue Artefact impr�vue � produire durant la t�che.
   */
  public void addArtefactImprevueSortie (MArtefactImprevue pArtefactImprevue)
  {
    mArtefactsImprevuesSorties.add (pArtefactImprevue) ;
  }


  /**
   * Supprime l'artefact impr�vue sp�cifi� en sortie de la t�che.
   * @param pIndice Indice de l'artefact impr�vue dans la liste.
   */
  public void supprimerArtefactImprevueSortie (int pIndice)
  {
    mArtefactsImprevuesSorties.remove (pIndice) ;
  }


  /**
   * Supprime l'artefact impr�vue sp�cifi� en sortie de la t�che.
   * @param pArtefactImprevue Artefact impr�vue � produire durant la t�che.
   */
  public void supprimerArtefactImprevueSortie (MArtefactImprevue pArtefactImprevue)
  {
    mArtefactsImprevuesSorties.remove (pArtefactImprevue) ;
  }


  /**
   * R�cup�re la charge pr�vue par le chef de projet.
   * @return Charge pr�vue par le chef de projet.
   */
  public double getChargeInitiale ()
  {
    return mChargeInitiale ;
  }


  /**
   * Initialise la charge pr�vue par le chef de projet.
   * @param pChargeInitiale Charge pr�vue par le chef de projet.
   */
  public void setChargeInitiale (double pChargeInitiale)
  {
    assert pChargeInitiale > 0.0 ;
    mChargeInitiale = pChargeInitiale ;
  }


  /**
   * R�cup�re le collaborateur r�alisant la t�che.
   * @return Collaborateur r�alisant la t�che.
   */
  public MCollaborateur getCollaborateur ()
  {
    return mCollaborateur ;
  }


  /**
   * Associe le collaborateur r�alisant la t�che.
   * @param pCollaborateur Collaborateur r�alisant la t�che.
   */
  public void setCollaborateur (MCollaborateur pCollaborateur)
  {
    mCollaborateur = pCollaborateur ;
  }


  /**
   * R�cup�re la date de d�but pr�vue par le chef de projet.
   * @return Date de d�but pr�vue par le chef de projet.
   */
  public Date getDateDebutPrevue ()
  {
    return mDateDebutPrevue ;
  }


  /**
   * R�cup�re la date de d�but pr�vue par le chef de projet.
   * @param pDateDebutPrevue The mDateDebutPrevue to set.
   */
  public void setDateDebutPrevue (Date pDateDebutPrevue)
  {
    assert mDateFinPrevue != null ? pDateDebutPrevue.before (mDateFinPrevue) : true ;
    mDateDebutPrevue = pDateDebutPrevue ;
  }


  /**
   * R�cup�re la date de d�but r�elle de la t�che.
   * @return Date de d�but r�elle de la t�che.
   */
  public Date getDateDebutReelle ()
  {
    return mDateDebutReelle ;
  }


  /**
   * Initialise la date de d�but r�elle de la t�che.
   * @param pDateDebutReelle Date de d�but r�elle de la t�che.
   */
  public void setDateDebutReelle (Date pDateDebutReelle)
  {
    assert mDateFinReelle != null ? pDateDebutReelle.before (mDateFinReelle) : true ;
    mDateDebutReelle = pDateDebutReelle ;
  }


  /**
   * R�cup�re la date de fin pr�vue par le chef de projet.
   * @return Date de fin pr�vue par le chef de projet.
   */
  public Date getDateFinPrevue ()
  {
    return mDateFinPrevue ;
  }


  /**
   * Initialise la date de fin pr�vue par le chef de projet.
   * @param pDateFinPrevue Date de fin pr�vue par le chef de projet.
   */
  public void setDateFinPrevue (Date pDateFinPrevue)
  {
    assert mDateDebutPrevue != null ? pDateFinPrevue.after (mDateDebutPrevue) : true ;
    mDateFinPrevue = pDateFinPrevue ;
  }


  /**
   * R�cup�re la daate de fin r�elle de la t�che.
   * @return Date de fin r�elle de la t�che.
   */
  public Date getDateFinReelle ()
  {
    return mDateFinReelle ;
  }


  /**
   * R�cup�re la date de fin pr�vue par le chef de projet.
   * @param pDateFinReelle Date de fin r�elle de la t�che.
   */
  public void setDateFinReelle (Date pDateFinReelle)
  {
    assert mDateDebutReelle != null ? pDateFinReelle.after (mDateDebutReelle) : true ;
    mDateFinReelle = pDateFinReelle ;
  }


  /**
   * R�cup�re la description de la t�che.
   * @return Description de la t�che.
   */
  public String getDescription ()
  {
    return mDescription ;
  }


  /**
   * Initialise la description de la t�che.
   * @param pDescription Description de la t�che.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }


  /**
   * R�cup�re l'�tat de r�alisation de la t�che.
   * @return Etat de r�alisation de la t�che.
   */
  public int getEtat ()
  {
    return mEtat ;
  }


  /**
   * Initialise l'�tat de r�alisation de la t�che.
   * @param pEtat Etat de r�alisation de la t�che.
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
   * R�cup�re la liste des conditions pour le passage de la t�che � l'�tat pr�t
   * @return Liste des conditions pour le passage de la t�che � l'�tat pr�t
   */
  public ArrayList getListeConditions ()
  {
    return mConditions ;
  }


  /**
   * Initialise la liste des conditions pour le passage de la t�che � l'�tat pr�t
   * @param pArtefactsEntrees Liste des conditions pour le passage de la t�che � l'�tat pr�t
   */
  public void setListeConditions (ArrayList pConditions)
  {
    mConditions = pConditions ;
  }


  /**
   * R�cup�re le nombre de conditions.
   * @return Nombre de conditions.
   */
  public int getNbConditions ()
  {
    return mConditions.size () ;
  }


  /**
   * R�cup�re la condition de la tache d'indice sp�cifi�
   * @param pIndice Indice de la condition de la tache
   * @return Condition de la t�che.
   */
  public MCondition getCondition (int pIndice)
  {
    return (MCondition) mConditions.get (pIndice) ;
  }


  /**
   * Ajoute la condition sp�cifi�e � la t�che.
   * @param pCondition condition de la t�che.
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
   * @param pCondition Condition de la t�che.
   */
  public void supprimerConditions (MCondition pCondition)
  {
    mConditions.remove (pCondition) ;
  }
  
  
  /**
   * R�cup�re l'identifiant de la t�che.
   * @return Identifiant unique de la t�che
   */
  public int getId ()
  {
    return mId ;
  }


  /**
   * Initialise l'identifiant de la t�che.
   * @param pId Identifiant unique de la t�che
   */
  public void setId (int pId)
  {
    mId = pId ;
  }


  /**
   * R�cup�re l'it�ration durant laquelle est effectu�e la t�che.
   * @return It�ration durant laquelle est effectu�e la t�che.
   */
  public MIteration getIteration ()
  {
    return mIteration ;
  }


  /**
   * Initialise l'it�ration durant laquelle est effectu�e la t�che.
   * @param pIteration It�ration durant laquelle est effectu�e la t�che.
   */
  public void setIteration (MIteration pIteration)
  {
    mIteration = pIteration ;
  }


  /**
   * R�cup�re le nom de la t�che.
   * @return Nom de la t�che
   */
  public String getNom ()
  {
    return mNom ;
  }


  /**
   * Initialise le nom de la t�che.
   * @param pNom Nom de la t�che
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }


  /**
   * R�cup�re le temps restant � passer sur la t�che.
   * @return Temps restant � passer sur la t�che
   */
  public double getResteAPasser ()
  {
    return mResteAPasser ;
  }


  /**
   * Initialise le temps restant � passer sur la t�che.
   * @param pResteAPasser Temps restant � passer sur la t�che
   */
  public void setResteAPasser (double pResteAPasser)
  {
    mResteAPasser = pResteAPasser ;
  }


  /**
   * R�cup�re le temps pass� sur la t�che.
   * @return Temps pass� sur la t�che
   */
  public double getTempsPasse ()
  {
    return mTempsPasse ;
  }


  /**
   * Initialise le temps pass� sur la t�che.
   * @param pTempsPasse Temps pass� sur la t�che
   */
  public void setTempsPasse (double pTempsPasse)
  {
    assert pTempsPasse > 0 ;
    mTempsPasse = pTempsPasse ;
  }


  /**
   * Calcule le budget consomm� pour la t�che. Formule : tempsPasse / chargeInitiale).
   * @return Budget consomm� pour la t�che.
   */
  public double getBudgetConsomme ()
  {
    return mTempsPasse / mChargeInitiale ;
  }


  /**
   * Calcule le nombre d'hommes x jours de d�passement de charges. Formule : tempsPasse +
   * resteAPasser - chargeInitiale.
   * @return Nombre d'hommes x jours de d�passement de charges.
   */
  public double getHJDepassementCharge ()
  {
    return mTempsPasse + mResteAPasser - mChargeInitiale ;
  }


  /**
   * Calcule le pourcentage de d�passement de charges. Formule : (tempsPasse + resteAPasser -
   * chargeInitiale) / chargeInitiale.
   * @return Pourcentage de d�passement de charges.
   */
  public double getPrcDepassementCharge ()
  {
    return (mTempsPasse + mResteAPasser - mChargeInitiale) / mChargeInitiale ;
  }


  /**
   * Calcule le pourcentage de d�passement de charges. Formule : tempsPasse / (tempsPasse +
   * resteAPasser).
   * @return Pourcentage de d�passement de charges.
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
   * R�cup�re la liste des probl�mes r�sultant de la t�che.
   * @return Liste des probl�mes r�sultant de la t�che.
   */
  public ArrayList getListeProblemesEntrees ()
  {
    return mProblemesEntrees ;
  }


  /**
   * Initialise la liste des probl�mes r�sultant de la t�che.
   * @param pProblemesEntrees Liste des probl�mes r�sultant de la t�che.
   */
  public void setListeProblemesEntrees (ArrayList pProblemesEntrees)
  {
    mProblemesEntrees = pProblemesEntrees ;
  }


  /**
   * Ajoute un probl�me r�sultant de la t�che.
   * @param pProblemeEntree Probl�me r�sultant de la t�che.
   */
  public void addProblemeEntree (MProbleme pProblemeEntree)
  {
    mProblemesEntrees.add (pProblemeEntree) ;
  }


  /**
   * R�cup�re la liste des probl�mes que r�sout la t�che.
   * @return Liste des probl�mes que r�sout la t�che.
   */
  public ArrayList getListeProblemesSorties ()
  {
    return mProblemesSorties ;
  }


  /**
   * Initialise la liste des probl�mes que r�sout la t�che.
   * @param pProblemesSorties Liste des probl�mes que r�sout la t�che.
   */
  public void setListeProblemesSorties (ArrayList pProblemesSorties)
  {
    mProblemesSorties = pProblemesSorties ;
  }
 

  /**
   * Ajoute un probl�me que r�sout la t�che.
   * @param pProblemeSortie Probl�me que r�sout la t�che.
   */
  public void addProblemeSortie (MProbleme pProblemeSortie)
  {
    mProblemesSorties.add (pProblemeSortie) ;
  }
}
