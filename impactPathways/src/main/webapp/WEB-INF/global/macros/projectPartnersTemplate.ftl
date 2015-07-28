[#ftl]
[#macro partnerSection projectPartners ap_name editable partnerTypes countries ppaPartner=false isBilateral=false responsabilities=false ]
  [#if projectPartners?has_content]
    [#list projectPartners as ap]   
        [@partner ap ap_index ap_name editable ppaPartner isBilateral responsabilities=responsabilities  /]
    [/#list]
  [#else]  
    [#if !editable][@s.text name="planning.projectPartners.emptyPartners" /][/#if]
  [/#if]  
[/#macro]

[#macro partner ap ap_index ap_name  editable=false isPPA=false isBilateral=true responsabilities=false   ]
  <div id="projectPartner-${ap.id}" class="projectPartner borderBox">
    [#if (!editable && canEdit)]
      <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]#projectPartner-${ap.id}">[@s.text name="form.buttons.edit" /]</a></div>
    [/#if]
    [#-- Partner identifier --]
    <input id="id" type="hidden" name="${ap_name}[${ap_index}].id" value="${ap.id?c}" />
    [#assign nameLegend]${isPPA?string("preplanning.projectPartners.ppaPartner", "preplanning.projectPartners.partner")}[/#assign]
    <legend>[@s.text name=nameLegend][@s.param name="0"] <span id="partnerIndex">${ap_index+1}</span>[/@s.param] [/@s.text]</legend>
    [#if editable]
      [#-- Remove link for all partners --]
      <div class="removeLink">
        <div id="removePartner" class="removePartner removeElement removeLink" title="[@s.text name="preplanning.projectPartners.removePartner" /]"></div>
      </div>
    [/#if] 
    [#-- Partner Name --]
    <div class="fullPartBlock partnerName chosen">
      [#assign institutionList]${isPPA?string("allPPAPartners", "allPartners")}[/#assign]
      [@customForm.select name="${ap_name}[${ap_index}].institution" value="${ap.institution.id?c}" label=""  disabled=!editable i18nkey="preplanning.projectPartners.partner.name" listName=institutionList keyFieldName="id"  displayFieldName="getComposedName()" editable=editable /]
    </div>
    [#-- Filters --]
    [#if editable && !isPPA]
      [#if ap.id != -1]
        <div class="filters-link">[@s.text name="preplanning.projectPartners.filters" /]</div>
        <div class="filters-content">
          [#-- Partner type list --]
          <div class="halfPartBlock partnerTypeName chosen">
            [#-- Name attribute is not needed, we just need to load the value, not save it it. --]
            [@customForm.select name="" label="" disabled=!editable i18nkey="preplanning.projectPartners.partnerType" listName="partnerTypes" keyFieldName="id"  displayFieldName="name" className="partnerTypes" value="${(ap.institution.type.id)!-1}" /]
          </div>
          [#-- Country list --]
          <div class="halfPartBlock countryListBlock chosen">
            [#-- Some partners like the Regional Programs, don't have country associated --]
            [#assign countryID][#if ap.country?has_content]${ap.country.code}[#else]-1[/#if][/#assign]
            [#-- Name attribute is not needed, we just need to load the value, not save it it. --]
            [@customForm.select name="" label="" disabled=!editable i18nkey="preplanning.projectPartners.country" listName="countries" keyFieldName="id"  displayFieldName="name" className="countryList" value="'${(ap.institution.country.id)!-1}'" /]
          </div>
        </div>
      [/#if]  
    [/#if]   
    [#-- Contact Person --]
    <div class="fullPartBlock clearfix">
      [#-- Contact Person information is going to come from the users table, not from project_partner table (refer to the table project_partners in the database) --] 
      [@customForm.input name="" value="${ap.user.composedName?html}" className="userName" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.contactPersonEmail" required=true readOnly=true editable=editable/]
      <input class="type" type="hidden" name="${ap_name}[${ap_index}].type" value="${isPPA?string(typeProjectPPA, typeProjectPartner)}">
      <input class="userId" type="hidden" name="${ap_name}[${ap_index}].user" value="${ap.user.id}">   
      [#if editable]<div class="searchUser">[@s.text name="form.buttons.searchUser" /]</div>[/#if] 
    </div>  
    [#-- Responsibilities --]
    [#if responsabilities]  
    <div class="fullPartBlock partnerResponsabilities chosen"> 
      [@customForm.textArea name="${ap_name}[${ap_index}].responsabilities" i18nkey="preplanning.projectPartners.responsabilities" required=true editable=editable /]
    </div>
    [/#if]
    [#-- Indicate which PPA Partners for second level partners --]
    [#if !isPPA && !isBilateral]
    <div class="fullPartBlock">      
      <div class="ppaPartnersList panel tertiary">
        <div class="panel-head">[@customForm.text name="preplanning.projectPartners.indicatePpaPartners" readText=!editable /]</div> 
        <div class="panel-body">
          [#if !(ap.contributeInstitutions?has_content) && !editable]
            <p>[@s.text name="planning.projectPartners.noSelectedCCAFSPartners" /] </p>
          [/#if]
          <ul class="list"> 
            [#list ap.contributeInstitutions as ppaPartner]
              <li class="clearfix [#if !ppaPartner_has_next]last[/#if]">
                <input class="id" type="hidden" name="${ap_name}[${ap_index}].contributeInstitutions[${ppaPartner_index}].id" value="${ppaPartner.id}" />
                <span class="name">${ppaPartner.getComposedName()}</span> 
                [#if editable]<span class="listButton remove">[@s.text name="form.buttons.remove" /]</span>[/#if]
              </li>
            [/#list]
          </ul>
          [#if editable]
            [@customForm.select name="" label="" disabled=!canEdit i18nkey="" listName="projectPPAPartners" keyFieldName="id"  displayFieldName="getComposedName()" className="ppaPartnersSelect" value="" /]
          [/#if] 
        </div>
      </div> 
    </div>
    [/#if]
  </div> <!-- End projectPartner-${ap_index} -->
[/#macro]

[#macro partnerTemplate isPPA=false showResponsabilities=false canEdit=true ]
  <div id="projectPartnerTemplate" class="borderBox" style="display:none">
        [#-- Partner identifier --]
        <input id="id" type="hidden" name="" value="-1" />
        [#assign nameLegend]${isPPA?string("preplanning.projectPartners.ppaPartner", "preplanning.projectPartners.partner")}[/#assign]
        <legend>[@s.text name=nameLegend][@s.param name="0"] <span id="partnerIndex">{0}</span>[/@s.param] [/@s.text]</legend>
        [#-- Remove link for all partners --]
        <div class="removeLink">
          <div id="removePartner" class="removePartner removeElement removeLink" title="[@s.text name="preplanning.projectPartners.removePartner" /]"></div>
        </div>
        [#-- Partner Name --]
        <div class="fullPartBlock partnerName chosen">
        [#assign institutionList]${isPPA?string("allPPAPartners", "allPartners")}[/#assign]
          [@customForm.select name="institution" className="institution" label=""  disabled=!canEdit i18nkey="preplanning.projectPartners.partner.name" listName=institutionList keyFieldName="id"  displayFieldName="getComposedName()" /]
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
        [#-- Contact Person --]
        <div class="fullPartBlock clearfix">
          [#-- Contact Person information is going to come from the users table, not from project_partner table (refer to the table project_partners in the database) --] 
          [@customForm.input name="" value="" className="userName" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.contactPersonEmail" required=true readOnly=true /]
          <input class="partnerId" type="hidden" name="" value="-1">
          <input class="type" type="hidden" name="" value="${isPPA?string(typeProjectPPA, typeProjectPartner)}">
          <input class="userId" type="hidden" name="" value="-1">  
          <div class="searchUser">[@s.text name="form.buttons.searchUser" /]</div>
        </div>
        [#-- Responsabilities --]
        [#if showResponsabilities]
        <div class="fullPartBlock partnerResponsabilities chosen">        
          [@customForm.textArea name="responsabilities" i18nkey="preplanning.projectPartners.responsabilities" required=true /]
        </div>
        [/#if]
        [#-- Indicate which PPA Partners for second level partners --]
        [#if !isPPA]
        <div class="fullPartBlock">      
          <div class="ppaPartnersList panel tertiary"> 
            <div class="panel-head">[@s.text name="preplanning.projectPartners.indicatePpaPartners" /]</div>
            <div class="panel-body">
              <ul class="list"></ul> 
              [@customForm.select name="" label="" disabled=!canEdit i18nkey="" listName="projectPPAPartners" keyFieldName="id" displayFieldName="getComposedName()" className="ppaPartnersSelect" value="" /]
            </div>
          </div> 
        </div>
        [/#if]
      </div> <!-- End projectPartner-Template -->
[/#macro]

[#macro projectLeader leader={} coordinator={} showResponsabilities=false canEdit=true editable=false]  
    <div id="projectLeader" class="projectLeader clearfix">        
        [#if leader?has_content]
          [#if leader.institution??]
          [#-- Lead List --]
          <div class="fullPartBlock organizationName chosen">   
            [@customForm.select name="project.leader.institution" value="${leader.institution.id}" disabled=!canEdit i18nkey="preplanning.projectPartners.leader.institutionName" listName="allPartners" keyFieldName="id"  displayFieldName="getComposedName()"  editable=editable /]
          </div>
          [/#if]
        [#-- Project Leader contact --] 
        <div class="fullPartBlock clearfix"> 
          [#if leader.user??]
            [@customForm.input name="" value="${leader.user.composedName?html}" className="userName" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.projectLeader" required=true readOnly=true editable=editable/]
            <input class="userId" type="hidden" name="project.leader.user" value="${leader.user.id}">
          [/#if]
          <input class="partnerId" type="hidden" name="project.leader.id" value="${leader.id}">
          <input class="type" type="hidden" name="project.leader.type" value="${typeProjectLeader}"> 
          [#if editable]<div class="searchUser">[@s.text name="form.buttons.searchUser" /]</div>[/#if]
        </div>
        [/#if]
        
        [#-- Project Coordinator --]
        [#if coordinator?has_content]
        <div class="fullPartBlock clearfix">  
          [#assign coordinatorUserValue][#if coordinator.user?has_content ]${coordinator.user.id}[#else]-1[/#if][/#assign] 
          [@customForm.input name="" value="${coordinator.user.composedName?html}" className="userName" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.projectCoordinator"  readOnly=true editable=editable/]
          <input class="partnerId" type="hidden" name="project.coordinator.id" value="${coordinator.id}">
          <input class="type" type="hidden" name="project.coordinator.type" value="${typeProjectCoordinator}">
          <input class="userId" type="hidden" name="project.coordinator.user" value="${coordinatorUserValue}">
          [#if editable]<div class="searchUser">[@s.text name="form.buttons.searchUser" /]</div>[/#if]
        </div> 
        [/#if]
        [#-- Responsibilities --]
        [#if showResponsabilities]
        <div class="fullBlock leaderResponsabilities chosen">
          [@customForm.textArea name="project.leaderResponsabilities" i18nkey="preplanning.projectPartners.leader.responsabilities" required=true editable=editable/]
        </div>
        [/#if] 
    </div> <!-- End projectLeader -->   
[/#macro]
