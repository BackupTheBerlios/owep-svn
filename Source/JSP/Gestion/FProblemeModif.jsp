<%@page import="java.util.ResourceBundle"%>
<%@page import="owep.infrastructure.Session"%>
<%@page import="owep.controle.CConstante" %>
<%@page import="owep.modele.execution.MProbleme" %>
<%@page import="owep.modele.execution.MProjet" %>
<%@page import="owep.modele.execution.MIteration" %>
<%@page import="owep.modele.execution.MTache" %>
<%@page import="owep.modele.execution.MTacheImprevue" %>
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
  MProbleme pProbleme = (MProbleme) request.getAttribute (CConstante.PAR_PROBLEME) ;
  MProjet   pProjet   = (MProjet)   request.getAttribute (CConstante.PAR_PROJET) ;
%>

<center>
<form action="./ProblemeModif" method="post" name="<%= CConstante.PAR_FORMULAIRE %>">
  <transfert:transfertbean scope="Session" type="owep.modele.execution.MProbleme" bean="<%= CConstante.PAR_PROBLEME %>" idArbre="<%= CConstante.PAR_ARBREPROBLEME %>">
  <%
  if (pProbleme.getId () != 0)
  {
  %>
    <input name="<%= CConstante.PAR_PROBLEME %>" type="hidden" value="<%= pProbleme.getId () %>">
  <%
  }
  %>
   
  <table class="tableau" border="0" cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td class="caseNiveau1" width="20%">
        <a href="#" class="niveau1" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("champObligatoireAide") %>', '<%= messages.getString("problemeModifAideLibelle") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("problemeModifLibelle") %></a>
      </td>
      <td class="caseNiveau3" width="80%" colspan="3">
        <input <transfert:transfertchamp membre="setNom" type="java.lang.String" libelle="<%= messages.getString("problemeModifJsLibelle") %>" convertor="VStringConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREPROBLEME %>"/>
         class="niveau2" type="text" value="<%= VStringConvertor.getString (pProbleme.getNom (), true) %>" size="<%= CConstante.LNG_MOYEN %>" maxlength="<%= CConstante.TXT_MOYEN %>">
      </td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("problemeModifAideEtat") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("problemeModifEtat") %></a>
      </td>
      <td class="caseNiveau3" colspan="3">
        <input <transfert:transfertchamp membre="setEtat" type="java.lang.String" libelle="<%= messages.getString("problemeModifJsEtat") %>" convertor="VStringConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBREPROBLEME %>"/>
         class="niveau2" type="text" value="<%= VStringConvertor.getString (pProbleme.getEtat (), true) %>" size="<%= CConstante.LNG_MOYEN %>" maxlength="<%= CConstante.TXT_MOYEN %>">
      </td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("problemeModifJsDescription") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("problemeModifDescription") %></a>
      </td>
      <td class="caseNiveau3" colspan="3">
        <textarea  <transfert:transfertchamp membre="setDescription" type="java.lang.String" libelle="<%= messages.getString("problemeModifAideDescription") %>" convertor="VStringConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBREPROBLEME %>"/>
         class="niveau2" rows="<%= CConstante.LNG_ROWSCOMMENTAIRE %>" cols="<%= CConstante.LNG_COLSCOMMENTAIRE %>" maxlength="<%= CConstante.LNG_COMMENTAIRE %>"><%= VStringConvertor.getString (pProbleme.getDescription (), true) %></textarea>
      </td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("champObligatoireAide") %>', '<%= messages.getString("problemeModifAideDateDetection") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("problemeModifDateDetection") %></a>
      </td>
      <td class="caseNiveau3" colspan="3">
        <input <transfert:transfertchamp membre="setDateIdentification" type="java.lang.Date" libelle="<%= messages.getString("problemeModifJsDateDetection") %>" convertor="VDateConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREPROBLEME %>"/>
         class="niveau2" type="text" value="<%= VDateConvertor.getString (pProbleme.getDateIdentification (), true) %>" size="<%= CConstante.LNG_DATE %>" maxlength="<%= CConstante.TXT_DATE %>">
      </td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("problemeModifAideDateCloture") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("problemeModifDateCloture") %></a>
      </td>
      <td class="caseNiveau3" colspan="3">
        <input <transfert:transfertchamp membre="setDateCloture" type="java.lang.Date" libelle="<%= messages.getString("problemeModifJsDateCloture") %>" convertor="VDateConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBREPROBLEME %>"/>
         type="text" class="niveau2" value="<%= VDateConvertor.getString (pProbleme.getDateCloture (), true) %>" size="<%= CConstante.LNG_DATE %>" maxlength="<%= CConstante.TXT_DATE %>">
      </td>
    </tr>
    
    
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("champObligatoireAide") %>', '<%= messages.getString("problemeModifAideTacheOrigine") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("problemeModifTacheOrigine") %></a>
      </td>
      
      <td class="caseNiveau3" style="border-width : 0px 0px 1px 1px ;">
        <font class="titre3"><%= messages.getString("problemeModifTachePossibleOrigine") %></font><br/>
        <select name="pSelectTachesProvoquePossible" class="niveau2" style="width: 250" size="6"
         onmouseover="tooltipTitreOn(this, event, 'Champ obligatoire', '<%= messages.getString("problemeModifAideTachePossibleOrigine") %>')" onmouseout="tooltipOff(this, event)">
        <%
        // Parcours l'ensemble des tâches du projet.
        for (int lIndiceIteration = 0; lIndiceIteration < pProjet.getNbIterations (); lIndiceIteration ++)
        {
          MIteration lIteration = pProjet.getIteration (lIndiceIteration) ;
          
          // Affichage de la liste des tâches prévues.
          for (int lIndiceTache = 0; lIndiceTache < lIteration.getNbTaches (); lIndiceTache ++)
          {
            MTache lTache = lIteration.getTache (lIndiceTache) ;
            
            // Vérifie que la tâche n'appartient pas au problème.
            boolean lTrouve = false ;
            for (int lIndicePbTache = 0; lIndicePbTache < pProbleme.getNbTachesProvoque (); lIndicePbTache ++)
            {
              if (pProbleme.getTacheProvoque (lIndicePbTache).getId () == lTache.getId ())
              {
                lTrouve = true ;
              }
            }
            
            // Si la tâche n'appartient pas au probleme.
            if (! lTrouve)
            {
        %>
          <option value="p-<%= lTache.getId () %>"><%= lTache.getNom () %></option>
        <%
            }
          }
          
          // Affichage de la liste des tâches imprévues.
          for (int lIndiceTache = 0; lIndiceTache < lIteration.getNbTachesImprevues (); lIndiceTache ++)
          {
            MTacheImprevue lTache = lIteration.getTacheImprevue (lIndiceTache) ;
            
            // Vérifie que la tâche n'appartient pas au problème.
            boolean lTrouve = false ;
            for (int lIndicePbTache = 0; lIndicePbTache < pProbleme.getNbTachesImprevuesProvoque (); lIndicePbTache ++)
            {
              if (pProbleme.getTacheImprevueProvoque (lIndicePbTache).getId () == lTache.getId ())
              {
                lTrouve = true ;
              }
            }
            
            // Si la tâche n'appartient pas au probleme.
            if (! lTrouve)
            {
        %>
          <option value="i-<%= lTache.getId () %>"><%= lTache.getNom () %></option>
        <%
            }
          }
        }
        %>
        </select>
      </td>
      
      <td class="caseNiveau3" align="center" valign="middle" width="0" style="border-width : 0px 0px 1px 0px ;">
        <font class="titre3">&nbsp;</font><br/>
        <center>
          <input type="button" value="<%= messages.getString("problemeModifBtnAjouterTacheOrigine") %>" class="bouton" onclick="transfererItem (document.<%= CConstante.PAR_FORMULAIRE %>.pSelectTachesProvoquePossible, document.<%= CConstante.PAR_FORMULAIRE %>.pSelectTachesProvoque) ;"
           onmouseover="tooltipOn(this, event, '<%= messages.getString("problemeModifAideBtnAjouterTacheOrigine") %>')" onmouseout="tooltipOff(this, event)"/>
          <br/>
          <input type="button" value="<%= messages.getString("problemeModifBtnSupprimerTacheOrigine") %>" class="bouton" onclick="transfererItem (document.<%= CConstante.PAR_FORMULAIRE %>.pSelectTachesProvoque, document.<%= CConstante.PAR_FORMULAIRE %>.pSelectTachesProvoquePossible) ;"
           onmouseover="tooltipOn(this, event, '<%= messages.getString("problemeModifAideBtnSupprimerTacheOrigine") %>')" onmouseout="tooltipOff(this, event)"/>
        </center>
      </td>
      
      <td class="caseNiveau3" style="border-width : 0px 0px 1px 0px ;">
        <font class="titre3"><%= messages.getString("problemeModifTacheOrigineChoisie") %></font><br/>
        <select name="pSelectTachesProvoque" class="niveau2" style="width: 250" size="6"
         onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("champObligatoireAide") %>', '<%= messages.getString("problemeModifAideTacheOrigineChoisie") %>')" onmouseout="tooltipOff(this, event)">
        <%
        for (int lIndiceTache = 0; lIndiceTache < pProbleme.getNbTachesProvoque (); lIndiceTache ++)
        {
          MTache lTache = pProbleme.getTacheProvoque (lIndiceTache) ;
        %>
          <option value="p-<%= lTache.getId () %>"> <%= lTache.getNom () %> </option>
        <%
        }
        %>
        <%
        for (int lIndiceTache = 0; lIndiceTache < pProbleme.getNbTachesImprevuesProvoque (); lIndiceTache ++)
        {
          MTacheImprevue lTache = pProbleme.getTacheImprevueProvoque (lIndiceTache) ;
        %>
          <option value="i-<%= lTache.getId () %>"> <%= lTache.getNom () %> </option>
        <%
        }
        %>
        </select>
      </td>
    </tr>
    
    
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%= messages.getString("problemeModifAideTacheResolution") %>')" onmouseout="tooltipOff(this, event)"><%= messages.getString("problemeModifTacheResolution") %>
      </td>
      
      <td class="caseNiveau3" style="border-width : 0px 0px 1px 1px ;">
        <font class="titre3"><%= messages.getString("problemeModifTachePossibleResolution") %></font><br/>
        <select name="pSelectTachesResoutPossible" class="niveau2" style="width: 250" size="6"
         onmouseover="tooltipOn(this, event, '<%= messages.getString("problemeModifAideTachePossibleResolution") %>')" onmouseout="tooltipOff(this, event)">
        <%
        // Parcours l'ensemble des tâches du projet.
        for (int lIndiceIteration = 0; lIndiceIteration < pProjet.getNbIterations (); lIndiceIteration ++)
        {
          MIteration lIteration = pProjet.getIteration (lIndiceIteration) ;
          
          // Affichage de la liste des tâches prévues.
          for (int lIndiceTache = 0; lIndiceTache < lIteration.getNbTaches (); lIndiceTache ++)
          {
            MTache lTache = lIteration.getTache (lIndiceTache) ;
            
            // Vérifie que la tâche n'appartient pas au problème.
            boolean lTrouve = false ;
            for (int lIndicePbTache = 0; lIndicePbTache < pProbleme.getNbTachesResout (); lIndicePbTache ++)
            {
              if (pProbleme.getTacheResout (lIndicePbTache).getId () == lTache.getId ())
              {
                lTrouve = true ;
              }
            }
            
            // Si la tâche n'appartient pas au probleme.
            if (! lTrouve)
            {
        %>
          <option value="p-<%= lTache.getId () %>"> <%= lTache.getNom () %> </option>
        <%
            }
          }
          
          // Affichage de la liste des tâches imprévues.
          for (int lIndiceTache = 0; lIndiceTache < lIteration.getNbTachesImprevues (); lIndiceTache ++)
          {
            MTacheImprevue lTache = lIteration.getTacheImprevue (lIndiceTache) ;
            
            // Vérifie que la tâche n'appartient pas au problème.
            boolean lTrouve = false ;
            for (int lIndicePbTache = 0; lIndicePbTache < pProbleme.getNbTachesImprevuesResout (); lIndicePbTache ++)
            {
              if (pProbleme.getTacheImprevueResout (lIndicePbTache).getId () == lTache.getId ())
              {
                lTrouve = true ;
              }
            }
            
            // Si la tâche n'appartient pas au probleme.
            if (! lTrouve)
            {
        %>
          <option value="i-<%= lTache.getId () %>"> <%= lTache.getNom () %> </option>
        <%
            }
          }
        }
        %>
        </select>
      </td>
      
      <td class="caseNiveau3" align="center" valign="middle" width="0" style="border-width : 0px 0px 1px 0px ;">
        <font class="titre3">&nbsp;</font><br/>
        <center>
          <input type="button" value="<%= messages.getString("problemeModifBtnAjouterTacheResolution") %>" class="bouton" onclick="transfererItem (document.<%= CConstante.PAR_FORMULAIRE %>.pSelectTachesResoutPossible, document.<%= CConstante.PAR_FORMULAIRE %>.pSelectTachesResout)"
           onmouseover="tooltipOn(this, event, '<%= messages.getString("problemeModifAideBtnAjouterTacheResolution") %>')" onmouseout="tooltipOff(this, event)"/>
          <br/>
          <input type="button" value="<%= messages.getString("problemeModifBtnSupprimerTacheResolution") %>" class="bouton" onclick="transfererItem (document.<%= CConstante.PAR_FORMULAIRE %>.pSelectTachesResout, document.<%= CConstante.PAR_FORMULAIRE %>.pSelectTachesResoutPossible)"
           onmouseover="tooltipOn(this, event, '<%= messages.getString("problemeModifAideBtnSupprimerTacheResolution") %>')" onmouseout="tooltipOff(this, event)"/>
        </center>
      </td>
      
      <td class="caseNiveau3" style="border-width : 0px 0px 1px 0px ;">
        <font class="titre3"><%= messages.getString("problemeModifTacheResolutionChoisie") %></font><br/>
        <select name="pSelectTachesResout" class="niveau2" style="width: 250" size="6"
         onmouseover="tooltipTitreOn(this, event, '<%= messages.getString("champObligatoireAide") %>', '<%= messages.getString("problemeModifAideTacheResolutionChoisie") %>')" onmouseout="tooltipOff(this, event)">
        <%
        for (int lIndiceTache = 0; lIndiceTache < pProbleme.getNbTachesResout (); lIndiceTache ++)
        {
          MTache lTache = pProbleme.getTacheResout (lIndiceTache) ;
        %>
          <option value="p-<%= lTache.getId () %>"> <%= lTache.getNom () %> </option>
        <%
        }
        %>
        <%
        for (int lIndiceTache = 0; lIndiceTache < pProbleme.getNbTachesImprevuesResout (); lIndiceTache ++)
        {
          MTacheImprevue lTache = pProbleme.getTacheImprevueResout (lIndiceTache) ;
        %>
          <option value="i-<%= lTache.getId () %>"> <%= lTache.getNom () %> </option>
        <%
        }
        %>
        </select>
      </td>
    </tr>
  </tbody>
  </table>
  </transfert:transfertbean>
  
  <p class="texteObligatoire"><%= messages.getString("champObligatoire") %></p>
  <p class="texteSubmit">
    <input name="<%= CConstante.PAR_LISTETACHESPROVOQUE %>" type="hidden" value=""/>
    <input name="<%= CConstante.PAR_LISTETACHESRESOUT %>"   type="hidden" value=""/>
    
    <% lCodeValidation   = VTransfertConstante.getVerification (CConstante.PAR_ARBREPROBLEME)+ " () ;" ; %>
    <% lCodeValidation  += "isSelectVide (document." + CConstante.PAR_FORMULAIRE + ".pSelectTachesProvoque, '" + messages.getString("problemeModifJsTacheOrigine") + "') ;" ; %>
    <% lCodeValidation  += "submitListesTaches (pSelectTachesProvoque, " + CConstante.PAR_LISTETACHESPROVOQUE + ") ;" ; %>
    <% lCodeValidation  += "submitListesTaches (pSelectTachesResout, " + CConstante.PAR_LISTETACHESRESOUT + ") ;" ; %>
    <% lCodeValidation  += "validerChamps () ;" ; %>
    <% lCodeAdditionnel  = "onmouseover=\"tooltipOn(this, event, '" + messages.getString("problemeModifAideBtnValider") + "')\" onmouseout=\"tooltipOff(this, event)\"" ; %>
    <transfert:transfertsubmit libelle="<%= messages.getString("boutonValider") %>" valeur="<%= CConstante.PAR_SUBMIT %>" verification="true" validation="<%= lCodeValidation %>"
     additionnel="<%= lCodeAdditionnel %>"/>
    <input type="button" value="<%= messages.getString("boutonAnnuler") %>" class="bouton" onclick="window.location.href = '/owep/Gestion/ListeProblemeVisu' ;"
     onmouseover="tooltipOn(this, event, '<%= messages.getString("problemeModifAideBtnAnnuler") %>')" onmouseout="tooltipOff(this, event)"/>
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
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREPROBLEME) %> () ;
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
pCodeAide  = "<%= messages.getString("listeRisqueVisuAide") %>" ;
</script>
