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
			[@customForm.textArea name="projectTitle" i18nkey="preplanning.projectDescription.projectTitle" required=true /]
			[#-- Project Summary --]
			[@customForm.textArea name="projectSummary" i18nkey="preplanning.projectDescription.projectSummary" required=true /]
	    <div id="projectDescription" class="">
	      [#-- Project Owner --]
	      <div class="halfPartBlock">
	        [@customForm.input name="" type="text" i18nkey="preplanning.projectDescription.projectOwner" required=true /]
	      </div>
	      [#-- Project Budget --]
	      <div class="halfPartBlock">
	        [@customForm.input name="" type="text" i18nkey="preplanning.projectDescription.budget" required=true /]
	      </div>
	      [#-- Start Date --]
		    <div class="halfPartBlock">
		      [@customForm.input name="" type="text" i18nkey="preplanning.projectDescription.startDate" required=true /]
		    </div> 
		    [#-- End Date --]
		    <div class="halfPartBlock">
	      [@customForm.input name="" type="text" i18nkey="preplanning.projectDescription.endDate" required=true /]
	    	</div>
	  	</div> 
    </fieldset>
    <fieldset class="fullBlock">  
    	<legend>[@s.text name="preplanning.projectDescription.projectLead" /] </legend> 
	    <div id="projectLead" class="">
	      [#-- Organization --]
	      <div class="fullBlock">
	      [@customForm.select name="" label="" i18nkey="preplanning.projectDescription.organization" showTitle=true listName="organizationList" keyFieldName="id"  displayFieldName="name" className="organization" required=true /]
	         
	      </div>
	      [#-- Contact name --]
	      <div class="halfPartBlock">
	        [@customForm.input name="" type="text" i18nkey="preplanning.projectDescription.contactName" required=true /]
	      </div>
	      [#-- Email --]
		    <div class="halfPartBlock">
		      [@customForm.input name="" type="text" i18nkey="preplanning.projectDescription.email" required=true /]
		    </div>  
	  	</div> 
    </fieldset>
    <fieldset class="fullBlock">  
    	<legend>[@s.text name="preplanning.projectDescription.projectWorking" /] </legend> 
	    <div id="projectWorking">
	      [#-- Regions --] 
	      <div id="projectRegions" class="halfPartBlock">
	        <h6>[@s.text name="preplanning.projectDescription.regions" /]</h6>
	        <div class="checkboxGroup">
	          [@s.fielderror cssClass="fieldError" fieldName="regionsSelected"/]          
	          [@s.checkboxlist name="region" list="regions" listKey="id" listValue="name" value="activeRegions" cssClass="checkbox" /]
	        </div>
	      </div> 
	      [#-- Flagships --] 
	      <div id="projectFlagships" class="halfPartBlock">
	        <h6>[@s.text name="preplanning.projectDescription.flagships" /]</h6>
	        <div class="checkboxGroup">
	          [@s.fielderror cssClass="fieldError" fieldName="flagshipsSelected"/]          
	          [@s.checkboxlist name="flagship" list="flagships" listKey="id" listValue="name" value="activeFlagships" cssClass="checkbox" /]
	        </div>
	      </div> 
	      [#-- Gender --] 
	      <div id="projectGender" class="halfPartBlock">
	        <h6>[@s.text name="preplanning.projectDescription.gender" /]</h6>
	        <div class="checkboxGroup">
	          [@s.fielderror cssClass="fieldError" fieldName="gendersSelected"/]          
	          [@s.checkboxlist name="gender" list="genders" listKey="id" listValue="name" value="activeGender" cssClass="checkbox" /]
	        </div>
	      </div> 
	      
	  	</div> 
    </fieldset>
 
     
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]