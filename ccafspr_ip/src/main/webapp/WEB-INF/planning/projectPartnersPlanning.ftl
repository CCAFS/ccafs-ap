[#ftl]
[#assign title = "Project Partners" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPrePlanningSection = "projects" /]
[#assign currentStage = "partners" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/projectPartnersTemplate.ftl" as partnersTemplate /]

[#if currentUser.isAdmin() || ( project.owner.employeeId == currentUser.employeeId)]
  [#assign _canEdit = true /]
[#else]
  [#assign _canEdit = false /]
[/#if]

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.mainInformation.help" /] </p>  
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]

  [@s.form action="partners" cssClass="pure-form"]  
  <article class="halfContent" id="mainInformation">  
  	<h1 class="contentTitle">
      [@s.text name="preplanning.projectPartners.leader.title" /]  
    </h1>
  	[#-- Displaying partner leader from partnersTemplate.ftl --]
    [@partnersTemplate.projectLeader leader=project.leader canEdit=true /] 

    <h1 class="contentTitle">
		  [@s.text name="preplanning.projectPartners.partners.title" /]  
    </h1> 
    [#-- Listing partners from partnersTemplate.ftl --]
    [@partnersTemplate.partnerSection projectPartners=project.projectPartners partnerTypes=partnerTypes countries=countries responsabilities=true canEdit=_canEdit canRemove=true /]

  	<div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
  </article>
  [/@s.form] 
  [#-- Single partner TEMPLATE from partnersTemplate.ftl --]
  [@partnersTemplate.partnerTemplate /]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]