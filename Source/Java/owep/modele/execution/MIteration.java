package owep.modele.execution ;


import java.util.ArrayList ;
import java.sql.Connection ;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date ;
import owep.modele.MModeleBase;


/**
 * Une itération est une étape d'un projet. Elle se caractérise par un ensemble de tâches et de
 * participant qui utilisent et réalisent des artefacts.
 */
public class MIteration extends MModeleBase
{
  private int       mId ;              // Identifie l'itération de manière unique.
  private int       mNumero ;          // Numéro de l'itération.
  private String    mNom ;             // Nom désignant l'itération.
  private Date      mDateDebutPrevue ; // Date de début prévue pour l'itération.
  private Date      mDateFinPrevue ;   // Date de fin prévue pour l'itération.
  private Date      mDateDebutReelle ; // Date de début réelle de l'itération.
  private Date      mDateFinReelle ;   // Date de fin réelle de l'itération.
  private MProjet   mProjet ;          // Projet dont l'itération est une étape.
  private ArrayList mTaches ;          // Liste des tâches réalisées durant l'itération.


  /**
   * Crée une instance vide de MIteration.
   */
  public MIteration ()
  {
    super () ;
    
    mTaches = new ArrayList () ;
  }


  /**
   * Crée une instance initialisée de MIteration.
   * @param pNumero Numéro de l'itération.
   * @param pNom Nom désignant l'itération.
   * @param pDateDebutPrevue Date de début prévue de l'itération.
   * @param pDateFinPrevue Date de fin prévue de l'itération.
   */
  public MIteration (int pNumero, String pNom, Date pDateDebutPrevue, Date pDateFinPrevue)
  {
    super () ;
    
    mNumero          = pNumero ;
    mNom             = pNom ;
    mDateDebutPrevue = pDateDebutPrevue ;
    mDateFinPrevue   = pDateFinPrevue ;
    
    mTaches = new ArrayList () ;
  }


  /**
   * Crée une instance de MIteration
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
    
    mNumero          = pNumero ;
    mNom             = pNom ;
    mDateDebutPrevue = pDateDebutPrevue ;
    mDateFinPrevue   = pDateFinPrevue ;
    mDateDebutReelle = pDateDebutReelle ;
    mDateFinReelle   = pDateFinReelle ;
    
    mTaches = new ArrayList () ;
  }

  /**
   * Insertion de l'itération courante dans la base de données.
   * @param pConnection Connexion avec la base de données.
   * @throws SQLException si une erreur survient durant l'insetion dans la BD.
   */
  public void create (Connection pConnection) throws SQLException 
  {
    assert getProjet () != null ;
    
    // Préparation de la requête
    /*String lRequete = "INSERT INTO ITE_ITERATION (ITE_NUMERO, ITE_NOM, ITE_DATEDEBUTPREVUE, ITE_DATEFINPREVUE, ITE_DATEDEBUTREELLE, ITE_DATEFINREELLE, ITE_ETAT, ITE_PRJ_ID) VALUES (" ;
    lRequete += getNumero() + ", '" ;
    lRequete += getNom () + "', '" ;
    lRequete += getDateDebutPrevue () + "', '" ;
    lRequete += getDateFinPrevue () + "', '" ;
    lRequete += getDateDebutReelle () + "', '" ;
    lRequete += getDateFinReelle () + "', " ;
    lRequete += 0 + ", " ;
    lRequete += getProjet ().getId () + ") " ;
    
    Statement lRequest = pConnection.createStatement () ;
    lRequest.executeQuery (lRequete) ;*/
    Statement lRequest = pConnection.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE) ;
    
