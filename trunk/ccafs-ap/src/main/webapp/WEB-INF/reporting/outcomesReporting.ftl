[#ftl]
[#assign title = "Outcomes Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/reporting/outcomesReporting.js", "${baseUrl}/js/global/utils.js"] /]
[#-- assign customCSS = ["${baseUrl}/css/reporting/partnersReporting.css"] / --]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "outcomes" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

[#macro outcomeSection]
  [#list outcomes as outcome]
    <div id="outcome-${outcome_index}" class="outcome">
      
      [#-- Remove link for an outcome --]
      <div class="removeLink">
        <a id="removeOutcome-${outcome_index}" href="" class="removeOutcome">Remove outcome</a>
      </div>
      
      [#-- Identifier --]
      <input type="hidden" name="outcomes[${outcome_index}].id" value="${outcome.id}" />
      
      [#-- Outcome --]
      <div class="fullBlock">
        [@customForm.textArea name="outcomes[${outcome_index}].outcome" i18nkey="reporting.outcomes.outcome" /]
      </div>     
      
      [#-- Output --]
      <div class="fullBlock">
        [@customForm.textArea name="outcomes[${outcome_index}].outputs" i18nkey="reporting.outcomes.outputs" /]
      </div>
      
      [#-- partners --]
      <div class="fullBlock">
        [@customForm.textArea name="outcomes[${outcome_index}].partners" i18nkey="reporting.outcomes.partners" /]
      </div>
      
      [#-- Output User --]
      <div class="fullBlock">
        [@customForm.textArea name="outcomes[${outcome_index}].outputUser" i18nkey="reporting.outcomes.outputUser" /]
      </div>
      
      [#-- How Used --]
      <div class="fullBlock">
        [@customForm.textArea name="outcomes[${outcome_index}].howUsed" i18nkey="reporting.outcomes.howUsed" /]
      </div>
      
      [#-- Evidence --]
      <div class="fullBlock">
        [@customForm.textArea name="outcomes[${outcome_index}].evidence" i18nkey="reporting.outcomes.evidence" /]
      </div>
      
    </div> <!-- End outcomes-${outcome_index} -->
    <hr />
  [/#list]
[/#macro]

<section class="content">
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  [@s.form action="outcomes"]
    <article class="halfContent">
      <div id="items">      
        <fieldset id="outcomeGroup">
          <legend>[@s.text name="reporting.outcomes" /]</legend>
          [@outcomeSection /]
        </fieldset>
        <div>
          <a href="" class="addOutcome">Add new outcome</a>
        </div>   
      </div>
      
      <!-- OUTCOME TEMPLATE -->
      <div id="template">
        <div id="outcome-9999" class="outcome" style="display: none;">      
          [#-- Remove link for an outcome --]
          <div class="removeLink">
            <a id="removeOutcome-9999" href="" class="removeOutcome">Remove outcome</a>
          </div>
          
          [#-- Identifier --]
          <input type="hidden" name="id" value="-1" />
          
          [#-- Outcome --]
          <div class="fullBlock">
            [@customForm.textArea name="outcome" i18nkey="reporting.outcomes.outcome" /]
          </div>
          
          [#-- Output --]
          <div class="fullBlock">
            [@customForm.textArea name="outputs" i18nkey="reporting.outcomes.outputs" /]
          </div>
          
          [#-- partners --]
          <div class="fullBlock">
            [@customForm.textArea name="partners" i18nkey="reporting.outcomes.partners" /]
          </div>
          
          [#-- Output User --]
          <div class="fullBlock">
            [@customForm.textArea name="outputUser" i18nkey="reporting.outcomes.outputUser" /]
          </div>
          
          [#-- How Used --]
          <div class="fullBlock">
            [@customForm.textArea name="howUsed" i18nkey="reporting.outcomes.howUsed" /]
          </div>
          
          [#-- Evidence --]
          <div class="fullBlock">
            [@customForm.textArea name="evidence" i18nkey="reporting.outcomes.evidence" /]
          </div>
        </div> <!-- End outcome template -->
      </div> <!-- End template -->
      
      [@s.submit type="button" name="save"]SAVE[/@s.submit]
    </article>
  [/@s.form]
 
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]