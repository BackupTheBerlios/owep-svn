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
  private Date      mDateIdentification ; // Date � laquelle le probleme a �t� identifi�
  private Date      mDateCloture ;        // Date � laquelle le probleme a �t� r�solu
  private ArrayList mTacheResout ;        // Liste des taches r�solvant le probl�me
  private ArrayList mTacheProvoque ;      // Liste des taches provoquant le probl�me


  /**
   * Retourne la tache d'indice pNumTacheProvoque qui a provoqu� le probl�me.
   * 
   * @param Indice d'une tache provoquant le probl�me.
   * @return La tache d�sign� par le param�tre.
   */
  public MTache getTacheProvoque (int pNumTacheProvoque)
  {
    return (MTache) mTacheProvoque.get (pNumTacheProvoque) ;
  }

  /**
   * Ajoute une tache � la liste des taches provoquant le probl�me.
   * 
   * @param Tache provoquant le probl�me.
   */
  public void addTacheProvoque (MTache pTache)
  {
    mTacheProvoque.add (pTache) ;
  }

  /**
   * R�cup�re la liste des taches provoquant le probl�me.
   * 
   * @return Liste des taches provoquant le probl�me.
   */
  public ArrayList getListeTacheProvoque ()
  {
    return mTacheProvoque ;
  }

  /**
   * Initialise la liste des taches provoquant le probl�me.
   * 
   * @param Liste des taches provoquant le probl�me.
   */
  public void setListeTacheProvoque (ArrayList pListeTacheProvoque)
  {
    mTacheProvoque = pListeTacheProvoque ;
  }

  /**
   * Retourne la tache qui resout le probl�me ayant pour index le num�ro pass� en param�tre.
   * 
   * @param Index de la tache que l'on souhaite r�cup�rer.
   * @return Tache correspondant � l'index pass� en param�tre.
   */
  public MTache getTacheResout (int pNumTacheResout)
  {
    return (MTache) mTacheResout.get (pNumTacheResout) ;
  }

  /**
   * Ajoute la tache � la liste des taches qui r�sout le probl�me.
   * 
   * @param Tache � ajouter � la liste des taches qui r�sout le probl�me.
   */
  public void addTacheResout (MTache pTacheResout)
  {
    mTacheResout.add (pTacheResout) ;
  }

  /**
   * R�cup�re la liste des taches qui r�solvent le probl�me.
   * 
   * @return Retourne la valeur de l'attribut tacheResout.
   */
  public ArrayList getListeTacheResout ()
  {
    return mTacheResout ;
  }

  /**
   * Initialise la liste des taches qui r�solvent le probl�me.
   * 
   * @param initialse tacheResout avec pTacheResout.
   */
  public void setListeTacheResout (ArrayList pTacheResout)
  {
    mTacheResout = pTacheResout ;
  }

  /**
   * Cr�� une instance vide de probl�me.
   */
  public MProbleme ()
  {
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
  }

  /**
   * R�cup�re la date de cloture du probl�me.
   * 
   * @return Retourne la valeur de l'attribut dateCloture.
   */
  public Date getDateCloture ()
  {
    return mDateCloture ;
  }

  /**
   * Initialise la date de cloture du probl�me.
   * 
   * @param initialse dateCloture avec pDateCloture.
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
   * @param initialse dateIdentifiaction avec pDateIdentifiaction.
   */
  public void setDateIdentification (Date pDateIdentification)
  {
    mDateIdentification = pDateIdentification ;
  }

  /**
   * R�cup�re la description du probl�me.
   * 
   * @return Retourne la valeur de l'attribut description.
   */
  public String getDescription ()
  {
    return mDescription ;
  }

  /**
   * Initialise la description du probl�me.
   * 
   * @param initialse description avec pDescription.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }

  /**
   * R�cup�re l'�tat du probl�me.
   * 
   * @return Retourne la valeur de l'attribut etat.
   */
  public String getEtat ()
  {
    return mEtat ;
  }

  /**
   * Initialise l'�tat du probl�me.
   * 
   * @param initialse etat avec pEtat.
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
   * 
   * @param initialse id avec pId.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }

  /**
   * R�cup�re le nom du probl�me.
   * 
   * @return Retourne la valeur de l'attribut nom.
   */
  public String getNom ()
  {
    return mNom ;
  }

  /**
   * Initialise le nom du probl�me.
   * 
   * @param initialse nom avec pNom.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }

}