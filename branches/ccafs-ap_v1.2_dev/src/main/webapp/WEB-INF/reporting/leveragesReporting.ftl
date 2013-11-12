[#ftl]
[#assign title = "Leverages Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/reporting/leveragesReporting.js"] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "leverages" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="reporting.leverages.help" /]</p>
  </div>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  [@s.form action="leverages"]
    <article class="halfContent">
      <h1 class="contentTitle">
        [@s.text name="reporting.leverages" /] - ${currentUser.leader.acronym} 
      </h1>
      <div id="items">
        [#if leverages?has_content]
          [#list leverages as leverage]
            
          [/#list]
        [/#if]
        
        [#-- Template --]
        [#-- [@customForm.textArea name="leverages[${ir_index}].supportLinks" i18nkey="reporting.indicators.supportLinks" value="${ir.supportLinks!''}" /] --]
      </div>
      
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
    </article>
  [/@s.form]
 
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]