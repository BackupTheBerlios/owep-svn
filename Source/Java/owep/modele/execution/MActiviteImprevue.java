package owep.modele.execution ;


import java.sql.Connection ;
import java.sql.ResultSet ;
import java.sql.SQLException ;
import java.sql.Statement ;
import java.util.ArrayList;


/**
 * Description de la classe.
 */
public class MActiviteImprevue
{
  private int     mId ;         // Identifie l'activit� imprevue de mani�re unique.
  private String  mNom ;        // Nom d�signant l'activit� imprevue.
  private String  mDescription ; // Description de l'activit� imprevue.
  private MProjet mProjet ;     // Projet dont l'activit� imprevue d�pend.
  private ArrayList mTachesImprevues ;

  /**
   * Construit une instance vide de MActivite.
   */
  public MActiviteImprevue ()
  {
    mTachesImprevues = new ArrayList() ;
  }


  /**
   * Construit une instance initialis�e de MActiviteImprevue.
   * @param pId Identifiant unqiue de l'activit�.
   * @param pNom Nom d�signant l'activit�.
   * @param pDescription Description de l'activit�.
   * @param pProjet Projet dont fait partie l'activit� imlpr�vue.
   */
  public MActiviteImprevue (int pId, String pNom, String pDescription, MProjet pProjet)
  {
    mId = pId ;
    mNom = pNom ;
    mDescription = pDescription ;
    mProjet = pProjet ;
    mTachesImprevues = new ArrayList() ;
  }

