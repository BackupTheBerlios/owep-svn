package owep.controle;

/**
 * Constantes utilisées dans le paquetage controle.
 */
public class CConstante
{
  // Chaînes transmises par les exceptions.
  public static final String EXC_CHARGEMENT         = "Une erreur est survenue lors du chargement des paramètres." ;
  public static final String EXC_CONNEXION          = "Erreur lors de la connexion à la base de données." ;
  public static final String EXC_DECONNEXION        = "Erreur lors de la déconnexion de la base de données." ;
  public static final String EXC_TRAITEMENT         = "Une erreur est survenue lors du traîtement." ;
  public static final String EXC_FORWARD            = "La page n'a pu être trouvée." ;
  
  // Identifiants des paramètres transmis à la JSP.
  public static final String PAR_SUBMIT          = "pSubmit" ;
  public static final String PAR_COLLABORATEUR   = "pCollaborateur" ;
  public static final String PAR_ITERATION       = "pIteration" ;
  public static final String PAR_TEMPSPASSE      = "pTempsPasse" ;
  public static final String PAR_RESTEAPASSER    = "pResteAPasser" ;
  public static final String PAR_DATEDEBUTREELLE = "pDateDebutReel" ;
  public static final String PAR_DATEFINREELLE   = "pDateFinReestimee" ;
}
