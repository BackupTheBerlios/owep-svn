package owep.modele.execution ;


import java.sql.Connection ;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList ;
import java.util.Date ;
import org.exolab.castor.jdo.ClassNotPersistenceCapableException;
import org.exolab.castor.jdo.Database;
import org.exolab.castor.jdo.DuplicateIdentityException;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.jdo.TransactionNotInProgressException;
import com.mysql.jdbc.Driver;

import owep.infrastructure.localisation.LocalisateurIdentifiant;
import owep.modele.MModeleBase ;


/**
 * Rpobleme survenue au cours d'une ou plusieurs tache du projet.
 */
public class MProbleme extends MModeleBase
{
  private int       mId ;                    // Identifiant du probleme.
  private String    mNom ;                   // Nom du probleme.
  private String    mDescription ;           // Description du probleme.
  private String    mEtat ;                  // Etat du probleme.
  private Date      mDateIdentification ;    // Date � laquelle le probleme a �t� identifi�.
  private Date      mDateCloture ;           // Date � laquelle le probleme a �t� r�solu.
  private ArrayList mTacheResout ;           // Liste des taches r�solvant le probl�me.
  private ArrayList mTacheProvoque ;         // Liste des taches provoquant le probl�me.
  private ArrayList mTacheImprevueResout ;   // Liste des taches impr�vues r�solvant le probl�me.
  private ArrayList mTacheImprevueProvoque ; // Liste des taches impr�vues provoquant le probl�me.


  /**
   * Cr�� une instance vide de probl�me.
   */
  public MProbleme ()
  {
    mTacheResout   = new ArrayList () ;
    mTacheProvoque = new ArrayList () ;
    mTacheImprevueResout   = new ArrayList () ;
    mTacheImprevueProvoque = new ArrayList () ;
  }


  /**
   * Cr�� une instance de probl�me et initialise les attributs description, �tat, nom et date
   * d'identification.
   * 
   * @param pDescription
   * @param pEtat
   * @param pNom
   * @param pDateIdentifiaction
   */
  public MProbleme (String pDescription, String pEtat, String pNom, Date pDateIdentifiaction)
  {
    mDescription = pDescription ;
    mEtat = pEtat ;
    mNom = pNom ;
    mDateIdentification = pDateIdentifiaction ;
    
    mTacheResout           = new ArrayList () ;
    mTacheProvoque         = new ArrayList () ;
    mTacheImprevueResout   = new ArrayList () ;
    mTacheImprevueProvoque = new ArrayList () ;
  }


  /**
   * Retourne le nombre de t�ches ayant provoqu� le probl�me.
   * @return Nombre de t�ches ayant provoqu� le probl�me.
   */
  public int getNbTachesProvoque ()
  {
    return mTacheProvoque.size () ;
  }


  /**
   * Retourne la t�che d'indice pNumTacheProvoque qui a provoqu� le probl�me.
   * @param pNumTacheProvoque Indice d'une tache provoquant le probl�me.
   * @return La t�che d�sign� par le param�tre.
   */
  public MTache getTacheProvoque (int pNumTacheProvoque)
  {
    return (MTache) mTacheProvoque.get (pNumTacheProvoque) ;
  }


  /**
   * Ajoute une t�che � la liste des t�ches provoquant le probl�me.
   * @param pTache T�che provoquant le probl�me.
   */
  public void addTacheProvoque (MTache pTache)
  {
    mTacheProvoque.add (pTache) ;
  }


  /**
   * Supprime une t�che de la liste des t�ches provoquant le probl�me.
   * @param pTache T�che provoquant le probl�me.
   */
  public void supprimeTacheProvoque (MTache pTache)
  {
    mTacheProvoque.remove (pTache) ;
  }


  /**
   * R�cup�re la liste des t�ches provoquant le probl�me.
   * @return Liste des t�ches provoquant le probl�me.
   */
  public ArrayList getListeTacheProvoque ()
  {
    return mTacheProvoque ;
  }


