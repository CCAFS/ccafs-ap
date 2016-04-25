[#ftl]
[#assign title = "Project Evaluation" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "select2"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/projects/projectDescription.js"] /]
[#assign currentSection = cycleName?lower_case /]
[#assign currentCycleSection = "projectsEvaluation" /]
[#assign currentStage = "evaluation" /]
[#assign currentSubStage = "evaluation" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projectsEvaluation", "nameSpace":"${currentSection}", "action":"projectsEvaluation"},
  {"label":"evaluation", "nameSpace":"${currentSection}/projects", "action":"evaluation", "param":"projectID=${project.id}"}
] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" /><p> [@s.text name="${currentSection}.projectDescription.help" /] </p>
  </div>  
  
  [@s.form action="description" method="POST" enctype="multipart/form-data" cssClass="pure-form"]
  <article class="fullBlock" id="mainInformation">
    [#include "/WEB-INF/projects/dataSheet.ftl" /]
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantProjectPlanningAccessInterceptor--]
    [#if submission?has_content]
      <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
    [#elseif !canEdit ]
      <p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param][@s.text name="planning.project"/][/@s.param][/@s.text]</p>
    [/#if] 
    <h1 class="contentTitle">[@s.text name="planning.projectDescription.title" /]</h1>  
    <div id="projectDescription" class="borderBox">
      [#-- Button for edit this section --]
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
          <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if] 
      <fieldset class="fullBlock">
        [#-- Project Title --]
        <div class="fullBlock">
          [@customForm.textArea name="project.title" i18nkey="planning.projectDescription.projectTitle" required=true className="project-title" editable=editable && action.hasProjectPermission("title",project.id)/]
        </div>
        <div class="fullBlock">
          [#-- Project Program Creator --]
          <div class="halfPartBlock">
            [@customForm.select name="project.liaisonInstitution" label="" disabled=( !editable || !action.hasProjectPermission("managementLiaison",project.id) ) i18nkey="planning.projectDescription.programCreator" listName="liaisonInstitutions" keyFieldName="id"  displayFieldName="name" required=true editable=( editable && action.hasProjectPermission("managementLiaison",project.id) ) /]
          </div>
          [#--  Project Owner Contact Person --]
          <div class="halfPartBlock">
            [@customForm.select name="project.owner" label="" disabled=( !editable || !action.hasProjectPermission("managementLiaison",project.id) ) i18nkey="preplanning.projectDescription.projectownercontactperson" listName="allOwners" keyFieldName="id"  displayFieldName="composedOwnerName" required=true editable=( editable && action.hasProjectPermission("managementLiaison",project.id) ) /]
          </div> 
        </div>  
        <div class="fullBlock">  
          [#-- Start Date --]
          <div class="halfPartBlock">
            [@customForm.input name="project.startDate" type="text" disabled=( !editable || !action.hasProjectPermission("startDate",project.id) ) i18nkey="preplanning.projectDescription.startDate" required=true editable=( editable && action.hasProjectPermission("startDate",project.id) ) /]
          </div> 
          [#-- End Date --]
          <div class="halfPartBlock">
            [@customForm.input name="project.endDate" type="text" disabled=( !editable || !action.hasProjectPermission("endDate",project.id) ) i18nkey="preplanning.projectDescription.endDate" required=true editable=( editable && action.hasProjectPermission("endDate",project.id) ) /]
          </div>
        </div>
        
        [#-- Project Summary --]
        <div class="fullBlock">
          [@customForm.textArea name="project.summary" i18nkey="preplanning.projectDescription.projectSummary" required=!project.bilateralProject className="project-description" editable=editable && action.hasProjectPermission("summary",project.id) /]
        </div>
        
        [#--  Regions/global and Flagships that the project is working on --]
        <h6>[@customForm.text name="preplanning.projectDescription.projectWorking" readText=!editable /]:[#if !project.bilateralProject && editable ]<span class="red">*</span>[/#if] </h6> 
        <div id="projectWorking" class="fullBlock clearfix">
          [#-- Flagships --] 
          <div id="projectFlagshipsBlock" class="grid_5">
            <h6>[@s.text name="preplanning.projectDescription.flagships" /]</h6>
            <div class="checkboxGroup">  
              [#if project.flagships?has_content]
                [#list project.flagships as element]
                 <p class="checked">${element.composedName}</p>
                [/#list]
              [#else]
                [#if !project.bilateralProject]<span class="fieldError">[@s.text name="form.values.required" /]</span>[/#if]
              [/#if]
            </div>
          </div> 
          [#-- Regions --]
          <div id="projectRegionsBlock" class="grid_4">
            <h6>[@s.text name="preplanning.projectDescription.regions" /]</h6>
            <div class="checkboxGroup">
              [#if project.regions?has_content]
                [#list project.regions as element]
                  <p class="checked">${element.composedName}</p>
                [/#list]
              [#else]
                [#if !project.bilateralProject]<span class="fieldError">[@s.text name="form.values.required" /]</span>[/#if]
              [/#if]
            </div>
          </div> 
        </div> 
      </fieldset>
    </div> 
    
    [#-- Project Evaluations --]
    <h1 class="contentTitle">Project Evaluations</h1>  
    <div id="projectDescription" class="borderBox">
    
    </div>
    
    [#-- Project identifier --]
    <input name="projectID" type="hidden" value="${project.id?c}" />
    [#if editable]
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
  [#-- Hidden values used by js --]
  <input id="minDateValue" value="${newProject?string(currentPlanningYear,startYear)}-01-01" type="hidden"/>
  <input id="maxDateValue" value="${endYear?c}-12-31" type="hidden"/> 
  <input id="programID" value="${project.liaisonInstitution.id?c}" type="hidden"/>
  <input id="projectsAction" type="hidden" value="${project.bilateralProject?string('coreProjects.do','bilateralCoFinancingProjects.do')}" />
  
  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]
