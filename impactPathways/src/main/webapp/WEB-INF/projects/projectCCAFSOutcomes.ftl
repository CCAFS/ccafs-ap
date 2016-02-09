[#ftl]
[#assign title = "CCAFS Outcomes" /]
[#assign globalLibs = ["jquery", "noty", "chosen", "cytoscape", "qtip","cytoscapePanzoom"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/ipGraph.js", "${baseUrl}/js/projects/projectImpactPathway.js"] /]
[#assign currentSection = cycleName?lower_case /]
[#assign currentCycleSection = "projects" /]
[#assign currentStage = "outcomes" /] 
[#assign currentSubStage = "ccafsOutcomes" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"${currentSection}", "action":"projectsList"}, 
  {"label":"projectCCAFSOutcomes", "nameSpace":"${currentSection}/projects", "action":"ccafsOutcomes", "param":"projectID=${project.id}"}
]/]

[#-- Cycle year --]
[#assign cycleYear = (reportingCycle?string(currentReportingYear,currentPlanningYear))?number /]

[#-- List of years --]
[#assign years= [cycleYear-1, cycleYear, cycleYear+1, midOutcomeYear] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.projectImpactPathways.help2" /] </p>
  </div>
  [#include "/WEB-INF/projects/projectsSubMenu.ftl" /]
  [@s.form action="ccafsOutcomes" cssClass="pure-form"]  
  <article class="halfContent" id="activityImpactPathway">
    [#include "/WEB-INF/projects/dataSheet.ftl" /]
    [#include "/WEB-INF/projects/projectIP-sub-menu.ftl" /]
    [#assign fieldEmpty]<div class="select"><p>[@s.text name="form.values.fieldEmpty" /]</p></div>[/#assign]
    
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantActivityPlanningAccessInterceptor--]
    [#if submission?has_content]
      <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
    [#elseif !canEdit ]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]
      </p>
    [/#if] 
      
    [#if project.startDate?? && project.endDate??]
    <div class="borderBox">
      [#-- Button for edit this section --]
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
          <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if]
      <h1 class="contentTitle">[@s.text name="planning.projectOutcome.contribution" /] </h1> 
      [#if contributingPrograms?has_content]
        <p><b>[@s.text name="planning.projectImpactPathways.contributingTo" /]</b> ${contributingPrograms}</p>
        [#-- Contributions Block --]
        <div id="contributionsBlock" class="">  
          [#if midOutcomesSelected?has_content]
            [#list midOutcomesSelected as midOutcome]
              <div class="contribution">
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
                          [#if editable] 
                            <input type="checkbox" class="projectIndicatorCheckbox" id="indicatorIndex-${indicator_index}" [#if projectIndicator.id != -1 || isUniqueIndicator]checked="checked" disabled="disabled"[/#if] />
                          [/#if]
                          [#-- Indicator description --]
                          <label class="indicatorDescription [#if !editable]checked[/#if]">${(indicator.parent.description)!indicator.description}</label>
                          <div class="indicatorTargets">
                            <ul class="">
                              [#list years as year]
                                <li class="target-${year}"><a href="#target-${year}">${year} [#if isYearRequired(year)]*[/#if]</a></li> 
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
                                  <input type="hidden" class="projectIndicatorParent" name="project.indicators.parent" value="${indicator.parent.id}"  />
                                [#else]
                                  <input type="hidden" class="projectIndicatorParent" name="project.indicators.parent" value="${indicator.id}"  />
                                [/#if]
                                
                                [#-- Hidden values --]
                                <input type="hidden" class="projectIndicatorID" name="project.indicators.id" value="${projectIndicator.id}" [#if projectIndicator.id == -1 ]disabled="disabled"[/#if]/>
                                <input type="hidden" class="projectIndicatorYear" name="project.indicators.year"  value="${year}" /> 
                                <input type="hidden" class="projectIndicatorOutcome" name="project.indicators.outcome"  value="${midOutcome.id}" /> 
                                
                                <div class="fullBlock">
                                  [#--  1. Indicator target value --]
                                  <div class="thirdPartBlock">
                                    [#assign isTargetValueRequired = isYearRequired(year) && (action.hasProjectPermission("target", project.id) || !projectIndicator.target?has_content) /]
                                    [#assign isTargetValueEditable = editable && (cycleYear lte year) && action.hasProjectPermission("target", project.id) /]
                                    <label><h6>[@s.text name="planning.projectImpactPathways.targetValue" /]:[@customForm.req required=isTargetValueRequired /]</h6></label>
                                    [#if isTargetValueEditable]
                                      <input type="text" class="projectIndicatorTarget ${(isYearRequired(year))?string('required','optional')}" name="project.indicators.target" value="${(projectIndicator.target)!}"/> 
                                    [#else]
                                      [#if !projectIndicator.target?has_content]
                                        [#if cycleYear lt year]
                                          ${fieldEmpty}
                                        [#else]
                                          [#if cycleYear == year]
                                            <input type="text" class="projectIndicatorTarget ${(isYearRequired(year))?string('required','optional')}" name="project.indicators.target" value="${(projectIndicator.target)!}"/>
                                          [#else]
                                            <div class="input"><p>[@s.text name="form.values.notDefined"/]</p></div>
                                          [/#if]
                                        [/#if]
                                      [#else]
                                        <div class="select"><p>${projectIndicator.target}</p></div>
                                        <input type="hidden" class="projectIndicatorTarget ${(isYearRequired(year))?string('required','optional')}" name="project.indicators.target" value="${(projectIndicator.target)!}"/>
                                      [/#if]
                                    [/#if]
                                  </div>
                                  [#-- 2. Cumulative target --]
                                  [#if (cycleYear lte year)]
                                  <div class="thirdPartBlock">
                                    <label><h6>[@s.text name="reporting.projectImpactPathways.comulativeTarget" /]:</h6></label>
                                    <div class="input"><p>${project.calculateAcumulativeTarget(year)}</p></div>
                                  </div>
                                  [/#if]
                                  [#-- 3. Reporting target --]
                                  [#-- -- -- REPORTING BLOCK -- -- --]
                                  [#if reportingCycle && (year == cycleYear)]
                                    <div class="thirdPartBlock">
                                      <label><h6 title='[@s.text name="reporting.projectImpactPathways.achievedTarget.help" /]'>[@s.text name="reporting.projectImpactPathways.achievedTarget" /]:[@customForm.req required=isYearRequired(year) && action.hasProjectPermission("achieved", project.id) /]</h6></label>
                                      [#if editable && (cycleYear lte year) && action.hasProjectPermission("achieved", project.id)]
                                        <input type="text" class="projectIndicatorAchievedTarget ${(isYearRequired(year))?string('required','optional')}" name="project.indicators.archived" value="${(projectIndicator.archived)!}"/> 
                                      [#else]
                                        <div class="input"><p>${(projectIndicator.archived)!}</p></div>
                                      [/#if]
                                    </div>
                                  [/#if]
                                </div>
                                
                                [#-- Indicator target narrative description --]
                                <div class="textArea fullBlock">
                                  <label><h6>[@s.text name="planning.projectImpactPathways.targetNarrative" /]:[@customForm.req required=isYearRequired(year) && action.hasProjectPermission("description", project.id) /]</h6></label>
                                  [#if editable && (cycleYear lte year) && action.hasProjectPermission("description", project.id)]
                                    <textarea class="projectIndicatorDescription ${(isYearRequired(year))?string('required','optional')}" name="project.indicators.description">${projectIndicator.description!}</textarea>
                                  [#else]
                                    [#if !projectIndicator.description?has_content]
                                      [#if cycleYear lt year]${fieldEmpty}[#else]<div class="select"><p>[@s.text name="form.values.notDefined"/]</p></div>[/#if]
                                    [#else]
                                      <div class="select"><p>${(projectIndicator.description)!}</p></div>
                                      <input type="hidden" class="projectIndicatorDescription ${(isYearRequired(year))?string('required','optional')}" name="project.indicators.description" value="${(projectIndicator.description)!}"/>
                                    [/#if] 
                                  [/#if] 
                                </div>
                                
                                [#-- -- -- REPORTING BLOCK -- -- --]
                                [#if reportingCycle && (year == cycleYear)]
                                  [#-- Narrative for your achieved targets, including evidence --]
                                  <div class="textArea fullBlock">
                                    <label><h6>[@s.text name="reporting.projectImpactPathways.targetNarrativeAchieved" /]:[@customForm.req required=isYearRequired(year) && action.hasProjectPermission("narrativeTargets", project.id) /]</h6></label>
                                    [#if editable && (cycleYear lte year) && action.hasProjectPermission("narrativeTargets", project.id)]
                                      <textarea class="projectIndicatorNarrativeAchieved ${(isYearRequired(year))?string('required','optional')}" name="project.indicators.narrativeTargets">${(projectIndicator.narrativeTargets)!}</textarea>
                                    [#else]
                                      [#if !projectIndicator.narrativeTargets?has_content]
                                        [#if cycleYear lt year]${fieldEmpty}[#else]<div class="select"><p>[@s.text name="form.values.notDefined"/]</p></div>[/#if]
                                      [#else]
                                        <div class="select"><p>${(projectIndicator.narrativeTargets)!}</p></div>
                                        <input type="hidden" class="projectIndicatorNarrativeAchieved ${(isYearRequired(year))?string('required','optional')}" name="project.indicators.narrativeTargets" value="${(projectIndicator.narrativeTargets)!}"/>
                                      [/#if] 
                                    [/#if]
                                  </div>
                                [/#if]
                                
                                [#-- The expected annual gender and social inclusion contribution to this CCAFS outcome --]
                                <div class="textArea fullBlock">
                                  <label><h6>[@s.text name="planning.projectImpactPathways.targetGender" /]:[@customForm.req required=isYearRequired(year) && action.hasProjectPermission("gender", project.id) /]</h6></label>
                                  [#if editable && (cycleYear lte year) && action.hasProjectPermission("gender", project.id)]
                                    <textarea class="projectIndicatorGender ${(isYearRequired(year))?string('required','optional')}" name="project.indicators.gender">${(projectIndicator.gender)!}</textarea>
                                  [#else]
                                    [#if !projectIndicator.gender?has_content]
                                      [#if cycleYear lt year]${fieldEmpty}[#else]<div class="select"><p>[@s.text name="form.values.prefilledIfAvailable"/]</p></div>[/#if]
                                    [#else]
                                      <div class="select"><p>${(projectIndicator.gender)!}</p></div>
                                      <input type="hidden" class="projectIndicatorGender ${(isYearRequired(year))?string('required','optional')}" name="project.indicators.gender" value="${(projectIndicator.gender)!}"/>
                                    [/#if] 
                                  [/#if] 
                                </div>
                                
                                [#-- -- -- REPORTING BLOCK -- -- --]
                                [#if reportingCycle && (year == cycleYear)]
                                  [#--  Narrative for your achieved annual gender and social inclusion contribution to this CCAFS outcome --]
                                  <div class="textArea fullBlock">
                                    <label><h6>[@s.text name="reporting.projectImpactPathways.targetNarrativeGenderAchieved" /]:[@customForm.req required=isYearRequired(year) && action.hasProjectPermission("narrativeGender", project.id) /]</h6></label>
                                    [#if editable && (cycleYear lte year) && action.hasProjectPermission("narrativeGender", project.id)]
                                      <textarea class="projectIndicatorNarrativeGenderAchieved ${(isYearRequired(year))?string('required','optional')}" name="project.indicators.narrativeGender" >${(projectIndicator.narrativeGender)!}</textarea>
                                    [#else]
                                      [#if !projectIndicator.narrativeGender?has_content]
                                        [#if cycleYear lt year]${fieldEmpty}[#else]<div class="select"><p>[@s.text name="form.values.prefilledIfAvailable"/]</p></div>[/#if]
                                      [#else]
                                        <div class="select"><p>${(projectIndicator.narrativeGender)!}</p></div>
                                        <input type="hidden" class="projectIndicatorNarrativeGenderAchieved ${(isYearRequired(year))?string('required','optional')}" name="project.indicators.narrativeGender" value="${(projectIndicator.narrativeGender)!}"/>
                                      [/#if] 
                                    [/#if]
                                  </div>
                                [/#if]
                                
                              </div>  
                            [/#list] 
                          </div>   
                        </div>  
                      [#else]
                        <div class="midOutcomeIndicator" >
                          <input type="hidden"  name="indicators.id" value="-1" disabled="disabled"/>
                          [#if editable && !reportingCycle]
                            <input type="checkbox" class="projectIndicatorCheckbox" id="indicatorIndex-${indicator_index}" />
                            <label class="indicatorDescription">${(indicator.parent.description)!indicator.description}</label> 
                          [/#if]
                          <div class="indicatorTargets" style="display:none">
                            <ul class="">
                              [#list years as year]
                                <li class="target-${year}"><a href="#target-${year}">${year} [#if isYearRequired(year)]*[/#if]</a></li> 
                              [/#list]   
                            </ul>
                            [#list years as year]
                            <div id="target-${year}" class="targetIndicator"> 
                              [#-- Indicator ID --]
                              <input type="hidden" class="projectIndicatorParent" name="project.indicators.parent" value="${(indicator.parent.id)!indicator.id}"  />
                              
                              [#-- Check if the value of this hidden input should be arbitrarily -1 --]
                              <input type="hidden" class="projectIndicatorID" name="project.indicators.id" value="-1" />
                              <input type="hidden" class="projectIndicatorYear" name="project.indicators.year"  value="${year}" />
                              <input type="hidden" class="projectIndicatorOutcome" name="project.indicators.outcome"  value="${midOutcome.id}" /> 
                              
                              <div class="fullBlock">
                                [#-- Target value --]
                                <div class="thirdPartBlock">
                                  <label><h6>[@s.text name="planning.projectImpactPathways.targetValue" /]</h6></label>
                                  [#if editable]
                                    <input type="text" class="projectIndicatorTarget ${(isYearRequired(year))?string('required','optional')}" name="project.indicators.target" />
                                  [/#if]
                                </div>
                              </div>
                              
                              [#-- Target description --]
                              <div class="fullBlock">
                                <label><h6>[@s.text name="planning.projectImpactPathways.targetNarrative" /]</h6></label>
                                [#if editable]
                                  <textarea class="projectIndicatorDescription ${(isYearRequired(year))?string('required','optional')}" name="project.indicators.description" ></textarea>
                                [/#if]
                              </div>
                              
                              [#-- Target Gender --]
                              <div class="fullBlock">
                                <label><h6>[@s.text name="planning.projectImpactPathways.targetGender" /]:</h6></label>
                                [#if editable]
                                  <textarea class="projectIndicatorGender ${(isYearRequired(year))?string('required','optional')}" name="project.indicators.gender" ></textarea>
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
                              <input type="checkbox" name="outputs.id" value="${output.id}" [#if (project.containsOutput(output.id, midOutcome.id))!false]class="disabled" checked onclick="return false"[/#if] />
                              <label class=""> ${output.program.acronym} - MOG #${action.getMOGIndex(output)}: ${output.description} </label>
                            [#else]
                               [#if (project.containsOutput(output.id, midOutcome.id))!false] 
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
          [#else]
            <p class="emptyText">[@s.text name="planning.projectImpactPathways.outcomesEmpty" /]</p>
          [/#if]
        </div> <!-- End Contributions Block -->
        [#-- Outcomes 2019 select list --]
        [#if editable && !reportingCycle]
          <div id="midOutcomesSelect">
            [@customForm.select name="midOutcomesList" i18nkey="planning.projectImpactPathways.outcome" listName="midOutcomes" className="midOutcomeSelect" /]
          </div>
        [/#if] 
      [#else]
        <p class="emptyText">[@s.text name="planning.projectImpactPathways.contributionsEmpty"][@s.param]<a href="[@s.url action='description'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]#projectWorking">[/@s.param][@s.param]</a>[/@s.param][/@s.text]</p> 
      [/#if] 
    </div>
    [#else]
      <p class="simpleBox center">[@s.text name="planning.projectOutcome.message.dateUndefined" /]</p>
    [/#if]
    
    [#if !newProject]
    <div id="lessons" class="borderBox">
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url][@s.param name="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#lessons">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
          <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]#lessons">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if]
      
      [#-- Lessons learnt from last planning/reporting cycle --]
      [#if (projectLessonsPreview.lessons?has_content)!false]
      <div class="fullBlock">
        <h6>[@customForm.text name="${currentSection}.projectCcafsOutcomes.previousLessons" param="${reportingCycle?string(currentReportingYear,currentPlanningYear-1)}" /]:</h6>
        <div class="textArea "><p>${projectLessonsPreview.lessons}</p></div>
      </div>
      [/#if]
      [#-- Planning/Reporting lessons --]
      <div class="fullBlock">
        <input type="hidden" name="projectLessons.id" value=${(projectLessons.id)!"-1"} />
        <input type="hidden" name="projectLessons.year" value=${reportingCycle?string(currentReportingYear,currentPlanningYear)} />
        <input type="hidden" name="projectLessons.componentName" value="${actionName}">
        [@customForm.textArea name="projectLessons.lessons" i18nkey="${currentSection}.projectCcafsOutcomes.lessons" required=!project.bilateralProject editable=editable /]
      </div>
      
    </div>
    [/#if]
    
    [#if editable] 
      [#-- Project identifier --]
      <input name="projectID" type="hidden" value="${project.id?c}" />
      <div class="[#if !newProject && !reportingCycle]borderBox[/#if]" >
        [#if !newProject && !reportingCycle][@customForm.textArea name="justification" i18nkey="saving.justification" required=true className="justification"/][/#if]
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
              <li class="target-${year}"><a href="#target-${year}">${year} [#if isYearRequired(year)]*[/#if]</a></li> 
            [/#list]
          </ul>
          [#list years as year]
          <div id="target-${year}" class="targetIndicator" >
            <input type="hidden" class="projectIndicatorParent" name="project.indicators.parent"   />
            <input type="hidden" class="projectIndicatorID" name="project.indicators.id" value="-1"/>
            <input type="hidden" class="projectIndicatorYear" name="project_indicator_year"  value="${year}" />
            <input type="hidden" class="projectIndicatorOutcome" name="project.indicators.outcome" /> 
            <div class="checkboxGroup vertical indicatorNarrative">
              [#-- Target value --]
              <div class="fullPartBlock">
                <label> <h6>[@s.text name="planning.projectImpactPathways.targetValue" /] [@customForm.req required=isYearRequired(year) /]
                </h6></label>
                <input type="text"  class="projectIndicatorTarget ${(isYearRequired(year))?string('required','optional')}" name="project_indicator_target" />
              </div>
              [#-- Target description --]
              <div class="textArea fullPartBlock">
                <label> <h6>[@s.text name="planning.projectImpactPathways.targetNarrative" /][@customForm.req required=isYearRequired(year) /]
                </h6></label>
                <textarea rows="4" class="projectIndicatorDescription ${(isYearRequired(year))?string('required','optional')}" name="project_indicator_description" ></textarea>
              </div>
              [#-- Target gender --]
              <div class="textArea fullPartBlock">
                <label><h6>[@s.text name="planning.projectImpactPathways.targetGender" /]: [@customForm.req required=isYearRequired(year) /]
                </h6></label>
                <textarea rows="4" class="projectIndicatorGender ${(isYearRequired(year))?string('required','optional')}" name="project_indicator_gender" ></textarea>
              </div>
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

[#-- Index active tab --]
[#assign indexTabCurrentYear][#list years as year][#if year == cycleYear]${year_index}[/#if][/#list][/#assign]
<input type="hidden" id="indexTabCurrentYear" value="${(indexTabCurrentYear)!0}" />

[#-- Get if the year is required--]
[#function isYearRequired year]
  [#if project.endDate??]
    [#assign endDate = (project.endDate?string.yyyy)?number]
    [#if reportingCycle]
      [#return (!project.bilateralProject && ((year == midOutcomeYear) ||(year == currentReportingYear) )) && (endDate gte year)]
    [#else]
      [#return (!project.bilateralProject && ((year == midOutcomeYear) ||(year == currentPlanningYear) || (year == currentPlanningYear+1))) && (endDate gte year)]
    [/#if]
  [#else]
    [#return false]
  [/#if]
[/#function]

[#include "/WEB-INF/global/pages/footer.ftl"]
