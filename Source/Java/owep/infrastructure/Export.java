package owep.infrastructure ;


import java.io.BufferedWriter ;
import java.io.File ;
import java.io.FileInputStream ;
import java.io.FileWriter ;
import java.io.IOException ;
import java.io.InputStream ;
import java.io.InputStreamReader ;
import java.text.SimpleDateFormat;
import java.util.ArrayList ;
import java.util.Iterator ;
import java.util.zip.ZipEntry ;
import java.util.zip.ZipFile ;

import org.exolab.castor.jdo.Database ;
import org.exolab.castor.jdo.JDO ;
import org.exolab.castor.jdo.OQLQuery ;
import org.exolab.castor.jdo.PersistenceException ;
import org.exolab.castor.jdo.QueryResults ;

import owep.infrastructure.localisation.LocalisateurIdentifiant ;
import owep.modele.execution.* ;
import owep.modele.processus.* ;


/**
 * Classe permettant l'export d'un projet
 */
public class Export
{
  private FileWriter     mFichier ;
  private MProjet        mProjet ;
  private BufferedWriter out ;
  private String         mChemin ;

  private JDO            lJdo ;               // Charge le système de persistence avec la base de
  // données
  private Database       mBaseDonnees = null ; // Connexion à la base de données
  private OQLQuery       lRequete ;           // Requête à réaliser sur la base
  private QueryResults   lResultat ;          // Résultat de la requête sur la base


  public Export (MProjet pProjet, String pChemin) throws IOException
  {
    mProjet = pProjet ;
    mChemin = pChemin ;

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

    ouvrirFichier () ;
    inclureDpe () ;
    objet () ;
    lienProjet () ;
    lienProjetProcessus () ;
    fermerFichier () ;

    try
    {
      mBaseDonnees.close () ;
    }
    catch (PersistenceException e)
    {
      e.printStackTrace () ;
    }
  }

  private void ouvrirFichier () throws IOException
  {
    (new File (mChemin + "Export")).mkdirs () ;
    File f = new File (mChemin + "Export/" + mProjet.getId () + "_" + mProjet.getNom () + ".xml") ;
    mFichier = new FileWriter (f) ;
    out = new BufferedWriter (mFichier) ;
    out.write ("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>") ;
    out.newLine () ;
    out.newLine () ;
  }

  private void fermerFichier () throws IOException
  {
    out.write ("</exportProjet>\n") ;
    out.flush () ;
    out.close () ;
    mFichier.close () ;
  }

  private void inclureDpe () throws IOException
  {
    out.write ("<exportProjet>\n") ;

    InputStream input = null ;
    File f ;

    f = new File (mChemin + "Import/" + mProjet.getId () + "_" + mProjet.getNom () + ".dpe") ;
    if (f.exists ())
      input = new FileInputStream (f) ;

    f = new File (mChemin + "Import/" + mProjet.getId () + "_" + mProjet.getNom () + ".dpc") ;
    if (f.exists ())
    {
      ZipFile zip = new ZipFile (f) ;
      ZipEntry entry = zip.getEntry ("processus.dpe") ;
      input = zip.getInputStream (entry) ;
    }

    InputStreamReader in = new InputStreamReader (input, "UTF-16") ;
    char [] c = new char [100] ;
    int i = in.read (c) ;
    boolean enleve = false ;
    while (i > 0)
    {
      String ligne ;
      ligne = String.valueOf (c, 0, i) ;
      if (ligne.contains ("?>") && !enleve)
      {
        ligne = ligne.substring (ligne.lastIndexOf ("?>") + 2) ;
        enleve = true ;
      }
      out.write (ligne) ;
      i = in.read (c) ;
    }
  }

