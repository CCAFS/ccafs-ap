[#ftl]
[#assign title = "Activity Deliverables" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "activity" /]
[#assign currentStage = "activityDeliverables" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.deliverables.help" /] </p>
  </div>

  [#include "/WEB-INF/planning/activityPlanningSubMenu.ftl" /]

  
  [@s.form action="activityDeliverables" cssClass="pure-form"]  
  <article class="halfContent" id="activityDeliverables">
    <h1 class="contentTitle">
    [@s.text name="planning.deliverables" /] 
    </h1> 
    <div class="borderBox">
    	<div class="removeLink">
			<img src="${baseUrl}/images/global/icon-remove.png" />
			<a id="removeDeliverable-" href="" class="removeActivityPartner">[@s.text name="planning.deliverables.removeDeliverable" /]</a>
		</div>
    	<b>[@s.text name="planning.deliverables.expectedDeliverable" /]</b><br/><br/>
    	[#-- Title --] 
    	[@customForm.input name="" type="text" i18nkey="planning.deliverables.title" required=true /]
    	<div class="halfPartBlock">
    		[#-- Type --]
    		[@customForm.select name="deliverable" label=""  disabled=false i18nkey="planning.deliverables.type" listName="" keyFieldName="id"  displayFieldName="name" /]
    	</div>
    	<div class="halfPartBlock">
    		[#-- Year  --]
    		[@customForm.select name="deliverable" label=""  disabled=false i18nkey="planning.deliverables.year" listName="" keyFieldName="id"  displayFieldName="name" /]
    	</div>
    	
    	<div class="borderBox">
    		[#-- Next User --]
    		[@customForm.input name="" type="text" i18nkey="planning.deliverables.nextUser" required=true /]<br/>
    		[#-- Expected Changes --]
    		[@customForm.textArea name="deliverable.expectedChanges" i18nkey="planning.deliverables.expectedChanges" required=true /]<br/>
    		[#-- Strategies --]
    		[@customForm.textArea name="deliverable.strategies" i18nkey="planning.deliverables.strategies" required=true /]<br/>
    	</div>
    </div>
    <div id="addDeliverable" class="addLink">
	  <img src="${baseUrl}/images/global/icon-add.png" />
	  <a href="" class="addDeliverable" >[@s.text name="planning.deliverables.addDeliverable" /]</a>
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