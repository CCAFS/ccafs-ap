[#ftl]
[#assign title = "Activity List" /]
[#assign globalLibs = ["jquery", "dataTable"] /]
[#assign customJS = ["${baseUrl}/js/reporting/activity-list.js"] /]
[#assign customCSS = ["${baseUrl}/css/reporting/activity-list.css", "${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/reporting/customDataTable.css"] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "activities" /]
[#assign currentStage = "status" /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/utils.ftl" as utilities/]
    
  <section >
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  <article class="halfContent">
    <h1>${milestone.code}</h1>
    
    <p>${milestone.description}</p>  
    
    
    
  </article>
  </section>
[#include "/WEB-INF/global/pages/footer.ftl"]