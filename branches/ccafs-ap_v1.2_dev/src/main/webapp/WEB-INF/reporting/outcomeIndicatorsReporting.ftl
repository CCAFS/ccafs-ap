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

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="reporting.outcomes.help" /]</p>
  </div>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  [@s.form action="outcomeIndicators"]
    <article class="halfContent">
      <h1 class="contentTitle">
        [@s.text name="reporting.outcomeIndicators" /] - ${currentUser.leader.acronym} 
      </h1>
      <div id="items">
        <fieldset id="outcomeIndicatorsGroup">
          [#list outcomeIndicators as oIndicator]
            [@customForm.textArea name="outcomeIndicators[${oIndicator_index}].outcome" i18nkey="reporting.outcomes.outcome" /]
          [/#list]
        </fieldset>
      </div>

      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
    </article>
  [/@s.form]

  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]