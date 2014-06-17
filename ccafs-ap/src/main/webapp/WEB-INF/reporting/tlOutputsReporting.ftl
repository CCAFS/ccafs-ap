[#ftl]
[#--
 
 * This file is part of CCAFS Planning and Reporting Platform.
 *
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 *
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see 
 * <http://www.gnu.org/licenses/>
  
--]

[#assign title = "Theme Leader Outputs Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/reporting/tlOutputsReporting.js"] /] 
[#-- assign customCSS = ["${baseUrl}/css/reporting/partnersReporting.css"] / --]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "tlRpl" /]
[#assign currentStage = "tlOutputSummary" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]


<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="reporting.tlOutputSummaries.help" /]</p>
  </div>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  [@s.form action="tlOutputs"]
    <article class="halfContent">
      [#include "/WEB-INF/reporting/tlRplSubMenu.ftl" /]
      <h1 class="contentTitle">
        [@s.text name="reporting.tlOutputSummaries" /] - ${currentUser.leader.acronym} 
      </h1>
      <div id="items">      
        <fieldset id="outputsGroup">
          <legend>[@s.text name="reporting.tlOutputSummaries" /]</legend>
          [#list tlOutputSummaries as tlOutputSummary]
            <div id="tlOutputSummary-${tlOutputSummary_index}">
              [#-- TL summary and output id --]
              [@s.hidden name="tlOutputSummaries[${tlOutputSummary_index}].id" /]              
              [@s.hidden name="tlOutputSummaries[${tlOutputSummary_index}].output.id" /]
              
              [#-- Title --]
              <div class="outputTitle">
                [@s.text name="reporting.tlOutputSummaries.output" /] ${tlOutputSummary.output.code}
              </div>
              <div class="outputDescription">
                ${tlOutputSummary.output.description}
              </div>
              
              <div class="fullBlock">
                [@customForm.textArea name="tlOutputSummaries[${tlOutputSummary_index}].description" i18nkey="reporting.tlOutputSummaries.description" /]
              </div>
              
              [#-- separator --]
              <hr />
            </div>
        [/#list]
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