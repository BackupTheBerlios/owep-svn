<%@page import="java.util.ResourceBundle"%>
<%@page import="owep.infrastructure.Session"%>
<%@page import="owep.controle.CConstante" %>
<%@page import="owep.modele.execution.MRisque" %>
<%@page import="owep.modele.execution.MProjet" %>
<%@page import="owep.modele.execution.MIteration" %>
<%@page import="owep.vue.transfert.convertor.VDateConvertor" %>
<%@page import="owep.vue.transfert.convertor.VStringConvertor" %>
<%@page import="owep.vue.transfert.VTransfertConstante" %>
<%@taglib uri='/WEB-INF/tld/transfert.tld' prefix='transfert' %>


<%
  //Récupération du ressource bundle.
  ResourceBundle messages = ResourceBundle.getBundle ("MessagesBundle") ;
  
  // Utilisé pour stocker le code javascript.
  String lCodeValidation ;
  String lCodeAdditionnel ;
  
  // Récupération des paramètres.
  MRisque pRisque = (MRisque) request.getAttribute (CConstante.PAR_RISQUE) ;
%>

<center>
<form action="./RisqueModif" method="post" name="<%= CConstante.PAR_FORMULAIRE %>">
  <transfert:transfertbean scope="Session" type="owep.modele.execution.MRisque" bean="<%= CConstante.PAR_RISQUE %>" idArbre="<%= CConstante.PAR_ARBRERISQUE %>">
  <%
  if (pRisque.getId () != 0)
  {
  %>
    <input name="<%= CConstante.PAR_RISQUE %>" type="hidden" value="<%= pRisque.getId () %>">
  <%
  }
  %>
   
  <table class="tableau" width="90%" border="0" cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td class="caseNiveau1" width="20%">
        <a href="#" class="niveau1" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("champObligatoireAide") %>', '<%= messages.getString("risqueModifAideLibelle") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("risqueModifLibelle") %></a>
      </td>
      <td class="caseNiveau3" width="80%">
        <input <transfert:transfertchamp membre="setNom" type="java.lang.String" libelle="<%= messages.getString("risqueModifJsLibelle") %>" convertor="VStringConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBRERISQUE %>"/>
         class="niveau2" type="text" value="<%= VStringConvertor.getString (pRisque.getNom (), true) %>" size="<%= CConstante.LNG_MOYEN %>" maxlength="<%= CConstante.TXT_MOYEN %>">
      </td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("risqueModifAideEtat") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("risqueModifEtat") %></a>
      </td>
      <td class="caseNiveau3">
        <input <transfert:transfertchamp membre="setEtat" type="java.lang.String" libelle="<%= messages.getString("risqueModifJsEtat") %>" convertor="VStringConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBRERISQUE %>"/>
         class="niveau2" type="text" value="<%= VStringConvertor.getString (pRisque.getEtat (), true) %>" size="<%= CConstante.LNG_MOYEN %>" maxlength="<%= CConstante.TXT_MOYEN %>">
      </td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("risqueModifAideDescription") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("risqueModifDescription") %></a>
      </td>
      <td class="caseNiveau3">
        <textarea <transfert:transfertchamp membre="setDescription" type="java.lang.String" libelle="<%= messages.getString("risqueModifJsDescription") %>" convertor="VStringConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBRERISQUE %>"/>
         class="niveau2" rows="<%= CConstante.LNG_ROWSCOMMENTAIRE %>" cols="<%= CConstante.LNG_COLSCOMMENTAIRE %>" maxlength="<%= CConstante.LNG_COMMENTAIRE %>"><%= VStringConvertor.getString (pRisque.getDescription (), true) %></textarea>
      </td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("risqueModifAideImpact") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("risqueModifImpact") %></a>
      </td>
      <td class="caseNiveau3">
        <textarea <transfert:transfertchamp membre="setImpact" type="java.lang.String" libelle="<%= messages.getString("risqueModifJsImpact") %>" convertor="VStringConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBRERISQUE %>"/>
         class="niveau2" rows="<%= CConstante.LNG_ROWSCOMMENTAIRE %>" cols="<%= CConstante.LNG_COLSCOMMENTAIRE %>" maxlength="<%= CConstante.LNG_COMMENTAIRE %>"><%= VStringConvertor.getString (pRisque.getImpact (), true) %></textarea>
      </td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("risqueModifAideActions") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("risqueModifActions") %></a>
      </td>
      <td class="caseNiveau3">
        <textarea <transfert:transfertchamp membre="setActions" type="java.lang.String" libelle="<%= messages.getString("risqueModifJsActions") %>" convertor="VStringConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBRERISQUE %>"/>
         class="niveau2" rows="<%= CConstante.LNG_ROWSCOMMENTAIRE %>" cols="<%= CConstante.LNG_COLSCOMMENTAIRE %>" maxlength="<%= CConstante.LNG_COMMENTAIRE %>"><%= VStringConvertor.getString (pRisque.getActions (), true) %></textarea>
      </td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("champObligatoireAide") %>', '<%= messages.getString("risqueModifAidePriorite") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("risqueModifPriorite") %></a>
      </td>
      <td class="caseNiveau3">
        <input <transfert:transfertchamp membre="setPriorite" type="java.lang.Integer" libelle="<%= messages.getString("risqueModifJsPriorite") %>" convertor="VIntegerConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBRERISQUE %>"/>
         type="text" class="niveau2" value="<%= pRisque.getPriorite () %>" size="<%= CConstante.LNG_NUMERO %>" maxlength="<%= CConstante.TXT_NUMERO %>">
      </td>
    </tr>
  </tbody>
  </table>
    
  </transfert:transfertbean>
  
  
  <p class="texteObligatoire"><%= messages.getString("champObligatoire") %></p>
  <p class="texteSubmit">
    <% lCodeValidation  = VTransfertConstante.getVerification (CConstante.PAR_ARBRERISQUE) + " () ;" ; %>
    <% lCodeValidation += "validerChamps () ;" ; %>
    <% lCodeAdditionnel = "onmouseover=\"tooltipOn(this, event, '" + messages.getString("risqueModifAideBtnValider") + "')\" onmouseout=\"tooltipOff(this, event)\"" ; %>
    <transfert:transfertsubmit libelle="<%= messages.getString("boutonValider") %>" valeur="<%= CConstante.PAR_SUBMIT %>" verification="true" validation="<%= lCodeValidation %>"
     additionnel="<%= lCodeAdditionnel %>"/>
    <input type="button" value="<%= messages.getString("boutonAnnuler") %>" class="bouton" onclick="window.location.href = '/owep/Gestion/ListeRisqueVisu' ;"
     onmouseover="tooltipOn(this, event, '<%= messages.getString("risqueModifAideBtnAnnuler") %>')" onmouseout="tooltipOff(this, event)"/>
  </p>

</form>
</center>


<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide = "<%= messages.getString("risqueModifAide") %>" ;
</script>
