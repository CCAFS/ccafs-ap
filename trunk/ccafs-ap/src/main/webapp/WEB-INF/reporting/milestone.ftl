[#ftl]
[#assign title = "Activity List" /]
[#assign globalLibs = ["", ""] /]
[#assign customJS = [""] /]
[#assign customCSS = ["", "", ""] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "activities" /]
[#assign currentStage = "status" /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
    
  <section >
  
  <article class="content">
    <h1>[@s.text name="reporting.activityMilestone.theme" /] ${milestone.output.objective.theme.code}</h1>    
    <p>${milestone.output.objective.theme.description}</p>  
    <br /><br />
    <h6>[@s.text name="reporting.activityMilestone.objective" /] ${milestone.output.objective.code}</h6>
    <p>${milestone.output.objective.description}</p>
    <br /><br />
    <h6>[@s.text name="reporting.activityMilestone.output" /] ${milestone.output.code}</h6>
    <p>milestone.output.description</p>
    <br /><br />
    <h6>[@s.text name="reporting.activityMilestone.milestone" /] ${milestone.code}</h6>
    <p>${milestone.description}</p>
    
  </article>
  </section>
[#include "/WEB-INF/global/pages/footer.ftl"]