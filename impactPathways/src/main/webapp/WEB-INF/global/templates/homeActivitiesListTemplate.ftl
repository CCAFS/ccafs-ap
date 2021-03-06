[#ftl]
[#-- This macro is being used in activities.ftl. The idea is to represent a table with specific information about activities --]
[#macro activitiesList activities owned=true canValidate=false canEditProject=false namespace="/" tableID="defaultTable"]
  [#if activities?has_content]
    <table class="activitiesList" id="${tableID}">
      <thead>
        <tr>
          <th id="ids" >[@s.text name="planning.activities.id" /]</th>
          <th id="activitiesTitles" class="left">[@s.text name="planning.activities.title" /]</th>
          
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
          </tr>  
        [/#list]
      </tbody>
    </table>
  [#else]
    <p>[@s.text name="planning.activities.empty" /]</p>
  [/#if]
[/#macro]