package owep.controle;

/**
 * Constantes utilisées dans le paquetage controle.
 */
public class CConstante
{
  // Chaînes transmises par les exceptions.
  public static final String EXC_CHARGEMENT  = "Une erreur est survenue lors du chargement des paramètres." ;
  public static final String EXC_CONNEXION   = "Erreur lors de la connexion à la base de données." ;
  public static final String EXC_DECONNEXION = "Erreur lors de la déconnexion de la base de données." ;
  public static final String EXC_TRAITEMENT  = "Une erreur est survenue lors du traîtement." ;
  public static final String EXC_PARAMETRE   = "Les paramètres transmis à la page sont incorrects." ;
  public static final String EXC_FORWARD     = "La page n'a pu être trouvée." ;
  public static final String EXC_UTILISATEUR = "Utilisateur inconnu";
  public static final String EXC_TRANSFERT   = "Une erreur est survenue lors d'un transfert de données." ;
  
  // Identifiants des paramètres transmis à la JSP.

  public static final String PAR_VIDE                        = "" ;  
  public static final String PAR_MODIFIER                    = "pModifier" ;  
  public static final String PAR_SUBMIT                      = "pSubmit" ;
  public static final String PAR_SUBMITAJOUTER               = "pSubmitAjouter" ;
  public static final String PAR_SUBMITMODIFIER              = "pSubmitModifier" ;
  public static final String PAR_SUBMITSUPPRIMER             = "pSubmitSupprimer" ;
  public static final String PAR_SUBMITAJOUTER_ARTSORTIES    = "pSubmitAjouterArtSorties" ;
  public static final String PAR_SUBMITMODIFIER_ARTSORTIES   = "pSubmitModifierArtSorties" ;
  public static final String PAR_SUBMITSUPPRIMER_ARTSORTIES  = "pSubmitSupprimerArtSorties" ;
  public static final String PAR_SUBMITAJOUTER_ARTENTREES    = "pSubmitAjouterArtEntrees" ;
  public static final String PAR_SUBMITSUPPRIMER_ARTENTREES  = "pSubmitSupprimerArtEntrees" ;
  public static final String PAR_SUBMITAJOUTER_TACPROVOQUE   = "pSubmitAjouterTacheProvoque" ;
  public static final String PAR_SUBMITSUPPRIMER_TACPROVOQUE = "pSubmitSupprimerTacheProvoque" ;
  public static final String PAR_SUBMITAJOUTER_TACRESOUT     = "pSubmitAjouterTacheResout" ;
  public static final String PAR_SUBMITSUPPRIMER_TACRESOUT   = "pSubmitSupprimerTacheResout" ;
  public static final String PAR_SUBMITAJOUTER_TACDEPEND     = "pSubmitAjouterTachesDependantes" ;
  public static final String PAR_SUBMITSUPPRIMER_TACDEPEND   = "pSubmitSupprimerTachesDependantes" ;
  public static final String PAR_TYPEINDICATEUR              = "pTypeIndicateur" ;
  public static final String PAR_TYPE_TACHE                  = "pTypeTache" ;
  public static final String TYPE_TACHE                      = "tache" ;
  public static final String TYPE_TACHE_IMPREVUE             = "tacheImprevue" ;
  
  public static final String PAR_MESSAGE               = "pMessage" ;
  public static final String PAR_FORMULAIRE            = "pFormulaire" ;
  public static final String PAR_COLLABORATEUR         = "pCollaborateur" ;
  public static final String PAR_ARTEFACT              = "pArtefact" ;
  public static final String PAR_ARTEFACT_IMPREVU      = "pArtefactImprevu" ;  
  public static final String PAR_TEMPSPASSE            = "pTempsPasse" ;
  public static final String PAR_RESTEAPASSER          = "pResteAPasser" ;
  public static final String PAR_DATEDEBUTREELLE       = "pDateDebutReel" ;
  public static final String PAR_DATEFINREELLE         = "pDateFinReestimee" ;
  public static final String PAR_ITERATION             = "pIteration" ;
  public static final String PAR_VALEURMESURE          = "pValeurMesure" ;
  public static final String PAR_COMMENTAIREMESURE     = "pCommentaireMesure" ;
  public static final String PAR_TACHE                 = "pTache" ;
  public static final String PAR_TACHE_IMPREVUE        = "pTacheImprevue" ;  
  public static final String PAR_ETAT                  = "pEtat" ;
  public static final String PAR_PROJET                = "pProjet" ;
  public static final String PAR_PROBLEME              = "pProbleme" ;
  public static final String PAR_RISQUE                = "pRisque" ;

