[#ftl]
[#macro activityPartner activityPartners canRemove=true canEdit=true]
  [#if activityPartners?has_content]
    [#list activityPartners as ap] 
      <div id="activityPartner-${ap_index}" class="activityPartner borderBox">
        [#if canRemove]   
          <div id="removeActivityPartner"class="removeActivityPartner removeElement removeLink" title="[@s.text name="planning.activityPartner.removePartner" /]"></div>  
        [/#if] 
        <input type="hidden" value="${ap.id}" name="activityPartners[${ap_index}].id">
        <legend>[@s.text name="planning.activityPartner.partner"][@s.param name="0"] <span id="partnerIndex">${ap_index+1}</span>[/@s.param] [/@s.text]</legend>
        [#-- Organizations List --]
        <div class="fullBlock chosen "> 
          [@customForm.select name="activity.activityPartners[${ap_index}].partner" i18nkey="planning.activityPartner.organization" listName="allPartners" keyFieldName="id" displayFieldName="composedName" /]
        </div> 
        
        [#-- Filters --]
        [#if canEdit]
          <div class="filters-link">[@s.text name="preplanning.projectPartners.filters" /]</div>
          <div class="filters-content">
            [#-- Partner type list --]
            <div class="halfPartBlock partnerTypeName chosen">
              [#-- Name attribute is not needed, we just need to load the value, not save it it. --]
              [@customForm.select name="" label="" disabled=!canEdit i18nkey="preplanning.projectPartners.partnerType" listName="partnerTypes" keyFieldName="id"  displayFieldName="name" className="partnerTypes" value="${ap.partner.type.id?c}" /]
            </div>
            [#-- Country list --]
            <div class="halfPartBlock countryListBlock chosen">
              [#-- Name attribute is not needed, we just need to load the value, not save it it. --]
              [@customForm.select name="" label="" disabled=!canEdit i18nkey="preplanning.projectPartners.country" listName="countries" keyFieldName="id"  displayFieldName="name" className="countryList" value="'${ap.partner.country.code}'" /]
            </div>
          </div>
        [/#if]
        
        [#-- Contact Name --] 
        <div class="halfPartBlock">
          [@customForm.input name="activity.activityPartners[${ap_index}].contactName" type="text" i18nkey="planning.activityPartner.contactName" required=true /]
        </div>
        [#-- Contact Email --]
        <div class="halfPartBlock">
          [@customForm.input name="activity.activityPartners[${ap_index}].contactEmail" type="text" i18nkey="planning.activityPartner.contactEmail" required=true /]
        </div>
        [#-- Contribution --]
        <div class="fullBlock partnerContribution">
          [@customForm.textArea name="activity.activityPartners[${ap_index}].contribution" i18nkey="planning.activityPartner.contribution" required=true /]
        </div>
      </div>
    [/#list]
  [/#if]
[/#macro]

[#macro partnerTemplate canEdit=true canRemove=true]
  <div id="activityPartnerTemplate" class="borderBox" style="display:none">
    [#if canRemove]   
      <div id="removeActivityPartner"class="removeActivityPartner removeElement removeLink" title="[@s.text name="planning.activityPartner.removePartner" /]"></div>  
    [/#if]
    <legend>[@s.text name="planning.activityPartner.partner"][@s.param name="0"] #<span id="partnerIndex">0</span>[/@s.param] [/@s.text]</legend>
    <input type="hidden" value="-1" name="].id">
    [#-- Organizations List --]
    <div class="fullBlock chosen "> 
      [@customForm.select name="partner" listName="allPartners" i18nkey="planning.activityPartner.organization" keyFieldName="id" displayFieldName="composedName" /]
    </div> 
    
    [#-- Filters --]
    [#if canEdit]
      <div class="filters-link">[@s.text name="preplanning.projectPartners.filters" /]</div>
      <div class="filters-content">
        [#-- Partner type list --]
        <div class="halfPartBlock partnerTypeName chosen">
          [#-- Name attribute is not needed, we just need to load the value, not save it it. --]
          [@customForm.select name="" label="" disabled=!canEdit i18nkey="preplanning.projectPartners.partnerType" listName="partnerTypes" keyFieldName="id"  displayFieldName="name" className="partnerTypes"  /]
        </div>
        [#-- Country list --]
        <div class="halfPartBlock countryListBlock chosen">
          [#-- Name attribute is not needed, we just need to load the value, not save it it. --]
          [@customForm.select name="" label="" disabled=!canEdit i18nkey="preplanning.projectPartners.country" listName="countries" keyFieldName="id"  displayFieldName="name" className="countryList" /]
        </div>
      </div>
    [/#if]
    
    [#-- Contact Name --] 
    <div class="halfPartBlock">
      [@customForm.input name="contactName" type="text" i18nkey="planning.activityPartner.contactName" required=true /]
    </div>
    [#-- Contact Email --]
    <div class="halfPartBlock">
      [@customForm.input name="contactEmail" type="text" i18nkey="planning.activityPartner.contactEmail" required=true /]
    </div>
    [#-- Contribution --]
    <div class="fullBlock partnerContribution">
      [@customForm.textArea name="contribution" i18nkey="planning.activityPartner.contribution" required=true /]
    </div>
  </div>
[/#macro]