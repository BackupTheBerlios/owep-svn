  function ajout(listeDep) {
    var existe = 0; 
    if(listeDep.options[listeDep.selectedIndex].value == 0){ } 
    else
    { 
      for(i=0;i< document.getElementById('selectArtefactEntreeChoisi').length ;i++)
      { 
        if(document.getElementById('selectArtefactEntreeChoisi').options[i].text == listeDep.options[listeDep.selectedIndex].text)
        { 
          existe = 1; 
        } 
        else 
        { 
          if(existe == 1)
          { 
            existe = 1; 
          } 
          else 
          { 
            existe = 0; 
          } 
        } 
      } 
      if(existe == 0)
      { 
        var option = new Option(listeDep.options[listeDep.selectedIndex].text,listeDep.options[listeDep.selectedIndex].value); 
        document.getElementById('selectArtefactEntreeChoisi').options[(document.getElementById('selectArtefactEntreeChoisi').length)] = option; 
        if ( document.getElementById('selectArtefactEntreeChoisi').length < document.getElementById('selectArtefactEntree').length) 
        {
		      document.getElementById('selectArtefactEntreeChoisi').size = document.getElementById('selectArtefactEntree').length;
        }
	    } 
      else
      { 
        alert('Cet artefact est déjà séléctionné !!!'); 
      } 
    }
  } 

  function enleve(listeArr)
  { 
    if(listeArr.options[listeArr.selectedIndex].value == 0){ } 
    else
    { 
      listeArr.options[listeArr.selectedIndex] = null; 
    }
  }
  
	/**Méthode vérifiant que le champ soit bien une date*/
  function isDate (pDate, pLibelle)
{
  var lRegExpression = /^\d{1}\d{1}\/\d{1}\d{1}\/\d{1}\d{1}\d{1}\d{1}$/ ;
  
  if ((pDate.length != 0) && (! lRegExpression.test (pDate)))
  {
    gChampsInvalides += 'Le champ \'' + pLibelle + '\' est incorrect.\n' ;
    return false ;
  }
  else
  {
    return true ;
  }
}
	
  function isEmpty(valeur)
  {
    var re = /^\S+$/;
    if(!re.test(valeur))
    {
      return false;
    }
    return true;
  }

  /**Méthode vérifiant que la valeur saisie soit bien un entier*/
  function isInteger(valeur)
  {
    var re = /^\d+$/;   
    if(!re.test(valeur))
    {
      return false;
   }
   return true;
  }


  function fenetreNouveauArtefact () 
  {
    window.open("NouvelArtefact.htm","PopUp",'width=500,height=210,location=no,status=no,toolbar=no,scrollbars=no');
  }
  
  function verifSaisie ()
  {
    var lSaisieCorrect = 1;
		
		if (!isEmpty(this.document.getElementById('textfieldNomTache').value)) 
    {
      lSaisieCorrect = 0;
			alert('Nom de la tache non saisie');
    }
		else if (!isEmpty(this.document.getElementById('textfieldDateDebutTache').value)) 
    {
      lSaisieCorrect = 0;
			alert('Date début non saisie');
    }
		else if (!isDate(this.document.getElementById('textfieldDateDebutTache').value)) 
    {
      lSaisieCorrect = 0;
			alert('Date de début incorrect');
    }
		else if (!isEmpty(this.document.getElementById('textfieldDateFinTache').value)) 
    {
      lSaisieCorrect = 0;
			alert('Date fin non saisie');
    }
		else if (!isDate(this.document.getElementById('textfieldDateFinTache').value)) 
    {
      lSaisieCorrect = 0;
			alert('Date de fin incorrect');
    }
		else if (!isEmpty(this.document.getElementById('textfieldChargeCollaborateur').value)) 
    {
      lSaisieCorrect = 0;
			alert('Charge non saisie');
    }
		else if (!isInteger(this.document.getElementById('textfieldChargeCollaborateur').value)) 
    {
      lSaisieCorrect = 0;
			alert('Charge incorrecte');
    }
    else if (this.document.getElementById('textfieldChargeCollaborateur').value < 0 ) 
    {
      lSaisieCorrect = 0;
			alert('Charge incorrecte');
    }
		/*else if (this.document.getElementById('selectArtefactSortie').options[0].text))
    {
      lSaisieCorrect = 0;
      alert('Artefact de sortie non saisie');
    }*/
	} 
	
  function ajoutNouvelArtefact (listArr) {
	  var existe = 0; 
    for(i=0;i< listArr.length ;i++)
    { 
      if(listArr.options[i].text == document.getElementById('textfieldNomArtefact').value)
      { 
        existe = 1; 
      } 
      else 
      { 
        if(existe == 1)
        { 
          existe = 1; 
        } 
        else 
        { 
          existe = 0; 
        } 
      } 
    } 
    if(existe == 0)
    {
	    var option = new Option(document.getElementById('textfieldNomArtefact').value,document.getElementById('textfieldNomArtefact').value); 
      listArr.options[(listArr.length)] = option; 
    }
    else
    {
      alert('Cet artefact est déjà présent.');
		}
	}