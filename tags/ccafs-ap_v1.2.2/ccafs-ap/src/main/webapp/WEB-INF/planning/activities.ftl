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

[#assign title = "Activity List (Planning)" /]
[#assign globalLibs = ["jquery", "dataTable", "noty"] /]
[#assign customJS = ["${baseUrl}/js/planning/activity-list.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = "planning" /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/utils.ftl" as utilities/]
[#import "/WEB-INF/planning/activitiesList.ftl" as activityList/]
    
<section id="activityListPlanning" class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.introduction.part1" /] </p>
    <p> [@s.text name="planning.introduction.part2" /] </p>
  </div>
  
  <article class="fullContent">
    <h1>[#if currentUser.leader??]${currentUser.leader.name}[/#if] [@s.text name="planning.activityList.activities" /] </h1>   
    
    <div id="activityTables">
      <ul>
        [#-- Current Activities --]
        [#if currentUser.TL]
          <li><a href="#activityTables-1"> [@s.text name="planning.activityList.themeActivities" /] </a></li>
        [#elseif currentUser.RPL ]
          <li><a href="#activityTables-1"> [@s.text name="planning.activityList.regionActivities" /] </a></li>
        [#else]
          <li><a href="#activityTables-1"> [@s.text name="planning.activityList.activities" /] ${currentYear?c} </a></li>
        [/#if]
        [#-- Future activities --]
        [#if futurePlanningActive]
          <li><a href="#activityTables-2"> [@s.text name="planning.activityList.futureActivities" /] </a></li>
        [/#if]
        [#-- Previous activities --]
        <li><a href="#activityTables-3"> [@s.text name="planning.activityList.previousActivities" /] </a></li>
        [#-- Related activities --]
        [#if currentUser.TL]
          <li><a href="#activityTables-4"> [@s.text name="planning.activityList.themeLedActivities" ] [@s.param] ${currentUser.leader.theme.code} [/@s.param] [/@s.text] </a></li>
        [#elseif currentUser.RPL ]
          <li><a href="#activityTables-4"> [@s.text name="planning.activityList.regionLedActivities" ] [@s.param] ${currentUser.leader.region.name} [/@s.param] [/@s.text] </a></li>
        [/#if]
      </ul>

      [#-- Current activities --]      
      <div id="activityTables-1" class="activityTable">
        
        [#if currentActivities?has_content]
          [@activityList.activitiesList activities=currentActivities canValidate=true canEditActivity=true tableID="currentActivities" /]
          
          [#-- If the workplan hasn't been submitted yet, show the button --]
          [#if !workplanSubmitted]
            [#if !currentUser.PI ]
              <div id="submitButtonBlock" class="buttons">
                [#if canSubmit]
                  [@s.form action="activities" id="submitForm" ]
                    [@s.submit type="button" name="save" method="submit" cssClass="test" ][@s.text name="form.buttons.submit" /][/@s.submit] 
                  [/@s.form]  
                [#else]
                  <button id="submitForm_save" class="disabled" title="[@s.text name="submit.disabled" /]"> [@s.text name="form.buttons.submit" /] </button>
                [/#if]
              </div>
            [/#if]
          [#else]
            <div id="submitButtonBlock" class="buttons">
              <img src="${baseUrl}/images/global/icon-complete.png" /> [@s.text name="submit.submitted" /]
            </div>
          [/#if]
        
        [#else]
          <div class="noActivities">
            [@s.text name="planning.activityList.empty" /]
          </div>
        [/#if]
        
        [#-- Show the Add activity button if the workplan hasn't been submitted yet--]
        [#if !workplanSubmitted]
          <span id="addActivity">
            <a href=" [@s.url action='addActivity' includeParams='get'] [@s.param name='${activityYearRequest}']${currentYear?c}[/@s.param] [/@s.url]" >
             [@s.text name="planning.activityList.addActivity" /]
            </a>
          </span>
        [/#if]
      </div>
  
      [#-- Future activities --]
      [#if futurePlanningActive]
        <div id="activityTables-2" class="activityTable">
          <div id="futureActivities">
            <ul>
              [#list futureActivities?keys as year]
                <li><a href="#futureActivities-${year_index+1}"> ${year?c} </a></li>
              [/#list]
            </ul>
            [#list futureActivities?keys as year]
              [#if futureActivities.get(year)?has_content]
                <div id="futureActivities-${year_index+1}">
                  [#assign listOfActivities = futureActivities.get(year)]
                  [@activityList.activitiesList activities=listOfActivities canValidate=false canEditActivity=true tableID="futureActivities" /]                
                  
                  [#-- Add activity button --]
                  [#if !workplanSubmitted && !currentUser.PI]
                    <div id="addActivity">
                      <a href=" [@s.url action='addActivity' includeParams='get'] [@s.param name='${activityYearRequest}']${year?c}[/@s.param] [/@s.url]" >
                        [@s.text name="planning.activityList.addActivity" /]
                      </a>
                    </div>
                  [/#if]
                </div>
              [#else]
                <div id="futureActivities-${year_index+1}">
                  <div class="noActivities">
                    [@s.text name="planning.activityList.empty" /]
                  </div>
                  
                  [#-- Add activity button --]
                  [#if !workplanSubmitted && !currentUser.PI]
                    <div id="addActivity">
                      <a href=" [@s.url action='addActivity' includeParams='get'] [@s.param name='${activityYearRequest}']${year?c}[/@s.param] [/@s.url]" >
                        [@s.text name="planning.activityList.addActivity" /]
                      </a>
                    </div>
                  [/#if]
                </div>
              [/#if]
            [/#list]
          </div>
        </div>
      [/#if]
          
      [#-- Previous activities --]
      <div id="activityTables-3" class="activityTable">
          <div id="previousActivities">
            <ul>
              [#list previousActivities?keys as year]
                [#if previousActivities.get(year)?has_content]
                  <li><a href="#previousActivities-${year_index+1}"> ${year?c} </a></li>
                [/#if]
              [/#list]
            </ul>
            [#list previousActivities?keys as year]
              [#if previousActivities.get(year)?has_content]
                <div id="previousActivities-${year_index+1}">
                  [#assign listOfActivities = previousActivities.get(year)]
                  [@activityList.activitiesList activities=listOfActivities canValidate=false canEditActivity=false owned=false /]                
                </div>
              [/#if]
            [/#list]
          </div>
      </div>

      [#-- Related activities --]      
      [#if currentUser.TL || currentUser.RPL ]
        <div id="activityTables-4" class="activityTable">
          [#if relatedActivities?has_content]
            [@activityList.activitiesList activities=relatedActivities canValidate=false canEditActivity=true owned=false /]
          [#else]
            <div class="noActivities">
              [@s.text name="planning.activityList.empty" /]
            </div>
          [/#if]
        </div>
      [/#if]
      
    </div>
    
    <input type="hidden" id="beforeSubmitMessage" value="[@s.text name="submit.beforeSubmit.message" /]" />
    
    <div class="clearfix"></div>
  </article>
  </section>
[#include "/WEB-INF/global/pages/footer.ftl"]