package owep.supervision;

import java.util.Vector;
import org.exolab.castor.jdo.Database;
import org.exolab.castor.jdo.JDO;
import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.QueryResults;
import owep.infrastructure.localisation.LocalisateurIdentifiant;
import owep.modele.execution.MCollaborateur;
import owep.modele.execution.MIndicateur;
import owep.modele.execution.MProjet;


/**
 * Description de la classe.
 */
public class Supervision
{
  private Database mBaseDonnees = null ; // Connexion à la base de données
  private OQLQuery lRequete ; // Requête à réaliser sur la base
  private QueryResults lResultat ; // Résultat de la requête sur la base
  
  public Supervision ()
  {
  }
  
  public String identify (String pLogin, String pPass)
  {
    MCollaborateur lCollaborateur = new MCollaborateur();
    
    try
    { 
      openDatabase() ;
      mBaseDonnees.begin () ;
      lRequete = mBaseDonnees.getOQLQuery ("select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mUtilisateur = $1 and mMotDePasse = $2") ;
      lRequete.bind (pLogin) ;
      lRequete.bind (pPass) ;
      lResultat = lRequete.execute () ;  
    
      lCollaborateur = (MCollaborateur) lResultat.next () ;

      mBaseDonnees.commit();
      closeDatabase() ;
    }
    catch (Exception e)
    {
      e.printStackTrace () ;
    }
    if (lCollaborateur.getDroit() == 0)
      return "collaborateur" ; 
    else
      return "chef de projet" ; 
  }
  
  public boolean createProject (String pName, int pBudget, String pStartDate, String pFinalDate)
  {
    try
    { 
      openDatabase() ;
      mBaseDonnees.begin () ;
      
      // Création du projet
      MProjet lProjet = new MProjet();
      lProjet.setNom(pName);
      lProjet.setDateDebutPrevue(java.sql.Date.valueOf(pStartDate));
      lProjet.setDateFinPrevue(java.sql.Date.valueOf(pFinalDate));
      lProjet.setBudget(pBudget);
      
      mBaseDonnees.create(lProjet);

      mBaseDonnees.commit();
      closeDatabase() ;
    }
    catch (Exception e)
    {
      e.printStackTrace () ;
      return false ;
    }  
    return true ;
  }
  
  public boolean createUser (String pNom, String pPrenom, String pType , String pLogin ,String pMotPasse, String pEmail)
  {
    try
    { 
      openDatabase() ;
      mBaseDonnees.begin () ;
      
      int lDroit ;
      if (pType.equals("chef de projet"))
        lDroit = 1 ;
      else
        lDroit = 0 ;
      
      // Création de l'utilisateur
      MCollaborateur lCollaborateur = new MCollaborateur();
      lCollaborateur.setNom(pNom);
      lCollaborateur.setPrenom(pPrenom);
      lCollaborateur.setDroit(lDroit);
      lCollaborateur.setUtilisateur(pLogin);
      lCollaborateur.setMotDePasse(pMotPasse);
      lCollaborateur.setEmail(pEmail);
      
      mBaseDonnees.create(lCollaborateur);

      mBaseDonnees.commit();
      closeDatabase() ;
    }
    catch (Exception e)
    {
      e.printStackTrace () ;
      return false ;
    }  
    return true ;
  }
  
  public boolean removeUser ( String pLogin)
  {
    MCollaborateur lCollaborateur = new MCollaborateur();
    
    try
    { 
      openDatabase() ;
      mBaseDonnees.begin () ;
      lRequete = mBaseDonnees.getOQLQuery ("select COLLABORATEUR from owep.modele.execution.MCollaborateur COLLABORATEUR where mUtilisateur = $1") ;
      lRequete.bind (pLogin) ;
      lResultat = lRequete.execute () ;  
    
      lCollaborateur = (MCollaborateur) lResultat.next () ;

      mBaseDonnees.remove(lCollaborateur);
      
      mBaseDonnees.commit();
      closeDatabase() ;
    }
    catch (Exception e)
    {
      e.printStackTrace () ;
      return false ;
    }
    return true ;
  }
  
  public Vector listProject()
  {
    MProjet lProjet = new MProjet();
    Vector lListeProjets = new Vector() ;
    
    try
    { 
      openDatabase() ;
      mBaseDonnees.begin () ;
      lRequete = mBaseDonnees.getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET") ;
      lResultat = lRequete.execute () ;  
    
      while (lResultat.hasMore ())
      {
        lProjet = (MProjet) lResultat.next () ;
        lListeProjets.add(lProjet) ;
      }
      
      mBaseDonnees.commit();
      closeDatabase() ;
    }
    catch (Exception e)
    {
      e.printStackTrace () ;
      return null ;
    }
    return lListeProjets ;
  }
  
  public boolean createIndicator( String pNom, String pDescription, String pUnite, String pNomProjet)
  {
    try
    {  
      MProjet lProjet ;
      
      // recuperation du projet
      openDatabase() ;
      mBaseDonnees.begin () ;
      lRequete = mBaseDonnees.getOQLQuery ("select PROJET from owep.modele.execution.MProjet PROJET where mNom = $1") ;
      lRequete.bind (pNomProjet) ;
      lResultat = lRequete.execute () ;  
      lProjet = (MProjet) lResultat.next () ; 
      
      // Création de l'indicateur
      MIndicateur lIndicateur = new MIndicateur();
      lIndicateur.setNom(pNom);
      lIndicateur.setDescription(pDescription);
      if (!pUnite.equals(""))
        lIndicateur.setUnite(pUnite);
      lIndicateur.setProjet(lProjet);     
      lProjet.addIndicateur(lIndicateur) ;
      mBaseDonnees.create(lIndicateur);
      mBaseDonnees.commit();
      closeDatabase() ;
    }
    catch (Exception e)
    {
      e.printStackTrace () ;
      return false ;
    }  
    return true ;
  }
  
  
  private void openDatabase()
  {
    JDO lJdo ; // Charge le système de persistence avec la base de données
    if(mBaseDonnees == null || mBaseDonnees.isClosed())
    {
      // Initie la connexion à la base de données.
      try
      {
        JDO.loadConfiguration (LocalisateurIdentifiant.LID_BDCONFIGURATION) ;
        lJdo = new JDO (LocalisateurIdentifiant.LID_BDNOM) ;
        mBaseDonnees = lJdo.getDatabase () ;
        mBaseDonnees.setAutoStore (false) ;
      }
      catch (Exception eException)
      {
        eException.printStackTrace () ;
      }
    }
  }
  
  private void closeDatabase()
  {
    try
    {
      if (lResultat != null)
      {
        lResultat.close();
      }
      if (lRequete!= null) 
      {
        lRequete.close();
      }
      if (mBaseDonnees != null)
      {    
        mBaseDonnees.close () ; 
      }
          
    }
    catch (Exception ex)
    {
      ex.printStackTrace() ;
    }  
  }
}

