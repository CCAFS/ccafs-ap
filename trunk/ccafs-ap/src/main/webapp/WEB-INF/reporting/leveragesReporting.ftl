[#ftl]
[#assign title = "Leverages Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/reporting/leveragesReporting.js"] /]
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
        <fieldset>
          [#if leverages?has_content]
            [#list leverages as leverage]
              <div class="leverage" id="leverage-${leverage_index}">
                [#-- Item index --]
                <div class="itemIndex">
                  [@s.text name="reporting.leverages.leverageTitle" /] ${leverage_index +1}
                </div>
                
                [#-- Remove link for a leverage --]
                <div class="removeLink">
                  <img src="${baseUrl}/images/global/icon-remove.png" />
                  <a id="removeLeverage-${leverage_index}" href="" class="removeLeverage">[@s.text name="reporting.leverages.removeLeverage" /]</a>
                </div>
                
                [#-- Identifier --]
                <input type="hidden" name="leverages[${leverage_index}].id" id="id" value="${leverage.id}" />
                
                [#-- Leverage title --]
                <div id="titleBlock" class="fullBlock">
                  [@customForm.textArea name="leverages[${leverage_index}].title" i18nkey="reporting.leverages.title" value="${leverage.title!''}" /]
                </div>
                
                [#-- Leverage partner name --]
                <div id="partnerBlock" class="fullBlock" >
                  [@customForm.input name="leverages[${leverage_index}].partnerName" i18nkey="reporting.leverages.partnerName" value="${leverage.partnerName!''}" /]          
                </div>
                
                [#-- Leverage theme --]
                <div id="themeBlock" class="thirdPartBlock" >
                  [@customForm.select name="leverages[${leverage_index}].theme.id" label="" i18nkey="reporting.leverages.theme" listName="themeList" value="${leverage.theme.id}" /]
                </div>
                
                [#-- Leverage start date --]
                <div id="startDateBlock" class="thirdPartBlock" >
                  [@customForm.select name="leverages[${leverage_index}].startYear" label="" i18nkey="reporting.leverages.startYear" listName="yearList" value="${leverage.startYear?c}" /]            
                </div>
                
                [#-- Leverage end date --]
                <div id="endDateBlock" class="thirdPartBlock" >
                  [@customForm.select name="leverages[${leverage_index}].endYear" label="" i18nkey="reporting.leverages.endYear" listName="yearList" value="${leverage.endYear?c}" /]            
                </div>
                
                [#-- Leverage budget --]
                <div id="budgetBlock" class="thirdPartBlock" >
                  [@customForm.input name="leverages[${leverage_index}].budget" i18nkey="reporting.leverages.budget" value="${leverage.budget?c}" /]          
                </div>
                
                <hr />
              </div>
            [/#list]
            
          [/#if]
        
          <div class="addLink">
            <img src="${baseUrl}/images/global/icon-add.png" />
            <a href="" class="addLeverage">[@s.text name="reporting.leverages.addNewLeverage" /]</a>
          </div>
        </fieldset>
      </div>
      
      [#-- Template --]
      <div id="template" style="display:none;">
        <div class="leverage" id="leverage-9999" >
          <div class="itemIndex">
            [@s.text name="reporting.leverages.leverageTitle" /]
          </div>
          <div class="removeLink">
            <img src="${baseUrl}/images/global/icon-remove.png" />
            <a id="removeLeverage-0" class="removeLeverage" href="" >[@s.text name="reporting.leverages.removeLeverage" /]</a>
          </div>
          [#-- Identifier --]
          <input type="hidden" id="id" value="-1" />
          <div id="titleBlock" class="fullBlock">
            [@customForm.textArea name="title" i18nkey="reporting.leverages.title" /]
          </div>
          <div id="partnerBlock" class="fullBlock" >
            [@customForm.input name="partnerName" i18nkey="reporting.leverages.partnerName"  /]          
          </div>
          [#-- Leverage theme --]
          <div id="themeBlock" class="thirdPartBlock" >
            [@customForm.select name="theme.id" label="" i18nkey="reporting.leverages.theme" listName="themeList" /]
          </div>          
          <div id="startDateBlock" class="thirdPartBlock" >
            [@customForm.select name="startYear" label="" i18nkey="reporting.leverages.startYear" listName="yearList" /]            
          </div>
          <div id="endDateBlock" class="thirdPartBlock" >
            [@customForm.select name="endYear" label="" i18nkey="reporting.leverages.endYear" listName="yearList" /]            
          </div>
          <div id="budgetBlock" class="thirdPartBlock" >
            [@customForm.input name="budget" i18nkey="reporting.leverages.budget" /]          
          </div>
        </div>
      </div>
      
      [#if canSubmit]
        <div class="buttons">
          [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
          [#-- @s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit --]
          [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
        </div>
      [/#if]
    </article>
  [/@s.form]
 
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]