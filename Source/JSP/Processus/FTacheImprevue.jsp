<%@page import="owep.controle.CConstante"%>
<%@page import="owep.modele.execution.MIteration"%>
<%@page import="owep.modele.execution.MTacheImprevue"%>
<%@page import="owep.modele.execution.MActiviteImprevue"%>
<%@page import="owep.modele.execution.MProjet"%>
<%@page import="owep.modele.execution.MCollaborateur"%>
<%@page import="owep.modele.execution.MArtefactImprevue"%>
<%@page import="owep.vue.transfert.VTransfert"%>
<%@page import="owep.vue.transfert.VTransfertConstante"%>
<%@page import="owep.vue.transfert.convertor.VDateConvertor"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="owep.infrastructure.Session"%>
<%@taglib uri='/WEB-INF/tld/transfert.tld' prefix='transfert' %>


<!-- Déclaration des variables locales -->
<%
  // Recuperation de la session
  HttpSession httpSession = request.getSession(true);
  //Récupération du ressource bundle
  ResourceBundle messages = ((Session) httpSession.getAttribute("SESSION")).getMessages();

  MProjet lProjet = (MProjet) request.getAttribute (CConstante.PAR_PROJET) ;
  MIteration lIteration = (MIteration) request.getAttribute (CConstante.PAR_ITERATION) ;
  String lCodeValidation ;
  String lCodeAdditionnel;

  // Liste des champs transférés.
  String lChampTacheImprevueNom             = "" ;
  String lChampTacheImprevueDescription     = "" ;
  String lChampTacheImprevueChargeInitiale  = "" ;
  String lChampTacheImprevueDateDebutPrevue = "" ;
  String lChampTacheImprevueDateFinPrevue   = "" ;
  String lChampArtefactNom          = "" ;
  String lChampArtefactDescription  = "" ;
%>




<p class="titre1"><%= messages.getString("projet") %> : <%= lProjet.getNom () %></p>
<p class="texte"><%= lProjet.getDescription () %></p>
<br/><br/>

<table class="tableau" width="100%" cellpadding="0" cellspacing="0">
<tbody> 
  <tr> 
    <td class="caseNiveau1" colspan="2"><%= messages.getString("tacheImprevueInfoIte") %> :</td>
  </tr>
  <tr>
    <td class="caseNiveau3" colspan="2">
      <%= messages.getString("iterationNom") %>  : <%= lIteration.getNom () %>
    </td>
  </tr>
  <tr>
    <td class="caseNiveau3">
      <%= messages.getString("iterationDateDebutReelle") %> : <%= VDateConvertor.getString(lIteration.getDateDebutReelle ()) %>
    </td>
    <td class="caseNiveau3">
      <%= messages.getString("iterationDateFinPrevue") %> : <%= VDateConvertor.getString(lIteration.getDateFinPrevue ()) %>
    </td>
  </tr>
</table>

