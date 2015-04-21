[#ftl]
[#macro partnerSection projectPartners partnerTypes countries canEdit=true canRemove=true responsabilities=false]
  [#if projectPartners?has_content]
    [#list projectPartners as ap]    	
      <div id="projectPartner-${ap_index}" class="projectPartner borderBox">
        [#-- Partner identifier --]
        <input id="id" type="hidden" name="project.projectPartners[${ap_index}].id" value="${ap.id?c}" />
        <legend>[@s.text name="preplanning.projectPartners.partner"][@s.param name="0"] <span id="partnerIndex">${ap_index+1}</span>[/@s.param] [/@s.text]</legend>
        [#if canRemove && canEdit]
          [#-- Remove link for all partners --]
          <div class="removeLink">
            <div id="removePartner" class="removePartner removeElement removeLink" title="[@s.text name="preplanning.projectPartners.removePartner" /]"></div>
          </div> 
              
        [/#if] 
        [#-- Partner Name --]
        <div class="fullPartBlock partnerName chosen">
          [@customForm.select name="project.projectPartners[${ap_index}].partner" label=""  disabled=!canEdit i18nkey="preplanning.projectPartners.partner.name" listName="allPartners" keyFieldName="id"  displayFieldName="getComposedName()" /]
        </div>
        
        [#-- Filters --]
        [#if canEdit && ap.id != -1]
          <div class="filters-link">[@s.text name="preplanning.projectPartners.filters" /]</div>
          <div class="filters-content">
            [#-- Partner type list --]
            <div class="halfPartBlock partnerTypeName chosen">
              [#-- Name attribute is not needed, we just need to load the value, not save it it. --]
              [@customForm.select name="" label="" disabled=!canEdit i18nkey="preplanning.projectPartners.partnerType" listName="partnerTypes" keyFieldName="id"  displayFieldName="name" className="partnerTypes" value="${ap.partner.type.id?c}" /]
            </div>
            [#-- Country list --]
            <div class="halfPartBlock countryListBlock chosen">
              [#-- Some partners like the Regional Programs, don't have country associated --]
              [#assign countryID]
                [#if ap.partner.country?has_content]${ap.partner.country.code}[#else]-1[/#if]
              [/#assign]
              [#-- Name attribute is not needed, we just need to load the value, not save it it. --]
              [@customForm.select name="" label="" disabled=!canEdit i18nkey="preplanning.projectPartners.country" listName="countries" keyFieldName="id"  displayFieldName="name" className="countryList" value="'${countryID}'" /]
            </div>
          </div>
        [/#if]
        
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
          <div class="fullPartBlock partnerResponsabilities chosen">        
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
        <legend>[@s.text name="preplanning.projectPartners.partner"][@s.param name="0"]<span id="partnerIndex">{0}</span>[/@s.param] [/@s.text]</legend> 
        [#-- Remove link for all partners --]
        <div class="removeLink">
          <div id="removePartner" class="removePartner removeElement removeLink" title="[@s.text name="preplanning.projectPartners.removePartner" /]"></div>
        </div>
        
        [#-- Partner Name --]
        <div class="fullPartBlock partnerName chosen">
          [@customForm.select name="partner" label=""  disabled=!canEdit i18nkey="preplanning.projectPartners.partner.name" listName="allPartners" keyFieldName="id"  displayFieldName="getComposedName()" /]
        </div>
        
        <div class="filters-link">[@s.text name="preplanning.projectPartners.filters" /]</div>
        <div class="filters-content">
          [#-- Partner type list --]
          <div class="halfPartBlock partnerTypeName chosen">
            [@customForm.select name="" label="" disabled=!canEdit i18nkey="preplanning.projectPartners.partnerType" listName="partnerTypes" keyFieldName="id"  displayFieldName="name" className="partnerTypes"  /]
          </div>
          
          [#-- Country list --]
          <div class="halfPartBlock countryListBlock chosen">
            [@customForm.select name="" label="" disabled=!canEdit i18nkey="preplanning.projectPartners.country" listName="countries" keyFieldName="id"  displayFieldName="name" className="countryList"  /]
          </div>
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
        <div class="fullPartBlock partnerResponsabilities chosen">        
          [@customForm.textArea name="responsabilities" i18nkey="preplanning.projectPartners.responsabilities" required=true /]
        </div>
        [/#if]
      </div> <!-- End projectPartner-Template -->
[/#macro]

[#macro projectLeader leader showResponsabilities=false canEdit=true]
  [#if leader?has_content]
      [#-- Explanatory message --]
      [#if expected]
        [@s.text name="preplanning.projectPartners.expectedLeader.explanation" /]
      [#else]
        [#-- Only show the message to Admins, FPLs, RPLs and POs --]
        [#if saveable && fullEditable]
          [@s.text name="preplanning.projectPartners.leader.explanation"]
            [@s.param]
              [#-- Mailto link --]
              [@s.text name="preplanning.projectPartners.leader.mailto"]
                [#-- Subject --]
                [@s.param]
                  [@s.text name="preplanning.projectPartners.leader.mailto.subject"]
                    [@s.param]${project.id?c}[/@s.param]
                  [/@s.text]
                [/@s.param]
                [#-- START Body --]
                [@s.param]
                  [@s.text name="preplanning.projectPartners.leader.mailto.body"]
                    [@s.param]${currentUser.firstName} ${currentUser.lastName}[/@s.param]
                  [/@s.text]
                [/@s.param]
                [#-- END Body --]
                [@s.param][@s.text name="preplanning.projectPartners.leader.mailto.body" /][/@s.param]
              [/@s.text]
            [/@s.param] 
          [/@s.text]
        [/#if]
      [/#if]
      <div id="projectLeader" class="projectLeader borderBox clearfix">
        [#if expected]
          [#-- Organizations List --]
          <div class="fullPartBlock organizationName chosen">
            [@customForm.select name="project.expectedLeader.currentInstitution" disabled=!canEdit i18nkey="preplanning.projectPartners.leader.institutionName" listName="allPartners" keyFieldName="id"  displayFieldName="getComposedName()" /]
          </div> 
          [#-- Contact First Name --] 
          <div class="halfPartBlock">
            [@customForm.input name="project.expectedLeader.firstName" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.leader.firstName" required=true /]
          </div>
          [#-- Contact Last Name --] 
          <div class="halfPartBlock">
            [@customForm.input name="project.expectedLeader.lastName" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.leader.lastName" required=true /]
          </div>
          [#-- Contact Email --]
          <div class="halfPartBlock">
            [@customForm.input name="project.expectedLeader.email" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.leader.email" required=true /]
          </div>
        [#else]          
          <div class="halfPartBlock">
            <h3>[@s.text name='preplanning.projectPartners.leader.firstName' /]</h3> ${leader.firstName} 
          </div>
          <div class="halfPartBlock">
            <h3>[@s.text name='preplanning.projectPartners.leader.lastName' /]</h3> ${leader.lastName}
          </div>
          <div class="halfPartBlock">
            <h3>[@s.text name='preplanning.projectPartners.leader.email' /]</h3> ${leader.email}
          </div>
          <div class="halfPartBlock">
            <h3>[@s.text name='preplanning.projectPartners.leader.institutionName' /]</h3> ${leader.currentInstitution.name}
          </div>
        [/#if]
        
        [#-- Responsabilities --]
        [#if showResponsabilities]
        <div class="fullBlock leaderResponsabilities chosen">        
          [@customForm.textArea name="project.leaderResponsabilities" i18nkey="preplanning.projectPartners.leader.responsabilities" required=true /]
        </div>
        [/#if]
         
      </div> <!-- End projectLeader -->  
  [/#if]  
[/#macro]