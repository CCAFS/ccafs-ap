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
    
  <section>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  [@s.form action="deliverables!save"]
  <article class="halfContent">
    <h1>
      ${activity.leader.acronym} - [@s.text name="reporting.activityList.activity" /] ${activity.id}      
    </h1>
    
    <h6>[@s.text name="reporting.activityStatus.title" /]</h6>
    <p> ${activity.title} </p>
    
    <fieldset>
      <legend> <h5> [@s.text name="reporting.activityDeliverables.expectedDeliverables" /] </h5> </legend>
      
      [#assign statusSelectHeadValue ] [@s.text name="reporting.activityDeliverables.statusSelect" /] [/#assign]
      
      [@s.set var="plannedDeliverables" value="deliverables.{?#this.expected == 1}" /]
      
      [#if plannedDeliverables.size() > 0]
        [@s.iterator value="plannedDeliverables" var="deliverable"]
          <div>
            <div>
              <a href="" class="removeDeliverable">Remove deliverable</a>
            </div>
            <div>
              <h6>[@s.text name="reporting.activityDeliverables.type" /]</h6>
              <p>${deliverable.type.name}</p>
            </div>
            <div>
              <h6>[@s.text name="reporting.activityDeliverables.description" /]</h6>
              <p>${deliverable.description}</p>
            </div>
            <div>
              <h6>[@s.text name="reporting.activityDeliverables.year" /]</h6>
              
              <p>${deliverable.year?c}</p>
            </div>            
            [@customForm.select name="selectedStatus" label="" i18nkey="reporting.activityDeliverables.deliverableStatus" listName="deliverableStatusList" headerValue="${statusSelectHeadValue}" keyFieldName="id"  displayFieldName="name" value="${deliverable.status.id}" /]
            [@s.if test="%{ #deliverable.type.id in typesFileFormatNeeded}"]
              [#if deliverable.fileFormats??]                
                [@customForm.checkboxGroup label="" name="selectedFormat" listName="fileFormatsList" value="deliverable.fileFormats.{? #this.id}" displayFieldName="name" keyFieldName="id" i18nkey="reporting.activityDeliverables.formatFiles" /]
              [/#if]
            [/@s.if]
          </div>
        [/@s.iterator]
      [#else]
        <p> [@s.text name="reporting.activityDeliverables.noPlannedDeliverables" /] </p>
      [/#if]      
    </fieldset>
    
    <fieldset>
      <legend> <h5> New deliverables </h5> </legend>
      
      [@s.set var="newDeliverables" value="deliverables.{?#this.expected == 0}" /]
      
      [#if newDeliverables.size() > 0]
        [@s.iterator value="newDeliverables" var="deliverable" status="stat"]
          <div id="reportingDeliverable${stat.count}" class="cloned">
            <div>
              <a href="" class="removeDeliverable">Remove deliverable</a>
            </div>
            [@customForm.select name="selectedType" label="" i18nkey="reporting.activityDeliverables.type" listName="deliverableTypesList" headerValue="Select a deliverable type" keyFieldName="id"  displayFieldName="name"  /]
            [@customForm.input name="selectedDeliverable" value="${deliverable.description}" type="text" i18nkey="reporting.activityDeliverables.description" disabled=true  /]
            [@customForm.input name="selectedYear" value="${deliverable.year?c}" type="text" i18nkey="reporting.activityDeliverables.deliverableYear" disabled=true  /]
            [@customForm.select name="selectedStatus" label="" i18nkey="reporting.activityDeliverables.deliverableStatus" listName="deliverableStatusList" headerValue="${statusSelectHeadValue}" keyFieldName="id"  displayFieldName="name"  /]
            <input name="expected" type="hidden" value="0" />
            [@s.if test="%{ #deliverable.type.id in typesFileFormatNeeded}"]            
              [@customForm.checkboxGroup label="" name="selectedFormat" listName="fileFormatsList" displayFieldName="name" keyFieldName="id" i18nkey="reporting.activityDeliverables.formatFiles" /]
            [/@s.if]
          </div>
        [/@s.iterator]
      [#else]
        <div id="reportingDeliverable1" class="cloned">
          [@customForm.input name="selectedDeliverable" value="" type="text" i18nkey="reporting.activityDeliverables.description" /]
          [@customForm.select name="selectedType" label="The fucking" i18nkey="reporting.activityDeliverables.type" listName="deliverableTypesList" headerValue="Select a deliverable type" keyFieldName="id"  displayFieldName="name"  /]
          [@customForm.input name="selectedYear" value="" type="text" i18nkey="reporting.activityDeliverables.deliverableYear" /]
          [@customForm.select name="selectedStatus" label="" i18nkey="reporting.activityDeliverables.deliverableStatus" listName="deliverableStatusList" headerValue="${statusSelectHeadValue}" keyFieldName="id"  displayFieldName="name"  /]
          <input name="expected" type="hidden" value="0" />
          [@customForm.checkboxGroup label="" name="selectedFormat" listName="fileFormatsList" displayFieldName="name" keyFieldName="id" i18nkey="reporting.activityDeliverables.formatFiles" /]          
        </div>
      [/#if]
      <div>
        <a href="" class="addDeliverable">Add deliverable</a>
      </div>
    </fieldset>
    
    <div id="reportingDeliverable">
      [@customForm.select name="selectedType" label="" i18nkey="reporting.activityDeliverables.type" listName="deliverableTypesList" headerValue="Select a deliverable type" keyFieldName="id"  displayFieldName="name"  /]
      [@customForm.input name="selectedDeliverable" value="deliverable.description" type="text" i18nkey="reporting.activityDeliverables.description" disabled=true  /]
      [@customForm.input name="selectedYear" type="text" i18nkey="reporting.activityDeliverables.deliverableYear" disabled=true  /]
      [@customForm.select name="selectedStatus" label="" i18nkey="reporting.activityDeliverables.deliverableStatus" listName="deliverableStatusList" headerValue="${statusSelectHeadValue}" keyFieldName="id"  displayFieldName="name"  /]
      <input name="expected" type="hidden" value="0" />
      [@customForm.checkboxGroup label="" name="selectedFormat" listName="fileFormatsList" displayFieldName="name" keyFieldName="id" i18nkey="reporting.activityDeliverables.formatFiles" /]      
    </div>    
    <!-- internal parameter -->
    <input name="activityID" type="hidden" value="${activity.id}" />
    [@s.submit type="button" name="save"]SAVE[/@s.submit]
         
    [#include "/WEB-INF/reporting/reportingStepSubMenu.ftl" /]  
    </article>
  [/@s.form]
 
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]