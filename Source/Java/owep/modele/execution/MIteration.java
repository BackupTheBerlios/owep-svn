package owep.modele.execution ;


import java.util.ArrayList ;
import java.util.Iterator ;
import java.util.Date ;
import owep.modele.MModeleBase ;


/**
 * Une itération est une étape d'un projet. Elle se caractérise par un ensemble de tâches et de
 * participant qui utilisent et réalisent des artefacts.
 */
public class MIteration extends MModeleBase
{
  public static final int ETAT_NON_DEMARRE = 0 ; // iteration non demarree
  public static final int ETAT_EN_COURS    = 1 ; // iteration en cours
  public static final int ETAT_TERMINE     = 2 ; //iteration terminee

  private int             mId ;                 // Identifie l'itération de manière unique.
  private int             mNumero ;             // Numéro de l'itération.
  private String          mNom ;                // Nom désignant l'itération.
  private String          mBilan ;              // Bilan de l'itération
  private Date            mDateDebutPrevue ;    // Date de début prévue pour l'itération.
  private Date            mDateFinPrevue ;      // Date de fin prévue pour l'itération.
  private Date            mDateDebutReelle ;    // Date de début réelle de l'itération.
  private Date            mDateFinReelle ;      // Date de fin réelle de l'itération.
  private MProjet         mProjet ;             // Projet dont l'itération est une étape.
  private ArrayList       mTaches ;             // Liste des tâches réalisées durant l'itération.
  private ArrayList       mTachesImprevues ;    // Liste des tâches imprévues réalisées durant
                                                // l'itération.
  private ArrayList       mMesures ;            // Liste des mesures réalisées durant l'itération.
  private int             mEtat ;               // Etat de l iteration


  /**
   * Crée une instance vide de MIteration.
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
   * Crée une instance initialisée de MIteration.
   * 
   * @param pNumero Numéro de l'itération.
   * @param pNom Nom désignant l'itération.
   * @param pDateDebutPrevue Date de début prévue de l'itération.
   * @param pDateFinPrevue Date de fin prévue de l'itération.
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
   * Crée une instance de MIteration
   * 
   * @param pNumero Numéro de l'itération.
   * @param pNom Nom désignant l'itération.
   * @param pDateDebutPrevue Date de début prévue de l'itération.
   * @param pDateFinPrevue Date de fin prévue de l'itération.
   * @param pDateDebutReelle Date de début réelle de l'itération.
   * @param pDateFinReelle Date de fin réelle de l'itération.
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
   * Récupère la date de début prévue pour l'itération.
   * 
   * @return Date de début prévue pour l'itération.
   */
  public Date getDateDebutPrevue ()
  {
    return mDateDebutPrevue ;
  }

  /**
   * Initialise la date de début prévue pour l'itération.
   * 
   * @param pDateDebutPrevue Date de début prévue pour l'itération.
   */
  public void setDateDebutPrevue (Date pDateDebutPrevue)
  {
    mDateDebutPrevue = pDateDebutPrevue ;
  }

  /**
   * Récupère la date de début réelle de l'itération.
   * 
   * @return Date de début réelle de l'itération.
   */
  public Date getDateDebutReelle ()
  {
    return mDateDebutReelle ;
  }

  /**
   * Initialise la date de début réelle de l'itération.
   * 
   * @param pDateDebutReelle Date de début réelle de l'itération.
   */
  public void setDateDebutReelle (Date pDateDebutReelle)
  {
    mDateDebutReelle = pDateDebutReelle ;
  }

  /**
   * Récupère la date de fin prévue pour l'itération.
   * 
   * @return Date de début fin pour l'itération.
   */
  public Date getDateFinPrevue ()
  {
    return mDateFinPrevue ;
  }

  /**
   * Initialise la date de fin prévue pour l'itération.
   * 
   * @param pDateFinPrevue Date de fin prévue pour l'itération.
   */
  public void setDateFinPrevue (Date pDateFinPrevue)
  {
    mDateFinPrevue = pDateFinPrevue ;
  }

  /**
   * Récupère la date de fin réelle de l'itération.
   * 
   * @return Date de début fin de l'itération.
   */
  public Date getDateFinReelle ()
  {
    return mDateFinReelle ;
  }

  /**
   * Initialise la date de fin réelle de l'itération.
   * 
   * @param pDateFinReelle Date de fin réelle de l'itération.
   */
  public void setDateFinReelle (Date pDateFinReelle)
  {
    mDateFinReelle = pDateFinReelle ;
  }

  /**
   * Récupère l'identifiant de l'itération.
   * 
   * @return Identifiant unique de l'itération.
   */
  public int getId ()
  {
    return mId ;
  }

  /**
   * Initialise l'identifiant de l'itération.
   * 
   * @param pId Identifiant unique de l'itération.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }

  /**
   * Récupère le nom de l'itération.
   * 
   * @return Nom désignant l'itération.
   */
  public String getNom ()
  {
    return mNom ;
  }

  /**
   * Initialise le nom de l'itération.
   * 
   * @param pNom Nom désignant l'itération.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }

  /**
   * Récupère le numéro de l'itération.
   * 
   * @return Numéro de l'itération.
   */
  public int getNumero ()
  {
    return mNumero ;
  }

  /**
   * Initialise le numéro de l'itération.
   * 
   * @param pNumero Numéro de l'itération.
   */
  public void setNumero (int pNumero)
  {
    mNumero = pNumero ;
  }

