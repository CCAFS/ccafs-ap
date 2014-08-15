[#ftl]
[#assign title = "Project Partners" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/preplanning/projectPartners.js"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "projects" /]
[#assign currentStage = "partners" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/projectPartnersTemplate.ftl" as partnersTemplate /]

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="preplanning.projectPartners.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl" /]
  
  [@s.form action="partners" cssClass="pure-form"]
  <article class="halfContent" id="mainInformation">
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GranProjectAccessInterceptor--]
    [#if !saveable]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"]
          [@s.param][@s.text name="preplanning.project"/][/@s.param]
        [/@s.text]
      </p>
    [/#if]
  
  	[#include "/WEB-INF/preplanning/projectPreplanningSubMenu.ftl" /]
  	<h1 class="contentTitle">
       ${project.composedId} [#if project.title?has_content] - ${project.title}[/#if]
    </h1>
  	<h1 class="contentSubTitle">
      [@s.text name="preplanning.projectPartners.leader.title" /]  
    </h1>
  	[#-- Displaying partner leader from partnersTemplate.ftl --]  	
    [@s.hidden id="isExpected" name="expected" /]
    [#if expected]
      [@partnersTemplate.projectLeader leader=project.expectedLeader canEdit=true /]
    [#else]
      [@partnersTemplate.projectLeader leader=project.leader canEdit=true /]
    [/#if] 
    
    <h1 class="contentSubTitle">
		  [@s.text name="preplanning.projectPartners.partners.title" /]  
    </h1>
    [#-- Listing partners from partnersTemplate.ftl --]
    [@partnersTemplate.partnerSection projectPartners=project.projectPartners partnerTypes=partnerTypes countries=countries canEdit=true canRemove=true /]
    
    [#-- Showing add buttons only to users with enough privileges. See GranProjectAccessInterceptor--]
    [#if saveable]
      <div id="addProjectPartner" class="addLink">
        <a href="" class="addProjectPartner addButton" >[@s.text name="preplanning.projectPartners.addProjectPartner" /]</a>
      </div>
    
      [#-- Internal parameter --]
      <input name="projectID" type="hidden" value="${project.id?c}" />
    	<div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [#-- @s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit --]
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
  [@partnersTemplate.partnerTemplate /]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]