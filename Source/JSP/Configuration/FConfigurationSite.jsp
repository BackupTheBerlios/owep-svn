<%@ page language="java" %>
<%@page import="owep.controle.CConstante"%>
<%@page import="owep.modele.configuration.MConfigurationSite"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="owep.infrastructure.Session"%>

<jsp:useBean id="lConfiguration" class="owep.modele.configuration.MConfigurationSite" scope="page"/>
<jsp:useBean id="lSession"       class="owep.infrastructure.Session"          scope="page"/> 

<%
  // Recuperation de la session
  HttpSession httpSession = request.getSession(true);
  lSession = (Session) httpSession.getAttribute("SESSION");

    lConfiguration = (MConfigurationSite) request.getAttribute (CConstante.PAR_CONFIGURATION) ;
    //Récupération du ressource bundle
    ResourceBundle messages = lSession.getMessages();
%>
<center>
<form action="./ConfigurationSite" method="post">
<table class="tableauInvisible" border="0" cellpadding="1" cellspacing="1">
<tr>
    <td class="caseNiveau2SansBordure">
      <%=messages.getString("configurationSiteLangue")%>
	</td>
	<td>
      <select class="niveau1" name="<%=CConstante.PAR_LANGUE%>" size ="1">
         <option VALUE="fr_FR" <%if (lConfiguration.getLangue().equals("fr_FR")) {%> selected <%}%>><%=messages.getString("configurationSiteLangueFrancais")%></option>
         <option VALUE="en_US" <%if (lConfiguration.getLangue().equals("en_US")) {%> selected <%}%>><%=messages.getString("configurationSiteLangueAnglais")%></option>
      </select>
    </td>  
  </tr>
  
  <tr>
    <td class="caseNiveau2SansBordure">
      <%=messages.getString("configurationSiteApparence")%>
	</td>
	<td>
      <select class="niveau1" name="<%=CConstante.PAR_APPARENCE%>" size ="1">
         <option VALUE="Blue.css" <%if (lConfiguration.getApparence().equals("Blue.css")) {%> selected <%}%>><%=messages.getString("configurationSiteApparenceBleue")%></option>
         <option VALUE="Red.css" <%if (lConfiguration.getApparence().equals("Red.css")) {%> selected <%}%>><%=messages.getString("configurationSiteApparenceRouge")%></option>
      </select>
    </td>
  </tr>
</table>  
</center>
    <br>
  <center>  
    <input name="<%= CConstante.PAR_SUBMIT %>" type="hidden" value=""/> 
    <transfert:transfertsubmit libelle="Valider" valeur="<%= CConstante.PAR_SUBMIT %>" 
     additionnel="onmouseover=\"tooltipOn(this, event, 'Cliquez pour valider toutes les modifications.')\" onmouseout=\"tooltipOff(this, event)\""/>  
    <input class="bouton" type="submit" value="Valider"
     onmouseover="tooltipOn(this, event, 'Cliquez pour valider le formulaires.')" onmouseout="tooltipOff(this, event)">
  </center>

</form>









