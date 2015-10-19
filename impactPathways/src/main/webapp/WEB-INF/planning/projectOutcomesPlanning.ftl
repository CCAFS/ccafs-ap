[#ftl]
[#assign title = "Project Outcomes" /]
[#assign globalLibs = ["jquery", "noty", "chosen", "cytoscape", "qtip","cytoscapePanzoom"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/ipGraph.js", "${baseUrl}/js/planning/projectImpactPathwayPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "outcomes" /] 
[#assign currentSubStage = "outcomes" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projectOutcomes", "nameSpace":"planning/projects", "action":"outcomes", "param":"projectID=${project.id}"},
  {"label":"pOutcomes", "nameSpace":"planning/projects", "action":"outcomes", "param":"projectID=${project.id}"}
]/]

[#assign years= [midOutcomeYear, currentPlanningYear, currentPlanningYear+1] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.projectImpactPathways.help" /] 
    <a href= [@s.url namespace="/" action='glossary'][/@s.url]>[@s.text name="planning.projectImpactPathways.help.glossary" /]</a>
    </p>
  </div>

  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]

  [@s.form action="outcomes" cssClass="pure-form"]  
  <article class="halfContent" id="activityImpactPathway">
    [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
    [#include "/WEB-INF/planning/projectIP-planning-sub-menu.ftl" /]
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantActivityPlanningAccessInterceptor--]
    [#if submission?has_content]
      <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
    [#elseif !canEdit ]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]
      </p>
    [/#if]
    [#if project.startDate??]
    <div id="projectOutcomes" class="borderBox">
      [#if !editable && canEdit]
        <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
        <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if]   
      [#-- Project  outcome block --]
      <div class="fullPartBlock clearfix">
        <h1 class="contentTitle">[@s.text name="planning.projectOutcome.narrative" /] </h1> 
        [#-- Project Outcome statement --]
        <div class="fullPartBlock" id="projectOutcomeStatement">
          [@customForm.textArea name="project.outcomes[${midOutcomeYear}].statement" required=!project.bilateralProject className="limitWords-150" i18nkey="planning.projectOutcome.statement" editable=editable /]
        </div>
        [#-- Annual progress --]
        [#list project.startDate?string.yyyy?number..midOutcomeYear?number-1 as year]
          <div class="fullPartBlock">
            [#if year lt currentPlanningYear?number]
              <h6>[@customForm.text name="planning.projectOutcome.annualProgress" readText=true param="${year}" /]</h6>
              [@customForm.textArea name="project.outcomes[${year?string}].statement" className="limitWords-80" showTitle=false editable=false /]
            [#else]
              [#if (year == currentPlanningYear) || (year == currentPlanningYear+1)]
                <h6>[@customForm.text name="planning.projectOutcome.annualProgress" readText=!editable param="${year}" /] [@customForm.req required=!project.bilateralProject /]</h6>
                [@customForm.textArea name="project.outcomes[${year?string}].statement" required=!project.bilateralProject className="limitWords-80" showTitle=false editable=editable /]
              [#else]  
                <h6>[@customForm.text name="planning.projectOutcome.annualProgress" readText=!editable param="${year}" /]</h6>
                [@customForm.textArea name="project.outcomes[${year?string}].statement" className="limitWords-80" showTitle=false editable=editable /]
              [/#if]
            [/#if]
          </div>
        [/#list]
        <input name="project.outcome[midOutcomeYear].id" type="hidden" value="${project.outcomes[midOutcomeYear+""].id?c}" />
      </div>
    </div>
    [#else]
      <p class="simpleBox center">[@s.text name="planning.projectOutcome.message.dateUndefined" /]</p>
    [/#if]
    [#if !newProject]
    <div id="lessons" class="borderBox">
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#lessons">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
          <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]#lessons">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if] 
      <div class="fullBlock">
        <input type="hidden" name="projectLessons.id" value=${(projectLessons.id)!"-1"} />
        <input type="hidden" name="projectLessons.year" value=${currentPlanningYear} />
        <input type="hidden" name="projectLessons.componentName" value="${actionName}">
        [@customForm.textArea name="projectLessons.lessons" i18nkey="planning.projectOutcomes.lessons" required=!project.bilateralProject editable=editable /]
      </div>
    </div>
    [/#if]
    
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
</section> 
[#include "/WEB-INF/global/pages/footer.ftl"]
