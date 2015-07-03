[#ftl]
[#assign title = "Project Outcomes" /]
[#assign globalLibs = ["jquery", "noty", "chosen", "cytoscape", "qtip","cytoscapePanzoom"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/ipGraph.js", "${baseUrl}/js/planning/projectImpactPathwayPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "outcomes" /] 
[#assign currentSubStage = "ccafsOutcomes" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projectOutcomes", "nameSpace":"planning/projects", "action":"outcomes", "param":"projectID=${project.id}"},
  {"label":"projectCCAFSOutcomes", "nameSpace":"planning/projects", "action":"ccafsOutcomes", "param":"projectID=${project.id}"}
]/]

[#assign years= [midOutcomeYear, currentPlanningYear, currentPlanningYear+1] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.projectImpactPathways.help" /] </p>
  </div>

  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]

  [@s.form action="ccafsOutcomes" cssClass="pure-form"]  
  <article class="halfContent" id="activityImpactPathway">
    [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
    [#include "/WEB-INF/planning/projectIP-planning-sub-menu.ftl" /]
    
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantActivityPlanningAccessInterceptor--]
    [#if !canEdit]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"]
          [@s.param][@s.text name="planning.projectImpactPathways.title"/][/@s.param]
        [/@s.text]
      </p>
    [/#if] 
      
    <div class="borderBox">
      [#-- Button for edit this section --]
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [/#if]

      <h1 class="contentTitle">[@s.text name="planning.projectOutcome.contribution" /] </h1> 
      <p>
        <b>[@s.text name="planning.projectImpactPathways.contributingTo" /]</b> 
        ${contributingPrograms}
      </p>
      
      [#-- Contributions Block --]
      <div id="contributionsBlock" class="">  
        [#if midOutcomesSelected?has_content]
          [#list midOutcomesSelected as midOutcome]
            <div class="contribution ">
              [#if editable]
                [#-- Remove Contribution --]
                <div id="removeContribution" class="removeContribution removeElement removeLink" title="[@s.text name="preplanning.midOutcomes.removeMidOutcome" /]"></div>
              [/#if] 
              [#-- Midoutcome title --]
              <div class="midOutcomeTitle">
                <input id="midOutcomeID" value="${midOutcome.id}" type="hidden" />
                <input id="programID" value="${midOutcome.program.id}" type="hidden" /> 
                <h6 class="title">${midOutcome.program.acronym} - [@s.text name="planning.projectImpactPathways.outcome2019" /]</h6>
                
                <p class="description"> ${midOutcome.description} </p>
              </div> 
              [#-- Indicators list --]
              <div class="indicators">
                <h6>[@s.text name="planning.projectImpactPathways.indicators" /]</h6>
                [#if midOutcome.indicators?has_content]
                <div class="indicatorsBlock">
                  [#list midOutcome.indicators as indicator]
  
                    [#if indicator.parent?has_content ]
                      [#assign projectIndicator = project.getIndicator(indicator.parent.id, midOutcome.id,  midOutcomeYear) /]
                    [#else]
                      [#assign projectIndicator = project.getIndicator(indicator.id, midOutcome.id,  midOutcomeYear) /]
                    [/#if]
                    [#assign isUniqueIndicator = (indicator_index == 0 && !indicator_has_next)]
                    [#if projectIndicator.id != -1 || isUniqueIndicator]
  
                      <div class="midOutcomeIndicator" >
                        <input type="hidden" class="projectIndicatorID" name="project.indicators.id" value="${projectIndicator.id}" [#if projectIndicator.id == -1 ]disabled="disabled"[/#if]/>
                        [#if editable] 
                          <input type="checkbox" class="projectIndicatorCheckbox" id="indicatorIndex-${indicator_index}" [#if projectIndicator.id != -1 || isUniqueIndicator]checked="checked"[/#if] [#if isUniqueIndicator]disabled="disabled"[/#if]  />
                        [/#if]
                        [#if indicator.parent?has_content] 
                          <label class="indicatorDescription [#if !editable]checked[/#if]">${indicator.parent.description}</label>
                        [#else]
                          <label class="indicatorDescription [#if !editable]checked[/#if]">${indicator.description}</label>
                        [/#if]
                        <div class="indicatorTargets">
                           <ul class="">
                            [#list years as year]
                              <li class=""><a href="#target-${year}">${year}</a></li> 
                            [/#list]   
                          </ul>
                          [#list years as year]
                            [#if indicator.parent?has_content]
                              [#assign projectIndicator = project.getIndicator(indicator.parent.id, midOutcome.id, year) /]
                            [#else]
                              [#assign projectIndicator = project.getIndicator(indicator.id, midOutcome.id,  year) /]
                            [/#if]
                            <div id="target-${year}" class="targetIndicator"> 
                              [#-- Indicator ID --]
                              [#if indicator.parent?has_content]
                                <input type="hidden" class="projectIndicatorParent" name="project.indicators.parent.id" value="${indicator.parent.id}"  />
                              [#else]
                                <input type="hidden" class="projectIndicatorParent" name="project.indicators.parent.id" value="${indicator.id}"  />
                              [/#if]
                              
                              [#-- Hidden values --]
                              <input type="hidden" class="projectIndicatorYear" name="project.indicators.year"  value="${year}" /> 
                              <input type="hidden" class="projectIndicatorOutcome" name="project.indicators.outcome"  value="${midOutcome.id}" /> 
                              
                              [#-- Indicator target value --]
                              <div class="checkboxGroup vertical indicatorNarrative" >
                                <label> <h6>[@s.text name="planning.projectImpactPathways.targetValue" /]</h6></label>
                                [#if editable]
                                  <textarea class="projectIndicatorTarget" name="project.indicators.target" >${projectIndicator.target!}</textarea>
                                [#else]
                                  <p>${projectIndicator.target!}</p>
                                [/#if]
                              </div> 
                              
                              [#-- Indicator target description --]
                              <div class="checkboxGroup vertical indicatorNarrative" >
                                <label> <h6>[@s.text name="planning.projectImpactPathways.targetNarrative" /]</h6></label>
                                [#if editable]
                                  <textarea class="projectIndicatorDescription" name="project.indicators.description" >${projectIndicator.description!}</textarea>
                                [#else]
                                  ${projectIndicator.description!}
                                [/#if] 
                              </div>
                            </div>  
                          [/#list] 
                        </div>   
                      </div>  
                    [#else] 
                      <div class="midOutcomeIndicator" >
                        <input type="hidden"  name="indicators.id" value="-1" disabled="disabled"/>
                        [#if editable]
                          <input type="checkbox" class="projectIndicatorCheckbox" id="indicatorIndex-${indicator_index}" />
                          [#if indicator.parent?has_content]
                            <label class="indicatorDescription">${indicator.parent.description}</label> 
                          [#else]
                            <label class="indicatorDescription">${indicator.description}</label> 
                          [/#if]
                        [/#if]
                        <div class="indicatorTargets" style="display:none">
                           <ul class="">
                            [#list years as year]
                              <li class=""><a href="#target-${year}">${year}</a></li> 
                            [/#list]   
                          </ul>
                          [#list years as year]
                          <div id="target-${year}" class="targetIndicator"> 
                            [#-- Indicator ID --]
                            [#if indicator.parent?has_content]
                              <input type="hidden" class="projectIndicatorParent" name="project.indicators.parent.id" value="${indicator.parent.id}"  />
                            [#else]
                              <input type="hidden" class="projectIndicatorParent" name="project.indicators.parent.id" value="${indicator.id}"  />
                            [/#if]
                            <input type="hidden" class="projectIndicatorYear" name="project.indicators.year"  value="${year}" />
                            <input type="hidden" class="projectIndicatorOutcome" name="project.indicators.outcome"  value="${midOutcome.id}" /> 
                            
                            [#-- Target value --]
                            <div class="checkboxGroup vertical indicatorNarrative">
                              <label>  <h6>[@s.text name="planning.projectImpactPathways.targetValue" /]</h6></label>
                              [#if editable]
                                <textarea class="projectIndicatorTarget" name="project.indicators.target" ></textarea>                              
                              [/#if]
                            </div>
                            
                            [#-- Target description --]
                            <div class="checkboxGroup vertical indicatorNarrative">
                              <label>  <h6>[@s.text name="planning.projectImpactPathways.targetNarrative" /]</h6></label>
                              [#if editable]
                                <textarea class="projectIndicatorDescription" name="project.indicators.description" ></textarea>
                              [/#if]
                            </div>
                          </div>   
                          [/#list]
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
                          [#if editable]
                            <input name="project.outputs.contributesTo[0].id" value="${midOutcome.id}"  type="hidden" />
                            <input type="checkbox" name="outputs.id" value="${output.id}" [#if project.containsOutput(output.id, midOutcome.id)] checked [/#if] />
                            <label class=""> ${output.program.acronym} - MOG #${action.getMOGIndex(output)}: ${output.description} </label>
                          [#else]
                             [#if project.containsOutput(output.id, midOutcome.id)] 
                             <label class="checked"> ${output.program.acronym} - MOG #${action.getMOGIndex(output)}: ${output.description} </label>
                             [/#if] 
                          [/#if]
                        </div>
                    [/#list]
                  </div>
                [/#if]
              </div>
            </div> 
          [/#list]
        [/#if]
      </div> <!-- End Contributions Block -->
      [#-- Outcomes 2019 select list --]
      [#if editable]
        <div id="midOutcomesSelect">
          [@customForm.select name="midOutcomesList" i18nkey="planning.projectImpactPathways.outcome" listName="midOutcomes" className="midOutcomeSelect" /]
        </div>
      [/#if]  
    </div>
    
    [#if editable] 
      [#-- Project identifier --]
      <div class="borderBox">
        <input name="projectID" type="hidden" value="${project.id?c}" />
        [@customForm.textArea name="justification" i18nkey="saving.justification" required=true className="justification"/]
        <div class="buttons">
          [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
          [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
          [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
        </div>
      </div>
    [#else]
        [#-- Display Log History --]
        [#if history??][@log.logList list=history /][/#if]   
    [/#if]
    
  </article>
  [/@s.form]  
</section>

[#-- Contribution template --]
<div class="contribution" id="contributionTemplate" style="display:none">
  [#-- Remove Contribution --]
  <div id="removeContribution" class="removeContribution removeElement removeLink" title="[@s.text name="preplanning.midOutcomes.removeMidOutcome" /]"></div>

  [#-- Midoutcome title --]
  <div class="midOutcomeTitle">
    <input id="midOutcomeID" value="" type="hidden" />
    <input id="programID" value="" type="hidden" /> 
    <h6 class="title">[@s.text name="planning.projectImpactPathways.outcome2019" /]</h6>
    <p class="description"></p>
  </div>
  
  [#-- Indicators template --]
  <div class="indicators">
    <h6>[@s.text name="planning.projectImpactPathways.indicators" /]</h6>
    <div class="indicatorsBlock">
      <img class="ajax-loader" style="" src="${baseUrl}/images/global/loading.gif" alt="Loader ..." />
      <div class="midOutcomeIndicator" id="midOutcomeIndicatorTemplate">
        <input type="hidden" class="projectIndicatorID" name="activity_indicator_id" value="-1" disabled="disabled"/>
        <input type="checkbox" class="projectIndicatorCheckbox" />
        <label class="indicatorDescription"></label>
        <div class="indicatorTargetsTemplate" style="display:none">
          <ul class="">
            [#list years as year]
              <li class=""><a href="#target-${year}">${year}</a></li> 
            [/#list]   
          </ul>
          [#list years as year]
          <div id="target-${year}" class="targetIndicator" >
            <input type="hidden" class="projectIndicatorParent" name="project.indicators.parent.id"   />
            <input type="hidden" class="projectIndicatorYear" name="project_indicator_year"  value="${year}" />
            <input type="hidden" class="projectIndicatorOutcome" name="project.indicators.outcome" /> 
            <div class="checkboxGroup vertical indicatorNarrative">
              [#-- Target value --]
              <label> <h6>[@s.text name="planning.projectImpactPathways.targetValue" /]</h6></label>
              <textarea  class="projectIndicatorTarget" name="project_indicator_target" ></textarea>
              [#-- Target description --]
              <label> <h6>[@s.text name="planning.projectImpactPathways.targetNarrative" /]</h6></label>
              <textarea  class="projectIndicatorDescription" name="project_indicator_description" ></textarea>
            </div>
          </div>
          [/#list]
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