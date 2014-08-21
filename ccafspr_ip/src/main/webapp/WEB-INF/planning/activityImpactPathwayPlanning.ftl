[#ftl]
[#assign title = "Activity Impact Pathway" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "activity" /]
[#assign currentStage = "activityImpactPathway" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning/projects", "action":"projects"},
  {"label":"projects", "nameSpace":"planning/projects", "action":"projects"},
  {"label":"activities", "nameSpace":"planning/activities", "action":"" },
  {"label":"activityImpactPathway", "nameSpace":"planning/activities", "action":"activityImpactPathway" }
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/planning/macros/activityImpactPathwayTemplate.ftl" as activityImpactPathway/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.activityImpactPathways.help" /] </p>
  </div>

  [#include "/WEB-INF/planning/activityPlanningSubMenu.ftl" /]

  [@s.form action="activityImpactPathways" cssClass="pure-form"]  
  <article class="halfContent" id="activityImpactPathway">
    [#-- Informing user that he/she doesn't have enough privileges to edit. See Grant -- AccessInterceptor--]
      [#if !saveable]
        <p class="readPrivileges">
          [@s.text name="saving.read.privileges"]
            [@s.param][@s.text name="planning.activityImpactPathways.title"/][/@s.param]
          [/@s.text]
        </p>
      [/#if]
    <h1 class="contentTitle">
    [@s.text name="planning.activityImpactPathways.title" /] 
    </h1> 
    [@activityImpactPathway.activityImpactPathwayContribute  /]
    <div class="halfPartBlock">
      <b>[@s.text name="planning.activityImpactPathways.higherImpact" /]</b>
    </div>
    <div id="addActivityIPMog" class="addLink">
      <a href="" class="addButton" >[@s.text name="planning.activityImpactPathways.addMog" /]</a>
    </div>
    <hr/>
    [@activityImpactPathway.activityImpactPathwayTarget  /]
    <div id="addActivityIPTarget" class="addLink">
      <a href=""  class="addButton">[@s.text name="planning.activityImpactPathways.addTarget" /]</a>
    </div>
    [#if saveable]
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
[#include "/WEB-INF/global/pages/footer.ftl"]