package owep.modele.execution ;


import java.util.ArrayList ;
import java.sql.Connection ;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date ;
import owep.modele.MModeleBase;


/**
 * Une it�ration est une �tape d'un projet. Elle se caract�rise par un ensemble de t�ches et de
 * participant qui utilisent et r�alisent des artefacts.
 */
public class MIteration extends MModeleBase
{
  private int       mId ;              // Identifie l'it�ration de mani�re unique.
  private int       mNumero ;          // Num�ro de l'it�ration.
  private String    mNom ;             // Nom d�signant l'it�ration.
  private Date      mDateDebutPrevue ; // Date de d�but pr�vue pour l'it�ration.
  private Date      mDateFinPrevue ;   // Date de fin pr�vue pour l'it�ration.
  private Date      mDateDebutReelle ; // Date de d�but r�elle de l'it�ration.
  private Date      mDateFinReelle ;   // Date de fin r�elle de l'it�ration.
  private MProjet   mProjet ;          // Projet dont l'it�ration est une �tape.
  private ArrayList mTaches ;          // Liste des t�ches r�alis�es durant l'it�ration.


  /**
   * Cr�e une instance vide de MIteration.
   */
  public MIteration ()
  {
    super () ;
    
    mTaches = new ArrayList () ;
  }


  /**
   * Cr�e une instance initialis�e de MIteration.
   * @param pNumero Num�ro de l'it�ration.
   * @param pNom Nom d�signant l'it�ration.
   * @param pDateDebutPrevue Date de d�but pr�vue de l'it�ration.
   * @param pDateFinPrevue Date de fin pr�vue de l'it�ration.
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
   * Cr�e une instance de MIteration
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
    
    mNumero          = pNumero ;
    mNom             = pNom ;
    mDateDebutPrevue = pDateDebutPrevue ;
    mDateFinPrevue   = pDateFinPrevue ;
    mDateDebutReelle = pDateDebutReelle ;
    mDateFinReelle   = pDateFinReelle ;
    
    mTaches = new ArrayList () ;
  }

  /**
   * Insertion de l'it�ration courante dans la base de donn�es.
   * @param pConnection Connexion avec la base de donn�es.
   * @throws SQLException si une erreur survient durant l'insetion dans la BD.
   */
  public void create (Connection pConnection) throws SQLException 
  {
    assert getProjet () != null ;
    
    // Pr�paration de la requ�te
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
    
//  Pr�paration de la requ�te permettant d'obtenir l'id de l'it�ration    
    String lRequete = "SELECT MAX(ITE_ID) FROM ITE_ITERATION" ;
    ResultSet result = lRequest.executeQuery (lRequete) ;
    if (result.next () )
      setId (result.getInt (1)) ;
    result.close ();
  }

  
  /**
   * Permet demettre � jour l'it�ration courante dans la base de donn�es.
   * @param pConnection Connexion avec la base de donn�es.
   * @throws SQLException si une erreur survient durant la mise � jour.
   */
  public void update (Connection pConnection) throws SQLException
  {
    assert getProjet () != null ;
    
    int lId = getId () ;
    
    // Pr�paration de la requ�te
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
   * R�cup�re la date de d�but pr�vue pour l'it�ration.
   * @return Date de d�but pr�vue pour l'it�ration.
   */
  public Date getDateDebutPrevue ()
  {
    return mDateDebutPrevue ;
  }


  /**
   * Initialise la date de d�but pr�vue pour l'it�ration.
   * @param pDateDebutPrevue Date de d�but pr�vue pour l'it�ration.
   */
  public void setDateDebutPrevue (Date pDateDebutPrevue)
  {
    mDateDebutPrevue = pDateDebutPrevue ;
  }


  /**
   * R�cup�re la date de d�but r�elle de l'it�ration.
   * @return Date de d�but r�elle de l'it�ration.
   */
  public Date getDateDebutReelle ()
  {
    return mDateDebutReelle ;
  }


  /**
   * Initialise la date de d�but r�elle de l'it�ration.
   * @param pDateDebutReelle Date de d�but r�elle de l'it�ration.
   */
  public void setDateDebutReelle (Date pDateDebutReelle)
  {
    mDateDebutReelle = pDateDebutReelle ;
  }


  /**
   * R�cup�re la date de fin pr�vue pour l'it�ration.
   * @return Date de d�but fin pour l'it�ration.
   */
  public Date getDateFinPrevue ()
  {
    return mDateFinPrevue ;
  }


  /**
   * Initialise la date de fin pr�vue pour l'it�ration.
   * @param pDateFinPrevue Date de fin pr�vue pour l'it�ration.
   */
  public void setDateFinPrevue (Date pDateFinPrevue)
  {
    mDateFinPrevue = pDateFinPrevue ;
  }


  /**
   * R�cup�re la date de fin r�elle de l'it�ration.
   * @return Date de d�but fin de l'it�ration.
   */
  public Date getDateFinReelle ()
  {
    return mDateFinReelle ;
  }


  /**
   * Initialise la date de fin r�elle de l'it�ration.
   * @param pDateFinReelle Date de fin r�elle de l'it�ration.
   */
  public void setDateFinReelle (Date pDateFinReelle)
  {
    mDateFinReelle = pDateFinReelle ;
  }


  /**
   * R�cup�re l'identifiant de l'it�ration.
   * @return Identifiant unique de l'it�ration.
   */
  public int getId ()
  {
    return mId ;
  }


  /**
   * Initialise l'identifiant de l'it�ration.
   * @param pId Identifiant unique de l'it�ration.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }


  /**
   * R�cup�re le nom de l'it�ration.
   * @return Nom d�signant l'it�ration.
   */
  public String getNom ()
  {
    return mNom ;
  }


  /**
   * Initialise le nom de l'it�ration.
   * @param pNom Nom d�signant l'it�ration.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }


  /**
   * R�cup�re le num�ro de l'it�ration.
   * @return Num�ro de l'it�ration.
   */
  public int getNumero ()
  {
    return mNumero ;
  }


  /**
   * Initialise le num�ro de l'it�ration.
   * @param pNumero Num�ro de l'it�ration.
   */
  public void setNumero (int pNumero)
  {
    mNumero = pNumero ;
  }


  /**
   * R�cup�re le projet dont l'it�ration est une �tape.
   * @return Projet dont l'it�ration est une �tape.
   */
  public MProjet getProjet ()
  {
    return mProjet ;
  }


  /**
   * Associe le projet dont l'it�ration est une �tape.
   * @param pProjet Projet dont l'it�ration est une �tape.
   */
  public void setProjet (MProjet pProjet)
  {
    mProjet = pProjet ;
  }


  /**
   * R�cup�re la liste des t�ches r�alis�es durant l'it�ration.
   * @return Liste des t�ches r�alis�es durant l'it�ration.
   */
  public ArrayList getListeTaches ()
  {
    return mTaches ;
  }


  /**
   * Initialise la liste des t�ches r�alis�es durant l'it�ration.
   * @param pTaches Liste des t�ches r�alis�es durant l'it�ration.
   */
  public void setListeTaches (ArrayList pTaches)
  {
    mTaches = pTaches ;
  }


  /**
   * R�cup�re le nombre de t�ches r�alis�es durant l'it�ration.
   * @return Nombre de t�ches r�alis�es durant l'it�ration.
   */
  public int getNbTaches ()
  {
    return mTaches.size () ;
  }


  /**
   * R�cup�re la t�che d'indice sp�cifi� r�alis�e durant l'it�ration.
   * @param pIndice Indice de la t�che dans la liste.
   * @return T�che r�alis�e durant l'it�ration.
   */
  public MTache getTache (int pIndice)
  {
    return (MTache) mTaches.get (pIndice) ;
  }


  /**
   * Ajoute la t�che sp�cifi�e � l'it�ration.
   * @param pTache T�che r�alis�e durant l'it�ration.
   */
  public void addTache (MTache pTache)
  {
    mTaches.add (pTache) ;
  }


  /**
   * Supprime la t�che sp�cifi�e de l'it�ration.
   * @param pIndice Indice de la t�che � supprimer.
   */
  public void supprimerTache (int pIndice)
  {
    mTaches.remove (pIndice) ;
  }
}
