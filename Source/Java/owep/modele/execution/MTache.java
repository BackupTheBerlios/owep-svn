package owep.modele.execution ;


import java.util.ArrayList ;
import java.util.Date ;


/**
 * Une t�che repr�sente le travail d'une personne sur un ou plusieurs artefact. La t�che contient
 * les estimations et mesures de charges et de dates.
 */
public class MTache extends MModeleBase
{
  // Indique l'�tat de la t�che
  public static final int ETAT_EN_COURS    = 0 ;
  public static final int ETAT_TERMINE     = 1 ;
  public static final int ETAT_NON_DEMARRE = 2 ;
  
  
  private int             mId ;                  // Identifie la t�che de mani�re unique
  private String          mNom ;                 // Nom de la t�che
  private String          mDescription ;         // Description de la t�che
  private double          mChargeInitiale ;      // Charge pr�vue par le chef de projet
  private double          mTempsPasse ;          // Temps pass� sur la t�che
  private double          mResteAPasser ;        // Temps restant � passer sur la t�che
  private Date            mDateDebutPrevue ;     // Date de d�but pr�vue par le chef de projet
  private Date            mDateDebutReelle ;     // Date de d�but r�elle de la t�che
  private Date            mDateFinPrevue ;       // Date de fin pr�vue par le chef de projet
  private Date            mDateFinReelle ;       // Date de fin r�elle de la t�che
  private ArrayList       mListeArtefactEntree ; // Liste des artefacts n�cessaires � la r�alisation
  private ArrayList       mListeArtefactSortie ; // Liste des artefacts � produire durant la t�che
  private MActivite       mActivite ;            // Activit� du processus correspondant � la t�che
  private MCollaborateur  mCollaborateur ;       // Collaborateur r�alisant la t�che
  
  
  /**
   * Cr�e une instance vide de MTache.
   */
  public MTache ()
  {
    mListeArtefactEntree = new ArrayList () ;
    mListeArtefactSortie = new ArrayList () ;
  }
  
  
  /**
   * Cr�e une instance de MArtefact initialis�e avec les donn�es du chef de projet.
   * @param pId Identifiant unique de l'artefact
   * @param pNom Nom de la t�che
   * @param pDescription Description de la t�che
   * @param pChargeInitiale Charge pr�vue par le chef de projet
   * @param pDateDebutPrevue Date de d�but pr�vue par le chef de projet
   * @param pDateFinPrevue Date de fin pr�vue par le chef de projet
   * @param pActivite Activit� du processus correspondant � la t�che
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
   * Cr�e une instance de MArtefact initialis�e avec les donn�es du chef de projet.
   * @param pId Identifiant unique de l'artefact
   * @param pNom Nom de la t�che
   * @param pDescription Description de la t�che
   * @param pChargeInitiale Charge pr�vue par le chef de projet
   * @param pTempsPasse Temps pass� sur la t�che
   * @param pDateDebutPrevue Date de d�but pr�vue par le chef de projet
   * @param pDateDebutReelle Date de d�but r�elle de la t�che
   * @param pDateFinPrevue Date de fin pr�vue par le chef de projet
   * @param pDateFinReelle Date de fin r�elle de la t�che
   * @param pActivite Activit� du processus correspondant � la t�che
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
   * R�cup�re l'activit� du processus correspondant � la t�che.
   * @return Activit� du processus correspondant � la t�che
   */
  public MActivite getActivite ()
  {
    return mActivite ;
  }
  
  
  /**
   * Associe l'activit� du processus correspondant � la t�che.
   * @param pActivite Activit� du processus correspondant � la t�che
   */
  public void setActivite (MActivite pActivite)
  {
    mActivite = pActivite ;
  }
  
  
  /**
   * Ajoute un nouvel artefact en entr�e.
   * @param pArtefactEntree Artefact en entr�e
   */
  public void ajouterArtefactEntree (MArtefact pArtefactEntree)
  {
    mListeArtefactEntree.add (pArtefactEntree) ;
  }
  
  
  /**
   * R�cup�re un artefact en entr�e � partir de son indice dans la liste.
   * @param pIndice Indice de l'artefact en entr�e
   * @return Artefact en entr�e
   */
  public MArtefact getArtefactEntree (int pIndice)
  {
    return (MArtefact) mListeArtefactEntree.get (pIndice) ;
  }
  
  
  /**
   * R�cup�re le nombre d'artefacts en entr�es.
   * @return Nombre d'artefacts en entr�es.
   */
  public int getNbArtefactEntree ()
  {
    return mListeArtefactEntree.size () ;
  }
  
  
  /**
   * R�cup�re la liste des artefacts en entr�es
   * @return Liste des artefacts en entr�es.
   */
  public ArrayList getListeArtefactEntree ()
  {
    return mListeArtefactEntree ;
  }
  
  
  /**
   * Initialise la liste des artefacts en entr�es
   * @param pListeArtefactEntree Liste des artefacts en entr�es.
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
   * R�cup�re un artefact en sortie � partir de son indice dans la liste.
   * @param pIndice Indice de l'artefact en sortie
   * @return Artefact en entr�e
   */
  public MArtefact getArtefactSortie (int pIndice)
  {
    return (MArtefact) mListeArtefactSortie.get (pIndice) ;
  }


