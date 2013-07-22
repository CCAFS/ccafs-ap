[#ftl]
[#assign title = "Activity Summaries" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/summaries/activitiesSummaries.js"] /]
[#assign currentSection = "summaries" /]
[#assign currentSummariesSection = "activities" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="summaries.activities.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/summaries-secondary-menu.ftl" /]
  
  
  [@s.form action="activities"]  
  <article class="halfContent" id="activitiesSummaries">
    <h1 class="contentTitle">
      [@s.text name="summaries.activities.title" /] 
    </h1>
    
    <div class="reportType fullBlock">
      [@customForm.radioButtonGroup i18nkey="summaries.activities.reportType" label="" name="reportTypeSelected" listName="reportTypes" /]
      
      <div class="reportOptions" >
        <div id="activityLeaderBlock">
          [#if currentUser.admin]
            <div class="thirdPartBlock">
              <label>[@s.text name="summaries.activities.leader" /]:</label>
              <img src="${baseUrl}/images/global/icon-help2.png" title="[@s.text name="summaries.activities.leaderHelp"/]" />
            </div>
            <div class="halfPartBlock">
              [@customForm.select name="activityLeader" label="" i18nkey="summaries.activities.leader" listName="leaders" keyFieldName="id" displayFieldName="acronym" showTitle=false /]
            </div>
          [#else]
            else
            <input type="hidden" name="activityLeader" value="${currentUser.leader.id}" />
          [/#if]
        </div>
        
        <div class="optionsTitle">
          <h6>[@s.text name="summaries.activities.options" /]</h6>
          [@s.fielderror cssClass="fieldError" fieldName="reportOptions"/]
        </div>
        
        <div class="" id="allActivitiesByYearBlock">
          <div class="thirdPartBlock">
           [@customForm.radioButton name="reportOptionSelected" value="allActivitiesByYear" i18nkey="summaries.activities.activitiesByYear" label="" id="allActivitiesByYear" /]
          </div>
          <div class="halfPartBlock">
            [@customForm.select name="reportYear" label="" i18nkey="summaries.activities.allActivities" listName="yearList" disabled=true showTitle=false /]
          </div>
        </div>
        
        <div class="" id="activityIdentifierBlock">
          <div class="thirdPartBlock">
            [@customForm.radioButton name="reportOptionSelected" value="activityIdentifier" i18nkey="summaries.activities.activityIdentifier" label="" id="activityIdentifier" /]
          </div>
          <div class="thirdPartBlock">
            [@customForm.input name="activityID" i18nkey="summaries.activities.allActivities" disabled=true showTitle=false /]
          </div>
        </div>
        
      </div>
    </div>
      
    
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]