<%@page import="owep.infrastructure.Session"%>
<%@page import="java.util.ResourceBundle"%>

<jsp:useBean id="lSession" class="owep.infrastructure.Session" scope="page"/>

<%
  // Recuperation de la session
  HttpSession httpSession = request.getSession(true);
  lSession = (Session) httpSession.getAttribute("SESSION");
  
  //Récupération du ressource bundle
  ResourceBundle messages = lSession.getMessages();
  
  String mFichier = (String) request.getAttribute("mFichierExport");
%>

<center>
  <%=messages.getString("exportMessage")%>
  <br>
  <a href="../Processus/Export/<%=mFichier%>"><%=mFichier%></a>
</center>


<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "<%=messages.getString("exportAide1")%>" ;
pCodeAide += "<%=messages.getString("exportAide2")%>" ;
</script>
