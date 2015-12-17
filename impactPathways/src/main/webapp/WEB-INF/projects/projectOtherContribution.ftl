[#ftl]
[#assign title = "Impact Pathways - Other Contribution" /]
[#assign globalLibs = ["jquery", "noty", "cytoscape", "qtip","cytoscapePanzoom", "select2"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/ipGraph.js", "${baseUrl}/js/projects/projectIpOtherContributions.js"] /]
[#assign currentSection = cycleName?lower_case /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "outcomes" /]
[#assign currentSubStage = "otherContributions" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projects", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projectOutcomes", "nameSpace":"${currentSection}/projects", "action":"outcomes", "param":"projectID=${project.id}"},
  {"label":"projectOtherContributions", "nameSpace":"${currentSection}/projects", "action":"otherContributions", "param":"projectID=${project.id}"}
]/]

[#assign params = {
  "otherContributions": {"id":"otherContributionsName", "name":"project.otherContributions"}
  }
/] 

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  [#include "/WEB-INF/projects/projectsSubMenu.ftl" /]
  
  [@s.form action="otherContributions" cssClass="pure-form"]  
  <article class="halfContent" id=""> 
    [#include "/WEB-INF/projects/dataSheet.ftl" /]
    [#include "/WEB-INF/projects/projectIP-sub-menu.ftl" /]
    [#-- Informing user that he/she doesnt have enough privileges to edit. See GrantActivityPlanningAccessInterceptor--]
    [#if submission?has_content]
      <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
    [#elseif !canEdit ]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"]
          [@s.text name="planning.project" /]: ${project.composedId} - [@s.param][@s.text name="planning.impactPathways.otherContributions.title"/][/@s.param]
        [/@s.text]
      </p>
    [/#if]
    
    <div id="otherContributions" class="borderBox">
      [#-- Button for edit this section --]
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
          <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if]  
      [#-- Tilte --]
      <h1 class="contentTitle">[@s.text name="planning.impactPathways.otherContributions.title" /] </h1> 
      
      [#-- How are contributing to other CCAFS IP --]
      <div class="fullBlock">
        [@customForm.textArea name="project.ipOtherContribution.contribution" className="contribution limitWords-100" i18nkey="planning.impactPathways.otherContributions.contribution" editable=editable && !reportingCycle /]  
      </div>
      
      [#-- -- -- REPORTING BLOCK -- -- --]
      [#-- Others impact pathways contributions --]
      [#if reportingCycle]
        <div id="otherContributionsBlock">
          [@otherContribution /] 
        </div>
        [#if editable]<div id="addOtherContribution"><a href="" class="addLink">[@s.text name="reporting.projectOtherContributions.addOtherContribution"/]</a></div>[/#if]
        <div class="clearfix"></div>
        <br />
      [/#if]
      
      [#-- Contribution to another center activity --]
      <div class="fullBlock">
        [@customForm.textArea name="project.ipOtherContribution.additionalContribution" className="additionalContribution limitWords-100" i18nkey="planning.impactPathways.otherContributions.contributionToAnotherCenter" editable=editable && !reportingCycle /]  
      </div>
      
      [#-- Collaborating with other CRPs --]
      [#assign crpsName= "project.ipOtherContribution.crps"/]
      <div class="fullPartBlock">      
        <div class="crpContribution panel tertiary">
          <div class="panel-head">[@customForm.text name="planning.impactPathways.otherContributions.collaboratingCRPs" readText=!editable /]</div> 
          <div class="panel-body"> 
            <ul id="contributionsBlock" class="list">
            [#if project.ipOtherContribution.crpContributions?has_content]  
              [#list project.ipOtherContribution.crpContributions as crp]
                <li class="clearfix [#if !crp_has_next]last[/#if]">
                  <input class="id" type="hidden" name="project.ipOtherContribution.crpContributions[${crp_index}].crp.id" value="${crp.crp.id}" />
                  [#-- CRP Title --]
                  <div class="fullPartBlock clearfix">
                    <span class="name crpName">${crp.crp.name}</span>
                  </div>
                  [#-- CRP Collaboration nature --]
                  [@customForm.input name="project.ipOtherContribution.crpContributions[${crp_index}].id" display=false className="crpContributionId" showTitle=false /]
                  <div class="fullPartBlock">
                    [@customForm.textArea name="project.ipOtherContribution.crpContributions[${crp_index}].natureCollaboration" className="crpCollaborationNature limitWords-50" i18nkey="planning.impactPathways.otherContributions.collaborationNature" editable=editable && !reportingCycle required=true/]  
                  </div>
                  [#-- The achieved outcome  --]
                  [#if reportingCycle]
                    <div class="fullPartBlock">
                      [@customForm.textArea name="project.ipOtherContribution.crpContributions[${crp_index}].natureAchieved" className="crpCollaborationAchieved limitWords-100" i18nkey="reporting.projectOtherContributions.collaborationAchieved" editable=editable required=true/]  
                    </div>
                  [/#if]
                  [#if editable]<span class="listButton remove">[@s.text name="form.buttons.remove" /]</span>[/#if]
                </li>
              [/#list] 
            [#else]
              <p class="emptyText"> [@s.text name="planning.impactPathways.otherContributions.crpsEmpty" /] </p>  
            [/#if]  
            </ul>
            [#if editable]
              [@customForm.select name="" label="" disabled=!canEdit i18nkey="" listName="crps" keyFieldName="id"  displayFieldName="name" className="crpsSelect" value="" /]
            [/#if] 
          </div>
        </div> 
      </div>
      
    </div> <!-- End otherContributions -->
    
    [#if !newProject]
    <div id="lessons" class="borderBox">
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#lessons">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
          <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]#lessons">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if]  
      [#-- Lessons learnt from last planning/reporting cycle --]
      [#if (projectLessonsPreview.lessons?has_content)!false]
      <div class="fullBlock">
        <h6>[@customForm.text name="${currentSection}.projectOtherContributions.previousLessons" param="${reportingCycle?string(currentReportingYear,currentPlanningYear-1)}" /]:</h6>
        <div class="textArea "><p>${projectLessonsPreview.lessons}</p></div>
      </div>
      [/#if]
      [#-- Planning/Reporting lessons --]
      <div class="fullBlock">
        <input type="hidden" name="projectLessons.id" value=${(projectLessons.id)!"-1"} />
        <input type="hidden" name="projectLessons.year" value=${reportingCycle?string(currentReportingYear,currentPlanningYear)} />
        <input type="hidden" name="projectLessons.componentName" value="${actionName}">
        [@customForm.textArea name="projectLessons.lessons" i18nkey="${currentSection}.projectOtherContributions.lessons" required=!project.bilateralProject editable=editable /]
      </div>
    </div>
    [/#if]
    
    [#if project.ipOtherContribution?has_content]
      <input name="project.ipOtherContribution.id" type="hidden" value="${project.ipOtherContribution.id}"/>
    [/#if]
    [#if editable] 
      <input type="hidden" id="crpsName" value="project.ipOtherContribution.crpContributions"/>
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
  
</section>

[#-- CRPs Template --]
<ul style="display:none">
  <li id="crpTemplate" class="clearfix">
    <input class="id" type="hidden" name="" value="" />
    [#-- CRP Title --]
    <div class="fullPartBlock clearfix">
      <span class="name crpName"></span>
    </div>
    [#-- CRP Collaboration nature --]
    <div class="fullPartBlock">
      [@customForm.textArea name="" className="crpCollaborationNature limitWords-50" i18nkey="planning.impactPathways.otherContributions.collaborationNature" editable=editable required=true/]  
    </div>
    [#-- The achieved outcome  --]
    [#if reportingCycle]
      <div class="fullPartBlock">
        [@customForm.textArea name="" className="crpCollaborationAchieved limitWords-100" i18nkey="reporting.projectOtherContributions.collaborationAchieved" editable=editable required=true/]  
      </div>
    [/#if]
    [#if editable]<span class="listButton remove">[@s.text name="form.buttons.remove" /]</span>[/#if] 
  </li>
</ul>

[#-- Internal parameters --]
[#list params?keys as prop]<input id="${params[prop].id}" type="hidden" value="${params[prop].name}" />[/#list]

[#-- Other contribution template --]
[@otherContribution template=true /]

[#macro otherContribution index="0" template=false]
  [#assign customName = "${params.otherContributions.name}[${index}]"/]
  [#assign contributionId][@s.property value="${customName}.id" /][/#assign]
  <div id="otherContribution-${template?string('template',index)}" class="otherContribution simpleBox" style="display:${template?string('none','block')}">
    [#-- Edit/Back/remove buttons --]
    [#if (editable && canEdit)]<div class="removeElement" title="[@s.text name="reporting.projectOtherContributions.removeOtherContribution" /]"></div>[/#if]
    [#-- Other Contribution ID --]
    <input type="hidden" name="${customName}.id" class="otherContributionId" value="${(contributionId)!-1}"/>
    <div class="fullBlock">
      [#-- Region --]
      <div class="halfPartBlock">
        [@customForm.select name="${customName}.region" className="otherContributionRegion" label="" i18nkey="reporting.projectOtherContributions.region" listName="regions" keyFieldName="id"  displayFieldName="name" required=true editable=editable /]
      </div>
      [#-- Flagship --]
      <div class="halfPartBlock">
        [@customForm.select name="${customName}.flagship" className="otherContributionFlagship" label="" i18nkey="reporting.projectOtherContributions.flagship" listName="flagships" keyFieldName="id"  displayFieldName="name" required=true editable=editable /]
      </div>
    </div>
    [#-- Indicator --]
    <div class="fullBlock">
      [@customForm.select name="${customName}.indicators" className="otherContributionIndicator" label="" i18nkey="reporting.projectOtherContributions.indicators" listName="otherIndicators" keyFieldName="id"  displayFieldName="name" required=true editable=editable /]
    </div>
    [#-- Describe how you are contributing to the selected outcome --]
    <div class="fullBlock">
      <h6>[@customForm.text name="reporting.projectOtherContributions.description" param="${currentReportingYear}" readText=!editable /]:</h6>
      [@customForm.textArea name="${customName}.description" className="otherContributionDescription limitWords-100"  i18nkey="" showTitle=false editable=editable required=true/]
    </div>
    [#-- Target contribution --]
    <div class="fullBlock">
      <h6>[@customForm.text name="reporting.projectOtherContributions.target" readText=!editable /]:</h6>
      <div class="halfPartBlock">
        [@customForm.input name="${customName}.target" className="otherContributionTarget" i18nkey="" showTitle=false editable=editable /]
      </div>
    </div>
  </div> 
[/#macro]


[#include "/WEB-INF/global/pages/footer.ftl"]