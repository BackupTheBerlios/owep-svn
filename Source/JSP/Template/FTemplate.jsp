<%@ page language="java" %>
<%@ taglib uri='/WEB-INF/tld/template.tld' prefix='template' %>

<!-- en-tête de la page -->
<html>
<head>
  <meta content="text/html; charset=ISO-8859-1" http-equiv="content-type">
  <meta name="author" content="OWEP Team">
  <meta name="description" content="Outil de Workflow pour une équipe de Projet">
  <title>OWEP</title>
  <link rel="stylesheet" href="../CSS/Red.css" type="text/css">
  <script language="javascript" src="<template:region nom="RegionScript"/>"></script>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>


<!-- corps de la page -->
<body>
  <table cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
    
      <!-- colonne de gauche -->
      <td height="100%">
        <table class="regionGauche" cellpadding="0" cellspacing="0">
        <tbody>
        
          <!-- logo de l'application -->
          <tr>
            <td class="regionLogo">
            </td>
          </tr>
          
          
          <!-- menu Avancement -->
          <tr>
            <td class="regionMenu">
              <template:region nom="RegionMenu"/>
            </td>
          </tr>
        </tbody>
        </table>
      </td>
      
      
      
      <!-- colonne principale -->
      <td height="100%" width="100%">
        <table class="regionDroite" cellpadding="0" cellspacing="0">
        <tbody>
                
          <!-- messagerie -->
          <tr>
            <td class="regionMessagerie">
              <template:region nom="RegionMessagerie"/>
            </td>
          </tr>
          
          <tr>
            <td class="regionPrincipal">
              <p class="titre"><template:region nom="RegionTitre"/></p>
              <template:region nom="RegionNavigation"/>
              <template:region nom="RegionPrincipal"/>
            </td>
          </tr>
          
          
        </tbody>
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