[#ftl]
[#macro partnerSection projectPartners partnerTypes countries canEdit=true responsabilities=false canRemove=false]
  [#if projectPartners?has_content]
    [#list projectPartners as ap]    	
      <div id="projectPartner-${ap_index}" class="projectPartner borderBox">
        [#-- Partner identifier --]
        <input id="id" type="hidden" name="project.projectPartners[${ap_index}].id" value="${ap.id?c}" />  
          
        <h6>[@s.text name="preplanning.projectPartners.partner"] [@s.param name="0"]${ap_index}[/@s.param] [/@s.text]</h6> 
        [#-- Remove link for all partners --]
        <div class="removeLink">
          <img src="${baseUrl}/images/global/icon-remove.png" />
          <a id="removePartner-${ap_index}" href="" class="removePartner">[@s.text name="preplanning.projectPartners.removePartner" /]</a>
        </div>
        
         [#-- Partner type list --]
        <div class="halfPartBlock partnerTypeName chosen">
          [#-- Name attribute is not needed, we just need to load the value, not save it it. --]
          [@customForm.select name="" label="" disabled=!canEdit i18nkey="preplanning.projectPartners.partnerType" listName="partnerTypes" keyFieldName="id"  displayFieldName="name" className="partnerTypes" value="${ap.partner.type.id?c}" /]
        </div>
        
        [#-- Country list --]
        <div class="halfPartBlock countryListBlock chosen">
          [#-- Name attribute is not needed, we just need to load the value, not save it it. --]
          [@customForm.select name="" label="" disabled=!canEdit i18nkey="preplanning.projectPartners.country" listName="countries" keyFieldName="code"  displayFieldName="name" className="countryList" value="'${ap.partner.country.code}'" /]
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
        [#if responsabilities]
        [#-- Responsabilities --]
        <div class="fullBlock partnerResponsabilities chosen">        
          [@customForm.textArea name="project.projectPartners[${ap_index}].responsabilities" i18nkey="preplanning.projectPartners.responsabilities" required=true /]
        </div>
        [/#if]
         
      </div> <!-- End projectPartner-${ap_index} -->
    [/#list]
  [/#if]  
[/#macro]

[#macro partnerTemplate showResponsabilities=false canEdit=true ]

  <div id="projectPartnerTemplate" class="borderBox" style="display:none">
        [#-- Partner identifier --]
        <input id="id" type="hidden" name="" value="-1" />   
        <h6>[@s.text name="preplanning.projectPartners.partner"] [@s.param name="0"][/@s.param] [/@s.text]</h6> 
        [#-- Remove link for all partners --]
        <div class="removeLink">
          <img src="${baseUrl}/images/global/icon-remove.png" />
          <a id="removePartner" href="" class="removePartner">[@s.text name="preplanning.projectPartners.removePartner" /]</a>
        </div>
        
         [#-- Partner type list --]
        <div class="halfPartBlock partnerTypeName chosen">
          [@customForm.select name="" label="" disabled=!canEdit i18nkey="preplanning.projectPartners.partnerType" listName="partnerTypes" keyFieldName="id"  displayFieldName="name" className="partnerTypes"  /]
        </div>
        
        [#-- Country list --]
        <div class="halfPartBlock countryListBlock chosen">
          [@customForm.select name="" label="" disabled=!canEdit i18nkey="preplanning.projectPartners.country" listName="countries" keyFieldName="id"  displayFieldName="name" className="countryList"  /]
        </div>
        
        [#-- Partner Name --]
        <div class="fullBlock partnerName chosen">
          [@customForm.select name="partner" label=""  disabled=!canEdit i18nkey="preplanning.projectPartners.partner.name" listName="allPartners" keyFieldName="id"  displayFieldName="name" /]
        </div>
        
        [#-- Contact Name --] 
        <div class="halfPartBlock">
          [@customForm.input name="contactName" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.contactPersonName" required=true /]
        </div>
        
        [#-- Contact Email --]
        <div class="halfPartBlock">
          [@customForm.input name="contactEmail" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.contactPersonEmail" required=true /]
        </div>
        
        [#-- Responsabilities --]
        [#if showResponsabilities]
        <div class="fullBlock partnerResponsabilities chosen">        
          [@customForm.textArea name="responsabilities" i18nkey="preplanning.projectPartners.responsabilities" required=true /]
        </div>
        [/#if]
      </div> <!-- End projectPartner-Template -->
[/#macro]

[#macro projectLeader leader showResponsabilities=false canEdit=true]
  [#if leader?has_content]
      <div id="projectLeader" class="projectLeader borderBox">
        [#-- Organizations List --]
        <div class="fullBlock organizationName chosen">
          [#-- @customForm.select name="project.leader.currentInstitution" disabled=!canEdit i18nkey="preplanning.projectPartners.leader.partner.name" listName="allPartners" keyFieldName="id"  displayFieldName="name" /--]
          [@customForm.select name="project.expectedLeader.currentInstitution" disabled=!canEdit i18nkey="preplanning.projectPartners.leader.partner.name" listName="allPartners" keyFieldName="id"  displayFieldName="name" /]
        </div> 

        [#-- Leaders List (User List) - Email accounts --]
        <!-- div class="fullBlock">
          [@customForm.select name="project.leader" label="" disabled=!canEdit i18nkey="preplanning.projectPartners.leader.contactPersonEmail" listName="allProjectLeaders" keyFieldName="id"  displayFieldName="composedName" /]
        </div -->
        [#---------- START EXPECTED PROJECT LEADER ------------]
        
        [#-- Contact Name --] 
        <div class="halfPartBlock">
          [@customForm.input name="project.expectedLeader.firstName" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.contactPersonName" required=true /]
        </div>
        
        [#-- Contact Email --]
        <div class="halfPartBlock">
          [@customForm.input name="project.expectedLeader.email" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.contactPersonEmail" required=true /]
        </div>
        
        [#---------- END EXPECTED PROJECT LEADER ------------]
        
        [#-- Responsabilities --]
        [#if showResponsabilities]
        <div class="fullBlock leaderResponsabilities chosen">        
          [@customForm.textArea name="project.leaderResponsabilities" i18nkey="preplanning.projectPartners.leader.responsabilities" required=true /]
        </div>
        [/#if]
         
      </div> <!-- End projectLeader -->  
  [/#if]  
[/#macro]