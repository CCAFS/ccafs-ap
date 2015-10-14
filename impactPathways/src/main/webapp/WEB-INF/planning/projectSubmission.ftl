[#ftl]
[#assign title = "Project Submission" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "submit" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"planning", "action":"projectsList"}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  
  [#-- include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /--]
  
 
  <article class="halfContent" id="projectDeliverable">  
    <h2>The project has been successfully submitted</h2>
    <p>${project.title}</p>
  </article>
   
</section> 


[#include "/WEB-INF/global/pages/footer.ftl"]