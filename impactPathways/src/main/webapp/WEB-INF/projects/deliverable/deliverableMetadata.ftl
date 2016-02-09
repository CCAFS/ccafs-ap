[#ftl]
[#macro metadataField title="" encodedName="" type="input"]

  <input type="hidden" name="${params.deliverable.name}.metadata[${deliverable.getMetadataIndex(encodedName)}].id" value="${deliverable.getMetadataID(encodedName)}" />
  [#if type == "input"]
    [@customForm.input name="${params.deliverable.name}.metadata[${deliverable.getMetadataIndex(encodedName)}].value"  type="text" i18nkey="reporting.projectDeliverable.metadata.${title}" help="reporting.projectDeliverable.metadata.${title}.help" editable=editable/]
  [/#if]
  [#if type == "textArea"]
    [@customForm.textArea name="${params.deliverable.name}.metadata[${deliverable.getMetadataIndex(encodedName)}].value" i18nkey="reporting.projectDeliverable.metadata.${title}" help="reporting.projectDeliverable.metadata.${title}.help" editable=editable/]
  [/#if]
[/#macro]

<div id="deliverable-metadata" class="clearfix" style="display:${(deliverable.dissemination??)?string('block','none')}">
  <h1 class="contentTitle">Deliverable Metadata</h1>
  <div id="metadata-block" class="fullBlock">
    [#-- Description --] 
    <div class="fullBlock">[@metadataField title="description" encodedName="dc.description.abstract" type="textArea"/]</div>
    
    [#-- Creators / Authors --]
    <div class="halfPartBlock" >[@metadataField title="creator" encodedName="dc:creator" /]</div>
    
    [#-- Author identifier --]
    <div class="halfPartBlock" >[@metadataField title="creatorId" encodedName="cg.creator.ID" /]</div>
   
    [#-- Publisher --]
    <div class="halfPartBlock" >[@metadataField title="publisher" encodedName="dc.publisher" /]</div>
  
    [#-- Contributor --]
    <div class="halfPartBlock" >[@metadataField title="contributor" encodedName="dc.contributor" /]</div>
    
    [#-- Contributor Center --]
    <div class="halfPartBlock" >[@metadataField title="contributorCenter" encodedName="cg.contributor.center" /]</div>
    
    [#-- Contributor CRP --]
    <div class="halfPartBlock" >[@metadataField title="contributorCRP" encodedName="cg.contributor.crp" /]</div>
    
    [#-- Contributor Funder --]
    <div class="halfPartBlock" >[@metadataField title="contributorFunder" encodedName="cg.contributor.funder" /]</div>
    
    [#-- Contributor Partner --]
    <div class="halfPartBlock" >[@metadataField title="contributorPartner" encodedName="cg.contributor.partnerId" /]</div>
    
    [#-- Contributor Project --]
    <div class="halfPartBlock" >[@metadataField title="contributorProject" encodedName="cg.contributor.project" /]</div>
    
    [#-- Publication date / Creation date --]
    <div class="halfPartBlock" >[@metadataField title="date" encodedName="dc.date" /]</div>
    
    [#-- Format --]
    <div class="halfPartBlock" >[@metadataField title="format" encodedName="dc.format" /]</div>
    
    [#-- Identifier --]
    <div class="halfPartBlock" >[@metadataField title="identifier" encodedName="dc.identifier" /]</div>
    
    [#-- Relation --]
    <div class="halfPartBlock" >[@metadataField title="relation" encodedName="dc.relation" /]</div>
    
    [#-- Source --]
    <div class="halfPartBlock" >[@metadataField title="source" encodedName="dc.source" /]</div>
    
    [#-- Language --]
    <div class="halfPartBlock" >[@metadataField title="language" encodedName="dc.language" /]</div>
    
    [#-- Coverage --]
    <div class="halfPartBlock" >[@metadataField title="coverage" encodedName="dc.coverage" /]</div>
    
    [#-- Coverage Region--]
    <div class="halfPartBlock" >[@metadataField title="coverageRegion" encodedName="cg.coverage.region" /]</div>
    
    [#-- Coverage Country--]
    <div class="halfPartBlock" >[@metadataField title="coverageCountry" encodedName="cg:coverage.country" /]</div>
    
    [#-- Coverage GeoLocation--]
    <div class="halfPartBlock" >[@metadataField title="coverageGeoLocation" encodedName="cg.coverage.geolocation" /]</div>
    
    [#-- Coverage Administrative unit--]
    <div class="halfPartBlock" >[@metadataField title="coverageAdminUnit" encodedName="cg:coverage.admin-unit" /]</div>
     
    [#-- Rights --]
    <div class="halfPartBlock" >[@metadataField title="rights" encodedName="dc.rights" /]</div>
    
    [#-- Subject --]
    <div class="halfPartBlock" >[@metadataField title="subject" encodedName="dc.subject" /]</div>
    
    [#-- Subject Agrovoc --]
    <div class="halfPartBlock" >[@metadataField title="subjectAgrovoc" encodedName="cg.subject.agrovoc" /]</div>
    
    [#-- Subject Domain Specific --]
    <div class="halfPartBlock" >[@metadataField title="subjectDomainSpecific" encodedName="cg.subject.domain-specific" /]</div> 
    
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