[#ftl]
[#assign title = "Projects" /]
[#assign globalLibs = ["jquery", "dataTable", "noty","autoSave"] /]
[#assign customJS = ["${baseUrl}/js/preplanning/project-list.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "projects" /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/preplanning/macros/projectsListTemplate.ftl" as projectList/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.mainInformation.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl" /]
  
  [@s.form action="projects"]  
  <article class="halfContent" id="mainInformation"> 
    <h1 class="contentTitle">
    [@s.text name="preplanning.projects.title" /]  
    </h1>

    [#if projects?size>0]
      [@projectList.projectsList projects=projects canValidate=true canEditProject=true tableID="projects" /]
    [#else]
      ${currentUser.currentInstitution.program.name} does not have projects yet!
    [/#if]
    
    <div class="buttons">
      [@s.submit type="button" name="add"][@s.text name="form.buttons.add" /][/@s.submit]      
    </div>

  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]