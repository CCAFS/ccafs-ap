[#ftl]
[#assign title = "Activity deliverables Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/reporting/deliverablesReporting.js"] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "activities" /]
[#assign currentStage = "deliverables" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

[#macro deliverableSection isExpected]
  [#list activity.deliverables as deliverable]
    [#if deliverable.expected == isExpected]
      <div id="deliverable-${deliverable_index}" class="deliverable">
        [#-- identifier --]
        <input name="activity.deliverables[${deliverable_index}].id" type="hidden" value="${deliverable.id?c}" />
        
        [#-- Adding remove link only for new deliverables --]
        [#if !deliverable.expected]
          <div class="removeLink">
              <img src="${baseUrl}/images/global/icon-remove.png" />
              <a id="removeDeliverable-${deliverable_index}" href="" class="removeDeliverable">
                [@s.text name="reporting.activityDeliverables.removeDeliverable" /]
              </a>
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
            [@customForm.select name="activity.deliverables[${deliverable_index}].type" label="" i18nkey="reporting.activityDeliverables.type" listName="deliverableTypesList" keyFieldName="id"  displayFieldName="name" className="deliverableType" /]
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
              [@s.fielderror cssClass="fieldError" fieldName="activity.deliverables[${deliverable_index}].fileFormats"/]
              [@s.checkboxlist name="activity.deliverables[${deliverable_index}].fileFormats" list="fileFormatsList" listKey="id" listValue="name" value="activity.deliverables[${deliverable_index}].fileFormatsIds" cssClass="checkbox" /]
            </div>
          </div>
        [/#if]
        
        [#-- File name --]
        <div class="fullBlock">
          [@customForm.input name="activity.deliverables[${deliverable_index}].fileName" type="text" i18nkey="reporting.activityDeliverables.filename" /]
        </div>
      </div> <!-- End deliverable-${deliverable_index} -->
      <hr />
    [/#if]
  [/#list]
[/#macro]
<section class="content">
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  [@s.form action="deliverables"]
  <article class="halfContent">
    <h1 class="contentTitle">
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
          <h5>[@s.text name="reporting.activityDeliverables.newDeliverable" /]</h5>
        </legend>             
        [#-- Listing new deliverables (see macro above) --]
        [@deliverableSection isExpected=false /]
      
        <div id="addDeliverableBlock" class="addLink">
          <img src="${baseUrl}/images/global/icon-add.png" />
          <a href="" class="addDeliverable" >[@s.text name="reporting.activityDeliverables.addDeliverable" /]</a>
        </div>
              
      </fieldset>
    </div> <!-- End items -->
    
    <!-- DELIVERABLE TEMPLATE -->
    <div id="template">
      <div id="deliverable-9999" class="deliverable" style="display: none;">      
        [#-- remove link --]      
        <div class="removeLink">            
            <img src="${baseUrl}/images/global/icon-remove.png" />
            <a id="removeDeliverable-9999" href="" class="removeDeliverable">
              [@s.text name="reporting.activityDeliverables.removeDeliverable" /]
            </a>
        </div>
        
        [#-- identifier --]
        <input name="id" type="hidden" value="-1" />
        
        [#-- Description --]
        <div class="fullBlock">                      
          [@customForm.textArea name="description" i18nkey="reporting.activityDeliverables.description" /]
        </div>
        
        [#-- Type --]
        <div class="thirdPartBlock">        
          [@customForm.select name="type" i18nkey="reporting.activityDeliverables.type" listName="deliverableTypesList" keyFieldName="id"  displayFieldName="name" className="deliverableType" /]
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
          <h6>[@s.text name="reporting.activityDeliverables.formatFiles" /]</h6>          
          <div class="checkboxGroup">                        
            [@s.checkboxlist name="fileFormats" list="fileFormatsList" listKey="id" listValue="name" cssClass="checkbox" /]
          </div>
        </div>
        
        [#-- File name --]
        <div class="fullBlock">
          [@customForm.input name="fileName" type="text" i18nkey="reporting.activityDeliverables.filename" /]
        </div>
              
      </div> <!-- End deliverable template -->
    </div> <!-- End template -->
    
    <!-- internal parameter -->
    <input name="activityID" type="hidden" value="${activity.id}" />
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
         
    [#include "/WEB-INF/reporting/activitiesReportingSubMenu.ftl" /]  
    </article>
  [/@s.form]
 
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]