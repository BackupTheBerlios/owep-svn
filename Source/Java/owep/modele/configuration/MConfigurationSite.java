/*
 * Created on 26 janv. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package owep.modele.configuration;

import owep.modele.MModeleBase;

/**
 * @author Victor Nancy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MConfigurationSite extends MModeleBase
{
  private int    mId;           
  private String mLangue;         //langue utilisée pour les textes
  private String mApparence;      //apparence du site
  private String mPathArtefact;   //path indiquant le dossier où sont téléchargés les artefacts
  private String mPathExport;     //path indiquant le dossier où sont stockés les fichiers de sauvegarde
  
  /**
   * @return Retourne la valeur de l'attribut id.
   */
  public int getId ()
  {
    return mId ;
  }
  /**
   * @param initialse id avec pId.
   */
  public void setId (int pId)
  {
    mId = pId ;
  }
  /**
   * @return Retourne la valeur de l'attribut langue.
   */
  public String getLangue ()
  {
    return mLangue ;
  }
  /**
   * @param initialse langue avec pLangue.
   */
  public void setLangue (String pLangue)
  {
    mLangue = pLangue ;
  }
  /**
   * @return Retourne la valeur de l'attribut pathArtefact.
   */
  public String getPathArtefact ()
  {
    return mPathArtefact ;
  }
  /**
   * @param initialse pathArtefact avec pPathArtefact.
   */
  public void setPathArtefact (String pPathArtefact)
  {
    mPathArtefact = pPathArtefact ;
  }

  /**
   * @return Retourne la valeur de l'attribut pathExport.
   */
  public String getPathExport ()
  {
    return mPathExport ;
  }
  
  /**
   * @param initialse pathExport avec pPathExport.
   */
  public void setPathExport (String pPathExport)
  {
    mPathExport = pPathExport ;
  }
  
  /**
   * @return Retourne la valeur de l'attribut apparence.
   */
  public String getApparence ()
  {
    return mApparence ;
  }
  /**
   * @param initialse apparence avec pApparence.
   */
  public void setApparence (String pApparence)
  {
    mApparence = pApparence ;
  }
}
