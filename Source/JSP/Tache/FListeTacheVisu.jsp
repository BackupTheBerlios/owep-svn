<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="owep.controle.CConstante"%>
<%@page import="owep.modele.execution.MCollaborateur"%>
<%@page import="owep.modele.execution.MTache"%>
<%@page import="owep.modele.execution.MTacheImprevue"%>
<%@page import="owep.infrastructure.Session"%>

<jsp:useBean id="lSession" class="owep.infrastructure.Session" scope="page"/>
<jsp:useBean id="lCollaborateur" class="owep.modele.execution.MCollaborateur" scope="page"/>
<jsp:useBean id="lTache"         class="owep.modele.execution.MTache"         scope="page"/> 
<jsp:useBean id="lTacheImprevue" class="owep.modele.execution.MTacheImprevue" scope="page"/> 

<%
    //localisation
    java.util.ResourceBundle messages;
    messages = java.util.ResourceBundle.getBundle("MessagesBundle");
  
    // Recuperation de la session
    HttpSession httpSession = request.getSession(true);
    lSession = (Session) httpSession.getAttribute("SESSION");

    SimpleDateFormat lDateFormat = new SimpleDateFormat ("dd/MM/yyyy") ;
    lCollaborateur = (MCollaborateur) request.getAttribute (CConstante.PAR_COLLABORATEUR) ;
  if(lSession.getIteration() != null)
  {
    ArrayList listeTaches = (ArrayList)lCollaborateur.getListe(new Integer(0)) ;
    ArrayList listeTachesImprevues = (ArrayList)lCollaborateur.getListe(new Integer(1)) ;
    if(listeTaches.size()+listeTachesImprevues.size()>0)
    {
%>

<table class="tableau" border="0" cellpadding="0" cellspacing="0">
<tbody>
  <tr>
    <td class="caseNiveau1" rowspan="2">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("listeTacheAideTache")%>')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("colonneTache")%>
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
    <td class="caseNiveau1" colspan="2">
      <%=messages.getString("colonneDepassementCharges")%>
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
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("listeTacheAideDepassementPC")%>')" onmouseout="tooltipOff(this, event)">
        (%)
      </a>
    </td>
    <td class="caseNiveau1">
      <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=messages.getString("listeTacheAideDepassementH")%>')" onmouseout="tooltipOff(this, event)">
        (min)
      </a>
    </td>
  </tr>
  
  <%
    for (int i = 0; i < listeTaches.size(); i ++)
    {
      lTache = (MTache)listeTaches.get(i) ;
  %>
    <tr>
      <td class='caseNiveau2'><a href="/owep/Tache/TacheVisu?pTacheAVisualiser=<%= lTache.getId()%>"><%= lTache.getNom ()%></a></td>
  
      <!-- Affiche la liste des artefacts -->
            
      <!-- Affiche les propriétés de la tâche -->
      <td class='caseNiveau3'><%=(int)lTache.getChargeInitiale ()%></td>
      <td class='caseNiveau3'><%=(int)lTache.getTempsPasse ()    %></td>
      <td class='caseNiveau3'><%=(int)lTache.getResteAPasser ()  %></td>
      <!-- On passe l id du bouton cliqué et l id de la tache en parametre de la requete -->      
      <td class='caseNiveau3'>
      
      <!-- Si l'itération visualisée correspond à l'itération en cours -->
      <% if (lSession.getIteration().getEtat() >= 1) {%>
      
      <!-- Si le collaborateur n'a pas de taches en état démarré, on peut commencer ou reprendre n'importe quelle tâche -->  
      <% if(lCollaborateur.getTacheEnCours()==-1)
           {
            switch (lTache.getEtat())
             { 
               case -1 : %>
                 <%-- Pour etat créé --%>
                 <%=messages.getString("listeTacheTacheNonPrete")%>
            <%  break ;
               case 0 : %>
                 <%-- Affichage boutons pour etat non commencé --%>
                 <a href="/owep/Tache/Etat?pBoutonClique=1&<%=CConstante.PAR_TACHE%>=<%=lTache.getId()%>&<%=CConstante.PAR_TYPE_TACHE%>=<%=CConstante.TYPE_TACHE%>"><IMG  src="/owep/Image/Vide.gif" class="bouttonPlay"></a><IMG  src="/owep/Image/Vide.gif" class="bouttonPause"><IMG  src="/owep/Image/Vide.gif" class="bouttonStop">
            <%  break ; 
               case 1 : %>
                 <%-- Affichage boutons pour etat commencé --%>
                 <IMG  src="/owep/Image/Vide.gif" class="bouttonPlayActif"><a href="/owep/Tache/Etat?pBoutonClique=2&<%=CConstante.PAR_TACHE%>=<%=lTache.getId()%>&<%=CConstante.PAR_TYPE_TACHE%>=<%=CConstante.TYPE_TACHE%>"><IMG  src="/owep/Image/Vide.gif" class="bouttonPause"></a><a href="/owep/Tache/Etat?pBoutonClique=3&<%=CConstante.PAR_TACHE%>=<%=lTache.getId()%>&<%=CConstante.PAR_TYPE_TACHE%>=<%=CConstante.TYPE_TACHE%>"><IMG  src="/owep/Image/Vide.gif" class="bouttonStop"></a>
            <% break ; 
               case 2 : %>
                 <%-- Affichage boutons pour etat suspendu --%>
                 <a href="/owep/Tache/Etat?pBoutonClique=1&<%=CConstante.PAR_TACHE%>=<%=lTache.getId()%>&<%=CConstante.PAR_TYPE_TACHE%>=<%=CConstante.TYPE_TACHE%>"><IMG  src="/owep/Image/Vide.gif" class="bouttonPlay"></a><IMG  src="/owep/Image/Vide.gif" class="bouttonPauseActif"><IMG  src="/owep/Image/Vide.gif" class="bouttonStop">
            <% break ;
               case 3 : %>
                 <!-- Affichage boutons pour etat terminé -->
                 <%=messages.getString("listeTacheTacheTerminee")%>
            <!-- Si le collaborateur a une tache en état démarré, on ne peut modifier l'état que de cette tâche -->
            <% }
           } 
          else if(lCollaborateur.getTacheEnCours()!=-1)
           {
            switch (lTache.getEtat())
             { 
               case -1 : 
                 %>
                 <%-- Pour etat créé --%>
                 <%=messages.getString("listeTacheTacheNonPrete")%>
            <%  break ;
               case 0 : 
                 %>
                 <%-- Affichage boutons non clicables pour etat non commencé --%>
                 <IMG  src="/owep/Image/Vide.gif" class="bouttonPlay"><IMG  src="/owep/Image/Vide.gif" class="bouttonPause"><IMG  src="/owep/Image/Vide.gif" class="bouttonStop">
            <%   break ; 
               case 1 : 
                 %>
                 
									<%-- Affichage boutons pour etat commencé --%>
                 <IMG  src="/owep/Image/Vide.gif" class="bouttonPlayActif"><a href="/owep/Tache/Etat?pBoutonClique=2&<%=CConstante.PAR_TACHE%>=<%=lTache.getId()%>&<%=CConstante.PAR_TYPE_TACHE%>=<%=CConstante.TYPE_TACHE%>"><IMG  src="/owep/Image/Vide.gif" class="bouttonPause"></a><a href="/owep/Tache/Etat?pBoutonClique=3&<%=CConstante.PAR_TACHE%>=<%=lTache.getId()%>&<%=CConstante.PAR_TYPE_TACHE%>=<%=CConstante.TYPE_TACHE%>"><IMG  src="/owep/Image/Vide.gif" class="bouttonStop"></a>
               
            <%   break ;  
               case 2 : 
                 %>
                 <%-- Affichage boutons non clicables pour etat suspendu --%>
                 <IMG  src="/owep/Image/Vide.gif" class="bouttonPlay"><IMG  src="/owep/Image/Vide.gif" class="bouttonPauseActif"><IMG  src="/owep/Image/Vide.gif" class="bouttonStop">
            <%   break ; 
               case 3 : %>
                 <!-- Affichage boutons pour etat terminé -->
                 <%=messages.getString("listeTacheTacheTerminee")%>
          <% }
           } %>

		<!-- Si on ne visualise pas l'itération en cours -->
		<%} else {%>
		<%=messages.getString("listeTacheTacheNonPrete")%>
        <%}%>

      </td>
      <td class='caseNiveau3'><% if (lTache.getDateDebutPrevue () != null)
                                 {
                                   out.print (lDateFormat.format (lTache.getDateDebutPrevue ())) ;
                                 }
                                 else
                                 {
                                   out.print ("X") ;
                                 } %>
      </td>
      <td class='caseNiveau3'><% if (lTache.getDateDebutReelle () != null)
                                 {
                                   out.print (lDateFormat.format (lTache.getDateDebutReelle ())) ;
                                 }
                                 else
                                 {
                                   out.print ("X") ;
                                 } %>
      </td>
      <td class='caseNiveau3'><% if (lTache.getDateFinPrevue () != null)
                              {
                                out.print (lDateFormat.format (lTache.getDateFinPrevue ())) ;
                              }
                           else
                           {
                             out.print ("X") ;
                           } %>
      </td>
      <td class='caseNiveau3'><% if (lTache.getDateFinReelle () != null)
                                 {
                                   out.print (lDateFormat.format (lTache.getDateFinReelle ())) ;
                                 }
                                 else
                                 {
                                   out.print ("X") ;
                                 } %>
      </td>
      <td class='caseNiveau3'><%= (int)(lTache.getPrcDepassementCharge () * 100) %></td>
      <td class='caseNiveau3'><%= (int)lTache.getHJDepassementCharge ()        %></td>
    </tr>
  <%
    }
  %>

<%
    for (int i = 0; i < listeTachesImprevues.size(); i ++)
    {
      lTacheImprevue = (MTacheImprevue)listeTachesImprevues.get(i) ;
  %>
    <tr>
      <td class='caseNiveau2Italic'><a href="/owep/Tache/TacheVisu?pTacheImprevueAVisualiser=<%= lTacheImprevue.getId()%>"><%= lTacheImprevue.getNom ()%></a></td>
  
      <!-- Affiche la liste des artefacts -->
            
      <!-- Affiche les propriétés de la tâche -->
      <td class='caseNiveau3'><%=(int)lTacheImprevue.getChargeInitiale ()%></td>
      <td class='caseNiveau3'><%=(int)lTacheImprevue.getTempsPasse ()    %></td>
      <td class='caseNiveau3'><%=(int)lTacheImprevue.getResteAPasser ()  %></td>
      <!-- On passe l id du bouton cliqué et l id de la tache en parametre de la requete -->      
      <td class='caseNiveau3'>
      
      <!-- Si l'itération visualisée correspond à l'itération en cours -->
      <% if (lSession.getIteration().getEtat() >= 1) {%>
      
      <!-- Si le collaborateur n'a pas de taches en état démarré, on peut commencer ou reprendre n'importe quelle tâche -->  
      <% if(lCollaborateur.getTacheEnCours()==-1)
           {
            switch (lTacheImprevue.getEtat())
             { 
               case -1 : %>
                 <%-- Pour etat créé --%>
                 <%=messages.getString("listeTacheTacheNonPrete")%>
            <%  break ;
               case 0 : %>
                 <%-- Affichage boutons pour etat non commencé --%>
                 <a href="/owep/Tache/Etat?pBoutonClique=1&<%=CConstante.PAR_TACHE%>=<%=lTacheImprevue.getId()%>&<%=CConstante.PAR_TYPE_TACHE%>=<%=CConstante.TYPE_TACHE_IMPREVUE%>"><IMG  src="/owep/Image/Vide.gif" class="bouttonPlay"></a><IMG  src="/owep/Image/Vide.gif" class="bouttonPause"><IMG  src="/owep/Image/Vide.gif" class="bouttonStop">
            <%  break ; 
               case 1 : %>
                 <%-- Affichage boutons pour etat commencé --%>
                 <IMG  src="/owep/Image/Vide.gif" class="bouttonPlayActif"><a href="/owep/Tache/Etat?pBoutonClique=2&<%=CConstante.PAR_TACHE%>=<%=lTacheImprevue.getId()%>&<%=CConstante.PAR_TYPE_TACHE%>=<%=CConstante.TYPE_TACHE_IMPREVUE%>"><IMG  src="/owep/Image/Vide.gif" class="bouttonPause"></a><a href="/owep/Tache/Etat?pBoutonClique=3&<%=CConstante.PAR_TACHE%>=<%=lTacheImprevue.getId()%>&<%=CConstante.PAR_TYPE_TACHE%>=<%=CConstante.TYPE_TACHE_IMPREVUE%>"><IMG  src="/owep/Image/Vide.gif" class="bouttonStop"></a>
            <% break ; 
               case 2 : %>
                 <%-- Affichage boutons pour etat suspendu --%>
                 <a href="/owep/Tache/Etat?pBoutonClique=1&<%=CConstante.PAR_TACHE%>=<%=lTacheImprevue.getId()%>&<%=CConstante.PAR_TYPE_TACHE%>=<%=CConstante.TYPE_TACHE_IMPREVUE%>"><IMG  src="/owep/Image/Vide.gif" class="bouttonPlay"></a><IMG  src="/owep/Image/Vide.gif" class="bouttonPauseActif"><IMG  src="/owep/Image/Vide.gif" class="bouttonStop">
            <% break ;
               case 3 : %>
                 <!-- Affichage boutons pour etat terminé -->
                 <%=messages.getString("listeTacheTacheTerminee")%>
            <!-- Si le collaborateur a une tache en état démarré, on ne peut modifier l'état que de cette tâche -->
            <% }
           } 
          else if(lCollaborateur.getTacheEnCours()!=-1)
           {
            switch (lTacheImprevue.getEtat())
             { 
               case -1 : 
                 %>
                 <%-- Pour etat créé --%>
                 <%=messages.getString("listeTacheTacheNonPrete")%>
            <%  break ;
               case 0 : 
                 %>
                 <%-- Affichage boutons non clicables pour etat non commencé --%>
                 <IMG  src="/owep/Image/Vide.gif" class="bouttonPlay"><IMG  src="/owep/Image/Vide.gif" class="bouttonPause"><IMG  src="/owep/Image/Vide.gif" class="bouttonStop">
            <%   break ; 
               case 1 : 
                 %>
                 
									<%-- Affichage boutons pour etat commencé --%>
                 <IMG  src="/owep/Image/Vide.gif" class="bouttonPlayActif"><a href="/owep/Tache/Etat?pBoutonClique=2&<%=CConstante.PAR_TACHE%>=<%=lTacheImprevue.getId()%>&<%=CConstante.PAR_TYPE_TACHE%>=<%=CConstante.TYPE_TACHE_IMPREVUE%>"><IMG  src="/owep/Image/Vide.gif" class="bouttonPause"></a><a href="/owep/Tache/Etat?pBoutonClique=3&<%=CConstante.PAR_TACHE%>=<%=lTacheImprevue.getId()%>&<%=CConstante.PAR_TYPE_TACHE%>=<%=CConstante.TYPE_TACHE_IMPREVUE%>"><IMG  src="/owep/Image/Vide.gif" class="bouttonStop"></a>
               
            <%   break ;  
               case 2 : 
                 %>
                 <%-- Affichage boutons non clicables pour etat suspendu --%>
                 <IMG  src="/owep/Image/Vide.gif" class="bouttonPlay"><IMG  src="/owep/Image/Vide.gif" class="bouttonPauseActif"><IMG  src="/owep/Image/Vide.gif" class="bouttonStop">
            <%   break ; 
               case 3 : %>
                 <!-- Affichage boutons pour etat terminé -->
                 <%=messages.getString("listeTacheTacheTerminee")%>
          <% }
           } %>

		<!-- Si on ne visualise pas l'itération en cours -->
		<%} else {%>
		<%=messages.getString("listeTacheTacheNonPrete")%>
        <%}%>

      </td>
      <td class='caseNiveau3'><% if (lTacheImprevue.getDateDebutPrevue () != null)
                                 {
                                   out.print (lDateFormat.format (lTacheImprevue.getDateDebutPrevue ())) ;
                                 }
                                 else
                                 {
                                   out.print ("X") ;
                                 } %>
      </td>
      <td class='caseNiveau3'><% if (lTacheImprevue.getDateDebutReelle () != null)
                                 {
                                   out.print (lDateFormat.format (lTacheImprevue.getDateDebutReelle ())) ;
                                 }
                                 else
                                 {
                                   out.print ("X") ;
                                 } %>
      </td>
      <td class='caseNiveau3'><% if (lTacheImprevue.getDateFinPrevue () != null)
                              {
                                out.print (lDateFormat.format (lTacheImprevue.getDateFinPrevue ())) ;
                              }
                           else
                           {
                             out.print ("X") ;
                           } %>
      </td>
      <td class='caseNiveau3'><% if (lTacheImprevue.getDateFinReelle () != null)
                                 {
                                   out.print (lDateFormat.format (lTacheImprevue.getDateFinReelle ())) ;
                                 }
                                 else
                                 {
                                   out.print ("X") ;
                                 } %>
      </td>
      <td class='caseNiveau3'><%= (int)(lTacheImprevue.getPrcDepassementCharge () * 100) %></td>
      <td class='caseNiveau3'><%= (int)lTacheImprevue.getHJDepassementCharge ()        %></td>
    </tr>
  <%
    }
  %>
</tbody>
</table>

<%
    }
    else
    {    
%>
    <%=messages.getString("listeTacheAucuneTache")%>
<%
    }
  }
  else
  {
%>
    <a href="/owep/Processus/IterationModif"><B><%=messages.getString("listeTacheAjouterIteration")%></B></a>
<%
  }
%>


<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "<%=messages.getString("listeTacheAide")%>" ;
</script>
