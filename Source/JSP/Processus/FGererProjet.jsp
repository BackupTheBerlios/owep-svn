<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="owep.modele.execution.MCollaborateur"%>
<%@page import="owep.controle.CConstante" %>
<%@page import="owep.infrastructure.Session" %>
<%@page import="java.util.ResourceBundle" %>

<jsp:useBean id="lCollaborateur" class="owep.modele.execution.MCollaborateur" scope="page"/>
<jsp:useBean id="lSession" class="owep.infrastructure.Session" scope="page"/>

<%@ page language="java" %>

<%
  // Recuperation de la session
  HttpSession httpSession = request.getSession(true);
  lSession = (Session) httpSession.getAttribute("SESSION");
  
  // Récupération du ressource bundle
  ResourceBundle lMessage = lSession.getMessages();
  
  // Récupération parametres
  ArrayList listCollaborateur = (ArrayList) request.getAttribute("mListCollaborateur");
  String lErreur = (String) request.getAttribute("erreur");
  String lIdProjet = (String) request.getAttribute("idProjet");
  
  // Aucune erreur n'est survenue
  if(lErreur.equals(""))
  {
%>

<script language="javascript">

function envoyer()
{
  var texte = "";
  if(document.formCreerProjet.mNom.value == "")
  {
    //alert("Veuillez saisir un nom.");
    texte = texte + "<%=lMessage.getString("projetMessageNom")%>\n";
  }
  if(document.formCreerProjet.mDateDebut.value == "")
    texte = texte + "<%=lMessage.getString("projetMessageDateDebut")%>\n";
  if(document.formCreerProjet.mDateFin.value == "")
    texte = texte + "<%=lMessage.getString("projetMessageDateFin")%>\n";
  // Verification d'un responsable selectionné
  if(!(
<%
  if(listCollaborateur.size() > 1)
  {
    for(int i = 0 ; i < listCollaborateur.size() ; i++)
    {
%>
      document.formCreerProjet.mResponsable[<%=i%>].checked
<%
      if(i < listCollaborateur.size()-1)
      {
%>
      ||
<%
      }
    }
  }
  else{
%>
      document.formCreerProjet.mResponsable.checked
<%
  }
%>
      ))
    texte = texte + "<%=lMessage.getString("projetMessageResponsable")%>\n";
  // Verification d'un processus selectionné
  if(!(
      document.formCreerProjet.mFichierProcessus.value != ""))
    texte = texte + "<%=lMessage.getString("projetMessageProcessus")%>\n";
  else{
      var fich = document.formCreerProjet.mFichierProcessus.value;
      var extension = fich.slice(fich.length-4);
      if(extension != ".dpc" && extension != ".dpe")
        texte = texte + "<%=lMessage.getString("projetMessageExtension")%>\n";
      else
        document.formCreerProjet.mExtension.value = extension;
  }
  
  // Verification format date
  if(texte == ""){
    if(!isDate(document.formCreerProjet.mDateDebut.value)
        || !isDate(document.formCreerProjet.mDateFin.value))
      texte = texte + "<%=lMessage.getString("projetErreurDate")%>\n";
  }
  
  if(texte == "")
    document.formCreerProjet.submit();
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

<center>
<form name="formCreerProjet" method="post" action="/owep/Processus/GererProjet" enctype="multipart/form-data">
<input type="hidden" name="creation" value="1">
<input type="hidden" name="mExtension">
<table class="tableau" border="0" cellpadding="0" cellspacing="0">
<tbody>
  <tr>
    <td class="caseNiveau1">
            <a href="#" class="niveau1" onmouseover="tooltipTitreOn(this, event, 'Champ obligatoire', 'Nom du projet.')" onmouseout="tooltipOff(this, event)"><%=lMessage.getString("projetNom")%> *</a>
    </td>
    <td class="caseNiveau3">
      <input type="text" name="mNom" value="" size="<%= CConstante.TXT_LOGIN %>" class="niveau2">
    </td>
  </tr>
  <tr>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipTitreOn(this, event, 'Champ obligatoire', 'Date prévue à laquelle doit commencer le projet.')" onmouseout="tooltipOff(this, event)"><%=lMessage.getString("projetDateDebut")%> *</a>
    </td>
    <td class="caseNiveau3">
      <input type="text" name="mDateDebut" value="" size="<%= CConstante.TXT_DATE %>" class="niveau2">
    </td>
  </tr>
  <tr>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipTitreOn(this, event, 'Champ obligatoire', 'Date prévue à laquelle doit se terminer le projet.')" onmouseout="tooltipOff(this, event)"><%=lMessage.getString("projetDateFin")%> *</a>
    </td>
    <td class="caseNiveau3">
      <input type="text" name="mDateFin" value="" size="<%= CConstante.TXT_DATE %>" class="niveau2">
    </td>
  </tr>
  <tr>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, 'Description de ce qui doit être réalisé au cours du projet.')" onmouseout="tooltipOff(this, event)"><%=lMessage.getString("projetDescription")%></a>
    </td>
    <td class="caseNiveau3">
      <textarea name="mDescription" class="niveau2"></textarea>
    </td>
  </tr>
  <tr>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, 'Budget attribué au projet.')" onmouseout="tooltipOff(this, event)"><%=lMessage.getString("projetBudget")%></a>
    </td>
    <td class="caseNiveau3">
      <input type="text" name="mBudget" value="" size="<%= CConstante.TXT_CHARGE %>" class="niveau2">
    </td>
  </tr>
  <tr>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipTitreOn(this, event, 'Champ obligatoire', 'Choisissez le fichier <b>.DPE</b> qui définit le processus à appliquer sur le projet.')" onmouseout="tooltipOff(this, event)"><%=lMessage.getString("projetProcessus")%> *</a>
    </td>
    <td class="caseNiveau3">
      <input type="file" name="mFichierProcessus" value="<%=lMessage.getString("projetParcourir")%>" size="<%= CConstante.TXT_LOGIN %>"  class="niveau2">
    </td>
  </tr>
  <tr>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipTitreOn(this, event, 'Champ obligatoire', 'Superviseur qui s\'occupe du projet.')" onmouseout="tooltipOff(this, event)"><%=lMessage.getString("projetResponsable")%> *</a>
    </td>
    <td class="caseNiveau3">
