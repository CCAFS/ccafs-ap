[#ftl]
[#assign title = "Activity Impact Pathway" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/activityImpactPathwayPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "activity" /]
[#assign currentStage = "activityImpactPathway" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projects"},
  {"label":"projects", "nameSpace":"planning", "action":"projects"},
  {"label":"activities", "nameSpace":"planning/activities", "action":"" },
  {"label":"activityImpactPathway", "nameSpace":"planning/activities", "action":"activityImpactPathway" }
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.activityImpactPathways.help" /] </p>
  </div>

  [#include "/WEB-INF/planning/activityPlanningSubMenu.ftl" /]

  [@s.form action="activityImpactPathway" cssClass="pure-form"]  
  <article class="halfContent" id="activityImpactPathway">
  [#include "/WEB-INF/planning/activityIP-planning-sub-menu.ftl" /]
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantActivityPlanningAccessInterceptor--]
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
    
    <p>
      <b>[@s.text name="planning.activityImpactPathways.contributingTo" /]</b> 
      [#list projectFocusList as program]
        ${program.acronym}[#if program_has_next],[/#if]
      [/#list]
    </p>
    
    <div id="contributionsBlock" class="borderBox">
    </div>
    
    <div id="midOutcomesSelect">
      [@customForm.select name="midOutcomesList" i18nkey="planning.activityImpactPathways.outcome" listName="midOutcomes" className="midOutcomeSelect" /]
    </div>
    
    <input type="hidden" name="activityID" value="${activityID}" />

    [#if saveable]
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
    [/#if]
  </article>
  [/@s.form]  
</section>

[#-- Contribution template --]
<div class="contribution borderBox" id="contributionTemplate" style="display:none">
  [#-- Remove Contribution --]
  <div id="removeContribution" class="removeContribution removeElement removeLink" title="[@s.text name="preplanning.midOutcomes.removeMidOutcome" /]"></div>

  [#-- Midoutcome title --]
  <div class="midOutcomeTitle">
    <h6>[@s.text name="planning.activityImpactPathways.outcome2019" /]</h6>
    <p class="description"></p>
  </div>
  
  [#-- Indicators template --]
  <div class="indicators">
    <h6>[@s.text name="planning.activityImpactPathways.indicators" /]</h6>
    <div class="indicatorsBlock">
      <div class="midOutcomeIndicator" id="midOutcomeIndicatorTemplate">
        <input type="checkbox" />
        <label></label>
      </div>
    </div>
  </div>

  [#-- Major Output Group template --]
  <div class="mogs">
    <h6>[@s.text name="planning.activityImpactPathways.mogs" /]</h6>
    <div class="mogsBlock">
      <div class="mog" id="mogTemplate">
        <input type="checkbox" />
        <label></label>
      </div>
    </div>
  </div>
  
</div>

[#include "/WEB-INF/global/pages/footer.ftl"]