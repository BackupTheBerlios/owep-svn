<%@page import="java.text.SimpleDateFormat"%>
<%@page import="owep.controle.CConstante"%>
<%@page import="owep.modele.execution.MTache"%>
<%@page import="owep.modele.execution.MTacheImprevue"%>
<%@page import="owep.infrastructure.Session"%>

<jsp:useBean id="lSession"            class="owep.infrastructure.Session"               scope="page"/> 
<jsp:useBean id="lCondition"          class="owep.modele.execution.MCondition"          scope="page"/>
<jsp:useBean id="lTache"              class="owep.modele.execution.MTache"              scope="page"/> 
<jsp:useBean id="lArtefact"           class="owep.modele.execution.MArtefact"           scope="page"/> 
<jsp:useBean id="lTacheImprevue"      class="owep.modele.execution.MTacheImprevue"      scope="page"/> 
<jsp:useBean id="lArtefactImprevue"   class="owep.modele.execution.MArtefactImprevue"   scope="page"/> 

<%
    //localisation
    java.util.ResourceBundle messages;
    messages = java.util.ResourceBundle.getBundle("MessagesBundle");

    SimpleDateFormat lDateFormat = new SimpleDateFormat ("dd/MM/yyyy") ;
    if(request.getAttribute (CConstante.PAR_TACHE) != null)
    {   
      lTache = (MTache) request.getAttribute (CConstante.PAR_TACHE) ;
      lTacheImprevue = null ;
    }
    else
    { 
      lTacheImprevue = (MTacheImprevue) request.getAttribute (CConstante.PAR_TACHE_IMPREVUE) ;
      lTache = null ;
    }
    
    lSession = (Session) request.getAttribute (CConstante.SES_SESSION) ;
    
    String [] tabEtat = {"Créée", "Prète", "Commencée", "Suspendue", "Terminée"};

    String PATH_ARTEFACT = lSession.getConfiguration().getPathArtefact();
%>

