<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="owep.modele.execution.MCollaborateur"%>
<%@page import="owep.modele.processus.MRole"%>
<%@page import="owep.infrastructure.Session" %>
<%@page import="java.util.ResourceBundle" %>
<%@page import="java.util.Iterator" %>

<jsp:useBean id="lSession" class="owep.infrastructure.Session" scope="page"/>
<jsp:useBean id="lCollaborateur" class="owep.modele.execution.MCollaborateur" scope="page"/>
<jsp:useBean id="lRole" class="owep.modele.processus.MRole" scope="page"/>

<%
  // Recuperation de la session
  HttpSession httpSession = request.getSession(true);
  lSession = (Session) httpSession.getAttribute("SESSION");
  
  // Récupération du ressource bundle
  ResourceBundle lMessage = lSession.getMessages();
  
  // Récupération des parametres
  ArrayList mListeCollaborateur = (ArrayList) request.getAttribute("listeCollaborateur"); // Liste des collaborateurs enregistré
  if(mListeCollaborateur == null)
    mListeCollaborateur = new ArrayList();
  ArrayList mListeRole = (ArrayList) request.getAttribute("listeRole");// Liste des roles possibles
  if(mListeRole == null)
    mListeRole = new ArrayList();
  String mNumPage = (String) request.getAttribute("numPage");
  int mIdProjet = Integer.parseInt((String) request.getAttribute("idProjet"));
  
  String mProbleme = (String) request.getAttribute("mProbleme");
  if(mProbleme == null)
    mProbleme = "";
  String mNom = (String) request.getAttribute("mNom");                 // Nom du collaborateur
  String mPrenom = (String) request.getAttribute("mPrenom");           // Prenom du collaborateur
  String mLogin = (String) request.getAttribute("mLogin");             // Login du collaborateur
  String mMail = (String) request.getAttribute("mMail");               // Email du collaborateur
  String mAdresse = (String) request.getAttribute("mAdresse");         // Adresse du collaborateur
  String mTelephone = (String) request.getAttribute("mTelephone");     // Numéro du telephone du collaborateur
  String mPortable = (String) request.getAttribute("mPortable");       // Numéro du portable du collaborateur
  String mCommentaire = (String) request.getAttribute("mCommentaire"); // Commentaire du collaborateur
%>

<center>
<br>
<form action="./CreationCollaborateur" method="post" name="formCreerCollaborateur">

