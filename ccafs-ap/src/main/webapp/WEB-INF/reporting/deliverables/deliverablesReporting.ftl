[#ftl]
[#assign title = "Activity deliverables Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/reporting/deliverables/deliverablesReportingInformation.js"] /]
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

    [#-- Deliverable Main Information --] 
    <h1 class="contentTitle">[@s.text name="reporting.activityDeliverables.mainInformation" /] </h1> 
    <div id="deliverableInformation" class="borderBox">
    
      <div id="deliverableTitle" class="fullBlock "> 
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Title')}].metadata.id" value="${deliverable.getMetadataID('Title')}" />
        [@customForm.input name="deliverable.metadata[${deliverable.getMetadataIndex('Title')}].value" type="text" i18nkey="reporting.deliverables.metadata.title" /]
      </div> 
          
      <div id="deliverableDescription" class="fullBlock ">
        <h6>[@s.text name="reporting.activityDeliverables.description" /]</h6>
        <span>${deliverable.description}</span>
      </div>
      
      <div id="deliverableIndentifier" class="fullBlock ">
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Identifier')}].metadata.id" value="${deliverable.getMetadataID('Identifier')}" />
        [@customForm.input name="deliverable.metadata[${deliverable.getMetadataIndex('Identifier')}].value" type="text" i18nkey="reporting.activityDeliverables.identifier" /]
         
      </div>
        
      <div id="deliverableDescriptionDissemination" class="fullBlock">
        [@customForm.textArea name="deliverable.descriptionDissemination" i18nkey="reporting.activityDeliverables.descriptionDissemination" help="reporting.activityDeliverables.descriptionDissemination.help" /]
      </div>
      
      <div id="deliverableStatus" class="halfPartBlock">
        [@customForm.select name="deliverable.status" i18nkey="reporting.activityDeliverables.status" listName="deliverableStatusList" keyFieldName="id"  displayFieldName="name" className="deliverableStatus" /]
      </div>
      
      <div id="deliverableStatusJustification" class="fullBlock" style="disply:none">
        [@customForm.textArea name="deliverable.statusJustification" i18nkey="reporting.activityDeliverables.statusJustification" help="reporting.activityDeliverables.statusJustification.help" /]
      </div>
      
    </div>
    
    [#-- Deliverable Metadata --]
    <h1 class="contentTitle">[@s.text name="reporting.activityDeliverables.deliverableMetadata" /] </h1> 
    <div id="deliverableMetadata" class="borderBox"> 
    
      <div id="deliverableType" class="halfPartBlock">
        <h6>[@s.text name="reporting.activityDeliverables.type" /]</h6>
        <span>${deliverable.type.parent.name}</span>
      </div>

      <div id="deliverableSubtype" class="halfPartBlock">
        <h6>[@s.text name="reporting.activityDeliverables.subtype" /]</h6>
        <span>${deliverable.type.name}</span>
      </div> 
      
      <div id="deliverableSubject" class="halfPartBlock" >
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Subject')}].metadata.id" value="${deliverable.getMetadataID('Subject')}" />
        [@customForm.input name="deliverable.metadata[${deliverable.getMetadataIndex('Subject')}].value" type="text" i18nkey="reporting.activityDeliverables.subject" /] 
      </div>
      
      <div id="deliverableContributor" class="halfPartBlock" > 
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Contributor')}].metadata.id" value="${deliverable.getMetadataID('Contributor')}" />
        [@customForm.input name="deliverable.metadata[${deliverable.getMetadataIndex('Contributor')}].value" type="text" i18nkey="reporting.activityDeliverables.contributor" /] 
      </div>
      
      <div id="deliverableCreator" class="halfPartBlock" >
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Creator')}].metadata.id" value="${deliverable.getMetadataID('Creator')}" />
        [@customForm.input name="deliverable.metadata[${deliverable.getMetadataIndex('Creator')}].value" type="text" i18nkey="reporting.activityDeliverables.creator" /]  
      </div>
      
      <div id="deliverablePublisher" class="halfPartBlock" >
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Publisher')}].metadata.id" value="${deliverable.getMetadataID('Publisher')}" />
        [@customForm.input name="deliverable.metadata[${deliverable.getMetadataIndex('Publisher')}].value" type="text" i18nkey="reporting.activityDeliverables.publisher" /]   
      </div>
      
      <div id="deliverableSubjectRelation" class="halfPartBlock" >
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Relation')}].metadata.id" value="${deliverable.getMetadataID('Relation')}" />
        [@customForm.input name="deliverable.metadata[${deliverable.getMetadataIndex('Relation')}].value" type="text" i18nkey="reporting.activityDeliverables.relation" /]
      </div>
      
      <div id="deliverableCoverage" class="halfPartBlock" >
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Coverage')}].metadata.id" value="${deliverable.getMetadataID('Coverage')}" />
        [@customForm.input name="deliverable.metadata[${deliverable.getMetadataIndex('Coverage')}].value" type="text" i18nkey="reporting.activityDeliverables.coverage" /]
      </div> 
      
      <div id="deliverableLanguage" class="halfPartBlock" >
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Language')}].metadata.id" value="${deliverable.getMetadataID('Language')}" />
        [@customForm.input name="deliverable.metadata[${deliverable.getMetadataIndex('Language')}].value" type="text" i18nkey="reporting.activityDeliverables.language" /]
      </div>
      
      <div id="deliverableYear" class="halfPartBlock">
        <h6>[@s.text name="reporting.activityDeliverables.year" /]</h6>
        <span>${deliverable.year}</span>
      </div>
      
      <div id="deliverableFormat" class="halfPartBlock" >
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Format')}].metadata.id" value="${deliverable.getMetadataID('Format')}" />
        [@customForm.input name="deliverable.metadata[${deliverable.getMetadataIndex('Format')}].value" type="text" i18nkey="reporting.activityDeliverables.format" /]
      </div> 
      
    </div>
    
    
    [#-- Deliverable Data access questions --]
    <h1 class="contentTitle">[@s.text name="reporting.activityDeliverables.deliverableDataAccess" /] </h1> 
    <div id="dataAccessQuestions" class="fullBlock borderBox">
      
      <div id="" class="fullBlock">
        <h6>[@s.text name="reporting.deliverables.dataAccess.dataDictionary" /]</h6>
        <div>
          [@s.radio name="deliverable.accessDetails.dataDictionary" list="yesNoRadio" /]
        </div>
      </div>

      <div class="fullBlock">
        [@customForm.input name="deliverable.accessDetails.qualityProcedures" type="text" i18nkey="reporting.deliverables.dataAccess.dataQuality" /]
      </div>

      <div id="restrictionImposed" class="fullBlock">
        <h6>[@s.text name="reporting.deliverables.dataAccess.dataRestriction" /]</h6> 
        <div>
          [@s.radio name="deliverable.accessDetails.accessRestrictions" list="notApplicableRadio" /]
        </div>
      </div>
      
      <div id="accessLimitOptions" style="display:none">
        <p>[@s.text name="reporting.deliverables.dataAccess.accessLimits" /]</p>
        <div class="verticallyRadio">
          [@s.radio name="deliverable.accessDetails.accessLimits" list="accessLimitsRadio" /]
        </div> 
          
        <div class="halfPartBlock accessLimit" style="display:none" >
          [@customForm.input name="deliverable.accessDetails.accessLimitStartDate" type="text" i18nkey="reporting.deliverables.dataAccess.accessLimits.startDate" /]
        </div>

        <div class="halfPartBlock accessLimit" style="display:none" >
          [@customForm.input name="deliverable.accessDetails.accessLimitEndDate" type="text" i18nkey="reporting.deliverables.dataAccess.accessLimits.endDate" /]
        </div>
      </div>
      
      <div id="deliverableRights" class="fullBlock">
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Rights')}].metadata.id" value="${deliverable.getMetadataID('Rights')}" /> 
        [@customForm.textArea name="deliverable.metadata[${deliverable.getMetadataIndex('Rights')}].value" i18nkey="reporting.deliverables.metadata.rights" help="reporting.deliverables.metadata.rights.help" /]
      </div>
      
      <div id="metadataProtocols" class="fullBlock">
        [@s.text name="reporting.deliverables.dataAccess.harvestingProtocols" /]
        [@s.radio name="deliverable.accessDetails.harvestingProtocols" list="yesNoRadio" /]
      </div>

      <div id="specifyProtocols" class="fullBlock" style="display:none">
        [@customForm.input name="deliverable.accessDetails.harvestingProtocolDetails" type="text" i18nkey="reporting.deliverables.dataAccess.harvestingProtocols.specify" /]
      </div>
      
    </div> 
    
    [#-- Deliverable Publications questions --]  
    <h1 class="contentTitle" [#if deliverable.publication]style="display:none"[/#if]>[@s.text name="reporting.activityDeliverables.deliverablePublications" /] </h1> 
    <div id="publicationQuestions" class="fullBlock borderBox" [#if deliverable.publication]style="display:none"[/#if]> 
    
      <div id="JournalQuestions">
        <div id="publicationOpenAccess" class="fullBlock accessType">
          [@customForm.radioButtonGroup name="publication.access" label="" i18nkey="reporting.publications.access" listName="publicationAccessList" keyFieldName="id" displayFieldName="name" value="" help="reporting.publications.access.help"/]
        </div>
        
        <div id="publicationJournalIndicators" class="fullBlock ">
        <h6>[@s.text name="reporting.activityDeliverables.publications.journalIndicators" /]</h6>
          <div class="fullBlock verticallyCheckBox">
            [@customForm.checkbox name="publication.isiPublication" i18nkey="reporting.publications.isiPublication" value="true" /]
            [@customForm.checkbox name="publication.publications.narsCoauthor" i18nkey="reporting.publications.narsCoauthor" value="true" /]
            [@customForm.checkbox name="publication.publications.earthSystemCoauthor" i18nkey="reporting.publications.earthSystemCoauthor" value="true" /]
          </div>
        </div> 
      </div> 
      
      <div id="publicationCitation" class="fullBlock">
        [@customForm.textArea name="publication.citation" i18nkey="reporting.activityDeliverables.publications.citation" /]
      </div> 
      
      <div id="ccafsAcknowledge" class="fullBlock verticallyCheckBox">
        [@customForm.checkbox name="publication.ccafsAcknowledge" i18nkey="reporting.activityDeliverables.publications.acknowledgeCCAFS" value="true" /]
      </div>
 
      <div id="publicationRelatedThemes" class="fullBlock">
        <h6>[@s.text name="reporting.activityDeliverables.publications.outputPublication" help="reporting.activityDeliverables.publications.outputPublication.help"/]</h6>
        <div class="checkboxGroup">
          [@s.fielderror cssClass="fieldError" fieldName="deliverable.publications.themeRelated"/]
          [@s.checkboxlist name="publication.relatedThemes" list="publicationThemeList" value="deliverable.publications.relatedThemesIds" cssClass="checkbox" /]
        </div>
      </div>
    
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