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
  private Date      mDateIdentification ;    // Date à laquelle le probleme a été identifié.
  private Date      mDateCloture ;           // Date à laquelle le probleme a été résolu.
  private ArrayList mTacheResout ;           // Liste des taches résolvant le problème.
  private ArrayList mTacheProvoque ;         // Liste des taches provoquant le problème.
  private ArrayList mTacheImprevueResout ;   // Liste des taches imprévues résolvant le problème.
  private ArrayList mTacheImprevueProvoque ; // Liste des taches imprévues provoquant le problème.


  /**
   * Créé une instance vide de problème.
   */
  public MProbleme ()
  {
    mTacheResout   = new ArrayList () ;
    mTacheProvoque = new ArrayList () ;
    mTacheImprevueResout   = new ArrayList () ;
    mTacheImprevueProvoque = new ArrayList () ;
  }


  /**
   * Créé une instance de problème et initialise les attributs description, état, nom et date
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
   * Retourne le nombre de tâches ayant provoqué le problème.
   * @return Nombre de tâches ayant provoqué le problème.
   */
  public int getNbTachesProvoque ()
  {
    return mTacheProvoque.size () ;
  }


  /**
   * Retourne la tâche d'indice pNumTacheProvoque qui a provoqué le problème.
   * @param pNumTacheProvoque Indice d'une tache provoquant le problème.
   * @return La tâche désigné par le paramètre.
   */
  public MTache getTacheProvoque (int pNumTacheProvoque)
  {
    return (MTache) mTacheProvoque.get (pNumTacheProvoque) ;
  }


  /**
   * Ajoute une tâche à la liste des tâches provoquant le problème.
   * @param pTache Tâche provoquant le problème.
   */
  public void addTacheProvoque (MTache pTache)
  {
    mTacheProvoque.add (pTache) ;
  }


  /**
   * Supprime une tâche de la liste des tâches provoquant le problème.
   * @param pTache Tâche provoquant le problème.
   */
  public void supprimeTacheProvoque (MTache pTache)
  {
    mTacheProvoque.remove (pTache) ;
  }


  /**
   * Récupère la liste des tâches provoquant le problème.
   * @return Liste des tâches provoquant le problème.
   */
  public ArrayList getListeTacheProvoque ()
  {
    return mTacheProvoque ;
  }


  /**
   * Initialise la liste des tâches provoquant le problème.
   * @param pListeTacheProvoque Liste des tâches provoquant le problème.
   */
  public void setListeTacheProvoque (ArrayList pListeTacheProvoque)
  {
    mTacheProvoque = pListeTacheProvoque ;
  }


  /**
   * Retourne le nombre de tâches résolvant le problème.
   * @return Nombre de tâches résolvant le problème.
   */
  public int getNbTachesResout ()
  {
    return mTacheResout.size () ;
  }


  /**
   * Retourne la tâche qui resout le problème ayant pour index le numèro passé en paramètre.
   * @param pNumTacheResout Index de la tâche que l'on souhaite récupèrer.
   * @return Tâche correspondant à l'index passé en paramètre.
   */
  public MTache getTacheResout (int pNumTacheResout)
  {
    return (MTache) mTacheResout.get (pNumTacheResout) ;
  }


  /**
   * Ajoute la tâche à la liste des taches qui résout le problème.
   * @param pTacheResout Tâche qui résout le problème.
   */
  public void addTacheResout (MTache pTacheResout)
  {
    mTacheResout.add (pTacheResout) ;
  }


  /**
   * Supprime la tâche de la liste des taches qui résout le problème.
   * @param pTacheResout Tâche qui résout le problème.
   */
  public void supprimeTacheResout (MTache pTacheResout)
  {
    mTacheResout.remove (pTacheResout) ;
  }


  /**
   * Récupère la liste des taches qui résolvent le problème.
   * @return Retourne la valeur de l'attribut tacheResout.
   */
  public ArrayList getListeTacheResout ()
  {
    return mTacheResout ;
  }


  /**
   * Initialise la liste des taches qui résolvent le problème.
   * 
   * @param pTacheResout Initialise tacheResout avec pTacheResout.
   */
  public void setListeTacheResout (ArrayList pTacheResout)
  {
    mTacheResout = pTacheResout ;
  }


  /**
   * Retourne le nombre de tâches imprévues ayant provoqué le problème.
   * @return Nombre de tâches imprévues ayant provoqué le problème.
   */
  public int getNbTachesImprevuesProvoque ()
  {
    return mTacheImprevueProvoque.size () ;
  }


  /**
   * Retourne la tâche imprévue d'indice pIndice qui a provoqué le problème.
   * @param pIndice Indice d'une tâche imprévue provoquant le problème.
   * @return La tâche imprévue désigné par le paramètre.
   */
  public MTacheImprevue getTacheImprevueProvoque (int pIndice)
  {
    return (MTacheImprevue) mTacheImprevueProvoque.get (pIndice) ;
  }


  /**
   * Ajoute une tâche imprévue à la liste des tâches provoquant le problème.
   * @param pTache Tâche imprévue provoquant le problème.
   */
  public void addTacheImprevueProvoque (MTacheImprevue pTache)
  {
    mTacheImprevueProvoque.add (pTache) ;
  }


  /**
   * Supprime une tâche de la liste des tâches provoquant le problème.
   * @param pTache Tâche provoquant le problème.
   */
  public void supprimeTacheImprevueProvoque (MTacheImprevue pTache)
  {
    mTacheImprevueProvoque.remove (pTache) ;
  }


  /**
   * Récupère la liste des taches imprévues résolvant le problème.
   * @return Liste des taches imprévues résolvant le problème.
   */
  public ArrayList getListeTacheImprevueProvoque ()
  {
    return mTacheImprevueProvoque ;
  }


  /**
   * Initialise la liste des taches imprévues résolvant le problème.
   * @param pTacheImprevueProvoque Liste des taches imprévues résolvant le problème.
   */
  public void setListeTacheImprevueProvoque (ArrayList pTacheImprevueProvoque)
  {
    mTacheImprevueProvoque = pTacheImprevueProvoque ;
  }


  /**
   * Retourne le nombre de tâches imprévues résolvant le problème.
   * @return Nombre de tâches imprévues résolvant le problème.
   */
  public int getNbTachesImprevuesResout ()
  {
    return mTacheImprevueResout.size () ;
  }


  /**
   * Retourne la tâche imprévue qui resout le problème ayant pour index le numèro passé en paramètre.
   * @param pIndice Indice de la tâche que l'on souhaite récupèrer.
   * @return Tâche correspondant à l'index passé en paramètre.
   */
  public MTacheImprevue getTacheImprevueResout (int pIndice)
  {
    return (MTacheImprevue) mTacheImprevueResout.get (pIndice) ;
  }


  /**
   * Ajoute la tâche imprévue à la liste des taches qui résout le problème.
   * @param pTacheImprevueResout Tâche imprévue qui résout le problème.
   */
  public void addTacheImprevueResout (MTacheImprevue pTacheImprevueResout)
  {
    mTacheImprevueResout.add (pTacheImprevueResout) ;
  }


  /**
   * Supprime la tâche imprévue de la liste des taches qui résout le problème.
   * @param pTacheImprevueResout Tâche imprévue qui résout le problème.
   */
  public void supprimeTacheImprevueResout (MTacheImprevue pTacheImprevueResout)
  {
    mTacheImprevueResout.remove (pTacheImprevueResout) ;
  }


  /**
   * Récupère la liste des taches imprévues provoquant le problème.
   * @return Liste des taches imprévues provoquant le problème.
   */
  public ArrayList getListeTacheImprevueResout ()
  {
    return mTacheImprevueResout ;
  }
 

  /**
   * Initialise la liste des taches imprévues provoquant le problème.
   * @param pTacheImprevueResout Liste des taches imprévues provoquant le problème.
   */
  public void setListeTacheImprevueResout (ArrayList pTacheImprevueResout)
  {
    mTacheImprevueResout = pTacheImprevueResout ;
  }


  /**
   * Récupère la date de cloture du problème.
   * @return Retourne la valeur de l'attribut dateCloture.
   */
  public Date getDateCloture ()
  {
    return mDateCloture ;
  }


  /**
   * Initialise la date de cloture du problème.
   * @param pDateCloture Initialise dateCloture avec pDateCloture.
   */
  public void setDateCloture (Date pDateCloture)
  {
    mDateCloture = pDateCloture ;
  }


  /**
   * Récupère la date d'identification du problème.
   * 
   * @return Retourne la valeur de l'attribut dateIdentifiaction.
   */
  public Date getDateIdentification ()
  {
    return mDateIdentification ;
  }


  /**
   * Initialise la date d'identification du problème.
   * 
   * @param pDateIdentification Initialise dateIdentifiaction avec pDateIdentifiaction.
   */
  public void setDateIdentification (Date pDateIdentification)
  {
    mDateIdentification = pDateIdentification ;
  }


  /**
   * Récupère la description du problème.
   * @return Retourne la valeur de l'attribut description.
   */
  public String getDescription ()
  {
    return mDescription ;
  }


  /**
   * Initialise la description du problème.
   * @param pDescription Initialise description avec pDescription.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }


  /**
   * Récupère l'état du problème.
   * @return Etat du problème.
   */
  public String getEtat ()
  {
    return mEtat ;
  }


  /**
   * Met à jour l'état du problème.
   * @param pEtat Nouvel état du problème.
   */
  public void setEtat (String pEtat)
  {
    mEtat = pEtat ;
  }


  /**
   * Récupère l'identifiant du problème.
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
   * Récupère le nom du problème.
   * @return Retourne la valeur de l'attribut nom.
   */
  public String getNom ()
  {
    return mNom ;
  }


  /**
   * Initialise le nom du problème.
   * 
   * @param pNom Initialise nom avec pNom.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }
}