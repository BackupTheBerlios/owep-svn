package owep.modele.execution ;


import owep.modele.MModeleBase ;
import owep.modele.processus.MProduit;
import java.sql.Connection ;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Un artefact représente un sous-ensemble d'un produit, réalisé durant un projet.
 */
public class MArtefact extends MModeleBase
{
  private int            mId ;            // Identifie l'artefact de manière unique.
  private String         mNom ;           // Nom de l'artefact.
  private String         mDescription ;   // Description de l'artefact.
  private String         mNomFichier ;    // Nom du fichier correspondant à l'artefact.
  private MTache         mTacheEntree ;   // Tâche dont est issu l'artefact.
  private MTache         mTacheSortie ;   // Tâche qui nécessite l'artefact.
  private MProduit       mProduit ;       // Produit que l'artefact instancie.
  private MCollaborateur mResponsable ;   // Collaborateur responsable de l'artefact.
  private MProjet        mProjet ;        // Projet pour lequel est réalisé l'artefact.


  /**
   * Crée une instance vide de MArtefact.
   */
  public MArtefact ()
  {
  }


  /**
   * Crée une instance initialisée de MArtefact.
   * @param pId Identifiant unique de l'artefact.
   * @param pNom Nom de l'artefact.
   * @param pDescription Description de l'artefact.
   */
  public MArtefact (int pId, String pNom, String pDescription)
  {
    mId          = pId ;
    mNom         = pNom ;
    mDescription = pDescription ;
  }

  
  /**
   * Insertion de l'artefact courant dans la base de données.
   * @param pConnection Connexion avec la base de données.
   * @throws SQLException si une erreur survient durant l'insetion dans la bd.
   */
  public void create (Connection pConnection) throws SQLException 
  {
    assert getProjet () != null ;
    assert getTacheEntree () != null ;
    assert getTacheSortie () != null ;
    assert getProduit () != null ;
    
    // Prépaation de la requête
   /* String lRequete = "INSERT INTO ART_ARTEFACT (ART_NOM, ART_DESCRIPTION, ART_NOM_FICHIER, ART_PRJ_ID, ART_COL_ID, ART_TAC_ID_ENTREE, ART_TAC_ID_SORTIE, ART_PRD_ID) VALUES ('" ;
    lRequete += getNom () + "', '" ;
    lRequete += getDescription () + "', '" ;
    lRequete += getNomFichier () + "', '" ;
    lRequete += getProjet ().getId () + "', '" ;
    lRequete += getResponsable ().getId () + "', '" ;
    lRequete += getTacheEntree ().getId () + "', '" ;
    lRequete += getTacheSortie ().getId () + "', '" ;
    lRequete += getProduit ().getId () + "') " ;
    
    Statement lRequest = pConnection.createStatement () ;
    lRequest.executeQuery (lRequete) ;
    */
    
    Statement lRequest = pConnection.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE) ;
    ResultSet curseurArtefact = lRequest.executeQuery ("SELECT * FROM ART_ARTEFACT") ;
    curseurArtefact.moveToInsertRow () ;
    curseurArtefact.updateString (2, getNom ()) ;
    curseurArtefact.updateString (3, getDescription ()) ;
    curseurArtefact.updateString (4, getNomFichier ()) ;
    curseurArtefact.updateInt (5, getProjet ().getId ()) ;
    curseurArtefact.updateInt (6, getResponsable ().getId ()) ;
    if (getTacheEntree () != null)
    {
      curseurArtefact.updateInt (7, getTacheEntree ().getId ()) ;
    }
    if (getTacheSortie () != null)
    {
      curseurArtefact.updateInt (8, getTacheSortie ().getId ()) ;
    }
    curseurArtefact.updateInt (9, getProduit ().getId ()) ;
    curseurArtefact.insertRow () ;
    curseurArtefact.close () ;
    //pConnection.commit () ;
    
