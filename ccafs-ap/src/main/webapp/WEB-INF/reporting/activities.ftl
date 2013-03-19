[#ftl]
[#assign title = "Activity List" /]
[#assign globalLibs = ["jquery", "dataTable", "noty"] /]
[#assign customJS = ["${baseUrl}/js/reporting/activity-list.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "activities" /]
[#assign currentStage = "status" /]
[#assign userRole = "${currentUser.role}"]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/utils.ftl" as utilities/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="reporting.activityList.help" /]</p>
  </div>
  
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  <article class="halfContent">
    <h1>[#if currentUser.leader??]${currentUser.leader.name}[/#if] ([@s.text name="reporting.activityList.activities" /] ${currentReportingLogframe.year?c})</h1>    
    <table id="activityList">
      <thead>
        <tr>
          <th id="id">[@s.text name="reporting.activityList.id" /]</th>
          <th id="activity">[@s.text name="reporting.activityList.activity" /]</th>
          <th id="contactPerson">[@s.text name="reporting.activityList.contactPerson" /]</th>
          <th id="theme">[@s.text name="reporting.activityList.milestone" /]</th>
          <th id="status">[@s.text name="reporting.activityList.reportingStatus" /]</th>
        </tr>
      </thead>
      <tbody>
        [#list currentActivities as activity]
          <tr>
            <td>${activity.id}</td>
            <td class="left">
              <a href="
              [@s.url action='status' includeParams='get']
                [@s.param name='${activityRequestParameter}']${activity.id}[/@s.param]
              [/@s.url]
              " title="${activity.title}">
                [#if activity.title?length < 50] ${activity.title}</a> [#else] [@utilities.wordCutter string=activity.title maxPos=50 /]...</a> [/#if]
            </td>
            <td>
              [#if activity.contactPersons??]
                ${activity.contactPersons[0].name}
              [#else]
                [@s.text name="reporting.activityList.contactPerson.empty" /]
              [/#if]
            </td>               
            <td>${activity.milestone.code}</td>
            <td>
              [#if activityStatuses[activity_index]?has_content]
                [#assign problemDescription=activityStatuses[activity_index]]
                <img src="${baseUrl}/images/global/icon-incomplete.png" alt="Activity Incomplete" title="${problemDescription}" />
              [#else]
                <img src="${baseUrl}/images/global/icon-complete.png" alt="Activity Completed" />
              [/#if]
              </td>
          </tr>
        [/#list]  
      </tbody>
	<ul>
    </ul>
    </table>
    
  </article>
  </section>
[#include "/WEB-INF/global/pages/footer.ftl"]