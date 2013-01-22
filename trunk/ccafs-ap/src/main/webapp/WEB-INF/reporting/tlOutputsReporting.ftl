[#ftl]
[#assign title = "Theme Leader Outputs Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#-- assign customJS = ["${baseUrl}/js/reporting/outcomesReporting.js", "${baseUrl}/js/global/utils.js"] / --]
[#-- assign customCSS = ["${baseUrl}/css/reporting/partnersReporting.css"] / --]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "tlRpl" /]
[#assign currentStage = "tlOutputSummary" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]


<section>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  [@s.form action="tlOutputs"]
    <article class="halfContent">
      <div id="items">      
        <fieldset id="outcomeGroup">
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
                [@customForm.textArea name="tlOutputSummaries[${tlOutputSummary_index}].description"  /]
              </div>
              
              [#-- separator --]
              <hr />
            </div>
        [/#list]
        </fieldset>        
      </div>      
      
      [@s.submit type="button" name="save"]SAVE[/@s.submit]
      
      [#include "/WEB-INF/reporting/TLRPLSubMenu.ftl" /]  
    </article>
  [/@s.form]
 
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]