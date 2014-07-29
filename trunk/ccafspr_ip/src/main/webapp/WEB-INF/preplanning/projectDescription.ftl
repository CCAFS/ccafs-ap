[#ftl]
[#assign title = "Project Description" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/mainInformation.js"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "projects" /]
[#assign currentStage = "description" /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.mainInformation.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl" /]
  
  [@s.form action="projectDescription" cssClass="pure-form"]  
  <article class="halfContent" id="mainInformation">
  	[#include "/WEB-INF/preplanning/projectPreplanningSubMenu.ftl" /]
    <h1 class="contentTitle">
    [@s.text name="preplanning.projectDescription.title" /] 
    </h1> 
    
    <fieldset class="fullBlock">  
			[#-- Project Title --]
			[@customForm.textArea name="project.title" i18nkey="preplanning.projectDescription.projectTitle" required=true value="${project.title}" /]
	    <div id="projectDescription" class="">
	      [#-- Project Owner --]
	      <div class="halfPartBlock">
	        [@customForm.input name="project.owner.username" type="text" i18nkey="preplanning.projectDescription.projectOwner" required=true /]
	      </div>
	      [#-- Project Owner Contact Person --]
	      <div class="halfPartBlock">
	        [@customForm.input name="" type="text" i18nkey="preplanning.projectDescription.projectownercontactperson" required=true /]
	      </div>
	      [#-- Start Date --]
		    <div class="halfPartBlock">
		     	[@customForm.input name="" type="text" i18nkey="preplanning.projectDescription.startDate" required=true value="${project.startDate?string('yyyy-MM-dd')}"/]
		    </div> 
		    [#-- End Date --]
		    <div class="halfPartBlock">
	      		[@customForm.input name="projects.endDate" type="text" i18nkey="preplanning.projectDescription.endDate" required=true value="${project.endDate?string('yyyy-MM-dd')}"/]
	    	</div>
	  	</div> 
	  		[#-- Project Summary --]
			[#--${projects}--]
			[@customForm.textArea name="projects.summary" i18nkey="preplanning.projectDescription.projectSummary" required=true value="${project.summary}" /]
    </fieldset><br/>
    <fieldset class="fullBlock">   
    	<legend>[@s.text name="preplanning.projectDescription.projectWorking" /] </legend> 
	    <div id="projectWorking">
	      [#-- Regions --]
	      <div id="projectRegions" class="halfPartBlock">
	        <h6>[@s.text name="preplanning.projectDescription.regions" /]</h6>
	        <div class="checkboxGroup"> 
	          [@s.fielderror cssClass="fieldError" fieldName="regionsSelected"/]          
	          [@s.checkboxlist name="region" list="ipProgramRegions" listKey="id" listValue="name" value="project.regionsIds" cssClass="checkbox" /]
	        </div>
	      </div> 
	      [#-- Flagships --] 
	      <div id="projectFlagships" class="halfPartBlock">
	        <h6>[@s.text name="preplanning.projectDescription.flagships" /]</h6>
	        <div class="checkboxGroup">  
	          [@s.fielderror cssClass="fieldError" fieldName="flagshipsSelected"/]          
	          [@s.checkboxlist name="flagship" list="ipProgramFlagships" listKey="id" listValue="name" value="project.flagshipsIds" cssClass="checkbox" /]
	        </div>
	      </div> 
	      [#-- Gender --] 
	      <div id="projectGender" class="halfPartBlock">
	        <h6>[@s.text name="preplanning.projectDescription.gender" /]</h6>
	        <div class="checkboxGroup">
	          [@s.fielderror cssClass="fieldError" fieldName="gendersSelected"/]          
	          [@s.checkboxlist name="gender" list="ipCrossCuttings" listKey="id" listValue="name" value="project.crossCutttingIds" cssClass="checkbox" /]
	        </div>
	      </div> 
	      
	      <div class="buttons">
		      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
		      [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
		      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
		    </div>
	  	</div> 
    </fieldset>
 
     
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]