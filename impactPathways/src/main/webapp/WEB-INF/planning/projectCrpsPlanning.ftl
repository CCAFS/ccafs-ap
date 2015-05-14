[#ftl]
[#assign title = "Project Partners" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/projectPartners.js"] /]

[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "description" /]
[#assign currentSubStage = "partners" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projects"},
  {"label":"projects", "nameSpace":"planning", "action":"projects"},
  {"label":"partners", "nameSpace":"planning/projects", "action":""}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
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
      <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all"> 
          <li id="" class="partnerTab ui-state-default ui-corner-top ">
            <a href="[@s.url action='partnerLead' includeParams='get'][/@s.url]"> Partner Lead </a>
          </li>
          <li id="" class="partnerTab ui-state-default ui-corner-top ">
            <a href="[@s.url action='ppaPartners' includeParams='get'][/@s.url]"> PPA Partners </a>
          </li> 
          <li id="" class="partnerTab ui-state-default ui-corner-top">
            <a href="[@s.url action='partners' includeParams='get'][/@s.url]"> Project Partners </a>
          </li> 
          <li id="" class="partnerTab ui-state-default ui-corner-top ui-tabs-active ui-state-active ui-state-hover">
            <a href="[@s.url action='crps' includeParams='get'][/@s.url]"> CRPs </a>
          </li> 
      </ul>
      <div id="partnerTables-partnerLead" class="partnerTable ui-tabs-panel ui-widget-content ui-corner-bottom clearfix"> 
        
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
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]