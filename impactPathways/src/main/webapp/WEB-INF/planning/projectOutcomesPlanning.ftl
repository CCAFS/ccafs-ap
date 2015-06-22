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
      <h1 class="contentTitle">[@s.text name="planning.projectOutcome.narrative" /] </h1> 
      [#-- Project Outcome statement --]
      <div class="fullBlock" id="projectOutcomeStatement">
        [@customForm.textArea name="project.outcomes[${midOutcomeYear}].statement" i18nkey="planning.projectOutcome.statement" /]
      </div>
      [#-- Annual progress --]
      [#list currentPlanningYear?number..midOutcomeYear?number-1 as year]
        <div class="fullBlock">
          [#assign label][@s.text name="planning.projectOutcome.annualProgress"][@s.param]${year}[/@s.param][/@s.text][/#assign]
          [@customForm.textArea name="project.outcomes[${year?string}].statement" i18nkey="${label}" /]
        </div>
      [/#list]
      <input name="project.outcome[midOutcomeYear].id" type="hidden" value="${project.outcomes[midOutcomeYear+""].id?c}" />
    </div>  
    
    <div id="projectOutcomes-contribution" class="borderBox">
      <h1 class="contentTitle">[@s.text name="planning.projectOutcome.genderAndSocialNarrative" /] </h1> 
      [#-- Gender and Social Narrative --]
      <div class="fullBlock" id="projectOutcome-genderAndSocialNarrative">
        [@customForm.textArea name="project.outcomes[${midOutcomeYear}].genderAndSocialStatement" i18nkey="planning.projectOutcome.genderAndSocialStatement" /]
      </div>
      [#-- Annual for the expected Gender and Social contribution --]
      [#list currentPlanningYear?number..midOutcomeYear?number-1 as year]
        <div class="fullBlock">
          [#assign label][@s.text name="planning.projectOutcome.genderAndSocialAnnualProgress"][@s.param]${year}[/@s.param][/@s.text][/#assign]
          [@customForm.textArea name="project.outcomes[${year?string}].genderAndSocialStatement" i18nkey="${label}" /]
        </div>
      [/#list]
      <input name="project.outcome[midOutcomeYear].id" type="hidden" value="${project.outcomes[midOutcomeYear+""].id?c}" />
    </div>   
    
    [#if editable] 
      <input type="hidden" id="projectID" name="projectID" value="${projectID}" />
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
    [/#if]
  </article>
  [/@s.form]  
</section> 
[#include "/WEB-INF/global/pages/footer.ftl"]