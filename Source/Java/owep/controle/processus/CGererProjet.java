package owep.controle.processus ;


import java.io.File ;
import java.io.FileOutputStream ;
import java.io.InputStream ;
import java.io.InputStreamReader ;
import java.io.OutputStreamWriter ;
import java.text.ParseException ;
import java.text.SimpleDateFormat ;
import java.util.ArrayList ;
import java.util.Date ;
import java.util.HashMap ;
import java.util.Iterator ;
import java.util.List ;
import java.util.ResourceBundle ;
import java.util.zip.ZipEntry ;
import java.util.zip.ZipFile ;

import javax.servlet.ServletException ;

import org.exolab.castor.jdo.OQLQuery ;
import org.exolab.castor.jdo.PersistenceException ;
import org.exolab.castor.jdo.QueryResults ;
import org.xml.sax.XMLReader ;
import org.xml.sax.helpers.XMLReaderFactory ;

import owep.controle.CControleurBase ;
import owep.infrastructure.Parser ;
import owep.infrastructure.fileupload.DiskFileUpload ;
import owep.infrastructure.fileupload.FileItem ;
import owep.infrastructure.fileupload.FileUploadException ;
import owep.modele.processus.MProcessus ;
import owep.modele.execution.MCollaborateur ;
import owep.modele.execution.MProjet ;
import owep.modele.execution.MTache;


/**
 * Controleur pour la création d'un projet.
 */
public class CGererProjet extends CControleurBase
{
  private String         mNom ;              // Nom du projet
  private String         mDescription ;      // Description du projet
  private String         mDateDebut ;        // Date de début du projet
  private String         mDateFin ;          // Date de fin du projet
  private float          mBudget     = 0 ;   // Budget du projet
  private String         mResponsable ;      // Identifiant du collaborateur responsable du
  // projet
  private String         mExtension ;        // Extension du fichier
  private int            mProcessus ;        // Identifiant du processus ou 0
  //private File mFichierProcessus = null ; // Fichier du processus
  FileItem               itemFichier = null ;

  private String         mErreur     = "" ;  // Texte d'erreur
  private ResourceBundle mMessage ;
  private String         mNouveau ;


  /**
   * Récupère les données nécessaire au controleur dans la base de données.
   * 
   * @throws ServletException Si une erreur survient durant la connexion
   * @see owep.controle.CControleurBase#initialiserBaseDonnees()
   */
  public void initialiserBaseDonnees () throws ServletException
  {
    OQLQuery lRequete ; // Requête à réaliser sur la base
    QueryResults lResultat ; // Résultat de la requête sur la base

    mMessage = getSession ().getMessages () ;

    try
    {
      getBaseDonnees ().begin () ;
      lRequete = getBaseDonnees ()
        .getOQLQuery (
                      "select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR") ;
      lResultat = lRequete.execute () ;
      ArrayList listCollaborateur = new ArrayList () ;
      while (lResultat.hasMore ())
      {
        listCollaborateur.add (lResultat.next ()) ;
      }
      getBaseDonnees ().commit () ;
      getRequete ().setAttribute ("mListCollaborateur", listCollaborateur) ;

    }
    catch (PersistenceException e)
    {
      mErreur = mMessage.getString ("projetErreur") ;
      e.printStackTrace () ;
    }
  }

  /**
   * Initialise le controleur et récupère les paramètres.
   * 
   * @throws ServletException Si une erreur sur les paramètres survient
   * @see owep.controle.CControleurBase#initialiserParametres()
   */
  public void initialiserParametres () throws ServletException
  {
    DiskFileUpload upload = new DiskFileUpload () ;
    if (DiskFileUpload.isMultipartContent (getRequete ()))
    {
      try
      {
        List items = upload.parseRequest (getRequete ()) ;
        Iterator itr = items.iterator () ;
        //parcour de la liste
        while (itr.hasNext ())
        {
          //récupération des éléments de la liste dans item
          FileItem item = (FileItem) itr.next () ;
          String fieldName = item.getFieldName () ;

          //si l'élément est le fichier
          if (fieldName.equals ("mFichierProcessus"))
          {
            itemFichier = item ;
            //mFichierProcessus = new File (item.getName ()) ;
          }

          // Si l'élément est le nom
          if (fieldName.equals ("mNom"))
          {
            mNom = item.getString () ;
          }

          // Si l'élément est la description
          if (fieldName.equals ("mDescription"))
            mDescription = item.getString () ;

          // Si l'élément est la date de debut
          if (fieldName.equals ("mDateDebut"))
            mDateDebut = item.getString () ;

          // Si l'élément est la date de fin
          if (fieldName.equals ("mDateFin"))
            mDateFin = item.getString () ;

          // Si l'élément est l'id du processus
          if (fieldName.equals ("mProcessus"))
          {
            mProcessus = Integer.parseInt (item.getString ()) ;
          }

          // Si l'élément est l'id du responsable de projet
          if (fieldName.equals ("mResponsable"))
            mResponsable = item.getString () ;

          if (fieldName.equals ("mBudget"))
          {
            String lBudget = item.getString () ;
            //System.out.println(lBudget);
            if (!lBudget.equals (""))
              mBudget = Float.parseFloat (lBudget) ;
          }

          if (fieldName.equals ("creation"))
            mNouveau = item.getString () ;

          if (fieldName.equals ("mExtension"))
            mExtension = item.getString () ;
        }
      }
      catch (FileUploadException e)
      {
        mErreur = mMessage.getString ("projetErreurTelechargement") ;
        e.printStackTrace () ;
      }
    }
    else
    {
      mNouveau = "" ;
      mErreur = "" ;
    }
  }

