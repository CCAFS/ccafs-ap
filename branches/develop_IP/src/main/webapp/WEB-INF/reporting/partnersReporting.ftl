[#ftl]
[#assign title = "Activity Partners Report" /]
[#assign globalLibs = ["jquery", "noty", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/reporting/partnersReporting.js", "${baseUrl}/js/global/utils.js"] /]
[#assign customCSS = [""] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "activities" /]
[#assign currentStage = "partners" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

[#macro partnerSection]
  [#list activity.activityPartners as ap]
    <div id="activityPartner-${ap_index}" class="activityPartner">
      [#-- Partner identifier --]
      <input type="hidden" name="activity.activityPartners[${ap_index}].id" value="${ap.id?c}" />
      
      [#-- Remove link for all partners --]
      <div class="removeLink">
        <img src="${baseUrl}/images/global/icon-remove.png" />
        <a id="removeActivityPartner-${ap_index}" href="" class="removeActivityPartner">[@s.text name="reporting.activityPartners.removePartner" /]</a>
      </div>
      
      [#-- Partner Name --]
      <div class="fullBlock partnerName chosen">
        [@customForm.select name="activity.activityPartners[${ap_index}].partner" label="" i18nkey="reporting.activityPartners.partner.name" listName="partners" keyFieldName="id"  displayFieldName="name" /]
      </div>
      
      [#-- Contact Name --]
      <div class="halfPartBlock">
        [@customForm.input name="activity.activityPartners[${ap_index}].contactName" type="text" i18nkey="reporting.activityPartners.contactPersonName" /]
      </div>
      
      [#-- Contact Email --]
      <div class="halfPartBlock">
        [@customForm.input name="activity.activityPartners[${ap_index}].contactEmail" type="text" i18nkey="reporting.activityPartners.contactPersonEmail" /]
      </div> 
    </div> <!-- End activityPartner-${ap_index} -->
    <hr />
  [/#list]
[/#macro]

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="reporting.activityPartners.help" /]</p>
  </div>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  [@s.form action="partners" cssClass="pure-form"]
  <article class="halfContent">
    [#include "/WEB-INF/reporting/activitiesReportingSubMenu.ftl" /]
    <h1 class="contentTitle">
      ${activity.leader.acronym}: [@s.text name="reporting.activityList.activity" /] ${activity.activityId}
    </h1>
    
    <h6>[@s.text name="reporting.activityPartners.title" /]</h6>
    <p> ${activity.title} </p>
    
    [#assign typeSelectHeadValue ] [@s.text name="reporting.activityPartners.selectPartnerType" /] [/#assign]
    
    <div id="items">
      <fieldset id="activityPartnerGroup" class="group">
        <legend>[@s.text name="reporting.activityPartners.partners" /]</legend>
        [@partnerSection /]
      <div class="addLink">
        <img src="${baseUrl}/images/global/icon-add.png" />
        <a href="" class="addActivityPartner">[@s.text name="reporting.activityPartners.addNewPartner" /]</a>
      </div>   
      </fieldset>
    </div>
    
    <p id="addPartnerText">
      [@s.text name="reporting.activityPartners.addPartnerMessage.first" /]
      <a class="popup" href="[@s.url action='partnerSave'][@s.param name='${activityRequestParameter}']${activityID?c}[/@s.param][/@s.url]">
        [@s.text name="reporting.activityPartners.addPartnerMessage.second" /]
      </a>       
    </p>
    
    <!-- PARTNERS TEMPLATE -->
    <div id="template">
      <div id="activityPartner-9999" class="activityPartner" style="display: none;">      
        [#-- remove link --]
        <div class="removeLink">
          <img src="${baseUrl}/images/global/icon-remove.png" />
          <a id="removeActivityPartner-9999" href="" class="removeActivityPartner">[@s.text name="reporting.activityPartners.removePartner" /]</a>
        </div>
        
        [#-- Partner identifier --]
        <input type="hidden" name="id" value="-1">
      
        [#-- Partner Name --]
        <div class="fullBlock partnerName chosen">
          [@customForm.select name="__partner" label="" i18nkey="reporting.activityPartners.partner.name" listName="partners" keyFieldName="id"  displayFieldName="name" /]
        </div>
        
        [#-- Contact Name --]
        <div class="halfPartBlock">
          [@customForm.input name="contactName" type="text" i18nkey="reporting.activityPartners.contactPersonName" /]
        </div>
      
        [#-- Contact Email --]
        <div class="halfPartBlock">
          [@customForm.input name="contactEmail" type="text" i18nkey="reporting.activityPartners.contactPersonEmail" /]
        </div>
      </div> <!-- End partner template -->
    </div> <!-- End template -->
    
    [#-- Partner list no result found message --]
    <input id="noResultText" type="hidden" value="[@s.text name="reporting.activityPartners.addNewPartner.noResultMatch" /]" />
    
    <!-- internal parameter -->
    <input name="activityID" type="hidden" value="${activity.id?c}" />
    
    [#if canSubmit]
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
    [/#if]
        
    </article>
  [/@s.form]
 
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]