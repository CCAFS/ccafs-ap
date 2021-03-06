[#ftl]
[#assign title = "Project Next Users" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/projects/projectNextUsers.js"] /] 
[#assign currentSection = cycleName?lower_case /]
[#assign currentCycleSection = "projects" /]
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
    <img src="${baseUrl}/images/global/icon-help.png" /><p>[@s.text name="${currentSection}.projectNextUsers.help" ][@s.param]<a href= [@s.url namespace="/" action='glossary'][/@s.url]>[@s.text name="planning.projectImpactPathways.help.glossary" /]</a>[/@s.param][/@s.text]</p>
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
        <div id="nextUsersBlock" class="">
          <div id="nextUsersList">
            [#-- Validating amount of projectNextUsers to be listed --]
            [#if (project.nextUsers?size >= 1)!false] 
              [#list project.nextUsers as nextuser]
                [@projectNextUsersMacro index="${nextuser_index}" /]
              [/#list]
            [#else]
              [#if !(project.bilateralProject && project.cofinancing)]
                [@projectNextUsersMacro index="0" /]
              [#else]
                <p class="simpleBox message center">There is not project next users yet.</p>
              [/#if]
            [/#if] 
          </div><!-- End projectNextUsers list -->
          [#-- Add projectNextUsers button --]
          [#if editable && canEdit]
            <div id="projectNextUsers_add" class="addLink"><a href="#" class="addButton">[@s.text name="reporting.projectNextUsers.button.add"/]</a></div>
          [/#if]
        </div>
        
        [#if editable]
        [#-- Project identifier --]
        <input name="projectID" type="hidden" value="${projectID?c}" /> 
        <div class="" >
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

[#macro projectNextUsersMacro index="-1" template=false]
  [#assign customName = "${params.nextUsers.name}[${index}]"/]
  [#assign customId = "nextUser-${template?string('template',index)}" /]
  [#assign display = "${template?string('none','block')}" /]
  [#assign element = (customName?eval)! /]
  
  <div id="${customId}" class="nextUser borderBox" style="display:${display}">
    [#-- Edit/Back/remove buttons --]
    [#if (!editable && canEdit)]
      <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#${customId}">[@s.text name="form.buttons.edit" /]</a></div>
    [#elseif canEdit]
        <div class="viewButton removeOption"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]#${customId}">[@s.text name="form.buttons.unedit" /]</a></div>
        <div class="removeElement" title="[@s.text name="reporting.projectNextUsers.removeNextUser" /]"></div>
    [/#if]
    [#-- Next user ID --]
    <input type="hidden" name="${customName}.id" class="nextUserID" value="${(element.id)!-1}"/>
    [#-- Next user index --]
    <div class="leftHead"><span class="index">${index?number+1}</span><span class="elementId">Next user </span></div>
    [#-- Key next user --]
    <div class="fullBlock">
      [@customForm.textArea name="${customName}.keyNextUser" i18nkey="reporting.projectNextUsers.keyNextUser" className="keyNextUser limitWords-200" required=true editable=editable /]
    </div>
    [#-- What strategies --]
    <div class="fullBlock">
      [@customForm.textArea name="${customName}.strategies" i18nkey="reporting.projectNextUsers.strategies" className="strategies limitWords-100" required=true editable=editable /]
    </div> 
    [#-- Select reported deliverables --]
    <div class="fullBlock">
      [@customForm.textArea name="${customName}.reportedDeliverables" i18nkey="reporting.projectNextUsers.reportedDeliverables" className="reportedDeliverables limitWords-100" required=true editable=editable /]
    </div> 
    [#-- Lessons and implications --]
    <div class="fullBlock">
      [@customForm.textArea name="${customName}.lessonsImplications" i18nkey="reporting.projectNextUsers.lessonsImplications" className="lessonsImplications limitWords-100" required=true editable=editable /]
    </div> 
  </div>
[/#macro]

[#-- Internal parameters --]
[#list params?keys as prop]<input id="${params[prop].id}" type="hidden" value="${params[prop].name}" />[/#list]

[#-- projectNextUsers template --]
[@projectNextUsersMacro template=true/]

[#include "/WEB-INF/global/pages/footer.ftl"]