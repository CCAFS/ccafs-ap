[#ftl]
[#assign title = "Project Deliverable" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/usersManagement.js", "${baseUrl}/js/planning/projectDeliverablesPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "outputs" /]
[#assign currentSubStage = "deliverables" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projectOutputs", "nameSpace":"planning/projects", "action":"outputs", "param":"projectID=${project.id}"},
  {"label":"projectDeliverables", "nameSpace":"planning/projects", "action":"deliverablesList", "param":"projectID=${project.id}"}
  {"label":"projectDeliverable", "nameSpace":"planning/projects", "action":"deliverable", "param":"deliverableID=${deliverable.id}"}
]/]

[#assign params = {
  "deliverable":  {"id":"deliverableName", "name":"deliverable"},
  "responsiblePartner":  {"id":"responsiblePartnerName", "name":"deliverable.responsiblePartner"},
  "nextUsers":    {"id":"nextUsersName",   "name":"deliverable.nextUsers"},
  "partners":     {"id":"partnersName",    "name":"deliverable.otherPartners"} 
}
/] 

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/usersPopup.ftl" as usersForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
[#import "/WEB-INF/planning/macros/projectDeliverablesTemplate.ftl" as deliverableTemplate/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.deliverables.help" /] 
    <a href = [@s.url namespace="/" action='glossary'][/@s.url]>[@s.text name="planning.deliverables.help2" /]</a>
    </p>
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
  
  [@s.form action="deliverable" cssClass="pure-form"]
  <article class="halfContent" id="projectDeliverable"> 
    [#include "/WEB-INF/planning/projectOutputs-sub-menu.ftl" /]
    [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
    [#-- Informing user that he-she does not have enough privileges to edit. See GrantProjectPlanningAccessInterceptor --]  
    [#if !canEdit]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"][@s.param][@s.text name=title /][/@s.param][/@s.text]
      </p>
    [/#if]
    [#--  Deliverable Information --] 
    <div id="deliverable-information" class="borderBox clearfix"> 
      [#if !editable && canEdit]
        <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [/#if]
      <h1 class="contentTitle">[@s.text name="planning.projectDeliverable.information" /] </h1>  
      <div class="fullBlock">
        [#-- Title --] 
        [@customForm.input name="${params.deliverable.name}.title" i18nkey="planning.deliverables.title" required=true editable=editable /]
      </div>
      <div class="fullBlock">
        [#-- MOG  --]
        <div class="${editable?string('halfPartBlock','fullBlock')} chosen"> 
          [@customForm.select name="${params.deliverable.name}.output" label=""  disabled=false i18nkey="planning.deliverables.mog" listName="outputs" keyFieldName="id"  displayFieldName="description" editable=editable /]
        </div> 
        [#-- Year  --]
        <div class="halfPartBlock chosen">
          [@customForm.select name="${params.deliverable.name}.year" value="${deliverable.year}" label=""  disabled=false i18nkey="planning.deliverables.year" listName="allYears" editable=editable /]
          [#if !editable]${deliverable.year}[/#if]
        </div>
      </div> 
      <div class="fullBlock">
        [#assign deliverableType][#if deliverable.type??]${deliverable.type.category.id}[#else]-1[/#if][/#assign]
        [#assign deliverableSubType][#if deliverable.type??]${deliverable.type.id}[#else]-1[/#if][/#assign]
        [#-- Main Type --]
        <div class="halfPartBlock chosen"> 
          [@customForm.select name="mainType" value="${deliverableType}" i18nkey="planning.deliverables.mainType" listName="deliverableTypes" keyFieldName="id"  displayFieldName="name" editable=editable /]
          [#if !editable]${deliverable.type.category.name}[/#if]
        </div> 
        [#-- Sub Type --]
        <div class="halfPartBlock chosen"> 
          [@customForm.select name="${params.deliverable.name}.type" value="${deliverableSubType}" i18nkey="planning.deliverables.subType" listName="" keyFieldName=""  displayFieldName="" editable=editable /]
          [#if !editable][#if deliverable.typeOther??]${(deliverable.typeOther)!}[#else]${deliverable.type.name}[/#if][/#if]
          <input type="hidden" id="subTypeSelected" value="${deliverableSubType}" />
          [#-- Specify other deliverable type--] 
          [@customForm.input name="${params.deliverable.name}.typeOther" value="${(deliverable.typeOther)!}" className="otherType" display=false showTitle=false i18nkey="planning.deliverables.specify" required=true disabled=true editable=editable /]          
        </div> 
      </div>
      [#if canEdit && !action.canDelete()]
        <div class="note left"><p>[@s.text name="planning.deliverables.disclaimerMessage" /]</p></div>
      [/#if]
    </div>
    
    [#-- Deliverable Next Users block  --]
    <div id="deliverable-nextUsers" class="borderBox clearfix">
      [#if !editable && canEdit]
        <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [/#if]
      <div class="fullBlock">
        <h1 class="contentTitle">[@s.text name="planning.projectDeliverable.nextUsers" /] </h1> 
        [#if deliverable.nextUsers?has_content]
          [#list deliverable.nextUsers as nu] 
            [@deliverableTemplate.nextUserTemplate nu_name=params.nextUsers.name nu_index="${nu_index}" nextUserValue="${nu.id}" editable=editable canEdit=canEdit /]
          [/#list]
        [#else]
          [@deliverableTemplate.nextUserTemplate nu_name=params.nextUsers.name nu_index="0" nextUserValue="-1" editable=editable canEdit=canEdit /] 
        [/#if]
        [#if editable && canEdit]
          <div id="addNextUserBlock" class="addLink"><a href=""  class="addNextUser addButton">[@s.text name="planning.deliverables.addNewUser" /]</a></div>
        [/#if] 
      </div>
    </div>  
    
    [#-- Deliverable partnership  --]
    <div id="deliverable-partnership" class="borderBox clearfix">
      [#if !editable && canEdit]
        <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [/#if]
      <h1 class="contentTitle">[@s.text name="planning.projectDeliverable.partnership" /] </h1> 
      <div class="fullBlock">
        [#-- Partner who is responsible --]
        <div class="fullBlock">
          <p>[@customForm.text name="planning.projectDeliverable.indicateResponsablePartner" readText=!editable/]</p>
          [#if deliverable.deliverablePartners?has_content]
          [@deliverableTemplate.deliverablePartner dp=deliverable.responsiblePartner dp_name=params.responsiblePartner.name dp_index=dp_index institutionList="institutions" isResponsable=true editable=editable /]
        [/#if]
        </div>
        [#-- Other contact person that will contribute --]
        <p>[@customForm.text name="planning.projectDeliverable.indicateOtherContact" readText=!editable/]</p>
        <div class="simpleBox">
          [#if deliverable.otherPartners?has_content]
            [#list deliverable.otherPartners as dp]  
              [@deliverableTemplate.deliverablePartner dp=dp dp_name=params.partners.name dp_index=dp_index editable=editable /]
            [/#list]
          [#else]
            <p class="emptyText center"> [@s.text name="planning.projectDeliverable.partnership.emptyText" /]</p>
          [/#if]
          [#if editable && canEdit]
            <div id="addPartnerBlock" class="addLink"><a href=""  class="addPartner addButton">[@s.text name="planning.deliverables.addPartner" /]</a></div>
          [/#if]
        </div>
      </div>
      [#if editable]
      <div class="partnerListMsj note">
        [@s.text name="preplanning.projectBudget.partnerNotList" /]
        <a href="[@s.url action='partners' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]"> 
          [@s.text name="preplanning.projectBudget.partnersLink" /] 
        </a>
      </div>
      [/#if]
    </div>
    
    <div id="lessons" class="borderBox">
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [/#if]
      <div class="fullBlock">
        [@customForm.textArea name="project.projectDeliverableLessons" i18nkey="planning.projectDeliverable.lessons" required=true editable=editable /]
      </div>
    </div>
    
    [#if editable] 
      <div class="borderBox"> 
        <input name="projectID" type="hidden" value="${project.id?c}" />
        <input name="deliverableID"type="hidden" value="${deliverable.id}">
        [@customForm.textArea name="justification" i18nkey="saving.justification" required=true className="justification"/]
        <div class="buttons">
          [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
          [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
          [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
        </div>
      </div>
    [#else]
        [#-- Display Log History --]
        [#if history??][@log.logList list=history /][/#if]   
    [/#if]
  </article>
  [/@s.form] 
   
</section> 

[#-- Internal parameters --]   
[#list params?keys as prop]
  <input id="${params[prop].id}" type="hidden" value="${params[prop].name}" /> 
[/#list]

[#-- Project deliverable Next user Template--]
[@deliverableTemplate.nextUserTemplate template=true /]

[#-- Deliverable Partner Template--]
[@deliverableTemplate.deliverablePartner dp={} dp_name=params.partners.name dp_index=dp_index institutionList="institutions" template=true /]

[#-- Search users Interface Popup --]
[@usersForm.searchUsers isActive=false/]

[#include "/WEB-INF/global/pages/footer.ftl"]
