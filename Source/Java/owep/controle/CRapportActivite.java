package owep.controle ;


import java.text.SimpleDateFormat ;
import javax.servlet.ServletException ;
import org.exolab.castor.jdo.OQLQuery ;
import org.exolab.castor.jdo.PersistenceException ;
import org.exolab.castor.jdo.QueryResults ;
import owep.modele.execution.MCollaborateur ;
import owep.modele.execution.MTache ;


/**
 * Controleur pour la saisie du rapport d'activité de l'utilisateur.
 */
public class CRapportActivite extends CControleurBase
{
  private int mIterationNum ;             // Numéro d'itération dont on liste les tâches
  private MCollaborateur mCollaborateur ; // Données du collaborateur
  
  
  /**
   * Récupère les données nécessaire au controleur dans la base de données. 
   * @throws ServletException Si une erreur survient durant la connexion
   * @see owep.controle.CControleurBase#initialiserBaseDonnees()
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    OQLQuery       lRequete ;       // Requête à réaliser sur la base
    QueryResults   lResultat ;      // Résultat de la requête sur la base
    
    try
    {
      getBaseDonnees ().begin () ;
      
      // Récupère la liste des tâches du collaborateur.
      lRequete = getBaseDonnees ().getOQLQuery ("select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mId = $1") ;
      lRequete.bind (1) ;
      lResultat      = lRequete.execute () ;
      mCollaborateur = (MCollaborateur) lResultat.next () ;
      
      getBaseDonnees ().commit () ;
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
  }
  
  
  /**
   * Initialise le controleur et récupère les paramètres. 
   * @throws ServletException Si une erreur sur les paramètres survient
   * @see owep.controle.CControleurBase#initialiserParametres()
   */
  public void initialiserParametres () throws ServletException
  {
    assert mCollaborateur != null ;
    
    // Récupère le numéro d'itération.
    if (getRequete ().getParameter (CConstante.PAR_ITERATION) == null)
    {
      // TODO Requête recup it en cours.
    }
    else
    {
      mIterationNum = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_ITERATION)) ;
    }
    mIterationNum = 1 ;
    
    
    // Récupère les données transmises par le collaborateur.
    if (getRequete ().getParameter (CConstante.PAR_SUBMIT) != null)
    {
      // Récupère le rapport d'activités pour chaque tâche du collaborateur.
      for (int i = 0; i < mCollaborateur.getNbTache (); i ++ )
      {
        MTache lTache = mCollaborateur.getTache (i) ;
        
        // Initialise les identifiants des paramètres
        int lTacheId            = lTache.getId () ;
        String lTempsPasseId    = CConstante.PAR_TEMPSPASSE      + lTacheId ;
        String lResteAPasserId  = CConstante.PAR_RESTEAPASSER    + lTacheId ;
        String lDateDebutReelId = CConstante.PAR_DATEDEBUTREELLE + lTacheId ;
        String lDateReestimeeId = CConstante.PAR_DATEFINREELLE   + lTacheId ;
        
        // Récupére les valeurs entrées par le collaborateur et met à jour la tâche dans le modèle .
        lTache.setTempsPasse   (Double.parseDouble (getRequete ().getParameter (lTempsPasseId))) ;
        lTache.setResteAPasser (Double.parseDouble (getRequete ().getParameter (lResteAPasserId))) ;
        try
        {
          if (! getRequete ().getParameter (lDateDebutReelId).equals (""))
          {
            lTache.setDateDebutReelle (new SimpleDateFormat ("yyyy-MM-dd").parse (getRequete ().getParameter (lDateDebutReelId))) ;
          }
          else
          {
            lTache.setDateDebutReelle (null) ;
          }
          if (! getRequete ().getParameter (lDateReestimeeId).equals (""))
          {
            lTache.setDateFinReelle (new SimpleDateFormat ("yyyy-MM-dd").parse (getRequete ().getParameter (lDateReestimeeId))) ;
          }
          else
          {
            lTache.setDateFinReelle (null) ;
          }
        }
        catch (Exception eException)
        {
          eException.printStackTrace () ;
          throw new ServletException (CConstante.EXC_CHARGEMENT) ;
        }
      }
    }
  }
  
  
  /**
   * Redirige vers la JSP d'affichage du rapport d'activité si aucune donnée n'est postée ou valide
   * les données dans la base sinon. 
   * @return URL de la page vers laquelle doit être redirigé le client.
   * @throws ServletException Si une erreur survient dans le controleur
   * @see owep.controle.CControleurBase#traiter()
   */
  public String traiter () throws ServletException
  {
    try
    {
      if (getRequete ().getParameter (CConstante.PAR_SUBMIT) != null)
      {
        // Met à jour la base de données avec les informations fournies par le collaborateur.
        getBaseDonnees ().begin () ;
        for (int i = 0; i < mCollaborateur.getNbTache (); i ++)
        {
          getBaseDonnees ().update (mCollaborateur.getTache (i)) ;
        }
        getBaseDonnees ().commit () ;
        
        return "..\\Tache\\ListeTacheVisu" ;
      }
      else
      {
        // Affiche le formulaire de saisie du rapport d'activité.
        getRequete ().setAttribute (CConstante.PAR_COLLABORATEUR, mCollaborateur) ;
        getRequete ().setAttribute (CConstante.PAR_ITERATION,     new Integer (mIterationNum)) ;
        return "..\\JSP\\Tache\\TRapportActivite.jsp" ;
      }
    }
    catch (Exception eException)
    {
      eException.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
    // Ferme la connexion à la base de données.
    finally
    {
      try
      {
        getBaseDonnees ().close () ;
      }
      catch (PersistenceException eException)
      {
        eException.printStackTrace () ;
        throw new ServletException (CConstante.EXC_DECONNEXION) ;
      }
    }
  }
}