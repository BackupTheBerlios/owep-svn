package owep.infrastructure ;


import java.util.ArrayList ;
import java.util.Enumeration ;
import java.util.Hashtable ;

import org.exolab.castor.jdo.Database ;
import org.exolab.castor.jdo.JDO ;
import org.exolab.castor.jdo.OQLQuery ;
import org.exolab.castor.jdo.PersistenceException ;
import org.exolab.castor.jdo.QueryResults ;
import org.xml.sax.Attributes ;
import org.xml.sax.ContentHandler ;
import org.xml.sax.Locator ;
import org.xml.sax.SAXException ;

import owep.infrastructure.localisation.LocalisateurIdentifiant ;
import owep.modele.processus.* ;


/**
 * Parser permettant de lire un fichier dpe et remplis la base de données.
 */
public class Parser implements ContentHandler
{
  private ArrayList          mBalise                  = new ArrayList () ;   // liste des balises
  private Database           mBaseDonnees             = null ;               // Connexion à la base
  // de données

  private Hashtable          mObjet                   = new Hashtable () ;   // liste des objets
  private Hashtable          mLienObjet               = new Hashtable () ;
  private Hashtable          mIdObjet                 = new Hashtable () ;
  private Hashtable          mIddpeObjet              = new Hashtable () ;

  private static String      BALISE_PROCESSUS         = "processus" ;
  private static String      BALISE_COMPOSANT         = "composant" ;        //"liste_composant" ;
  private static String      BALISE_ROLE              = "role" ;             //"liste_role" ;
  private static String      BALISE_PRODUIT           = "produit" ;          //"liste_produit" ;
  private static String      BALISE_DEFINITIONTRAVAIL = "definitionTravail" ; //"liste_definitionTravail"
  private static String      BALISE_ACTIVITE          = "activite" ;         //"liste_activite" ;

  private static String      OBJET_PROCESSUS          = "processus" ;
  private static String      OBJET_COMPOSANT          = "composant" ;
  private static String      OBJET_ROLE               = "role" ;
  private static String      OBJET_PRODUIT            = "produit" ;
  private static String      OBJET_DEFINITIONTRAVAIL  = "definitionTravail" ;
  private static String      OBJET_ACTIVITE           = "activite" ;
  private static String      OBJET_ENTREE             = "Entree" ;
  private static String      OBJET_SORTIE             = "Sortie" ;

  private int                nbComposant              = 0 ;
  private int                nbRole                   = 0 ;
  private int                nbProduit                = 0 ;
  private int                nbDefinitionTravail      = 0 ;
  private int                nbActivite               = 0 ;

  private int                mIdProcessus             = 0 ;
  private int                mIdComposant             = 0 ;
  private int                mIdDefinitionTravail     = 0 ;
  private int                mIdRole                  = 0 ;
  private int                mIdProduit               = 0 ;
  private int                mIdActivite              = 0 ;

  private MProcessus         mProcessus ;
  private MComposant         mComposant ;
  private MDefinitionTravail mDefinitionTravail ;
  private MActivite          mActivite ;
  private MRole              mRole ;
  private MProduit           mProduit ;

  private String             memoire                  = "" ;


  /**
   * @param pMaxIdProcessus
   */
  public Parser (int pMaxIdProcessus)
  {
    mIdProcessus = pMaxIdProcessus ;
  }

  /**
   * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
   */
  public void setDocumentLocator (Locator pArg0)
  {
  }

  /**
   * Initialise la connection à la base de données. Initialise les identifiants pour les objets du
   * processus.
   * 
   * @throws
   * @see org.xml.sax.ContentHandler#startDocument()
   */
  public void startDocument () throws SAXException
  {
    JDO lJdo ; // Charge le système de persistence avec la base de données
    OQLQuery lRequete ; // Requête à réaliser sur la base
    QueryResults lResultat ; // Résultat de la requête sur la base

    // Initialise la connexion à la base de données.
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
      throw new SAXException () ;
    }

    // Initialise les identifiants des objets
    // Processus passe en parametre

    // Activite
    try
    {
      mBaseDonnees.begin () ;
      lRequete = mBaseDonnees
        .getOQLQuery ("select ACTIVITE from owep.modele.processus.MActivite ACTIVITE") ;
      lResultat = lRequete.execute () ;

      MActivite lActivite ;
      while (lResultat.hasMore ())
      {
        lActivite = (MActivite) lResultat.next () ;
        if (mIdActivite < lActivite.getId ())
          mIdActivite = lActivite.getId () ;
      }
      mIdActivite++ ;

      //mBaseDonnees.commit () ;
    }
    catch (PersistenceException e)
    {
      e.printStackTrace () ;
      throw new SAXException () ;
    }

