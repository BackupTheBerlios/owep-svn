<%@page import="owep.infrastructure.Session"%>
<%@page import="owep.controle.CConstante" %>

<jsp:useBean id="lSession" class="owep.infrastructure.Session" scope="page"/>

<center>

<%
  // Recuperation de la session
  HttpSession httpSession = request.getSession(true);
  lSession = (Session) httpSession.getAttribute("SESSION");
  
  //Récupération du ressource bundle
  java.util.ResourceBundle lMessages = lSession.getMessages();
  
  // Récupération des parametres
  String mErreur = (String) request.getAttribute("mErreur");       // Probleme rencontré lors de la modification du profil
  
  String mNom = (String) request.getAttribute("mNom");                 // Nom du collaborateur
  String mPrenom = (String) request.getAttribute("mPrenom");           // Prenom du collaborateur
  String mLogin = (String) request.getAttribute("mLogin");             // Login du collaborateur
  String mMail = (String) request.getAttribute("mMail");               // Email du collaborateur
  String mAdresse = (String) request.getAttribute("mAdresse");         // Adresse du collaborateur
  String mTelephone = (String) request.getAttribute("mTelephone");     // Numéro du telephone du collaborateur
  String mPortable = (String) request.getAttribute("mPortable");       // Numéro du portable du collaborateur
  String mCommentaire = (String) request.getAttribute("mCommentaire"); // Commentaire du collaborateur
  
%>

<script language="javascript">
function changementMdp(){
  document.modificationProfil.mModifMdp.value = "1";
}
function envoyer(){
  var texte = "";
  
  if(document.modificationProfil.mNom.value == "")
    texte = texte + "<%=lMessages.getString("messageNom")%>\n";
  if(document.modificationProfil.mLogin.value == "")
    texte = texte + "<%=lMessages.getString("messageLogin")%>\n";
  else{
    if(document.modificationProfil.mLogin.value != "<%=mLogin%>")
      document.modificationProfil.mModifLogin.value = 1;
  }
  if(document.modificationProfil.mNouveauMdp.value != document.modificationProfil.mConfirmationMdp.value)
    texte = texte + "<%=lMessages.getString("messageMdp")%>\n";
  
  if(texte == "")
    document.modificationProfil.submit();
  else
    alert(texte);
}
</script>
<%=mErreur%>
<br>
<%
  if(!mErreur.equals(lMessages.getString("profilMessageOk")))
  {
%>
<form action="./ModificationProfil" method="post" name="modificationProfil">
  <table class="tableau" border="0" cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipTitreOn(this, event, '<%=lMessages.getString("aideChampObligatoire")%>', '<%=lMessages.getString("aideCollaborateurNom")%>')" onmouseout="tooltipOff(this, event)"><%=lMessages.getString("collaborateurNom")%> *</a>
      </td>
      <td class="caseNiveau3"><input type="text" name="mNom" value="<%=mNom%>" size="<%= CConstante.TXT_LOGIN %>" class="niveau2"></td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=lMessages.getString("aideCollaborateurPrenom")%>')" onmouseout="tooltipOff(this, event)"><%=lMessages.getString("collaborateurPrenom")%></a>
      </td>
      <td class="caseNiveau3"><input type="text" name="mPrenom" value="<%=mPrenom%>" size="<%= CConstante.TXT_LOGIN %>" class="niveau2"></td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipTitreOn(this, event, '<%=lMessages.getString("aideChampObligatoire")%>', '<%=lMessages.getString("aideCollaborateurMail")%>')" onmouseout="tooltipOff(this, event)"><%=lMessages.getString("collaborateurMail")%></a>
      </td>
      <td class="caseNiveau3"><input type="text" name="mMail" value="<%=mMail%>" size="<%= CConstante.TXT_LOGIN %>" class="niveau2"></td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=lMessages.getString("aideCollaborateurAdresse")%>')" onmouseout="tooltipOff(this, event)"><%=lMessages.getString("collaborateurAdresse")%></a>
      </td>
      <td class="caseNiveau3"><textarea name="mAdresse" rows=3><%=mAdresse%></textarea></td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=lMessages.getString("aideCollaborateurTelephone")%>')" onmouseout="tooltipOff(this, event)"><%=lMessages.getString("collaborateurTelephone")%></a>
      </td>
      <td class="caseNiveau3"><input type="text" name="mTelephone" value="<%=mTelephone%>" size="<%= CConstante.TXT_LOGIN %>" class="niveau2"></td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=lMessages.getString("aideCollaborateurPortable")%>')" onmouseout="tooltipOff(this, event)"><%=lMessages.getString("collaborateurPortable")%></a>
      </td>
      <td class="caseNiveau3"><input type="text" name="mPortable" value="<%=mPortable%>" size="<%= CConstante.TXT_LOGIN %>" class="niveau2"></td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=lMessages.getString("aideCollaborateurCommentaire")%>')" onmouseout="tooltipOff(this, event)"><%=lMessages.getString("collaborateurCommentaire")%></a>
      </td>
      <td class="caseNiveau3"><textarea name="mCommentaire" rows=3><%=mCommentaire%></textarea></td>
    </tr>
  </tbody>
  </table>
  <br><br>
  <table class="tableau" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipTitreOn(this, event, '<%=lMessages.getString("aideChampObligatoire")%>', '<%=lMessages.getString("aideCollaborateurLogin")%>')" onmouseout="tooltipOff(this, event)"><%=lMessages.getString("collaborateurLogin")%> *</a>
      </td>
      <td class="caseNiveau3"><input type="text" name="mLogin" value="<%=mLogin%>" size="<%= CConstante.TXT_LOGIN %>" class="niveau2"></td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=lMessages.getString("aideCollaborateurProfilAncienMdp")%>')" onmouseout="tooltipOff(this, event)"><%=lMessages.getString("profilAncienMdp")%></a>
      </td>
      <td class="caseNiveau3"><input type="PASSWORD" name="mAncienMdp" size="<%= CConstante.TXT_LOGIN %>" class="niveau2" onclick="changementMdp();"></td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=lMessages.getString("aideCollaborateurProfilNouveauMdp")%>')" onmouseout="tooltipOff(this, event)"><%=lMessages.getString("profilNouveauMdp")%></a>
      </td>
      <td class="caseNiveau3"><input type="PASSWORD" name="mNouveauMdp" size="<%= CConstante.TXT_LOGIN %>" class="niveau2" onclick="changementMdp();"></td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=lMessages.getString("aideCollaborateurProfilConfirmationMdp")%>')" onmouseout="tooltipOff(this, event)"><%=lMessages.getString("profilConfirmationMdp")%></a>
      </td>
      <td class="caseNiveau3"><input type="PASSWORD" name="mConfirmationMdp" size="<%= CConstante.TXT_LOGIN %>" class="niveau2" onclick="changementMdp();"></td>
    </tr>
  </table>
  <input type="hidden" name="mModifLogin" value="0">
  <input type="hidden" name="mModifMdp" value="0">
  <br><br>
  <p class="texteObligatoire"><%=lMessages.getString("champObligatoire")%></p>
  <p class="texteSubmit">
    <input class="bouton" type="button" value="<%=lMessages.getString("identificationValider")%>" onclick="envoyer();"
     onmouseover="tooltipOn(this, event, '<%=lMessages.getString("aideCollaborateurValidationFormulaire")%>')" onmouseout="tooltipOff(this, event)">
  </p>
</form>
<%
  }
%>
</center>

<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "<%=lMessages.getString("aidePageCollaborateurProfil")%>" ;
</script>