  /**
   * Récupère le projet dont l'itération est une étape.
   * 
   * @return Projet dont l'itération est une étape.
   */
  public MProjet getProjet ()
  {
    return mProjet ;
  }

  /**
   * Associe le projet dont l'itération est une étape.
   * 
   * @param pProjet Projet dont l'itération est une étape.
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
   * Récupère la liste des tâches réalisées durant l'itération.
   * 
   * @return Liste des tâches réalisées durant l'itération.
   */
  public ArrayList getListeTaches ()
  {
    return mTaches ;
  }

  /**
   * Initialise la liste des tâches réalisées durant l'itération.
   * 
   * @param pTaches Liste des tâches réalisées durant l'itération.
   */
  public void setListeTaches (ArrayList pTaches)
  {
    mTaches = pTaches ;
  }

  /**
   * Récupère le nombre de tâches réalisées durant l'itération.
   * 
   * @return Nombre de tâches réalisées durant l'itération.
   */
  public int getNbTaches ()
  {
    return mTaches.size () ;
  }

  /**
   * Récupère la tâche d'indice spécifié réalisée durant l'itération.
   * 
   * @param pIndice Indice de la tâche dans la liste.
   * @return Tâche réalisée durant l'itération.
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
   * Ajoute la tâche spécifiée à l'itération.
   * 
   * @param pTache Tâche réalisée durant l'itération.
   */
  public void addTache (MTache pTache)
  {
    mTaches.add (pTache) ;
  }

  /**
   * Supprime la tâche spécifiée de l'itération.
   * 
   * @param pIndice Indice de la tâche à supprimer.
   */
  public void supprimerTache (int pIndice)
  {
    mTaches.remove (pIndice) ;
  }

  /**
   * Supprime la tâche spécifiée de l'itération.
   * 
   * @param pTache Tâche à supprimer.
   */
  public void supprimerTache (MTache pTache)
  {
    mTaches.remove (pTache) ;
  }

  /**
   * TODO Récupère mMesures.
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
   * @param pMesure Mesure à ajouter a l iteration.
   */
  public void addMesure (MMesureIndicateur pMesure)
  {
    if (!mMesures.contains (pMesure))
      mMesures.add (pMesure) ;
    if (pMesure.getIteration () != this)
      pMesure.setIteration (this) ;
  }

  /**
   * Récupère la liste des tâches imprévues réalisées durant l'itération.
   * 
   * @return Liste des tâches imprévues réalisées durant l'itération.
   */
  public ArrayList getListeTachesImprevues ()
  {
    return mTachesImprevues ;
  }

  /**
   * Initialise la liste des tâches imprévues réalisées durant l'itération.
   * 
   * @param pTachesImprevues Liste des tâches imprévues réalisées durant l'itération.
   */
  public void setListeTachesImprevues (ArrayList pTachesImprevues)
  {
    mTachesImprevues = pTachesImprevues ;
  }

  /**
   * Récupère le nombre de tâches imprévue réalisées durant l'itération.
   * 
   * @return Nombre de tâches imprévue réalisées durant l'itération.
   */
  public int getNbTachesImprevues ()
  {
    return mTachesImprevues.size () ;
  }

  /**
   * Récupère la tâche imprévue d'indice spécifié réalisée durant l'itération.
   * 
   * @param pIndice Indice de la tâche imprévue dans la liste.
   * @return Tâche imprévue réalisée durant l'itération.
   */
  public MTacheImprevue getTacheImprevue (int pIndice)
  {
    return (MTacheImprevue) mTachesImprevues.get (pIndice) ;
  }

  /**
   * Ajoute la tâche imprévue spécifiée à l'itération.
   * 
   * @param pTacheImprevues Tâche imprévue réalisée durant l'itération.
   */
  public void addTacheImprevue (MTacheImprevue pTacheImprevue)
  {
    mTachesImprevues.add (pTacheImprevue) ;
  }

  /**
   * Supprime la tâche imprévue spécifiée de l'itération.
   * 
   * @param pIndice Indice de la tâche imprévue à supprimer.
   */
  public void supprimerTacheImprevue (int pIndice)
  {
    mTachesImprevues.remove (pIndice) ;
  }

  /**
   * Supprime la tâche imprévue spécifiée de l'itération.
   * 
   * @param pTacheImprevue Tâche imprévue à supprimer.
   */
  public void supprimerTacheImprevue (MTacheImprevue pTacheImprevue)
  {
    mTachesImprevues.remove (pTacheImprevue) ;
  }

  /**
   * TODO Calcule la charge initiale prévue pour l iteration
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
   * TODO Calcule le temps passé pour l iteration
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
   * TODO Calcule le reste à passer pour l iteration
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
   * TODO Calcule le pourcentage de dépassement de charge pour l iteration
   * 
   * @return pourcentage de depassement de charge.
   */
  public double getPrcDepassementCharge ()
  {
    return (this.getTempsPasse () + this.getResteAPasser () - this.getChargeInitiale ())
           / this.getChargeInitiale () ;
  }

  /**
   * TODO Calcule le dépassement de charge pour l iteration en homme jour
   * 
   * @return depassement de charge en HJ.
   */
  public double getHJDepassementCharge ()
  {
    return (this.getTempsPasse () + this.getResteAPasser () - this.getChargeInitiale ()) ;
  }

  /**
   * TODO Calcule le budget consommé pour l iteration
   * 
   * @return budget consommé
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