    // Composant
    try
    {
      //mBaseDonnees.begin () ;
      lRequete = mBaseDonnees
        .getOQLQuery ("select COMPOSANT from owep.modele.processus.MComposant COMPOSANT") ;
      lResultat = lRequete.execute () ;

      MComposant lComposant ;
      while (lResultat.hasMore ())
      {
        lComposant = (MComposant) lResultat.next () ;
        if (mIdComposant < lComposant.getId ())
          mIdComposant = lComposant.getId () ;
      }
      mIdComposant++ ;

      //mBaseDonnees.commit () ;
    }
    catch (PersistenceException e)
    {
      e.printStackTrace () ;
      throw new SAXException () ;
    }

    // Produit
    try
    {
      //mBaseDonnees.begin () ;
      lRequete = mBaseDonnees
        .getOQLQuery ("select PRODUIT from owep.modele.processus.MProduit PRODUIT") ;
      lResultat = lRequete.execute () ;

      MProduit lProduit ;
      while (lResultat.hasMore ())
      {
        lProduit = (MProduit) lResultat.next () ;
        if (mIdProduit < lProduit.getId ())
          mIdProduit = lProduit.getId () ;
      }
      mIdProduit++ ;

      //mBaseDonnees.commit () ;
    }
    catch (PersistenceException e)
    {
      e.printStackTrace () ;
      throw new SAXException () ;
    }

    // Role
    try
    {
      //mBaseDonnees.begin () ;
      lRequete = mBaseDonnees.getOQLQuery ("select ROLE from owep.modele.processus.MRole ROLE") ;
      lResultat = lRequete.execute () ;

      MRole lRole ;
      while (lResultat.hasMore ())
      {
        lRole = (MRole) lResultat.next () ;
        if (mIdRole < lRole.getId ())
          mIdRole = lRole.getId () ;
      }
      mIdRole++ ;

      //mBaseDonnees.commit () ;
    }
    catch (PersistenceException e)
    {
      e.printStackTrace () ;
      throw new SAXException () ;
    }

