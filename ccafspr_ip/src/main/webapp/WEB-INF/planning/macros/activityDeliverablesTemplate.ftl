[#ftl]
[#macro activityDeliverablesList deliverables canEdit=true]
  [#if deliverables?has_content]
    [#list deliverables as dl]
    <div id="activityDeliverable-${dl_index}" class="activityDeliverable borderBox">
      [#if canEdit]
        <div id="removeDeliverable-${dl_index}"  class="removeDeliverable removeElement removeLink " title="[@s.text name="planning.deliverables.removeDeliverable" /]"></div>
        <input type="hidden" value="${dl.id}" name="activity.deliverables[${dl_index}].id">
      [/#if]
      <legend>
        [@s.text name="planning.deliverables.expectedDeliverable" ]
          [@s.param name="0"]
            <span id="deliverableIndex">${dl_index+1}</span>
          [/@s.param]
        [/@s.text]
      </legend>
      [#-- Title --] 
      [@customForm.input name="activity.deliverables[${dl_index}].title" type="text" i18nkey="planning.deliverables.title" required=true /]
      [#-- Main Type --]
      <div class="halfPartBlock chosen">
        [@customForm.select name="mainType" value="activity.deliverables[${dl_index}].type.category.id" label=""  disabled=false i18nkey="planning.deliverables.mainType" listName="deliverableTypes" keyFieldName="id"  displayFieldName="name" /]
      </div>
      [#-- Sub Type --]
      <div class="halfPartBlock chosen">
        [@customForm.select name="activity.deliverables[${dl_index}].type" label=""  disabled=false i18nkey="planning.deliverables.subType" listName="" keyFieldName=""  displayFieldName="" /]
        <input type="hidden" id="subTypeSelected_${dl_index}" value="${activity.deliverables[dl_index].type.id}" />
      </div>
      [#-- Year  --]
      <div class="halfPartBlock chosen">
        [@customForm.select name="activity.deliverables[${dl_index}].year" label=""  disabled=false i18nkey="planning.deliverables.year" listName="allYears" /]
      </div>
      [#if dl.nextUsers?has_content]
        [#list dl.nextUsers as nu] 
          [#-- Next User block  --] 
          [@nextUserTemplate dl_index="${dl_index}" nu_index="${nu_index}" nextUserValue="${nu.id}" canEdit=canEdit /]
        [/#list]
        [#if canEdit]
          <div id="addActivityNextUserBlock" class="addLink"><a href=""  class="addActivityNextUser addButton">[@s.text name="planning.deliverables.addNewUser" /]</a></div>
        [/#if]
      [/#if]
      </div>
    [/#list] 
  [/#if]
[/#macro]

[#macro activityDeliverableTemplate dl_index=0 nu_index=0 ]
  <div id="activityDeliverable-999" class="borderBox" style="display:none">
    <div id="removeDeliverable-${dl_index}"  class="removeDeliverable removeElement removeLink" title="[@s.text name="planning.deliverables.removeDeliverable" /]"></div>
    <input type="hidden" value="-1" name="].id">
    <legend>[@s.text name="planning.deliverables.expectedDeliverable" ][@s.param name="0"] <span id="deliverableIndex">${dl_index+1}</span>[/@s.param] [/@s.text]</legend>
    [#-- Title --] 
    [@customForm.input name="title" type="text" i18nkey="planning.deliverables.title" required=true /]
    [#-- Main Type --]
    <div class="halfPartBlock chosen">
      [@customForm.select name="mainType" label=""  disabled=false i18nkey="planning.deliverables.mainType" listName="deliverableTypes" keyFieldName="id"  displayFieldName="name" /]
    </div>
    [#-- Sub Type --]
    <div class="halfPartBlock chosen">
      [@customForm.select name="type" label=""  disabled=false i18nkey="planning.deliverables.subType" listName="" keyFieldName=""  displayFieldName="" /]
    </div>
    [#-- Year  --]
    <div class="halfPartBlock chosen">
      [@customForm.select name="year" label=""  disabled=false i18nkey="planning.deliverables.year" listName="allYears" /]
    </div> 
    [#-- Add next user button  --]
    <div id="addActivityNextUserBlock" class="addLink"><a href=""  class="addActivityNextUser addButton">[@s.text name="planning.deliverables.addNewUser" /]</a></div>
  </div>  
[/#macro]

[#macro nextUserTemplate dl_index="0" nu_index="0" nextUserValue="-1" template=false canEdit=true ]
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
        <input type="hidden" name="activity.deliverables[${dl_index}].nextUsers[${nu_index}].id" value="${nextUserValue}" />
      [/#if]
      <span id="index">${nu_index?number+1}</span>
      [#-- Next User --]
      [@customForm.input name="activity.deliverables[${dl_index}].nextUsers[${nu_index}].user" type="text" i18nkey="planning.deliverables.nextUser" required=true /]<br/>
      [#-- Expected Changes --]
      [@customForm.textArea name="activity.deliverables[${dl_index}].nextUsers[${nu_index}].expectedChanges" i18nkey="planning.deliverables.expectedChanges" required=true /]<br/>
      [#-- Strategies --]
      [@customForm.textArea name="activity.deliverables[${dl_index}].nextUsers[${nu_index}].strategies" i18nkey="planning.deliverables.strategies" required=true /]<br/>
    </div>
  [/#if]
    
[/#macro]