function navigationIteration (pNumIteration)
{
  location.href = pNumIteration.options[pNumIteration.selectedIndex].value ;
  
      // Cherche l'itération à ouvrir
      /*getBaseDonnees ().begin () ;
      lRequete = getBaseDonnees ()
        .getOQLQuery ("select ITERATION from owep.modele.execution.MIteration ITERATION where mId = $1") ;
      lRequete.bind (pNumIteration) ;
      lResultat = lRequete.execute () ;*/

      MIteration lIteration = (MIteration) lResultat.next () ;

      /*getBaseDonnees ().commit () ;

      // Enregistre l'itération à ouvrir dans la session
      getSession ().setIteration(lIteration) ;*/
}