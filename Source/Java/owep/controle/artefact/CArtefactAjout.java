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
  private MArtefact mArtefact ; // Donn�es de l'artefact
  String PATH_ARTEFACT;
  private int idArtefact = -1;
  
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
    if(getRequete().getParameter("pArtefact") != null)
    {
      idArtefact = Integer.parseInt(getRequete().getParameter("pArtefact"));
    }
    
    //si l'idArtefact est valoris� 
    //(si ce n'est pas le cas cel� sera fait dans traiter � cause des probl�me avec le formulaire de type "multipart/form-data")
    if(idArtefact >=0)
    {
      try
      {
        getBaseDonnees ().begin () ;
        // R�cup�re l'artefact concern� par l'upload.
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
    try
    {
      HttpServletRequest request = getRequete() ;
      
      	//Si on a valid� le formulaire pour uploader un fichier
        //La m�thode qui suis est utilis�e car le formulaire est de type "multipart/form-data"
        //pour r�cup�rer des fichiers. On ne peut donc pas utilis� les m�thodes habituelles
        //comme getParameter
        if(DiskFileUpload.isMultipartContent(request))
        {
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
              
              //si l'�l�ment est l'idArtefact
              if(fieldName.equals("pArtefact"))
              {
                //on initialise la BD
                initialiserBaseDonnees ();
              }
              
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
        if(fullFile.getName()!="")
        {
          //sauvegarde du nom du fichier dans la bd
          mArtefact.setNomFichier(fullFile.getName());  
          
          //Cr�ation du r�pertoire ou on t�l�charge le fichier si il n'existe pas
          (new File(getServletContext().getRealPath("/")+PATH_ARTEFACT+mArtefact.getPathFichier())).mkdirs();
          //cr�ation du fichier 
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
          // Met � jour la base de donn�es.
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
}
