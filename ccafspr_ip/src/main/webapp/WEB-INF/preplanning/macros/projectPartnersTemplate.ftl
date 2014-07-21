[#ftl]
[#macro partnerSection projectPartners partnerTypes countries canRemove=false]
  [#if projectPartners?has_content]
    [#list projectPartners as ap]    	
      <div id="projectPartner-${ap_index}" class="projectPartner">
        [#-- Partner identifier --]
        <input type="hidden" name="projectPartners[${ap_index}].id" value="${ap.id?c}" />        
        
        [#-- Remove link for all partners --]
        <div class="removeLink">
          <img src="${baseUrl}/images/global/icon-remove.png" />
          <a id="removePartner-${ap_index}" href="" class="removePartner">[@s.text name="preplanning.projectPartners.removePartner" /]</a>
        </div>
        
         [#-- Partner type list --]
        <div class="halfPartBlock partnerTypeName chosen">
          [@customForm.select name="partnerTypeList" label="" i18nkey="preplanning.projectPartners.partnerType" listName="partnerTypes" keyFieldName="id"  displayFieldName="name" className="partnerTypes" value="${ap.partner.type.id?c}" /]
        </div>
        
        [#-- Country list --]
        <div class="halfPartBlock countryListBlock chosen">
          [@customForm.select name="countryList" label="" i18nkey="preplanning.projectPartners.country" listName="countries" keyFieldName="id"  displayFieldName="name" className="countryList" value="'${ap.partner.country.id}'" /]
        </div>
        
        [#-- NOT WORKING YET - Partner Name --]
        <div class="fullBlock partnerName chosen">
          [@customForm.select name="project.projectPartners[${ap_index}].partner" label="" i18nkey="preplanning.projectPartners.partner.name" listName="allPartners" keyFieldName="id"  displayFieldName="name" /]
        </div>
        
      </div> <!-- End activityPartner-${ap_index} -->
    [/#list]
  [/#if]  
[/#macro]

[#macro partnerSectionOld partners canRemove=false]
  [#if partners?has_content]
    [#list partners as ap]
      <div id="partner-${ap_index}" class="projectPartner">
        [#-- Partner identifier --]
        <input type="hidden" name="partners[${ap_index}].id" value="${ap.id?c}" />
        
        [#-- Remove link for all partners --]
        <div class="removeLink">
          <img src="${baseUrl}/images/global/icon-remove.png" />
          <a id="removePartner-${ap_index}" href="" class="removePartner">[@s.text name="preplanning.projectPartners.removePartner" /]</a>
        </div>

        [#-- Partner type list --]
        <div class="halfPartBlock partnerTypeName chosen">
          [@customForm.select name="partnerTypeList" label="" i18nkey="planning.activityPartners.partnerType" listName="partnerTypes" keyFieldName="id"  displayFieldName="name" className="partnerTypes" value="activity.activityPartners[${ap_index}].partner.type.id" /]
        </div>

        [#-- Country list --]
        <div class="halfPartBlock countryListBlock chosen">
          [@customForm.select name="countryList" label="" i18nkey="planning.activityPartners.country" listName="countries" keyFieldName="id"  displayFieldName="name" className="countryList" value="activity.activityPartners[${ap_index}].partner.country.id" /]
        </div>

        [#-- Partner Name --]
        <div class="fullBlock partnerName chosen">
          [@customForm.select name="activity.activityPartners[${ap_index}].partner" label="" i18nkey="planning.activityPartners.partner.name" listName="partners" keyFieldName="id"  displayFieldName="name" /]
        </div>

        [#-- Contact Name --]
        <div class="halfPartBlock">
          [@customForm.input name="activity.activityPartners[${ap_index}].contactName" type="text" i18nkey="planning.activityPartners.contactPersonName" required=true /]
        </div>

        [#-- Contact Email --]
        <div class="halfPartBlock">
          [@customForm.input name="activity.activityPartners[${ap_index}].contactEmail" type="text" i18nkey="planning.activityPartners.contactPersonEmail" required=true /]
        </div> 
        <hr />
      </div> <!-- End activityPartner-${ap_index} -->
    [/#list]
  [/#if]  
[/#macro]