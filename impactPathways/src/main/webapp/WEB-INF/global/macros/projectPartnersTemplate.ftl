[#ftl]
[#macro partnerSection projectPartners ap_name partnerTypes countries ppaPartner=false canEdit=true canRemove=true responsabilities=false ]
  [#if projectPartners?has_content]
    [#list projectPartners as ap]   
        [@partner ap ap_index ap_name ppaPartner canEdit=canEdit canRemove=canRemove responsabilities=responsabilities  /]
    [/#list]
  [#else]  
    This project not has partners
  [/#if]  
[/#macro]

[#macro partner ap ap_index ap_name isPPA=false canEdit=true canRemove=true responsabilities=false   ]
  <div id="projectPartner-${ap_index}" class="projectPartner borderBox">
    [#-- Partner identifier --]
    <input id="id" type="hidden" name="${ap_name}[${ap_index}].id" value="${ap.id?c}" />
    [#assign nameLegend]${isPPA?string("preplanning.projectPartners.ppaPartner", "preplanning.projectPartners.partner")}[/#assign]
    <legend>[@s.text name=nameLegend][@s.param name="0"] <span id="partnerIndex">${ap_index+1}</span>[/@s.param] [/@s.text]</legend>
    [#if canRemove && canEdit]
      [#-- Remove link for all partners --]
      <div class="removeLink">
        <div id="removePartner" class="removePartner removeElement removeLink" title="[@s.text name="preplanning.projectPartners.removePartner" /]"></div>
      </div>
    [/#if] 
    [#-- Partner Name --]
    <div class="fullPartBlock partnerName chosen">
      [@customForm.select name="${ap_name}[${ap_index}].partner" label=""  disabled=!canEdit i18nkey="preplanning.projectPartners.partner.name" listName="allPartners" keyFieldName="id"  displayFieldName="getComposedName()" value="${ap.institution.id?c}" /]
    </div>
    [#-- Filters --]
    [#if canEdit && ap.id != -1]
      <div class="filters-link">[@s.text name="preplanning.projectPartners.filters" /]</div>
      <div class="filters-content">
        [#-- Partner type list --]
        <div class="halfPartBlock partnerTypeName chosen">
          [#-- Name attribute is not needed, we just need to load the value, not save it it. --]
          [@customForm.select name="" label="" disabled=!canEdit i18nkey="preplanning.projectPartners.partnerType" listName="partnerTypes" keyFieldName="id"  displayFieldName="name" className="partnerTypes" value="${ap.institution.type.id?c}" /]
        </div>
        [#-- Country list --]
        <div class="halfPartBlock countryListBlock chosen">
          [#-- Some partners like the Regional Programs, don't have country associated --]
          [#assign countryID][#if ap.country?has_content]${ap.country.code}[#else]-1[/#if][/#assign]
          [#-- Name attribute is not needed, we just need to load the value, not save it it. --]
          [@customForm.select name="" label="" disabled=!canEdit i18nkey="preplanning.projectPartners.country" listName="countries" keyFieldName="id"  displayFieldName="name" className="countryList" value="'${ap.institution.country.id?c}'" /]
        </div>
      </div>
    [/#if]     
    [#-- Contact Person --]
    <div class="fullPartBlock clearfix">
      [#-- Contact Person information is going to come from the users table, not from project_partner table (refer to the table project_partners in the database) --] 
      [@customForm.input name="" value="${ap.user.composedName}" className="userName" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.contactPersonEmail" required=true readOnly=true/]
      <input class="userId" type="hidden" name="preplanning.projectPartners.projectLeader" value="${ap.user.id}">
      <div class="searchUser">[@s.text name="form.buttons.searchUser" /]</div>
    </div>  
    [#-- Responsabilities --]
    [#if responsabilities]  
    <div class="fullPartBlock partnerResponsabilities chosen">        
      [@customForm.textArea name="${ap_name}[${ap_index}].responsabilities" value="${ap.responsabilities}" i18nkey="preplanning.projectPartners.responsabilities" required=true /]
    </div>
    [/#if]
    [#-- Indicate which PPA Partners for second level partners --]
    [#if !isPPA]
    <div class="fullPartBlock">      
      <div class="ppaPartnersList panel primary">
        <div class="panel-head">
          [@s.text name="preplanning.projectPartners.indicatePpaPartners" /]
        </div>
        [#assign ppaPartnersList = [
          {"id":"1", "name":"PPA Partner #1 Bioversity International"},
          {"id":"2", "name":"PPA Partner #2 CIAT - Centro Internacional de Agricultura tropial"},
          {"id":"3", "name":"PPA Partner #3 CIP - Centro Internacional de la papa"}
        ]/]
        <div class="panel-body">
          <ul> 
            [#list ppaPartnersList as ppaPartner]
              <li class="[#if !ppaPartner_has_next]last[/#if]">${ppaPartner.name}<span class="listButton remove">Remove</span></li>
            [/#list]
          </ul>
          [@customForm.select name="" label="" disabled=!canEdit i18nkey="" listName="partnerTypes" keyFieldName="id"  displayFieldName="name" className="ppaPartnersSelect" value="" /]
        </div>
      </div> 
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
            [@customForm.select name="project.leader.institution" disabled=!canEdit i18nkey="preplanning.projectPartners.leader.institutionName" listName="allPartners" keyFieldName="id"  displayFieldName="getComposedName()" value="${project.leader.institution.id?c}" /]
          </div> 
          [#-- Project Leader contact --] 
          <div class="fullPartBlock clearfix">
            [@customForm.input name="project.leader.composedName" className="userName" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.projectLeader" required=true readOnly=true/]
            <input class="userId" type="hidden" name="preplanning.projectPartners.projectLeader" value="${project.leader.id}">
            <div class="searchUser">[@s.text name="form.buttons.searchUser" /]</div>
          </div>
          [#-- Project Coordinator --] 
          <div class="fullPartBlock clearfix">
            [@customForm.input name="project.coordinator.composedName" className="userName" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.projectCoordinator"  readOnly=true/]
            <input class="userId" type="hidden" name="preplanning.projectPartners.projectCoordinator" value="${project.coordinator.id}">
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