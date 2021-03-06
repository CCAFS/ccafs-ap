[#ftl]
[#import "/WEB-INF/global/macros/utils.ftl" as utilities/]
[#-- This macro is being used in projectsListPreplanning.ftl and projectsListPlanning.ftl The idea is to represent a table with specific information about projects --]
[#macro projectsList projects owned=true canValidate=false canEdit=false isPlanning=false namespace="/" defaultAction="description"]
  <table class="projectsList" id="projects">
    <thead>
      <tr class="header">
        <th colspan="6">General Information</th>
        <th colspan="2">[@s.text name="preplanning.projects.projectBudget" /] ${reportingCycle?string(currentReportingYear,currentPlanningYear)}</th> 
        <th colspan="3">Actions</th> 
      </tr>
      <tr class="subHeader">
        <th id="ids">[@s.text name="preplanning.projects.projectids" /]</th>
        <th id="projectTitles" >[@s.text name="preplanning.projects.projectTitles" /]</th>
        <th id="projectLeader" >[@s.text name="preplanning.projects.projectLeader" /]</th>
        <th id="projectType">[@s.text name="preplanning.projects.projectType" /]</th>
        <th id="projectRegions">[@s.text name="preplanning.projects.projectRegions" /]</th>
        <th id="projectFlagships">[@s.text name="preplanning.projects.projectFlagships" /]</th>
        <th id="projectBudget">[@s.text name="preplanning.projects.W1W2projectBudget" /]</th>
        <th id="projectBudget">[@s.text name="preplanning.projects.W3BILATERALprojectBudget" /]</th>
        <th id="projectActionStatus">[@s.text name="preplanning.projects.projectActionStatus" /]</th>
        <th id="projectDownload">[@s.text name="preplanning.projects.download" /]</th>
        <th id="projectDelete">[@s.text name="preplanning.projects.delete" /]</th>
        [#if isPlanning]
          <th id="projectBudget">[@s.text name="planning.projects.completion" /]</th>
        [/#if]
      </tr>
    </thead>
    <tbody>
      [#list projects as project]
        <tr>
        [#-- ID --]
        <td class="projectId">
          <a href="[@s.url namespace=namespace action=defaultAction][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]"> P${project.id}</a>
        </td>
          [#-- Project Title --]
          <td class="left"> 
            [#if project.title?has_content]
              <a href="[@s.url namespace=namespace action=defaultAction] [@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]" title="${project.title}">
              [#if project.title?length < 120] ${project.title}</a> [#else] [@utilities.wordCutter string=project.title maxPos=120 /]...</a> [/#if]
            [#else]
              <a href="[@s.url namespace=namespace action=defaultAction includeParams='get'] [@s.param name='projectID']${project.id?c}[/@s.param][/@s.url] ">
                [@s.text name="preplanning.projects.title.none" /]
              </a>
            [/#if]
          </td>
          [#-- Project Leader --]
          <td class=""> 
            [#if project.leadInstitutionAcronym?has_content]${project.leadInstitutionAcronym}[#else][@s.text name="preplanning.projects.title.none" /][/#if]
          </td>
          [#-- Project Type --]
          <td>
            [#if project.bilateralProject && project.cofinancing]
              <p title="[@s.text name="planning.projects.type.explanation.bilateralCoFinancing" /]" id="">[@s.text name="planning.projects.type.bilateralCoFinancing" /]</p>
            [#else]
              <p title="[@s.text name="planning.projects.type.explanation.${project.type?lower_case}" /]" id="">[@s.text name="planning.projects.type.${project.type?lower_case}" /]</p>
            [/#if]
          </td>
          [#-- Region --]
          <td> 
            [#if project.regionsAcronym?has_content]${project.regionsAcronym}[#else][@s.text name="preplanning.projects.none" /][/#if]
          </td>
          [#-- Flagship --]
          <td> 
            [#if project.flagshipsAcronym?has_content]${project.flagshipsAcronym}[#else][@s.text name="preplanning.projects.none" /][/#if]
          </td>
          [#-- Budget W1/W2 --]
          <td class="budget"> 
            [#if project.totalBudget?has_content]
              <p id="">US$ <span id="">${((project.totalCcafsBudget)!0)?string(",##0.00")}</span></p> 
            [#else]
              [@s.text name="preplanning.projects.none" /]
            [/#if]
          </td>
          [#-- Budget W3/ Bilateral --]
          <td class="budget"> 
            [#if project.totalBudget?has_content]
              <p id="">US$ <span id="">${((project.totalBilateralBudget)!0)?string(",##0.00")}</span></p> 
            [#else]
              [@s.text name="preplanning.projects.none" /]
            [/#if]
          </td>
          [#-- Project Action Status --]
          <td>
            [#assign currentCycleYear= (reportingCycle?string(currentReportingYear,currentPlanningYear))?number /]
            [#assign submission = (project.isSubmitted(currentCycleYear, cycleName))! /]
            [#assign canSubmit = action.hasProjectPermission("submitProject", project.id, "manage") /]
            
            [#-- Check button --] 
            [#if !submission?has_content ]
              [#if canEdit && canSubmit && !action.isProjectComplete(project.id)]
                <a id="validateProject-${project.id}" title="Check for missing fields" class="validateButton ${(project.type)!''}" href="#" >[@s.text name="form.buttons.check" /]</a>
                <div id="progressbar-${project.id}" class="progressbar" style="display:none"></div>
              [/#if]
            [/#if]
            
            [#-- Submit button --]
            [#if submission?has_content]
              <p title="Submitted on ${(submission.dateTime?date)?string.full} ">Submitted</p>
            [#else]
              [#if canSubmit]
                [#assign showSubmit=(canSubmit && !submission?has_content && action.isProjectComplete(project.id))]
                <a id="submitProject-${project.id}" class="submitButton" href="[@s.url namespace=namespace action='submit'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]" style="display:${showSubmit?string('block','none')}">[@s.text name="form.buttons.submit" /]</a>
              [/#if]
            [/#if]
            
            [#if !submission?has_content && !canEdit ]
              <p title="The project can be submitted by Management liaisons and Contact points">Not Submitted</p>
            [/#if]
          </td>
          [#-- Track completition of entry --]
          [#if isPlanning]
          <td> <a href="#">Complete / Incomplete</a></td>
          [/#if]
          [#-- Summary PDF download --]
          <td>
            [#if true]
            <a href="[@s.url namespace="/summaries" action='project'][@s.param name='projectID']${project.id?c}[/@s.param][@s.param name='cycle']${action.getCycleName()}[/@s.param][/@s.url]" target="__BLANK">
              <img src="${baseUrl}/images/global/download-summary.png" height="25" title="[@s.text name="summaries.project.download" /]" />
            </a>
            [#else]
              <img src="${baseUrl}/images/global/download-summary-disabled.png" height="25" title="[@s.text name="menu.link.disabled" /]" />
            [/#if]
          </td>
          [#-- Delete Project--]
          <td>
            [#if (action.hasProjectPermission("deleteProject", project.id, "manage") && project.isNew(currentPlanningStartDate)) ]
              <a id="removeProject-${project.id}" class="removeProject" href="#" title="">
                <img src="${baseUrl}/images/global/trash.png" title="[@s.text name="preplanning.projects.deleteProject" /]" /> 
              </a>
            [#else]
              <img src="${baseUrl}/images/global/trash_disable.png" title="[@s.text name="preplanning.projects.cantDeleteProject" /]" />
            [/#if]
          </td>
        </tr>  
      [/#list]
    </tbody>
  </table>
[/#macro]

[#macro evaluationProjects projects owned=true canValidate=false canEdit=false isPlanning=false namespace="/" defaultAction="evaluation"]
  <table class="evaluationProjects" id="projects">
    <thead> 
      <tr class="subHeader">
        <th class="idsCol">[@s.text name="preplanning.projects.projectids" /]</th>
        <th class="projectTitlesCol" >[@s.text name="preplanning.projects.projectTitles" /]</th>
        <th class="leaderCol">Leader</th>
        <th class="focusCol">Region / Flagship</th>
        <th class="yearCol">Year</th>
        <th class="statusCol">Status</th>
        <th class="totalScoreCol">Total Score</th>
      </tr>
    </thead>
    <tbody>
      [#list projects as project]
        [#assign projectUrl][@s.url namespace=namespace action=defaultAction][@s.param name='projectID']${project.id?c}[/@s.param][@s.param name='edit']true[/@s.param][/@s.url][/#assign]
        <tr>
          [#-- ID --]
          <td class="projectId">
            <a href="${projectUrl}"> P${project.id}</a>
          </td>
          [#-- Project Title --]
          <td class="left"> 
            [#if project.title?has_content]
              <a href="${projectUrl}" title="${project.title}">[@utilities.wordCutter string=project.title maxPos=120 /]</a>  
            [#else]
              <a href="${projectUrl}">[@s.text name="preplanning.projects.title.none" /]</a>
            [/#if]
          </td>
          [#-- Leader --]
          <td>[#if project.leadInstitutionAcronym?has_content]${project.leadInstitutionAcronym}[#else][@s.text name="preplanning.projects.title.none" /][/#if]</td>
          [#-- Region / Flagship --]
          <td>
            [#if project.flagships?has_content][#list project.flagships as element]<p class="focus region">${(element.acronym)!}</p>[/#list][/#if]
            [#if project.regions?has_content][#list project.regions as element]<p class="focus flagship">${(element.acronym)!}</p>[/#list][/#if]
          </td>
          [#-- Year --]
          <td><p class="center">${project.yearEvaluation}</p></td>
          [#-- Status --]
          <td><p class="center">${project.statusEvaluation}</p></td>
          [#-- Total Score --]
          <td><p class="totalScore">${project.totalScoreEvaluation}</p></td>
        </tr>  
      [/#list]
    </tbody>
  </table>
[/#macro]