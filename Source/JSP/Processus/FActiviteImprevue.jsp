<%@page import="owep.controle.CConstante"%>
<%@page import="owep.vue.transfert.VTransfert"%>
<%@page import="owep.vue.transfert.VTransfertConstante"%>
<%@page import="owep.modele.execution.MProjet"%>
<%@page import="owep.modele.execution.MActiviteImprevue"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="owep.infrastructure.Session"%>
<%@taglib uri='/WEB-INF/tld/transfert.tld' prefix='transfert' %>

<%
  // Recuperation de la session
  HttpSession httpSession = request.getSession(true);
  //Récupération du ressource bundle
  ResourceBundle messages = ((Session) httpSession.getAttribute("SESSION")).getMessages();
  
  MProjet lProjet = (MProjet) request.getAttribute (CConstante.PAR_PROJET) ;
  
  // Liste des champs transférés.
  String lChampActiviteImprevueNom             = "" ;
  String lChampActiviteImprevueDescription     = "" ;
%>


<p class="titre1"><%= messages.getString("projet") %> : <%= lProjet.getNom () %></p>
<p class="texte"><%= lProjet.getDescription () %></p>
<br/><br/>
 
<form action="./ActiviteImprevue" method="post" name="<%= CConstante.PAR_FORMULAIRE%>">

      <%
        for (int lIndiceActiviteImp = 0; lIndiceActiviteImp < lProjet.getNbActivitesImprevues () ; lIndiceActiviteImp ++)
        {
          MActiviteImprevue lActiviteImprevue = lProjet.getActiviteImprevue (lIndiceActiviteImp) ;
      %>
        <transfert:transfertbean scope="Session" type="owep.modele.processus.MActiviteImprevue" bean="pActiviteImprevue" idArbre="<%= CConstante.PAR_ARBREACTIVITEIMPREVUE %>">
          <!-- Ajoute le champ "Nom" à la liste -->
          <input <transfert:transfertchamp membre="setNom" type="" libelle="Nom de l\\'activité imprévue" convertor="VStringConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREACTIVITEIMPREVUE %>"/>
           type="hidden" value="<%= lActiviteImprevue.getNom () %>">
        
          <!-- Ajoute le champ "Description" à la liste -->
          <input <transfert:transfertchamp membre="setDescription" type="" libelle="Description de l'activité imprévue" convertor="VStringConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBREACTIVITEIMPREVUE %>"/>
           type="hidden" value="<%= lActiviteImprevue.getDescription () %>">
       </transfert:transfertbean>
      <%
        }
      %>  
        


  <table class="tableau" width="100%" cellpadding="0" cellspacing="0">
  <tbody>
  
    <!-- Champs de l'activité imprévue -->
    <tr> 
      <td class="caseNiveau1" colspan="2"><%= messages.getString("activiteImprevue") %> :</td>
    </tr>
    <tr>
      <td>
        <select name="<%= CConstante.PAR_LISTEACTIVITESIMPREVUES %>" class="niveau2" style="width: 200" size="5"
         onchange="selectActiviteImprevue (document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITESIMPREVUES %>.selectedIndex)"
         onmouseover="tooltipOn (this, event, '<%= messages.getString("activiteImprevueListeActivite") %>')" onmouseout="tooltipOff(this, event)">
          <% for (int lIndiceActiviteImprevue = 0; lIndiceActiviteImprevue < lProjet.getNbActivitesImprevues (); lIndiceActiviteImprevue++)
             {
          %>
               <option value="<%= lIndiceActiviteImprevue %>"> <%= lProjet.getActiviteImprevue (lIndiceActiviteImprevue).getNom () %> </option>
          <%
             }
          %>
        </select>
      </td>
      <td>
        <transfert:transfertbean scope="Session" type="owep.modele.processus.MActiviteImprevue" bean="pActiviteImprevue" idArbre="<%= CConstante.PAR_ARBREACTIVITE %>">
        <table width="100%" cellpadding="0" cellspacing="0" valign="top">
          <tr>
            <td class="caseNiveau3SansBordure">
              <%= messages.getString("activiteImprevueNom") %> * :
            </td>
            <td>
              <input <transfert:transfertchamp membre="setNom" type="java.lang.String" libelle="Nom de l\\'activité" convertor="VStringConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREACTIVITE %>"/>
               type="text" size="48" class="niveau2" value="" maxlength="<%= CConstante.TXT_MOYEN %>"
               onmouseover="tooltipOn (this, event, '<%= messages.getString("activiteImprevueSaisieNom") %>')" onmouseout="tooltipOff(this, event)">
              <% lChampActiviteImprevueNom = VTransfert.getDernierChamp () ; %>
            </td>
          </tr>
          <tr>
            <td class="caseNiveau3SansBordure">
             <%= messages.getString("activiteImprevueDescription") %> :
            </td>
            <td>
              <input <transfert:transfertchamp membre="setDescription" type="java.lang.String" libelle="Description" convertor="VStringConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBREACTIVITE %>"/> 
               type="text" size="48" class="niveau2" value=""  maxlength="<%= CConstante.TXT_MOYEN %>"
               onmouseover="tooltipOn (this, event, '<%= messages.getString("activiteImprevueSaisieDescription") %>')" onmouseout="tooltipOff(this, event)">
              <% lChampActiviteImprevueDescription = VTransfert.getDernierChamp () ; %>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <p class="texteObligatoire"><%= messages.getString("champObligatoire") %></p>

  </transfert:transfertbean>

  <br>
  <p class="paragrapheSubmit">
    <transfert:transfertsubmit libelle="<%= messages.getString("boutonAjouter") %>" valeur="<%= CConstante.PAR_SUBMIT %>" verification="true" validation="validerFormulaireAjouter () ;"/>
    <transfert:transfertsubmit libelle="<%= messages.getString("boutonModifier") %>" valeur="<%= CConstante.PAR_SUBMITMODIFIER %>" verification="true" validation="validerFormulaireModifier () ;"/>
    <transfert:transfertsubmit libelle="<%= messages.getString("boutonSupprimer") %>" valeur="<%= CConstante.PAR_SUBMITSUPPRIMER %>" verification="true" validation="validerFormulaireSupprimer () ;"/>
  </p>

