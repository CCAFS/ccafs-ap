[#ftl]
[#assign title = "Activity Deliverables" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/activityDeliverablesPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "activity" /]
[#assign currentStage = "activityDeliverables" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projects"},
  {"label":"projects", "nameSpace":"planning", "action":"projects"},
  {"label":"activities", "nameSpace":"planning/projects", "action":"activities" ,"param":"projectID=${project.id}" },
  {"label":"activityDeliverables", "nameSpace":"planning/activities", "action":"activityDeliverables" }
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/planning/macros/activityDeliverablesTemplate.ftl" as deliverableTemplate/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.deliverables.help" /] </p>
  </div>

  [#include "/WEB-INF/planning/activityPlanningSubMenu.ftl" /]
  
  [@s.form action="activityDeliverables" cssClass="pure-form"]  
    <article class="halfContent" id="activityDeliverables">
      [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantActivityPlanningAccessInterceptor --]
      [#if !saveable]
        <p class="readPrivileges">
          [@s.text name="saving.read.privileges"]
            [@s.param][@s.text name="planning.deliverables"/][/@s.param]
          [/@s.text]
        </p>
      [/#if]
      <h1 class="contentTitle">
        [@s.text name="planning.deliverables" /] 
      </h1>
      [#if outputs?size > 0]
        [#if activity.deliverables?size > 0]
          [@deliverableTemplate.activityDeliverablesList deliverables=activity.deliverables canEdit=saveable /]
        [#else]
          [#-- Just show this empty message to those users who are not able to modify this section --]
          [#if !saveable]
            <p>[@s.text name="planning.deliverables.empty"/]</p>
          [/#if]
        [/#if]
        [#if saveable]
          <div id="addDeliverable" class="addLink">
            <a href="" class="addButton" >[@s.text name="planning.deliverables.addDeliverable" /]</a>
          </div>
          <input type="hidden" name="activityID" value="${activity.id}">
          <div class="buttons">
            [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
            [@s.submit type="button" name="next"][@s.text name="form.buttons.nextAndFinish" /][/@s.submit]
            [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
          </div>
        [/#if]
      [#else]
        [#-- Just show this empty message to those users who are not able to modify this section --]
        [#if !saveable]
          <p>[@s.text name="planning.deliverables.empty"/]</p>
        [#else]
          <p>
            [@s.text name="planning.deliverables.outputs.empty"]
              [@s.param]
                [@s.url action='activityImpactPathway' includeParams='get'][/@s.url]
              [/@s.param]
            [/@s.text]
          </p>
        [/#if]
      [/#if]
    </article>
  [/@s.form]  
</section>

[#-- Templates --]
[@s.form action="none"]  
  [#-- Activity Deliverable Template--]
  [@deliverableTemplate.activityDeliverableTemplate /]
  [#-- Activity Next user Template--]
  [@deliverableTemplate.nextUserTemplate template=true /]
[/@s.form]  

[#include "/WEB-INF/global/pages/footer.ftl"]