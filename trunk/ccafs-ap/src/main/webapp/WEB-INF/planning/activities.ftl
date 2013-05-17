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
    <p> [@s.text name="planning.activityList.help" /] </p>
  </div>
  
  <article class="fullContent">
    <h1>[#if currentUser.leader??]${currentUser.leader.name}[/#if] ([@s.text name="planning.activityList.activities" /] ${currentPlanningLogframe.year?c})</h1>   
    <table id="activityList">
      <thead>
        <tr>
          <th id="id">[@s.text name="planning.activityList.id" /]</th>
          <th id="activity">[@s.text name="planning.activityList.activity" /]</th>
          <th id="contactPerson">[@s.text name="planning.activityList.contactPerson" /]</th>
          <th id="theme">[@s.text name="planning.activityList.milestone" /]</th>
        </tr>
      </thead>
      <tbody>
      [#if currentActivities??]
        [#list currentActivities as activity]
          <tr>
            <td>${activity.id}</td>
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
                [#if activity.contactPersons[0].email??]
                  <a href="mailto:${activity.contactPersons[0].email}">${activity.contactPersons[0].name}</a>
                [#else]
                  ${activity.contactPersons[0].name}
                [/#if]
              [#else]
                [@s.text name="planning.activityList.contactPerson.empty" /]
              [/#if]
            </td>               
            <td>${activity.milestone.code}</td>
          </tr>
        [/#list]
      [/#if]
      </tbody>
    </table>
    <div class="clearfix"></div>
    [#if currentUser.admin]
      <div id="addActivity">
        <a href="${baseUrl}/planning/addActivity.do">Add new activity</a>
      </div>
    [/#if]
  </article>
  </section>
[#include "/WEB-INF/global/pages/footer.ftl"]