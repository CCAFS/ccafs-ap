[#ftl]
[#assign title = "Project Outcomes" /]
[#assign globalLibs = ["jquery", "noty","autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "project" /]
[#assign currentStage = "outcome" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.mainInformation.help" /] </p>
  </div>
  [#include "/WEB-INF/planning/projectPlanningSubMenu.ftl" /]
  
  [@s.form action="projectOutcomes" cssClass="pure-form"]  
  <article class="halfContent borderBox" id="projectOutcomes"> 
  	 
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
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]