<%@page import="owep.modele.execution.MIteration"%>
<%@page import="owep.modele.execution.MProjet"%>
<%@page import="owep.controle.CConstante"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@taglib uri='/WEB-INF/tld/transfert.tld' prefix='transfert' %>

<jsp:useBean id="lProjet" class="owep.modele.execution.MProjet" scope="page"/>

<% 
    //localisation
    java.util.ResourceBundle messages;
    messages = java.util.ResourceBundle.getBundle("MessagesBundle");

    lProjet  = (MProjet) request.getAttribute (CConstante.PAR_PROJET) ;
%>
  
<% SimpleDateFormat lDateFormat = new SimpleDateFormat ("dd/MM/yyyy") ;
   
   // si le projet ne comporte aucune iteration
   if (lProjet.getTailleListe()==0)
   {
%>
   <%=messages.getString("avancementGlobalProjetAucuneIteration")%><BR><BR>
   
   <%=messages.getString("avancementGlobalDDP")%>
   <% if (lProjet.getDateDebutPrevue () != null)
                         {
                           out.print (lDateFormat.format (lProjet.getDateDebutPrevue ())) ;
                         }
                         else
                         {
                           out.print (messages.getString("avancementGlobalProjetInconnue")) ;
                         } %>
   <BR>
   <%=messages.getString("avancementGlobalDFP")%>
   <% if (lProjet.getDateFinPrevue () != null)
                         {
                           out.print (lDateFormat.format (lProjet.getDateFinPrevue ())) ;
                         }
                         else
                         {
                           out.print (messages.getString("avancementGlobalProjetInconnue")) ;
                         } %>
<%
   }
   // si le projet comporte au moins une itération
   else
   {
%>
  
<table class="tableau" border="0" cellpadding="0" cellspacing="0">
<tbody>
  <tr>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementGlobalAideIteration")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneIteration")%></a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementGlobalAideTempsPrevu")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneTempsPrevu")%></a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementGlobalAideTempsPasse")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneTempsPasse")%></a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementGlobalAideResteAPasser")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneResteAPasser")%></a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event,'<%=messages.getString("avancementGlobalAideEtat")%>')" onmouseout="tooltipOff(this, event)">
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
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementGlobalAideBudget")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneBudgetConsomme")%></a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementGlobalAidePRCAv")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneAvancement")%></a>
    </td>
  </tr>
  <tr>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementGlobalAideDDP")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneDebutPrevue")%></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementGlobalAideDDR")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneDebutReelle")%></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementGlobalAideDFP")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneFinPrevue")%></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementGlobalAideDFR")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneFinReestimee")%></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementGlobalAideDepPC")%>')" onmouseout="tooltipOff(this, event)">(%)</a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementGlobalAideDepH")%>')" onmouseout="tooltipOff(this, event)">(min)</a>
    </td>
  </tr>
  <!--Affichage des taches de l iteration-->
  <%
  int i = 0 ;
  for (int nbIteration = 1; nbIteration <= ((Integer)lProjet.getListe(new Integer(0))).intValue() ; nbIteration ++)
  {
    MIteration lIteration = (MIteration)lProjet.getListe(new Integer(nbIteration)) ;
    %>
    <tr>      
      <td class="caseNiveau2"><a href="/owep/Avancement/AvancementProjet?<%=CConstante.PAR_ITERATION%>=<%=lIteration.getId()%>"><%=lIteration.getNom()%></a></td>
      <td class="caseNiveau3"><%=(int)lIteration.getChargeInitiale()%></td>
      <td class="caseNiveau3"><%=(int)lIteration.getTempsPasse()%></td>
      <td class="caseNiveau3"><%=(int)lIteration.getResteAPasser()%></td>
      <td class="caseNiveau3">
      <%
        switch (lIteration.getEtat())
        {
          case 0 : 
        %>
          <%=messages.getString("avancementGlobalIterationNonDemarre")%>
        <%  
          break ;
          case 1 : 
        %>
          <%=messages.getString("avancementGlobalIterationEnCours")%>
        <%  
          break ;
          case 2 : 
        %>
          <%=messages.getString("avancementGlobalIterationTerminee")%>
        <%  
          break ;
        }
        %>
      </td>
      <td class="caseNiveau3"><% if (lIteration.getDateDebutPrevue () != null)
                         {
                           out.print (lDateFormat.format (lIteration.getDateDebutPrevue ())) ;
                         }
                         else
                         {
                           out.print ("X") ;
                         } %></td>
      <td class="caseNiveau3"><% if (lIteration.getDateDebutReelle () != null)
                         {
                           out.print (lDateFormat.format (lIteration.getDateDebutReelle ())) ;
                         }
                         else
                         {
                           out.print ("X") ;
                         } %></td>
      <td class="caseNiveau3"><% if (lIteration.getDateFinPrevue () != null)
                         {
                           out.print (lDateFormat.format (lIteration.getDateFinPrevue ())) ;
                         }
                         else
                         {
                           out.print ("X") ;
                         } %></td>
      <td class="caseNiveau3"><% if (lIteration.getDateFinReelle () != null)
                         {
                           out.print (lDateFormat.format (lIteration.getDateFinReelle ())) ;
                         }
                         else
                         {
                           out.print ("X") ;
                         } %></td>
      <td class="caseNiveau3"><%=(int)(lIteration.getPrcDepassementCharge () * 100)%></td>
      <td class="caseNiveau3"><%=(int)lIteration.getHJDepassementCharge ()%></td>
      <td class="caseNiveau3"><%=(int)(lIteration.getBudgetConsomme() * 100)%></td>
      <td class="caseNiveau3"><%=(int)(lIteration.getPrcAvancement() * 100)%></td>
    </tr>
    <% i = nbIteration ;
  }
  %> 
  <tr>      
      <td class="caseNiveau1">
        <a class="niveau1" href="#" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementGlobalAideTotal")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("avancementGlobalTotal")%></a>
      </td>
      <td class="caseNiveau2"><%=((Double)lProjet.getListe(new Integer(i+1))).intValue()%></td>
      <td class="caseNiveau2"><%=((Double)lProjet.getListe(new Integer(i+2))).intValue()%></td>
      <td class="caseNiveau2"><%=((Double)lProjet.getListe(new Integer(i+3))).intValue()%></td>
      <td class="caseNiveau2">X</td>
      <td class="caseNiveau2"><% if (((Date)lProjet.getListe(new Integer(i+4))) != null)
                         {
                           out.print (lDateFormat.format ((Date)lProjet.getListe(new Integer(i+4)))) ;
                         }
                         else
                         {
                           out.print ("X") ;
                         } %></td>
      <td class="caseNiveau2"><% if (((Date)lProjet.getListe(new Integer(i+5))) != null)
                         {
                           out.print (lDateFormat.format ((Date)lProjet.getListe(new Integer(i+5)))) ;
                         }
                         else
                         {
                           out.print ("X") ;
                         } %></td>
      <td class="caseNiveau2"><% if (((Date)lProjet.getListe(new Integer(i+6))) != null)
                         {
                           out.print (lDateFormat.format ((Date)lProjet.getListe(new Integer(i+6)))) ;
                         }
                         else
                         {
                           out.print ("X") ;
                         } %></td>
      <td class="caseNiveau2"><%out.print ("X") ;%></td>
      <td class="caseNiveau2"><%=(int)(((Double)lProjet.getListe(new Integer(i+7))).doubleValue() * 100)%></td>
      <td class="caseNiveau2"><%=((Double)lProjet.getListe(new Integer(i+8))).intValue()%></td>
      <td class="caseNiveau2"><%=(int)(((Double)lProjet.getListe(new Integer(i+9))).doubleValue() * 100)%></td>
      <td class="caseNiveau2"><%=(int)(((Double)lProjet.getListe(new Integer(i+10))).doubleValue() * 100)%></td>
    </tr>
</tbody>
</table>
<%
}
%>


<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "<%=messages.getString("avancementGlobalAide")%>" ;
</script>
