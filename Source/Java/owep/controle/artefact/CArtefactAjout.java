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
  private MArtefact mArtefact ; // Données de l'artefact
  private MArtefactImprevue mArtefactImprevu ; // Données de l'artefact
  String PATH_ARTEFACT;
  private int idArtefact = -1;
  private int idArtefactImprevu = -1;
  
  /**
   * Récupère les données nécessaire au controleur dans la base de données. 
   * @throws ServletException Si une erreur survient durant la connexion
   * @see owep.controle.CControleurBase#initialiserBaseDonnees()
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    OQLQuery       lRequete ;       // Requête à réaliser sur la base
    QueryResults   lResultat ;      // Résultat de la requête sur la base
   
    //Cas ou l'idArtefact est passé dans l'url
    if(getRequete().getParameter(CConstante.PAR_ARTEFACT) != null)
    {
      getRequete().getSession().setAttribute(CConstante.PAR_ARTEFACT,getRequete().getParameter(CConstante.PAR_ARTEFACT));
      idArtefact = Integer.parseInt(getRequete().getParameter(CConstante.PAR_ARTEFACT));
    }
    //Cas ou l'idArtefact est passé dans l'url et est un artefact imprévu
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
        //Artefact Imprévu  	
        idArtefactImprevu = Integer.parseInt((String)(getRequete().getSession().getAttribute(CConstante.PAR_ARTEFACT_IMPREVU)));
      }
    }
    
    //(si ce n'est pas le cas cela sera fait dans traiter à cause des problème avec le formulaire de type "multipart/form-data")
      try
      {
        getBaseDonnees ().begin () ;
        if(idArtefact>-1)
        {
          // Récupère l'artefact concerné par l'upload.
          lRequete = getBaseDonnees ().getOQLQuery ("select ARTEFACT from owep.modele.execution.MArtefact ARTEFACT where mId = $1") ;
          lRequete.bind (idArtefact) ;
          lResultat = lRequete.execute () ;
          mArtefact = (MArtefact) lResultat.next () ;
          mArtefactImprevu = null ;
        }
        //Artefact imprévu
        else
        {
          // Récupère l'artefact concerné par l'upload.
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
   * Initialise le controleur et récupère les paramètres. 
   * @throws ServletException Si une erreur sur les paramètres survient
   * @see owep.controle.CControleurBase#initialiserParametres()
   */
  public void initialiserParametres () throws ServletException
  {
    PATH_ARTEFACT = getSession().getConfiguration().getPathArtefact(); 
  }
  
  /**
   * Redirige vers la JSP d'affichage du rapport d'activité si aucune donnée n'est postée ou valide
   * les données dans la base sinon. 
   * @return URL de la page vers laquelle doit être redirigé le client.
   * @throws ServletException Si une erreur survient dans le controleur
   * @see owep.controle.CControleurBase#traiter()
   */
  public String traiter () throws ServletException
  {
    boolean ok = false;
    try
    {
      HttpServletRequest request = getRequete() ;
      
      //Si on a validé le formulaire pour uploader un fichier
      //La méthode qui suis est utilisée car le formulaire est de type "multipart/form-data"
      //pour récupérer des fichiers. On ne peut donc pas utilisé les méthodes habituelles
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
          //on crée la liste des éléments du formulaire
          items = upload.parseRequest(request) ;
          //itérateur parcourant la liste
          Iterator itr = items.iterator();
          //parcour de la liste
          while(itr.hasNext())
          {
            //récupération des éléments de la liste dans item
            FileItem item = (FileItem) itr.next();
            String fieldName = item.getFieldName();
            
            //si l'élément est la fichier
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
        
        //Si l'utilisateur a entré un nom de fichier
        if (fullFile.getName() != "")
        {
          String pathFichier ;
          //sauvegarde du nom du fichier dans la bd
          if(mArtefact!=null)
          {
            mArtefact.setNomFichier (fullFile.getName()) ;  
            pathFichier = mArtefact.getPathFichier() ;
          }
          //Artefact imprévu
          else
          {
            mArtefactImprevu.setNomFichier (fullFile.getName()) ;  
            pathFichier = mArtefactImprevu.getPathFichier() ;
          }
          
          //Création du répertoire ou on télécharge le fichier si il n'existe pas
          (new File(getServletContext().getRealPath("/")+PATH_ARTEFACT+pathFichier)).mkdirs();
          //création du fichier 
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
        //Artefact imprévu
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
