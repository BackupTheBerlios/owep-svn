<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="owep.controle.CConstante"%>
<%@page import="owep.modele.execution.MTache"%>
<%@page import="owep.modele.execution.MTacheImprevue"%>

<jsp:useBean id="lTache"         class="owep.modele.execution.MTache"         scope="session"/> 
<jsp:useBean id="lTacheImprevue"         class="owep.modele.execution.MTacheImprevue" scope="session"/> 
<%
//localisation
    java.util.ResourceBundle messages;
    messages = java.util.ResourceBundle.getBundle("MessagesBundle");
%>
<% 
   String lTypeTache = (String) request.getAttribute (CConstante.PAR_TYPE_TACHE) ; 
   if (lTypeTache.equals("tache")) 
   { 
     lTache = (MTache) request.getAttribute (CConstante.PAR_TACHE) ; 
   }
   else
   {
     lTacheImprevue = (MTacheImprevue) request.getAttribute (CConstante.PAR_TACHE_IMPREVUE) ;
   } 
%>
<HEAD>
<SCRIPT LANGUAGE="JavaScript">
  <!--
    var gChampsInvalides = new String ('') ;
    var champDate = new String ('') ;
    var champHeure = new String ('') ; 
    var typeTache = new String ('<%=lTypeTache%>') ;
    var etatTache = new String ('');
    
    if (typeTache=='tache')
    {
      etatTache = new String ('<%=(Integer)lTache.getListe("etat")%>');
    }
    else
    {
      etatTache = new String ('<%=(Integer)lTacheImprevue.getListe("etat")%>');
    }
    
    // fonction de conversion de la date saisie : jj/mm/aaaa en aaaa-mm-jj
    function valider() { 
      
      expr_reg_heure = /^[0-9]+$/ ;
      expr_reg_date = /^[0-9][0-9]\/[0-9][0-9]\/[0-9][0-9][0-9][0-9]$/ ;
      
      champHeure = document.getElementById('<%=CConstante.PAR_TEMPSPASSE%>').value;

      if ( expr_reg_heure.test(champHeure) == 0 ) 
      {
        gChampsInvalides += '<%=messages.getString("JSChamp")%> \'' + '<%=messages.getString("JSChampTempsPasse")%>' + '\' <%=messages.getString("JSIncorrect")%> ' + '<%=messages.getString("JSAlertEntier")%>. \n' ;
      }

      if ((typeTache == 'tache' && etatTache=='2')||(typeTache == 'tacheImprevue' && etatTache=='2'))
	  {
        champHeure = document.getElementById('<%=CConstante.PAR_RESTEAPASSER%>').value;
        if ( expr_reg_heure.test(champHeure) == 0 ) 
        {
          gChampsInvalides += '<%=messages.getString("JSChamp")%> \'' + '<%=messages.getString("JSChampResteAPasse")%>' + '\' <%=messages.getString("JSIncorrect")%> ' + '<%=messages.getString("JSAlertEntier")%>. \n' ;
        }
      }

      champDate = document.getElementById('<%=CConstante.PAR_DATEDEBUTREELLE%>').value;
      if ( expr_reg_date.test(champDate) ==0 ) 
      {
        gChampsInvalides += '<%=messages.getString("JSChamp")%> \'' + '<%=messages.getString("JSChampDDR")%>' + '\' <%=messages.getString("JSIncorrect")%> ' + '<%=messages.getString("JSAlertDate")%>. \n' ;
      }
      
      champDate = document.getElementById('<%=CConstante.PAR_DATEFINREELLE%>').value;
      if ( expr_reg_date.test(champDate) ==0 ) 
      {
        gChampsInvalides += '<%=messages.getString("JSChamp")%> \'' + '<%=messages.getString("JSChampDFR")%>' + '\' <%=messages.getString("JSIncorrect")%> ' + '<%=messages.getString("JSAlertDate")%>. \n' ;
      }

      if (gChampsInvalides != '')
		  {
		    alert (gChampsInvalides) ;
		    gChampsInvalides = '' ;
		  }
		  else
		  {
        var madateReelle = document.formValidation.pDateDebutReel.value ; 
        var nouvelleDateReelle = madateReelle.split("\/"); 
        document.formValidation.pDateDebutReel.value = nouvelleDateReelle[2]+"-"+nouvelleDateReelle[1]+"-"+nouvelleDateReelle[0] ; 
        var madateReestimee = document.formValidation.pDateFinReestimee.value ; 
        var nouvelleDateReestimee = madateReestimee.split("\/"); 
        document.formValidation.pDateFinReestimee.value = nouvelleDateReestimee[2]+"-"+nouvelleDateReestimee[1]+"-"+nouvelleDateReestimee[0] ; 
        document.formValidation.submit () ;
      }  
    }
  // -->
  </SCRIPT>
  
  <NOSCRIPT>
  <B><%=messages.getString("JSBrowser")%></B>
  </NOSCRIPT>