<%
if(lTache != null){
%>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tbody>
<tr>
  <td width="50%">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
      <tr>
        <td width="30%" class="caseNiveau2SansBordure" onmouseover="tooltipOn(this, event, 'Temps nécessaire, estimé par le collaborateur, pour réaliser la tâche.')" onmouseout="tooltipOff(this, event)">
          <a href="#" class="niveau2" onmouseover="tooltipOn(this, event,'<%=messages.getString("tacheVisuAideEtat")%>')" onmouseout="tooltipOff(this, event)">
            <%=messages.getString("tacheVisuEtat")%> 
          </a>
        </td>
        <td align="left" class="caseNiveau3SansBordure">
          <%= tabEtat[lTache.getEtat()+1]%>
        </td>
      </tr>
      <%if (lTache.getDescription() != "" && lTache.getDescription() != null) {%>
      <tr>
        <td width="30%" class="caseNiveau2SansBordure">
          <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideDescription")%>' )" onmouseout="tooltipOff(this, event)">
            <%=messages.getString("tacheVisuDescription")%> 
          </a>
        </td>
        <td align="left" class="caseNiveau3SansBordure"><%= lTache.getDescription()%></td>
      </tr>
      <%}%>
    </tbody>
    </table> 
  </td>

  <%if (lTache.getNbConditions()>0 && lTache.getEtat()==-1){%>
  <td width="50%">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
      <tr>
        <td width="50%" valign="top" class="caseNiveau2SansBordure">
          <center><%=messages.getString("tacheVisuTableauCondition")%> </center><br>
        <table width="100%" class="tableau" border="0" cellpadding="0" cellspacing="0">
        <tbody>
          <tr>
            <td class="caseNiveau1">
              <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideTableauConditionTacheAttendueAide")%>')" onmouseout="tooltipOff(this, event)">
                <%=messages.getString("tacheVisuTableauConditionTacheAttendue")%> 
              </a>
            </td>
            <td class="caseNiveau1">
              <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideTableauConditionEtatAide")%>')" onmouseout="tooltipOff(this, event)">
                <%=messages.getString("tacheVisuTableauConditionEtat")%> 
              </a>
            </td>
            <td class="caseNiveau1">
              <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideTableauConditionConditionAide")%>')" onmouseout="tooltipOff(this, event)">
                <%=messages.getString("tacheVisuTableauConditionCondition")%> 
              </a>
            </td>
          </tr>
          <%for (int i = 0; i < lTache.getNbConditions(); i++)
            {
              lCondition = lTache.getCondition(i) ;
          %>
            <tr>
              <td class='caseNiveau2'><%= lCondition.getTachePrecedente().getNom()%></td>
              <td class='caseNiveau2'><%= tabEtat[lCondition.getEtat()]%></td>
              <td class='caseNiveau2'><%= tabEtat[lCondition.getTachePrecedente().getEtat()]%></td>
            </tr>
          <%}%>
        </tbody>
        </table>
        </td>  
      </tr>
    </tbody>
    </table> 
  </td>
  <%}%> 
</tr>

</tbody>
</table> 
 
<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tbody>
  <tr>
    <td width="15%" class="caseNiveau2SansBordure">
      <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideTempsPrevuAide")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("tacheVisuTempsPrevu")%> 
      </a>
    </td>
    <td width="20%" class="caseNiveau3SansBordure">
      <%=lTache.getChargeInitiale ()%>
    </td>
    <td width="15%" class="caseNiveau2SansBordure">
      <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideTempsPasseAide")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("tacheVisuTempsPasse")%> 
      </a>
    </td>
    <td width="20%" class="caseNiveau3SansBordure">
      <%=lTache.getTempsPasse ()    %>
    </td>
    <td width="15%" class="caseNiveau2SansBordure">
      <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideResteAPasserAide")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("tacheVisuResteAPasser")%> 
      </a>
    </td>
    <td width="15%" class="caseNiveau3SansBordure">
      <%=lTache.getResteAPasser ()  %>
    </td>
  </tr>
</tbody>
</table>

<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tbody>
  <tr>
    <td colspan="4" class="caseNiveau2SansBordure">
      <%=messages.getString("tacheVisuDate")%> 
    </td>
    <td colspan="2" class="caseNiveau2SansBordure">
      <%=messages.getString("tacheVisuCharge")%> 
    </td>
  </tr>
  <tr>
    <td width="15%" class="caseNiveau2SansBordure">
      <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideDateDebutPrevue")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("tacheVisuDateDebutPrevue")%> 
      </a>
    </td>
    <td width="20%" class="caseNiveau3SansBordure"><% if (lTache.getDateDebutPrevue () != null)
           {
             out.print (lDateFormat.format (lTache.getDateDebutPrevue ())) ;
           }
           else
           {
             out.print ("X") ;
           } %>
    </td>
    <td width="15%" class="caseNiveau2SansBordure">
      <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideDateDebutReelle")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("tacheVisuDateDebutReelle")%> 
      </a>
    </td>
    <td width="20%" class="caseNiveau3SansBordure"><% if (lTache.getDateDebutReelle () != null)
           {
             out.print (lDateFormat.format (lTache.getDateDebutReelle ())) ;
           }
           else
           {
             out.print ("X") ;
           } %>
    </td>
    <td width="15%" class="caseNiveau2SansBordure">
      <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideChargePourcentage")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("tacheVisuChargePourcentage")%> 
      </a>
    </td>
    <td width="20%" class="caseNiveau3SansBordure">
      <%= lTache.getPrcDepassementCharge () * 100 %>
    </td>
  </tr>
  <tr>
    <td width="15%" class="caseNiveau2SansBordure">
      <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideDateFinPrevue")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("tacheVisuDateFinPrevue")%> 
      </a>
    </td>
    <td width="20%" class="caseNiveau3SansBordure">
      <% if (lTache.getDateFinPrevue () != null)
         {
           out.print (lDateFormat.format (lTache.getDateFinPrevue ())) ;
         }
         else
         {
           out.print ("X") ;
         }
       %>
    </td>
    <td width="15%" class="caseNiveau2SansBordure">
      <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideDateFinReestimee")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("tacheVisuDateFinReestimee")%> 
      </a>
    </td>
    <td width="20%" class="caseNiveau3SansBordure"><% if (lTache.getDateFinReelle () != null)
           {
             out.print (lDateFormat.format (lTache.getDateFinReelle ())) ;
           }
           else
           {
             out.print ("X") ;
           } %>
    </td>
    <td width="15%" class="caseNiveau2SansBordure">
      <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideChargeHommeJour")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("tacheVisuChargeHommeJour")%> 
      </a>
    </td>
    <td width="20%" class="caseNiveau3SansBordure">
      <%= lTache.getHJDepassementCharge () %>
    </td>
  </tr>
</tbody>
</table>

<br><br>

<table width="100%">
  <tr>
<%if (lTache.getNbArtefactsEntrees()>0){%>
  <td width="50%" valign="top" class="caseNiveau2SansBordure">
    <center><%=messages.getString("tacheVisuTableauArtefactEntree")%> </center><br>
  <table width="100%" class="tableau" border="0" cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td class="caseNiveau1"><%=messages.getString("tacheVisuTableauArtefactEntreeArtefact")%> </td>
      <td class="caseNiveau1"><%=messages.getString("tacheVisuTableauArtefactEntreeDisponibilite")%> </td>
      <td class="caseNiveau1"><%=messages.getString("tacheVisuTableauArtefactEntreeResponsable")%> </td>
    </tr>
    <%for (int i = 0; i < lTache.getNbArtefactsEntrees(); i ++)
      {
        lArtefact = lTache.getArtefactEntree(i) ;  
    %>
      <tr>
        <td class='caseNiveau2'><%= lArtefact.getNom()%></td>
        <%if (lArtefact.getNomFichier() != null){%>
          <td class='caseNiveau2'><a href=<%= "/owep/"+PATH_ARTEFACT+lArtefact.getPathFichier()+lArtefact.getNomFichier()%>><%= lArtefact.getNomFichier()%></a></td>
        <%}else{%>
            <td class='caseNiveau2'><%=messages.getString("tacheVisuTableauArtefactEntreeNonDisponible")%> </td>
          <%}%>
        <td class='caseNiveau2'><%= lArtefact.getResponsable().getPrenom()+" "+lArtefact.getResponsable().getNom()%></td>
      </tr>
    <%}%>
  </tbody>
  </table>
  </td>  
<%}%>

<%if (lTache.getNbArtefactsSorties()>0){%>
  <td width="50%" valign="top" class="caseNiveau2SansBordure">
    <center><%=messages.getString("tacheVisuTableauArtefactSortie")%> </center><br>
  <table width="100%" class="tableau" border="0" cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td class="caseNiveau1"><%=messages.getString("tacheVisuTableauArtefactSortieArtefact")%> </td>
      <td class="caseNiveau1"><%=messages.getString("tacheVisuTableauArtefactSortieDisponibilite")%> </td>
      <td class="caseNiveau1"><%=messages.getString("tacheVisuTableauArtefactSortieUpload")%> </td>
    </tr>
    <%for (int i = 0; i < lTache.getNbArtefactsSorties(); i ++)
      {
        lArtefact = lTache.getArtefactSortie(i) ;
    %>
      <tr>
        <td class='caseNiveau2'><%= lArtefact.getNom()%></td>
        <%if (lArtefact.getNomFichier() != null){%>
          <td class='caseNiveau2'><a href=<%= "/owep/"+PATH_ARTEFACT+lArtefact.getPathFichier()+lArtefact.getNomFichier()%>><%= lArtefact.getNomFichier()%></a></td>
        <%}else{%>
            <td class='caseNiveau2'><%=messages.getString("tacheVisuTableauArtefactSortieNonDisponible")%> </td>
          <%}%>
          <td class='caseNiveau2'><a href=<%= "/owep/Artefact/ArtefactAjout?pArtefact="+ Integer.toString(lArtefact.getId())+"&pTacheAVisualiser="+lTache.getId()%>><%= "Ajouter"%></a></td>
      </tr>
    <%}%>
  </tbody>
  </table>
  </td>
<%}%>

  </tr>
  
</table>

<%/********************************Taches imprévues********************************/%>

<%} else {%>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tbody>
<tr>
  <td width="50%">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
      <tr>
        <td width="30%" class="caseNiveau2SansBordure" onmouseover="tooltipOn(this, event, 'Temps nécessaire, estimé par le collaborateur, pour réaliser la tâche.')" onmouseout="tooltipOff(this, event)">
          <a href="#" class="niveau2" onmouseover="tooltipOn(this, event,'<%=messages.getString("tacheVisuAideEtat")%>')" onmouseout="tooltipOff(this, event)">
            <%=messages.getString("tacheVisuEtat")%> 
          </a>
        </td>
        <td align="left" class="caseNiveau3SansBordure">
          <%= tabEtat[lTacheImprevue.getEtat()+1]%>
        </td>
      </tr>
      <%if (lTacheImprevue.getDescription() != "" && lTacheImprevue.getDescription() != null) {%>
      <tr>
        <td width="30%" class="caseNiveau2SansBordure">
          <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideDescription")%>' )" onmouseout="tooltipOff(this, event)">
            <%=messages.getString("tacheVisuDescription")%> 
          </a>
        </td>
        <td align="left" class="caseNiveau3SansBordure"><%= lTacheImprevue.getDescription()%></td>
      </tr>
      <%}%>
    </tbody>
    </table> 
  </td>

  <%if (lTacheImprevue.getNbConditions()>0 && lTacheImprevue.getEtat()==-1){%>
  <td width="50%">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
      <tr>
        <td width="50%" valign="top" class="caseNiveau2SansBordure">
          <center><%=messages.getString("tacheVisuTableauCondition")%> </center><br>
        <table width="100%" class="tableau" border="0" cellpadding="0" cellspacing="0">
        <tbody>
          <tr>
            <td class="caseNiveau1">
              <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideTableauConditionTacheAttendueAide")%>')" onmouseout="tooltipOff(this, event)">
                <%=messages.getString("tacheVisuTableauConditionTacheAttendue")%> 
              </a>
            </td>
            <td class="caseNiveau1">
              <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideTableauConditionEtatAide")%>')" onmouseout="tooltipOff(this, event)">
                <%=messages.getString("tacheVisuTableauConditionEtat")%> 
              </a>
            </td>
            <td class="caseNiveau1">
              <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideTableauConditionConditionAide")%>')" onmouseout="tooltipOff(this, event)">
                <%=messages.getString("tacheVisuTableauConditionCondition")%> 
              </a>
            </td>
          </tr>
          <%for (int i = 0; i < lTacheImprevue.getNbConditions(); i++)
            {
              lCondition = lTacheImprevue.getCondition(i) ;
          %>
            <tr>
              <td class='caseNiveau2'><%= lCondition.getTachePrecedente().getNom()%></td>
              <td class='caseNiveau2'><%= tabEtat[lCondition.getEtat()]%></td>
              <td class='caseNiveau2'><%= tabEtat[lCondition.getTachePrecedente().getEtat()]%></td>
            </tr>
          <%}%>
        </tbody>
        </table>
        </td>  
      </tr>
    </tbody>
    </table> 
  </td>
  <%}%> 
</tr>

</tbody>
</table> 
 
<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tbody>
  <tr>
    <td width="15%" class="caseNiveau2SansBordure">
      <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideTempsPrevuAide")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("tacheVisuTempsPrevu")%> 
      </a>
    </td>
    <td width="20%" class="caseNiveau3SansBordure">
      <%=lTacheImprevue.getChargeInitiale ()%>
    </td>
    <td width="15%" class="caseNiveau2SansBordure">
      <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideTempsPasseAide")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("tacheVisuTempsPasse")%> 
      </a>
    </td>
    <td width="20%" class="caseNiveau3SansBordure">
      <%=lTacheImprevue.getTempsPasse ()    %>
    </td>
    <td width="15%" class="caseNiveau2SansBordure">
      <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideResteAPasserAide")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("tacheVisuResteAPasser")%> 
      </a>
    </td>
    <td width="15%" class="caseNiveau3SansBordure">
      <%=lTacheImprevue.getResteAPasser ()  %>
    </td>
  </tr>
</tbody>
</table>

<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tbody>
  <tr>
    <td colspan="4" class="caseNiveau2SansBordure">
      <%=messages.getString("tacheVisuDate")%> 
    </td>
    <td colspan="2" class="caseNiveau2SansBordure">
      <%=messages.getString("tacheVisuCharge")%> 
    </td>
  </tr>
  <tr>
    <td width="15%" class="caseNiveau2SansBordure">
      <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideDateDebutPrevue")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("tacheVisuDateDebutPrevue")%> 
      </a>
    </td>
    <td width="20%" class="caseNiveau3SansBordure"><% if (lTacheImprevue.getDateDebutPrevue () != null)
           {
             out.print (lDateFormat.format (lTacheImprevue.getDateDebutPrevue ())) ;
           }
           else
           {
             out.print ("X") ;
           } %>
    </td>
    <td width="15%" class="caseNiveau2SansBordure">
      <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideDateDebutReelle")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("tacheVisuDateDebutReelle")%> 
      </a>
    </td>
    <td width="20%" class="caseNiveau3SansBordure"><% if (lTacheImprevue.getDateDebutReelle () != null)
           {
             out.print (lDateFormat.format (lTacheImprevue.getDateDebutReelle ())) ;
           }
           else
           {
             out.print ("X") ;
           } %>
    </td>
    <td width="15%" class="caseNiveau2SansBordure">
      <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideChargePourcentage")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("tacheVisuChargePourcentage")%> 
      </a>
    </td>
    <td width="20%" class="caseNiveau3SansBordure">
      <%= lTacheImprevue.getPrcDepassementCharge () * 100 %>
    </td>
  </tr>
  <tr>
    <td width="15%" class="caseNiveau2SansBordure">
      <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideDateFinPrevue")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("tacheVisuDateFinPrevue")%> 
      </a>
    </td>
    <td width="20%" class="caseNiveau3SansBordure">
      <% if (lTacheImprevue.getDateFinPrevue () != null)
         {
           out.print (lDateFormat.format (lTacheImprevue.getDateFinPrevue ())) ;
         }
         else
         {
           out.print ("X") ;
         }
       %>
    </td>
    <td width="15%" class="caseNiveau2SansBordure">
      <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideDateFinReestimee")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("tacheVisuDateFinReestimee")%> 
      </a>
    </td>
    <td width="20%" class="caseNiveau3SansBordure"><% if (lTacheImprevue.getDateFinReelle () != null)
           {
             out.print (lDateFormat.format (lTacheImprevue.getDateFinReelle ())) ;
           }
           else
           {
             out.print ("X") ;
           } %>
    </td>
    <td width="15%" class="caseNiveau2SansBordure">
      <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, '<%=messages.getString("tacheVisuAideChargeHommeJour")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("tacheVisuChargeHommeJour")%> 
      </a>
    </td>
    <td width="20%" class="caseNiveau3SansBordure">
      <%= lTacheImprevue.getHJDepassementCharge () %>
    </td>
  </tr>
</tbody>
</table>

<br><br>

<table width="100%">
  <tr>
<%if (lTacheImprevue.getNbArtefactsImprevuesEntrees()>0){%>
  <td width="50%" valign="top" class="caseNiveau2SansBordure">
    <center><%=messages.getString("tacheVisuTableauArtefactEntree")%> </center><br>
  <table width="100%" class="tableau" border="0" cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td class="caseNiveau1"><%=messages.getString("tacheVisuTableauArtefactEntreeArtefact")%> </td>
      <td class="caseNiveau1"><%=messages.getString("tacheVisuTableauArtefactEntreeDisponibilite")%> </td>
      <td class="caseNiveau1"><%=messages.getString("tacheVisuTableauArtefactEntreeResponsable")%> </td>
    </tr>
    <%for (int i = 0; i < lTacheImprevue.getNbArtefactsImprevuesEntrees(); i ++)
      {
        lArtefactImprevue = lTacheImprevue.getArtefactImprevueEntree(i) ;  
    %>
      <tr>
        <td class='caseNiveau2'><%= lArtefactImprevue.getNom()%></td>
        <%if (lArtefactImprevue.getNomFichier() != null){%>
          <td class='caseNiveau2'><a href=<%= "/owep/"+PATH_ARTEFACT+lArtefactImprevue.getPathFichier()+lArtefactImprevue.getNomFichier()%>><%= lArtefactImprevue.getNomFichier()%></a></td>
        <%}else{%>
            <td class='caseNiveau2'><%=messages.getString("tacheVisuTableauArtefactEntreeNonDisponible")%> </td>
          <%}%>
        <td class='caseNiveau2'><%= lArtefactImprevue.getResponsable().getPrenom()+" "+lArtefactImprevue.getResponsable().getNom()%></td>
      </tr>
    <%}%>
  </tbody>
  </table>
  </td>  
<%}%>

<%if (lTacheImprevue.getNbArtefactsImprevuesSorties()>0){%>
  <td width="50%" valign="top" class="caseNiveau2SansBordure">
    <center><%=messages.getString("tacheVisuTableauArtefactSortie")%> </center><br>
  <table width="100%" class="tableau" border="0" cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td class="caseNiveau1"><%=messages.getString("tacheVisuTableauArtefactSortieArtefact")%> </td>
      <td class="caseNiveau1"><%=messages.getString("tacheVisuTableauArtefactSortieDisponibilite")%> </td>
      <td class="caseNiveau1"><%=messages.getString("tacheVisuTableauArtefactSortieUpload")%> </td>
    </tr>
    <%for (int i = 0; i < lTacheImprevue.getNbArtefactsImprevuesSorties(); i ++)
      {
        lArtefactImprevue = lTacheImprevue.getArtefactImprevueSortie(i) ;
    %>
      <tr>
        <td class='caseNiveau2'><%= lArtefactImprevue.getNom()%></td>
        <%if (lArtefactImprevue.getNomFichier() != null){%>
          <td class='caseNiveau2'><a href=<%= "/owep/"+PATH_ARTEFACT+lArtefactImprevue.getPathFichier()+lArtefactImprevue.getNomFichier()%>><%= lArtefactImprevue.getNomFichier()%></a></td>
        <%}else{%>
            <td class='caseNiveau2'><%=messages.getString("tacheVisuTableauArtefactSortieNonDisponible")%> </td>
          <%}%>
          <td class='caseNiveau2'><a href=<%= "/owep/Artefact/ArtefactAjout?pArtefactImprevu="+ Integer.toString(lArtefactImprevue.getId())+"&pTacheImprevueAVisualiser="+lTacheImprevue.getId()%>><%= "Ajouter"%></a></td>
      </tr>
    <%}%>
  </tbody>
  </table>
  </td>
<%}%>

  </tr>
  
</table>

<%}%>

<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = " <%= messages.getString("tacheVisuAide") %> " ;
</script>
