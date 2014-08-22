[#ftl]
[#-- This macro is being used in activities.ftl. The idea is to represent a table with specific information about activities --]
[#macro activitiesList activities owned=true canValidate=false canEditProject=false tableID="defaultTable"]
    <table class="activitiesList" id="${tableID}">
		  [#if activities?has_content]
		    <thead>
          <tr>
            <th id="ids" >[@s.text name="planning.activities.id" /]</th>
            <th id="activitiesTitles" class="left">[@s.text name="planning.activities.title" /]</th>
            <th id="activitiesLeaders">[@s.text name="planning.activities.leader" /]</th>
            <th id="activitiesOrganizations">[@s.text name="planning.activities.organization" /]</th>
            <th id="activitiesStatuses">[@s.text name="planning.activities.statusCompletion" /]</th>
          </tr>
        </thead>
        <tbody>
        [#list activities as activity]
    		   <tr>
              <td>
                  <a href="[@s.url action='activityDescription' ][@s.param name='activityID']${activity.id?c}[/@s.param][/@s.url]">${activity.id?c}</a>
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
                    [#if activity.leader?has_content]
                    	${activity.leader.lastName} ${activity.leader.firstName}
                    [#else]
                    	${activity.expectedLeader.name}
                    [/#if]
                  </a>
              </td>
              <td> 
                  <a href="[@s.url action='activityDescription' ] [@s.param name='activityID']${activity.id?c}[/@s.param] [/@s.url]">
                     [#if activity.leader?has_content]
                    	${activity.leader.currentInstitution.name} 
                    [#else]
                    	${activity.expectedLeader.institution.name}
                    [/#if]
                  </a>
              </td>
              <td> 
                  <a href="[@s.url action='activityDescription' ] [@s.param name='activityID']${activity.id?c}[/@s.param] [/@s.url]">
                    Complete / Incomplete
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