</form>


<script>
  <!-------------------------------------------------->
  <!-- Ensemble des données des activités imprévues -->
  <!-------------------------------------------------->
  var gListeActivitesImprevues = new Array () ;
  <%
    for (int lIndiceActiviteImprevue = 0; lIndiceActiviteImprevue < lProjet.getNbActivitesImprevues (); lIndiceActiviteImprevue ++)
    {
      MActiviteImprevue lActiviteImprevueTmp = lProjet.getActiviteImprevue (lIndiceActiviteImprevue) ;
  %>
      gListeActivitesImprevues.push (new Array ("<%= lIndiceActiviteImprevue %>", "<%= lActiviteImprevueTmp.getNom () %>", "<%= lActiviteImprevueTmp.getDescription () %>")) ;
  <%
    }
  %>

  <!------------------------------------------------------->
  <!-- Fonctions de gestion des composants du formulaire -->
  <!------------------------------------------------------->
  function selectActiviteImprevue (pIndiceActiviteImprevue)
  {
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampActiviteImprevueNom %>.value = gListeActivitesImprevues[pIndiceActiviteImprevue][1] ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampActiviteImprevueDescription %>.value = gListeActivitesImprevues[pIndiceActiviteImprevue][2] ;
  }
  
  <!------------------------------------------->
  <!-- Fonctions de validation du formulaire -->
  <!------------------------------------------->

  function validerFormulaireAjouter () 
  {
    <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREACTIVITE) %> () ;
    validerChamps () ;
  }
  
  function validerFormulaireModifier () 
  {
    if (confirm("<%= messages.getString("activiteImprevueMessageModifier") %>"))
    {
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREACTIVITE) %> () ;
      validerChamps () ;
    }
  }
  
  function validerFormulaireSupprimer () 
  {
    if (confirm("<%= messages.getString("activiteImprevueMessageSupprimer") %>"))
    {
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREACTIVITE) %> () ;
      validerChamps () ;
    }
  }
</script>

<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "<%= messages.getString("activiteImprevueAide") %>" ;
</script>