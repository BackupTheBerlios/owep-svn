<%@page import="owep.controle.CConstante"%>
<%@page import="owep.modele.execution.MProjet"%>
<%@page import="owep.modele.execution.MMesureIndicateur"%>
<%@taglib uri='/WEB-INF/tld/transfert.tld' prefix='transfert' %>

<jsp:useBean id="lProjet" class="owep.modele.execution.MProjet" scope="page"/>

<% 
    //localisation
    java.util.ResourceBundle messages;
    messages = java.util.ResourceBundle.getBundle("MessagesBundle");
    lProjet  = (MProjet) request.getAttribute (CConstante.PAR_PROJET) ; 
%>
<HEAD>
<SCRIPT LANGUAGE="JavaScript">  
  <!--
    var gChampsInvalides = new String ('') ;
    var champValeur = new String ('') ; 
    
    function valider() { 
      
      expr_reg = /^[0-9]+$/ ;
      
      <%
      for (int i = 0; i < lProjet.getNbIndicateurs(); i ++)
	  {
      %>
        if (document.getElementById('<%=CConstante.PAR_VALEURMESURE+i%>') != null)
        {
          champValeur = document.getElementById('<%=CConstante.PAR_VALEURMESURE+i%>').value;
          if ( expr_reg.test(champValeur) == 0 ) 
          {
            gChampsInvalides += '<%=messages.getString("JSChamp")%> \'' + '<%=messages.getString("JSChampValeur")%>' + '\' <%=messages.getString("indicateursJSIndic")%> \'' + champValeur + '\' <%=messages.getString("JSIncorrect")%> ' + '<%=messages.getString("JSAlertEntier")%>. \n' ;
          }
        }
      <%
      }
      %>
      
      if (gChampsInvalides != '')
      {
        alert (gChampsInvalides) ;
        gChampsInvalides = '' ;
      }
      else
      {
        document.formValidation.submit () ;
      }  
    }
  // --> 
  </SCRIPT>
  
  <NOSCRIPT>
  <B><%=messages.getString("JSBrowser")%></B>
  </NOSCRIPT>
</HEAD>

<%
  if (lProjet.getNbIndicateurs()==0)
  {
%>
    <center><%=messages.getString("indicateursAucuneMesure")%></center>
<%
  }
  else
  {
%>
	<form action="./ValidationIndicateurs" method="post" name="formValidation">
	<center>
	<table class="tableau" border="0" cellpadding="0" cellspacing="0">
	<tbody>
	  <tr>
	    <td class="caseNiveau1">
	      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("indicateursAideMesure")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneMesure")%></a>
	    </td>
	    <td class="caseNiveau1">
	      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("indicateursAideDescription")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneDescription")%></a>
	    </td>
	    <td class="caseNiveau1">
	      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("indicateursAideValeur")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneValeur")%></a>
	    </td>
	    <td class="caseNiveau1">
	      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("indicateursAideUnite")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneUnite")%></a>
	    </td>
	    <td class="caseNiveau1">
	      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("indicateursAideCommentaire")%>')" onmouseout="tooltipOff(this, event)"><%=messages.getString("colonneCommentaires")%></a>
	    </td>
	  </tr>
	  
	  
	  <!--Affichage des taches en cours des collaborateurs-->
		<%
		// pour chaque collaborateur
		for (int i = 0; i < lProjet.getNbIndicateurs(); i ++)
		{
		  %>
		  <tr>      
	      <td class="caseNiveau2"><%=lProjet.getIndicateur(i).getNom()%></td>
	      <td class="caseNiveau3"><%=lProjet.getIndicateur(i).getDescription()%></td>
		    <%MMesureIndicateur lMesureIndicateur = (MMesureIndicateur)lProjet.getListe(new Integer(i)); %>
		    <%if (lProjet.getIndicateur(i).getUnite()==null)
		      {%>
          <td class="caseNiveau3">X</td>
          <td class="caseNiveau3">X</td>
          <input type=hidden name="<%=CConstante.PAR_TYPEINDICATEUR+i%>" value="commentaire">
        <%}
          else
          {
          %>
          <input type=hidden name="<%=CConstante.PAR_TYPEINDICATEUR+i%>" value="valeur">
          <td class="caseNiveau3"><input class="niveau2" type=text size=<%=CConstante.LNG_VALEUR%> name="<%=CConstante.PAR_VALEURMESURE+i%>"   value="<%=(int)lMesureIndicateur.getValeur()%>"></td>
		      <td class="caseNiveau3"><%=lProjet.getIndicateur(i).getUnite()%></td>
		    <%}%>
		    <%if (lMesureIndicateur.getCommentaire()==null){
		      lMesureIndicateur.setCommentaire("") ;
		    }%>
		    <td class="caseNiveau3"><TEXTAREA class="niveau2" COLS="<%=CConstante.LNG_COLSCOMMENTAIRE%>" WRAP="VIRTUAL" name="<%=CConstante.PAR_COMMENTAIREMESURE+i%>"><%=lMesureIndicateur.getCommentaire()%></TEXTAREA>
        </td>
		  </tr>
		  <%
		}
		%> 
	</tbody>
	</table>  </center>
	<BR>
	
	<center>
    <table border="0">
      <tr>
        <td>
          <input class="bouton" type="button" value="<%=messages.getString("boutonValider")%>" OnClick="valider()" onmouseover="tooltipOn(this, event, '<%=messages.getString("indicateursValider")%>')" onmouseout="tooltipOff(this, event)">
        </td>
        <td>
          <input type="button" value="<%=messages.getString("boutonAnnuler")%>" class="bouton" onclick="window.location.href = '/owep/Tache/ListeTacheVisu' ;" onmouseover="tooltipOn(this, event, '<%=messages.getString("indicateursAnnuler")%>')" onmouseout="tooltipOff(this, event)"/>
        </td>
      </tr>
    </table>
</center>

</form>
	
<%
  }
%>

<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "<%=messages.getString("indicateursAide")%>" ;
</script>
