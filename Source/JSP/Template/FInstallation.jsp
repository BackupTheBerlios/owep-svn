<%@ page language="java" %>
<%@ taglib uri='/WEB-INF/tld/template.tld' prefix='template' %>
<%@page import="owep.controle.CConstante"%>


<!-- en-tête de la page -->
<html>
<head>
  <meta content="text/html; charset=ISO-8859-1" http-equiv="content-type">
  <meta name="author" content="OWEP Team">
  <meta name="description" content="Outil de Workflow pour une équipe de Projet">
  <title>OWEP</title>
  <link rel="stylesheet" href="/owep/CSS/Apparence.css" type="text/css">
  <script language="javascript" src="/owep/JavaScript/VerificationChamp.js"></script>
  <script language="javascript" src="/owep/JavaScript/AideEnLigne.js"></script>
  <script language="javascript" src="/owep/JavaScript/DomTT/domLib.js"></script>
  <script language="javascript" src="/owep/JavaScript/DomTT/alphaAPI.js"></script>
  <script language="javascript" src="/owep/JavaScript/DomTT/domTT.js"></script>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>


<!-- corps de la page -->
<body>
  <table cellpadding="0" height="100%" cellspacing="0">
  <tbody>
    <tr>
    
      <!-- colonne de gauche -->
      <td class="regionGauche"><!--  height="100%"-->
        <table class="regionLogoInstallation" cellpadding="0" cellspacing="0">
        <tbody>
        
          <!-- logo de l'application -->
          <tr>
            <td><!-- class="regionIdentificationPrincipal"-->
              <img src="/owep/Image/Vide.gif" class="logoInstallation">
            </td>
          </tr>
          
        </tbody>
        </table>
      </td>
      
      
      
      <!-- colonne principale -->
      <td width="100%"><!-- height="100%"-->
        <table class="regionInstallationDroite" cellpadding="0" cellspacing="0">
        <tbody>
                
          <tr>
            <td class="regionIdentificationPrincipal"><!---->
              <table>
                <tr>
                  <td class="titre" width="100%">
                    <template:region nom="RegionTitre"/>
                  </td>
                  <td>
                    <a onclick="afficherAide (pCodeAide)">
                      <img src="/owep/Image/Vide.gif" class="iconeAide" onmouseout="tooltipOff(this, event)"
                       onmouseover="tooltipTitreOn(this, event, 'Aide en ligne', 'Cliquez pour afficher de l\'aide sur cette page.')"/>
                    </a>
                  </td>
                </tr>
                <tr>
                  <td class="caseAide" colspan="2">
                    <div id="regionAide">
                    </div>
                  </td>
                </tr>
              <%
                String lMessage = (String) request.getAttribute (CConstante.PAR_MESSAGE) ;
                if (lMessage != null)
                {
              %>
                <tr>
                  <td class="texteMessage" width="100%">
                      <%= lMessage %>
                  </td>
                </tr>
              <%
                }
              %>
              </table>
              <br/>
            </td>
          </tr>
          
          
        </tbody>
        </table>
      </td>
    </tr>
    
    <tr>
      <td colspan="2" width="100%" height="100%">
        <table class="regionInstallationCentre" cellpadding="0" cellspacing="0">
        <tr>
          <td width="10px">&nbsp</td>
          <td class="InstallationCentre" width="130px">
<!--            <br><br><br>
            <font class="titre2">Etapes :</font>
            <br><br>
            <font class="titre3">
              &nbsp&nbsp&nbspAccueil<br>
              &nbsp&nbsp&nbspBase de données<br>
              &nbsp&nbsp&nbspCompte superviseur<br>
              &nbsp&nbsp&nbspConfiguration<br>
              &nbsp&nbsp&nbspFin
            </font>-->
          </td>
          <td class="InstallationCentre">
            <template:region nom="RegionPrincipal"/>
          </td>
        </tr>
        </table>
      </td>
    </tr>
    
    <!-- pied de page -->
    <tr>
      <td class="regionPied" colspan="2">
        <p class="pied">IUP ISI 2004/2005</p>
      </td>
    </tr>
  </tbody>
  </table>
</body>
</html>