package owep.controle.gestion ;


import java.util.ResourceBundle;
import javax.servlet.ServletException ;
import owep.controle.CConstante ;
import owep.controle.CControleurBase ;
import owep.infrastructure.Session ;
import owep.modele.execution.MRisque ;
import owep.modele.execution.MProjet ;
import owep.vue.transfert.VTransfert ;


/**
 * Controleur pour la modification d'un probleme.
 */
public class CRisqueModif extends CControleurBase
{
  private MProjet mProjet ; // Projet actuellement ouvert.
  private MRisque mRisque ; // Probl�me � cr�er ou modifier.
  
  
  /**
   * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es. 
   * @throws ServletException Si une erreur survient durant la connexion � la base.
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    Session      lSession ;  // Session actuelle de l'utilisateur.
    String       lIdRisque ; // Identifiant du risque.
    
    lSession    = (Session) getRequete ().getSession ().getAttribute (CConstante.SES_SESSION) ;
    mProjet     = lSession.getProjet () ;
    lIdRisque = getRequete ().getParameter (CConstante.PAR_RISQUE) ;
    
    
    begin () ;
    
    // R�cup�re le projet actuellement ouvert.
    creerRequete ("select PROJET from owep.modele.execution.MProjet PROJET where mId = $1") ;
    parametreRequete (mProjet.getId ()) ;
    executer () ;
    // Si on r�cup�re correctement le projet dans la base,
    if (contientResultat ())
    {
      mProjet = (MProjet) getResultat () ;
    }
    // Si le projet n'existe pas,
    else
    {
      throw new ServletException (CConstante.EXC_TRAITEMENT) ;
    }
    
    
    // Si un probl�me est pass� en param�tre,
    if (lIdRisque != null)
    {
      // Charge le probl�me pass� en param�tre.
      creerRequete ("select RISQUE from owep.modele.execution.MRisque RISQUE where mId = $1 AND mProjet.mId = $2") ;
      parametreRequete (Integer.parseInt (lIdRisque)) ;
      parametreRequete (mProjet.getId ()) ;
      executer () ;
      
      // Si on r�cup�re correctement le probl�me dans la base,
      if (contientResultat ())
      {
        mRisque = (MRisque) getResultat () ;
      }
      // Si le probl�me n'existe pas ou n'appartient pas au projet ouvert,
      else
      {
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
    }
    // Si aucun probl�me existant n'est pass� en param�tre,
    else
    {
      mRisque = new MRisque () ;
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
      VTransfert.transferer (getRequete (), mRisque, CConstante.PAR_ARBRERISQUE) ;
      mRisque.setProjet (mProjet) ;
      if (mRisque.getId () == 0)
      {
        mProjet.addRisque (mRisque) ;
      }
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
    // Si l'utilisateur affiche la page pour la premi�re fois,
    if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_VIDE))
    {
      // Si l'utilisateur acc�de � la page d'ajout/modification, transmet les donn�es � la page.
      getRequete ().setAttribute (CConstante.PAR_RISQUE, mRisque) ;
      getRequete ().setAttribute (CConstante.PAR_PROJET, mProjet) ;
      // Valide les donn�es.
      commit () ;
      close () ;
      
      // Affiche la page de modification de probl�me.
      return "/JSP/Gestion/TRisqueModif.jsp" ;
    }
    else
    {
      // Si l'utilisateur valide les donn�es, alors on les enregistre dans la base.
      String lMessage = "" ;
      if (VTransfert.getValeurTransmise (getRequete (), CConstante.PAR_SUBMIT))
      {
        ResourceBundle lMessages = ResourceBundle.getBundle("MessagesBundle") ;
        
        // Cr�e l'objet ou le met � jour s'il existe d�j�.
        if (mRisque.getId () == 0)
        {
          creer (mRisque) ;
          lMessage = lMessages.getString ("risqueModifMsgCreation1") + mRisque.getNom () + lMessages.getString ("risqueModifMsgCreation2") ;
        }
        else
        {
          lMessage = lMessages.getString ("risqueModifMsgModification1") + mRisque.getNom () + lMessages.getString ("risqueModifMsgModification2") ;
        }
      }
      // Valide les donn�es.
      commit () ;
      close () ;
      
      // Affiche la page de visualisation de la liste des probl�mes.
      getRequete ().setAttribute (CConstante.PAR_MESSAGE, lMessage) ;
      return "/Gestion/ListeRisqueVisu" ;
    }
  }
}