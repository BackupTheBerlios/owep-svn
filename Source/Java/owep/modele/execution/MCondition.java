package owep.modele.execution ;

import java.sql.Connection ;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 * MCondition permet de stocker une dépendance entre deux tâches. Si la tâche à l'origine de la
 * dépendance est dans l'état souhaité, la condition est considérée comme vérifiée.
 */
public class MCondition
{
  private int    mId ;              // Identifie la condition de manière unique
  private MTache mTache ;           // Tâche ayant une dépendance.
  private MTache mTachePrecedente ; // Tâche vers laquelle il y a une dépendance.
  private int    mEtat ;            // Etat de la tâche nécessaire pour que la condition soit vrai.


  /**
   * Construit une instance vide de MCondition.
   */
  public MCondition ()
  {
    super () ;
  }


  /**
   * Construit une instance initialisée de MCondition.
   * @param pTache Identifiant de la tâche ayant une dépendance.
   * @param pTachePrecedente Tâche vers laquelle il y a une dépendance.
   * @param pEtat Etat de la tâche nécessaire pour que la condition soit vrai.
   */
  public MCondition (int pId, MTache pTache, MTache pTachePrecedente, int pEtat)
  {
    super () ;
    
    mId              = pId ;
    mTache           = pTache ;
    mTachePrecedente = pTachePrecedente ;
    mEtat            = pEtat ;
  }

  
  
  /**
   * Récupère l'identifiant de la condition.
   * @return Identifiant unique de la condition
   */
  public int getId ()
  {
    return mId ;
  }


  /**
   * Insertion de la condition courante dans la base de données.
   * @param pConnection Connexion avec la base de données
   * @throws SQLException Si une erreur survient durant l'insertion dans la BD.
   */
  public void create (Connection pConnection) throws SQLException 
  {
    assert getTache () != null ;
    assert getTachePrecedente () != null ;
    
    
    Statement lRequest = pConnection.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, 

ResultSet.CONCUR_UPDATABLE) ;
    ResultSet curseurCondition = lRequest.executeQuery ("SELECT * FROM CND_CONDITION") ;
    curseurCondition.moveToInsertRow () ;
    curseurCondition.updateInt (2, getTache ().getId ()) ;
    curseurCondition.updateInt (3, getTachePrecedente ().getId ()) ;
    curseurCondition.updateInt (4,  getEtat ()) ;
    curseurCondition.insertRow () ;
    curseurCondition.close () ;
    
//  Préparation de la requête permettant d'obtenir l'id de la tâche
    String lRequete = "SELECT MAX(CND_ID) FROM CND_CONDITION" ;
    ResultSet result = lRequest.executeQuery (lRequete) ;
    if (result.next ())
    {
      setId (result.getInt (1)) ;
    }
    result.close () ;
  }

  
  /**
   * Permet de mettre à jour la condition courant dans la base de données
   * @param pConnection Connexion avec la BD.
   * @throws SQLException Si une erreur survient durant la mise à jour.
   */
  public void update (Connection pConnection) throws SQLException
  {
    assert getTache () != null ;
    assert getTachePrecedente () != null ;
    
    int lId = getId () ;
    
    String lRequete = "UPDATE CND_CONDITION SET " ;
    lRequete += "CND_TAC_ID = " + getTache ().getId () + ", " ;
    lRequete += "CND_TAC_ID_PREC = " + getTachePrecedente ().getId () + ", " ;
    lRequete += "CND_ETAT = " + getEtat () + " " ;
    lRequete += "WHERE CND_ID = " + lId ;
    
    Statement lRequest = pConnection.createStatement () ;
    lRequest.executeUpdate (lRequete) ;
  }
  
  
  /**
   * Initialise l'identifiant de la condition.
   * @param pId Identifiant unique de la condition
   */
  public void setId (int pId)
  {
    mId = pId ;
  }
  
  
  /**
   * Récupère l'état de la tâche nécessaire pour que la condition soit vrai.
   * @return Etat de la tâche nécessaire pour que la condition soit vrai.
   */
  public int getEtat ()
  {
    return mEtat ;
  }


  /**
   * Initialise l'état de la tâche nécessaire pour que la condition soit vrai.
   * @param pEtat Etat de la tâche nécessaire pour que la condition soit vrai.
   */
  public void setEtat (int pEtat)
  {
    mEtat = pEtat ;
  }


  /**
   * Récupère la tâche ayant une dépendance.
   * @return Tâche ayant une dépendance.
   */
  public MTache getTache ()
  {
    return mTache ;
  }


  /**
   * Initialise la tâche ayant une dépendance.
   * @param pTache Tâche ayant une dépendance.
   */
  public void setTache (MTache pTache)
  {
    mTache = pTache ;
  }


  /**
   * Récupère la tâche vers laquelle il y a une dépendance.
   * @return Tâche vers laquelle il y a une dépendance.
   */
  public MTache getTachePrecedente ()
  {
    return mTachePrecedente ;
  }


  /**
   * Associe la tâche vers laquelle il y a une dépendance.
   * @param pTachePrecedente Tâche vers laquelle il y a une dépendance.
   */
  public void setTachePrecedente (MTache pTachePrecedente)
  {
    mTachePrecedente = pTachePrecedente ;
  }
  
  
  /**
   * Indique si la condition est vérifiée, c'est à dire si la tâche à l'origine de la dépendance est
   * dans l'état souhaité.
   * @return True si la condition est vérifiée et False sinon.
   */
  boolean estVerifiee ()
  {
    return mTachePrecedente.getEtat () == mEtat ;
  }
}
