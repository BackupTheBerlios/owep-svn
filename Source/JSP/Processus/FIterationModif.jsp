<%@page import="owep.controle.CConstante"%>
<%@page import="owep.modele.processus.MComposant"%>
<%@page import="owep.modele.processus.MProcessus"%>
<%@page import="owep.modele.processus.MDefinitionTravail"%>
<%@page import="owep.modele.processus.MActivite"%>
<%@page import="owep.modele.processus.MProduit"%>
<%@page import="owep.modele.processus.MRole"%>
<%@page import="owep.modele.execution.MIteration"%>
<%@page import="owep.modele.execution.MTache"%>
<%@page import="owep.modele.execution.MProjet"%>
<%@page import="owep.modele.execution.MCollaborateur"%>
<%@page import="owep.modele.execution.MArtefact"%>
<%@page import="owep.modele.execution.MCondition"%>
<%@page import="owep.vue.transfert.VTransfert"%>
<%@page import="owep.vue.transfert.VTransfertConstante"%>
<%@page import="owep.vue.transfert.convertor.VDateConvertor"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="owep.infrastructure.Session"%>
<%@taglib uri='/WEB-INF/tld/transfert.tld' prefix='transfert' %>


<!-- Déclaration des variables locales -->
<%
  // Recuperation de la session
  HttpSession httpSession = request.getSession(true);
  //Récupération du ressource bundle
  ResourceBundle messages = ((Session) httpSession.getAttribute("SESSION")).getMessages();

  boolean lModif = request.getParameter (CConstante.PAR_SUBMIT) != null ;
  MIteration lIteration = (MIteration) session.getAttribute (CConstante.PAR_ITERATION) ;
  MProjet lProjet = (MProjet) request.getAttribute (CConstante.PAR_PROJET) ;
  MProcessus lProcessus = lProjet.getProcessus () ;
  String lCodeValidation ;
  String lCodeAdditionnel;

  // Liste des champs transférés.
  String lChampTacheNom             = "" ;
  String lChampTacheDescription     = "" ;
  String lChampTacheChargeInitiale  = "" ;
  String lChampTacheDateDebutPrevue = "" ;
  String lChampTacheDateFinPrevue   = "" ;
  String lChampArtefactNom          = "" ;
  String lChampArtefactDescription  = "" ;
%>




<p class="titre1"><%= messages.getString("projet") %> : <%= lProjet.getNom () %></p>
<p class="texte"><%= lProjet.getDescription () %></p>
<br/><br/>
 
