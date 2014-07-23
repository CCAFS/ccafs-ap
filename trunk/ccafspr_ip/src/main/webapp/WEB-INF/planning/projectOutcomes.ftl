[#ftl]
[#assign title = "Project Outcomes" /]
[#assign globalLibs = ["jquery", "noty","autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPrePlanningSection = "projects" /]
[#assign currentStage = "projectOutcomes" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.mainInformation.help" /] </p>
  </div>
  [#include "/WEB-INF/planning/projectPlanningSubMenu.ftl" /]
  
  [@s.form action="projectOutcomes" cssClass="pure-form"]  
  <article class="halfContent" id="projectOutcomes"> 
  	 
    <h1 class="contentTitle">
    [@s.text name="planning.projectOutcomes.title" /]  
    </h1> 
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]