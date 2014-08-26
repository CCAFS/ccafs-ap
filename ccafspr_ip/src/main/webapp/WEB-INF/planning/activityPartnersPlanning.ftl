[#ftl]
[#assign title = "Activity Partners" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/activityPartnersPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "activity" /]
[#assign currentStage = "activityPartners" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projects"},
  {"label":"projects", "nameSpace":"planning", "action":"projects"},
  {"label":"activities", "nameSpace":"planning/projects", "action":"activities" ,"param":"projectID=${project.id}" },
  {"label":"activityPartners", "nameSpace":"planning/activities", "action":"activityPartners" }
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/planning/macros/activityPartnersTemplate.ftl" as activityPartnersTemplate/]

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.activityPartner.help" /] </p>
  </div>

  [#include "/WEB-INF/planning/activityPlanningSubMenu.ftl" /]


  [@s.form action="activityPartners" cssClass="pure-form"]  
    <article class="halfContent" id="activityPartners">
      [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantActivityPlanningAccessInterceptor--]
      [#if !saveable]
        <p class="readPrivileges">
          [@s.text name="saving.read.privileges"]
            [@s.param][@s.text name="planning.activityPartner.title"/][/@s.param]
          [/@s.text]
        </p>
      [/#if]
      <h1 class="contentTitle">
        [@s.text name="planning.activityPartner.title" /] 
      </h1>
      [#-- Display message in case there are not Activity Partners and the user has not enough privileges to edit --]
      [#if !saveable && activity.activityPartners?size==0]
        <p>[@s.text name="planning.activityPartner.noPartners" /]</p>
      [#else]
        [@activityPartnersTemplate.activityPartner activityPartners=activity.activityPartners canEdit=saveable canRemove=saveable /]
      [/#if]
      [#-- Showing buttons only for those who have privileges. --]
      [#if saveable]
      <div id="addActivityPartner" class="addLink">
        <a href=""  class="addButton">[@s.text name="planning.activityPartner.addPartner" /]</a>
      </div>
        <input type="hidden" name="activityID" value="">
        <div class="buttons">
          [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
          [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
          [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
        </div>
      [/#if]
    </article>
  [/@s.form]  
</section>

[#-- Activity partner Template --]
[@activityPartnersTemplate.partnerTemplate/]
[#include "/WEB-INF/global/pages/footer.ftl"]