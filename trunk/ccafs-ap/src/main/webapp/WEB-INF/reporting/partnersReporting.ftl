[#ftl]
[#assign title = "Activity Partners Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = [""] /]
[#assign customCSS = [""] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "activities" /]
[#assign currentStage = "partners" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
    
  <section>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  [@s.form action="deliverables!save"]
  <article class="halfContent">
    <h1>
      ${activity.leader.acronym} - [@s.text name="reporting.activityPartners.activity" /] ${activity.id}      
    </h1>
    
    <h6>[@s.text name="reporting.activityPartners.title" /]</h6>
    <p> ${activity.title} </p>
    
    [#assign typeSelectHeadValue ] [@s.text name="reporting.activityPartners.selectPartnerType" /] [/#assign]
    
    [#if partners?? ] 
    
      <fieldset>
        <legend>[@s.text name="reporting.activityDeliverables.expectedDeliverables" /]</legend>
        
        [@s.iterator value="partners" var="partner"]
          [@customForm.select name="selectedPartnerType" label="" i18nkey="reporting.activityPartners.partnerType" listName="partnerTypes" value="${partner.type.id}" headerValue="${typeSelectHeadValue}" keyFieldName="id"  displayFieldName="name"  /]
          [@customForm.input name="selectedPartner" value="${partner.name}" type="text" i18nkey="reporting.activityPartners.partnerName" disabled=true  /]
          [#if partner.contactPoints ?? ]
            [@s.iterator value="partner.contactPoints" var="contactPoint"]
              [@customForm.input name="selectedPartner" value="${contactPoint.name}" type="text" i18nkey="reporting.activityPartners.contactPersonName" disabled=true  /]
              [@customForm.input name="selectedPartner" value="${contactPoint.email}" type="text" i18nkey="reporting.activityPartners.contactPersonEmail" disabled=true  /]
            [/@s.iterator]
          [/#if]
          <hr />
        [/@s.iterator]
      </fieldset>
    [#else]
      <div id="activityDescription" class="fullBlock">
        <h6>[@s.text name="reporting.activityPartners.noPartners" /]</h6>        
      </div>
       
    [/#if]
    
    
    <!-- internal parameter -->
    <input name="activityID" type="hidden" value="${activity.id}" />
    [@s.submit type="button" name="save"]SAVE[/@s.submit]
         
    [#include "/WEB-INF/reporting/reportingStepSubMenu.ftl" /]  
    </article>
  [/@s.form]
 
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]