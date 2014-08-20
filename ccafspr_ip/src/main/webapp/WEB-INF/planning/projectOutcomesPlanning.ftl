[#ftl]
[#assign title = "Project Outcomes" /]
[#assign globalLibs = ["jquery", "noty","autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "projectOutcomes" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning/projects", "action":"projects"},
  {"label":"projects", "nameSpace":"planning/projects", "action":"projects"},
  {"label":"projectOutcomes", "nameSpace":"planning/projects", "action":""}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.projectOutcome.help" /] </p>
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
  
  [@s.form action="projectOutcomes" cssClass="pure-form"]  
  <article class="halfContent" id="projectOutcomes"> 
    <div class="borderBox"> 
      <h6 class="contentTitle">
      [@customForm.textArea name="project.outcome.statement" i18nkey="planning.projectOutcome.statement" /]  
      </h6> <br/>
      <h6 class="contentTitle">
      [@customForm.textArea name="project.outcome.stories" i18nkey="planning.projectOutcome.story" /]  
      </h6> 
       <!-- internal parameter -->
      <input name="projectID" type="hidden" value="${project.id?c}" /> 
      <input name="project.outcome.id" type="hidden" value="${project.outcome.id}" />
      <input name="project.outcome.year" type="hidden" value="${project.outcome.year?c}" />
    </div>
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]