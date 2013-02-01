[#ftl]
[#assign title = "Activity List" /]
[#assign globalLibs = ["jquery", "dataTable"] /]
[#assign customJS = ["${baseUrl}/js/reporting/activity-list.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/reporting/customDataTable.css"] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "activities" /]
[#assign currentStage = "status" /]
[#assign userRole = "${currentUser.role}"]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/utils.ftl" as utilities/]
    
  <section class="content">
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  <article class="halfContent">
    <h1>[#if currentUser.leader??]${currentUser.leader.name}[/#if] ([@s.text name="reporting.activityList.activities" /] ${currentLogframe.year?c})</h1>  
    
    <table id="activityList">
      <thead>
        <tr>
          <th id="id">[@s.text name="reporting.activityList.id" /]</th>
          <th id="activity">[@s.text name="reporting.activityList.activity" /]</th>
          <th id="leaderName">[@s.text name="reporting.activityList.leaderName" /]</th>
          <th id="theme">[@s.text name="reporting.activityList.theme" /]</th>
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
                [#if activity.title?length < 60] ${activity.title}</a> [#else] [@utilities.wordCutter string=activity.title maxPos=60 /]...</a> [/#if]
            </td>
            <td>${activity.leader.acronym}</td>
            <td>${activity.milestone.output.objective.theme.code}</td>
          </tr>
        [/#list]  
      </tbody>
	<ul>
    </ul>
    </table>
    
  </article>
  </section>
[#include "/WEB-INF/global/pages/footer.ftl"]