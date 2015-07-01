[#ftl]
[#macro deliverablesList deliverables canEdit=true]
  [#if deliverables?has_content]
    <table id="projectDeliverables" class="default">
      <thead>
        <tr>
          <th class="mogId" >MOG</th>
          <th class="id" class="left">ID</th>
          <th class="name">Deliverable Name</th>
          <th class="type">Type</th> 
          <th class="date-added">Date Added</th> 
          <th class="removeDeliverable"> </th> 
        </tr>
      </thead>
      <tbody>
      [#list deliverables as dl]  
        [#assign dlurl][@s.url namespace=namespace action='deliverable' ][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url][/#assign]
        <tr>
          <td class="mogId" ><a href="${dlurl}">{dl.mog}</a></td>
          <td class="id" class="left"><a href="${dlurl}">${dl.id}</a></td>
          <td class="name"><a href="${dlurl}">${dl.title}</a></td>
          <td class="type"><a href="${dlurl}">${dl.type.name}</a></td> 
          <td class="date-added"><a href="${dlurl}">{dl.date}</a></td> 
          <td class="removeDeliverable">
            [#if true ]
              <a href="[@s.url action='deleteProject' includeParams='get' namespace='/planning/projects' /]" title="" class="removeDeliverable">
                <img src="${baseUrl}/images/global/trash.png" title="[@s.text name="preplanning.projects.deleteProject" /]" /> 
              </a>
            [#else]
              <img src="${baseUrl}/images/global/trash_disable.png" title="[@s.text name="preplanning.projects.cantDeleteProject" /]" />
            [/#if]
          </td> 
        </tr> 
      [/#list]
      </tbody> 
    </table>
  [/#if]  
[/#macro]

[#macro deliverable deliverable editable=true canEdit=true]
  [#assign dl_index = 0]
  [#if deliverable?has_content] 
       
  [/#if]
[/#macro]

[#macro nextUserTemplate dl_index="0" nu_index="0" nextUserValue="-1" template=false editable=true canEdit=true ]
  [#if template]
    <div id="projectNextUserTemplate" class="borderBox" style="display:none">
      <div id="removeNextUser-${nu_index}"class="removeNextUser removeElement removeLink" title="[@s.text name="planning.deliverables.removeNewUser" /]"></div>
      <input type="hidden" value="${nextUserValue}" name="].id" />
      <span id="index">${nu_index?number+1}</span>
      [#-- Next User --]
      [@customForm.input name="user" type="text" i18nkey="planning.deliverables.nextUser" required=true /]<br/>
      [#-- Expected Changes --]
      [@customForm.textArea name="expectedChanges" i18nkey="planning.deliverables.expectedChanges" required=true /]<br/>
      [#-- Strategies --]
      [@customForm.textArea name="strategies" i18nkey="planning.deliverables.strategies" required=true /]<br/>
    </div>
  [#else]
    <div id="projectNextUser-${nu_index}" class="projectNextUser borderBox">
      [#if editable]
        <div id="removeNextUser-${nu_index}"class="removeNextUser removeElement removeLink" title="[@s.text name="planning.deliverables.removeNewUser" /]"></div>
        <input type="hidden" name="project.deliverables[${dl_index}].nextUsers[${nu_index}].id" value="${nextUserValue}" />
      [/#if]
      <span id="index">${nu_index?number+1}</span>
      [#-- Next User --]
      [@customForm.input name="project.deliverables[${dl_index}].nextUsers[${nu_index}].user" type="text" i18nkey="planning.deliverables.nextUser" required=true editable=editable /]<br/>
      [#-- Expected Changes --]
      [@customForm.textArea name="project.deliverables[${dl_index}].nextUsers[${nu_index}].expectedChanges" i18nkey="planning.deliverables.expectedChanges" required=true editable=editable /]<br/>
      [#-- Strategies --]
      [@customForm.textArea name="project.deliverables[${dl_index}].nextUsers[${nu_index}].strategies" i18nkey="planning.deliverables.strategies" required=true editable=editable /]<br/>
    </div>
  [/#if]
[/#macro]