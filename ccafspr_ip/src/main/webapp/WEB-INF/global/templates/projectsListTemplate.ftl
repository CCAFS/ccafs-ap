[#ftl]
[#-- This macro is being used in projects.ftl. The idea is to represent a table with specific information about projects --]
[#macro projectsList projects owned=true canValidate=false isPlanning=false]
  <table class="projectsList" id="projects">
	  <thead>
	    <tr>
	      <th id="ids">[@s.text name="preplanning.projects.projectids" /]</th>
	      <th id="projectTitles">[@s.text name="preplanning.projects.projectTitles" /]</th>
        <th id="projectRegions">[@s.text name="preplanning.projects.projectRegions" /]</th>
	      <th id="projectFlagships">[@s.text name="preplanning.projects.projectFlagships" /]</th>
	      <th id="projectBudget">[@s.text name="preplanning.projects.projectBudget" /]</th>
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
          <a href="[@s.url action='description' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">
            ${project.composedId}
          </a>
        </td>
          [#-- Project Title --]
          <td>
              <a href="[@s.url action='description' includeParams='get'] [@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">
                [#if project.title?has_content]
                  ${project.title}
                [#else]
                  [@s.text name="preplanning.projects.title.none" /]
                [/#if]
              </a>
          </td>
          [#-- Region --]
          <td> 
              <a href="[@s.url action='description' includeParams='get'] [@s.param name='projectID']${project.id?c}[/@s.param] [/@s.url]">
                [#if project.regionsAcronym?has_content]
                ${project.regionsAcronym}
                [#else]
                  [@s.text name="preplanning.projects.none" /]
                [/#if]
              </a>
          </td>
          [#-- Flagship --]
          <td> 
              <a href="[@s.url action='description' includeParams='get'] [@s.param name='projectID']${project.id?c}[/@s.param] [/@s.url]">
                [#if project.flagshipsAcronym?has_content]
                ${project.flagshipsAcronym}
                [#else]
                  [@s.text name="preplanning.projects.none" /]
                [/#if]
              </a>
          </td>
          [#-- Budget --]
          <td> 
              <a href="[@s.url action='description' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">
                [#if project.totalCcafsBudget?has_content]
                ${project.totalCcafsBudget}
                [#else]
                  [@s.text name="preplanning.projects.none" /]
                [/#if]
              </a>
          </td>
          [#-- Track completition of entry --]
          [#if isPlanning]
          <td> 
              <a href="[@s.url action='description' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">
                Complete / Incomplete
              </a>
          </td>
          [/#if]
        </tr>  
      [/#list]
	  </tbody>
	</table>

[/#macro]