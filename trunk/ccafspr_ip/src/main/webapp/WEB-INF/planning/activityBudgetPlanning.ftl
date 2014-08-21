[#ftl]
[#assign title = "Activity Budget" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "activity" /]
[#assign currentStage = "activityBudget" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning/projects", "action":"projects"},
  {"label":"projects", "nameSpace":"planning/projects", "action":"projects"},
  {"label":"activities", "nameSpace":"planning/activities", "action":"" },
  {"label":"activityBudget", "nameSpace":"planning/activities", "action":"activityBudget" }
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.activityBudget.help" /] </p>
  </div>

  [#include "/WEB-INF/planning/activityPlanningSubMenu.ftl" /]

  
  [@s.form action="activityBudget" cssClass="pure-form"]  
    <article class="halfContent" id="activityBudget">
      [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantActivityPlanningAccessInterceptor--]
      [#if !saveable]
        <p class="readPrivileges">
          [@s.text name="saving.read.privileges"]
            [@s.param][@s.text name="planning.activityBudget.title"/][/@s.param]
          [/@s.text]
        </p>
      [/#if]
      <h1 class="contentTitle">
      [@s.text name="planning.activityBudget.title" /] 
      </h1> 
       
      [#if saveable]
        <input type="hidden" name="activityID" value="">
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