[#ftl]
[#assign title = "Projects Evaluation" /]
[#assign globalLibs = ["jquery", "dataTable", "noty","autoSave"] /]
[#assign customJS = ["${baseUrl}/js/projects/projectsEvaluation.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = reportingCycle?string('reporting','planning') /]
[#assign currentCycleSection = "projectsEvaluation" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projectsEvaluation", "nameSpace":"${currentSection}", "action":"projectsEvaluation"}
]/]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
[#import "/WEB-INF/global/templates/projectsListTemplate.ftl" as projectList /]
    
<section class="content">
  [#if !reportingCycle]
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" /><p></p>
  </div>
  [/#if]
  <br>  
  <article class="fullBlock" id="mainInformation">
    [#if projects?size>0]
      [#-- Evaluation projects --]
      <h3 class="projectSubTitle">  Evaluation Projects </h3>
      <div class="loadingBlock"></div>
      <div style="display:none">
        [@projectList.evaluationProjects projects=projects canValidate=true canEdit=true namespace="/${currentSection}/projects" /]
      </div>
    [#else]
      <div class="borderBox center">
        [#if canEdit]
          <p>[@s.text name="planning.projects.empty"][@s.param][@s.url namespace="/pre-planning" action='projects'/][/@s.param][/@s.text]</p>
        [#else]
          <p>[@s.text name="planning.projects.empty.PL" /]</p>
        [/#if]
      </div>
    [/#if]    
    <div class="clearfix"></div> 
  </article>
</section>
[@customForm.confirmJustification action="deleteProject" namespace="/${currentSection}" title="Remove Project" /]

[#-- validateProjectSection.do --]
<input type="hidden" id="currentCycle" value="${cycleName}" />
[#include "/WEB-INF/global/pages/footer.ftl"]
