[#ftl]
[#assign title = "Activity deliverables Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/reporting/deliverables/deliverablesReportingInformation.js"] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "activities" /]
[#assign currentStage = "metadata" /]
[#assign userRole = "${currentUser.role}"]

[#assign isNewDeliverable = !deliverable.expected && deliverable.year == currentReportingLogframe.year]
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
        [@customForm.input name="deliverable.metadata[${deliverable.getMetadataIndex('Title')}].value" required=action.isMetadataMandatory('Title') type="text" i18nkey="reporting.activityDeliverables.metadata.title" help="reporting.activityDeliverables.metadata.title.help" /]
      </div> 
      
      <div id="deliverableType" class="halfPartBlock">
          [@customForm.select name="deliverable.parentType" value="deliverable.type.parent.id" i18nkey="reporting.activityDeliverables.type" listName="deliverableTypes" keyFieldName="id"  displayFieldName="name" required=true className="deliverableTypes" /]
      </div>
      
      <div id="deliverableSubtype" class="halfPartBlock">
        [@customForm.select name="deliverable.type.id" i18nkey="reporting.activityDeliverables.subtype" listName="deliverableSubTypes" keyFieldName="id"  displayFieldName="name" required=true className="deliverableSubTypes" /]
      </div> 


      <div id="deliverableOtherType" class="fullBlock" [#if deliverable.type.id != 25]style="display:none" [/#if]>
        [@customForm.input name="deliverable.otherType" i18nkey="reporting.activityDeliverables.otherType" /]
      </div>

      <div id="deliverableDescription" class="fullBlock ">
        [#if isNewDeliverable]
          [@customForm.textArea name="deliverable.description" i18nkey="reporting.activityDeliverables.description" /]
        [#else]
          <h6>[@s.text name="reporting.activityDeliverables.description" /]</h6>
          <span>${deliverable.description}</span>        
        [/#if]
      </div>

      <div id="deliverableIndentifier" class="fullBlock ">
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Identifier')}].metadata.id" value="${deliverable.getMetadataID('Identifier')}" />
        [@customForm.input name="deliverable.metadata[${deliverable.getMetadataIndex('Identifier')}].value"  required=action.isMetadataMandatory('Identifier') type="text" i18nkey="reporting.activityDeliverables.identifier" help="reporting.activityDeliverables.metadata.identifier.help" /]
      </div>

      <div id="deliverableDescriptionDissemination" class="fullBlock">
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('DisseminationDescription')}].metadata.id" value="${deliverable.getMetadataID('DisseminationDescription')}" />
        [@customForm.textArea name="deliverable.metadata[${deliverable.getMetadataIndex('DisseminationDescription')}].value" i18nkey="reporting.activityDeliverables.metadata.disseminationDescription" help="reporting.activityDeliverables.metadata.disseminationDescription.help" /]
      </div>

      <div id="deliverableStatus" class="halfPartBlock">
        [@customForm.select name="deliverable.status" i18nkey="reporting.activityDeliverables.status" listName="deliverableStatusList" keyFieldName="id"  displayFieldName="name" className="deliverableStatus" /]
      </div>

      <div id="deliverableYear" class="halfPartBlock">
        <h6>[@s.text name="reporting.activityDeliverables.year" /]</h6>
        [#if isNewDeliverable]
          <span>${deliverable.year}</span>  
        [#else] 
          <span>${deliverable.year}</span>     
        [/#if]
      </div>
      
      <div id="deliverableStatusJustification" class="fullBlock" style="disply:none">
        [@customForm.textArea name="deliverable.descriptionUpdate" i18nkey="reporting.activityDeliverables.statusJustification" help="reporting.activityDeliverables.statusJustification.help"  required=true /]
      </div>
          
      <div id="deliverableSubject" class="halfPartBlock" >
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Subject')}].metadata.id" value="${deliverable.getMetadataID('Subject')}" />
        [@customForm.input name="deliverable.metadata[${deliverable.getMetadataIndex('Subject')}].value" required=action.isMetadataMandatory('Subject') type="text" i18nkey="reporting.activityDeliverables.metadata.subject" help="reporting.activityDeliverables.metadata.subject.help" /] 
      </div>
      
      <div id="deliverableContributor" class="halfPartBlock" > 
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Contributor')}].metadata.id" value="${deliverable.getMetadataID('Contributor')}" />
        [@customForm.input name="deliverable.metadata[${deliverable.getMetadataIndex('Contributor')}].value" required=action.isMetadataMandatory('Contributor') type="text" i18nkey="reporting.activityDeliverables.metadata.contributor" help="reporting.activityDeliverables.metadata.contributor.help" /] 
      </div>
      
      <div id="deliverableCreator" class="halfPartBlock" >
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Creator')}].metadata.id" value="${deliverable.getMetadataID('Creator')}" />
        [@customForm.input name="deliverable.metadata[${deliverable.getMetadataIndex('Creator')}].value" required=action.isMetadataMandatory('Creator') type="text" i18nkey="reporting.activityDeliverables.metadata.creator" help="reporting.activityDeliverables.metadata.creator.help" /]  
      </div>
      
      <div id="deliverablePublisher" class="halfPartBlock" >
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Publisher')}].metadata.id" value="${deliverable.getMetadataID('Publisher')}" />
        [@customForm.input name="deliverable.metadata[${deliverable.getMetadataIndex('Publisher')}].value" required=action.isMetadataMandatory('Publisher') type="text" i18nkey="reporting.activityDeliverables.metadata.publisher" help="reporting.activityDeliverables.metadata.publisher.help" /]   
      </div>
      
      <div id="deliverableRelation" class="halfPartBlock" >
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Relation')}].metadata.id" value="${deliverable.getMetadataID('Relation')}" />
        [@customForm.input name="deliverable.metadata[${deliverable.getMetadataIndex('Relation')}].value" required=action.isMetadataMandatory('Relation') type="text" i18nkey="reporting.activityDeliverables.metadata.relation" help="reporting.activityDeliverables.metadata.relation.help" /]
      </div>
      
      <div id="deliverableCoverage" class="halfPartBlock" >
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Coverage')}].metadata.id" value="${deliverable.getMetadataID('Coverage')}" />
        [@customForm.input name="deliverable.metadata[${deliverable.getMetadataIndex('Coverage')}].value" required=action.isMetadataMandatory('Coverage') type="text" i18nkey="reporting.activityDeliverables.metadata.coverage" help="reporting.activityDeliverables.metadata.coverage.help" /]
      </div> 
      
      <div id="deliverableLanguage" class="halfPartBlock" >
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Language')}].metadata.id" value="${deliverable.getMetadataID('Language')}" />
        [@customForm.input name="deliverable.metadata[${deliverable.getMetadataIndex('Language')}].value" required=action.isMetadataMandatory('Language') type="text" i18nkey="reporting.activityDeliverables.metadata.language" help="reporting.activityDeliverables.metadata.language.help" /]
      </div>
      
      <div id="deliverableFormat" class="halfPartBlock" >
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Format')}].metadata.id" value="${deliverable.getMetadataID('Format')}" />
        [@customForm.input name="deliverable.metadata[${deliverable.getMetadataIndex('Format')}].value" required=action.isMetadataMandatory('Format') type="text" i18nkey="reporting.activityDeliverables.metadata.format" help="reporting.activityDeliverables.metadata.format.help" /]
      </div> 
      
    </div>
    
    [#-- Deliverable Publications questions --]  
    <h1 class="contentTitle publicationQuestions" [#if !deliverable.publication]style="display:none"[/#if]>[@s.text name="reporting.activityDeliverables.deliverablePublications" /] </h1> 
    <div id="publicationQuestions" class="fullBlock borderBox publicationQuestions" [#if !deliverable.publication]style="display:none"[/#if]> 

       <div id="JournalQuestions" [#if !deliverable.journalArticle]style="display:none"[/#if]>
        <div id="publicationOpenAccess" class="fullBlock accessType">
          [@customForm.radioButtonGroup name="publication.access" label="" i18nkey="reporting.publications.access" listName="publicationAccessList" keyFieldName="id" displayFieldName="name" help="reporting.publications.access.help" required=true /]
        </div>
        
        <div id="publicationJournalIndicators" class="fullBlock ">
        <h6>[@s.text name="reporting.activityDeliverables.publications.journalIndicators" /]</h6>
          <div class="fullBlock verticallyCheckBox">
            [@customForm.checkbox name="publication.isiPublication" i18nkey="reporting.publications.isiPublication" checked=publication.isiPublication value="true" /]
            [@customForm.checkbox name="publication.narsCoauthor" i18nkey="reporting.publications.narsCoauthor" checked=publication.narsCoauthor value="true" /]
            [@customForm.checkbox name="publication.earthSystemCoauthor" i18nkey="reporting.publications.earthSystemCoauthor" checked=publication.earthSystemCoauthor value="true" /]
          </div>
        </div> 
      </div> 
      
      <div id="publicationCitation" class="fullBlock">
        [@customForm.textArea name="publication.citation" i18nkey="reporting.activityDeliverables.publications.citation" required=true /]
      </div> 
      
      <div id="ccafsAcknowledge" class="fullBlock verticallyCheckBox">
        [@customForm.checkbox name="publication.ccafsAcknowledge" i18nkey="reporting.activityDeliverables.publications.acknowledgeCCAFS" checked=publication.ccafsAcknowledge value="true" /]
      </div>
 
      <div id="publicationRelatedThemes" class="fullBlock">
        <h6>[@s.text name="reporting.activityDeliverables.publications.outputPublication" help="reporting.activityDeliverables.publications.outputPublication.help" required=true /]</h6>
        <div class="checkboxGroup">
          [@s.fielderror cssClass="fieldError" fieldName="deliverable.publications.themeRelated"/]
          [@s.checkboxlist name="publication.relatedThemes" list="publicationThemeList" value="publication.relatedThemesIds" cssClass="checkbox" /]
        </div>
      </div>
    
    </div>
    
    
    [#-- Deliverable Data access questions --]
  <h1 class="contentTitle dataAccessQuestions" [#if !deliverable.data]style="display:none"[/#if]>[@s.text name="reporting.activityDeliverables.deliverableDataAccess" /] </h1> 
    <div class="dataAccessQuestions borderBox" id="dataAccessQuestions" class="fullBlock borderBox" [#if !deliverable.data]style="display:none"[/#if]>
      
      <div id="" class="fullBlock">
        <h6>[@s.text name="reporting.deliverables.dataAccess.dataDictionary" /]</h6>
        <div>
          [@s.radio name="deliverable.accessDetails.dataDictionary" list="yesNoRadio" /]
        </div>
      </div>

      <div class="fullBlock">
        [@customForm.input name="deliverable.accessDetails.qualityProcedures" type="text" i18nkey="reporting.deliverables.dataAccess.dataQuality"  required=true /]
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
          
        <div id="accessLimitStartDate" class="halfPartBlock accessLimit" style="display:none" >
          [@customForm.input name="deliverable.accessDetails.accessLimitStartDate" type="text" i18nkey="reporting.deliverables.dataAccess.accessLimits.startDate" /]
        </div>

        <div id="accessLimitEndDate" class="halfPartBlock accessLimit" style="display:none" >
          [@customForm.input name="deliverable.accessDetails.accessLimitEndDate" type="text" i18nkey="reporting.deliverables.dataAccess.accessLimits.endDate" /]
        </div>
      </div>
      
      <div id="deliverableRights" class="fullBlock">
        <input type="hidden" name="deliverable.metadata[${deliverable.getMetadataIndex('Rights')}].metadata.id" value="${deliverable.getMetadataID('Rights')}" /> 
        [@customForm.textArea name="deliverable.metadata[${deliverable.getMetadataIndex('Rights')}].value" required=action.isMetadataMandatory('Rights') i18nkey="reporting.activityDeliverables.metadata.rights" help="reporting.activityDeliverables.metadata.rights.help" /]
      </div>
      
      <div id="metadataProtocols" class="fullBlock">
        [@s.text name="reporting.deliverables.dataAccess.harvestingProtocols" /]
        [@s.radio name="deliverable.accessDetails.harvestingProtocols" list="yesNoRadio" /]
      </div>

      <div id="specifyProtocols" class="fullBlock" style="display:none">
        [@customForm.input name="deliverable.accessDetails.harvestingProtocolDetails" type="text" i18nkey="reporting.deliverables.dataAccess.harvestingProtocols.specify" /]
      </div>
      
    </div> 
     

    
    <!-- internal parameter -->
    <input name="activityID" type="hidden" value="${activityID}" />
    <input name="deliverableID" type="hidden" value="${deliverable.id?c}" />
    [#if publication?has_content]
      <input type="hidden" name="publication.id" value="${publication.id}" />
    [#else]
      <input type="hidden" name="publication.id" value="-1" />
    [/#if] 
    
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
  
  <script>var isNewDelverable = [#if isNewDeliverable]true[#else]false[/#if];</script>
  
[#include "/WEB-INF/global/pages/footer.ftl"]