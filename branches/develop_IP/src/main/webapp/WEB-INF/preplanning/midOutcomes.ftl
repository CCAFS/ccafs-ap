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
    
    
   	[#-- Mid Outcome template --]
    <div id="midOutcomeTemplate" class="midOutcome" style="display:block">
      [#-- Objective identifier --]
      <input type="hidden" name="id" value="-1" />
      [#-- Title --]
      [@customForm.textArea name="description" i18nkey="preplanning.midOutcomes.outcome" required=true /]
      
      <div class="contentElements">
      	<div class="itemIndex">[@s.text name="preplanning.midOutcomes.contributes" /] </div>
      	[#-- Contribute template --]
      	<div class="indicator" style="display:block">  
      		<p>By 2025, national/subnational governments are making EQUITABLE INSTITUTIONAL INVESTMENTS IN CLIMATE SMART FOOD SYSTEM in 25 countries that have increased by 50% compared with 2014.</p> 
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
      		<p># of equitable national/sub-national food system policies that take into consideration climate smart practices and strategies.</p>
					[@customForm.input name="target"  i18nkey="preplanning.midOutcomes.target" required=true /]  
					[#-- remove link --]      
		      <div class="removeLink">            
		        <img src="${baseUrl}/images/global/icon-remove.png" />
		        <a id="removeIndicator" href="" class="removeIndicator">[@s.text name="preplanning.midOutcomes.removeIndicator" /]</a>
		      </div>     	
      	</div> 
      	[#-- Add Indicator --]
        <div class="fullBlock">
        	[@customForm.select name="midOutcomes.indicators[].type" label="" i18nkey="indicator" showTitle=false listName="indicatorsList" keyFieldName="id"  displayFieldName="name" addButton=true className="indicator" /]
        </div> 
      </div>  
    </div>
    
    <div id="addMidOutcomeBlock" class="addLink">
      <img src="${baseUrl}/images/global/icon-add.png" />
      <a href="" class="addMidOutcome" >[@s.text name="preplanning.midOutcome.addOutcome" /]</a>
    </div>
     
     
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]