/**
 * Variables globales du module.
 */
var gChampsInvalides = new String ('') ;
var gCouleurFond     = null ;


/**
 * Méthode vérifiant qu'un champ n'est pas vide.
 */
function isVide (pValeur, pLibelle)
{
  var lRegExpression = /^\S+$/ ;
  
  if(! lRegExpression.test (pValeur))
  {
    gChampsInvalides += 'Le champ \'' + pLibelle + '\' est vide.\n' ;
    return true ;
  }
  else
  {
    return false ;
  }
}


/**
 * Méthode vérifiant qu'un champ contient une date correctement formatée.
 */
function isDate (pDate, pLibelle)
{
  //var lRegExpression = /^\d{1}\d{1}\-\d{1}\d{1}\-\d{1}\d{1}\d{1}\d{1}$/ ;
  var lRegExpression = /^\d{1}\d{1}\d{1}\d{1}\-\d{1}\d{1}\-\d{1}\d{1}$/ ;
  
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


/**
 * Méthode vérifiant qu'un champ contient un entier correctement formatée.
 */
function isInteger (pInteger, pLibelle)
{
  var lRegExpression = /^\S+$/ ;
  
  if ((pInteger.length != 0) && (! lRegExpression.test (pInteger)))
  {
    gChampsInvalides += 'Le champ \'' + pLibelle + '\' est incorrect.\n' ;
    return false ;
  }
  else
  {
    return true ;
  }
}


/**
 * Fonction qui vérifiant si les différents champs sont correctement saisies. Si ce n'est pas le
 * cas, une fenêtre contenant la liste des champs incorrect est affichée.
 */	
function validerChamps ()
{
  if (gChampsInvalides != '')
  {
    alert (gChampsInvalides) ;
    gChampsInvalides = '' ;
  }
  else
  {
    document.forms[0].submit () ;
  }
} 

/** 
 * Fonction qui vérifie qu'un élément d'une liste est sélectionné.
 */
function validerSelect (pSelect, pMessage)
{
  if (pSelect.selectedIndex == -1)
  {
    alert (pMessage) ;
  }
  else
  {
    document.forms[0].submit () ;
  }
}

function ajout(listeDep, listeArr)
{
  var option = new Option(listeDep.options[listeDep.selectedIndex].text,listeDep.options[listeDep.selectedIndex].value); 
  listeArr.options[listeArr.length] = option; 
  listeDep.options[listeDep.selectedIndex] = null ;
}


  function enleve(listeArr)
  { 
    if(listeArr.options[listeArr.selectedIndex].value == 0){ } 
    else
    { 
      listeArr.options[listeArr.selectedIndex] = null; 
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

/**
 * Fonction qui permet de vérifier que les différents champs sont correstement saisie.
 */	
function validationSaisie ()
{
  alert('ghj');
  var lChampIncorrect ;
  
  
  for (i = 0; i < gTabChampText.length (); i++) 
  {
    if (!isEmpty(this.document.getElementById(gTabChampText[i]).value)) 
    {
      lChampIncorrect = lChampIncorrect +  '\n' + gTabChampText[i] + ' non saisi.' ;
    }
  }
  
  for (i = 0; i < gTabChampDate.length (); i++)
  {
    if (!isDate(this.document.getElementById(gTabChampDate[i]).value)) 
    {
      lChampIncorrect = lChampIncorrect + '\n' + gTabChampDate[i] + ' incorrect.' ;
    }
	else if (!isEmpty(this.document.getElementById(gTabChampDate[i]).value)) 
    {
      lChampIncorrect = lChampIncorrect +  '\n' + gTabChampDate[i] + ' non saisi.' ;
    } 
  } 
  
  alert (lChampIncorrect);
}
