package owep.controle.installation ;


import java.io.BufferedWriter ;
import java.io.File ;
import java.io.FileWriter ;
import java.io.IOException ;
import java.sql.DriverManager ;
import java.sql.SQLException ;

import javax.servlet.ServletException ;

import owep.infrastructure.localisation.LocalisateurIdentifiant ;

import com.mysql.jdbc.Connection ;
import com.mysql.jdbc.Statement ;


/**
 * Controleur permettant la configuration de la base de données.
 */
public class CConfigurationBD extends CControleurBaseInstallation
{

  /**
   * Effectue les traitements nécessaires à la création de la base de données.
   * 
   * @see owep.controle.installation.CControleurBaseInstallation#traiter()
   */
  public String traiter () throws ServletException
  {
    String mUrlBd = getRequete ().getParameter ("url") ;
    String mLogin = getRequete ().getParameter ("login") ;
    String mMdp = getRequete ().getParameter ("mdp") ;
    String mErreur = "" ;

    if (mUrlBd == null)
    {
      return "..\\JSP\\Installation\\TConfigurationBD.jsp" ;
    }

    if (!mUrlBd.endsWith ("/"))
      mUrlBd = mUrlBd + "/" ;

    // création fichier database.xml
    try
    {
      File database = new File (getServletContext ().getRealPath ("/")
                                + LocalisateurIdentifiant.LID_BDCONFIGURATION) ;
      BufferedWriter out = new BufferedWriter (new FileWriter (database)) ;
      out
        .write ("<!DOCTYPE databases PUBLIC \"-//EXOLAB/Castor JDO Configuration DTD Version 1.0//EN\" \"http://castor.exolab.org/jdo-conf.dtd\">\n") ;
      out.write ("\n") ;
      out.write ("<jdo-conf>\n") ;
      out.write ("  <database name=\"owep\" engine=\"mysql\">\n") ;
      out.write ("    <driver class-name=\"com.mysql.jdbc.Driver\" url=\"" + mUrlBd + "owep\">\n") ;
      out.write ("      <param name=\"user\" value=\"" + mLogin + "\"/>\n") ;
      out.write ("      <param name=\"password\" value=\"" + mMdp + "\"/>\n") ;
      out.write ("    </driver>\n") ;
      out.write ("    <mapping href=\"Owep-mapping.xml\"/>\n") ;
      out.write ("  </database>\n") ;
      out.write ("  <transaction-demarcation mode=\"local\"/>\n") ;
      out.write ("</jdo-conf>\n") ;
      out.close () ;
    }
    catch (IOException e1)
    {
      mErreur = "Une erreur s'est produite lors de la création du fichier de configuration." ;
      e1.printStackTrace () ;
    }

    // base de données owep
    Connection conn = null ;
    Statement st = null ;
    try
    {
      Class.forName ("com.mysql.jdbc.Driver").newInstance () ;
      conn = (Connection) DriverManager.getConnection (mUrlBd + "?user=" + mLogin + "&password="
                                                       + mMdp) ;
    }
    catch (InstantiationException e)
    {
      mErreur = "Une erreur s'est produite lors de la connexion à la base de donnée." ;
      e.printStackTrace();
    }
    catch (IllegalAccessException e)
    {
      mErreur = "Une erreur s'est produite lors de la connexion à la base de donnée." ;
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      mErreur = "Une erreur s'est produite lors de la connexion à la base de donnée." ;
      e.printStackTrace();
    }
    catch (SQLException e)
    {
      // TODO Ecrire le bloc try-catch.
      e.printStackTrace () ;
    }
    try{
      st = (Statement) conn.createStatement () ;
      conn.setCatalog ("owep") ;
      st.close () ;
    }
    catch (SQLException e)
    {
      mErreur = "Une erreur s'est produite lors de la connexion à la database owep. Vérifier qu'elle existe." ;
      e.printStackTrace();
    }

    try
    {
      st = (Statement) conn.createStatement () ;

      /*********************************************************************************************
       * Tables du modèle processus. *
       ********************************************************************************************/
      st.execute ("CREATE TABLE PRC_PROCESSUS("
                  + "PRC_ID          INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "PRC_NOM         VARCHAR(48)      NOT NULL,"
                  + "PRC_DESCRIPTION TEXT             NULL,"
                  + "PRC_NOMAUTEUR   VARCHAR(48)      NULL,"
                  + "PRC_MAILAUTEUR  VARCHAR(32)      NULL,"
                  + "PRC_IDDPE       TEXT             NOT NULL,"
                  + "PRIMARY KEY PK_PRC_PROCESSUS (PRC_ID)" + ") ;") ;

      st
        .execute ("CREATE TABLE COM_COMPOSANT("
                  + "COM_ID          INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "COM_NOM         VARCHAR(48)      NOT NULL,"
                  + "COM_DESCRIPTION TEXT             NULL,"
                  + "COM_NOMAUTEUR   VARCHAR(48)      NULL,"
                  + "COM_MAILAUTEUR  VARCHAR(32)      NULL,"
                  + "COM_PRC_ID      INTEGER UNSIGNED NOT NULL,"
                  + "COM_IDDPE       TEXT             NOT NULL,"
                  + "PRIMARY KEY PK_COM_COMPOSANT (COM_ID),"
                  + "FOREIGN KEY FK_COM_PRC (COM_PRC_ID) REFERENCES PRC_PROCESSUS (PRC_ID)" + ") ;") ;

      st
        .execute ("CREATE TABLE DFT_DEFINITIONTRAVAIL("
                  + "DFT_ID          INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "DFT_NOM         VARCHAR(48)      NOT NULL,"
                  + "DFT_DESCRIPTION TEXT             NULL,"
                  + "DFT_COM_ID      INTEGER UNSIGNED NOT NULL,"
                  + "DFT_IDDPE       TEXT             NOT NULL,"
                  + "PRIMARY KEY PK_DFT_DEFINITIONTRAVAIL (DFT_ID),"
                  + "FOREIGN KEY FK_DFT_COM (DFT_COM_ID) REFERENCES COM_COMPOSANT (COM_ID)" + ") ;") ;

      st.execute ("CREATE TABLE ACT_ACTIVITE("
                  + "ACT_ID          INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "ACT_NOM         VARCHAR(48)      NOT NULL,"
                  + "ACT_DESCRIPTION TEXT             NULL,"
                  + "ACT_DFT_ID      INTEGER UNSIGNED NOT NULL,"
                  + "ACT_IDDPE       TEXT             NOT NULL,"
                  + "PRIMARY KEY PK_ACT_ACTIVITE (ACT_ID),"
                  + "FOREIGN KEY FK_ACT_DFT (ACT_DFT_ID) REFERENCES DFT_DEFINITIONTRAVAIL (DFT_ID)"
                  + ") ;") ;

      st
        .execute ("CREATE TABLE ROL_ROLE("
                  + "ROL_ID          INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "ROL_NOM         VARCHAR(48)      NOT NULL,"
                  + "ROL_DESCRIPTION TEXT             NULL,"
                  + "ROL_COM_ID      INTEGER UNSIGNED NOT NULL,"
                  + "ROL_IDDPE       TEXT             NOT NULL,"
                  + "PRIMARY KEY PK_ROL_ROLE (ROL_ID),"
                  + "FOREIGN KEY FK_ROL_COM (ROL_COM_ID) REFERENCES COM_COMPOSANT (COM_ID)" + ") ;") ;

      st.execute ("CREATE TABLE PRD_PRODUIT("
                  + "PRD_ID          INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "PRD_NOM         VARCHAR(48)      NOT NULL,"
                  + "PRD_DESCRIPTION TEXT             NULL,"
                  + "PRD_COM_ID      INTEGER UNSIGNED NULL,"
                  + "PRD_ROL_ID      INTEGER UNSIGNED NULL,"
                  + "PRD_IDDPE       TEXT             NOT NULL,"
                  + "PRIMARY KEY PK_PRD_PRODUIT (PRD_ID),"
                  + "FOREIGN KEY FK_PRD_COM (PRD_COM_ID) REFERENCES COM_COMPOSANT (COM_ID),"
                  + "FOREIGN KEY FK_PRD_ROL (PRD_ROL_ID) REFERENCES ROL_ROLE (ROL_ID)" + ") ;") ;

      st.execute ("CREATE TABLE R01_ACT_PRD_ENTREE(" + "R01_ACT_ID INTEGER UNSIGNED NOT NULL,"
                  + "R01_PRD_ID INTEGER UNSIGNED NOT NULL,"
                  + "PRIMARY KEY PK_R01_ACT_PRD_ENTREE (R01_ACT_ID, R01_PRD_ID),"
                  + "FOREIGN KEY FK_R01_ACT (R01_ACT_ID) REFERENCES ACT_ACTIVITE (ACT_ID),"
                  + "FOREIGN KEY FK_R01_PRD (R01_PRD_ID) REFERENCES PRD_PRODUIT (PRD_ID)" + ") ;") ;

      st.execute ("CREATE TABLE R02_ACT_PRD_SORTIE(" + "R02_ACT_ID INTEGER UNSIGNED NOT NULL,"
                  + "R02_PRD_ID INTEGER UNSIGNED NOT NULL,"
                  + "PRIMARY KEY PK_R02_ACT_PRD_SORTIE (R02_ACT_ID, R02_PRD_ID),"
                  + "FOREIGN KEY FK_R02_ACT (R02_ACT_ID) REFERENCES ACT_ACTIVITE (ACT_ID),"
                  + "FOREIGN KEY FK_R02_PRD (R02_PRD_ID) REFERENCES PRD_PRODUIT (PRD_ID)" + ") ;") ;

      st.execute ("CREATE TABLE R03_ACT_ROL(" + "R03_ACT_ID  INTEGER UNSIGNED NOT NULL,"
                  + "R03_ROL_ID  INTEGER UNSIGNED NOT NULL,"
                  + "PRIMARY KEY PK_R03_ACT_ROL (R03_ACT_ID, R03_ROL_ID),"
                  + "FOREIGN KEY FK_R03_ACT (R03_ACT_ID) REFERENCES ACT_ACTIVITE (ACT_ID),"
                  + "FOREIGN KEY FK_R03_ROL (R03_ROL_ID) REFERENCES ROL_ROLE (ROL_ID)" + ") ;") ;

      /*********************************************************************************************
       * Tables du modèle exécution. *
       ********************************************************************************************/
      st.execute ("CREATE TABLE COL_COLLABORATEUR("
                  + "COL_ID           INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "COL_NOM          VARCHAR(32)      NOT NULL,"
                  + "COL_PRENOM       VARCHAR(32)      NOT NULL,"
                  + "COL_ADRESSE      VARCHAR(256)     NULL,"
                  + "COL_TELEPHONE    VARCHAR(24)      NULL,"
                  + "COL_PORTABLE     VARCHAR(24)      NULL,"
                  + "COL_EMAIL        VARCHAR(48)      NULL,"
                  + "COL_COMMENTAIRES TEXT             NULL,"
                  + "COL_UTILISATEUR  VARCHAR(24)      NOT NULL,"
                  + "COL_MOTDEPASSE   VARCHAR(24)      NOT NULL,"
                  + "COL_TACHEENCOURS INTEGER          NOT NULL,"
                  + "COL_DROIT        INTEGER UNSIGNED NULL,"
                  + "PRIMARY KEY PK_COL_COLLABORATEUR (COL_ID)" + ") ;") ;

      st
        .execute ("CREATE TABLE PRJ_PROJET("
                  + "PRJ_ID              INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "PRJ_NOM             VARCHAR(48)      NOT NULL,"
                  + "PRJ_DESCRIPTION     TEXT             NULL,"
                  + "PRJ_BILAN           TEXT             NULL,"
                  + "PRJ_DATEDEBUTPREVUE DATE             NULL,"
                  + "PRJ_DATEFINPREVUE   DATE             NULL,"
                  + "PRJ_DATEDEBUTREELLE DATE             NULL,"
                  + "PRJ_DATEFINREELLE   DATE             NULL,"
                  + "PRJ_ETAT            INTEGER UNSIGNED NULL,"
                  + "PRJ_BUDGET          DOUBLE           NULL,"
                  + "PRJ_PRC_ID          INTEGER UNSIGNED NOT NULL,"
                  + "PRJ_COL_ID_CHEF     INTEGER UNSIGNED NOT NULL,"
                  + "PRIMARY KEY PK_PRJ_PROJET (PRJ_ID),"
                  + "FOREIGN KEY FK_PRJ_PRC      (PRJ_PRC_ID)      REFERENCES PRC_PROCESSUS (PRC_ID),"
                  + "FOREIGN KEY FK_PRJ_COL_CHEF (PRJ_COL_ID_CHEF) REFERENCES COL_COLLABORATEUR (COL_ID)"
                  + ") ;") ;

      st.execute ("CREATE TABLE ITE_ITERATION("
                  + "ITE_ID              INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "ITE_NUMERO          INTEGER UNSIGNED NOT NULL,"
                  + "ITE_NOM             VARCHAR(48)      NOT NULL,"
                  + "ITE_BILAN           TEXT             NULL,"
                  + "ITE_DATEDEBUTPREVUE DATE             NULL,"
                  + "ITE_DATEFINPREVUE   DATE             NULL,"
                  + "ITE_DATEDEBUTREELLE DATE             NULL,"
                  + "ITE_DATEFINREELLE   DATE             NULL,"
                  + "ITE_ETAT            INTEGER UNSIGNED NULL,"
                  + "ITE_PRJ_ID          INTEGER UNSIGNED NOT NULL,"
                  + "PRIMARY KEY PK_ITE_ITERATION (ITE_ID),"
                  + "FOREIGN KEY FK_ITE_PRJ (ITE_PRJ_ID) REFERENCES PRJ_PROJET (PRJ_ID)" + ") ;") ;

      st.execute ("CREATE TABLE TAC_TACHE("
                  + "TAC_ID              INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "TAC_NOM             VARCHAR(48)      NOT NULL,"
                  + "TAC_DESCRIPTION     VARCHAR(48)      NULL,"
                  + "TAC_CHARGEINITIALE  FLOAT            NOT NULL,"
                  + "TAC_TEMPSPASSE      FLOAT            NOT NULL,"
                  + "TAC_RESTEAPASSER    FLOAT            NOT NULL,"
                  + "TAC_ETAT            INTEGER          NOT NULL,"
                  + "TAC_DATEDEBUTPREVUE DATE             NOT NULL,"
                  + "TAC_DATEFINPREVUE   DATE             NOT NULL,"
                  + "TAC_DATEDEBUTREELLE DATE             NULL,"
                  + "TAC_DATEFINREELLE   DATE             NULL,"
                  + "TAC_ITE_ID          INTEGER UNSIGNED NOT NULL,"
                  + "TAC_COL_ID          INTEGER UNSIGNED NOT NULL,"
                  + "TAC_ACT_ID          INTEGER UNSIGNED NULL,"
                  + "TAC_DATEDEBUTCHRONO BIGINT           NULL,"
                  + "PRIMARY KEY PK_TAC_TACHE (TAC_ID),"
                  + "FOREIGN KEY FK_TAC_ITE (TAC_ITE_ID) REFERENCES ITE_ITERATION (ITE_ID),"
                  + "FOREIGN KEY FK_TAC_COL (TAC_COL_ID) REFERENCES COL_COLLABORATEUR (COL_ID),"
                  + "FOREIGN KEY FK_TAC_ACT (TAC_ACT_ID) REFERENCES ACT_ACTIVITE (ACT_ID)" + ") ;") ;

      st
        .execute ("CREATE TABLE ART_ARTEFACT("
                  + "ART_ID            INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "ART_NOM           VARCHAR(48)      NOT NULL,"
                  + "ART_DESCRIPTION   TEXT             NULL,"
                  + "ART_NOM_FICHIER   VARCHAR(48)      NULL,"
                  + "ART_PRJ_ID        INTEGER UNSIGNED NOT NULL,"
                  + "ART_COL_ID        INTEGER UNSIGNED NOT NULL,"
                  + "ART_TAC_ID_ENTREE INTEGER UNSIGNED NULL,"
                  + "ART_TAC_ID_SORTIE INTEGER UNSIGNED NULL,"
                  + "ART_PRD_ID        INTEGER UNSIGNED NOT NULL,"
                  + "PRIMARY KEY PK_ART_ARTEFACT (ART_ID),"
                  + "FOREIGN KEY FK_ART_PRJ        (ART_PRJ_ID)        REFERENCES PRJ_PROJET (PRJ_ID),"
                  + "FOREIGN KEY FK_ART_COL        (ART_COL_ID)        REFERENCES COL_COLLABORATEUR (COL_ID),"
                  + "FOREIGN KEY FK_ART_TAC_ENTREE (ART_TAC_ID_ENTREE) REFERENCES TAC_TACHE (TAC_ID),"
                  + "FOREIGN KEY FK_ART_TAC_SORTIE (ART_TAC_ID_SORTIE) REFERENCES TAC_TACHE (TAC_ID),"
                  + "FOREIGN KEY FK_ART_PRD        (ART_PRD_ID)        REFERENCES PRD_PRODUIT (PRD_ID)"
                  + ") ;") ;

      st.execute ("CREATE TABLE CND_CONDITION("
                  + "CND_ID          INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "CND_TAC_ID      INTEGER UNSIGNED NOT NULL,"
                  + "CND_TAC_ID_PREC INTEGER UNSIGNED NOT NULL,"
                  + "CND_ETAT        INTEGER          NOT NULL,"
                  + "PRIMARY KEY PK_COL_ROL      (CND_ID),"
                  + "FOREIGN KEY FK_CND_TAC      (CND_TAC_ID)      REFERENCES TAC_TACHE (TAC_ID),"
                  + "FOREIGN KEY FK_CND_TAC_PREC (CND_TAC_ID_PREC) REFERENCES TAC_TACHE (TAC_ID)"
                  + ") ;") ;

      st.execute ("CREATE TABLE AIM_ACTIVITEIMPREVUE("
                  + "AIM_ID          INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "AIM_NOM         VARCHAR(48)      NOT NULL,"
                  + "AIM_DESCRIPTION TEXT             NULL,"
                  + "AIM_PRJ_ID          INTEGER UNSIGNED NOT NULL,"
                  + "PRIMARY KEY PK_ACT_ACTIVITE (AIM_ID),"
                  + "FOREIGN KEY FK_AIM_PRJ (AIM_PRJ_ID) REFERENCES PRJ_PROJET (PRJ_ID)" + ") ;") ;

      st.execute ("CREATE TABLE TIM_TACHEIMPREVUE("
                  + "TIM_ID              INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "TIM_NOM             VARCHAR(48)      NOT NULL,"
                  + "TIM_DESCRIPTION     VARCHAR(48)      NULL,"
                  + "TIM_CHARGEINITIALE  FLOAT            NOT NULL,"
                  + "TIM_TEMPSPASSE      FLOAT            NOT NULL,"
                  + "TIM_RESTEAPASSER    FLOAT            NOT NULL,"
                  + "TIM_ETAT            INTEGER          NOT NULL,"
                  + "TIM_DATEDEBUTPREVUE DATE             NOT NULL,"
                  + "TIM_DATEFINPREVUE   DATE             NOT NULL,"
                  + "TIM_DATEDEBUTREELLE DATE             NULL,"
                  + "TIM_DATEFINREELLE   DATE             NULL,"
                  + "TIM_ITE_ID          INTEGER UNSIGNED NOT NULL,"
                  + "TIM_COL_ID          INTEGER UNSIGNED NOT NULL,"
                  + "TIM_AIM_ID          INTEGER UNSIGNED NULL,"
                  + "TIM_DATEDEBUTCHRONO bigint           NULL,"
                  + "PRIMARY KEY PK_TIM_TACHE (TIM_ID),"
                  + "FOREIGN KEY FK_TIM_ITE (TIM_ITE_ID) REFERENCES ITE_ITERATION (ITE_ID),"
                  + "FOREIGN KEY FK_TIM_COL (TIM_COL_ID) REFERENCES COL_COLLABORATEUR (COL_ID),"
                  + "FOREIGN KEY FK_TIM_ACT (TIM_AIM_ID) REFERENCES AIM_ACTIVITEIMPREVUE (AIM_ID)"
                  + ") ;") ;

      st
        .execute ("CREATE TABLE ARI_ARTEFACTIMPREVUE("
                  + "ARI_ID            INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "ARI_NOM           VARCHAR(48)      NOT NULL,"
                  + "ARI_DESCRIPTION   TEXT             NULL,"
                  + "ARI_NOM_FICHIER   VARCHAR(48)      NULL,"
                  + "ARI_PRJ_ID        INTEGER UNSIGNED NOT NULL,"
                  + "ARI_COL_ID        INTEGER UNSIGNED NOT NULL,"
                  + "ARI_TIM_ID_ENTREE INTEGER UNSIGNED NULL,"
                  + "ARI_TIM_ID_SORTIE INTEGER UNSIGNED NULL,"
                  + "PRIMARY KEY PK_ARI_ARTEFACT (ARI_ID),"
                  + "FOREIGN KEY FK_ARI_PRJ        (ARI_PRJ_ID)        REFERENCES PRJ_PROJET (PRJ_ID),"
                  + "FOREIGN KEY FK_ARI_COL        (ARI_COL_ID)        REFERENCES COL_COLLABORATEUR (COL_ID),"
                  + "FOREIGN KEY FK_ARI_TIM_ENTREE (ARI_TIM_ID_ENTREE) REFERENCES TIM_TACHEIMPREVUE (TIM_ID),"
                  + "FOREIGN KEY FK_ARI_TIM_SORTIE (ARI_TIM_ID_SORTIE) REFERENCES TIM_TACHEIMPREVUE (TIM_ID)"
                  + ") ;") ;

      st.execute ("CREATE TABLE R04_COL_ROL(" + "R04_COL_ID INTEGER UNSIGNED NOT NULL,"
                  + "R04_ROL_ID INTEGER UNSIGNED NOT NULL,"
                  + "PRIMARY KEY PK_COL_ROL (R04_COL_ID, R04_ROL_ID),"
                  + "FOREIGN KEY FK_R04_COL (R04_COL_ID) REFERENCES COL_COLLABORATEUR (COL_ID),"
                  + "FOREIGN KEY FK_R04_ROL (R04_ROL_ID) REFERENCES ROL_ROLE (ROL_ID)" + ") ;") ;

      st.execute ("CREATE TABLE R05_COL_PRJ(" + "R05_COL_ID INTEGER UNSIGNED NOT NULL,"
                  + "R05_PRJ_ID INTEGER UNSIGNED NOT NULL,"
                  + "PRIMARY KEY PK_R05_COL_PRJ (R05_COL_ID, R05_PRJ_ID),"
                  + "FOREIGN KEY FK_R05_COL (R05_COL_ID) REFERENCES COL_COLLABORATEUR (COL_ID),"
                  + "FOREIGN KEY FK_R05_PRJ (R05_PRJ_ID) REFERENCES PRJ_PROJET (PRJ_ID)" + ") ;") ;

      /*********************************************************************************************
       * Tables de gestion de projet. *
       ********************************************************************************************/
      st.execute ("CREATE TABLE RIS_RISQUE("
                  + "RIS_ID          INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "RIS_NOM         VARCHAR(48)      NOT NULL,"
                  + "RIS_DESCRIPTION TEXT             NULL,"
                  + "RIS_PRIORITE    INTEGER UNSIGNED NOT NULL,"
                  + "RIS_IMPACT      TEXT             NULL,"
                  + "RIS_ACTIONS     TEXT             NULL,"
                  + "RIS_ETAT        TEXT             NOT NULL,"
                  + "RIS_PRJ_ID      INTEGER UNSIGNED NOT NULL,"
                  + "PRIMARY KEY PK_RIS_RISQUE (RIS_ID),"
                  + "FOREIGN KEY FK_RIS_PRJ (RIS_PRJ_ID) REFERENCES PRJ_PROJET (PRJ_ID)" + ") ;") ;

      st.execute ("CREATE TABLE IND_INDICATEUR("
                  + "IND_ID          INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "IND_NOM         VARCHAR(48)      NOT NULL,"
                  + "IND_DESCRIPTION TEXT             NULL,"
                  + "IND_UNITE       VARCHAR(48)      NULL,"
                  + "IND_PRJ_ID      INTEGER UNSIGNED NOT NULL,"
                  + "PRIMARY KEY PK_IND_INDICATEUR (IND_ID),"
                  + "FOREIGN KEY FK_IND_PRJ (IND_PRJ_ID) REFERENCES PRJ_PROJET (PRJ_ID)" + ");") ;

      st
        .execute ("CREATE TABLE MSI_MESUREINDICATEUR("
                  + "MSI_ID          INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "MSI_VALEUR      FLOAT            NULL,"
                  + "MSI_COMMENTAIRE TEXT             NULL,"
                  + "MSI_COL_ID      INTEGER UNSIGNED NOT NULL,"
                  + "MSI_IND_ID      INTEGER UNSIGNED NOT NULL,"
                  + "MSI_ITE_ID      INTEGER UNSIGNED NOT NULL,"
                  + "PRIMARY KEY PK_MSI_MESUREINDICATEUR (MSI_ID),"
                  + "FOREIGN KEY FK_MSI_COL (MSI_COL_ID) REFERENCES COL_COLLABORATEUR (COL_ID),"
                  + "FOREIGN KEY FK_MSI_IND (MSI_IND_ID) REFERENCES IND_INDICATEUR (IND_ID),"
                  + "FOREIGN KEY FK_MSI_ITE (MSI_ITE_ID) REFERENCES ITE_ITERATION (ITE_ID)" + ") ;") ;

      st.execute ("CREATE TABLE IMP_ACTIVITEIMP("
                  + "IMP_ID          INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "IMP_NOM         VARCHAR(48)      NOT NULL,"
                  + "IMP_DESCRIPTION TEXT             NULL,"
                  + "PRIMARY KEY PK_IMP_ACTIVITEIMP (IMP_ID)" + ");") ;

      st.execute ("CREATE TABLE TCL_TACHECOLLABORATIVE("
                  + "TCL_ID              INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "TCL_NOM             VARCHAR(48)      NOT NULL,"
                  + "TCL_DESCRIPTION     VARCHAR(48)      NULL,"
                  + "TCL_CHARGEINITIALE  FLOAT            NOT NULL,"
                  + "TCL_TEMPSPASSE      FLOAT            NOT NULL,"
                  + "TCL_RESTEAPASSER    FLOAT            NOT NULL,"
                  + "TCL_ETAT            INTEGER          NOT NULL,"
                  + "TCL_DATEDEBUTPREVUE DATE             NOT NULL,"
                  + "TCL_DATEFINPREVUE   DATE             NOT NULL,"
                  + "TCL_DATEDEBUTREELLE DATE             NULL,"
                  + "TCL_DATEFINREELLE   DATE             NULL,"
                  + "TCL_ITE_ID          INTEGER UNSIGNED NOT NULL,"
                  + "TCL_COL_ID          INTEGER UNSIGNED NOT NULL,"
                  + "TCL_DATEDEBUTCHRONO DATE             NULL,"
                  + "PRIMARY KEY PK_TCL_TACHE (TCL_ID),"
                  + "FOREIGN KEY FK_TCL_ITE (TCL_ITE_ID) REFERENCES ITE_ITERATION (ITE_ID),"
                  + "FOREIGN KEY FK_TCL_COL (TCL_COL_ID) REFERENCES COL_COLLABORATEUR (COL_ID)"
                  + ") ;") ;

      st
        .execute ("CREATE TABLE R06_COL_TCL("
                  + "R06_COL_ID INTEGER UNSIGNED NOT NULL,"
                  + "R06_TCL_ID INTEGER UNSIGNED NOT NULL,"
                  + "PRIMARY KEY PK_R06_COL_TCL(R06_COL_ID, R06_TCL_ID),"
                  + "FOREIGN KEY FK_R06_COL (R06_COL_ID) REFERENCES COL_COLLABORATEUR (COL_ID),"
                  + "FOREIGN KEY FK_R06_TCL (R06_TCL_ID) REFERENCES TCL_TACHECOLLABORATIVE (TCL_ID)"
                  + ") ;") ;

      st.execute ("CREATE TABLE PRB_PROBLEME("
                  + "PRB_ID                 INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "PRB_NOM                VARCHAR(48)      NOT NULL,"
                  + "PRB_DESCRIPTION        TEXT             NULL,"
                  + "PRB_ETAT               VARCHAR(48)      NOT NULL,"
                  + "PRB_DATEIDENTIFICATION DATE             NOT NULL,"
                  + "PRB_DATECLOTURE        DATE             NULL,"
                  + "PRIMARY KEY PK_PRB_PROBLEME (PRB_ID)" + ");") ;

      st.execute ("CREATE TABLE R07_PRB_TAC_RESOUT(" + "R07_PRB_ID INTEGER UNSIGNED NOT NULL,"
                  + "R07_TAC_ID INTEGER UNSIGNED NOT NULL,"
                  + "PRIMARY KEY PK_R07_TAC_PRB (R07_PRB_ID, R07_TAC_ID),"
                  + "FOREIGN KEY FK_R07_PRB (R07_PRB_ID) REFERENCES PRB_PROBLEME (PRB_ID),"
                  + "FOREIGN KEY FK_R07_TAC (R07_TAC_ID) REFERENCES TAC_TACHE (TAC_ID)" + ") ;") ;

      st.execute ("CREATE TABLE R08_PRB_TAC_PROVOQUE(" + "R08_PRB_ID INTEGER UNSIGNED NOT NULL,"
                  + "R08_TAC_ID INTEGER UNSIGNED NOT NULL,"
                  + "PRIMARY KEY PK_R08_TAC_PRB (R08_PRB_ID, R08_TAC_ID),"
                  + "FOREIGN KEY FK_R08_PRB (R08_PRB_ID) REFERENCES PRB_PROBLEME (PRB_ID),"
                  + "FOREIGN KEY FK_R08_TAC (R08_TAC_ID) REFERENCES TAC_TACHE (TAC_ID)" + ") ;") ;

      st.execute ("CREATE TABLE R09_PRB_TIM_RESOUT(" + "R09_PRB_ID INTEGER UNSIGNED NOT NULL,"
                  + "R09_TIM_ID INTEGER UNSIGNED NOT NULL,"
                  + "PRIMARY KEY PK_R09_TIM_PRB (R09_PRB_ID, R09_TIM_ID),"
                  + "FOREIGN KEY FK_R09_PRB (R09_PRB_ID) REFERENCES PRB_PROBLEME (PRB_ID),"
                  + "FOREIGN KEY FK_R09_TAC (R09_TIM_ID) REFERENCES TIM_TACHEIMPREVUE (TIM_ID)"
                  + ") ;") ;

      st.execute ("CREATE TABLE R10_PRB_TIM_PROVOQUE(" + "R10_PRB_ID INTEGER UNSIGNED NOT NULL,"
                  + "R10_TIM_ID INTEGER UNSIGNED NOT NULL,"
                  + "PRIMARY KEY PK_R10_TIM_PRB (R10_PRB_ID, R10_TIM_ID),"
                  + "FOREIGN KEY FK_R10_PRB (R10_PRB_ID) REFERENCES PRB_PROBLEME (PRB_ID),"
                  + "FOREIGN KEY FK_R10_TIM (R10_TIM_ID) REFERENCES TIM_TACHEIMPREVUE (TIM_ID)"
                  + ") ;") ;

      /*********************************************************************************************
       * Tables de configuration. *
       ********************************************************************************************/
      st.execute ("CREATE TABLE CON_CONFIGURATION("
                  + "CON_ID              INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
                  + "CON_LANGUE          VARCHAR(5)       NOT NULL,"
                  + "CON_APPARENCE       VARCHAR(10)      NOT NULL,"
                  + "CON_PATH_ARTEFACT   VARCHAR(48)      NOT NULL,"
                  + "CON_PATH_EXPORT     VARCHAR(48)      NOT NULL,"
                  + "PRIMARY KEY PK_CON_CONFIGURTION(CON_ID)" + ") ;") ;
    }
    catch (SQLException e)
    {
      mErreur = "Une erreur s'est produite lors de la création des tables.<br>Vérifier que votre database owep ne comporte aucune table." ;
      e.printStackTrace () ;
    }
    finally
    {
      if (conn != null)
      {
        try
        {
          conn.close () ;
        }
        catch (SQLException e2)
        {
          mErreur = "Une erreur s'est produite lors de la connexion à la base de donnée." ;
          e2.printStackTrace () ;
        }
      }
      if (st != null)
      {
        try
        {
          st.close () ;
        }
        catch (SQLException e2)
        {
          mErreur = "Une erreur s'est produite lors de la connexion à la base de donnée." ;
          e2.printStackTrace () ;
        }
      }
    }

    if (!mErreur.equals (""))
    {
      getRequete ().setAttribute ("mErreur", mErreur) ;
      return "..\\JSP\\Installation\\TConfigurationBD.jsp" ;
    }

    return "..\\JSP\\Installation\\TSuperviseur.jsp" ;
  }
}