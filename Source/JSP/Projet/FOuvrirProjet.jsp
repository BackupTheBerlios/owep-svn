<%@page import="java.util.ArrayList"%>
<%@page import="owep.modele.execution.MProjet"%>

<jsp:useBean id="lProjet" class="owep.modele.execution.MProjet" scope="page"/>

<center>

<%
  // Récupération des paramétres
  ArrayList lListProjet = (ArrayList) request.getAttribute("listProjetPossible");       // Probleme rencontré lors de l'enregistrement du collaborateur
  
  int i;
  for(i = 0 ; i < lListProjet.size() ; i++)
  {
    lProjet = (MProjet) lListProjet.get(i);
%>
<a href="../Projet/OuvrirProjet?mIdProjet=<%=lProjet.getId()%>"><%=lProjet.getNom()%></a><br>
<%
  }
%>

</center>
