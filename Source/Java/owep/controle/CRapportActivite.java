package owep.controle ;


import java.text.SimpleDateFormat ;
import javax.servlet.ServletException ;
import org.exolab.castor.jdo.OQLQuery ;
import org.exolab.castor.jdo.PersistenceException ;
import org.exolab.castor.jdo.QueryResults ;
import owep.modele.execution.MCollaborateur ;
import owep.modele.execution.MTache ;


/**
 * Controleur pour la saisie du rapport d'activit� de l'utilisateur.
 */
public class CRapportActivite extends CControleurBase
{
  private int mIterationNum ;             // Num�ro d'it�ration dont on liste les t�ches
  private MCollaborateur mCollaborateur ; // Donn�es du collaborateur
  
  
  /**
   * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es. 
   * @throws ServletException Si une erreur survient durant la connexion
   * @see owep.controle.CControleurBase#initialiserBaseDonnees()
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    OQLQuery       lRequete ;       // Requ�te � r�aliser sur la base
    QueryResults   lResultat ;      // R�sultat de la requ�te sur la base
    
    try
    {
      getBaseDonnees ().begin () ;
      
      // R�cup�re la liste des t�ches du collaborateur.
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
   * Initialise le controleur et r�cup�re les param�tres. 
   * @throws ServletException Si une erreur sur les param�tres survient
   * @see owep.controle.CControleurBase#initialiserParametres()
   */
  public void initialiserParametres () throws ServletException
  {
    assert mCollaborateur != null ;
    
    // R�cup�re le num�ro d'it�ration.
    if (getRequete ().getParameter (CConstante.PAR_ITERATION) == null)
    {
      // TODO Requ�te recup it en cours.
    }
    else
    {
      mIterationNum = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_ITERATION)) ;
    }
    mIterationNum = 1 ;
    
    
    // R�cup�re les donn�es transmises par le collaborateur.
    if (getRequete ().getParameter (CConstante.PAR_SUBMIT) != null)
    {
      // R�cup�re le rapport d'activit�s pour chaque t�che du collaborateur.
      for (int i = 0; i < mCollaborateur.getNbTache (); i ++ )
      {
        MTache lTache = mCollaborateur.getTache (i) ;
        
        // Initialise les identifiants des param�tres
        int lTacheId            = lTache.getId () ;
        String lTempsPasseId    = CConstante.PAR_TEMPSPASSE      + lTacheId ;
        String lResteAPasserId  = CConstante.PAR_RESTEAPASSER    + lTacheId ;
        String lDateDebutReelId = CConstante.PAR_DATEDEBUTREELLE + lTacheId ;
        String lDateReestimeeId = CConstante.PAR_DATEFINREELLE   + lTacheId ;
        
        // R�cup�re les valeurs entr�es par le collaborateur et met � jour la t�che dans le mod�le .
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
   * Redirige vers la JSP d'affichage du rapport d'activit� si aucune donn�e n'est post�e ou valide
   * les donn�es dans la base sinon. 
   * @return URL de la page vers laquelle doit �tre redirig� le client.
   * @throws ServletException Si une erreur survient dans le controleur
   * @see owep.controle.CControleurBase#traiter()
   */
  public String traiter () throws ServletException
  {
    try
    {
      if (getRequete ().getParameter (CConstante.PAR_SUBMIT) != null)
      {
        // Met � jour la base de donn�es avec les informations fournies par le collaborateur.
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
        // Affiche le formulaire de saisie du rapport d'activit�.
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
    // Ferme la connexion � la base de donn�es.
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