<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/tld/template.tld" prefix="template" %>

<%@page import="owep.infrastructure.Session"%>

<%
  // Recuperation de la session
  HttpSession httpSession = request.getSession(true);
  owep.infrastructure.Session lSession = (Session) httpSession.getAttribute("SESSION");
  
  //Récupération du ressource bundle
  java.util.ResourceBundle lMessages = lSession.getMessages();
  String titre = lMessages.getString("profilTitre");
%>

<template:template nom="/JSP/Template/FTemplate.jsp">
  <template:section region="RegionTitre"      contenu="<%=titre%>"                                typePage="false"/>
  <template:section region="RegionPrincipal"  contenu="/JSP/Outil/FModificationProfil.jsp"        typePage="true"/>
  <template:section region="RegionLogo"       contenu="/JSP/Template/FLogo.jsp"                   typePage="true"/>
  <template:section region="RegionMenu"       contenu="/JSP/Template/FMenu.jsp"                   typePage="true"/>
  <template:section region="RegionMessagerie" contenu="/JSP/Template/FMessagerie.jsp"             typePage="true"/>
  <template:section region="RegionPied"       contenu="/JSP/Template/FPied.jsp"                   typePage="true"/>
</template:template>