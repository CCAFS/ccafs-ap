[#ftl]
[#assign title = "Activity Status Report" /]
[#assign globalLibs = ["jquery"] /]
[#assign customJS = [""] /]
[#assign customCSS = ["", "", ""] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]

    
  <section>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  <article class="halfContent">
    <p>title: ${activity.title}</p> 
    
    
  </article>
  </section>
[#include "/WEB-INF/global/pages/footer.ftl"]