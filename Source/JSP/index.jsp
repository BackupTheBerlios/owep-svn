<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/tld/template.tld" prefix="template" %>

<% 
  //localisation
  java.util.ResourceBundle messages ;
  messages = java.util.ResourceBundle.getBundle("MessagesBundle");
  String titre = messages.getString ("identificationTitre") ;
%>  

<template:template nom="/JSP/Template/FIdentification.jsp">
  <template:section region="RegionTitre"      contenu="<%= titre %>"               typePage="false"/>
  <template:section region="RegionPrincipal"  contenu="/JSP/FIndex.jsp"          typePage="true"/>
  <template:section region="RegionPied"       contenu="/JSP/Template/FPied.jsp"  typePage="true"/>
</template:template>