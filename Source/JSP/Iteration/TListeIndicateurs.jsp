<%@ page language="java" %>
<%@page import="owep.modele.execution.MIteration"%>
<%@page import="owep.modele.execution.MProjet"%>
<%@page import="owep.controle.CConstante"%>
<%@ taglib uri="/WEB-INF/tld/template.tld" prefix="template" %>
<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">

<%
//localisation
    java.util.ResourceBundle messages;
    messages = java.util.ResourceBundle.getBundle("MessagesBundle");

  String lTitrePage ;
  
  MProjet lProjet = (MProjet)request.getAttribute (CConstante.PAR_PROJET) ;
  if (lProjet.getNbIndicateurs()>0)
  {
    MIteration lIteration  = (MIteration) request.getAttribute (CConstante.PAR_ITERATION) ;
    lTitrePage = messages.getString("indicateursTitre")+lIteration.getNumero() ;
  }
  else
  {
    lTitrePage = messages.getString("aucunIndicateur") ;
  }
%>

<template:template nom="/JSP/Template/FTemplate.jsp">
  <template:section region="RegionTitre"      contenu="<%=lTitrePage%>" typePage="false"/>  
  <template:section region="RegionPrincipal"  contenu="/JSP/Iteration/FListeIndicateurs.jsp" typePage="true"/>
  <template:section region="RegionLogo"       contenu="/JSP/Template/FLogo.jsp"        typePage="true"/>
  <template:section region="RegionMenu"       contenu="/JSP/Template/FMenu.jsp"        typePage="true"/>
  <template:section region="RegionMessagerie" contenu="/JSP/Template/FMessagerie.jsp"  typePage="true"/>
  <template:section region="RegionPied"       contenu="/JSP/Template/FPied.jsp"        typePage="true"/>
</template:template>