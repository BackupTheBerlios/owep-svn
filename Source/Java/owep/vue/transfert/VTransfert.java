package owep.vue.transfert ;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import owep.controle.CConstante;


/**
 *
 */
public class VTransfert
{
  protected static String mDernierChamp = "" ;
  
  
  public static void transferer (HttpServletRequest pRequete, Object pBean, String pIdArbre) throws ServletException 
  {
    assert (VArbreBeans) pRequete.getSession ().getAttribute (pIdArbre) != null ;
    
    transfererRecursif (pRequete, pBean, (VArbreBeans) pRequete.getSession ().getAttribute (pIdArbre)) ; 
  }


  private static void transfererRecursif (HttpServletRequest pRequete, Object pBean, VArbreBeans pArbre) throws ServletException
  {
    // champs
    for (int i = 0; i < pArbre.getNbAssociations (); i ++)
    {
      try
      {
        Class[]  lTypeParam  = new Class [1] ; // Type du paramètre passé à la fonction setteur
        Class[]  lTabClass   = new Class [1] ;
        Object[] lParam2     = new Object [1] ;
        
        Object[] lValeur       = new Object [1] ;
        lValeur[0] = pRequete.getParameter (pArbre.getAssociation (i).getChamp ()) ;
        lTabClass[0] = String.class ;
        
        lTypeParam [0] = (Class) Class.forName (VTransfertConstante.TRANSFERT_CONVERTORPACKAGE + pArbre.getAssociation (i).getConvertor ()).getMethod ("getType", null).invoke (null, null) ;
        lParam2 [0]    = Class.forName (VTransfertConstante.TRANSFERT_CONVERTORPACKAGE + pArbre.getAssociation (i).getConvertor ()).getMethod ("getObject", lTabClass).invoke (null, lValeur) ;
        
        pBean.getClass ().getMethod (pArbre.getAssociation (i).getMembre (), lTypeParam).invoke (pBean, lParam2) ;
      }
      catch (Exception eException)
      {
        eException.printStackTrace () ;
        throw new ServletException(CConstante.EXC_TRANSFERT) ;
      }
    }
        
    // bean enfant
    for (int i = 0; i < pArbre.getNbEnfant (); i ++)
    {
      try
      {
        Object[] lParam = new Object [1] ;
        Class[] lParam2 = new Class [1] ;
        lParam [0] = new Integer (i) ;
        lParam2 [0] = Integer.TYPE ;
        transfererRecursif (pRequete, pBean.getClass ().getMethod (pArbre.getEnfant (i).getBean (), lParam2).invoke (pBean, lParam), pArbre.getEnfant(i)) ;
      }
      catch (Exception eException)
      {
        eException.printStackTrace () ;
        throw new  ServletException(CConstante.EXC_TRANSFERT) ;
      }
    }
  }


  /**
   * Récupère la valeur du bouton de soumission.
   * @param pRequete Requete transmise par un bouton de soumission.
   * @param pValeur Valeur attendue pour le bouton.
   * @return true si la valeur transmise et attendue sont équivalentes et false sinon.
   */
  public static boolean getValeurTransmise (HttpServletRequest pRequete, String pValeur)
  {
    String lValeurTransmise = pRequete.getParameter (VTransfertConstante.TRANSFERT_SOUMISSION) ;
    
    if (pValeur.equals (CConstante.PAR_VIDE))
    {
      return lValeurTransmise == null ; 
    }
    else
    {
      return (lValeurTransmise != null) && (lValeurTransmise.equals (pValeur)) ; 
    }
  }


  /**
   * Retourne le nom du dernier champ qui a été définit dans la page.
   * @return Nom du dernier champ qui a été définit dans la page.
   */
  public static String getDernierChamp ()
  {
    return mDernierChamp ;
  }


  /**
   * Initialise le nom du dernier champ qui a été définit dans la page.
   * @param pDernierChamp Nom du dernier champ qui a été définit dans la page.
   */
  protected static void setDernierChamp (String pDernierChamp)
  {
    mDernierChamp = pDernierChamp ;
  }
}