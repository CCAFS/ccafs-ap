[#ftl]
[#-- This macro is being used in activities.ftl. The idea is to represent a table with specific information about activities --]
[#macro activitiesList activities owned=true canValidate=false canEditProject=false tableID=""]

    <table class="activitiesList" id="${tableID}">
		  <thead>
		    <tr>
		      <th id="ids">[@s.text name="planning.activities.id" /]</th>
		      <th id="activitiesTitles">[@s.text name="planning.activities.title" /]</th>
	        <th id="activitiesLeaders">[@s.text name="planning.activities.leader" /]</th>
		      <th id="activitiesOrganizations">[@s.text name="planning.activities.organization" /]</th>
		      <th id="activitiesStatuses">[@s.text name="planning.activities.statusCompletion" /]</th>
		    </tr>
		  </thead>
		  <tbody>
		  [#if activities?has_content]
        [#list activities as activity]
    		   <tr>
              <td>
                  <a href="[@s.url action='activityDescription' ][@s.param name='activityID']${activity.id?c}[/@s.param][/@s.url]">
                  ${activity.id?c}
                  </a>
              </td>
              <td>
                  <a href="[@s.url action='activityDescription' ] [@s.param name='activityID']${activity.id?c}[/@s.param][/@s.url]">
                    [#if activity.title?has_content]
                      ${activity.title}
                    [#else]
                      [@s.text name="planning.activities.title.none" /]
                    [/#if]
                  </a>
              </td>
              <td> 
                  <a href="[@s.url action='activityDescription'] [@s.param name='activityID']${activity.id?c}[/@s.param] [/@s.url]">
                    TODO
                  </a>
              </td>
              <td> 
                  <a href="[@s.url action='activityDescription' ] [@s.param name='activityID']${activity.id?c}[/@s.param] [/@s.url]">
                    TODO
                  </a>
              </td>
              <td> 
                  <a href="[@s.url action='activityDescription' ] [@s.param name='activityID']${activity.id?c}[/@s.param] [/@s.url]">
                    TODO
                  </a>
              </td>
          	</tr>  
        	[/#list]
        [#else]
          <tr>
            <td colspan="5"><h6>[@s.text name="planning.activityList.empty" /]</h6></td>
          </tr>
        [/#if]
		  </tbody>
		</table>

[/#macro]