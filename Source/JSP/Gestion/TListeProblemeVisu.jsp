<%@ page language="java" %>
<%@page import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/tld/template.tld" prefix="template" %>

<%
  //Récupération du ressource bundle.
  ResourceBundle messages = ResourceBundle.getBundle ("MessagesBundle") ;
%>

<template:template nom="/JSP/Template/FTemplate.jsp">
  <template:section region="RegionTitre"      contenu="<%= messages.getString("listeProblemeVisuTitre") %>" typePage="false"/>
  <template:section region="RegionPrincipal"  contenu="/JSP/Gestion/FListeProblemeVisu.jsp" typePage="true"/>
  <template:section region="RegionLogo"       contenu="/JSP/Template/FLogo.jsp"             typePage="true"/>
  <template:section region="RegionMenu"       contenu="/JSP/Template/FMenu.jsp"             typePage="true"/>
  <template:section region="RegionMessagerie" contenu="/JSP/Template/FMessagerie.jsp"       typePage="true"/>
  <template:section region="RegionPied"       contenu="/JSP/Template/FPied.jsp"             typePage="true"/>
</template:template>