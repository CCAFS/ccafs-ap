[#ftl]
[#assign title = "Activity List" /]
[#assign jsIncludes = ["jquery"] /]
[#assign customCSS = ["${baseUrl}/css/reporting/activity-list.css"] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]

[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
<article>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  <div class="content">
    <h1>[#if currentUser.leader??]${currentUser.leader.name}[/#if] Activities ${currentLogframe.year?c} = ${currentActivities?size}</h1>  
    
    <table id="activityList">
      <thead>
        <tr>
          <td>ID</td>
          <td>Activity</td>
          <td>Leader Name</td>
          <td>Theme</td>
          <td>Last Modified</td>
        </tr>
      </thead>
      <tbody>
        [#list currentActivities as activity]
          <tr>
            <td>${activity.id}</td>
            <td>${activity.title?substring(20)}</td>
            <td>${activity.leader.name}</td>
            <td>${activity.milestone.output.objective.theme.code}</td>
            <td>{Last Modified}</td>
          </tr>
        [/#list]  
      </tbody>
    <ul>
      
    </ul>
    </table>
    
    
    
    
  </div>
</article>
[#include "/WEB-INF/global/pages/footer.ftl"]