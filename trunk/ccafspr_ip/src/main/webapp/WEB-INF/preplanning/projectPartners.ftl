[#ftl]
[#assign title = "Project Partners" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/mainInformation.js"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "projects" /]
[#assign currentStage = "partners" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
[#macro partnerSection]

  [#if activity.activityPartners?has_content]
    [#list activity.activityPartners as ap]
      <div id="activityPartner-${ap_index}" class="activityPartner">
        [#-- Partner identifier --]
        <input type="hidden" name="activity.activityPartners[${ap_index}].id" value="${ap.id?c}" />
        
        [#-- Remove link for all partners --]
        <div class="removeLink">
          <img src="${baseUrl}/images/global/icon-remove.png" />
          <a id="removeActivityPartner-${ap_index}" href="" class="removeActivityPartner">[@s.text name="planning.activityPartners.removePartner" /]</a>
        </div>

        [#-- Partner type list --]
        <div class="halfPartBlock partnerTypeName chosen">
          [@customForm.select name="partnerTypeList" label="" i18nkey="planning.activityPartners.partnerType" listName="partnerTypes" keyFieldName="id"  displayFieldName="name" className="partnerTypes" value="activity.activityPartners[${ap_index}].partner.type.id" /]
        </div>

        [#-- Country list --]
        <div class="halfPartBlock countryListBlock chosen">
          [@customForm.select name="countryList" label="" i18nkey="planning.activityPartners.country" listName="countries" keyFieldName="id"  displayFieldName="name" className="countryList" value="activity.activityPartners[${ap_index}].partner.country.id" /]
        </div>

        [#-- Partner Name --]
        <div class="fullBlock partnerName chosen">
          [@customForm.select name="activity.activityPartners[${ap_index}].partner" label="" i18nkey="planning.activityPartners.partner.name" listName="partners" keyFieldName="id"  displayFieldName="name" /]
        </div>

        [#-- Contact Name --]
        <div class="halfPartBlock">
          [@customForm.input name="activity.activityPartners[${ap_index}].contactName" type="text" i18nkey="planning.activityPartners.contactPersonName" required=true /]
        </div>

        [#-- Contact Email --]
        <div class="halfPartBlock">
          [@customForm.input name="activity.activityPartners[${ap_index}].contactEmail" type="text" i18nkey="planning.activityPartners.contactPersonEmail" required=true /]
        </div> 
        <hr />
      </div> <!-- End activityPartner-${ap_index} -->
    [/#list]
  [/#if]  
[/#macro]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.mainInformation.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl" /]
  
  [@s.form action="projectPartners" cssClass="pure-form"]  
  <article class="halfContent" id="mainInformation">
  	[#include "/WEB-INF/preplanning/projectPreplanningSubMenu.ftl" /]
    <h1 class="contentTitle">
    [@s.text name="preplanning.projectPartners.title" /]  
    </h1>
    [#list partners as partner]
    	${partner.name}<br>
    [/#list]
    
    
     
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]