<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/tld/template.tld" prefix="template" %>
<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">

<template:template nom="/JSP/Template/FTemplate.jsp">
  <template:section region="RegionScript"     contenu="NavigationIteration.js"         typePage="false"/>
  <template:section region="RegionTitre"      contenu="Liste des t�ches"               typePage="false"/>
  <template:section region="RegionNavigation" contenu="/JSP/Navigation/FIteration.jsp" typePage="true"/>
  <template:section region="RegionPrincipal"  contenu="/JSP/Tache/FListeTacheVisu.jsp" typePage="true"/>
  <template:section region="RegionLogo"       contenu="/JSP/Template/FLogo.jsp"        typePage="true"/>
  <template:section region="RegionMenu"       contenu="/JSP/Template/FMenu.jsp"        typePage="true"/>
  <template:section region="RegionMessagerie" contenu="/JSP/Template/FMessagerie.jsp"  typePage="true"/>
  <template:section region="RegionPied"       contenu="/JSP/Template/FPied.jsp"        typePage="true"/>
</template:template>