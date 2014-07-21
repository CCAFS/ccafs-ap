[#ftl]
[#macro partnerSection projectPartners partnerTypes countries canEdit=true canRemove=false]
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
          [@customForm.select name="partnerTypeList" label="" disabled=!canEdit i18nkey="preplanning.projectPartners.partnerType" listName="partnerTypes" keyFieldName="id"  displayFieldName="name" className="partnerTypes" value="${ap.partner.type.id?c}" /]
        </div>
        
        [#-- Country list --]
        <div class="halfPartBlock countryListBlock chosen">
          [@customForm.select name="countryList" label="" disabled=!canEdit i18nkey="preplanning.projectPartners.country" listName="countries" keyFieldName="id"  displayFieldName="name" className="countryList" value="'${ap.partner.country.id}'" /]
        </div>
        
        [#-- Partner Name --]
        <div class="fullBlock partnerName chosen">
          [@customForm.select name="project.projectPartners[${ap_index}].partner" label=""  disabled=!canEdit i18nkey="preplanning.projectPartners.partner.name" listName="allPartners" keyFieldName="id"  displayFieldName="name" /]
        </div>
        
        [#-- Contact Name --]
        <div class="halfPartBlock">
          [@customForm.input name="project.projectPartners[${ap_index}].contactName" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.contactPersonName" required=true /]
        </div>
        
        [#-- Contact Email --]
        <div class="halfPartBlock">
          [@customForm.input name="project.projectPartners[${ap_index}].contactEmail" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.contactPersonEmail" required=true /]
        </div>
        
        [#-- Responsabilities --]
        <div class="fullBlock partnerResponsabilities chosen">        
          [@customForm.textArea name="project.projectPartners[${ap_index}].responsabilities" i18nkey="preplanning.projectPartners.responsabilities" required=true /]
        </div>
        
        <hr />
      </div> <!-- End projectPartner-${ap_index} -->
    [/#list]
  [/#if]  
[/#macro]

[#macro projectLeader leader canEdit=true]
  [#if leader?has_content]
      <div id="projectLeader" class="projectPartner">
        [#-- Partner identifier --]
        <input type="hidden" name="leader.id" value="${leader.id?c}" />        

        [#-- Partner Name --] 
        <div class="fullBlock partnerName chosen">
          [@customForm.select name="project.leader.partner" disabled=!canEdit label="" i18nkey="preplanning.projectPartners.leader.partner.name" listName="allPartners" keyFieldName="id"  displayFieldName="name" /]
        </div>

        [#-- Contact Name --]
        <div class="halfPartBlock">
          [@customForm.input name="project.leader.contactName" disabled=!canEdit type="text" i18nkey="preplanning.projectPartners.leader.contactPersonName" required=true /]
        </div>

        [#-- Contact Email --]
        <div class="halfPartBlock">
          [@customForm.input name="project.leader.contactEmail" disabled=!canEdit type="text" i18nkey="preplanning.projectPartners.leader.contactPersonEmail" required=true /]
        </div>
        
        [#-- Responsabilities --]
        <div class="fullBlock leaderResponsabilities chosen">        
          [@customForm.textArea name="project.leader.responsabilities" i18nkey="preplanning.projectPartners.leader.responsabilities" required=true /]
        </div>
        
        <hr />
      </div> <!-- End projectLeader -->  
  [/#if]  
[/#macro]