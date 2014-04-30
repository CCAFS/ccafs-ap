
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
          <ul> 
          [#list indicatorReports as ir ]
            [#if ir.indicator.type.id != indicatorTypeId ]
              [#-- If this is not the first div, close the previous one --]
              [#if indicatorTypeId != 0]  [/#if]
              
              [#-- Assign the new value of indicatorTypeID --]
              [#assign indicatorTypeId = ir.indicator.type.id]
              
              <li><a href="#tabs-${indicatorTypeId}">Indicator type ${indicatorTypeId}</a></li> 
               
            [/#if] 
          [/#list]
          </ul> 
          [#assign indicatorTypeId = 0]
          [#list indicatorReports as ir ]  
          	  
          	  [#if ir.indicator.type.id != indicatorTypeId ]
                [#-- If this is not the first div, close the previous one --]
                [#if indicatorTypeId != 0] </div> [/#if]
              
                [#-- Assign the new value of indicatorTypeID --]
                [#assign indicatorTypeId = ir.indicator.type.id]
                
                <div id="tabs-${indicatorTypeId}">
              	<div class="itemIndex">${ir.indicator.type.name}</div>
              [/#if] 
              	<br>
                [#-- Indicator title --]
                <h6>${ir_index+1}. ${ir.indicator.name} </h6>
                
                [#-- Indicator report id --]
                <input name="indicatorReports[${ir_index}].id" value="${ir.id?c}" type="hidden" />
                
                [#-- Indicator description --]
                [#if ir.indicator.description?has_content]
                  <p class="indicator_description" style="display:none">
                    ${ir.indicator.description}
                  </p>
                  <div>
                    <a class="toggleDescription show">[@s.text name="reporting.indicators.showDescription" /]</a>
                  </div>
                [/#if]
                
                [#-- Target --]
                <div class="thirdPartBlock">
                  <span class="halfPartBlock"> <h6>[@s.text name="reporting.indicators.target" ] [@s.param]${currentReportingYear?c}:[/@s.param] [/@s.text] </h6></span>
                  <span class="thirdPartBlock">[#if ir.target?has_content]${ir.target}[#else] 0 [/#if]</span>
                </div>
                
                [#-- Actual --]
                <div class="thirdPartBlock">
                  <div class="thirdPartBlock">
                    <h6>[@s.text name="reporting.indicators.actual" /]</h6>
                  </div>
                  <div class="halfPartBlock">
                    [#if ir.actual?has_content]
                      [#assign actual = ir.actual]
                    [#else]
                      [#assign actual = ""]
                    [/#if]
                    [@customForm.input name="indicatorReports[${ir_index}].actual" type="text" i18nkey="reporting.indicators.actual" value="${actual}" showTitle=false /]
                  </div>
                </div>
                
                [#-- Next year target --]
                <div class="thirdPartBlock">
                  <div class="thirdPartBlock">
                    <h6>[@s.text name="reporting.indicators.target" ] [@s.param]${(currentReportingYear+1)?c }:[/@s.param] [/@s.text]</h6>
                  </div>
                  <div class="halfPartBlock">                    
                    [#if ir.nextYearTarget?has_content]
                      [#assign nextYearTarget = ir.nextYearTarget]
                    [#else]
                      [#assign nextYearTarget = ""]
                    [/#if]
                    [@customForm.input name="indicatorReports[${ir_index}].nextYearTarget" type="text" i18nkey="reporting.indicators.actual" value="${nextYearTarget}" showTitle=false /]
                  </div>
                </div>
                
                [#-- Support links --]
                <div class="fullBlock">
                  [@customForm.textArea name="indicatorReports[${ir_index}].supportLinks" i18nkey="reporting.indicators.supportLinks" value="${ir.supportLinks!''}" /]
                </div>
                
                [#-- Deviation --]
                <div class="fullBlock" >
                  [@customForm.textArea name="indicatorReports[${ir_index}].deviation" i18nkey="reporting.indicators.deviation" value="${ir.deviation!''}" /]
                </div>
                
                <hr/> 
              
          [/#list]
            [#-- Close the last div of the list --]
        </div>
      </div>
      <input type="hidden" id="showDescriptionText" value="[@s.text name="reporting.indicators.showDescription" /]" />
      <input type="hidden" id="hideDescriptionText" value="[@s.text name="reporting.indicators.hideDescription" /]" />
      
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