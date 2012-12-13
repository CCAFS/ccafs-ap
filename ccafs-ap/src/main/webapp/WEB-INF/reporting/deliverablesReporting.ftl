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
    
    <fieldset id="expectedDeliverables">
      <legend> <h5> [@s.text name="reporting.activityDeliverables.expectedDeliverables" /] </h5> </legend>
      
      [#assign statusSelectHeadValue ] [@s.text name="reporting.activityDeliverables.statusSelect" /] [/#assign]
      
      [@s.set var="plannedDeliverables" value="deliverables.{?#this.expected == 1}" /]
      
      [#if plannedDeliverables.size() > 0]
        [@s.iterator value="plannedDeliverables" var="deliverable" status="stat"]
          <div id="expectedDeliverable-${stat.count}">
            <div class="removeLink">
              <a href="" class="removeDeliverable">Remove deliverable</a>
            </div>
            <div class="fullBlock">
              <h6>[@s.text name="reporting.activityDeliverables.description" /]</h6>
              <p>${deliverable.description}</p>
            </div>
            <div class="thirdPartBlock">
              <h6>[@s.text name="reporting.activityDeliverables.type" /]</h6>
              <p>${deliverable.type.name}</p>
            </div>            
            <div class="thirdPartBlock">
              <h6>[@s.text name="reporting.activityDeliverables.year" /]</h6>              
              <p>${deliverable.year?c}</p>
            </div>
            <div class="thirdPartBlock">
              [@customForm.select name="selectedStatus" label="" i18nkey="reporting.activityDeliverables.deliverableStatus" listName="deliverableStatusList" headerValue="${statusSelectHeadValue}" keyFieldName="id"  displayFieldName="name" value="${deliverable.status.id}" /]
            </div>
            [@s.if test="%{ #deliverable.type.id in typesFileFormatNeeded}"]
              [#if deliverable.fileFormats??]
                <div class="fullBlock">
                  <h6>[@s.text name="reporting.activityDeliverables.formatFiles" /]</h6>
                  <div class="checkboxGroup">
                    [@s.iterator value="fileFormatsList" var="fileFormat"]
                      [@customForm.checkbox name="chkName" id="${fileFormat.id}" i18nkey="${fileFormat.name}" /]
                    [/@s.iterator]
                  </div>
                </div>                  
              [/#if]
            [/@s.if]
            <hr />
          </div>
        [/@s.iterator]
      [#else]
        <p> [@s.text name="reporting.activityDeliverables.noPlannedDeliverables" /] </p>
      [/#if]      
    </fieldset>
    
    <fieldset id="newDeliverables">
      <legend> <h5> New deliverables </h5> </legend>
      
      [@s.set var="newDeliverables" value="deliverables.{?#this.expected == 0}" /]
      
      [#if newDeliverables.size() > 0]
        [@s.iterator value="newDeliverables" var="deliverable" status="stat"]
          <div id="reportingDeliverable${stat.count}" class="cloned">
            <div class="removeLink">
              <a href="" class="removeDeliverable">Remove deliverable</a>
            </div>
            <div class="fullBlock">      
              [@customForm.input name="selectedDeliverable" value="${deliverable.description}" type="text" i18nkey="reporting.activityDeliverables.description" disabled=true  /]
            </div>
            <div class="thirdPartBlock">
              [@customForm.select name="selectedType" label="" i18nkey="reporting.activityDeliverables.type" listName="deliverableTypesList" headerValue="Select a deliverable type" keyFieldName="id"  displayFieldName="name"  /]
            </div>
            <div class="thirdPartBlock">
              [@customForm.input name="selectedYear" value="${deliverable.year?c}" type="text" i18nkey="reporting.activityDeliverables.deliverableYear" disabled=true  /]
            </div>
            <div class="thirdPartBlock">
              [@customForm.select name="selectedStatus" label="" i18nkey="reporting.activityDeliverables.deliverableStatus" listName="deliverableStatusList" headerValue="${statusSelectHeadValue}" keyFieldName="id"  displayFieldName="name"  /]
            </div>              
            <input name="expected" type="hidden" value="0" />
            [@s.if test="%{ #deliverable.type.id in typesFileFormatNeeded}"]
              <div class="fullBlock">
                <h6>[@s.text name="reporting.activityDeliverables.formatFiles" /]</h6>
                <div class="checkboxGroup">
                  [@s.iterator value="fileFormatsList" var="fileFormat"]
                    [@customForm.checkbox name="chkName" id="${fileFormat.id}" i18nkey="${fileFormat.name}" /]
                  [/@s.iterator]
                </div>
              </div>  
            [/@s.if]
            <hr />
          </div>
        [/@s.iterator]
      [#else]
        <div id="reportingDeliverable1" class="cloned">
          <div class="removeLink">
            <a href="" class="removeDeliverable">Remove deliverable</a>
          </div>
          <div class="fullBlock">
            [@customForm.input name="selectedDeliverable" value="" type="text" i18nkey="reporting.activityDeliverables.description" /]
          </div>
          <div class="thirdPartBlock">
            [@customForm.select name="selectedType" label="" i18nkey="reporting.activityDeliverables.type" listName="deliverableTypesList" headerValue="Select a deliverable type" keyFieldName="id"  displayFieldName="name"  /]
          </div>
          <div class="thirdPartBlock">
            [@customForm.input name="selectedYear" value="" type="text" i18nkey="reporting.activityDeliverables.deliverableYear" /]
          </div>
          <div class="thirdPartBlock">
            [@customForm.select name="selectedStatus" label="" i18nkey="reporting.activityDeliverables.deliverableStatus" listName="deliverableStatusList" headerValue="${statusSelectHeadValue}" keyFieldName="id"  displayFieldName="name"  /]
          </div>            
          <input name="expected" type="hidden" value="0" />
          <div class="fullBlock">
            <h6>[@s.text name="reporting.activityDeliverables.formatFiles" /]</h6>
            <div class="checkboxGroup">
              [@s.iterator value="fileFormatsList" var="fileFormat"]
                [@customForm.checkbox name="chkName" id="${fileFormat.id}" i18nkey="${fileFormat.name}" /]
              [/@s.iterator]
            </div>
          </div>          
          <hr />          
        </div>
      [/#if]
      <div>
        <a href="" class="addDeliverable">Add deliverable</a>
      </div>
    </fieldset>
    
    <div id="reportingDeliverable">
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
        [@customForm.select name="selectedStatus" label="" i18nkey="reporting.activityDeliverables.deliverableStatus" listName="deliverableStatusList" headerValue="${statusSelectHeadValue}" keyFieldName="id"  displayFieldName="name"  /]
      </div>
      <input name="expected" type="hidden" value="0" />
      <div class="fullBlock">
            <h6>[@s.text name="reporting.activityDeliverables.formatFiles" /]</h6>
            <div class="checkboxGroup">
              [@s.iterator value="fileFormatsList" var="fileFormat"]
                [@customForm.checkbox name="chkName" id="${fileFormat.id}" i18nkey="${fileFormat.name}" /]
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