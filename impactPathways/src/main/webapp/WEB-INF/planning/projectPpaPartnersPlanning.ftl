[#ftl]
[#assign title = "Project Partners" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/projectPartners.js"] /]

[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "description" /]
[#assign currentSubStage = "partners" /]
[#assign partnerStage = "ppaPartners" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projects"},
  {"label":"projects", "nameSpace":"planning", "action":"projectsList"},
  {"label":"partners", "nameSpace":"planning/projects", "action":""}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/usersPopup.ftl" as usersForm/]
[#import "/WEB-INF/global/macros/projectPartnersTemplate.ftl" as partnersTemplate /]

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.projectPartners.help1" /] <a href="[@s.url namespace="/" action='glossary'][/@s.url]#partners">[@s.text name="planning.projectPartners.partners" /]</a> [@s.text name="planning.projectPartners.help2" /]</p>
    <p> [@s.text name="planning.projectPartners.help3" /] <a href="[@s.url namespace="/" action='glossary'][/@s.url]#managementLiaison">[@s.text name="planning.projectPartners.managementLiaison" /]</a> [@s.text name="planning.projectPartners.help4" /]</p>
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]

  [@s.form action="partners" cssClass="pure-form"]
  <article class="halfContent" id="projectPartners">
    [#include "/WEB-INF/planning/projectDescription-planning-sub-menu.ftl" /]
    [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
    
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GranProjectPlanningAccessInterceptor--]
    [#if !saveable]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"]
          [@s.param][@s.text name="preplanning.project"/][/@s.param]
        [/@s.text]
      </p>
    [/#if]
    
    <div id="PartnersTabs" class="ui-tabs ui-widget ui-widget-content ui-corner-all"> 
      [#-- Project Partners Sub-menu --]
      [#include "/WEB-INF/planning/projectPartners-sub-menu.ftl" /]
      <div id="partnerTables-partnerLead" class="partnerTable ui-tabs-panel ui-widget-content ui-corner-bottom clearfix"> 
          <h1 class="contentTitle">
            [@s.text name="preplanning.projectPartners.partners.title" /]  
          </h1> 
          [#-- Listing partners from partnersTemplate.ftl --]
          [@partnersTemplate.partnerSection projectPartners=project.projectPartners partnerTypes=partnerTypes countries=countries responsabilities=true canEdit=fullEditable canRemove=saveable ppaPartners=true /]
          
          [#if saveable] 
            [#if fullEditable]
            <div id="addProjectPartner" class="addLink">
              <a href="" class="addProjectPartner addButton" >[@s.text name="preplanning.projectPartners.addProjectPartner" /]</a>
            </div>
            [/#if]
          [/#if]  
      </div>
    </div>   
    
    [#if saveable]  
      [#-- Internal parameter --]
      <input name="projectID" type="hidden" value="${project.id?c}" />
    	<div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
      <p id="addPartnerText" class="helpMessage">
        [@s.text name="preplanning.projectPartners.addPartnerMessage.first" /]
        <a class="popup" href="[@s.url action='partnerSave'][@s.param name='${projectRequest}']${project.id?c}[/@s.param][/@s.url]">
          [@s.text name="preplanning.projectPartners.addPartnerMessage.second" /]
        </a>
      </p>
    [/#if]
  </article>
  [/@s.form] 
  [#-- Single partner TEMPLATE from partnersTemplate.ftl --]
  [@partnersTemplate.partnerTemplate showResponsabilities=true /]  
  [#-- Search users Interface --]
  [@usersForm.searchUsers/]
  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]