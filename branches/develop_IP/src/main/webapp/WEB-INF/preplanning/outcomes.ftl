[#ftl]
[#assign title = "Main information" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/mainInformation.js"] /]
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
    
    
    [#-- Outcome 2025 template --]
    <div id="outcomeTemplate" class="outcome" style="display:block">
      [#-- Objective identifier --]
      <input type="hidden" name="id" value="-1" />
           
      [#-- Title --]
      [@customForm.textArea name="description" i18nkey="preplanning.outcomes.outcome" required=true /]
      
      <div class="contentElements">
      	<div class="itemIndex">[@s.text name="preplanning.outcomes.indicators" /] </div>
      	[#-- Indicator template --]
      	<div class="indicator" style="display:block">  
      		<p># of regional and global organisations in which investment in equitable food system institutions that take into consideration climate smart practices/strategies has increased by 50% compared with 2014</p>
					[@customForm.input name="target"  i18nkey="preplanning.outcomes.target" required=true /]  
					[#-- remove link --]      
		      <div class="removeLink">            
		        <img src="${baseUrl}/images/global/icon-remove.png" />
		        <a id="removeObjective" href="" class="removeObjective">[@s.text name="preplanning.outcomes.removeIndicator" /]</a>
		      </div>     	
      	</div> 
      	
      	[#-- Add Indicator --]
        <div class="fullBlock">
        	[@customForm.select name="outcome.indicators[].type" label="" i18nkey="Indicator" showTitle=false listName="indicatorsList" keyFieldName="id"  displayFieldName="name" addButton=true className="indicator" /]
        </div> 
      </div>  
    </div>
     
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]