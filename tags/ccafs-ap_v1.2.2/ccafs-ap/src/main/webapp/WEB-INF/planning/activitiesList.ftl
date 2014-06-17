[#ftl]
[#--
 
 * This file is part of CCAFS Planning and Reporting Platform.
 *
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 *
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see 
 * <http://www.gnu.org/licenses/>
  
--]

[#macro activitiesList activities owned=true canValidate=false canEditActivity=false tableID=""]

  <table class="activityList" id="${tableID}">
  <thead>
    <tr>
      <th id="id">[@s.text name="planning.activityList.id" /]</th>
      <th id="activity">[@s.text name="planning.activityList.activity" /]</th>
      [#if owned]
        <th id="contactPerson">[@s.text name="planning.activityList.contactPerson" /]</th>
      [#else]
        <th id="leaderName">[@s.text name="planning.activityList.leader" /]</th>
      [/#if]
      <th id="theme">[@s.text name="planning.activityList.milestone" /]</th>
      [#if canValidate]
        <th id="validated">[@s.text name="planning.activityList.validated" /]</th>
      [/#if]
    </tr>
  </thead>
  <tbody>
    [#if activities?has_content]
      [#list activities as activity]
        <tr>
          <td>
            [#if canEditActivity]
              <a href=" [@s.url action='mainInformation' includeParams='get'] [@s.param name='${activityRequestParameter}']${activity.id}[/@s.param] [/@s.url]" >
                ${activity.activityId}
              </a> 
            [#else]
              <a target="_blank" href=" [@s.url action='activity' namespace="/home" includeParams='get'] [@s.param name='${publicActivityRequestParameter}']${activity.id?c}[/@s.param] [/@s.url]" >
                ${activity.id?c}
              </a>
            [/#if]
          </td>
          <td class="left">
            [#if canEditActivity]
              <a href="
                [@s.url action='mainInformation' includeParams='get']
                  [@s.param name='${activityRequestParameter}']${activity.id?c}[/@s.param]
                [/@s.url]" title="${activity.title}">
                [#if activity.title?has_content]
                  [#if activity.title?length < 70] ${activity.title} [#else] [@utilities.wordCutter string=activity.title maxPos=70 /]... [/#if]
                [#else]
                  [@s.text name="planning.activityList.title.undefined" /]
                [/#if]
              </a> 
            [#else]
              <a target="_blank" href=" [@s.url action='activity' namespace="/home" includeParams='get'] [@s.param name='${publicActivityRequestParameter}']${activity.id?c}[/@s.param] [/@s.url]" >
                [#if activity.title?has_content]
                  [#if activity.title?length < 70] ${activity.title} [#else] [@utilities.wordCutter string=activity.title maxPos=70 /]... [/#if]
                [#else]
                  [@s.text name="planning.activityList.title.undefined" /]
                [/#if]
              </a>
            [/#if]
          </td>
          <td>
            [#if owned]
              [#if activity.contactPersons?has_content]
                [#if activity.contactPersons[0].email?has_content]
                  <a href="mailto:${activity.contactPersons[0].email}">${activity.contactPersons[0].name}</a>
                [#else]
                  ${activity.contactPersons[0].name}
                [/#if]
              [#else]
                [@s.text name="planning.activityList.contactPerson.empty" /]
              [/#if]
            [#else]
              ${activity.leader.acronym}
            [/#if]
          </td>               
          <td>
            [#if activity.milestone.code?has_content]
              ${activity.milestone.code}
            [#else]
              [@s.text name="planning.activityList.milestone.undefined" /]
            [/#if]
          </td>
          [#if canValidate]
            <td>
              [#if activity.validated] 
                <div alt="Activity validated" title="Activity validated" ><div class="icon-20" id="i-checkedActivity"></div>Validated </div>
              [#else]
                [#-- The PI only can see a notification, they can't validate the activity --]
                [#if currentUser.PI] 
                  <div alt="This activity has not been validated yet" title="This activity has not been validated yet"  ><div class="icon-20" id="i-errorCheckedActivity"></div>Validate</div>
                [#else]
                  [#-- The CP/TL/RPL can validate the activity if needed --]
                  [#if activityID == activity.id]
                    [#-- User tried to submit this activity but there is some missing data. --] 
                    <div alt="There is missing data" title="There is missing data"  ><div class="icon-20" id="i-errorCheckedActivity"></div>Validate</div>
                  [#else]
                    [#-- We send the index of the activity in the array, not the activity identifier  --]
                    [#-- in order find quickly the activity in the array to modify it.  --]
                    [@s.form action="activities" cssClass="buttons"]
                      <input name="activityIndex" value="${activity_index}" type="hidden"/>
                      [@s.submit type="button" name="save"][@s.text name="form.buttons.validate" /][/@s.submit]
                    [/@s.form]  
                  [/#if]
                [/#if]
              [/#if]
            </td>
          [/#if]
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