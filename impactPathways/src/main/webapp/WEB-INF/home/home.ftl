[#ftl]
[#assign title = "Dashboard - CCAFS P&R" /]
[#assign globalLibs = ["jquery", "noty", "jreject", "dataTable", "slidr", "cytoscape", "qtip","cytoscapePanzoom"] /]
[#assign customJS = ["${baseUrl}/js/home/login.js","${baseUrl}/js/global/ipGraph.js","${baseUrl}/js/home/dashboard.js", "${baseUrl}/js/projects/projectsList.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = "home" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
[#import "/WEB-INF/global/templates/homeProjectsListTemplate.ftl" as projectList /]

<article id="dashboard">

  <div class="content">
    <h1>[@s.text name="home.dashboard.title" /]</h1>
    <div class="homeTitle"><b>What do you want to do ?</b></div>
    <div id="decisionTree" class="borderBox">
      <div id="newProject" class="option disabled" title="This link is disabled"><p>Enter a new project</p></div>
      <div id="updatePlanning" class="option disabled" title="This link is disabled"><p>Update planning of an ongoing project</p></div>
      <a href="[@s.url namespace="/planning" action='projectsList'/]"><div id="reportProject" class="option "><p>Report on an ongoing project</p></div></a>
      <div class="clearfix"></div>
      <div class="addProjectButtons clearfix" style="display:none">
        <p class="title">What type of project do you want to enter?</p>
        [#if securityContext.canAddCoreProject()]
          <a href="[@s.url namespace="/planning" action='addNewCoreProject'/]"><div class="addProject"><p>CCAFS <br />Core Project</p></div></a>
        [/#if]
        [#if securityContext.canAddBilateralProject()]
          <a href="[@s.url namespace="/planning" action='addNewBilateralProject'/]"><div class="addProject"><p>Bilateral <br />Project</p></div></a>
        [/#if]
        [#if !securityContext.canAddCoreProject() && !securityContext.canAddBilateralProject() && !securityContext.canAddCofoundedProject()]
          <p>You don't have sufficient permissions to add a project</p>
        [/#if]
      </div>
    </div>
    [#if reportingActive]
    <div id="deadlineTitle"  class="homeTitle"><b>[@s.text name="home.dashboard.deadline.title" /]</b></div>
    <div id="timelineBlock" class="borderBox">
      <ul class="timeline" id="timeline">
        [@time title="Project Reporting (PLs & CPs)"    subTitle="Project Leaders & Contact Points"   dateText="2016-02-15" status="Open"/]
        [@time title="Project Reporting (PLs & CPs)"    subTitle="Project Leaders & Contact Points"   dateText="2016-03-03" status="Close"/]
        
        [@time title="CRP Indicators (CPs) & <br />Regional Synthesis (RPLs)"  subTitle="Contact Points & Regional Leaders"  dateText="2016-03-07" status="Open"/]
        [@time title="CRP Indicators (CPs) & <br />Regional Synthesis (RPLs)"  subTitle="Contact Points &  Regional Leaders"  dateText="2016-03-18" status="Close"/]
        
        [@time title="CRP Indicators & Synthesis (FPLs & RPLs)"  subTitle="Flagships Leaders & Regional Leaders"  dateText="2016-03-21" status="Open"/]
        [@time title="CRP Indicators & Synthesis (FPLs &  RPLs)"  subTitle="Flagships Leaders & Regional Leaders"  dateText="2016-04-01" status="Close"/]
  
  
  
      </ul> 
      <div class="clearfix"></div> 
    </div>
    <div id="leftSide">
      [#-- Deadline --]
      <div id="deadlineTitle"  class="homeTitle"><b>[@s.text name="home.dashboard.shortcuts" /]</b></div>
      <div id="deadline" class="borderBox">
        <ul class="subMenu">
          <li><a href="${baseUrl}/planning/projectsList.do" class="">Projects</a></li>
          <li><a href="${baseUrl}/reporting/synthesis/crpIndicators.do?liaisonInstitutionID&edit=true" >CRP Indicators</a></li>
          <li><a href="${baseUrl}/reporting/synthesis/outcomeSynthesis.do?liaisonInstitutionID&edit=true" >Outcome Synthesis</a></li>
          <li class="last"><a href="${baseUrl}/reporting/synthesis/synthesisByMog.do?liaisonInstitutionID&edit=true">Synthesis by MOG</a></li>
        </ul>
      </div>
    </div> <!-- End leftSide -->
    
    
    [/#if]
    
    [#if !reportingActive]
    <div id="leftSide">
      [#-- Deadline --]
      <div id="deadlineTitle"  class="homeTitle"><b>[@s.text name="home.dashboard.shortcuts" /]</b></div>
      <div id="deadline" class="borderBox">
        <ul class="subMenu">
          <li><a href="${baseUrl}/planning/projectsList.do" class="">Projects</a></li>
         
        </ul>
      </div>
    </div> <!-- End leftSide -->
    [/#if]
    
    <div id="rightSide">
      [#-- DashBoard --]
      <div class="loadingBlock"></div>
      <div style="display:none">
        <div id="dashboardTitle" class="homeTitle"><b>[@s.text name="home.dashboard.name" /]</b></div> 
        <div id="dashboard-tabs">
          <ul class="dashboardHeaders">
            <li class=""><a href="#projects">[@s.text name="home.dashboard.projects" /]</a></li>
            <li class=""><a href="#ipGraph-content">[@s.text name="home.dashboard.impactPathway" /]</a></li>
          </ul> <!-- End dashboardHeaders -->
          [#-- My projects --]
          <div id="projects"> 
            [#if projects?has_content]
              [@projectList.projectsList projects=projects canValidate=true namespace="/planning/projects" tableID="projects-table" /]
            [#else]
              <p class="emptyMessage">[@s.text name="home.dashboard.projects.empty"][@s.param][@s.url namespace="/reporting" action="projectsList" /][/@s.param][/@s.text]<p>
            [/#if]
          </div>
          [#-- IP Graph --]
          <div id="ipGraph-content" style="position: relative;">
            <div id="loading-ipGraph-content" style="display:none;position: absolute;top: 45%;right: 45%;">
                <img style="display: block; margin: 0 auto;" src="./images/global/loading.gif" alt="Loader" />
            </div>
          </div>
          
        </div>
      </div>
    </div> <!-- End rightSide -->
    
  </div>
</article>
[#-- Show P&R proccess workflow --]
<div id="showPandRWorkflowDialog" style="display:none; height:100%;  width: 100%;" title="[@s.text name="home.dashboard.workflow" /]"> 
  <div sytle="height:100%;  width: 100%;">
    <img id="imgBigModal" src="${baseUrl}/images/global/pandrWorkflow.png"/>
  </div>
</div> 

[#macro time title subTitle dateText status]
<li class="li">
  <div class="timestamp" title="${title}">
    <p class="author"> ${status} </p>
    [#setting date_format="YYYY-MM-DD"]
    <p class="date">${dateText}</p>
    <p class="dateText" style="display:none">${dateText}</p>
    <p class="isOpen" style="display:none">${(status == "Open")?string}</p>
  </div>
  <div class="status"><h4>${title}</h4></div>
</li>
[/#macro]

[#include "/WEB-INF/global/pages/footer-logos.ftl"]