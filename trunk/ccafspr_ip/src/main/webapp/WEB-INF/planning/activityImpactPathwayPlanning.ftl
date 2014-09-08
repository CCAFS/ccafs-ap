[#ftl]
[#assign title = "Activity Impact Pathway" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen", "cytoscape", "qtip","cytoscapePanzoom"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/ipGraph.js", "${baseUrl}/js/planning/activityImpactPathwayPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "activity" /]
[#assign currentStage = "activityImpactPathway" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projects"},
  {"label":"project", "nameSpace":"planning/projects", "action":"description", "param":"projectID=${project.id}"},
  {"label":"activities", "nameSpace":"planning/projects", "action":"activities" ,"param":"projectID=${project.id}" },
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
      [@s.text name="planning.activity" /]: ${activity.composedId} - [@s.text name="planning.activityImpactPathways.title" /] 
    </h1> 
    <div class="borderBox">
      <p>
        <b>[@s.text name="planning.activityImpactPathways.contributingTo" /]</b> 
        [#list projectFocusList as program]
          ${program.acronym}[#if program_has_next],[/#if]
        [/#list]
      </p>
    </div>
    
    <div id="contributionsBlock" class="">
      [#if midOutcomesSelected?has_content]
        [#list midOutcomesSelected as midOutcome]
          <div class="contribution borderBox">
            [#if saveable]
              [#-- Remove Contribution --]
              <div id="removeContribution" class="removeContribution removeElement removeLink" title="[@s.text name="preplanning.midOutcomes.removeMidOutcome" /]"></div>
            [/#if]
          
            [#-- Midoutcome title --]
            <div class="midOutcomeTitle">
              <input id="midOutcomeID" value="${midOutcome.id}" type="hidden" />
              <input id="programID" value="${midOutcome.program.id}" type="hidden" /> 
              <h6>[@s.text name="planning.activityImpactPathways.outcome2019" /]</h6>
              <p class="description"> ${midOutcome.description} </p>
            </div>
            
            [#-- Major Output Group list --]
            <div class="mogs">
              <h6>[@s.text name="planning.activityImpactPathways.mogs" /]</h6>
              [#if action.getMidOutcomeOutputs(midOutcome.id)?has_content]
                [#assign outputs = action.getMidOutcomeOutputs(midOutcome.id)]
                <div class="mogsBlock">
                  [#list outputs as output]
                      <div class="mog">
                        <input name="activity.outputs.contributesTo[0].id" value="${midOutcome.id}"  type="hidden" />
                        <input type="checkbox" name="outputs.id" value="${output.id}" [#if activity.containsOutput(output.id)] checked [/#if] />
                        <label> ${output.description}</label>
                      </div>
                  [/#list]
                </div>
              [/#if]
            </div>
            
            [#-- Indicators list --]
            <div class="indicators">
              <h6>[@s.text name="planning.activityImpactPathways.indicators" /]</h6>
              [#if midOutcome.indicators?has_content]
              <div class="indicatorsBlock">
                [#list midOutcome.indicators as indicator]
                  [#if activity.getIndicatorByParent(indicator.id)?has_content]
                    [#assign activityIndicator = activity.getIndicatorByParent(indicator.id) /]
                    <div class="midOutcomeIndicator" >
                      <input type="hidden" disabled name="activity.indicators.id" value="${activityIndicator.id}" />
                      <input type="checkbox" name="activity.indicators.parent.id" value="${indicator.id}" checked />
                      [#if indicator.parent?has_content]
                        <label class="indicatorDescription">${indicator.parent.description}</label>
                      [#else]
                        <label class="indicatorDescription">${indicator.description}</label>
                      [/#if]
                      <div class="checkboxGroup vertical indicatorNarrative" >
                        [#-- Target value --]
                        <label> <h6>[@s.text name="planning.activityImpactPathways.targetValue" /]</h6></label>
                        <input type="text" name="activity.indicators.target" value="${activityIndicator.target}" >
                        
                        <label> <h6>[@s.text name="planning.activityImpactPathways.targetNarrative" /]</h6></label>
                        <textarea name="activity.indicators.description" >${activityIndicator.description}</textarea>
                      </div> 
                    </div>  
                  [#else]
                    <div class="midOutcomeIndicator" >
                      <input type="hidden" disabled name="indicators.id" value="-1" />
                      <input type="checkbox" name="activity.indicators.parent.id" value="${indicator.id}" />
                      [#if indicator.parent?has_content]
                        <label class="indicatorDescription">${indicator.parent.description}</label> 
                      [#else]
                        <label class="indicatorDescription">${indicator.description}</label> 
                      [/#if]
                      <div class="checkboxGroup vertical indicatorNarrative" style="display:none">
                        [#-- Target value --]
                        <label>  <h6>[@s.text name="planning.activityImpactPathways.targetValue" /]</h6></label>
                        <input type="text" name="activity.indicators.target" >
                        
                        <label>  <h6>[@s.text name="planning.activityImpactPathways.targetNarrative" /]</h6></label>
                        <textarea name="activity.indicators.description" ></textarea>
                      </div> 
                      
                    </div>
                  [/#if]
                [/#list]  
              </div>
              [/#if]
              </div>
          
          </div>
        [/#list]
      [/#if]
    </div>
    
    [#if saveable]
      <div id="midOutcomesSelect">
        [@customForm.select name="midOutcomesList" i18nkey="planning.activityImpactPathways.outcome" listName="midOutcomes" className="midOutcomeSelect" /]
      </div>
      <input type="hidden" name="activityID" value="${activityID}" />
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
    <input id="midOutcomeID" value="" type="hidden" />
    <input id="programID" value="" type="hidden" /> 
    <h6>[@s.text name="planning.activityImpactPathways.outcome2019" /]</h6>
    <p class="description"></p>
  </div>
  
 [#-- Major Output Group template --]
  <div class="mogs">
    <h6>[@s.text name="planning.activityImpactPathways.mogs" /]</h6>
    <div class="mogsBlock">
      <img class="ajax-loader" style="" src="${baseUrl}/images/global/loading.gif" alt="Loader ..." />
      <div class="mog" id="mogTemplate">
        <input name="contributesTo[0].id" type="hidden" />
        <input type="checkbox" name="activity.outputs" />
        <label></label>
      </div>
    </div>
  </div>
  
  [#-- Indicators template --]
  <div class="indicators">
    <h6>[@s.text name="planning.activityImpactPathways.indicators" /]</h6>
    <div class="indicatorsBlock">
      <img class="ajax-loader" style="" src="${baseUrl}/images/global/loading.gif" alt="Loader ..." />
      <div class="midOutcomeIndicator" id="midOutcomeIndicatorTemplate">
        <input type="hidden" disabled name="activity_indicator_id" value="-1" />
        <input type="checkbox" name="activity.indicators" />
        <label class="indicatorDescription"></label>
        
        <div class="checkboxGroup vertical indicatorNarrative" style="display:none">
          [#-- Target value --]
          <label> <h6>[@s.text name="planning.activityImpactPathways.targetValue" /]</h6></label>
          <input type="text" name="activity_indicator_target" >
          
          <label> <h6>[@s.text name="planning.activityImpactPathways.targetNarrative" /]</h6></label>
          <textarea name="activity_indicator_description" ></textarea>
        </div>
      </div>
    </div>
  </div>

</div>

[#include "/WEB-INF/global/pages/footer.ftl"]