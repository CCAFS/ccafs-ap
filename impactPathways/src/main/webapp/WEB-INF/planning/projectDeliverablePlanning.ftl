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
  {"label":"projectDeliverable", "nameSpace":"planning/projects", "action":"deliverable", "param":"projectID=${project.id}"}
]/]

[#assign params = {
  "deliverable":  {"id":"deliverableName", "name":"deliverable"},
  "nextUsers":    {"id":"nextUsersName",   "name":"deliverable.nextUsers"},
  "partners":     {"id":"partnersName",    "name":"deliverable.partners"} 
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
    <p> [@s.text name="planning.projectDeliverables.help" /] </p>
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
  
  [@s.form action="deliverables" cssClass="pure-form"]
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
        <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]#deliverable-information">[@s.text name="form.buttons.edit" /]</a></div>
      [/#if]
      <h1 class="contentTitle">[@s.text name="planning.projectDeliverable.information" /] </h1>  
      <div class="fullBlock">
        [#-- Title --] 
        [@customForm.input name="${params.deliverable.name}.title" i18nkey="planning.deliverables.title" required=true editable=editable /]
      </div>
      <div class="fullBlock">
        [#-- MOG  --]
        <div class="halfPartBlock chosen"> 
          [@customForm.select name="${params.deliverable.name}.output" label=""  disabled=false i18nkey="planning.deliverables.mog" listName="outputs" keyFieldName="id"  displayFieldName="description" editable=editable /]
        </div> 
        [#-- Year  --]
        <div class="halfPartBlock chosen">
          [@customForm.select name="${params.deliverable.name}.year" label=""  disabled=false i18nkey="planning.deliverables.year" listName="allYears" editable=editable /]
        </div>
      </div> 
      <div class="fullBlock">
        [#-- Main Type --]
        <div class="halfPartBlock chosen"> 
          [@customForm.select name="mainType" label=""  i18nkey="planning.deliverables.mainType" listName="deliverableTypes" keyFieldName="id"  displayFieldName="name" editable=editable /]
        </div> 
        [#-- Sub Type --]
        <div class="halfPartBlock chosen"> 
          [@customForm.select name="${params.deliverable.name}.type" value="${deliverable.type.id}" i18nkey="planning.deliverables.subType" listName="" keyFieldName=""  displayFieldName="" editable=editable /]
          <input type="hidden" id="subTypeSelected" value="${deliverable.type.id}" />
          [#-- Specify other deliverable type--] 
          [@customForm.input name="${params.deliverable.name}.otherType" className="otherType" showTitle=false i18nkey="planning.deliverables.specify" display=false required=true editable=editable /]
        </div>
      </div>
    </div>
    
    [#-- Deliverable Next Users block  --] 
    <div id="deliverable-nextUsers" class="borderBox clearfix">
      [#if !editable && canEdit]
        <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]#deliverable-nextUsers">[@s.text name="form.buttons.edit" /]</a></div>
      [/#if]
      <div class="fullBlock">
        <h1 class="contentTitle">[@s.text name="planning.projectDeliverable.nextUsers" /] </h1> 
        [#if deliverable.nextUsers?has_content]
          [#list deliverable.nextUsers as nu] 
            [@deliverableTemplate.nextUserTemplate nu_name=params.nextUsers.name nu_index="${nu_index}" nextUserValue="${nu.id}" editable=editable canEdit=canEdit /]
          [/#list]
        [/#if]
        [#if editable && canEdit]
          <div id="addNextUserBlock" class="addLink"><a href=""  class="addNextUser addButton">[@s.text name="planning.deliverables.addNewUser" /]</a></div>
        [/#if] 
      </div>
    </div>  
    
    [#-- Deliverable partnership  --] 
    <div id="deliverable-partnership" class="borderBox clearfix">
      [#if !editable && canEdit]
        <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]#deliverable-partnership">[@s.text name="form.buttons.edit" /]</a></div>
      [/#if]
      <div class="fullBlock">
        <h1 class="contentTitle">[@s.text name="planning.projectDeliverable.partnership" /] </h1>
        [#assign deliverablePartners = [
          {"id":"1", "name":"Deliverable #1", "user":"Carvajal, Hernán <h.d.carvajal@cgiar.org>"},
          {"id":"2", "name":"Deliverable #2", "user":"Tall, Arame <a.tall@cgiar.org>"},
          {"id":"3", "name":"Deliverable #3", "user":"Hansen, James <jhansen@iri.columbia.edu>"}
        ]/]
        <div class="fullBlock">
          <p>[@s.text name="planning.projectDeliverable.indicateResponsablePartner" /]</p>
          [@deliverableTemplate.deliverablePartner dp=deliverablePartners[0] dp_name=params.partners.name dp_index=dp_index institutionList="deliverableTypes" isResponsable=true editable=editable /]
        </div>
        <div class="fullBlock">
          <p>[@s.text name="planning.projectDeliverable.indicateOtherContact" /]</p>
          [#if deliverablePartners?has_content]
            [#list deliverablePartners as dp] 
              [@deliverableTemplate.deliverablePartner dp=dp dp_name=params.partners.name dp_index=dp_index institutionList="deliverableTypes" editable=editable /]
            [/#list]
          [/#if]
          [#if editable && canEdit]
            <div id="addPartnerBlock" class="addLink"><a href=""  class="addPartner addButton">[@s.text name="planning.deliverables.addPartner" /]</a></div>
          [/#if]
        </div>
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
[@deliverableTemplate.deliverablePartner dp={} dp_name=params.partners.name dp_index=dp_index institutionList="deliverableTypes" template=true /]

[#-- Search users Interface Popup --]
[@usersForm.searchUsers isActive=false/]

[#include "/WEB-INF/global/pages/footer.ftl"]