  /**
   * Redirige vers la JSP d'affichage de la page de création d'un projet.
   * 
   * @return URL de la page qui doit être affichée.
   * @throws ServletException si une erreur survient dans le controleur.
   * @see owep.controle.CControleurBase#traiter()
   */
  public String traiter () throws ServletException
  {
    OQLQuery lRequete ; // Requête à réaliser sur la base
    QueryResults lResultat ; // Résultat de la requête sur la base
    File fichierDpe = null ;

    if (mNouveau != null && mNouveau.equals ("1"))
    {
      // changement de format des dates
      SimpleDateFormat dateFormat = new SimpleDateFormat ("dd/MM/yyyy") ;
      Date d = null ;
      Date d2 = null ;
      try
      {
        d = dateFormat.parse (mDateDebut) ;
        d2 = dateFormat.parse (mDateFin) ;
      }
      catch (ParseException e)
      {
        mErreur = mMessage.getString ("projetErreurDate") ;
        //e.printStackTrace () ;
      }
      SimpleDateFormat dateFormat2 = new SimpleDateFormat ("yyyy-MM-dd") ;
      mDateDebut = dateFormat2.format (d) ;
      mDateFin = dateFormat2.format (d2) ;
    }

    if (!mErreur.equals (""))
    {
      getRequete ().setAttribute ("erreur", mErreur) ;
      return "..\\JSP\\Processus\\TGererProjet.jsp" ;
    }

    if (mNouveau != null && mNouveau.equals ("1"))
    {
      MProcessus lProcessus ;
      //      if (mProcessus == 0)
      //      {
      // Nouveau processus
      // Création du répertoire
      (new File (getServletContext ().getRealPath ("/") + "/Processus/Import")).mkdirs () ;
      // Création du fichier
      File savedFile = new File (getServletContext ().getRealPath ("/") + "/Processus/Import/",
                                 "temp") ;
      try
      {
        // Sauvegarde du fichier
        itemFichier.write (savedFile) ;

        if (mExtension.equals (".dpe"))
          fichierDpe = savedFile ;

        if (mExtension.equals (".dpc"))
        {
          ZipFile zip = new ZipFile (savedFile) ;
          ZipEntry entry = zip.getEntry ("processus.dpe") ;
          InputStream input = zip.getInputStream (entry) ;

          File f = new File (getServletContext ().getRealPath ("/") + "/Processus/Import/", "temp2") ;

          FileOutputStream fOut = new FileOutputStream (f) ;
          OutputStreamWriter out = new OutputStreamWriter (fOut, "UTF-16") ;

          InputStreamReader in = new InputStreamReader (input, "UTF-16") ;
          char [] c = new char [100] ;
          int i = in.read (c) ;
          while (i > 0)
          {
            String ligne ;
            ligne = String.valueOf (c, 0, i) ;
            out.write (ligne) ;
            i = in.read (c) ;
          }

          in.close () ;
          out.close () ;
          fOut.close () ;
          input.close () ;
          zip.close () ;

          fichierDpe = f ;
        }
      }
      catch (Exception e)
      {
        mErreur = mMessage.getString ("projetErreurTelechargement") ;
        e.printStackTrace () ;
        getRequete ().setAttribute ("erreur", mErreur) ;
        return "..\\JSP\\Processus\\TGererProjet.jsp" ;
      }

      // Recherche de l'id du nouveau processus
      int maxIdProcessus = 0 ;
      try
      {
        getBaseDonnees ().begin () ;
        lRequete = getBaseDonnees ()
          .getOQLQuery ("select PROCESSUS from owep.modele.processus.MProcessus PROCESSUS") ;
        lResultat = lRequete.execute () ;
        while (lResultat.hasMore ())
        {
          MProcessus tmpProcessus = (MProcessus) lResultat.next () ;
          if (maxIdProcessus < tmpProcessus.getId ())
          {
            maxIdProcessus = tmpProcessus.getId () ;
            lProcessus = tmpProcessus ;
          }
        }
        maxIdProcessus++ ;
        getBaseDonnees ().commit () ;
      }
      catch (PersistenceException e3)
      {
        e3.printStackTrace () ;
        mErreur = mMessage.getString ("projetErreur") ;
        getRequete ().setAttribute ("erreur", mErreur) ;
        return "..\\JSP\\Processus\\TGererProjet.jsp" ;
      }

      // Enregistrement des données du fichier en entrée à l'aide du parser
      XMLReader saxReader ;
      try
      {
        if (fichierDpe == null)
          throw new Exception ("Fichier erroné") ;

        saxReader = XMLReaderFactory.createXMLReader ("org.apache.xerces.parsers.SAXParser") ;
        saxReader.setContentHandler (new Parser (maxIdProcessus)) ;
        saxReader.parse (fichierDpe.toURI ().toString ()) ;
      }
      catch (Exception e1)
      {
        e1.printStackTrace () ;
        mErreur = mMessage.getString ("projetErreurParser") ;
        getRequete ().setAttribute ("erreur", mErreur) ;
        return "..\\JSP\\Processus\\TGererProjet.jsp" ;
      }

      mProcessus = maxIdProcessus ;
      //      }

      try
      {
        getBaseDonnees ().begin () ;

        // Récupération du responsable
        lRequete = getBaseDonnees ()
          .getOQLQuery (
                        "select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mId = $1") ;
        lRequete.bind (mResponsable) ;
        lResultat = lRequete.execute () ;
        MCollaborateur lCollaborateur = (MCollaborateur) lResultat.next () ;

        // Récupération du processus
        lRequete = getBaseDonnees ()
          .getOQLQuery (
                        "select PROCESSUS from owep.modele.processus.MProcessus PROCESSUS where mId = $1") ;
        lRequete.bind (mProcessus) ;
        lResultat = lRequete.execute () ;
        lProcessus = (MProcessus) lResultat.next () ;

        // Récupération de l'id du projet
        lRequete = getBaseDonnees ()
          .getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET") ;
        lResultat = lRequete.execute () ;
        MProjet lProjet ;
        int maxIdProjet = 0 ;
        while (lResultat.hasMore ())
        {
          lProjet = (MProjet) lResultat.next () ;
          if (maxIdProjet < lProjet.getId ())
          {
            maxIdProjet = lProjet.getId () ;
          }
        }
        maxIdProjet++ ;

        // Création du projet
        lProjet = new MProjet (maxIdProjet) ;
        lProjet.setNom (mNom) ;
        lProjet.setDateDebutPrevue (java.sql.Date.valueOf (mDateDebut)) ;
        lProjet.setDateFinPrevue (java.sql.Date.valueOf (mDateFin)) ;
        lProjet.setEtat(MTache.ETAT_NON_DEMARRE);
        lProjet.setDescription (mDescription) ;
        lProjet.setBudget (mBudget) ;
        lProjet.setProcessus (lProcessus) ;
        lProjet.setChefProjet (lCollaborateur) ; // initialise le chef de projet et ajoute le chef
        // au col du projet

        getBaseDonnees ().create (lProjet) ;
        getBaseDonnees ().commit () ;

        // Ajout du projet à la liste des projets possibles si utilisateur connecté est le
        // responsable
        if (getSession ().getIdCollaborateur () == lCollaborateur.getId ())
        {
          ArrayList listProjetPossible = getSession ().getListProjetPossible () ;
          listProjetPossible.add (lProjet) ;
          getSession ().setListProjetPossible (listProjetPossible) ;
        }

        // Renommage du fichier processus
        File f = new File (getServletContext ().getRealPath ("/") + "/Processus/Import/", "temp") ;
        f.renameTo (new File (getServletContext ().getRealPath ("/") + "/Processus/Import/",
                              lProjet.getId () + "_" + lProjet.getNom () + mExtension)) ;

        if (mExtension.equals (".dpc"))
        {
          f = new File (getServletContext ().getRealPath ("/") + "/Processus/Import/", "temp") ;
          f.delete () ;
        }

        mErreur = mMessage.getString ("projetMessageCreer") ;
        getRequete ().setAttribute ("idProjet", String.valueOf (maxIdProjet)) ;
      }
      catch (PersistenceException e2)
      {
        mErreur = mMessage.getString ("projetErreur") ;
        e2.printStackTrace () ;
      }
      finally
      {
        try
        {
          getBaseDonnees ().close () ;
        }
        catch (PersistenceException e3)
        {
          mErreur = mMessage.getString ("projetErreur") ;
          e3.printStackTrace () ;
        }
      }
    }
    else
    {
      mErreur = "" ;
      try
      {
        getBaseDonnees ().close () ;
      }
      catch (PersistenceException e)
      {
        mErreur = mMessage.getString ("projetErreur") ;
        e.printStackTrace () ;
      }
    }

    getRequete ().setAttribute ("erreur", mErreur) ;
    return "..\\JSP\\Processus\\TGererProjet.jsp" ;

  }

}