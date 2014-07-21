[#ftl]
[#assign title = "Project Partners" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "projects" /]
[#assign currentStage = "partners" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/preplanning/macros/projectPartnersTemplate.ftl" as partnersTemplate /]

   
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.mainInformation.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl" /]
  
  [@s.form action="partners" cssClass="pure-form"]  
  <article class="halfContent" id="mainInformation">
  	[#include "/WEB-INF/preplanning/projectPreplanningSubMenu.ftl" /]
  	<h1 class="contentTitle">
      [@s.text name="preplanning.projectPartners.leader.title" /]  
    </h1>
    <hr>
  	[#-- Displaying partner leader from partnersTemplate.ftl --]
    [@partnersTemplate.projectLeader leader=project.leader canEdit=true /]
  	
    <h1 class="contentTitle">
		  [@s.text name="preplanning.projectPartners.partners.title" /]  
    </h1>
    <hr>
    [#-- Listing partners from partnersTemplate.ftl --]
    [@partnersTemplate.partnerSection projectPartners=project.projectPartners partnerTypes=partnerTypes countries=countries canEdit=true canRemove=true /]
    <div id="addProjectPartner" class="addLink">
      <img src="${baseUrl}/images/global/icon-add.png" />
      <a href="" class="addProjectPartner" >[@s.text name="preplanning.projectPartners.addProjectPartner" /]</a>
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