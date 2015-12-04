[#ftl]
[#assign title = "Project Next Users" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/projects/projectNextUsers.js"] /]
[#assign currentSection = cycleName?lower_case /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "outputs" /]
[#assign currentSubStage = "nextUsers" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projects", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"nextUsers", "nameSpace":"${currentSection}/projects", "action":"nextUsers", "param":"projectID=${projectID}" }
]/]

[#assign params = {
  "nextUsers": {"id":"nextUsersName", "name":"project.nextUsers"}
  }
/] 

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log /]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" /> <p> //TODO </p>
  </div>
  [#include "/WEB-INF/projects/projectsSubMenu.ftl" /]
    <article class="halfContent" id="projectNextUsers">
      [#include "/WEB-INF/projects/dataSheet.ftl" /]
      [#if submission?has_content]
        <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
      [#elseif !canEdit ]
        <p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]</p>
      [/#if]
      [@s.form action="nextUsers" cssClass="pure-form"]
      [#if project.startDate?? && project.endDate??]
        <h1 class="contentTitle">[@s.text name="reporting.projectNextUsers.title" /]</h1> 
        <div id="nextUsersList" class="simpleBox">
          [#-- Validating amount of projectNextUsers to be listed --]
          [#if project.nextUsers?? || (project.nextUsers?size > 3)!false] 
            [#-- TODO --]
          [#else]
            [@projectNextUsersMacro index="0" /]
            [@projectNextUsersMacro index="1" /]
            [@projectNextUsersMacro index="2" /]
          [/#if] 
        </div><!-- End projectNextUsers list -->
        [#-- Add projectNextUsers button --]
        [#if editable && canEdit]
          <div id="projectNextUsers_add" class="addLink"><a href="" class="addButton">[@s.text name="reporting.projectNextUsers.button.add"/]</a></div>
        [/#if]
        
        [#if editable]
        [#-- Project identifier --]
        <input name="projectID" type="hidden" value="${projectID?c}" /> 
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
      <br /><br /><p class="simpleBox center">[@s.text name="reporting.projectNextUsers.message.dateUndefined" /]</p>
    [/#if]
    
    [/@s.form]  
    </article>
</section>

[#-- Internal parameters --]   
[#list params?keys as prop]<input id="${params[prop].id}" type="hidden" value="${params[prop].name}" />[/#list]

[#-- projectNextUsers template --]
[@projectNextUsersMacro index="0" template=true/]

[#macro projectNextUsersMacro index="0" template=true]
<div class="borderBox">
  Next User ${index}
</div>
<div></div>
[/#macro]

[#include "/WEB-INF/global/pages/footer.ftl"]