<br><br>
<form action="./TacheImprevue" method="post" name="<%= CConstante.PAR_FORMULAIRE%>">

  <transfert:transfertbean scope="Session" type="owep.modele.execution.MIteration" bean="pIteration" idArbre="<%= CConstante.PAR_ARBREITERATION %>">
  <input <transfert:transfertchamp membre="setNumero" type="java.lang.Integer" libelle="Numéro de l\\'itération" convertor="VIntegerConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
   type="hidden" value="<%= lIteration.getNumero () %>" maxlength="<%= CConstante.TXT_MOYEN %>">
  

  <table class="tableau" width="100%" cellpadding="0" cellspacing="0">
  <tbody>
  
    <!-- Liste des tâches imprévues-->
    <tr>
      <td class="caseNiveau1" colspan="2">
        <%= messages.getString("tacheImprevueListe") %> :
      </td>
    </tr>
    <tr> 
      <!-- Liste des champs cachés contenant les données des tâches -->
      <%
        for (int lIndiceTacheImprevue = 0; lIndiceTacheImprevue < lIteration.getNbTachesImprevues (); lIndiceTacheImprevue ++)
        {
          MTacheImprevue lTacheImprevue = lIteration.getTacheImprevue (lIndiceTacheImprevue) ;
      %>
        <transfert:transfertbean scope="Session" type="owep.modele.execution.MTacheImprevue" bean="getTacheImprevue" idArbre="<%= CConstante.PAR_ARBREITERATION %>">
        <!-- Ajoute le champ "Nom" à la liste -->
        <input <transfert:transfertchamp membre="setNom" type="" libelle="Nom de la tâche imprévue" convertor="VStringConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
         type="hidden" value="<%= lTacheImprevue.getNom () %>">
        
        <!-- Ajoute le champ "Description" à la liste -->
        <input <transfert:transfertchamp membre="setDescription" type="" libelle="Description de la tâche imprévue" convertor="VStringConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
         type="hidden" value="<%= lTacheImprevue.getDescription () %>">
        
        <!-- Ajoute le champ "ChargeInitiale" à la liste -->
        <input <transfert:transfertchamp membre="setChargeInitiale" type="" libelle="Charge initiale de la tâche imprévue" convertor="VDoubleConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
         type="hidden" value="<%= lTacheImprevue.getChargeInitiale () %>">
        
        <!-- Ajoute le champ "DateDebutPrevu" à la liste -->
        <input <transfert:transfertchamp membre="setDateDebutPrevue" type="" libelle="Date de début prévue de la tâche imprévue" convertor="VDateConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
         type="hidden" value="<%= VDateConvertor.getString (lTacheImprevue.getDateDebutPrevue ()) %>">
        
        <!-- Ajoute le champ "DateFinPrevue" à la liste -->
        <input <transfert:transfertchamp membre="setDateFinPrevue" type="" libelle="Date de fin prévue de la tâche imprévue" convertor="VDateConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
         type="hidden" value="<%= VDateConvertor.getString (lTacheImprevue.getDateFinPrevue ()) %>">
        
        
        <!-- Ajoute les champs activités imprévues et collaborateurs -->
        <!-- TODO A optimiser en utilisant une méthode générale de recherche (cf. TODO controleur) -->
        <% 
          /* Liste des champs cachés contenant les données des artefacts en sorties */ 
          for (int lIndiceArtefactImprevueSortie = 0 ; lIndiceArtefactImprevueSortie < lTacheImprevue.getNbArtefactsImprevuesSorties () ; lIndiceArtefactImprevueSortie ++)
          {
        %>
            <transfert:transfertbean scope="Session" type="owep.modele.execution.MArtefactImprevue" bean="getArtefactImprevueSortie" idArbre="<%= CConstante.PAR_ARBREITERATION %>">

            <input <transfert:transfertchamp membre="setNom" type="" libelle="Nom de l\\'artefact en sortie" convertor="VStringConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
             type="hidden" value="<%= lTacheImprevue.getArtefactImprevueSortie (lIndiceArtefactImprevueSortie).getNom () %>">
             
            <input <transfert:transfertchamp membre="setDescription" type="" libelle="Description de l\\'artefact en sortie" convertor="VStringConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
             type="hidden" value="<%= lTacheImprevue.getArtefactImprevueSortie (lIndiceArtefactImprevueSortie).getDescription () %>">
             
            </transfert:transfertbean>
        <%
          }
        %>
      </transfert:transfertbean>
      <%
        }
      %>
      
      
      <td class="caseNiveau3">
        <select name="<%= CConstante.PAR_LISTETACHESIMPREVUES %>" class="niveau2" style="width: 200" size="29"
         onchange="selectTacheImprevue (document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTETACHESIMPREVUES %>.selectedIndex)"
          onmouseover="tooltipOn(this, event, 'Liste des tâches imprévues actuellement définies pour l\'itération. Selectionnez-en une pour pouvoir la modifier.')" onmouseout="tooltipOff(this, event)">
          <%
            for (int lIndiceTacheImprevue = 0; lIndiceTacheImprevue < lIteration.getNbTachesImprevues (); lIndiceTacheImprevue ++)
            {
          %>
          <option value="<%= lIndiceTacheImprevue %>"> <%= lIteration.getTacheImprevue (lIndiceTacheImprevue).getNom () %> </option>
          <%    
            }
          %>
        </select>
      </td>
      
      
      
      <!-- Détail de la tâche -->
      <transfert:transfertbean scope="Session" type="owep.modele.execution.MTacheImprevue" bean="getTacheImprevue" idArbre="<%= CConstante.PAR_ARBRETACHESIMPREVUES %>">
      <td class="caseNiveau3" valign="top" align="left" width="100%" rowspan="2">
        <table width="100%" cellpadding="0" cellspacing="0" valign="top">
          <tr>
            <td colspan="2">
              <p class="titre2" onmouseout="tooltipOff(this, event)" onmouseover="tooltipOn(this, event, '<%= messages.getString("aideChampObligatoire") %><b>Section</b> vous permettant de spécifier les informations sur une tâches imprévues.<br/>' +
               ' Cliquez ensuite sur le bouton <b>Ajouter</b> ou <b>Modifier</b> en bas à gauche pour valider.')">
                <%= messages.getString("tacheImprevueDetail") %> :
              </p>
            </td>        
          </tr>
          <tr>
            <td width="50%" class="caseNiveau3SansBordure">
              <a href="#" class="texte" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("aideChampObligatoire") %>', '<%= messages.getString("aideTacheImprevueNom") %>')" onmouseout="tooltipOff(this, event)">    
                <%= messages.getString("tacheNom") %> * :
              </a>
            </td>
            <td>
              <input <transfert:transfertchamp membre="setNom" type="java.lang.String" libelle="Nom" convertor="VStringConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBRETACHESIMPREVUES %>"/>
               type="text" size="<%= CConstante.LNG_PETIT %>" class="niveau2" value="" maxlength="<%= CConstante.TXT_MOYEN %>"> &nbsp;&nbsp;&nbsp;<input class="bouton" type="button" value="Réinitialiser" onclick="reinitialiserChamps () ;"><br/>
              <% lChampTacheImprevueNom = VTransfert.getDernierChamp () ; %>
              
            </td>
          </tr>
          <tr>
            <td class="caseNiveau3SansBordure">
              <a href="#" class="texte" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("aideChampObligatoire") %>', '<%= messages.getString("aideTacheImprevueDescription") %>')" onmouseout="tooltipOff(this, event)">
                <%= messages.getString("tacheDescription") %> :
              </a>
            </td>
            <td>
              <input <transfert:transfertchamp membre="setDescription" type="java.lang.String" libelle="Description" convertor="VStringConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBRETACHESIMPREVUES %>"/>
               type="text" size="<%= CConstante.LNG_MOYEN %>" class="niveau2" value="" maxlength="<%= CConstante.TXT_LARGE %>"><br/>
              <% lChampTacheImprevueDescription = VTransfert.getDernierChamp () ; %>
            </td>
          </tr>
          <tr>
            <td class="caseNiveau3SansBordure">
              <a href="#" class="texte" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("aideChampObligatoire") %>', '<%= messages.getString("aideTacheImprevueCharge") %>')" onmouseout="tooltipOff(this, event)">
                <%= messages.getString("tacheChargeInitiale") %> * :
              </a>
            </td>
            <td>
              <input <transfert:transfertchamp membre="setChargeInitiale" type="java.lang.Double" libelle="Charge initiale" convertor="VDoubleConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBRETACHESIMPREVUES %>"/>
               type="text" size="8" class="niveau2" value="" maxlength="<%= CConstante.TXT_CHARGE %>"><br/>
              <% lChampTacheImprevueChargeInitiale = VTransfert.getDernierChamp () ; %>
            </td>
          </tr>
          <tr>
            <td class="caseNiveau3SansBordure">
              <a href="#" class="texte" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("aideChampObligatoire") %>', '<%= messages.getString("aideTacheImprevueDateDebut") %>')" onmouseout="tooltipOff(this, event)">
                <%= messages.getString("tacheDateDebutPrevue") %> * :
              </a>
            </td>
            <td>
              <input <transfert:transfertchamp membre="setDateDebutPrevue" type="java.util.Date" libelle="Date de début prévue" convertor="VDateConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBRETACHESIMPREVUES %>"/>
               type="text" size="8" class="niveau2" value="" maxlength="<%= CConstante.TXT_DATE %>"><br/>
              <% lChampTacheImprevueDateDebutPrevue = VTransfert.getDernierChamp () ; %>
            </td>
          </tr>
          <tr>
            <td class="caseNiveau3SansBordure">
              <a href="#" class="texte" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("aideChampObligatoire") %>', '<%= messages.getString("aideTacheImprevueDateFin") %>')" onmouseout="tooltipOff(this, event)">
                <%= messages.getString("tacheDateFinPrevue") %> * :
              </a>
            </td>
            <td>
              <input <transfert:transfertchamp membre="setDateFinPrevue" type="java.lang.Date" libelle="Date de fin prévue" convertor="VDateConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBRETACHESIMPREVUES %>"/>
               type="text" size="8" class="niveau2" value="" maxlength="<%= CConstante.TXT_DATE %>"><br/>
              <% lChampTacheImprevueDateFinPrevue = VTransfert.getDernierChamp () ; %>
            </td>
          </tr>
          </transfert:transfertbean>

          <tr>
            <td colspan="2">
              &nbsp;
            </td>
          </tr>
        
          <tr>
            <td class="caseNiveau3SansBordure">
              <!-- Données de l'activité imprévue. -->
              <a href="#" class="texte" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("aideChampObligatoire") %>', '<%= messages.getString("aideTacheImprevueActivite") %>')" onmouseout="tooltipOff(this, event)">
                <%= messages.getString("activiteImprevue") %> * :
              </a>
              <select class="niveau2" name="<%= CConstante.PAR_LISTEACTIVITESIMPREVUES %>" onchange="">
              <%
                for (int i = 0 ; i < lProjet.getNbActivitesImprevues () ; i++)
                {
              %> 
                <option value="<%= lProjet.getActiviteImprevue(i).getId() %>"> <%= lProjet.getActiviteImprevue(i).getNom () %> </option>
              <%
                }
              %>
              </select>
            </td>
          </tr>
          <tr>
            <td colspan="2" class="caseNiveau3SansBordure">
              <a href="#" class="texte" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("aideChampObligatoire") %>', '<%= messages.getString("aideTacheImprevueCollaborateur") %>')" onmouseout="tooltipOff(this, event)">
                <%= messages.getString("tacheCollaborateurAffecte") %> * :
              </a>
              <select class="niveau2" name="<%= CConstante.PAR_LISTECOLLABORATEURS %>">
              <%
                for (int i = 0 ; i < lProjet.getNbCollaborateurs () ; i++)
                {
              %>
              <option value="<%= lProjet.getCollaborateur (i).getId () %>"> <%= lProjet.getCollaborateur (i).getNom () %> &nbsp; <%= lProjet.getCollaborateur (i).getPrenom () %> </option>
              <%
                }
              %>
              </select>
            </td>
          </tr>
          <tr>
            <td colspan="2"> &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="2"> &nbsp;
              <transfert:transfertbean scope="Session" type="owep.modele.execution.MArtefact" bean="getArtefactSortie" idArbre="<%= CConstante.PAR_ARBREARTEFACTSORTIES %>">
              
              <% lCodeValidation = "validerTacheImprevueAjout () ;" ; %>
              <transfert:transfertsubmit libelle="<%= messages.getString("boutonAjouter") %>" valeur="<%= CConstante.PAR_SUBMITAJOUTER %>" verification="true" validation="<%= lCodeValidation %>"/>
        
              <% lCodeValidation = "validerTacheImprevueModif (document. " + CConstante.PAR_FORMULAIRE + "." + CConstante.PAR_LISTETACHESIMPREVUES + ", '" +  messages.getString("tacheImprevueMessageAucuneSelect") + "') ;" ; %>
              <transfert:transfertsubmit libelle="<%= messages.getString("boutonModifier") %>" valeur="<%= CConstante.PAR_SUBMITMODIFIER %>" verification="true" validation="<%= lCodeValidation %>"/>
        
              <% lCodeValidation = "validerTacheImprevueSuppr (document. " + CConstante.PAR_FORMULAIRE + "." + CConstante.PAR_LISTETACHESIMPREVUES + ", '" + messages.getString("tacheImprevueMessageAucuneSelect") + "') ;" ; %>
              <transfert:transfertsubmit libelle="<%= messages.getString("boutonSupprimer") %>" valeur="<%= CConstante.PAR_SUBMITSUPPRIMER %>" verification="true" validation="<%= lCodeValidation %>"/>
            </td>
          </tr>
          <tr>
            <td colspan="2"> &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="2"> &nbsp;
            </td>
          </tr>
          
          <tr>
            <td>
              <!-- Liste des artefacts en sorties -->
              
              <p class="titre2" onmouseout="tooltipOff(this, event)" onmouseover="tooltipOn(this, event, '<%= messages.getString("aideArtefactImprevueSortie") %>')">
                <%= messages.getString("artefactSortie") %> :
              </p>
            </td>
            <td>
              <p class="titre2" onmouseout="tooltipOff(this, event)" onmouseover="tooltipOn(this, event, '<%= messages.getString("aideArtefactImprevueEntree") %>')">
                <%= messages.getString("artefactEntree") %> :
              </p>  
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%" cellpadding="0" cellspacing="0" valign="top">
                <tr>       
                  <td colspan="2" align="center">
                    <select class="niveau2" name="<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>" style="width: 80%" 
                     onchange="selectArtefactImprevueSortie (document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTETACHESIMPREVUES %>.selectedIndex, document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>.selectedIndex)" size="4"
                     onmouseout="tooltipOff(this, event)" onmouseover="tooltipOn(this, event, '<%= messages.getString("aideArtefactImprevueSortieListe") %>')">
                    </select>
                  </td>
                </tr>
                <tr>
                  <td width="50%" class="caseNiveau3SansBordure"> 
                    <a href="#" class="texte" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("aideChampObligatoire") %>', '<%= messages.getString("aideArtefactSortieNom") %>')" onmouseout="tooltipOff(this, event)">    
                      <%= messages.getString("artefactNom") %> * :
                    </a>
                  </td>
                  <td>
                    <input <transfert:transfertchamp membre="setNom" type="java.lang.String" libelle="Nom" convertor="VStringConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREARTEFACTSORTIES %>"/>
                     type="text" size="8" class="niveau2" value="" maxlength="<%= CConstante.TXT_MOYEN %>">
                    <br/>
                    <% lChampArtefactNom = VTransfert.getDernierChamp () ; %>
                  </td>
                </tr>
                  <td class="caseNiveau3SansBordure">
                    <a href="#" class="texte" onmouseover="tooltipOn(this, event, '<%= messages.getString("aideArtefactSortieDescription") %>')" onmouseout="tooltipOff(this, event)">
                      <%= messages.getString("artefactDescription") %> :
                    </a>
                  </td>
                  <td>
                    <input <transfert:transfertchamp membre="setDescription" type="java.lang.String" libelle="Description" convertor="VStringConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBREARTEFACTSORTIES %>"/>
                     type="text" size="8" class="niveau2" value="" maxlength="<%= CConstante.TXT_LARGE %>">
                    <br/>
                    <% lChampArtefactDescription = VTransfert.getDernierChamp () ; %>
                  </td>
                </tr>
              </transfert:transfertbean>
                <tr>
                  <td class="caseNiveau3SansBordure">
                    <a href="#" class="texte" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("aideChampObligatoire") %>', '<%= messages.getString("aideArtefactSortieResponsable") %>')" onmouseout="tooltipOff(this, event)">
                      <%= messages.getString("artefactResponsable") %> * :
                    </a>
                  </td>
                  <td>
                    <select class="niveau2" name="<%= CConstante.PAR_LISTERESPONSABLES %>">
                    <%
                      for (int i = 0 ; i < lProjet.getNbCollaborateurs () ; i++)
                      {
                    %>
                        <option value="<%= i %>"> <%= lProjet.getCollaborateur (i).getNom () %> &nbsp; <%= lProjet.getCollaborateur (i).getPrenom () %> </option>
                    <%
                      }
                    %>
                    </select>
                    <br/>
                  </td>
                </tr>
                <tr>
                  <td colspan="2">        
                    <!-- Barre d'outils d'artefacts -->
                    <% lCodeValidation = "validerArtefactImprevueSortie (document. " + CConstante.PAR_FORMULAIRE + "." + CConstante.PAR_LISTETACHESIMPREVUES + ", 'Attention aucune tâche imprévue n\\'a été sélectionnée.' ) ;" ; %>
                    <% lCodeAdditionnel  = "onmouseover=\"tooltipOn(this, event, '" + messages.getString("aideArtefactSortieAjouter") + "')\" onmouseout=\"tooltipOff(this, event)\"" ; %>
                    <transfert:transfertsubmit libelle="<%= messages.getString("boutonAjouter") %>"   valeur="<%= CConstante.PAR_SUBMITAJOUTER_ARTSORTIES %>" verification="true" validation="<%= lCodeValidation %>"
                     additionnel="<%= lCodeAdditionnel %>"/>
                    
                    <% lCodeValidation = "validerArtefactImprevueSortie (document. " + CConstante.PAR_FORMULAIRE + "." + CConstante.PAR_LISTEARTEFACTSSORTIES + ", 'Attention aucun artefact en sortie n\\'a été sélectionné.') ;" ; %>
                    <% lCodeAdditionnel  = "onmouseover=\"tooltipOn(this, event, '" + messages.getString("aideArtefactSortieModifier") + "')\" onmouseout=\"tooltipOff(this, event)\"" ; %>
                    <transfert:transfertsubmit libelle="<%= messages.getString("boutonModifier") %>"  valeur="<%= CConstante.PAR_SUBMITMODIFIER_ARTSORTIES %>" verification="true" validation="<%= lCodeValidation %>"
                     additionnel="<%= lCodeAdditionnel %>"/>
                    
                    <% lCodeValidation = "validerArtefactImprevueSortieSuppr (document. " + CConstante.PAR_FORMULAIRE +"." + CConstante.PAR_LISTEARTEFACTSSORTIES + ", 'Attention aucun artefact en sortie n\\'a été sélectionné.') ;" ; %>
                    <% lCodeAdditionnel  = "onmouseover=\"tooltipOn(this, event, '" + messages.getString("aideArtefactSortieSupprimer") + "')\" onmouseout=\"tooltipOff(this, event)\"" ; %>
                    <transfert:transfertsubmit libelle="<%= messages.getString("boutonSupprimer") %>" valeur="<%= CConstante.PAR_SUBMITSUPPRIMER_ARTSORTIES %>" verification="true" validation="<%= lCodeValidation %>"
                     additionnel="<%= lCodeAdditionnel %>"/>
                  </td>
                </tr>
              </table>    
              
            </td>
            <td>
            
              <!-- Liste des artefacts en entrées -->
              <table width="100%" height="100%" cellpadding="0" cellspacing="0" valign="top">
                <tr>
                  <td class="caseNiveau3SansBordure" width="50%"> 
                    <a href="#" class="texte" onmouseover="tooltipOn(this, event, '<%= messages.getString("aideArtefactEntreeListePossible") %>')" onmouseout="tooltipOff(this, event)">       
                      <%= messages.getString("artefactEntreePossibles") %> :
                    </a>
                  </td>
                  <td class="caseNiveau3SansBordure"> 
                    <a href="#" class="texte" onmouseover="tooltipOn(this, event, '<%= messages.getString("aideArtefactEntreeListeEffectif") %>')" onmouseout="tooltipOff(this, event)">      
                      <%= messages.getString("artefactEntreesEffectifs") %> :
                    </a>
                  </td>
                </tr>
                <tr>
                  <td align="center">        
                    <select class="niveau2" name="<%= CConstante.PAR_LISTEARTEFACTSPOSSIBLES %>"  style="width: 95%" size="9">
                    </select>
                  </td>
                  <td align="center">       
                    <select class="niveau2" name="<%= CConstante.PAR_LISTEARTEFACTSENTREES %>" style="width: 95%" size="9">
                    </select>
                  </td>
                </tr>
                <tr>
                  <td align="center">
                    <% lCodeValidation = "validerArtefactImprevueEntree (document. " + CConstante.PAR_FORMULAIRE + "." + CConstante.PAR_LISTEARTEFACTSPOSSIBLES + ", 'Attention aucun artefact en entrée n\\'a été sélectionné.' ) ;" ; %>
                    <% lCodeAdditionnel  = "onmouseover=\"tooltipOn(this, event, '" + messages.getString("aideArtefactEntreeAjouter") + "')\" onmouseout=\"tooltipOff(this, event)\"" ; %>
                     <transfert:transfertsubmit libelle="<%= messages.getString("boutonAjouter") %>"  valeur="<%= CConstante.PAR_SUBMITAJOUTER_ARTENTREES %>" verification="true" validation="<%= lCodeValidation %>"
                     additionnel="<%= lCodeAdditionnel %>"/>
                  </td>
                  <td align="center">     
                    <% lCodeValidation = "validerArtefactImprevueEntreeSuppr (document. " + CConstante.PAR_FORMULAIRE +"." + CConstante.PAR_LISTEARTEFACTSENTREES + ", 'Attention aucun artefact en entrée n\\'a été sélectionné.' ) ;" ; %>
                    <% lCodeAdditionnel  = "onmouseover=\"tooltipOn(this, event, '" + messages.getString("aideArtefactEntreeSupprimer") + "')\" onmouseout=\"tooltipOff(this, event)\"" ; %>
                    <transfert:transfertsubmit libelle="<%= messages.getString("boutonSupprimer") %>"  valeur="<%= CConstante.PAR_SUBMITSUPPRIMER_ARTENTREES %>" verification="true" validation="<%= lCodeValidation %>"
                     additionnel="<%= lCodeAdditionnel %>"/>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td colspan="2"> &nbsp;
            </td>
          </tr>
          
        </table>
      </td>
    </tr>
  </tbody>
  </table>
  <p class="texteObligatoire"><%= messages.getString("champObligatoire") %></p>
  
  </transfert:transfertbean>
  
