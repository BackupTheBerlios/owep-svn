<%@page import="java.text.SimpleDateFormat"%>
<%@page import="owep.controle.CConstante"%>
<%@page import="owep.modele.execution.MCollaborateur"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<jsp:useBean id="lCollaborateur" class="owep.modele.execution.MCollaborateur" scope="session"/>
<jsp:useBean id="lTache"         class="owep.modele.execution.MTache"         scope="session"/> 

<form action="./RapportActivite" method="post">
  <table class="tableau" border="0" cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td class="caseNiveau1" rowspan="2">Tâches</td>
      <td class="caseNiveau1" rowspan="2">Artefact</td>
      <td class="caseNiveau1" rowspan="2">Charge prévue</td>
      <td class="caseNiveau1" rowspan="2">Temps passé</td>
      <td class="caseNiveau1" rowspan="2">Reste à passer</td>
      <td class="caseNiveau1" rowspan="2">Etat</td>
      <td class="caseNiveau1" colspan="4">Date</td>
    </tr>
    <tr>
      <td class="caseNiveau1">début prévue</td>
      <td class="caseNiveau1">début réelle</td>
      <td class="caseNiveau1">fin prévue</td>
      <td class="caseNiveau1">fin réestimée</td>
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
            SimpleDateFormat lDateFormat = new SimpleDateFormat ("yyyy-MM-dd") ;
            out.print (lTache.getArtefactSortie (lTache.getNbArtefactSortie () - 1).getNom ()) ;
            for (int j = 0; j < lTache.getNbArtefactSortie () - 1; j ++)
            {
              out.print ("<br/>" + lTache.getArtefactSortie (j).getNom ()) ;
            }
          %>
        </td>
        
        <!-- Affiche les propriétés de la tâche -->
        <td class='caseNiveau3'><%=lTache.getChargeInitiale ()%></td>
        <td class='caseNiveau3'><input type=text size=1 name="<%=CConstante.PAR_TEMPSPASSE%><%=lTache.getId()%>"   value="<%=lTache.getTempsPasse()%>"></td>
        <td class='caseNiveau3'><input type=text size=1 name="<%=CConstante.PAR_RESTEAPASSER%><%=lTache.getId()%>" value="<%=lTache.getResteAPasser()%>"></td>
        <td class='caseNiveau3'><%=lTache.getEtat ()%></td>
        <td class='caseNiveau3'><%if (lTache.getDateDebutPrevue () != null)
                                  {
                                    out.print (lDateFormat.format (lTache.getDateDebutPrevue ())) ;
                                  }
                                  else
                                  {
                                    out.print ("X") ;
                                  } %>
        </td>
        <td class='caseNiveau3'><input type=text size=8 name="<%=CConstante.PAR_DATEDEBUTREELLE%><%=lTache.getId()%>"
                                 value="<%if (lTache.getDateDebutReelle () != null)
                                          {
                                            out.print (lTache.getDateDebutReelle ()) ;
                                          }%>">
        </td>
        <td class='caseNiveau3'><%if (lTache.getDateFinPrevue () != null)
                                  {
                                    out.print (lDateFormat.format (lTache.getDateFinPrevue ())) ;
                                  }
                                  else
                                  {
                                    out.print ("X") ;
                                  } %>
        </td>
        <td class='caseNiveau3'><input type=text size=8 name="<%=CConstante.PAR_DATEFINREELLE%><%=lTache.getId()%>"
                           value="<%if (lTache.getDateFinReelle () != null)
                                    {
                                      out.print (lTache.getDateFinReelle ()) ;
                                    }%>">
        </td>
      </tr>
    <%
      }
    %>
  </tbody>
  </table>
  <input type=hidden name="<%=CConstante.PAR_SUBMIT%>" value="true">
  <br><br>
  <font class="texteSubmit">
    <input type="submit" value="Valider">
  </font>
</form>