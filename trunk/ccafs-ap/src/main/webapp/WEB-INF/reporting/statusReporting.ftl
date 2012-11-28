[#ftl]
[#assign title = "Activity Status Report" /]
[#assign globalLibs = ["jquery"] /]
[#assign customJS = [""] /]
[#assign customCSS = ["${baseUrl}/css/reporting/statusReporting.css", "", ""] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "activities" /]
[#assign currentStage = "status" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/utils.ftl" as utilities/]
    
  <section>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  <article class="halfContent">
    <h1>
      ${activity.id} - [#if activity.title?length < 60] ${activity.title}</a> [#else] [@utilities.wordCutter string=activity.title maxPos=60 /]...</a> [/#if]
    </h1>
    <span class="infoBox"> <span class="title">Start date:</span> <span class="value"> 2012-10-01</span> </span>
    <span class="infoBox"> <span class="title">Start date:</span> <span class="value"> 2012-10-01</span> </span>
    <span class="infoBox"> <span class="title">Budget:</span> <span class="value"> 201.210,01</span> </span>
    <span class="infoBoxLarge"> <span class="title">Contact person:</span> <span class="value"> Hernan Carvajal (carvajal.hernan@cgiar.org)</span> </span>
    
  </article>
  </section>
[#include "/WEB-INF/global/pages/footer.ftl"]