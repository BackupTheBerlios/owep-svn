<%@ page language="java" %>
<%@page import="java.util.ResourceBundle"%>
<%@page import="owep.infrastructure.Session"%>
<%@ taglib uri="/WEB-INF/tld/template.tld" prefix="template" %>


<% 
  // Recuperation de la session
  HttpSession httpSession = request.getSession(true);
  //R�cup�ration du ressource bundle
  ResourceBundle messages = ((Session) httpSession.getAttribute("SESSION")).getMessages();
%>

<template:template nom="/JSP/Template/FTemplate.jsp">
  <template:section region="RegionTitre"      contenu="<%= messages.getString(\"projetGestion\")%>"  typePage="false"/>
  <template:section region="RegionPrincipal"  contenu="/JSP/Processus/FProjetVisu.jsp"               typePage="true"/>
  <template:section region="RegionLogo"       contenu="/JSP/Template/FLogo.jsp"                      typePage="true"/>
  <template:section region="RegionMenu"       contenu="/JSP/Template/FMenu.jsp"                      typePage="true"/>
  <template:section region="RegionMessagerie" contenu="/JSP/Template/FMessagerie.jsp"                typePage="true"/>
  <template:section region="RegionPied"       contenu="/JSP/Template/FPied.jsp"                      typePage="true"/>
</template:template>