<%@page import="owep.controle.CConstante"%>

<%
  // Récupération parametre
  String mErreur = (String) request.getAttribute("mErreur");
  if(mErreur == null)
    mErreur = "";
  
%>
<center>
  <font class="titre3">Configuration de la base de données.</font>
  <br><br>
  <%=mErreur%>
  <form method="post" action="/owep/Installation/ConfigurationBD" name="formConfigurationBD">
    <table class="tableauInvisible" border="0" cellpadding="1" cellspacing="1">
      <tr>
        <td class="caseInvisible" align="right">
          <a href="#" class="niveau2" onmouseover="tooltipTitreOn(this, event, 'Champ obligatoire', 'Remplacer <b>localhost</b> par l\'adresse DNS de votre serveur base de donnée.<br> Remplacer <b>3306</b> par le port d\'accès à votre base de données.')" onmouseout="tooltipOff(this, event)">URL de connexion à la base de données : </a>
        </td>
        <td class="caseInvisible">
          <input class="niveau2" type="text" name="url" value="jdbc:mysql://localhost:3306/" size="<%=CConstante.LNG_PETIT%>">
        </td>
      </tr>
      <tr>
        <td class="caseInvisible" align="right">
          <a href="#" class="niveau2" onmouseover="tooltipTitreOn(this, event, 'Champ obligatoire', 'Nom avec lequel on peut se connecter à la base données.')" onmouseout="tooltipOff(this, event)">Nom d'utilisateur base de données : </a>
        </td>
        <td class="caseInvisible">
          <input class="niveau2" type="text" name="login" value="root" size="<%=CConstante.LNG_LOGIN%>">
        </td>
      </tr>
      <tr>
        <td class="caseInvisible" align="right">
          <a href="#" class="niveau2" onmouseover="tooltipTitreOn(this, event, 'Champ obligatoire', 'Mot de passe associée au nom d\'utilisateur de la base de donnée.')" onmouseout="tooltipOff(this, event)">Mot de passe base de données : </a>
        </td>
        <td class="caseInvisible">
          <input class="niveau2" type="PASSWORD" name="mdp" value="" size="<%=CConstante.LNG_LOGIN%>">
        </td>
      </tr>
    </table>
    <br>
    <input type="submit" class="bouton" value="Suivant  &gt"
     onmouseover="tooltipOn(this, event, 'Cliquez pour valider les informations.')" onmouseout="tooltipOff(this, event)">
  </form>
</center>

<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "La page de <b>Configuration de la base de données</b> permet de saisir les informations nécessaires pour se connecter à la base de données. La base de donnée doit disposer d'une database portant le nom <b>owep</b>." ;
</script>
