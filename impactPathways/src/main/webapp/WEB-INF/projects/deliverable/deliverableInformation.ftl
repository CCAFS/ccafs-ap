[#ftl]
<div id="deliverable-information" class="clearfix"> 
  [#if !editable && canEdit]
    <div class="editButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
  [#else]
    [#if canEdit && !newProject]
      <div class="viewButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>
    [/#if]
  [/#if]
  <h1 class="contentTitle">[@s.text name="planning.projectDeliverable.information" /] </h1>  
  <div class="fullBlock">
    [#-- Title --] 
    [@customForm.input name="${params.deliverable.name}.title" className="deliverableTitle limitWords-15" i18nkey="planning.deliverables.title" required=true editable=editable /]
  </div> 
  [#-- MOG  --]
  <div class="fullBlock chosen"> 
    [@customForm.select name="${params.deliverable.name}.output" label=""  disabled=false i18nkey="planning.deliverables.mog" listName="outputs" keyFieldName="id"  displayFieldName="description" required=true editable=editable /]
  </div> 
  <div class="fullBlock">
    [#assign deliverableType][#if deliverable.type??]${deliverable.type.category.id}[#else]-1[/#if][/#assign]
    [#assign deliverableSubType][#if deliverable.type??]${deliverable.type.id}[#else]-1[/#if][/#assign]
    [#-- Main Type --]
    <div class="halfPartBlock chosen"> 
      [@customForm.select name="mainType" value="${deliverableType}" i18nkey="planning.deliverables.mainType" listName="deliverableTypes" keyFieldName="id"  displayFieldName="name" required=true editable=editable /]
      [#if !editable]<div class="select"><p>${(deliverable.type.category.name)!}</p></div>[/#if]
    </div> 
    [#-- Sub Type --]
    <div class="halfPartBlock chosen">
      [#assign fieldEmpty] <div class="select"><p>[@s.text name="form.values.fieldEmpty" /]</p></div>[/#assign]
      [@customForm.select name="${params.deliverable.name}.type" value="${deliverableSubType}" i18nkey="planning.deliverables.subType" listName="" keyFieldName=""  displayFieldName="" required=true editable=editable /]
      [#if !editable]<div class="select"><p>[#if deliverable.typeOther??]${(deliverable.typeOther)!fieldEmpty}[#else]${(deliverable.type.name)!fieldEmpty}[/#if]</p></div>[/#if]
      <input type="hidden" id="subTypeSelected" value="${deliverableSubType}" />
      [#-- Specify other deliverable type--] 
      [@customForm.input name="${params.deliverable.name}.typeOther" value="${(deliverable.typeOther)!}" className="otherType" display=false showTitle=false i18nkey="planning.deliverables.specify" required=true disabled=true editable=editable /]          
    </div> 
  </div>
  
  [#-- Year  --]
  <div class="halfPartBlock chosen">
    [@customForm.select name="${params.deliverable.name}.year" value="${deliverable.year}" label=""  disabled=false i18nkey="planning.deliverables.year" listName="allYears" required=true editable=editable /]
    [#if !editable] <div class="select"><p>${deliverable.year}</p></div> [/#if]
  </div>
  
  [#-- -- -- REPORTING BLOCK -- -- --]
  [#if reportingCycle]
    [#-- Deliverable Status  --]
    <div class="fullBlock">
      <div class="halfPartBlock"> 
        [@customForm.select name="${params.deliverable.name}.status" label=""  disabled=false i18nkey="reporting.projectDeliverable.status" listName="statuses"  required=true editable=editable /]
      </div>
    </div>
    [#-- Status justification  --]
    <div class="fullBlock">
      [@customForm.textArea name="${params.deliverable.name}.statusDescription" i18nkey="reporting.projectDeliverable.statusJustification" editable=editable/]
    </div>
  [/#if]
  
  [#-- Deliverables table dialog --] 
  [@deliverableTemplate.deliverablesTypesTable types=deliverableTypes show=(canEdit && !action.canDelete() && !reportingCycle) /]
  
  [#-- Deliverable type description and message--]
  [#if canEdit && editable && !action.canDelete() && !reportingCycle]
  <div class="fullBlock">
    <div class="note left">
      [#if deliverable.type??]<p><b>Deliverable type description:</b> [@s.text name="${(deliverable.type.description)!}" /]</p>[/#if]
      <p>[@s.text name="planning.deliverables.disclaimerMessage" /]</p>
    </div>
  </div>
  [/#if]
</div>