<%@page import="owep.controle.CConstante"%>
<%@page import="owep.infrastructure.Session"%>
<%@page import="owep.modele.processus.MComposant"%>
<%@page import="owep.modele.processus.MProcessus"%>
<%@page import="owep.modele.processus.MDefinitionTravail"%>
<%@page import="owep.modele.processus.MActivite"%>
<%@page import="owep.modele.processus.MProduit"%>
<%@page import="owep.modele.processus.MRole"%>
<%@page import="owep.modele.execution.MIteration"%>
<%@page import="owep.modele.execution.MTache"%>
<%@page import="owep.modele.execution.MProjet"%>
<%@page import="owep.modele.execution.MCollaborateur"%>
<%@page import="owep.modele.execution.MArtefact"%>
<%@page import="owep.vue.transfert.VTransfert"%>
<%@page import="owep.vue.transfert.VTransfertConstante"%>
<%@page import="owep.vue.transfert.convertor.VDateConvertor"%>
<%@taglib uri='/WEB-INF/tld/transfert.tld' prefix='transfert' %>


<!-- Déclaration des variables locales -->
<%
  boolean lModif = request.getParameter (CConstante.PAR_SUBMIT) != null ;
  Session    lSession   = (Session) session.getAttribute (CConstante.SES_SESSION) ;
  MIteration lIteration = (MIteration) session.getAttribute (CConstante.SES_ITERATION) ;
  MProjet    lProjet    = lIteration.getProjet () ;
  MProcessus lProcessus = lProjet.getProcessus () ;
  String lCodeValidation ;

  // Liste des champs transférés.
  String lChampTacheNom             = "" ;
  String lChampTacheDescription     = "" ;
  String lChampTacheChargeInitiale  = "" ;
  String lChampTacheDateDebutPrevue = "" ;
  String lChampTacheDateFinPrevue   = "" ;
  String lChampArtefactNom          = "" ;
  String lChampArtefactDescription  = "" ;
%>




<p class="titre1">PROJET : <%= lProjet.getNom () %></p>
<p class="texte"><%= lProjet.getDescription () %></p>
<br/><br/>
 
