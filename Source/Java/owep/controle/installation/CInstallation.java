package owep.controle.installation;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.ServletException;

/**
 * Redirige vers la première page d'installation.
 */
public class CInstallation extends CControleurBaseInstallation
{
  public String traiter () throws ServletException
  {
    File f = new File(getServletContext ().getRealPath ("/")+"/WEB-INF/Database.xml");
    
    if(f.exists()){
      f.delete();
    }
    
    return "/JSP/Installation/Bienvenue.jsp";
  }

}
