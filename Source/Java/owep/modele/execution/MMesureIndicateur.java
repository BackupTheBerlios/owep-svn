package owep.modele.execution ;

import owep.modele.MModeleBase;


/**
 * Description de la classe.
 */
public class MMesureIndicateur extends MModeleBase
{
  private int            mId ;            // Identifiant de la mesure
  private double         mValeur ;        // Valeur de la mesure
  private String         mCommentaire ;   // commentaire de la mesure
  private MIndicateur    mIndicateur ;    // indicateur associe a la mesure
  private MCollaborateur mCollaborateur ; // collaborateur associe a la mesure
  private MIteration     mIteration ;     // Iteration pour laquelle a été réalisée la mesure


  /**
   * TODO Récupère mCommentaire.
   * @return mCommentaire.
   */
  public String getCommentaire ()
  {
    return mCommentaire ;
  }


  /**
   * TODO Initialise mCommentaire.
   * @param commentaire mCommentaire.
   */
  public void setCommentaire (String pCommentaire)
  {
    mCommentaire = pCommentaire ;
  }


  /**
   * TODO Récupère mValeur.
   * @return mValeur.
   */
  public double getValeur ()
  {
    return mValeur ;
  }


  /**
   * TODO Initialise mValeur.
   * @param valeur mValeur.
   */
  public void setValeur (double pValeur)
  {
    mValeur = pValeur ;
  }


  /**
   * TODO Récupère mCollaborateur.
   * @return mCollaborateur.
   */
  public MCollaborateur getCollaborateur ()
  {
    return mCollaborateur ;
  }


  /**
   * TODO Initialise mCollaborateur.
   * @param collaborateur mCollaborateur.
   */
  public void setCollaborateur (MCollaborateur pCollaborateur)
  {
    mCollaborateur = pCollaborateur ;
  }


  /**
   * TODO Récupère mId.
   * @return mId.
   */
  public int getId ()
  {
    return mId ;
  }


  /**
   * TODO Initialise mId.
   * @param id mId.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }


  /**
   * TODO Récupère mIndicateur.
   * @return mIndicateur.
   */
  public MIndicateur getIndicateur ()
  {
    return mIndicateur ;
  }


  /**
   * TODO Initialise mIndicateur.
   * @param indicateur mIndicateur.
   */
  public void setIndicateur (MIndicateur pIndicateur)
  {
    if(mIndicateur != pIndicateur)
    mIndicateur = pIndicateur ;
    if(!pIndicateur.getListeMesures().contains(this))
      pIndicateur.addMesure(this);
  }
  
  /**
   * TODO Récupère mIteration.
   * @return mIteration.
   */
  public MIteration getIteration ()
  {
    return mIteration ;
  }
  
  /**
   * TODO Initialise mIteration.
   * @param iteration mIteration.
   */
  public void setIteration (MIteration pIteration)
  {
    if(mIteration != pIteration)
    mIteration = pIteration ;
    if(pIteration.getListeMesures() != null && !pIteration.getListeMesures().contains(this)){
      pIteration.addMesure(this);
    }
  }
}