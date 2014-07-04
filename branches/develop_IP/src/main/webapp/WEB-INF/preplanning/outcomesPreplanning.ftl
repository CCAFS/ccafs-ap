[#ftl]
[#assign title = "Main information" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/preplanning/outcomesPreplanning.js"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "outcomes" /]
[#assign currentStage = "outcomes" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.mainInformation.help" /] </p>
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
                <div class="indicator" style="display:block">
                  <input type="hidden" name="outcomes[${outcome_index}].indicators[${indicator_index}].id" value="${indicator.id}" />  
                  [@customForm.textArea showTitle=false name="outcomes[${outcome_index}].indicators[${indicator_index}].description" i18nkey="preplanning.outcomes.outcome" required=true /]
                  [@customForm.input name="outcomes[${outcome_index}].indicators[${indicator_index}].target"  i18nkey="preplanning.outcomes.target" /]  
                  [#-- remove link --]      
                  <div class="removeLink">            
                    <img src="${baseUrl}/images/global/icon-remove.png" />
                    <a id="removeOutcomeIndicator" href="" class="removeOutcomeIndicator">[@s.text name="preplanning.outcomes.removeIndicator" /]</a>
                  </div>
                </div>
              [/#list]
            [#else]
              <div class="indicator" style="display:block">
                <input type="hidden" name="outcomes[${outcome_index}].indicators[0].id" value="-1" />  
                [@customForm.textArea showTitle=false name="outcomes[${outcome_index}].indicators[0].description" i18nkey="preplanning.outcomes.outcome" required=true /]
                [@customForm.input name="outcomes[${outcome_index}].indicators[0].target"  i18nkey="preplanning.outcomes.target" /]  
                [#-- remove link --]      
                <div class="removeLink">            
                  <img src="${baseUrl}/images/global/icon-remove.png" />
                  <a id="removeOutcomeIndicator" href="" class="removeOutcomeIndicator">[@s.text name="preplanning.outcomes.removeIndicator" /]</a>
                </div>
              </div>
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
              
              <div class="indicator" style="display:block">
                <input type="hidden" name="outcomes[0].indicators[0].id" value="-1" />  
                [@customForm.textArea showTitle=false name="outcomes[0].indicators[0].description" i18nkey="preplanning.outcomes.outcome" required=true /]
                [@customForm.input name="outcomes[0].indicators[0].target"  i18nkey="preplanning.outcomes.target" /]  
                [#-- remove link --]      
                <div class="removeLink">            
                  <img src="${baseUrl}/images/global/icon-remove.png" />
                  <a id="removeOutcomeIndicator" href="" class="removeOutcomeIndicator">[@s.text name="preplanning.outcomes.removeIndicator" /]</a>
                </div>
              </div>
                
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
      	<div class="indicator">  
      		[@customForm.textArea showTitle=false name="description" i18nkey="preplanning.outcomes.outcome" required=true /]
          [@customForm.input name="target"  i18nkey="preplanning.outcomes.target" /]  
					[#-- remove link --]      
		      <div class="removeLink">            
            <img src="${baseUrl}/images/global/icon-remove.png" />
            <a id="removeOutcomeIndicator" href="" class="removeOutcomeIndicator">[@s.text name="preplanning.outcomes.removeIndicator" /]</a>
          </div>
      	</div>
      </div>
    </div>
    
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
     
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]