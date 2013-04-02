[#ftl]
[#assign title = "Add New Activity" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#-- assign customJS = ["${baseUrl}/js/planning/activity-list.js"] / --]
[#-- assign customCSS = ["${baseUrl}/css/global/customDataTable.css"] / --]
[#assign currentSection = "planning" /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]

[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
    
<section id="activityListPlanning" class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>// TODO... </p>
  </div>
  
  [@s.form action="addActivity"]
    <article class="fullContent">
      <h1>[@s.text name="planning.addActivity.headerTitle" /] (${currentPlanningLogframe.year?c})</h1>    
      
      [#-- Activity title --]
      <div class="halfPartBlock">
        [@customForm.input name="activity.title" type="text" i18nkey="planning.addActivity.title" /]
      </div>
      
      [#-- Milestone Codes --]
      <div class="halfPartBlock">
        [@customForm.select name="activity.milestone" label="" i18nkey="planning.addActivity.milestone" listName="milestones" keyFieldName="id"  displayFieldName="code" /]
      </div>
      
      [#-- Activity Leader --]
      <div class="thirdPartBlock">
        [@customForm.select name="activity.leader" label="" i18nkey="planning.addActivity.leader" listName="leaders" keyFieldName="id"  displayFieldName="acronym" /]
      </div>
      
      [#-- Is this activity commissioned? --]
      <div class="thirdPartBlock">
        [@customForm.checkbox name="activity.commissioned" label="" i18nkey="planning.addActivity.isCommissioned" required=true /]
      </div>
      
      [#-- Is this activity a continuation of a previous activity? --]
      <div class="thirdPartBlock">
        [@customForm.select name="activity.continuousActivity" label="" i18nkey="planning.addActivity.continuousActivity" listName="continuousActivityList" /]
      </div>
      
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
    </article>
  [/@s.form]
  </section>
[#include "/WEB-INF/global/pages/footer.ftl"]