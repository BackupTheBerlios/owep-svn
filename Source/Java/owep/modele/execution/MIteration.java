package owep.modele.execution ;


import java.util.ArrayList ;
import java.util.Iterator ;
import java.util.Date ;
import owep.modele.MModeleBase ;


/**
 * Une it�ration est une �tape d'un projet. Elle se caract�rise par un ensemble de t�ches et de
 * participant qui utilisent et r�alisent des artefacts.
 */
public class MIteration extends MModeleBase
{
  public static final int ETAT_NON_DEMARRE = 0 ; // iteration non demarree
  public static final int ETAT_EN_COURS    = 1 ; // iteration en cours
  public static final int ETAT_TERMINE     = 2 ; //iteration terminee

  private int             mId ;                 // Identifie l'it�ration de mani�re unique.
  private int             mNumero ;             // Num�ro de l'it�ration.
  private String          mNom ;                // Nom d�signant l'it�ration.
  private String          mBilan ;              // Bilan de l'it�ration
  private Date            mDateDebutPrevue ;    // Date de d�but pr�vue pour l'it�ration.
  private Date            mDateFinPrevue ;      // Date de fin pr�vue pour l'it�ration.
  private Date            mDateDebutReelle ;    // Date de d�but r�elle de l'it�ration.
  private Date            mDateFinReelle ;      // Date de fin r�elle de l'it�ration.
  private MProjet         mProjet ;             // Projet dont l'it�ration est une �tape.
  private ArrayList       mTaches ;             // Liste des t�ches r�alis�es durant l'it�ration.
  private ArrayList       mTachesImprevues ;    // Liste des t�ches impr�vues r�alis�es durant
                                                // l'it�ration.
  private ArrayList       mMesures ;            // Liste des mesures r�alis�es durant l'it�ration.
  private int             mEtat ;               // Etat de l iteration


  /**
   * Cr�e une instance vide de MIteration.
   */
  public MIteration ()
  {
    super () ;
    mTachesImprevues = new ArrayList () ;
    mTaches = new ArrayList () ;
    mMesures = new ArrayList () ;
    mEtat = ETAT_NON_DEMARRE ;
  }

  /**
   * Cr�e une instance initialis�e de MIteration.
   * 
   * @param pNumero Num�ro de l'it�ration.
   * @param pNom Nom d�signant l'it�ration.
   * @param pDateDebutPrevue Date de d�but pr�vue de l'it�ration.
   * @param pDateFinPrevue Date de fin pr�vue de l'it�ration.
   */
  public MIteration (int pNumero, String pNom, Date pDateDebutPrevue, Date pDateFinPrevue)
  {
    super () ;

    mNumero = pNumero ;
    mNom = pNom ;
    mDateDebutPrevue = pDateDebutPrevue ;
    mDateFinPrevue = pDateFinPrevue ;
    mTaches = new ArrayList () ;
    mTachesImprevues = new ArrayList () ;
    mMesures = new ArrayList () ;
    mEtat = ETAT_NON_DEMARRE ;
  }

  /**
   * Cr�e une instance de MIteration
   * 
   * @param pNumero Num�ro de l'it�ration.
   * @param pNom Nom d�signant l'it�ration.
   * @param pDateDebutPrevue Date de d�but pr�vue de l'it�ration.
   * @param pDateFinPrevue Date de fin pr�vue de l'it�ration.
   * @param pDateDebutReelle Date de d�but r�elle de l'it�ration.
   * @param pDateFinReelle Date de fin r�elle de l'it�ration.
   */
  public MIteration (int pNumero, String pNom, Date pDateDebutPrevue, Date pDateFinPrevue,
                     Date pDateDebutReelle, Date pDateFinReelle)
  {
    super () ;

    mNumero = pNumero ;
    mNom = pNom ;
    mDateDebutPrevue = pDateDebutPrevue ;
    mDateFinPrevue = pDateFinPrevue ;
    mDateDebutReelle = pDateDebutReelle ;
    mDateFinReelle = pDateFinReelle ;
    mTaches = new ArrayList () ;
    mTachesImprevues = new ArrayList () ;
    mMesures = new ArrayList () ;
    mEtat = ETAT_NON_DEMARRE ;
  }

