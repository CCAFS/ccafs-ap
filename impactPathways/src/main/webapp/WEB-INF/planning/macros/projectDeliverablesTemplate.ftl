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
  [#if deliverable?has_content]
    <div id="projectDeliverable" class="projectDeliverable borderBox">
      [#if editable && canEdit]
        <div id="removeDeliverable"  class="removeDeliverable removeElement removeLink " title="[@s.text name="planning.deliverables.removeDeliverable" /]"></div>
        <input type="hidden" value="${deliverable.id}" name="project.deliverable.id">
      [/#if]
      [#-- Title --] 
      [@customForm.input name="project.deliverable.title" type="text" i18nkey="planning.deliverables.title" required=true /]

      [#-- MOG  --]
      <div class="halfPartBlock chosen">
        [@customForm.select name="project.deliverable.output" label=""  disabled=false i18nkey="planning.deliverables.mog" listName="outputs" keyFieldName="id"  displayFieldName="description"  /]
      </div>

      [#-- Year  --]
      <div class="halfPartBlock chosen">
        [@customForm.select name="project.deliverable.year" label=""  disabled=false i18nkey="planning.deliverables.year" listName="allYears" /]
      </div>

      [#-- Main Type --]
      <div class="halfPartBlock chosen">
        [@customForm.select name="mainType" value="project.deliverable.type.category.id" label=""  disabled=false i18nkey="planning.deliverables.mainType" listName="deliverableTypes" keyFieldName="id"  displayFieldName="name" /]
      </div>

      [#-- Sub Type --]
      <div class="halfPartBlock chosen">
        [@customForm.select name="project.deliverable.type" label=""  disabled=false i18nkey="planning.deliverables.subType" listName="" keyFieldName=""  displayFieldName="" /]
        <input type="hidden" id="subTypeSelected" value="${deliverable.type.id}" />
      </div>

      [#-- Sub Type --]
      <div class="halfPartBlock chosen" style="display:none">
        [@customForm.input name="project.deliverable.type" i18nkey="planning.deliverables.subType" /]
        <input type="hidden" id="" value="" />
      </div>
      
      [#if deliverable.nextUsers?has_content]
        [#list deliverable.nextUsers as nu] 
          [#-- Next User block  --] 
          [@nextUserTemplate nu_index="${nu_index}" nextUserValue="${nu.id}" canEdit=canEdit /]
        [/#list]
      [/#if]
      [#if canEdit]
        <div id="addActivityNextUserBlock" class="addLink"><a href=""  class="addActivityNextUser addButton">[@s.text name="planning.deliverables.addNewUser" /]</a></div>
      [/#if]
      </div>
  [/#if]
[/#macro]

[#macro nextUserTemplate nu_index="0" nextUserValue="-1" template=false canEdit=true ]
  [#if template]
    <div id="activityNextUserTemplate" class="borderBox" style="display:none">
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
    <div id="activityNextUser-${nu_index}" class="activityNextUser borderBox">
      [#if canEdit]
        <div id="removeNextUser-${nu_index}"class="removeNextUser removeElement removeLink" title="[@s.text name="planning.deliverables.removeNewUser" /]"></div>
        <input type="hidden" name="activity.deliverable.nextUsers[${nu_index}].id" value="${nextUserValue}" />
      [/#if]
      <span id="index">${nu_index?number+1}</span>
      [#-- Next User --]
      [@customForm.input name="activity.deliverable.nextUsers[${nu_index}].user" type="text" i18nkey="planning.deliverables.nextUser" required=true /]<br/>
      [#-- Expected Changes --]
      [@customForm.textArea name="activity.deliverable.nextUsers[${nu_index}].expectedChanges" i18nkey="planning.deliverables.expectedChanges" required=true /]<br/>
      [#-- Strategies --]
      [@customForm.textArea name="activity.deliverable.nextUsers[${nu_index}].strategies" i18nkey="planning.deliverables.strategies" required=true /]<br/>
    </div>
  [/#if]
[/#macro]