[#ftl]
[#assign title = "Activity Summaries" /]
[#assign globalLibs = ["jquery", "noty", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/summaries/caseStudiesSummaries.js"] /]
[#assign currentSection = "summaries" /]
[#assign currentSummariesSection = "caseStudies" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="summaries.caseStudies.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/summaries-secondary-menu.ftl" /]
  
  
  [@s.form action="caseStudies"]  
  <article class="halfContent" id="caseStudiesSummaries">
    <h1 class="contentTitle">
      [@s.text name="summaries.caseStudies.title" /] 
    </h1>
    
    <div class="options">
      
      <div class="filters">
        <h6>[@s.text name="summaries.caseStudies.selectFilter" /]</h6>
        
        [#-- Chosen default messages --]
        <input id="countryListDefault" type="hidden" value="[@s.text name="summaries.caseStudies.selectCountry" /]" />
        
        [#-- Leader --]        
        [#if currentUser.admin]
          <div class="halfPartBlock">
            [@customForm.select name="leadersSelected" label="" i18nkey="summaries.caseStudies.leader" listName="leaders" keyFieldName="id" displayFieldName="acronym" /]
          </div>
        [#else]
          <input type="hidden" name="leadersSelected" value="${currentUser.leader.id}" />
        [/#if]
        
        [#-- Year --]
        <div class="halfPartBlock">
          [@customForm.select name="yearSelected" label="" i18nkey="summaries.caseStudies.year" listName="yearList" /]
        </div>
        
        [#-- Countries --]
        <div class="fullBlock">
          [@customForm.select name="countriesSelected" label="" i18nkey="summaries.caseStudies.countries" listName="countries" keyFieldName="id" displayFieldName="name" multiple=true /]
        </div>
        
        [#-- Types --]
        <div class="fullBlock">
          <h6>[@s.text name="summaries.caseStudies.selectType" /]</h6>
          <div class="checkboxGroup">
            [@s.fielderror cssClass="fieldError" fieldName="typesSelected"/]
            [@s.checkboxlist name="typesSelected" list="caseStudyTypeList" listKey="id" listValue="name" cssClass="checkbox" /]
          </div>                          
        </div>
        
      </div>
    </div>
    
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.generate" /][/@s.submit]
    </div>
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]