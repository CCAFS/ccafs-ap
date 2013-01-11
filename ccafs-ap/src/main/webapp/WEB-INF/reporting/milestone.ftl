[#ftl]
[#assign title = "Milestone details" /]
[#assign customCSS = ["${baseUrl}/css/reporting/milestone.css"] /]

[#include "/WEB-INF/global/pages/popup-header.ftl" /]
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
</body>