  public static final String PAR_ARBREITERATION        = "pArbreIteration" ;
  public static final String PAR_ARBRETACHES           = "pArbreTaches" ;
  public static final String PAR_ARBREARTEFACTSORTIES  = "pArbreArtefactSorties" ;
  public static final String PAR_ARBREPROBLEME         = "pArbreProblemes" ;
  public static final String PAR_ARBRERISQUE           = "pArbreRisques" ;
  public static final String PAR_ARBREACTIVITE         = "pArbreActivite" ;
  public static final String PAR_ARBRETACHESIMPREVUES  = "pArbreTachesImprevues" ;
  public static final String PAR_ARBREACTIVITEIMPREVUE = "pArbreActiviteImprevue" ;

  public static final String PAR_LISTETACHES             = "pListeTaches" ;
  public static final String PAR_LISTETACHESPROVOQUE     = "pListeTachesProvoque" ;
  public static final String PAR_LISTETACHESRESOUT       = "pListeTachesResout" ;
  public static final String PAR_LISTEDISCIPLINES        = "pListeDisciplines" ;
  public static final String PAR_LISTEACTIVITES          = "pListeActivites" ;
  public static final String PAR_LISTECOLLABORATEURS     = "pListeCollaborateurs" ;
  public static final String PAR_LISTERESPONSABLES       = "pListeResponsables" ;
  public static final String PAR_LISTEARTEFACTSENTREES   = "pListeArtefactsEntrees" ;
  public static final String PAR_LISTEARTEFACTSPOSSIBLES = "pListeArtefactsPossibles" ;
  public static final String PAR_LISTEARTEFACTSSORTIES   = "pListeArtefactsSorties" ;
  public static final String PAR_LISTEPRODUITS           = "pListeProduits" ;
  public static final String PAR_LISTEITERATIONS         = "pListeIterations" ;
  
  public static final String PAR_LISTETACHESPOSSIBLES    = "pListeTachesPossibles" ;
  public static final String PAR_LISTETACHESDEPENDANTES  = "pListeTachesDependantes" ;
  public static final String PAR_LISTETACHESCONDITION    = "pListeTachesCondition" ;
  public static final String PAR_LISTEACTIVITESIMPREVUES = "pListeActivitesImprevues" ;
  public static final String PAR_LISTETACHESIMPREVUES    = "pListeTachesImprevues" ;
  public static final String PAR_LISTEPROBLEMES          = "pListeProblemes" ;
  public static final String PAR_LISTERISQUES            = "pListeRisques" ;
  
  
  public static final String PAR_LISTEITERATIONSMENU     = "pListeIterationsMenu" ;
  
  
  public static final String PAR_CONFIGURATION           = "pConfiguration" ; 
  public static final String PAR_LANGUE                  = "pLangue" ;
  public static final String PAR_APPARENCE               = "pApparence" ; 
  
  // Identifiants des paramètres de sessions.
  public static final String SES_SESSION   = "SESSION" ;
  public static final String SES_ITERATION = "pSessionIteration" ;
  public static final String SES_TACHE     = "pSessionTache" ;
  
  // Longueur des champs dans le formulaire.
  public static final int LNG_CHARGE          = 5 ;
  public static final int LNG_DATE            = 7 ;
  public static final int LNG_PETIT           = 35 ;
  public static final int LNG_MOYEN           = 53 ;
  public static final int LNG_LARGE           = 60 ;
  public static final int LNG_LOGIN           = 15 ;
  public static final int LNG_FICHIER         = 75 ;
  public static final int LNG_VALEUR          = 5 ;
  public static final int LNG_NUMERO          = 2 ;
  public static final int LNG_COMMENTAIRE     = 1024 ;
  public static final int LNG_COLSCOMMENTAIRE = 60 ;
  public static final int LNG_ROWSCOMMENTAIRE = 4 ;
  
  // Taille des champs dans la base de données.
  public static final int TXT_CHARGE  = 5 ;
  public static final int TXT_DATE    = 10 ;
  public static final int TXT_PETIT   = 32 ;
  public static final int TXT_MOYEN   = 48 ;
  public static final int TXT_LARGE   = 256 ;
  public static final int TXT_NUMERO  = 2 ;
  public static final int TXT_LOGIN   = 24 ;
  public static final int TXT_FICHIER = 255 ;
}