  private void objet () throws IOException
  {
    ArrayList liste ;
    Iterator it ;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    out.write ("\t<elementProjet>") ;
    out.newLine () ;

    // Condition definition travail
    out.write ("\t\t<listeConditionDefTravail>") ;
    out.newLine () ;
    out.write ("\t\t</listeConditionDefTravail>") ;
    out.newLine () ;

    // Projet
    out.write ("\t\t<projet>") ;
    out.newLine () ;
    out.write ("\t\t\t<id>" + mProjet.getId () + "</id>") ;
    out.newLine () ;
    out.write ("\t\t\t<nom>" + mProjet.getNom () + "</nom>") ;
    out.newLine () ;
    out.write ("\t\t\t<description>" + mProjet.getDescription () + "</description>") ;
    out.newLine () ;
    out.write ("\t\t\t<dateDebut>" + dateFormat.format(mProjet.getDateDebutPrevue ()) + "</dateDebut>") ;
    out.newLine () ;
    out.write ("\t\t\t<dateFin>" + dateFormat.format(mProjet.getDateFinPrevue())+ "</dateFin>") ;
    out.newLine () ;
    out.write ("\t\t\t<budget>" + mProjet.getBudget () + "</budget>") ;
    out.newLine () ;
    out.write ("\t\t</projet>") ;
    out.newLine () ;

    // Metrique
    out.write ("\t\t<listeMetriques>\n") ;
    liste = mProjet.getListeIndicateurs () ;
    if (liste == null)
      liste = new ArrayList () ;
    ArrayList listeMesure = new ArrayList () ;
    Iterator itMesure ;
    it = liste.iterator () ;
    while (it.hasNext ())
    {
      MIndicateur lIndicateur = (MIndicateur) it.next () ;
      listeMesure = lIndicateur.getListeMesures () ;
      if (listeMesure == null)
        listeMesure = new ArrayList () ;
      itMesure = listeMesure.iterator () ;
      while (itMesure.hasNext ())
      {
        MMesureIndicateur mesure = (MMesureIndicateur) itMesure.next () ;
        out.write ("\t\t\t<eltMetriques>\n") ;
        out.write ("\t\t\t\t<id>" + mesure.getId () + "</id>\n") ;
        out.write ("\t\t\t\t<nom>" + lIndicateur.getNom () + "</nom>\n") ;
        out.write ("\t\t\t\t<description>" + lIndicateur.getDescription () + "</description>\n") ;
        out.write ("\t\t\t\t<unite>" + lIndicateur.getUnite () + "</unite>\n") ;
        out.write ("\t\t\t\t<valeur>" + mesure.getValeur () + "</valeur>\n") ;
        out.write ("\t\t\t\t<commentaire>" + mesure.getCommentaire () + "</commentaire>\n") ;
        out.write ("\t\t\t\t<idMembre>" + mesure.getCollaborateur ().getId () + "</idMembre>\n") ;
        out.write ("\t\t\t</eltMetriques>\n") ;
      }
    }
    out.write ("\t\t</listeMetriques>\n") ;

    // Risque
    out.write ("\t\t<listeRisques>") ;
    out.newLine () ;
    try
    {
      mBaseDonnees.begin () ;
      lRequete = mBaseDonnees
        .getOQLQuery ("select RISQUE from owep.modele.execution.MRisque RISQUE where mProjet = $1") ;
      lRequete.bind (mProjet.getId ()) ;
      lResultat = lRequete.execute () ;

      while (lResultat.hasMore ())
      {
        MRisque lRisque = (MRisque) lResultat.next () ;
        out.write ("\t\t\t<eltRisque>\n") ;
        out.write ("\t\t\t\t<id>" + lRisque.getId () + "</id>\n") ;
        out.write ("\t\t\t\t<nom>" + lRisque.getNom () + "</nom>\n") ;
        out.write ("\t\t\t\t<description>" + lRisque.getDescription () + "</description>\n") ;
        out.write ("\t\t\t\t<priorite>" + lRisque.getPriorite () + "</priorite>\n") ;
        out.write ("\t\t\t\t<impact>" + lRisque.getImpact () + "</impact>\n") ;
        out.write ("\t\t\t\t<etat>" + lRisque.getEtat () + "</etat>") ;
        out.write ("\t\t\t</eltRisque>\n") ;
      }

      mBaseDonnees.commit () ;
    }
    catch (PersistenceException e)
    {
      e.printStackTrace () ;
    }
    out.write ("\t\t</listeRisques>") ;
    out.newLine () ;

    // Membre
    out.write ("\t\t<listeMembres>") ;
    out.newLine () ;
    liste = mProjet.getListeCollaborateurs () ;
    if (liste == null)
      liste = new ArrayList () ;
    it = liste.iterator () ;
    while (it.hasNext ())
    {
      MCollaborateur lCollaborateur = (MCollaborateur) it.next () ;
      out.write ("\t\t\t<eltMembre>\n") ;
      out.write ("\t\t\t\t<id>" + lCollaborateur.getId () + "</id>\n") ;
      out.write ("\t\t\t\t<nom>" + lCollaborateur.getNom () + "</nom>\n") ;
      out.write ("\t\t\t\t<prenom>" + lCollaborateur.getPrenom () + "</prenom>\n") ;
      out.write ("\t\t\t\t<login>" + lCollaborateur.getUtilisateur () + "</login>\n") ;
      out.write ("\t\t\t\t<adresse>" + lCollaborateur.getAdresse () + "</adresse>\n") ;
      out.write ("\t\t\t\t<telephone>" + lCollaborateur.getTelephone () + "</telephone>\n") ;
      out.write ("\t\t\t\t<email>" + lCollaborateur.getEmail () + "</email>\n") ;
      out.write ("\t\t\t</eltMembre>\n") ;
    }
    out.write ("\t\t</listeMembres>") ;
    out.newLine () ;

    // Iteration
    out.write ("\t\t<listeIterations>") ;
    out.newLine () ;
    liste = mProjet.getListeIterations () ;
    if (liste == null)
      liste = new ArrayList () ;
    it = liste.iterator () ;
    while (it.hasNext ())
    {
      MIteration lIteration = (MIteration) it.next () ;
      out.write ("\t\t\t<eltIteration>\n") ;
      out.write ("\t\t\t\t<id>" + lIteration.getId () + "</id>\n") ;
      out.write ("\t\t\t\t<numero>" + lIteration.getNumero () + "</numero>\n") ;
      out.write ("\t\t\t\t<dateDebutPrevue>");
      if(lIteration.getDateDebutPrevue() != null)
      out.write(dateFormat.format(lIteration.getDateDebutPrevue ()));
      out.write("</dateDebutPrevue>\n") ;
      out.write ("\t\t\t\t<dateDebutReelle>");
      if(lIteration.getDateDebutReelle() != null)
        out.write(dateFormat.format(lIteration.getDateDebutReelle()));
      out.write("</dateDebutReelle>\n") ;
      out.write ("\t\t\t\t<dateFinPrevue>");
      if(lIteration.getDateFinPrevue() != null)
        out.write(dateFormat.format(lIteration.getDateFinPrevue ()));
      out.write("</dateFinPrevue>\n") ;
      out.write ("\t\t\t\t<dateFinReelle>");
      if(lIteration.getDateFinReelle() != null)
        out.write(dateFormat.format(lIteration.getDateFinReelle()));
      out.write("</dateFinReelle>\n") ;
      out.write ("\t\t\t</eltIteration>\n") ;
    }
    out.write ("\t\t</listeIterations>") ;
    out.newLine () ;

    // Tache
    out.write ("\t\t<listeTaches>") ;
    out.newLine () ;
    int maxIdTache = 0;
    liste = mProjet.getListeIterations () ;
    if (liste == null)
      liste = new ArrayList () ;
    it = liste.iterator () ;
    ArrayList listeTache = new ArrayList () ;
    ArrayList listeTImprevue = new ArrayList();
    while (it.hasNext ())
    {
      MIteration lIteration = (MIteration) it.next () ;
      listeTache.addAll (lIteration.getListeTaches ()) ;
      listeTImprevue.addAll(lIteration.getListeTachesImprevues());
    }
    it = listeTache.iterator () ;
    while (it.hasNext ())
    {
      MTache lTache = (MTache) it.next () ;
      if(maxIdTache < lTache.getId())
        maxIdTache = lTache.getId();
      out.write ("\t\t\t<eltTache>\n") ;
      out.write ("\t\t\t\t<id>" + lTache.getId () + "</id>\n") ;
      out.write ("\t\t\t\t<nom>" + lTache.getNom () + "</nom>\n") ;
      out.write ("\t\t\t\t<description>" + lTache.getDescription () + "</description>\n") ;
      out.write ("\t\t\t\t<etat>") ;
      // etat : <!-- prete - commence - arrete -->
      switch (lTache.getEtat ())
      {
        case MTache.ETAT_CREEE :
          out.write ("CREEE") ;
          break ;
        case MTache.ETAT_EN_COURS :
          out.write ("EN_COURS") ;
          break ;
        case MTache.ETAT_NON_DEMARRE :
          out.write ("NON_DEMARRE") ;
          break ;
        case MTache.ETAT_SUSPENDU :
          out.write ("SUSPENDU") ;
          break ;
        case MTache.ETAT_TERMINE :
          out.write ("TERMINE") ;
          break ;
      }
      out.write ("</etat>\n") ;
      out.write ("\t\t\t\t<chargePrevue>" + lTache.getChargeInitiale () + "</chargePrevue>\n") ;
      out.write ("\t\t\t\t<tempsPasse>" + lTache.getTempsPasse () + "</tempsPasse>\n") ;
      out.write ("\t\t\t\t<resteAPasser>" + lTache.getResteAPasser () + "</resteAPasser>\n") ;
      out.write ("\t\t\t\t<dateDebutPrevue>");
      if(lTache.getDateDebutPrevue() != null)
        out.write(dateFormat.format(lTache.getDateDebutPrevue ()));
      out.write("</dateDebutPrevue>\n") ;
      out.write ("\t\t\t\t<dateDebutReelle>");
      if(lTache.getDateDebutReelle() != null)
        out.write(dateFormat.format(lTache.getDateDebutReelle()));
      out.write("</dateDebutReelle>\n") ;
      out.write ("\t\t\t\t<dateFinPrevue>");
      if(lTache.getDateFinPrevue() != null)
        out.write(dateFormat.format(lTache.getDateFinPrevue ()));
      out.write("</dateFinPrevue>\n") ;
      out.write ("\t\t\t\t<dateFinReelle>");
      if(lTache.getDateFinReelle() != null)
        out.write(dateFormat.format(lTache.getDateFinReelle()));
      out.write("</dateFinReelle>\n") ;
      out.write ("\t\t\t</eltTache>\n") ;
    }
    it = listeTImprevue.iterator () ;
    while (it.hasNext ())
    {
      MTacheImprevue lTache = (MTacheImprevue) it.next () ;
      if(maxIdTache < lTache.getId())
        maxIdTache = lTache.getId();
      out.write ("\t\t\t<eltTache>\n") ;
      out.write ("\t\t\t\t<id>" + (lTache.getId ()+maxIdTache) + "</id>\n") ;
      out.write ("\t\t\t\t<nom>" + lTache.getNom () + "</nom>\n") ;
      out.write ("\t\t\t\t<description>" + lTache.getDescription () + "</description>\n") ;
      out.write ("\t\t\t\t<etat>") ;
      // etat : <!-- prete - commence - arrete -->
      switch (lTache.getEtat ())
      {
        case MTache.ETAT_CREEE :
          out.write ("CREEE") ;
          break ;
        case MTache.ETAT_EN_COURS :
          out.write ("EN_COURS") ;
          break ;
        case MTache.ETAT_NON_DEMARRE :
          out.write ("NON_DEMARRE") ;
          break ;
        case MTache.ETAT_SUSPENDU :
          out.write ("SUSPENDU") ;
          break ;
        case MTache.ETAT_TERMINE :
          out.write ("TERMINE") ;
          break ;
      }
      out.write ("</etat>\n") ;
      out.write ("\t\t\t\t<chargePrevue>" + lTache.getChargeInitiale () + "</chargePrevue>\n") ;
      out.write ("\t\t\t\t<tempsPasse>" + lTache.getTempsPasse () + "</tempsPasse>\n") ;
      out.write ("\t\t\t\t<resteAPasser>" + lTache.getResteAPasser () + "</resteAPasser>\n") ;
      out.write ("\t\t\t\t<dateDebutPrevue>");
      if(lTache.getDateDebutPrevue() != null)
        out.write(dateFormat.format(lTache.getDateDebutPrevue ()));
      out.write("</dateDebutPrevue>\n") ;
      out.write ("\t\t\t\t<dateDebutReelle>");
      if(lTache.getDateDebutReelle() != null)
        out.write(dateFormat.format(lTache.getDateDebutReelle ()));
      out.write("</dateDebutReelle>\n") ;
      out.write ("\t\t\t\t<dateFinPrevue>");
      if(lTache.getDateFinPrevue() != null)
        out.write(dateFormat.format(lTache.getDateFinPrevue ()));
      out.write("</dateFinPrevue>\n") ;
      out.write ("\t\t\t\t<dateFinReelle>");
      if(lTache.getDateFinReelle() != null)
        out.write(dateFormat.format(lTache.getDateFinReelle ()));
      out.write("</dateFinReelle>\n") ;
      out.write ("\t\t\t</eltTache>\n") ;
    }
    out.write ("\t\t</listeTaches>") ;
    out.newLine () ;

    // Condition tache
    out.write ("\t\t<listeConditionTache>") ;
    out.newLine () ;
    it = listeTache.iterator () ;
    while (it.hasNext ())
    {
      MTache lTache = (MTache) it.next () ;
      ArrayList listeCondition = lTache.getListeConditions () ;
      Iterator itCondition = listeCondition.iterator () ;
      while (itCondition.hasNext ())
      {
        MCondition lCondition = (MCondition) itCondition.next () ;
        out.write ("\t\t\t<eltCondition>\n") ;
        out.write ("\t\t\t\t<id>" + lCondition.getId () + "</id>\n") ;
        out.write ("\t\t\t\t<type>");
        switch(lCondition.getEtat()){
          case MTache.ETAT_EN_COURS :
            out.write("commence");
          break;
          case MTache.ETAT_TERMINE :
            out.write("fini");
          break;
        }
        out.write("</type>\n") ;
        out.write ("\t\t\t</eltCondition>\n") ;
      }
    }
    out.write ("\t\t</listeConditionTache>") ;
    out.newLine () ;

    // Tache collaborative
    out.write ("\t\t<listeTacheCollaboratives>") ;
    out.newLine () ;
    out.write ("\t\t</listeTacheCollaboratives>") ;
    out.newLine () ;

    // Artefact
    out.write ("\t\t<listeArtefacts>") ;
    out.newLine () ;
    liste = mProjet.getListeArtefacts () ;
    if (liste == null)
      liste = new ArrayList () ;
    it = liste.iterator () ;
    while (it.hasNext ())
    {
      MArtefact lArtefact = (MArtefact) it.next () ;
      out.write ("\t\t\t<eltArtefact>\n") ;
      out.write ("\t\t\t\t<id>" + lArtefact.getId () + "</id>\n") ;
      out.write ("\t\t\t\t<nom>" + lArtefact.getNom () + "</nom>\n") ;
      out.write ("\t\t\t\t<description>" + lArtefact.getDescription () + "</description>\n") ;
      out.write ("\t\t\t\t<livrable></livrable>\n") ;
      // TODO livrable <!-- true si le produit est livré au client -->
      out.write ("\t\t\t\t<etat></etat>\n") ;
      // TODO etat <!-- prete ou pas -->
      out.write ("\t\t\t</eltArtefact>\n") ;
    }
    out.write ("\t\t</listeArtefacts>") ;
    out.newLine () ;

    // Probleme
    out.write ("\t\t<listeProblemes>") ;
    out.newLine () ;
    liste = new ArrayList () ;
    it = listeTache.iterator () ;
    if (liste == null)
      liste = new ArrayList () ;
    while (it.hasNext ())
    {
      MTache lTache = (MTache) it.next () ;
      ArrayList listeProbleme = lTache.getListeProblemesEntrees () ;
      Iterator itProbleme = listeProbleme.iterator () ;
      while (itProbleme.hasNext ())
      {
        MProbleme lProbleme = (MProbleme) itProbleme.next () ;
        if (!liste.contains (lProbleme))
          liste.add (lProbleme) ;
      }
    }
    it = liste.iterator () ;
    while (it.hasNext ())
    {
      MProbleme lProbleme = (MProbleme) it.next () ;
      out.write ("\t\t\t<eltProbleme>\n") ;
      out.write ("\t\t\t\t<id>" + lProbleme.getId () + "</id>\n") ;
      out.write ("\t\t\t\t<nom>" + lProbleme.getNom () + "</nom>\n") ;
      out.write ("\t\t\t\t<cause>" + lProbleme.getDescription () + "</cause>\n") ;
      out.write ("\t\t\t\t<dateDebut>");
      if(lProbleme.getDateIdentification() != null)
        out.write(dateFormat.format(lProbleme.getDateIdentification ()));
      out.write("</dateDebut>") ;
      out.write ("\t\t\t\t<dateFin>");
      if(lProbleme.getDateCloture() != null)
        out.write(dateFormat.format(lProbleme.getDateCloture ()));
      out.write("</dateFin>") ;
      out.write ("\t\t\t</eltProbleme>\n") ;
    }
    out.write ("\t\t</listeProblemes>") ;
    out.newLine () ;

    out.write ("\t</elementProjet>") ;
    out.newLine () ;
  }

