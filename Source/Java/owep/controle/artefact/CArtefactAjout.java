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

/**
 * @author Victor Nancy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CArtefactAjout extends CControleurBase
{
  private MArtefact mArtefact ; // Données de l'artefact
  String PATH_ARTEFACT;
  private int idArtefact = -1;
  
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
    if(getRequete().getParameter("pArtefact") != null)
    {
      idArtefact = Integer.parseInt(getRequete().getParameter("pArtefact"));
    }
    
    //si l'idArtefact est valorisé 
    //(si ce n'est pas le cas celà sera fait dans traiter à cause des problème avec le formulaire de type "multipart/form-data")
    if(idArtefact >=0)
    {
      try
      {
        getBaseDonnees ().begin () ;
        // Récupère l'artefact concerné par l'upload.
        lRequete = getBaseDonnees ().getOQLQuery ("select ARTEFACT from owep.modele.execution.MArtefact ARTEFACT where mId = $1") ;
        lRequete.bind (idArtefact) ;
        lResultat = lRequete.execute () ;
        mArtefact = (MArtefact) lResultat.next () ;
      
        getBaseDonnees ().commit () ;
      }
      catch (Exception eException)
      {
        eException.printStackTrace () ;
        throw new ServletException (CConstante.EXC_TRAITEMENT) ;
      }
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
    try
    {
      HttpServletRequest request = getRequete() ;
      
      	//Si on a validé le formulaire pour uploader un fichier
        //La méthode qui suis est utilisée car le formulaire est de type "multipart/form-data"
        //pour récupérer des fichiers. On ne peut donc pas utilisé les méthodes habituelles
        //comme getParameter
        if(DiskFileUpload.isMultipartContent(request))
        {
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
              
              //si l'élément est l'idArtefact
              if(fieldName.equals("pArtefact"))
              {
                //on initialise la BD
                initialiserBaseDonnees ();
              }
              
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
        if(fullFile.getName()!="")
        {
          //sauvegarde du nom du fichier dans la bd
          mArtefact.setNomFichier(fullFile.getName());  
          
          //Création du répertoire ou on télécharge le fichier si il n'existe pas
          (new File(getServletContext().getRealPath("/")+PATH_ARTEFACT+mArtefact.getPathFichier())).mkdirs();
          //création du fichier 
          File savedFile = new File(getServletContext().getRealPath("/")+"/"+PATH_ARTEFACT+mArtefact.getPathFichier(), fullFile.getName());
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
          // Met à jour la base de données.
          getBaseDonnees ().begin () ;
          getBaseDonnees ().update (mArtefact) ;
          getBaseDonnees ().commit () ;
        
        return "..\\Tache\\TacheVisu?pTacheAVisualiser="+mArtefact.getTacheSortie().getId() ;
      }
      else
      {
        // Affiche le formulaire d'ajout d'artefact.
        getRequete ().setAttribute (CConstante.PAR_ARTEFACT, mArtefact) ;
        getRequete ().setAttribute (CConstante.SES_SESSION, getSession()) ;
        
        return "..\\JSP\\Artefact\\TArtefactAjout.jsp"  ;
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
