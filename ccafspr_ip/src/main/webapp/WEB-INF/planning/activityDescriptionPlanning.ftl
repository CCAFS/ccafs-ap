[#ftl]
[#assign title = "Activity Description" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "activity" /]
[#assign currentStage = "activityDescription" /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.mainInformation.help" /] </p>
  </div>
  [#include "/WEB-INF/planning/projectPlanningSubMenu.ftl" /]
  
  [@s.form action="activityDescription" cssClass="pure-form"]  
  <article class="halfContent" id="mainInformation">
    <h1 class="contentTitle">
    [@s.text name="planning.activityDescription.title" /] 
    </h1> 
    <div id="activityDescription" class="borderBox">
         <fieldset class="fullBlock">  
  			[#-- Activity Leader --]
  			[@customForm.select name="activity.leader.institution.id" label=""  disabled=false i18nkey="planning.activityDescription.leader" listName="allPartners" keyFieldName="id"  displayFieldName="name" /]
  	    <div id="projectDescription" class="">
  	      [#-- Contact Name --]
  	      <div class="halfPartBlock">  
  	      	[@customForm.input name="activity.leader.name" type="text" i18nkey="planning.activityDescription.contactName" required=true  /]
          </div>    	
  	      [#--  Contact Email --]
          <div class="halfPartBlock">          
            [@customForm.input name="activity.leader.email" type="text"  i18nkey="planning.activityDescription.contactEmail" required=true  /]
          </div>
          [#-- Activity Title --]
  			[@customForm.textArea name="activity.title" i18nkey="planning.activityDescription.title" required=true /]
  	      [#-- Activity Description --]
  			[@customForm.textArea name="activity.description" i18nkey="planning.activityDescription.description" required=true  /]
  	      [#-- Start Date --]
  		    <div class="halfPartBlock">
  		     	[@customForm.input name="activity.start" type="text" i18nkey="planning.activityDescription.startDate" required=true /]
  		    </div> 
  		    [#-- End Date --]
  		    <div class="halfPartBlock">
  	      		[@customForm.input name="activity.end" type="text" i18nkey="planning.activityDescription.endDate" required=true /]
  	    	</div>
  	  	</div> 
  	  		
      </fieldset><br/>
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