<%@page import="java.text.SimpleDateFormat"%>
<%@page import="owep.controle.CConstante"%>
<%@page import="owep.modele.execution.MTache"%>

<jsp:useBean id="lTache"         class="owep.modele.execution.MTache"         scope="session"/> 
<HEAD>
<SCRIPT LANGUAGE="JavaScript">
  <!--
    // fonction de vérification du bon format de la date
    function test_date(date) {
      expr_reg = /^[0-9][0-9]\/[0-9][0-9]\/[0-9][0-9][0-9][0-9]$/ ;

      if ( expr_reg.test(date.value) ==0 ) 
      {
        // ce n'est pas une date valide
        alert ("La date que vous devez saisir doit être de la forme jj/mm/aaaa") ;
      }
    } 
    
    // fonction de vérification du bon format des heures
    function test_heure(heure) {
      expr_reg = /^[0-9]+$/ ;

      if ( expr_reg.test(heure.value) == 0 ) 
      {
        // ce n'est pas un nombre entier
        alert ("Vous devez saisir un nombre entier") ;
      }
    }  
    
    // fonction de conversion de la date saisie : jj/mm/aaaa en aaaa-mm-jj
    function valider() { 
      var madateReelle = document.formValidation.pDateDebutReel.value ; 
      var nouvelleDateReelle = madateReelle.split("\/"); 
      document.formValidation.pDateDebutReel.value = nouvelleDateReelle[2]+"-"+nouvelleDateReelle[1]+"-"+nouvelleDateReelle[0] ; 
      var madateReestimee = document.formValidation.pDateFinReestimee.value ; 
      var nouvelleDateReestimee = madateReestimee.split("\/"); 
      document.formValidation.pDateFinReestimee.value = nouvelleDateReestimee[2]+"-"+nouvelleDateReestimee[1]+"-"+nouvelleDateReestimee[0] ; 
    }
  // -->
  </SCRIPT>
  
  <NOSCRIPT>
  <B>Votre browser ne supporte pas JavaScript et ne peut donc pas exploiter
  les fonctionnalités de cette page Web</B>
  </NOSCRIPT>
</HEAD>

<form action="./ValidationRapport" method="post" name="formValidation">
  <table class="tableau" border="0" cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td class="caseNiveau1" rowspan="2">Nom de la tâche</td>
      <td class="caseNiveau1" rowspan="2">Artefacts</td>
      <td class="caseNiveau1" rowspan="2">Temps prévu(h)</td>
      <td class="caseNiveau1" rowspan="2">Temps passé(h)</td>
      <td class="caseNiveau1" rowspan="2">Reste à passer(h)</td>
      <td class="caseNiveau1" rowspan="2">Etat</td>
      <td class="caseNiveau1" colspan="4">Date</td>
    </tr>
    <tr>
      <td class="caseNiveau1">début prévue</td>
      <td class="caseNiveau1">début réelle</td>
      <td class="caseNiveau1">fin prévue</td>
      <td class="caseNiveau1">fin réestimée</td>
    </tr>
   
    <tr>
      <% lTache = (MTache) request.getAttribute (CConstante.PAR_TACHE) ; %>
	    <td class='caseNiveau2'><%= lTache.getNom ()%></td>
	    <!--
	    <!-- Affiche la liste des artefacts -->
	    <td class='caseNiveau2'>
	      <%
	        SimpleDateFormat lDateFormat = new SimpleDateFormat ("dd/MM/yyyy") ;
	        out.print (lTache.getArtefactSortie (0).getNom ()) ;
	        for (int j = 1; j < lTache.getNbArtefactsSorties (); j ++)
	        {
	          out.print ("<br/>" + lTache.getArtefactSortie (j).getNom ()) ;
	        }
	      %>
	    </td>
	    
	    <!-- Affiche les propriétés de la tâche -->
	    <td class='caseNiveau3'><%=(int)lTache.getChargeInitiale ()%></td>
	    <td class='caseNiveau3'><input type=text size=1 name="<%=CConstante.PAR_TEMPSPASSE%>"   value="<%=(int)lTache.getTempsPasse()%>" onBlur="test_heure(this)"></td>
	    <% int bouton = Integer.parseInt(request.getParameter("pIdBoutonClique")) ; 
	       if (bouton == 2)
	       {
	    %>
	    <td class='caseNiveau3'><input type=text size=1 name="<%=CConstante.PAR_RESTEAPASSER%>" value="<%=(int)lTache.getResteAPasser()%>" onBlur="test_heure(this)"></td>
	    <% } %>
      <% if (bouton == 3) 
        {
      %>
      <td class='caseNiveau3'><%=(int)lTache.getResteAPasser()%></td>
	    <% } %>
	    <% if (lTache.getEtat () == 2)
	     {
	     %>
	    <td class='caseNiveau3'>Suspendue</td>
	    <% } %>
      <% if (lTache.getEtat () == 3)
       {
       %>
      <td class='caseNiveau3'>Terminée</td>
      <% } %>
	    <td class='caseNiveau3'><%=lDateFormat.format (lTache.getDateDebutPrevue () )%></td>
	    <td class='caseNiveau3'><input type=text size=8 name="<%=CConstante.PAR_DATEDEBUTREELLE%>"
	                             value="<%=lDateFormat.format (lTache.getDateDebutReelle ()) %>" onBlur="test_date(this)">
	    </td>
	    <td class='caseNiveau3'><%=lDateFormat.format (lTache.getDateFinPrevue ())%></td>
	    <td class='caseNiveau3'><input type=text size=8 name="<%=CConstante.PAR_DATEFINREELLE%>" 
	                             value="<%=lDateFormat.format (lTache.getDateFinReelle())%>" onBlur="test_date(this)">
	    </td>
    </tr>
  </tbody>
  </table>
  <br><br>
  <input type=hidden name="<%=CConstante.PAR_TACHE%>" value=<%=lTache.getId()%>>
  <input type=hidden name="<%=CConstante.PAR_RESTEAPASSER%>" value=<%=lTache.getResteAPasser()%>>
  <input type=hidden name="<%=CConstante.PAR_ETAT%>" value=<%=lTache.getEtat()%>>
<center>
  <table border="0">
    <tr>
	    <td>
		    <font class="texteSubmit">
		    <input type="submit" value="Valider" OnClick="valider()">
		    </font>
	      </form>
      </td>
      <td>
	      <form action="./ListeTacheVisu" method="post">
	      <input type="submit" value="Annuler">
	      </form>
      </td>
    </tr>
  </table>
</center>
