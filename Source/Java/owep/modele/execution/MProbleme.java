package owep.modele.execution ;


import java.util.ArrayList ;
import java.util.Date ;

import owep.modele.MModeleBase ;


/**
 * Rpobleme survenue au cours d'une ou plusieurs tache du projet.
 */
public class MProbleme extends MModeleBase
{
  private int       mId ;                 // Identifiant du probleme
  private String    mDescription ;        // Description du probleme
  private String    mEtat ;               // Etat du probleme
  private String    mNom ;                // Nom du probleme
  private Date      mDateIdentification ; // Date à laquelle le probleme a été identifié
  private Date      mDateCloture ;        // Date à laquelle le probleme a été résolu
  private ArrayList mTacheResout ;        // Liste des taches résolvant le problème
  private ArrayList mTacheProvoque ;      // Liste des taches provoquant le problème


  /**
   * Retourne la tache d'indice pNumTacheProvoque qui a provoqué le problème.
   * 
   * @param Indice d'une tache provoquant le problème.
   * @return La tache désigné par le paramètre.
   */
  public MTache getTacheProvoque (int pNumTacheProvoque)
  {
    return (MTache) mTacheProvoque.get (pNumTacheProvoque) ;
  }

  /**
   * Ajoute une tache à la liste des taches provoquant le problème.
   * 
   * @param Tache provoquant le problème.
   */
  public void addTacheProvoque (MTache pTache)
  {
    mTacheProvoque.add (pTache) ;
  }

  /**
   * Récupère la liste des taches provoquant le problème.
   * 
   * @return Liste des taches provoquant le problème.
   */
  public ArrayList getListeTacheProvoque ()
  {
    return mTacheProvoque ;
  }

  /**
   * Initialise la liste des taches provoquant le problème.
   * 
   * @param Liste des taches provoquant le problème.
   */
  public void setListeTacheProvoque (ArrayList pListeTacheProvoque)
  {
    mTacheProvoque = pListeTacheProvoque ;
  }

  /**
   * Retourne la tache qui resout le problème ayant pour index le numèro passé en paramètre.
   * 
   * @param Index de la tache que l'on souhaite récupèrer.
   * @return Tache correspondant à l'index passé en paramètre.
   */
  public MTache getTacheResout (int pNumTacheResout)
  {
    return (MTache) mTacheResout.get (pNumTacheResout) ;
  }

  /**
   * Ajoute la tache à la liste des taches qui résout le problème.
   * 
   * @param Tache à ajouter à la liste des taches qui résout le problème.
   */
  public void addTacheResout (MTache pTacheResout)
  {
    mTacheResout.add (pTacheResout) ;
  }

  /**
   * Récupère la liste des taches qui résolvent le problème.
   * 
   * @return Retourne la valeur de l'attribut tacheResout.
   */
  public ArrayList getListeTacheResout ()
  {
    return mTacheResout ;
  }

  /**
   * Initialise la liste des taches qui résolvent le problème.
   * 
   * @param initialse tacheResout avec pTacheResout.
   */
  public void setListeTacheResout (ArrayList pTacheResout)
  {
    mTacheResout = pTacheResout ;
  }

  /**
   * Créé une instance vide de problème.
   */
  public MProbleme ()
  {
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
  }

  /**
   * Récupère la date de cloture du problème.
   * 
   * @return Retourne la valeur de l'attribut dateCloture.
   */
  public Date getDateCloture ()
  {
    return mDateCloture ;
  }

  /**
   * Initialise la date de cloture du problème.
   * 
   * @param initialse dateCloture avec pDateCloture.
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
   * @param initialse dateIdentifiaction avec pDateIdentifiaction.
   */
  public void setDateIdentification (Date pDateIdentification)
  {
    mDateIdentification = pDateIdentification ;
  }

  /**
   * Récupère la description du problème.
   * 
   * @return Retourne la valeur de l'attribut description.
   */
  public String getDescription ()
  {
    return mDescription ;
  }

  /**
   * Initialise la description du problème.
   * 
   * @param initialse description avec pDescription.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }

  /**
   * Récupère l'état du problème.
   * 
   * @return Retourne la valeur de l'attribut etat.
   */
  public String getEtat ()
  {
    return mEtat ;
  }

  /**
   * Initialise l'état du problème.
   * 
   * @param initialse etat avec pEtat.
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
   * 
   * @param initialse id avec pId.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }

  /**
   * Récupère le nom du problème.
   * 
   * @return Retourne la valeur de l'attribut nom.
   */
  public String getNom ()
  {
    return mNom ;
  }

  /**
   * Initialise le nom du problème.
   * 
   * @param initialse nom avec pNom.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }

}