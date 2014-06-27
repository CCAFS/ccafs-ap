[#ftl]
[#assign title = "Main information" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/mainInformation.js"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "outcomes" /]
[#assign currentStage = "midOutcomes" /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.mainInformation.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl" /]
  
  [@s.form action="mainInformation" cssClass="pure-form"]  
  <article class="halfContent" id="mainInformation">
  	[#include "/WEB-INF/preplanning/ipPreplanningSubMenu.ftl" /]
    <h1 class="contentTitle">
    [@s.text name="preplanning.midOutcomes.title" /]  
    </h1>


    <div id="outcomesBlock" class="outcome">

      [#if midOutcomes?has_content]
        [#list midOutcomes as outcome]
          [#-- Outcome identifier --]
          <input type="hidden" name="id" value="${outcome.id}" />
  
          [#-- Title --]
          [@customForm.textArea name="midOutcomes[${outcome_index}].description" i18nkey="preplanning.outcomes.outcome" required=true /]

          [#-- Relations --]
          <div class="contentElements">
            <div class="itemIndex">[@s.text name="preplanning.midOutcomes.contributes" /] </div>
            [#if outcome.parents?has_content]
              [#list outcome.parents as parent]
                [#-- Contributions block--]
                <div class="indicator" >
                  <p>${parent.description}</p>
                  [#-- remove link --]
                  <div class="removeLink">
                    <img src="${baseUrl}/images/global/icon-remove.png" />
                    <a id="removeContribute" href="" class="removeContribute">[@s.text name="preplanning.midOutcomes.removeContribute" /]</a>
                  </div>
                </div>
              [/#list]
            [/#if]
            
            [#-- Add contribute --]
            <div class="fullBlock">
              [@customForm.select name="newParent" label="" i18nkey="outcome" showTitle=false listName="outcomes" keyFieldName="id"  displayFieldName="description" addButton=true className="contributes" /]
            </div> 
          </div>

          [#-- Indicators --]
          <div class="indicatorElements">            
            [#if outcome.indicators?has_content]
              [#list outcome.indicators as indicator]
                <div class="itemIndex">[@s.text name="preplanning.outcomes.indicators" /] </div>
                [#-- Indicators --]
                <div class="indicator" style="display:block">  
                  <p>${indicator.description}</p>
                  [@customForm.input name="outcomes[${outcome_index}].indicator[${indicator_index}].target"  i18nkey="preplanning.outcomes.target" required=true /]  

                  [#-- remove link --]      
                  <div class="removeLink">            
                    <img src="${baseUrl}/images/global/icon-remove.png" />
                    <a id="removeOutcomeIndicator" href="" class="removeOutcomeIndicator">[@s.text name="preplanning.outcomes.removeIndicator" /]</a>
                  </div>
                </div> 
        
              [/#list]
            [/#if]
              [#-- Add Indicator --]
              <div class="fullBlock">
                [@customForm.select name="newIndicator" label="" i18nkey="Indicator" showTitle=false listName="indicatorsList" keyFieldName="id"  displayFieldName="name" addButton=true className="indicator" /]
              </div>
          </div>
        [/#list]

      [#else]

          [#-- Outcome identifier --]
          <input type="hidden" name="id" value="-1" />
  
          [#-- Title --]
          [@customForm.textArea name="description" i18nkey="preplanning.outcomes.outcome" required=true /]
          
          <div class="indicatorElements">
            <div class="itemIndex">[@s.text name="preplanning.outcomes.indicators" /] </div>
              [#-- Add Indicator --]
              <div class="fullBlock">
                [@customForm.select name="newIndicator" label="" i18nkey="Indicator" showTitle=false listName="indicatorsList" keyFieldName="id"  displayFieldName="name" addButton=true className="indicator" /]
              </div> 
            </div>
          </div>

      [/#if]
    </div>
    
   	[#-- Mid Outcome template --]
    <div id="midOutcomeTemplate" class="midOutcome" style="display:none">
      [#-- Objective identifier --]
      <input type="hidden" name="id" value="-1" />
      [#-- Title --]
      [@customForm.textArea name="description" i18nkey="preplanning.midOutcomes.outcome" required=true /]
      
      <div class="contentElements">
      	<div class="itemIndex">[@s.text name="preplanning.midOutcomes.contributes" /] </div>
      	[#-- Contribute template --]
      	<div class="indicator" style="display:block">  
      		<p>Example text :By 2025, national/subnational governments are making EQUITABLE INSTITUTIONAL INVESTMENTS IN CLIMATE SMART FOOD SYSTEM in 25 countries that have increased by 50% compared with 2014.</p> 
					[#-- remove link --]      
		      <div class="removeLink">            
		        <img src="${baseUrl}/images/global/icon-remove.png" />
		        <a id="removeContribute" href="" class="removeContribute">[@s.text name="preplanning.midOutcomes.removeContribute" /]</a>
		      </div>     	
      	</div> 
      	[#-- Add contribute --]
        <div class="fullBlock">
        	[@customForm.select name="midOutcomes.outcomes[].type" label="" i18nkey="outcome" showTitle=false listName="outcomesList" keyFieldName="id"  displayFieldName="name" addButton=true className="contributes" /]
        </div> 
      </div> 
      
      <div class="contentElements">
      	<div class="itemIndex">[@s.text name="preplanning.midOutcomes.indicators" /] </div>
      	[#-- Indicator template --]
      	<div class="indicator" style="display:block">  
      		[@customForm.textArea value="indicator.description" showTitle=false name="midOutcomes[outcome_index].indicator[indicator_index].description" i18nkey="preplanning.midOutcomes.outcome" required=true /]
          [@customForm.input value="indicator.target" name="midOutcomes[outcome_index].indicator[indicator_index].target"  i18nkey="preplanning.midOutcomes.target" required=true /]  
					[#-- remove link --]      
		      <div class="removeLink">            
		        <img src="${baseUrl}/images/global/icon-remove.png" />
		        <a id="removeIndicator" href="" class="removeIndicator">[@s.text name="preplanning.midOutcomes.removeIndicator" /]</a>
		      </div>     	
      	</div> 
      	[#-- Add Indicator --]
        <div class="fullBlock">
        	[@customForm.textArea name="newIndicator" i18nkey="Indicator" showTitle=false addButton=true /]
        </div> 
      </div>  
    </div>
    
    <div id="addMidOutcomeBlock" class="addLink">
      <img src="${baseUrl}/images/global/icon-add.png" />
      <a href="" class="addMidOutcome" >[@s.text name="preplanning.midOutcomes.addOutcome" /]</a>
    </div>
     
     
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]