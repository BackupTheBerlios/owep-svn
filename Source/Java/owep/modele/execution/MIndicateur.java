package owep.modele.execution ;


import java.util.ArrayList ;
import java.util.Iterator ;

import owep.modele.MModeleBase ;


/**
 * Un indicateur représente une mesure supplémentaire effectuée sur le projet et/ou l'itération
 * associée.
 */
public class MIndicateur extends MModeleBase
{
  private int       mId ;         // Identifiant de l'indicateur
  private String    mNom ;        // Nom de l'indicateur
  private String    mDescription ; // Description de l'indicateur
  private String    mUnite ;      // Unite de l'indicateur
  private MProjet   mProjet ;     // Projet associé à l'indicateur
  private ArrayList mMesures ;    // Liste des mesures associées a l'indicateur.


  /**
   * Créé une instance vide d'indicateur.
   */
  public MIndicateur ()
  {
    super () ;
    mMesures = new ArrayList () ;
  }

  /**
   * TODO Récupère mDescription.
   * 
   * @return mDescription.
   */
  public String getDescription ()
  {
    return mDescription ;
  }

  /**
   * TODO Initialise mDescription.
   * 
   * @param description mDescription.
   */
  public void setDescription (String pDescription)
  {
    mDescription = pDescription ;
  }

  /**
   * TODO Récupère mId.
   * 
   * @return mId.
   */
  public int getId ()
  {
    return mId ;
  }

  /**
   * TODO Initialise mId.
   * 
   * @param id mId.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }

  /**
   * TODO Récupère mNom.
   * 
   * @return mNom.
   */
  public String getNom ()
  {
    return mNom ;
  }

  /**
   * TODO Initialise mNom.
   * 
   * @param nom mNom.
   */
  public void setNom (String pNom)
  {
    mNom = pNom ;
  }

  /**
   * TODO Récupère mProjet.
   * 
   * @return mProjet.
   */
  public MProjet getProjet ()
  {
    return mProjet ;
  }

  /**
   * TODO Initialise mProjet.
   * 
   * @param projet mProjet.
   */
  public void setProjet (MProjet pProjet)
  {
    if (mProjet != pProjet)
      mProjet = pProjet ;
    if (!pProjet.getListeIndicateurs ().contains (this))
      pProjet.addIndicateur (this) ;
  }

  /**
   * TODO Récupère mUnite.
   * 
   * @return mUnite.
   */
  public String getUnite ()
  {
    return mUnite ;
  }

  /**
   * TODO Initialise mUnite.
   * 
   * @param unite mUnite.
   */
  public void setUnite (String pUnite)
  {
    mUnite = pUnite ;
  }

  /**
   * Récupère la liste des mesures associées a l'indicateur.
   * 
   * @return Liste des mesures associées a l'indicateur.
   */
  public ArrayList getListeMesures ()
  {
    return mMesures ;
  }

  /**
   * Initialise la liste des mesures associées a l'indicateur.
   * 
   * @param pArtefacts Liste des mesures associées a l'indicateur.
   */
  public void setListeMesures (ArrayList pMesures)
  {
    mMesures = pMesures ;
    Iterator it = pMesures.iterator () ;
    while (it.hasNext ())
    {
      MMesureIndicateur lMesure = (MMesureIndicateur) it.next () ;
      lMesure.setIndicateur (this) ;
    }
  }

  /**
   * Ajoute la mesure spécifiée à l'indicateur.
   * 
   * @param pMesure Mesure à ajouter à l'indicateur.
   */
  public void addMesure (MMesureIndicateur pMesure)
  {
    if (!mMesures.contains (pMesure))
      mMesures.add (pMesure) ;
    pMesure.setIndicateur (this) ;
  }
}