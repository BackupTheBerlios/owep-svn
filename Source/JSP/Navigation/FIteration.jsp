<%@page import="owep.controle.CConstante"%>
<%@page import="owep.infrastructure.Session"%>
<%@page import="owep.modele.execution.MIteration"%>
<%@page import="java.util.ArrayList"%>

<jsp:useBean id="lSession"   class="owep.infrastructure.Session"      scope="page"/> 
<jsp:useBean id="lIteration" class="owep.modele.execution.MIteration" scope="page"/> 

<%
  	MIteration iteration;
  	lSession = (Session)(request.getSession().getAttribute("SESSION"));
  
    //localisation
    java.util.ResourceBundle messages;
    messages = java.util.ResourceBundle.getBundle("MessagesBundle");
  
  	//récupération de la liste des itérations à partir du projet
  	request.getRequestDispatcher ("/Navigation/RefreshIteration").include (request, response) ;
  	ArrayList listeIteration = (ArrayList) request.getAttribute (CConstante.PAR_LISTEITERATIONSMENU) ;
/*  	if (request.getAttribute (CConstante.PAR_LISTEITERATIONS)!=null)
  	{
      listeIteration = (ArrayList) request.getAttribute (CConstante.PAR_LISTEITERATIONS) ;
    }
    else
    {
      listeIteration = (ArrayList) lSession.getProjet().getListeIterations() ;
    }*/
    //Si il y a des itérations planifiées
    if(listeIteration.size()>0)
    {
%>

<center>

	<form action="/owep/Navigation/NavigationIteration" id="navigation" name="formulaireIteration">
    <a href="#" class="niveau2" onmouseover="tooltipTitreOn(this, event, '<%=messages.getString("navigationIterationAideChoixTitre")%>', '<%=messages.getString("navigationIterationAideChoix")%>')" onmouseout="tooltipOff(this, event)">
      <%=messages.getString("navigationIterationChoix")%>
    </a>
      <select class="niveau1" name="<%=CConstante.PAR_ITERATION%>" size=1 onChange="formulaireIteration.submit();">   
      <%  
        for(int i=0; i<listeIteration.size(); i++)
        {
          iteration = (MIteration)listeIteration.get(i);
      %>
        <option
          <%if (iteration.getId() == lSession.getIteration().getId()){%>
            selected
          <%}%>
          value="<%=iteration.getId()%>"><%=messages.getString("navigationIteration")%><%=iteration.getNumero()%>
		  <%if (iteration.getEtat() == 1) {%> <%=messages.getString("navigationIterationEncours")%> <%}%>            	
        </option>
	  <%}%>  
	    </select>
	</form>
</center>
<%
    //fin du if(listeIteration.size()>0)
    }
%>