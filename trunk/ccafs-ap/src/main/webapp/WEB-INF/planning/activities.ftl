[#ftl]
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
    <h1>[#if currentUser.leader??]${currentUser.leader.name}[/#if] ([@s.text name="planning.activityList.activities" /] ${currentPlanningLogframe.year?c})</h1>   
    
    <div id="activityTables">
      <ul>
        <li><a href="#activityTables-1"> [@s.text name="planning.activityList.currentActivities" /] </a></li>
        <li><a href="#activityTables-2"> [@s.text name="planning.activityList.futureActivities" /] </a></li>
        <li><a href="#activityTables-3"> [@s.text name="planning.activityList.previousActivities" /] </a></li>
        [#if relatedActivities?has_content]
          <li><a href="#activityTables-4"> [@s.text name="planning.activityList.relatedActivities" /] </a></li>
        [/#if]
      </ul>

      [#-- Current activities --]      
      <div id="activityTables-1" class="activityTable">
        [#if workplanSubmitted]
          <p class="workplanSubmitted">[@s.text name="planning.activityList.workplanSubmitted" /]</p>
        [/#if]
        
        [#if currentActivities?has_content]
          [@activityList.activitiesList activities=currentActivities canValidate=true canEditActivity=true /]
        [#else]
          <div class="noActivities">
            [@s.text name="planning.activityList.empty" /]
          </div>
        [/#if]
        
        [#if !currentUser.PI ]
          [#-- If the workplan hasn't been submitted yet, show the button --]
          [#if !workplanSubmitted]
            <div id="submitButtonBlock" class="buttons">
              [#if canSubmit]
              [@s.form action="activities" ]
                [@s.submit type="button" name="save" method="submit" ][@s.text name="form.buttons.submit" /][/@s.submit] 
              [/@s.form]  
              [#else]
              <button class="disabled" title="[@s.text name="planning.activityList.submit.disabled" /]"> [@s.text name="form.buttons.submit" /] </button>
              [/#if]
            </div>
          [/#if]
        [/#if]
        
        [#-- Show the Add activity button if the workplan hasn't been submitted yet--]
        [#if !workplanSubmitted]
          <div id="addActivity">
            <a href=" [@s.url action='addActivity' includeParams='get'] [@s.param name='${activityYearRequest}']${currentYear?c}[/@s.param] [/@s.url]" >
              [@s.text name="planning.activityList.addActivity" /]
            </a>
          </div>
        [/#if]
      </div>
  
      [#-- Future activities --]
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
                [@activityList.activitiesList activities=listOfActivities canValidate=false canEditActivity=true /]                
                
                [#-- Add activity button --]
                <div id="addActivity">
                  <a href=" [@s.url action='addActivity' includeParams='get'] [@s.param name='${activityYearRequest}']${year?c}[/@s.param] [/@s.url]" >
                    [@s.text name="planning.activityList.addActivity" /]
                  </a>
                </div>
              </div>
            [#else]
              <div id="futureActivities-${year_index+1}">
                <div class="noActivities">
                  [@s.text name="planning.activityList.empty" /]
                </div>
                
                [#-- Add activity button --]
                <div id="addActivity">
                  <a href=" [@s.url action='addActivity' includeParams='get'] [@s.param name='${activityYearRequest}']${year?c}[/@s.param] [/@s.url]" >
                    [@s.text name="planning.activityList.addActivity" /]
                  </a>
                </div>
              </div>
            [/#if]
          [/#list]
          
        </div>
        
      </div>
          
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
      [#if relatedActivities?has_content]
        <div id="activityTables-4" class="activityTable">      
          [@activityList.activitiesList activities=relatedActivities canValidate=false canEditActivity=true owned=false /]
        </div>
      [/#if]
      
    </div>
    
    <div class="clearfix"></div>
  </article>
  </section>
[#include "/WEB-INF/global/pages/footer.ftl"]