<%@page import="owep.controle.CConstante" %>
<%@page import="owep.modele.execution.MProjet" %>
<%@page import="owep.modele.execution.MIteration" %>
<%@page import="owep.vue.transfert.convertor.VDateConvertor" %>
<%@page import="owep.vue.transfert.convertor.VStringConvertor" %>
<%@page import="owep.vue.transfert.VTransfertConstante" %>
<%@page import="owep.infrastructure.Session"%>
<%@page import="java.util.ResourceBundle"%>
<%@taglib uri='/WEB-INF/tld/transfert.tld' prefix='transfert' %>

<jsp:useBean id="lSession"       class="owep.infrastructure.Session"          scope="page"/> 

<%
  // Utilisé pour stocker le code javascript.
  String lCodeValidation ;
  
  // Récupération des paramètres.
  MIteration pIteration = (MIteration) request.getAttribute (CConstante.PAR_ITERATION) ;
  MProjet    pProjet    = (MProjet)    request.getAttribute (CConstante.PAR_PROJET) ;

  // Recuperation de la session
  HttpSession httpSession = request.getSession(true);
  lSession = (Session) httpSession.getAttribute("SESSION");
  
  //Récupération du ressource bundle
  ResourceBundle messages = lSession.getMessages();
%>

<%String boutonValider = messages.getString("boutonValider");%>

<center>
<form action="./CloturerIteration" method="post" name="<%= CConstante.PAR_FORMULAIRE %>">
  <transfert:transfertbean scope="Session" type="owep.modele.execution.MIteration" bean="<%= CConstante.PAR_ITERATION %>" idArbre="<%= CConstante.PAR_ARBREITERATION %>">
  <%
  if (pIteration.getId () != 0)
  {
  %>
    <input name="<%= CConstante.PAR_ITERATION %>" type="hidden" value="<%= pIteration.getId () %>">
  <%
  }
  %>
   
  <table class="tableau" border="0" cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td class="caseNiveau1" colspan="4">
        <%=pIteration.getNom()%>
      </td>
    </tr>
    <tr>
      <td class="caseNiveau1" width="20%">
        <%=messages.getString("cloturerIterationDateDebutPrevue")%>
      </td>
      <td class="caseNiveau3" width="30%">
		<%=pIteration.getDateDebutPrevue()%>
      </td>
      <td class="caseNiveau1" width="20%">
        <%=messages.getString("cloturerIterationDateFinPrevue")%>
      </td>
      <td class="caseNiveau3" width="30%">
        <%=pIteration.getDateFinPrevue()%>
      </td>
    </tr>   	
    <tr>
      <td class="caseNiveau1" width="20%">
        <a href="#" class="niveau1" onmouseover="tooltipTitreOn(this, event, '<%=messages.getString("champObligatoireAide")%>', '<%=messages.getString("cloturerIterationAideDateDebutReelle")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("cloturerIterationDateDebutReelle")%></a>
      </td>
      <td class="caseNiveau3" width="30%">  
        <input <transfert:transfertchamp membre="setDateDebutReelle" type="java.lang.Date" libelle="<%=messages.getString("cloturerIterationDateDebutReelleErreur")%>" convertor="VDateConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
         class="niveau2" type="text" value="<%= VDateConvertor.getString (pIteration.getDateDebutReelle(), true) %>" size="<%= CConstante.LNG_DATE %>" maxlength="<%= CConstante.TXT_DATE %>">
      </td>
      <td class="caseNiveau1" width="20%">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("champObligatoireAide")%>', '<%=messages.getString("cloturerIterationAideDateFinReelle")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("cloturerIterationDateFinReelle")%></a>
      </td>
      <td class="caseNiveau3" width="30%">   
        <input <transfert:transfertchamp membre="setDateFinReelle" type="java.lang.Date" libelle="<%=messages.getString("cloturerIterationDateFinReelleErreur")%>" convertor="VDateConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
         class="niveau2" type="text" value="<%= VDateConvertor.getString (pIteration.getDateFinReelle(), true) %>" size="<%= CConstante.LNG_DATE %>" maxlength="<%= CConstante.TXT_DATE %>">
      </td>
    </tr>

    <tr>
      <td class="caseNiveau1" width="20%">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("cloturerIterationAideBilan")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("cloturerIterationBilan")%></a>
      </td>
      <td class="caseNiveau3" width="80%" colspan="3">  
        <textarea  <transfert:transfertchamp membre="setBilan" type="java.lang.String" libelle="<%=messages.getString("cloturerIterationBilanErreur")%>" convertor="VStringConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
         class="niveau2" rows="10" cols="<%= CConstante.LNG_LARGE %>" maxlength="<%= CConstante.TXT_LARGE %>"><%= VStringConvertor.getString (pIteration.getBilan(), true) %></textarea>
      </td>
    </tr>
  </tbody>
  </table>
  </transfert:transfertbean>
  
  <p class="texteObligatoire"><%=messages.getString("champObligatoire")%></p>
  <p class="texteSubmit" align='center'>
    <% lCodeValidation = VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION)+ " () ;" ; %>
    <% lCodeValidation += "validerChamps () ;" ; %>
    <transfert:transfertsubmit libelle="<%= boutonValider %>" valeur="<%= CConstante.PAR_SUBMIT %>" verification="true" validation="<%= lCodeValidation %>"
     additionnel="onmouseover=\"tooltipOn(this, event, '<%=messages.getString('cloturerIterationAideValider')%>')\" onmouseout=\"tooltipOff(this, event)\""/>
    <input type="button" value="<%=messages.getString("boutonAnnuler")%>" class="bouton" onclick="window.location.href = '/owep/Tache/ListeTacheVisu' ;"
     onmouseover="tooltipOn(this, event, '<%=messages.getString("cloturerIterationAideAnnuler")%>')" onmouseout="tooltipOff(this, event)"/>
  </p>
  
</form>
</center>




<!-- Code javascript -->
<script type="text/javascript" language="JavaScript">

  <!----------------------------------------------------------------------->
  <!-- Fonctions de validation des boutons d'ajout/suppression de tâches -->
  <!----------------------------------------------------------------------->
  function validerAjoutSupprTache (pIndice, pMessage)
  {
    if (pIndice != -1)
    {
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
      validerChamps () ;
    }
    else
    {
      alert (pMessage) ;
    }
  }
  
  function submitListesTaches (pSelect, pHidden)
  {
    pHidden.value = "" ;
    for (i = 0; i < pSelect.length; i ++)
    {
      pHidden.value = pHidden.value + "-" + pSelect[i].value ;
    }
  }
  
</script>


<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "<%=messages.getString("cloturerIterationAide")%>";
</script>