</HEAD>

<form action="./ValidationRapport" method="post" name="formValidation">
  <table class="tableau" border="0" cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td class="caseNiveau1" rowspan="2">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("validationRapportAideTache")%>')" onmouseout="tooltipOff(this, event)">
          <%=messages.getString("colonneTache")%>
        </a>
      </td>
      <td class="caseNiveau1" rowspan="2">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("validationRapportAideArtefact")%>')" onmouseout="tooltipOff(this, event)">
          <%=messages.getString("colonneArtefact")%>
        </a>
      </td>
      <td class="caseNiveau1" rowspan="2">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("listeTacheAideTempsPrevu")%>')" onmouseout="tooltipOff(this, event)">
         <%=messages.getString("colonneTempsPrevu")%>
        </a>
      </td>
      <td class="caseNiveau1" rowspan="2">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("listeTacheAideTempsPasse")%>')" onmouseout="tooltipOff(this, event)">
          <%=messages.getString("colonneTempsPasse")%>
        </a>
      </td>
      <td class="caseNiveau1" rowspan="2">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("listeTacheAideResteAPasser")%>')" onmouseout="tooltipOff(this, event)">
          <%=messages.getString("colonneResteAPasser")%>
        </a>
      </td>
      <td class="caseNiveau1" rowspan="2">
	      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event,
	       '<%=messages.getString("listeTacheAideEtat")%>')" onmouseout="tooltipOff(this, event)">
	        <%=messages.getString("colonneEtat")%>
	      </a>
      </td>
      <td class="caseNiveau1" colspan="4">
        <%=messages.getString("colonneDate")%>
      </td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("listeTacheAideDebutPrevue")%>')" onmouseout="tooltipOff(this, event)">
          <%=messages.getString("colonneDebutPrevue")%>
        </a>
      </td>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("listeTacheAideDebutReelle")%>')" onmouseout="tooltipOff(this, event)">
          <%=messages.getString("colonneDebutReelle")%>
        </a>
      </td>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("listeTacheAideFinPrevue")%>')" onmouseout="tooltipOff(this, event)">
          <%=messages.getString("colonneFinPrevue")%>
        </a>
      </td>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("listeTacheAideFinReestmee")%>')" onmouseout="tooltipOff(this, event)">
          <%=messages.getString("colonneFinReestimee")%>
        </a>
      </td>
    </tr>
    
    <% 
      if (lTypeTache.equals("tache")) 
      { 
    %>
    
	    <tr>
	      <td class='caseNiveau2'><%= lTache.getNom ()%></td>
	      <!--
	      <!-- Affiche la liste des artefacts -->
	      <td class='caseNiveau2'>
	        <%
	           SimpleDateFormat lDateFormat = new SimpleDateFormat ("dd/MM/yyyy") ;
	          if (lTache.getNbArtefactsSorties()==0)
	          {
	            out.print ("X") ;
	          }
	          else
	          {
	            out.print (lTache.getArtefactSortie (0).getNom ()) ;
	            for (int j = 1; j < lTache.getNbArtefactsSorties (); j ++)
	            {
	              out.print ("<br/>" + lTache.getArtefactSortie (j).getNom ()) ;
	            }
	          }
	        %>
	      </td>
	      
	      <!-- Affiche les propriétés de la tâche -->
	      <td class='caseNiveau3'><%=(int)lTache.getChargeInitiale ()%></td>
	      <td class='caseNiveau3'><input class="niveau2" type=text size=<%=CConstante.LNG_CHARGE%> name="<%=CConstante.PAR_TEMPSPASSE%>"   value="<%=((Integer)lTache.getListe("tempsPasse")).intValue()%>"></td>
	      <% int bouton = Integer.parseInt(request.getParameter("pIdBoutonClique")) ; 
	         if (bouton == 2)
	         {
	      %>
	      <td class='caseNiveau3'><input class="niveau2" type=text size=<%=CConstante.LNG_CHARGE%> name="<%=CConstante.PAR_RESTEAPASSER%>" value="<%=((Double)lTache.getListe("resteAPasser")).intValue()%>"></td>
	      <% } %>
	      <% if (bouton == 3) 
	        {
	      %>
          <input type=hidden name="<%=CConstante.PAR_RESTEAPASSER%>" value="0">
	      <td class='caseNiveau3'><%=((Double)lTache.getListe("resteAPasser")).intValue()%></td>
          <%}%>
	      <% if (((Integer)lTache.getListe("etat")).intValue() == 2)
	       {
	       %>
	      <td class='caseNiveau3'><%=messages.getString("validationRapportTacheSuspendue")%></td>
	      <% } %>
	      <% if (((Integer)lTache.getListe("etat")).intValue() == 3)
	       {
	       %>
	      <td class='caseNiveau3'><%=messages.getString("listeTacheTacheTerminee")%></td>
	      <% } %>
	      <td class='caseNiveau3'><%=lDateFormat.format (lTache.getDateDebutPrevue () )%></td>
	      <td class='caseNiveau3'><input class="niveau2" type=text size=<%=CConstante.LNG_DATE%> name="<%=CConstante.PAR_DATEDEBUTREELLE%>"
	                               value="<%=lDateFormat.format (lTache.getDateDebutReelle ()) %>">
	      </td>
	      <td class='caseNiveau3'><%=lDateFormat.format (lTache.getDateFinPrevue ())%></td>
	      <td class='caseNiveau3'><input class="niveau2" type=text size=<%=CConstante.LNG_DATE%> name="<%=CConstante.PAR_DATEFINREELLE%>" 
	                               value="<%=lDateFormat.format ((Date)lTache.getListe("dateFinReelle"))%>">
	      </td>
	    </tr>
	    <input type=hidden name="<%=CConstante.PAR_TYPE_TACHE%>" value="tache">
	    <input type=hidden name="<%=CConstante.PAR_TACHE%>" value=<%=lTache.getId()%>>
        <input type=hidden name="<%=CConstante.PAR_ETAT%>" value=<%=((Integer)lTache.getListe("etat")).intValue()%>>
	  <%
	  }
	  else
	  {
	  %>
		<tr>
	      <td class='caseNiveau2'><%= lTacheImprevue.getNom ()%></td>
	      <!--
	      <!-- Affiche la liste des artefacts -->
	      <td class='caseNiveau2'>
	        <%
	          SimpleDateFormat lDateFormat = new SimpleDateFormat ("dd/MM/yyyy") ;
	          if (lTacheImprevue.getNbArtefactsImprevuesSorties()==0)
	          {
	            out.print ("X") ;
	          }
	          else
	          {
	            out.print (lTacheImprevue.getArtefactImprevueSortie (0).getNom ()) ;
	            for (int j = 1; j < lTacheImprevue.getNbArtefactsImprevuesSorties (); j ++)
	            {
	              out.print ("<br/>" + lTacheImprevue.getArtefactImprevueSortie (j).getNom ()) ;
	            }
	          }
	        %>
	      </td>
	      
	      <!-- Affiche les propriétés de la tâche -->
	      <td class='caseNiveau3'><%=(int)lTacheImprevue.getChargeInitiale ()%></td>
	      <td class='caseNiveau3'><input class="niveau2" type=text size=<%=CConstante.LNG_CHARGE%> name="<%=CConstante.PAR_TEMPSPASSE%>"   value="<%=((Integer)lTacheImprevue.getListe("tempsPasse")).intValue()%>"></td>
	      <% int bouton = Integer.parseInt(request.getParameter("pIdBoutonClique")) ; 
	         if (bouton == 2)
	         {
	      %>
	      <td class='caseNiveau3'><input class="niveau2" type=text size=<%=CConstante.LNG_CHARGE%> name="<%=CConstante.PAR_RESTEAPASSER%>" value="<%=((Double)lTacheImprevue.getListe("resteAPasser")).intValue()%>"></td>
	      <% } %>
	      <% if (bouton == 3) 
	        {
	      %>
          <input type=hidden name="<%=CConstante.PAR_RESTEAPASSER%>" value="0">
	      <td class='caseNiveau3'><%=((Double)lTacheImprevue.getListe("resteAPasser")).intValue()%></td>
	      <% } %>
	      <% if (((Integer)lTacheImprevue.getListe("etat")).intValue() == 2)
	       {
	       %>
	      <td class='caseNiveau3'><%=messages.getString("validationRapportTacheSuspendue")%></td>
	      <% } %>
	      <% if (((Integer)lTacheImprevue.getListe("etat")).intValue() == 3)
	       {
	       %>
	      <td class='caseNiveau3'><%=messages.getString("listeTacheTacheTerminee")%></td>
	      <% } %>
	      <td class='caseNiveau3'><%=lDateFormat.format (lTacheImprevue.getDateDebutPrevue () )%></td>
	      <td class='caseNiveau3'><input class="niveau2" type=text size=<%=CConstante.LNG_DATE%> name="<%=CConstante.PAR_DATEDEBUTREELLE%>"
	                               value="<%=lDateFormat.format (lTacheImprevue.getDateDebutReelle ()) %>">
	      </td>
	      <td class='caseNiveau3'><%=lDateFormat.format (lTacheImprevue.getDateFinPrevue ())%></td>
	      <td class='caseNiveau3'><input class="niveau2" type=text size=<%=CConstante.LNG_DATE%> name="<%=CConstante.PAR_DATEFINREELLE%>" 
	                               value="<%=lDateFormat.format ((Date)lTacheImprevue.getListe("dateFinReelle"))%>" onBlur="test_date(this, '<%=messages.getString("validationRapportDFReelle")%>')">
	      </td>
	    </tr>
	    <input type=hidden name="<%=CConstante.PAR_TYPE_TACHE%>" value="tacheImprevue">
	    <input type=hidden name="<%=CConstante.PAR_TACHE%>" value=<%=lTacheImprevue.getId()%>>
        <input type=hidden name="<%=CConstante.PAR_ETAT%>" value=<%=((Integer)lTacheImprevue.getListe("etat")).intValue()%>>
	  <%
	  }
	  %>
  </tbody>
  </table>
  <br><br>

<center>
    <table border="0">
      <tr>
        <td>
          <input class="bouton" type="button" value="<%=messages.getString("boutonValider")%>" OnClick="valider()" onmouseover="tooltipOn(this, event, '<%=messages.getString("validationRapportAideValider")%>')" onmouseout="tooltipOff(this, event)">
        </td>
        <td>
          <input type="button" value="<%=messages.getString("boutonAnnuler")%>" class="bouton" onclick="window.location.href = '/owep/Tache/ListeTacheVisu' ;" onmouseover="tooltipOn(this, event, '<%=messages.getString("validationRapportAideAnnuler")%>')" onmouseout="tooltipOff(this, event)"/>
        </td>
      </tr>
    </table>
</center>

</form>

<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "<%=messages.getString("validationRapportAide")%>" ;
</script>
