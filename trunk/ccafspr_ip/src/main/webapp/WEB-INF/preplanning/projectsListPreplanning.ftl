[#ftl]
[#assign title = "Projects" /]
[#assign globalLibs = ["jquery", "dataTable", "noty","autoSave"] /]
[#assign customJS = ["${baseUrl}/js/preplanning/projectsListPreplanning.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "projects" /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/templates/projectsListTemplate.ftl" as projectList/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.projectsList.help1" /] </p>
    <p> [@s.text name="planning.projectsList.help2" /] </p>
    <p> [@s.text name="planning.projectsList.help3" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl" /]
  
  [@s.form action="projects"]  
  <article class="halfContent" id="projects"> 
    <h1 class="contentTitle">
    [@s.text name="preplanning.projects.title" /]  
    </h1>

    [#if projects?size>0]
      [@projectList.projectsList projects=projects canValidate=true /]
      <div class="buttons">
        [@s.submit type="button" name="add"][@s.text name="preplanning.projects.addProject" /][/@s.submit]
      </div>
    [#else]
    <div class="borderBox center">
      <p>[@s.text name="preplanning.projects.empty" /]</p> 
      [@s.submit type="button" name="add"][@s.text name="preplanning.projects.addProject" /][/@s.submit]
    </div>
    [/#if]
    

  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]