[#ftl]
[#assign title = "Project Impact Pathway" /]
[#assign globalLibs = ["jquery", "noty", "chosen", "cytoscape", "qtip","cytoscapePanzoom"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/ipGraph.js", "${baseUrl}/js/planning/projectImpactPathwayPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "projectImpactPathway" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projects"},
  {"label":"project", "nameSpace":"planning/projects", "action":"description", "param":"projectID=${project.id}"}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.projectImpactPathways.help" /] </p>
  </div>

  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]

  [@s.form action="impactPathway" cssClass="pure-form"]  
  <article class="halfContent" id="activityImpactPathway">
    [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
    [#include "/WEB-INF/planning/projectIP-planning-sub-menu.ftl" /]
    
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantActivityPlanningAccessInterceptor--]
    [#if !saveable]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"]
          [@s.param][@s.text name="planning.projectImpactPathways.title"/][/@s.param]
        [/@s.text]
      </p>
    [/#if]
    
    <h1 class="contentTitle">
      [@s.text name="planning.project" /]: ${project.composedId} - [@s.text name="planning.projectImpactPathways.title" /] 
    </h1> 
    <div class="borderBox">
      <p>
        <b>[@s.text name="planning.projectImpactPathways.contributingTo" /]</b> 
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
              <h6>[@s.text name="planning.projectImpactPathways.outcome2019" /]</h6>
              <p class="description"> ${midOutcome.description} </p>
            </div>

            [#-- Indicators list --]
            <div class="indicators">
              <h6>[@s.text name="planning.projectImpactPathways.indicators" /]</h6>
              [#if midOutcome.indicators?has_content]
              <div class="indicatorsBlock">
                [#list midOutcome.indicators as indicator]
                  [#if project.getIndicatorByParent(indicator.id)?has_content]
                    [#assign projectIndicator = project.getIndicatorByParent(indicator.id) /]
                    <div class="midOutcomeIndicator" >
                      <input type="hidden" disabled name="project.indicators.id" value="${projectIndicator.id}" />
                      <input type="checkbox" name="project.indicators.parent.id" value="${indicator.id}" checked />
                      [#if indicator.parent?has_content]
                        <label class="indicatorDescription">${indicator.parent.description}</label>
                      [#else]
                        <label class="indicatorDescription">${indicator.description}</label>
                      [/#if]
                      <div class="checkboxGroup vertical indicatorNarrative" >
                        [#-- Target value --]
                        <label> <h6>[@s.text name="planning.projectImpactPathways.targetValue" /]</h6></label>
                        <!-- input type="text" name="project.indicators.target" value="${projectIndicator.target!}" -->
                        <textarea name="project.indicators.target" >${projectIndicator.target!}</textarea>
                      </div> 
                      <div class="checkboxGroup vertical indicatorNarrative" >
                        <label> <h6>[@s.text name="planning.projectImpactPathways.targetNarrative" /]</h6></label>
                        <textarea name="project.indicators.description" >${projectIndicator.description!}</textarea>
                      </div> 
                    </div>  
                  [#else]
                    <div class="midOutcomeIndicator" >
                      <input type="hidden" disabled name="indicators.id" value="-1" />
                      <input type="checkbox" name="project.indicators.parent.id" value="${indicator.id}" />
                      [#if indicator.parent?has_content]
                        <label class="indicatorDescription">${indicator.parent.description}</label> 
                      [#else]
                        <label class="indicatorDescription">${indicator.description}</label> 
                      [/#if]
                      <div class="checkboxGroup vertical indicatorNarrative" style="display:none">
                        [#-- Target value --]
                        <label>  <h6>[@s.text name="planning.projectImpactPathways.targetValue" /]</h6></label>
                        <input type="text" name="project.indicators.target" >

                        <label>  <h6>[@s.text name="planning.projectImpactPathways.targetNarrative" /]</h6></label>
                        <textarea name="project.indicators.description" ></textarea>
                      </div> 

                    </div>
                  [/#if]
                [/#list]  
              </div>
              [/#if]
              </div>

            [#-- Major Output Group list --]
            <div class="mogs">
              <h6>[@s.text name="planning.projectImpactPathways.mogs" /]</h6>
              [#if action.getMidOutcomeOutputs(midOutcome.id)?has_content]
                [#assign outputs = action.getMidOutcomeOutputs(midOutcome.id)]
                <div class="mogsBlock">
                  [#list outputs as output]
                      <div class="mog">
                        <input name="project.outputs.contributesTo[0].id" value="${midOutcome.id}"  type="hidden" />
                        <input type="checkbox" name="outputs.id" value="${output.id}" [#if project.containsOutput(output.id)] checked [/#if] />
                        <label> ${output.program.acronym} - MOG #${output_index+1}: ${output.description} </label>
                      </div>
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
        [@customForm.select name="midOutcomesList" i18nkey="planning.projectImpactPathways.outcome" listName="midOutcomes" className="midOutcomeSelect" /]
      </div>
      
      <input type="hidden" name="projectID" value="${projectID}" />
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
    <h6>[@s.text name="planning.projectImpactPathways.outcome2019" /]</h6>
    <p class="description"></p>
  </div>
  
  [#-- Indicators template --]
  <div class="indicators">
    <h6>[@s.text name="planning.projectImpactPathways.indicators" /]</h6>
    <div class="indicatorsBlock">
      <img class="ajax-loader" style="" src="${baseUrl}/images/global/loading.gif" alt="Loader ..." />
      <div class="midOutcomeIndicator" id="midOutcomeIndicatorTemplate">
        <input type="hidden" disabled name="activity_indicator_id" value="-1" />
        <input type="checkbox" name="project.indicators" />
        <label class="indicatorDescription"></label>
        
        <div class="checkboxGroup vertical indicatorNarrative" style="display:none">
          [#-- Target value --]
          <label> <h6>[@s.text name="planning.projectImpactPathways.targetValue" /]</h6></label>
          <input type="text" name="project_indicator_target" class="targetValue">
          
          <label> <h6>[@s.text name="planning.projectImpactPathways.targetNarrative" /]</h6></label>
          <textarea name="project_indicator_description" ></textarea>
        </div>
      </div>
    </div>
  </div>
  
 [#-- Major Output Group template --]
  <div class="mogs">
    <h6>[@s.text name="planning.projectImpactPathways.mogs" /]</h6>
    <div class="mogsBlock">
      <img class="ajax-loader" style="" src="${baseUrl}/images/global/loading.gif" alt="Loader ..." />
      <div class="mog" id="mogTemplate">
        <input name="contributesTo[0].id" type="hidden" />
        <input type="checkbox" name="project.outputs" />
        <label></label>
      </div>
    </div>
  </div>

</div>

[#include "/WEB-INF/global/pages/footer.ftl"]