<form action="./IterationModif" method="post" name="formIterationModif">

  <transfert:transfertbean scope="Session" type="owep.modele.execution.MIteration" bean="pIteration" idArbre="<%= CConstante.PAR_ARBREITERATION %>">
  <input <transfert:transfertchamp membre="setNumero" type="java.lang.Integer" libelle="Numéro de l\\'itération" convertor="VIntegerConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
   type="hidden" value="<%= lIteration.getNumero () %>" maxlength="<%= CConstante.TXT_MOYEN %>">
  
  
  <table class="tableau" width="100%" cellpadding="0" cellspacing="0">
  <tbody>
  
    <!-- Champs de l'itération -->
    <tr> 
      <td class="caseNiveau1" colspan="2">Itération <%= lIteration.getNumero () %> :</td>
    </tr>
    <tr>
      <td class="caseNiveau3" colspan="2">
        Nom de l'itération * : <input <transfert:transfertchamp membre="setNom" type="java.lang.String" libelle="Nom de l\\'itération" convertor="VStringConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
                              type="text" size="48" class="niveau2"
                              value="<%= lIteration.getNom () %>" maxlength="<%= CConstante.TXT_MOYEN %>">
      </td>
    </tr>
    <tr> 
      <td class="caseNiveau3" width="50%">
        Date de début prévue * : <input <transfert:transfertchamp membre="setDateDebutPrevue" type="java.util.Date" libelle="Date de début prévue" convertor="VDateConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/> 
                                type="text" size="8" class="niveau2"
                                value="<%= VDateConvertor.getString (lIteration.getDateDebutPrevue ()) %>"  maxlength="<%= CConstante.TXT_DATE %>">
      </td>
      <td class="caseNiveau3" width="50%">
        Date de fin prévue * : <input <transfert:transfertchamp membre="setDateFinPrevue" type="java.util.Date" libelle="Date de fin prévue" convertor="VDateConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>  
                              type="text" size="8" class="niveau2"
                              value="<%= VDateConvertor.getString (lIteration.getDateFinPrevue ()) %>" 
                              maxlength="<%= CConstante.TXT_DATE %>">
      </td>
    </tr>
  </tbody>
  </table>
  
  <br/><br/><br/>
  
  <table class="tableau" width="100%" cellpadding="0" cellspacing="0">
  <tbody>
  
    <!-- Liste des tâches -->
    <tr>
      <td class="caseNiveau1" colspan="2">
        Liste des tâches à réaliser :
      </td>
    </tr>
    <tr> 
      
      <!-- Liste des champs cachés contenant les données des tâches -->
      <%
        for (int lIndiceTache = 0; lIndiceTache < lIteration.getNbTaches (); lIndiceTache ++)
        {
          MTache lTache = lIteration.getTache (lIndiceTache) ;
      %>
        <transfert:transfertbean scope="Session" type="owep.modele.execution.MTache" bean="getTache" idArbre="<%= CConstante.PAR_ARBREITERATION %>">
        <!-- Ajoute le champ "Nom" à la liste -->
        <input <transfert:transfertchamp membre="setNom" type="" libelle="Nom de la tâche" convertor="VStringConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
         type="hidden" value="<%= lTache.getNom () %>">
        
        <!-- Ajoute le champ "Description" à la liste -->
        <input <transfert:transfertchamp membre="setDescription" type="" libelle="Description de la tâche" convertor="VStringConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
         type="hidden" value="<%= lTache.getDescription () %>">
        
        <!-- Ajoute le champ "ChargeInitiale" à la liste -->
        <input <transfert:transfertchamp membre="setChargeInitiale" type="" libelle="Charge initiale de la tâche" convertor="VDoubleConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
         type="hidden" value="<%= lTache.getChargeInitiale () %>">
        
        <!-- Ajoute le champ "DateDebutPrevu" à la liste -->
        <input <transfert:transfertchamp membre="setDateDebutPrevue" type="" libelle="Date de début prévue de la tâche" convertor="VDateConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
         type="hidden" value="<%= VDateConvertor.getString (lTache.getDateDebutPrevue ()) %>">
        
        <!-- Ajoute le champ "DateFinPrevue" à la liste -->
        <input <transfert:transfertchamp membre="setDateFinPrevue" type="" libelle="Date de fin prévue de la tâche" convertor="VDateConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
         type="hidden" value="<%= VDateConvertor.getString (lTache.getDateFinPrevue ()) %>">
        
        
        <!-- Ajoute les champs Disciplines, activités et collaborateurs -->
        <!-- TODO A optimiser en utilisant une méthode générale de recherche (cf. TODO controleur) -->
        <% 
          /* Liste des champs cachés contenant les données des artefacts en sorties */ 
          for (int lIndiceArtefactSortie = 0 ; lIndiceArtefactSortie < lTache.getNbArtefactsSorties () ; lIndiceArtefactSortie ++)
          {
        %>
            <transfert:transfertbean scope="Session" type="owep.modele.execution.MArtefact" bean="getArtefactSortie" idArbre="<%= CConstante.PAR_ARBREITERATION %>">

            <input <transfert:transfertchamp membre="setNom" type="" libelle="Nom de l\\'artefact en sortie" convertor="VStringConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
             type="hidden" value="<%= lTache.getArtefactSortie (lIndiceArtefactSortie).getNom () %>">
             
            <input <transfert:transfertchamp membre="setDescription" type="" libelle="Description de l\\'artefact en sortie" convertor="VStringConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBREITERATION %>"/>
             type="hidden" value="<%= lTache.getArtefactSortie (lIndiceArtefactSortie).getDescription () %>">
             
            </transfert:transfertbean>
        <%
          }
        %>
      </transfert:transfertbean>
      <%
        }
      %>
      
      
      <td class="caseNiveau3">
        <select name="<%= CConstante.PAR_LISTETACHES %>" class="niveau2" style="width: 200" size="29"
         onchange="selectTache (document.formIterationModif.<%= CConstante.PAR_LISTETACHES %>.selectedIndex)">
          <%
            for (int lIndiceTache = 0; lIndiceTache < lIteration.getNbTaches (); lIndiceTache ++)
            {
          %>
          <option value="<%= lIndiceTache %>"> <%= lIteration.getTache (lIndiceTache).getNom () %> </option>
          <%    
            }
          %>
        </select>
      </td>
      
      
      
      <!-- Détail de la tâche -->
      <transfert:transfertbean scope="Session" type="owep.modele.execution.MTache" bean="getTache" idArbre="<%= CConstante.PAR_ARBRETACHES %>">
      <td class="caseNiveau3" valign="top" align="left" width="100%" rowspan="2">
        <table width="100%" cellpadding="0" cellspacing="0" valign="top">
          <tr>
            <td colspan="2">
              <p class="titre2">Détail de la tâche :</p>
            </td>        
          </tr>
          <tr>
            <td width="50%" class="caseNiveau3SansBordure">    
              Nom * :
            </td>
            <td>
              <input <transfert:transfertchamp membre="setNom" type="java.lang.String" libelle="Nom" convertor="VStringConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBRETACHES %>"/>
               type="text" size="8" class="niveau2" value="" maxlength="<%= CConstante.TXT_MOYEN %>"><br/>
              <% lChampTacheNom = VTransfert.getDernierChamp () ; %>
            </td>
          </tr>
          <tr>
            <td class="caseNiveau3SansBordure">
              Description :
            </td>
            <td>
              <input <transfert:transfertchamp membre="setDescription" type="java.lang.String" libelle="Description" convertor="VStringConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBRETACHES %>"/>
               type="text" size="8" class="niveau2" value="" maxlength="<%= CConstante.TXT_LARGE %>"><br/>
              <% lChampTacheDescription = VTransfert.getDernierChamp () ; %>
            </td>
          </tr>
          <tr>
            <td class="caseNiveau3SansBordure">
              Charge initiale * :
            </td>
            <td>
              <input <transfert:transfertchamp membre="setChargeInitiale" type="java.lang.Double" libelle="Charge initiale" convertor="VDoubleConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBRETACHES %>"/>
               type="text" size="8" class="niveau2" value="" maxlength="<%= CConstante.TXT_CHARGE %>"><br/>
              <% lChampTacheChargeInitiale = VTransfert.getDernierChamp () ; %>
            </td>
          </tr>
          <tr>
            <td class="caseNiveau3SansBordure">
              Date de début prévue * :
            </td>
            <td>
              <input <transfert:transfertchamp membre="setDateDebutPrevue" type="java.util.Date" libelle="Date de début prévue" convertor="VDateConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBRETACHES %>"/>
               type="text" size="8" class="niveau2" value="" maxlength="<%= CConstante.TXT_DATE %>"><br/>
              <% lChampTacheDateDebutPrevue = VTransfert.getDernierChamp () ; %>
            </td>
          </tr>
          <tr>
            <td class="caseNiveau3SansBordure">
              Date de fin prévue * :
            </td>
            <td>
              <input <transfert:transfertchamp membre="setDateFinPrevue" type="java.lang.Date" libelle="Date de fin prévue" convertor="VDateConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBRETACHES %>"/>
               type="text" size="8" class="niveau2" value="" maxlength="<%= CConstante.TXT_DATE %>"><br/>
              <% lChampTacheDateFinPrevue = VTransfert.getDernierChamp () ; %>
            </td>
          </tr>
          </transfert:transfertbean>

          <tr>
            <td colspan="2">
              &nbsp;
            </td>
          </tr>
        
          <tr>
            <td class="caseNiveau3SansBordure">
              <!-- Activités et collaborateurs -->
              Discpline * :
              <select class="niveau2" name="<%= CConstante.PAR_LISTEDISCIPLINES %>" onchange="selectDiscipline (document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex) ;
                                                                                              selectActivite (document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex, document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.selectedIndex);
                                                                                              selectProduit (document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex, document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.selectedIndex, document.formIterationModif.<%= CConstante.PAR_LISTEPRODUITS %>.selectedIndex);">
              <% 
                 int lDisciplineCourante = 0 ;
                 for (int i = 0; i < lProcessus.getNbComposants (); i++)             
                 {
                   MComposant lComposant = lProcessus.getComposant(i);
                   
                   for (int j = 0; j < lComposant.getNbDefinitionsTravail (); j++)
                   {
              %>
                <option value="<%= lDisciplineCourante ++ %>"> <%= lComposant.getDefinitionTravail (j).getNom () %> </option>
              <%   
                   } 
                }
              %>
              </select>
            </td>
            
            <td class="caseNiveau3SansBordure">
              <!-- Données de l'activité. -->
              Activité * :
              <select class="niveau2" name="<%= CConstante.PAR_LISTEACTIVITES %>" onchange="selectActivite (document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex, document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.selectedIndex) ;
                                                                                            selectProduit (document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex, document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.selectedIndex, document.formIterationModif.<%= CConstante.PAR_LISTEPRODUITS %>.selectedIndex);">
              <%
                MDefinitionTravail lDefTravTmp = lProcessus.getComposant(0).getDefinitionTravail(0) ;
                for (int i = 0 ; i < lDefTravTmp.getNbActivites() ; i++)
                {
              %> 
                <option value="<%= lDefTravTmp.getActivite(i).getId() %>"> <%= lDefTravTmp.getActivite(i).getNom () %> </option>
              <%
                }
              %>
              </select>
            </td>
          </tr>
          <tr>
            <td colspan="2" class="caseNiveau3SansBordure">
              Collaborateur affecté * :
              <select class="niveau2" name="<%= CConstante.PAR_LISTECOLLABORATEURS %>">
              <%
                for (int i = 0; i < lProjet.getNbCollaborateurs (); i++) 
                {
              %>
              <option value="<%= lProjet.getCollaborateur (i).getId () %>"> <%= lProjet.getCollaborateur (i).getNom () %> &nbsp; <%= lProjet.getCollaborateur (i).getPrenom () %> </option>
              <%
                }
              %>
              </select>
            </td>
          </tr>
          <tr>
            <td colspan="2"> &nbsp;
            </td>
          </tr>
            <tr>
            <td colspan="2"> &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="2"> &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="2"> &nbsp;
            </td>
          </tr>
          
          <tr>
            <td>
              <!-- Liste des artefacts en sorties -->
              <transfert:transfertbean scope="Session" type="owep.modele.execution.MArtefact" bean="getArtefactSortie" idArbre="<%= CConstante.PAR_ARBREARTEFACTSORTIES %>">
              <p class="titre2">Artefacts en Sorties :</p>
            </td>
            <td>
              <p class="titre2">Artefacts en Entrées :</p>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%" cellpadding="0" cellspacing="0" valign="top">
                <tr>       
                  <td colspan="2" align="center">
                    <select class="niveau2" name="<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>" style="width: 80%" onchange="selectArtefact (document.formIterationModif.<%= CConstante.PAR_LISTETACHES %>.selectedIndex, document.formIterationModif.<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>.selectedIndex)" size="4">
                    </select>
                  </td>
                </tr>
                <tr>
                  <td width="50%" class="caseNiveau3SansBordure">     
                    Nom * :
                  </td>
                  <td>
                    <input <transfert:transfertchamp membre="setNom" type="java.lang.String" libelle="Nom" convertor="VStringConvertor" obligatoire="true" idArbre="<%= CConstante.PAR_ARBREARTEFACTSORTIES %>"/>
                     type="text" size="8" class="niveau2" value="" maxlength="<%= CConstante.TXT_MOYEN %>">
                    <br/>
                    <% lChampArtefactNom = VTransfert.getDernierChamp () ; %>
                  </td>
                </tr>
                  <td class="caseNiveau3SansBordure">
                    Description :
                  </td>
                  <td>
                    <input <transfert:transfertchamp membre="setDescription" type="java.lang.String" libelle="Description" convertor="VStringConvertor" obligatoire="false" idArbre="<%= CConstante.PAR_ARBREARTEFACTSORTIES %>"/>
                     type="text" size="8" class="niveau2" value="" maxlength="<%= CConstante.TXT_LARGE %>">
                    <br/>
                    <% lChampArtefactDescription = VTransfert.getDernierChamp () ; %>
                  </td>
                </tr>
              </transfert:transfertbean>
                <tr>
                  <td class="caseNiveau3SansBordure">
                    Produit * :
                  </td>
                  <td>
                    <select class="niveau2" name="<%= CConstante.PAR_LISTEPRODUITS %>" onchange="selectProduit (document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex, document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.selectedIndex, document.formIterationModif.<%= CConstante.PAR_LISTEPRODUITS %>.selectedIndex)">
                    <%
                      MActivite lActivite = lProcessus.getComposant(0).getDefinitionTravail(0).getActivite (0) ;
                      for (int i = 0 ; i < lActivite.getNbProduitsSorties () ; i++)
                      {
                    %> 
                      <option value="<%= i %>"> <%= lActivite.getProduitSortie (i).getNom () %> </option>
                    <%
                      }
                    %>
                    </select>
                    <br/>
                  </td>
                </tr>
                <tr>
                  <td class="caseNiveau3SansBordure">
                    Responsable * :
                  </td>
                  <td>
                    <select class="niveau2" name="<%= CConstante.PAR_LISTERESPONSABLES %>">
                    <%
                      MRole lRole = lProcessus.getComposant(0).getDefinitionTravail(0).getActivite (0).getProduitSortie (0).getResponsable () ;
                      for (int i = 0 ; i < lRole.getNbCollaborateurs () ; i++)
                      {
                    %> 
                      <option value="<%= i %>"> <%= lRole.getCollaborateur (i).getNom () + " &nbsp; " + lRole.getCollaborateur (i).getPrenom () %> </option>
                    <%
                      }
                    %>
                    </select>
                    <br/>
                  </td>
                </tr>
                <tr>
                  <td colspan="2">        
                    <!-- Barre d'outils d'artefacts -->
                    <% lCodeValidation = "validerArtefactSortie (document.formIterationModif." + CConstante.PAR_LISTETACHES + ", 'Attention aucune tâche n\\'a été sélectionnée.' ) ;" ; %>
                    <transfert:transfertsubmit libelle="Ajouter"   valeur="<%= CConstante.PAR_SUBMITAJOUTER_ARTSORTIES %>" verification="true" validation="<%= lCodeValidation %>"/>
                    
                    <% lCodeValidation = "validerArtefactSortie (document.formIterationModif." + CConstante.PAR_LISTEARTEFACTSSORTIES + ", 'Attention aucun artefact en sortie n\\'a été sélectionné.') ;" ; %>
                    <transfert:transfertsubmit libelle="Modifier"  valeur="<%= CConstante.PAR_SUBMITMODIFIER_ARTSORTIES %>" verification="true" validation="<%= lCodeValidation %>"/>
                    
                    <% lCodeValidation = "validerArtefactSortieSuppr (document.formIterationModif." + CConstante.PAR_LISTEARTEFACTSSORTIES + ", 'Attention aucun artefact en sortie n\\'a été sélectionné.') ;" ; %>
                    <transfert:transfertsubmit libelle="Supprimer" valeur="<%= CConstante.PAR_SUBMITSUPPRIMER_ARTSORTIES %>" verification="true" validation="<%= lCodeValidation %>"/>
                  </td>
                </tr>
              </table>    
              
            </td>
            <td>
            
              <!-- Liste des artefacts en entrées -->
              <table width="100%" height="100%" cellpadding="0" cellspacing="0" valign="top">
                <tr>
                  <td class="caseNiveau3SansBordure" width="50%">        
                    <p class="">Possibles :</p>
                  </td>
                  <td class="caseNiveau3SansBordure">       
                    Effectifs :
                  </td>
                </tr>
                <tr>
                  <td align="center">        
                    <select class="niveau2" name="<%= CConstante.PAR_LISTEARTEFACTSPOSSIBLES %>" style="width: 90%" size="9">
                    </select>
                  </td>
                  <td align="center">       
                    <select class="niveau2" name="<%= CConstante.PAR_LISTEARTEFACTSENTREES %>" style="width: 90%" size="9">
                    </select>
                  </td>
                </tr>
                <tr>
                  <td align="center">
                    <% lCodeValidation = "validerArtefactEntree (document.formIterationModif." + CConstante.PAR_LISTEARTEFACTSPOSSIBLES + ", 'Attention aucun artefact en entrée n\\'a été sélectionné.' ) ;" ; %>
                    <transfert:transfertsubmit libelle="Ajouter"  valeur="<%= CConstante.PAR_SUBMITAJOUTER_ARTENTREES %>" verification="true" validation="<%= lCodeValidation %>"/>
                  </td>
                  <td align="center">     
                    <% lCodeValidation = "validerArtefactEntreeSuppr (document.formIterationModif." + CConstante.PAR_LISTEARTEFACTSENTREES + ", 'Attention aucun artefact en entrée n\\'a été sélectionné.' ) ;" ; %>
                    <transfert:transfertsubmit libelle="Supprimer"  valeur="<%= CConstante.PAR_SUBMITSUPPRIMER_ARTENTREES %>" verification="true" validation="<%= lCodeValidation %>"/>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr> 
      <!-- Barre d'outils de tâches -->
      <td class="caseNiveau3">
        <% lCodeValidation = "validerTacheAjout () ;" ; %>
        <transfert:transfertsubmit libelle="Ajouter" valeur="<%= CConstante.PAR_SUBMITAJOUTER %>" verification="true" validation="<%= lCodeValidation %>"/>
        
        <% lCodeValidation = "validerTacheModif (document.formIterationModif." + CConstante.PAR_LISTETACHES + ", 'Attention aucune tâche n\\'a été sélectionnée.') ;" ; %>
        <transfert:transfertsubmit libelle="Modifier" valeur="<%= CConstante.PAR_SUBMITMODIFIER %>" verification="true" validation="<%= lCodeValidation %>"/>
        
        <% lCodeValidation = "validerTacheSuppr (document.formIterationModif." + CConstante.PAR_LISTETACHES + ", 'Attention aucune tâche n\\'a été sélectionnée.') ;" ; %>
        <transfert:transfertsubmit libelle="Supprimer" valeur="<%= CConstante.PAR_SUBMITSUPPRIMER %>" verification="true" validation="<%= lCodeValidation %>"/>
      </td>
    </tr>
  </tbody>
  </table>
  
  </transfert:transfertbean>

  <br>
  <p class="paragrapheSubmit">
    <transfert:transfertsubmit libelle="Valider" valeur="<%= CConstante.PAR_SUBMIT %>" verification="true" validation="validerFormulaire () ;"/>
  </p>
  
