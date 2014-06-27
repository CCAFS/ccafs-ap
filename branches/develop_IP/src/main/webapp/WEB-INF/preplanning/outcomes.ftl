[#ftl]
[#assign title = "Outcomes 2025" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/preplanning/outcomes.js"] /]
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
          ${outcome}
          [#-- Outcome identifier --]
          <input type="hidden" name="id" value="${outcome.id}" />
  
          [#-- Title --]
          [@customForm.textArea name="outcomes[${outcome_index}].description" i18nkey="preplanning.outcomes.outcome" required=true /]

          <div class="contentElements"> 
          <div class="itemIndex">[@s.text name="preplanning.outcomes.indicators" /] </div>
            [#if outcome.indicators?has_content]
              [#list outcome.indicators as indicator] 
                [#-- Indicators --]
                <div class="indicator" style="display:block">  
                  [@customForm.textArea value="${indicator.description}" showTitle=false name="outcomes[${outcome_index}].indicator[${indicator_index}].description" i18nkey="preplanning.outcomes.outcome" required=true /]
                  [@customForm.input value="${indicator.target}" name="outcomes[${outcome_index}].indicator[${indicator_index}].target"  i18nkey="preplanning.outcomes.target" /]  
                  [#-- remove link --]      
                  <div class="removeLink">            
                    <img src="${baseUrl}/images/global/icon-remove.png" />
                    <a id="removeOutcomeIndicator" href="" class="removeOutcomeIndicator">[@s.text name="preplanning.outcomes.removeIndicator" /]</a>
                  </div>
                </div>  
              [/#list]
            [/#if]
              [#-- Add Indicator --]
              <div class="fullBlock clearfix">
                [@customForm.textArea name="newIndicator" i18nkey="Indicator" showTitle=false addButton=true /]
              </div> 
          </div>
        [/#list]

      [#else]

          [#-- Outcome identifier --]
          <input type="hidden" name="id" value="-1" /> 
          [#-- Title --]
          [@customForm.textArea name="description" i18nkey="preplanning.outcomes.outcome" required=true /] 
          <div class="contentElements">
            <div class="itemIndex">[@s.text name="preplanning.outcomes.indicators" /] </div>
              [#-- Add Indicator --]
              <div class="fullBlock clearfix">
                [@customForm.select name="newIndicator" label="" i18nkey="Indicator" showTitle=false listName="indicatorsList" keyFieldName="id"  displayFieldName="name" addButton=true className="indicator" /]
              </div> 
            </div>
          </div>

      [/#if]
    </div>
    
    [#-- Outcome 2025 template --]
    <div id="outcomeTemplate" class="outcome" style="display:block">
      [#-- Outcome identifier --]
      <input type="hidden" name="id" value="-1" /> 
      [#-- Title --]
      [@customForm.textArea name="description" i18nkey="preplanning.outcomes.outcome" required=true /] 
      <div class="contentElements">
      	<div class="itemIndex">[@s.text name="preplanning.outcomes.indicators" /] </div>
      	[#-- Indicator template --]
      	<div class="indicator indicatorTemplate">  
      		[@customForm.textArea value="indicator.description" showTitle=false name="outcomes[outcome_index}].indicator[indicator_index}].description" i18nkey="preplanning.outcomes.outcome" required=true /]
          [@customForm.input value="indicator.target" name="target"  i18nkey="preplanning.outcomes.target" /]  
					[#-- remove link --]      
		      <div class="removeLink">            
		        <img src="${baseUrl}/images/global/icon-remove.png" />
		        <a id="removeIndicator" href="" class="removeIndicator">[@s.text name="preplanning.outcomes.removeIndicator" /]</a>
		      </div>     	
      	</div>  
      	[#-- Add Indicator --]
        <div class="fullBlock clearfix">
        	[@customForm.select name="newIndicator" label="" i18nkey="Indicator" showTitle=false listName="indicatorsList" keyFieldName="id"  displayFieldName="name" addButton=true className="indicator" /]
        </div> 
      </div>  
    </div>
     
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]