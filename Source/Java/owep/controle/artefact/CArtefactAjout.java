/*
 * Created on 12 janv. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package owep.controle.artefact;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.jdo.QueryResults;

import owep.controle.CConstante;
import owep.controle.CControleurBase;
import owep.infrastructure.fileupload.DiskFileUpload;
import owep.infrastructure.fileupload.FileItem;
import owep.infrastructure.fileupload.FileUploadException;
import owep.modele.execution.MArtefact;
import owep.modele.execution.MArtefactImprevue;

/**
 * @author Victor Nancy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CArtefactAjout extends CControleurBase
{
  private MArtefact mArtefact ; // Donn�es de l'artefact
  private MArtefactImprevue mArtefactImprevu ; // Donn�es de l'artefact
  String PATH_ARTEFACT;
  private int idArtefact = -1;
  private int idArtefactImprevu = -1;
  
  /**
   * R�cup�re les donn�es n�cessaire au controleur dans la base de donn�es. 
   * @throws ServletException Si une erreur survient durant la connexion
   * @see owep.controle.CControleurBase#initialiserBaseDonnees()
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    OQLQuery       lRequete ;       // Requ�te � r�aliser sur la base
    QueryResults   lResultat ;      // R�sultat de la requ�te sur la base
   
    //Cas ou l'idArtefact est pass� dans l'url
    if(getRequete().getParameter(CConstante.PAR_ARTEFACT) != null)
    {
      getRequete().getSession().setAttribute(CConstante.PAR_ARTEFACT,getRequete().getParameter(CConstante.PAR_ARTEFACT));
      idArtefact = Integer.parseInt(getRequete().getParameter(CConstante.PAR_ARTEFACT));
    }
    //Cas ou l'idArtefact est pass� dans l'url et est un artefact impr�vu
    else if(getRequete().getParameter(CConstante.PAR_ARTEFACT_IMPREVU) != null)
    {
      getRequete().getSession().setAttribute(CConstante.PAR_ARTEFACT_IMPREVU,getRequete().getParameter(CConstante.PAR_ARTEFACT_IMPREVU));
      idArtefactImprevu = Integer.parseInt(getRequete().getParameter(CConstante.PAR_ARTEFACT_IMPREVU));
    }
    else
    {
      if(getRequete().getSession().getAttribute(CConstante.PAR_ARTEFACT) != null)
      {
        idArtefact = Integer.parseInt((String)(getRequete().getSession().getAttribute(CConstante.PAR_ARTEFACT)));
      }
      else
      {
        //Artefact Impr�vu  	
        idArtefactImprevu = Integer.parseInt((String)(getRequete().getSession().getAttribute(CConstante.PAR_ARTEFACT_IMPREVU)));
      }
    }
    
    //(si ce n'est pas le cas cela sera fait dans traiter � cause des probl�me avec le formulaire de type "multipart/form-data")
      try
      {
        getBaseDonnees ().begin () ;
        if(idArtefact>-1)
        {
          // R�cup�re l'artefact concern� par l'upload.
          lRequete = getBaseDonnees ().getOQLQuery ("select ARTEFACT from owep.modele.execution.MArtefact ARTEFACT where mId = $1") ;
          lRequete.bind (idArtefact) ;
          lResultat = lRequete.execute () ;
          mArtefact = (MArtefact) lResultat.next () ;
          mArtefactImprevu = null ;
        }
        //Artefact impr�vu
        else
        {
          // R�cup�re l'artefact concern� par l'upload.
          lRequete = getBaseDonnees ().getOQLQuery ("select ARTEFACTIMPREVU from owep.modele.execution.MArtefactImprevue ARTEFACTIMPREVU where mId = $1") ;
          lRequete.bind (idArtefactImprevu) ;
          lResultat = lRequete.execute () ;
          mArtefactImprevu = (MArtefactImprevue) lResultat.next () ;    
          mArtefact = null ;
        }
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
    PATH_ARTEFACT = getSession().getConfiguration().getPathArtefact(); 
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
    boolean ok = false;
    try
    {
      HttpServletRequest request = getRequete() ;
      
      //Si on a valid� le formulaire pour uploader un fichier
      //La m�thode qui suis est utilis�e car le formulaire est de type "multipart/form-data"
      //pour r�cup�rer des fichiers. On ne peut donc pas utilis� les m�thodes habituelles
      //comme getParameter
      if(DiskFileUpload.isMultipartContent(request))
      {
        ok = true;
        DiskFileUpload upload = new DiskFileUpload();
        List items;
        FileItem itemFichier = null;
        File fullFile = null;
        
        try
        {
          //on cr�e la liste des �l�ments du formulaire
          items = upload.parseRequest(request) ;
          //it�rateur parcourant la liste
          Iterator itr = items.iterator();
          //parcour de la liste
          while(itr.hasNext())
          {
            //r�cup�ration des �l�ments de la liste dans item
            FileItem item = (FileItem) itr.next();
            String fieldName = item.getFieldName();
            
            //si l'�l�ment est la fichier
            if(fieldName.equals("fichierArtefact"))
            {     
              itemFichier = item;
              fullFile = new File(item.getName());
            }              
          }
        }
        catch (FileUploadException e1)
        {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        
        //Si l'utilisateur a entr� un nom de fichier
        if (fullFile.getName() != "")
        {
          String pathFichier ;
          //sauvegarde du nom du fichier dans la bd
          if(mArtefact!=null)
          {
            mArtefact.setNomFichier (fullFile.getName()) ;  
            pathFichier = mArtefact.getPathFichier() ;
          }
          //Artefact impr�vu
          else
          {
            mArtefactImprevu.setNomFichier (fullFile.getName()) ;  
            pathFichier = mArtefactImprevu.getPathFichier() ;
          }
          
          //Cr�ation du r�pertoire ou on t�l�charge le fichier si il n'existe pas
          (new File(getServletContext().getRealPath("/")+PATH_ARTEFACT+pathFichier)).mkdirs();
          //cr�ation du fichier 
          File savedFile = new File(getServletContext().getRealPath("/")+"/"+PATH_ARTEFACT+pathFichier, fullFile.getName());
          try
          {
            //Sauvegarde du fichier 
            itemFichier.write(savedFile);
          }  
          catch (Exception e)
          {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }       
        }
        
        //Message de confirmation pour l'ajout d'un artefact
        String nomArtefact ;
        String nomFichier ;
        if(mArtefact!=null)
        {
          	nomArtefact = mArtefact.getNom() ;
          	nomFichier = mArtefact.getNomFichier() ;
        }
        //Artefact impr�vu
        else
        {
          nomArtefact = mArtefactImprevu.getNom() ;
          nomFichier = mArtefactImprevu.getNomFichier() ;
        }
        ResourceBundle lMessages = ResourceBundle.getBundle("MessagesBundle") ; 
        String lMessage = lMessages.getString ("ajoutArtefactMessageConfirmation1") + " " + nomFichier + " " + lMessages.getString ("ajoutArtefactMessageConfirmation2") + " " + nomArtefact + " " + lMessages.getString ("ajoutArtefactMessageConfirmation3") ;
        getRequete ().setAttribute (CConstante.PAR_MESSAGE, lMessage) ;
        
      }
      else
      {
        // Affiche le formulaire d'ajout d'artefact.
        if(mArtefact != null)
        {
          getRequete ().setAttribute (CConstante.PAR_ARTEFACT, mArtefact) ;
        }
        //Artefact imprevu
        else
        {
          getRequete ().setAttribute (CConstante.PAR_ARTEFACT_IMPREVU, mArtefactImprevu) ;
        }
        getRequete ().setAttribute (CConstante.SES_SESSION, getSession()) ;
        
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
        getBaseDonnees ().commit () ;
        getBaseDonnees ().close () ;
      }
      catch (PersistenceException eException)
      {        
        eException.printStackTrace () ;
        throw new ServletException (CConstante.EXC_DECONNEXION) ;
      }
    }
    if (ok)
    {
      if(mArtefact != null)
      {
        return "..\\Tache\\TacheVisu?pTacheAVisualiser="+mArtefact.getTacheSortie().getId() ;
      }
      //Artefact imprevu
      else
      {
        return "..\\Tache\\TacheVisu?pTacheImprevueAVisualiser="+mArtefactImprevu.getTacheImprevueSortie().getId() ;
      }
    }
    else
    {
      return "..\\JSP\\Artefact\\TArtefactAjout.jsp"  ;
    }
  }
}
