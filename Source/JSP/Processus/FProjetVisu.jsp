<%@page import="owep.modele.execution.MProjet"%>
<%@page import="owep.modele.execution.MIteration"%>
<%@page import="owep.modele.execution.MTache"%>
<%@page import="owep.modele.execution.MTacheImprevue"%>
<%@page import="owep.vue.transfert.convertor.VDateConvertor"%>
<%@page import="owep.controle.CConstante"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="owep.infrastructure.Session"%>
<%@taglib uri='/WEB-INF/tld/transfert.tld' prefix='transfert' %>

<form name="projetVisu" action="../Processus/IterationModif">
  <input type="hidden" name="<%= CConstante.PAR_ITERATION %>" value="">
<%
  // Recuperation de la session
  HttpSession httpSession = request.getSession(true);
  //Récupération du ressource bundle
  ResourceBundle messages = ((Session) httpSession.getAttribute("SESSION")).getMessages();

  MProjet lProjet = (MProjet) request.getAttribute (CConstante.PAR_PROJET) ;
  double lBudgetConsomme = 0.0 ;
  double lBudgetConsommeImprevue = 0.0 ;
  
  // Calcul du budget consommé dans le projet.
  for (int lIndiceIteration = 0; lIndiceIteration < lProjet.getNbIterations (); lIndiceIteration++)
  {
    MIteration lIteration = lProjet.getIteration (lIndiceIteration) ;
    for (int lIndiceTache = 0; lIndiceTache < lIteration.getNbTaches (); lIndiceTache ++)
    { 
      MTache lTache = lIteration.getTache (lIndiceTache);
      if (lTache.getEtat () == 3)
      {
      	lBudgetConsomme += lTache.getTempsPasse () ;
      }
      else
      {
        lBudgetConsomme += lTache.getChargeInitiale () ;
      }
    }
    for (int lIndiceTacheImprevue = 0; lIndiceTacheImprevue < lIteration.getNbTachesImprevues (); lIndiceTacheImprevue ++)
    {
      MTacheImprevue lTacheImprevue = lIteration.getTacheImprevue (lIndiceTacheImprevue) ;
      if (lTacheImprevue.getEtat () == 3)
      {
      	lBudgetConsommeImprevue += lTacheImprevue.getTempsPasse () ;
      }
      else
      {
        lBudgetConsommeImprevue += lTacheImprevue.getChargeInitiale () ;
      }
    }
  }
%>

<table class="tableau" width="100%" cellpadding="0" cellspacing="0">
<tbody> 
  <tr> 
    <td class="caseNiveau1" colspan="4"><%= messages.getString("projetInfo") %> :</td>
  </tr>
  <tr>
    <td class="caseNiveau3" colspan="4">
      <%= messages.getString("projetNomProjet") %> : <%= lProjet.getNom () %>
    </td>
  </tr>
  <tr>
    <td class="caseNiveau3" colspan="2">
      <%= messages.getString("projetDateDebut") %> : <%= VDateConvertor.getString(lProjet.getDateDebutPrevue ()) %>
    </td>
    <td class="caseNiveau3" colspan="2">
      <%= messages.getString("projetDateFin") %> : <%= VDateConvertor.getString(lProjet.getDateFinPrevue ()) %>
    </td>
  </tr>
  <tr> 
    <td class="caseNiveau1" colspan="4"><%= messages.getString("budgetInfo") %> :</td>
  </tr>
    <td class="caseNiveau3" width="25%">
      <center><%= messages.getString("budgetTotal") %></center>
    </td>
    <td class="caseNiveau3" width="25%">
      <center><%= messages.getString("budgetConsomme") %><br>
      <%= messages.getString("budgetSsTI") %></center>
    </td>
    <td class="caseNiveau3" width="25%">
      <center><%= messages.getString("budgetConsomme") %><br>
      <%= messages.getString("budgetTI") %></center>
    </td>
    <td class="caseNiveau3" width="25%">
      <center><%= messages.getString("budgetRestant") %></center>
    </td>
  <tr>
  </tr>
  <tr>
    <td class="caseNiveau3">
      <center><%= lProjet.getBudget() %></center>
    </td>
    <td class="caseNiveau3">
      <center><%= lBudgetConsomme %></center>
    </td>
    <td class="caseNiveau3">
      <center><%= lBudgetConsommeImprevue %></center>
    </td>
    <td class="caseNiveau3">
      <center><%= lProjet.getBudget() - lBudgetConsomme - lBudgetConsommeImprevue %></center>
    </td>
  </tr>
</table>
<br><br><br>
  <%
  for (int i = 0; i < lProjet.getNbIterations (); i ++)
  {
    MIteration lIteration = lProjet.getIteration (i) ;
  %>
    <%= messages.getString("iteration") %> <%= lIteration.getNumero () %>&nbsp;
  <%
    // si l'itération n'a pas encore démarée, alors on peut la modifier.
    if (lIteration.getEtat () == 0)
    {
  %>
    &nbsp;<input class="bouton" type="submit" name="<%= CConstante.PAR_MODIFIER %>" value="Modifier"
     onclick="document.projetVisu.<%= CConstante.PAR_ITERATION %>.value = <%= lIteration.getId () %>"><br/>
  <%
    }
    // si l'itération est en cours alors on l'indique au chef de projet.
    else if (lIteration.getEtat () == 1)
    {
  %>
    <%= messages.getString("iterationEnCours") %>.<br/>
  <%
    }
    // sinon c'est que l'itération est terminée.
    else
    {
  %>
    <%= messages.getString("iterationTermine") %>.<br/>
  <%
    }
  }
%>
</form>
<a href="../Processus/IterationModif" onmouseover="tooltipOn(this, event, 'Permet de créer une nouvelle itération.')" onmouseout="tooltipOff(this, event)"><%= messages.getString("iterationAjouter") %>...</a>

