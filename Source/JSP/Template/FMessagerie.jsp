<%@page language="java" %>
<%@page import="java.util.ResourceBundle"%>
<%@page import="owep.infrastructure.Session"%>


<%
  // Récuperation de la session.
  HttpSession httpSession = request.getSession(true);
  Session lSession = (Session) httpSession.getAttribute("SESSION");
  
  //Récupération du ressource bundle.
  ResourceBundle messages = lSession.getMessages () ;
%>

<table cellpadding="0" cellspacing="0">
<tbody style="vertical-align : top">
  <tr>
  
    <!-- commandes de la messagerie -->
    <td>
      <table width="85px" cellpadding="0" cellspacing="0">
      <tbody>
        <tr>
          <td class="caseMessageCommande">
            <a class="messageCommande" href="mailto:"><%= messages.getString("messagerieEnvoyer") %></a>
          </td>
        </tr>
        <tr>
        <td class="caseMessageCommande">
            <a class="messageCommande" href="<%= messages.getString("messagerieRechercherValeur") %>"><%= messages.getString("messagerieRechercher") %></a>
          </td>
        </tr>
        <tr>
          <td class="caseMessageCommande">
            <a class="messageCommande" href="<%= messages.getString("messagerieAProposLien") %>"><%= messages.getString("messagerieAPropos") %></a>
          </td>
        </tr>
      </tbody>
      </table>
    </td>
    
    
    <!-- liste des messages -->
    <td width="100%">
      <table width="100%" cellpadding="0" cellspacing="0">
      <tbody>
        <tr>
          <td class="caseMessage">
            <a class="messageOrigine" href=""></a>
          </td>
          <td width="100%" class="caseMessage">
            <p class="message"></p>
          </td>
        </tr>
      </tbody>
      </table>
    </td>
    
  </tr>
</tbody>
</table>