    // Definition travail
    try
    {
      //mBaseDonnees.begin () ;
      lRequete = mBaseDonnees
        .getOQLQuery ("select DEFINITIONTRAVAIL from owep.modele.processus.MDefinitionTravail DEFINITIONTRAVAIL") ;
      lResultat = lRequete.execute () ;

      MDefinitionTravail lDefinitionTravail ;
      while (lResultat.hasMore ())
      {
        lDefinitionTravail = (MDefinitionTravail) lResultat.next () ;
        if (mIdDefinitionTravail < lDefinitionTravail.getId ())
          mIdDefinitionTravail = lDefinitionTravail.getId () ;
      }
      mIdDefinitionTravail++ ;

      //mBaseDonnees.commit () ;
    }
    catch (PersistenceException e)
    {
      e.printStackTrace () ;
      throw new SAXException () ;
    }
  }

  /**
   * Enregistre les nouveaux objets de la base de données.
   * 
   * @see org.xml.sax.ContentHandler#endDocument()
   */
  public void endDocument () throws SAXException
  {
    OQLQuery lRequete ; // Requête à réaliser sur la base
    QueryResults lResultat ; // Résultat de la requête sur la base

    // Insertion dans la base de donnée
    try
    {
      //mBaseDonnees.begin () ;

      // Processus
      MProcessus lProcessus = (MProcessus) mObjet.get (OBJET_PROCESSUS) ;
      if (lProcessus != null)
      {
        mBaseDonnees.create (lProcessus) ;
      }

      //mBaseDonnees.commit () ;

      // Composant
      for (int i = 1 ; i <= nbComposant ; i++)
      {
        MComposant lComposant = (MComposant) mObjet.get (OBJET_COMPOSANT + i) ;
        if (lComposant != null)
        {
          //mBaseDonnees.begin () ;

          mObjet.put (OBJET_COMPOSANT + i, lComposant) ;

          mBaseDonnees.create (lComposant) ;
          //mBaseDonnees.commit () ;
        }
      }

      // Definition de travail
      for (int i = 1 ; i <= nbDefinitionTravail ; i++)
      {
        MDefinitionTravail lDefinitionTravail = (MDefinitionTravail) mObjet
          .get (OBJET_DEFINITIONTRAVAIL + i) ;
        if (lDefinitionTravail != null)
        {
          //mBaseDonnees.begin () ;

          mObjet.put (OBJET_DEFINITIONTRAVAIL + i, lDefinitionTravail) ;

          mBaseDonnees.create (lDefinitionTravail) ;
          //mBaseDonnees.commit () ;
        }
      }

      // Role
      for (int i = 1 ; i <= nbRole ; i++)
      {
        MRole lRole = (MRole) mObjet.get (OBJET_ROLE + i) ;
        if (lRole != null)
        {
          //mBaseDonnees.begin () ;

          mObjet.put (OBJET_ROLE + i, lRole) ;

          mBaseDonnees.create (lRole) ;
          //mBaseDonnees.commit () ;
        }
      }

      // Activite
      for (int i = 1 ; i <= nbActivite ; i++)
      {
        MActivite lActivite = (MActivite) mObjet.get (OBJET_ACTIVITE + i) ;
        if (lActivite != null)
        {
          //mBaseDonnees.begin () ;

          mObjet.put (OBJET_ACTIVITE + i, lActivite) ;

          mBaseDonnees.create (lActivite) ;
          //mBaseDonnees.commit () ;
        }
      }

      // Produit
      for (int i = 1 ; i <= nbProduit ; i++)
      {
        MProduit lProduit = (MProduit) mObjet.get (OBJET_PRODUIT + i) ;
        if (lProduit != null)
        {
          //mBaseDonnees.begin () ;

          mObjet.put (OBJET_PRODUIT + i, lProduit) ;

          mBaseDonnees.create (lProduit) ;
          //mBaseDonnees.commit () ;
        }
      }

      // Liens pour la table r01_act_prd_entree
      for (int i = 1 ; i <= nbActivite ; i++)
      {
        String cle = OBJET_ACTIVITE + i + OBJET_PRODUIT + OBJET_ENTREE ;
        ArrayList listProduit = (ArrayList) mLienObjet.get (cle) ;
        if (listProduit != null)
        {
          for (int j = 1 ; j <= nbProduit ; j++)
          {
            if (listProduit.contains (mIdObjet.get (OBJET_PRODUIT + j)))
            {
              //mBaseDonnees.begin () ;
              MProduit lProduit = (MProduit) mObjet.get (OBJET_PRODUIT + j) ;

              lRequete = mBaseDonnees
                .getOQLQuery ("select PRODUIT from owep.modele.processus.MProduit PRODUIT where mId = $1") ;
              lRequete.bind (lProduit.getId ()) ;
              lResultat = lRequete.execute () ;
              lProduit = (MProduit) lResultat.next () ;

              MActivite lActivite = (MActivite) mObjet.get (OBJET_ACTIVITE + i) ;
              lRequete = mBaseDonnees
                .getOQLQuery ("select ACTIVITE from owep.modele.processus.MActivite ACTIVITE where mId = $1") ;
              lRequete.bind (lActivite.getId ()) ;
              lResultat = lRequete.execute () ;
              lActivite = (MActivite) lResultat.next () ;

              lProduit.addActiviteSortie (lActivite) ;
              //mBaseDonnees.commit () ;
            }
          }
        }
      }

      // Liens pour la table r02_act_prd_sortie
      for (int i = 1 ; i <= nbActivite ; i++)
      {
        String cle = OBJET_ACTIVITE + i + OBJET_PRODUIT + OBJET_SORTIE ;
        ArrayList listProduit = (ArrayList) mLienObjet.get (cle) ;
        if (listProduit != null)
        {
          for (int j = 1 ; j <= nbProduit ; j++)
          {
            if (listProduit.contains (mIdObjet.get (OBJET_PRODUIT + j)))
            {
              //mBaseDonnees.begin () ;
              MProduit lProduit = (MProduit) mObjet.get (OBJET_PRODUIT + j) ;

              lRequete = mBaseDonnees
                .getOQLQuery ("select PRODUIT from owep.modele.processus.MProduit PRODUIT where mId = $1") ;
              lRequete.bind (lProduit.getId ()) ;
              lResultat = lRequete.execute () ;
              lProduit = (MProduit) lResultat.next () ;

              MActivite lActivite = (MActivite) mObjet.get (OBJET_ACTIVITE + i) ;
              lRequete = mBaseDonnees
                .getOQLQuery ("select ACTIVITE from owep.modele.processus.MActivite ACTIVITE where mId = $1") ;
              lRequete.bind (lActivite.getId ()) ;
              lResultat = lRequete.execute () ;
              lActivite = (MActivite) lResultat.next () ;

              lProduit.addActiviteEntree (lActivite) ;
              //mBaseDonnees.commit () ;
            }
          }
        }
      }

      // Liens pour la table r03_act_rol
      for (int i = 1 ; i <= nbRole ; i++)
      {
        String cle = OBJET_ROLE + i + OBJET_ACTIVITE ;
        ArrayList listProduit = (ArrayList) mLienObjet.get (cle) ;
        if (listProduit != null)
        {
          for (int j = 1 ; j <= nbActivite ; j++)
          {
            if (listProduit.contains (mIdObjet.get (OBJET_ACTIVITE + j)))
            {
              //mBaseDonnees.begin () ;
              MActivite lActivite = (MActivite) mObjet.get (OBJET_ACTIVITE + j) ;

              lRequete = mBaseDonnees
                .getOQLQuery ("select ACTIVITE from owep.modele.processus.MActivite ACTIVITE where mId = $1") ;
              lRequete.bind (lActivite.getId ()) ;
              lResultat = lRequete.execute () ;
              lActivite = (MActivite) lResultat.next () ;

              MRole lRole = (MRole) mObjet.get (OBJET_ROLE + i) ;
              lRequete = mBaseDonnees
                .getOQLQuery ("select ROLE from owep.modele.processus.MRole ROLE where mId = $1") ;
              lRequete.bind (lRole.getId ()) ;
              lResultat = lRequete.execute () ;
              lRole = (MRole) lResultat.next () ;

              lActivite.addRole (lRole) ;
              //mBaseDonnees.commit () ;
            }
          }
        }
      }

    }
    catch (PersistenceException e)
    {
      e.printStackTrace () ;
      throw new SAXException () ;
    }
    finally
    {
      try
      {
        mBaseDonnees.commit () ;
        mBaseDonnees.close () ;
      }
      catch (PersistenceException e1)
      {
        e1.printStackTrace () ;
        throw new SAXException () ;
      }
    }
  }

  /**
   * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String, java.lang.String)
   */
  public void startPrefixMapping (String pArg0, String pArg1) throws SAXException
  {
  }

  /**
   * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
   */
  public void endPrefixMapping (String pArg0) throws SAXException
  {
  }

  /**
   * Traitement a chaque fois qu'une balise ouvrante est trouvée.
   * 
   * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String,
   *      java.lang.String, org.xml.sax.Attributes)
   */
  public void startElement (String nameSpaceURI, String localName, String pArg2,
                            Attributes attributs) throws SAXException
  {
    mBalise.add (localName) ;

    // Processus
    if (localName.equals (BALISE_PROCESSUS))
    {
      mProcessus = new MProcessus (mIdProcessus) ;
    }

    // Composant
    if (localName.equals (BALISE_COMPOSANT))
    {
      mComposant = new MComposant (mIdComposant) ;
      mIdComposant++ ;
      nbComposant++ ;
    }

    // Definition travail
    if (localName.equals (BALISE_DEFINITIONTRAVAIL))
    {
      mDefinitionTravail = new MDefinitionTravail (mIdDefinitionTravail) ;
      mIdDefinitionTravail++ ;
      nbDefinitionTravail++ ;
    }

    // Activite
    if (localName.equals (BALISE_ACTIVITE))
    {
      mActivite = new MActivite (mIdActivite) ;
      mIdActivite++ ;
      nbActivite++ ;
    }

    // Role
    if (localName.equals (BALISE_ROLE))
    {
      mRole = new MRole (mIdRole) ;
      mIdRole++ ;
      nbRole++ ;
    }

    // Produit
    if (localName.equals (BALISE_PRODUIT))
    {
      mProduit = new MProduit (mIdProduit) ;
      mIdProduit++ ;
      nbProduit++ ;
    }
  }

  /**
   * Traitement pour chaque balise fermante.
   * 
   * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String,
   *      java.lang.String)
   */
  public void endElement (String nameSpaceUri, String localName, String rawName)
    throws SAXException
  {
    int i = mBalise.lastIndexOf (localName) ;
    mBalise.remove (i) ;

    // Processus
    if (localName.equals (BALISE_PROCESSUS))
    {
      mObjet.put (OBJET_PROCESSUS, mProcessus) ;
    }

    // Composant
    if (localName.equals (BALISE_COMPOSANT))
    {
      mComposant.setProcessus (mProcessus) ;
      mObjet.put (OBJET_COMPOSANT + nbComposant, mComposant) ;
      mIddpeObjet.put (mComposant.getIdDpe (), mComposant) ;
    }

    // Definition travail
    if (localName.equals (BALISE_DEFINITIONTRAVAIL))
    {
      mObjet.put (OBJET_DEFINITIONTRAVAIL + nbDefinitionTravail, mDefinitionTravail) ;
      mIddpeObjet.put (mDefinitionTravail.getIdDpe (), mDefinitionTravail) ;
    }

    // Activite
    if (localName.equals (BALISE_ACTIVITE))
    {
      mObjet.put (OBJET_ACTIVITE + nbActivite, mActivite) ;
      mIddpeObjet.put (mActivite.getIdDpe (), mActivite) ;
    }

    // Role
    if (localName.equals (BALISE_ROLE))
    {
      mObjet.put (OBJET_ROLE + nbRole, mRole) ;
      mIddpeObjet.put (mRole.getIdDpe (), mRole) ;
    }

    // Produit
    if (localName.equals (BALISE_PRODUIT))
    {
      mObjet.put (OBJET_PRODUIT + nbProduit, mProduit) ;
      mIddpeObjet.put (mProduit.getIdDpe (), mProduit) ;
    }
  }

  /**
   * Traitement pour le texte situé entre 2 balises.
   * 
   * @see org.xml.sax.ContentHandler#characters(char[], int, int)
   */
  public void characters (char [] ch, int start, int end) throws SAXException
  {
    String lAttribut = new String (ch, start, end) ;

    if (!lAttribut.contains ("\n"))
    {
      // Processus
      if (mBalise.contains (BALISE_PROCESSUS))
      {
        if (mBalise.contains ("id"))
        {
          mProcessus.setIdDpe (lAttribut) ;
          mIdObjet.put (OBJET_PROCESSUS, lAttribut) ;
        }

        if (mBalise.contains ("nom"))
        {
          mProcessus.setNom (lAttribut) ;
        }

        if (mBalise.contains ("nomAuteur"))
        {
          mProcessus.setNomAuteur (lAttribut) ;
        }

        if (mBalise.contains ("emailAuteur"))
        {
          mProcessus.setEmailAuteur (lAttribut) ;
        }

        if (mBalise.contains ("description"))
        {
          mProcessus.setDescription (lAttribut) ;
        }

      }

      // Composant
      if (mBalise.contains (BALISE_COMPOSANT))
      {
        if (mBalise.contains ("id"))
        {
          mIdObjet.put (OBJET_COMPOSANT + nbComposant, lAttribut) ;
          String tmp = mComposant.getIdDpe () ;
          mComposant.setIdDpe (tmp + lAttribut) ;
        }

        if (mBalise.contains ("nom"))
        {
          mComposant.setNom (lAttribut) ;
        }

        if (mBalise.contains ("nomAuteur"))
        {
          mComposant.setNomAuteur (lAttribut) ;
        }

        if (mBalise.contains ("emailAuteur"))
        {
          mComposant.setEmailAuteur (lAttribut) ;
        }

        if (mBalise.contains ("description"))
        {
          mComposant.setDescription (lAttribut) ;
        }
      }

      // Definition de travail
      if (mBalise.contains (BALISE_DEFINITIONTRAVAIL))
      {
        if (mBalise.contains ("id"))
        {
          lAttribut = mDefinitionTravail.getIdDpe () + lAttribut ;
          mIdObjet.put (OBJET_DEFINITIONTRAVAIL + nbDefinitionTravail, lAttribut) ;
          mDefinitionTravail.setIdDpe (lAttribut) ;
        }

        if (mBalise.contains ("nom"))
        {
          mDefinitionTravail.setNom (lAttribut) ;
        }

        if (mBalise.contains ("agregatComposant"))
        {
          Enumeration enum = mIddpeObjet.keys () ;
          while (enum.hasMoreElements ())
          {
            String key = (String) enum.nextElement () ;
            if (key.startsWith (lAttribut))
              lAttribut = key ;
          }
          MComposant lComposant = (MComposant) mIddpeObjet.get (lAttribut) ;
          mDefinitionTravail.setComposant (lComposant) ;
        }
      }

      // Activite
      if (mBalise.contains (BALISE_ACTIVITE))
      {
        if (mBalise.contains ("id"))
        {
          mIdObjet.put (OBJET_ACTIVITE + nbActivite, lAttribut) ;
          mActivite.setIdDpe (lAttribut) ;
        }

        if (mBalise.contains ("nom"))
        {
          mActivite.setNom (lAttribut) ;
        }

        if (mBalise.contains ("agregatDefinitionTravail"))
        {
          MDefinitionTravail lDefinitionTravail = (MDefinitionTravail) mIddpeObjet.get (lAttribut) ;
          if (lDefinitionTravail == null)
          {
            if (memoire.equals (""))
              memoire = lAttribut ;
            else
            {
              lDefinitionTravail = (MDefinitionTravail) mIddpeObjet.get (memoire + lAttribut) ;
              memoire = "" ;
            }
          }
          if(lDefinitionTravail != null)
            mActivite.setDefinitionsTravail (lDefinitionTravail) ;
        }

        if (mBalise.contains ("entreeProduit"))
        {
          String cle = OBJET_ACTIVITE + nbActivite + OBJET_PRODUIT + OBJET_ENTREE ;
          ArrayList valeur = (ArrayList) mLienObjet.get (cle) ;
          if (valeur == null)
            valeur = new ArrayList () ;
          valeur.add (lAttribut) ;
          mLienObjet.put (cle, valeur) ;
        }

        if (mBalise.contains ("sortieProduit"))
        {
          String cle = OBJET_ACTIVITE + nbActivite + OBJET_PRODUIT + OBJET_SORTIE ;
          ArrayList valeur = (ArrayList) mLienObjet.get (cle) ;
          if (valeur == null)
            valeur = new ArrayList () ;
          valeur.add (lAttribut) ;
          mLienObjet.put (cle, valeur) ;
        }
      }

      // Role
      if (mBalise.contains (BALISE_ROLE))
      {
        if (mBalise.contains ("id"))
        {
          mIdObjet.put (OBJET_ROLE + nbRole, lAttribut) ;
          mRole.setIdDpe (lAttribut) ;
        }

        if (mBalise.contains ("nom"))
        {
          mRole.setNom (lAttribut) ;
        }

        if (mBalise.contains ("agregatComposant"))
        {
          MComposant lComposant = (MComposant) mIddpeObjet.get (lAttribut) ;
          mRole.setComposant (lComposant) ;
        }

        if (mBalise.contains ("participationActivite"))
        {
          String cle = OBJET_ROLE + nbRole + OBJET_ACTIVITE ;
          ArrayList valeur = (ArrayList) mLienObjet.get (cle) ;
          if (valeur == null)
            valeur = new ArrayList () ;
          valeur.add (lAttribut) ;
          mLienObjet.put (cle, valeur) ;
        }
      }

      // Produit
      if (mBalise.contains (BALISE_PRODUIT))
      {
        if (mBalise.contains ("id"))
        {
          mIdObjet.put (OBJET_PRODUIT + nbProduit, lAttribut) ;
          mProduit.setIdDpe (lAttribut) ;
        }

        if (mBalise.contains ("nom"))
        {
          mProduit.setNom (lAttribut) ;
        }

        if (mBalise.contains ("agregatComposant"))
        {
          MComposant lComposant = (MComposant) mIddpeObjet.get (lAttribut) ;
          mProduit.setComposant (lComposant) ;
        }

        if (mBalise.contains ("responsabiliteRole"))
        {
          MRole lRole = (MRole) mIddpeObjet.get (lAttribut) ;
          mProduit.setResponsable (lRole) ;
        }

        /*
         * if (mBalise.contains ("entreeActivite")) { String cle = OBJET_PRODUIT + nbProduit +
         * OBJET_ACTIVITE + OBJET_ENTREE ; ArrayList valeur = (ArrayList) mLienObjet.get (cle) ; if
         * (valeur == null) valeur = new ArrayList () ; valeur.add (lAttribut) ; mLienObjet.put
         * (cle, valeur) ; }
         */

        /*
         * if (mBalise.contains ("sortieActivite")) { String cle = OBJET_PRODUIT + nbProduit +
         * OBJET_ACTIVITE + OBJET_SORTIE ; ArrayList valeur = (ArrayList) mLienObjet.get (cle) ; if
         * (valeur == null) valeur = new ArrayList () ; valeur.add (lAttribut) ; mLienObjet.put
         * (cle, valeur) ; }
         */
      }

    }
  }

  /**
   * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
   */
  public void ignorableWhitespace (char [] pArg0, int pArg1, int pArg2) throws SAXException
  {
  }

  /**
   * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
   */
  public void processingInstruction (String pArg0, String pArg1) throws SAXException
  {
  }

  /**
   * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
   */
  public void skippedEntity (String pArg0) throws SAXException
  {
  }

}