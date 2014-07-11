[#ftl]
[#assign title = "Main information" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/preplanning/outputsPreplanning.js"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "impactPathways" /]
[#assign currentStage = "outputs" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/indicatorTemplate.ftl" as indicatorTemplate/]
[#import "/WEB-INF/preplanning/contributeTemplate.ftl" as contributeTemplate/]
    
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
    
    [#-- Output template --]
    <div id="outputTemplate" class="output" style="display:block">
      [#-- Objective identifier --]
      <input type="hidden" name="id" value="-1" />
      [#-- Remove output --]      
      <div id="removeOutputBlock" class="removeLink">            
        <img src="${baseUrl}/images/global/icon-remove.png" />
        <a id="removeContribute" href="" class="removeContribute">[@s.text name="preplanning.outputs.removeOutput" /]</a>
      </div> 
      [#-- Title --]
      [@customForm.textArea name="outputs[0].description" i18nkey="preplanning.outputs.output" required=true /]
      <div class="contentElements">
      	<div class="itemIndex">[@s.text name="preplanning.outputs.contributes" /] </div>
      	[#-- Contribute template --]
      	[@contributeTemplate.outputs/] 
      	[#-- Add contribute --]
        <div class="fullBlock">
        	[@customForm.select name="outputs.outcomes[].type" label="" i18nkey="outcome" showTitle=false listName="outcomesList" keyFieldName="id"  displayFieldName="name" addButton=true className="contributes" /]
        </div> 
      </div>  
      <div class="contentElements outputIndicatorsBlock">
      	<div class="itemIndex">[@s.text name="preplanning.outputs.indicators" /] </div>
      	[#-- Indicator template --]
      	[@indicatorTemplate.outputs template=true /] 
      	[#-- Add Indicator --]
        <div class="fullBlock">
        	[@customForm.button i18nkey="preplanning.outputs.addIndicator" class="addButton" /]
        </div> 
      </div>  
    </div>
    [#-- End Output template --]
    
    <div id="addOutputBlock" class="addLink">
      <img src="${baseUrl}/images/global/icon-add.png" />
      <a href="" class="addOutput" >[@s.text name="preplanning.outputs.addOutput" /]</a>
    </div>
    
     
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]