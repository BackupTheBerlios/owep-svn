package owep.controle.gestion ;


import java.util.ArrayList ;
import javax.servlet.ServletException ;
import owep.controle.CConstante ;
import owep.controle.CControleurBase ;
import owep.infrastructure.Session ;
import owep.modele.execution.MProbleme;
import owep.modele.execution.MProjet ;


/**
 * Controleur pour la visualisation  de la liste des problèmes.
 */
public class CListeProblemeVisu extends CControleurBase
{
  private ArrayList mListeProblemes ; // Liste des problèmes survenus sur le projet en cours.
  
  
  /**
   * Récupère les données nécessaire au controleur dans la base de données. 
   * @throws ServletException Si une erreur survient durant la connexion à la base.
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    Session      lSession ;  // Session actuelle de l'utilisateur.
    MProjet      lProjet ;   // Projet en cours.
    
    lSession = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
    lProjet  = lSession.getProjet () ;
    
    // Charge la liste des problèmes pour le projet ouvert.
    begin () ;
      
    // Exécute la requête de récupération des problèmes.
    // TODO : Filtrer sur les itérations directement dans la requête (problème pour le faire avec Castor).
    creerRequete ("select PROBLEME from owep.modele.execution.MProbleme PROBLEME") ;
    executer () ;
    
    // Parcours le résultat de la requête et ajoute chaque problème à la liste.
    mListeProblemes = new ArrayList () ;
    while (contientResultat ())
    {
      MProbleme lProbleme = (MProbleme) getResultat () ;
      // Prend en compte le problème que s'il appartient au projet ouvert.
      if (lProbleme.getTacheProvoque (0).getIteration ().getProjet ().getId () == lProjet.getId ())
      {
        mListeProblemes.add (lProbleme) ;
      }
    }
    
    commit () ;
    close () ;
  }
  
  
  /**
   * Récupère les paramètres passés au controleur. 
   * @throws ServletException -
   */
  public void initialiserParametres () throws ServletException
  {
  }
  
  
  /**
   * Récupère la liste des tâches d'un collaborateur pour l'itération choisie, et la transmet à la
   * JSP. 
   * @return URL de la page vers laquelle doit être redirigé le client.
   * @throws ServletException Si une erreur survient dans le controleur
   */
  public String traiter () throws ServletException
  {
    getRequete ().setAttribute (CConstante.PAR_LISTEPROBLEMES, mListeProblemes) ;
    
    // Transmet les données à la JSP d'affichage.
    return "/JSP/Gestion/TListeProblemeVisu.jsp" ;
  }
}