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
                <input type="hidden" name="leverages[${leverage_index}].id" value="${leverage.id}" />
                
                [#-- Leverage title --]
                <div id="titleBlock" class="fullBlock">
                  [@customForm.textArea name="leverages[0].title" i18nkey="reporting.leverages.title" value="${leverage.title!''}" /]
                </div>
                
                [#-- Leverage partner name --]
                <div id="partnerBlock" class="fullBlock" >
                  [@customForm.input name="leverages[0].partnerName" i18nkey="reporting.leverages.partnerName" value="${leverage.partnerName!''}" /]          
                </div>
                
                [#-- Leverage theme --]
                <div id="themeBlock" class="thirdPartBlock" >
                  [@customForm.select name="leverages[0].theme" label="" i18nkey="reporting.leverages.theme" listName="themeList" value="${leverage.theme.id}" /]
                </div>
                
                [#-- Leverage start date --]
                <div id="startDateBlock" class="thirdPartBlock" >
                  [@customForm.input name="leverages[0].startYear" i18nkey="reporting.leverages.startYear" value="${leverage.startYear?c}" /]          
                </div>
                
                [#-- Leverage end date --]
                <div id="endDateBlock" class="thirdPartBlock" >
                  [@customForm.input name="leverages[0].endYear" i18nkey="reporting.leverages.endYear" value="${leverage.endYear?c}" /]          
                </div>
                
                [#-- Leverage budget --]
                <div id="budgetBlock" class="thirdPartBlock" >
                  [@customForm.input name="leverages[0].budget" i18nkey="reporting.leverages.budget" value="${leverage.budget?c}" /]          
                </div>
                
                <hr />
              </div>
            [/#list]
            
          [#else]
            <div class="leverage-0">
              [#-- Item index --]
              <div class="itemIndex">
                [@s.text name="reporting.leverages.leverageTitle" /] 1
              </div>
              
              [#-- Remove link for a leverage --]
              <div class="removeLink">
                <img src="${baseUrl}/images/global/icon-remove.png" />
                <a id="removeLeverage-0" href="" class="removeLeverage" href="">[@s.text name="reporting.leverages.removeLeverage" /]</a>
              </div>
                
              [#-- Leverage title --]
              <div id="titleBlock" class="fullBlock">
                [@customForm.textArea name="leverages[0].title" i18nkey="reporting.leverages.title" /]
              </div>
              
              [#-- Identifier --]
              <input type="hidden" name="leverages[0].id" value="-1" />
              
              [#-- Leverage partner name --]
              <div id="partnerBlock" class="fullBlock" >
                [@customForm.input name="leverages[0].partnerName" i18nkey="reporting.leverages.partnerName" /]          
              </div>
              
              [#-- Leverage theme --]
              <div id="themeBlock" class="thirdPartBlock" >
                [@customForm.select name="leverages[0].theme" label="" i18nkey="reporting.leverages.theme" listName="themeList"  /]
              </div>
              
              [#-- Leverage start date --]
              <div id="startDateBlock" class="thirdPartBlock" >
                [@customForm.input name="leverages[0].startYear" i18nkey="reporting.leverages.startYear" /]          
              </div>
              
              [#-- Leverage end date --]
              <div id="endDateBlock" class="thirdPartBlock" >
                [@customForm.input name="leverages[0].endYear" i18nkey="reporting.leverages.endYear" /]          
              </div>
              
              [#-- Leverage budget --]
              <div id="budgetBlock" class="thirdPartBlock" >
                [@customForm.input name="leverages[0].budget" i18nkey="reporting.leverages.budget" /]          
              </div>
              
              <hr />
            </div>
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
          <div id="titleBlock" class="fullBlock">
            [@customForm.textArea name="title" i18nkey="reporting.leverages.title" /]
          </div>
          [#-- Identifier --]
          <input type="hidden" name="id" value="-1" />
          <div id="partnerBlock" class="fullBlock" >
            [@customForm.input name="partnerName" i18nkey="reporting.leverages.partnerName"  /]          
          </div>
          <div id="budgetBlock" class="thirdPartBlock" >
            [@customForm.input name="budget" i18nkey="reporting.leverages.budget" /]          
          </div>
          <div id="startDateBlock" class="thirdPartBlock" >
            [@customForm.input name="startYear" i18nkey="reporting.leverages.startYear" /]          
          </div>
          <div id="endDateBlock" class="thirdPartBlock" >
            [@customForm.input name="endYear" i18nkey="reporting.leverages.endYear" /]          
          </div>
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