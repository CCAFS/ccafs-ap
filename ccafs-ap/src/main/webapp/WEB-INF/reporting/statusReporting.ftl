[#ftl]
[#assign title = "Activity Status Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/reporting/statusReporting.js", "${baseUrl}/js/global/utils.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/jqueryUI/jquery-ui-1.9.2.custom.css", ""] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "activities" /]
[#assign currentStage = "status" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]


[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="reporting.activityStatus.help" /]</p>
  </div>    
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  [@s.form action="status"]
  <article class="halfContent">
    [#include "/WEB-INF/reporting/activitiesReportingSubMenu.ftl" /]
    [#if activity.commissioned] 
      <h1>${activity.leader.acronym}: [@s.text name="planning.mainInformation.commissionedActivity" /] ${activity.activityId} </h1>
    [#else] 
      <h1>${activity.leader.acronym}: [@s.text name="reporting.activityList.activity" /] ${activity.activityId} </h1>
    [/#if]
    
    <fieldset>
      <div id="activityTitle" class="fullBlock">
        <h6>[@s.text name="reporting.activityStatus.title" /]</h6>
        <p>${activity.title}</p>
      </div>
      
      <div id="activityDescription" class="fullBlock">
        <h6>[@s.text name="reporting.activityStatus.description" /]</h6>
        <p> [#if activity.description?has_content] ${activity.description} [/#if]</p>
      </div>
      
      <table id="generalInformation"  class="fullBlock">
        <tbody>
          <tr>
            <td class="title">[@s.text name="reporting.activityStatus.startDate" /]</td>
            <td>[#if activity.startDate?has_content]${activity.startDate?string("MM/dd/yyyy")}[/#if]</td>
            <td class="title">[@s.text name="reporting.activityStatus.budget" /]</td>
            <td>${activity.budget.usd}</td>
          </tr>
          <tr>
            <td class="title">[@s.text name="reporting.activityStatus.endDate" /]</td>
            <td>[#if activity.endDate?has_content]${activity.endDate?string("MM/dd/yyyy")}[/#if]</td>
            <td class="title">[@s.text name="reporting.activityStatus.milestone" /]</td>
            <td>
              <a class="popup" href="[@s.url action='milestone'][@s.param name='${milestoneRequestParameter}']${activity.milestone.id}[/@s.param][/@s.url]">
                ${activity.milestone.code}
              </a>
            </td>
          </tr>
          [#if activity.contactPersons?has_content]
            <tr>
              <td class="title">[@s.text name="reporting.activityStatus.contactPerson" /]</td>
              <td colspan="3">
                  ${activity.contactPersons[0].name}
                 [#if activity.contactPersons[0].email?has_content ]
                  (<a id="contactEmail" href="mailto: ${activity.contactPersons[0].email} ">${activity.contactPersons[0].email}</a>)
                 [/#if] 
                [#if activity.contactPersons?size > 1] <a id="viewMoreContacts" href="">, [@s.text name="reporting.activityStatus.contactPerson.showTable" /]</a> [/#if]
              </td>        
            </tr>
          [/#if]
        </tbody>
      </table>
      
      <div id="statusOptions" class="fullBlock">
        [@customForm.radioButtonGroup label="Activity Status" name="activity.status" listName="statusList" keyFieldName="id" displayFieldName="name" value="${activity.status.id}" /]        
      </div>
      
      <div id="statusDescription" class="fullBlock">
        [@customForm.textArea name="activity.statusDescription" i18nkey="reporting.activityStatus.statusDescription" required=true /]
      </div>
      
      <div id="gender">
        [#if hasGender]
          <div class="fullBlock">
            [#-- if the activity has gender integration the user won't be able to retract (All gender section must be disabled) --]
            [@customForm.radioButtonGroup label="Gender Integration" name="genderIntegrationOption" listName="genderOptions" disabled=true value="${hasGender?string('1', '0')}" /]
          </div>
          <div class="fullBlock">
            [@customForm.textArea name="activity.genderIntegrationsDescription" i18nkey="reporting.activityStatus.genderIntegrationDescription" required=true /]
          </div>
        [#else]
          <div class="fullBlock">
            [@customForm.radioButtonGroup label="Gender Integration" name="genderIntegrationOption" listName="genderOptions" value="${hasGender?string('1', '0')}" /]
          </div>
          <div id="genderIntegrationDescription" class="fullBlock" style="display: none; ">
            [@customForm.textArea name="activity.genderIntegrationsDescription" i18nkey="reporting.activityStatus.genderIntegrationDescription" /]
          </div>
        [/#if]
      </div>
      
      <div id="contactPersons" title="[@s.text name="reporting.activityStatus.contactPerson.table.title" /]">
        <table id="contactPersonsTable">
          <thead>
            <tr>
              <th>[@s.text name="reporting.activityStatus.contactPerson.table.name" /]</th>
              <th>[@s.text name="reporting.activityStatus.contactPerson.table.email" /]</th>
            </tr>
          </thead>
          <tbody>
            [#if activity.contactPersons?has_content ]
              [#list activity.contactPersons as contactPerson]    
                <tr>
                  <td>${contactPerson.name}</td>
                  <td>${contactPerson.email}</td>
                </tr>
              [/#list]    
            [/#if]
          </tbody>
        </table>
      </div>
    </fieldset>
    
    <!-- internal parameter -->    
    <input name="${activityIdParameter}" type="hidden" value="${activity.id?c}" />
    
    [#if canSubmit]
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
    [/#if]
      
  </article>
  
  [/@s.form]
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]