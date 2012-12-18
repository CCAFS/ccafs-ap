[#ftl]
[#assign title = "Activity deliverables Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/reporting/deliverablesReporting.js"] /]
[#assign customCSS = ["${baseUrl}/css/reporting/deliverablesReporting.css"] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "activities" /]
[#assign currentStage = "deliverables" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

[#macro deliverableSection isExpected]
  [#list activity.deliverables as deliverable]
    [#if deliverable.expected == isExpected]
      <div id="deliverable-${deliverable_index}">
        <input name="activity.deliverables[${deliverable_index}].id" type="hidden" value="${deliverable.id}" />
        [#-- Adding remove link only for new deliverables --]
        [#if !deliverable.expected]
          <div class="removeLink">
              <a id="removeDeliverable-${deliverable_index}" href="" class="removeDeliverable">Remove deliverable</a>
          </div>
        [/#if]
        
        [#-- Description --]
        <div class="fullBlock">
          <h6>[@s.text name="reporting.activityDeliverables.description" /]</h6>
          [#if deliverable.expected]
            <p>${activity.deliverables[deliverable_index].description}</p>
          [#else]
            [@customForm.input name="selectedDeliverable" value="${deliverable.description}" type="text" i18nkey="reporting.activityDeliverables.description" /]                
          [/#if]    
        </div>
        
        [#-- Type --]
        <div class="thirdPartBlock">
          [#if deliverable.expected]
            <h6>[@s.text name="reporting.activityDeliverables.type" /]</h6>
            <p>${activity.deliverables[deliverable_index].type.name}</p>
          [#else]
            [@customForm.select name="selectedType" label="" i18nkey="reporting.activityDeliverables.type" listName="deliverableTypesList" headerValue="Select a deliverable type" keyFieldName="id"  displayFieldName="name" /]
          [/#if]
        </div>
        
        [#-- Year --]
        <div class="thirdPartBlock">
          [#if deliverable.expected]
            <h6>[@s.text name="reporting.activityDeliverables.year" /]</h6>
            <p>${activity.deliverables[deliverable_index].year?c}</p>
          [#else]
            [@customForm.input name="selectedYear" type="text" i18nkey="reporting.activityDeliverables.year" disabled=true  /]
          [/#if]
        </div>
        
        [#-- Status --]
        <div class="thirdPartBlock">
          [@customForm.select name="selectedStatus" label="" i18nkey="reporting.activityDeliverables.deliverableStatus" listName="deliverableStatusList" keyFieldName="id"  displayFieldName="name" value="${activity.deliverables[deliverable_index].status.id}" /]
        </div>
        
        [#-- Formats --]
        [#if deliverableTypeIdsNeeded?seq_contains(activity.deliverables[deliverable_index].type.id)]              
          <div class="fullBlock">
            <h6>[@s.text name="reporting.activityDeliverables.formatFiles" /]</h6>
            <div class="checkboxGroup">
              [#list fileFormatsList as fileFormat]
                [#if activity.deliverables[deliverable_index].fileFormatsIds?seq_contains(fileFormat.id)]
                  [@customForm.checkbox name="${fileFormat.id}" i18nkey="${fileFormat.name}" checked=true /]
                [#else]
                  [@customForm.checkbox name="${fileFormat.id}" i18nkey="${fileFormat.name}" /]
                [/#if]
              [/#list]
            </div>
          </div>
        [/#if]
        
      </div> <!-- End deliverable-${deliverable_index} -->
      <hr />
    [/#if]
  [/#list]
[/#macro]
  <section>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  [@s.form action="deliverables!save"]
  <article class="halfContent">
    <h1>
      ${activity.leader.acronym} - [@s.text name="reporting.activityList.activity" /] ${activity.id}      
    </h1>
    
    <h6>[@s.text name="reporting.activityStatus.title" /]</h6>
    <p>${activity.title}</p>
    
    <fieldset id="expectedDeliverablesGroup">
      <legend>
        <h5>[@s.text name="reporting.activityDeliverables.expectedDeliverables" /]</h5>
      </legend>
      [#-- Listing expected deliverables (see macro above) --]
      [@deliverableSection isExpected=true /]         
    </fieldset>
    
    <fieldset id="newDeliverablesGroup">
      <legend>
        <h5>New deliverables</h5>
      </legend>
           
      [#-- Listing new deliverables (see macro above) --]
      [@deliverableSection isExpected=false /]
      
      <div>
        <a href="" class="addDeliverable">Add deliverable</a>
      </div>
    </fieldset>
    
    <h1>----------------------------------------------</h1>
    <div id="deliverableTemplate">
    <!-- div id="deliverableTemplate" style="display: none" -->
      <div class="removeLink">
        <a href="" class="removeDeliverable">Remove deliverable</a>
      </div>
      <div class="fullBlock">
        [@customForm.input name="selectedDeliverable" value="deliverable.description" type="text" i18nkey="reporting.activityDeliverables.description" disabled=true  /]
      </div>
      <div class="thirdPartBlock">
        [@customForm.select name="selectedType" label="" i18nkey="reporting.activityDeliverables.type" listName="deliverableTypesList" headerValue="Select a deliverable type" keyFieldName="id"  displayFieldName="name"  /]
      </div>
      <div class="thirdPartBlock">
        [@customForm.input name="selectedYear" type="text" i18nkey="reporting.activityDeliverables.deliverableYear" disabled=true  /]
      </div>
      <div class="thirdPartBlock">
        [@customForm.select name="selectedStatus" label="" i18nkey="reporting.activityDeliverables.deliverableStatus" listName="deliverableStatusList" keyFieldName="id"  displayFieldName="name"  /]
      </div>
      <input name="expected" type="hidden" value="0" />
      <div class="fullBlock">
            <h6>[@s.text name="reporting.activityDeliverables.formatFiles" /]</h6>
            <div class="checkboxGroup">
              [@s.iterator value="fileFormatsList" var="fileFormat"]
                [@customForm.checkbox name="${fileFormat.id}" i18nkey="${fileFormat.name}" /]
              [/@s.iterator]
            </div>
          </div>
      <hr />
    </div>
    <!-- internal parameter -->
    <input name="activityID" type="hidden" value="${activity.id}" />
    [@s.submit type="button" name="save"]SAVE[/@s.submit]
         
    [#include "/WEB-INF/reporting/reportingStepSubMenu.ftl" /]  
    </article>
  [/@s.form]
 
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]