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
  <script language="javascript" src="../JavaScript/<template:region nom="RegionScript"/>"></script>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>


<!-- corps de la page -->
<body>
  <table style="width : 100%" cellpadding="0" cellspacing="0">
  <tbody>
    <tr style="height : 100%">
    
      <!-- colonne de gauche -->
      <td style="width : 170px">
        <table class="regionGauche" style="width : 100%; height : 100%" cellpadding="0" cellspacing="0">
        <tbody>
        
          <!-- logo de l'application -->
          <tr>
            <td>
              <template:region nom="RegionLogo"/>
            </td>
          </tr>
          
          
          <!-- menu Avancement -->
          <tr>
            <td>
              <template:region nom="RegionMenu"/>
            </td>
          </tr>
        </tbody>
        </table>
      </td>
      
      
      
      <!-- colonne principale -->
      <td style="width : 100%">
        <table class="regionDroit" style="width : 100%; height : 100%" cellpadding="0" cellspacing="0">
        <tbody>
                
          <!-- messagerie -->
          <tr>
            <td>
              <template:region nom="RegionMessagerie"/>
            </td>
          </tr>
          
          <tr style="height: 100%; width: 100%">
            <td>
              <table class="regionPrincipal" style="height: 100%; width: 100%">
              <tbody>
                <!-- titre -->
                <tr>
                  <td class="regionTitre">
                    <template:region nom="RegionTitre"/>
                  </td>
                </tr>
                
                <!-- navigation -->
                <tr>
                  <td class="regionNavigation">
                    <template:region nom="RegionNavigation"/>
                  </td>
                </tr>
                
                <!-- contenu -->
                <tr style="height: 100%">
                  <td class="regionContenu">
                    <template:region nom="RegionPrincipal"/>
                  </td>
                </tr>
              </tbody>
              </table>
            </td>
          </tr>
          
          
        </tbody>
        </table>
      </td>
    </tr>
    
    
    <!-- pied de page -->
    <tr>
      <td colspan="2">
        <template:region nom="RegionPied"/>
      </td>
    </tr>
  </tbody>
  </table>
</body>
</html>