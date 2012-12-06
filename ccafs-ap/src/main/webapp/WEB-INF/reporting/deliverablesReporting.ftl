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
    
  <section>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  <article class="halfContent">
    <h1>
      ${activity.leader.acronym} - [@s.text name="reporting.activityList.activity" /] ${activity.id}      
    </h1>
    
    <h6>[@s.text name="reporting.activityStatus.title" /]</h6>
    <p> ${activity.title} </p>
    
    <fieldset>
      <legend> <h5> [@s.text name="reporting.activityDeliverables.plannedDeliverables" /] </h5> </legend>
      
      [#if deliverables??]
        <div>
          [@s.select label="What's your favor search engine?" list="deliverableTypesList" listKey="id" listValue="name" name="selectedDeliverableType" /]
        </div>
      [#else]
        <p> There is no planned deliverables </p>
      [/#if]
      
    </fieldset>
      
    [#include "/WEB-INF/reporting/reportingStepSubMenu.ftl" /]  
  </article>
  
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]