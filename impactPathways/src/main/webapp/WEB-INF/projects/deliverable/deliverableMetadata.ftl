[#ftl]
<div id="deliverable-metadata" class="clearfix">
  <h1 class="contentTitle">Deliverable Metadata</h1>
  <div id="metadata-block" class="fullBlock">
    <div class="fullBlock">
      [#-- Description --] 
      [@customForm.textArea name="${params.deliverable.name}.dissemination.descriptionMetadata" i18nkey="reporting.projectDeliverable.metadata.description" help="reporting.projectDeliverable.metadata.description.help" required=true editable=editable /]
    </div>
    
    [#-- Creators / Authors --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.authorsMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.creator" help="reporting.projectDeliverable.metadata.creator.help" editable=editable/] 
    </div>
    
    [#-- Identifier --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.identifierMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.identifier" help="reporting.projectDeliverable.metadata.identifier.help" editable=editable/] 
    </div>
   
    [#-- Publisher --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.publishierMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.publisher" help="reporting.projectDeliverable.metadata.publisher.help" editable=editable/] 
    </div>
    
    [#-- Relation --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.relationMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.relation" help="reporting.projectDeliverable.metadata.relation.help" editable=editable/] 
    </div>
  
    [#-- Contributor --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.contributorMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.contributor" help="reporting.projectDeliverable.metadata.contributor.help" editable=editable/] 
    </div>
    
    [#-- Subject --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.subjectMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.subject" help="reporting.projectDeliverable.metadata.subject.help" editable=editable/] 
    </div> 
    
    [#-- Source --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.sourceMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.source" help="reporting.projectDeliverable.metadata.source.help" editable=editable/] 
    </div>
    
    [#-- Publication date / Creation date --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.publicationMetada" type="text" i18nkey="reporting.projectDeliverable.metadata.date" help="reporting.projectDeliverable.metadata.date.help" editable=editable/] 
    </div>
    
    [#-- Language --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.languageMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.language" help="reporting.projectDeliverable.metadata.language.help" editable=editable/] 
    </div>
    
    [#-- Coverage --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.coverageMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.coverage" help="reporting.projectDeliverable.metadata.coverage.help" editable=editable/] 
    </div> 
    
    [#-- Format --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.formatMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.format" help="reporting.projectDeliverable.metadata.format.help" editable=editable/] 
    </div>
    
    [#-- Rights --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.dissemination.rigthsMetadata" type="text" i18nkey="reporting.projectDeliverable.metadata.rights" help="reporting.projectDeliverable.metadata.rights.help" editable=editable /] 
    </div>  
  </div>
  
  [#-- Deliverable Publications Metadata --]  
  <div id="publicationQuestions" class="fullBlock publicationQuestions requiredForType-3 requiredForType-21 requiredForType-23 requiredForType-24" style="display:block"> 
    <h1 class="contentTitle publicationQuestions" style="display:block">[@s.text name="reporting.projectDeliverable.deliverablePublications" /] </h1> 
    
    [#-- Open access status --]
    <div class="fullBlock requiredForType-21">
      [@customForm.radioButtonGroup name="${params.deliverable.name}.publicationMetadata.openAcessStatus" value="${(deliverable.publicationMetadata.openAcessStatus)!}" label="" i18nkey="reporting.projectDeliverable.publicationAccessStatus" listName="openAccessStatuses" required=true editable=editable /]
    </div>
    
    [#-- Indicators for journal articles --]
    <div class="fullBlock requiredForType-21">
      <h6>[@s.text name="reporting.projectDeliverable.journalIndicators" /]:</h6>
      <div class="fullBlock verticallyCheckBox">
        [@customForm.checkbox name="${params.deliverable.name}.publicationMetadata.isiPublication"   i18nkey="reporting.projectDeliverable.isiPublication" value="true"       checked=(deliverable.publicationMetadata.isiPublication)!false        editable=editable /]
        [@customForm.checkbox name="${params.deliverable.name}.publicationMetadata.narsCoAuthor"     i18nkey="reporting.projectDeliverable.narsCoauthor" value="true"         checked=(deliverable.publicationMetadata.narsCoauthor)!false          editable=editable /]
        [@customForm.checkbox name="${params.deliverable.name}.publicationMetadata.academicCoAuthor" i18nkey="reporting.projectDeliverable.earthSystemCoauthor" value="true"  checked=(deliverable.publicationMetadata.earthSystemCoauthor)!false   editable=editable /]
        <div class="clearfix"></div>
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
    
    [#-- Publication is an output of which flagships --]
    <div class="fullBlock">
      <h6>[@s.text name="reporting.projectDeliverable.outputPublication" help="reporting.projectDeliverable.outputPublication.help" required=true /]:</h6>
      <div id="metadata-flagships" class="checkboxGroup">
        [#if editable] 
          [@s.fielderror cssClass="fieldError" fieldName="${params.deliverable.name}.relatedFlagships"/]
          [@s.checkboxlist name="${params.deliverable.name}.publicationMetadata.relatedFlagships" list="ipProgramFlagships" value="${params.deliverable.name}.publicationMetadata.relatedFlagships" listKey="id" listValue="getComposedName()"  cssClass="checkbox" /]
        [#else]
          [@s.property value="${params.deliverable.name}.relatedFlagshipsIds"/]
          [#if deliverable.relatedFlagshipsIds?has_content]
            [#list deliverable.relatedFlagshipsIds as element]<p class="checked">${element.getComposedName()}</p>[/#list]
          [#else]
            <div class="select"><p>There is not a Flagship selected.</p></div>
          [/#if]
        [/#if]
      </div>
    </div> 
      
  </div>
</div>