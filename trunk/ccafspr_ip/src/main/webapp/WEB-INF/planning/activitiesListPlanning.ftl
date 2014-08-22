[#ftl]
[#assign title = "Activities" /]
[#assign globalLibs = ["jquery", "noty","autoSave", "dataTable"] /]
[#assign customJS = ["${baseUrl}/js/planning/activity-list.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "activities" /]
[#assign currentStage = "activities" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning/projects", "action":"projects"},
  {"label":"projects", "nameSpace":"planning/projects", "action":"projects"},
  {"label":"activities", "nameSpace":"planning/activities", "action":"activities", "param":"projectID=${projectID}" }
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
[#import "/WEB-INF/planning/macros/activitiesListTemplate.ftl" as activitiesList /]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="planning.activities.help1" /]</p>
    <p>[@s.text name="planning.activities.help2" /]</p>
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]

  [@s.form action="activities" cssClass="pure-form"]
    <article class="halfContent" id="activities">
      <h1 class="contentTitle">
        [@s.text name="planning.activities.title" /]
      </h1>
      [#-- Validating amount of activities to be listed --]
      [#if activities?size > 0]
        [@activitiesList.activitiesList activities=activities canValidate=true canEditProject=true tableID="activities" /]
      [#else]
        [#if saveable]
          <p>[@s.text name="planning.activities.message.empty" /] [@s.text name="planning.activities.message.addNew" /]</p>
          <div class="buttons">
            [@s.submit type="button" name="add"][@s.text name="planning.activities.button.add" /][/@s.submit]
          </div>
        [#else]
          <p>[@s.text name="planning.activities.message.empty" /]</p>
        [/#if]
      [/#if]
    </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]