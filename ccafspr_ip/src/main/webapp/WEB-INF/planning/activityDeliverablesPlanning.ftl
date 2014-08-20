[#ftl]
[#assign title = "Activity Deliverables" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "activity" /]
[#assign currentStage = "activityDeliverables" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning/projects", "action":"projects"},
  {"label":"projects", "nameSpace":"planning/projects", "action":"projects"},
  {"label":"activities", "nameSpace":"planning/activities", "action":"" },
  {"label":"activityDeliverables", "nameSpace":"planning/activities", "action":"activityDeliverables" }
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/planning/macros/activityDeliverablesTemplate.ftl" as activityDeliverable/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.deliverables.help" /] </p>
  </div>

  [#include "/WEB-INF/planning/activityPlanningSubMenu.ftl" /]

  
  [@s.form action="activityDeliverables" cssClass="pure-form"]  
  <article class="halfContent" id="activityDeliverables">
    <h1 class="contentTitle">
    [@s.text name="planning.deliverables" /] 
    </h1> 
	[@activityDeliverable.activityDeliverable  /]

    <div id="addDeliverable" class="addLink">
	  <a href="" class="addButton" >[@s.text name="planning.deliverables.addDeliverable" /]</a>
	</div>
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
     
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]