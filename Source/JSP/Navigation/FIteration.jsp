<%@page import="owep.controle.CConstante"%>
<center>
	<form id="navigation">
    Choisissez l'itération : 
	  <select name="pNumIteration" size=1 onChange="navigationIteration (this.form.pNumIteration)">
      <option selected value="<%=request.getAttribute (CConstante.PAR_ITERATION)%>">Itération <%=request.getAttribute (CConstante.PAR_ITERATION)%></option>
	  </select>
	</form>
</center>