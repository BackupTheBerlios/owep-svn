<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="owep.modele.execution.MCollaborateur"%>
<%@page import="owep.modele.execution.MTache"%>
<%@page import="owep.modele.execution.MTacheImprevue"%>
<%@page import="owep.controle.CConstante"%>
<%@taglib uri='/WEB-INF/tld/transfert.tld' prefix='transfert' %>

<jsp:useBean id="lCollaborateur" class="owep.modele.execution.MCollaborateur" scope="page"/>

<% 
   //localisation
   java.util.ResourceBundle messages;
   messages = java.util.ResourceBundle.getBundle("MessagesBundle");

   lCollaborateur  = (MCollaborateur) request.getAttribute (CConstante.PAR_COLLABORATEUR) ;
   SimpleDateFormat lDateFormat = new SimpleDateFormat ("dd/MM/yyyy") ;
   ArrayList lListeTaches = (ArrayList)lCollaborateur.getListe(new Integer(0)) ;
   ArrayList lListeTachesImprevues = (ArrayList)lCollaborateur.getListe(new Integer(1)) ;
   if ((lListeTaches.size()+lListeTachesImprevues.size())==0)
   {
%>
   <%=messages.getString("detailAvancementCollabCollaborateur")%><%=lCollaborateur.getNom()%> <%=messages.getString("detailAvancementCollabNoTaches")%>
<%
   }
   else
   {
%>
  
<table class="tableau" border="0" cellpadding="0" cellspacing="0">
<tbody>
  <tr>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("detailAvancementCollabAideTache")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneTache")%></a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideTempsPrevu")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneTempsPrevu")%></a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideTempsPasse")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneTempsPasse")%></a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideResteAPasser")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneResteAPasser")%></a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event,'<%=messages.getString("avancementProjetAideEtat")%>')" onmouseout="tooltipOff(this, event)">
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
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideBudget")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneBudgetConsomme")%></a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideAv")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneAvancement")%></a>
    </td>
  </tr>
  <tr>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideDDP")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneDebutPrevue")%></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideDDR")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneDebutReelle")%></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideDFP")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneFinPrevue")%></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideDFR")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneFinReestimee")%></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideDepPRC")%>')" onmouseout="tooltipOff(this, event)">(%)</a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideDepH")%>')" onmouseout="tooltipOff(this, event)">(min)</a>
    </td>
  </tr>
  <!--Affichage des taches du collaborateur-->
	<%
	for (int i = 0; i < lListeTaches.size(); i ++)
	{
	  MTache lTache = (MTache)lListeTaches.get(i) ;
	  %>
	  <tr>      
      <td class="caseNiveau2"><%=lTache.getNom()%></td>
      <td class="caseNiveau3"><%=(int)lTache.getChargeInitiale()%></td>
      <td class="caseNiveau3"><%=(int)lTache.getTempsPasse()%></td>
      <td class="caseNiveau3"><%=(int)lTache.getResteAPasser()%></td>
      <td class="caseNiveau3">
      <%
        switch (lTache.getEtat())
        {
          case -1 : 
        %>
          <%=messages.getString("avancementProjetNonPrete")%>
        <%  
          break ;
          case 0 : 
        %>
          <%=messages.getString("avancementProjetNonDemarree")%>
        <%  
          break ;
          case 1 : 
        %>
          <%=messages.getString("avancementProjetEnCours")%>
        <%  
          break ;
          case 2 : 
        %>
          <%=messages.getString("avancementProjetSuspendue")%>
        <%  
          break ;
          case 3 : 
        %>
          <%=messages.getString("avancementProjetTerminee")%>
        <%
          break ;
        }
        %>
      </td>
      <td class="caseNiveau3"><% if (lTache.getDateDebutPrevue () != null)
                         {
                           out.print (lDateFormat.format (lTache.getDateDebutPrevue ())) ;
                         }
                         else
                         {
                           out.print ("X") ;
                         } %></td>
      <td class="caseNiveau3"><% if (lTache.getDateDebutReelle () != null)
                         {
                           out.print (lDateFormat.format (lTache.getDateDebutReelle ())) ;
                         }
                         else
                         {
                           out.print ("X") ;
                         } %></td>
      <td class="caseNiveau3"><% if (lTache.getDateFinPrevue () != null)
                         {
                           out.print (lDateFormat.format (lTache.getDateFinPrevue ())) ;
                         }
                         else
                         {
                           out.print ("X") ;
                         } %></td>
      <td class="caseNiveau3"><% if (lTache.getDateFinReelle () != null)
                         {
                           out.print (lDateFormat.format (lTache.getDateFinReelle ())) ;
                         }
                         else
                         {
                           out.print ("X") ;
                         } %></td>
      <td class="caseNiveau3"><%=(int)(lTache.getPrcDepassementCharge () * 100)%></td>
      <td class="caseNiveau3"><%=(int)lTache.getHJDepassementCharge ()%></td>
      <td class="caseNiveau3"><%=(int)(lTache.getBudgetConsomme() * 100)%></td>
      <td class="caseNiveau3"><%=(int)(lTache.getPrcAvancement() * 100)%></td>
	  </tr>
	  <%
	}
	%> 
	
	<!--Affichage des taches imprévues du collaborateur-->
	<%
	for (int i = 0; i < lListeTachesImprevues.size(); i ++)
	{
	  MTacheImprevue lTacheImprevue = (MTacheImprevue)lListeTachesImprevues.get(i) ;
	  %>
	  <tr>      
      <td class="caseNiveau2Italic"><%=lTacheImprevue.getNom()%></td>
      <td class="caseNiveau3"><%=(int)lTacheImprevue.getChargeInitiale()%></td>
      <td class="caseNiveau3"><%=(int)lTacheImprevue.getTempsPasse()%></td>
      <td class="caseNiveau3"><%=(int)lTacheImprevue.getResteAPasser()%></td>
      <td class="caseNiveau3">
      <%
        switch (lTacheImprevue.getEtat())
        {
          case -1 : 
        %>
          <%=messages.getString("avancementProjetNonPrete")%>
        <%  
          break ;
          case 0 : 
        %>
          <%=messages.getString("avancementProjetNonDemarree")%>
        <%  
          break ;
          case 1 : 
        %>
          <%=messages.getString("avancementProjetEnCours")%>
        <%  
          break ;
          case 2 : 
        %>
          <%=messages.getString("avancementProjetSuspendue")%>
        <%  
          break ;
          case 3 : 
        %>
          <%=messages.getString("avancementProjetTerminee")%>
        <%
          break ;
        }
        %>
      </td>
      <td class="caseNiveau3"><% if (lTacheImprevue.getDateDebutPrevue () != null)
                         {
                           out.print (lDateFormat.format (lTacheImprevue.getDateDebutPrevue ())) ;
                         }
                         else
                         {
                           out.print ("X") ;
                         } %></td>
      <td class="caseNiveau3"><% if (lTacheImprevue.getDateDebutReelle () != null)
                         {
                           out.print (lDateFormat.format (lTacheImprevue.getDateDebutReelle ())) ;
                         }
                         else
                         {
                           out.print ("X") ;
                         } %></td>
      <td class="caseNiveau3"><% if (lTacheImprevue.getDateFinPrevue () != null)
                         {
                           out.print (lDateFormat.format (lTacheImprevue.getDateFinPrevue ())) ;
                         }
                         else
                         {
                           out.print ("X") ;
                         } %></td>
      <td class="caseNiveau3"><% if (lTacheImprevue.getDateFinReelle () != null)
                         {
                           out.print (lDateFormat.format (lTacheImprevue.getDateFinReelle ())) ;
                         }
                         else
                         {
                           out.print ("X") ;
                         } %></td>
      <td class="caseNiveau3"><%=(int)(lTacheImprevue.getPrcDepassementCharge () * 100)%></td>
      <td class="caseNiveau3"><%=(int)lTacheImprevue.getHJDepassementCharge ()%></td>
      <td class="caseNiveau3"><%=(int)(lTacheImprevue.getBudgetConsomme() * 100)%></td>
      <td class="caseNiveau3"><%=(int)(lTacheImprevue.getPrcAvancement() * 100)%></td>
	  </tr>
	  <%
	}
	%> 
</tbody>
</table>
<%
}
%>


<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "<%=messages.getString("detailAvancementCollabAide")%>" ;
</script>
