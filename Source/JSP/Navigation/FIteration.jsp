<%@page import="owep.controle.CConstante"%>
<center>
	<form id="navigation">
    Choisissez l'it�ration : 
	  <select name="pNumIteration" size=1 onChange="navigationIteration (this.form.pNumIteration)">
      <option selected value="<%=request.getAttribute (CConstante.PAR_ITERATION)%>">It�ration <%=request.getAttribute (CConstante.PAR_ITERATION)%></option>
	  </select>
	</form>
</center>