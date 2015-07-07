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
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.projectOutputs.help" /] </p>
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
  
  [@s.form action="outputs" cssClass="pure-form"]
  <article class="halfContent" id="mainInformation"> 
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
        <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [/#if]  
      
      [#assign years= [midOutcomeYear, currentPlanningYear, currentPlanningYear+1] /]
      [#if midOutcomesSelected?has_content]
        [#list years as year]  
          [#-- Major Output Group list --]
          <div class="mogsBlock"> 
            [#-- Title --]
            <div class="midOutcomeTitle"><h6 class="title">[@s.text name="planning.projectImpactPathways.mogs" /] - ${year}</h6></div>
            [#if midOutcomesSelected?has_content]
              [#list midOutcomesSelected as midOutcome]
                [#if action.getMidOutcomeOutputs(midOutcome.id)?has_content]
                  [#assign outputs = action.getMidOutcomeOutputs(midOutcome.id)] 
                  [#list outputs as output]
                    <div class="mog fullBlock clearfix">
                      [#if project.containsOutput(output.id, midOutcome.id)] 
                        <div class="fullPartBlock">
                          <p class="checked">${output.program.acronym} - MOG #${action.getMOGIndex(output)}: ${output.description} </p>
                        </div>
                        <div class="fullBlock">
                          <h6>[@customForm.text name="planning.projectOutputs.expectedBulletPoints" readText=!editable param="${year}" /]</h6>  
                          [@customForm.textArea name="" i18nkey="planning.projectOutputs.expectedBulletPoints" required=true showTitle=false editable=editable /]
                        </div>
                        <div class="fullBlock">
                          [@customForm.textArea name="" i18nkey="planning.projectOutputs.expectedSocialAndGenderPlan" required=true editable=editable /]
                        </div> 
                      [/#if] 
                    </div>
                  [/#list] [#-- End MOGs list --] 
                [/#if] 
              [/#list] [#-- End Outcomes 2019 list --] 
            [/#if]
          </div> 
        [/#list] [#-- End years list --] 
      [#else]
        [@s.text name="planning.projectOutputs.empty" /]
      [/#if]
       
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