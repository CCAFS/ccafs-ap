[#ftl]
[#assign title = "Project Description" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/projectDescriptionPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "description" /]
[#assign currentSubStage = "description" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"planning", "action":"projectsList"},
  {"label":"description", "nameSpace":"planning/projects", "action":"description", "param":"projectID=${project.id}"},
  {"label":"information", "nameSpace":"planning/projects", "action":"description", "param":"projectID=${project.id}"}
] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" /><p> [@s.text name="planning.projectDescription.help" /] </p>
  </div> 
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
  
  [@s.form action="description" method="POST" enctype="multipart/form-data" cssClass="pure-form"]
  <article class="halfContent" id="mainInformation"> 
    [#include "/WEB-INF/planning/projectDescription-planning-sub-menu.ftl" /]
    [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantProjectPlanningAccessInterceptor--]
    [#if !canEdit ]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"][@s.param name ="projectID"]${project.id}[/@s.param][@s.param][@s.text name="planning.project"/][/@s.param][/@s.text]
      </p>
    [/#if] 
    <div id="projectDescription" class="borderBox">
      [#-- Button for edit this section --]
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [/#if]
      <h1 class="contentTitle">[@s.text name="planning.projectDescription.title" /]</h1>  
      <fieldset class="fullBlock">
        [#-- Project Title --]
        <div class="fullBlock">
          [@customForm.textArea name="project.title" i18nkey="planning.projectDescription.projectTitle" required=true className="project-title" editable=editable/]
        </div>
        <div class="fullBlock">
          [#-- Project Program Creator --]
          <div class="halfPartBlock">
            [@customForm.select name="project.liaisonInstitution" label="" disabled=( !editable || !securityContext.canEditManagementLiaison() ) i18nkey="planning.projectDescription.programCreator" listName="liaisonInstitutions" keyFieldName="id"  displayFieldName="name" required=true editable=( editable && securityContext.canEditManagementLiaison() ) /]
          </div>
          [#--  Project Owner Contact Person --]
          <div class="halfPartBlock">
            [@customForm.select name="project.owner" label="" disabled=( !editable || !securityContext.canEditManagementLiaison() ) i18nkey="preplanning.projectDescription.projectownercontactperson" listName="allOwners" keyFieldName="id"  displayFieldName="composedOwnerName" required=true editable=( editable && securityContext.canEditManagementLiaison() ) /]
          </div> 
        </div>  
        <div class="fullBlock">  
          [#-- Start Date --]
          <div class="halfPartBlock">
            [@customForm.input name="project.startDate" type="text" disabled=( !editable || !securityContext.canEditStartDate() ) i18nkey="preplanning.projectDescription.startDate" required=true editable=( editable && securityContext.canEditStartDate() ) /]
          </div> 
          [#-- End Date --]
          <div class="halfPartBlock">
            [@customForm.input name="project.endDate" type="text" disabled=( !editable || !securityContext.canEditEndDate() ) i18nkey="preplanning.projectDescription.endDate" required=true editable=( editable && securityContext.canEditEndDate() ) /]
          </div>
        </div>
        <div class="fullBlock">
          [#-- Project Type --]
          <div class="halfPartBlock"> 
            [@customForm.select name="project.type" value="project.type" i18nkey="planning.projectDescription.projectType" listName="projectTypes" required=true disabled=true /]
            <input name="project.type" value="${project.type}" type="hidden"/>
          </div>
        </div> 

        [#-- Project upload work plan --]
        [#if !project.bilateralProject]
        <div id="uploadWorkPlan" class="tickBox-wrapper fullBlock" style="[#if !project.workplanRequired && !project.workplanName?has_content && !editable]display:none[/#if]">
          [#if securityContext.canAllowProjectWorkplanUpload() ]
            [@customForm.checkbox name="project.workplanRequired" value="true" checked=project.workplanRequired  i18nkey="preplanning.projectDescription.isRequiredUploadworkplan" disabled=!editable editable=editable /]
          [/#if]
          <div class="tickBox-toggle uploadContainer" [#if (!project.workplanRequired )]style="display:none"[/#if]>
            <div class="halfPartBlock fileUpload projectWorkplan"> 
              [#if project.workplanName?has_content]
                <p> 
                  [#if editable]<span id="remove-projectWorkplan" class="remove"></span>[#else]<span id="" class="file"></span>[/#if] 
                  <a href="${workplanURL}">${project.workplanName}</a>  <input type="hidden" name="project.workplanName" value="${project.workplanName}" /> 
                </p>
              [#else]
                [#if editable]
                  [#if !securityContext.canAllowProjectWorkplanUpload() ]
                    <h6>
                      [@s.text name="preplanning.projectDescription.uploadProjectWorkplan" /][#if project.workplanRequired ]<span class="red"> *</span>[/#if]
                    </h6>
                  [/#if]
                  [@customForm.inputFile name="file"  /]
                [/#if] 
              [/#if] 
            </div> 
          </div>  
        </div>
        [/#if]

        [#-- Project upload bilateral contract --]
        [#if (project.bilateralProject && securityContext.canUploadBilateralContract())]
        <div class="fullBlock fileUpload bilateralContract">
          <h6>[@customForm.text name="preplanning.projectDescription.uploadBilateral" readText=!editable /][#if project.bilateralProject ]<span class="red"> *</span>[/#if]:</h6>
          <div class="uploadContainer">
            [#if project.bilateralContractProposalName?has_content]
              <p> <a href="bilateralContractURL">${project.bilateralContractProposalName}</a>  [#if editable]<span id="remove-bilateralContract" class="ui-icon ui-icon-closethick remove"></span>[/#if] </p>
            [#else]
              [#if editable] 
                [@customForm.inputFile name="file"  /]
              [#else]  
                [@s.text name="form.values.notFileUploaded" /]
              [/#if] 
            [/#if]
          </div>  
        </div>
        [/#if]
        
        [#-- Project Summary --]
        <div class="fullBlock">
          [@customForm.textArea name="project.summary" i18nkey="preplanning.projectDescription.projectSummary" required=true className="project-description" editable=editable /]
        </div>
        
        <h6>[@customForm.text name="preplanning.projectDescription.projectWorking" readText=!editable /][#if !project.bilateralProject ]<span class="red"> *</span>[/#if]: </h6> 
        <div id="projectWorking" class="fullBlock clearfix">
          [#-- Flagships --] 
          <div id="projectFlagshipsBlock" class="grid_5">
            <h6>[@s.text name="preplanning.projectDescription.flagships" /]</h6>
            <div class="checkboxGroup">  
              [#if editable && securityContext.canEditProjectFlagships()]
                [@s.fielderror cssClass="fieldError" fieldName="project.flagships"/]
                [@s.checkboxlist name="project.flagships" disabled=!securityContext.canEditProjectFlagships() list="ipProgramFlagships" listKey="id" listValue="getComposedName(id)" cssClass="checkbox" value="flagshipIds" /]
              [#else] 
                [#list project.flagships as element]
                 <p class="checked">${element.name}</p>
                [/#list]
              [/#if]
            </div>
          </div> 
          [#-- Regions --]
          <div id="projectRegionsBlock" class="grid_4">
            <h6>[@s.text name="preplanning.projectDescription.regions" /]</h6>
            <div class="checkboxGroup">
              [#if editable && securityContext.canEditProjectRegions()]
                [@s.fielderror cssClass="fieldError" fieldName="project.regions"/]
                [@s.checkboxlist name="project.regions" disabled=!securityContext.canEditProjectRegions()  list="ipProgramRegions" listKey="id" listValue="name" cssClass="checkbox" value="regionIds" /]
              [#else]  
                [#list project.regions as element]
                  <p class="checked">${element.name}</p>
                [/#list]
              [/#if]
            </div>
          </div> 
        </div> 
      </fieldset> 

      [#-- Bilateral/Core projects only for CCAFS Projects --]
      <h1 id="bilateralProjects" class="contentTitle"> [@s.text name="planning.projectDescription.${project.bilateralProject?string('coreProjects','bilateralProjects')}" /] </h1> 
      <div class="panel tertiary">
        [#if project.bilateralProject]
          <div class="panel-head">[@customForm.text name="planning.projectDescription.selectCoreProject" readText=!editable /]:</div>
        [/#if]
        <div id="projectsList" class="panel-body"> 
          <ul class="list">
          [#if project.linkedProjects?has_content]
            [#list project.linkedProjects as element]
              <li class="clearfix [#if !element_has_next]last[/#if]">
                <input class="id" type="hidden" name="project.linkedProjects" value="${element.id?c}" />
                <a href="[@s.url action='description'][@s.param name='projectID']${element.id}[/@s.param][/@s.url]"><span class="name">${element.id} - ${element.title}</span> </a>
                [#if editable && project.bilateralProject]<span class="listButton remove">[@s.text name="form.buttons.remove" /]</span>[/#if] 
              </li>
            [/#list]
          [#else]
            <p class="emptyText"> [@s.text name="planning.projectDescription.${project.bilateralProject?string('coreProjects','bilateralProjects')}.emptyText" /]</p>
          [/#if]  
          </ul>
          [#if editable && project.bilateralProject]
            [#-- The values of this list are loaded via ajax --]
            [@customForm.select name="" label="" disabled=!canEdit i18nkey="" listName="" keyFieldName="id" displayFieldName="" className="" value="" /]
          [/#if] 
        </div>
      </div>

    </div> 
    [#if editable]
      [#-- Project identifier --]
      <input name="projectID" type="hidden" value="${project.id?c}" />
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
  <input id="minDateValue" value="${startYear?c}-01-01" type="hidden"/>
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
  
  [#-- File projectWorkplan upload Template--]
  [@customForm.inputFile name="project.projectWorkplan" template=true /]
  
  [#-- File bilateralContractTemplate upload Template--] 
  [@customForm.inputFile name="project.bilateralContract" template=true /] 
  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]
