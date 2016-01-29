[#ftl]
<div id="deliverable-metadata" class="clearfix">
  <h1 class="contentTitle">Deliverable Metadata</h1>
  <div id="metadata-block" class="fullBlock">
    [#-- Description --] 
    <div class="fullBlock">
      [@customForm.textArea name="${params.deliverable.name}.dissemination.descriptionMetadata" i18nkey="reporting.projectDeliverable.metadata.description" help="reporting.projectDeliverable.metadata.description.help" required=true editable=editable /]
    </div>
    
    [#-- Creators / Authors --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.authorsMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.creator" help="reporting.projectDeliverable.metadata.creator.help" editable=editable/] 
    </div>
    
    [#-- Author identifier --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.creatorIdMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.creatorId" help="reporting.projectDeliverable.metadata.creatorId.help" editable=editable/] 
    </div>
   
    [#-- Publisher --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.publishierMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.publisher" help="reporting.projectDeliverable.metadata.publisher.help" editable=editable/] 
    </div>
  
    [#-- Contributor --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.contributorMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.contributor" help="reporting.projectDeliverable.metadata.contributor.help" editable=editable/] 
    </div>
    
    [#-- Contributor Center --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.contributorCenterMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.contributorCenter" help="reporting.projectDeliverable.metadata.contributorCenter.help" editable=editable/] 
    </div>
    
    [#-- Contributor CRP --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.contributorCRPMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.contributorCRP" help="reporting.projectDeliverable.metadata.contributorCRP.help" editable=editable/] 
    </div>
    
    [#-- Contributor Funder --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.contributorFunderMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.contributorFunder" help="reporting.projectDeliverable.metadata.contributorFunder.help" editable=editable/] 
    </div>
    
    [#-- Contributor Partner --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.contributorPartnerMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.contributorPartner" help="reporting.projectDeliverable.metadata.contributorPartner.help" editable=editable/] 
    </div>
    
    [#-- Contributor Project --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.contributorProjectMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.contributorProject" help="reporting.projectDeliverable.metadata.contributorProject.help" editable=editable/] 
    </div>
    
    [#-- Publication date / Creation date --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.publicationMetada" type="text" i18nkey="reporting.projectDeliverable.metadata.date" help="reporting.projectDeliverable.metadata.date.help" editable=editable/] 
    </div>
    
    [#-- Format --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.formatMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.format" help="reporting.projectDeliverable.metadata.format.help" editable=editable/] 
    </div>
    
    [#-- Identifier --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.identifierMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.identifier" help="reporting.projectDeliverable.metadata.identifier.help" editable=editable/] 
    </div>
    
    [#-- Relation --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.relationMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.relation" help="reporting.projectDeliverable.metadata.relation.help" editable=editable/] 
    </div>
    
    [#-- Source --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.sourceMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.source" help="reporting.projectDeliverable.metadata.source.help" editable=editable/] 
    </div>
    
    [#-- Language --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.languageMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.language" help="reporting.projectDeliverable.metadata.language.help" editable=editable/] 
    </div>
    
    [#-- Coverage --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.coverageMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.coverage" help="reporting.projectDeliverable.metadata.coverage.help" editable=editable/] 
    </div>
    
    [#-- Coverage Region--]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.coverageRegionMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.coverageRegion" help="reporting.projectDeliverable.metadata.coverageRegion.help" editable=editable/] 
    </div>
    
    [#-- Coverage Country--]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.coverageCountryMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.coverageCountry" help="reporting.projectDeliverable.metadata.coverageCountry.help" editable=editable/] 
    </div>
    
    [#-- Coverage GeoLocation--]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.coverageGeoLocationMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.coverageGeoLocation" help="reporting.projectDeliverable.metadata.coverageCountry.help" editable=editable/] 
    </div>
    
    [#-- Coverage Administrative unit--]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.coverageAdminUnitMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.coverageAdminUnit" help="reporting.projectDeliverable.metadata.coverageAdminUnit.help" editable=editable/] 
    </div>
     
    [#-- Rights --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.rigthsMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.rights" help="reporting.projectDeliverable.metadata.rights.help" editable=editable /] 
    </div>
    
    [#-- Subject --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.subjectMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.subject" help="reporting.projectDeliverable.metadata.subject.help" editable=editable/] 
    </div>
    
    [#-- Subject Agrovoc --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.subjectAgrovocMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.subjectAgrovoc" help="reporting.projectDeliverable.metadata.subjectAgrovoc.help" editable=editable/] 
    </div>
    
    [#-- Subject Domain Specific --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.subjectDomainSpecificMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.subjectDomainSpecific" help="reporting.projectDeliverable.metadata.subjectDomainSpecific.help" editable=editable/] 
    </div> 
    
  </div>
  
  [#-- Deliverable Publications Metadata --]
  <div id="publicationQuestions" class="fullBlock publicationQuestions requiredForType-3 requiredForType-21 requiredForType-23 requiredForType-24" style="display:${deliverable.publicationType?string('block','none')}"> 
    <h1 class="contentTitle publicationQuestions">[@s.text name="reporting.projectDeliverable.deliverablePublications" /] </h1> 
    
    [#-- Open access status --]
    <div class="fullBlock requiredForType-21" style="display:${deliverable.publicationPeerReviewType?string('block','none')}">
      [#if editable]
        [@customForm.radioButtonGroup name="${params.deliverable.name}.publicationMetadata.openAcessStatus" value="'${(deliverable.publicationMetadata.openAcessStatus)!-1}'" label="" i18nkey="reporting.projectDeliverable.publicationAccessStatus"  listName="openAccessStatuses" required=true editable=editable /]
      [#else]
        <h6>[@s.text name="reporting.projectDeliverable.publicationAccessStatus" /]:</h6>
        <div class="input"><p>[#if (deliverable.publicationMetadata.openAcessStatus??)!false][@s.text name="reporting.projectDeliverable.openAcessStatus.${deliverable.publicationMetadata.openAcessStatus}" /][#else]Open access status is not selected[/#if]</p></div>
      [/#if]
    </div>
    
    [#-- Indicators for journal articles --]
    <div class="fullBlock requiredForType-21" style="display:${deliverable.publicationPeerReviewType?string('block','none')}">
      <h6>[@s.text name="reporting.projectDeliverable.journalIndicators" /]:</h6>
      <div class="fullBlock verticallyCheckBox">
        [@customForm.checkbox name="${params.deliverable.name}.publicationMetadata.isiPublication"   i18nkey="reporting.projectDeliverable.isiPublication" value="true"       checked=(deliverable.publicationMetadata.isiPublication)!false        editable=editable /]
        [@customForm.checkbox name="${params.deliverable.name}.publicationMetadata.narsCoAuthor"     i18nkey="reporting.projectDeliverable.narsCoauthor" value="true"         checked=(deliverable.publicationMetadata.narsCoAuthor)!false          editable=editable /]
        [@customForm.checkbox name="${params.deliverable.name}.publicationMetadata.academicCoAuthor" i18nkey="reporting.projectDeliverable.earthSystemCoauthor" value="true"  checked=(deliverable.publicationMetadata.academicCoAuthor)!false    editable=editable /]
        
        [#if !editable && ( ((deliverable.publicationMetadata.isiPublication)!false) == false && ((deliverable.publicationMetadata.narsCoAuthor)!false) == false && ((deliverable.publicationMetadata.academicCoAuthor)!false) == false )]
          <div class="select"><p>There is not a indicator selected.</p></div>
        [/#if]
        <div class="clearfix"></div>
      </div>
    </div>
    
    [#-- Publication is an output of which flagships --]
    <div class="fullBlock">
      <h6>[@s.text name="reporting.projectDeliverable.outputPublication" help="reporting.projectDeliverable.outputPublication.help" required=true /]:</h6>
      <div id="metadata-flagships" class="checkboxGroup">
        [#if editable] 
          [@s.fielderror cssClass="fieldError" fieldName="${params.deliverable.name}.relatedFlagships"/]
          [@s.checkboxlist name="${params.deliverable.name}.publicationMetadata.relatedFlagshipsIds" list="ipProgramFlagships" value="${params.deliverable.name}.publicationMetadata.relatedFlagshipsIds" itemKey="id"   cssClass="checkbox" /]
        [#else]
          [@s.property value="${params.deliverable.name}.ipFlashigps"/]
          [#if (deliverable.publicationMetadata.relatedFlagships?has_content)!false]
            [#list deliverable.publicationMetadata.relatedFlagships as element]<p class="checked">${element.getComposedName()}</p>[/#list]
          [#else]
            <div class="select"><p>There is not a Flagship selected.</p></div>
          [/#if]
        [/#if]
      </div>
    </div> 
    
    [#-- Citation --]
    <div class="fullBlock">
      [@customForm.textArea name="${params.deliverable.name}.publicationMetadata.citation" i18nkey="reporting.projectDeliverable.metadata.citation" help="reporting.projectDeliverable.metadata.citation.help" required=true editable=editable /]
    </div>
    
    [#-- Publication --]
    <table id="alreadyDisseminated" class="default">
      <tbody>
        <tr>
          <td class="key">[@s.text name="reporting.projectDeliverable.acknowledgeCCAFS" /]</td> 
          <td class="value">[@deliverableTemplate.yesNoInput name="${params.deliverable.name}.publicationMetadata.acknowledgeCcafs" editable=editable/]</td>
        </tr>
      </tbody>
    </table>
    <br /> 
  </div>
</div>