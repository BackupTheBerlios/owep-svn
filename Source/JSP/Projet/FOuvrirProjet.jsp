<%@page import="java.util.ArrayList"%>
<%@page import="owep.modele.execution.MProjet"%>
<%@page import="owep.infrastructure.Session"%>

<jsp:useBean id="lProjet" class="owep.modele.execution.MProjet" scope="page"/>

<%
  // Recuperation de la session
  HttpSession httpSession = request.getSession(true);
  owep.infrastructure.Session lSession = (Session) httpSession.getAttribute("SESSION");
  
  //Récupération du ressource bundle
  java.util.ResourceBundle lMessages = lSession.getMessages();
%>
<center>
  <%
    // Récupération des paramétres
    ArrayList lListProjet = (ArrayList) request.getAttribute ("listProjetPossible") ;       // Probleme rencontré lors de l'enregistrement du collaborateur
    
    if(lListProjet.size() == 0)
    {
%>
  <%=lMessages.getString("ouvrirProjetAucunProjet")%><br/><br/>
<%
    }
    else
    {
%>
  <%=lMessages.getString("ouvrirProjetMessage")%><br/><br/>
<%
    }
    
    int i;
    for(i = 0 ; i < lListProjet.size() ; i++)
    {
      lProjet = (MProjet) lListProjet.get(i);
  %>
    <a class="niveau2" href="../Projet/OuvrirProjet?mIdProjet=<%=lProjet.getId ()%>"
     onmouseover="tooltipOn(this, event, '<%=lMessages.getString("aideOuvrirProjetCliquez")%>')" onmouseout="tooltipOff(this, event)">
      <%=lProjet.getNom ()%>
    </a><br>
  <%
    }
  %>
</center>


<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "<%=lMessages.getString("aideOuvrirProjetPage")%>" ;
</script>
