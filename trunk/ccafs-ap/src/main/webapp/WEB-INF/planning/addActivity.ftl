[#ftl]
[#assign title = "Add New Activity" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/planning/addActivity.js"] /]
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
      <h1>[@s.text name="planning.addActivity.headerTitle" /] (${year?c})</h1>    
      
      [#-- Activity title --]
      <div class="fullBlock">
        [@customForm.textArea name="activity.title" i18nkey="planning.addActivity.title" /]
        [#-- @customForm.input name="activity.title" type="text" i18nkey="planning.addActivity.title" / --]
      </div>
      
      [#-- Is this activity a continuation of a previous activity? --]
      <div class="halfPartBlock">
        [#assign continuousActivityId = -1]
        [#if activity?has_content][#if activity.continuousActivity?has_content][#assign continuousActivityId = activity.continuousActivity.id][/#if][/#if]
        [@customForm.select name="activity.continuousActivity" label="" i18nkey="planning.addActivity.continuousActivity" listName="continuousActivityList" value="${continuousActivityId}" /]
      </div>
      
      [#-- Dates --]
      <div class="halfPartBlock" id="datesBlock">
        [#-- Start Date --]
        <div class="halfPartBlock">
          [@customForm.input name="activity.startDate" type="text" i18nkey="planning.mainInformation.startDate" /]
        </div>
      
        [#-- End Date --]
        <div class="halfPartBlock">
          [@customForm.input name="activity.endDate" type="text" i18nkey="planning.mainInformation.endDate" /]
        </div>
      </div>
      
      [#-- Commisioned activity ? --]
      [#if currentUser.TL || currentUser.RPL]
        <div class="halfPartBlock">
          [@customForm.select name="activity.leader.commisioned" label="" i18nkey="planning.addActivity.isCommissioned" listName="leaders" keyFieldName="id"  displayFieldName="acronym" /]
        </div>
      [/#if]
      
      [#-- Leader --]
      [#if currentUser.admin]
        <div class="thirdPartBlock">
          [@customForm.select name="activity.leader" label="" i18nkey="planning.addActivity.leader" listName="leaders" keyFieldName="id"  displayFieldName="acronym" /]
        </div>
      [#else]
        <div class="thirdPartBlock">
          <input type="hidden" name="activity.leader" value="${currentUser.leader.id}" id="addActivity_activity_leader">
        </div>
      [/#if]
      
      [#-- Hidden values used by js --]
      <input name="activity.year" value="${year?c}" type="hidden"/>
      <input id="minDateValue" value="${startYear?c}-01-01" type="hidden"/>
      <input id="maxDateValue" value="${endYear?c}-12-31" type="hidden"/>
      
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
    </article>
  [/@s.form]
  </section>
[#include "/WEB-INF/global/pages/footer.ftl"]