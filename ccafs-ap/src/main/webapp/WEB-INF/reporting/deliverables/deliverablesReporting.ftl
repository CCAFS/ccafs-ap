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

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="reporting.activityDeliverables.help" /]</p>
  </div>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  [@s.form action="deliverables"]
  <article class="halfContent">
    [#include "/WEB-INF/reporting/deliverables/deliverablesReportingSubMenu.ftl" /]
    <h1 class="contentTitle">
      Algo: [@s.text name="reporting.activityList.activity" /] algo
    </h1>

    <div id="deliverableInformation">
      <div id="deliverableTitle">
        [@customForm.input name="deliverable.description" type="text" i18nkey="reporting.activityDeliverables.deliverableTitle" /]
      </div>

      <div id="deliverableTitle" class="halfPartBlock">
        <h6>[@s.text name="reporting.activityDeliverables.type" /]</h6>
        <span>${deliverable.type.parent.name}</span>
      </div>

      <div id="deliverableTitle" class="halfPartBlock">
        <h6>[@s.text name="reporting.activityDeliverables.subtype" /]</h6>
        <span>${deliverable.type.name}</span>
      </div>

      <div id="deliverableTitle" class="halfPartBlock">
        <h6>[@s.text name="reporting.activityDeliverables.year" /]</h6>
        <span>${deliverable.year}</span>
      </div>

      <div id="deliverableTitle" class="halfPartBlock">
        [@customForm.select name="deliverable.status" i18nkey="reporting.activityDeliverables.status" listName="deliverableStatusList" keyFieldName="id"  displayFieldName="name" className="deliverableStatus" /]
      </div>
      
      <div id="deliverableDescriptionUpdate">
        [@customForm.textArea name="deliverable.descriptionUpdate" i18nkey="reporting.activityDeliverables.descriptionUpdate" help="reporting.activityDeliverables.descriptionUpdate.help" /]
      </div>
    </div>
    
    <div id="trafficLightQuestions" >
      <h6>[@s.text name="reporting.activityDeliverables.trafficLight" /]</h6>
      <div>
        [@s.text name="reporting.activityDeliverables.trafficLight.metadataDocumented" /]
        [@s.radio name="deliverable.trafficLight.metadataDocumented" list="yesNoRadio" /]
      </div>
      
      <div>
        [@s.text name="reporting.activityDeliverables.trafficLight.collectionTools" /]
        [@s.radio name="deliverable.trafficLight.haveCollectionTools" list="yesNoRadio" /]
      </div>
      <div>
        [@s.text name="reporting.activityDeliverables.trafficLight.qualityDocumented" /]
        [@s.radio name="deliverable.trafficLight.qualityDocumented" list="yesNoRadio" /]
      </div>
    </div>
    
    <div id="metadataQuestions">
      <h6>[@s.text name="reporting.activityDeliverables.metadata" /]</h6>
      
      [#list metadataList as metadata]
        <div id="metadata-${metadata_index}}" class="halfPartBlock" >
          <input type="hidden" name="deliverable.metadata[${metadata_index}].metadata.id" value="${metadata.id}" />
          [@customForm.input name="deliverable.metadata[${metadata_index}].value" type="text" i18nkey="reporting.deliverables.metadata.${metadata.name?lower_case}" /]
        </div>
      [/#list]
      
    </div>
    
    
    <!-- internal parameter -->
    <input name="activityID" type="hidden" value="${activityID}" />
    <input name="deliverableID" type="hidden" value="${deliverable.id?c}" />
    
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