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
 * Une t�che repr�sente le travail d'une personne sur un ou plusieurs artefact. La t�che contient
 * les estimations et mesures de charges et de dates.
 */
public class MTache extends MModeleBase
{
  public static final int ETAT_CREEE = -1 ; // T�che cr��e
  public static final int ETAT_NON_DEMARRE = 0 ; // T�che pr�te
  public static final int ETAT_EN_COURS    = 1 ;
  public static final int ETAT_SUSPENDU = 2 ;
  public static final int ETAT_TERMINE     = 3 ;


  private int            mId ;               // Identifie la t�che de mani�re unique.
  private String         mNom ;              // Nom de la t�che.
  private String         mDescription ;      // Description de la t�che.
  private int            mEtat ;             // Etat de la t�che.
  private double         mChargeInitiale ;   // Charge pr�vue par le chef de projet.
  private double         mTempsPasse ;       // Temps pass� sur la t�che.
  private double         mResteAPasser ;     // Temps restant � passer sur la t�che.
  private Date           mDateDebutPrevue ;  // Date de d�but pr�vue par le chef de projet.
  private Date           mDateFinPrevue ;    // Date de fin pr�vue par le chef de projet.
  private Date           mDateDebutReelle ;  // Date de d�but r�elle de la t�che.
  private Date           mDateFinReelle ;    // Date de fin r�elle de la t�che.
  private ArrayList      mArtefactsEntrees ; // Liste des artefacts n�cessaires � la t�che.
  private ArrayList      mArtefactsSorties ; // Liste des artefacts � produire durant la t�che.
  private MActivite      mActivite ;         // Activit� que la t�che instancie. 
  private MCollaborateur mCollaborateur ;    // Collaborateur r�alisant la t�che.
  private MIteration     mIteration ;        // It�ration durant laquelle est effectu�e la t�che.
  private long           mDateDebutChrono ;  // Date a laquelle on demarre le chrono pour le temps pass� sur une tache.
  private ArrayList      mConditions ;       // Liste de conditions n�cessaires pour que la t�che soit dans l'�tat pr�t.
  private ArrayList      mProblemesEntrees ; // Liste des probl�mes r�sultant de la t�che.
  private ArrayList      mProblemesSorties ; // Liste des probl�mes que r�sout la t�che.



