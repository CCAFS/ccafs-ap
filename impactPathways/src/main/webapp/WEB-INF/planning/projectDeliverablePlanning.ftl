[#ftl]
[#assign title = "Project Deliverable" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/projectDeliverablesPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "outputs" /]
[#assign currentSubStage = "deliverables" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projectOutputs", "nameSpace":"planning/projects", "action":"outputs", "param":"projectID=${project.id}"},
  {"label":"projectDeliverables", "nameSpace":"planning/projects", "action":"deliverablesList", "param":"projectID=${project.id}"}
  {"label":"projectDeliverable", "nameSpace":"planning/projects", "action":"deliverable", "param":"projectID=${project.id}"}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
[#import "/WEB-INF/planning/macros/projectDeliverablesTemplate.ftl" as deliverableTemplate/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.projectDeliverables.help" /] </p>
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
  
  [@s.form action="deliverables" cssClass="pure-form"]
  <article class="halfContent" id="mainInformation"> 
    [#include "/WEB-INF/planning/projectOutputs-sub-menu.ftl" /]
    [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantProjectPlanningAccessInterceptor--]
    [#if !canEdit]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"]
          [@s.param][@s.text name="planning.project"/][/@s.param]
        [/@s.text]
      </p>
    [/#if]
    <div id="projectDeliverable" class="borderBox clearfix"> 
      [#if !editable]
        <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [/#if] 
      
      [#--  Deliverable Information --]
      <h1 class="contentTitle">[@s.text name="planning.projectDeliverable.title" /] </h1> 
      <input type="hidden" value="${project.deliverables[dl_index].id}" name="project.deliverables[${dl_index}].id">
      <div class="fullBlock">
        [#-- Title --] 
        [@customForm.input name="project.deliverables[${dl_index}].title" type="text" i18nkey="planning.deliverables.title" required=true editable=editable /]
      </div>
      <div class="fullBlock">
        [#-- MOG  --]
        <div class="halfPartBlock chosen">
          [@customForm.select name="project.deliverables[${dl_index}].output" label=""  disabled=false i18nkey="planning.deliverables.mog" listName="outputs" keyFieldName="id"  displayFieldName="description" editable=editable /]
        </div> 
        [#-- Year  --]
        <div class="halfPartBlock chosen">
          [@customForm.select name="project.deliverables[${dl_index}].year" label=""  disabled=false i18nkey="planning.deliverables.year" listName="allYears" editable=editable /]
        </div>
      </div> 
      <div class="fullBlock">
        [#-- Main Type --]
        <div class="halfPartBlock chosen"> 
          [@customForm.select name="mainType" label=""  i18nkey="planning.deliverables.mainType" listName="deliverableTypes" keyFieldName="id"  displayFieldName="name" editable=editable /]
        </div> 
        [#-- Sub Type --]
        <div class="halfPartBlock chosen">
          [@customForm.select name="project.deliverables[${dl_index}].type"  label="" i18nkey="planning.deliverables.subType" listName="" keyFieldName=""  displayFieldName="" editable=editable /]
          <input type="hidden" id="subTypeSelected" value="${project.deliverables[dl_index].type.id}" />
        </div>  
        [#if editable && canEdit]
        [#-- Sub Type --]
        <div class="halfPartBlock chosen" style="display:none">
          [@customForm.input name="project.deliverables[${dl_index}].type" i18nkey="planning.deliverables.subType" /]
          <input type="hidden" id="" value="" />
        </div>
        [/#if]
      </div>
      
      [#-- Deliverable Next Users block  --] 
      <div class="fullBlock">
        <h1 class="contentTitle">[@s.text name="planning.projectDeliverable.nextUsers" /] </h1> 
        [#if project.deliverables[0].nextUsers?has_content]
          [#list project.deliverables[0].nextUsers as nu] 
            [@deliverableTemplate.nextUserTemplate dl_index="0" nu_index="${nu_index}" nextUserValue="${nu.id}" editable=editable canEdit=canEdit /]
          [/#list]
        [/#if]
        [#if editable && canEdit]
          <div id="addNextUserBlock" class="addLink"><a href=""  class="addNextUser addButton">[@s.text name="planning.deliverables.addNewUser" /]</a></div>
        [/#if] 
      </div>
      
      [#-- Deliverable partnership  --] 
      <div class="fullBlock">
        <h1 class="contentTitle">[@s.text name="planning.projectDeliverable.partnership" /] </h1>  
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
    [#else]
        [#-- Display Log History --]
        [#if history??][@log.logList list=history /][/#if]   
    [/#if]
  </article>
  [/@s.form] 
   
</section>


[#-- Project deliverable Next user Template--]
[@deliverableTemplate.nextUserTemplate template=true /]

[#include "/WEB-INF/global/pages/footer.ftl"]