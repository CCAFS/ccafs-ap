[#ftl]
[#assign title = "Project Outputs" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/projectOutputsPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "outputs" /]
[#assign currentSubStage = "overviewByMogs" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projectOutputs", "nameSpace":"planning/projects", "action":"outputs", "param":"projectID=${project.id}"},
  {"label":"projectMogs", "nameSpace":"planning/projects", "action":"outputs", "param":"projectID=${project.id}"}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
  
  [@s.form action="outputs" cssClass="pure-form"]
  <article class="halfContent" id="mainInformation"> 
    [#assign years= [midOutcomeYear, currentPlanningYear, currentPlanningYear+1] /]
    [#include "/WEB-INF/planning/projectOutputs-sub-menu.ftl" /]
    [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantProjectPlanningAccessInterceptor--]
    [#if !canEdit]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]
      </p>
    [/#if]
    <div id="projectOutputs" class="borderBox">
      <h1 class="contentTitle">[@s.text name="planning.projectOutputs.title" /]</h1> 
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]#projectOutputs">[@s.text name="form.buttons.edit" /]</a></div>
      [/#if]
      [#if project.outputs?has_content]
        <div id="mogsTabs">
          [#-- Years Menu --]
          <ul>
            [#list years as year]
              <li class=""><a href="#mogs-${year}">${year}</a></li> 
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
                  <div class="fullPartBlock">
                    <p class="checked">${output.program.acronym} - MOG #${action.getMOGIndex(output)}: ${output.description} </p>
                  </div>
                  <div class="fullBlock">
                    <h6>[@customForm.text name="planning.projectOutputs.expectedBulletPoints" readText=!editable param="${year}" /]:<span class="red">*</span></h6>  
                    [@customForm.textArea name="project.outputsOverview[${index}].expectedAnnualContribution" value=outputOverview.expectedAnnualContribution!"" i18nkey="planning.projectOutputs.expectedBulletPoints" required=true showTitle=false editable=editable /]
                  </div>
                  <div class="fullBlock">
                    [@customForm.textArea name="project.outputsOverview[${index}].socialInclusionDimmension" value=outputOverview.socialInclusionDimmension!"" i18nkey="planning.projectOutputs.expectedSocialAndGenderPlan" required=true editable=editable /]
                  </div> 
                </div>
              [/#list] [#-- End Outcomes 2019 list --]
            </div>
          [/#list] [#-- End years list --] 
        </div>
      [#else]
        <p class="simpleBox center">[@s.text name="planning.projectOutputs.empty" /]</p>
      [/#if] 
    </div>
    <div id="lessons" class="borderBox">
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]#lessons">[@s.text name="form.buttons.edit" /]</a></div>
      [/#if]
      <div class="fullBlock">
        [@customForm.textArea name="project.outputsOverview[${index}].lessons" i18nkey="planning.projectOutputs.lessons" required=true editable=editable /]
      </div>
    </div>
    
    [#if editable]  
      [#-- Project identifier --]
      <input name="projectID" type="hidden" value="${project.id?c}" />
      <div class="borderBox">
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
[#include "/WEB-INF/global/pages/footer.ftl"]