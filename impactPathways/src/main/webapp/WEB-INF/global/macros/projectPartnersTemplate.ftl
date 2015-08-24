[#ftl]
[#macro projectPartner projectPartner={} projectPartnerName="" projectPartnerIndex="0" ]
  <div id="projectPartner-${(projectPartner.id)!}" class="projectPartner borderBox">
    <div class="loading" style="display:none"></div>
    [#-- Edit Button  --]
    [#if (!editable && canEdit)]
      <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#projectPartner-${(projectPartner.id)!}">[@s.text name="form.buttons.edit" /]</a></div>
    [/#if]
    
    [#-- Remove link for all partners --]
    [#if editable]
      <div class="removeLink"><div id="removePartner" class="removePartner removeElement removeLink" title="[@s.text name="preplanning.projectPartners.removePartner" /]"></div></div>
    [/#if]
    
    <span class="elementId">Project Partner</span>
    <span class="index">${projectPartnerIndex?number+1}</span>
    <input id="id" class="partnerId" type="hidden" name="${projectPartnerName}[${projectPartnerIndex}].id" value="${(projectPartner.id)!}" />

    [#-- Filters --]
    [#if editable]
      [#if projectPartner.id != -1]
        <div class="filters-link">[@s.text name="preplanning.projectPartners.filters" /]</div>
        <div class="filters-content">
          [#-- Partner type list --]
          <div class="halfPartBlock partnerTypeName chosen">
            [#-- Name attribute is not needed, we just need to load the value, not save it it. --]
            [@customForm.select name="" label="" disabled=!editable i18nkey="preplanning.projectPartners.partnerType" listName="partnerTypes" keyFieldName="id"  displayFieldName="name" className="partnerTypes" value="${(projectPartner.institution.type.id)!-1}" /]
          </div>
          [#-- Country list --]
          <div class="halfPartBlock countryListBlock chosen">
            [#-- Name attribute is not needed, we just need to load the value, not save it it. --]
            [@customForm.select name="" label="" disabled=!editable i18nkey="preplanning.projectPartners.country" listName="countries" keyFieldName="id"  displayFieldName="name" className="countryList" value="'${(projectPartner.institution.country.id)!-1}'" /]
          </div>
        </div>
      [/#if]  
    [/#if]
    
    [#-- Organization  --]
    <div class="fullPartBlock partnerName chosen">
      [@customForm.select name="${projectPartnerName}[${projectPartnerIndex}].institution" value="${(projectPartner.institution.id)!'-1'}" required=true  disabled=!editable i18nkey="preplanning.projectPartners.partner.name" listName="allInstitutions" keyFieldName="id"  displayFieldName="getComposedName()" editable=editable /]
    </div>
    
    [#-- Contacts person  --]
    <div class="contactsPerson panel tertiary">
      <div class="panel-head">Project partner contacts</div>
      <div class="fullPartBlock">
        [#list [1] as contact]
          [@contactPerson contact={} contactName="contactPerson" contactIndex="${contact_index}" /]
        [/#list]
        [#if (editable && canEdit)]
          <div class="addContact"><a href="" class="addLink">[@s.text name="planning.projectPartners.addContact"/]</a></div> 
        [/#if]
      </div>
    </div>
    
    [#-- Indicate which PPA Partners for second level partners --]
    <div class="ppaPartnersList panel tertiary">
      <div class="panel-head">[@customForm.text name="preplanning.projectPartners.indicatePpaPartners" readText=!editable /]</div> 
      <div class="panel-body">
        [#if !(projectPartner.contributeInstitutions?has_content) && !editable]
          <p>[@s.text name="planning.projectPartners.noSelectedCCAFSPartners" /] </p>
        [/#if]
        <ul class="list"> 
        [#if projectPartner.contributeInstitutions?has_content]
          [#list projectPartner.contributeInstitutions as ppaPartner]
            <li class="clearfix [#if !ppaPartner_has_next]last[/#if]">
              <input class="id" type="hidden" name="${projectPartnerName}[${projectPartnerIndex}].contributeInstitutions[${ppaPartner_index}].id" value="${ppaPartner.id}" />
              <span class="name">${(ppaPartner.composedName)!}</span> 
              [#if editable]<span class="listButton remove">[@s.text name="form.buttons.remove" /]</span>[/#if]
            </li>
          [/#list]
        [/#if]
        </ul>
        [#if editable]
          [@customForm.select name="" label="" disabled=!canEdit i18nkey="" listName="projectPPAPartners" keyFieldName="id"  displayFieldName="getComposedName()" className="ppaPartnersSelect" value="" /]
        [/#if] 
      </div>
    </div> 
    
    
  </div>
[/#macro]

[#macro contactPerson contact={} contactName="" contactIndex="0" ]
  <div class="contactPerson simpleBox">
    [#-- Remove link for all partners --]
    [#if editable]
      <div class="removeLink"><div id="removePartner" class="removePartner removeElement removeLink" title="[@s.text name="preplanning.projectPartners.removePartner" /]"></div></div>
    [/#if]
    <span class="index">${contactIndex?number+1}</span>
    <div class="fullPartBlock"> 
      [#-- Partner Person type --]
      <div class="partnerPerson-type halfPartBlock clearfix">
      [@customForm.select name="${contactName}[${contactIndex}].type" disabled=!canEdit i18nkey="Contact person role" listName="projectPPAPartners" keyFieldName="id"  displayFieldName="getComposedName()" className="" editable=editable /]
      </div>
      
      [#-- Contact Person email --]
      <div class="partnerPerson-email halfPartBlock clearfix">
        [#-- Contact Person information is going to come from the users table, not from project_partner table (refer to the table project_partners in the database) --] 
        [@customForm.input name="contact-person-${contactIndex}" value="${(contact.user.composedName?html)!}" className="userName" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.contactPersonEmail" required=!project.bilateralProject readOnly=true editable=editable/]
        [#if editable]<div class="searchUser">[@s.text name="form.buttons.searchUser" /]</div>[/#if] 
        <input class="type" type="hidden" name="${contactName}[${contactIndex}].type" value="">
        <input class="userId" type="hidden" name="${contactName}[${contactIndex}].user" value="${(contact.user.id)!'-1'}">   
      </div>
    </div>
    [#-- Responsibilities --] 
    <div class="fullPartBlock partnerResponsabilities chosen"> 
      [@customForm.textArea name="${contactName}[${contactIndex}].responsibilities" className="resp" i18nkey="preplanning.projectPartners.responsabilities" required=!project.bilateralProject editable=editable /]
    </div>
    [#-- Activities leading and Deliverables with responsibilities --]
    <div class="fullPartBlock clearfix"> 
      <div class="tag">Leader in <span>3</span> activities</div>
      <div class="tag">Responsable of <span>4</span> deliverables</div>
    </div>
  </div>
[/#macro]

[#macro partnerTemplate isPPA=false showResponsabilities=false canEdit=true ]
  <div id="projectPartnerTemplate" class="borderBox" style="display:none">
        <div class="loading" style="display:none"></div>
        [#-- Partner identifier --]
        <input id="id" type="hidden" name="" value="-1" />
        [#assign nameLegend]${isPPA?string("preplanning.projectPartners.ppaPartner", "preplanning.projectPartners.partner")}[/#assign]
        <legend>[@s.text name=nameLegend][@s.param name="0"] <span id="partnerIndex">{0}</span>[/@s.param] [/@s.text]</legend>
        [#-- Remove link for all partners --]
        <div class="removeLink">
          <div id="removePartner" class="removePartner removeElement removeLink" title="[@s.text name="preplanning.projectPartners.removePartner" /]"></div>
        </div>
        [#-- Organization --]
        <div class="fullPartBlock partnerName chosen">
          [@customForm.select name="institution" className="institution" label="" required=true disabled=!canEdit i18nkey="preplanning.projectPartners.partner.name" listName="allInstitutions" keyFieldName="id"  displayFieldName="getComposedName()" /]
        </div>
        [#-- Filters --]
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
          [@customForm.input name="" value="" className="userName" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.contactPersonEmail" required=!project.bilateralProject readOnly=true /]
          <div class="searchUser">[@s.text name="form.buttons.searchUser" /]</div>
          <input class="partnerId" type="hidden" name="" value="-1">
          <input class="type" type="hidden" name="" value="${isPPA?string(typeProjectPPA, typeProjectPartner)}">
          <input class="userId" type="hidden" name="" value="-1">  
        </div>
        [#-- Responsabilities --]
        [#if showResponsabilities]
        <div class="fullPartBlock partnerResponsabilities chosen">        
          [@customForm.textArea name="responsabilities" className="resp" i18nkey="preplanning.projectPartners.responsabilities" required=!project.bilateralProject /]
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
    [#if (!editable && canEdit)]
      <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#projectLeader">[@s.text name="form.buttons.edit" /]</a></div>
    [/#if]
    [#-- Lead List --]
    <div class="fullPartBlock organizationName chosen">   
      [@customForm.select name="project.leader.institution" value="${(leader.institution.id)!'-1'}" required=true disabled=!canEdit i18nkey="preplanning.projectPartners.leader.institutionName" listName="allPartners" keyFieldName="id"  displayFieldName="getComposedName()"  editable=editable /]
    </div>
    [#-- Project Leader contact --] 
    <div class="fullPartBlock clearfix">
      [@customForm.input name="contact-person-leader" value="${(leader.user.composedName?html)!''}" className="userName" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.projectLeader" required=true readOnly=true editable=editable/]
      [#if editable]<div class="searchUser">[@s.text name="form.buttons.searchUser" /]</div>[/#if]
      <input class="userId" type="hidden" name="project.leader.user" value="${(leader.user.id)!"-1"}">
      <input class="partnerId" type="hidden" name="project.leader.id" value="${(leader.id)!"-1"}">
      <input class="type" type="hidden" name="project.leader.type" value="${typeProjectLeader}"> 
    </div>
    
    [#-- Project Coordinator contact --]
    <div class="fullPartBlock clearfix">  
      [#assign coordinatorUserValue][#if coordinator.user?has_content ]${coordinator.user.id}[#else]-1[/#if][/#assign] 
      [@customForm.input name="contact-person-coordinator" value="${(coordinator.user.composedName?html)!''}" className="userName" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.projectCoordinator"  readOnly=true editable=editable/]
      [#if editable]<div class="searchUser">[@s.text name="form.buttons.searchUser" /]</div>[/#if]
      <input class="partnerId" type="hidden" name="project.coordinator.id" value="${(coordinator.id)!'-1'}">
      <input class="type" type="hidden" name="project.coordinator.type" value="${typeProjectCoordinator}">
      <input class="userId" type="hidden" name="project.coordinator.user" value="${coordinatorUserValue}">
    </div>
    [#-- Responsibilities --]
    [#if showResponsabilities]
    <div class="fullBlock leaderResponsabilities chosen">
      [@customForm.textArea name="project.leader.responsabilities" className="resp" i18nkey="preplanning.projectPartners.leader.responsabilities" required=true editable=editable/]
    </div>
    [/#if] 
  </div> <!-- End projectLeader -->   
[/#macro]
