<%@page import="owep.controle.CConstante" %>
<%@page import="owep.modele.execution.MProjet" %>

<%
  // Utilisé pour stocker le code javascript.
  String lCodeValidation ;
  
  // Récupération des paramètres.
  MProjet    pProjet    = (MProjet)    request.getAttribute (CConstante.PAR_PROJET) ;
  

  //Récupération du ressource bundle
  java.util.ResourceBundle messages;
  messages = java.util.ResourceBundle.getBundle("MessagesBundle");
  
  java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");
%>

<%String boutonValider = messages.getString("boutonValider");%>

<center>
<form action="./CloturerProjet" method="post" name="formCloturerProjet">
   
  <table class="tableau" border="0" cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td class="caseNiveau1" colspan="4">
        <%=pProjet.getNom()%>
      </td>
    </tr>
    <tr>
      <td class="caseNiveau1" width="20%">
        <%=messages.getString("cloturerIterationDateDebutPrevue")%>
      </td>
      <td class="caseNiveau3" width="30%">
		<%=dateFormat.format(pProjet.getDateDebutPrevue())%>
      </td>
      <td class="caseNiveau1" width="20%">
        <%=messages.getString("cloturerIterationDateFinPrevue")%>
      </td>
      <td class="caseNiveau3" width="30%">
        <%=dateFormat.format(pProjet.getDateFinPrevue())%>
      </td>
    </tr>   	
    <tr>
      <td class="caseNiveau1" width="20%">
        <a href="#" class="niveau1" onmouseover="tooltipTitreOn(this, event, '<%=messages.getString("champObligatoireAide")%>', '<%=messages.getString("cloturerProjetAideDateDebutReelle")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("cloturerIterationDateDebutReelle")%></a>
      </td>
      <td class="caseNiveau3" width="30%">  
        <input name="mDateDebutReelle" class="niveau2" type="text" value="<%=dateFormat.format(pProjet.getDateDebutReelle())%>">
      </td>
      <td class="caseNiveau1" width="20%">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("champObligatoireAide")%>', '<%=messages.getString("cloturerProjetAideDateFinReelle")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("cloturerIterationDateFinReelle")%></a>
      </td>
      <td class="caseNiveau3" width="30%">   
        <input name="mDateFinReelle" class="niveau2" type="text" value="<%=dateFormat.format(new java.util.Date())%>">
      </td>
    </tr>

    <tr>
      <td class="caseNiveau1" width="20%">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("cloturerIterationAideBilan")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("cloturerIterationBilan")%></a>
      </td>
      <td class="caseNiveau3" width="80%" colspan="3">  
        <textarea name="mBilan" class="niveau2" rows="10" cols="<%= CConstante.LNG_LARGE %>" maxlength="<%= CConstante.TXT_LARGE %>"></textarea>
      </td>
    </tr>
  </tbody>
  </table>
  
  <p class="texteObligatoire"><%=messages.getString("champObligatoire")%></p>
  <p class="texteSubmit" align='center'>
    <input class="bouton" type="button" value="<%=messages.getString("identificationValider")%>" onclick="valider();"
     onmouseover="tooltipOn(this, event, '<%=messages.getString("aideCollaborateurValidationFormulaire")%>')" onmouseout="tooltipOff(this, event)">
    <input type="button" value="<%=messages.getString("boutonAnnuler")%>" class="bouton" onclick="window.location.href = '/owep/Tache/ListeTacheVisu' ;"
     onmouseover="tooltipOn(this, event, '<%=messages.getString("cloturerIterationAideAnnuler")%>')" onmouseout="tooltipOff(this, event)"/>
  </p>
  
</form>
</center>

<script type="text/javascript" language="JavaScript">
function valider() {
  var texte = "";
  if (document.formCloturerProjet.mDateDebutReelle.value == "") {
    texte += "";
  }
  if (document.formCloturerProjet.mDateFinReelle.value == "") {
    texte += "";
  }
  if(texte == ""){
    if(!isDate(document.formCloturerProjet.mDateDebutReelle.value))
      texte += "";
    if(!isDate(document.formCloturerProjet.mDateFinReelle.value))
      texte += "";
  }
  if(texte == "")
    document.formCloturerProjet.submit();
  else
    alert(texte);
}

function isDate (pDate)
{
  var lRegExpression = /^\d{1}\d{1}\/\d{1}\d{1}\/\d{1}\d{1}\d{1}\d{1}$/ ;
  
  if ((pDate.length != 0) && (! lRegExpression.test (pDate)))
  {
    return false ;
  }
  else
  {
    return true ;
  }
}
</script>

<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "<%=messages.getString("cloturerIterationAide")%>";
</script>
