<%@page import="owep.modele.execution.MIteration"%>
<%@page import="owep.modele.execution.MTache"%>
<%@page import="owep.modele.execution.MTacheImprevue"%>
<%@page import="owep.controle.CConstante"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@taglib uri='/WEB-INF/tld/transfert.tld' prefix='transfert' %>

<jsp:useBean id="lIteration" class="owep.modele.execution.MIteration" scope="page"/>

<% 
    //localisation
    java.util.ResourceBundle messages;
    messages = java.util.ResourceBundle.getBundle("MessagesBundle");
    
    lIteration  = (MIteration) request.getAttribute (CConstante.PAR_ITERATION) ;
%>
  
<% SimpleDateFormat lDateFormat = new SimpleDateFormat ("dd/MM/yyyy") ;
   // si l iteration ne comporte aucune tache
   if ((lIteration.getNbTaches()+lIteration.getNbTachesImprevues()) == 0)
   {
%>
   <%=messages.getString("avancementProjetAucuneTache")%><BR><BR>
   
   <%=messages.getString("avancementGlobalDDP")%>
   <% if (lIteration.getDateDebutPrevue () != null)
                         {
                           out.print (lDateFormat.format (lIteration.getDateDebutPrevue ())) ;
                         }
                         else
                         {
                           out.print (messages.getString("avancementGlobalProjetInconnue")) ;
                         } %>
   <BR>
   <%=messages.getString("avancementGlobalDFP")%>
   <% if (lIteration.getDateFinPrevue () != null)
                         {
                           out.print (lDateFormat.format (lIteration.getDateFinPrevue ())) ;
                         }
                         else
                         {
                           out.print (messages.getString("avancementGlobalProjetInconnue")) ;
                         } %>
<%
   }
   // si l iteration comporte au moins un tache
   else
   {
%>
  
<table class="tableau" border="0" cellpadding="0" cellspacing="0">
<tbody>
  <tr>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideTache")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneTache")%></a>
    </td>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideCollaborateur")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneCollaborateur")%></a>
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
  <!--Affichage des taches de l iteration-->
  <%
  for (int i = 0; i < lIteration.getNbTaches() ; i ++)
  {
    MTache lTache = (MTache)lIteration.getTache(i) ;
    %>
    <tr>      
      <td class="caseNiveau2"><%=lTache.getNom()%></td>
      <td class="caseNiveau3"><%=lTache.getCollaborateur().getNom()%></td>
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
  
  <!--Affichage des taches imprevues de l iteration-->
  <%
  for (int i = 0; i < lIteration.getNbTachesImprevues() ; i ++)
  {
    MTacheImprevue lTacheImprevue = (MTacheImprevue)lIteration.getTacheImprevue(i) ;
    %>
    <tr>      
      <td class="caseNiveau2Italic"><%=lTacheImprevue.getNom()%></td>
      <td class="caseNiveau3"><%=lTacheImprevue.getCollaborateur().getNom()%></td>
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
  
  <tr>      
      <td class="caseNiveau1" colspan="2">
        <a class="niveau1" href="#" onmouseover="tooltipOn(this, event, '<%=messages.getString("avancementProjetAideTotal")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("avancementProjetTotal")%></a>
      </td>
      <td class="caseNiveau2"><%=(int)lIteration.getChargeInitiale()%></td>
      <td class="caseNiveau2"><%=(int)lIteration.getTempsPasse()%></td>
      <td class="caseNiveau2"><%=(int)lIteration.getResteAPasser()%></td>
      <td class="caseNiveau2">
      <%
        switch (lIteration.getEtat())
        {
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
          <%=messages.getString("avancementProjetTerminee")%>
        <%  
          break ;
        }
        %>
      </td>
      <td class="caseNiveau2"><% if (lIteration.getDateDebutPrevue () != null)
                         {
                           out.print (lDateFormat.format (lIteration.getDateDebutPrevue ())) ;
                         }
                         else
                         {
                           out.print ("X") ;
                         } %></td>
      <td class="caseNiveau2"><% if (lIteration.getDateDebutReelle () != null)
                         {
                           out.print (lDateFormat.format (lIteration.getDateDebutReelle ())) ;
                         }
                         else
                         {
                           out.print ("X") ;
                         } %></td>
      <td class="caseNiveau2"><% if (lIteration.getDateFinPrevue () != null)
                         {
                           out.print (lDateFormat.format (lIteration.getDateFinPrevue ())) ;
                         }
                         else
                         {
                           out.print ("X") ;
                         } %></td>
      <td class="caseNiveau2"><% if (lIteration.getDateFinReelle () != null)
                         {
                           out.print (lDateFormat.format (lIteration.getDateFinReelle ())) ;
                         }
                         else
                         {
                           out.print ("X") ;
                         } %></td>
      <td class="caseNiveau2"><%=(int)(lIteration.getPrcDepassementCharge() * 100)%></td>
      <td class="caseNiveau2"><%=(int)lIteration.getHJDepassementCharge()%></td>
      <td class="caseNiveau2"><%=(int)(lIteration.getBudgetConsomme() * 100)%></td>
      <td class="caseNiveau2"><%=(int)(lIteration.getPrcAvancement() * 100)%></td>
    </tr>
</tbody>
</table>
<%
}
%>


<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "<%=messages.getString("avancementProjetAide")%>" ;
</script>
