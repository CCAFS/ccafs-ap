[#ftl]
[#assign title = "User Description" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/preplanning/projectDescriptionPreplanning.js"] /]
[#assign currentSection = "user" /]
[#assign currentStage = "description" /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]

<section class="content">
  
  
  
  [@s.form action="description" cssClass="pure-form"]  
  <article class="" id="mainInformation">
    <h1 class="contentTitle">
    [@s.text name="user.description.title" /] 
    </h1> 
    <div id="projectDescription" class="borderBox">
      <fieldset class="fullBlock"> 
      	<div class="halfPartBlock"> 
	        [#-- User First Name --]
        	[@customForm.input name="user.firstName" type="text" i18nkey="user.description.firstName" required=true /]
        </div>
        <div class="halfPartBlock">
        	[#-- User Last Name --]
        	[@customForm.input name="user.lastName" type="text" i18nkey="user.description.lastName" required=true /]
        </div>
        <div class="halfPartBlock">
            [#-- User E-Mail --]
        	[@customForm.input name="user.email" type="text" i18nkey="user.description.email" required=true /]
         </div>
          [#--  Roles --]
          <div class="halfPartBlock">
            [@customForm.select name="user.role" label=""  disabled=false i18nkey="user.description.role" listName="roles" keyFieldName="id"  displayFieldName="name" required=true /]
          </div>
          [#-- Institutions --]
          <div class="fullBlock">
            [@customForm.select name="user.currentInstitution" label=""  disabled=false i18nkey="user.description.institution" listName="institutions" keyFieldName="id"  displayFieldName="name" required=true/]
          </div> 
          
        
      </fieldset>
      
    </div> 
    [#-- Project identifier --]
    <input name="projectID" type="hidden" value="" />
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
     
  </article>
  [/@s.form] 
  [#-- Hidden values used by js --]
  <input id="programID" value="" type="hidden"/>
  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]