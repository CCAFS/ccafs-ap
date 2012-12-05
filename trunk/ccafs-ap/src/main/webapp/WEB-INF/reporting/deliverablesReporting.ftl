[#ftl]
[#assign title = "Activity deliverables Report" /]
[#assign globalLibs = ["", ""] /]
[#assign customJS = [""] /]
[#assign customCSS = ["${baseUrl}/css/reporting/deliverablesReporting.css"] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "activities" /]
[#assign currentStage = "deliverables" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
[#import "/WEB-INF/global/macros/utils.ftl" as utilities/]
    
  <section>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  <article class="halfContent">
    <h1>
      ${activity.leader.name?substring(0, activity.leader.name?index_of(" ") )} - [@s.text name="reporting.activityList.activity" /] ${activity.id}      
    </h1>
    
    <h6>[@s.text name="reporting.activityStatus.title" /]</h6>
    <p> ${activity.title} </p>
    
    <fieldset>
      <legend> <h5> [@s.text name="reporting.activityDeliverables.plannedDeliverables" /] </h5> </legend>
      
      <div>
        <span class="infoBox"> 
          <span class="title">[@s.text name="reporting.activityDeliverables.type" /]</span>  <span class="value"> Type</span> </span>
        </span>      
        <span class="infobox">
          <span class="title">[@s.text name="reporting.activityDeliverables.description" /]</span>  <span class="value"> Description </span> </span>
        </span>      
        <span class="infobox">
          <span class="title">[@s.text name="reporting.activityDeliverables.year" /]</span>  <span class="value"> 2012 </span> </span>
        </span>
        <span class="infobox">
          <span class="title">[@s.text name="reporting.activityDeliverables.status" /]</span>  <span class="value"> 2012 </span> </span>
        </span>
      </div>
      
    </fieldset>
      
    [#include "/WEB-INF/reporting/reportingStepSubMenu.ftl" /]  
  </article>
  
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]