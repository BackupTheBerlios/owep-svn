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



/**
 * @author Emmanuelle et R�mi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CEtat extends CControleurBase{
    private int mIterationNum ;  // Num�ro d'it�ration dont on a list� les t�ches  
    private MTache mTache ;      // Tache concern�e par le changement d'�tat
    private MCollaborateur mCollaborateur ; // Collaborateur ayant ouvert la session
    private int idBoutonClique ; //bouton sur lequel on a cliqu�
    private Session mSession ;              // Session associ� � la connexion
        
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
        // R�cup�re le collaborateur connect�
        HttpSession session = getRequete ().getSession (true) ;
        mSession = (Session)session.getAttribute("SESSION") ;
        mCollaborateur = mSession.getCollaborateur() ;
        
        getBaseDonnees ().begin () ;
        
        int idCollab = mCollaborateur.getId() ;
        // R�cup�re la liste des t�ches du collaborateur.
        lRequete = getBaseDonnees ().getOQLQuery ("select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mId = $1") ;
        lRequete.bind (idCollab) ;
        lResultat      = lRequete.execute () ;
        mCollaborateur = (MCollaborateur) lResultat.next () ;
        
        getBaseDonnees ().commit () ;
        
        // r�cup�ration de l'id de la tache
        int idTache = Integer.parseInt(getRequete().getParameter(CConstante.PAR_TACHE));
        getBaseDonnees ().begin () ;
        // R�cup�re la t�che
        String req = "select TACHE from owep.modele.execution.MTache TACHE where mId = $1";
        lRequete = getBaseDonnees ().getOQLQuery (req) ;
        lRequete.bind (idTache) ;
        lResultat      = lRequete.execute () ;
        mTache = (MTache) lResultat.next () ;
 
        getBaseDonnees ().commit () ;
      }
      catch (Exception eException)
      {
        eException.printStackTrace () ;
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
    }
    
    /**
     * R�cup�re les param�tres pass�s au controleur. 
     * @throws ServletException -
     * @see owep.controle.CControleurBase#initialiserParametres()
     */
    public void initialiserParametres () throws ServletException
    {
      try
      {
        // R�cup�re l'identifiant du bouton cliqu�
        idBoutonClique = Integer.parseInt(getRequete().getParameter("pBoutonClique")) ;
        
        // R�cup�re le num�ro d'it�ration.
        if (getRequete ().getParameter (CConstante.PAR_ITERATION) == null)
        {
          mIterationNum = 1 ;
        }
        else
        {
          mIterationNum = Integer.parseInt (getRequete ().getParameter (CConstante.PAR_ITERATION)) ;
        }
      }
      catch (Exception eException)
      {
        eException.printStackTrace () ;
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
    }
    
    
    /**
     * R�cup�re la liste des t�ches d'un collaborateur pour l'it�ration choisie, et la transmet � la
     * JSP. 
     * @return URL de la page vers laquelle doit �tre redirig� le client.
     * @throws ServletException Si une erreur survient dans le controleur
     * @see owep.controle.CControleurBase#traiter()
     */
    public String traiter () throws ServletException
    {  
      Date dateFinChrono = new Date () ;
      // d�claration d'un calendrier
      GregorianCalendar calendrierFinChrono = new GregorianCalendar();
      // initialise le calendrier � la valeur calendrierFinChrono
      calendrierFinChrono.setTime(dateFinChrono);
      // d�claration d'un calendrier
      GregorianCalendar calendrierDebutChrono = new GregorianCalendar();
      
      switch(idBoutonClique){
        
        // cas ou on appuie sur le bouton commencer
        case 1 : 
          // si la tache �tait dans l'�tat non d�marr�  
          if (mTache.getEtat() == 0) 
          {
              // on met l etat de la tache � 'commenc�' et on retient la date de d�but
              // on met la date de fin r�estim�e (i.e r�elle) � la date de fin pr�vue
              // on retient la date courante
              mTache.setEtat(1) ; 
              mTache.setDateDebutReelle(new Date()) ; 
              
              //si la date de d�but r�elle est diff�rente de celle pr�vue ; on modifie la date de fin r�estim�e
              if(mTache.getDateDebutPrevue() != mTache.getDateDebutReelle())
              {
                long ecartDate = mTache.getDateFinPrevue().getTime() + mTache.getDateDebutReelle().getTime() - mTache.getDateDebutPrevue().getTime();	
                mTache.setDateFinReelle(new Date(ecartDate)) ; 
              }
              else
              {
                mTache.setDateFinReelle(mTache.getDateFinPrevue()) ;
              }
              
              mTache.setDateDebutChrono(new Date()) ;
              // Le collaborateur a maintenant une t�che en cours
              mCollaborateur.setTacheEnCours(1) ;
          }   
          // si la tache �tait dans l'�tat suspendu
          if (mTache.getEtat() == 2) 
          {
            // on met l etat de la tache � 'commenc�' et on retient la date de d�but
            mTache.setEtat(1) ; 
            mTache.setDateDebutChrono(new Date()) ;
            // Le collaborateur a maintenant une t�che en cours
            mCollaborateur.setTacheEnCours(1) ;
          } 
          
          try
          {
            // Met � jour l'�tat de la t�che dans la base de donn�es
            getBaseDonnees ().begin () ;
            getBaseDonnees ().update (mTache) ;
            getBaseDonnees ().update (mCollaborateur) ;
            getBaseDonnees ().commit () ; 
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
          
          // Transmet les donn�es � la JSP d'affichage.
          getRequete ().setAttribute (CConstante.PAR_ITERATION,     new Integer (mIterationNum)) ;
          return "ListeTacheVisu" ;
            
        // cas ou on appuie sur le bouton suspendre 
        case 2 : 
          // on met l etat de la tache � 'suspendu'
          mTache.setEtat(2) ; 
          // calcul du temps pass�
          // initialise le calendrier � la valeur calendrierDebutChrono
          calendrierDebutChrono.setTime(mTache.getDateDebutChrono());
          
          // temps pass� = date de fin du chrono - date de d�but du chrono
          long difftps = calendrierFinChrono.getTime().getTime() - calendrierDebutChrono.getTime().getTime();
          // conversion du temps en minutes
          int tps = (int)(difftps/(60*1000.0));
          mTache.setTempsPasse(mTache.getTempsPasse() + tps) ;
          
          // calcul du reste � passer = Reste � passer - temps pass�
          double rps = mTache.getResteAPasser() - tps ;
          // le reste � passer ne peut pas �tre n�gatif
          if ( rps < 0 ) rps = 0 ;
          mTache.setResteAPasser(rps) ;
          
          // Transmet les donn�es � la JSP d'affichage.
          getRequete ().setAttribute (CConstante.PAR_TACHE, mTache) ;
          return "..\\JSP\\Tache\\TValidationRapport.jsp?pIdBoutonClique="+idBoutonClique ;

        // cas ou on appuie sur le bouton terminer
        case 3 :  
          // on met l etat de la tache � 'termin�'
          mTache.setEtat(3) ; 
          // on met le reste � passer � 0 et la date de fin r�estim�e � la date courante
          mTache.setResteAPasser(0);
          mTache.setDateFinReelle(new Date()) ;
          
          // calcul du temps pass�
          // initialise le calendrier � la valeur calendrierDebutChrono
          calendrierDebutChrono.setTime(mTache.getDateDebutChrono());
          
          // temps pass� = date de fin du chrono - date de d�but du chrono
          long difftps2 = calendrierFinChrono.getTime().getTime() - calendrierDebutChrono.getTime().getTime();
          // conversion du temps en minutes
          int tps2 = (int)(difftps2/(60*1000.0));
          mTache.setTempsPasse(mTache.getTempsPasse() + tps2) ;
          
          // Transmet les donn�es � la JSP d'affichage.
          getRequete ().setAttribute (CConstante.PAR_TACHE, mTache) ;
          return "..\\JSP\\Tache\\TValidationRapport.jsp?pIdBoutonClique="+idBoutonClique ;
          
        default : 
          // Transmet les donn�es � la JSP d'affichage.
          getRequete ().setAttribute (CConstante.PAR_ITERATION,     new Integer (mIterationNum)) ;
          return "ListeTacheVisu" ;
      }
    }
}
