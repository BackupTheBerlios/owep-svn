<%@page import="java.util.ArrayList"%>
<%@page import="owep.infrastructure.Session"%>
<%@page import="owep.modele.execution.MProjet"%>
<%@page import="owep.modele.execution.MTache"%>
<%@page import="java.util.ResourceBundle"%>

<jsp:useBean id="lSession" class="owep.infrastructure.Session" scope="page"/>
<jsp:useBean id="lProjet" class="owep.modele.execution.MProjet" scope="page"/>

<%try{
  // Recuperation de la session
  HttpSession httpSession = request.getSession(true);
  lSession = (Session) httpSession.getAttribute("SESSION");
  
  //Récupération du ressource bundle
  ResourceBundle messages = lSession.getMessages();
  
  // Variable de la page
  boolean mProjetOuvert = (lSession.getProjet() != null);
  boolean lIterationExiste = (lSession.getIteration() != null);
  boolean mEstChefProjet = false;
  if(mProjetOuvert)
  {
    mEstChefProjet = (lSession.getProjet().getChefProjet().getId() == lSession.getCollaborateur().getId());
  }
  
  //Droit administrateur
  int ADMIN = 1;
  boolean mEstAdmin = (lSession.getCollaborateur().getDroit() == ADMIN);
%>  

<%
  // Déclaration des variables
  int idProjetOuvert ;
  
  // Recuperation de la liste de projet possible
  ArrayList lListProjet = lSession.getListProjetPossible();
  
  // Recuperation du projet ouvert
  lProjet = lSession.getProjet() ;
  
  
  // Si aucun projet n'est ouvert
  if(lProjet == null)
  {
    // Alors  la variable idProjetOuvert prend pour valeur -1
    idProjetOuvert = -1;
  }
  else
  {
    // Sinon la variable prend pour valeur l'id du projet
    idProjetOuvert = lProjet.getId();
  }
%>

<table class="regionMenu" style="width : 100%; height : 100%" cellpadding="0" cellspacing="0">
<!-- <table width="100%" height="100%" cellpadding="0" cellspacing="0"> -->
<tbody>

  <!-- menu Avancement -->
  <form name="changerProjet" action="../Projet/OuvrirProjet" method="post">
  <tr>
    <td class="caseMenuProjet">
      <!-- Le formulaire ayant pour nom changerProjet est envoyé dés qu'un changement sur la sélection de la liste a été constaté -->
      <select class="menuProjet" name="mIdProjet" size ="1" onchange="changerProjet.submit()"
       onmouseover="tooltipTitreOn(this, event, 'Selecteur de projet', 'Choisissez le projet que vous voulez visualiser.')" onmouseout="tooltipOff(this, event)">
<%
  // Affichage de la liste des projets possibles
  // Le projet ouvert est sélectionné par défaut
  for(int i = 0 ; i<lListProjet.size() ; i++)
  {
    lProjet = (MProjet) lListProjet.get(i);
%>
        <option VALUE="<%=lProjet.getId()%>" <%=(idProjetOuvert == lProjet.getId())?"selected":""%>>
          <%=lProjet.getNom()%>
        </option>
<%
  }
  if(!mProjetOuvert)
  {
%>
        <option VALUE="" selected>
          &nbsp;
        </option>
<%
  }
  
  // Recuperation du projet ouvert
  lProjet = lSession.getProjet() ;
%>
      </select>
    </td>
  </tr>
  </form>
  
  <tr>
    <td class="caseMenuConnexion">
      <a class="menuConnexion" href="../Outil/Deconnexion"
       onmouseover="tooltipOn(this, event, 'Déconnectez vous de l\'application <b>OWEP</b>.')" onmouseout="tooltipOff(this, event)">
        <%=messages.getString("menuDeconnexion")%>
      </a>
    </td>
  </tr>
  <tr>
    <td class="caseMenuSeparation">
      &nbsp;
    </td>
  </tr>
  
<%
  if((mProjetOuvert)&&(lIterationExiste)){
%>  
  <!-- menu avancement -->
  <tr>
    <td class="caseMenuNiveau1">
      <p class="menuNiveau1">Avancement :</p>
    </td>
  </tr>
  <tr>
    <td class="caseMenuNiveau2">
      <a class="menuNiveau2" href="../Tache/ListeTacheVisu?menu=clique">Liste des tâches</a>
    </td>
  </tr>
  <tr>
    <td class="caseMenuNiveau2">
      <a class="menuNiveau2" href="../Iteration/ListeIndicateurs?menu=clique">Mesures supplémentaires</a>
    </td>
  </tr>
  <tr>
    <td class="caseMenuSeparation">
      &nbsp;
    </td>
  </tr>
<%
  }
  
  // Test si un projet est ouvert et si le collaborateur connecté est le responsale du projet ouvert
  if(mProjetOuvert && mEstChefProjet)
  {
    if (lIterationExiste)
    {
%>
  <!-- menu Suivi de projet -->
  <tr>
    <td class="caseMenuNiveau1">
      <p class="menuNiveau1">Suivi de Projet :</p>
    </td>
  </tr>
  <tr>
    <td class="caseMenuNiveau2">
      <a class="menuNiveau2" href="../Avancement/AvancementGlobalProjet?menu=clique"
       onmouseover="tooltipOn(this, event, 'Détail de l\'avancement pour le projet ouvert et chacune des itérations.')" onmouseout="tooltipOff(this, event)">
        Projet
      </a>
    </td>
  </tr>
  <tr>
    <td class="caseMenuNiveau2">
      <a class="menuNiveau2" href="../Avancement/AvancementCollaborateur?menu=clique"
       onmouseover="tooltipOn(this, event, 'Détail de l\'avancement pour chacun des collaborateurs sur le projet ouvert.')" onmouseout="tooltipOff(this, event)">
        Collaborateurs
      </a>
    </td>
  </tr>
  <tr>
    <td class="caseMenuNiveau2">
      <a class="menuNiveau2" href="../Gestion/ListeRisqueVisu"
       onmouseover="tooltipOn(this, event, 'Liste des risques qui pourrait survenir sur le projet.')" onmouseout="tooltipOff(this, event)">
        Risques
      </a>
    </td>
  </tr>
  <tr>
    <td class="caseMenuNiveau2">
      <a class="menuNiveau2" href="../Gestion/ListeProblemeVisu"
       onmouseover="tooltipOn(this, event, 'Liste des problèmes survenus sur le projet et tâches pour les résoudre.')" onmouseout="tooltipOff(this, event)">
        Problèmes
      </a>
    </td>
  </tr>
  <tr>
    <td class="caseMenuNiveau2">
      <a class="menuNiveau2" href="../Processus/CloturerIteration">Cloturer l'itération</a>
    </td>
  </tr>
  <tr>
    <td class="caseMenuSeparation">
      &nbsp;
    </td>
  </tr>
  <%
    }
  %>
  <!-- menu Gestion de projet -->
  <tr>
    <td class="caseMenuNiveau1">
      <p class="menuNiveau1">Gestion de Projet :</p>
    </td>
  </tr>
  <tr>
    <td class="caseMenuNiveau2">
      <a class="menuNiveau2" href="../Processus/ProjetVisu"
       onmouseover="tooltipOn(this, event, 'Ajout et modification des itérations pour le projet.')" onmouseout="tooltipOff(this, event)">
        Projet
      </a>
    </td>
  </tr>
  <tr>
    <td class="caseMenuNiveau2">
      <a class="menuNiveau2" href="../Processus/CreationCollaborateur"
       onmouseover="tooltipOn(this, event, 'Ajout de nouveaux collaborateur sur le projet.')" onmouseout="tooltipOff(this, event)">
        Collaborateurs
      </a>
    </td>
  </tr>
  <tr>
    <td class="caseMenuNiveau2">
      <a class="menuNiveau2" href="../Processus/ActiviteImprevue"
       onmouseover="tooltipOn(this, event, 'Ajout et modification d\'activité non prévues dans le processus.')" onmouseout="tooltipOff(this, event)">
        Activités imprévues
      </a>
    </td>
  </tr>
  
  <%
  if (lIterationExiste)
  {
  %>
  <tr>
    <td class="caseMenuNiveau2">
      <a class="menuNiveau2" href="../Processus/TacheImprevue"
       onmouseover="tooltipOn(this, event, 'Ajout et modification de tâches non prévues dans le processus.')" onmouseout="tooltipOff(this, event)">
        Tâches imprévues
      </a>
    </td>
  </tr>
  <%
  }
  if(lProjet.getEtat() == MTache.ETAT_NON_DEMARRE)
  {
  %>
  <tr>
    <td class="caseMenuNiveau2">
      <a class="menuNiveau2" href="../Outil/DemarrerProjet">
        Demarrer le projet
      </a>
    </td>
  </tr>
<%
  }
  if(lProjet.getEtat() == MTache.ETAT_EN_COURS)
  {
%>
  <tr>
    <td class="caseMenuNiveau2">
      <a class="menuNiveau2" href="../Processus/CloturerProjet">
        Cloturer le projet
      </a>
    </td>
  </tr>
<%
  }
  //Test si l'utilisateur connecté a les droits de l'administrateur
  if (mProjetOuvert && mEstAdmin) {%>
  <tr>
    <td class="caseMenuNiveau2">
      <a class="menuNiveau2" href="../Processus/GererProjet"
       onmouseover="tooltipOn(this, event, 'Exporte les données du projet au format <b>.XML</b>.')" onmouseout="tooltipOff(this, event)">
        Création d'un projet
      </a>
    </td>
  </tr>
<%
    }
%>
  <tr>
    <td class="caseMenuNiveau2">
      <a class="menuNiveau2" href="../Outil/ExporterProjet">Exporter le projet</a><br>
    </td>
  </tr>
  <tr>
    <td class="caseMenuSeparation">
      &nbsp;
    </td>
  </tr>
  
<%
  }
  
  // Si aucun projet n'est ouvert le collaborateur peut créer un projet si il en a le droit
  if(!mProjetOuvert && mEstAdmin)
  {
%>
  <!-- menu projet -->
  <tr>
    <td class="caseMenuNiveau1">
      <p class="menuNiveau1">Projet :</p>
    </td>
  </tr>
  <tr>
    <td class="caseMenuNiveau2">
      <a class="menuNiveau2" href="../Processus/GererProjet">Création d'un projet</a><br>
    </td>
  </tr>
  <tr>
    <td class="caseMenuSeparation">
      &nbsp;
    </td>
  </tr>
<%
  }
%>
  
  <!-- menu configuration -->
  <tr>
    <td class="caseMenuNiveau1">
      <p class="menuNiveau1">Configuration :</p>
    </td>
  </tr>
  <tr>
    <td class="caseMenuNiveau2">
      <a class="menuNiveau2" href="../Outil/ModificationProfil">Modifier son profil</a>
    </td>
  </tr>
  
<%//Test si l'utilisateur connecté a les droits de l'administrateur
  if (mEstAdmin) {%>   
  <tr>
    <td class="caseMenuNiveau2">
      <a class="menuNiveau2" href="../Configuration/ConfigurationSite">Option de l'application</a><br>
    </td>
  </tr>
<%}%>  
  
<%}catch (Exception lException) {%><%=lProjet%><%=lSession.getProjet().getChefProjet()%><%}%>  
  <tr>
    <td height="100%" class="caseMenuSeparation2">
      &nbsp;
    </td>
  </tr>
  <tr>
    <td class="caseMenuSeparation">
      &nbsp;
    </td>
  </tr>
</tbody>
</table>