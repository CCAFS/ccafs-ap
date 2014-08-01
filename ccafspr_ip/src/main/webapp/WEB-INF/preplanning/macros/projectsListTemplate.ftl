[#ftl]
[#-- This macro is being used in projects.ftl. The idea is to represent a table with specific information about projects --]
[#macro projectsList projects owned=true canValidate=false canEditProject=false tableID=""]

    <table class="projectsList" id="${tableID}">
		  <thead>
		    <tr>
		      <th id="ids">[@s.text name="preplanning.projects.projectids" /]</th>
		      <th id="projectTitles">[@s.text name="preplanning.projects.projectTitles" /]</th>
	        <th id="projectRegions">[@s.text name="preplanning.projects.projectRegions" /]</th>
		      <th id="projectFlagships">[@s.text name="preplanning.projects.projectFlagships" /]</th>
		      <th id="projectBudget">[@s.text name="preplanning.projects.projectBudget" /]</th>
		    </tr>
		  </thead>
		  <tbody>
		  [#if projects?has_content]
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
                  <a href="[@s.url action='description' includeParams='get'] [@s.param name='projectID']${project.id?c}[/@s.param] [/@s.url]">
                    [#if project.totalCcafsBudget?has_content]
                    ${project.totalCcafsBudget}
                    [#else]
                      [@s.text name="preplanning.projects.none" /]
                    [/#if]
                  </a>
              </td>
          	</tr>  
        	[/#list]
        [#else]
          <tr>
            <td colspan="5">[@s.text name="planning.activityList.empty" /]</td>
          </tr>
        [/#if]
		  </tbody>
		</table>

[/#macro]