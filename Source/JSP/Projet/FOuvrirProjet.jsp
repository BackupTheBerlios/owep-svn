<%@page import="java.util.ArrayList"%>
<%@page import="owep.modele.execution.MProjet"%>

<jsp:useBean id="lProjet" class="owep.modele.execution.MProjet" scope="page"/>

<center>

<%
  // R�cup�ration des param�tres
  ArrayList lListProjet = (ArrayList) request.getAttribute("listProjetPossible");       // Probleme rencontr� lors de l'enregistrement du collaborateur
  
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
