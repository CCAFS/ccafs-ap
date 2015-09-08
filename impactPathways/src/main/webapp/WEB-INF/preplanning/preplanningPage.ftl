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
      [#list programs as program]
        [#if program.id != globalProgramID]
          <a href="[@s.url action='outcomes'] [@s.param name="${programRequest}"]${program.id}[/@s.param] [/@s.url]">
            <div class="program">${program.acronym}</div>
          </a>
        [/#if]
      [/#list]
    </div>
  [/@s.form]  
  </article>
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]