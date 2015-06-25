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
  {"label":"project", "nameSpace":"planning/projects", "action":"description", "param":"projectID=${project.id}"}
]/]

[#assign years= [midOutcomeYear, currentPlanningYear, currentPlanningYear+1] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.projectImpactPathways.help" /] </p>
  </div>

  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]

  [@s.form action="outcomes" cssClass="pure-form"]  
  <article class="halfContent" id="activityImpactPathway">
    [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
    [#include "/WEB-INF/planning/projectIP-planning-sub-menu.ftl" /]
    
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantActivityPlanningAccessInterceptor--]
    [#if !saveable]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"]
          [@s.param][@s.text name="planning.projectImpactPathways.title"/][/@s.param]
        [/@s.text]
      </p>
    [/#if]

    <div id="projectOutcomes-narrative" class="borderBox">
      [#if !editable]
        <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [/#if] 

      [#-- Project  outcome block --]
      <div class="fullPartBlock clearfix">
        <h1 class="contentTitle">[@s.text name="planning.projectOutcome.narrative" /] </h1> 
        [#-- Project Outcome statement --]
        <div class="fullPartBlock" id="projectOutcomeStatement">
          [@customForm.textArea name="project.outcomes[${midOutcomeYear}].statement" i18nkey="planning.projectOutcome.statement" editable=editable/]
        </div>
        [#-- Annual progress --]
        [#list currentPlanningYear?number..midOutcomeYear?number-1 as year]
          <div class="fullPartBlock"> 
            <h6>[@customForm.text name="planning.projectOutcome.annualProgress" readText=!editable param="${year}" /]</h6>
            [@customForm.textArea name="project.outcomes[${year?string}].statement" showTitle=false editable=editable /]
          </div>
        [/#list]
        <input name="project.outcome[midOutcomeYear].id" type="hidden" value="${project.outcomes[midOutcomeYear+""].id?c}" />
      </div>

      [#-- Gender contribution block --]
      <div class="fullPartBlock">
        <h1 class="contentTitle">[@s.text name="planning.projectOutcome.genderAndSocialNarrative" /] </h1> 

        [#-- Gender and Social Narrative --]
        <div class="fullPartBlock" id="projectOutcome-genderAndSocialNarrative">
          [@customForm.textArea name="project.outcomes[${midOutcomeYear}].genderDimension" i18nkey="planning.projectOutcome.genderAndSocialStatement" editable=editable /]
        </div>

        [#-- Annual for the expected Gender and Social contribution --]
        [#list currentPlanningYear?number..midOutcomeYear?number-1 as year]
          <div class="fullPartBlock">
            <h6>[@customForm.text name="planning.projectOutcome.genderAndSocialAnnualProgress" readText=!editable param="${year}" /]</h6>
            [@customForm.textArea name="project.outcomes[${year?string}].genderDimension" showTitle=false editable=editable /]
          </div>
        [/#list]
        <input name="project.outcome[midOutcomeYear].id" type="hidden" value="${project.outcomes[midOutcomeYear+""].id?c}" />

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
[#include "/WEB-INF/global/pages/footer.ftl"]
