<%@page import="owep.controle.CConstante"%>
<%@page import="owep.modele.execution.MCollaborateur"%>

<jsp:useBean id="lCollaborateur" class="owep.modele.execution.MCollaborateur" scope="session"/> 


<%
    //localisation
    java.util.ResourceBundle messages;
    messages = java.util.ResourceBundle.getBundle("MessagesBundle"); 
%>
    <%=messages.getString("aucuneIterationCree")%>

 <BR><BR>
<%
  lCollaborateur = (MCollaborateur) request.getAttribute (CConstante.PAR_COLLABORATEUR) ; 
  if (lCollaborateur.getDroit()==1)
  {  
%>
 <a href="/owep/Processus/IterationModif"><B><%=messages.getString("listeTacheAjouterIteration")%></B></a>
<%
  }
%>
