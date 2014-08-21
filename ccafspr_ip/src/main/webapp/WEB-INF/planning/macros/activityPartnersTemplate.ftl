[#ftl]
[#macro activityPartner activityPartners]
  [#if activityPartners?has_content]
    [#list activityPartners as ap] 
      <div class="borderBox">
        <div id="removeActivityPartner"class="removeElement removeLink" title="[@s.text name="planning.activityPartner.removePartner" /]"></div>
          [#-- Organizations List --]
          <div class="fullBlock chosen ">
            <h2><b>[@s.text name="planning.activityPartner.partner"][@s.param name="0"] <span id="partnerIndex">${ap_index+1}</span>[/@s.param] [/@s.text]</b></h2>
            [@customForm.select name="institution" listName="allPartners" keyFieldName="id" displayFieldName="name" /]
          </div> 
          [#-- Contact Name --] 
          <div class="halfPartBlock">
            [@customForm.input name="activityPartner.contactName" type="text" i18nkey="planning.activityPartner.contactName" required=true /]
          </div>
          [#-- Contact Email --]
          <div class="halfPartBlock">
            [@customForm.input name="activityPartner.contactEmail" type="text" i18nkey="planning.activityPartner.contactEmail" required=true /]
          </div>
          [#-- Contribution --]
          <div class="fullBlock partnerContribution">
            [@customForm.textArea name="activityPartner.contribution" i18nkey="planning.activityPartner.contribution" required=true /]
          </div>
        </div>
      [/#list]
    [/#if]
[/#macro]