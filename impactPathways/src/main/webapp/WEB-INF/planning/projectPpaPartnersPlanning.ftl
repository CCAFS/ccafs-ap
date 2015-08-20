[#ftl]
[#assign title = "Project Partners" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/usersManagement.js", "${baseUrl}/js/planning/projectPartners.js"] /]

[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "description" /]
[#assign currentSubStage = "partners" /]
[#assign partnerStage = "ppaPartners" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"planning", "action":"projectsList"},
  {"label":"description", "nameSpace":"planning/projects", "action":"description", "param":"projectID=${project.id}"},
  {"label":"ccafsPartners", "nameSpace":"planning/projects", "action":"ppaPartners", "param":"projectID=${project.id}"}
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
    <p> [@s.text name="planning.projectPartners.ccafsPartners.help" /]</p>
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]

  [@s.form action="ppaPartners" cssClass="pure-form"]
  <article class="halfContent" id="projectPartners">
    [#include "/WEB-INF/planning/projectDescription-planning-sub-menu.ftl" /]
    [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
    
    [#if !project.bilateralProject]
      [#-- Informing user that he/she doesn't have enough privileges to edit. See GranProjectPlanningAccessInterceptor--]
      [#if !canEdit]
        <p class="readPrivileges">
          [@s.text name="saving.read.privileges"]
            [@s.param][@s.text name="preplanning.project"/][/@s.param]
          [/@s.text]
        </p>
      [/#if]
      [#include "/WEB-INF/planning/projectPartners-sub-menu.ftl" /]
      <div id="PartnersTabs" class="simpleBox"> 
        [#-- Project Partners Sub-menu --]
        <div id="partnerTables-partnerLead" class="partnerTable clearfix"> 
          [#-- Listing partners from partnersTemplate.ftl --]
          [@partnersTemplate.partnerSection projectPartners=project.PPAPartners ap_name='project.PPAPartners' editable=editable partnerTypes=partnerTypes countries=countries ppaPartner=true responsabilities=true /]
          [#if (editable && canEdit)] 
            <div id="addProjectPartner" class="addLink">
              <a href="" class="addProjectPartner addButton" >[@s.text name="preplanning.projectPartners.addProjectPartner" /]</a>
            </div>
          [/#if]  
        </div>
      </div>
      
      [#if !newProject]
      <div id="lessons" class="borderBox">
        [#if (!editable && canEdit)]
          <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
        [/#if]
        <div class="fullBlock">
          <input type="hidden" name="projectLessons.id" value=${(projectLessons.id)!"-1"} />
          <input type="hidden" name="projectLessons.year" value=${currentPlanningYear} />
          <input type="hidden" name="projectLessons.componentName" value="${actionName}">
          [@customForm.textArea name="projectLessons.lessons" i18nkey="planning.projectPartners.lessons" required=!project.bilateralProject editable=editable /]
        </div>
      </div>
      [/#if]
      
      [#if editable]  
        [#-- Project identifier --]
        <input name="projectID" type="hidden" value="${project.id?c}" />
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
    [#else]
      <p class="simpleBox center">[@s.text name="planning.projectPartners.cantAllowCCAFSPartners" /]</p>  
    [/#if]
  </article>
  [/@s.form] 
  [#-- Hidden Parameters Interface --]
  <input id="partners-name" type="hidden" value="project.PPAPartners" />
  [#-- Single partner TEMPLATE from partnersTemplate.ftl --]
  [@partnersTemplate.partnerTemplate isPPA=true showResponsabilities=true /]  
  [#-- Search users Interface --]
  [@usersForm.searchUsers/]
  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]
