[#ftl]
[#macro activityPartner activityPartners canRemove=true]
  [#if activityPartners?has_content]
    [#list activityPartners as ap] 
      <div id="activityPartner-${ap_index}" class="activityPartner borderBox">
         [#if canRemove]   
         <div id="removeActivityPartner"class="removeElement removeLink" title="[@s.text name="planning.activityPartner.removePartner" /]"></div>  
         [/#if] 
        <input type="hidden" value="${ap.id}" name="activityPartners[${ap_index}].id">
        [#-- Organizations List --]
        <div class="fullBlock chosen ">
          <h2><b>[@s.text name="planning.activityPartner.partner"][@s.param name="0"] <span id="partnerIndex">${ap_index+1}</span>[/@s.param] [/@s.text]</b></h2>
          [@customForm.select name="activityPartners[${ap_index}].partner.id" listName="allPartners" keyFieldName="id" displayFieldName="name" /]
        </div> 
        [#-- Contact Name --] 
        <div class="halfPartBlock">
          [@customForm.input name="activityPartners[${ap_index}].contactName" type="text" i18nkey="planning.activityPartner.contactName" required=true /]
        </div>
        [#-- Contact Email --]
        <div class="halfPartBlock">
          [@customForm.input name="activityPartners[${ap_index}].contactEmail" type="text" i18nkey="planning.activityPartner.contactEmail" required=true /]
        </div>
        [#-- Contribution --]
        <div class="fullBlock partnerContribution">
          [@customForm.textArea name="activityPartners[${ap_index}].contribution" i18nkey="planning.activityPartner.contribution" required=true /]
        </div>
      </div>
    [/#list]
  [/#if]
[/#macro]

[#macro partnerTemplate canEdit=true canRemove=true]
   <div id="activityPartner-${ap_index}" class="activityPartner borderBox">
   [#if canRemove]   
   <div id="removeActivityPartner"class="removeElement removeLink" title="[@s.text name="planning.activityPartner.removePartner" /]"></div>  
   [/#if] 
    
    <input type="hidden" value="${ap.id}" name="activityPartners[${ap_index}].id">
    [#-- Organizations List --]
    <div class="fullBlock chosen ">
      <h2><b>[@s.text name="planning.activityPartner.partner"][@s.param name="0"] <span id="partnerIndex">${ap_index+1}</span>[/@s.param] [/@s.text]</b></h2>
      [@customForm.select name="activityPartners[${ap_index}].partner.id" listName="allPartners" keyFieldName="id" displayFieldName="name" /]
    </div> 
    [#-- Contact Name --] 
    <div class="halfPartBlock">
      [@customForm.input name="activityPartners[${ap_index}].contactName" type="text" i18nkey="planning.activityPartner.contactName" required=true /]
    </div>
    [#-- Contact Email --]
    <div class="halfPartBlock">
      [@customForm.input name="activityPartners[${ap_index}].contactEmail" type="text" i18nkey="planning.activityPartner.contactEmail" required=true /]
    </div>
    [#-- Contribution --]
    <div class="fullBlock partnerContribution">
      [@customForm.textArea name="activityPartners[${ap_index}].contribution" i18nkey="planning.activityPartner.contribution" required=true /]
    </div>
  </div>
[/#macro]