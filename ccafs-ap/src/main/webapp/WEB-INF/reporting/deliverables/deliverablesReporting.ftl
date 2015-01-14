[#ftl]
[#assign title = "Activity deliverables Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/reporting/deliverablesReporting.js"] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "activities" /]
[#assign currentStage = "metadata" /]
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
      [@s.text name="reporting.activityDeliverables.deliverable" /] - ${deliverableID}
    </h1> 

    <div id="deliverableInformation">
      <div id="deliverableTitle" class="fullBlock ">
        <h6>[@s.text name="reporting.activityDeliverables.description" /]</h6>
        <span>${deliverable.description}</span>
      </div>

      <div id="deliverableType" class="halfPartBlock">
        <h6>[@s.text name="reporting.activityDeliverables.type" /]</h6>
        <span>${deliverable.type.parent.name}</span>
      </div>

      <div id="deliverableSubtype" class="halfPartBlock">
        <h6>[@s.text name="reporting.activityDeliverables.subtype" /]</h6>
        <span>${deliverable.type.name}</span>
      </div>

      <div id="deliverableYear" class="halfPartBlock">
        <h6>[@s.text name="reporting.activityDeliverables.year" /]</h6>
        <span>${deliverable.year}</span>
      </div>

      <div id="deliverableStatus" class="halfPartBlock">
        [@customForm.select name="deliverable.status" i18nkey="reporting.activityDeliverables.status" listName="deliverableStatusList" keyFieldName="id"  displayFieldName="name" className="deliverableStatus" /]
      </div>
      
      <div id="deliverableDescriptionUpdate" class="fullBlock">
        [@customForm.textArea name="deliverable.descriptionUpdate" i18nkey="reporting.activityDeliverables.descriptionUpdate" help="reporting.activityDeliverables.descriptionUpdate.help" /]
      </div>
    </div>
    
    <div id="trafficLightQuestions" class="fullBlock">
      <h6>[@s.text name="reporting.activityDeliverables.trafficLight" /]</h6>
      <table>
        <tbody>
          <tr>
            <td class="question"> [@s.text name="reporting.activityDeliverables.trafficLight.metadataDocumented" /]</td>
            <td class="answer"> [@s.radio name="deliverable.trafficLight.metadataDocumented" list="yesNoRadio" /]</td>
          </tr>
          
          <tr>
            <td class="question"> [@s.text name="reporting.activityDeliverables.trafficLight.collectionTools" /]<span class="quote"> [@s.text name="reporting.activityDeliverables.trafficLight.collectionTools.quote" /]<span> </td>
            <td class="answer"> [@s.radio name="deliverable.trafficLight.haveCollectionTools" list="yesNoRadio" /]</td>
          </tr>
          
          <tr>
            <td class="question"> [@s.text name="reporting.activityDeliverables.trafficLight.qualityDocumented" /]</td>
            <td class="answer"> [@s.radio name="deliverable.trafficLight.qualityDocumented" list="yesNoRadio" /]</td>
          </tr>
          
        </tbody>  
      </table> 
    </div>
    
    <div id="dataAccessQuestions" class="fullBlock">
      
      <div>
        [@s.text name="reporting.deliverables.dataAccess.dataDictionary" /]
        [@s.radio name="deliverable.accessDetails.dataDictionary" list="yesNoRadio" /]
      </div>

      <div>
        [@customForm.input name="deliverable.accessDetails.qualityProcedures" type="text" i18nkey="reporting.deliverables.dataAccess.dataQuality" /]
      </div>

      <div>
        [@s.text name="reporting.deliverables.dataAccess.dataRestriction" /]
        [@s.radio name="deliverable.accessDetails.accessRestrictions" list="notApplicableRadio" /]
      </div>
      
      <div id="accessLimitOptions">
        <div>
          [@s.text name="reporting.deliverables.dataAccess.accessLimits" /]
          [@s.radio name="deliverable.accessDetails.accessLimits" list="accessLimitsRadio" /]
        </div>
  
        <div class="halfPartBlock">
          [@customForm.input name="deliverable.accessDetails.accessLimitStartDate" type="text" i18nkey="reporting.deliverables.dataAccess.accessLimits.startDate" /]
        </div>

        <div class="halfPartBlock">
          [@customForm.input name="deliverable.accessDetails.accessLimitEndDate" type="text" i18nkey="reporting.deliverables.dataAccess.accessLimits.endDate" /]
        </div>
      </div>
      
      <div>
        [@s.text name="reporting.deliverables.dataAccess.harvestingProtocols" /]
        [@s.radio name="deliverable.accessDetails.harvestingProtocols" list="yesNoRadio" /]
      </div>

      <div>
        [@customForm.input name="deliverable.accessDetails.harvestingProtocolDetails" type="text" i18nkey="reporting.deliverables.dataAccess.harvestingProtocols.specify" /]
      </div>
    </div>
    
    <h6>[@s.text name="reporting.activityDeliverables.metadata" /]</h6>
    <div id="metadataQuestions" class="fullBlock"> 
      [#list metadataList as metadata]
        <div id="metadata-${metadata_index}" class="halfPartBlock" >
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