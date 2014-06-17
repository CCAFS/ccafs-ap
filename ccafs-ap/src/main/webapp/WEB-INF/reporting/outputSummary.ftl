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

[#assign title = "Summary by outputs" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = [""] /]
[#assign customCSS = [""] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "outputs" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
  <section class="content">
    <div class="helpMessage">
      <img src="${baseUrl}/images/global/icon-help.png" />
      <p>[@s.text name="reporting.outputSummary.help" /]</p>
    </div>
    [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]  
    [@s.form action="outputSummary"]
    <article class="halfContent">
        <h1 class="contentTitle">
          [@s.text name="reporting.outputSummary.outputSummary" /] - ${currentUser.leader.acronym} 
        </h1>
        <fieldset>
        [#list outputSummaries as outputSummary]
          <div id="outputSummary-${outputSummary_index}">
            [#-- Title --]
            <div class="outputTitle">
              [@s.text name="reporting.outputSummary.output" /] ${outputSummary.output.objective.theme.code}.${outputSummary.output.objective.code}.${outputSummary.output.code}
            </div>
            
            [#-- Output description --]
            <div class="outputDescription">
              ${outputSummary.output.description}
            </div>
            
            [#-- Output Summary --]
            <div class="outputSummary">
              [@customForm.textArea name="outputSummaries[${outputSummary_index}].description" i18nkey="reporting.outputSummary.description" /]
            </div>
            
            [#-- separator --]
            <hr />            
            
          </div>
        [/#list]
        </fieldset>
        
        [#if canSubmit]
          <div class="buttons">
            [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
            [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
            [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
          </div>
        [/#if]
        
      [/@s.form]
    </article>
  </section>
[#include "/WEB-INF/global/pages/footer.ftl"]