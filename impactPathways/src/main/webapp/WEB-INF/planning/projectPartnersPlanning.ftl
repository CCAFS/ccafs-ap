[#ftl]
[#assign title = "Project Partners" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/projectPartners.js", "${baseUrl}/js/global/usersManagement.js"] /]

[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "description" /]
[#assign currentSubStage = "partners" /]
[#assign partnerStage = "partners" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"planning", "action":"projectsList"},
  {"label":"description", "nameSpace":"planning/projects", "action":"description", "param":"projectID=${project.id}"},
  {"label":"projectPartners", "nameSpace":"planning/projects", "action":"partners", "param":"projectID=${project.id}"}
]/]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
[#import "/WEB-INF/global/macros/usersPopup.ftl" as usersForm/]
[#import "/WEB-INF/global/macros/projectPartnersTemplate.ftl" as partnersTemplate /]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]


<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" /><p>[@s.text name="planning.projectPartners.otherPartners.help" /]</p>
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
  
  <article class="halfContent" id="projectPartners">
  [@s.form action="partners" cssClass="pure-form"]
    [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GranProjectPlanningAccessInterceptor--]
    [#if submission?has_content]
      <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
    [#elseif !canEdit ]
      <p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param][@s.text name="preplanning.project"/][/@s.param][/@s.text]</p>
    [/#if]
    
    [#-- Listing Partners from partnersTemplate.ftl --]
    <h1 class="contentTitle">[@s.text name="planning.projectPartners.subMenu.partners" /]</h1>
    <div class="loadingBlock"></div>
    <div id="projectPartnersBlock" class="simpleBox" style="display:none">
      [#if project.projectPartners?has_content]
        [#list project.projectPartners as projectPartner]
          [@partnersTemplate.projectPartner projectPartner=projectPartner projectPartnerName="project.projectPartners" projectPartnerIndex="${projectPartner_index}" /]
        [/#list]
      [#else]
        [#if !editable]
          <p class="center">[@s.text name="planning.projectPartners.empty" /]
          <a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.clickHere" /]</a> [@s.text name="planning.projectPartners.switchEditingMode" /]
          </p>
        [/#if]
      [/#if] 
      [#if (editable && canEdit)]
        <div id="addProjectPartner" class="addLink">
          <a href="" class="addProjectPartner addButton" >[@s.text name="preplanning.projectPartners.addProjectPartner" /]</a>
        </div> 
      [/#if]
    </div>
    
    [#if !newProject]
    <div id="lessons" class="borderBox">
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#lessons">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
          <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]#lessons">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if] 
      <div class="fullBlock">
        <input type="hidden" name="projectLessons.id" value=${(projectLessons.id)!"-1"} />
        <input type="hidden" name="projectLessons.year" value=${currentPlanningYear} />
        <input type="hidden" name="projectLessons.componentName" value="${actionName}">
        [@customForm.textArea name="projectLessons.lessons" i18nkey="planning.projectPartners.lessons" required=!project.bilateralProject editable=editable /]
      </div>
    </div>
    [/#if]
    
    [#-- Project identifier --]
    <input name="projectID" type="hidden" value="${project.id?c}" />
    [#if editable]  
      <div class="clearfix [#if !newProject]borderBox[/#if]" >
        [#if !newProject] [@customForm.textArea name="justification" i18nkey="saving.justification" required=true className="justification"/][/#if]
        <div class="buttons">
          [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
          [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
          [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
        </div>
      </div>
      <p id="addPartnerText" class="helpMessage">
        [@s.text name="preplanning.projectPartners.addPartnerMessage.first" /]
        <a class="popup" href="[@s.url action='partnerSave'][@s.param name='${projectRequest}']${project.id?c}[/@s.param][/@s.url]">
          [@s.text name="preplanning.projectPartners.addPartnerMessage.second" /]
        </a>
      </p>
    [#else]
      [#-- Display Log History --]
      [#if history??][@log.logList list=history /][/#if] 
    [/#if]
  [/@s.form] 
  </article>
  
  [#-- Hidden Parameters Interface --]
  <input id="partners-name" type="hidden" value="project.projectPartners" />
  
  [#-- Single partner TEMPLATE from partnersTemplate.ftl --]
  [@partnersTemplate.projectPartner projectPartnerName="project.projectPartners" template=true /]
  
  [#-- Contact person TEMPLATE from partnersTemplate.ftl --]
  [@partnersTemplate.contactPerson contactName="contactPerson" template=true /]
  
  [#-- PPA list Template --]
  <ul style="display:none">
    <li id="ppaListTemplate" class="clearfix">
      <input class="id" type="hidden" name="" value="" />
      <span class="name"></span> 
      [#if editable]<span class="listButton remove">[@s.text name="form.buttons.remove" /]</span>[/#if] 
    </li>
  </ul>
  
  [#-- allPPAInstitutions --]
  <input type="hidden" id="allPPAInstitutions" value="[[#list allPPAInstitutions as item]${item.id}[#if item_has_next],[/#if][/#list]]"/>
  
  [#-- Can update PPA Partners --]
  <input type="hidden" id="canUpdatePPAPartners" value="${(securityContext.canUpdatePPAPartners(project.id) || project.bilateralProject)?string}"/>
  
  [#-- Project PPA Partners --]
  <select id="projectPPAPartners" style="display:none">
  [#if project.PPAPartners??]
    [#list project.PPAPartners as ppaPartner]<option value="${ppaPartner.institution.id}">${ppaPartner.institution.getComposedName()}</option>[/#list]
  [/#if]  
  </select>
  
  [#-- Remove Partner Dialog --]
  <div id="partnerRemove-dialog" title="Remove partner" style="display:none">
    <p>[@s.text name="planning.projectPartners.partnerCannotBeDeleted" /]</p>
    <ul class="messages"></ul>
  </div>
  
  [#-- Remove partner person leader dialog --]
  <div id="contactRemove-dialog" title="Remove person" style="display:none">
    <p>[@s.text name="planning.projectPartners.personCannotBeDeleted" /]</p>
    <ul class="messages"></ul>
  </div>
  
  [#-- Change partner person email dialog --]
  <div id="contactChange-dialog" title="Change contact person" style="display:none">
    <ul class="messages"></ul>
  </div>
  
  [#-- Change partner person type dialog --]
  <div id="contactChangeType-dialog" title="Change person type" style="display:none">
    <ul class="messages"></ul>
  </div>
  
  [#-- Partner person relations dialog --]
  <div id="relations-dialog" title="Leading Activities/Deliverables" style="display:none">
  </div>
  
  [#-- Search users Interface --]
  [@usersForm.searchUsers/]
  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]
