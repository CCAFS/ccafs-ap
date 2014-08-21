[#ftl]
[#macro activityDeliverable deliverables]
  [#if deliverables?has_content]
    [#list deliverables as dl]
    <div class="borderBox">
      <div id="removeDeliverable-"  class="removeElement removeLink " title="[@s.text name="planning.deliverables.removeDeliverable" /]"></div>
      <input type="hidden" value="${dl.id}" name="deliverables[${dl_index}].id">
      <b>[@s.text name="planning.deliverables.expectedDeliverable" ][@s.param name="0"] <span id="partnerIndex">${dl_index+1}</span>[/@s.param] [/@s.text]</b>
      [#-- Title --] 
      [@customForm.input name="deliverables[${dl_index}].title" type="text" i18nkey="planning.deliverables.title" required=true /]
      <div class="halfPartBlock chosen">
        [#-- Type --]
        [@customForm.select name="deliverables[${dl_index}].type.id" label=""  disabled=false i18nkey="planning.deliverables.type" listName="deliverableTypes" keyFieldName="id"  displayFieldName="name" /]
      </div>
      <div class="halfPartBlock chosen">
        [#-- Year  --]
        [@customForm.select name="deliverables[${dl_index}].year" label=""  disabled=false i18nkey="planning.deliverables.year" listName="" keyFieldName="id"  displayFieldName="name" /]
      </div>
      [#if dl.nextUsers?has_content]
        [#list dl.nextUsers as nu] 
          <div class="borderBox">
            <div id="removeActivityImpactPathway"class="removeElement removeLink" title="[@s.text name="planning.deliverables.removeNewUser" /]"></div>
            <input type="hidden" value="${nu.id}" name="deliverables[${dl_index}].nextUsers[${nu_index}].id">
            [#-- Next User --]
            [@customForm.input name="deliverables[${dl_index}].nextUsers[${nu_index}].user" type="text" i18nkey="planning.deliverables.nextUser" required=true /]<br/>
            [#-- Expected Changes --]
            [@customForm.textArea name="deliverables[${dl_index}].nextUsers[${nu_index}].expectedChanges" i18nkey="planning.deliverables.expectedChanges" required=true /]<br/>
            [#-- Strategies --]
            [@customForm.textArea name="deliverables[${dl_index}].nextUsers[${nu_index}].strategies" i18nkey="planning.deliverables.strategies" required=true /]<br/>
          </div>
        [/#list]
        <div id="addActivityNextUser" class="addLink">
          <a href=""  class="addButton">[@s.text name="planning.deliverables.addNewUser" /]</a>
        </div>
      [/#if]
      </div>
    [/#list]
    
  
  [/#if]
[/#macro]