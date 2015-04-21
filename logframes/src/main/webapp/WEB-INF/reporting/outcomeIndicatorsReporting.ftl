[#ftl]
[#assign title = "Outcomes Indicators Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#-- assign customJS = ["${baseUrl}/js/reporting/outcomesReporting.js"] / --]
[#-- assign customCSS = ["${baseUrl}/css/reporting/partnersReporting.css"] / --]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "outcomeIndicators" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

[#macro outcomeIndicators]
  [#list outcomeIndicatorReports as oir]
    [#if oir.outcomeIndicator.theme.code == currentIndicatorsTheme?string]
      <div class="outcomeIndicator">
        <p class="fullBlock">
          ${oir.outcomeIndicator.code}
          ${oir.outcomeIndicator.description}
        </p>

        [@customForm.textArea name="outcomeIndicatorReports[${oir_index}].achievements" i18nkey="reporting.outcomeIndicators.achievements" /]
        [@customForm.textArea name="outcomeIndicatorReports[${oir_index}].evidence" i18nkey="reporting.outcomeIndicators.evidence" /]
      </div>
    [/#if]
  [/#list]
[/#macro]

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="reporting.outcomeIndicators.help" /]</p>
  </div>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  [#if currentIndicatorsTheme == 1]
    [#assign actionName="themeOneOutcomeIndicators" /]
  [#elseif currentIndicatorsTheme == 2]
    [#assign actionName="themeTwoOutcomeIndicators" /]
  [#elseif currentIndicatorsTheme == 3]
    [#assign actionName="themeThreeOutcomeIndicators" /]
  [#elseif currentIndicatorsTheme == 4]
    [#assign actionName="themeFourOutcomeIndicators" /]
  [/#if]
  
  [@s.form action="${actionName}"]
    <article class="halfContent">
      [#include "/WEB-INF/reporting/outcomeIndicatorsSubMenu.ftl" /]
      <h1 class="contentTitle">
        [@s.text name="reporting.outcomeIndicators" /] - ${currentUser.leader.acronym} 
      </h1>
      <div id="items">
        <fieldset id="outcomeIndicatorsGroup">
          [@outcomeIndicators /]
        </fieldset>
      </div>

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