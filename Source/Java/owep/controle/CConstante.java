package owep.controle;

/**
 * Constantes utilis�es dans le paquetage controle.
 */
public class CConstante
{
  // Cha�nes transmises par les exceptions.
  public static final String EXC_CHARGEMENT  = "Une erreur est survenue lors du chargement des param�tres." ;
  public static final String EXC_CONNEXION   = "Erreur lors de la connexion � la base de donn�es." ;
  public static final String EXC_DECONNEXION = "Erreur lors de la d�connexion de la base de donn�es." ;
  public static final String EXC_TRAITEMENT  = "Une erreur est survenue lors du tra�tement." ;
  public static final String EXC_FORWARD     = "La page n'a pu �tre trouv�e." ;
  public static final String EXC_UTILISATEUR = "Utilisateur inconnu";
  public static final String EXC_TRANSFERT   = "Une erreur est survenue lors d'un transfert de donn�es." ;
  
  // Identifiants des param�tres transmis � la JSP.
  public static final String PAR_VIDE                       = "" ;  
  public static final String PAR_MODIFIER                   = "pModifier" ;  
  public static final String PAR_SUBMIT                     = "pSubmit" ;
  public static final String PAR_SUBMITAJOUTER              = "pSubmitAjouter" ;
  public static final String PAR_SUBMITMODIFIER             = "pSubmitModifier" ;
  public static final String PAR_SUBMITSUPPRIMER            = "pSubmitSupprimer" ;
  public static final String PAR_SUBMITAJOUTER_ARTSORTIES   = "pSubmitAjouterArtSorties" ;
  public static final String PAR_SUBMITMODIFIER_ARTSORTIES  = "pSubmitModifierArtSorties" ;
  public static final String PAR_SUBMITSUPPRIMER_ARTSORTIES = "pSubmitSupprimerArtSorties" ;
  public static final String PAR_SUBMITAJOUTER_ARTENTREES   = "pSubmitAjouterArtEntrees" ;
  public static final String PAR_SUBMITSUPPRIMER_ARTENTREES = "pSubmitSupprimerArtEntrees" ;
  
  public static final String PAR_COLLABORATEUR         = "pCollaborateur" ;
  public static final String PAR_ARTEFACT              = "pArtefact" ;
  public static final String PAR_TEMPSPASSE            = "pTempsPasse" ;
  public static final String PAR_RESTEAPASSER          = "pResteAPasser" ;
  public static final String PAR_DATEDEBUTREELLE       = "pDateDebutReel" ;
  public static final String PAR_DATEFINREELLE         = "pDateFinReestimee" ;
  public static final String PAR_ITERATION             = "pIteration" ;
  public static final String PAR_TACHE                 = "pTache" ;
  public static final String PAR_ETAT                  = "pEtat" ;
  
  public static final String PAR_ARBREITERATION        = "pArbreIteration" ;
  public static final String PAR_ARBRETACHES           = "pArbreTaches" ;
  public static final String PAR_ARBREARTEFACTSORTIES  = "pArbreArtefactSorties" ;
  
  public static final String PAR_LISTETACHES             = "pListeTaches" ;
  public static final String PAR_LISTEDISCIPLINES        = "pListeDisciplines" ;
  public static final String PAR_LISTEACTIVITES          = "pListeActivites" ;
  public static final String PAR_LISTECOLLABORATEURS     = "pListeCollaborateurs" ;
  public static final String PAR_LISTERESPONSABLES       = "pListeResponsables" ;
  public static final String PAR_LISTEARTEFACTSENTREES   = "pListeArtefactsEntrees" ;
  public static final String PAR_LISTEARTEFACTSPOSSIBLES = "pListeArtefactsPossibles" ;
  public static final String PAR_LISTEARTEFACTSSORTIES   = "pListeArtefactsSorties" ;
  public static final String PAR_LISTEPRODUITS           = "pListeProduits" ;
  
  // Identifiants des param�tres de sessions.
  public static final String SES_SESSION   = "SESSION" ;
  public static final String SES_ITERATION = "pSessionIteration" ;
  public static final String SES_TACHE     = "pSessionTache" ;
  
  // Taille des champs dans la base de donn�es.
  public static final int TXT_CHARGE = 5 ;
  public static final int TXT_DATE   = 10 ;
  public static final int TXT_PETIT  = 32 ;
  public static final int TXT_MOYEN  = 48 ;
  public static final int TXT_LARGE  = 256 ;
}
