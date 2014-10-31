[#ftl]
[#assign title = "Project Outcomes" /]
[#assign globalLibs = ["jquery", "noty","autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/projectOutcomesPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "projectOutcomes" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projects"},
  {"label":"projects", "nameSpace":"planning", "action":"projects"},
  {"label":"projectOutcomes", "nameSpace":"planning/projects", "action":""}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>
      [@s.text name="planning.projectOutcome.help1" /] 
      <a href="[@s.url namespace="/" action='glossary'][/@s.url]#project-outcome">[@s.text name="planning.projectOutcome.projectOutcome" /]</a>
      [@s.text name="planning.projectOutcome.help2" /] 
      <a href="[@s.url namespace="/" action='glossary'][/@s.url]#next-users">[@s.text name="planning.projectOutcome.nextUsers" /]</a>
      [@s.text name="planning.projectOutcome.help3" /] 
      <a href="[@s.url namespace="/" action='glossary'][/@s.url]#activity">[@s.text name="planning.projectOutcome.activity" /]</a>
      [@s.text name="planning.projectOutcome.help4" /] 
    </p>  
    <p>[@s.text name="planning.projectOutcome.help5" /]</p>
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
  
  [@s.form action="projectOutcomes" cssClass="pure-form"]
    <article class="halfContent" id="projectOutcomes">
    [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
      [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantProjectPlanningAccessInterceptor--]
      [#if !saveable]
        <p class="readPrivileges">
          [@s.text name="saving.read.privileges"]
            [@s.param][@s.text name="planning.project"/][/@s.param]
          [/@s.text]
        </p>
      [/#if]
      <h1 class="contentTitle">
        ${project.composedId} - [@s.text name="planning.projectOutcome.title" /]
      </h1> 
      
      <div id="projectOutcomes" class="borderBox">

        [#-- Project Outcome statement --]
        <div class="fullBlock">
          [@customForm.textArea name="project.outcomes[${midOutcomeYear}].statement" i18nkey="planning.projectOutcome.statement" /]
        </div>
        
        [#-- Annual progress --]
        [#list currentPlanningYear?number..midOutcomeYear?number-1 as year]
          <div class="fullBlock">
            [#assign label][@s.text name="planning.projectOutcome.annualProgress"][@s.param]${year}[/@s.param][/@s.text][/#assign]
            [@customForm.textArea name="project.outcomes[${year?string}].statement" i18nkey="${label}" /]
          </div>
        [/#list]
        <input name="project.outcome[midOutcomeYear].id" type="hidden" value="${project.outcomes[midOutcomeYear].id?c}" />
      </div>


      [#if saveable]
        <!-- internal parameters -->
        <input name="projectID" type="hidden" value="${project.id?c}" /> 
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