package owep.infrastructure ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import owep.modele.configuration.MConfigurationSite;
import owep.modele.execution.MCollaborateur;
import owep.modele.execution.MIteration;
import owep.modele.execution.MProjet;

/**
 * Permet le passage de variable tout au long d'une session utilisateur
 */
public class Session
{
  private MCollaborateur mCollaborateur = null;  // Collaborateur connecté
  private MProjet mProjet = null; // Projet ouvert par le collaborateur connecté
  private ArrayList mListeProjetPossible; // Liste des projets concernant l'utilisateur connecté
  private MIteration mIteration = null; // itération ouverte par le collaborateur connecté
  private ResourceBundle messages = null; // resource bundle
  private MConfigurationSite mConfiguration = null; // Tous les éléments de configuration
  private String URLPagePrecedente = null ; //conserve l'URL de la page précédente pour pouvoire y retourner
  private int idCollabAVisualiser ; // identifiant du collaborateur dont on veut visualiser la liste des taches
  private int mIdCollaborateur = 0;
  private int mIdProjet = 0;
  private int mIdIteration = 0;
  private HashMap mListeIdNomProjetPossible = null; // key : idProjet / value : nomProjet 
  private boolean mEstChefProjet = false;
  private boolean mEstSuperviseur = false;
  
  /**
   * TODO Récupère idCollabAVisualiser.
   * 
   * @return idCollabAVisualiser.
   */
  public int getIdCollabAVisualiser ()
  {
    return idCollabAVisualiser ;
  }

  /**
   * TODO Initialise idCollabAVisualiser.
   * 
   * @param idCollabAVisualiser idCollabAVisualiser.
   */
  public void setIdCollabAVisualiser (int mIdCollabAVisualiser)
  {
    this.idCollabAVisualiser = mIdCollabAVisualiser ;
  }

  /**
   * Ouvre la session pour le collaborateur passé en paramètre et initialise les variables.
   * 
   * @param Collaborateur connecté.
   */
  public void ouvertureSession (MCollaborateur pCollaborateur)
  {
    // TODO suppr
    mCollaborateur = pCollaborateur ;
    mIdCollaborateur = pCollaborateur.getId();

    if (mListeProjetPossible == null)
      mListeProjetPossible = new ArrayList () ;
  }
  public void ouvertreSession(int pIdCollaborateur){
    mIdCollaborateur = pIdCollaborateur;
    
    if(mListeIdNomProjetPossible == null)
      mListeIdNomProjetPossible = new HashMap();
  }

  /**
   * Retourne le collaborateur qui a ouvert la session.
   * 
   * @return Collaborateur connecté.
   */
  public MCollaborateur getCollaborateur ()
  {
    // TODO suppr
    return mCollaborateur ;
  }
  public int getIdCollaborateur(){
    return mIdCollaborateur;
  }

  /**
   * Retourne le projet ouvert par le collaborateur connecté.
   * 
   * @return Projet ouvert. Retourne null si aucun projet n'est ouvert.
   */
  public MProjet getProjet ()
  {
    // TODO suppr
    return mProjet ;
  }
  public int getIdProjet()
  {
    return mIdProjet;
  }

  /**
   * Ouvre le projet passé en paramètre.
   * 
   * @param Projet qui doit ouvert.
   */
  public void ouvrirProjet (MProjet pProjet)
  {
    // TODO suppr
    mProjet = pProjet ;
    mIdProjet = pProjet.getId();
  }
  public void ouvrirProjet(int pIdProjet)
  {
    mIdProjet = pIdProjet;
  }

  /**
   * Ferme le projet ouvert
   */
  public void fermerProjet ()
  {
    mProjet = null ;
    mIdProjet = 0;
  }

  public void setProjet (MProjet pProjet)
  {
    // TODO suppr
    mProjet = pProjet ;
    mIdProjet = pProjet.getId();
  }
  public void setIdProjet(int pIdProjet)
  {
    mIdProjet = pIdProjet;
  }

  /**
   * Retourne la liste des projets sur lesquels le collaborateur peut travailler.
   * 
   * @return Liste des projets pouvant être ouvert par le collaborateur.
   */
  public ArrayList getListProjetPossible ()
  {
    // TODO suppr
    return mListeProjetPossible ;
  }

  /**
   * Initialise la liste des projets pouvant être ouvert par le collaborateur.
   * 
   * @param Liste des projets pouvant être ouvert par le collaborateur.
   */
  public void setListProjetPossible (ArrayList pListProjetPossible)
  {
    // TODO suppr
    mListeProjetPossible = pListProjetPossible ;
  }

  public MIteration getIteration ()
  {
    // TODO suppr
    return mIteration ;
  }
  public int getIdIteration(){
    return mIdIteration;
  }

  public void setIteration (MIteration pIteration)
  {
    // TODO suppr
    mIteration = pIteration ;
    if(pIteration != null)
    mIdIteration = pIteration.getId();
  }
  public void setIdIteration(int pIdIteration)
  {
    mIdIteration = pIdIteration;
  }

  /**
   * @return Retourne la valeur de l'attribut messages.
   */
  public ResourceBundle getMessages ()
  {
    return messages ;
  }

  /**
   * @param initialse messages avec pMessages.
   */
  public void setMessages (ResourceBundle pMessages)
  {
    messages = pMessages ;
  }

  /**
   * @return Retourne la valeur de l'attribut cofiguration.
   */
  public MConfigurationSite getConfiguration ()
  {
    return mConfiguration ;
  }

  /**
   * @param initialse cofiguration avec pCofiguration.
   */
  public void setConfiguration (MConfigurationSite pCofiguration)
  {
    mConfiguration = pCofiguration ;
  }

  /**
   * @return Retourne la valeur de l'attribut uRLPagePrecedente.
   */
  public String getURLPagePrecedente ()
  {
    return URLPagePrecedente ;
  }

  /**
   * @param initialse uRLPagePrecedente avec pPagePrecedente.
   */
  public void setURLPagePrecedente (String pPagePrecedente)
  {
    URLPagePrecedente = pPagePrecedente ;
  }
  
  /**
   * @param initialse idCollaborateur avec pIdCollaborateur.
   */
  public void setIdCollaborateur (int pIdCollaborateur)
  {
    mIdCollaborateur = pIdCollaborateur ;
  }
  
  /**
   * @return Retourne la valeur de l'attribut estChefProjet.
   */
  public boolean isEstChefProjet ()
  {
    return mEstChefProjet ;
  }
  /**
   * @param initialse estChefProjet avec pEstChefProjet.
   */
  public void setEstChefProjet (boolean pEstChefProjet)
  {
    mEstChefProjet = pEstChefProjet ;
  }
  /**
   * @return Retourne la valeur de l'attribut estSuperviseur.
   */
  public boolean isEstSuperviseur ()
  {
    return mEstSuperviseur ;
  }
  /**
   * @param initialse estSuperviseur avec pEstSuperviseur.
   */
  public void setEstSuperviseur (boolean pEstSuperviseur)
  {
    mEstSuperviseur = pEstSuperviseur ;
  }
}