[#ftl]
[#assign title = "Project Partners" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/usersManagement.js", "${baseUrl}/js/planning/projectPartners.js"] /]

[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "description" /]
[#assign currentSubStage = "partners" /]
[#assign partnerStage = "partnerLead" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"planning", "action":"projectsList"},
  {"label":"description", "nameSpace":"planning/projects", "action":"description", "param":"projectID=${project.id}"},
  {"label":"partners", "nameSpace":"planning/projects", "action":"partnerLead", "param":"projectID=${project.id}"}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/usersPopup.ftl" as usersForm/]
[#import "/WEB-INF/global/macros/projectPartnersTemplate.ftl" as partnersTemplate /]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="planning.projectPartners.leader.help" /]</p>
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
  
  [@s.form action="partnerLead" cssClass="pure-form"]
  <article class="halfContent" id="projectPartners">
    [#include "/WEB-INF/planning/projectDescription-planning-sub-menu.ftl" /]
    [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
    
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GranProjectPlanningAccessInterceptor --]
    [#if !canEdit]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"]
          [@s.param][@s.text name="preplanning.project"/][/@s.param]
        [/@s.text]
      </p>
    [/#if]
    
    <div id="PartnersTabs" class=""> 
      [#-- Project Partners Sub-menu --]
      [#include "/WEB-INF/planning/projectPartners-sub-menu.ftl" /]
      <div id="partnerTables-partnerLead" class="partnerTable borderBox clearfix">  
        [#if !editable && canEdit]
          <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
        [/#if]  
        [#-- Displaying partner leader from projectPartnersTemplate.ftl --]  
        [@partnersTemplate.projectLeader leader=project.leader coordinator=project.coordinator showResponsabilities=true editable=editable /] 
      </div>
    </div>
    
    <div id="lessons" class="borderBox">
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]#lessons">[@s.text name="form.buttons.edit" /]</a></div>
      [/#if]
      <div class="fullBlock">
        [@customForm.textArea name="project.partnershipsLessons" i18nkey="planning.projectPartners.lessons" required=true editable=editable /]
      </div>
    </div>
    
    [#if editable] 
      [#-- Project identifier --]
      <div class="borderBox">
        <input name="projectID" type="hidden" value="${project.id?c}" />
        [@customForm.textArea name="justification" i18nkey="saving.justification" required=true className="justification"/]
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
  </article>
  [/@s.form] 

  [#-- Search users Interface Popup --]
  [@usersForm.searchUsers isActive=true/]
  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]
