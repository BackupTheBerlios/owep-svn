package owep.controle.projet ;


import java.util.ArrayList ;

import javax.servlet.ServletException ;

import org.exolab.castor.jdo.OQLQuery ;
import org.exolab.castor.jdo.PersistenceException ;
import org.exolab.castor.jdo.QueryResults ;

import owep.controle.CConstante ;
import owep.controle.CControleurBase ;
import owep.modele.execution.MCollaborateur ;
import owep.modele.execution.MIteration;
import owep.modele.execution.MProjet ;


/**
 * Controleur pour l'ouverture d'un projet
 */
public class COuvrirProjet extends CControleurBase
{
  private String mIdProjet ; // Id du projet que le collaborateur souhaite ouvrir
  private ArrayList mListeProjetPossible ; // Liste des projets concernant l'utilisateur connect�

  /**
   * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es.
   * 
   * @throws ServletException Si une erreur survient durant la connexion
   * @see owep.controle.CControleurBase#initialiserBaseDonnees()
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    OQLQuery lRequete ; // Requ�te � r�aliser sur la base
    QueryResults lResultat ; // R�sultat de la requ�te sur la base
    MProjet lProjet ; // Projet enregistr�

    MCollaborateur lCollaborateur = getSession ().getCollaborateur () ; // Collaborateur connect�
    mListeProjetPossible = new ArrayList () ;

    try
    {
      getBaseDonnees ().begin () ;

      // Cherche tous les projets enregistr�
      lRequete = getBaseDonnees ()
        .getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET") ;
      lResultat = lRequete.execute () ;

      // Pour chaque projet pr�sent dans la base de donn�e, on cherche si le collaborateur connect�
      // y participe
      while (lResultat.hasMore ())
      {
        lProjet = (MProjet) lResultat.next () ;
        ArrayList lListCollaborateur = lProjet.getListeCollaborateurs () ;
        int i = 0 ;
        for (i = 0 ; i < lListCollaborateur.size () ; i++)
        {
          // Si le collaborateur connect� participe � ce projet alors on ajoute le projet a la list
          // retourn�
          if (lCollaborateur.getId () == ((MCollaborateur) lListCollaborateur.get (i)).getId ())
          {
            mListeProjetPossible.add (lProjet) ;
            i = lListCollaborateur.size () ;
          }
        }
      }

      getBaseDonnees ().commit () ;

      // La connexion � la base de donn�es est ferm� dans le finally de la fonction traiter
    }
    catch (PersistenceException e)
    {
      e.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }

    // Enregistre la liste de projet possible dans la session
    getSession ().setListProjetPossible (mListeProjetPossible) ;
  }

  /**
   * Initialise le controleur et r�cup�re les param�tres.
   * 
   * @throws ServletException Si une erreur sur les param�tres survient
   * @see owep.controle.CControleurBase#initialiserParametres()
   */
  public void initialiserParametres () throws ServletException
  {
    mIdProjet = getRequete ().getParameter ("mIdProjet") ;
  }

  /**
   * Redirige vers la JSP d'ouverture d'un projet ou la liste des taches a effectuer au cours de
   * l'it�ration courante du projet ouvert.
   * 
   * @return URL de la page qui doit �tre affich�e.
   * @throws ServletException si une erreur survient dans le controleur.
   * @see owep.controle.CControleurBase#traiter()
   */
  public String traiter () throws ServletException
  {
    OQLQuery lRequete ; // Requ�te � r�aliser sur la base
    QueryResults lResultat ; // R�sultat de la requ�te sur la base
    MProjet lProjet ; // Projet � ouvrir

    if (mIdProjet == null)
    {
      // Si la liste de projet n'en contient qu'un 
      if (mListeProjetPossible.size () == 1)
      {
        // Alors on ouvre directement ce projet
        int mId = ((MProjet) mListeProjetPossible.get (0)).getId () ;
        mIdProjet = String.valueOf (mId) ;
      }
      else
      {
        // Sinon on affiche la liste des projets possibles pour que le collaborateur choisisse
        getRequete ().setAttribute ("listProjetPossible", mListeProjetPossible) ;
        return "..\\JSP\\Projet\\OuvrirProjet.jsp" ;
      }
    }

    try
    {
      getBaseDonnees ().begin () ;

      // Cherche le projet � ouvrir
      lRequete = getBaseDonnees ()
        .getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
      lRequete.bind (mIdProjet) ;
      lResultat = lRequete.execute () ;

      lProjet = (MProjet) lResultat.next () ;

      getBaseDonnees ().commit () ;

      // Enregistre le projet � ouvrir dans la session
      getSession ().ouvrirProjet (lProjet) ;
      
      // Cherche l'it�ration � ouvrir
      getBaseDonnees ().begin () ;
      lRequete = getBaseDonnees ()
        .getOQLQuery ("select ITERATION from owep.modele.execution.MIteration ITERATION where mProjet = $1 and mEtat = 1") ;
      lRequete.bind (lProjet) ;
      lResultat = lRequete.execute () ;

      MIteration lIteration = (MIteration) lResultat.next () ;

      getBaseDonnees ().commit () ;

      // Enregistre l'it�ration � ouvrir dans la session
      getSession ().setIteration(lIteration) ;      
      
      return "..\\Tache\\ListeTacheVisu" ;
    }
    catch (PersistenceException e)
    {
      e.printStackTrace () ;
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

  /**
   * Cherche tous les projets auxquels le collaborateur participe
   * 
   * @return Liste des projets auxquelq le collaborateur participe
   * @throws ServletException
   */
/*  private ArrayList getProjet () throws ServletException
  {
    OQLQuery lRequete ; // Requ�te � r�aliser sur la base
    QueryResults lResultat ; // R�sultat de la requ�te sur la base
    ArrayList lListProjet = new ArrayList () ; // Liste de projet ayant pour collaborateur celui
    // connect�
    MCollaborateur lCollaborateur = getSession ().getCollaborateur () ; // Collaborateur connect�
    MProjet lProjet ; // Projet enregistr�

    try
    {
      getBaseDonnees ().begin () ;

      // Cherche tous les projets enregistr�
      lRequete = getBaseDonnees ()
        .getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET") ;
      lResultat = lRequete.execute () ;

      // Pour chaque projet pr�sent dans la base de donn�e, on cherche si le collaborateur connect�
      // y participe
      while (lResultat.hasMore ())
      {
        lProjet = (MProjet) lResultat.next () ;
        ArrayList lListCollaborateur = lProjet.getListeCollaborateurs () ;
        int i = 0 ;
        for (i = 0 ; i < lListCollaborateur.size () ; i++)
        {
          // Si le collaborateur connect� participe � ce projet alors on ajoute le projet a la list
          // retourn�
          if (lCollaborateur.getId () == ((MCollaborateur) lListCollaborateur.get (i)).getId ())
          {
            lListProjet.add (lProjet) ;
            i = lListCollaborateur.size () ;
          }
        }
      }

      getBaseDonnees ().commit () ;

      // La connexion � la base de donn�es est ferm� dans le finally de la fonction traiter
    }
    catch (PersistenceException e)
    {
      e.printStackTrace () ;
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }

    return lListProjet ;

  }*/
}