[#ftl]
[#assign title = "Deliverable overview" /]

[#include "/WEB-INF/global/pages/popup-header.ftl" /]


<section class="content">
  
  <article class="halfContent">

    <div class="halfPartBlock">
      [@s.text name="reporting.deliverableOverview.description" /]
      ${deliverable.description}
    </div>

    <div class="halfPartBlock">
    </div>
    
    <div class="halfPartBlock">
    </div>
    
    <div class="halfPartBlock">
    </div>



  
    [@s.text name="reporting.deliverableOverview.deliverableInformation" /] 
    <hr />
    <br />
    [@s.text name="reporting.deliverableOverview.description" /]- ${deliverable.description}
    <br />
    [@s.text name="reporting.deliverableOverview.status" /]- ${deliverable.status.name}
    <br />
    [@s.text name="reporting.deliverableOverview.year" /]- ${deliverable.year}
    <br />
    [@s.text name="reporting.deliverableOverview.delvierableType" /]- ${deliverable.type.name}
    <br />
    
    <br />
    [@s.text name="reporting.deliverableOverview.activityInformation" /] - ${activity.id}
    <hr />
    [@s.text name="reporting.deliverableOverview.title" /] - ${activity.title}
    <br />
    [@s.text name="reporting.deliverableOverview.description" /] - ${activity.description!""}
    <br />
    [@s.text name="reporting.deliverableOverview.year" /] - ${activity.year}
    <br />

  </article>
  </section>
</body>