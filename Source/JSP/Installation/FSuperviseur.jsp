
<%
  // Récupération des paramétres
  String mErreur = (String) request.getAttribute("mErreur");
  if(mErreur == null)
    mErreur = "";
%>

<!-- Code javascript -->
<script type="text/javascript" language="JavaScript">
function valider(){
  var texte = "";
  
  if(document.formCreerSuperviseur.mNom.value == "")
    texte += "Veuillez saisir un nom.\n";
  if(document.formCreerSuperviseur.mLogin.value == "")
    texte += "Veuillez saisir un login.\n";
  if(document.formCreerSuperviseur.mMail.value == "")
    texte += "Veuillez saisir un e-mail.\n";
  
  if(texte == "")
    document.formCreerSuperviseur.submit();
  else
    alert(texte);
}
</script>

<center>
<font class="titre3">Configuration du compte superviseur.</font>
<br><br>
<%=mErreur%>
<form action="/owep/Installation/Superviseur" method="post" name="formCreerSuperviseur">
  <table class="tableauInvisible" border="0" cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td class="caseInvisible">
        <a href="#" class="niveau2" onmouseover="tooltipTitreOn(this, event, 'Champ obligatoire', 'Nom du collaborateur.')" onmouseout="tooltipOff(this, event)">Nom *</a>
      </td>
      <td class="caseInvisible"><input class="niveau2" type="text" name="mNom"></td>
    </tr>
    <tr>
      <td class="caseInvisible">
        <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, 'Prénom du collaborateur.')" onmouseout="tooltipOff(this, event)">Prenom</a>
      </td>
      <td class="caseInvisible"><input class="niveau2" type="text" name="mPrenom"></td>
    </tr>
    <tr>
      <td class="caseInvisible">
        <a href="#" class="niveau2" onmouseover="tooltipTitreOn(this, event, 'Champ obligatoire', 'Nom que le collaborateur utilisera pour se connecter. Ce login doit être unique.')" onmouseout="tooltipOff(this, event)">Login *</a>
      </td>
      <td class="caseInvisible"><input class="niveau2" type="text" name="mLogin"></td>
    </tr>
    <tr>
      <td class="caseInvisible">
        <a href="#" class="niveau2" onmouseover="tooltipTitreOn(this, event, 'Champ obligatoire', 'Adresse mail du collaborateur. Cette adresse sera utilisée par <b>OWEP</b> pour transmettre des messages.')" onmouseout="tooltipOff(this, event)">E-Mail *</a>
      </td>
      <td class="caseInvisible"><input class="niveau2" type="text" name="mMail"</td>
    </tr>
    <tr>
      <td class="caseInvisible">
        <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, 'Domicile du collaborateur.')" onmouseout="tooltipOff(this, event)">Adresse</a>
      </td>
      <td class="caseInvisible"><textarea class="niveau2" name="mAdresse" rows=3></textarea></td>
    </tr>
    <tr>
      <td class="caseInvisible">
        <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, 'Numéro de téléphone fixe du collaborateur.')" onmouseout="tooltipOff(this, event)">Telephone</a>
      </td>
      <td class="caseInvisible"><input class="niveau2" type="text" name="mTelephone"</td>
    </tr>
    <tr>
      <td class="caseInvisible">
        <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, 'Numéro de portable du collaborateur.')" onmouseout="tooltipOff(this, event)">Portable</a>
      </td>
      <td class="caseInvisible"><input class="niveau2" type="text" name="mPortable"</td>
    </tr>
    <tr>
      <td class="caseInvisible">
        <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, 'Commentaires et précisions sur le collaborateur. Ces informations ne sont visibles que par les chefs de projets.')" onmouseout="tooltipOff(this, event)">Commentaire</a>
      </td>
      <td class="caseInvisible"><textarea class="niveau2" name="mCommentaire" rows=3></textarea></td>
    </tr>
  </tbody>
  </table>
  <input type="hidden" name="numPage" value="2">
  <p class="texteObligatoire">Les champs marqué d'une * sont obligatoires.</p>
    <input class="bouton" type="button" value="Suivant  &gt" onclick="valider();"
     onmouseover="tooltipOn(this, event, 'Cliquez pour valider le formulaires.')" onmouseout="tooltipOff(this, event)">
</form>
</center>

<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "La page de <b>Configuration du compte superviseur</b> permet de créer le compte utilisateur du superviseur. Utilisez le <b>login</b> que vous allez saisir pour vous connecter à l'application une fois l'installation fini. Les informations pourront être modifiés une fois l'utilisateur connecté au logiciel." ;
</script>
