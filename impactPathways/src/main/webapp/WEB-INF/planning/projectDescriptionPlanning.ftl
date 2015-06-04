[#ftl]
[#assign title = "Project Description" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/projectDescriptionPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "description" /]
[#assign currentSubStage = "description" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projects"},
  {"label":"projects", "nameSpace":"planning", "action":"projects"},
  {"label":"description", "nameSpace":"planning/projects", "action":""}
] /]





[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.projectDescription.help" /] </p>
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
  
  [@s.form action="description" cssClass="pure-form"]
  <article class="halfContent" id="mainInformation">
    [#include "/WEB-INF/planning/projectDescription-planning-sub-menu.ftl" /]
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantProjectPlanningAccessInterceptor--]
    [#if !saveable]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"]
          [@s.param][@s.text name="planning.project"/][/@s.param]
        [/@s.text]
      </p>
    [/#if] 
    <div id="projectDescription" class="borderBox">
      [#-- Button for edit this section --] 
      [#if !editable]
        <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="editable"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [/#if]
      <h1 class="contentTitle"> ${project.composedId} - [@s.text name="planning.projectDescription.title" /] </h1>  
      <fieldset class="fullBlock">
        [#-- Project Title --]
        [@customForm.textArea name="project.title" i18nkey="planning.projectDescription.projectTitle" required=true className="project-title" editable=editable/]
        <div id="projectDescription" class="fullBlock">
          [#-- Project Program Creator --]
          <div class="halfPartBlock">
            <h6>[@s.text name="planning.projectDescription.programCreator" /]</h6>
            <p>${project.programCreator.acronym}</p>
          </div>
          [#--  Project Owner Contact Person --]
          <div class="halfPartBlock">
            [@customForm.select name="project.owner" label="" disabled=!fullEditable i18nkey="preplanning.projectDescription.projectownercontactperson" listName="allOwners" keyFieldName="employeeId"  displayFieldName="composedOwnerName" editable=editable/]
          </div> 
          [#-- Start Date --]
          <div class="halfPartBlock">
            [@customForm.input name="project.startDate" type="text" disabled=!fullEditable i18nkey="preplanning.projectDescription.startDate" required=true editable=editable /]
          </div> 
          [#-- End Date --]
          <div class="halfPartBlock">
              [@customForm.input name="project.endDate" type="text" disabled=!fullEditable i18nkey="preplanning.projectDescription.endDate" required=true editable=editable /]
          </div>
        </div>
        [#-- Project upload work plan --]
        <div id="uploadWorkPlan" class="tickBox-wrapper fullBlock">
          [@customForm.checkbox name="project.isRequiredUploadworkplan" value=""  i18nkey="preplanning.projectDescription.isRequiredUploadworkplan" disabled=!editable /]
          [#if editable]
            <div class="tickBox-toggle uploadContainer">
              <div class="halfPartBlock">
                <p>[@s.text name="preplanning.projectDescription.uploadProjectWorkplan" /]</p>
                <input type="file" name="fileToUpload" id="fileToUpload"> 
              </div>
              <div class="halfPartBlock">
                <p>[@s.text name="preplanning.projectDescription.uploadBilateral" /]</p>
                <input type="file" name="fileToUpload" id="fileToUpload">  
              </div>
            </div> 
          [/#if] 
        </div>
        [#-- Project Summary --]
        [@customForm.textArea name="project.summary" i18nkey="preplanning.projectDescription.projectSummary" required=true className="project-description" editable=editable /]
      </fieldset>
      <fieldset class="fullBlock">   
        <legend>[@s.text name="preplanning.projectDescription.projectWorking" /] </legend> 
        <div id="projectWorking">
          [#-- Flagships --] 
          <div id="projectFlagshipsBlock" class="grid_5">
            <h6>[@s.text name="preplanning.projectDescription.flagships" /]</h6>
            <div class="checkboxGroup">  
              [#if editable]
                [@s.fielderror cssClass="fieldError" fieldName="project.flagships"/]
                [@s.checkboxlist name="project.flagships" disabled=!fullEditable list="ipProgramFlagships" listKey="id" listValue="getComposedName(id)" cssClass="checkbox" value="flagshipIds" /]
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
              [#if editable]
                [@s.fielderror cssClass="fieldError" fieldName="project.regions"/]
                [@s.checkboxlist name="project.regions" disabled=!fullEditable list="ipProgramRegions" listKey="id" listValue="name" cssClass="checkbox" value="regionIds" /]
              [#else]  
                [#list project.regions as element]
                  <p class="checked">${element.name}</p>
                [/#list]
              [/#if]
            </div>
          </div> 
        </div> 
      </fieldset>
      
      [#-- Core Projects for Bilateral project type --] 
      <h1 class="contentTitle"> [@s.text name="planning.projectDescription.coreProjects" /] </h1> 
      <div id="projectCoreProjects" class="isLinked tickBox-wrapper fullBlock"> 
        [@customForm.checkbox name="project.isLinked" value=""  i18nkey="planning.projectDescription.isLinkedCoreProjects" disabled=!editable  /]
        [#if editable]
          <div class="tickBox-toggle coreProjects fullBlock">
            <div class="usersList panel primary">
              <div class="panel-head"> [@s.text name="planning.projectDescription.chouseCoreProject" /]</div>
              <div class="panel-body"> 
                [@s.checkboxlist name="project.coreProjects"  list="coreProjects" listKey="id" listValue="name" cssClass="checkbox" value="" /]
              </div>
            </div>
          </div>
        [/#if]
      </div> 
      
    </div> 
    
    [#if saveable]
      [#-- Project identifier --]
      <input name="projectID" type="hidden" value="${project.id?c}" />
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
    [/#if]
     
  </article>
  [/@s.form] 
  [#-- Hidden values used by js --]
  <input id="minDateValue" value="${startYear?c}-01-01" type="hidden"/>
  <input id="maxDateValue" value="${endYear?c}-12-31" type="hidden"/> 
  <input id="programID" value="${project.programCreator.id?c}" type="hidden"/>
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]