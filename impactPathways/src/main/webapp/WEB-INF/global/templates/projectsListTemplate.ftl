[#ftl]
[#import "/WEB-INF/global/macros/utils.ftl" as utilities/]
[#-- This macro is being used in projectsListPreplanning.ftl and projectsListPlanning.ftl The idea is to represent a table with specific information about projects --]
[#macro projectsList projects owned=true canValidate=false isPlanning=false namespace="/"]
  <table class="projectsList" id="projects">
    <thead>
      <tr class="header">
        <th colspan="5">General Information</th>
        <th colspan="2">[@s.text name="preplanning.projects.projectBudget" /]</th> 
        <th colspan="3">Actions</th> 
      </tr>
      <tr class="subHeader">
        <th id="ids">[@s.text name="preplanning.projects.projectids" /]</th>
        <th id="projectTitles" >[@s.text name="preplanning.projects.projectTitles" /]</th>
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
              <a href="[@s.url namespace=namespace action='description'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">
        P${project.id}
          </a>
        </td>
          [#-- Project Title --]
          <td class="left"> 
                [#if project.title?has_content]
                  <a href="[@s.url namespace=namespace action='description'] [@s.param name='projectID']${project.id?c}[/@s.param][/@s.url] "
                  title="${project.title}">
                  [#if project.title?length < 120] ${project.title}</a> [#else] [@utilities.wordCutter string=project.title maxPos=120 /]...</a> [/#if]
                [#else]
                  <a href="[@s.url namespace=namespace action='description' includeParams='get'] [@s.param name='projectID']${project.id?c}[/@s.param][/@s.url] ">
                    [@s.text name="preplanning.projects.title.none" /]
                  </a>
                [/#if]
               
          </td>
          [#-- Project Type --]
          <td>
            <p title="[@s.text name="planning.projects.type.explanation.${project.type?lower_case}" /]" id="">[@s.text name="planning.projects.type.${project.type?lower_case}" /]</p>
          </td>
          [#-- Region --]
          <td> 
            [#if project.regionsAcronym?has_content]
              ${project.regionsAcronym}
            [#else]
              [@s.text name="preplanning.projects.none" /]
            [/#if]
          </td>
          [#-- Flagship --]
          <td> 
            [#if project.flagshipsAcronym?has_content]
              ${project.flagshipsAcronym}
            [#else]
              [@s.text name="preplanning.projects.none" /]
            [/#if]
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
            [#assign submission = project.isSubmitted(currentPlanningYear, 'Planning')! /]
            <p>Has submission : ${(submission?has_content)?string}</p>
            [#-- Check button --]
            [#if securityContext.canSubmitProject(project.id) && !submission?has_content]
              <a id="validateProject-${project.id}" class="validateButton ${(project.type)!''}" href="#">[@s.text name="form.buttons.check" /]</a>
              <div id="progressbar-${project.id}" class="progressbar" style="display:none"></div>
            [/#if]
            
            [#-- Submit button --]
            [#if submission?has_content]
              <p>Submitted on </p>
            [#else]
              [#if securityContext.canSubmitProject(project.id)]
                <a id="submitProject-${project.id}" class="submitButton" href="[@s.url namespace=namespace action='submit'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]" style="display:none">[@s.text name="form.buttons.submit" /]</a>
              [#else]
                <p title="The project can be submitted by Management liaisons and Contact points">Not Submitted</p>
              [/#if]
            [/#if]
            
          </td>
          [#-- Track completition of entry --]
          [#if isPlanning]
          <td> 
            <a href="#">Complete / Incomplete</a>
          </td>
          [/#if]
          [#-- Summary download --]
          <td> 
            <a href="[@s.url namespace="/summaries" action='project'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]" target="__BLANK">
              <img src="${baseUrl}/images/global/download-summary.png" height="25" alt="[@s.text name="summaries.project.download" /]" title="[@s.text name="summaries.project.download" /]" />
            </a> 
          </td>
          [#-- Delete Project--]
          <td>
            [#if securityContext.canDeleteProject() && project.isNew(currentPlanningStartDate) ]
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