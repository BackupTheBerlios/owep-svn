/*
 * Created on 25 févr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package owep.controle.processus;

import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.ServletException;

import org.exolab.castor.jdo.ClassNotPersistenceCapableException;
import org.exolab.castor.jdo.DuplicateIdentityException;
import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.jdo.QueryResults;
import org.exolab.castor.jdo.TransactionNotInProgressException;

import owep.controle.CConstante;
import owep.controle.CControleurBase;
import owep.infrastructure.Session;
import owep.modele.execution.MActiviteImprevue;
import owep.modele.execution.MCollaborateur;
import owep.modele.execution.MIteration;
import owep.modele.execution.MProjet;
import owep.modele.execution.MTache;
import owep.modele.execution.MTacheImprevue;
import owep.modele.processus.MActivite;
import owep.vue.transfert.VTransfert ;

/**
 * @author Victor Nancy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CCloturerIteration extends CControleurBase
{

    private MProjet   mProjet ;          // Projet actuellement ouvert.
    private MIteration mIteration ;      // Itération à cloturer.
    
    
    /**
     * Récupère les données nécessaire au controleur dans la base de données. 
     * @throws ServletException Si une erreur survient durant la connexion à la base.
     */
    public void initialiserBaseDonnees () throws ServletException
    {
      Session      lSession ;     // Session actuelle de l'utilisateur.
      OQLQuery     lRequete ;     // Requête à réaliser sur la base.
      QueryResults lResultat ;    // Résultat de la requête sur la base.
      
      lSession     = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
      //mProjet      = lSession.getProjet () ;
      int idProjet = lSession.getIdProjet();
      
      
      try
      {
        getBaseDonnees ().begin () ;
        
        // Récupère le projet actuellement ouvert.
        lRequete = getBaseDonnees ().getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
        lRequete.bind (idProjet) ;
        lResultat = lRequete.execute () ;
        // Si on récupère correctement le projet dans la base,
        if (lResultat.hasMore ())
        {
          mProjet = (MProjet) lResultat.next () ;
        }
        // Si le projet n'existe pas,
        else
        {
          throw new ServletException (CConstante.EXC_TRAITEMENT) ;
        }
        
        // Charge l'itération en cours.
        lRequete = getBaseDonnees ().getOQLQuery ("select ITERATION from owep.modele.execution.MIteration ITERATION where mEtat = $1 AND mProjet.mId = $2") ;
        lRequete.bind (MIteration.ETAT_EN_COURS) ;
        lRequete.bind (mProjet.getId ()) ;
        lResultat = lRequete.execute () ;
        // Si on récupère correctement l'itération dans la base,
        if (lResultat.hasMore ())
        {
          mIteration = (MIteration) lResultat.next () ;
        }
        // Si l'itération n'existe pas ou n'appartient pas au projet ouvert,
        else
        {
          throw new ServletException (CConstante.EXC_TRAITEMENT) ;
        }
          
      }
      catch (Exception eException)
      {
        eException.printStackTrace () ;
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
    }
    
    
    /**
     * Récupère les paramètres passés au contrôleur. 
     * @throws ServletException -
     */
    public void initialiserParametres () throws ServletException
    {
      // Si l'utilisateur valide les données, récupère les données.
      if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMIT))
      {
        VTransfert.transferer (getRequete (), mIteration, CConstante.PAR_ARBREITERATION) ;
      }
      //Sinon on initialise la date de fin réelle à la date du jour
      else
      {
        mIteration.setDateFinReelle(new Date());
      }
    }
    
    
    /**
     * Transmet le problème à la JSP d'affichage si aucune données n'est soumise. Sinon, met à jour ou
     * insère le problème.
     * @return URL de la page vers laquelle doit être redirigé le client.
     * @throws ServletException Si une erreur survient dans le controleur
     */
    public String traiter () throws ServletException
    {
      try
      {
        // Si l'utilisateur affiche la page pour la première fois,
        if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_VIDE))
        {
          //getBaseDonnees ().commit() ;
          //getBaseDonnees ().close () ;
          
          // Si l'utilisateur accède à la page de cloturation, transmet les données à la page.
          getRequete ().setAttribute (CConstante.PAR_ITERATION, mIteration) ;
          getRequete ().setAttribute (CConstante.PAR_PROJET, mProjet) ;
                    
          // Affiche la page de modification de problème.
          return "/JSP/Processus/TCloturerIteration.jsp" ;
        }
        else
        {
          Session      lSession ;     // Session actuelle de l'utilisateur.
          OQLQuery     lRequete ;     // Requête à réaliser sur la base.
          QueryResults lResultat ;    // Résultat de la requête sur la base.
          
          lSession     = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
          //mProjet      = lSession.getProjet () ;
          int idProjet = lSession.getIdProjet();
          
          // Récupère le projet actuellement ouvert.
          lRequete = getBaseDonnees ().getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
          lRequete.bind (idProjet) ;
          lResultat = lRequete.execute () ;
          // Si on récupère correctement le projet dans la base,
          if (lResultat.hasMore ())
          {
            mProjet = (MProjet) lResultat.next () ;
          }
          // Si le projet n'existe pas,
          else
          {
            throw new ServletException (CConstante.EXC_TRAITEMENT) ;
          }
          
          /*************** Modification de l'itération en cours ************/
         
          //L'état passe à terminée
          mIteration.setEtat(MIteration.ETAT_TERMINE);          
          
          /***************** Ensuite on change d'itération *****************/
          
          //Récupération de l'itération suivante (numéro sivant)
          MIteration mNouvelleIteration ;
          lRequete = getBaseDonnees ().getOQLQuery ("select ITERATION from owep.modele.execution.MIteration ITERATION where mNumero = $1 AND mProjet.mId = $2") ;
          lRequete.bind (mIteration.getNumero()+1) ;
          lRequete.bind (mProjet.getId ()) ;
          lResultat = lRequete.execute () ;

          // Si on récupère correctement l'itération dans la base,
          if (lResultat.hasMore ())
          {
            mNouvelleIteration = (MIteration) lResultat.next () ;
            
            //On change l'état à en_cours
            mNouvelleIteration.setEtat(MIteration.ETAT_EN_COURS);
            //On valorise la date de début réelle à la date de fin réelle de l'itération précédente
            mNouvelleIteration.setDateDebutReelle(mIteration.getDateFinReelle());
            //On parcour la liste des taches de la nouvelle itération
            //et on passe l'état de celle qui peuvent commencer à prète
            //Une tache est prète à commencer si elle n'a aucune condition
            for (int i = 0; i < mNouvelleIteration.getNbTaches(); i ++)
            {
              MTache lTache = mNouvelleIteration.getTache (i) ;
              if (lTache.getNbConditions()==0)
                lTache.setEtat(MTache.ETAT_NON_DEMARRE);
            }
            
            //On parcour la liste des taches de l'itération précédente
            //afin de dupliquer les taches non terminées dans la nouvelle itération
            //et de les terminer dans l'itération précédente
            for(int i = 0; i < mIteration.getNbTaches(); i++)
            {
              MTache lTache = mIteration.getTache (i);
              if(lTache.getEtat() != MTache.ETAT_TERMINE)
              {
                MTache lNouvelleTache = new MTache(lTache);
                lNouvelleTache.setChargeInitiale(lTache.getResteAPasser());
                lNouvelleTache.setResteAPasser(lTache.getResteAPasser());
                lNouvelleTache.setTempsPasse(lTache.getTempsPasse());
                lNouvelleTache.setIteration(mNouvelleIteration);
                lNouvelleTache.setEtat(MTache.ETAT_NON_DEMARRE);
                mNouvelleIteration.addTache(lNouvelleTache);
                
                /*********** Charge le collaborateur responsable. ***********/
                lRequete = getBaseDonnees ().getOQLQuery ("select COLLABORATEUR	from owep.modele.execution.MCollaborateur COLLABORATEUR where mId = $1") ;
                lRequete.bind (lNouvelleTache.getCollaborateur().getId()) ;
                lResultat = lRequete.execute () ;
                // Si on récupère correctement l'itération dans la base,
                if (lResultat.hasMore ())
                {
                  MCollaborateur lCollaborateur = (MCollaborateur) lResultat.next () ;
                  lCollaborateur.addTache(lNouvelleTache);
                }
                // Si l'itération n'existe pas ou n'appartient pas au projet ouvert,
                else
                {
                  throw new ServletException (CConstante.EXC_TRAITEMENT) ;
                }
 
                /*********** Charge l'activité. ***********/
                lRequete = getBaseDonnees ().getOQLQuery ("select ACTIVITE	from owep.modele.processus.MActivite ACTIVITE where mId = $1") ;
                lRequete.bind (lNouvelleTache.getActivite().getId()) ;
                lResultat = lRequete.execute () ;
                // Si on récupère correctement l'itération dans la base,
                if (lResultat.hasMore ())
                {
                  MActivite lActivite = (MActivite) lResultat.next () ;
                  lActivite.addTache(lNouvelleTache);
                }
                // Si l'itération n'existe pas ou n'appartient pas au projet ouvert,
                else
                {
                  throw new ServletException (CConstante.EXC_TRAITEMENT) ;
                }
                
                /*********** Création de la tache ***********/
                try
                {
                  // ajout de la tâche dans la base de données.
                  getBaseDonnees ().create (lNouvelleTache);
                }
                catch (ClassNotPersistenceCapableException e)
                {
                  e.printStackTrace () ;
                  throw new ServletException (CConstante.EXC_TRAITEMENT) ;
                }
                catch (DuplicateIdentityException e)
                {
                  e.printStackTrace () ;
                  throw new ServletException (CConstante.EXC_TRAITEMENT) ;
                }
                catch (TransactionNotInProgressException e)
                {
                  e.printStackTrace () ;
                  throw new ServletException (CConstante.EXC_TRAITEMENT) ;
                }
                catch (PersistenceException e)
                {
                  e.printStackTrace () ;
                  throw new ServletException (CConstante.EXC_TRAITEMENT) ;
                }
                lTache.setEtat(MTache.ETAT_TERMINE);
              }
            }

            //On fait de même avec la liste des taches imprévues
            for(int i = 0; i < mIteration.getNbTachesImprevues(); i++)
            {
              MTacheImprevue lTache = mIteration.getTacheImprevue(i);
              if(lTache.getEtat() != MTache.ETAT_TERMINE)
              {
                MTacheImprevue lNouvelleTache = new MTacheImprevue(lTache);
                lNouvelleTache.setChargeInitiale(lTache.getResteAPasser());
                lNouvelleTache.setResteAPasser(lTache.getResteAPasser());
                lNouvelleTache.setTempsPasse(lTache.getTempsPasse());
                lNouvelleTache.setIteration(mNouvelleIteration);
                lNouvelleTache.setEtat(MTache.ETAT_NON_DEMARRE);
                mNouvelleIteration.addTacheImprevue(lNouvelleTache);
                
                /*********** Charge le collaborateur responsable. ***********/
                lRequete = getBaseDonnees ().getOQLQuery ("select COLLABORATEUR	from owep.modele.execution.MCollaborateur COLLABORATEUR where mId = $1") ;
                lRequete.bind (lNouvelleTache.getCollaborateur().getId()) ;
                lResultat = lRequete.execute () ;
                // Si on récupère correctement l'itération dans la base,
                if (lResultat.hasMore ())
                {
                  MCollaborateur lCollaborateur = (MCollaborateur) lResultat.next () ;
                  lCollaborateur.addTacheImprevue(lNouvelleTache);
                }
                // Si l'itération n'existe pas ou n'appartient pas au projet ouvert,
                else
                {
                  throw new ServletException (CConstante.EXC_TRAITEMENT) ;
                }
 
                /*********** Charge l'activité. ***********/
                lRequete = getBaseDonnees ().getOQLQuery ("select ACTIVITEIMPREVUE	from owep.modele.execution.MActiviteImprevue ACTIVITEIMPREVUE where mId = $1") ;
                lRequete.bind (lNouvelleTache.getActiviteImprevue().getId()) ;
                lResultat = lRequete.execute () ;
                // Si on récupère correctement l'itération dans la base,
                if (lResultat.hasMore ())
                {
                  MActiviteImprevue lActivite = (MActiviteImprevue) lResultat.next () ;
                  lActivite.addTacheImprevue(lNouvelleTache);
                }
                // Si l'itération n'existe pas ou n'appartient pas au projet ouvert,
                else
                {
                  throw new ServletException (CConstante.EXC_TRAITEMENT) ;
                }
                
                /*********** Création de la tache ***********/
                try
                {
                  // ajout de la tâche dans la base de données.
                  getBaseDonnees ().create (lNouvelleTache);
                }
                catch (ClassNotPersistenceCapableException e)
                {
                  e.printStackTrace () ;
                  throw new ServletException (CConstante.EXC_TRAITEMENT) ;
                }
                catch (DuplicateIdentityException e)
                {
                  e.printStackTrace () ;
                  throw new ServletException (CConstante.EXC_TRAITEMENT) ;
                }
                catch (TransactionNotInProgressException e)
                {
                  e.printStackTrace () ;
                  throw new ServletException (CConstante.EXC_TRAITEMENT) ;
                }
                catch (PersistenceException e)
                {
                  e.printStackTrace () ;
                  throw new ServletException (CConstante.EXC_TRAITEMENT) ;
                }
                lTache.setEtat(MTache.ETAT_TERMINE);
              }
            }
            
            getSession().setIteration(mNouvelleIteration);
            
          }
          // Si l'itération n'existe pas ou n'appartient pas au projet ouvert,
          else
          {
            //On termine les taches et les taches imprévues
            for(int i = 0; i < mIteration.getNbTaches(); i++)
            {
              mIteration.getTache(i).setEtat(MTache.ETAT_TERMINE); 
            }
            for(int i = 0; i < mIteration.getNbTachesImprevues(); i++)
            {
              mIteration.getTacheImprevue(i).setEtat(MTache.ETAT_TERMINE); 
            }
            
            getSession().setIteration(null);
          }

          ResourceBundle messages = java.util.ResourceBundle.getBundle("MessagesBundle");
          String lMessage = messages.getString("cloturerIterationMessageConfirmation1") + " " + mIteration.getNumero() + " " + messages.getString("cloturerIterationMessageConfirmation2");
          getRequete ().setAttribute (CConstante.PAR_MESSAGE, lMessage) ;
          
          // Affiche la page de visualisation des taches à réaliser dans la nouvelle itération.
          return "/Tache/ListeTacheVisu" ;
        }
      }
      catch (Exception eException)
      {
        eException.printStackTrace () ;
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
      finally
      {
        try
        {
          getBaseDonnees ().commit () ;
          getBaseDonnees ().close () ;
        }
        catch (Exception eException)
        {
          eException.printStackTrace () ;
          throw new ServletException (CConstante.EXC_TRAITEMENT) ;
        }
      }
    }
  }