  /**
   * R�cup�re le nombre d'artefacts en sorties.
   * @return Nombre d'artefacts en sorties.
   */
  public int getNbArtefactSortie ()
  {
    return mListeArtefactSortie.size () ;
  }
  
  
  /**
   * R�cup�re la liste des artefacts en sorties
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
   * @return Collaborateur r�alisant la t�che
   */
  public MCollaborateur getCollaborateur ()
  {
    return mCollaborateur ;
  }
  
  
  /**
   * Associe le collaborateur r�alisant la t�che.
   * @param pCollaborateur Collaborateur r�alisant la t�che
   */
  public void setCollaborateur (MCollaborateur pCollaborateur)
  {
    mCollaborateur = pCollaborateur ;
  }
  
  
  /**
   * R�cup�re la date de d�but pr�vue par le chef de projet.
   * @return Date de d�but pr�vue par le chef de projet
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
   * @return Date de d�but r�elle de la t�che
   */
  public Date getDateDebutReelle ()
  {
    return mDateDebutReelle ;
  }


  /**
   * Initialise la date de d�but r�elle de la t�che.
   * @param pDateDebutReelle Date de d�but r�elle de la t�che
   */
  public void setDateDebutReelle (Date pDateDebutReelle)
  {
    assert mDateFinReelle != null ? pDateDebutReelle.before (mDateFinReelle) : true ;
    
    mDateDebutReelle = pDateDebutReelle ;
  }
  
  
  /**
   * R�cup�re la date de fin pr�vue par le chef de projet.
   * @return Date de fin pr�vue par le chef de projet
   */
  public Date getDateFinPrevue ()
  {
    return mDateFinPrevue ;
  }
  
  
  /**
   * Initialise la date de fin pr�vue par le chef de projet.
   * @return Date de fin pr�vue par le chef de projet
   */
  public void setDateFinPrevue (Date pDateFinPrevue)
  {
    assert mDateDebutPrevue != null ? pDateFinPrevue.after (mDateDebutPrevue) : true ;
    
    mDateFinPrevue = pDateFinPrevue ;
  }
  
  
  /**
   * R�cup�re la daate de fin r�elle de la t�che.
   * @return Date de fin r�elle de la t�che
   */
  public Date getDateFinReelle ()
  {
    return mDateFinReelle ;
  }
  
  
  /**
   * R�cup�re la date de fin pr�vue par le chef de projet.
   * @return Date de fin r�elle de la t�che
   */
  public void setDateFinReelle (Date pDateFinReelle)
  {
    assert mDateDebutReelle != null ? pDateFinReelle.after (mDateDebutReelle) : true ;
    
    mDateFinReelle = pDateFinReelle ;
  }
  
  
  /**
   * R�cup�re la description de la t�che.
   * @return Description de la t�che
   */
  public String getDescription ()
  {
    return mDescription ;
  }
  
  
  /**
   * Initialise la description de la t�che.
   * @param pDescription Description de la t�che
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
   * R�cup�re le temps restant � passer sur la t�che.
   * @param pResteAPasser Temps restant � passer sur la t�che
   */
  public void setResteAPasser (double pResteAPasser)
  {
    assert pResteAPasser > 0 ;
    
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
   * Calcule le nombre d'hommes x jours de d�passement de charges.
   * Formule : tempsPasse + resteAPasser - chargeInitiale.
   * @return Nombre d'hommes x jours de d�passement de charges.
   */
  public double getHJDepassementCharge ()
  {
    return mTempsPasse + mResteAPasser - mChargeInitiale ;
  }
  
  
  /**
   * Calcule le pourcentage de d�passement de charges.
   * Formule : (tempsPasse + resteAPasser - chargeInitiale) / chargeInitiale.
   * @return Pourcentage de d�passement de charges.
   */
  public double getPrcDepassementCharge ()
  {
    return (mTempsPasse + mResteAPasser - mChargeInitiale) / mChargeInitiale ;
  }
  
  
  /**
   * Calcule le pourcentage de d�passement de charges.
   * Formule : tempsPasse / (tempsPasse + resteAPasser).
   * @return Pourcentage de d�passement de charges.
   */
  public double getPrcAvancement ()
  {
    return mTempsPasse / (mTempsPasse + mResteAPasser) ;
  }
}