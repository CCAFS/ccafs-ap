[#ftl]
[#assign title = "Activity List" /]
[#assign globalLibs = ["jquery", "dataTable"] /]
[#assign customJS = ["${baseUrl}/js/reporting/activity-list.js"] /]
[#assign customCSS = ["${baseUrl}/css/reporting/activity-list.css", "${baseUrl}/css/libs/dataTables/jquery.dataTables.css", "${baseUrl}/css/reporting/customDataTable.css"] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]

    
  <section >
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  <article class="halfContent">
    <h1>[#if currentUser.leader??]${currentUser.leader.name}[/#if] Activities ${currentLogframe.year?c} = ${currentActivities?size}</h1>  
    
    <table id="activityList">
      <thead>
        <tr>
          <th id="id">[@s.text name="reporting.activityList.id" /]</th>
          <th id="activity">[@s.text name="reporting.activityList.activity" /]</th>
          <th id="leaderName">[@s.text name="reporting.activityList.leaderName" /]</th>
          <th id="theme">[@s.text name="reporting.activityList.theme" /]</th>
          <th id="lastModified">[@s.text name="reporting.activityList.lastModified" /]</th>
        </tr>
      </thead>
      <tbody>
        [#list currentActivities as activity]
          <tr>
            <td class="center">${activity.id}</td>
            <td>
              <a href="
              [@s.url action='status' includeParams='get']
                [@s.param name='${activityRequestParameter}']${activity.id}[/@s.param]
              [/@s.url]
              ">              
                [#if activity.title?length < 80]
                  ${activity.title}</a>
                [#else]
                  ${activity.title?substring(0, 80)}...</a>
                [/#if]
            </td>
            <td class="center">${activity.leader.name?substring(0, 10)}</td>
            <td class="center">${activity.milestone.output.objective.theme.code}</td>
            <td class="center">{Last Modified}</td>
          </tr>
        [/#list]  
      </tbody>
	<ul>
    </ul>
    </table>
    
  </article>
  </section>
[#include "/WEB-INF/global/pages/footer.ftl"]