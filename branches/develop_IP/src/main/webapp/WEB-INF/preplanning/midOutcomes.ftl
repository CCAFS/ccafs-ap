[#ftl]
[#assign title = "Outcomes 2019 - Preplanning" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/preplanning/midOutcomesPreplanning.js"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "impactPathways" /]
[#assign currentStage = "midOutcomes" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/preplanning/indicatorTemplate.ftl" as indicatorTemplate/]
[#import "/WEB-INF/preplanning/contributeTemplate.ftl" as contributeTemplate/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.mainInformation.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl" /]
  
  [@s.form action="midOutcomes" cssClass="pure-form"]  
  <article class="halfContent" id="midOutcomes">
  	[#include "/WEB-INF/preplanning/ipPreplanningSubMenu.ftl" /]
    <h1 class="contentTitle">
    [@s.text name="preplanning.midOutcomes.title" /]  
    </h1>

    <div id="midOutcomesBlock" class="midOutcome">
      [#if midOutcomes?has_content]
        [#list midOutcomes as midOutcome]
          [#-- Mid outcome identifier --]
          <input type="hidden" name="id" value="${midOutcome.id}" />
          
          [#-- Title --]
          [@customForm.textArea name="midOutcomes[${midOutcome_index}].description" i18nkey="preplanning.midOutcomes.outcome" required=true /]
      
          <div class="contentElements parentsBlock">
            <div class="itemIndex">[@s.text name="preplanning.midOutcomes.contributes" /] </div>
            [#-- midOutcome's parents --]
            [#if midOutcomes.parents?has_content]
              [#list midOutcomes.parents as parent]
                <div class="contributions">  
                  <input type="hidden" name="midOutcomes[${midOutcome_index}].parents[${parent_index}].id" value="${parent.id}" />
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
              [@customForm.select name="contributionId" value="" showTitle=false listName="outcomesList" keyFieldName="id"  displayFieldName="description" addButton=true className="contributes" /]
            </div> 
          </div> 

          <div class="contentElements indicatorsBlock">
            <div class="itemIndex">[@s.text name="preplanning.midOutcomes.indicators" /] </div>
            [#-- midOutcome's indicators --]
            [#if midOutcome.indicators?has_content]
              [#list midOutcome.indicators as indicator]
                <div class="indicator">
                  <input type="hidden" name="midOutcomes[${midOutcome_index}].indicators[${indicator_index}].id" value="${indicator.id}" /> 
                  [@customForm.textArea showTitle=false name="midOutcomes[${midOutcome_index}].indicators[${indicator_index}].description" i18nkey="preplanning.midOutcomes.outcome" required=true /]
                  [@customForm.input name="midOutcomes[${midOutcome_index}].indicators[${indicator_index}].target"  i18nkey="preplanning.midOutcomes.target" required=true /]  
                  [#-- remove link --]      
                  <div class="removeLink">            
                    <img src="${baseUrl}/images/global/icon-remove.png" />
                    <a id="removeIndicator" href="" class="removeIndicator">[@s.text name="preplanning.midOutcomes.removeIndicator" /]</a>
                  </div>      
                </div> 
              [/#list]
            [#else]
              <div class="indicator">
                <input type="hidden" name="midOutcomes[${midOutcome_index}].indicators[0].id" value="-1" /> 
                [@customForm.textArea showTitle=false name="midOutcomes[${midOutcome_index}].indicators[0].description" i18nkey="preplanning.midOutcomes.outcome" required=true /]
                [@customForm.input name="midOutcomes[${midOutcome_index}].indicators[0].target"  i18nkey="preplanning.midOutcomes.target" required=true /]  
                [#-- remove link --]      
                <div class="removeLink">            
                  <img src="${baseUrl}/images/global/icon-remove.png" />
                  <a id="removeIndicator" href="" class="removeIndicator">[@s.text name="preplanning.midOutcomes.removeIndicator" /]</a>
                </div>      
              </div>
            [/#if]
            [#-- Add Indicator --]
            <div class="fullBlock">
              [@customForm.button i18nkey="preplanning.midOutcomes.addIndicator" class="addButton" /]
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
              [@customForm.select name="contribution" showTitle=false listName="outcomesList" keyFieldName="id"  displayFieldName="description" addButton=true className="contributes" /]
            </div> 
          </div> 

          <div class="contentElements indicatorsBlock">
            <div class="itemIndex">[@s.text name="preplanning.midOutcomes.indicators" /] </div>
            [#-- midOutcome's indicators --]
            <div class="indicator">
              <input type="hidden" name="midOutcomes[0].indicators[0].id" value="-1" /> 
              [@customForm.textArea showTitle=false name="midOutcomes[0].indicators[0].description" i18nkey="preplanning.midOutcomes.outcome" required=true /]
              [@customForm.input name="midOutcomes[0].indicators[0].target"  i18nkey="preplanning.midOutcomes.target" required=true /]  
              [#-- remove link --]      
              <div class="removeLink">            
                <img src="${baseUrl}/images/global/icon-remove.png" />
                <a id="removeIndicator" href="" class="removeIndicator">[@s.text name="preplanning.midOutcomes.removeIndicator" /]</a>
              </div>      
            </div> 
            [#-- Add Indicator --]
            <div class="fullBlock">
              [@customForm.button i18nkey="preplanning.midOutcomes.addIndicator" class="addButton" /]
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
      	<div class="contributions">  
          <input type="hidden" name="id" value="" />
          <p></p> 
          [#-- remove link --]      
          <div class="removeLink">            
            <img src="${baseUrl}/images/global/icon-remove.png" />
            <a id="removeContribute" href="" class="removeContribute">[@s.text name="preplanning.midOutcomes.removeContribute" /]</a>
          </div>
        </div>
      	[#-- Add contribute --]
        <div class="fullBlock">
        	[@customForm.select name="contributions" showTitle=false listName="outcomesList" keyFieldName="id"  displayFieldName="description" addButton=true className="contributes" /]
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
        	[@customForm.button i18nkey="preplanning.midOutcomes.addIndicator" class="addButton" /]
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