[#ftl]
[#assign title = "Activity deliverables ranking Report" /]
[#assign globalLibs = ["jquery", "noty", "star-rating"] /]
[#assign customJS = ["${baseUrl}/js/reporting/deliverables/deliverablesReportingRank.js"] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "activities" /]
[#assign currentStage = "ranking" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="reporting.activityDeliverables.ranking.help" /]</p>
  </div>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  [@s.form action="deliverables"]
  <article class="halfContent">
    [#include "/WEB-INF/reporting/deliverables/deliverablesReportingSubMenu.ftl" /]
    <h1 class="contentTitle">
      [@s.text name="reporting.activityDeliverables.deliverable" /] - ${deliverableID}
    </h1> 
    
    <h6>[@s.text name="reporting.activityDeliverables.ranking.rankDeliverable" /]</h6>
    <div class="borderBox">
      <p>[@s.text name="reporting.activityDeliverables.ranking.rankDeliverableText" /]</p>
      <div id="rankingBlock"> 
        <input class="hover-star required" type="radio" name="deliverable.rank" value="1" title="[@s.text name='reporting.activityDeliverables.ranking.level.notImportant' /]"/>
        <input class="hover-star" type="radio" name="deliverable.rank" value="2" title="[@s.text name='reporting.activityDeliverables.ranking.level.lowImportance' /]"/>
        <input class="hover-star" type="radio" name="deliverable.rank" value="3" title="[@s.text name='reporting.activityDeliverables.ranking.level.good' /]"/>
        <input class="hover-star" type="radio" name="deliverable.rank" value="4" title="[@s.text name='reporting.activityDeliverables.ranking.level.important' /]"/>
        <input class="hover-star" type="radio" name="deliverable.rank" value="5" title="[@s.text name='reporting.activityDeliverables.ranking.level.veryImportant' /]" />
        <div id="hover-test" style=""></div> 
        <div class="clearfix"></div>
      </div>
     
    </div>
    
    <div id="rate-legend">
      <ul>
        <li><strong>[@s.text name='reporting.activityDeliverables.ranking.level.veryImportant' /] </strong> -
        [@s.text name='reporting.activityDeliverables.ranking.level.veryImportant.help' /] </li>
        <li><strong>[@s.text name='reporting.activityDeliverables.ranking.level.important' /] </strong> -
        [@s.text name='reporting.activityDeliverables.ranking.level.important.help' /] </li>
        <li><strong>[@s.text name='reporting.activityDeliverables.ranking.level.good' /] </strong> -
        [@s.text name='reporting.activityDeliverables.ranking.level.good.help' /] </li>
        <li><strong>[@s.text name='reporting.activityDeliverables.ranking.level.lowImportance' /] </strong> -
        [@s.text name='reporting.activityDeliverables.ranking.level.lowImportance.help' /] </li>
        <li><strong>[@s.text name='reporting.activityDeliverables.ranking.level.notImportant' /] </strong> -
        [@s.text name='reporting.activityDeliverables.ranking.level.notImportant.help' /] </li>
      </ul>
    </div>
     
    
    <!-- internal parameter -->
    <input name="activityID" type="hidden" value="${activityID}" />
    <input name="deliverableID" type="hidden" value="${deliverable.id?c}" />
    
    [#if canSubmit]
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
    [/#if]

    </article>
  [/@s.form]
 
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]