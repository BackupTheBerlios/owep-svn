<%@page import="java.util.ResourceBundle"%>
<%@page import="owep.infrastructure.Session"%>
<%@page import="java.util.ArrayList" %>
<%@page import="owep.controle.CConstante" %>
<%@page import="owep.modele.execution.MProbleme" %>
<%@page import="owep.modele.execution.MTache" %>
<%@page import="owep.modele.execution.MTacheImprevue" %>
<%@page import="owep.vue.transfert.convertor.VDateConvertor" %>
<%@page import="owep.vue.transfert.convertor.VStringConvertor" %>


<%
  //Récupération du ressource bundle.
  ResourceBundle messages = ResourceBundle.getBundle ("MessagesBundle") ;
  
  // Récupération des paramètres.
  ArrayList pListeProblemes = (ArrayList) request.getAttribute (CConstante.PAR_LISTEPROBLEMES) ;
%>

<table class="tableau" border="0" cellpadding="0" cellspacing="0">
<tbody>
  <tr>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("listeProblemeVisuAideLibelle") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("listeProblemeVisuLibelle") %></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("listeProblemeVisuAideEtat") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("listeProblemeVisuEtat") %></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("listeProblemeVisuAideDescription") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("listeProblemeVisuDescription") %></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("listeProblemeVisuAideDateIdentification") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("listeProblemeVisuDateIdentification") %></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("listeProblemeVisuAideDateCloture") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("listeProblemeVisuDateCloture") %></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("listeProblemeVisuAideTacheOrigine") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("listeProblemeVisuTacheOrigine") %></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("listeProblemeVisuAideTacheResolution") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("listeProblemeVisuTacheResolution") %></a>
    </td>
    <td class="caseNiveau1">
      &nbsp;
    </td>
  </tr>
  
  
  <%
  for (int lIndiceProbleme = 0; lIndiceProbleme < pListeProblemes.size (); lIndiceProbleme ++)
  {
    MProbleme lProbleme = (MProbleme) pListeProblemes.get (lIndiceProbleme) ;
  %>
    </tr>
     <td style="text-align: center" class="caseNiveau2">
        <a href="/owep/Gestion/ProblemeModif?<%= CConstante.PAR_PROBLEME %>=<%= lProbleme.getId () %>"><%= VStringConvertor.getString (lProbleme.getNom (), false) %></a>
      </td>
      <td class="caseNiveau3">
        <%= VStringConvertor.getString (lProbleme.getEtat (), false) %>
      </td>
      <td class="caseNiveau3">
        <%= VStringConvertor.getString (lProbleme.getDescription (), false) %>
      </td>
      <td style="text-align: center" class="caseNiveau3">
        <%= VDateConvertor.getString (lProbleme.getDateIdentification (), false) %>
      </td>
      <td style="text-align: center" class="caseNiveau3">
        <%= VDateConvertor.getString (lProbleme.getDateCloture (), false) %>
      </td>
      
      <td class="caseNiveau3Lien">
        <%
        // Affiche la liste des tâches à l'origine du problème.
        for (int lIndiceTache = 0; lIndiceTache < lProbleme.getNbTachesProvoque (); lIndiceTache ++)
        {
          MTache lTache = lProbleme.getTacheProvoque (lIndiceTache) ;
        %>
          <a href="/owep/Tache/TacheVisu?pTacheAVisualiser=<%= lTache.getId () %>"><%= lTache.getNom () %></a>
        <%
          // S'il ne s'agit pas du dernier problème de la liste, ajouter un retour ligne.
          if (lIndiceTache != lProbleme.getNbTachesProvoque ())
          {
        %>
          <br/>
        <%
          }
        }
        %>
        <%
        // Affiche la liste des tâches imprévues à l'origine du problème.
        for (int lIndiceTache = 0; lIndiceTache < lProbleme.getNbTachesImprevuesProvoque (); lIndiceTache ++)
        {
          MTacheImprevue lTache = lProbleme.getTacheImprevueProvoque (lIndiceTache) ;
        %>
          <a href="/owep/Tache/TacheVisu?pTacheAVisualiser=<%= lTache.getId () %>"><%= lTache.getNom () %></a>
        <%
          // S'il ne s'agit pas du dernier problème de la liste, ajouter un retour ligne.
          if (lIndiceTache != lProbleme.getNbTachesImprevuesProvoque ())
          {
        %>
          <br/>
        <%
          }
        }
        %>
      </td>
      
      
      <td class="caseNiveau3Lien">
        <%
        if ((lProbleme.getNbTachesResout () == 0) && (lProbleme.getNbTachesImprevuesResout () == 0))
        {
        %>
          &nbsp;
        <%
        }
        else
        {
          // Affiche la liste des tâches résolvant le problème.
          for (int lIndiceTache = 0; lIndiceTache < lProbleme.getNbTachesResout (); lIndiceTache ++)
          {
            MTache lTache = lProbleme.getTacheResout (lIndiceTache) ;
        %>
            <a href="/owep/Tache/TacheVisu?pTacheAVisualiser=<%= lTache.getId () %>"><%= lTache.getNom () %></a>
            <%
            // S'il ne s'agit pas du dernier problème de la liste, ajouter un retour ligne.
            if ((lIndiceTache != lProbleme.getNbTachesResout () - 1) || (lProbleme.getNbTachesImprevuesResout () > 0))
            {
            %>
              <br/>
        <%
            }
          }
          // Affiche la liste des tâches imprévues résolvant le problème.
          for (int lIndiceTache = 0; lIndiceTache < lProbleme.getNbTachesImprevuesResout (); lIndiceTache ++)
          {
            MTacheImprevue lTache = lProbleme.getTacheImprevueResout (lIndiceTache) ;
        %>
            <a href="/owep/Tache/TacheVisu?pTacheAVisualiser=<%= lTache.getId () %>"><%= lTache.getNom () %></a>
            <%
            // S'il ne s'agit pas du dernier problème de la liste, ajouter un retour ligne.
            if (lIndiceTache != lProbleme.getNbTachesImprevuesResout () - 1)
            {
            %>
              <br/>
        <%
            }
          }
        }
        %>
      </td>
      
      
      <td style="text-align: center" class="caseNiveau3" width="1px">
        <input type="button" value="<%= messages.getString("listeProblemeVisuBtnModfier") %>"  class="bouton" onclick="window.location.href = '/owep/Gestion/ProblemeModif?<%= CConstante.PAR_PROBLEME %>=<%= lProbleme.getId () %>' ;"
         onmouseover="tooltipOn (this, event, '<%= messages.getString("listeProblemeVisuAideBtnModfier") %>')" onmouseout="tooltipOff(this, event)"/>
        <input type="button" value="<%= messages.getString("listeProblemeVisuBtnSupprimer") %>" class="bouton" onclick="window.location.href = '/owep/Gestion/ProblemeSuppr?<%= CConstante.PAR_PROBLEME %>=<%= lProbleme.getId () %>' ;"
         onmouseover="tooltipOn (this, event, '<%= messages.getString("listeProblemeVisuAideBtnSupprimer") %>')" onmouseout="tooltipOff(this, event)"/>
      </td>
    </tr>
  <%
  }
  %>

  </tr>
    <td class="caseNiveau3Lien" colspan="8">
      <a href="/owep/Gestion/ProblemeModif" onmouseover="tooltipOn(this, event, '<%= messages.getString("listeProblemeVisuAideBtnAjouter") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("listeProblemeVisuBtnAjouter") %></a>
    </td>
  </tr>
  
</tbody>
</table>


<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "<%= messages.getString("listeProblemeVisuAide") %>" ;
</script>