<%
  if(mNumPage.equals("1"))
  {
%>
<!-- Code javascript -->
<script type="text/javascript" language="JavaScript">
function changeRole(pListe){
  var num = pListe.options[pListe.selectedIndex].value;
  var id = 'divRole'+num;
  var subOptions =
    document.all ? document.all[id] :
    document.getElementById ? document.getElementById(id) :
    null;
  reset();
  if (subOptions) {
    subOptions.style.display = 'block';
  }
}

function reset(){
  var i;
  for(i = 1 ; i <= <%=mListeCollaborateur.size()%> ; i++)
  {
    var id = 'divRole'+i;
    var subOptions =
      document.all ? document.all[id] :
      document.getElementById ? document.getElementById(id) :
      null;
    if (subOptions) {
      subOptions.style.display = 'none';
    }
  }
}

function envoyer(){
  var i;
<%
      for(int i = 1 ; i <= mListeCollaborateur.size() ; i++)
      {
%>
  for(i = 0 ; i < document.formCreerCollaborateur.pSelectRole<%=i%>.length ; i++){
    var tmp = document.formCreerCollaborateur.mRoleSelected<%=i%>.value;
    if(tmp != "")
      tmp = tmp+"_";
    document.formCreerCollaborateur.mRoleSelected<%=i%>.value = tmp+document.formCreerCollaborateur.pSelectRole<%=i%>.options[i].value;
  }
<%
      }
%>
  document.formCreerCollaborateur.submit();
}

function valider(){
  var texte = "";
  
  if(document.formCreerCollaborateur.mNom.value == "")
    texte += "<%=lMessage.getString("messageNom")%>\n";
  if(document.formCreerCollaborateur.mLogin.value == "")
    texte += "<%=lMessage.getString("messageLogin")%>\n";
  if(document.formCreerCollaborateur.mMail.value == "")
    texte += "<%=lMessage.getString("messageMail")%>\n";
  
  if(texte == "")
    document.formCreerCollaborateur.submit();
  else
    alert(texte);
}
</script>

  <table class="tableau" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipTitreOn(this, event, '<%=lMessage.getString("aideChampObligatoire")%>', '<%=lMessage.getString("aideCollaborateurNom")%>')" onmouseout="tooltipOff(this, event)"><%=lMessage.getString("collaborateurNom")%></a>
      </td>
      <td class="caseNiveau3">
        <select name="mCollaborateur" class="niveau2" onchange="changeRole(document.formCreerCollaborateur.mCollaborateur);">
<%
      HashMap mapRolePossible = new HashMap();
      HashMap mapRoleEffectif = new HashMap();
      Iterator it = mListeCollaborateur.iterator();
      while(it.hasNext())
      {
        lCollaborateur = (MCollaborateur) it.next();
%>
        <option value="<%=lCollaborateur.getId()%>"><%=lCollaborateur.getPrenom()+"&nbsp;"+lCollaborateur.getNom()%></option>
<%
        ArrayList listeRolePossible = new ArrayList();
        ArrayList listeRoleEffectif = new ArrayList();
        ArrayList listeRoleCollab = lCollaborateur.getListeRoles();
        
        Iterator itRole = mListeRole.iterator();
        while(itRole.hasNext())
        {
          lRole = (MRole) itRole.next();
          Iterator itRoleEffectif = listeRoleCollab.iterator();
          boolean b = false;
          while(itRoleEffectif.hasNext() && !b)
          {
            MRole role = (MRole) itRoleEffectif.next();
            b = (lRole.getId() == role.getId());
          }
          if(!b)
            listeRolePossible.add(lRole);
          else
            listeRoleEffectif.add(lRole);
        }
        
        mapRolePossible.put(String.valueOf(lCollaborateur.getId()),listeRolePossible);
        mapRoleEffectif.put(String.valueOf(lCollaborateur.getId()),listeRoleEffectif);
      }
%>
        </select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="./CreationCollaborateur?numPageSuivante=2"><%=lMessage.getString("collaborateurAjout")%></a>
      </td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=lMessage.getString("aideCollaborateurRole")%>')" onmouseout="tooltipOff(this, event)"><%=lMessage.getString("collaborateurCreationRole")%></a>
      </td>
      <td class="caseNiveau3">
      
<%
      for(int i = 1 ; i <= mListeCollaborateur.size() ; i++)
      {
%>
      <input type="hidden" name="mRoleSelected<%=i%>" value="">
<%
        ArrayList listeRolePossible = (ArrayList) mapRolePossible.get(String.valueOf(i));
        if(listeRolePossible == null)
          listeRolePossible = new ArrayList();
        ArrayList listeRoleEffectif = (ArrayList) mapRoleEffectif.get(String.valueOf(i));
        if(listeRoleEffectif == null)
          listeRoleEffectif = new ArrayList();
%>
      <div id="divRole<%=i%>" style="display: <%=(i==1)?"block":"none"%>;">
      <table border="0" cellpadding="0" cellspacing="0"><tr>
      <td>
        <font class="titre3">Rôles possibles :</font><br/>
        <select name="pSelectRolePossible<%=i%>" class="niveau2" style="width: 250" size="6"
         onmouseover="tooltipTitreOn(this, event, '<%=lMessage.getString("aideChampObligatoire")%>', '<%=lMessage.getString("aideCollaborateurRolePossible")%>')" onmouseout="tooltipOff(this, event)">
<%
        it = listeRolePossible.iterator();
        while(it.hasNext())
        {
          lRole = (MRole) it.next();
%>
          <option value="<%=lRole.getId()%>"><%=lRole.getNom()%></option>
<%
        }
%>
        </select>
      </td>
      
      <td align="center" valign="middle" width="0">
        <font class="titre3">&nbsp;</font><br/>
        <center>
          <input type="button" value="    Ajouter >  " class="bouton" onclick="transfererItem (document.formCreerCollaborateur.pSelectRolePossible<%=i%>, document.formCreerCollaborateur.pSelectRole<%=i%>) ;"
           onmouseover="tooltipOn(this, event, '<%=lMessage.getString("aideCollaborateurAjoutRole")%>')" onmouseout="tooltipOff(this, event)"/>
          <br/>
          <input type="button" value="< Supprimer" class="bouton" onclick="transfererItem (document.formCreerCollaborateur.pSelectRole<%=i%>, document.formCreerCollaborateur.pSelectRolePossible<%=i%>) ;"
           onmouseover="tooltipOn(this, event, '<%=lMessage.getString("aideCollaborateurSupprimerRole")%>')" onmouseout="tooltipOff(this, event)"/>
        </center>
      </td>
      
      <td>
        <font class="titre3"><%=lMessage.getString("collaborateurRoleEffectif")%></font><br/>
        <select name="pSelectRole<%=i%>" class="niveau2" style="width: 250" size="6"
         onmouseover="tooltipTitreOn(this, event, '<%=lMessage.getString("aideChampObligatoire")%>', '<%=lMessage.getString("aideCollaborateurRoleEffectif")%>')" onmouseout="tooltipOff(this, event)">
<%
        it = listeRoleEffectif.iterator();
        while(it.hasNext())
        {
          lRole = (MRole) it.next();
%>
          <option value="<%=lRole.getId()%>"><%=lRole.getNom()%></option>
<%
        }
%>
        </select>
      </td>
      </tr></table>
      </div>

<%
      }
%>

      </td>
    </tr>
  </table>
  <input type="hidden" name="numPage" value="1">
  <input type="hidden" name="numPageSuivante" value="1">
  <p class="texteSubmit">
    <input class="bouton" type="button" value="Valider" onclick="envoyer();"
     onmouseover="tooltipOn(this, event, 'Cliquez pour associer le collaborateur sélectionné au projet et lui attribuer un ou des rôles.')" onmouseout="tooltipOff(this, event)">
  </p>
  <br>
<%
  }
  
  if(mNumPage.equals("2"))
  {
%>
<!-- Code javascript -->
<script type="text/javascript" language="JavaScript">
function valider(){
  var texte = "";
  
  if(document.formCreerCollaborateur.mNom.value == "")
    texte += "<%=lMessage.getString("messageNom")%>\n";
  if(document.formCreerCollaborateur.mLogin.value == "")
    texte += "<%=lMessage.getString("messageLogin")%>\n";
  if(document.formCreerCollaborateur.mMail.value == "")
    texte += "<%=lMessage.getString("messageMail")%>\n";
  
  if(texte == "")
    document.formCreerCollaborateur.submit();
  else
    alert(texte);
}
</script>
  <%=mProbleme%><br>
  <table class="tableau" border="0" cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipTitreOn(this, event, '<%=lMessage.getString("aideChampObligatoire")%>', '<%=lMessage.getString("aideCollaborateurNom")%>')" onmouseout="tooltipOff(this, event)"><%=lMessage.getString("collaborateurNom")%> *</a>
      </td>
      <td class="caseNiveau3"><input class="niveau2" type="text" name="mNom" value="<%=(mProbleme.equals(""))?"":mNom%>"></td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=lMessage.getString("aideCollaborateurPrenom")%>')" onmouseout="tooltipOff(this, event)"><%=lMessage.getString("collaborateurPrenom")%></a>
      </td>
      <td class="caseNiveau3"><input class="niveau2" type="text" name="mPrenom" value="<%=(mProbleme.equals(""))?"":mPrenom%>"></td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipTitreOn(this, event, '<%=lMessage.getString("aideChampObligatoire")%>', '<%=lMessage.getString("aideCollaborateurLogin")%>')" onmouseout="tooltipOff(this, event)"><%=lMessage.getString("collaborateurLogin")%> *</a>
      </td>
      <td class="caseNiveau3"><input class="niveau2" type="text" name="mLogin" value="<%=(mProbleme.equals(""))?"":mLogin%>"></td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipTitreOn(this, event, '<%=lMessage.getString("aideChampObligatoire")%>', '<%=lMessage.getString("aideCollaborateurMail")%>')" onmouseout="tooltipOff(this, event)"><%=lMessage.getString("collaborateurMail")%> *</a>
      </td>
      <td class="caseNiveau3"><input class="niveau2" type="text" name="mMail" value="<%=(mProbleme.equals(""))?"":mMail%>"></td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=lMessage.getString("aideCollaborateurAdresse")%>')" onmouseout="tooltipOff(this, event)"><%=lMessage.getString("collaborateurAdresse")%></a>
      </td>
      <td class="caseNiveau3"><textarea class="niveau2" name="mAdresse" rows=3><%=(mProbleme.equals(""))?"":mAdresse%></textarea></td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=lMessage.getString("aideCollaborateurTelephone")%>')" onmouseout="tooltipOff(this, event)"><%=lMessage.getString("collaborateurTelephone")%></a>
      </td>
      <td class="caseNiveau3"><input class="niveau2" type="text" name="mTelephone" value="<%=(mProbleme.equals(""))?"":mTelephone%>"></td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=lMessage.getString("aideCollaborateurPortable")%>')" onmouseout="tooltipOff(this, event)"><%=lMessage.getString("collaborateurPortable")%></a>
      </td>
      <td class="caseNiveau3"><input class="niveau2" type="text" name="mPortable" value="<%=(mProbleme.equals(""))?"":mPortable%>"></td>
    </tr>
    <tr>
      <td class="caseNiveau1">
        <a href="#" class="niveau1" onmouseover="tooltipOn(this, event, '<%=lMessage.getString("aideCollaborateurCommentaire")%>')" onmouseout="tooltipOff(this, event)"><%=lMessage.getString("collaborateurCommentaire")%></a>
      </td>
      <td class="caseNiveau3"><textarea class="niveau2" name="mCommentaire" rows=3><%=(mProbleme.equals(""))?"":mCommentaire%></textarea></td>
    </tr>
  </tbody>
  </table>
  <input type="hidden" name="numPage" value="2">
  <p class="texteObligatoire"><%=lMessage.getString("champObligatoire")%></p>
  <p class="texteSubmit">
    <input class="bouton" type="button" value="<%=lMessage.getString("identificationValider")%>" onclick="valider();"
     onmouseover="tooltipOn(this, event, '<%=lMessage.getString("aideCollaborateurValidationFormulaire")%>')" onmouseout="tooltipOff(this, event)">
  </p>

<%
  }
%>

</form>
</center>

<!-- Aide en ligne -->
<script type="text/javascript" language="JavaScript">
pCodeAide  = "<%=lMessage.getString("aidePageCollaborateur")%>" ;
</script>
