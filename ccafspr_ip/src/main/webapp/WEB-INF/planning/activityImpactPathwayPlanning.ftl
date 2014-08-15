[#ftl]
[#assign title = "Activity Impact Pathway" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "activity" /]
[#assign currentStage = "activityImpactPathway" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.activityImpactPathways.help" /] </p>
  </div>

  [#include "/WEB-INF/planning/activityPlanningSubMenu.ftl" /]

  
  [@s.form action="activityImpactPathways" cssClass="pure-form"]  
  <article class="halfContent" id="activityImpactPathway">
    <h1 class="contentTitle">
    [@s.text name="planning.activityImpactPathways.title" /] 
    </h1> 
    <div class="halfPartBlock">
    	<b>[@s.text name="planning.activityImpactPathways.contributingTo" /]</b> TODO Get Program
    	<div class="borderBox">
    		[@customForm.select name="activity" label=""  disabled=false i18nkey="planning.activityImpactPathways.outcome" listName="" keyFieldName="id"  displayFieldName="name" /]
    		<br/>
    		[@customForm.select name="activity" label=""  disabled=false i18nkey="planning.activityImpactPathways.mog" listName="" keyFieldName="id"  displayFieldName="name" /]
    	</div>
    	
		<div id="addActivityIPMog" class="addLink">
		  <img src="${baseUrl}/images/global/icon-add.png" />
		  <a href="" class="addActivityPartner" >[@s.text name="planning.activityImpactPathways.addMog" /]</a>
		</div>
    	
    </div>
    <div class="halfPartBlock">
    	<b>[@s.text name="planning.activityImpactPathways.higherImpact" /]</b>
    	<br/>
    	<br/>
    	<br/>
    	<br/>
    </div>
    <br/>
    <br/>
    <div class="fullBlock">
    	<b>[@s.text name="planning.activityImpactPathways.targets" /]</b>
    	<div class="borderBox">
			<div class="removeLink">
				<img src="${baseUrl}/images/global/icon-remove.png" />
				<a id="removeActivityPartner-" href="" class="removeActivityPartner">[@s.text name="planning.activityImpactPathways.removeTarget" /]</a>
			</div>
			<div class="checkboxGroup">
    			TODO bring Indicator[@customForm.checkbox  name=""  value="" /]
    		</div><br/>
    		[@customForm.input name="" type="text" i18nkey="planning.activityImpactPathways.targetValue" required=true /]
    	
    		[@customForm.textArea name="" i18nkey="planning.activityImpactPathways.targetNarrative" required=true /]
    	</div>
    	<div id="addActivityIPTarget" class="addLink">
		  <img src="${baseUrl}/images/global/icon-add.png" />
		  <a href="" class="addActivityIPTarget" >[@s.text name="planning.activityImpactPathways.addTarget" /]</a>
		</div>
    </div>
    
    
     
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]