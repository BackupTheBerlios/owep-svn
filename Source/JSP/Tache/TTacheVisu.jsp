<%@ page language="java" %>
<%@page import="owep.modele.execution.MTache"%>
<%@page import="owep.modele.execution.MTacheImprevue"%>
<%@page import="owep.controle.CConstante"%>
<%@ taglib uri="/WEB-INF/tld/template.tld" prefix="template" %>

<jsp:useBean id="lTache"              class="owep.modele.execution.MTache"              scope="page"/> 
<jsp:useBean id="lTacheImprevue"      class="owep.modele.execution.MTacheImprevue"      scope="page"/> 

<%
    //localisation
    java.util.ResourceBundle messages;
    messages = java.util.ResourceBundle.getBundle("MessagesBundle");
    
    String nomTache;
    
    if(request.getAttribute (CConstante.PAR_TACHE) != null)
    {   
      lTache = (MTache) request.getAttribute (CConstante.PAR_TACHE) ;
      nomTache = messages.getString("tacheVisuTitre")+lTache.getNom() ;
    }
    else
    { 
      lTacheImprevue = (MTacheImprevue) request.getAttribute (CConstante.PAR_TACHE_IMPREVUE) ;
      nomTache = messages.getString("tacheVisuImprevueTitre")+lTacheImprevue.getNom() ;
    }
  
%>

<template:template nom="/JSP/Template/FTemplate.jsp">
  <template:section region="RegionTitre"      contenu="<%=nomTache%>" typePage="false"/>
  <template:section region="RegionPrincipal"  contenu="/JSP/Tache/FTacheVisu.jsp"      typePage="true"/>
  <template:section region="RegionLogo"       contenu="/JSP/Template/FLogo.jsp"        typePage="true"/>
  <template:section region="RegionMenu"       contenu="/JSP/Template/FMenu.jsp"        typePage="true"/>
  <template:section region="RegionMessagerie" contenu="/JSP/Template/FMessagerie.jsp"  typePage="true"/>
  <template:section region="RegionPied"       contenu="/JSP/Template/FPied.jsp"        typePage="true"/>
</template:template>