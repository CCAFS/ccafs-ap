 [#ftl]
[#assign title = "Regional Program Leader Synthesis Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#-- assign customJS = ["${baseUrl}/js/reporting/outcomesReporting.js", "${baseUrl}/js/global/utils.js"] / --]
[#-- assign customCSS = ["${baseUrl}/css/reporting/partnersReporting.css"] / --]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "tlRpl" /]
[#assign currentStage = "rplSynthesisReport" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]


<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="reporting.synthesisReport.help" /]</p>
  </div>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  [@s.form action="rplSynthesis"]
    <article class="halfContent">
      [#include "/WEB-INF/reporting/tlRplSubMenu.ftl" /]
      <h1 class="contentTitle">
        [@s.text name="reporting.rplSynthesisreport" /] - ${currentUser.leader.acronym} 
      </h1>
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
      
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
      
    </article>
  [/@s.form]
 
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]