  /**
   * R�cup�re la date de d�but pr�vue pour l'it�ration.
   * 
   * @return Date de d�but pr�vue pour l'it�ration.
   */
  public Date getDateDebutPrevue ()
  {
    return mDateDebutPrevue ;
  }

  /**
   * Initialise la date de d�but pr�vue pour l'it�ration.
   * 
   * @param pDateDebutPrevue Date de d�but pr�vue pour l'it�ration.
   */
  public void setDateDebutPrevue (Date pDateDebutPrevue)
  {
    mDateDebutPrevue = pDateDebutPrevue ;
  }

  /**
   * R�cup�re la date de d�but r�elle de l'it�ration.
   * 
   * @return Date de d�but r�elle de l'it�ration.
   */
  public Date getDateDebutReelle ()
  {
    return mDateDebutReelle ;
  }

  /**
   * Initialise la date de d�but r�elle de l'it�ration.
   * 
   * @param pDateDebutReelle Date de d�but r�elle de l'it�ration.
   */
  public void setDateDebutReelle (Date pDateDebutReelle)
  {
    mDateDebutReelle = pDateDebutReelle ;
  }

  /**
   * R�cup�re la date de fin pr�vue pour l'it�ration.
   * 
   * @return Date de d�but fin pour l'it�ration.
   */
  public Date getDateFinPrevue ()
  {
    return mDateFinPrevue ;
  }

  /**
   * Initialise la date de fin pr�vue pour l'it�ration.
   * 
   * @param pDateFinPrevue Date de fin pr�vue pour l'it�ration.
   */
  public void setDateFinPrevue (Date pDateFinPrevue)
  {
    mDateFinPrevue = pDateFinPrevue ;
  }

  /**
   * R�cup�re la date de fin r�elle de l'it�ration.
   * 
   * @return Date de d�but fin de l'it�ration.
   */
  public Date getDateFinReelle ()
  {
    return mDateFinReelle ;
  }

  /**
   * Initialise la date de fin r�elle de l'it�ration.
   * 
   * @param pDateFinReelle Date de fin r�elle de l'it�ration.
   */
  public void setDateFinReelle (Date pDateFinReelle)
  {
    mDateFinReelle = pDateFinReelle ;
  }

  /**
   * R�cup�re l'identifiant de l'it�ration.
   * 
   * @return Identifiant unique de l'it�ration.
   */
  public int getId ()
  {
    return mId ;
  }

  /**
   * Initialise l'identifiant de l'it�ration.
   * 
   * @param pId Identifiant unique de l'it�ration.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }

  /**
   * R�cup�re le nom de l'it�ration.
   * 
   * @return Nom d�signant l'it�ration.
   */
  public String getNom ()
  {
    return mNom ;
  }

  /**
   * Initialise le nom de l'it�ration.
   * 
   * @param pNom Nom d�signant l'it�ration.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }

  /**
   * R�cup�re le num�ro de l'it�ration.
   * 
   * @return Num�ro de l'it�ration.
   */
  public int getNumero ()
  {
    return mNumero ;
  }

  /**
   * Initialise le num�ro de l'it�ration.
   * 
   * @param pNumero Num�ro de l'it�ration.
   */
  public void setNumero (int pNumero)
  {
    mNumero = pNumero ;
  }

  /**
   * R�cup�re le projet dont l'it�ration est une �tape.
   * 
   * @return Projet dont l'it�ration est une �tape.
   */
  public MProjet getProjet ()
  {
    return mProjet ;
  }

