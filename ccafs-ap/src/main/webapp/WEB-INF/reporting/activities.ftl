[#ftl]
[#assign title = "Activity List" /]
[#assign jsIncludes = ["jquery"] /]
[#assign customCSS = ["${baseUrl}/css/reporting/activity-list.css"] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]

[#import "/WEB-INF/global/macros/forms.ftl" as form /]
<article>
  <div class="content">
    <h1>Activities 2012 = ${currentActivities?size}</h1>  
    
    <ul>
      [#list currentActivities as activity]
        <li>${activity.id}. ${activity.title}</li>
      [/#list]
    </ul>
    
    
    
    
  </div>
</article>
[#include "/WEB-INF/global/pages/footer.ftl"]