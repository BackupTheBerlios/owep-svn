<%@page import="owep.controle.CConstante"%>
<%@page import="owep.infrastructure.Session"%>
<%@page import="owep.modele.execution.MIteration"%>
<%@page import="java.util.ArrayList"%>


<center>
	<form action="../Navigation/NavigationIteration" id="navigation">
    Choisissez l'it�ration : 	    
       <select name="<%=CConstante.PAR_ITERATION%>" size=1 onChange="forms[0].submit();">   
      <%
      	MIteration iteration;
      	Session getSession = (Session)(request.getSession().getAttribute("SESSION"));
      
      	//r�cup�ration de la liste des it�rations � partir du projet s�lectionn� dans la session
        ArrayList listeIteration = getSession.getProjet().getListeIterations(); 
        for(int i=0; i<listeIteration.size(); i++)
        {
          iteration = (MIteration)listeIteration.get(i);
      %>
        <option
          <%if (iteration.getId() == getSession.getIteration().getId()){%>
            selected
          <%}%>
          value="<%=iteration.getId()%>">It�ration <%=iteration.getNumero()%>
        </option>
	  <%}%>  
	    </select>
	</form>
</center>