[#ftl]
[#-- This macro is being used in activities.ftl. The idea is to represent a table with specific information about activities --]
[#macro activitiesList activities owned=true canValidate=false canEditProject=false namespace="/" tableID="defaultTable"]
  [#if activities?has_content]
    <table class="activitiesList" id="${tableID}">
      <thead>
        <tr>
          <th id="ids" >[@s.text name="planning.activities.id" /]</th>
          <th id="activitiesTitles" class="left">[@s.text name="planning.activities.title" /]</th>
          <th id="activitiesLeaders">[@s.text name="planning.activities.leader" /]</th>
          <th id="activitiesOrganizations">[@s.text name="planning.activities.organization" /]</th>
          [#-- Complete / Incomplete  - TODO --][#-- <th id="activitiesStatuses">[@s.text name="planning.activities.statusCompletion" /]</th> --]
        </tr>
      </thead>
      <tbody>
        [#list activities as activity]
          <tr>
            <td>
              <a href="[@s.url namespace=namespace action='activityDescription' ][@s.param name='activityID']${activity.id?c}[/@s.param][/@s.url]">${activity.composedId}</a>
            </td>
            <td>
              <a href="[@s.url namespace=namespace action='activityDescription' ] [@s.param name='activityID']${activity.id?c}[/@s.param][/@s.url]">
                [#if activity.title?has_content]
                  ${activity.title}
                [#else]
                  [@s.text name="planning.activities.notDefined" /]
                [/#if]
              </a>
            </td>
            <td> 
              <a href="[@s.url namespace=namespace action='activityDescription'] [@s.param name='activityID']${activity.id?c}[/@s.param] [/@s.url]">
                [#if activity.leader?has_content]
                  ${activity.leader.lastName} ${activity.leader.firstName}
                [#elseif activity.expectedLeader?has_content]
                  ${activity.expectedLeader.firstName}
                [#else]
                  [@s.text name="planning.activities.notDefined" /]
                [/#if]
              </a>
            </td>
            <td> 
              <a href="[@s.url namespace=namespace action='activityDescription' ] [@s.param name='activityID']${activity.id?c}[/@s.param] [/@s.url]">
                [#if activity.leader?has_content]
                  ${activity.leader.currentInstitution.name} 
                [#elseif activity.expectedLeader?has_content]
                  ${activity.expectedLeader.currentInstitution.name}
                [#else]
                  [@s.text name="planning.activities.notDefined" /]
                [/#if]
              </a>
            </td>
              [#-- Complete / Incomplete  - TODO --]
              [#-- 
              <td> 
                <a href="[@s.url namespace=namespace action='activityDescription' ] [@s.param name='activityID']${activity.id?c}[/@s.param] [/@s.url]">
                  Complete / Incomplete
                </a>
              </td>
              --]
          </tr>  
        [/#list]
      </tbody>
    </table>
  [#else]
    <p class="simpleBox center">[@s.text name="planning.activities.empty" /]</p>
  [/#if]
[/#macro]