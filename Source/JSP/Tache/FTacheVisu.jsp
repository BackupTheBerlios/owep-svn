<%@page import="java.text.SimpleDateFormat"%>
<%@page import="owep.controle.CConstante"%>
<%@page import="owep.modele.execution.MTache"%>
<%@page import="owep.infrastructure.Session"%>

<jsp:useBean id="lArtefact" class="owep.modele.execution.MArtefact" scope="page"/>
<jsp:useBean id="lTache"    class="owep.modele.execution.MTache"    scope="page"/> 
<jsp:useBean id="lSession"  class="owep.infrastructure.Session"     scope="page"/> 

<%
    SimpleDateFormat lDateFormat = new SimpleDateFormat ("dd/MM/yyyy") ;
    lTache = (MTache) request.getAttribute (CConstante.PAR_TACHE) ;
    lSession = (Session) request.getAttribute (CConstante.SES_SESSION) ;
    
    String [] tabEtat = {"Cr��e", "Pr�te", "Commenc�e", "Suspendue", "Termin�e"};

	String PATH_ARTEFACT = lSession.getConfiguration().getPathArtefact();
%>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tbody>
  <tr>
    <td width="20%">T�che : </td>
    <td align="left"><%= lTache.getNom ()%></td>
  </tr>
  <tr>
    <td width="20%">Etat : </td>
    <td align="left"><%= tabEtat[lTache.getEtat()+1]%></td>
  </tr>
  <tr>
    <td width="20%">Description : </td>
    <td align="left"><%= lTache.getDescription()%></td>
  </tr>
</tbody>
</table> 
 
<br><br>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tbody>
  <tr>
	<td width="15%">Temps pr�vu : </td>
	<td width="20%"><%=lTache.getChargeInitiale ()%></td>
    <td width="15%">Temps pass� : </td>
    <td width="20%"><%=lTache.getTempsPasse ()    %></td>
    <td width="15%">Reste � passer : </td>
    <td width="15%"><%=lTache.getResteAPasser ()  %></td>
  </tr>
</tbody>
</table>

<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tbody>
  <tr>
    <td colspan="5">Dates : </td>
  </tr>
  <tr>
    <td width="15%">d�but pr�vue : </td>
    <td width="20%"><% if (lTache.getDateDebutPrevue () != null)
           {
             out.print (lDateFormat.format (lTache.getDateDebutPrevue ())) ;
           }
           else
           {
             out.print ("X") ;
           } %>
    </td>
    <td width="15%">d�but r�elle : </td>
    <td width="20%"><% if (lTache.getDateDebutReelle () != null)
           {
             out.print (lDateFormat.format (lTache.getDateDebutReelle ())) ;
           }
           else
           {
             out.print ("X") ;
           } %>
    </td>
    <td width="30%"></td>
  </tr>
  <tr>
    <td width="15%">fin pr�vue</td>
    <td width="20%"><% if (lTache.getDateFinPrevue () != null)
           {
             out.print (lDateFormat.format (lTache.getDateFinPrevue ())) ;
           }
           else
           {
             out.print ("X") ;
           } %>
    </td>
    <td width="15%">fin r�estim�e : </td>
    <td width="20%"><% if (lTache.getDateFinReelle () != null)
           {
             out.print (lDateFormat.format (lTache.getDateFinReelle ())) ;
           }
           else
           {
             out.print ("X") ;
           } %>
    </td>
    <td width="30%"></td>
  </tr>
</tbody>
</table>

<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tbody>
  <tr>
    <td colspan="5">D�passement de charge : </td>
  </tr>
  <tr>
    <td width="15%">pourcentage : </td>
    <td width="20%"><%= lTache.getPrcDepassementCharge () * 100 %></td>
    <td width="15%">hommes jour : </td>
    <td width="20%"><%= lTache.getHJDepassementCharge ()        %></td>
    <td width="30%"></td>
  </tr>
</tbody>
</table>

<br><br>

<table width="100%">
  <tr>
<%if (lTache.getNbArtefactsEntrees()>0){%>
  <td width="50%" valign="top">
    <center>Artefacts en entr�e de la tache</center><br>
	<table width="100%" class="tableau" border="0" cellpadding="0" cellspacing="0">
	<tbody>
	  <tr>
	    <td class="caseNiveau1">Artefact</td>
	    <td class="caseNiveau1">Disponibilit�</td>
	    <td class="caseNiveau1">Responsable</td>
	  </tr>
	  <%for (int i = 0; i < lTache.getNbArtefactsEntrees(); i ++)
	    {
	      lArtefact = lTache.getArtefactEntree(i) ;  
	  %>
	    <tr>
	      <td class='caseNiveau2'><%= lArtefact.getNom()%></td>
	      <%if (lArtefact.getNomFichier() != null){%>
	        <td class='caseNiveau2'><a href=<%= "/owep/"+PATH_ARTEFACT+lArtefact.getPathFichier()+lArtefact.getNomFichier()%>><%= lArtefact.getNomFichier()%></a></td>
	      <%}else{%>
            <td class='caseNiveau2'>Nom disponible</td>
          <%}%>
	      <td class='caseNiveau2'><%= lArtefact.getResponsable().getPrenom()+" "+lArtefact.getResponsable().getNom()%></td>
	    </tr>
	  <%}%>
	</tbody>
	</table>
  </td>	
<%}%>

<%if (lTache.getNbArtefactsSorties()>0){%>
  <td width="50%" valign="top">
    <center>Artefacts en sortie de la tache</center><br>
	<table width="100%" class="tableau" border="0" cellpadding="0" cellspacing="0">
	<tbody>
	  <tr>
	    <td class="caseNiveau1">Artefact</td>
	    <td class="caseNiveau1">Disponibilit�</td>
	    <td class="caseNiveau1">Upload</td>
	  </tr>
	  <%for (int i = 0; i < lTache.getNbArtefactsSorties(); i ++)
	    {
	      lArtefact = lTache.getArtefactSortie(i) ;
	  %>
	    <tr>
	      <td class='caseNiveau2'><%= lArtefact.getNom()%></td>
	      <%if (lArtefact.getNomFichier() != null){%>
	        <td class='caseNiveau2'><a href=<%= "/owep/"+PATH_ARTEFACT+lArtefact.getPathFichier()+lArtefact.getNomFichier()%>><%= lArtefact.getNomFichier()%></a></td>
	      <%}else{%>
            <td class='caseNiveau2'>Nom disponible</td>
          <%}%>
	        <td class='caseNiveau2'><a href=<%= "/owep/Artefact/ArtefactAjout?pArtefact="+ Integer.toString(lArtefact.getId())+"&pTacheAVisualiser="+lTache.getId()%>><%= "Ajouter"%></a></td>
	    </tr>
	  <%}%>
	</tbody>
	</table>
  </td>	
<%}%>

  </tr>
</table>