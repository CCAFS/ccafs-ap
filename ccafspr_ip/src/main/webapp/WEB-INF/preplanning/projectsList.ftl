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
              <td> 
                  <a href=" [@s.url action='description' includeParams='get']  [/@s.url]" >
                    ${project.id}
                  </a>  
              </td>
              <td> 
                  <a href=" [@s.url action='description' includeParams='get']  [/@s.url]" >
                    ${project.title}
                  </a>  
              </td>
              <td> 
                  <a href=" [@s.url action='description' includeParams='get']  [/@s.url]" >
                    regions
                  </a>  
              </td>
              <td> 
                  <a href=" [@s.url action='description' includeParams='get']  [/@s.url]" >
                    flagship
                  </a>  
              </td>
              <td> 
                  <a href=" [@s.url action='description' includeParams='get']  [/@s.url]" >
                    TBD
                  </a>  
              </td>
          	</tr>  
        	[/#list]
        [#else]
          <tr>
            <td colspan="4">[@s.text name="planning.activityList.empty" /]</td>
          </tr>
        [/#if]  
		  </tbody>
		</table>

[/#macro]