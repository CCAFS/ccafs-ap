[#ftl]
[#assign title = "Project Description" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/projectDescriptionPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentStage = "description" /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]


[#if currentUser.isAdmin() || ( project.owner.employeeId == currentUser.employeeId)]
  [#assign canEdit = true /]
[#else]
  [#assign canEdit = false /]
[/#if]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.projectDescription.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/planning-secondary-menu.ftl" /]
  
  [@s.form action="description" cssClass="pure-form"]
  <article class="halfContent" id="mainInformation">
    <h1 class="contentTitle">
      ${project.composedId} - [@s.text name="planning.projectDescription.title" /]
    </h1> 
    <div id="projectDescription" class="borderBox">
      [#-- Project identifier --]
      <input name="projectID" type="hidden" value="${project.id?c}" />
      <fieldset class="fullBlock">  
        [#-- Project Title --]
        [@customForm.textArea name="project.title" i18nkey="planning.projectDescription.projectTitle" required=true /]
        <div id="projectDescription" class="">
          [#-- Project Program Creator --]
          <div class="halfPartBlock">
            <h6>[@s.text name="planning.projectDescription.programCreator" /]</h6>
            <p>${project.programCreator.acronym}</p>
          </div>
          [#--  Project Owner Contact Person --]
          <div class="halfPartBlock">
            [@customForm.select name="project.owner" label="" disabled=!canEdit i18nkey="preplanning.projectDescription.projectownercontactperson" listName="allOwners" keyFieldName="employeeId"  displayFieldName="composedOwnerName" /]
          </div>
          [#-- Start Date --]
          <div class="halfPartBlock">
            [@customForm.input name="project.startDate" type="text" disabled=!canEdit i18nkey="preplanning.projectDescription.startDate" required=true /]
          </div> 
          [#-- End Date --]
          <div class="halfPartBlock">
              [@customForm.input name="project.endDate" type="text" disabled=!canEdit i18nkey="preplanning.projectDescription.endDate" required=true /]
          </div>
        </div>
        [#-- Project Summary --]
        [@customForm.textArea name="project.summary" i18nkey="preplanning.projectDescription.projectSummary" required=true /]
      </fieldset>
      <fieldset class="fullBlock">   
        <legend>[@s.text name="preplanning.projectDescription.projectWorking" /] </legend> 
        <div id="projectWorking">
          [#-- Regions --]
          <div id="projectRegions" class="thirdPartBlock">
            <h6>[@s.text name="preplanning.projectDescription.regions" /]</h6>
            <div class="checkboxGroup">
              [@s.fielderror cssClass="fieldError" fieldName="project.regions"/]
              [@s.checkboxlist name="project.regions" disabled=!canEdit list="ipProgramRegions" listKey="id" listValue="name" cssClass="checkbox" value="regionIds" /]
            </div>
          </div> 
          [#-- Flagships --] 
          <div id="projectFlagships" class="thirdPartBlock">
            <h6>[@s.text name="preplanning.projectDescription.flagships" /]</h6>
            <div class="checkboxGroup">  
              [@s.fielderror cssClass="fieldError" fieldName="project.flagships"/]
              [@s.checkboxlist name="project.flagships" disabled=!canEdit list="ipProgramFlagships" listKey="id" listValue="getComposedName(id)" cssClass="checkbox" value="flagshipIds" /]
            </div>
          </div> 
          [#-- Cross Cutting --] 
          <div id="projectGender" class="thirdPartBlock">
            <h6>[@s.text name="preplanning.projectDescription.crossCutting" /]</h6>
            <div class="checkboxGroup">
              [@s.fielderror cssClass="fieldError" fieldName="project.crossCuttings"/]
              [@s.checkboxlist name="project.crossCuttings" disabled=!canEdit list="ipCrossCuttings" listKey="id" listValue="name" cssClass="checkbox" value="crossCuttingIds" /]
            </div>
          </div>  
        </div> 
      </fieldset>
    </div> 
    
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [#-- @s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit --]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
     
  </article>
  [/@s.form] 
  [#-- Hidden values used by js --]
  <input id="minDateValue" value="${startYear?c}-01-01" type="hidden"/>
  <input id="maxDateValue" value="${endYear?c}-12-31" type="hidden"/> 
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]