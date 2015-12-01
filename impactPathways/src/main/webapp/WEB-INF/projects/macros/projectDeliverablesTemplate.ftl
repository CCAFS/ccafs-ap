[#ftl]
[#macro deliverablesList deliverables canEdit=true]
  [#if deliverables?has_content]
    [@s.set var="counter" value=0/] 
    <table id="projectDeliverables">
      <thead>
        <tr>
          <th class="id" >ID</th> 
          <th class="name">Deliverable Name</th>
          <th class="type">Type</th> 
          <th class="year">Year</th>
          <th class="status">Status</th> 
          <th class="removeDeliverable">Remove</th> 
        </tr>
      </thead>
      <tbody>
      [#list deliverables as dl]
        [#assign dlurl][@s.url namespace=namespace action='deliverable' ][@s.param name='deliverableID']${dl.id}[/@s.param][/@s.url][/#assign]
        <tr>
          <td class="id" ><a href="${dlurl}">${dl.id}</a></td> 
          <td class="name"><a href="${dlurl}">${dl.title!"Untitled"}</a></td>
          <td class="type"><a href="${dlurl}">${(dl.type.name)!"Not defined"}</a></td> 
          <td class="year"><a href="${dlurl}">${dl.year}</a></td> 
          <td class="status">
            [#if action.getDeliverableStatus(dl.id,'deliverable')??]
              [#if !((action.getDeliverableStatus(dl.id,'deliverable')).missingFieldsWithPrefix)?has_content]
                <span class="icon-20 icon-check" title="Complete"></span>
                [@s.set var="counter" value=counter+1/] 
              [#else]
                <span class="icon-20 icon-uncheck" title=""></span> 
              [/#if]
            [#else]
                <span class="icon-20 icon-uncheck" title=""></span>
            [/#if]
          </td>
          
          <td class="removeDeliverable-row">
            [#if canEdit]
              <a id="removeDeliverable-${dl.id}" class="removeDeliverable" href="deliverableID${dl.id}" title="" >
                <img src="${baseUrl}/images/global/trash.png" title="[@s.text name="planning.deliverables.removeDeliverable" /]" /> 
              </a>
            [#else]
              <img src="${baseUrl}/images/global/trash_disable.png" title="[@s.text name="planning.deliverables.cantDeleteDeliverable" /]" />
            [/#if]
          </td> 
        </tr> 
      [/#list]
      </tbody> 
    </table>
    <div class="clearfix"></div>
    <p id="deliverables-status" style="display:none"> <br /> (${counter}/${deliverables?size})</p>
  [/#if]  
[/#macro]

[#macro deliverablesTypesTable types show=true]
[#if show]
  <div id="dialog" title="Deliverable types" style="display: none">
    <table id="deliverableTypes" style="height:700px; width:900px;">
      <th> [@s.text name="planning.deliverables.dialogMessage.part1" /] </th>
      <th> [@s.text name="planning.deliverables.dialogMessage.part2" /] </th>
      <th> [@s.text name="planning.deliverables.dialogMessage.part3" /] </th>
      [#list deliverableTypes as mt]
        [#list action.getDeliverableSubTypes(mt.id) as st]
          [#if st_index == 0]
          <tr>
            <th rowspan="${action.getDeliverableSubTypes(mt.id).size()}"> ${mt.name} </th>
                <td> ${st.name} </td>
                <td> ${(st.description)!}</td>
          </tr>
          [#else]
          <tr>
            <td> ${st.name} </td>
            <td> ${(st.description)!} </td>
          </tr>
          [/#if]
        [/#list]
      [/#list]  
    </table>
  </div> <!-- End dialog-->
  <div class="helpMessage3">
    <p><a href="#" id="opener"><img src="${baseUrl}/images/global/icon-help.png" />[@s.text name="planning.deliverables.deliverableType" /]</a></p>
  </div>
  <br />
[/#if] 
[/#macro]


[#macro rank name disabled=false editable=true]
  [#assign score][@s.property value="${name}"/][/#assign]
  <div class="rankingBlock" style="text-align:center;">
    [#if editable]
    <input class="hover-star required" type="radio" name="${name}" value="1" [#if score == "1"]checked[/#if] [#if disabled]disabled="disabled"[/#if] title=""/>
    <input class="hover-star" type="radio" name="${name}" value="2" [#if score == "2"]checked[/#if] [#if disabled]disabled="disabled"[/#if] title=""/>
    <input class="hover-star" type="radio" name="${name}" value="3" [#if score == "3"]checked[/#if] [#if disabled]disabled="disabled"[/#if] title="" />
    <input class="hover-star" type="radio" name="${name}" value="4" [#if score == "4"]checked[/#if] [#if disabled]disabled="disabled"[/#if] title="" />
    <input class="hover-star" type="radio" name="${name}" value="5" [#if score == "5"]checked[/#if] [#if disabled]disabled="disabled"[/#if] title="" />
    <div class="hover-test" style=""></div> 
    <div class="clearfix"></div>
    [#else]
      [#if score?has_content]Rate ${score}[#else]Not rated[/#if]
    [/#if]
  </div>
[/#macro]

[#macro yesNoInput name disabled=false editable=true inverse=false]
  [#assign value][@s.property value="${name}"/][/#assign]
  <div class="onoffswitch">
    [#if editable]
      <input id="myonoffswitch-${name}" class="onoffswitch-checkbox [#if inverse]inverse[/#if]" type="checkbox" name="${name}" value="yes" [#if disabled]disabled[/#if] />
      <label class="onoffswitch-label" for="myonoffswitch-${name}">
          <span class="onoffswitch-inner"></span>
          <span class="onoffswitch-switch"></span>
      </label>
      [#if !disabled] <input type="hidden" name="${name}" value="yes" />[/#if]
    [#else]
      [#if value?has_content]Yes[#else]No[/#if]
    [/#if]
  </div>
[/#macro]

[#macro nextUserTemplate nu_name="" nu_index="0" nextUserValue="-1" template=false editable=true canEdit=true ]
  [#if template]
    <div id="projectNextUserTemplate" class="simpleBox" style="display:none">
      <div id="removeNextUser-${nu_index}"class="removeNextUser removeElement removeLink" title="[@s.text name="planning.deliverables.removeNewUser" /]"></div>
      <input type="hidden" value="${nextUserValue}" name="].id" />
      <div class="leftHead">
        <span class="index">${nu_index?number+1}</span>
      </div>
      [#-- Next User --]
      [@customForm.input name="user" type="text" i18nkey="planning.deliverables.nextUser" className="limitWords-20" required=true /]<br/>
      [#-- Expected Changes --]
      [@customForm.textArea name="expectedChanges" i18nkey="planning.deliverables.expectedChanges" className="limitWords-50" required=true /]<br/>
      [#-- Strategies --]
      [@customForm.textArea name="strategies" i18nkey="planning.deliverables.strategies" className="limitWords-50" required=true /]<br/>
    </div>
  [#else]
    <div id="projectNextUser-${nu_index}" class="projectNextUser simpleBox">
      [#if editable]
        <div id="removeNextUser-${nu_index}"class="removeNextUser removeElement removeLink" title="[@s.text name="planning.deliverables.removeNewUser" /]"></div>
        <input type="hidden" name="${nu_name}[${nu_index}].id" value="${nextUserValue}" />
      [/#if]
      <div class="leftHead">
        <span class="index">${nu_index?number+1}</span>
      </div>
      [#-- Next User --]
      [@customForm.input name="${nu_name}[${nu_index}].user" type="text" i18nkey="planning.deliverables.nextUser" className="limitWords-20" required=true editable=editable /]<br/>
      [#-- Expected Changes --]
      [@customForm.textArea name="${nu_name}[${nu_index}].expectedChanges" i18nkey="planning.deliverables.expectedChanges" className="limitWords-50" required=true editable=editable /]<br/>
      [#-- Strategies --]
      [@customForm.textArea name="${nu_name}[${nu_index}].strategies" i18nkey="planning.deliverables.strategies" className="limitWords-50" required=true editable=editable /]<br/>
    </div>
  [/#if]
[/#macro]

[#macro deliverablePartner dp={} dp_name="" dp_index="" isResponsable=false template=false editable=true]
  <div id="${template?string('deliverablePartnerTemplate','')}" class="${isResponsable?string('responsiblePartner','deliverablePartner')} ${isResponsable?string('simpleBox','borderBox')}" style="${template?string('display:none','')}">
    [#if editable && !isResponsable]
      <div class="removeElement removeLink" title="[@s.text name="planning.deliverables.removePartnerContribution" /]"></div> 
    [/#if]
    [#if !isResponsable]
    <div class="leftHead">
      <span class="index">${dp_index+1}</span>
    </div> 
    [/#if]
    [#assign customName]${dp_name}[#if !isResponsable][${dp_index}][/#if][/#assign]
    <input class="id" type="hidden" name="${customName}.id" value="${(dp.id)!'-1'}">
    <input class="type" type="hidden" name="${customName}.type" value="${isResponsable?string('Resp','Other')}">
    [#if template]
      [#-- Partner Name --]
      <div class="fullPartBlock partnerName chosen"> 
        [@customForm.select name="" value="-1" className="partner" i18nkey="preplanning.projectPartners.partner.name" listName="projectPartnerPersons" editable=editable /]
      </div>
    [#else]
      [#-- Partner Name --]
      [#assign partnerId][#if dp.partner??]${dp.partner.id}[#else]-1[/#if][/#assign]
      <div class="fullPartBlock partnerName chosen"> 
        [@customForm.select name="${customName}.partner" value="${partnerId}" className="partner" required=isResponsable label="" i18nkey="planning.projectDeliverable.partner" listName="projectPartnerPersons" displayFieldName="" editable=editable/]
      </div>
    [/#if] 
  </div> 
[/#macro] 
