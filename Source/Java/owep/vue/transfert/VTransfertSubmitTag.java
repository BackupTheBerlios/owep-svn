package owep.vue.transfert ;


import java.io.IOException ;
import java.util.ArrayList ;
import javax.servlet.jsp.JspException ;
import javax.servlet.jsp.PageContext ;
import javax.servlet.jsp.tagext.TagSupport ;
import owep.controle.CConstante ;


/**
 * Permet de créer un bouton de validation qui vérifie automatiquement tous les champs transmis par
 * un transfertchamp.
 */
public class VTransfertSubmitTag extends TagSupport
{
  private String  mLibelle ;      // Libellé du bouton de soumission.
  private String  mValeur ;       // Valeur renvoyée par le bouton de soumission.
  private boolean mVerification ; // Indique si la vérification de champ doit être réalisée.
  private String  mValidation ;   // Code javascript de validation.
  private String  mAdditionnel ;   // Code javascript additionnel.


  /**
   * Génère le bouton de validation.
   * @return Statut du tag.
   * @throws JspException -
   */
  public int doStartTag () throws JspException
  {
    assert findAncestorWithClass (this, VTransfertBeanTag.class) == null ;
    assert pageContext.getAttribute (VTransfertConstante.TRANSFERT_LISTEARBRES, PageContext.REQUEST_SCOPE) != null ;
    assert ((ArrayList) pageContext.getAttribute (VTransfertConstante.TRANSFERT_LISTEARBRES, PageContext.REQUEST_SCOPE)).size () > 0 ;
    
    // Récupère l'arbre principal de transfert.
    ArrayList   lListeArbres  = (ArrayList) pageContext.getAttribute (VTransfertConstante.TRANSFERT_LISTEARBRES, PageContext.REQUEST_SCOPE) ;
    VArbreBeans lArbreCourant = (VArbreBeans) lListeArbres.get (0) ;
    
    try
    {
      // Crée un champ caché qui contiendra la valeur du bouton de soumission si aucun n'a été créé préalablement.
      if (! lArbreCourant.isSoumission ())
      {
        pageContext.getOut ().print ("<input type=\"hidden\" name=\"" + VTransfertConstante.TRANSFERT_SOUMISSION + "\" value=\"\">\n") ;
        lArbreCourant.setSoumission (true) ;
        
        // Ajoute les fonction de vérification de champs.
        pageContext.getOut ().print ("<script type=\"text/javascript\" language=\"JavaScript\">\n") ;
        for (int i = 0; i < lListeArbres.size () ; i ++)
        {
          pageContext.getOut ().print ("function " + VTransfertConstante.TRANSFERT_VERIFICATION + ((VArbreBeans) lListeArbres.get (i)).getNom () + " () {\n") ;
          pageContext.getOut ().print (gestionnaireValidation ((VArbreBeans) lListeArbres.get (i))) ;
          pageContext.getOut ().print ("} ;\n") ;
        }
        pageContext.getOut ().print ("</script>\n") ;
      }
      
      // Génère le bouton de soumission en intégrant le gestionnaire javascript de validation si nécessaire.
      if (mVerification)
      {
        pageContext.getOut ().print ("<input class='bouton' type=\"button\" value=\"" + mLibelle + "\" " + 
                                     "onclick=\"" + VTransfertConstante.TRANSFERT_SOUMISSION + ".value='" + mValeur + "' ;\n" +  
                                     mValidation + "\"" + mAdditionnel + ">") ;
      }
      else
      {
        pageContext.getOut ().print ("<input class='bouton' type=\"button\" value=\"" + mLibelle + "\" " + 
                                     "onclick=\"document." + CConstante.PAR_FORMULAIRE + ".submit () ;\">") ;
      }
      pageContext.getOut ().flush () ;
    }
    catch (IOException lException)
    {
      throw new JspException (lException.getMessage ()) ;
    }
    
    return SKIP_BODY ;
  }


