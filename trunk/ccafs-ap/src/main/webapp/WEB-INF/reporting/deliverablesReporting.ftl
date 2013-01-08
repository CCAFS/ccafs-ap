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
      <div id="deliverable-${deliverable_index}" class="deliverable">
        <input name="activity.deliverables[${deliverable_index}].id" type="hidden" value="${deliverable.id}" />
        [#-- Adding remove link only for new deliverables --]
        [#if !deliverable.expected]
          <div class="removeLink">
              <a id="removeDeliverable-${deliverable_index}" href="" class="removeDeliverable">Remove deliverable</a>
          </div>
        [/#if]
        
        [#-- Description --]
        <div class="fullBlock">
          [#if deliverable.expected]
            <h6>[@s.text name="reporting.activityDeliverables.description" /]</h6>
            <p>${activity.deliverables[deliverable_index].description}</p>
          [#else]             
            [@customForm.textArea name="activity.deliverables[${deliverable_index}].description" i18nkey="reporting.activityDeliverables.description" /]
          [/#if]    
        </div>
        
        [#-- Type --]
        <div class="thirdPartBlock">
          [#if deliverable.expected]
            <h6>[@s.text name="reporting.activityDeliverables.type" /]</h6>
            <p>${activity.deliverables[deliverable_index].type.name}</p>
          [#else]
            [@customForm.select name="activity.deliverables[${deliverable_index}].type" label="" i18nkey="reporting.activityDeliverables.type" listName="deliverableTypesList" keyFieldName="id"  displayFieldName="name" /]
          [/#if]
        </div>
        
        [#-- Year --]
        <div class="thirdPartBlock">
          [#if deliverable.expected]
            <h6>[@s.text name="reporting.activityDeliverables.year" /]</h6>
            <p>${activity.deliverables[deliverable_index].year?c}</p>
          [#else]
            [@customForm.input name="activity.deliverables[${deliverable_index}].year" type="text" i18nkey="reporting.activityDeliverables.year" /]
          [/#if]
        </div>
        
        [#-- Status --]
        <div class="thirdPartBlock">
          [@customForm.select name="activity.deliverables[${deliverable_index}].status" label="" i18nkey="reporting.activityDeliverables.deliverableStatus" listName="deliverableStatusList" keyFieldName="id"  displayFieldName="name" value="${activity.deliverables[deliverable_index].status.id}" /]
        </div>
        
        [#-- Formats --]
        [#if deliverableTypeIdsNeeded?seq_contains(activity.deliverables[deliverable_index].type.id)]              
          <div class="fullBlock">
            <h6>[@s.text name="reporting.activityDeliverables.formatFiles" /]</h6>
            <div class="checkboxGroup">            
              [@s.checkboxlist name="activity.deliverables[${deliverable_index}].fileFormats" list="fileFormatsList" listKey="id" listValue="name" value="activity.deliverables[${deliverable_index}].fileFormatsIds" cssClass="checkbox" /]
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
  
  [@s.form action="deliverables"]
  <article class="halfContent">
    <h1>
      ${activity.leader.acronym} - [@s.text name="reporting.activityList.activity" /] ${activity.id}      
    </h1>
    
    <h6>[@s.text name="reporting.activityStatus.title" /]</h6>
    <p>${activity.title}</p>
    
    <div id="items">
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
      </fieldset>
      <div>
        <a href="" class="addDeliverable">Add deliverable</a>
      </div>
    </div> <!-- End deliverables -->
    
    <!-- DELIVERABLE TEMPLATE -->
    <div id="template">
      <div id="deliverable-9999" class="deliverable" style="display: none;">      
        [#-- remove link --]      
        <div class="removeLink">
            <a id="removeDeliverable-9999" href="" class="removeDeliverable">Remove deliverable</a>
        </div>
        
        [#-- Description --]
        <div class="fullBlock">                      
          [@customForm.textArea name="description" i18nkey="reporting.activityDeliverables.description" /]        
        </div>
        
        [#-- Type --]
        <div class="thirdPartBlock">        
          [@customForm.select name="type" i18nkey="reporting.activityDeliverables.type" listName="deliverableTypesList" keyFieldName="id"  displayFieldName="name" /]
        </div>
        
        [#-- Year --]
        <div class="thirdPartBlock">        
          [@customForm.input name="year" type="text" i18nkey="reporting.activityDeliverables.year" /]
        </div>
        
        [#-- Status --]
        <div class="thirdPartBlock">
          [@customForm.select name="status" label="" i18nkey="reporting.activityDeliverables.deliverableStatus" listName="deliverableStatusList" keyFieldName="id"  displayFieldName="name" value="3" /]
        </div>
        
        [#-- Formats --]                    
        <div class="fullBlock">          
          <div class="checkboxGroup">
            <h6>[@s.text name="reporting.activityDeliverables.formatFiles" /]</h6>
            [@s.checkboxlist name="fileFormats" list="fileFormatsList" listKey="id" listValue="name" cssClass="checkbox" /]
          </div>
        </div>      
      </div> <!-- End deliverable template -->
    </div> <!-- End template -->
    
    <!-- internal parameter -->
    <input name="activityID" type="hidden" value="${activity.id}" />
    [@s.submit type="button" name="save"]SAVE[/@s.submit]
    [@s.submit type="button" name="cancel"]CANCEL[/@s.submit]
         
    [#include "/WEB-INF/reporting/reportingStepSubMenu.ftl" /]  
    </article>
  [/@s.form]
 
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]