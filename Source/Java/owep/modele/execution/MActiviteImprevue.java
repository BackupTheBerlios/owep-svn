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
  private int     mId ;         // Identifie l'activité imprevue de manière unique.
  private String  mNom ;        // Nom désignant l'activité imprevue.
  private String  mDescription ; // Description de l'activité imprevue.
  private MProjet mProjet ;     // Projet dont l'activité imprevue dépend.
  private ArrayList mTachesImprevues ;

  /**
   * Construit une instance vide de MActivite.
   */
  public MActiviteImprevue ()
  {
    mTachesImprevues = new ArrayList() ;
  }


  /**
   * Construit une instance initialisée de MActiviteImprevue.
   * @param pId Identifiant unqiue de l'activité.
   * @param pNom Nom désignant l'activité.
   * @param pDescription Description de l'activité.
   * @param pProjet Projet dont fait partie l'activité imlprévue.
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
   * Insertion de l'activité mprévue courant dans la base de données.
   * @param pConnection Connexion avec la base de données.
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
    // Préparation de la requête permettant d'obtenir l'id de l'activité imprévue
    String lRequete = "SELECT MAX(AIM_ID) FROM AIM_ACTIVITEIMPREVUE" ;
    ResultSet result = lRequest.executeQuery (lRequete) ;
    if (result.next ())
      setId (result.getInt (1)) ;
    result.close () ;
  }


  /**
   * Mise à jour de l'activité imprévue courant dans la base de données.
   * @param pConnection Connexion avec la base de données.
   * @throws SQLException si une erreur survient durant la mise à jour de la bd.
   */
  public void update (Connection pConnection) throws SQLException
  {
    assert getProjet () != null ;
    
    int lId = getId () ;
    
    // Préparation de la requête.
    String lRequete = "UPDATE AIM_ACTIVITEIMPREVUE SET " ;
    lRequete += "AIM_NOM = '" + getNom () + "', " ;
    lRequete += "AIM_DESCRIPTION = '" + getDescription () + "', " ;
    lRequete += "AIM_PRJ_ID = " + mProjet.getId () + " " ;
    lRequete += "WHERE AIM_ID = " + lId ;
    
    
    Statement lRequest = pConnection.createStatement () ;
    lRequest.executeUpdate (lRequete) ;
  }
  
  
  /**
   * Récupère la description de l'activité.
   * @return Description de l'activité.
   */
  public String getDescription ()
  {
    return mDescription ;
  }


  /**
   * Initialise la description de l'activité.
   * @param pDescription Description de l'activité.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }


  /**
   * Récupère l'identifiant de l'activité.
   * @return Identifiant unique de l'activité.
   */
  public int getId ()
  {
    return mId ;
  }


  /**
   * Initialise l'identifiant de l'activité.
   * @param pId Identifiant unique de l'activité.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }


  /**
   * Récupère le nom de l'activité.
   * @return Nom désignant l'activité.
   */
  public String getNom ()
  {
    return mNom ;
  }


  /**
   * Initialise le nom de l'activité.
   * @param pNom Nom désignant l'activité.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }


  /**
   * Récupère le projet dont fait partie l'activité imprévue.
   * @return le projet dont fait partie l'activité imprévue.
   */
  public MProjet getProjet ()
  {
    return mProjet ;
  }


  /**
   * Initialise le projet dont fait partie l'activité imprévue.
   * @param projet projet dont fait partie l'activité imprévue.
   */
  public void setProjet (MProjet pProjet)
  {
    mProjet = pProjet ;
  }
  
  
  /**
   * Récupère la liste des tâches imprévues qui instancie l'activité.
   * @return Liste des tâches imprévues qui instancie l'activité.
   */
  public ArrayList getListeTachesImprevues ()
  {
    return mTachesImprevues ;
  }


  /**
   * Initialise la liste des tâches imprévues qui instancie l'activité.
   * @param pTachesImprevues Liste des tâches imprévues qui instancie l'activité.
   */
  public void setListeTachesImprevues (ArrayList pTachesImprevues)
  {
    mTachesImprevues = pTachesImprevues ;
  }


  /**
   * Récupère le nombre de tâches imprévues qui instancie l'activité.
   * @return Nombre de tâches imprévues qui instancie l'activité.
   */
  public int getNbTachesImprevues ()
  {
    return mTachesImprevues.size () ;
  }


  /**
   * Récupère la tâche imprévue d'indice spécifié qui instancie l'activité.
   * @param pIndice Indice de la tâche imprévue dans la liste.
   * @return Tâche imprévue qui instancie l'activité.
   */
  public MTacheImprevue getTacheImprevue (int pIndice)
  {
    return (MTacheImprevue) mTachesImprevues.get (pIndice) ;
  }


  /**
   * Ajoute la tâche imprévue spécifiée qui instancie l'activité.
   * @param pTacheImprevue Tâche imprévue qui instancie l'activité.
   */
  public void addTacheImprevue (MTacheImprevue pTacheImprevue)
  {
    mTachesImprevues.add (pTacheImprevue) ;
  }
  
  
  /**
   * Supprime la tâche imprévue spécifiée qui instancie l'activité.
   * @param pTacheImprevue Tâche imprévue qui instancie l'activité.
   */
  public void supprimerTacheImprevue (MTacheImprevue pTacheImprevue)
  {
    mTachesImprevues.remove (pTacheImprevue) ;
  }
  
  /**
   * Supprime la tâche imprévue spécifiée qui instancie l'activité.
   * @param pTacheImprevue Tâche imprévue qui instancie l'activité.
   */
  public void supprimerTacheImprevue (int pPosition)
  {
    mTachesImprevues.remove (pPosition) ;
  }
  

  /**
   * Mise à jour de l'activité imprévue courant dans la base de données.
   * @param pConnection Connexion avec la base de données.
   * @throws SQLException si une erreur survient durant la mise à jour de la bd.
   */
  public void delete (Connection pConnection) throws SQLException
  {
    
    int lId = getId () ;
    
    // Préparation de la requête.
    String lRequete = "DELETE FROM AIM_ACTIVITEIMPREVUE " ;
    lRequete += "WHERE AIM_ID = " + lId ;
    
    
    Statement lRequest = pConnection.createStatement () ;
    lRequest.executeUpdate (lRequete) ;
  }
}