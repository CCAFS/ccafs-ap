[#ftl]
[#assign title = "Project Description" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "select2"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/projects/projectDescription.js"] /]
[#assign currentSection = cycleName?lower_case /]
[#assign currentCycleSection = "projects" /]
[#assign currentStage = "description" /]
[#assign currentSubStage = "description" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projects", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"description", "nameSpace":"${currentSection}/projects", "action":"description", "param":"projectID=${project.id}"}
] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" /><p> [@s.text name="${currentSection}.projectDescription.help" /] </p>
  </div> 
  [#include "/WEB-INF/projects/projectsSubMenu.ftl" /]
  
  [@s.form action="description" method="POST" enctype="multipart/form-data" cssClass="pure-form"]
  <article class="halfContent" id="mainInformation">
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
        <div class="fullBlock">
          [#-- Project Type --]
          <div class="halfPartBlock"> 
            [@customForm.select name="project.type" value="${project.type}" i18nkey="planning.projectDescription.projectType" listName="projectTypes" disabled=true editable=false stringKey=true /]

          </div>
        </div> 

        [#-- Project upload work plan --]
        [#if !project.bilateralProject]
        <div id="uploadWorkPlan" class="tickBox-wrapper fullBlock" style="[#if !project.workplanRequired && !project.workplanName?has_content && !editable]display:none[/#if]">
          [#if action.hasProjectPermission("workplan",project.id) ]
            [@customForm.checkbox name="project.workplanRequired" value="true" checked=project.workplanRequired  i18nkey="preplanning.projectDescription.isRequiredUploadworkplan" disabled=!editable editable=editable /]
          [/#if]
          <div class="tickBox-toggle uploadContainer" [#if (!project.workplanRequired )]style="display:none"[/#if]>
            <div class="halfPartBlock fileUpload projectWorkplan"> 
              [#if project.workplanName?has_content]
                <p> 
                  [#if editable]<span id="remove-file" class="remove"></span>[#else]<span class="file"></span>[/#if] 
                  <a href="${workplanURL}${project.workplanName}">${project.workplanName}</a>  <input type="hidden" name="project.workplanName" value="${project.workplanName}" /> 
                </p>
              [#else]
                [#if editable]
                  [#if !action.hasProjectPermission("workplan",project.id) ]
                    <h6>[@s.text name="preplanning.projectDescription.uploadProjectWorkplan" /]:</h6>
                  [/#if]
                  [@customForm.inputFile name="file"  /]
                [/#if] 
              [/#if] 
            </div> 
          </div>  
        </div>
        [/#if]

        [#-- Project upload bilateral contract --]
        [#if (project.bilateralProject && action.hasProjectPermission("bilateralContract",project.id) )]
        <div class="fullBlock fileUpload bilateralContract">
          <h6>[@customForm.text name="preplanning.projectDescription.uploadBilateral" readText=!editable /]:</h6>
          <div class="uploadContainer">
            [#if project.bilateralContractProposalName?has_content]
              [#if editable]<span id="remove-file" class="remove"></span>[#else]<span class="file"></span>[/#if] 
              <p> <a href="${bilateralContractURL}${project.bilateralContractProposalName}">${project.bilateralContractProposalName}</a> 
            [#else]
              [#if editable]
                [@customForm.inputFile name="file"  /]
              [#else]  
                <p>[@s.text name="form.values.notFileUploaded" /]</p> 
              [/#if] 
            [/#if]
          </div>  
        </div>
        [/#if]
        
        [#-- Project Summary --]
        <div class="fullBlock">
          [@customForm.textArea name="project.summary" i18nkey="preplanning.projectDescription.projectSummary" required=!project.bilateralProject className="project-description" editable=editable && action.hasProjectPermission("summary",project.id) /]
        </div>
        
        [#-- -- -- REPORTING BLOCK -- -- --]
        [#if reportingCycle]
          [#-- Project upload annual report to donor--]
          [#if project.bilateralProject]
          <div class="fullBlock fileUpload annualreportDonor">
            <h6>[@customForm.text name="reporting.projectDescription.annualreportDonor" readText=!editable /]:</h6>
            <div class="uploadContainer">
              [#if project.annualReportDonor?has_content]
                [#if editable]<span id="remove-fileReporting" class="remove"></span>[#else]<span class="file"></span>[/#if] 
                <p><a href="${AnualReportURL}${project.annualReportDonor}">${project.annualReportDonor}</a> </p>
              [#else]
                [#if editable]
                  [@customForm.inputFile name="fileReporting"  /]
                [#else]  
                  <p> [@s.text name="form.values.notFileUploaded" /]</p>
                [/#if] 
              [/#if]
            </div>  
          </div>
          [/#if]
         
        [/#if]
        
        [#--  Regions/global and Flagships that the project is working on --]
        <h6>[@customForm.text name="preplanning.projectDescription.projectWorking" readText=!editable /]:[#if !project.bilateralProject && editable ]<span class="red">*</span>[/#if] </h6> 
        <div id="projectWorking" class="fullBlock clearfix">
          [#-- Flagships --] 
          <div id="projectFlagshipsBlock" class="grid_5">
            <h6>[@s.text name="preplanning.projectDescription.flagships" /]</h6>
            <div class="checkboxGroup">  
              [#if editable && (action.hasProjectPermission("flagships",project.id) || project.bilateralProject)]
                [@s.fielderror cssClass="fieldError" fieldName="project.flagships"/]
                [@s.checkboxlist name="project.flagships" disabled=!(action.hasProjectPermission("flagships",project.id) || project.bilateralProject) list="ipProgramFlagships" listKey="id" listValue="getComposedName()" cssClass="checkbox" value="flagshipIds" /]
              [#else]
                [#if project.flagships?has_content]
                  [#list project.flagships as element]
                   <p class="checked">${element.composedName}</p>
                  [/#list]
                [#else]
                  [#if !project.bilateralProject]<span class="fieldError">[@s.text name="form.values.required" /]</span>[/#if]
                [/#if]
              [/#if]
            </div>
          </div> 
          [#-- Regions --]
          <div id="projectRegionsBlock" class="grid_4">
            <h6>[@s.text name="preplanning.projectDescription.regions" /]</h6>
            <div class="checkboxGroup">
              [#if editable && (action.hasProjectPermission("regions",project.id) || project.bilateralProject)]
                [@s.fielderror cssClass="fieldError" fieldName="project.regions"/]
                [@s.checkboxlist name="project.regions" disabled=!(action.hasProjectPermission("regions",project.id) || project.bilateralProject)  list="ipProgramRegions" listKey="id" listValue="getComposedName()" cssClass="checkbox" value="regionIds" /]
              [#else]
                [#if project.regions?has_content]
                  [#list project.regions as element]
                    <p class="checked">${element.composedName}</p>
                  [/#list]
                [#else]
                  [#if !project.bilateralProject]<span class="fieldError">[@s.text name="form.values.required" /]</span>[/#if]
                [/#if]
              [/#if]
            </div>
          </div> 
        </div> 
      </fieldset> 

      [#-- Bilateral/Core projects only for CCAFS Projects --]
      <h1 id="bilateralProjects" class="contentTitle"> [@s.text name="planning.projectDescription.projectsContributing" /] </h1> 
      <div class="panel tertiary">
        <div class="panel-head">
          [#if project.bilateralProject]
            [@customForm.text name="planning.projectDescription.selectCoreProject" readText=!editable /]:
          [#else]
            [@customForm.text name="planning.projectDescription.selectBilateralProject" readText=!editable /]:
          [/#if]
        </div>
        <div id="projectsList" class="panel-body"> 
          <ul class="list">
          [#if project.linkedProjects?has_content]
            [#list project.linkedProjects as element]
              <li class="clearfix [#if !element_has_next]last[/#if]">
                <input class="id" type="hidden" name="project.linkedProjects" value="${element.id?c}" />
                <a href="[@s.url action='description'][@s.param name='projectID']${element.id}[/@s.param][/@s.url]"><span class="name">${element.id} - ${element.title}</span> </a>
                [#if editable]<span class="listButton remove popUpValidation">[@s.text name="form.buttons.remove" /]</span>[/#if] 
              </li>
            [/#list]
          [#else]
            <p class="emptyText"> [@s.text name="planning.projectDescription.${project.bilateralProject?string('coreProjects','bilateralProjects')}.emptyText" /]</p>
          [/#if]  
          </ul>
          [#if editable ]
            [#-- The values of this list are loaded via ajax --]
            [@customForm.select name="" label="" disabled=!canEdit i18nkey="" listName="" keyFieldName="id" displayFieldName="" className="" value="" /]
          [/#if] 
        </div>
      </div>

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
  
  [#-- Core project list template --]
  <ul style="display:none">
    <li id="cpListTemplate" class="clearfix">
      <input class="id" type="hidden" name="" value="" />
      <span class="name"></span> 
      <span class="listButton remove">[@s.text name="form.buttons.remove" /]</span>
    </li>
  </ul>
  
  [#-- Remove project contribution popup --]
  <div id="removeContribution-dialog" title="Remover project contribution" style="display:none">
    <ul class="messages"><li>[@s.text name="reporting.projectDescription.removeContributionDialog" /]</li></ul>
  </div>
  
  [#-- File upload Template--] 
  [@customForm.inputFile name="file" template=true /] 
  [@customForm.inputFile name="fileReporting" template=true /] 
  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]
