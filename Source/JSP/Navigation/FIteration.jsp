<%@page import="owep.controle.CConstante"%>
<%@page import="owep.infrastructure.Session"%>
<%@page import="owep.modele.execution.MIteration"%>
<%@page import="java.util.ArrayList"%>


<center>
	<form action="../Navigation/NavigationIteration" id="navigation">
    Choisissez l'itération : 	    
       <select name="<%=CConstante.PAR_ITERATION%>" size=1 onChange="forms[0].submit();">   
      <%
      	MIteration iteration;
      	Session getSession = (Session)(request.getSession().getAttribute("SESSION"));
      
      	//récupération de la liste des itérations à partir du projet sélectionné dans la session
        ArrayList listeIteration = getSession.getProjet().getListeIterations(); 
        for(int i=0; i<listeIteration.size(); i++)
        {
          iteration = (MIteration)listeIteration.get(i);
      %>
        <option
          <%if (iteration.getId() == getSession.getIteration().getId()){%>
            selected
          <%}%>
          value="<%=iteration.getId()%>">Itération <%=iteration.getNumero()%>
        </option>
	  <%}%>  
	    </select>
	</form>
</center>