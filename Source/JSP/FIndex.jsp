<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<center>

<%
  // Récupération des parametres
  String mProbleme = (String) request.getAttribute("mProbleme"); // Probleme rencontré lors de l'enregistrement du collaborateur

  //localisation
  String langue = new String("fr");
  String pays = new String("FR");
  
  java.util.Locale currentLocale;
  java.util.ResourceBundle messages;
  currentLocale = new java.util.Locale(langue, pays);

  messages = java.util.ResourceBundle.getBundle("MessagesBundle", currentLocale);

  // Si l'attribut mProbleme existe alors un probleme est survenu lors de l'dentification
  if(mProbleme != null)
  {
%>
Le login et le mot de passe saisi ne sont pas valide<BR>
<%
  }
%>

<form action="../Outil/Connexion" method="post">
  <table class="tableauInvisible" border="0" cellpadding="1" cellspacing="1">
    <tr>
      <td class="caseInvisible" rowspan="2" align="right"><%=messages.getString("identificationUtilisateur")%><br><%=messages.getString("identificationMotDePasse")%></td>
      <td class="caseInvisible" rowspan="2"><input type=text size=10 name="login"><br><input type="PASSWORD" size=10 name="pwd"></td>
    </tr>
  </table>
  <br><br>
  <font class="texteSubmit">
    <input type="submit" value="<%=messages.getString("identificationValider")%>">
  </font>
</form>
</center>