  /**
   * Associe le projet dont l'it�ration est une �tape.
   * 
   * @param pProjet Projet dont l'it�ration est une �tape.
   */
  public void setProjet (MProjet pProjet)
  {
    mProjet = pProjet ;
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
   * R�cup�re la liste des t�ches r�alis�es durant l'it�ration.
   * 
   * @return Liste des t�ches r�alis�es durant l'it�ration.
   */
  public ArrayList getListeTaches ()
  {
    return mTaches ;
  }

  /**
   * Initialise la liste des t�ches r�alis�es durant l'it�ration.
   * 
   * @param pTaches Liste des t�ches r�alis�es durant l'it�ration.
   */
  public void setListeTaches (ArrayList pTaches)
  {
    mTaches = pTaches ;
  }

  /**
   * R�cup�re le nombre de t�ches r�alis�es durant l'it�ration.
   * 
   * @return Nombre de t�ches r�alis�es durant l'it�ration.
   */
  public int getNbTaches ()
  {
    return mTaches.size () ;
  }

  /**
   * R�cup�re la t�che d'indice sp�cifi� r�alis�e durant l'it�ration.
   * 
   * @param pIndice Indice de la t�che dans la liste.
   * @return T�che r�alis�e durant l'it�ration.
   */
  public MTache getTache (int pIndice)
  {
    return (MTache) mTaches.get (pIndice) ;
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

  /**
   * Ajoute la t�che sp�cifi�e � l'it�ration.
   * 
   * @param pTache T�che r�alis�e durant l'it�ration.
   */
  public void addTache (MTache pTache)
  {
    mTaches.add (pTache) ;
  }

  /**
   * Supprime la t�che sp�cifi�e de l'it�ration.
   * 
   * @param pIndice Indice de la t�che � supprimer.
   */
  public void supprimerTache (int pIndice)
  {
    mTaches.remove (pIndice) ;
  }

  /**
   * Supprime la t�che sp�cifi�e de l'it�ration.
   * 
   * @param pTache T�che � supprimer.
   */
  public void supprimerTache (MTache pTache)
  {
    mTaches.remove (pTache) ;
  }

  /**
   * TODO R�cup�re mMesures.
   * 
   * @return mMesures.
   */
  public ArrayList getListeMesures ()
  {
    return mMesures ;
  }

  /**
   * TODO Initialise mMesures.
   * 
   * @param mesures mMesures.
   */
  public void setListeMesures (ArrayList pMesures)
  {
    mMesures = pMesures ;
    Iterator it = pMesures.iterator () ;
    while (it.hasNext ())
    {
      MMesureIndicateur mes = (MMesureIndicateur) it.next () ;
      if (mes.getIteration () != this)
        mes.setIteration (this) ;
    }
  }

  /**
   * Ajoute une mesure a l iteration.
   * 
   * @param pMesure Mesure � ajouter a l iteration.
   */
  public void addMesure (MMesureIndicateur pMesure)
  {
    if (!mMesures.contains (pMesure))
      mMesures.add (pMesure) ;
    if (pMesure.getIteration () != this)
      pMesure.setIteration (this) ;
  }

  /**
   * R�cup�re la liste des t�ches impr�vues r�alis�es durant l'it�ration.
   * 
   * @return Liste des t�ches impr�vues r�alis�es durant l'it�ration.
   */
  public ArrayList getListeTachesImprevues ()
  {
    return mTachesImprevues ;
  }

  /**
   * Initialise la liste des t�ches impr�vues r�alis�es durant l'it�ration.
   * 
   * @param pTachesImprevues Liste des t�ches impr�vues r�alis�es durant l'it�ration.
   */
  public void setListeTachesImprevues (ArrayList pTachesImprevues)
  {
    mTachesImprevues = pTachesImprevues ;
  }

  /**
   * R�cup�re le nombre de t�ches impr�vue r�alis�es durant l'it�ration.
   * 
   * @return Nombre de t�ches impr�vue r�alis�es durant l'it�ration.
   */
  public int getNbTachesImprevues ()
  {
    return mTachesImprevues.size () ;
  }

  /**
   * R�cup�re la t�che impr�vue d'indice sp�cifi� r�alis�e durant l'it�ration.
   * 
   * @param pIndice Indice de la t�che impr�vue dans la liste.
   * @return T�che impr�vue r�alis�e durant l'it�ration.
   */
  public MTacheImprevue getTacheImprevue (int pIndice)
  {
    return (MTacheImprevue) mTachesImprevues.get (pIndice) ;
  }

  /**
   * Ajoute la t�che impr�vue sp�cifi�e � l'it�ration.
   * 
   * @param pTacheImprevues T�che impr�vue r�alis�e durant l'it�ration.
   */
  public void addTacheImprevue (MTacheImprevue pTacheImprevue)
  {
    mTachesImprevues.add (pTacheImprevue) ;
  }

  /**
   * Supprime la t�che impr�vue sp�cifi�e de l'it�ration.
   * 
   * @param pIndice Indice de la t�che impr�vue � supprimer.
   */
  public void supprimerTacheImprevue (int pIndice)
  {
    mTachesImprevues.remove (pIndice) ;
  }

  /**
   * Supprime la t�che impr�vue sp�cifi�e de l'it�ration.
   * 
   * @param pTacheImprevue T�che impr�vue � supprimer.
   */
  public void supprimerTacheImprevue (MTacheImprevue pTacheImprevue)
  {
    mTachesImprevues.remove (pTacheImprevue) ;
  }

  /**
   * TODO Calcule la charge initiale pr�vue pour l iteration
   * 
   * @return chargeInitiale.
   */
  public double getChargeInitiale ()
  {
    double chargeInitiale = 0 ;
    MTache lTache ;
    for (int i = 0 ; i < mTaches.size () ; i++)
    {
      lTache = (MTache) mTaches.get (i) ;
      chargeInitiale = chargeInitiale + lTache.getChargeInitiale () ;
    }
    MTacheImprevue lTacheImprevue ;
    for (int i = 0 ; i < mTachesImprevues.size () ; i++)
    {
      lTacheImprevue = (MTacheImprevue) mTachesImprevues.get (i) ;
      chargeInitiale = chargeInitiale + lTacheImprevue.getChargeInitiale () ;
    }
    return chargeInitiale ;
  }

  /**
   * TODO Calcule le temps pass� pour l iteration
   * 
   * @return tempsPasse.
   */
  public double getTempsPasse ()
  {
    double tempsPasse = 0 ;
    MTache lTache ;
    for (int i = 0 ; i < mTaches.size () ; i++)
    {
      lTache = (MTache) mTaches.get (i) ;
      tempsPasse = tempsPasse + lTache.getTempsPasse () ;
    }
    MTacheImprevue lTacheImprevue ;
    for (int i = 0 ; i < mTachesImprevues.size () ; i++)
    {
      lTacheImprevue = (MTacheImprevue) mTachesImprevues.get (i) ;
      tempsPasse = tempsPasse + lTacheImprevue.getTempsPasse () ;
    }
    return tempsPasse ;
  }

  /**
   * TODO Calcule le reste � passer pour l iteration
   * 
   * @return resteAPasser.
   */
  public double getResteAPasser ()
  {
    double resteAPasser = 0 ;
    MTache lTache ;
    for (int i = 0 ; i < mTaches.size () ; i++)
    {
      lTache = (MTache) mTaches.get (i) ;
      resteAPasser = resteAPasser + lTache.getResteAPasser () ;
    }
    MTacheImprevue lTacheImprevue ;
    for (int i = 0 ; i < mTachesImprevues.size () ; i++)
    {
      lTacheImprevue = (MTacheImprevue) mTachesImprevues.get (i) ;
      resteAPasser = resteAPasser + lTacheImprevue.getResteAPasser () ;
    }
    return resteAPasser ;
  }

  /**
   * TODO Calcule le pourcentage de d�passement de charge pour l iteration
   * 
   * @return pourcentage de depassement de charge.
   */
  public double getPrcDepassementCharge ()
  {
    return (this.getTempsPasse () + this.getResteAPasser () - this.getChargeInitiale ())
           / this.getChargeInitiale () ;
  }

  /**
   * TODO Calcule le d�passement de charge pour l iteration en homme jour
   * 
   * @return depassement de charge en HJ.
   */
  public double getHJDepassementCharge ()
  {
    return (this.getTempsPasse () + this.getResteAPasser () - this.getChargeInitiale ()) ;
  }

  /**
   * TODO Calcule le budget consomm� pour l iteration
   * 
   * @return budget consomm�
   */
  public double getBudgetConsomme ()
  {
    return (this.getTempsPasse () / this.getChargeInitiale ()) ;
  }

  /**
   * TODO Calcule le pourcentage d'avancement pour l iteration
   * 
   * @return pourcentage d avancement
   */
  public double getPrcAvancement ()
  {
    return (this.getTempsPasse () / (this.getTempsPasse () + this.getResteAPasser ())) ;
  }
}