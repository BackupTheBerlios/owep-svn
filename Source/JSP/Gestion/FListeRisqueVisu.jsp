<%@page import="java.util.ResourceBundle"%>
<%@page import="owep.infrastructure.Session"%>
<%@page import="java.util.ArrayList" %>
<%@page import="owep.controle.CConstante" %>
<%@page import="owep.modele.execution.MRisque" %>
<%@page import="owep.modele.execution.MProjet" %>
<%@page import="owep.vue.transfert.convertor.VIntegerConvertor" %>
<%@page import="owep.vue.transfert.convertor.VStringConvertor" %>


<%
  //Récupération du ressource bundle.
  ResourceBundle messages = ResourceBundle.getBundle ("MessagesBundle") ;
  
  // Récupération des paramètres.
  MProjet pProjet = (MProjet) request.getAttribute (CConstante.PAR_PROJET) ;
  
  // Trie les risques.
  ArrayList lRisquesTmp = new ArrayList (pProjet.getListeRisques ()) ;
  ArrayList lRisques = new ArrayList () ;
  for (int lIndiceRisque = 0; lIndiceRisque < pProjet.getNbRisques (); lIndiceRisque ++)
  {
    // Cherche le risque de priorité la plus basse.
    int lPrioriteMax = 0 ;
    for (int i = 1; i < lRisquesTmp.size (); i ++)
    {
      if (((MRisque) lRisquesTmp.get (i)).getPriorite () < ((MRisque) lRisquesTmp.get (lPrioriteMax)).getPriorite ())
      {
        lPrioriteMax = i ;
      }
    }
    lRisques.add (lRisquesTmp.get (lPrioriteMax)) ;
    lRisquesTmp.remove (lPrioriteMax) ;
  }
%>

<center>
<table width="90%" class="tableau" border="0" cellpadding="0" cellspacing="0">
<tbody>
  <tr>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("listeRisqueVisuAideLibelle") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("listeRisqueVisuLibelle") %></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("listeRisqueVisuAideEtat") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("listeRisqueVisuEtat") %></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("listeRisqueVisuAideDescription") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("listeRisqueVisuDescription") %></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("listeRisqueVisuAideImpact") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("listeRisqueVisuImpact") %></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("listeRisqueVisuAideActions") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("listeRisqueVisuActions") %></a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("listeRisqueVisuAidePriorite") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("listeRisqueVisuPriorite") %></a>
    </td>
    <td class="caseNiveau1">
      &nbsp;
    </td>
  </tr>
  
  
  <%
  for (int lIndiceRisque = 0; lIndiceRisque < lRisques.size (); lIndiceRisque ++)
  {
    MRisque lRisque = (MRisque) lRisques.get (lIndiceRisque) ;
  %>
    </tr>
     <td style="text-align: center" class="caseNiveau2">
        <a href="/owep/Gestion/RisqueModif?<%= CConstante.PAR_RISQUE %>=<%= lRisque.getId () %>"><%= VStringConvertor.getString (lRisque.getNom (), false) %></a>
      </td>
      <td class="caseNiveau3">
        <%= VStringConvertor.getString (lRisque.getEtat (), false) %>
      </td>
      <td class="caseNiveau3">
        <%= VStringConvertor.getString (lRisque.getDescription (), false) %>
      </td>
      <td class="caseNiveau3">
        <%= VStringConvertor.getString (lRisque.getImpact (), false) %>
      </td>
      <td class="caseNiveau3">
        <%= VStringConvertor.getString (lRisque.getActions (), false) %>
      </td>
      <td style="text-align: center" class="caseNiveau3">
        <%= lRisque.getPriorite () %>
      </td>
      
            
      <td style="text-align: center" class="caseNiveau3" width="1px">
        <input type="button" value="<%= messages.getString("listeRisqueVisuBtnModifier") %>"  class="bouton" onclick="window.location.href = '/owep/Gestion/RisqueModif?<%= CConstante.PAR_RISQUE %>=<%= lRisque.getId () %>' ;"
         onmouseover="tooltipOn (this, event, '<%= messages.getString("listeRisqueVisuAideBtnModifier") %>')" onmouseout="tooltipOff(this, event)"/>
        <input type="button" value="<%= messages.getString("listeRisqueVisuBtnSupprimer") %>" class="bouton" onclick="window.location.href = '/owep/Gestion/RisqueSuppr?<%= CConstante.PAR_RISQUE %>=<%= lRisque.getId () %>' ;"
         onmouseover="tooltipOn (this, event, '<%= messages.getString("listeRisqueVisuAideBtnSupprimer") %>')" onmouseout="tooltipOff(this, event)"/>
      </td>
    </tr>
  <%
  }
  %>

  </tr>
    <td class="caseNiveau3Lien" colspan="8">
      <a href="/owep/Gestion/RisqueModif" onmouseover="tooltipOn(this, event, '<%= messages.getString("listeRisqueVisuAideBtnAjouter") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("listeRisqueVisuBtnAjouter") %></a>
    </td>
  </tr>
  
</tbody>
</table>
</center>


<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "<%= messages.getString("listeRisqueVisuAide") %>" ;
</script>
