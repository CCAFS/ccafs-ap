[#ftl]
[#assign title = "Regional Program Leader Synthesis Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#-- assign customJS = ["${baseUrl}/js/reporting/outcomesReporting.js", "${baseUrl}/js/global/utils.js"] / --]
[#-- assign customCSS = ["${baseUrl}/css/reporting/partnersReporting.css"] / --]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "tlRpl" /]
[#assign currentStage = "rplSynthesisReport" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]


<section>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  [@s.form action="rplSynthesis"]
    <article class="halfContent">
      <div id="items">
        <fieldset id="rplSynthesisGroup">
          <legend>[@s.text name="reporting.rplSynthesisreport" /]</legend>
          
            <div id="synthesisReport">
              [#-- RPL Synthesis id --]
              [@s.hidden name="synthesisReport.id" /]
              
              <div class="fullBlock">
                [@customForm.textArea name="synthesisReport.ccafsSites" i18nkey="reporting.synthesisReport.ccafsSites" /]
              </div>
              
              <div class="fullBlock">
                [@customForm.textArea name="synthesisReport.crossCenter" i18nkey="reporting.synthesisReport.crossCenter" /]
              </div>
              
              <div class="fullBlock">
                [@customForm.textArea name="synthesisReport.regional" i18nkey="reporting.synthesisReport.regional" /]
              </div>
              
              <div class="fullBlock">
                [@customForm.textArea name="synthesisReport.decisionSupport" i18nkey="reporting.synthesisReport.decisionSupport" /]
              </div>              
            
            </div>
       
        </fieldset>
      </div>      
      
      [@s.submit type="button" name="save"]SAVE[/@s.submit]
      
      [#include "/WEB-INF/reporting/TLRPLSubMenu.ftl" /]  
    </article>
  [/@s.form]
 
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]