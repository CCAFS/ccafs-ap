[#ftl]
[#macro projectsList projects owned=true canValidate=false canEditProject=false tableID=""]
    
    <table class="activityList" id="${tableID}">
		  <thead>
		    <tr>
		      <th id="ids">[@s.text name="preplanning.project.ids" /]</th>
		      <th id="projectTitles">[@s.text name="preplanning.project.projectTitles" /]</th> 
	        <th id="projectRegions">[@s.text name="preplanning.projects.projectRegions" /]</th>
		      <th id="projectFlagships">[@s.text name="preplanning.projects.projectFlagships" /]</th> 
		      <th id="projectBudget">[@s.text name="preplanning.projects.projectBudget" /]</th> 
		    </tr>
		  </thead>
		  <tbody> 
		  </tbody>
		</table>

[/#macro]