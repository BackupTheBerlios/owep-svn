package owep.controle.processus;

import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.jdo.QueryResults;
import org.exolab.castor.jdo.TransactionAbortedException;
import org.exolab.castor.jdo.TransactionNotInProgressException;
import owep.controle.CConstante;
import owep.controle.CControleurBase;
import owep.infrastructure.Session;
import owep.modele.execution.MActiviteImprevue;
import owep.modele.execution.MArtefactImprevue;
import owep.modele.execution.MCollaborateur;
import owep.modele.execution.MIteration;
import owep.modele.execution.MProjet;
import owep.modele.execution.MTacheImprevue;
import owep.vue.transfert.VTransfert;



/**
 * Controleur pour les activités imprévues.
 */
public class CActiviteImprevue extends CControleurBase
{
  private MProjet    mProjet ;    // Projet actuellement ouvert par l'utilisateur.
  private MActiviteImprevue mActiviteImprevue ; // Activité imprévue à créer
  private String     mMessage;    // Message qui sera afficher dans la jsp pour indiquer l'opération effectuée.
  private ResourceBundle bundle;

  /**
   * Permet d'initialiser la connexion avec la base de données et d'initiliser si besoin les 
   * variables de la classe.
   * @throws ServletException si une erreur se produit lors de la connexion avec la base de données.
   */
  
