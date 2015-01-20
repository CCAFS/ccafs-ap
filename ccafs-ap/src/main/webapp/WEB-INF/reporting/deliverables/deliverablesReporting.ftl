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

    [#-- Deliverable Main Information --] 
    <h1 class="contentTitle">[@s.text name="reporting.activityDeliverables.mainInformation" /] </h1> 
    <div id="deliverableInformation" class="borderBox">
      <div id="deliverableTitle" class="fullBlock ">
        [@customForm.input name="deliverable.title" type="text" i18nkey="reporting.activityDeliverables.title" /]
      </div>
    
      <div id="deliverableTitle" class="fullBlock ">
        <h6>[@s.text name="reporting.activityDeliverables.description" /]</h6>
        <span>${deliverable.description}</span>
      </div>
      
      <div id="deliverableIndentifier" class="fullBlock ">
        [@customForm.input name="deliverable.identifier" type="text" i18nkey="reporting.activityDeliverables.identifier" /]
      </div>

      <div id="deliverableDescriptionDissemination" class="fullBlock">
        [@customForm.textArea name="deliverable.descriptionDissemination" i18nkey="reporting.activityDeliverables.descriptionDissemination" help="reporting.activityDeliverables.descriptionDissemination.help" /]
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
        [@customForm.input name="deliverable.subject" type="text" i18nkey="reporting.activityDeliverables.subject" /]
      </div>
      
      <div id="deliverableContributor" class="halfPartBlock" >
        [@customForm.input name="deliverable.contributor" type="text" i18nkey="reporting.activityDeliverables.contributor" /]
      </div>
      
      <div id="deliverableCreator" class="halfPartBlock" >
        [@customForm.input name="deliverable.creator" type="text" i18nkey="reporting.activityDeliverables.creator" /]
      </div>
      
      <div id="deliverablePublisher" class="halfPartBlock" >
        [@customForm.input name="deliverable.publisher" type="text" i18nkey="reporting.activityDeliverables.publisher" /]
      </div>
      
      <div id="deliverableSubjectRelation" class="halfPartBlock" >
        [@customForm.input name="deliverable.relation" type="text" i18nkey="reporting.activityDeliverables.relation" /]
      </div>
      
      <div id="deliverableCoverage" class="halfPartBlock" >
        [@customForm.input name="deliverable.coverage" type="text" i18nkey="reporting.activityDeliverables.coverage" /]
      </div> 
      
      <div id="deliverableLanguage" class="halfPartBlock" >
        [@customForm.input name="deliverable.language" type="text" i18nkey="reporting.activityDeliverables.language" /]
      </div>
      
      <div id="deliverableYear" class="halfPartBlock">
        <h6>[@s.text name="reporting.activityDeliverables.year" /]</h6>
        <span>${deliverable.year}</span>
      </div>
      
      <div id="deliverableFormat" class="halfPartBlock" >
        [@customForm.input name="deliverable.format" type="text" i18nkey="reporting.activityDeliverables.format" /]
      </div>
      
      <div id="deliverableStatus" class="halfPartBlock">
        [@customForm.select name="deliverable.status" i18nkey="reporting.activityDeliverables.status" listName="deliverableStatusList" keyFieldName="id"  displayFieldName="name" className="deliverableStatus" /]
      </div>
      
      <div id="deliverableStatusJustification" class="fullBlock">
        [@customForm.textArea name="deliverable.statusJustification" i18nkey="reporting.activityDeliverables.statusJustification" help="reporting.activityDeliverables.statusJustification.help" /]
      </div>
      
    </div>
    
    
    [#-- Deliverable Data access questions --]
    <h1 class="contentTitle">[@s.text name="reporting.activityDeliverables.deliverableDataAccess" /] </h1> 
    <div id="dataAccessQuestions" class="fullBlock borderBox">
      
      <div class="fullBlock">
        <h6>[@s.text name="reporting.deliverables.dataAccess.dataDictionary" /]</h6>
        <div>
          [@s.radio name="deliverable.accessDetails.dataDictionary" list="yesNoRadio" /]
        </div>
      </div>

      <div class="fullBlock">
        [@customForm.input name="deliverable.accessDetails.qualityProcedures" type="text" i18nkey="reporting.deliverables.dataAccess.dataQuality" /]
      </div>

      <div class="fullBlock">
        <h6>[@s.text name="reporting.deliverables.dataAccess.dataRestriction" /]</h6> 
        <div>
          [@s.radio name="deliverable.accessDetails.accessRestrictions" list="notApplicableRadio" /]
        </div>
      </div>
      
      <div id="accessLimitOptions">
        <p>[@s.text name="reporting.deliverables.dataAccess.accessLimits" /]</p>
        <div class="verticallyRadio">
          [@s.radio name="deliverable.accessDetails.accessLimits" list="accessLimitsRadio" /]
        </div> 
          
        <div class="halfPartBlock">
          [@customForm.input name="deliverable.accessDetails.accessLimitStartDate" type="text" i18nkey="reporting.deliverables.dataAccess.accessLimits.startDate" /]
        </div>

        <div class="halfPartBlock">
          [@customForm.input name="deliverable.accessDetails.accessLimitEndDate" type="text" i18nkey="reporting.deliverables.dataAccess.accessLimits.endDate" /]
        </div>
      </div>
      
      <div id="deliverableRights" class="fullBlock">
        [@customForm.textArea name="deliverable.metadata.rights" i18nkey="reporting.deliverables.metadata.rights" help="reporting.deliverables.metadata.rights.help" /]
      </div>
      
      <div class="fullBlock">
        [@s.text name="reporting.deliverables.dataAccess.harvestingProtocols" /]
        [@s.radio name="deliverable.accessDetails.harvestingProtocols" list="yesNoRadio" /]
      </div>

      <div class="fullBlock">
        [@customForm.input name="deliverable.accessDetails.harvestingProtocolDetails" type="text" i18nkey="reporting.deliverables.dataAccess.harvestingProtocols.specify" /]
      </div>
      
    </div>
    
    [#-- Deliverable Case Studies questions --]
    <h1 class="contentTitle">[@s.text name="reporting.activityDeliverables.deliverableCaseStudies" /] </h1> 
    <div id="caseStudyQuestions" class="fullBlock borderBox">
    
      <div id="caseStudyType" class="fullBlock">
        <h6>[@s.text name="reporting.activityDeliverables.caseStudies.type" /]</h6>
        <div>[@s.radio name="reporting.activityDeliverables.caseStudies.type" list="yesNoRadio" /]</div>
      </div>
      
      <div id="caseStudyStartDate" class="halfPartBlock">
        [@customForm.input name="deliverable.caseStudies.startDate" type="text" i18nkey="reporting.activityDeliverables.caseStudies.startDate" /]
      </div>
      
      <div id="caseStudyEndDate" class="halfPartBlock">
        [@customForm.input name="deliverable.caseStudies.endDate" type="text" i18nkey="reporting.activityDeliverables.caseStudies.endDate" /]
      </div>
      
      <div id="caseStudyIsGlobal" class="halfPartBlock">
        [@customForm.checkbox  name="deliverable.caseStudies.global" i18nkey="reporting.activityDeliverables.caseStudies.isGlobal" value="false" /]
      </div>
      
      <div id="caseStudyCountry" class="halfPartBlock">
        [@customForm.input name="deliverable.caseStudies.country" type="text" i18nkey="reporting.activityDeliverables.caseStudies.country" /]
      </div>
      
      <div id="caseStudyImage" class="fullBlock imageBlock">
        <div class="halfPartBlock browseInput">
          [@customForm.input name="caseStudies[{caseStudy_index}].image" type="file" i18nkey="reporting.caseStudies.image" /]
        </div>                            
        <div id="caseStudies[{caseStudy_index}].image" class="halfPartBlock image">
          <img src="{caseStudiesImagesUrl}/{caseStudy.imageFileName}" width="100%">
        </div>
        <div class="clearfix"></div>
      </div> 
            
      <div id="caseStudyKeywords" class="fullBlock">
        [@customForm.input name="deliverable.caseStudies.keywords" type="text" i18nkey="reporting.activityDeliverables.caseStudies.keywords" /]
      </div>
      
      <div id="caseStudyDescription" class="fullBlock">
        [@customForm.textArea name="deliverable.caseStudies.projectDescription" i18nkey="reporting.activityDeliverables.caseStudies.projectDescription" /]
      </div>
      
      <div id="caseStudyResults" class="fullBlock">
        [@customForm.textArea name="deliverable.caseStudies.projectResults" i18nkey="reporting.activityDeliverables.caseStudies.projectResults" /]
      </div>
      
      <div id="caseStudyIntroductionObjectives" class="fullBlock">
        [@customForm.textArea name="deliverable.caseStudies.introductionObjectives" i18nkey="reporting.activityDeliverables.caseStudies.introductionObjectives" /]
      </div>
      
      <div id="caseStudyPartners" class="fullBlock">
        [@customForm.textArea name="deliverable.caseStudies.partners" i18nkey="reporting.activityDeliverables.caseStudies.partners" /]
      </div>
      
      <div id="caseStudyLinks" class="fullBlock">
        [@customForm.textArea name="deliverable.caseStudies.links" i18nkey="reporting.activityDeliverables.caseStudies.links" /]
      </div>
      
    </div>
    
    [#-- Deliverable Publications questions --]
    <h1 class="contentTitle">[@s.text name="reporting.activityDeliverables.deliverablePublications" /] </h1> 
    <div id="publicationsQuestions" class="fullBlock borderBox">
    
      <div id="publicationsLinks" class="fullBlock">
          <h6>[@s.text name="reporting.activityDeliverables.publications.sopenAccessStatus" /]</h6>
          <div class="verticallyRadio">
            [@s.radio name="deliverable.publications.links" list="accessLimitsRadio" /]
          </div> 
      </div> 
      
      <div id="publicationsJournalIndicators" class="fullBlock ">
      <h6>[@s.text name="reporting.activityDeliverables.publications.journalIndicators" /]</h6>
        <div class="fullBlock verticallyCheckBox">
          [@customForm.checkbox name="deliverable.publications.isiPublication" i18nkey="reporting.publications.isiPublication" value="true" /]
          [@customForm.checkbox name="deliverable.publications.narsCoauthor" i18nkey="reporting.publications.narsCoauthor" value="true" /]
          [@customForm.checkbox name="deliverable.publications.earthSystemCoauthor" i18nkey="reporting.publications.earthSystemCoauthor" value="true" /]
        </div>
      </div> 
      
      <div id="publicationsCitation" class="fullBlock">
        [@customForm.textArea name="deliverable.publications.citation" i18nkey="reporting.activityDeliverables.publications.citation" /]
      </div> 
      
      <div id="ccafsAcknowledge" class="fullBlock">
        [@customForm.checkbox name="deliverable.publications.ccafsAcknowledge" i18nkey="reporting.activityDeliverables.publications.citation.acknowledgeCCAFS" value="true" /]
      </div>
 
      <div id="publicationsRelatedThemes" class="fullBlock">
        <h6>[@s.text name="reporting.activityDeliverables.publications.outputPublication" help="reporting.activityDeliverables.publications.outputPublication.help"/]</h6>
        <div class="checkboxGroup">
          [@s.fielderror cssClass="fieldError" fieldName="deliverable.publications.themeRelated"/]
          [@s.checkboxlist name="deliverable.publications.relatedThemes" list="publicationThemeList" value="deliverable.publications.relatedThemesIds" cssClass="checkbox" /]
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