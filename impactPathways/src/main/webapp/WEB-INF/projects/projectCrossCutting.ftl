[#ftl]
[#assign title = "Project Cross-Cutting" /]
[#assign globalLibs = ["jquery", "noty", "chosen", "cytoscape", "qtip","cytoscapePanzoom"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/ipGraph.js", "${baseUrl}/js/projects/projectImpactPathway.js"] /]
[#assign currentSection = cycleName?lower_case /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "outcomes" /] 
[#assign currentSubStage = "crossCutting" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projects", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projectOutcomes", "nameSpace":"${currentSection}/projects", "action":"outcomes", "param":"projectID=${project.id}"},
  {"label":"crossCutting", "nameSpace":"${currentSection}/projects", "action":"crossCutting", "param":"projectID=${project.id}"}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  [#-- Section help message --]
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" /><p>[@s.text name="reporting.crossCutting.help" /]</p>
  </div>
  
  [#-- Project subMenu --]
  [#include "/WEB-INF/projects/projectsSubMenu.ftl" /]

  [@s.form action="outcomes" cssClass="pure-form"]  
  <article class="halfContent" id="activityImpactPathway">
    [#include "/WEB-INF/projects/dataSheet.ftl" /]
    [#include "/WEB-INF/projects/projectIP-sub-menu.ftl" /]
    
    [#-- Submission and privileges message --]
    [#if submission?has_content]
      <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
    [#elseif !canEdit ]
      <p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]</p>
    [/#if]
    
    [#-- Gender and social inclusion towards CCAFS Outcomes --]
    <div class="borderBox">
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]<div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>[/#if]
      [/#if] 
      <h1 class="contentTitle">[@s.text name="reporting.projectCrossCutting.genderSocialTitle" /] </h1>
      
      [#-- Gender and social inclusion contribution current reporting cycle --]
      <div class="fullBlock">
        [@customForm.textArea name="project.crossCutting.genderSocialContribution" className="limitWords-100" i18nkey="reporting.projectCrossCutting.genderSocialContribution" required=true editable=editable/]
      </div> 
       
      [#-- Reporting lessons --]
      <div class="fullBlock">
        <input type="hidden" name="projectLessons.id" value=${(projectLessons.id)!"-1"} />
        <input type="hidden" name="projectLessons.year" value=${reportingCycle?string(currentReportingYear,currentPlanningYear)} />
        <input type="hidden" name="projectLessons.componentName" value="${actionName}">
        [@customForm.textArea name="projectLessons.lessons" i18nkey="${currentSection}.projectCrossCutting.lessons" required=!project.bilateralProject editable=editable /]
      </div>
      
    </div>
    
    [#-- Communication and  engagement in towards CCAFS Outcomes--]
    <div class="borderBox">
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]<div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>[/#if]
      [/#if]
      <h1 class="contentTitle">[@s.text name="reporting.projectCrossCutting.comnEngagementTitle" /] </h1>
      
      [#-- Comunication and engagement activities --]
      <div class="fullBlock">
        [@customForm.textArea name="project.crossCutting.genderSocialContribution" className="limitWords-100" i18nkey="reporting.projectCrossCutting.genderSocialContribution" required=true editable=editable/]
      </div>
      
      [#-- Category of communication/engagement activity --]
      <div class="fullBlock">
        <h6>[@customForm.text name="reporting.projectCrossCutting.category" param="${currentReportingYear}" readText=!editable /]:</h6>
        [@customForm.select name="" className="project.crossCutting.commEngageCategory" showTitle=false i18nkey="" listName="commEngageCategories" required=true editable=editable/]
      </div>
      
      [#-- Upload summary--] 
      <div class="fullBlock fileUpload uploadSummary">
        <h6>[@customForm.text name="reporting.projectCrossCutting.uploadSummary" readText=!editable /]:</h6>
        <div class="uploadContainer">
          [#if project.annualreportDonor?has_content]
            [#if editable]<span id="remove-uploadSummary" class="remove"></span>[/#if] 
            <p><a href="${AnualReportURL}${project.annualreportDonor}">${project.annualreportDonor}</a></p>
          [#else]
            [#if editable]
              [@customForm.inputFile name="uploadSummary"  /]
            [#else]  
              <span class="fieldError">[@s.text name="form.values.required" /]</span>  [@s.text name="form.values.notFileUploaded" /]
            [/#if] 
          [/#if]
        </div>  
      </div> 
      
    </div>
    
    [#if editable] 
      [#-- Project identifier --]
      <input name="projectID" type="hidden" value="${project.id?c}" />
      <div class="[#if !newProject]borderBox[/#if]" >
        [#if !newProject][@customForm.textArea name="justification" i18nkey="saving.justification" required=true className="justification"/][/#if]
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
[#include "/WEB-INF/global/pages/footer.ftl"]
