[#ftl]
[#assign title = "Indicators Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/reporting/indicatorsReporting.js"] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "indicators" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="reporting.indicators.help" /]</p>
  </div>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  [@s.form action="indicators"]
    <article class="halfContent">
      <h1 class="contentTitle">
        [@s.text name="reporting.indicators" /] - ${currentUser.leader.acronym} 
      </h1>
      <div id="items">      
        <div id="accordion">
          [#assign indicatorTypeId = 0]
          [#list indicatorReports as ir ]
            [#if ir.indicator.type.id != indicatorTypeId ]
              [#-- If this is not the first div, close the previous one --]
              [#if indicatorTypeId != 0] </div> [/#if]
              
              [#-- Assign the new value of indicatorTypeID --]
              [#assign indicatorTypeId = ir.indicator.type.id]
              <h3>${ir.indicator.type.name}</h3>
              <div> 
            [/#if]
              <div> 
                [#-- Indicator title --]
                <p>${ir_index+1}. ${ir.indicator.name} </p>
                
                [#-- Indicator report id --]
                <input name="indicatorReports[${ir_index}].id" value="${ir.id}" type="hidden" />
                
                [#-- Indicator description --]
                [#if ir.indicator.description?has_content]
                  <p class="indicator_description">
                    ${ir.indicator.description}
                  </p>
                [/#if]
                
                [#-- Target --]
                <div class="halfPartBlock">
                  <span class="thirdPartBlock"> <h6>[@s.text name="reporting.indicators.target" /] </h6></span>
                  <span class="halfPartBlock">${ir.target}</span>
                </div>
                
                [#-- Actual --]
                <div class="halfPartBlock">
                  <div class="thirdPartBlock">
                    <h6>[@s.text name="reporting.indicators.actual" /]</h6>
                  </div>
                  <div class="halfPartBlock">                    
                    [@customForm.input name="indicatorReports[${ir_index}].actual" type="text" i18nkey="reporting.indicators.actual" value="${ir.actual}" showTitle=false /]
                  </div>
                </div>
                
                [#-- Support links --]
                <div class="fullBlock">
                  [@customForm.textArea name="indicatorReports[${ir_index}].supportLinks" i18nkey="reporting.indicators.supportLinks" value="${ir.supportLinks!''}" /]
                </div>
                
                [#-- Deviation --]
                <div class="fullBlock">
                  [@customForm.textArea name="indicatorReports[${ir_index}].deviation" i18nkey="reporting.indicators.deviation" value="${ir.deviation!''}" /]
                </div>
                
                <hr/>
              </div> 
          [/#list]
            [#-- Close the last div of the list --]
        </div>
      </div>
      
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
    </article>
  [/@s.form]
 
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]