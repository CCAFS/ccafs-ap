[#ftl]
[#assign title = "Main information" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/mainInformation.js"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "outcomes" /]
[#assign currentStage = "outputs" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.mainInformation.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl" /]
  
  [@s.form action="outputs" cssClass="pure-form"]  
  <article class="halfContent" id="outputs">
  	[#include "/WEB-INF/preplanning/ipPreplanningSubMenu.ftl" /]
    <h1 class="contentTitle">
    [@s.text name="preplanning.outputs.title" /]  
    </h1>
    
    [#-- Mid Outcome template --]
    <div id="midOutcomeTemplate" class="midOutcome" style="display:block">
      [#-- Objective identifier --]
      <input type="hidden" name="id" value="-1" />
      [#-- Title --]
      [@customForm.textArea name="description" i18nkey="preplanning.outputs.output" required=true /]
      
      <div class="contentElements">
      	<div class="itemIndex">[@s.text name="preplanning.outputs.contributes" /] </div>
      	[#-- Contribute template --]
      	<div class="indicator" style="display:block">  
      		<p>National/sub-national governments, better articulate among themselves and in collaboration with the private sector and civil society, enact equitable food system policies that take into consideration climate smart practices/ strategies</p> 
					[#-- remove link --]      
		      <div class="removeLink">            
		        <img src="${baseUrl}/images/global/icon-remove.png" />
		        <a id="removeContribute" href="" class="removeContribute">[@s.text name="preplanning.outputs.removeContribute" /]</a>
		      </div>     	
      	</div> 
      	[#-- Add contribute --]
        <div class="fullBlock">
        	[@customForm.select name="outputs.outcomes[].type" label="" i18nkey="outcome" showTitle=false listName="outcomesList" keyFieldName="id"  displayFieldName="name" addButton=true className="contributes" /]
        </div> 
      </div> 
      
      <div class="contentElements">
      	<div class="itemIndex">[@s.text name="preplanning.outputs.indicators" /] </div>
      	[#-- Indicator template --]
      	<div class="indicator" style="display:block">  
      		[@customForm.textArea value="indicator.description" showTitle=false name="midOutcomes[outcome_index].indicator[indicator_index].description" i18nkey="preplanning.midOutcomes.outcome" required=true /]
          [@customForm.input value="indicator.target" name="midOutcomes[outcome_index].indicator[indicator_index].target"  i18nkey="preplanning.midOutcomes.target" required=true /]
					[#-- remove link --]      
		      <div class="removeLink">            
		        <img src="${baseUrl}/images/global/icon-remove.png" />
		        <a id="removeIndicator" href="" class="removeIndicator">[@s.text name="preplanning.outputs.removeIndicator" /]</a>
		      </div>     	
      	</div> 
      	[#-- Add Indicator --]
        <div class="fullBlock">
        	[@customForm.textArea name="newIndicator" i18nkey="Indicator" showTitle=false addButton=true /]
        </div> 
      </div>  
    </div>
    
    <div id="addOutputBlock" class="addLink">
      <img src="${baseUrl}/images/global/icon-add.png" />
      <a href="" class="addOutput" >[@s.text name="preplanning.outputs.addOutput" /]</a>
    </div>
    
     
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]