<form action="./IterationModif" method="post" name="<%= CConstante.PAR_FORMULAIRE%>">

  <transfert:transfertbean scope="Session" type="owep.modele.execution.MIteration" bean="pIteration" idArbre="<%= CConstante.PAR_ARBREITERATION %>">
  <input <transfert:transfertchamp membre="setNumero" type="java.lang.Integer" libelle="Numéro de l\\'itération" convertor="VIntegerConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
   type="hidden" value="<%= lIteration.getNumero () %>" maxlength="<%= CConstante.TXT_MOYEN %>">
  
  
  <table class="tableau" width="100%" cellpadding="0" cellspacing="0">
  <tbody>
  
    <!-- Champs de l'itération -->
    <tr> 
      <td class="caseNiveau1" colspan="2"><%= messages.getString("iteration") %> <%= lIteration.getNumero () %> :</td>
    </tr>
    <tr>
      <td class="caseNiveau3" colspan="2">
        <%= messages.getString("iterationNom") %> * : <input <transfert:transfertchamp membre="setNom" type="java.lang.String" libelle="Nom de l\\'itération" convertor="VStringConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
                              type="text" size="48" class="niveau2"
                              value="<%= lIteration.getNom () %>" maxlength="<%= CConstante.TXT_MOYEN %>">
      </td>
    </tr>
    <tr> 
      <td class="caseNiveau3" width="50%">
        <%= messages.getString("iterationDateDebutPrevue") %> * : <input <transfert:transfertchamp membre="setDateDebutPrevue" type="java.util.Date" libelle="Date de début prévue" convertor="VDateConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/> 
                                type="text" size="8" class="niveau2"
                                value="<%= VDateConvertor.getString (lIteration.getDateDebutPrevue ()) %>"  maxlength="<%= CConstante.TXT_DATE %>">
      </td>
      <td class="caseNiveau3" width="50%">
        <%= messages.getString("iterationDateFinPrevue") %> * : <input <transfert:transfertchamp membre="setDateFinPrevue" type="java.util.Date" libelle="Date de fin prévue" convertor="VDateConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>  
                              type="text" size="8" class="niveau2"
                              value="<%= VDateConvertor.getString (lIteration.getDateFinPrevue ()) %>" 
                              maxlength="<%= CConstante.TXT_DATE %>">
      </td>
    </tr>
  </tbody>
  </table>
  
  <br/><br/><br/>
  
  <table class="tableau" width="100%" cellpadding="0" cellspacing="0">
  <tbody>
  
    <!-- Liste des tâches -->
    <tr>
      <td class="caseNiveau1" colspan="2">
        <%= messages.getString("tacheListeRealiser") %> :
      </td>
    </tr>
    <tr> 
      
      <!-- Liste des champs cachés contenant les données des tâches -->
      <%
        for (int lIndiceTache = 0; lIndiceTache < lIteration.getNbTaches (); lIndiceTache ++)
        {
          MTache lTache = lIteration.getTache (lIndiceTache) ;
      %>
        <transfert:transfertbean scope="Session" type="owep.modele.execution.MTache" bean="getTache" idArbre="<%= CConstante.PAR_ARBREITERATION %>">
        <!-- Ajoute le champ "Nom" à la liste -->
        <input <transfert:transfertchamp membre="setNom" type="" libelle="Nom de la tâche" convertor="VStringConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
         type="hidden" value="<%= lTache.getNom () %>">
        
        <!-- Ajoute le champ "Description" à la liste -->
        <input <transfert:transfertchamp membre="setDescription" type="" libelle="Description de la tâche" convertor="VStringConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
         type="hidden" value="<%= lTache.getDescription () %>">
        
        <!-- Ajoute le champ "ChargeInitiale" à la liste -->
        <input <transfert:transfertchamp membre="setChargeInitiale" type="" libelle="Charge initiale de la tâche" convertor="VDoubleConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
         type="hidden" value="<%= lTache.getChargeInitiale () %>">
        
        <!-- Ajoute le champ "DateDebutPrevu" à la liste -->
        <input <transfert:transfertchamp membre="setDateDebutPrevue" type="" libelle="Date de début prévue de la tâche" convertor="VDateConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
         type="hidden" value="<%= VDateConvertor.getString (lTache.getDateDebutPrevue ()) %>">
        
        <!-- Ajoute le champ "DateFinPrevue" à la liste -->
        <input <transfert:transfertchamp membre="setDateFinPrevue" type="" libelle="Date de fin prévue de la tâche" convertor="VDateConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
         type="hidden" value="<%= VDateConvertor.getString (lTache.getDateFinPrevue ()) %>">
        
        
        <!-- Ajoute les champs Disciplines, activités et collaborateurs -->
        <!-- TODO A optimiser en utilisant une méthode générale de recherche (cf. TODO controleur) -->
        <% 
          /* Liste des champs cachés contenant les données des artefacts en sorties */ 
          for (int lIndiceArtefactSortie = 0 ; lIndiceArtefactSortie < lTache.getNbArtefactsSorties () ; lIndiceArtefactSortie ++)
          {
        %>
            <transfert:transfertbean scope="Session" type="owep.modele.execution.MArtefact" bean="getArtefactSortie" idArbre="<%= CConstante.PAR_ARBREITERATION %>">

            <input <transfert:transfertchamp membre="setNom" type="" libelle="Nom de l\\'artefact en sortie" convertor="VStringConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
             type="hidden" value="<%= lTache.getArtefactSortie (lIndiceArtefactSortie).getNom () %>">
             
            <input <transfert:transfertchamp membre="setDescription" type="" libelle="Description de l\\'artefact en sortie" convertor="VStringConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
             type="hidden" value="<%= lTache.getArtefactSortie (lIndiceArtefactSortie).getDescription () %>">
             
            </transfert:transfertbean>
        <%
          }
        %>
      </transfert:transfertbean>
      <%
        }
      %>
      
      
      <td class="caseNiveau3">
        <select name="<%= CConstante.PAR_LISTETACHES %>" class="niveau2" style="width: 200" size="29"
         onchange="selectTache (document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTETACHES %>.selectedIndex)"
         onmouseover="tooltipOn(this, event, 'Liste des tâches actuellement définies pour l\'itération. Selectionnez-en une pour pouvoir la modifier.')" onmouseout="tooltipOff(this, event)">
          <%
            for (int lIndiceTache = 0; lIndiceTache < lIteration.getNbTaches (); lIndiceTache ++)
            {
          %>
          <option value="<%= lIndiceTache %>"> <%= lIteration.getTache (lIndiceTache).getNom () %> </option>
          <%    
            }
          %>
        </select>
      </td>
      
      
      
      <!-- Détail de la tâche -->
      <transfert:transfertbean scope="Session" type="owep.modele.execution.MTache" bean="getTache" idArbre="<%= CConstante.PAR_ARBRETACHES %>">
      <td class="caseNiveau3" valign="top" align="left" width="100%" rowspan="2">
        <table width="100%" cellpadding="0" cellspacing="0" valign="top">
          <tr>
            <td colspan="2">
              <p class="titre2"
              onmouseout="tooltipOff(this, event)" onmouseover="tooltipOn(this, event, '<%= messages.getString("aideIterationDetailTache") %>')">
                <%= messages.getString("tacheDetail") %> :</p>
            </td>        
          </tr>
          <tr>
            <td width="50%" class="caseNiveau3SansBordure">
              <a href="#" class="texte" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("aideChampObligatoire") %>', '<%= messages.getString("aideTacheNom") %>')" onmouseout="tooltipOff(this, event)">    
                <%= messages.getString("tacheNom") %> * :
              </a>
            </td>
            <td>
              <input <transfert:transfertchamp membre="setNom" type="java.lang.String" libelle="Nom" convertor="VStringConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBRETACHES %>"/>
               type="text" size="<%= CConstante.LNG_PETIT %>" class="niveau2" value="" maxlength="<%= CConstante.TXT_MOYEN %>">&nbsp;&nbsp;&nbsp;<input class="bouton" type="button" value="Réinitialiser" onclick="reinitialiserChamps () ;"><br/>
              <% lChampTacheNom = VTransfert.getDernierChamp () ; %>
            </td>
          </tr>
          <tr>
            <td class="caseNiveau3SansBordure">
              <a href="#" class="texte" onmouseover="tooltipOn(this, event, '<%= messages.getString("aideTacheDescription") %>')" onmouseout="tooltipOff(this, event)">
                <%= messages.getString("tacheDescription") %> :
              </a>
            </td>
            <td>
              <input <transfert:transfertchamp membre="setDescription" type="java.lang.String" libelle="Description" convertor="VStringConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBRETACHES %>"/>
               type="text" size="<%= CConstante.LNG_MOYEN %>" class="niveau2" value="" maxlength="<%= CConstante.TXT_LARGE %>"><br/>
              <% lChampTacheDescription = VTransfert.getDernierChamp () ; %>
            </td>
          </tr>
          <tr>
            <td class="caseNiveau3SansBordure">
              <a href="#" class="texte" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("aideChampObligatoire") %>', '<%= messages.getString("aideTacheCharge") %>')" onmouseout="tooltipOff(this, event)">
                <%= messages.getString("tacheChargeInitiale") %> * :
              </a>
            </td>
            <td>
              <input <transfert:transfertchamp membre="setChargeInitiale" type="java.lang.Double" libelle="Charge initiale" convertor="VDoubleConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBRETACHES %>"/>
               type="text" size="8" class="niveau2" value="" maxlength="<%= CConstante.TXT_CHARGE %>"><br/>
              <% lChampTacheChargeInitiale = VTransfert.getDernierChamp () ; %>
            </td>
          </tr>
          <tr>
            <td class="caseNiveau3SansBordure">
              <a href="#" class="texte" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("aideChampObligatoire") %>', '<%= messages.getString("aideTacheDateDebut") %>')" onmouseout="tooltipOff(this, event)">
                <%= messages.getString("tacheDateDebutPrevue") %> * :
              </a>
            </td>
            <td>
              <input <transfert:transfertchamp membre="setDateDebutPrevue" type="java.util.Date" libelle="Date de début prévue" convertor="VDateConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBRETACHES %>"/>
               type="text" size="8" class="niveau2" value="" maxlength="<%= CConstante.TXT_DATE %>"><br/>
              <% lChampTacheDateDebutPrevue = VTransfert.getDernierChamp () ; %>
            </td>
          </tr>
          <tr>
            <td class="caseNiveau3SansBordure">
              <a href="#" class="texte" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("aideChampObligatoire") %>', '<%= messages.getString("aideTacheDateFin") %>')" onmouseout="tooltipOff(this, event)">
                <%= messages.getString("tacheDateFinPrevue") %> * :
              </a>
            </td>
            <td>
              <input <transfert:transfertchamp membre="setDateFinPrevue" type="java.lang.Date" libelle="Date de fin prévue" convertor="VDateConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBRETACHES %>"/>
               type="text" size="8" class="niveau2" value="" maxlength="<%= CConstante.TXT_DATE %>"><br/>
              <% lChampTacheDateFinPrevue = VTransfert.getDernierChamp () ; %>
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
              <!-- Activités et collaborateurs -->
              <a href="#" class="texte" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("aideChampObligatoire") %>', '<%= messages.getString("aideTacheDiscipline") %>')" onmouseout="tooltipOff(this, event)">
                <%= messages.getString("tachetacheDiscipline") %> * :
              </a>
              <select class="niveau2" name="<%= CConstante.PAR_LISTEDISCIPLINES %>" onchange="selectDiscipline (document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex) ;
                                                                                              selectActivite (document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex, document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.selectedIndex);
                                                                                              selectProduit (document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex, document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.selectedIndex, document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEPRODUITS %>.selectedIndex);">
              <% 
                 int lDisciplineCourante = 0 ;
                 for (int i = 0; i < lProcessus.getNbComposants (); i++)             
                 {
                   MComposant lComposant = lProcessus.getComposant(i);
                   
                   for (int j = 0; j < lComposant.getNbDefinitionsTravail (); j++)
                   {
              %>
                <option value="<%= lDisciplineCourante ++ %>"> <%= lComposant.getDefinitionTravail (j).getNom () %> </option>
              <%   
                   } 
                }
              %>
              </select>
            </td>
            
            <td class="caseNiveau3SansBordure">
              <!-- Données de l'activité. -->
              <a href="#" class="texte" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("aideChampObligatoire") %>', '<%= messages.getString("aideTacheActivite") %>')" onmouseout="tooltipOff(this, event)">
                <%= messages.getString("tacheActivite") %> * :
              </a>
              <select class="niveau2" name="<%= CConstante.PAR_LISTEACTIVITES %>" onchange="selectActivite (document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex, document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.selectedIndex) ;
                                                                                            selectProduit (document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex, document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.selectedIndex, document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEPRODUITS %>.selectedIndex);">
              <%
                MDefinitionTravail lDefTravTmp = lProcessus.getComposant(0).getDefinitionTravail(0) ;
                for (int i = 0 ; i < lDefTravTmp.getNbActivites() ; i++)
                {
              %> 
                <option value="<%= lDefTravTmp.getActivite(i).getId() %>"> <%= lDefTravTmp.getActivite(i).getNom () %> </option>
              <%
                }
              %>
              </select>
            </td>
          </tr>
          <tr>
            <td colspan="2" class="caseNiveau3SansBordure">
              <a href="#" class="texte" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("aideChampObligatoire") %>', '<%= messages.getString("aideTacheCollaborateur") %>')" onmouseout="tooltipOff(this, event)">
                <%= messages.getString("tacheCollaborateurAffecte") %> * :
              </a>
              <select class="niveau2" name="<%= CConstante.PAR_LISTECOLLABORATEURS %>">
              <%
                MActivite lActiviteTmp = lProcessus.getComposant (0).getDefinitionTravail (0).getActivite (0) ;
                MCollaborateur lCollaborateurAct = null ;
                ArrayList lListeCollaborateurAct = new ArrayList () ;
                for (int i = 0; i < lActiviteTmp.getNbRoles (); i++) 
                {
                  MRole lRoleTmp = lActiviteTmp.getRole (i) ;
                  for (int j = 0 ; j < lRoleTmp.getNbCollaborateurs () ; j++)
                  {
                    lCollaborateurAct = lRoleTmp.getCollaborateur (j) ;
                    if (!lListeCollaborateurAct.contains (lCollaborateurAct))
                    {
                      lListeCollaborateurAct.add (lCollaborateurAct) ;
                    }
                  }
                }
                for (int i = 0 ; i < lListeCollaborateurAct.size () ; i++)
                {
                  lCollaborateurAct = (MCollaborateur) lListeCollaborateurAct.get (i) ;
              %>
              <option value="<%= lCollaborateurAct.getId () %>"> <%= lCollaborateurAct.getNom () %> &nbsp; <%= lCollaborateurAct.getPrenom () %> </option>
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
              <% lCodeValidation = "validerTacheAjout () ;" ; %>
              <% lCodeAdditionnel  = "onmouseover=\"tooltipOn(this, event, '" + messages.getString("aideTacheAjouter") + "')\" onmouseout=\"tooltipOff(this, event)\"" ; %>
              <transfert:transfertsubmit libelle="Ajouter" valeur="<%= CConstante.PAR_SUBMITAJOUTER %>" verification="true" validation="<%= lCodeValidation %>"
               additionnel="<%= lCodeAdditionnel %>"/>
        
              <% lCodeValidation = "validerTacheModif (document." + CConstante.PAR_FORMULAIRE + "." + CConstante.PAR_LISTETACHES + ", '" + messages.getString("tacheMessageAucuneSelect") + "') ;" ; %>
              <% lCodeAdditionnel  = "onmouseover=\"tooltipOn(this, event, '" + messages.getString("aideTacheModifier") + "')\" onmouseout=\"tooltipOff(this, event)\"" ; %>
              <transfert:transfertsubmit libelle="Modifier" valeur="<%= CConstante.PAR_SUBMITMODIFIER %>" verification="true" validation="<%= lCodeValidation %>"
               additionnel="<%= lCodeAdditionnel %>"/>
        
              <% lCodeValidation = "validerTacheSuppr (document." + CConstante.PAR_FORMULAIRE +"." + CConstante.PAR_LISTETACHES + ", '" + messages.getString("tacheMessageAucuneSelect") + "') ;" ; %>
              <% lCodeAdditionnel  = "onmouseover=\"tooltipOn(this, event, '" + messages.getString("aideTacheSupprimer") + "')\" onmouseout=\"tooltipOff(this, event)\"" ; %>
              <transfert:transfertsubmit libelle="Supprimer" valeur="<%= CConstante.PAR_SUBMITSUPPRIMER %>" verification="true" validation="<%= lCodeValidation %>"
               additionnel="<%= lCodeAdditionnel %>"/>
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
              
              <p class="titre2" onmouseout="tooltipOff(this, event)" onmouseover="tooltipOn(this, event, '<%= messages.getString("aideArtefactSortie") %>')">
                <%= messages.getString("artefactSortie") %> :
              </p>
            </td>
            <td>
              <p class="titre2" onmouseout="tooltipOff(this, event)" onmouseover="tooltipOn(this, event, '<%= messages.getString("aideArtefactEntree") %>')">
                <%= messages.getString("artefactEntree") %> :
              </p>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%" cellpadding="0" cellspacing="0" valign="top">
                <tr>       
                  <td colspan="2" align="center">
                    <select class="niveau2" name="<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>" style="width: 80%" onchange="selectArtefact (document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTETACHES %>.selectedIndex, document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>.selectedIndex)" size="4"
                    onmouseout="tooltipOff(this, event)" onmouseover="tooltipOn(this, event, '<%= messages.getString("aideArtefactSortieListe") %>')">
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
                    <a href="#" class="texte" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("aideChampObligatoire") %>', '<%= messages.getString("aideArtefactSortieProduit") %>')" onmouseout="tooltipOff(this, event)">
                      <%= messages.getString("artefactProduit") %> * :
                    </a>
                  </td>
                  <td>
                    <select class="niveau2" name="<%= CConstante.PAR_LISTEPRODUITS %>" onchange="selectProduit (document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex, document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.selectedIndex, document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEPRODUITS %>.selectedIndex)">
                    <%
                      MActivite lActivite = lProcessus.getComposant(0).getDefinitionTravail(0).getActivite (0) ;
                      for (int i = 0 ; i < lActivite.getNbProduitsSorties () ; i++)
                      {
                    %> 
                      <option value="<%= i %>"> <%= lActivite.getProduitSortie (i).getNom () %> </option>
                    <%
                      }
                    %>
                    </select>
                    <br/>
                  </td>
                </tr>
                <tr>
                  <td class="caseNiveau3SansBordure">
                    <a href="#" class="texte" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("aideChampObligatoire") %>', '<%= messages.getString("aideArtefactSortieResponsable") %>')" onmouseout="tooltipOff(this, event)">
                      <%= messages.getString("artefactResponsable") %> * :
                    </a>
                  </td>
                  <td>
                    <select class="niveau2" name="<%= CConstante.PAR_LISTERESPONSABLES %>">
                    <%
                      MRole lRole = lProcessus.getComposant(0).getDefinitionTravail(0).getActivite (0).getProduitSortie (0).getResponsable () ;
                      for (int i = 0 ; i < lRole.getNbCollaborateurs () ; i++)
                      {
                    %> 
                      <option value="<%= i %>"> <%= lRole.getCollaborateur (i).getNom () + " &nbsp; " + lRole.getCollaborateur (i).getPrenom () %> </option>
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
                    <% lCodeValidation = "validerArtefactSortie (document." + CConstante.PAR_FORMULAIRE +"." + CConstante.PAR_LISTETACHES + ", '" + messages.getString("tacheMessageAucuneSelect") + "' ) ;" ; %>
                    <% lCodeAdditionnel  = "onmouseover=\"tooltipOn(this, event, '" + messages.getString("aideArtefactSortieAjouter") + "')\" onmouseout=\"tooltipOff(this, event)\"" ; %>
                    <transfert:transfertsubmit libelle="<%= messages.getString("boutonAjouter") %>"   valeur="<%= CConstante.PAR_SUBMITAJOUTER_ARTSORTIES %>" verification="true" validation="<%= lCodeValidation %>"
                     additionnel="<%= lCodeAdditionnel %>"/>
                    
                    <% lCodeValidation = "validerArtefactSortie (document." + CConstante.PAR_FORMULAIRE + "." + CConstante.PAR_LISTEARTEFACTSSORTIES + ", '" + messages.getString("artefactSortieMessageAucunSelect") + "') ;" ; %>
                    <% lCodeAdditionnel  = "onmouseover=\"tooltipOn(this, event, '" + messages.getString("aideArtefactSortieModifier") + "')\" onmouseout=\"tooltipOff(this, event)\"" ; %>
                    <transfert:transfertsubmit libelle="<%= messages.getString("boutonModifier") %>"  valeur="<%= CConstante.PAR_SUBMITMODIFIER_ARTSORTIES %>" verification="true" validation="<%= lCodeValidation %>"
                     additionnel="<%= lCodeAdditionnel %>"/>
                    
                    <% lCodeValidation = "validerArtefactSortieSuppr (document." + CConstante.PAR_FORMULAIRE +"." + CConstante.PAR_LISTEARTEFACTSSORTIES + ", '" + messages.getString("artefactSortieMessageAucunSelect") + "') ;" ; %>
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
                      <%= messages.getString("artefactEntreePossibles") %> :</p>
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
                    <select class="niveau2" name="<%= CConstante.PAR_LISTEARTEFACTSPOSSIBLES %>" style="width: 90%" size="9">
                    </select>
                  </td>
                  <td align="center">       
                    <select class="niveau2" name="<%= CConstante.PAR_LISTEARTEFACTSENTREES %>" style="width: 90%" size="9">
                    </select>
                  </td>
                </tr>
                <tr>
                  <td align="center">
                    <% lCodeValidation = "validerArtefactEntree (document." + CConstante.PAR_FORMULAIRE + "." + CConstante.PAR_LISTEARTEFACTSPOSSIBLES + ", '" + messages.getString("artefactEntreeMessageAucunSelect") + "' ) ;" ; %>
                    <% lCodeAdditionnel  = "onmouseover=\"tooltipOn(this, event, '" + messages.getString("aideArtefactEntreeAjouter") + "')\" onmouseout=\"tooltipOff(this, event)\"" ; %>
                    <transfert:transfertsubmit libelle="<%= messages.getString("boutonAjouter") %>"  valeur="<%= CConstante.PAR_SUBMITAJOUTER_ARTENTREES %>" verification="true" validation="<%= lCodeValidation %>"
                     additionnel="<%= lCodeAdditionnel %>"/>
                  </td>
                  <td align="center">     
                    <% lCodeValidation = "validerArtefactEntreeSuppr (document." + CConstante.PAR_FORMULAIRE + "." + CConstante.PAR_LISTEARTEFACTSENTREES + ", '" + messages.getString("artefactEntreeMessageAucunSelect") + "' ) ;" ; %>
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
          <tr>
            <td>
              <p class="titre2" onmouseout="tooltipOff(this, event)" onmouseover="tooltipOn(this, event, '<%= messages.getString("aideTacheDependance") %>')">
                <%= messages.getString("tacheDepend") %> :
              </p>
              
              <!-- Liste des tâches dépendantes -->
              <table width="100%" height="100%" cellpadding="0" cellspacing="0" valign="top">
                <tr>
                  <td align="center">        
                    <select class="niveau2" name="<%= CConstante.PAR_LISTETACHESDEPENDANTES %>" style="width: 80%" size="4" onchange="selectTacheDependante (document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTETACHES%>.selectedIndex, document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTETACHESDEPENDANTES%>.selectedIndex)">
                    </select>
                  </td>
                </tr>
                <tr>
                  <td class="caseNiveau3SansBordure">
                    <a href="#" class="texte" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("aideChampObligatoire") %>', '<%= messages.getString("aideTacheDependanceSelect") %>')" onmouseout="tooltipOff(this, event)">        
                      <%= messages.getString("tachePossible") %> * : 
                    </a>
                    <select class="niveau2" name="<%= CConstante.PAR_LISTETACHESPOSSIBLES %>">
                      <%
                          for (int lIndiceTache = 0; lIndiceTache < lIteration.getNbTaches (); lIndiceTache ++)
                          {
                      %>
                            <option value="<%= lIndiceTache %>"> <%= lIteration.getTache (lIndiceTache).getNom () %> </option>
                      <%
                           }
                      %>
                    </select>
                  </td>
                </tr>
                <tr>
                  <td class="caseNiveau3SansBordure">
                    <a href="#" class="texte" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("aideChampObligatoire") %>', '<%= messages.getString("aideTacheDependanceEtat") %>')" onmouseout="tooltipOff(this, event)">
                      <%= messages.getString("condition") %> * : 
                    </a>  
                    <select class="niveau2" name="<%= CConstante.PAR_LISTETACHESCONDITION %>">
                      <option value="0"> <%= messages.getString("conditionEnCours") %> </option>
                      <option value="1"> <%= messages.getString("conditionTerminee") %> </option>
                    </select>
                  </td>
                  
                </tr>
                <tr>
                  <td align="center">
                    <% lCodeValidation = "validerTachesDep (document." + CConstante.PAR_FORMULAIRE +"." + CConstante.PAR_LISTETACHES + ", '" + messages.getString("tacheMessageAucuneSelect") + "' ) ;" ; %>
                    <% lCodeAdditionnel  = "onmouseover=\"tooltipOn(this, event, '" + messages.getString("aideTacheDependanceAjouter") + "')\" onmouseout=\"tooltipOff(this, event)\"" ; %>
                    <transfert:transfertsubmit libelle="<%= messages.getString("boutonAjouter") %>"  valeur="<%= CConstante.PAR_SUBMITAJOUTER_TACDEPEND %>" verification="true" validation="<%= lCodeValidation %>"
                     additionnel="<%= lCodeAdditionnel %>"/> 
                    <% lCodeValidation = "validerTachesDepSuppr (document." + CConstante.PAR_FORMULAIRE + "." + CConstante.PAR_LISTETACHESDEPENDANTES + ", '" + messages.getString("conditionMessageAucunSelect") + "' ) ;" ; %>   
                    <% lCodeAdditionnel  = "onmouseover=\"tooltipOn(this, event, '" + messages.getString("aideTacheDependanceSupprimer") + "')\" onmouseout=\"tooltipOff(this, event)\"" ; %>
                    <transfert:transfertsubmit libelle="<%= messages.getString("boutonSupprimer") %>"  valeur="<%= CConstante.PAR_SUBMITSUPPRIMER_TACDEPEND %>" verification="true" validation="<%= lCodeValidation %>"
                     additionnel="<%= lCodeAdditionnel %>"/>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr> 
      <!-- Barre d'outils de tâches -->
      <td class="caseNiveau3">
        </td>
    </tr>
  </tbody>
  </table>
  <p class="texteObligatoire"><%= messages.getString("champObligatoire") %></p>
    
  </transfert:transfertbean>

  <br>
  <p class="paragrapheSubmit">
    <% lCodeAdditionnel  = "onmouseover=\"tooltipOn(this, event, '" + messages.getString("aideIterationValider") + "')\" onmouseout=\"tooltipOff(this, event)\"" ; %>
    <transfert:transfertsubmit libelle="<%= messages.getString("boutonValider") %>" valeur="<%= CConstante.PAR_SUBMIT %>" verification="true" validation="validerFormulaire () ;"
     additionnel="<%= lCodeAdditionnel %>"/>
  </p>
  
</form>




<!-- Code javascript -->
<script type="text/javascript" language="JavaScript">

  <!----------------------------------------->
  <!-- Ensemble des données de l'itération -->
  <!----------------------------------------->
  var gListeTaches = new Array () ;
  <%
  // Parcours la liste des tâches.
  for (int lIndiceTache = 0; lIndiceTache < lIteration.getNbTaches (); lIndiceTache ++)
  {
    MTache    lTache         = lIteration.getTache (lIndiceTache) ;
    MActivite lTacheActivite = lTache.getActivite () ;
    int lIdActivite             = lTacheActivite.getId () ;
    int lIndiceDefTravailAbsolu = 0 ;
    int lIndiceActivite = 0 ;
    
    // Recherche l'activité correspondant à celle instanciée par la tâche courante.
    boolean lTrouve     = false ;
    for (int lIndiceComposant = 0; (! lTrouve) && (lIndiceComposant < lProcessus.getNbComposants ()); lIndiceComposant ++)             
    {
      MComposant lComposant = lProcessus.getComposant (lIndiceComposant) ;
    
      // Parcours la liste des définitions de travail.
      for (int lIndiceDefTravail = 0; (! lTrouve) && (lIndiceDefTravail < lComposant.getNbDefinitionsTravail ()); lIndiceDefTravail ++)
      {
        MDefinitionTravail lDefinitionTravail = lComposant.getDefinitionTravail (lIndiceDefTravail) ;
        
        // Parcours la liste des activités.
        for (lIndiceActivite = 0; (! lTrouve) && (lIndiceActivite < lDefinitionTravail.getNbActivites ()); lIndiceActivite ++) 
        {
          MActivite lActivite = lDefinitionTravail.getActivite (lIndiceActivite) ;
          
          // Si on trouve l'activité liée à la tâche est trouvée dans la définition de travail, on met leurs indices dans le tableau.
          if (lActivite.getId () == lIdActivite)
          {
            lTrouve = true ;
          }
        }
        
        lIndiceDefTravailAbsolu ++ ;
      }
    }
    
    // Rectifie les indices.
    lIndiceDefTravailAbsolu -- ;
    lIndiceActivite -- ;
    
    // Recherche le collaborateur correspondant à celui réalisant la tâche courante.
    int lIdCollaborateur = lTache.getCollaborateur ().getId () ;
    lTrouve = false ;
    //for (int lIndiceCollaborateur = 0; (! lTrouve) && (lIndiceCollaborateur < lProjet.getNbCollaborateurs ()); lIndiceCollaborateur ++) 
    //{
      //if (lProjet.getCollaborateur (lIndiceCollaborateur).getId () == lIdCollaborateur)
      //{
    ArrayList list = new ArrayList ();
    MActivite  act = lTache.getActivite ();
    for (int l = 0; l < act.getNbRoles(); l++)
    {
      MRole rol = act.getRole(l);
      for (int p = 0; p < rol.getNbCollaborateurs();p++)
      {
        if (!list.contains(rol.getCollaborateur(p)))
        {
          list.add(rol.getCollaborateur(p));
        }
      }
    }
    for (int lIndiceCollaborateur = 0; lIndiceCollaborateur < list.size(); lIndiceCollaborateur++)
    {
      if (lIdCollaborateur == ((MCollaborateur)list.get(lIndiceCollaborateur)).getId())
      {
  %>
        <!-- Données de la tâche. -->
        gListeTaches.push (new Array ("<%= lTache.getNom () %>", "<%=lTache.getDescription ()%>", "<%=lTache.getChargeInitiale () %>",
                                      "<%= VDateConvertor.getString (lTache.getDateDebutPrevue ()) %>", "<%= VDateConvertor.getString (lTache.getDateFinPrevue ()) %>",
                                      "<%= lIndiceDefTravailAbsolu %>", "<%= lIndiceActivite %>",
                                      "<%= lIndiceCollaborateur %>", new Array (), new Array(), new Array())) ;
  <%
      }
    }
    
    
    // Parcours la liste des artefacts en entrées.
    for (int lIndiceArtefactEntree = 0; lIndiceArtefactEntree < lTache.getNbArtefactsEntrees (); lIndiceArtefactEntree ++)
    {
      MArtefact lArtefactEntree = lTache.getArtefactEntree (lIndiceArtefactEntree) ;
  %>
      <!-- Données de l'artefact en entrée -->
      gListeTaches[<%= lIndiceTache %>][9].push (new Array ("<%= lTache.getArtefactEntree (lIndiceArtefactEntree).getId () %>", "<%= lTache.getArtefactEntree (lIndiceArtefactEntree).getNom () %>")) ;
  <%
    }
    
    // Parcours la liste des artefacts en sorties.
    for (int lIndiceArtefactSortie = 0; lIndiceArtefactSortie < lTache.getNbArtefactsSorties (); lIndiceArtefactSortie ++)
    {
      MArtefact      lArtefactSortie = lTache.getArtefactSortie (lIndiceArtefactSortie) ;
      MCollaborateur lResponsable    = lArtefactSortie.getResponsable () ;
      lTrouve = false ;
      
      // Parcours la liste des produits en sorties.
      for (int lIndiceProduitSortie  = 0; (! lTrouve) && (lIndiceProduitSortie < lTacheActivite.getNbProduitsSorties ()); lIndiceProduitSortie ++) 
      {
        MProduit lProduit         = lTacheActivite.getProduitSortie (lIndiceProduitSortie) ;
        MRole    lRoleResponsable = lProduit.getResponsable () ;
        int      lIndiceResponsable ;
        
        // Si on a trouvé le produit contenant l'artefact courant,
        if (lProduit.getId () == lArtefactSortie.getProduit ().getId ()) 
        {
          // Parcour l'ensemble des collaborateurs tenant le rôle responsable de l'artefact.
          for (lIndiceResponsable = 0; (! lTrouve) && (lIndiceResponsable < lRoleResponsable.getNbCollaborateurs ()); lIndiceResponsable ++)
          {
            MCollaborateur lCollaborateur = lRoleResponsable.getCollaborateur (lIndiceResponsable) ;
            
            if (lCollaborateur.getId () == lResponsable.getId ()) 
            {
              lTrouve = true ;
            }
          }
          
          // Rectifie les indices.
          lIndiceResponsable -- ;
  %>
          <!-- Données de l'artefact en sortie -->
          gListeTaches[<%= lIndiceTache %>][8].push (new Array ("<%= lIndiceArtefactSortie %>", "<%= lArtefactSortie.getNom () %>",
                                                                "<%= lArtefactSortie.getDescription () %>", "<%= lIndiceProduitSortie %>",
                                                                "<%= lIndiceResponsable %>")) ;
  <%
        }
      }
    }
    
    // Parcours des tâches dépendantes
    for (int lIndiceTacheDep = 0; lIndiceTacheDep < lTache.getNbConditions (); lIndiceTacheDep ++)
    {
      MCondition lCondition = lTache.getCondition (lIndiceTacheDep) ;
      MTache     lTacheDep  = lCondition.getTachePrecedente () ;
      int lIndiceEtat ;
      if (lCondition.getEtat () == 1) 
      {
        lIndiceEtat = 0 ;
      }
      else
      {
        lIndiceEtat = 1 ;
      }
   %>
        gListeTaches[<%= lIndiceTache %>][10].push (new Array ("<%= lIndiceTacheDep %>", "<%= lIteration.getListeTaches().indexOf(lTacheDep) %>", "<%= lIndiceEtat %>")) ; 
   <%   
    }
  }
  %>




  <!--------------------------------------->
  <!-- Ensemble des données du processus -->
  <!--------------------------------------->
  var gListeActivites = new Array () ;
  <%
  MComposant lComposant ;
  // Parcours la liste des composants.
  int lDisciplineCourante = 0 ;
  for (int lIndiceComposant = 0; lIndiceComposant < lProcessus.getNbComposants (); lIndiceComposant ++)
  {
    lComposant = lProcessus.getComposant(lIndiceComposant);
    
    // Parcours la liste des définition de travail.
    for (int lIndiceDefinitionTravail = 0; lIndiceDefinitionTravail < lComposant.getNbDefinitionsTravail (); lIndiceDefinitionTravail ++)
    {
      MDefinitionTravail lDefinitionTravail = lComposant.getDefinitionTravail (lIndiceDefinitionTravail) ;
  %>
      gListeActivites.push (new Array ()) ;
  <%
      for (int lIndiceActivite = 0; lIndiceActivite < lDefinitionTravail.getNbActivites (); lIndiceActivite ++)
      {
        MActivite lActivite = lComposant.getDefinitionTravail (lIndiceDefinitionTravail).getActivite(lIndiceActivite) ;
        ArrayList lListeRoleCollaborateur = new ArrayList () ; // Contient la liste des collaborateur pouvant participer à cette activité
  %>
        <!-- Données de l'activité -->
        gListeActivites[<%= lDisciplineCourante %>].push (new Array ("<%= lActivite.getId () %>", "<%= lActivite.getNom () %>", new Array (), new Array (), new Array ())) ;
  <%
        // Parcours des produits en sorties de l'activite.
        for (int lIndiceProduitSortie = 0; lIndiceProduitSortie < lActivite.getNbProduitsSorties(); lIndiceProduitSortie ++) 
        {
          MProduit lProduit = lActivite.getProduitSortie(lIndiceProduitSortie) ;
          MRole lRole = lProduit.getResponsable () ;
  %>
          <!-- Données du produit -->
          gListeActivites[<%= lDisciplineCourante %>][<%= lIndiceActivite %>][2].push (new Array ("<%= lIndiceProduitSortie %>", "<%= lProduit.getNom () %>", new Array ())) ;
  <%
          // Sauvegarde des collaborateurs suceptible d'être responsables du produits.
          for (int lIndiceCollaborateur = 0; lIndiceCollaborateur < lRole.getNbCollaborateurs (); lIndiceCollaborateur ++)
          {
            MCollaborateur lCollaborateur = lRole.getCollaborateur (lIndiceCollaborateur) ;
  %>
            <!-- Données du collaborateur -->
            gListeActivites[<%= lDisciplineCourante %>][<%= + lIndiceActivite %>][2][<%= lIndiceProduitSortie %>][2].push (new Array ("<%= lIndiceCollaborateur %>", "<%= lCollaborateur.getNom () %>", "<%= lCollaborateur.getPrenom () %>")) ;
  <%
          }
        }
        
        // Parcours la liste des artefact en entrées.
        for (int lIndiceProduitSortie = 0 ; lIndiceProduitSortie < lActivite.getNbProduitsEntrees (); lIndiceProduitSortie ++)
        {
          MProduit lProduitEntree = lActivite.getProduitEntree (lIndiceProduitSortie) ;
          
          for (int ll = 0 ; ll < lProduitEntree.getNbArtefacts (); ll ++)
          {
            MArtefact lArtEntreeTmp = lProduitEntree.getArtefact (ll) ;
  %>
            <!-- Données de l'artefact -->
            gListeActivites[<%= lDisciplineCourante %>][<%= lIndiceActivite %>][3].push (new Array ("<%= + lArtEntreeTmp.getId () %>", "<%= lArtEntreeTmp.getNom () %>")) ;
  <%
          }
        }
        
        // Parcours des rôles pouvant faire cette activité
        for (int lIndiceRole = 0 ;  lIndiceRole < lActivite.getNbRoles () ; lIndiceRole ++)
        {
          MRole lRole = lActivite.getRole (lIndiceRole) ;
          
          // Parcours de la liste des collaborateurs ayant ce rôle 
          for (int lIndiceRoleCollaborateur = 0 ; lIndiceRoleCollaborateur < lRole.getNbCollaborateurs () ; lIndiceRoleCollaborateur ++)
          {
            MCollaborateur lCollaborateurTmp = lRole.getCollaborateur (lIndiceRoleCollaborateur) ;
            if (!lListeRoleCollaborateur.contains (lCollaborateurTmp))
            {
              lListeRoleCollaborateur.add (lCollaborateurTmp) ;
            }
          }
        }
        for (int lIndiceListe = 0 ; lIndiceListe < lListeRoleCollaborateur.size () ; lIndiceListe ++)
        {
          MCollaborateur lCollaborateurTmp = (MCollaborateur) lListeRoleCollaborateur.get (lIndiceListe) ;
  %>
          <!-- Sauvegarde des collaborateurs pouvant faire la tâche -->
          gListeActivites[<%= lDisciplineCourante %>][<%= lIndiceActivite %>][4].push (new Array ("<%= lCollaborateurTmp.getId () %>", "<%= lCollaborateurTmp.getNom () %>", "<%= lCollaborateurTmp.getPrenom () %>")) ;
  <%
        }
      }
      
      lDisciplineCourante ++ ;
    }
  }
  %>
  
  
  
  
  <!------------------------------------------------------->
  <!-- Fonctions de gestion des composants du formulaire -->
  <!------------------------------------------------------->
  function selectTache(pIndice)
  {
    // Efface les ancienne données.
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>.length = 0 ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampArtefactNom %>.value = '' ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampArtefactDescription %>.value = '' ;
    
    // Données de la tâche.
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampTacheNom %>.value = gListeTaches[pIndice][0] ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampTacheDescription %>.value = gListeTaches[pIndice][1] ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampTacheChargeInitiale %>.value = gListeTaches[pIndice][2] ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampTacheDateDebutPrevue %>.value = gListeTaches[pIndice][3] ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampTacheDateFinPrevue %>.value = gListeTaches[pIndice][4] ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex = gListeTaches[pIndice][5] ;
    selectDiscipline (document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex) ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.selectedIndex = gListeTaches[pIndice][6] ;
    selectActivite (document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex, document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.selectedIndex) ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTECOLLABORATEURS %>.selectedIndex = gListeTaches[pIndice][7] ;
    
    
    // Initialise la liste des artefacts en sorties.
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>.length = 0 ;
    for (i = 0 ; i <  gListeTaches[pIndice][8].length ; i ++)
    {
      var option = new Option(gListeTaches [pIndice][8][i][1],gListeTaches [pIndice][8][i][0]) ;
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>.options[i] = option ;
    }
    
    // Initialise la liste des artefacts en entrées.
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSENTREES %>.length = 0 ;
    for (i = 0 ; i <  gListeTaches[pIndice][9].length ; i ++)
    {
      var option = new Option(gListeTaches [pIndice][9][i][1],gListeTaches [pIndice][9][i][0]) ;
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSENTREES %>.options[i] = option ;
    }
    
    // Bloque la sélection d'une activité ou discipline si un artefact en sortie est associé à la tâche.
    if (document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>.length > 0)
    {
      // Initialise la liste des artefacts et désactive les listes disciplines et activités.
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>.selectedIndex = 0 ;
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = true ;
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = true ;
      
      selectArtefact (document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTETACHES %>.selectedIndex, 0) ;
    }
    else
    // Bloque la sélection d'une activité ou discipline si un artefact en entrée est associé à la tâche.
    if (document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSENTREES %>.length > 0)
    {
      // Désactive les listes disciplines et activités.
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = true ;
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = true ;
    }
    else
    {
      // Active les listes disciplines et activités.
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = false ;
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = false ;
    }
    
    // Initialise la liste des tâches dépendantes
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTETACHESDEPENDANTES %>.length = 0 ;
    for (i = 0 ; i < gListeTaches[pIndice][10].length ; i ++)
    {
      indice = gListeTaches[pIndice][10][i][1] ;
      var option = new Option (gListeTaches[indice][0] , gListeTaches[pIndice][10][i][0]) ;
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTETACHESDEPENDANTES %>.options[i] = option ;
    }
    
  }
  
  
  function selectDiscipline (pIndice)
  {
    // Met à jour la liste des activités.
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.length = 0 ;
    for (i = 0 ; i <  gListeActivites[pIndice].length ; i++)
    {
      var option = new Option(gListeActivites [pIndice][i][1],gListeActivites [pIndice][i][0]) ;
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.options[i] = option ;
    }
  }
  
  
  function selectActivite (pIndiceDiscipline, pIndiceActivite)
  {
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEPRODUITS %>.length = 0 ;
    
    // Met à jour la liste des activités.
    for (lIndiceProduit = 0; lIndiceProduit <  gListeActivites[pIndiceDiscipline][pIndiceActivite][2].length; lIndiceProduit ++)
    {
      var option = new Option(gListeActivites [pIndiceDiscipline][pIndiceActivite][2][lIndiceProduit][1],
                              gListeActivites [pIndiceDiscipline][pIndiceActivite][2][lIndiceProduit][0]) ;
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEPRODUITS %>.options[lIndiceProduit] = option;
    }
    
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTECOLLABORATEURS %>.length = 0 ;
    // Met à jour la liste des collaborateurs
    for (lIndiceCollaborateur = 0; lIndiceCollaborateur < gListeActivites[pIndiceDiscipline][pIndiceActivite][4].length; lIndiceCollaborateur++)
    {
      var option = new Option(gListeActivites [pIndiceDiscipline][pIndiceActivite][4][lIndiceCollaborateur][1] + '   ' +
                              gListeActivites [pIndiceDiscipline][pIndiceActivite][4][lIndiceCollaborateur][2],
                              gListeActivites [pIndiceDiscipline][pIndiceActivite][4][lIndiceCollaborateur][0]) ;
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTECOLLABORATEURS %>.options[lIndiceCollaborateur] = option ;
    }
    
    // Met à jour la liste des artefacts.
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSPOSSIBLES %>.length = 0 ;          
    if (document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTETACHES %>.selectedIndex != -1)
    {
      // Cherche l'artefact dans la liste des artefacts affectés à la tâche.
      for (lIndiceArtefact = 0; lIndiceArtefact < gListeActivites[pIndiceDiscipline][pIndiceActivite][3].length; lIndiceArtefact++)
      {
        var lPresent = 0 ;
        
        for (lIndiceTache = 0; lIndiceTache <  gListeTaches[document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTETACHES %>.selectedIndex][9].length; lIndiceTache ++)
        {
          if (gListeActivites [pIndiceDiscipline][pIndiceActivite][3][lIndiceArtefact][0] == gListeTaches[document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTETACHES %>.selectedIndex][9][lIndiceTache][0])
          {
             lPresent = 1 ;
          }
        }
        
        // Ajoute l'artefact à la liste des artefacts possible s'il n'est pas associé à la tâche.
        if (lPresent == 0)
        {
          var option = new Option(gListeActivites [pIndiceDiscipline][pIndiceActivite][3][lIndiceArtefact][1],
                                  gListeActivites [pIndiceDiscipline][pIndiceActivite][3][lIndiceArtefact][0]) ; 
          document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSPOSSIBLES %>.options[document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSPOSSIBLES %>.length] = option ;
        }
      }
    }
    else
    {
      for (lIndiceArtefact = 0; lIndiceArtefact < gListeActivites[pIndiceDiscipline][pIndiceActivite][3].length; lIndiceArtefact ++)
      {
        var option = new Option(gListeActivites [pIndiceDiscipline][pIndiceActivite][3][lIndiceArtefact][1],
                                gListeActivites [pIndiceDiscipline][pIndiceActivite][3][lIndiceArtefact][0]) ;
        document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSPOSSIBLES %>.options[document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSPOSSIBLES %>.length] = option ;
      }
    }
  }
  
  
  function selectProduit (pIndiceDiscipline, pIndiceActivite, pIndiceProduit)
  {
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTERESPONSABLES %>.length = 0 ;
    for (lIndiceCollaborateur = 0; lIndiceCollaborateur <  gListeActivites[pIndiceDiscipline][pIndiceActivite][2][pIndiceProduit][2].length; lIndiceCollaborateur ++)
    { 
      var option = new Option(gListeActivites [pIndiceDiscipline][pIndiceActivite][2][pIndiceProduit][2][lIndiceCollaborateur][1] + '   ' +
                              gListeActivites [pIndiceDiscipline][pIndiceActivite][2][pIndiceProduit][2][lIndiceCollaborateur][2],
                              gListeActivites [pIndiceDiscipline][pIndiceActivite][2][pIndiceProduit][2][lIndiceCollaborateur][0]) ;
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTERESPONSABLES %>.options[lIndiceCollaborateur] = option ;
    }
  }
  
  
  function selectArtefact(pIndiceTache, pIndiceArtefact)
  {
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampArtefactNom %>.value = gListeTaches[pIndiceTache][8][pIndiceArtefact][1] ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampArtefactDescription %>.value = gListeTaches[pIndiceTache][8][pIndiceArtefact][2] ;
    selectActivite (document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex, document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.selectedIndex) ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEPRODUITS %>.selectedIndex = gListeTaches[pIndiceTache][8][pIndiceArtefact][3] ;
    selectProduit (document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex, document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.selectedIndex, document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEPRODUITS %>.selectedIndex) ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTERESPONSABLES %>.selectedIndex = gListeTaches[pIndiceTache][8][pIndiceArtefact][4] ;
  }
  
  
  function selectTacheDependante(pIndiceTache, pIndiceTacheDep)
  {
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTETACHESPOSSIBLES %>.selectedIndex = gListeTaches[pIndiceTache][10][pIndiceTacheDep][1] ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTETACHESCONDITION %>.selectedIndex = gListeTaches[pIndiceTache][10][pIndiceTacheDep][2] ;
    
  }
  
  function reinitialiserChamps () 
  {
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>.length = 0 ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampArtefactNom %>.value = '' ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampArtefactDescription %>.value = '' ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSPOSSIBLES %>.length = 0 ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEARTEFACTSENTREES %>.length = 0 ;
    
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampTacheNom %>.value = '' ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampTacheDescription %>.value = '' ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampTacheChargeInitiale %>.value = '' ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampTacheDateDebutPrevue %>.value = '' ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= lChampTacheDateFinPrevue %>.value = '' ;
    
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = false ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = false ;
  }
  <!------------------------------------------->
  <!-- Fonctions de validation du formulaire -->
  <!------------------------------------------->
  function validerTacheAjout ()
  {
    <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
    <%= VTransfertConstante.getVerification (CConstante.PAR_ARBRETACHES) %> () ;
    validerChamps () ;  
  }
  
  
  function validerTacheModif (pSelect, pMessage)
  {
    if (pSelect.selectedIndex == -1)
    {
      alert (pMessage) ;
    }
    else
    {
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = false ;
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = false ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBRETACHES) %> () ;
      validerChamps () ;
    }
  }
  
  
  function validerTacheSuppr (pSelect, pMessage)
  {
    if (pSelect.selectedIndex == -1)
    {
      alert (pMessage) ;
    }
    else
    {
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = false ;
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = false ;
      document.<%= CConstante.PAR_FORMULAIRE%>.submit () ;
    }
  }
  
  
  function validerArtefactSortie (pSelect, pMessage)
  {
    if (pSelect.selectedIndex == -1)
    {
      alert (pMessage) ;
    }
    else
    {
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = false ;
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = false ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREARTEFACTSORTIES) %> () ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBRETACHES) %> () ;
      validerChamps () ;
    }
  }
  

  function validerArtefactSortieSuppr (pSelect, pMessage)
  {
    if (pSelect.selectedIndex == -1)
    {
      alert (pMessage) ;
    }
    else
    {
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = false ;
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = false ;
      document.<%= CConstante.PAR_FORMULAIRE%>.submit () ;
    }
  }
  
  
  function validerArtefactEntree (pSelect, pMessage)
  {
    if (pSelect.selectedIndex == -1)
    {
      alert (pMessage) ;
    }
    else
    {
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = false ;
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = false ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBRETACHES) %> () ;
      validerChamps () ;
    }
  }
  
  
  function validerArtefactEntreeSuppr (pSelect, pMessage)
  {
    if (pSelect.selectedIndex == -1)
    {
      alert (pMessage) ;
    }
    else
    {
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = false ;
      document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = false ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBRETACHES) %> () ;
      validerChamps () ;
    }
  }
  
  
  function validerFormulaire ()
  {
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = false ;
    document.<%= CConstante.PAR_FORMULAIRE%>.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = false ;
    <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
    
    validerChamps () ;
  }


  function validerTachesDep (pSelect, pMessage)
  {
    if (pSelect.selectedIndex == -1)
    {
      alert (pMessage) ;
    }
    else
    {
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBRETACHES) %> () ;
      validerChamps () ;
    }
  }
  
  
  function validerTachesDepSuppr (pSelect, pMessage)
  {
    if (pSelect.selectedIndex == -1)
    {
      alert (pMessage) ;
    }
    else
    {
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBRETACHES) %> () ;
      validerChamps () ;
    }
  }

</script>


<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "<%= messages.getString("aideIterationPage") %>";
    
</script>