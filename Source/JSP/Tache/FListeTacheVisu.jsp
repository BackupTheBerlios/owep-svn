<%@page import="java.text.SimpleDateFormat"%>
<%@page import="owep.controle.CConstante"%>
<%@page import="owep.modele.execution.MCollaborateur"%>

<jsp:useBean id="lCollaborateur" class="owep.modele.execution.MCollaborateur" scope="page"/>
<jsp:useBean id="lTache"         class="owep.modele.execution.MTache"         scope="page"/> 

<table class="tableau" border="0" cellpadding="0" cellspacing="0">
<tbody>
  <tr>
    <td class="caseNiveau1" rowspan="2">T�ches</td>
    <td class="caseNiveau1" rowspan="2">Artefact</td>
    <td class="caseNiveau1" rowspan="2">Charge pr�vue</td>
    <td class="caseNiveau1" rowspan="2">Temps pass�</td>
    <td class="caseNiveau1" rowspan="2">Reste � passer</td>
    <td class="caseNiveau1" rowspan="2">Etat</td>
    <td class="caseNiveau1" colspan="4">Date</td>
    <td class="caseNiveau1" rowspan="2">% Avancement</td>
    <td class="caseNiveau1" rowspan="2">Budget consomm�</td>
    <td class="caseNiveau1" colspan="2">D�passement de charge</td>
  </tr>
  <tr>
    <td class="caseNiveau1">d�but pr�vue</td>
    <td class="caseNiveau1">d�but r�elle</td>
    <td class="caseNiveau1">fin pr�vue</td>
    <td class="caseNiveau1">fin r�estim�e</td>
    <td class="caseNiveau1">(%)</td>
    <td class="caseNiveau1">(h x j)</td>
  </tr>
  
  <%
    lCollaborateur = (MCollaborateur) request.getAttribute (CConstante.PAR_COLLABORATEUR) ;
    for (int i = 0; i < lCollaborateur.getNbTache (); i ++)
    {
      lTache = lCollaborateur.getTache (i) ;
  %>
    <tr>
      <td class='caseNiveau2'><%= lTache.getNom ()%></td>
  
      <!-- Affiche la liste des artefacts -->
      <td class='caseNiveau2'>
        <%
          SimpleDateFormat lDateFormat = new SimpleDateFormat ("dd/MM/yyyy") ;
          out.print (lTache.getArtefactSortie (lTache.getNbArtefactSortie () - 1).getNom ()) ;
          for (int j = 0; j < lTache.getNbArtefactSortie () - 1; j ++)
          {
            out.print ("<br/>" + lTache.getArtefactSortie (j).getNom ()) ;
          }
        %>
      </td>
      
      <!-- Affiche les propri�t�s de la t�che -->
      <td class='caseNiveau3'><%=lTache.getChargeInitiale ()%></td>
      <td class='caseNiveau3'><%=lTache.getTempsPasse ()    %></td>
      <td class='caseNiveau3'><%=lTache.getResteAPasser ()  %></td>
      <td class='caseNiveau3'><%=lTache.getEtat ()          %></td>
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
      <td class='caseNiveau3'><%= lTache.getPrcAvancement () * 100        %></td>
      <td class='caseNiveau3'><%= lTache.getBudgetConsomme () * 100       %></td>
      <td class='caseNiveau3'><%= lTache.getPrcDepassementCharge () * 100 %></td>
      <td class='caseNiveau3'><%= lTache.getHJDepassementCharge ()        %></td>
    </tr>
  <%
    }
  %>
</tbody>
</table>