    // Préparation de la requête permettant d'obtenir l'id de l'artefact
    String lRequete = "SELECT MAX(ART_ID) FROM ART_ARTEFACT" ;
    ResultSet result = lRequest.executeQuery (lRequete) ;
    if (result.next ())
    {
      setId (result.getInt (1)) ;
    }
    result.close () ;
  }
  
  
  /**
   * Mise à jour de l'artefact courant dans la base de données.
   * @param pConnection Connexion avec la base de données.
   * @throws SQLException si une erreur survient durant la mise à jour de la bd.
   */
  public void update (Connection pConnection) throws SQLException
  {
    assert getProjet () != null ;
    assert getTacheSortie () != null ;
    
    int lId = getId () ;
    
    // Préparation de la requête.
    String lRequete = "UPDATE ART_ARTEFACT SET " ;
    lRequete += "ART_NOM = '" + getNom () + "', " ;
    lRequete += "ART_DESCRIPTION = '" + getDescription () + "', " ;
    lRequete += "ART_NOM_FICHIER = '" + getNomFichier () + "', " ;
    lRequete += "ART_PRJ_ID = " + getProjet ().getId () + ", " ;
    lRequete += "ART_COL_ID = " + getResponsable ().getId () + ", " ;
    if (getTacheEntree () != null) 
    {
      lRequete += "ART_TAC_ID_ENTREE = " + getTacheEntree ().getId () + ", " ;
    }
    else
    {
      lRequete += "ART_TAC_ID_ENTREE = NULL , " ;
    }
    lRequete += "ART_TAC_ID_SORTIE = " + getTacheSortie ().getId () + ", " ;
    if (getProduit () != null)
    {
      lRequete += "ART_PRD_ID = " + getProduit ().getId () + " " ;
    }
    else
    {
      lRequete += "ART_PRD_ID = NULL " ;
    }
    lRequete += "WHERE ART_ID = " + lId ;
    
    
    Statement lRequest = pConnection.createStatement () ;
    lRequest.executeUpdate (lRequete) ;
  }
  
  
  /**
   * Récupère le collaborateur responsable de l'artefact.
   * @return Collaborateur responsable de l'artefact.
   */
  public MCollaborateur getResponsable ()
  {
    return mResponsable ;
  }


  /**
   * Associe le collaborateur responsable de l'artefact.
   * @param pResponsable Collaborateur responsable de l'artefact.
   */
  public void setResponsable (MCollaborateur pResponsable)
  {
    mResponsable = pResponsable ;
  }


  /**
   * Récupère la description de l'artefact.
   * @return Description de l'artefact.
   */
  public String getDescription ()
  {
    return mDescription ;
  }


  /**
   * Initialise la description de l'artefact.
   * @param pDescription Description de l'artefact.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }


  /**
   * Récupère l'identifiant de l'artefact.
   * @return Identifiant unique de l'artefact.
   */
  public int getId ()
  {
    return mId ;
  }


  /**
   * Initialise l'identifiant de l'artefact.
   * @param pId Identifiant unique de l'artefact.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }


  /**
   * Récupère le nom de l'artefact.
   * @return Nom de l'artefact.
   */
  public String getNom ()
  {
    return mNom ;
  }


  /**
   * Initialise le nom de l'artefact.
   * @param pNom Nom de l'artefact.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }


  /**
   * Récupère le nom du fichier de l'artefact.
   * @return Nom du fichier de l'artefact.
   */
  public String getNomFichier ()
  {
    return mNomFichier ;
  }


  /**
   * Initialise le nom du fichier de l'artefact.
   * @param pNomFichier Nom du fichier de l'artefact.
   */
  public void setNomFichier (String pNomFichier)
  {
    mNomFichier = pNomFichier ;
  }


  /**
   * Récupère le produit que l'artefact instancie.
   * @return Produit que l'artefact instancie.
   */
  public MProduit getProduit ()
  {
    return mProduit ;
  }


  /**
   * Associe le produit que l'artefact instancie.
   * @param pProduit Produit que l'artefact instancie.
   */
  public void setProduit (MProduit pProduit)
  {
    mProduit = pProduit ;
  }


  /**
   * Récupère le projet pour lequel est réalisé l'artefact.
   * @return Projet pour lequel est réalisé l'artefact.
   */
  public MProjet getProjet ()
  {
    return mProjet ;
  }


  /**
   * Initialise le projet pour lequel est réalisé l'artefact.
   * @param pProjet Projet pour lequel est réalisé l'artefact.
   */
  public void setProjet (MProjet pProjet)
  {
    mProjet = pProjet ;
  }


  /**
   * Récupère la tâche dont est issu l'artefact.
   * @return Tâche dont est issu l'artefact.
   */
  public MTache getTacheEntree ()
  {
    return mTacheEntree ;
  }


  /**
   * Initialise la tâche dont est issu l'artefact.
   * @param pTacheEntree Tâche dont est issu l'artefact.
   */
  public void setTacheEntree (MTache pTacheEntree)
  {
    mTacheEntree = pTacheEntree ;
  }


  /**
   * Récupère la tâche qui nécessite l'artefact.
   * @return Tâche qui nécessite l'artefact.
   */
  public MTache getTacheSortie ()
  {
    return mTacheSortie ;
  }


  /**
   * Initialise la tâche qui nécessite l'artefact.
   * @param pTacheSortie Tâche qui nécessite l'artefact.
   */
  public void setTacheSortie (MTache pTacheSortie)
  {
    mTacheSortie = pTacheSortie ;
  }


  /**
   * Récupère le lien vers le fichier correspondant à l'artefact.
   * @return le lien vers le fichier correspondant à l'artefact si il existe.
   */
  public String getPathFichier ()
  {
    if (mNomFichier!=null)
    {
      return mProjet.getNom()+"/"+mTacheSortie.getIteration().getNumero()+"/"+mProduit.getNom()+"/" ;
    }
    else
    {
      return "Non disponible";
    }
  }
}
