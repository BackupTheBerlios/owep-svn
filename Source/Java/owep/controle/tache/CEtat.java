/*
 * Created on 10 janv. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package owep.controle.tache;

import java.util.Date;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.jdo.QueryResults;

import owep.controle.CConstante;
import owep.controle.CControleurBase;
import owep.infrastructure.Session;
import owep.modele.execution.MCollaborateur;
import owep.modele.execution.MTache;
import owep.modele.execution.MTacheImprevue;



/**
 * @author Emmanuelle et Rémi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CEtat extends CControleurBase{
    private int mIterationNum ;  // Numéro d'itération dont on a listé les tâches  
    private MTache mTache ;      // Tache concernée par le changement d'état
    private MTacheImprevue mTacheImprevue ; // Tache imprévue concernée par le changement d'état
    private MCollaborateur mCollaborateur ; // Collaborateur ayant ouvert la session
    private int idBoutonClique ; //bouton sur lequel on a cliqué
    private Session mSession ;              // Session associé à la connexion
    private String lTypeTache ; // type de la tache (tache imprevue ou tache)
        
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
        // Récupère le collaborateur connecté
        HttpSession session = getRequete ().getSession (true) ;
        mSession = (Session)session.getAttribute("SESSION") ;
        mCollaborateur = mSession.getCollaborateur() ;
        
        getBaseDonnees ().begin () ;
        
        int idCollab = mCollaborateur.getId() ;
        // Récupère la liste des tâches du collaborateur.
        lRequete = getBaseDonnees ().getOQLQuery ("select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mId = $1") ;
        lRequete.bind (idCollab) ;
        lResultat      = lRequete.execute () ;
        mCollaborateur = (MCollaborateur) lResultat.next () ;
        
        // récupération du type de la tâche
        lTypeTache = (String)getRequete().getParameter(CConstante.PAR_TYPE_TACHE);
        
        if (lTypeTache.equals("tache"))
        {
	      // récupération de l'id de la tache
	      int idTache = Integer.parseInt(getRequete().getParameter(CConstante.PAR_TACHE));
	      // Récupère la tâche
	      String req = "select TACHE from owep.modele.execution.MTache TACHE where mId = $1";
	      lRequete = getBaseDonnees ().getOQLQuery (req) ;
	      lRequete.bind (idTache) ;
	      lResultat      = lRequete.execute () ;
	      mTache = (MTache) lResultat.next () ;
        }
        else if (lTypeTache.equals("tacheImprevue"))
        {
	      // récupération de l'id de la tache
	      int idTacheImprevue = Integer.parseInt(getRequete().getParameter(CConstante.PAR_TACHE));
	      // Récupère la tâche
	      String req = "select TACHEIMPREVUE from owep.modele.execution.MTacheImprevue TACHEIMPREVUE where mId = $1";
	      lRequete = getBaseDonnees ().getOQLQuery (req) ;
	      lRequete.bind (idTacheImprevue) ;
	      lResultat      = lRequete.execute () ;
	      mTacheImprevue = (MTacheImprevue) lResultat.next () ;
        }
      }
      catch (Exception eException)
      {
        eException.printStackTrace () ;
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
    }
    
    /**
     * Récupère les paramètres passés au controleur. 
     * @throws ServletException -
     * @see owep.controle.CControleurBase#initialiserParametres()
     */
    public void initialiserParametres () throws ServletException
    {
      try
      {
        // Récupère l'identifiant du bouton cliqué
        idBoutonClique = Integer.parseInt(getRequete().getParameter("pBoutonClique")) ;
      }
      catch (Exception eException)
      {
        eException.printStackTrace () ;
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
    }
    
    
    /**
     * Récupère la liste des tâches d'un collaborateur pour l'itération choisie, et la transmet à la
     * JSP. 
     * @return URL de la page vers laquelle doit être redirigé le client.
     * @throws ServletException Si une erreur survient dans le controleur
     * @see owep.controle.CControleurBase#traiter()
     */
    public String traiter () throws ServletException
    {  
      java.util.ResourceBundle messages;
      messages = java.util.ResourceBundle.getBundle("MessagesBundle");
      String lMessage = "" ;
      try
      {
        Date dateFinChrono = new Date () ;
        // déclaration d'un calendrier
        GregorianCalendar calendrierFinChrono = new GregorianCalendar();
        // initialise le calendrier à la valeur calendrierFinChrono
        calendrierFinChrono.setTime(dateFinChrono);
        
        getRequete ().setAttribute(CConstante.PAR_TYPE_TACHE, lTypeTache) ;
        
        if (lTypeTache.equals("tache"))
        {
	      switch(idBoutonClique){
	        
	        // cas ou on appuie sur le bouton commencer
	        case 1 : 
	          // si la tache était dans l'état non démarré  
	          if (mTache.getEtat() == 0) 
	          {
	              lMessage = messages.getString("cEtatTache")+" \""+mTache.getNom()+"\" "+messages.getString("cEtatTacheDemarree");
	              // on met l etat de la tache à 'commencé' et on retient la date de début
	              // on met la date de fin réestimée (i.e réelle) à la date de fin prévue
	              // on retient la date courante
	              mTache.setEtat(1) ; 
	              mTache.setDateDebutReelle(new Date()) ; 
	              
	              //si la date de début réelle est différente de celle prévue ; on modifie la date de fin réestimée
	              if(mTache.getDateDebutPrevue() != mTache.getDateDebutReelle())
	              {
	                long ecartDate = mTache.getDateFinPrevue().getTime() + mTache.getDateDebutReelle().getTime() - mTache.getDateDebutPrevue().getTime();	
	                mTache.setDateFinReelle(new Date(ecartDate)) ; 
	              }
	              else
	              {
	                mTache.setDateFinReelle(mTache.getDateFinPrevue()) ;
	              }
	              
	              mTache.setDateDebutChrono(new Date().getTime()) ;
	              // Le collaborateur a maintenant une tâche en cours
	              mCollaborateur.setTacheEnCours(mTache.getId()) ;
	          }   
	          // si la tache était dans l'état suspendu
	          if (mTache.getEtat() == 2) 
	          {
	            lMessage = messages.getString("cEtatTache")+" \""+mTache.getNom()+"\" "+messages.getString("cEtatTacheRedemarree");
	            // on met l etat de la tache à 'commencé' et on retient la date de début
	            mTache.setEtat(1) ; 
	            mTache.setDateDebutChrono(new Date().getTime()) ;
	            // Le collaborateur a maintenant une tâche en cours
	            mCollaborateur.setTacheEnCours(mTache.getId()) ;
	          } 
	          // Transmet les données à la JSP d'affichage.
	          getRequete ().setAttribute (CConstante.PAR_MESSAGE, lMessage) ;
	          return "ListeTacheVisu" ;
	              
	        // cas ou on appuie sur le bouton suspendre 
	         case 2 : 
	          // on met l etat de la tache à 'suspendu'
	          mTache.setListe("etat", new Integer(2)) ;
	          // calcul du temps passé
	          
	          // temps passé = date de fin du chrono - date de début du chrono
	          long difftps = calendrierFinChrono.getTime().getTime() - mTache.getDateDebutChrono();
	          // conversion du temps en minutes
	          int tps = (int)(difftps/(60*1000.0));
	          int tempsPasse = (int)mTache.getTempsPasse() + tps ;
	          mTache.setListe("tempsPasse", new Integer(tempsPasse)) ;
	          
	          // calcul du reste à passer = Reste à passer - temps passé
	          double rps = mTache.getResteAPasser() - tps ;
	          // le reste à passer ne peut pas être négatif
	          if ( rps < 0 ) rps = 0 ;
	          mTache.setListe("resteAPasser", new Double(rps)) ;
	          mTache.setListe("dateFinReelle", mTache.getDateFinReelle()) ;
	          
	          // Transmet les données à la JSP d'affichage.
	          getRequete ().setAttribute (CConstante.PAR_TACHE, mTache) ;
	          return "..\\JSP\\Tache\\TValidationRapport.jsp?pIdBoutonClique="+idBoutonClique ;
	          // cas ou on appuie sur le bouton terminer
	        case 3 :  
	          // on met l etat de la tache à 'terminé'
	          mTache.setListe("etat", new Integer(3)) ;
	          
	          // on met le reste à passer à 0 et la date de fin réestimée à la date courante
	          mTache.setListe("resteAPasser", new Double(0)) ;
	          mTache.setListe("dateFinReelle", new Date()) ;
	          
	          // calcul du temps passé
	          
	          // temps passé = date de fin du chrono - date de début du chrono
	          long difftps2 = calendrierFinChrono.getTime().getTime() - mTache.getDateDebutChrono();
	          // conversion du temps en minutes
	          int tps2 = (int)(difftps2/(60*1000.0));
	          tempsPasse = (int)mTache.getTempsPasse() + tps2 ;
	          mTache.setListe("tempsPasse", new Integer(tempsPasse)) ;
	          
	          // Transmet les données à la JSP d'affichage.
	          getRequete ().setAttribute (CConstante.PAR_TACHE, mTache) ;
	          return "..\\JSP\\Tache\\TValidationRapport.jsp?pIdBoutonClique="+idBoutonClique ;
	          
	        default : 
	          // Transmet les données à la JSP d'affichage.
	          return "ListeTacheVisu" ;
	      }
        }
        else
        {
	      switch(idBoutonClique){
	        
	        // cas ou on appuie sur le bouton commencer
	        case 1 : 
	          // si la tache était dans l'état non démarré  
	          if (mTacheImprevue.getEtat() == 0) 
	          {
	              lMessage = messages.getString("cEtatTacheImprevue")+" \""+mTacheImprevue.getNom()+"\" "+messages.getString("cEtatTacheDemarree");
	              // on met l etat de la tache à 'commencé' et on retient la date de début
	              // on met la date de fin réestimée (i.e réelle) à la date de fin prévue
	              // on retient la date courante
	              mTacheImprevue.setEtat(1) ; 
	              mTacheImprevue.setDateDebutReelle(new Date()) ; 
	              
	              //si la date de début réelle est différente de celle prévue ; on modifie la date de fin réestimée
	              if(mTacheImprevue.getDateDebutPrevue() != mTacheImprevue.getDateDebutReelle())
	              {
	                long ecartDate = mTacheImprevue.getDateFinPrevue().getTime() + mTacheImprevue.getDateDebutReelle().getTime() - mTacheImprevue.getDateDebutPrevue().getTime();	
	                mTacheImprevue.setDateFinReelle(new Date(ecartDate)) ; 
	              }
	              else
	              {
	                mTacheImprevue.setDateFinReelle(mTacheImprevue.getDateFinPrevue()) ;
	              }
	              
	              mTacheImprevue.setDateDebutChrono(new Date().getTime()) ;
	              // Le collaborateur a maintenant une tâche imprévue en cours
	              mCollaborateur.setTacheEnCours(mTacheImprevue.getId()) ;
	          }   
	          // si la tache était dans l'état suspendu
	          if (mTacheImprevue.getEtat() == 2) 
	          {
	            lMessage = messages.getString("cEtatTacheImprevue")+" \""+mTacheImprevue.getNom()+"\" "+messages.getString("cEtatTacheRedemarree");
	            // on met l etat de la tache à 'commencé' et on retient la date de début
	            mTacheImprevue.setEtat(1) ; 
	            mTacheImprevue.setDateDebutChrono(new Date().getTime()) ;
	            // Le collaborateur a maintenant une tâche imprévue en cours
	            mCollaborateur.setTacheEnCours(mTacheImprevue.getId()) ;
	          } 
	          // Transmet les données à la JSP d'affichage.
	          getRequete ().setAttribute (CConstante.PAR_MESSAGE, lMessage) ;
	          return "ListeTacheVisu" ;
	              
	        // cas ou on appuie sur le bouton suspendre 
	         case 2 : 
	          // on met l etat de la tache à 'suspendu'
	          mTacheImprevue.setListe("etat", new Integer(2)) ;
	          // calcul du temps passé
	          
	          // temps passé = date de fin du chrono - date de début du chrono
	          long difftps = calendrierFinChrono.getTime().getTime() - mTacheImprevue.getDateDebutChrono();
	          // conversion du temps en minutes
	          int tps = (int)(difftps/(60*1000.0));
	          int tempsPasse = (int)mTacheImprevue.getTempsPasse() + tps ;
	          mTacheImprevue.setListe("tempsPasse", new Integer(tempsPasse)) ;
	          
	          // calcul du reste à passer = Reste à passer - temps passé
	          double rps = mTacheImprevue.getResteAPasser() - tps ;
	          // le reste à passer ne peut pas être négatif
	          if ( rps < 0 ) rps = 0 ;
	          mTacheImprevue.setListe("resteAPasser", new Double(rps)) ;
	          mTacheImprevue.setListe("dateFinReelle", mTacheImprevue.getDateFinReelle()) ;
	          
	          // Transmet les données à la JSP d'affichage.
	          getRequete ().setAttribute (CConstante.PAR_TACHE_IMPREVUE, mTacheImprevue) ;
	          return "..\\JSP\\Tache\\TValidationRapport.jsp?pIdBoutonClique="+idBoutonClique ;
	          // cas ou on appuie sur le bouton terminer
	        case 3 :  
	          // on met l etat de la tache à 'terminé'
	          mTacheImprevue.setListe("etat", new Integer(3)) ;
	          
	          // on met le reste à passer à 0 et la date de fin réestimée à la date courante
	          mTacheImprevue.setListe("resteAPasser", new Double(0)) ;
	          mTacheImprevue.setListe("dateFinReelle", new Date()) ;
	          
	          // calcul du temps passé
	          
	          // temps passé = date de fin du chrono - date de début du chrono
	          long difftps2 = calendrierFinChrono.getTime().getTime() - mTacheImprevue.getDateDebutChrono();
	          // conversion du temps en minutes
	          int tps2 = (int)(difftps2/(60*1000.0));
	          tempsPasse = (int)mTacheImprevue.getTempsPasse() + tps2 ;
	          mTacheImprevue.setListe("tempsPasse", new Integer(tempsPasse)) ;
	          
	          // Transmet les données à la JSP d'affichage.
	          getRequete ().setAttribute (CConstante.PAR_TACHE_IMPREVUE, mTacheImprevue) ;
	          return "..\\JSP\\Tache\\TValidationRapport.jsp?pIdBoutonClique="+idBoutonClique ;
	          
	        default : 
	          // Transmet les données à la JSP d'affichage.
	          return "ListeTacheVisu" ;
	      }
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
