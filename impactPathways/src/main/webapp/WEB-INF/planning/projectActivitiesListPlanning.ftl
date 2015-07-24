[#ftl]
[#assign title = "Project Activities" /]
[#assign globalLibs = ["jquery", "noty","autoSave", "dataTable", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/usersManagement.js", "${baseUrl}/js/planning/activity-list.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "activities" /]
[#assign currentStage = "activities" /]
[#assign currentSubStage = "activities" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"planning", "action":"projectsList"},
  {"label":"activities", "nameSpace":"planning/projects", "action":"activities", "param":"projectID=${projectID}" }
]/]

[#assign params = {
  "activities": {"id":"activitiesName", "name":"activities"}
  }
/] 

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
[#import "/WEB-INF/global/macros/usersPopup.ftl" as usersForm/]
[#import "/WEB-INF/planning/macros/projectActivitiesTemplate.ftl" as activitiesForms /]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>
      [@s.text name="planning.activities.help1" /] 
      <a href="[@s.url namespace="/" action='glossary'][/@s.url]#activity">[@s.text name="planning.activities.activities" /]</a>
      [@s.text name="planning.activities.help2" /] 
      <a href="[@s.url namespace="/" action='glossary'][/@s.url]#activity">[@s.text name="planning.activities.activityLeader" /]</a> 
    </p>
    <p>[@s.text name="planning.activities.help3" /]</p>
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
    <article class="halfContent" id="activities">
      [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
      [#if !canEdit]
        <p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]</p>
      [/#if]
      [@s.form action="activities" cssClass="pure-form"]
      <div id="activitiesList" class="">
        <h1 class="contentTitle">[@s.text name="planning.activities.title" /]</h1> 
        [#-- Validating amount of activities to be listed --]
        [#if activities?size > 0]
          [#list activities as activity] 
            [#-- Activity --]
            [@activitiesForms.activityMacro activity=activity activity_name=params.activities.name activity_index=activity_index editable=editable canEdit=canEdit /]
          [/#list] 
        [#else]
          [#if editable]
            <p>[@s.text name="planning.activities.message.empty" /] [@s.text name="planning.activities.message.addNew" /]</p>
            <div class="buttons">[@s.submit type="button" name="add"][@s.text name="planning.activities.button.add" /][/@s.submit]</div>
          [#else]
            <p>[@s.text name="planning.activities.message.empty" /]</p>
          [/#if]
        [/#if]
      </div><!-- End Activities list -->
      [#-- Add activity button --]
      [#if editable && canEdit]
        <div id="activities_add" class="addLink"><a href="" class="addButton">[@s.text name="planning.activities.button.add"/]</a></div>
      [/#if]
      [#if editable]
      <div class="borderBox">
        [#-- Project identifier --]
        <input name="projectID" type="hidden" value="${projectID?c}" />
        [@customForm.textArea name="justification" i18nkey="saving.justification" required=true className="justification"/]
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
    [/@s.form]  
    </article>
</section>

[#-- Internal parameters --]   
[#list params?keys as prop]<input id="${params[prop].id}" type="hidden" value="${params[prop].name}" />[/#list]

[#-- Search users Interface Popup --]
[@activitiesForms.activityMacro activity={} activity_index="0" template=true/]

[#-- Search users Interface Popup --]
[@usersForm.searchUsers isActive=false/]

[#include "/WEB-INF/global/pages/footer.ftl"]