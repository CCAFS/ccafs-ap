[#ftl]
[#assign title = "Pre-Planning" /]
[#assign globalLibs = ["jquery", "dataTable", "noty","autoSave"] /]
[#assign customJS = ["${baseUrl}/js/preplanning/projectsListPreplanning.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "projects" /]

[#assign breadCrumb = [
  {"label":"preplanning", "nameSpace":"pre-planning", "action":"intro"}
]/]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png"/>
    <p>//TODO</p>
  </div>
  <article id="preplanningPrograms" class="fullBlock" > 
  [@s.form action="projects"]  
    <br />
    <h1 class="contentTitle">Pre-Planning introduction</h1>
    <div>
      [#-- Flagships --]
      <div id="flagships" class="borderBox">
        <h3 class="projectSubTitle" style="margin-top:0">Flagships</h3>
        [#list flagshipPrograms as program]
          [#if program.id != globalProgramID]
            <a href="[@s.url action='outcomes'] [@s.param name="${programRequest}"]${program.id}[/@s.param] [/@s.url]">
              <div class="program">${program.acronym} 
                <p class="name">${program.name}</p>
              </div>
            </a>
          [/#if]
        [/#list]
      </div>
      [#-- Regions --]
      <div id="regions" class="borderBox">
        <h3 class="projectSubTitle" style="margin-top:0">Regions</h3>
        [#list regionPrograms as program]
          [#if program.id != globalProgramID]
            <a href="[@s.url action='outcomes'] [@s.param name="${programRequest}"]${program.id}[/@s.param] [/@s.url]">
              <div class="program">${program.acronym}
                <p class="name">${program.name}</p>
              </div>
            </a>
          [/#if]
        [/#list]
      </div>
    </div>
  [/@s.form]  
  </article>
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]