[#ftl]
[#assign title = "Project Deliverables List" /]
[#assign globalLibs = ["jquery", "noty", "dataTable", "autoSave", "chosen"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/projects/projectDeliverablesList.js"] /]
[#assign currentSection = "planning" /]
[#assign currentCycleSection = "projects" /]
[#assign currentStage = "outputs" /]
[#assign currentSubStage = "deliverables" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projectDeliverables", "nameSpace":"planning/projects", "action":"deliverablesList", "param":"projectID=${project.id}"}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
[#import "/WEB-INF/projects/macros/projectDeliverablesTemplate.ftl" as deliverableTemplate/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.deliverables.list.help" /] </p>
  </div>
  [#include "/WEB-INF/projects/projectsSubMenu.ftl" /]
  
  <article class="halfContent" id="mainInformation"> 
    [#include "/WEB-INF/projects/dataSheet.ftl" /]
    <h1 class="contentTitle">[@s.text name="planning.projectDeliverables.title" /]</h1> 
    <div id="projectDeliverables" class="clearfix">
      [#if allYears?has_content]
        [#-- Planned Deliverables --]
        <div class="fullBlock clearfix">
          <h3 class="projectSubTitle">[@s.text name="planning.projectDeliverables.plannedDeliverables" /]</h3>
          [#if project.deliverables?has_content]
            [@deliverableTemplate.deliverablesList deliverables=project.deliverables canEdit=canEdit /]
          [#else]
            [#-- Just show this empty message to those users who are not able to modify this section --]
            <p class="simpleBox center">[@s.text name="planning.deliverables.empty"/]</p> 
          [/#if]   
          [#if canEdit]
          <div class="buttons"> 
            <a class="addButton" href="[@s.url namespace="/planning/projects" action='addNewDeliverable'] [@s.param name="${projectRequestID}"]${projectID}[/@s.param][/@s.url]">
              [@s.text name="planning.projectDeliverables.addNewDeliverable" /]
            </a>
          </div>
          [/#if]
        </div>
      [#else]
        [#-- If the project has not an start date and/or end date defined --]
        <p class="simpleBox center">[@s.text name="planning.projectDeliverable.message.dateUndefined" /]</p>
      [/#if]
    </div>     
  </article>
  
</section>
[@customForm.confirmJustification action="deleteDeliverable" namespace="/planning/projects" nameId="deliverableID" projectID="${projectID}" title="Remove Deliverable" /]
 
[#include "/WEB-INF/global/pages/footer.ftl"]