  /**
   * Initialise la liste des t�ches provoquant le probl�me.
   * @param pListeTacheProvoque Liste des t�ches provoquant le probl�me.
   */
  public void setListeTacheProvoque (ArrayList pListeTacheProvoque)
  {
    mTacheProvoque = pListeTacheProvoque ;
  }


  /**
   * Retourne le nombre de t�ches r�solvant le probl�me.
   * @return Nombre de t�ches r�solvant le probl�me.
   */
  public int getNbTachesResout ()
  {
    return mTacheResout.size () ;
  }


  /**
   * Retourne la t�che qui resout le probl�me ayant pour index le num�ro pass� en param�tre.
   * @param pNumTacheResout Index de la t�che que l'on souhaite r�cup�rer.
   * @return T�che correspondant � l'index pass� en param�tre.
   */
  public MTache getTacheResout (int pNumTacheResout)
  {
    return (MTache) mTacheResout.get (pNumTacheResout) ;
  }


  /**
   * Ajoute la t�che � la liste des taches qui r�sout le probl�me.
   * @param pTacheResout T�che qui r�sout le probl�me.
   */
  public void addTacheResout (MTache pTacheResout)
  {
    mTacheResout.add (pTacheResout) ;
  }


  /**
   * Supprime la t�che de la liste des taches qui r�sout le probl�me.
   * @param pTacheResout T�che qui r�sout le probl�me.
   */
  public void supprimeTacheResout (MTache pTacheResout)
  {
    mTacheResout.remove (pTacheResout) ;
  }


  /**
   * R�cup�re la liste des taches qui r�solvent le probl�me.
   * @return Retourne la valeur de l'attribut tacheResout.
   */
  public ArrayList getListeTacheResout ()
  {
    return mTacheResout ;
  }


  /**
   * Initialise la liste des taches qui r�solvent le probl�me.
   * 
   * @param pTacheResout Initialise tacheResout avec pTacheResout.
   */
  public void setListeTacheResout (ArrayList pTacheResout)
  {
    mTacheResout = pTacheResout ;
  }


  /**
   * Retourne le nombre de t�ches impr�vues ayant provoqu� le probl�me.
   * @return Nombre de t�ches impr�vues ayant provoqu� le probl�me.
   */
  public int getNbTachesImprevuesProvoque ()
  {
    return mTacheImprevueProvoque.size () ;
  }


  /**
   * Retourne la t�che impr�vue d'indice pIndice qui a provoqu� le probl�me.
   * @param pIndice Indice d'une t�che impr�vue provoquant le probl�me.
   * @return La t�che impr�vue d�sign� par le param�tre.
   */
  public MTacheImprevue getTacheImprevueProvoque (int pIndice)
  {
    return (MTacheImprevue) mTacheImprevueProvoque.get (pIndice) ;
  }


  /**
   * Ajoute une t�che impr�vue � la liste des t�ches provoquant le probl�me.
   * @param pTache T�che impr�vue provoquant le probl�me.
   */
  public void addTacheImprevueProvoque (MTacheImprevue pTache)
  {
    mTacheImprevueProvoque.add (pTache) ;
  }


  /**
   * Supprime une t�che de la liste des t�ches provoquant le probl�me.
   * @param pTache T�che provoquant le probl�me.
   */
  public void supprimeTacheImprevueProvoque (MTacheImprevue pTache)
  {
    mTacheImprevueProvoque.remove (pTache) ;
  }


  /**
   * R�cup�re la liste des taches impr�vues r�solvant le probl�me.
   * @return Liste des taches impr�vues r�solvant le probl�me.
   */
  public ArrayList getListeTacheImprevueProvoque ()
  {
    return mTacheImprevueProvoque ;
  }


  /**
   * Initialise la liste des taches impr�vues r�solvant le probl�me.
   * @param pTacheImprevueProvoque Liste des taches impr�vues r�solvant le probl�me.
   */
  public void setListeTacheImprevueProvoque (ArrayList pTacheImprevueProvoque)
  {
    mTacheImprevueProvoque = pTacheImprevueProvoque ;
  }


  /**
   * Retourne le nombre de t�ches impr�vues r�solvant le probl�me.
   * @return Nombre de t�ches impr�vues r�solvant le probl�me.
   */
  public int getNbTachesImprevuesResout ()
  {
    return mTacheImprevueResout.size () ;
  }


