<%@page import="owep.controle.CConstante"%>
<%@page import="owep.modele.execution.MArtefact"%>
<%@page import="owep.infrastructure.Session"%>

<jsp:useBean id="lArtefact" class="owep.modele.execution.MArtefact" scope="page"/>
<jsp:useBean id="lSession"  class="owep.infrastructure.Session"     scope="page"/> 

<%
    lArtefact = (MArtefact) request.getAttribute (CConstante.PAR_ARTEFACT) ;
%>

<body>
<table width="100%">
<tr>
<td width="100%">
  <form action="./ArtefactAjout" method="post" enctype="multipart/form-data">
      
      <%if (lArtefact.getNomFichier() != null) {%>
      <center>
        Attention il existe déjà un fichier pour l'artefact <%=lArtefact.getNom()%> : <%=lArtefact.getNomFichier()%> !! 
        <br>
        Si vous continuez vous remplacerez l'ancien fichier par le nouveau. 
      </center>
      <br><br>
      <%}%>

      <center>
        Nouveau fichier : <input type="file" name="fichierArtefact">
      </center>
    <br>
  
    <input type="hidden" name="pArtefact" value="<%=lArtefact%>>
</td>
</tr> 
    
<tr>
<td width="100%">    
  <table border="0" width="100%">
    <tr>
	    <td align=right width="50%">
		    <font class="texteSubmit">
		    <input type="submit" value="Valider">
		    </font>
	      </form>
      </td>
      <td align=left width="50%">
	      <form action="../Tache/TacheVisu" method="get">
	      <input type="submit" value="Annuler">
	      <input type="hidden" name="pTacheAVisualiser" value="<%=lArtefact.getTacheSortie().getId()%>"+" ">
	      </form>
      </td>
    </tr>
  </table>
</td>
</tr>
</table>
</body>