  /**
   * Cr�e une instance vide de MTache.
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
   * Cr�e une instance de MArtefact initialis�e avec les donn�es du chef de projet.
   * @param pId Identifiant unique de l'artefact.
   * @param pNom Nom de la t�che.
   * @param pDescription Description de la t�che.
   * @param pChargeInitiale Charge pr�vue par le chef de projet.
   * @param pDateDebutPrevue Date de d�but pr�vue par le chef de projet.
   * @param pDateFinPrevue Date de fin pr�vue par le chef de projet.
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
   * @param pTache Tache � recopier.
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
   * Insertion de la t�che courante dans la base de donn�es.
   * @param pConnection Connexion avec la base de donn�es
   * @throws SQLException Si une erreur survient durant l'insertion dans la BD.
   */
  public void create (Connection pConnection) throws SQLException 
  {
    assert getActivite () != null ;
    
    // Pr�paration de la requ�te d'insertion
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
    
//  Pr�paration de la requ�te permettant d'obtenir l'id de la t�che
    String lRequete = "SELECT MAX(TAC_ID) FROM TAC_TACHE" ;
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
   * R�cup�re l'activit� que la t�che instancie.
   * @return Activit� que la t�che instancie.
   */
  public MActivite getActivite ()
  {
    return mActivite ;
  }


  /**
   * Associe l'activit� que la t�che instancie.
   * @param pActivite Activit� que la t�che instancie.
   */
  public void setActivite (MActivite pActivite)
  {
    mActivite = pActivite ;
  }


  /**
   * R�cup�re la liste des artefacts en entr�es de la t�che.
   * @return Liste des artefacts n�cessaires � la t�che.
   */
  public ArrayList getListeArtefactsEntrees ()
  {
    return mArtefactsEntrees ;
  }


  /**
   * Initialise la liste des artefacts en entr�es de la t�che.
   * @param pArtefactsEntrees Liste des artefacts n�cessaires � la t�che.
   */
  public void setListeArtefactsEntrees (ArrayList pArtefactsEntrees)
  {
    mArtefactsEntrees = pArtefactsEntrees ;
  }


  /**
   * R�cup�re le nombre d'artefacts en entr�es.
   * @return Nombre d'artefacts en entr�es.
   */
  public int getNbArtefactsEntrees ()
  {
    return mArtefactsEntrees.size () ;
  }


  /**
   * R�cup�re l'artefact d'indice sp�cifi� n�cessaire � la t�che.
   * @param pIndice Indice de l'artefact en entr�e dans la liste.
   * @return Artefact n�cessaire � la t�che.
   */
  public MArtefact getArtefactEntree (int pIndice)
  {
    return (MArtefact) mArtefactsEntrees.get (pIndice) ;
  }


  /**
   * Ajoute l'artefact sp�cifi� en entr�e de la t�che.
   * @param pArtefact Artefact n�cessaire � la t�che.
   */
  public void addArtefactEntrees (MArtefact pArtefact)
  {
    mArtefactsEntrees.add (pArtefact) ;
  }


  /**
   * Supprime l'artefact sp�cifi� en entr�e de la t�che.
   * @param pIndice Indice de l'artefact dans la liste.
   */
  public void supprimerArtefactEntree (int pIndice)
  {
    mArtefactsEntrees.remove (pIndice) ;
  }


  /**
   * Supprime l'artefact sp�cifi� en entr�e de la t�che.
   * @param pArtefact Artefact n�cessaire � la t�che. Indice de l'artefact dans la liste.
   */
  public void supprimerArtefactEntree (MArtefact pArtefact)
  {
    mArtefactsEntrees.remove (pArtefact) ;
  }


  /**
   * R�cup�re la liste des artefacts en sorties de la t�che.
   * @return Liste des artefacts � produire durant la t�che.
   */
  public ArrayList getListeArtefactsSorties ()
  {
    return mArtefactsSorties ;
  }


  /**
   * Initialise la liste des artefacts en sorties de la t�che.
   * @param pArtefactsSorties Liste des artefacts � produire durant la t�che.
   */
  public void setListeArtefactsSorties (ArrayList pArtefactsSorties)
  {
    mArtefactsSorties = pArtefactsSorties ;
  }


  /**
   * R�cup�re le nombre d'artefacts en sorties.
   * @return Nombre d'artefacts en sorties.
   */
  public int getNbArtefactsSorties ()
  {
    return mArtefactsSorties.size () ;
  }


  /**
   * R�cup�re l'artefact d'indice sp�cifi� � produire durant la t�che.
   * @param pIndice Indice de l'artefact en sortie dans la liste.
   * @return Artefact � produire durant la t�che.
   */
  public MArtefact getArtefactSortie (int pIndice)
  {
    return (MArtefact) mArtefactsSorties.get (pIndice) ;
  }


  /**
   * Ajoute l'artefact sp�cifi� en sortie de la t�che.
   * @param pArtefact Artefact � produire durant la t�che.
   */
  public void addArtefactSortie (MArtefact pArtefact)
  {
    mArtefactsSorties.add (pArtefact) ;
  }


  /**
   * Supprime l'artefact sp�cifi� en sortie de la t�che.
   * @param pIndice Indice de l'artefact dans la liste.
   */
  public void supprimerArtefactSortie (int pIndice)
  {
    mArtefactsSorties.remove (pIndice) ;
  }


  /**
   * Supprime l'artefact sp�cifi� en sortie de la t�che.
   * @param pArtefact Artefact � produire durant la t�che.
   */
  public void supprimerArtefactSortie (MArtefact pArtefact)
  {
    mArtefactsSorties.remove (pArtefact) ;
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
   * @param pConditions Liste des conditions pour le passage de la t�che � l'�tat pr�t
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
  public void supprimerCondition (MCondition pCondition)
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
