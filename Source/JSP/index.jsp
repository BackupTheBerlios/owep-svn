<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/tld/template.tld" prefix="template" %>

<% 
  //localisation
  String langue = new String("fr");
  String pays = new String("FR");
  
  java.util.Locale currentLocale;
  java.util.ResourceBundle messages;
  currentLocale = new java.util.Locale(langue, pays);

  messages = java.util.ResourceBundle.getBundle("MessagesBundle", currentLocale);
  String titre = messages.getString("identificationTitre");
%>  

<template:template nom="/JSP/Template/FTemplate.jsp">
  <template:section region="RegionTitre"      contenu="<%=titre%>"               typePage="false"/>
  <template:section region="RegionPrincipal"  contenu="/JSP/FIndex.jsp"          typePage="true"/>
  <template:section region="RegionLogo"       contenu="/JSP/Template/FLogo.jsp"  typePage="true"/>
  <template:section region="RegionPied"       contenu="/JSP/Template/FPied.jsp"  typePage="true"/>
</template:template>