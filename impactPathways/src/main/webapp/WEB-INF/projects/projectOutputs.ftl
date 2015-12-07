[#ftl]
[#assign title = "Overview by MOGs" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/projects/projectOutputs.js"] /]
[#assign currentSection = cycleName?lower_case /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "outputs" /]
[#assign currentSubStage = "overviewByMogs" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projects", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projectOutputs", "nameSpace":"${currentSection}/projects", "action":"outputs", "param":"projectID=${project.id}"},
  {"label":"projectMogs", "nameSpace":"${currentSection}/projects", "action":"outputs", "param":"projectID=${project.id}"}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  [#include "/WEB-INF/projects/projectsSubMenu.ftl" /]
  
  [@s.form action="outputs" cssClass="pure-form"]
  <article class="halfContent" id="mainInformation"> 
    [#assign years= [midOutcomeYear, currentPlanningYear-1,currentPlanningYear, currentPlanningYear+1] /]
    [#include "/WEB-INF/projects/dataSheet.ftl" /]
    <br />
    [#-- Informing user that he/she doesnt have enough privileges to edit. See GrantProjectPlanningAccessInterceptor--]
    [#if submission?has_content]
      <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
    [#elseif !canEdit ]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]
      </p>
    [/#if]
    <div id="projectOutputs" class="borderBox">
      <h1 class="contentTitle">[@s.text name="planning.projectOutputs.title" /]</h1> 
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
          <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if] 
      [#if project.outputs?has_content]
        <div id="mogsTabs">
          [#-- Years Menu --]
          <ul>
            [#list years as year]
              <li class=""><a href="#mogs-${year}">${year} [#if isYearRequired(year)]*[/#if]</a></li> 
            [/#list]
          </ul>
          [#list years as year]
            [#-- Major Output Group list by year --]
            <div id="mogs-${year}">
              [#list project.outputs as output]
                [#assign index = (year_index * project.outputs?size)+output_index /] 
                [#assign outputOverview = project.getOutputOverview(output.id, year)! /]
                <div class="mog simpleBox clearfix">
                  [#-- Hidden values --]
                  <input type="hidden" name="project.outputsOverview[${index}].id" value="${outputOverview.id!"-1"}" />
                  <input type="hidden" name="project.outputsOverview[${index}].year" value="${year}" />
                  <input type="hidden" name="project.outputsOverview[${index}].output.id" value="${output.id}" />
                  [#-- MOG Title --]
                  <div class="fullPartBlock"><p class="checked">${output.program.acronym} - MOG #${action.getMOGIndex(output)}: ${output.description} </p></div>
                  [#-- Brief bullet points of your expected annual year contribution towards the selected MOG --]
                  <div class="fullBlock">
                    <h6>[@customForm.text name="planning.projectOutputs.expectedBulletPoints" readText=!(editable && !reportingCycle) param="${year}" /]:[@customForm.req required=isYearRequired(year) /]</h6>  
                    [@customForm.textArea name="project.outputsOverview[${index}].expectedAnnualContribution" value=outputOverview.expectedAnnualContribution!"" i18nkey="planning.projectOutputs.expectedBulletPoints" required=isYearRequired(year) showTitle=false editable=(editable && !reportingCycle) /]
                  </div>
                  [#-- Brief summary of your actual annual contribution --]
                  [#if reportingCycle]
                  <div class="fullBlock">
                    <h6>[@customForm.text name="reporting.projectOutputs.summaryAnnualContribution" readText=!editable param="${year}" /]:[@customForm.req required=isYearRequired(year) /]</h6>  
                    [@customForm.textArea name="project.outputsOverview[${index}].summaryAnnualContribution" value=""  required=isYearRequired(year) showTitle=false editable=editable /]
                  </div>
                  [/#if]
                  [#-- Brief plan of the gender and social inclusion dimension of the expected annual output --]
                  <div class="fullBlock">
                    <h6>[@customForm.text name="planning.projectOutputs.expectedSocialAndGenderPlan" readText=!(editable && !reportingCycle) param="${year}" /]:[@customForm.req required=isYearRequired(year) /]</h6>
                    [@customForm.textArea name="project.outputsOverview[${index}].socialInclusionDimmension" value=outputOverview.socialInclusionDimmension!"" showTitle=false required=isYearRequired(year) editable=(editable && !reportingCycle) /]
                  </div>
                  
                  [#-- Summary of the gender and social inclusion dimension --]
                  [#if reportingCycle]
                  <div class="fullBlock">
                    <h6>[@customForm.text name="reporting.projectOutputs.summarySocialInclusionDimmension" readText=!editable param="${year}" /]:[@customForm.req required=isYearRequired(year) /]</h6> 
                    [@customForm.textArea name="project.outputsOverview[${index}].summarySocialInclusionDimmension" value="" required=isYearRequired(year) showTitle=false editable=editable /]
                  </div>
                  [/#if]
                </div>
              [/#list] [#-- End Outcomes 2019 list --]
            </div>
          [/#list] [#-- End years list --] 
        </div>
      [#else]
        <p class="simpleBox center">[@s.text name="planning.projectOutputs.empty" /]</p>
      [/#if] 
    </div>
    
    [#if !newProject]
    <div id="lessons" class="borderBox">
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#lessons">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
          <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]#lessons">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if] 
      <div class="fullBlock">
        <input type="hidden" name="projectLessons.id" value=${(projectLessons.id)!"-1"} />
        <input type="hidden" name="projectLessons.year" value=${currentPlanningYear} />
        <input type="hidden" name="projectLessons.componentName" value="${actionName}">
        [@customForm.textArea name="projectLessons.lessons" i18nkey="${currentSection}.projectOutputs.lessons" required=!project.bilateralProject editable=editable /]
      </div>
    </div>
    [/#if]
    
    [#if editable]  
      [#-- Project identifier --]
      <input name="projectID" type="hidden" value="${project.id?c}" />
      <div class="[#if !newProject]borderBox[/#if]" >
        [#if !newProject] [@customForm.textArea name="justification" i18nkey="saving.justification" required=true className="justification"/][/#if]
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

  [#-- Index active tab --]
  [#assign indexTabCurrentYear][#list years as year][#if year == currentPlanningYear]${year_index}[/#if][/#list][/#assign]
  <input type="hidden" id="indexTabCurrentYear" value="${(indexTabCurrentYear)!0}" />
  
</section>

[#-- Get if the year is required--]
[#function isYearRequired year]
  [#return (!project.bilateralProject && (currentPlanningYear == year))]
[/#function]

[#include "/WEB-INF/global/pages/footer.ftl"]