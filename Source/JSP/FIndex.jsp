<%@page import="owep.controle.CConstante" %>

<center>
<%
  // Récupération des parametres
  String mProbleme = (String) request.getAttribute("mProbleme"); // Probleme rencontré lors de l'enregistrement du collaborateur

  //localisation
  java.util.ResourceBundle messages;
  messages = java.util.ResourceBundle.getBundle("MessagesBundle");

  // Si l'attribut mProbleme existe alors un probleme est survenu lors de l'dentification
  if(mProbleme != null)
  {
%>
Le login et le mot de passe saisi ne sont pas valide<br>
<%
  }
%>

<form action="/owep/Outil/Connexion" method="post">
  <table class="tableauInvisible" border="0" cellpadding="1" cellspacing="1">
    <tr>
      <td class="caseInvisible">
        <font class="titre3" onmouseover="tooltipOn(this, event, 'Entrez votre nom d\'utilisateur pour le logiciel OWEP.')" onmouseout="tooltipOff(this, event)">
          <%=messages.getString("identificationUtilisateur")%>
        </font>
      </td>
      <td class="caseInvisible">
        <input class="niveau2" type="text" size="<%= CConstante.TXT_LOGIN %>" name="login">
      </td>
    </tr>
    <tr>
      <td class="caseInvisible">
        <font class="titre3" onmouseover="tooltipOn(this, event, 'Entrez votre mot de passe pour le logiciel OWEP.')" onmouseout="tooltipOff(this, event)">
          <%=messages.getString ("identificationMotDePasse")%>
        </font>
      </td>
      <td class="caseInvisible">
        <input class="niveau2" type="PASSWORD" size="<%= CConstante.TXT_LOGIN %>" name="pwd">
      </td>
    </tr>
  </table>
  <br><br>
  <font class="texteSubmit">
    <input class="bouton" type="submit" value="<%=messages.getString ("identificationValider")%>">
  </font>
</form>
</center>

<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "Entrez votre <b>nom d'utilisateur</b> et votre <b>mot de passe</b> pour accéder au logiciel OWEP." ;
pCodeAide += " Si vous vous connectez pour la première fois, le <b>mot de passe</b> est identique à votre <b>nom d'utilisateur</b>." ;
pCodeAide += " Veillez donc bien à les modifier dans la section <b>\"Modifier son profil\"</b> accéssible depuis le menu." ;
</script>