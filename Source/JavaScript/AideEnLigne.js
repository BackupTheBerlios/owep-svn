lAideAffiche = false ;


/**
 * Affiche le texte d'aide pour une page lorsque l'utilisateur clique sur l'icone correspondant.
 */
function afficherAide (pAide)
{
  lAideAffiche = ! lAideAffiche ;
  if (lAideAffiche)
  {
    document.getElementById ('regionAide').innerHTML = "<img align='left' class='iconeCaseAide' src='/owep/Image/Vide.gif'>" + pAide ;
  }
  else
  {
    document.getElementById ('regionAide').innerHTML = '' ;
  }
}


/**
 * Affiche un tooltip contenant une aide contextuelle.
 */
function tooltipOn (pObjet, pEvenement, pLibelle)
{
  domTT_activate (pObjet, pEvenement, 'content', pLibelle, 'trail', true, 'fade', 'in') ;
}


/**
 * Affiche un tooltip contenant une aide contextuelle avec un titre.
 */
function tooltipTitreOn (pObjet, pEvenement, pTitre, pLibelle)
{
  domTT_activate (pObjet, pEvenement, 'caption', pTitre, 'content', pLibelle, 'trail', true, 'fade', 'in') ;
}


/**
 * Masque le tooltip contenant une aide contextuelle.
 */
function tooltipOff (pObjet, pEvenement)
{
  domTT_mouseout (pObjet, pEvenement) ;
}


/**
 * Ouvre une fenêtre popup.
 */
function popup (pPage)
{
  windowprops = "height=600,width=800,location=0,scrollbars=1,menubars=0,resizable=1";
  window.open (pPage, "Aide", windowprops) ;
}