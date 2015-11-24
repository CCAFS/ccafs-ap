[#ftl]
[#assign title = "Project Highlights List" /]
[#assign globalLibs = ["jquery", "noty", "dataTable", "autoSave", "chosen"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/projects/projectHighlights.js"] /]
[#assign currentSection = cycleName?lower_case /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "outputs" /]
[#assign currentSubStage = "highlights" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projects", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projectOutputs", "nameSpace":"${currentSection}/projects", "action":"outputs", "param":"projectID=${project.id}"},
  {"label":"projectHighlights", "nameSpace":"${currentSection}/projects", "action":"highlights", "param":"projectID=${project.id}"}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
[#-- TODO: Make a new template --]
[#import "/WEB-INF/projects/macros/projectHighlightsTemplate.ftl" as highlightTemplate/]
    
<section class="content">
  <div class="helpMessage">
    [#-- TODO: Change help text --]
    <img src="${baseUrl}/images/global/icon-help.png" /><p>[@s.text name="reporting.projectHighlights.help" /] </p>
  </div>
  [#include "/WEB-INF/projects/projectsSubMenu.ftl" /]
  
  <article class="halfContent" id="mainInformation"> 
    [#include "/WEB-INF/projects/dataSheet.ftl" /]
    <h1 class="contentTitle">[@s.text name="reporting.projectHighlights.title" /]</h1> 
    <div id="projectDeliverables" class="clearfix">
      [#if allYears?has_content]
        [#-- Project Highlights --]
        <div class="fullBlock clearfix">
          <h3 class="projectSubTitle">[@s.text name="reporting.projectHighlights.highlights" /]</h3>
          [#if project.deliverables?has_content]
            [@highlightTemplate.highlightsList highlights=project.deliverables canEdit=canEdit /]
          [#else]
            [#-- Just show this empty message to those users who are not able to modify this section --]
            <p class="simpleBox center">[@s.text name="reporting.projectHighlights.empty"/]</p> 
          [/#if]   
          [#if canEdit]
          <div class="buttons"> 
            <a class="addButton" href="[@s.url namespace="/reporting/projects" action='addNewhighlight'] [@s.param name="${projectRequestID}"]${projectID}[/@s.param][/@s.url]">
              [@s.text name="reporting.projectHighlights.addNewhighlight" /]
            </a>
          </div>
          [/#if]
        </div>
      [#else]
        [#-- If the project has not an start date and/or end date defined --]
        <p class="simpleBox center">[@s.text name="reporting.projectHighlights.message.dateUndefined" /]</p>
      [/#if]
    </div>     
  </article>
  
</section>
[#-- TODO: Change function to delete --]
[@customForm.confirmJustification action="deleteHighlights" namespace="/reporting/projects" nameId="highlightID" projectID="${projectID}" title="Remove project highlights" /]
 
[#include "/WEB-INF/global/pages/footer.ftl"]