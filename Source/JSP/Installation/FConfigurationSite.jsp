<%
  // Récupération des paramétres
  String mErreur = (String) request.getAttribute("mErreur");
  if(mErreur == null)
    mErreur = "";
%>

<center>
<font class="titre3">Configuration du site.</font>
<br><br>
<%=mErreur%>
<form action="/owep/Installation/Site" method="post">
<table class="tableauInvisible" border="0" cellpadding="1" cellspacing="1">
<tr>
    <td class="caseInvisible">
      <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, 'Langue souhaité pour le logiciel.')" onmouseout="tooltipOff(this, event)">Langue : </a>
    </td>
	<td>
      <select class="niveau1" name="mLangue" size ="1">
         <option VALUE="fr_FR" selected>Francais</option>
         <option VALUE="en_US" >Anglais</option>
      </select>
    </td>  
  </tr>
  
  <tr>
    <td class="caseInvisible">
      <a href="#" class="niveau2" onmouseover="tooltipOn(this, event, 'Apparence souhaité pour le logiciel.')" onmouseout="tooltipOff(this, event)">Apparence : </a>
    </td>
	<td>
      <select class="niveau1" name="mApparence" size ="1">
         <option VALUE="Blue.css" >Bleue</option>
         <option VALUE="Red.css" selected>Rouge</option>
      </select>
    </td>
  </tr>
</table>  
</center>
    <br>
  <center>  
    <input class="bouton" type="submit" value="Suivant  &gt"
     onmouseover="tooltipOn(this, event, 'Cliquez pour valider le formulaires.')" onmouseout="tooltipOff(this, event)">
  </center>

</form>


<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "La page de <b>Configuration du site</b> permet de choisir la langue et l'apparence de OWEP. Ces deux paramètres pourront être modifiés par le superviseur après s'être connecté au logiciel OWEP." ;
</script>
