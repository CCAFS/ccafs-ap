[#ftl]
<div id="deliverable-metadata" class="clearfix">
  [#if !editable && canEdit]
    <div class="editButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#deliverable-metadata">[@s.text name="form.buttons.edit" /]</a></div>
  [#else]
    [#if canEdit && !newProject]
      <div class="viewButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][/@s.url]#deliverable-metadata">[@s.text name="form.buttons.unedit" /]</a></div>
    [/#if]
  [/#if]
  <h1 class="contentTitle">Deliverable Metadata</h1>
  <div id="metadata-block" class="fullBlock">
    [#-- Creators / Authors --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.metadata.creator" type="text" i18nkey="reporting.projectDeliverable.metadata.creator" help="reporting.projectDeliverable.metadata.creator.help" editable=editable/] 
    </div>
    
    [#-- Identifier --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.metadata.identifier" type="text" i18nkey="reporting.projectDeliverable.metadata.identifier" help="reporting.projectDeliverable.metadata.identifier.help" editable=editable/] 
    </div>
   
    [#-- Publisher --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.metadata.publisher" type="text" i18nkey="reporting.projectDeliverable.metadata.publisher" help="reporting.projectDeliverable.metadata.publisher.help" editable=editable/] 
    </div>
    
    [#-- Relation --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.metadata.relation" type="text" i18nkey="reporting.projectDeliverable.metadata.relation" help="reporting.projectDeliverable.metadata.relation.help" editable=editable/] 
    </div>
  
    [#-- Contributor --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.metadata.contributor" type="text" i18nkey="reporting.projectDeliverable.metadata.contributor" help="reporting.projectDeliverable.metadata.contributor.help" editable=editable/] 
    </div>
    
    [#-- Subject --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.metadata.subject" type="text" i18nkey="reporting.projectDeliverable.metadata.subject" help="reporting.projectDeliverable.metadata.subject.help" editable=editable/] 
    </div> 
    
    [#-- Source --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.metadata.source" type="text" i18nkey="reporting.projectDeliverable.metadata.source" help="reporting.projectDeliverable.metadata.source.help" editable=editable/] 
    </div>
    
    [#-- Publication date / Creation date --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.metadata.date" type="text" i18nkey="reporting.projectDeliverable.metadata.date" help="reporting.projectDeliverable.metadata.date.help" editable=editable/] 
    </div>
    
    [#-- Language --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.metadata.language" type="text" i18nkey="reporting.projectDeliverable.metadata.language" help="reporting.projectDeliverable.metadata.language.help" editable=editable/] 
    </div>
    
    [#-- Coverage --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.metadata.coverage" type="text" i18nkey="reporting.projectDeliverable.metadata.coverage" help="reporting.projectDeliverable.metadata.coverage.help" editable=editable/] 
    </div> 
    
    [#-- Format --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.metadata.format" type="text" i18nkey="reporting.projectDeliverable.metadata.format" help="reporting.projectDeliverable.metadata.format.help" editable=editable/] 
    </div>
    
    [#-- Rights --]
    <div class="halfPartBlock" >
      [@customForm.input name="${params.deliverable.name}.metadata.rights" type="text" i18nkey="reporting.projectDeliverable.metadata.rights" help="reporting.projectDeliverable.metadata.rights.help" editable=editable /] 
    </div>  
  </div>
  
  [#-- Deliverable Publications Metadata --]  
  <div id="publicationQuestions" class="fullBlock publicationQuestions" style="display:block"> 
    <h1 class="contentTitle publicationQuestions" style="display:block">[@s.text name="reporting.projectDeliverable.deliverablePublications" /] </h1> 
    
    [#-- Open access status --]
    <div class="fullBlock">
      [@customForm.radioButtonGroup name="${params.deliverable.name}.publicationAccessStatus" label="" i18nkey="reporting.projectDeliverable.publicationAccessStatus" listName="openAccessStatuses" required=true editable=editable /]
    </div>
    
    [#-- Indicators for journal articles --]
    <div class="fullBlock ">
      <h6>[@s.text name="reporting.projectDeliverable.journalIndicators" /]:</h6>
      <div class="fullBlock verticallyCheckBox">
        [@customForm.checkbox name="${params.deliverable.name}.isiPublication" i18nkey="reporting.projectDeliverable.isiPublication"  value="true" editable=editable /]
        [@customForm.checkbox name="${params.deliverable.name}.narsCoauthor" i18nkey="reporting.projectDeliverable.narsCoauthor"  value="true" editable=editable /]
        [@customForm.checkbox name="${params.deliverable.name}.earthSystemCoauthor" i18nkey="reporting.projectDeliverable.earthSystemCoauthor" value="true" editable=editable /]
        <div class="clearfix"></div>
      </div>
    </div>
    
    [#-- Citation --]
    <div class="fullBlock">
      [@customForm.textArea name="${params.deliverable.name}.citation" i18nkey="reporting.projectDeliverable.metadata.citation" help="reporting.projectDeliverable.metadata.citation.help" required=true editable=editable /]
    </div>
    
    [#-- Publication --]
    <table id="alreadyDisseminated" class="default">
      <tbody>
        <tr>
          <td class="key">[@s.text name="reporting.projectDeliverable.acknowledgeCCAFS" /]</td> 
          <td class="value">[@deliverableTemplate.yesNoInput name="${params.deliverable.name}.acknowledgeCCAFS" editable=editable/]</td>
        </tr>
      </tbody>
    </table>
    <br />
    
    [#-- Publication is an output of which flagships --]
    <div class="fullBlock">
      <h6>[@s.text name="reporting.projectDeliverable.outputPublication" help="reporting.projectDeliverable.outputPublication.help" required=true /]</h6>
      <div class="checkboxGroup">
        [#if editable]
          [@s.fielderror cssClass="fieldError" fieldName="${params.deliverable.name}.relatedFlagships"/]
          [@s.checkboxlist name="${params.deliverable.name}.relatedFlagships" list="ipProgramFlagships" listKey="id" listValue="getComposedName()" value="${params.deliverable.name}.relatedFlagshipsIds" cssClass="checkbox" /]
        [#else]
          TODO: Options checked [@s.property value="${params.deliverable.name}.relatedFlagshipsIds"/]
        [/#if]
      </div>
    </div> 
      
  </div>
</div>