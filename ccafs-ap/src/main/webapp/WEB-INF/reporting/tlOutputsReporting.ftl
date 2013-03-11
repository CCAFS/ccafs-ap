[#ftl]
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
      
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
      
      [#include "/WEB-INF/reporting/tlRplSubMenu.ftl" /]  
    </article>
  [/@s.form]
 
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]