<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/tld/template.tld" prefix="template" %>


<template:template nom="/JSP/Template/FInstallation.jsp">
  <template:section region="RegionTitre"      contenu="Installation de OWEP"                    typePage="false"/>
  <template:section region="RegionPrincipal"  contenu="/JSP/Installation/FSuperviseur.jsp"  typePage="true"/>
  <template:section region="RegionPied"       contenu="/JSP/Template/FPied.jsp"                 typePage="true"/>
</template:template>