  public void initialiserBaseDonnees () throws ServletException
  {
    // Si on accède pour la première fois au controleur (ajout ou modification d'une itération).
    if ((! VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMIT)) ||
      (! VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITMODIFIER)) ||
      (! VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITSUPPRIMER)))
    {
      Session          lSession ;  // Session actuelle de l'utilisateur.
      OQLQuery         lRequete ;  // Requête à réaliser sur la base
      QueryResults     lResultat ; // Résultat de la requête sur la base
      
      lSession = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
      mProjet  = lSession.getProjet () ;
      
      // Recuperation de la session
      HttpSession httpSession = getRequete ().getSession(true);
      //Récupération du ressource bundle
      bundle = ((Session) httpSession.getAttribute("SESSION")).getMessages();
      
      // Charge une copie du projet ouvert.
      try
      {
        getBaseDonnees ().begin () ;
        
        // Récupère le projet ouvert.
        lRequete = getBaseDonnees ().getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
        lRequete.bind (mProjet.getId ()) ;
        lResultat  = lRequete.execute () ;
        mProjet = (MProjet) lResultat.next () ;
        
        getRequete ().setAttribute (CConstante.PAR_PROJET, mProjet) ;
      }
      catch (Exception eException)
      {
        eException.printStackTrace () ;
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
    }
  }

  /**
   * TODO Description de initialiserParametres.
   * @throws ServletException
   * @see owep.controle.CControleurBase#initialiserParametres()
   */
  public void initialiserParametres () throws ServletException
  {
  }

  /**
   * TODO Description de traiter.
   * @return
   * @throws ServletException
   * @see owep.controle.CControleurBase#traiter()
   */
  
  public String traiter () throws ServletException
  {
    Session          lSession ;  // Session actuelle de l'utilisateur.
    OQLQuery         lRequete ;  // Requête à réaliser sur la base
    QueryResults     lResultat ; // Résultat de la requête sur la base
    if ((! VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMIT)) &&
        (! VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITMODIFIER)) &&
        (! VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITSUPPRIMER)))
    {      
      try
      {
        getBaseDonnees().commit();
      }
      catch (TransactionNotInProgressException e)
      {
        e.printStackTrace () ;
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
      catch (TransactionAbortedException e)
      {
        e.printStackTrace () ;
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
      finally
      {        
        try
        {
          getBaseDonnees().close () ;
        }
        catch (PersistenceException e)
        {
          e.printStackTrace () ;
          throw new ServletException (CConstante.EXC_TRAITEMENT) ;
        }
      }
    }
    else
    {    
        try
        {
          MActiviteImprevue lActiviteImprevue;
          MTacheImprevue lTacheImprevue;
          MCollaborateur lCollaborateur;
          MArtefactImprevue lArtefactImprevueSortie;
          MArtefactImprevue lArtefactImprevueEntree;
          MIteration lIteration;
          
          // Ajout d'une activité imprévue
          if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMIT))
          {
            
            mActiviteImprevue = new MActiviteImprevue ();
            VTransfert.transferer (getRequete (), mActiviteImprevue, CConstante.PAR_ARBREACTIVITE) ;
	        mActiviteImprevue.setProjet (mProjet);
	        mProjet.addActiviteImprevue (mActiviteImprevue) ;
	        
	        getBaseDonnees().create(mActiviteImprevue);
	        
	        mMessage = bundle.getString("clActiviteImprevue")+ " " + mActiviteImprevue.getNom () + " " + bundle.getString("cBienCreee");
          }
          // Suppression d'une activité imprévue
          else if(VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITSUPPRIMER))
          {
            int lIndiceActiviteImprevue = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTEACTIVITESIMPREVUES));
            int lIdActiviteImprevue = mProjet.getActiviteImprevue (lIndiceActiviteImprevue).getId ();
            
            lRequete = getBaseDonnees ().getOQLQuery ("select ACTIVITEIMPREVUE from owep.modele.execution.MActiviteImprevue ACTIVITEIMPREVUE where mId = $1") ;
            lRequete.bind (lIdActiviteImprevue) ;
            lResultat  = lRequete.execute () ;
            lActiviteImprevue = (MActiviteImprevue) lResultat.next () ;
            
            // Parcours des tâches imprévues de l'activité afin de les supprimer
            for (int i =lActiviteImprevue.getNbTachesImprevues () -1; i >= 0; i--)
            {
              int lIdTacheImprevue = lActiviteImprevue.getTacheImprevue(i).getId ();
              
              lRequete = getBaseDonnees ().getOQLQuery ("select TACHEIMPREVUE from owep.modele.execution.MTacheImprevue TACHEIMPREVUE where mId = $1") ;
              lRequete.bind (lIdTacheImprevue) ;
              lResultat  = lRequete.execute () ;
              lTacheImprevue = (MTacheImprevue) lResultat.next () ;
              
              lRequete = getBaseDonnees ().getOQLQuery ("select ITERATION from owep.modele.execution.MIteration ITERATION where mId = $1") ;
              lRequete.bind (lTacheImprevue.getIteration ().getId ()) ;
              lResultat  = lRequete.execute () ;
              lIteration = (MIteration) lResultat.next () ;
              
              lRequete = getBaseDonnees ().getOQLQuery ("select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mId = $1") ;
              lRequete.bind (lTacheImprevue.getCollaborateur ().getId ()) ;
              lResultat  = lRequete.execute () ;
              lCollaborateur = (MCollaborateur) lResultat.next () ;
              
              lCollaborateur.supprimerTacheImprevue(lTacheImprevue);
              lIteration.supprimerTacheImprevue(lTacheImprevue);
              
              // On supprime les artefacts en entrées de la tâche imprévue courante.
              for (int j = lTacheImprevue.getNbArtefactsImprevuesEntrees ()-1; j >= 0; j--){
                lRequete = getBaseDonnees ().getOQLQuery ("select ARTEFACTIMPREVUE from owep.modele.execution.MArtefactImprevue ARTEFACTIMPREVUE where mId = $1") ;
                lRequete.bind (lTacheImprevue.getArtefactImprevueEntree (j).getId ()) ;
                lResultat  = lRequete.execute () ;
                lArtefactImprevueEntree = (MArtefactImprevue) lResultat.next () ;
                
                lArtefactImprevueEntree.setTacheImprevueEntree(null);
                lTacheImprevue.supprimerArtefactImprevueEntree (j) ;
              }
              // On supprime les artefacts en sorties de la tâche imprévue courante.
              for (int j = lTacheImprevue.getNbArtefactsImprevuesSorties ()-1; j >= 0; j--){
                int lIdArtefactImprevue = lTacheImprevue.getArtefactImprevueSortie(j).getId ();
                
                lRequete = getBaseDonnees ().getOQLQuery ("select ARTEFACTIMPREVUE from owep.modele.execution.MArtefactImprevue ARTEFACTIMPREVUE where mId = $1") ;
                lRequete.bind (lIdArtefactImprevue) ;
                lResultat  = lRequete.execute () ;
                lArtefactImprevueSortie = (MArtefactImprevue) lResultat.next () ;
                
                lTacheImprevue.supprimerArtefactImprevueSortie (lArtefactImprevueSortie) ;
                getBaseDonnees ().remove (lArtefactImprevueSortie);
              }
              // on supprime la tâche de l'activité.
              lActiviteImprevue.supprimerTacheImprevue (lTacheImprevue);
              getBaseDonnees ().remove (lTacheImprevue);
              
              
            }
            // on met à jour le modèle
            mProjet.supprimerActiviteImprevue (lIndiceActiviteImprevue) ;
            lActiviteImprevue.setProjet(null);
            getBaseDonnees ().remove (lActiviteImprevue);
            
            mMessage = bundle.getString("clActiviteImprevue")+ " " + mActiviteImprevue.getNom () + " " + bundle.getString("cBienSupprimee");
           }
          // Modification d'une activité imprévue.
          else if(VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMITMODIFIER))
          {            
            int lIndiceActiviteImprevue     = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_LISTEACTIVITESIMPREVUES)) ;
            MActiviteImprevue lActiviteImprevueTmp = new MActiviteImprevue ();
            VTransfert.transferer (getRequete (), lActiviteImprevueTmp, CConstante.PAR_ARBREACTIVITE) ;
            
            int lIdActiviteImprevue = mProjet.getActiviteImprevue (lIndiceActiviteImprevue).getId () ;
            
            lRequete = getBaseDonnees ().getOQLQuery ("select ACTIVITEIMPREVUE from owep.modele.execution.MActiviteImprevue ACTIVITEIMPREVUE where mId = $1") ;
            lRequete.bind (lIdActiviteImprevue) ;
            lResultat  = lRequete.execute () ;
            lActiviteImprevue = (MActiviteImprevue) lResultat.next () ;
            
            lActiviteImprevue.setNom(lActiviteImprevueTmp.getNom());
            lActiviteImprevue.setDescription(lActiviteImprevueTmp.getDescription());
            
            mMessage = bundle.getString("clActiviteImprevue")+ " " + mActiviteImprevue.getNom () + " " + bundle.getString("cBienModifiee");
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
            getBaseDonnees ().commit () ;
            getBaseDonnees().close () ;
            getRequete ().setAttribute (CConstante.PAR_PROJET, mProjet) ;
          }
          catch (PersistenceException e)
          {
            e.printStackTrace () ;
            throw new ServletException (CConstante.EXC_TRAITEMENT) ;
          }
        }
      }
    getRequete ().setAttribute (CConstante.PAR_MESSAGE, mMessage) ;
    return "..\\JSP\\Processus\\TActiviteImprevue.jsp" ;
  }
}
