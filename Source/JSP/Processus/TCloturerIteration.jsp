<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/tld/template.tld" prefix="template" %>
<%@page import="owep.infrastructure.Session"%>
<%@page import="java.util.ResourceBundle"%>

<jsp:useBean id="lSession"       class="owep.infrastructure.Session"          scope="page"/> 

<%
  // Recuperation de la session
  HttpSession httpSession = request.getSession(true);
  lSession = (Session) httpSession.getAttribute("SESSION");
  
  //Récupération du ressource bundle
  ResourceBundle messages = lSession.getMessages();
%>  

<template:template nom="/JSP/Template/FTemplate.jsp">
  <template:section region="RegionTitre"      contenu="<%=messages.getString("cloturerIterationTitre")%>" typePage="false"/>
  <template:section region="RegionPrincipal"  contenu="/JSP/Processus/FCloturerIteration.jsp"        typePage="true"/>
  <template:section region="RegionLogo"       contenu="/JSP/Template/FLogo.jsp"                      typePage="true"/>
  <template:section region="RegionMenu"       contenu="/JSP/Template/FMenu.jsp"                      typePage="true"/>
  <template:section region="RegionMessagerie" contenu="/JSP/Template/FMessagerie.jsp"                typePage="true"/>
  <template:section region="RegionPied"       contenu="/JSP/Template/FPied.jsp"                      typePage="true"/>
</template:template>