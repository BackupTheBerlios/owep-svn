<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/tld/template.tld" prefix="template" %>

<%
    //localisation
    java.util.ResourceBundle messages;
    messages = java.util.ResourceBundle.getBundle("MessagesBundle");
    String titre = messages.getString("ajoutArtefactTitre");
%>

<template:template nom="/JSP/Template/FTemplate.jsp">
  <template:section region="RegionTitre"      contenu="<%=titre%>"                       typePage="false"/>
  <template:section region="RegionPrincipal"  contenu="/JSP/Artefact/FArtefactAjout.jsp" typePage="true"/>
  <template:section region="RegionLogo"       contenu="/JSP/Template/FLogo.jsp"          typePage="true"/>
  <template:section region="RegionMenu"       contenu="/JSP/Template/FMenu.jsp"          typePage="true"/>
  <template:section region="RegionMessagerie" contenu="/JSP/Template/FMessagerie.jsp"    typePage="true"/>
  <template:section region="RegionPied"       contenu="/JSP/Template/FPied.jsp"          typePage="true"/>
</template:template>