<%
  Iterator it = listCollaborateur.iterator();
  while(it.hasNext())
  {
    lCollaborateur = (MCollaborateur) it.next();
%>
      <input name="mResponsable" type="radio" value="<%=lCollaborateur.getId()%>">
      <%=lCollaborateur.getPrenom()+"&nbsp;"+lCollaborateur.getNom()%>
      <br>
<%
  }
%>
    </td>
  </tr>
</tbody>
</table>
</form>
<br>
<p class="texteSubmit"><input class="bouton" type="button" value="<%=lMessage.getString("projetCreer")%>" onclick="envoyer();"></p>
</center>

<p class="texteObligatoire">Les champs marqué d'un * sont obligatoires.</p>

<%
  }
  else
  {
    // Une erreur est survenue
%>
<center>
  <%=lErreur%><br>
  
<%
    if(lErreur.equals(lMessage.getString("projetMessageCreer")) && !lIdProjet.equals("0"))
    {
%>
  <a href="../Projet/OuvrirProjet?mIdProjet=<%=lIdProjet%>"><%=lMessage.getString("projetMessageOuvrir")%></a><br>
  <a href="../Processus/GererProjet"><%=lMessage.getString("projetMessageCreerNouveau")%></a>
<%
    }
%>

</center>
<%
  }
%>

<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "La page de <b>Création d\'un projet</b> permet, comme son nom l\'indique, à un chef de projet de créer un nouveau projet accessible par <b>OWEP</b>." ;
pCodeAide += " Vous devez pour cela définir les <b>caractéristiques</b> du projet (nom, dates, etc.), le <b>processus</b> appliqué sur le projet (fichier au format .DPE exporté depuis <b>IEPP</b>)" ;
pCodeAide += " et enfin le <b>superviseur</b> responsable du projet. La personne qui crée le projet est considérée comme <b>chef</b> de ce projet." ;
</script>
