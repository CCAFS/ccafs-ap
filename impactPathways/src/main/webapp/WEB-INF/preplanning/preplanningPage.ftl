[#ftl]
[#assign title = "Projects" /]
[#assign globalLibs = ["jquery", "dataTable", "noty","autoSave"] /]
[#assign customJS = ["${baseUrl}/js/preplanning/projectsListPreplanning.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "projects" /]

[#assign breadCrumb = [
  {"label":"preplanning", "nameSpace":"pre-planning", "action":"outcomes"}
]/]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png"/>
    <p>//TODO</p>
  </div>
  <article id="preplanningPrograms" class="fullBlock" > 
  [@s.form action="projects"]  
    <br />
    <h1 class="contentTitle">Pre-Planning introduction</h1>
    <div class="borderBox">
      <a href=""><div class="program">Flagship 1</div></a>
      <a href=""><div class="program">Flagship 2</div></a>
      <a href=""><div class="program">Flagship 3</div></a>
      <a href=""><div class="program">Flagship 4</div></a>
      <a href=""><div class="program">Latin America</div></a>
      <a href=""><div class="program">South East Asia</div></a>
      <a href=""><div class="program">South Asia</div></a>
      <a href=""><div class="program">East Africa</div></a>
      <a href=""><div class="program">West Africa</div></a>
    </div>
  [/@s.form]  
  </article>
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]