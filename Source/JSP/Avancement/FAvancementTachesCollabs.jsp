<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="owep.modele.execution.MProjet"%>
<%@page import="owep.modele.execution.MCollaborateur"%>
<%@page import="owep.controle.CConstante"%>
<%@taglib uri='/WEB-INF/tld/transfert.tld' prefix='transfert' %>

<jsp:useBean id="lProjet" class="owep.modele.execution.MProjet" scope="page"/>
<jsp:useBean id="lTache" class="owep.modele.execution.MTache" scope="page"/>
<jsp:useBean id="lCollaborateur" class="owep.modele.execution.MCollaborateur" scope="page"/>

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
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementCollabAideCollaborateur")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneCollaborateur")%></a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementDetailCollabAideNbTaches")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneNbTaches")%></a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementDetailCollabAideTempsPrevu")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneTempsPrevu")%></a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementDetailCollabAideTempsPasse")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneTempsPasse")%></a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementDetailCollabAideResteAPasser")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneResteAPasser")%></a>
    </td>
    <td class="caseNiveau1" colspan="4">
      <%=messages.getString("colonneDate")%>
    </td>
    <td class="caseNiveau1" colspan="2">
      <%=messages.getString("colonneDepassementCharges")%>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementDetailCollabAideBudget")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneBudgetConsomme")%></a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementDetailCollabAideAv")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneAvancement")%></a>
    </td>
  </tr>
  <tr>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementDetailCollabAideDDP")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneDebutPrevue")%></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementDetailCollabAideDDR")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneDebutReelle")%></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementDetailCollabAideDFP")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneFinPrevue")%></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementDetailCollabAideDFR")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneFinReestimee")%></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementDetailCollabAideDepPRC")%>')" onmouseout="tooltipOff(this, event)">(%)</a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementDetailCollabAideDepH")%>')" onmouseout="tooltipOff(this, event)">(min)</a>
    </td>
  </tr>
  
  
  <!--Pour chaque collaborateur-->
	<%
	for (int i = 0; i < lProjet.getNbCollaborateurs(); i ++)
	{
	  lCollaborateur = (MCollaborateur)lProjet.getListe(new Integer(i));
	  %>
	  <tr>      
      <td class="caseNiveau2">
        <a href="/owep/Avancement/DetailAvancementCollab?<%=CConstante.PAR_COLLABORATEUR%>=<%= lCollaborateur.getId()%>"><%=lCollaborateur.getNom()%></a>
      </td>
      <%if (((Integer)lCollaborateur.getListe(new Integer(0))).intValue() == 0)
        {
      %>
      <td class="caseNiveau3" colspan="13"><center><%=messages.getString("avancementDetailCollabAucuneTache")%></center></td>
      <%  
        }
        else
        {
	        %>
				    <td class="caseNiveau3"><%=((Integer)lCollaborateur.getListe(new Integer(0))).intValue()%></td>
				    <td class="caseNiveau3"><%=((Integer)lCollaborateur.getListe(new Integer(1))).intValue()%></td>
            <td class="caseNiveau3"><%=((Integer)lCollaborateur.getListe(new Integer(2))).intValue()%></td>
            <td class="caseNiveau3"><%=((Integer)lCollaborateur.getListe(new Integer(3))).intValue()%></td>
            <td class="caseNiveau3"><% if (((Date)lCollaborateur.getListe(new Integer(4))) != null)
                               {
                                 out.print (lDateFormat.format (((Date)lCollaborateur.getListe(new Integer(4))))) ;
                               }
                               else
                               {
                                 out.print ("X") ;
                               } %></td>
            <td class="caseNiveau3"><% if (((Date)lCollaborateur.getListe(new Integer(5))) != null)
                               {
                                 out.print (lDateFormat.format (((Date)lCollaborateur.getListe(new Integer(5))))) ;
                               }
                               else
                               {
                                 out.print ("X") ;
                               } %></td>
            <td class="caseNiveau3"><% if (((Date)lCollaborateur.getListe(new Integer(6))) != null)
                               {
                                 out.print (lDateFormat.format (((Date)lCollaborateur.getListe(new Integer(6))))) ;
                               }
                               else
                               {
                                 out.print ("X") ;
                               } %></td>
            <td class="caseNiveau3"><% if (((Date)lCollaborateur.getListe(new Integer(7))) != null)
                               {
                                 out.print (lDateFormat.format (((Date)lCollaborateur.getListe(new Integer(7))))) ;
                               }
                               else
                               {
                                 out.print ("X") ;
                               } %></td>
            <td class="caseNiveau3"><%=(int)(((Double)lCollaborateur.getListe(new Integer(8))).doubleValue() * 100)%></td>
            <td class="caseNiveau3"><%=(int)((Double)lCollaborateur.getListe(new Integer(9))).doubleValue()%></td>
            <td class="caseNiveau3"><%=(int)(((Double)lCollaborateur.getListe(new Integer(10))).doubleValue() * 100)%></td>
            <td class="caseNiveau3"><%=(int)(((Double)lCollaborateur.getListe(new Integer(11))).doubleValue() * 100)%></td>
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
pCodeAide  = "<%=messages.getString("avancementDetailCollabAide")%>" ;
</script>
