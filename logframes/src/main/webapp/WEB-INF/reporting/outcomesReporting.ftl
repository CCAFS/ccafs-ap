[#ftl]
[#assign title = "Outcomes Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/reporting/outcomesReporting.js", "${baseUrl}/js/global/utils.js"] /]
[#-- assign customCSS = ["${baseUrl}/css/reporting/partnersReporting.css"] / --]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "outcomes" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

[#macro outcomeSection]
  [#list outcomes as outcome]
    <div id="outcome-${outcome_index}" class="outcome">
      
      [#-- Item index --]
      <div class="itemIndex">
        [@s.text name="reporting.outcomes.outcomeTitle" /] ${outcome_index +1}
      </div>
      
      [#-- Remove link for an outcome --]
      <div class="removeLink">
        <img src="${baseUrl}/images/global/icon-remove.png" />
        <a id="removeOutcome-${outcome_index}" href="" class="removeOutcome">[@s.text name="reporting.outcomes.removeOutcome" /]</a>
      </div>
      
      [#-- Identifier --]
      <input type="hidden" name="outcomes[${outcome_index}].id" value="${outcome.id}" />
      
      [#-- Title --]
      <div class="fullBlock">
        [@customForm.textArea name="outcomes[${outcome_index}].title" i18nkey="reporting.outcomes.title" help="reporting.outcomes.title.help" /]
      </div>
      
      [#-- Outcome --]
      <div class="fullBlock">
        [@customForm.textArea name="outcomes[${outcome_index}].outcome" i18nkey="reporting.outcomes.outcome" help="reporting.outcomes.outcome.help" /]
      </div>
      
      [#-- Output --]
      <div class="fullBlock">
        [@customForm.textArea name="outcomes[${outcome_index}].outputs" i18nkey="reporting.outcomes.outputs" help="reporting.outcomes.outputs.help" /]
      </div>
      
      [#-- partners --]
      <div class="fullBlock">
        [@customForm.textArea name="outcomes[${outcome_index}].partners" i18nkey="reporting.outcomes.partners" help="reporting.outcomes.partners.help" /]
      </div>
      
      [#-- Activities --]
      <div class="fullBlock">
        [@customForm.textArea name="outcomes[${outcome_index}].activities" i18nkey="reporting.outcomes.activities" help="reporting.outcomes.activities.help" /]
      </div>
      
      [#-- Non research partners --]
      <div class="fullBlock">
        [@customForm.textArea name="outcomes[${outcome_index}].nonResearchPartners" i18nkey="reporting.outcomes.nonResearchPartners" help="reporting.outcomes.nonResearchPartners.help" /]
      </div>
      
      [#-- Output User --]
      <div class="fullBlock">
        [@customForm.textArea name="outcomes[${outcome_index}].outputUser" i18nkey="reporting.outcomes.outputUser" help="reporting.outcomes.outputUser.help" /]
      </div>
      
      [#-- How Used --]
      <div class="fullBlock">
        [@customForm.textArea name="outcomes[${outcome_index}].howUsed" i18nkey="reporting.outcomes.howUsed" help="reporting.outcomes.howUsed.help" /]
      </div>
      
      [#-- Evidence --]
      <div class="fullBlock">
        [@customForm.textArea name="outcomes[${outcome_index}].evidence" i18nkey="reporting.outcomes.evidence" help="reporting.outcomes.evidence.help" /]
      </div>
      
    </div> <!-- End outcomes-${outcome_index} -->
    <hr />
  [/#list]
[/#macro]

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="reporting.outcomes.help" /]</p>
    <p><a target="_BLANK" href="../resources/documents/CCAFS CU outcome story.pdf" >[@s.text name="reporting.outcomes.help2" /]</a></p>
  </div>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  [@s.form action="outcomes"]
    <article class="halfContent">
      <h1 class="contentTitle">
        [@s.text name="reporting.outcomes" /] - ${currentUser.leader.acronym} 
      </h1>
      <div id="items">      
        <fieldset id="outcomeGroup">
          
          [@outcomeSection /]
          <div class="addLink">
            <img src="${baseUrl}/images/global/icon-add.png" />
            <a href="" class="addOutcome">[@s.text name="reporting.outcomes.addNewOutcome" /]</a>
          </div>
        </fieldset>
      </div>
      
      <!-- OUTCOME TEMPLATE -->
      <div id="template">
        <div id="outcome-9999" class="outcome" style="display: none;">
          
          [#-- Item index --]
          <div class="itemIndex">
            [@s.text name="reporting.outcomes.outcomeTitle" /]
          </div>
          
          [#-- Remove link for an outcome --]
          <div class="removeLink">
            <img src="${baseUrl}/images/global/icon-remove.png" />
            <a id="removeOutcome-9999" href="" class="removeOutcome">[@s.text name="reporting.outcomes.removeOutcome" /]</a>
          </div>
          
          [#-- Identifier --]
          <input type="hidden" name="id" value="-1" />
          
          [#-- Title --]
          <div class="fullBlock">
            [@customForm.textArea name="title" i18nkey="reporting.outcomes.title" help="reporting.outcomes.title.help" /]
          </div>
          
          [#-- Outcome --]
          <div class="fullBlock">
            [@customForm.textArea name="outcome" i18nkey="reporting.outcomes.outcome" help="reporting.outcomes.outcomes.help" /]
          </div>
          
          [#-- Output --]
          <div class="fullBlock">
            [@customForm.textArea name="outputs" i18nkey="reporting.outcomes.outputs" help="reporting.outcomes.outputs.help" /]
          </div>
          
          [#-- partners --]
          <div class="fullBlock">
            [@customForm.textArea name="partners" i18nkey="reporting.outcomes.partners" help="reporting.outcomes.partners.help" /]
          </div>
          
          [#-- Activities --]
          <div class="fullBlock">
            [@customForm.textArea name="activities" i18nkey="reporting.outcomes.activities" help="reporting.outcomes.activities.help" /]
          </div>
          
          [#-- Non research partners --]
          <div class="fullBlock">
            [@customForm.textArea name="nonResearchPartners" i18nkey="reporting.outcomes.nonResearchPartners" help="reporting.outcomes.nonResearchPartners.help" /]
          </div>
          
          [#-- Output User --]
          <div class="fullBlock">
            [@customForm.textArea name="outputUser" i18nkey="reporting.outcomes.outputUser" help="reporting.outcomes.outputUser.help" /]
          </div>
          
          [#-- How Used --]
          <div class="fullBlock">
            [@customForm.textArea name="howUsed" i18nkey="reporting.outcomes.howUsed" help="reporting.outcomes.howUsed.help" /]
          </div>
          
          [#-- Evidence --]
          <div class="fullBlock">
            [@customForm.textArea name="evidence" i18nkey="reporting.outcomes.evidence" help="reporting.outcomes.evidence.help" /]
          </div>
        </div> <!-- End outcome template -->
      </div> <!-- End template -->
      
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