[#ftl]
[#assign title = "Outcomes 2019 - Preplanning" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/midOutcomes.js"] /]
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
  
  [@s.form action="outcomes" cssClass="pure-form"]  
  <article class="halfContent" id="mainInformation">
  	[#include "/WEB-INF/preplanning/ipPreplanningSubMenu.ftl" /]
    <h1 class="contentTitle">
    [@s.text name="preplanning.midOutcomes.title" /]  
    </h1>



    <div id="midOutcomeTemplate" class="midOutcome" style="display:block">
      [#if midOutcomes?has_content]
        [#list midOutcomes as outcome]
          [#-- Mid outcome identifier --]
          <input type="hidden" name="id" value="${outcome.id}" />
          
          [#-- Title --]
          [@customForm.textArea name="midOutcomes[${outcome_index}].description" i18nkey="preplanning.midOutcomes.outcome" required=true /]
      
          <div class="contentElements parentsBlock">
            <div class="itemIndex">[@s.text name="preplanning.midOutcomes.contributes" /] </div>
            [#-- midOutcome's parents --]
            [#if midOutcomes.parents?has_content]
              [#list midOutcomes.parents as parent]
                <div class="contributions">  
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
              [@customForm.select name="midOutcomes.outcomes[].type" i18nkey="outcome" showTitle=false listName="outcomesList" keyFieldName="id"  displayFieldName="name" addButton=true className="contributes" /]
            </div> 
          </div> 

          <div class="contentElements indicatorsBlock">
            <div class="itemIndex">[@s.text name="preplanning.midOutcomes.indicators" /] </div>
            [#-- midOutcome's indicators --]
            [#if midOutcomes.indicators?has_content]
              [#list midOutcomes.indicators as indicator]
                <div class="indicator">
                  [@customForm.textArea showTitle=false name="midOutcomes[${outcome_index}].indicator[${indicator_index}].description" i18nkey="preplanning.midOutcomes.outcome" required=true /]
                  [@customForm.input name="midOutcomes[${outcome_index}].indicator[${indicator_index}].target"  i18nkey="preplanning.midOutcomes.target" required=true /]  
                  [#-- remove link --]      
                  <div class="removeLink">            
                    <img src="${baseUrl}/images/global/icon-remove.png" />
                    <a id="removeIndicator" href="" class="removeIndicator">[@s.text name="preplanning.midOutcomes.removeIndicator" /]</a>
                  </div>      
                </div> 
              [/#list]
            [/#if]
            [#-- Add Indicator --]
            <div class="fullBlock">
              [@customForm.textArea name="newIndicator" i18nkey="Indicator" showTitle=false addButton=true /]
            </div> 
          </div>
        [/#list]
      [#else]

        [#-- Mid outcome identifier --]
        <input type="hidden" name="id" value="-1" />
        
        [#-- Title --]
        [@customForm.textArea name="midOutcomes[0].description" i18nkey="preplanning.midOutcomes.outcome" required=true /]
    
        <div class="contentElements parentsBlock">
          <div class="itemIndex">[@s.text name="preplanning.midOutcomes.contributes" /] </div>
            [#-- midOutcome's parents --]
            <div class="contributions">  
            </div>
            [#-- Add contribute --]
            <div class="fullBlock">
              [@customForm.select name="midOutcomes.outcomes[].type" i18nkey="outcome" showTitle=false listName="outcomesList" keyFieldName="id"  displayFieldName="name" addButton=true className="contributes" /]
            </div> 
          </div> 

          <div class="contentElements indicatorsBlock">
            <div class="itemIndex">[@s.text name="preplanning.midOutcomes.indicators" /] </div>
            [#-- midOutcome's indicators --]
            <div class="indicator">
              [@customForm.textArea showTitle=false name="midOutcomes[0].indicator[0].description" i18nkey="preplanning.midOutcomes.outcome" required=true /]
              [@customForm.input name="midOutcomes[0].indicator[0].target"  i18nkey="preplanning.midOutcomes.target" required=true /]  
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
     
     <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]