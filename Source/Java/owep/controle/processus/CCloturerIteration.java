/*
 * Created on 25 f�vr. 2005
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
    private MIteration mIteration ;      // It�ration � cloturer.
    
    
    /**
     * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es. 
     * @throws ServletException Si une erreur survient durant la connexion � la base.
     */
    public void initialiserBaseDonnees () throws ServletException
    {
      Session      lSession ;     // Session actuelle de l'utilisateur.
      OQLQuery     lRequete ;     // Requ�te � r�aliser sur la base.
      QueryResults lResultat ;    // R�sultat de la requ�te sur la base.
      
      lSession     = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
      //mProjet      = lSession.getProjet () ;
      int idProjet = lSession.getIdProjet();
      
      
      try
      {
        getBaseDonnees ().begin () ;
        
        // R�cup�re le projet actuellement ouvert.
        lRequete = getBaseDonnees ().getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
        lRequete.bind (idProjet) ;
        lResultat = lRequete.execute () ;
        // Si on r�cup�re correctement le projet dans la base,
        if (lResultat.hasMore ())
        {
          mProjet = (MProjet) lResultat.next () ;
        }
        // Si le projet n'existe pas,
        else
        {
          throw new ServletException (CConstante.EXC_TRAITEMENT) ;
        }
        
        // Charge l'it�ration en cours.
        lRequete = getBaseDonnees ().getOQLQuery ("select ITERATION from owep.modele.execution.MIteration ITERATION where mEtat = $1 AND mProjet.mId = $2") ;
        lRequete.bind (MIteration.ETAT_EN_COURS) ;
        lRequete.bind (mProjet.getId ()) ;
        lResultat = lRequete.execute () ;
        // Si on r�cup�re correctement l'it�ration dans la base,
        if (lResultat.hasMore ())
        {
          mIteration = (MIteration) lResultat.next () ;
        }
        // Si l'it�ration n'existe pas ou n'appartient pas au projet ouvert,
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
     * R�cup�re les param�tres pass�s au contr�leur. 
     * @throws ServletException -
     */
    public void initialiserParametres () throws ServletException
    {
      // Si l'utilisateur valide les donn�es, r�cup�re les donn�es.
      if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMIT))
      {
        VTransfert.transferer (getRequete (), mIteration, CConstante.PAR_ARBREITERATION) ;
      }
      //Sinon on initialise la date de fin r�elle � la date du jour
      else
      {
        mIteration.setDateFinReelle(new Date());
      }
    }
    
    
    /**
     * Transmet le probl�me � la JSP d'affichage si aucune donn�es n'est soumise. Sinon, met � jour ou
     * ins�re le probl�me.
     * @return URL de la page vers laquelle doit �tre redirig� le client.
     * @throws ServletException Si une erreur survient dans le controleur
     */
    public String traiter () throws ServletException
    {
      try
      {
        // Si l'utilisateur affiche la page pour la premi�re fois,
        if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_VIDE))
        {
          //getBaseDonnees ().commit() ;
          //getBaseDonnees ().close () ;
          
          // Si l'utilisateur acc�de � la page de cloturation, transmet les donn�es � la page.
          getRequete ().setAttribute (CConstante.PAR_ITERATION, mIteration) ;
          getRequete ().setAttribute (CConstante.PAR_PROJET, mProjet) ;
                    
          // Affiche la page de modification de probl�me.
          return "/JSP/Processus/TCloturerIteration.jsp" ;
        }
        else
        {
          Session      lSession ;     // Session actuelle de l'utilisateur.
          OQLQuery     lRequete ;     // Requ�te � r�aliser sur la base.
          QueryResults lResultat ;    // R�sultat de la requ�te sur la base.
          
          lSession     = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
          //mProjet      = lSession.getProjet () ;
          int idProjet = lSession.getIdProjet();
          
          // R�cup�re le projet actuellement ouvert.
          lRequete = getBaseDonnees ().getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
          lRequete.bind (idProjet) ;
          lResultat = lRequete.execute () ;
          // Si on r�cup�re correctement le projet dans la base,
          if (lResultat.hasMore ())
          {
            mProjet = (MProjet) lResultat.next () ;
          }
          // Si le projet n'existe pas,
          else
          {
            throw new ServletException (CConstante.EXC_TRAITEMENT) ;
          }
          
          /*************** Modification de l'it�ration en cours ************/
         
          //L'�tat passe � termin�e
          mIteration.setEtat(MIteration.ETAT_TERMINE);          
          
          /***************** Ensuite on change d'it�ration *****************/
          
          //R�cup�ration de l'it�ration suivante (num�ro sivant)
          MIteration mNouvelleIteration ;
          lRequete = getBaseDonnees ().getOQLQuery ("select ITERATION from owep.modele.execution.MIteration ITERATION where mNumero = $1 AND mProjet.mId = $2") ;
          lRequete.bind (mIteration.getNumero()+1) ;
          lRequete.bind (mProjet.getId ()) ;
          lResultat = lRequete.execute () ;

          // Si on r�cup�re correctement l'it�ration dans la base,
          if (lResultat.hasMore ())
          {
            mNouvelleIteration = (MIteration) lResultat.next () ;
            
            //On change l'�tat � en_cours
            mNouvelleIteration.setEtat(MIteration.ETAT_EN_COURS);
            //On valorise la date de d�but r�elle � la date de fin r�elle de l'it�ration pr�c�dente
            mNouvelleIteration.setDateDebutReelle(mIteration.getDateFinReelle());
            //On parcour la liste des taches de la nouvelle it�ration
            //et on passe l'�tat de celle qui peuvent commencer � pr�te
            //Une tache est pr�te � commencer si elle n'a aucune condition
            for (int i = 0; i < mNouvelleIteration.getNbTaches(); i ++)
            {
              MTache lTache = mNouvelleIteration.getTache (i) ;
              if (lTache.getNbConditions()==0)
                lTache.setEtat(MTache.ETAT_NON_DEMARRE);
            }
            
            //On parcour la liste des taches de l'it�ration pr�c�dente
            //afin de dupliquer les taches non termin�es dans la nouvelle it�ration
            //et de les terminer dans l'it�ration pr�c�dente
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
                // Si on r�cup�re correctement l'it�ration dans la base,
                if (lResultat.hasMore ())
                {
                  MCollaborateur lCollaborateur = (MCollaborateur) lResultat.next () ;
                  lCollaborateur.addTache(lNouvelleTache);
                }
                // Si l'it�ration n'existe pas ou n'appartient pas au projet ouvert,
                else
                {
                  throw new ServletException (CConstante.EXC_TRAITEMENT) ;
                }
 
                /*********** Charge l'activit�. ***********/
                lRequete = getBaseDonnees ().getOQLQuery ("select ACTIVITE	from owep.modele.processus.MActivite ACTIVITE where mId = $1") ;
                lRequete.bind (lNouvelleTache.getActivite().getId()) ;
                lResultat = lRequete.execute () ;
                // Si on r�cup�re correctement l'it�ration dans la base,
                if (lResultat.hasMore ())
                {
                  MActivite lActivite = (MActivite) lResultat.next () ;
                  lActivite.addTache(lNouvelleTache);
                }
                // Si l'it�ration n'existe pas ou n'appartient pas au projet ouvert,
                else
                {
                  throw new ServletException (CConstante.EXC_TRAITEMENT) ;
                }
                
                /*********** Cr�ation de la tache ***********/
                try
                {
                  // ajout de la t�che dans la base de donn�es.
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

            //On fait de m�me avec la liste des taches impr�vues
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
                // Si on r�cup�re correctement l'it�ration dans la base,
                if (lResultat.hasMore ())
                {
                  MCollaborateur lCollaborateur = (MCollaborateur) lResultat.next () ;
                  lCollaborateur.addTacheImprevue(lNouvelleTache);
                }
                // Si l'it�ration n'existe pas ou n'appartient pas au projet ouvert,
                else
                {
                  throw new ServletException (CConstante.EXC_TRAITEMENT) ;
                }
 
                /*********** Charge l'activit�. ***********/
                lRequete = getBaseDonnees ().getOQLQuery ("select ACTIVITEIMPREVUE	from owep.modele.execution.MActiviteImprevue ACTIVITEIMPREVUE where mId = $1") ;
                lRequete.bind (lNouvelleTache.getActiviteImprevue().getId()) ;
                lResultat = lRequete.execute () ;
                // Si on r�cup�re correctement l'it�ration dans la base,
                if (lResultat.hasMore ())
                {
                  MActiviteImprevue lActivite = (MActiviteImprevue) lResultat.next () ;
                  lActivite.addTacheImprevue(lNouvelleTache);
                }
                // Si l'it�ration n'existe pas ou n'appartient pas au projet ouvert,
                else
                {
                  throw new ServletException (CConstante.EXC_TRAITEMENT) ;
                }
                
                /*********** Cr�ation de la tache ***********/
                try
                {
                  // ajout de la t�che dans la base de donn�es.
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
          // Si l'it�ration n'existe pas ou n'appartient pas au projet ouvert,
          else
          {
            //On termine les taches et les taches impr�vues
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
          
          // Affiche la page de visualisation des taches � r�aliser dans la nouvelle it�ration.
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
