[#ftl]
[#assign title = "Summary by outputs" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = [""] /]
[#assign customCSS = [""] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "outputs" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
  <section class="content">
    [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]  
    <article class="halfContent">
      [@s.form action="outputSummary"]
        <h1 class="contentTitle">
          [@s.text name="reporting.outputSummary.outputSummary" /] - ${currentUser.leader.acronym} 
        </h1>
        
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
        [@s.submit type="button" name="save"]SAVE[/@s.submit]
      [/@s.form]
    </article>
  </section>
[#include "/WEB-INF/global/pages/footer.ftl"]