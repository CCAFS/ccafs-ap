[#ftl]
[#assign title = "Main information" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/mainInformation.js"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "outcomes" /]
[#assign currentStage = "actions" /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.mainInformation.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl" /]
  
  [@s.form action="actions" cssClass="pure-form"]  
  <article class="halfContent" id="mainInformation">
 		[#include "/WEB-INF/preplanning/ipPreplanningSubMenu.ftl" /]
    <h1 class="contentTitle">
    [@s.text name="preplanning.actions.title" /]  
    </h1>
    
    [#-- Mid Outcome template --]
    <div id="midOutcomeTemplate" class="midOutcome" style="display:block">
      [#-- Objective identifier --]
      <input type="hidden" name="id" value="-1" />
      [#-- Title --]
      [@customForm.textArea name="description" i18nkey="preplanning.actions.action" required=true /]
      
      <div class="contentElements">
      	<div class="itemIndex">[@s.text name="preplanning.actions.contributes" /] </div>
      	[#-- Contribute template --]
      	<div class="indicator" style="display:block">  
      		<p>Decision support systems developed, evaluated and usefulness demonstrated with national policy makers, while building on strong stakeholder engagement and communication processes.</p> 
					[#-- remove link --]      
		      <div class="removeLink">            
		        <img src="${baseUrl}/images/global/icon-remove.png" />
		        <a id="removeContribute" href="" class="removeContribute">[@s.text name="preplanning.actions.removeContribute" /]</a>
		      </div>     	
      	</div> 
      	[#-- Add contribute --]
        <div class="fullBlock">
        	[@customForm.select name="actions.outputs[].type" label="" i18nkey="output" showTitle=false listName="outputsList" keyFieldName="id"  displayFieldName="name" addButton=true className="contributes" /]
        </div> 
      </div> 
    </div> 
    <div id="addActionBlock" class="addLink">
      <img src="${baseUrl}/images/global/icon-add.png" />
      <a href="" class="addAction" >[@s.text name="preplanning.actions.addAction" /]</a>
    </div>
    
    
     
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]