package owep.controle.gestion ;


import java.util.ArrayList ;
import javax.servlet.ServletException ;
import owep.controle.CConstante ;
import owep.controle.CControleurBase ;
import owep.infrastructure.Session ;
import owep.modele.execution.MProbleme;
import owep.modele.execution.MProjet ;


/**
 * Controleur pour la visualisation  de la liste des probl�mes.
 */
public class CListeProblemeVisu extends CControleurBase
{
  private ArrayList mListeProblemes ; // Liste des probl�mes survenus sur le projet en cours.
  
  
  /**
   * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es. 
   * @throws ServletException Si une erreur survient durant la connexion � la base.
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    Session      lSession ;  // Session actuelle de l'utilisateur.
    MProjet      lProjet ;   // Projet en cours.
    
    lSession = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
    lProjet  = lSession.getProjet () ;
    
    // Charge la liste des probl�mes pour le projet ouvert.
    begin () ;
      
    // Ex�cute la requ�te de r�cup�ration des probl�mes.
    // TODO : Filtrer sur les it�rations directement dans la requ�te (probl�me pour le faire avec Castor).
    creerRequete ("select PROBLEME from owep.modele.execution.MProbleme PROBLEME") ;
    executer () ;
    
    // Parcours le r�sultat de la requ�te et ajoute chaque probl�me � la liste.
    mListeProblemes = new ArrayList () ;
    while (contientResultat ())
    {
      MProbleme lProbleme = (MProbleme) getResultat () ;
      // Prend en compte le probl�me que s'il appartient au projet ouvert.
      if (lProbleme.getTacheProvoque (0).getIteration ().getProjet ().getId () == lProjet.getId ())
      {
        mListeProblemes.add (lProbleme) ;
      }
    }
    
    commit () ;
    close () ;
  }
  
  
  /**
   * R�cup�re les param�tres pass�s au controleur. 
   * @throws ServletException -
   */
  public void initialiserParametres () throws ServletException
  {
  }
  
  
  /**
   * R�cup�re la liste des t�ches d'un collaborateur pour l'it�ration choisie, et la transmet � la
   * JSP. 
   * @return URL de la page vers laquelle doit �tre redirig� le client.
   * @throws ServletException Si une erreur survient dans le controleur
   */
  public String traiter () throws ServletException
  {
    getRequete ().setAttribute (CConstante.PAR_LISTEPROBLEMES, mListeProblemes) ;
    
    // Transmet les donn�es � la JSP d'affichage.
    return "/JSP/Gestion/TListeProblemeVisu.jsp" ;
  }
}