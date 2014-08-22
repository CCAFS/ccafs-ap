[#ftl]
[#assign title = "Projects" /]
[#assign globalLibs = ["jquery", "dataTable", "noty","autoSave"] /]
[#assign customJS = ["${baseUrl}/js/preplanning/projectsListPreplanning.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "projects" /]

[#assign breadCrumb = [
  {"label":"preplanning", "nameSpace":"pre-planning", "action":"outcomes"},
  {"label":"projects", "nameSpace":"pre-planning", "action":"projects"}
]/]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/templates/projectsListTemplate.ftl" as projectList/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png"/>
    <p>[@s.text name="preplanning.projects.help"/]</p>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl"/]
  [@s.form action="projects"]  
    <article class="halfContent" id="projects"> 
      <h1 class="contentTitle">
        [@s.text name="preplanning.projects.title"/]
      </h1>
      <p class="note">[@s.text name="preplanning.projects.note.myProjects" /]</p>
      [#if projects?size>0]
        <h3 class="projectSubTitle">[@s.text name="preplanning.projects.yourProjects"/]</h3>
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
      <hr/>
      <p class="note">[@s.text name="preplanning.projects.note.otherProjects" /]</p>
      <h3 class="projectSubTitle">[@s.text name="preplanning.projects.otherProjects" /]</h3>
      [@projectList.projectsList projects=allProjects canValidate=true /]
      
    </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]