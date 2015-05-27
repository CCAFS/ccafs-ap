[#ftl]
[#macro partnerSection projectPartners partnerTypes countries canEdit=true canRemove=true responsabilities=false ppaPartners=false]
  [#if projectPartners?has_content]
    [#list projectPartners as ap]    
      [#if ap.partner.PPA && ppaPartners] 
        [@partner ap ap_index canEdit=canEdit canRemove=canRemove responsabilities=responsabilities /]
      [#elseif !ap.partner.PPA && !ppaPartners] 
        [@partner ap ap_index canEdit=canEdit canRemove=canRemove responsabilities=responsabilities /]
      [/#if]
    [/#list]
  [/#if]  
[/#macro]

[#macro partner ap ap_index canEdit=true canRemove=true responsabilities=false  ]
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
          [#assign countryID][#if ap.partner.country?has_content]${ap.partner.country.code}[#else]-1[/#if][/#assign]
          [#-- Name attribute is not needed, we just need to load the value, not save it it. --]
          [@customForm.select name="" label="" disabled=!canEdit i18nkey="preplanning.projectPartners.country" listName="countries" keyFieldName="id"  displayFieldName="name" className="countryList" value="'${countryID}'" /]
        </div>
      </div>
    [/#if]        
    [#-- Contact Name --] 
    <div class="fullPartBlock">
      [#-- Contact Person information is going to come from the users table, not from project_partner table (refer to the table project_partners in the database) --] 
      [#--]@customForm.input name="project.projectPartners[${ap_index}].contactName" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.contactPersonName" required=true /--]
    </div> 
    [#-- Contact Email --]
    <div class="fullPartBlock clearfix">
      [#-- Contact Person information is going to come from the users table, not from project_partner table (refer to the table project_partners in the database) --] 
      [@customForm.input name="project.projectPartners[${ap_index}].contactEmail" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.contactPersonEmail" required=true /]
      <div class="searchUser">[@s.text name="form.buttons.searchUser" /]</div>
    </div>  
    [#-- Responsabilities --]
    [#if responsabilities]  
    <div class="fullPartBlock partnerResponsabilities chosen">        
      [@customForm.textArea name="project.projectPartners[${ap_index}].responsabilities" i18nkey="preplanning.projectPartners.responsabilities" required=true /]
    </div>
    [/#if]
    [#-- Indicate which PPA Partners for second level partners --]
    [#if !ap.partner.PPA]
    <div class="fullPartBlock">        
      <h6>[@s.text name="preplanning.projectPartners.indicatePpaPartners" /]</h6>
    </div>
    [/#if]
  </div> <!-- End projectPartner-${ap_index} -->
[/#macro]

[#macro partnerTemplate showResponsabilities=false canEdit=true isSecondLvlPartners=false ]
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
        <div class="fullPartBlock clearfix">
          [@customForm.input name="contactName" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.contactPersonName" required=true /]
          <div class="searchUser">[@s.text name="form.buttons.searchUser" /]</div>
        </div>
        [#-- Responsabilities --]
        [#if showResponsabilities]
        <div class="fullPartBlock partnerResponsabilities chosen">        
          [@customForm.textArea name="responsabilities" i18nkey="preplanning.projectPartners.responsabilities" required=true /]
        </div>
        [/#if]
        [#-- Indicate which PPA Partners --]
        [#if isSecondLvlPartners]
        <div class="fullPartBlock">        
          <h6>[@s.text name="preplanning.projectPartners.indicatePpaPartners" /]</h6>
        </div>
        [/#if]
      </div> <!-- End projectPartner-Template -->
[/#macro]

[#macro projectLeader leader showResponsabilities=false canEdit=true]
  [#if leader?has_content]
      <div id="projectLeader" class="projectLeader clearfix">
          [#-- Lead List --]
          <div class="fullPartBlock organizationName chosen">
            [@customForm.select name="project.expectedLeader.currentInstitution" disabled=!canEdit i18nkey="preplanning.projectPartners.leader.institutionName" listName="allPartners" keyFieldName="id"  displayFieldName="getComposedName()" /]
          </div> 
          [#-- Project Leader contact --] 
          <div class="fullPartBlock clearfix"> 
            [@customForm.input name="preplanning.projectPartners.projectLeader" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.projectLeader" required=true readOnly=true/]
            <div class="searchUser">[@s.text name="form.buttons.searchUser" /]</div>
          </div>
          [#-- Project Coordinator --] 
          <div class="fullPartBlock clearfix">
            [@customForm.input name="preplanning.projectPartners.projectCoordinator" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.projectCoordinator"  readOnly=true/]
            <div class="searchUser">[@s.text name="form.buttons.searchUser" /]</div>
          </div> 
          [#-- Responsabilities --]
          [#if showResponsabilities]
          <div class="fullBlock leaderResponsabilities chosen">        
            [@customForm.textArea name="project.leaderResponsabilities" i18nkey="preplanning.projectPartners.leader.responsabilities" required=true /]
          </div>
          [/#if] 
      </div> <!-- End projectLeader -->  
  [/#if]  
[/#macro]