</form>




<!-- Code javascript -->
<script type="text/javascript" language="JavaScript">

  <!----------------------------------------->
  <!-- Ensemble des données de l'itération -->
  <!----------------------------------------->
  var gListeTaches = new Array () ;
  <%
  // Parcours la liste des tâches.
  for (int lIndiceTache = 0; lIndiceTache < lIteration.getNbTaches (); lIndiceTache ++)
  {
    MTache    lTache         = lIteration.getTache (lIndiceTache) ;
    MActivite lTacheActivite = lTache.getActivite () ;
    int lIdActivite             = lTacheActivite.getId () ;
    int lIndiceDefTravailAbsolu = 0 ;
    int lIndiceActivite = 0 ;
    
    // Recherche l'activité correspondant à celle instanciée par la tâche courante.
    boolean lTrouve     = false ;
    for (int lIndiceComposant = 0; (! lTrouve) && (lIndiceComposant < lProcessus.getNbComposants ()); lIndiceComposant ++)             
    {
      MComposant lComposant = lProcessus.getComposant (lIndiceComposant) ;
    
      // Parcours la liste des définitions de travail.
      for (int lIndiceDefTravail = 0; (! lTrouve) && (lIndiceDefTravail < lComposant.getNbDefinitionsTravail ()); lIndiceDefTravail ++)
      {
        MDefinitionTravail lDefinitionTravail = lComposant.getDefinitionTravail (lIndiceDefTravail) ;
        
        // Parcours la liste des activités.
        for (lIndiceActivite = 0; (! lTrouve) && (lIndiceActivite < lDefinitionTravail.getNbActivites ()); lIndiceActivite ++) 
        {
          MActivite lActivite = lDefinitionTravail.getActivite (lIndiceActivite) ;
          
          // Si on trouve l'activité liée à la tâche est trouvée dans la définition de travail, on met leurs indices dans le tableau.
          if (lActivite.getId () == lIdActivite)
          {
            lTrouve = true ;
          }
        }
        
        lIndiceDefTravailAbsolu ++ ;
      }
    }
    
    // Rectifie les indices.
    lIndiceDefTravailAbsolu -- ;
    lIndiceActivite -- ;
    
    // Recherche le collaborateur correspondant à celui réalisant la tâche courante.
    int lIdCollaborateur = lTache.getCollaborateur ().getId () ;
    lTrouve = false ;
    for (int lIndiceCollaborateur = 0; (! lTrouve) && (lIndiceCollaborateur < lProjet.getNbCollaborateurs ()); lIndiceCollaborateur ++) 
    {
      if (lProjet.getCollaborateur (lIndiceCollaborateur).getId () == lIdCollaborateur)
      {
  %>
        <!-- Données de la tâche. -->
        gListeTaches.push (new Array ("<%= lTache.getNom () %>", "<%=lTache.getDescription ()%>", "<%=lTache.getChargeInitiale () %>",
                                      "<%= VDateConvertor.getString (lTache.getDateDebutPrevue ()) %>", "<%= VDateConvertor.getString (lTache.getDateFinPrevue ()) %>",
                                      "<%= lIndiceDefTravailAbsolu %>", "<%= lIndiceActivite %>",
                                      "<%= lIndiceCollaborateur %>", new Array (), new Array())) ;
  <%
      }
    }
    
    
    // Parcours la liste des artefacts en entrées.
    for (int lIndiceArtefactEntree = 0; lIndiceArtefactEntree < lTache.getNbArtefactsEntrees (); lIndiceArtefactEntree ++)
    {
      MArtefact lArtefactEntree = lTache.getArtefactEntree (lIndiceArtefactEntree) ;
  %>
      <!-- Données de l'artefact en entrée -->
      gListeTaches[<%= lIndiceTache %>][9].push (new Array ("<%= lTache.getArtefactEntree (lIndiceArtefactEntree).getId () %>", "<%= lTache.getArtefactEntree (lIndiceArtefactEntree).getNom () %>")) ;
  <%
    }
    
    // Parcours la liste des artefacts en sorties.
    for (int lIndiceArtefactSortie = 0; lIndiceArtefactSortie < lTache.getNbArtefactsSorties (); lIndiceArtefactSortie ++)
    {
      MArtefact      lArtefactSortie = lTache.getArtefactSortie (lIndiceArtefactSortie) ;
      MCollaborateur lResponsable    = lArtefactSortie.getResponsable () ;
      lTrouve = false ;
      
      // Parcours la liste des produits en sorties.
      for (int lIndiceProduitSortie  = 0; (! lTrouve) && (lIndiceProduitSortie < lTacheActivite.getNbProduitsSorties ()); lIndiceProduitSortie ++) 
      {
        MProduit lProduit         = lTacheActivite.getProduitSortie (lIndiceProduitSortie) ;
        MRole    lRoleResponsable = lProduit.getResponsable () ;
        int      lIndiceResponsable ;
        
        // Si on a trouvé le produit contenant l'artefact courant,
        if (lProduit.getId () == lArtefactSortie.getProduit ().getId ()) 
        {
          // Parcour l'ensemble des collaborateurs tenant le rôle responsable de l'artefact.
          for (lIndiceResponsable = 0; (! lTrouve) && (lIndiceResponsable < lRoleResponsable.getNbCollaborateurs ()); lIndiceResponsable ++)
          {
            MCollaborateur lCollaborateur = lRoleResponsable.getCollaborateur (lIndiceResponsable) ;
            
            if (lCollaborateur.getId () == lResponsable.getId ()) 
            {
              lTrouve = true ;
            }
          }
          
          // Rectifie les indices.
          lIndiceResponsable -- ;
  %>
          <!-- Données de l'artefact en sortie -->
          gListeTaches[<%= lIndiceTache %>][8].push (new Array ("<%= lIndiceArtefactSortie %>", "<%= lArtefactSortie.getNom () %>",
                                                                "<%= lArtefactSortie.getDescription () %>", "<%= lIndiceProduitSortie %>",
                                                                "<%= lIndiceResponsable %>")) ;
  <%
        }
      }
    }
  }
  %>




  <!--------------------------------------->
  <!-- Ensemble des données du processus -->
  <!--------------------------------------->
  var gListeActivites = new Array () ;
  <%
  MComposant lComposant ;
  // Parcours la liste des composants.
  int lDisciplineCourante = 0 ;
  for (int lIndiceComposant = 0; lIndiceComposant < lProcessus.getNbComposants (); lIndiceComposant ++)
  {
    lComposant = lProcessus.getComposant(lIndiceComposant);
    
    // Parcours la liste des définition de travail.
    for (int lIndiceDefinitionTravail = 0; lIndiceDefinitionTravail < lComposant.getNbDefinitionsTravail (); lIndiceDefinitionTravail ++)
    {
      MDefinitionTravail lDefinitionTravail = lComposant.getDefinitionTravail (lIndiceDefinitionTravail) ;
  %>
      gListeActivites.push (new Array ()) ;
  <%
      for (int lIndiceActivite = 0; lIndiceActivite < lDefinitionTravail.getNbActivites (); lIndiceActivite ++)
      {
        MActivite lActivite = lComposant.getDefinitionTravail (lIndiceDefinitionTravail).getActivite(lIndiceActivite) ;
  %>
        <!-- Données de l'activité -->
        gListeActivites[<%= lDisciplineCourante %>].push (new Array ("<%= lActivite.getId () %>", "<%= lActivite.getNom () %>", new Array (), new Array ())) ;
  <%
        // Parcours des produits en sorties de l'activite.
        for (int lIndiceProduitSortie = 0; lIndiceProduitSortie < lActivite.getNbProduitsSorties(); lIndiceProduitSortie ++) 
        {
          MProduit lProduit = lActivite.getProduitSortie(lIndiceProduitSortie) ;
          MRole lRole = lProduit.getResponsable () ;
  %>
          <!-- Données du produit -->
          gListeActivites[<%= lDisciplineCourante %>][<%= lIndiceActivite %>][2].push (new Array ("<%= lIndiceProduitSortie %>", "<%= lProduit.getNom () %>", new Array ())) ;
  <%
          // Sauvegarde des collaborateurs suceptible d'être responsables du produits.
          for (int lIndiceCollaborateur = 0; lIndiceCollaborateur < lRole.getNbCollaborateurs (); lIndiceCollaborateur ++)
          {
            MCollaborateur lCollaborateur = lRole.getCollaborateur (lIndiceCollaborateur) ;
  %>
            <!-- Données du collaborateur -->
            gListeActivites[<%= lDisciplineCourante %>][<%= + lIndiceActivite %>][2][<%= lIndiceProduitSortie %>][2].push (new Array ("<%= lIndiceCollaborateur %>", "<%= lCollaborateur.getNom () %>", "<%= lCollaborateur.getPrenom () %>")) ;
  <%
          }
        }
        
        // Parcours la liste des artefact en entrées.
        for (int lIndiceProduitSortie = 0 ; lIndiceProduitSortie < lActivite.getNbProduitsEntrees (); lIndiceProduitSortie ++)
        {
          MProduit lProduitEntree = lActivite.getProduitEntree (lIndiceProduitSortie) ;
          
          for (int ll = 0 ; ll < lProduitEntree.getNbArtefacts (); ll ++)
          {
            MArtefact lArtEntreeTmp = lProduitEntree.getArtefact (ll) ;
  %>
            <!-- Données de l'artefact -->
            gListeActivites[<%= lDisciplineCourante %>][<%= lIndiceActivite %>][3].push (new Array ("<%= + lArtEntreeTmp.getId () %>", "<%= lArtEntreeTmp.getNom () %>")) ;
  <%
          }
        }
      }
      
      lDisciplineCourante ++ ;
    }
  }
  %>
  
  
  
  
  <!------------------------------------------------------->
  <!-- Fonctions de gestion des composants du formulaire -->
  <!------------------------------------------------------->
  function selectTache(pIndice)
  {
    // Efface les ancienne données.
    document.formIterationModif.<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>.length = 0 ;
    document.formIterationModif.<%= lChampArtefactNom %>.value = '' ;
    document.formIterationModif.<%= lChampArtefactDescription %>.value = '' ;
    
    // Données de la tâche.
    document.formIterationModif.<%= lChampTacheNom %>.value = gListeTaches[pIndice][0] ;
    document.formIterationModif.<%= lChampTacheDescription %>.value = gListeTaches[pIndice][1] ;
    document.formIterationModif.<%= lChampTacheChargeInitiale %>.value = gListeTaches[pIndice][2] ;
    document.formIterationModif.<%= lChampTacheDateDebutPrevue %>.value = gListeTaches[pIndice][3] ;
    document.formIterationModif.<%= lChampTacheDateFinPrevue %>.value = gListeTaches[pIndice][4] ;
    document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex = gListeTaches[pIndice][5] ;
    selectDiscipline (document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex) ;
    document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.selectedIndex = gListeTaches[pIndice][6] ;
    document.formIterationModif.<%= CConstante.PAR_LISTECOLLABORATEURS %>.selectedIndex = gListeTaches[pIndice][7] ;
    selectActivite (document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex, document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.selectedIndex) ;
    
    
    // Initialise la liste des artefacts en sorties.
    document.formIterationModif.<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>.length = 0 ;
    for (i = 0 ; i <  gListeTaches[pIndice][8].length ; i ++)
    {
      var option = new Option(gListeTaches [pIndice][8][i][1],gListeTaches [pIndice][8][i][0]) ;
      document.formIterationModif.<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>.options[i] = option ;
    }
    
    // Initialise la liste des artefacts en entrées.
    document.formIterationModif.<%= CConstante.PAR_LISTEARTEFACTSENTREES %>.length = 0 ;
    for (i = 0 ; i <  gListeTaches[pIndice][9].length ; i ++)
    {
      var option = new Option(gListeTaches [pIndice][9][i][1],gListeTaches [pIndice][9][i][0]) ;
      document.formIterationModif.<%= CConstante.PAR_LISTEARTEFACTSENTREES %>.options[i] = option ;
    }
    
    // Bloque la sélection d'une activité ou discipline si un artefact en sortie est associé à la tâche.
    if (document.formIterationModif.<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>.length > 0)
    {
      // Initialise la liste des artefacts et désactive les listes disciplines et activités.
      document.formIterationModif.<%= CConstante.PAR_LISTEARTEFACTSSORTIES %>.selectedIndex = 0 ;
      document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = true ;
      document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = true ;
      
      selectArtefact (document.formIterationModif.<%= CConstante.PAR_LISTETACHES %>.selectedIndex, 0) ;
    }
    else
    // Bloque la sélection d'une activité ou discipline si un artefact en entrée est associé à la tâche.
    if (document.formIterationModif.<%= CConstante.PAR_LISTEARTEFACTSENTREES %>.length > 0)
    {
      // Désactive les listes disciplines et activités.
      document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = true ;
      document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = true ;
    }
    else
    {
      // Active les listes disciplines et activités.
      document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = false ;
      document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = false ;
    }
  }
  
  
  function selectDiscipline (pIndice)
  {
    // Met à jour la liste des activités.
    document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.length = 0 ;
    for (i = 0 ; i <  gListeActivites[pIndice].length ; i++)
    {
      var option = new Option(gListeActivites [pIndice][i][1],gListeActivites [pIndice][i][0]) ;
      document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.options[i] = option ;
    }
  }
  
  
  function selectActivite (pIndiceDiscipline, pIndiceActivite)
  {
    document.formIterationModif.<%= CConstante.PAR_LISTEPRODUITS %>.length = 0 ;
    
    // Met à jour la liste des activités.
    for (lIndiceProduit = 0; lIndiceProduit <  gListeActivites[pIndiceDiscipline][pIndiceActivite][2].length; lIndiceProduit ++)
    {
      var option = new Option(gListeActivites [pIndiceDiscipline][pIndiceActivite][2][lIndiceProduit][1],
                              gListeActivites [pIndiceDiscipline][pIndiceActivite][2][lIndiceProduit][0]) ;
      document.formIterationModif.<%= CConstante.PAR_LISTEPRODUITS %>.options[lIndiceProduit] = option;
    }
    
    // Met à jour la liste des artefacts.
    document.formIterationModif.<%= CConstante.PAR_LISTEARTEFACTSPOSSIBLES %>.length = 0 ;          
    if (document.formIterationModif.<%= CConstante.PAR_LISTETACHES %>.selectedIndex != -1)
    {
      // Cherche l'artefact dans la liste des artefacts affectés à la tâche.
      for (lIndiceArtefact = 0; lIndiceArtefact < gListeActivites[pIndiceDiscipline][pIndiceActivite][3].length; lIndiceArtefact++)
      {
        var lPresent = 0 ;
        
        for (lIndiceTache = 0; lIndiceTache <  gListeTaches[document.formIterationModif.<%= CConstante.PAR_LISTETACHES %>.selectedIndex][9].length; lIndiceTache ++)
        {
          if (gListeActivites [pIndiceDiscipline][pIndiceActivite][3][lIndiceArtefact][0] == gListeTaches[document.formIterationModif.<%= CConstante.PAR_LISTETACHES %>.selectedIndex][9][lIndiceTache][0])
          {
             lPresent = 1 ;
          }
        }
        
        // Ajoute l'artefact à la liste des artefacts possible s'il n'est pas associé à la tâche.
        if (lPresent == 0)
        {
          var option = new Option(gListeActivites [pIndiceDiscipline][pIndiceActivite][3][lIndiceArtefact][1],
                                  gListeActivites [pIndiceDiscipline][pIndiceActivite][3][lIndiceArtefact][0]) ; 
          document.formIterationModif.<%= CConstante.PAR_LISTEARTEFACTSPOSSIBLES %>.options[document.formIterationModif.<%= CConstante.PAR_LISTEARTEFACTSPOSSIBLES %>.length] = option ;
        }
      }
    }
    else
    {
      for (lIndiceArtefact = 0; lIndiceArtefact < gListeActivites[pIndiceDiscipline][pIndiceActivite][3].length; lIndiceArtefact ++)
      {
        var option = new Option(gListeActivites [pIndiceDiscipline][pIndiceActivite][3][lIndiceArtefact][1],
                                gListeActivites [pIndiceDiscipline][pIndiceActivite][3][lIndiceArtefact][0]) ;
        document.formIterationModif.<%= CConstante.PAR_LISTEARTEFACTSPOSSIBLES %>.options[document.formIterationModif.<%= CConstante.PAR_LISTEARTEFACTSPOSSIBLES %>.length] = option ;
      }
    }
  }
  
  
  function selectProduit (pIndiceDiscipline, pIndiceActivite, pIndiceProduit)
  {
    document.formIterationModif.<%= CConstante.PAR_LISTERESPONSABLES %>.length = 0 ;
    for (lIndiceCollaborateur = 0; lIndiceCollaborateur <  gListeActivites[pIndiceDiscipline][pIndiceActivite][2][pIndiceProduit][2].length; lIndiceCollaborateur ++)
    { 
      var option = new Option(gListeActivites [pIndiceDiscipline][pIndiceActivite][2][pIndiceProduit][2][lIndiceCollaborateur][1] + '   ' +
                              gListeActivites [pIndiceDiscipline][pIndiceActivite][2][pIndiceProduit][2][lIndiceCollaborateur][2],
                              gListeActivites [pIndiceDiscipline][pIndiceActivite][2][pIndiceProduit][2][lIndiceCollaborateur][0]) ;
      document.formIterationModif.<%= CConstante.PAR_LISTERESPONSABLES %>.options[lIndiceCollaborateur] = option ;
    }
  }
  
  
  function selectArtefact(pIndiceTache, pIndiceArtefact)
  {
    document.formIterationModif.<%= lChampArtefactNom %>.value = gListeTaches[pIndiceTache][8][pIndiceArtefact][1] ;
    document.formIterationModif.<%= lChampArtefactDescription %>.value = gListeTaches[pIndiceTache][8][pIndiceArtefact][2] ;
    selectActivite (document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex, document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.selectedIndex) ;
    document.formIterationModif.<%= CConstante.PAR_LISTEPRODUITS %>.selectedIndex = gListeTaches[pIndiceTache][8][pIndiceArtefact][3] ;
    selectProduit (document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.selectedIndex, document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.selectedIndex, document.formIterationModif.<%= CConstante.PAR_LISTEPRODUITS %>.selectedIndex) ;
    document.formIterationModif.<%= CConstante.PAR_LISTERESPONSABLES %>.selectedIndex = gListeTaches[pIndiceTache][8][pIndiceArtefact][4] ;
  }
  
  
  
  
  <!------------------------------------------->
  <!-- Fonctions de validation du formulaire -->
  <!------------------------------------------->
  function validerTacheAjout ()
  {
    <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
    <%= VTransfertConstante.getVerification (CConstante.PAR_ARBRETACHES) %> () ;
    validerChamps () ;  
  }
  
  
  function validerTacheModif (pSelect, pMessage)
  {
    if (pSelect.selectedIndex == -1)
    {
      alert (pMessage) ;
    }
    else
    {
      document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = false ;
      document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = false ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBRETACHES) %> () ;
      validerChamps () ;
    }
  }
  
  
  function validerTacheSuppr (pSelect, pMessage)
  {
    if (pSelect.selectedIndex == -1)
    {
      alert (pMessage) ;
    }
    else
    {
      document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = false ;
      document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = false ;
      document.formIterationModif.submit () ;
    }
  }
  
  
  function validerArtefactSortie (pSelect, pMessage)
  {
    if (pSelect.selectedIndex == -1)
    {
      alert (pMessage) ;
    }
    else
    {
      document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = false ;
      document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = false ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREARTEFACTSORTIES) %> () ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBRETACHES) %> () ;
      validerChamps () ;
    }
  }
  
  
  function validerArtefactSortieSuppr (pSelect, pMessage)
  {
    if (pSelect.selectedIndex == -1)
    {
      alert (pMessage) ;
    }
    else
    {
      document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = false ;
      document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = false ;
      document.formIterationModif.submit () ;
    }
  }
  
  
  function validerArtefactEntree (pSelect, pMessage)
  {
    if (pSelect.selectedIndex == -1)
    {
      alert (pMessage) ;
    }
    else
    {
      document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = false ;
      document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = false ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBRETACHES) %> () ;
      validerChamps () ;
    }
  }
  
  
  function validerArtefactEntreeSuppr (pSelect, pMessage)
  {
    if (pSelect.selectedIndex == -1)
    {
      alert (pMessage) ;
    }
    else
    {
      document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = false ;
      document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = false ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
      <%= VTransfertConstante.getVerification (CConstante.PAR_ARBRETACHES) %> () ;
      validerChamps () ;
    }
  }
  
  
  function validerFormulaire ()
  {
    document.formIterationModif.<%= CConstante.PAR_LISTEDISCIPLINES %>.disabled = false ;
    document.formIterationModif.<%= CConstante.PAR_LISTEACTIVITES %>.disabled = false ;
    <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREITERATION) %> () ;
    <%= VTransfertConstante.getVerification (CConstante.PAR_ARBREARTEFACTSORTIES) %> () ;
    <%= VTransfertConstante.getVerification (CConstante.PAR_ARBRETACHES) %> () ;
    validerChamps () ;
  }
  
</script>