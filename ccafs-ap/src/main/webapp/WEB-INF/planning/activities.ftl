[#ftl]
[#assign title = "Activity List (Planning)" /]
[#assign globalLibs = ["jquery", "dataTable", "noty"] /]
[#assign customJS = ["${baseUrl}/js/planning/activity-list.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = "planning" /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/utils.ftl" as utilities/]
    
<section id="activityListPlanning" class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.introduction.part1" /] </p>
    <p> [@s.text name="planning.introduction.part2" /] </p>
  </div>
  
  <article class="fullContent">
    <h1>[#if currentUser.leader??]${currentUser.leader.name}[/#if] ([@s.text name="planning.activityList.activities" /] ${currentPlanningLogframe.year?c})</h1>   
    
    <div id="activityTables">
      <ul>
        <li><a href="#activityTables-1"> [@s.text name="planning.activityList.currentActivities" /] </a></li>
        [#if othersActivities?has_content]
          <li><a href="#activityTables-2"> [@s.text name="planning.activityList.relatedActivities" /] </a></li>
          <li><a href="#activityTables-3"> [@s.text name="planning.activityList.pastActivities" /] </a></li>
        [#else]
          <li><a href="#activityTables-3"> [@s.text name="planning.activityList.pastActivities" /] </a></li>
        [/#if]
        
      </ul>
      
      <div id="activityTables-1">
        [#if workplanSubmitted]
          <p class="workplanSubmitted">[@s.text name="planning.activityList.workplanSubmitted" /]</p>
        [/#if]
        <table id="ownActivitiesTable" class="activityList">
        <thead>
          <tr>
            <th id="id">[@s.text name="planning.activityList.id" /]</th>
            <th id="activity">[@s.text name="planning.activityList.activity" /]</th>
            <th id="contactPerson">[@s.text name="planning.activityList.contactPerson" /]</th>
            <th id="theme">[@s.text name="planning.activityList.milestone" /]</th>
            <th id="validated">[@s.text name="planning.activityList.validated" /]</th>
          </tr>
        </thead>
        <tbody>
          [#if ownActivities?has_content]
            [#list ownActivities as activity]
              <tr>
                <td>
                  <a href=" [@s.url action='mainInformation' includeParams='get'] [@s.param name='${activityRequestParameter}']${activity.id}[/@s.param] [/@s.url]" >
                    ${activity.id}
                  </a> 
                </td>
                <td class="left">
                  <a href="
                  [@s.url action='mainInformation' includeParams='get']
                    [@s.param name='${activityRequestParameter}']${activity.id}[/@s.param]
                  [/@s.url]
                  " title="${activity.title}">
                    [#if activity.title?length < 70] ${activity.title}</a> [#else] [@utilities.wordCutter string=activity.title maxPos=70 /]...</a> [/#if]
                </td>
                <td>
                  [#if activity.contactPersons??]
                    [#if activity.contactPersons[0].email?has_content]
                      <a href="mailto:${activity.contactPersons[0].email}">${activity.contactPersons[0].name}</a>
                    [#else]
                      ${activity.contactPersons[0].name}
                    [/#if]
                  [#else]
                    [@s.text name="planning.activityList.contactPerson.empty" /]
                  [/#if]
                </td>               
                <td>${activity.milestone.code}</td>
                <td>
                  [#if activity.validated]
                    <img src="${baseUrl}/images/global/icon-complete.png" alt="Activity submitted" title="Activity submitted" />
                  [#else]
                    [#-- The PI only can see a notification, they can't validate the activity --]
                    [#if currentUser.PI]
                      <img src="${baseUrl}/images/global/icon-incomplete.png" alt="This activity has not been validated yet" title="This activity has not been validated yet" />
                    [#else]
                      [#-- The CP/TL/RPL can validate the activity if needed --]
                      [#if activityID == activity.id]
                        [#-- User tried to submit this activity but there is some missing data. --]
                        <img src="${baseUrl}/images/global/icon-incomplete.png" alt="There is missing data" title="There is missing data" />
                      [#else]
                        [#-- We send the index of the activity in the array, not the activity identifier  --]
                        [#-- in order find quickly the activity in the array to modify it.  --]
                        [@s.form action="activities" cssClass="buttons"]
                          <input name="activityIndex" value="${activity_index}" type="hidden"/>
                          [@s.submit type="button" name="save"][@s.text name="form.buttons.validate" /][/@s.submit]
                        [/@s.form]  
                      [/#if]
                    [/#if]
                  [/#if]
                </td>            
              </tr>
            [/#list]
          [/#if]
          </tbody>
        </table>
        [#if currentUser.TL || currentUser.RPL || currentUser.admin ]
          [#-- If the workplan hasn't been submitted yet, show the button --]
          [#if !workplanSubmitted]
            <div id="submitButtonBlock" class="buttons">
              [#if canSubmit]
              [@s.form action="activities" ]
                [@s.submit type="button" name="save" method="submit" ][@s.text name="form.buttons.submit" /][/@s.submit] 
              [/@s.form]  
              [#else]
              <button class="disabled" title="[@s.text name="planning.activityList.submit.disabled" /]"> [@s.text name="form.buttons.submit" /] </button>
              [/#if]
            </div>
          [/#if]
        [/#if]
      </div>
  
      [#if othersActivities?has_content]
        <div id="activityTables-2">      
          <table id="othersActivitiesTable" class="activityList">
            <thead>
              <tr>
                <th id="id">[@s.text name="planning.activityList.id" /]</th>
                <th id="activity">[@s.text name="planning.activityList.activity" /]</th>
                <th id="leaderName">[@s.text name="planning.activityList.leader" /]</th>
                <th id="theme">[@s.text name="planning.activityList.milestone" /]</th>
              </tr>
            </thead>
            <tbody>
            [#if othersActivities?has_content]
              [#list othersActivities as activity]
                <tr>
                  <td>
                    <a href=" [@s.url action='mainInformation' includeParams='get'] [@s.param name='${activityRequestParameter}']${activity.id}[/@s.param] [/@s.url]" >
                      ${activity.id}
                    </a> 
                  </td>
                  <td class="left">
                    <a href="
                    [@s.url action='mainInformation' includeParams='get']
                      [@s.param name='${activityRequestParameter}']${activity.id}[/@s.param]
                    [/@s.url]
                    " title="${activity.title}">
                      [#if activity.title?length < 70] ${activity.title}</a> [#else] [@utilities.wordCutter string=activity.title maxPos=70 /]...</a> [/#if]
                  </td>
                  <td>${activity.leader.acronym}</td>
                  <td>${activity.milestone.code}</td>
                </tr>
              [/#list]
            [/#if]
            </tbody>
          </table>
        </div>
      [/#if]
  
      <div id="activityTables-3">
        <table id="pastActivitiesTable" class="activityList">
          <thead>
            <tr>
              <th id="id">[@s.text name="planning.activityList.id" /]</th>
              <th id="activity">[@s.text name="planning.activityList.activity" /]</th>
              <th id="leaderName">[@s.text name="planning.activityList.leader" /]</th>
              <th id="theme">[@s.text name="planning.activityList.milestone" /]</th>
            </tr>
          </thead>
          <tbody>
          [#if pastActivities?has_content]
            [#list pastActivities as activity]
              <tr>
                <td>
                  <a target="_blank" href=" [@s.url action='activity' namespace="/home" includeParams='get'] [@s.param name='${publicActivityRequestParameter}']${activity.id}[/@s.param] [/@s.url]" >
                    ${activity.id}
                  </a> 
                </td>
                <td class="left">
                  <a target="_blank" href="
                  [@s.url action='activity' namespace="/home" includeParams='get']
                    [@s.param name='${publicActivityRequestParameter}']${activity.id}[/@s.param]
                  [/@s.url]
                  " title="${activity.title}">
                    [#if activity.title?length < 70] ${activity.title}</a> [#else] [@utilities.wordCutter string=activity.title maxPos=70 /]...</a> [/#if]
                </td>
                <td>${activity.leader.acronym}</td>
                <td>${activity.milestone.code}</td>
              </tr>
            [/#list]
          [/#if]
          </tbody>
        </table>
      </div>
      
    </div>
    
    <div class="clearfix"></div>
    [#if currentUser.admin]
      <div id="addActivity">
        <a href="${baseUrl}/planning/addActivity.do">Add new activity</a>
      </div>
    [/#if]
  </article>
  </section>
[#include "/WEB-INF/global/pages/footer.ftl"]