[#ftl]
[#import "/WEB-INF/global/macros/utils.ftl" as utilities/]
[#-- This macro is being used in projectsListPreplanning.ftl and projectsListPlanning.ftl The idea is to represent a table with specific information about projects --]
[#macro projectsList projects owned=true canValidate=false isPlanning=false namespace="/"]
  <table class="projectsList" id="projects">
    <thead>
      <tr>
        <th id="ids">[@s.text name="preplanning.projects.projectids" /]</th>
        <th id="projectTitles" >[@s.text name="preplanning.projects.projectTitles" /]</th>
        <th id="projectRegions">[@s.text name="preplanning.projects.projectRegions" /]</th>
        <th id="projectFlagships">[@s.text name="preplanning.projects.projectFlagships" /]</th>
        <th id="projectBudget">[@s.text name="preplanning.projects.projectBudget" /]</th>
        <th id="projectType">[@s.text name="preplanning.projects.projectType" /]</th>
        <th id="projectReportStatus">[@s.text name="preplanning.projects.projectReportStatus" /]</th>
        <th id="projectDownload"></th>
        <th id="projectDelete"></th>
        [#if isPlanning]
        <th id="projectBudget">[@s.text name="planning.projects.completion" /]</th>
        [/#if]
      </tr>
    </thead>
    <tbody>
      [#list projects as project]
        <tr>
        [#-- ID --]
        <td>
          <a href="[@s.url namespace=namespace action='description' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">
            P${project.id}
          </a>
        </td>
          [#-- Project Title --]
          <td class="left"> 
                [#if project.title?has_content]
                  <a href="[@s.url namespace=namespace action='description' includeParams='get'] [@s.param name='projectID']${project.id?c}[/@s.param][/@s.url] "
                  title="${project.title}">
                  [#if project.title?length < 120] ${project.title}</a> [#else] [@utilities.wordCutter string=project.title maxPos=120 /]...</a> [/#if]
                [#else]
                  <a href="[@s.url namespace=namespace action='description' includeParams='get'] [@s.param name='projectID']${project.id?c}[/@s.param][/@s.url] ">
                    [@s.text name="preplanning.projects.title.none" /]
                  </a>
                [/#if]
               
          </td>
          [#-- Region --]
          <td> 
              <a href="[@s.url namespace=namespace action='description' includeParams='get'] [@s.param name='projectID']${project.id?c}[/@s.param] [/@s.url]">
                [#if project.regionsAcronym?has_content]
                  ${project.regionsAcronym}
                [#else]
                  [@s.text name="preplanning.projects.none" /]
                [/#if]
              </a>
          </td>
          [#-- Flagship --]
          <td> 
              <a href="[@s.url namespace=namespace action='description' includeParams='get'] [@s.param name='projectID']${project.id?c}[/@s.param] [/@s.url]">
                [#if project.flagshipsAcronym?has_content]
                  ${project.flagshipsAcronym}
                [#else]
                  [@s.text name="preplanning.projects.none" /]
                [/#if]
              </a>
          </td>
          [#-- Budget --]
          <td> 
              <a href="[@s.url namespace=namespace action='description' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">
                [#if project.totalCcafsBudget?has_content]
                  <p id="">US$ <span id="">${project.totalCcafsBudget?string(",##0.00")}</span></p> 
                [#else]
                  [@s.text name="preplanning.projects.none" /]
                [/#if]
              </a>
          </td>
          [#-- Project Type --]
          <td> 
              <a href="[@s.url namespace=namespace action='description' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">
                <p id="">{project.type}</p> 
              </a>
          </td>
          [#-- Project Report Status --]
          <td> 
              <a href="[@s.url namespace=namespace action='description' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">
                <p id="">Submit</p> 
              </a>
          </td>
          [#-- Track completition of entry --]
          [#if isPlanning]
          <td> 
              <a href="[@s.url namespace=namespace action='description' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">
                Complete / Incomplete
              </a>
          </td>
          [/#if]
          [#-- Summary download --]
          <td> 
              <a href="[@s.url namespace="/summaries" action='project' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]" target="__BLANK">
                <img src="${baseUrl}/images/global/download-summary.png" height="25" alt="[@s.text name="summaries.project.download" /]" title="[@s.text name="summaries.project.download" /]" />
              </a> 
          </td>
          [#-- Delete Project--]
          <td>
              [#if securityContext.canDeleteProject() && project.isErasable(2014) ]
                <a href="
                [@s.url action='deleteProject' includeParams='get' namespace='/planning/projects' ] 
                  [@s.param name='${projectRequest}' ]${project.id?c}[/@s.param]
                [/@s.url]
                " title="" class="removeDeliverable">
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