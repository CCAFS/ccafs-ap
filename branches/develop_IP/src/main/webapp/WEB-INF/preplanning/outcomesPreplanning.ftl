[#ftl]
[#assign title = "Main information" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/preplanning/outcomesPreplanning.js"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "impactPathways" /]
[#assign currentStage = "outcomes" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/indicatorTemplate.ftl" as indicatorTemplate/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="preplanning.outcomes.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl" /]
  
  [@s.form action="outcomes" cssClass="pure-form"]  
  <article class="halfContent" id="outcomes" class="impactPathway">
  	[#include "/WEB-INF/preplanning/ipPreplanningSubMenu.ftl" /]
    <h1 class="contentTitle">
      [@s.text name="preplanning.outcomes.title" /]
    </h1>
    
    <div id="outcomesBlock" class="outcome">

      [#if outcomes?has_content]
        [#list outcomes as outcome]
          [#-- Outcome identifier --]
          <input type="hidden" name="id" value="${outcome.id}" />
          [#-- Title --]
          [@customForm.textArea name="outcomes[${outcome_index}].description" i18nkey="preplanning.outcomes.outcome" required=true /]
          <div class="contentElements outcomeIndicatorsBlock"> 
            <div class="itemIndex">[@s.text name="preplanning.outcomes.indicators" /] </div>
            [#if outcome.indicators?has_content]
              [#-- Indicators --]
              [#list outcome.indicators as indicator]
                [@indicatorTemplate.outcomes outcome_index="${outcome_index}" indicator_index="${indicator_index}" value="${indicator.id}" /]
              [/#list]
            [#else]
              [@indicatorTemplate.outcomes outcome_index="${outcome_index}" indicator_index="${indicator_index}"/]
            [/#if]
            
            [#-- Add Indicator --]
            <div class="fullBlock" id="addIndicatorBlock">
              [@customForm.button i18nkey="preplanning.outcomes.addIndicator" class="addButton" /]
            </div> 
          </div>
        [/#list]
      [#else]
          [#-- Outcome identifier --]
          <input type="hidden" name="id" value="-1" /> 
          [#-- Title --]
          [@customForm.textArea name="outcomes[0].description" i18nkey="preplanning.outcomes.outcome" required=true /] 

          <div class="contentElements outcomeIndicatorsBlock">
            <div class="itemIndex">[@s.text name="preplanning.outcomes.indicators" /] </div>
              [@indicatorTemplate.outcomes /] 
              [#-- Add Indicator --]
              <div class="fullBlock" id="addIndicatorBlock">
                [@customForm.button i18nkey="preplanning.outcomes.addIndicator" class="addButton" /]
              </div> 
            </div>
          </div>

      [/#if]
    </div>
    
    [#-- Outcome 2025 template --]
    <div id="outcomeTemplate" class="outcome" style="display:none">
      [#-- Outcome identifier --]
      <input type="hidden" name="id" value="-1" /> 
      [#-- Title --]
      [@customForm.textArea name="description" i18nkey="preplanning.outcomes.outcome" required=true /] 
      <div class="contentElements">
      	<div class="itemIndex">[@s.text name="preplanning.outcomes.indicators" /] </div>
      	[#-- Indicator template --] 
      	[@indicatorTemplate.outcomes template=true /] 
      </div>
    </div>
    [#-- End Outcome 2025 template --]
    
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
     
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]