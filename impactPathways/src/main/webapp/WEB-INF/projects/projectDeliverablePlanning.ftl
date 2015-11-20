[#ftl]
[#assign title = "Project Deliverable" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/usersManagement.js", "${baseUrl}/js/projects/projectDeliverablesPlanning.js"] /]
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
[#import "/WEB-INF/projects/macros/projectDeliverablesTemplate.ftl" as deliverableTemplate/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.deliverables.help" /] 
    <a href = [@s.url namespace="/" action='glossary'][/@s.url]>[@s.text name="planning.deliverables.help2" /]</a>
    </p>
  </div>
  [#include "/WEB-INF/projects/planningProjectsSubMenu.ftl" /]
  
  [@s.form action="deliverable" cssClass="pure-form"]
  <article class="halfContent" id="projectDeliverable">  
    [#include "/WEB-INF/projects/planningDataSheet.ftl" /]
    <br />
    [#-- Informing user that he-she does not have enough privileges to edit. See GrantProjectPlanningAccessInterceptor --]  
    [#if submission?has_content]
      <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
    [#elseif !canEdit ]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"][@s.param][@s.text name=title /][/@s.param][/@s.text]
      </p>
    [/#if]
    [#--  Deliverable Information --] 
    <div id="deliverable-information" class="borderBox clearfix"> 
      [#if !editable && canEdit]
        <div class="editButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
          <div class="viewButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if]
      <h1 class="contentTitle">[@s.text name="planning.projectDeliverable.information" /] </h1>  
      <div class="fullBlock">
        [#-- Title --] 
        [@customForm.input name="${params.deliverable.name}.title" className="deliverableTitle" i18nkey="planning.deliverables.title" required=true editable=editable /]
      </div>
      <div class="fullBlock">
        [#-- MOG  --]
        <div class="${editable?string('halfPartBlock','fullBlock')} chosen"> 
          [@customForm.select name="${params.deliverable.name}.output" label=""  disabled=false i18nkey="planning.deliverables.mog" listName="outputs" keyFieldName="id"  displayFieldName="description" required=true editable=editable /]
        </div> 
        [#-- Year  --]
        <div class="halfPartBlock chosen">
          [@customForm.select name="${params.deliverable.name}.year" value="${deliverable.year}" label=""  disabled=false i18nkey="planning.deliverables.year" listName="allYears" required=true editable=editable /]
          [#if !editable]${deliverable.year}[/#if]
        </div>
      </div> 
      <div class="fullBlock">
        [#assign deliverableType][#if deliverable.type??]${deliverable.type.category.id}[#else]-1[/#if][/#assign]
        [#assign deliverableSubType][#if deliverable.type??]${deliverable.type.id}[#else]-1[/#if][/#assign]
        [#-- Main Type --]
        <div class="halfPartBlock chosen"> 
          [@customForm.select name="mainType" value="${deliverableType}" i18nkey="planning.deliverables.mainType" listName="deliverableTypes" keyFieldName="id"  displayFieldName="name" required=true editable=editable /]
          [#if !editable]${(deliverable.type.category.name)!}[/#if]
        </div> 
        [#-- Sub Type --]
        <div class="halfPartBlock chosen">
          [#assign fieldEmpty] <div class="select"><p>[@s.text name="form.values.fieldEmpty" /]</p></div>[/#assign]
          [@customForm.select name="${params.deliverable.name}.type" value="${deliverableSubType}" i18nkey="planning.deliverables.subType" listName="" keyFieldName=""  displayFieldName="" required=true editable=editable /]
          [#if !editable][#if deliverable.typeOther??]${(deliverable.typeOther)!fieldEmpty}[#else]${(deliverable.type.name)!fieldEmpty}[/#if][/#if]
          <input type="hidden" id="subTypeSelected" value="${deliverableSubType}" />
          [#-- Specify other deliverable type--] 
          [@customForm.input name="${params.deliverable.name}.typeOther" value="${(deliverable.typeOther)!}" className="otherType" display=false showTitle=false i18nkey="planning.deliverables.specify" required=true disabled=true editable=editable /]          
        </div> 
      </div>
      
      [#-- Deliverables table dialog --]
      [#if canEdit && !action.canDelete()]
      <div id="dialog" title="Deliverable types" style="display: none">
        <table id="deliverableTypes" style="height:700px; width:900px;">
          <th> [@s.text name="planning.deliverables.dialogMessage.part1" /] </th>
          <th> [@s.text name="planning.deliverables.dialogMessage.part2" /] </th>
          <th> [@s.text name="planning.deliverables.dialogMessage.part3" /] </th>
          [#list deliverableTypes as mt]
            [#list action.getDeliverableSubTypes(mt.id) as st]
              [#if st_index == 0]
              <tr>
                <th rowspan="${action.getDeliverableSubTypes(mt.id).size()}"> ${mt.name} </th>
                    <td> ${st.name} </td>
                    <td> ${(st.description)!}</td>
              </tr>
              [#else]
              <tr>
                <td> ${st.name} </td>
                <td> ${(st.description)!} </td>
              </tr>
              [/#if]
            [/#list]
          [/#list]  
        </table>
      </div> <!-- End dialog-->
      <div class="helpMessage3">
        <p><a href="#" id="opener"><img src="${baseUrl}/images/global/icon-help.png" />[@s.text name="planning.deliverables.deliverableType" /]</a></p>
      </div>
      <br />
      [/#if]
      
      [#-- Deliverable type description and message--]
      [#if canEdit && editable && !action.canDelete()]

      <div class="note left">
        [#if editable && deliverable.type??]
          <p><b>Deliverable type description:</b> [@s.text name="${(deliverable.type.description)!}" /]</p>
          <br />
        [/#if]
        <p>[@s.text name="planning.deliverables.disclaimerMessage" /]</p>
      </div>
      [/#if]
    </div>
    
    [#-- Deliverable Next Users block  --]
    <div id="deliverable-nextUsers" class="borderBox clearfix">
      [#if !editable && canEdit]
        <div class="editButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#deliverable-nextUsers">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
          <div class="viewButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][/@s.url]#deliverable-nextUsers">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
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
        <div class="editButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#deliverable-partnership">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
          <div class="viewButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][/@s.url]#deliverable-partnership">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if]
      <h1 class="contentTitle">[@s.text name="planning.projectDeliverable.partnership" /] </h1> 
      <div class="fullBlock">
        [#-- Partner who is responsible --]
        <div class="fullBlock">
          <p>[@customForm.text name="planning.projectDeliverable.indicateResponsablePartner" readText=!editable/]</p>
          [@deliverableTemplate.deliverablePartner dp=deliverable.responsiblePartner dp_name=params.responsiblePartner.name dp_index=dp_index isResponsable=true editable=editable /]
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
        <a href="[@s.url action='partners'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]"> 
          [@s.text name="preplanning.projectBudget.partnersLink" /] 
        </a>
      </div>
      [/#if]
    </div>
    
    [#if editable] 
      <input name="projectID" type="hidden" value="${project.id?c}" />
      <input name="deliverableID"type="hidden" value="${deliverable.id}">
      <div class="[#if !newProject]borderBox[/#if]" >
        [#if !newProject] [@customForm.textArea name="justification" i18nkey="saving.justification" required=true className="justification"/][/#if]
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
[@deliverableTemplate.deliverablePartner dp={} dp_name=params.partners.name dp_index=dp_index template=true /]

[#include "/WEB-INF/global/pages/footer.ftl"]