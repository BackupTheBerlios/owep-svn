<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/tld/template.tld" prefix="template" %>

<% 
  //localisation
  String langue = new String ("fr") ;
  String pays = new String ("FR") ;
  
  java.util.Locale currentLocale ;
  java.util.ResourceBundle messages ;
  currentLocale = new java.util.Locale (langue, pays) ;

  messages = java.util.ResourceBundle.getBundle("MessagesBundle", currentLocale);
  String titre = messages.getString ("identificationTitre") ;
%>  

<template:template nom="/JSP/Template/FAide.jsp">
  <template:section region="RegionTitre"      contenu="Création d'une itération étape par étape." typePage="false"/>
  <template:section region="RegionPrincipal"  contenu="/JSP/Aide/FAideIterationModif.jsp"             typePage="true"/>
  <template:section region="RegionPied"       contenu="/JSP/Template/FPied.jsp"         typePage="true"/>
</template:template>