</form>




<!-- Code javascript -->
<script type="text/javascript" language="JavaScript1.2">

  <!----------------------------------------->
  <!-- Ensemble des données de l'itération -->
  <!----------------------------------------->
  var gListeTachesImprevues = new Array () ;
  <%
  // Parcours la liste des tâches imprévues.
  for (int lIndiceTacheImprevue = 0; lIndiceTacheImprevue < lIteration.getNbTachesImprevues (); lIndiceTacheImprevue ++)
  {
    MTacheImprevue    lTacheImprevue         = lIteration.getTacheImprevue (lIndiceTacheImprevue) ;
    MActiviteImprevue lTacheActiviteImprevue = lTacheImprevue.getActiviteImprevue () ;
    int lIdActiviteImprevue             = lTacheActiviteImprevue.getId () ;
    int lIdCollaborateurTacheImprevue = lTacheImprevue.getCollaborateur ().getId () ;
    int lIndiceActiviteImprevue = 0 ;
    int lIndiceCollaborateur = 0 ;
    boolean trouve = false ;
    
    for (lIndiceActiviteImprevue = 0; (! trouve) && (lIndiceActiviteImprevue < lProjet.getNbActivitesImprevues ()); lIndiceActiviteImprevue ++)
    {
      if (lProjet.getActiviteImprevue (lIndiceActiviteImprevue).getId () == lIdActiviteImprevue)
      {
        trouve = true ;
      }
    }
    trouve = false ;
    for (lIndiceCollaborateur = 0; (! trouve) && (lIndiceCollaborateur < lProjet.getNbCollaborateurs ()); lIndiceCollaborateur ++)
    {
      if (lProjet.getCollaborateur (lIndiceCollaborateur).getId () == lIdCollaborateurTacheImprevue)
      {
        trouve = true ;
      } 
    }
    lIndiceActiviteImprevue -- ;
    lIndiceCollaborateur -- ;
  %>
        <!-- Données de la tâche. -->
        gListeTachesImprevues.push (new Array ("<%= lTacheImprevue.getNom () %>", "<%=lTacheImprevue.getDescription ()%>", "<%=lTacheImprevue.getChargeInitiale () %>",
                                      "<%= VDateConvertor.getString (lTacheImprevue.getDateDebutPrevue ()) %>", "<%= VDateConvertor.getString (lTacheImprevue.getDateFinPrevue ()) %>",
                                      "<%= lIndiceActiviteImprevue %>",
                                      "<%= lIndiceCollaborateur %>", new Array (), new Array(), new Array())) ;
  <%
    // Parcours la liste des artefacts en entrées.
    for (int lIndiceArtefactImprevueEntree = 0; lIndiceArtefactImprevueEntree < lTacheImprevue.getNbArtefactsImprevuesEntrees (); lIndiceArtefactImprevueEntree ++)
    {
      MArtefactImprevue lArtefactImprevueEntree = lTacheImprevue.getArtefactImprevueEntree (lIndiceArtefactImprevueEntree) ;
  %>
      <!-- Données de l'artefact en entrée -->
      gListeTachesImprevues[<%= lIndiceTacheImprevue %>][8].push (new Array ("<%= lArtefactImprevueEntree.getId () %>", "<%= lTacheImprevue.getArtefactImprevueEntree (lIndiceArtefactImprevueEntree).getNom () %>")) ;
  <%
    }
    
    // Parcours la liste des artefacts en sorties.
    for (int lIndiceArtefactImprevueSortie = 0; lIndiceArtefactImprevueSortie < lTacheImprevue.getNbArtefactsImprevuesSorties (); lIndiceArtefactImprevueSortie ++)
    {
      MArtefactImprevue      lArtefactImprevueSortie = lTacheImprevue.getArtefactImprevueSortie (lIndiceArtefactImprevueSortie) ;
      MCollaborateur lResponsable    = lArtefactImprevueSortie.getResponsable () ;
      trouve = false ;
      int lIndiceResponsableA = 0 ;
      
      for (lIndiceResponsableA = 0 ; (! trouve) && (lIndiceResponsableA < lProjet.getNbCollaborateurs ()); lIndiceResponsableA ++)
      {
        if (lProjet.getCollaborateur (lIndiceResponsableA).getId () == lResponsable.getId ())
        {
          trouve = true ;
        }
      }
      lIndiceResponsableA -- ;
      
  %>
          <!-- Données de l'artefact en sortie -->
          gListeTachesImprevues[<%= lIndiceTacheImprevue %>][7].push (new Array ("<%= lArtefactImprevueSortie.getId () %>", "<%= lArtefactImprevueSortie.getNom () %>",
                                                                "<%= lArtefactImprevueSortie.getDescription () %>", "<%= lIndiceResponsableA %>")) ;
  <%
    }
  }
  %>




  <!--------------------------------------->
  <!-- Ensemble des données du processus -->
  <!--------------------------------------->
  var gListeActivitesImprevues = new Array () ;
  <%
    for (int lIndiceActiviteImprevue = 0; lIndiceActiviteImprevue < lProjet.getNbActivitesImprevues (); lIndiceActiviteImprevue ++)
    {
        MActiviteImprevue lActiviteImprevue = lProjet.getActiviteImprevue (lIndiceActiviteImprevue) ;
  %>
        <!-- Données de l'activité -->
        gListeActivitesImprevues.push (new Array ("<%= lActiviteImprevue.getId () %>", "<%= lActiviteImprevue.getNom () %>")) ;
  <%
   }
  %>
  
  
  
  
  <!------------------------------------------------------->
  <!-- Fonctions de gestion des composants du formulaire -->
  <!------------------------------------------------------->
  function selectTacheImprevue(pIndice)
  {
    // Efface les ancienne données.
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>.length = 0 ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampArtefactNom %>.value = '' ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampArtefactDescription %>.value = '' ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSPOSSIBLES %>.length = 0 ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSENTREES %>.length = 0 ;
    
    // Données de la tâche.
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampTacheImprevueNom %>.value = gListeTachesImprevues[pIndice][0] ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampTacheImprevueDescription %>.value = gListeTachesImprevues[pIndice][1] ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampTacheImprevueChargeInitiale %>.value = gListeTachesImprevues[pIndice][2] ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampTacheImprevueDateDebutPrevue %>.value = gListeTachesImprevues[pIndice][3] ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampTacheImprevueDateFinPrevue %>.value = gListeTachesImprevues[pIndice][4] ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITESIMPREVUES %>.selectedIndex = gListeTachesImprevues[pIndice][5] ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTECOLLABORATEURS %>.selectedIndex = gListeTachesImprevues[pIndice][6] ;
    
    // Initialise la liste des artefacts en sorties.
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>.length = 0 ;
    for (i = 0 ; i <  gListeTachesImprevues[pIndice][7].length ; i ++)
    {
      var option = new Option(gListeTachesImprevues [pIndice][7][i][1],gListeTachesImprevues [pIndice][7][i][0]) ;
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>.options[i] = option ;
    }
    
    // Initialise la liste des artefacts en entrées.
    for (i = 0 ; i <  gListeTachesImprevues[pIndice][8].length ; i ++)
    {
      var option = new Option(gListeTachesImprevues [pIndice][8][i][1],gListeTachesImprevues [pIndice][8][i][0]) ;
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSENTREES %>.options[i] = option ;
    }
    
    // Initialise la liste des artefacts en entrées
    k = 0;
    for (i = 0; i < gListeTachesImprevues.length ; i++)
    {
      if (i != pIndice)
      {
        for (j = 0; j < gListeTachesImprevues[i][7].length; j ++)
        {
          var option = new Option(gListeTachesImprevues [i][7][j][1],gListeTachesImprevues [i][7][j][0]) ;
          document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSPOSSIBLES %>.options[k] = option ;
          k = k + 1;
        }
      }
    }
  }
  
  
  function selectArtefactImprevueSortie(pIndiceTacheImprevue, pIndiceArtefactImprevue)
  {
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampArtefactNom %>.value = gListeTachesImprevues[pIndiceTacheImprevue][7][pIndiceArtefactImprevue][1] ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampArtefactDescription %>.value = gListeTachesImprevues[pIndiceTacheImprevue][7][pIndiceArtefactImprevue][2] ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTERESPONSABLES %>.selectedIndex = gListeTachesImprevues[pIndiceTacheImprevue][7][pIndiceArtefactImprevue][3] ;
  }
  
  function reinitialiserChamps () 
  {
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>.length = 0 ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampArtefactNom %>.value = '' ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampArtefactDescription %>.value = '' ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSPOSSIBLES %>.length = 0 ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSENTREES %>.length = 0 ;
    
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampTacheImprevueNom %>.value = '' ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampTacheImprevueDescription %>.value = '' ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampTacheImprevueChargeInitiale %>.value = '' ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampTacheImprevueDateDebutPrevue %>.value = '' ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampTacheImprevueDateFinPrevue %>.value = '' ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITESIMPREVUES %>.selectedIndex = 0 ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTECOLLABORATEURS %>.selectedIndex = 0 ;
  }
  <!------------------------------------------->
  <!-- Fonctions de validation du formulaire -->
  <!------------------------------------------->
  function validerTacheImprevueAjout ()
  {
    <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
    <%= VTransfertConstante.getVerification (CConstante.PAR_ARBRETACHESIMPREVUES) %> () ;
    validerChamps () ;  
  }
  
  
  function validerTacheImprevueModif (pSelect, pMessage)
  {
    if (pSelect.selectedIndex == -1)
    {
      alert (pMessage) ;
    }
    else
    {
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITESIMPREVUES %>.disabled = false ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBRETACHESIMPREVUES) %> () ;
      validerChamps () ;
    }
  }
  
  
  function validerTacheImprevueSuppr (pSelect, pMessage)
  {
    if (pSelect.selectedIndex == -1)
    {
      alert (pMessage) ;
    }
    else
    {
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITESIMPREVUES %>.disabled = false ;
      document.<%= CConstante.PAR_FORMULAIRE%>.submit () ;
    }
  }
  
  
  function validerArtefactImprevueSortie (pSelect, pMessage)
  {
    if (pSelect.selectedIndex == -1)
    {
      alert (pMessage) ;
    }
    else
    {
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITESIMPREVUES %>.disabled = false ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREARTEFACTSORTIES) %> () ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBRETACHESIMPREVUES) %> () ;
      validerChamps () ;
    }
  }
  

  function validerArtefactImprevueSortieSuppr (pSelect, pMessage)
  {
    if (pSelect.selectedIndex == -1)
    {
      alert (pMessage) ;
    }
    else
    {
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITESIMPREVUES %>.disabled = false ;
      document.<%= CConstante.PAR_FORMULAIRE%>.submit () ;
    }
  }
  
  
  function validerArtefactImprevueEntree (pSelect, pMessage)
  {
    if (pSelect.selectedIndex == -1)
    {
      alert (pMessage) ;
    }
    else
    {
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITESIMPREVUES %>.disabled = false ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBRETACHESIMPREVUES) %> () ;
      validerChamps () ;
    }
  }
  
  
  function validerArtefactImprevueEntreeSuppr (pSelect, pMessage)
  {
    if (pSelect.selectedIndex == -1)
    {
      alert (pMessage) ;
    }
    else
    {
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITESIMPREVUES %>.disabled = false ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBRETACHESIMPREVUES) %> () ;
      validerChamps () ;
    }
  }
  
  
  function validerFormulaire ()
  {
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITESIMPREVUES %>.disabled = false ;
    <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
    <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREARTEFACTSORTIES) %> () ;
    <%= VTransfertConstante.getVerification (CConstante.PAR_ARBRETACHESIMPREVUES) %> () ;
    validerChamps () ;
  }
  
  function isDateComprise (pDateDebut, pDateFin, pDate)
  {
    var dateDebutIt = valid_date(pDateDebut);
    var dateFinIt = valid_daye(pDateFin);
    var date = valid_date(pDate);
    if (dateDebutIt <= date <= dateFinIt)
    {
      alert("ok")
    }
    else
    {
      alert("ko");
    }
  }
  
  function valid_date(dateaaaammjj)
  {
    var dt=dateaaaammjj.split("-"),
    date=new Date(dt[0],dt[1]-1,dt[2]);
    return date.getDate()==date.getFullYear()==dt[2]?date:false&&date.getMonth()+1==dt[1]&&dt[0];
  } 
  
</script>

<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "<%= messages.getString("aideTacheImprevuePage") %>"
</script>