    ResultSet curseurIteration = lRequest.executeQuery ("SELECT * FROM ITE_ITERATION") ;
    curseurIteration.moveToInsertRow () ;
    curseurIteration.updateInt (2, getNumero ()) ;
    curseurIteration.updateString (3, getNom ()) ;
    curseurIteration.updateDate (4, new java.sql.Date (getDateDebutPrevue ().getTime ())) ;
    curseurIteration.updateDate (5, new java.sql.Date (getDateFinPrevue ().getTime ())) ;
    //curseurIteration.updateDate(6, new java.sql.Date(getDateDebutReelle().getTime()));
    //curseurIteration.updateDate(7, new java.sql.Date(getDateFinReelle().getTime()));
    curseurIteration.updateInt (8, 0) ;
    curseurIteration.updateInt (9, getProjet ().getId ()) ;
    curseurIteration.insertRow () ;
    curseurIteration.close () ;
    //pConnection.commit () ;
    
//  Préparation de la requête permettant d'obtenir l'id de l'itération    
    String lRequete = "SELECT MAX(ITE_ID) FROM ITE_ITERATION" ;
    ResultSet result = lRequest.executeQuery (lRequete) ;
    if (result.next () )
      setId (result.getInt (1)) ;
    result.close ();
  }

  
  /**
   * Permet demettre à jour l'itération courante dans la base de données.
   * @param pConnection Connexion avec la base de données.
   * @throws SQLException si une erreur survient durant la mise à jour.
   */
  public void update (Connection pConnection) throws SQLException
  {
    assert getProjet () != null ;
    
    int lId = getId () ;
    
    // Préparation de la requête
    String lRequete = "UPDATE ITE_ITERATION SET " ;
    lRequete += "ITE_NUMERO = " + getNumero () + ", " ;
    lRequete += "ITE_NOM = '" + getNom () + "', " ;
    lRequete += "ITE_DATEDEBUTPREVUE = '" + getDateDebutPrevue () + "', " ;
    lRequete += "ITE_DATEFINPREVUE = '" + getDateFinPrevue () + "', " ;
    lRequete += "ITE_DATEDEBUTREELLE = '" + getDateDebutReelle () + "', " ;
    lRequete += "ITE_DATEFINREELLE = '" + getDateFinReelle () + "', " ;
    lRequete += "ITE_ETAT = " + 0 + ", " ;
    lRequete += "ITE_PRJ_ID = " + getProjet ().getId () + " " ;
    lRequete += "WHERE ITE_ID = " + lId ;
    
    Statement lRequest = pConnection.createStatement () ;
    lRequest.executeUpdate (lRequete) ;
  }
  
  /**
   * Récupère la date de début prévue pour l'itération.
   * @return Date de début prévue pour l'itération.
   */
  public Date getDateDebutPrevue ()
  {
    return mDateDebutPrevue ;
  }


  /**
   * Initialise la date de début prévue pour l'itération.
   * @param pDateDebutPrevue Date de début prévue pour l'itération.
   */
  public void setDateDebutPrevue (Date pDateDebutPrevue)
  {
    mDateDebutPrevue = pDateDebutPrevue ;
  }


  /**
   * Récupère la date de début réelle de l'itération.
   * @return Date de début réelle de l'itération.
   */
  public Date getDateDebutReelle ()
  {
    return mDateDebutReelle ;
  }


  /**
   * Initialise la date de début réelle de l'itération.
   * @param pDateDebutReelle Date de début réelle de l'itération.
   */
  public void setDateDebutReelle (Date pDateDebutReelle)
  {
    mDateDebutReelle = pDateDebutReelle ;
  }


  /**
   * Récupère la date de fin prévue pour l'itération.
   * @return Date de début fin pour l'itération.
   */
  public Date getDateFinPrevue ()
  {
    return mDateFinPrevue ;
  }


  /**
   * Initialise la date de fin prévue pour l'itération.
   * @param pDateFinPrevue Date de fin prévue pour l'itération.
   */
  public void setDateFinPrevue (Date pDateFinPrevue)
  {
    mDateFinPrevue = pDateFinPrevue ;
  }


  /**
   * Récupère la date de fin réelle de l'itération.
   * @return Date de début fin de l'itération.
   */
  public Date getDateFinReelle ()
  {
    return mDateFinReelle ;
  }


  /**
   * Initialise la date de fin réelle de l'itération.
   * @param pDateFinReelle Date de fin réelle de l'itération.
   */
  public void setDateFinReelle (Date pDateFinReelle)
  {
    mDateFinReelle = pDateFinReelle ;
  }


  /**
   * Récupère l'identifiant de l'itération.
   * @return Identifiant unique de l'itération.
   */
  public int getId ()
  {
    return mId ;
  }


  /**
   * Initialise l'identifiant de l'itération.
   * @param pId Identifiant unique de l'itération.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }


  /**
   * Récupère le nom de l'itération.
   * @return Nom désignant l'itération.
   */
  public String getNom ()
  {
    return mNom ;
  }


  /**
   * Initialise le nom de l'itération.
   * @param pNom Nom désignant l'itération.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }


  /**
   * Récupère le numéro de l'itération.
   * @return Numéro de l'itération.
   */
  public int getNumero ()
  {
    return mNumero ;
  }


  /**
   * Initialise le numéro de l'itération.
   * @param pNumero Numéro de l'itération.
   */
  public void setNumero (int pNumero)
  {
    mNumero = pNumero ;
  }


  /**
   * Récupère le projet dont l'itération est une étape.
   * @return Projet dont l'itération est une étape.
   */
  public MProjet getProjet ()
  {
    return mProjet ;
  }


  /**
   * Associe le projet dont l'itération est une étape.
   * @param pProjet Projet dont l'itération est une étape.
   */
  public void setProjet (MProjet pProjet)
  {
    mProjet = pProjet ;
  }


  /**
   * Récupère la liste des tâches réalisées durant l'itération.
   * @return Liste des tâches réalisées durant l'itération.
   */
  public ArrayList getListeTaches ()
  {
    return mTaches ;
  }


  /**
   * Initialise la liste des tâches réalisées durant l'itération.
   * @param pTaches Liste des tâches réalisées durant l'itération.
   */
  public void setListeTaches (ArrayList pTaches)
  {
    mTaches = pTaches ;
  }


  /**
   * Récupère le nombre de tâches réalisées durant l'itération.
   * @return Nombre de tâches réalisées durant l'itération.
   */
  public int getNbTaches ()
  {
    return mTaches.size () ;
  }


  /**
   * Récupère la tâche d'indice spécifié réalisée durant l'itération.
   * @param pIndice Indice de la tâche dans la liste.
   * @return Tâche réalisée durant l'itération.
   */
  public MTache getTache (int pIndice)
  {
    return (MTache) mTaches.get (pIndice) ;
  }


  /**
   * Ajoute la tâche spécifiée à l'itération.
   * @param pTache Tâche réalisée durant l'itération.
   */
  public void addTache (MTache pTache)
  {
    mTaches.add (pTache) ;
  }


  /**
   * Supprime la tâche spécifiée de l'itération.
   * @param pIndice Indice de la tâche à supprimer.
   */
  public void supprimerTache (int pIndice)
  {
    mTaches.remove (pIndice) ;
  }
}