  /**
   * Retourne la t�che impr�vue qui resout le probl�me ayant pour index le num�ro pass� en param�tre.
   * @param pIndice Indice de la t�che que l'on souhaite r�cup�rer.
   * @return T�che correspondant � l'index pass� en param�tre.
   */
  public MTacheImprevue getTacheImprevueResout (int pIndice)
  {
    return (MTacheImprevue) mTacheImprevueResout.get (pIndice) ;
  }


  /**
   * Ajoute la t�che impr�vue � la liste des taches qui r�sout le probl�me.
   * @param pTacheImprevueResout T�che impr�vue qui r�sout le probl�me.
   */
  public void addTacheImprevueResout (MTacheImprevue pTacheImprevueResout)
  {
    mTacheImprevueResout.add (pTacheImprevueResout) ;
  }


  /**
   * Supprime la t�che impr�vue de la liste des taches qui r�sout le probl�me.
   * @param pTacheImprevueResout T�che impr�vue qui r�sout le probl�me.
   */
  public void supprimeTacheImprevueResout (MTacheImprevue pTacheImprevueResout)
  {
    mTacheImprevueResout.remove (pTacheImprevueResout) ;
  }


  /**
   * R�cup�re la liste des taches impr�vues provoquant le probl�me.
   * @return Liste des taches impr�vues provoquant le probl�me.
   */
  public ArrayList getListeTacheImprevueResout ()
  {
    return mTacheImprevueResout ;
  }
 

  /**
   * Initialise la liste des taches impr�vues provoquant le probl�me.
   * @param pTacheImprevueResout Liste des taches impr�vues provoquant le probl�me.
   */
  public void setListeTacheImprevueResout (ArrayList pTacheImprevueResout)
  {
    mTacheImprevueResout = pTacheImprevueResout ;
  }


  /**
   * R�cup�re la date de cloture du probl�me.
   * @return Retourne la valeur de l'attribut dateCloture.
   */
  public Date getDateCloture ()
  {
    return mDateCloture ;
  }


  /**
   * Initialise la date de cloture du probl�me.
   * @param pDateCloture Initialise dateCloture avec pDateCloture.
   */
  public void setDateCloture (Date pDateCloture)
  {
    mDateCloture = pDateCloture ;
  }


  /**
   * R�cup�re la date d'identification du probl�me.
   * 
   * @return Retourne la valeur de l'attribut dateIdentifiaction.
   */
  public Date getDateIdentification ()
  {
    return mDateIdentification ;
  }


  /**
   * Initialise la date d'identification du probl�me.
   * 
   * @param pDateIdentification Initialise dateIdentifiaction avec pDateIdentifiaction.
   */
  public void setDateIdentification (Date pDateIdentification)
  {
    mDateIdentification = pDateIdentification ;
  }


  /**
   * R�cup�re la description du probl�me.
   * @return Retourne la valeur de l'attribut description.
   */
  public String getDescription ()
  {
    return mDescription ;
  }


  /**
   * Initialise la description du probl�me.
   * @param pDescription Initialise description avec pDescription.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }


  /**
   * R�cup�re l'�tat du probl�me.
   * @return Etat du probl�me.
   */
  public String getEtat ()
  {
    return mEtat ;
  }


  /**
   * Met � jour l'�tat du probl�me.
   * @param pEtat Nouvel �tat du probl�me.
   */
  public void setEtat (String pEtat)
  {
    mEtat = pEtat ;
  }


  /**
   * R�cup�re l'identifiant du probl�me.
   * 
   * @return Retourne la valeur de l'attribut id.
   */
  public int getId ()
  {
    return mId ;
  }


  /**
   * Initialise l'identifiant du probleme.
   * @param pId Initialise id avec pId.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }


  /**
   * R�cup�re le nom du probl�me.
   * @return Retourne la valeur de l'attribut nom.
   */
  public String getNom ()
  {
    return mNom ;
  }


  /**
   * Initialise le nom du probl�me.
   * 
   * @param pNom Initialise nom avec pNom.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }
}