<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="owep.controle.CConstante"%>
<%@page import="owep.modele.execution.MProjet"%>
<%@page import="owep.modele.execution.MTache"%>
<%@page import="owep.modele.execution.MTacheImprevue"%>
<%@page import="owep.controle.CConstante"%>
<%@taglib uri='/WEB-INF/tld/transfert.tld' prefix='transfert' %>

<jsp:useBean id="lProjet" class="owep.modele.execution.MProjet" scope="page"/>
<jsp:useBean id="lTacheEnCours" class="owep.modele.execution.MTache" scope="page"/>
<jsp:useBean id="lTacheImprevueEnCours" class="owep.modele.execution.MTacheImprevue" scope="page"/>

<% 
   //localisation
   java.util.ResourceBundle messages;
   messages = java.util.ResourceBundle.getBundle("MessagesBundle");

   lProjet  = (MProjet) request.getAttribute (CConstante.PAR_PROJET) ;
   SimpleDateFormat lDateFormat = new SimpleDateFormat ("dd/MM/yyyy") ;
%>
  
<table class="tableau" border="0" cellpadding="0" cellspacing="0">
<tbody>
  <tr>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementCollabAideCollaborateur")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("colonneCollaborateur")%>
      </a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementCollabAideTache")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("colonneTache")%>
      </a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideTempsPrevu")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("colonneTempsPrevu")%>
      </a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideTempsPasse")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("colonneTempsPasse")%>
      </a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideResteAPasser")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("colonneResteAPasser")%>
      </a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event,'<%=messages.getString("avancementCollabAideEtat")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("colonneEtat")%>
      </a>
    </td>
    <td class="caseNiveau1" colspan="4">
      <%=messages.getString("colonneDate")%>
    </td>
    <td class="caseNiveau1" colspan="2">
      <%=messages.getString("colonneDepassementCharges")%>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideBudget")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("colonneBudgetConsomme")%>
      </a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideAv")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("colonneAvancement")%>
      </a>
    </td>
  </tr>
  <tr>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideDDP")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("colonneDebutPrevue")%>
      </a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideDDR")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("colonneDebutReelle")%>
      </a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideDFP")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("colonneFinPrevue")%>
      </a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideDFR")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("colonneFinReestimee")%>
      </a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideDepPRC")%>')" onmouseout="tooltipOff(this, event)">
        (%)
      </a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideDepH")%>')" onmouseout="tooltipOff(this, event)">
        (min)
      </a>
    </td>
  </tr>
  
  
  <!--Affichage des taches en cours des collaborateurs-->
  <%
  // pour chaque collaborateur
  for (int i = 0; i < lProjet.getNbCollaborateurs(); i ++)
  {
    %>
    <tr>      
      <td class="caseNiveau2"><a href="/owep/Avancement/DetailAvancementCollab?<%=CConstante.PAR_COLLABORATEUR%>=<%= lProjet.getCollaborateur(i).getId()%>"><%=lProjet.getCollaborateur(i).getNom()%></a></td>
      <%
        ArrayList listTache = (ArrayList)lProjet.getListe(new Integer(i)) ;

        if (listTache.size()==0)
        {
      %>
          <td class="caseNiveau3" colspan="13"><center><%=messages.getString("avancementCollabAucuneTache")%></center></td>
      <%  
        }
	    else if (((String)listTache.get(0)).equals("tache"))
	    {
	      lTacheEnCours = (MTache)listTache.get(1) ;
	  %>
	      <td class="caseNiveau3"><%=lTacheEnCours.getNom()%></td>
          <td class="caseNiveau3"><%=(int)lTacheEnCours.getChargeInitiale()%></td>
          <td class="caseNiveau3"><%=(int)lTacheEnCours.getTempsPasse()%></td>
          <td class="caseNiveau3"><%=(int)lTacheEnCours.getResteAPasser()%></td>
          <td class="caseNiveau3">En cours</td>
          <td class="caseNiveau3"><% if (lTacheEnCours.getDateDebutPrevue () != null)
                                 {
                                   out.print (lDateFormat.format (lTacheEnCours.getDateDebutPrevue ())) ;
                                 }
                                 else
                                 {
                                   out.print ("X") ;
                                 } %></td>
          <td class="caseNiveau3"><% if (lTacheEnCours.getDateDebutReelle () != null)
                                 {
                                   out.print (lDateFormat.format (lTacheEnCours.getDateDebutReelle ())) ;
                                 }
                                 else
                                 {
                                   out.print ("X") ;
                                 } %></td>
          <td class="caseNiveau3"><% if (lTacheEnCours.getDateFinPrevue () != null)
                                 {
                                   out.print (lDateFormat.format (lTacheEnCours.getDateFinPrevue ())) ;
                                 }
                                 else
                                 {
                                   out.print ("X") ;
                                 } %></td>
          <td class="caseNiveau3"><% if (lTacheEnCours.getDateFinReelle () != null)
                                 {
                                   out.print (lDateFormat.format (lTacheEnCours.getDateFinReelle ())) ;
                                 }
                                 else
                                 {
                                   out.print ("X") ;
                                 } %></td>
          <td class="caseNiveau3"><%=(int)(lTacheEnCours.getPrcDepassementCharge () * 100)%></td>
          <td class="caseNiveau3"><%=(int)lTacheEnCours.getHJDepassementCharge ()%></td>
          <td class="caseNiveau3"><%=(int)(lTacheEnCours.getBudgetConsomme() * 100)%></td>
          <td class="caseNiveau3"><%=(int)(lTacheEnCours.getPrcAvancement() * 100)%></td>  
	    <%
	    }
	    else
	    {
	      lTacheImprevueEnCours = (MTacheImprevue)listTache.get(1) ;
	    %>
	      <td class="caseNiveau3Italic"><%=lTacheImprevueEnCours.getNom()%></td>
          <td class="caseNiveau3"><%=(int)lTacheImprevueEnCours.getChargeInitiale()%></td>
          <td class="caseNiveau3"><%=(int)lTacheImprevueEnCours.getTempsPasse()%></td>
          <td class="caseNiveau3"><%=(int)lTacheImprevueEnCours.getResteAPasser()%></td>
          <td class="caseNiveau3">En cours</td>
          <td class="caseNiveau3"><% if (lTacheImprevueEnCours.getDateDebutPrevue () != null)
                                 {
                                   out.print (lDateFormat.format (lTacheImprevueEnCours.getDateDebutPrevue ())) ;
                                 }
                                 else
                                 {
                                   out.print ("X") ;
                                 } %></td>
          <td class="caseNiveau3"><% if (lTacheImprevueEnCours.getDateDebutReelle () != null)
                                 {
                                   out.print (lDateFormat.format (lTacheImprevueEnCours.getDateDebutReelle ())) ;
                                 }
                                 else
                                 {
                                   out.print ("X") ;
                                 } %></td>
          <td class="caseNiveau3"><% if (lTacheImprevueEnCours.getDateFinPrevue () != null)
                                 {
                                   out.print (lDateFormat.format (lTacheImprevueEnCours.getDateFinPrevue ())) ;
                                 }
                                 else
                                 {
                                   out.print ("X") ;
                                 } %></td>
          <td class="caseNiveau3"><% if (lTacheImprevueEnCours.getDateFinReelle () != null)
                                 {
                                   out.print (lDateFormat.format (lTacheImprevueEnCours.getDateFinReelle ())) ;
                                 }
                                 else
                                 {
                                   out.print ("X") ;
                                 } %></td>
          <td class="caseNiveau3"><%=(int)(lTacheImprevueEnCours.getPrcDepassementCharge () * 100)%></td>
          <td class="caseNiveau3"><%=(int)lTacheImprevueEnCours.getHJDepassementCharge ()%></td>
          <td class="caseNiveau3"><%=(int)(lTacheImprevueEnCours.getBudgetConsomme() * 100)%></td>
          <td class="caseNiveau3"><%=(int)(lTacheImprevueEnCours.getPrcAvancement() * 100)%></td>   
	    <%
	    }
      %>
    </tr>
    <%
  }
    %> 
</tbody>
</table>


<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "<%=messages.getString("avancementCollabAide")%>" ;
</script>
