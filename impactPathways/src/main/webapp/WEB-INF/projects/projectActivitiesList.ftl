[#ftl]
[#assign title = "Project Activities" /]
[#assign globalLibs = ["jquery", "noty","autoSave", "dataTable", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/usersManagement.js", "${baseUrl}/js/projects/activity-list.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = cycleName?lower_case /]
[#assign currentCycleSection = "activities" /]
[#assign currentStage = "activities" /]
[#assign currentSubStage = "activities" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projects", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"activities", "nameSpace":"${currentSection}/projects", "action":"activities", "param":"projectID=${projectID}" }
]/]

[#assign params = {
  "activities": {"id":"activitiesName", "name":"project.activities"}
  }
/] 

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
[#import "/WEB-INF/global/macros/usersPopup.ftl" as usersForm/]
[#import "/WEB-INF/projects/macros/projectActivitiesTemplate.ftl" as activitiesForms /]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>
      [@s.text name="planning.activities.help1" /] 
      <a href="[@s.url namespace="/" action='glossary'][/@s.url]">[@s.text name="planning.activities.activities" /]</a>
      [@s.text name="planning.activities.help2" /] 
      <a href="[@s.url namespace="/" action='glossary'][/@s.url]">[@s.text name="planning.activities.activityLeader" /]</a> 
    </p>
    
  </div>
  [#include "/WEB-INF/projects/projectsSubMenu.ftl" /]
    <article class="halfContent" id="activities">
      [#include "/WEB-INF/projects/dataSheet.ftl" /]
      [#if submission?has_content]
        <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
      [#elseif !canEdit ]
        <p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]</p>
      [/#if]
      [@s.form action="activities" cssClass="pure-form"]
      [#if project.startDate?? && project.endDate??]
        <h1 class="contentTitle">[@s.text name="planning.activities.title" /]</h1> 
        <div id="" class="simpleBox">  
          <div id="activitiesList" class="">
            [#-- Validating amount of activities to be listed --]
            [#if project.activities?size > 0]
                [#list project.activities as activity] 
                  [#-- Activity --]
                  [@activitiesForms.activityMacro activity=activity activity_name=params.activities.name activity_index=activity_index editable=editable canEdit=canEdit /]
                [/#list] 
            [#else]
              <p class="emptyText simpleBox center">
              [#if editable]
                [@s.text name="planning.activities.message.addNew" /]
              [#else]
                [@s.text name="planning.activities.message.empty" /]
                [#if canEdit]
                  <a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.clickHere" /]</a> [@s.text name="planning.activities.message.switchEditingMode" /]
                [/#if]
              [/#if]
              </p>
            [/#if]
          </div><!-- End Activities list -->
          [#-- Add activity button --]
          [#if editable && canEdit && !reportingCycle]
            <div id="activities_add" class="addLink"><a href="" class="addButton">[@s.text name="planning.activities.button.add"/]</a></div>
          [/#if]
        </div>
        
        [#if !newProject]
        <div id="lessons" class="borderBox">
          [#if (!editable && canEdit)]
            <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#lessons">[@s.text name="form.buttons.edit" /]</a></div>
          [#else]
            [#if canEdit && !newProject]
              <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>
            [/#if]
          [/#if] 
          
          [#-- Lessons learnt from last planning/reporting cycle --]
          [#if (projectLessonsPreview.lessons?has_content)!false]
          <div class="fullBlock">
            <h6>[@customForm.text name="${currentSection}.activities.previousLessons" param="${reportingCycle?string(currentReportingYear,currentPlanningYear-1)}" /]:</h6>
            <div class="textArea "><p>${projectLessonsPreview.lessons}</p></div>
          </div>
          [/#if]
          [#-- Planning/Reporting lessons --]
          <div class="fullBlock">
            <input type="hidden" name="projectLessons.id" value=${(projectLessons.id)!"-1"} />
            <input type="hidden" name="projectLessons.year" value=${reportingCycle?string(currentReportingYear,currentPlanningYear)} />
            <input type="hidden" name="projectLessons.componentName" value="${actionName}">
            [@customForm.textArea name="projectLessons.lessons" i18nkey="${currentSection}.activities.lessons" required=!project.bilateralProject editable=editable /]
          </div>
          
        </div>
        [/#if]
        
        [#if editable]
        [#-- Project identifier --]
        <input name="projectID" type="hidden" value="${projectID?c}" />
        <input id="minDateValue" value="${project.startDate?string.yyyy}-01-01" type="hidden"/>
        <input id="maxDateValue" value="${project.endDate?string.yyyy}-12-31" type="hidden"/> 
        <div class="[#if !newProject]borderBox[/#if]" >
          [#if !newProject] [@customForm.textArea name="justification" i18nkey="saving.justification" required=true className="justification"/][/#if]
          <div class="buttons">
            [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
            [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
            [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
          </div>
        </div>
      [#else]
        [#-- Display Log History --]
        [#if history??][@log.logList list=history /][/#if] 
      [/#if]
      
    [#else]
    <br /><br />
      <p class="simpleBox center">[@s.text name="activities.message.dateUndefined" /]</p>
    [/#if]
    [/@s.form]  
    </article>
</section>

[#-- Internal parameters --]   
[#list params?keys as prop]<input id="${params[prop].id}" type="hidden" value="${params[prop].name}" />[/#list]

[#-- Activity template --]
[@activitiesForms.activityMacro activity={} activity_index="0" template=true/]

[#-- Search users Interface Popup --]
[@usersForm.searchUsers isActive=false/]

[#include "/WEB-INF/global/pages/footer.ftl"]