  private void lienProjet () throws IOException
  {
    ArrayList liste ;
    Iterator it ;

    out.write ("\t<lienProjet>") ;
    out.newLine () ;

    // Definition travail / Condition definition travail / porte sur
    out.write ("\t\t<listeDefTravailconditionDefTravail_debuteSi>") ;
    out.newLine () ;
    out.write ("\t\t</listeDefTravailconditionDefTravail_debuteSi>") ;
    out.newLine () ;

    // Definition travail / Condition definition travail / debute si
    out.write ("\t\t<listeDefTravailconditionDefTravail_porteSur>") ;
    out.newLine () ;
    out.write ("\t\t</listeDefTravailconditionDefTravail_porteSur>") ;
    out.newLine () ;

    // Projet / Iteration
    out.write ("\t\t<listeProjetIteration>") ;
    out.newLine () ;
    liste = mProjet.getListeIterations () ;
    if (liste == null)
      liste = new ArrayList () ;
    if (liste.size () > 0)
    {
      it = liste.iterator () ;
      out.write ("\t\t\t<ProjetIteration>\n") ;
      out.write ("\t\t\t\t<idProjet>" + mProjet.getId () + "</idProjet>\n") ;
      out.write ("\t\t\t\t<listeIdIteration>\n") ;
      while (it.hasNext ())
      {
        MIteration lIteration = (MIteration) it.next () ;
        out.write ("\t\t\t\t\t<id>" + lIteration.getId () + "</id>\n") ;
      }
      out.write ("\t\t\t\t</listeIdIteration>\n") ;
      out.write ("\t\t\t</ProjetIteration>\n") ;
    }
    out.write ("\t\t</listeProjetIteration>") ;
    out.newLine () ;

    // Projet / Metrique
    out.write ("\t\t<listeIterationMetrique>\n") ;
    liste = mProjet.getListeIterations () ;
    it = liste.iterator () ;
    while (it.hasNext ())
    {
      MIteration ite = (MIteration) it.next () ;
      if (ite.getListeMesures ().size () > 0)
      {
        out.write ("\t\t\t<IterationMetrique>\n") ;
        out.write ("\t\t\t\t<idIteration>" + ite.getId () + "</idIteration>\n") ;
        out.write ("\t\t\t\t<listeIdMetrique>\n") ;
        ArrayList listeMesure = ite.getListeMesures () ;
        Iterator itmes = listeMesure.iterator () ;
        while (itmes.hasNext ())
        {
          MMesureIndicateur mes = (MMesureIndicateur) itmes.next () ;
          out.write ("\t\t\t\t\t<id>" + mes.getId () + "</id>\n") ;
        }
        out.write ("\t\t\t\t</listeIdMetrique>\n") ;
        out.write ("\t\t\t</IterationMetrique>\n") ;
      }
    }
    out.write ("\t\t</listeIterationMetrique>\n") ;

    // Projet / Risque
    out.write ("\t\t<listeProjetRisque>") ;
    out.newLine () ;
    try
    {
      mBaseDonnees.begin () ;
      lRequete = mBaseDonnees
        .getOQLQuery ("select RISQUE from owep.modele.execution.MRisque RISQUE where mProjet = $1") ;
      lRequete.bind (mProjet.getId ()) ;
      lResultat = lRequete.execute () ;

      if (lResultat.size () > 0)
      {
        out.write ("\t\t\t<ProjetRisque>\n") ;
        out.write ("\t\t\t\t<idProjet>" + mProjet.getId () + "</idProjet>\n") ;
        out.write ("\t\t\t\t<listeIdRisque>\n") ;
        while (lResultat.hasMore ())
        {
          MRisque lRisque = (MRisque) lResultat.next () ;
          out.write ("\t\t\t\t<id>" + lRisque.getId () + "</id>\n") ;
        }
        out.write ("\t\t\t\t</listeIdRisque>\n") ;
        out.write ("\t\t\t</ProjetRisque>\n") ;
      }

      mBaseDonnees.commit () ;
    }
    catch (PersistenceException e)
    {
      e.printStackTrace () ;
    }
    out.write ("\t\t</listeProjetRisque>") ;
    out.newLine () ;

    // Iteration / Tache
    out.write ("\t\t<listeIterationTache>") ;
    out.newLine () ;
    liste = mProjet.getListeIterations () ;
    if (liste == null)
      liste = new ArrayList () ;
    it = liste.iterator () ;
    while (it.hasNext ())
    {
      MIteration lIteration = (MIteration) it.next () ;
      ArrayList listeTache = lIteration.getListeTaches () ;
      if (listeTache.size () > 0)
      {
        out.write ("\t\t\t<IterationTache>\n") ;
        out.write ("\t\t\t\t<idIteration>" + lIteration.getId () + "</idIteration>\n") ;
        out.write ("\t\t\t\t<listeIdTache>\n") ;
        Iterator itTache = listeTache.iterator () ;
        while (itTache.hasNext ())
        {
          MTache lTache = (MTache) itTache.next () ;
          out.write ("\t\t\t\t<id>" + lTache.getId () + "</id>\n") ;
        }
        out.write ("\t\t\t\t</listeIdTache>\n") ;
        out.write ("\t\t\t</IterationTache>\n") ;
      }
    }
    out.write ("\t\t</listeIterationTache>") ;
    out.newLine () ;

    // Iteration / Tache collaborative
    out.write ("\t\t<listeIterationTacheCol>") ;
    out.newLine () ;
    out.write ("\t\t</listeIterationTacheCol>") ;
    out.newLine () ;

    // Projet / Membre
    out.write ("\t\t<listeProjetMembre>") ;
    out.newLine () ;
    liste = mProjet.getListeCollaborateurs () ;
    if (liste == null)
      liste = new ArrayList () ;
    if (liste.size () > 0)
    {
      it = liste.iterator () ;
      out.write ("\t\t\t<ProjetMembre>\n") ;
      out.write ("\t\t\t\t<idProjet>" + mProjet.getId () + "</idProjet>\n") ;
      out.write ("\t\t\t\t<listeMembre>\n") ;
      while (it.hasNext ())
      {
        MCollaborateur lCollaborateur = (MCollaborateur) it.next () ;
        out.write ("\t\t\t\t\t<id>" + lCollaborateur.getId () + "</id>\n") ;
      }
      out.write ("\t\t\t\t</listeMembre>\n") ;
      out.write ("\t\t\t</ProjetMembre>\n") ;
    }
    out.write ("\t\t</listeProjetMembre>") ;
    out.newLine () ;

    // Membre / Artefact
    out.write ("\t\t<listeMembreArtefact>") ;
    out.newLine () ;
    liste = mProjet.getListeCollaborateurs () ;
    if (liste == null)
      liste = new ArrayList () ;
    it = liste.iterator () ;
    while (it.hasNext ())
    {
      MCollaborateur lCollaborateur = (MCollaborateur) it.next () ;
      ArrayList listeArtefact = lCollaborateur.getListeArtefacts () ;
      if (listeArtefact.size () > 0)
      {
        out.write ("\t\t\t<MembreArtefact>\n") ;
        out.write ("\t\t\t\t<idMembre>" + lCollaborateur.getId () + "</idMembre>\n") ;
        out.write ("\t\t\t\t<listeArtefact>\n") ;
        Iterator itArtefact = listeArtefact.iterator () ;
        while (itArtefact.hasNext ())
        {
          MArtefact lArtefact = (MArtefact) itArtefact.next () ;
          out.write ("\t\t\t\t<id>" + lArtefact.getId () + "</id>\n") ;
        }
        out.write ("\t\t\t\t</listeArtefact>\n") ;
        out.write ("\t\t\t</MembreArtefact>\n") ;
      }
    }
    out.write ("\t\t</listeMembreArtefact>") ;
    out.newLine () ;

    // Membre / Tache
    out.write ("\t\t<listeMembreTache>") ;
    out.newLine () ;
    liste = mProjet.getListeCollaborateurs () ;
    if (liste == null)
      liste = new ArrayList () ;
    it = liste.iterator () ;
    ArrayList listTaches = new ArrayList () ;
    while (it.hasNext ())
    {
      MCollaborateur lCollaborateur = (MCollaborateur) it.next () ;
      ArrayList listeTache = lCollaborateur.getListeTaches () ;
      if (listeTache.size () > 0)
      {
        listTaches.addAll (listeTache) ;
        out.write ("\t\t\t<MembreTache>\n") ;
        out.write ("\t\t\t\t<idMembre>" + lCollaborateur.getId () + "</idMembre>\n") ;
        out.write ("\t\t\t\t<listeTache>\n") ;
        Iterator itTache = listeTache.iterator () ;
        while (itTache.hasNext ())
        {
          MTache lTache = (MTache) itTache.next () ;
          out.write ("\t\t\t\t<id>" + lTache.getId () + "</id>\n") ;
        }
        out.write ("\t\t\t\t</listeTache>\n") ;
        out.write ("\t\t\t</MembreTache>\n") ;
      }
    }
    out.write ("\t\t</listeMembreTache>") ;
    out.newLine () ;

    // Tache / Debute si / Condition
    out.write ("\t\t<listeTache_DebuteSi_Condition>") ;
    out.newLine () ;
    it = listTaches.iterator () ;
    while (it.hasNext ())
    {
      MTache lTache = (MTache) it.next () ;
      ArrayList listeCondition = lTache.getListeConditions () ;
      if (listeCondition.size () > 0)
      {
        out.write ("\t\t\t<Tache_DebuteSi_Condition>\n") ;
        out.write ("\t\t\t\t<idTache>" + lTache.getId () + "</idTache>\n") ;
        out.write ("\t\t\t\t<listeCondition>\n") ;
        Iterator itCondition = listeCondition.iterator () ;
        while (itCondition.hasNext ())
        {
          MCondition lCondition = (MCondition) itCondition.next () ;
          out.write ("\t\t\t\t\t<id>" + lCondition.getId () + "</id>\n") ;
        }
        out.write ("\t\t\t\t</listeCondition>\n") ;
        out.write ("\t\t\t</Tache_DebuteSi_Condition>\n") ;
      }
    }
    out.write ("\t\t</listeTache_DebuteSi_Condition>") ;
    out.newLine () ;

    // Condition / Porte sur / Tache
    out.write ("\t\t<listeCondition_PorteSur_Tache>") ;
    out.newLine () ;
    out.write ("\t\t</listeCondition_PorteSur_Tache>") ;
    out.newLine () ;

    // Tache collaborative / Debute si / Condition
    out.write ("\t\t<listeTacheCol_DebuteSi_Condition>") ;
    out.newLine () ;
    out.write ("\t\t</listeTacheCol_DebuteSi_Condition>") ;
    out.newLine () ;

    // Condition / Porte sur / Tache collaborative
    out.write ("\t\t<listeCondition_PorteSur_TacheCol>") ;
    out.newLine () ;
    out.write ("\t\t</listeCondition_PorteSur_TacheCol>") ;
    out.newLine () ;

    // Tache collaborative / Artefact / Entree
    out.write ("\t\t<listeTacheColArtefact_Entree>") ;
    out.newLine () ;
    out.write ("\t\t</listeTacheColArtefact_Entree>") ;
    out.newLine () ;

    // Tache collaborative / Artefact / Sortie
    out.write ("\t\t<listeTacheColArtefact_Sortie>") ;
    out.newLine () ;
    out.write ("\t\t</listeTacheColArtefact_Sortie>") ;
    out.newLine () ;

    // Tache / Artefact / Entree
    out.write ("\t\t<listeTacheArtefact_Entree>") ;
    out.newLine () ;
    it = listTaches.iterator () ;
    while (it.hasNext ())
    {
      MTache lTache = (MTache) it.next () ;
      ArrayList listeArtefact = lTache.getListeArtefactsEntrees () ;
      if (listeArtefact.size () > 0)
      {
        out.write ("\t\t\t<TacheArtefact_Entree>\n") ;
        out.write ("\t\t\t\t<idTache>" + lTache.getId () + "</idTache>\n") ;
        out.write ("\t\t\t\t<listeArtefact>\n") ;
        Iterator itArtefact = listeArtefact.iterator () ;
        while (itArtefact.hasNext ())
        {
          MArtefact lArtefact = (MArtefact) itArtefact.next () ;
          out.write ("\t\t\t\t\t<id>" + lArtefact.getId () + "</id>\n") ;
        }
        out.write ("\t\t\t\t</listeArtefact>\n") ;
        out.write ("\t\t\t</TacheArtefact_Entree>\n") ;
      }
    }
    out.write ("\t\t</listeTacheArtefact_Entree>") ;
    out.newLine () ;

    // Tache / Artefact / Sortie
    out.write ("\t\t<listeTacheArtefact_Sortie>") ;
    out.newLine () ;
    it = listTaches.iterator () ;
    while (it.hasNext ())
    {
      MTache lTache = (MTache) it.next () ;
      ArrayList listeArtefact = lTache.getListeArtefactsEntrees () ;
      if (listeArtefact.size () > 0)
      {
        out.write ("\t\t\t<TacheArtefact_Sortie>\n") ;
        out.write ("\t\t\t\t<idTache>" + lTache.getId () + "</idTache>\n") ;
        out.write ("\t\t\t\t<listeArtefact>\n") ;
        Iterator itArtefact = listeArtefact.iterator () ;
        while (itArtefact.hasNext ())
        {
          MArtefact lArtefact = (MArtefact) itArtefact.next () ;
          out.write ("\t\t\t\t\t<id>" + lArtefact.getId () + "</id>\n") ;
        }
        out.write ("\t\t\t\t</listeArtefact>\n") ;
        out.write ("\t\t\t</TacheArtefact_Sortie>\n") ;
      }
    }
    out.write ("\t\t</listeTacheArtefact_Sortie>") ;
    out.newLine () ;

    // Membre / Tache collaborative / Responsable
    out.write ("\t\t<listeMembreTacheCollaborative_Responsable>") ;
    out.newLine () ;
    out.write ("\t\t</listeMembreTacheCollaborative_Responsable>") ;
    out.newLine () ;

    // Membre / Tache collaborative / Realise
    out.write ("\t\t<MembreTacheCollaborative_Realise>") ;
    out.newLine () ;
    out.write ("\t\t</MembreTacheCollaborative_Realise>") ;
    out.newLine () ;

    // Tache / Provoque / Probleme
    out.write ("\t\t<Tache_Provoque_Probleme>") ;
    out.newLine () ;
    it = listTaches.iterator () ;
    ArrayList listProblemes = new ArrayList () ;
    while (it.hasNext ())
    {
      MTache lTache = (MTache) it.next () ;
      ArrayList listeProbleme = lTache.getListeProblemesEntrees () ;
      if (listeProbleme.size () > 0)
      {
        out.write ("\t\t\t<Tache>\n") ;
        out.write ("\t\t\t\t<id>" + lTache.getId () + "</id>\n") ;
        out.write ("\t\t\t\t<listeProbleme>\n") ;
        Iterator itProbleme = listeProbleme.iterator () ;
        while (itProbleme.hasNext ())
        {
          MProbleme lProbleme = (MProbleme) itProbleme.next () ;
          out.write ("\t\t\t\t\t<id>" + lProbleme.getId () + "</id>\n") ;
          if (!listProblemes.contains (lProbleme))
            listProblemes.add (lProbleme) ;
        }
        out.write ("\t\t\t\t</listeProbleme>\n") ;
        out.write ("\t\t\t</Tache>\n") ;
      }
    }
    out.write ("\t\t</Tache_Provoque_Probleme>") ;
    out.newLine () ;

    // Probleme / Resout / Tache
    out.write ("\t\t<Probleme_Resoud_Tache>") ;
    out.newLine () ;
    it = listProblemes.iterator () ;
    while (it.hasNext ())
    {
      MProbleme lProbleme = (MProbleme) it.next () ;
      ArrayList listeTache = lProbleme.getListeTacheResout () ;
      if (listeTache.size () > 0)
      {
        out.write ("\t\t\t<Probleme>\n") ;
        out.write ("\t\t\t\t<id>" + lProbleme.getId () + "</id>\n") ;
        out.write ("\t\t\t\t<listeTache>\n") ;
        Iterator itTache = listeTache.iterator () ;
        while (itTache.hasNext ())
        {
          MTache lTache = (MTache) itTache.next () ;
          out.write ("\t\t\t\t\t<id>" + lTache.getId () + "</id>\n") ;
        }
        out.write ("\t\t\t\t</listeTache>\n") ;
        out.write ("\t\t\t</Probleme>\n") ;
      }
    }
    out.write ("\t\t</Probleme_Resoud_Tache>") ;
    out.newLine () ;

    // Tache collaborative / Provoque / Probleme
    out.write ("\t\t<TacheCol_Provoque_Probleme>") ;
    out.newLine () ;
    out.write ("\t\t</TacheCol_Provoque_Probleme>") ;
    out.newLine () ;

    // Probleme / Resout / Tache collaborative
    out.write ("\t\t<Probleme_Resoud_TacheCol>") ;
    out.newLine () ;
    out.write ("\t\t</Probleme_Resoud_TacheCol>") ;
    out.newLine () ;

    out.write ("\t</lienProjet>") ;
    out.newLine () ;
  }

