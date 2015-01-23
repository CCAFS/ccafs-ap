[#ftl]
[#assign title = "Insert a partner" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customCSS = ["${baseUrl}/css/reporting/partnersReporting.css"] /]
[#assign customJS = ["${baseUrl}/js/reporting/partnerSaveReporting.js"] /]

[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

[#include "/WEB-INF/global/pages/popup-header.ftl" /]
  <section>
    <article class="content">
      <h1>[@s.text name="reporting.activityPartners.addPartner" /]</h1>
      [@s.form action="partnerSave"]
      
      [#-- Partner Name --]
      <div class="halfPartBlock">
        [@customForm.input name="activityPartner.partner.name" type="text" i18nkey="reporting.PartnersSave.name" /]
      </div>
      
      [#-- Partner Acronym --]
      <div class="halfPartBlock">
        [@customForm.input name="activityPartner.partner.acronym" type="text" i18nkey="reporting.PartnersSave.acronym" /]
      </div>
      
      [#-- Partner types list --]
      <div class="halfPartBlock">
        [@customForm.select name="activityPartner.partner.type.id" label="" i18nkey="reporting.PartnersSave.partnerType" listName="partnerTypesList" keyFieldName="id"  displayFieldName="name" /]
      </div>
      
      [#-- Countries list --]
      <div class="halfPartBlock">
        [@customForm.select name="activityPartner.partner.country" label="" i18nkey="reporting.PartnersSave.country" listName="countriesList" keyFieldName="id"  displayFieldName="name" /]        
      </div>
      
      [#-- City of location --]
      <div class="halfPartBlock">
        [@customForm.input name="activityPartner.partner.city" type="text" i18nkey="reporting.PartnersSave.city" /]
      </div>
      
      [#-- Empty space --]
      <div class="halfPartBlock">
      </div>
      
      [#-- Contact point name --]
      <div class="halfPartBlock">
        [@customForm.input name="activityPartner.contactName" type="text" i18nkey="reporting.PartnersSave.contactName" /]
      </div>
      
      [#-- Contact point email --]
      <div class="halfPartBlock">
        [@customForm.input name="activityPartner.contactEmail" type="text" i18nkey="reporting.PartnersSave.contactEmail" /]
      </div>
      
      [#-- Web page link --]
      <div class="fullBlock">
        [@customForm.input name="partnerWebPage" type="text" i18nkey="reporting.PartnersSave.webPage" /]
      </div>
      
      [#-- Hidden input with message of success --]
      <input type="hidden" id="message.success" value="[@s.text name="reporting.PartnersSave.successMessage" /]"/>
            
      <!-- internal parameter -->
      <input name="activityID" type="hidden" value="${activityID?c}" />
      [@s.submit type="button" name="save"][@s.text name="form.buttons.savePartner.request" /][/@s.submit]
      [/@s.form]
    </article>
  </section> 
  [#include "/WEB-INF/global/pages/js-imports.ftl"]
</body>