  /**
   * Retourne le gestionnaire d'évenement javascript permettant de valider tous les champs d'un
   * formulaire définits avec la balise transfertchamp.
   * @param pArbre Arbre contenant tous les champs transférés.
   * @return Gestionnaire d'évenement javascript.
   * @throws JspException Si une erreur survient lors du parcoursde l'arbre.
   */
  private String gestionnaireValidation (VArbreBeans pArbre) throws JspException
  {
    assert pArbre != null ;
    
    String lGestionnaireValidation = "" ;
    
    // Parcours tous les champs du bean courant.
    for (int i = 0; i < pArbre.getNbAssociations (); i ++)
    {
      // Récupère la fonction javascript de validation du champ courant et la concatènne au
      // gestionnaire d'évènement.
      try
      {
        String lFonctionValidation = (String) Class.forName (VTransfertConstante.TRANSFERT_CONVERTORPACKAGE + pArbre.getAssociation (i).getConvertor ()).getMethod ("getValidation", null).invoke (null, null) ;
        
        // Ajoute la fonction de vérifiaction de champ vide si nécessaire.
        if (pArbre.getAssociation (i).isObligatoire ())
        {
          lGestionnaireValidation += "if (! isVide (document." + CConstante.PAR_FORMULAIRE + "." +
                                     pArbre.getAssociation (i).getChamp () + ".value, '" +
                                     pArbre.getAssociation (i).getLibelle () + "')) {\n" ;
        }
        if (! lFonctionValidation.equals (""))
        {
          // Ajoute la fonction de vérification du champ courant au gestionnaire d'évenement.
          lGestionnaireValidation += lFonctionValidation + " (document." + CConstante.PAR_FORMULAIRE + "." +
                                    pArbre.getAssociation (i).getChamp () + ".value, '" +
                                    pArbre.getAssociation (i).getLibelle () + "') ;\n" ;
        }
        if (pArbre.getAssociation (i).isObligatoire ())
        {
          lGestionnaireValidation += "}\n" ;
        }
      }
      catch (Exception eException)
      {
        eException.printStackTrace () ;
        throw new JspException (CConstante.EXC_TRANSFERT) ; 
        
      }
    }
    
    // Appelle récursivement gestionnaireValidation pour tous les enfants.
    for (int i = 0; i < pArbre.getNbEnfant (); i ++)
    {
      lGestionnaireValidation += gestionnaireValidation (pArbre.getEnfant (i)) ;
    }
    
    // Retourne le gestionnaire d'évenement.
    return lGestionnaireValidation ;
  }


  /**
   * Récupère le code additionnel javascript pour le bouton.
   * @return Code additionnel (pour ajouter de nouveaux évenements par exemple).
   */
  public String getAdditionnel ()
  {
    return mAdditionnel ;
  }


  /**
   * Initialise le code additionnel javascript pour le bouton.
   * @param pAdditionnel Code additionnel (pour ajouter de nouveaux évenements par exemple).
   */
  public void setAdditionnel (String pAdditionnel)
  {
    mAdditionnel = pAdditionnel ;
  }


  /**
   * Récupère le libellé du bouton de soumission.
   * @return Libellé du bouton de soumission.
   */
  public String getLibelle ()
  {
    return mLibelle ;
  }


  /**
   * Initialise le libellé du bouton de soumission.
   * @param pLibelle Libellé du bouton de soumission.
   */
  public void setLibelle (String pLibelle)
  {
    mLibelle = pLibelle ;
  }


  /**
   * Récupère la valeur renvoyée par le bouton de soumission.
   * @return Valeur renvoyée par le bouton de soumission.
   */
  public String getValeur ()
  {
    return mValeur ;
  }


  /**
   * Initialise la valeur renvoyée par le bouton de soumission.
   * @param pValeur Valeur renvoyée par le bouton de soumission.
   */
  public void setValeur (String pValeur)
  {
    mValeur = pValeur ;
  }


  /**
   * Récupère le code javascript de validation.
   * @return Code javascript de validation.
   */
  public String getValidation ()
  {
    return mValidation ;
  }
  
  
  /**
   * Initialise le code javascript de validation.
   * @param pValidation Code javascript de validation.
   */
  public void setValidation (String pValidation)
  {
    mValidation = pValidation ;
  }
  
  
  /**
   * Retourne la valeur indiquant si la vérification de champ doit être réalisée.
   * @return "true" si la vérification de champ doit être réalisée et "false" sinon.
   */
  public boolean getVerification ()
  {
    return mVerification ;
  }


  /**
   * Initialise la valeur indiquant si la vérification de champ doit être réalisée.
   * @param pVerification "true" si la vérification de champ doit être réalisée et "false" sinon.
   */
  public void setVerification (boolean pVerification)
  {
    mVerification = pVerification ;
  }
}