  /**
   * Insertion de l'activit� mpr�vue courant dans la base de donn�es.
   * @param pConnection Connexion avec la base de donn�es.
   * @throws SQLException si une erreur survient durant l'insetion dans la bd.
   */
  public void create (Connection pConnection) throws SQLException
  {
    Statement lRequest = pConnection.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE,
                                                      ResultSet.CONCUR_UPDATABLE) ;
    ResultSet curseurActiviteImprevue = lRequest
      .executeQuery ("SELECT * FROM AIM_ACTIVITEIMPREVUE") ;
    curseurActiviteImprevue.moveToInsertRow () ;
    curseurActiviteImprevue.updateString (2, getNom ()) ;
    curseurActiviteImprevue.updateString (3, getDescription ()) ;
    curseurActiviteImprevue.updateInt (4, mProjet.getId ());
    curseurActiviteImprevue.insertRow () ;
    curseurActiviteImprevue.close () ;
    // Pr�paration de la requ�te permettant d'obtenir l'id de l'activit� impr�vue
    String lRequete = "SELECT MAX(AIM_ID) FROM AIM_ACTIVITEIMPREVUE" ;
    ResultSet result = lRequest.executeQuery (lRequete) ;
    if (result.next ())
      setId (result.getInt (1)) ;
    result.close () ;
  }


  /**
   * Mise � jour de l'activit� impr�vue courant dans la base de donn�es.
   * @param pConnection Connexion avec la base de donn�es.
   * @throws SQLException si une erreur survient durant la mise � jour de la bd.
   */
  public void update (Connection pConnection) throws SQLException
  {
    assert getProjet () != null ;
    
    int lId = getId () ;
    
    // Pr�paration de la requ�te.
    String lRequete = "UPDATE AIM_ACTIVITEIMPREVUE SET " ;
    lRequete += "AIM_NOM = '" + getNom () + "', " ;
    lRequete += "AIM_DESCRIPTION = '" + getDescription () + "', " ;
    lRequete += "AIM_PRJ_ID = " + mProjet.getId () + " " ;
    lRequete += "WHERE AIM_ID = " + lId ;
    
    
    Statement lRequest = pConnection.createStatement () ;
    lRequest.executeUpdate (lRequete) ;
  }
  
  
  /**
   * R�cup�re la description de l'activit�.
   * @return Description de l'activit�.
   */
  public String getDescription ()
  {
    return mDescription ;
  }


  /**
   * Initialise la description de l'activit�.
   * @param pDescription Description de l'activit�.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }


  /**
   * R�cup�re l'identifiant de l'activit�.
   * @return Identifiant unique de l'activit�.
   */
  public int getId ()
  {
    return mId ;
  }


  /**
   * Initialise l'identifiant de l'activit�.
   * @param pId Identifiant unique de l'activit�.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }


  /**
   * R�cup�re le nom de l'activit�.
   * @return Nom d�signant l'activit�.
   */
  public String getNom ()
  {
    return mNom ;
  }


  /**
   * Initialise le nom de l'activit�.
   * @param pNom Nom d�signant l'activit�.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }


  /**
   * R�cup�re le projet dont fait partie l'activit� impr�vue.
   * @return le projet dont fait partie l'activit� impr�vue.
   */
  public MProjet getProjet ()
  {
    return mProjet ;
  }


  /**
   * Initialise le projet dont fait partie l'activit� impr�vue.
   * @param projet projet dont fait partie l'activit� impr�vue.
   */
  public void setProjet (MProjet pProjet)
  {
    mProjet = pProjet ;
  }
  
  
  /**
   * R�cup�re la liste des t�ches impr�vues qui instancie l'activit�.
   * @return Liste des t�ches impr�vues qui instancie l'activit�.
   */
  public ArrayList getListeTachesImprevues ()
  {
    return mTachesImprevues ;
  }


  /**
   * Initialise la liste des t�ches impr�vues qui instancie l'activit�.
   * @param pTachesImprevues Liste des t�ches impr�vues qui instancie l'activit�.
   */
  public void setListeTachesImprevues (ArrayList pTachesImprevues)
  {
    mTachesImprevues = pTachesImprevues ;
  }


  /**
   * R�cup�re le nombre de t�ches impr�vues qui instancie l'activit�.
   * @return Nombre de t�ches impr�vues qui instancie l'activit�.
   */
  public int getNbTachesImprevues ()
  {
    return mTachesImprevues.size () ;
  }


  /**
   * R�cup�re la t�che impr�vue d'indice sp�cifi� qui instancie l'activit�.
   * @param pIndice Indice de la t�che impr�vue dans la liste.
   * @return T�che impr�vue qui instancie l'activit�.
   */
  public MTacheImprevue getTacheImprevue (int pIndice)
  {
    return (MTacheImprevue) mTachesImprevues.get (pIndice) ;
  }


  /**
   * Ajoute la t�che impr�vue sp�cifi�e qui instancie l'activit�.
   * @param pTacheImprevue T�che impr�vue qui instancie l'activit�.
   */
  public void addTacheImprevue (MTacheImprevue pTacheImprevue)
  {
    mTachesImprevues.add (pTacheImprevue) ;
  }
  
  
  /**
   * Supprime la t�che impr�vue sp�cifi�e qui instancie l'activit�.
   * @param pTacheImprevue T�che impr�vue qui instancie l'activit�.
   */
  public void supprimerTacheImprevue (MTacheImprevue pTacheImprevue)
  {
    mTachesImprevues.remove (pTacheImprevue) ;
  }
  
  /**
   * Supprime la t�che impr�vue sp�cifi�e qui instancie l'activit�.
   * @param pTacheImprevue T�che impr�vue qui instancie l'activit�.
   */
  public void supprimerTacheImprevue (int pPosition)
  {
    mTachesImprevues.remove (pPosition) ;
  }
  

  /**
   * Mise � jour de l'activit� impr�vue courant dans la base de donn�es.
   * @param pConnection Connexion avec la base de donn�es.
   * @throws SQLException si une erreur survient durant la mise � jour de la bd.
   */
  public void delete (Connection pConnection) throws SQLException
  {
    
    int lId = getId () ;
    
    // Pr�paration de la requ�te.
    String lRequete = "DELETE FROM AIM_ACTIVITEIMPREVUE " ;
    lRequete += "WHERE AIM_ID = " + lId ;
    
    
    Statement lRequest = pConnection.createStatement () ;
    lRequest.executeUpdate (lRequete) ;
  }
}