  private void lienProjetProcessus () throws IOException
  {
    MProcessus lProcessus = mProjet.getProcessus () ;
    ArrayList liste ;
    Iterator it ;

    // Récupération d'objet
    liste = lProcessus.getListeComposants () ;
    if (liste == null)
      liste = new ArrayList () ;
    it = liste.iterator () ;
    ArrayList listeProduit = new ArrayList () ;
    ArrayList listeDefinitionTravail = new ArrayList () ;
    ArrayList listeRoleProcessus = new ArrayList();
    while (it.hasNext ())
    {
      MComposant lComposant = (MComposant) it.next () ;
      listeProduit.addAll (lComposant.getListeProduits ()) ;
      listeDefinitionTravail.addAll (lComposant.getListeDefinitionsTravail ()) ;
      
      ArrayList listeRoleP = lComposant.getListeRoles();
      Iterator itRoleP = listeRoleP.iterator();
      while(itRoleP.hasNext()){
        MRole roleP = (MRole) itRoleP.next();
        if(listeRoleProcessus.size() == 0){
          listeRoleProcessus.addAll(listeRoleP);
          System.out.println(listeRoleP.size());
        }
        else{
          if(!listeRoleProcessus.contains(roleP))
            listeRoleProcessus.add(roleP);
        }
      }
    }
    ArrayList listeActivite = new ArrayList () ;
    it = listeDefinitionTravail.iterator () ;
    while (it.hasNext ())
    {
      MDefinitionTravail lDefinitionTravail = (MDefinitionTravail) it.next () ;
      listeActivite.addAll (lDefinitionTravail.getListeActivites ()) ;
    }
    

    out.write ("\t<lienProjetProcessus>") ;
    out.newLine () ;

    // Processus / Projet
    out.write ("\t\t<ProcessusProjet>\n") ;
    out.write ("\t\t\t<idProcessus>" + lProcessus.getIdDpe () + "</idProcessus>\n") ;
    out.write ("\t\t\t<IdProjet>" + mProjet.getId () + "</IdProjet>\n") ;
    out.write ("\t\t</ProcessusProjet>\n") ;

    // Produit / Artefact
    out.write ("\t\t<listeProduitArtefact>\n") ;
    it = listeProduit.iterator () ;
    while (it.hasNext ())
    {
      MProduit lProduit = (MProduit) it.next () ;
      liste = lProduit.getListeArtefacts () ;
      if (liste == null)
        liste = new ArrayList () ;
      if (liste.size () > 0)
      {
        out.write ("\t\t\t<ProduitArtefact>") ;
        out.write ("\t\t\t\t<idProduit>" + lProduit.getIdDpe () + "</idProduit>") ;
        out.write ("\t\t\t\t<listeIdArtefact>") ;
        Iterator itArtefact = liste.iterator () ;
        while (itArtefact.hasNext ())
        {
          MArtefact lArtefact = (MArtefact) itArtefact.next () ;
          out.write ("\t\t\t\t\t<id>" + lArtefact.getId () + "</id>") ;
        }
        out.write ("\t\t\t\t</listeIdArtefact>") ;
        out.write ("\t\t\t</ProduitArtefact>") ;
      }
    }
    out.write ("\t\t</listeProduitArtefact>\n") ;

    // Activite / Tache
    out.write ("\t\t<listeActiviteTache>\n") ;
    it = listeActivite.iterator () ;
    while (it.hasNext ())
    {
      MActivite lActivite = (MActivite) it.next () ;
      liste = lActivite.getListeTaches () ;
      if (liste == null)
        liste = new ArrayList () ;
      if (liste.size () > 0)
      {
        out.write ("\t\t\t<ActiviteTache>\n") ;
        out.write ("\t\t\t\t<idActivite>" + lActivite.getIdDpe () + "</idActivite>\n") ;
        out.write ("\t\t\t\t<listeIdTache>\n") ;
        Iterator itTache = liste.iterator () ;
        while (itTache.hasNext ())
        {
          MTache lTache = (MTache) itTache.next () ;
          out.write ("\t\t\t\t\t<id>" + lTache.getId () + "</id>\n") ;
        }
        out.write ("\t\t\t\t</listeIdTache>\n") ;
        out.write ("\t\t\t</ActiviteTache>\n") ;
      }
    }
    out.write ("\t\t</listeActiviteTache>\n") ;

    // Membre / Role
    out.write ("\t\t<MembreRole>\n") ;
    liste = mProjet.getListeCollaborateurs () ;
    if (liste == null)
      liste = new ArrayList () ;
    it = liste.iterator () ;
    while (it.hasNext ())
    {
      MCollaborateur lCollaborateur = (MCollaborateur) it.next () ;
      ArrayList listeRole = lCollaborateur.getListeRoles () ;
      if (listeRole == null)
        listeRole = new ArrayList () ;
      if (listeRole.size () > 0)
      {
        out.write ("\t\t\t<Membre>\n") ;
        out.write ("\t\t\t\t<id>" + lCollaborateur.getId () + "</id>\n") ;
        out.write ("\t\t\t\t<listeRole>\n") ;
        Iterator itRole = listeRole.iterator () ;
        while (itRole.hasNext ())
        {
          MRole lRole = (MRole) itRole.next () ;
          //role app au processus
          Iterator itRoleP = listeRoleProcessus.iterator();
          while(itRoleP.hasNext()){
            MRole rolep = (MRole) itRoleP.next();
            if(lRole.getId() == rolep.getId())
              out.write ("\t\t\t\t\t<id>" + lRole.getIdDpe () + "</id>\n") ;
          }
        }
        out.write ("\t\t\t\t</listeRole>\n") ;
        out.write ("\t\t\t</Membre>\n") ;
      }
    }
    out.write ("\t\t</MembreRole>\n") ;

    // Composant / Iteration
    out.write ("\t\t\t<ComposantIteration>\n") ;
    out.write ("\t\t</ComposantIteration>\n") ;

    // Condition definition travail / Iteration
    out.write ("\t\t<listCondDefTravailIteration>") ;
    out.newLine () ;
    out.write ("\t\t</listCondDefTravailIteration>") ;
    out.newLine () ;

    out.write ("\t</lienProjetProcessus>") ;
    out.newLine () ;
  }
}