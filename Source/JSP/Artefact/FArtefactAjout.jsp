<%@page import="owep.controle.CConstante"%>
<%@page import="owep.modele.execution.MArtefact"%>
<%@page import="owep.modele.execution.MArtefactImprevue"%>

<jsp:useBean id="lArtefact"        class="owep.modele.execution.MArtefact"         scope="page"/>
<jsp:useBean id="lArtefactImprevu" class="owep.modele.execution.MArtefactImprevue" scope="page"/>

<%
    //localisation
    java.util.ResourceBundle messages;
    messages = java.util.ResourceBundle.getBundle("MessagesBundle");
    
    if(request.getAttribute (CConstante.PAR_ARTEFACT) != null)
    {
      lArtefact = (MArtefact) request.getAttribute (CConstante.PAR_ARTEFACT) ;
      lArtefactImprevu = null;
    }
    //Artefact imprévu
    else
    {
      lArtefactImprevu = (MArtefactImprevue) request.getAttribute (CConstante.PAR_ARTEFACT_IMPREVU) ; 
      lArtefact = null ;   
    } 
%>

<%
if (lArtefact != null) 
{
%>

<table width="100%">
<tr>
<td width="100%">
  <form action="./ArtefactAjout" method="post" enctype="multipart/form-data">
      
    <%if (lArtefact.getNomFichier() != null) {%>
    <p class="texteImportant">
      <%=messages.getString("ajoutArtefactAlerte1")%> <%=lArtefact.getNom()%> : <%=lArtefact.getNomFichier()%> !! 
      <br>
      <%=messages.getString("ajoutArtefactAlerte2")%>
    </p>
    <br>
    <%}%>

    <center>
      <font class="titre3"><%=messages.getString("ajoutArtefactNouveau")%></font>
      <input class="niveau2" size="<%= CConstante.LNG_FICHIER %>" maxlength="<%= CConstante.TXT_FICHIER %>" type="file" name="fichierArtefact"
       onmouseover="tooltipOn(this, event, '<%=messages.getString("ajoutArtefactAideNouveau")%>')" onmouseout="tooltipOff(this, event)">
      <input type="hidden" name="pArtefact" value="<%= lArtefact.getId () %>">
    </center>
  <br>
  
</td>
</tr> 
    
<tr>
<td width="100%">    
  <table border="0" width="100%">
    <tr>
      <td align="right" width="50%">
        <font class="texteSubmit">
          <input class="bouton" type="submit" value="<%=messages.getString("boutonValider")%>"
           onmouseover="tooltipOn(this, event, '<%=messages.getString("ajoutArtefactAideBoutonValider")%>')" onmouseout="tooltipOff(this, event)">
          </form>
        </font>
      </td>
      <td align="left" width="50%">
        <form action="../Tache/TacheVisu" method="get">
          <input class="bouton" type="submit" value="<%=messages.getString("boutonAnnuler")%>"
           onmouseover="tooltipOn(this, event, '<%=messages.getString("ajoutArtefactAideBoutonAnnuler")%>')" onmouseout="tooltipOff(this, event)">
          <input type="hidden" name="pTacheAVisualiser" value="<%= lArtefact.getTacheSortie ().getId () %>">
        </form>
      </td>
    </tr>
  </table>
</td>
</tr>
</table>

<%
}
/****************************** Artefact Imprévu *******************************/
else
{
%>

<table width="100%">
<tr>
<td width="100%">
  <form action="./ArtefactAjout" method="post" enctype="multipart/form-data">
      
    <%if (lArtefactImprevu.getNomFichier() != null) {%>
    <p class="texteImportant">
      <%=messages.getString("ajoutArtefactAlerte1")%> <%=lArtefactImprevu.getNom()%> : <%=lArtefactImprevu.getNomFichier()%> !! 
      <br>
      <%=messages.getString("ajoutArtefactAlerte2")%>
    </p>
    <br>
    <%}%>

    <center>
      <font class="titre3"><%=messages.getString("ajoutArtefactNouveau")%></font>
      <input class="niveau2" size="<%= CConstante.LNG_FICHIER %>" maxlength="<%= CConstante.TXT_FICHIER %>" type="file" name="fichierArtefact"
       onmouseover="tooltipOn(this, event, '<%=messages.getString("ajoutArtefactAideNouveau")%>')" onmouseout="tooltipOff(this, event)">
      <input type="hidden" name="pArtefactImprevu" value="<%= lArtefactImprevu.getId () %>">
    </center>
  <br>
  
</td>
</tr> 
    
<tr>
<td width="100%">    
  <table border="0" width="100%">
    <tr>
      <td align="right" width="50%">
        <font class="texteSubmit">
          <input class="bouton" type="submit" value="<%=messages.getString("boutonValider")%>"
           onmouseover="tooltipOn(this, event, '<%=messages.getString("ajoutArtefactAideBoutonValider")%>')" onmouseout="tooltipOff(this, event)">
          </form>
        </font>
      </td>
      <td align="left" width="50%">
        <form action="../Tache/TacheVisu" method="get">
          <input class="bouton" type="submit" value="<%=messages.getString("boutonAnnuler")%>"
           onmouseover="tooltipOn(this, event, '<%=messages.getString("ajoutArtefactAideBoutonAnnuler")%>')" onmouseout="tooltipOff(this, event)">
          <input type="hidden" name="pTacheImprevueAVisualiser" value="<%= lArtefactImprevu.getTacheImprevueSortie ().getId () %>">
        </form>
      </td>
    </tr>
  </table>
</td>
</tr>
</table>

<%}%>

<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "<%=messages.getString("ajoutArtefactAide")%> " ;
</script>
