package owep.modele.execution;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import owep.modele.MModeleBase;


/**
 * Description de la classe.
 */
public class MArtefactImprevue extends MModeleBase
{
  public String PATH_ARTEFACT = "Artefacts/"; //Variable global avec le path vers les artefacts (� mettre dans un fichier avec des variables globales)

  private int            mId ;                    // Identifie l'artefact de mani�re unique.
  private String         mNom ;                   // Nom de l'artefact.
  private String         mDescription ;           // Description de l'artefact.
  private String         mNomFichier ;            // Nom du fichier correspondant � l'artefact.
  private MTacheImprevue mTacheImprevueEntree ;   // T�che dont est issu l'artefact.
  private MTacheImprevue mTacheImprevueSortie ;   // T�che qui n�cessite l'artefact.
  private MCollaborateur mResponsable ;           // Collaborateur responsable de l'artefact.
  private MProjet        mProjet ;                // Projet pour lequel est r�alis� l'artefact.


  /**
   * Cr�e une instance vide de MArtefact.
   */
  public MArtefactImprevue ()
  {
  }


  /**
   * Cr�e une instance initialis�e de MArtefact.
   * @param pId Identifiant unique de l'artefact.
   * @param pNom Nom de l'artefact.
   * @param pDescription Description de l'artefact.
   */
  public MArtefactImprevue (int pId, String pNom, String pDescription)
  {
    mId          = pId ;
    mNom         = pNom ;
    mDescription = pDescription ;
  }

  
  /**
   * Insertion de l'artefact courant dans la base de donn�es.
   * @param pConnection Connexion avec la base de donn�es.
   * @throws SQLException si une erreur survient durant l'insetion dans la bd.
   */
  public void create (Connection pConnection) throws SQLException 
  {
    assert getProjet () != null ;
    assert getTacheImprevueEntree () != null ;
    assert getTacheImprevueSortie () != null ;
    
    
    Statement lRequest = pConnection.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE) ;
    ResultSet curseurArtefact = lRequest.executeQuery ("SELECT * FROM ARI_ARTEFACTIMPREVUE") ;
    curseurArtefact.moveToInsertRow () ;
    curseurArtefact.updateString (2, getNom ()) ;
    curseurArtefact.updateString (3, getDescription ()) ;
    curseurArtefact.updateString (4, getNomFichier ()) ;
    curseurArtefact.updateInt (5, getProjet ().getId ()) ;
    curseurArtefact.updateInt (6, getResponsable ().getId ()) ;
    if (getTacheImprevueEntree () != null)
    {
      curseurArtefact.updateInt (7, getTacheImprevueEntree ().getId ()) ;
    }
    if (getTacheImprevueSortie () != null)
    {
      curseurArtefact.updateInt (8, getTacheImprevueSortie ().getId ()) ;
    }
    curseurArtefact.insertRow () ;
    curseurArtefact.close () ;
    
    // Pr�paration de la requ�te permettant d'obtenir l'id de l'artefact
    String lRequete = "SELECT MAX(ARI_ID) FROM ARI_ARTEFACTIMPREVUE" ;
    ResultSet result = lRequest.executeQuery (lRequete) ;
    if (result.next ())
    {
      setId (result.getInt (1)) ;
    }
    result.close () ;
  }
  
  
  /**
   * Mise � jour de l'artefact courant dans la base de donn�es.
   * @param pConnection Connexion avec la base de donn�es.
   * @throws SQLException si une erreur survient durant la mise � jour de la bd.
   */
  public void update (Connection pConnection) throws SQLException
  {
    assert getProjet () != null ;
    assert getTacheImprevueSortie () != null ;
    
    int lId = getId () ;
    
    // Pr�paration de la requ�te.
    String lRequete = "UPDATE ARI_ARTEFACTIMPREVUE SET " ;
    lRequete += "ARI_NOM = '" + getNom () + "', " ;
    lRequete += "ARI_DESCRIPTION = '" + getDescription () + "', " ;
    lRequete += "ARI_NOM_FICHIER = '" + getNomFichier () + "', " ;
    lRequete += "ARI_PRJ_ID = " + getProjet ().getId () + ", " ;
    lRequete += "ARI_COL_ID = " + getResponsable ().getId () + ", " ;
    if (getTacheImprevueEntree () != null) 
    {
      lRequete += "ARI_TIM_ID_ENTREE = " + getTacheImprevueEntree ().getId () + ", " ;
    }
    else
    {
      lRequete += "ARI_TIM_ID_ENTREE = NULL , " ;
    }
    lRequete += "ARI_TIM_ID_SORTIE = " + getTacheImprevueSortie ().getId () + " " ;
    lRequete += "WHERE ARI_ID = " + lId ;
    
    
    Statement lRequest = pConnection.createStatement () ;
    lRequest.executeUpdate (lRequete) ;
  }
  
  
  /**
   * R�cup�re le collaborateur responsable de l'artefact.
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
   * R�cup�re la description de l'artefact.
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
   * R�cup�re l'identifiant de l'artefact.
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
   * R�cup�re le nom de l'artefact.
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
   * R�cup�re le nom du fichier de l'artefact.
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
   * R�cup�re le projet pour lequel est r�alis� l'artefact.
   * @return Projet pour lequel est r�alis� l'artefact.
   */
  public MProjet getProjet ()
  {
    return mProjet ;
  }


  /**
   * Initialise le projet pour lequel est r�alis� l'artefact.
   * @param pProjet Projet pour lequel est r�alis� l'artefact.
   */
  public void setProjet (MProjet pProjet)
  {
    mProjet = pProjet ;
  }


  /**
   * R�cup�re la t�che dont est issu l'artefact.
   * @return T�che dont est issu l'artefact.
   */
  public MTacheImprevue getTacheImprevueEntree ()
  {
    return mTacheImprevueEntree ;
  }


  /**
   * Initialise la t�che dont est issu l'artefact.
   * @param pTacheEntree T�che dont est issu l'artefact.
   */
  public void setTacheImprevueEntree (MTacheImprevue pTacheImprevueEntree)
  {
    mTacheImprevueEntree = pTacheImprevueEntree ;
  }


  /**
   * R�cup�re la t�che qui n�cessite l'artefact.
   * @return T�che qui n�cessite l'artefact.
   */
  public MTacheImprevue getTacheImprevueSortie ()
  {
    return mTacheImprevueSortie ;
  }


  /**
   * Initialise la t�che qui n�cessite l'artefact.
   * @param pTacheSortie T�che qui n�cessite l'artefact.
   */
  public void setTacheImprevueSortie (MTacheImprevue pTacheImprevueSortie)
  {
    mTacheImprevueSortie = pTacheImprevueSortie ;
  }


  /**
   * R�cup�re le lien vers le fichier correspondant � l'artefact.
   * @return le lien vers le fichier correspondant � l'artefact si il existe.
   */
  public String getPathFichier ()
  {
    if (mNomFichier!=null)
    {
      return mProjet.getNom()+"/"+mTacheImprevueEntree.getIteration().getNumero()+"/ArtefactsImprevus/"+mTacheImprevueEntree.getNom()+"/" ;
    }
    else
    {
      return "Non disponible";
    }
  }
}
