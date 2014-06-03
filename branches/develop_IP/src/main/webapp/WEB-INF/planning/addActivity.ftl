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
        [@customForm.checkbox  name="continuousActivity" i18nkey="planning.addActivity.isContinuation" value="true" /]
      </div>

      [#-- Commisioned activity ? --]
      <div class="halfPartBlock">
        [#if currentUser.TL || currentUser.RPL]
          [@customForm.checkbox  name="activity.commissioned" i18nkey="planning.addActivity.isCommissioned" value="true" /]
        [/#if]
      </div>
      
      [#-- Which activity is the previous one? --]
      <div class="halfPartBlock" id="activityListBlock" >
        <div class="halfPartBlock" >
          [#assign continuousActivityId = -1]
          [#if activity?has_content][#if activity.continuousActivity?has_content][#assign continuousActivityId = activity.continuousActivity.id][/#if][/#if]
          [@customForm.select name="activity.continuousActivity" label="" i18nkey="planning.addActivity.continuousActivity" listName="continuousActivityList" value="${continuousActivityId?c}" showTitle=false display=false /]
        </div>
      </div>
        
      <div class="halfPartBlock" id="leaderListBlock" >
        <div class="halfPartBlock"  >
          [@customForm.select name="activity.commissioned" label="" i18nkey="planning.addActivity.commissionedLeader" listName="leaders" keyFieldName="id"  displayFieldName="acronym" showTitle=false display=false /]
        </div>
      </div>

      [#-- Leader --]
      [#if currentUser.admin]
        <div class="thirdPartBlock" id="leaderBlock" >
          [@customForm.select name="activity.leader" label="" i18nkey="planning.addActivity.leader" listName="leaders" keyFieldName="id"  displayFieldName="acronym" /]
        </div>
      [#else]
        <div class="thirdPartBlock" id="leaderBlock" >
          <input type="hidden" name="activity.leader" value="${currentUser.leader.id?c}" id="addActivity_activity_leader">
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