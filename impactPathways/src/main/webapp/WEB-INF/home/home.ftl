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
      <div id="newProject" class="option"><p>Enter a new project</p></div>
      <a href="[@s.url namespace="/planning" action='projectsList'/]"><div id="updatePlanning" class="option"><p>Update planning of an ongoing project</p></div></a>
      <div id="reportProject" class="option disabled" title="This link is disabled"><p>Report on an ongoing project</p></div>
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
    <div id="leftSide">
      [#-- Deadline --]
      <div id="deadlineTitle"  class="homeTitle"><b>[@s.text name="home.dashboard.deadline.title" /]</b></div>
      <div id="deadline" class="borderBox">
        <div id="deadlineGraph" >
          <div class="point active">1</div>
          <div class="point inactive">2</div>
          <div class="point inactive">3</div>
          <div class="point inactive">4</div>
        </div>
        <div id="deadlineGraph">
          <div class="textPoint">[@s.text name="home.dashboard.deadline.planning" /] </div>
          <div class="textPoint">[@s.text name="home.dashboard.deadline.summaries" /] </div>
          <div class="textPoint">[@s.text name="home.dashboard.deadline.reporting" /]</div>
          <div class="textPoint">[@s.text name="home.dashboard.deadline.learnValidation" /] </div>
        </div>
        <div id="deadlineDates">
          <table>
            <tr>
              <td>[@s.text name="home.dashboard.deadline.planning" /] - Managment Liaisons & Contact Points</td>
              <td>20th October 2015</td>
            </tr>
            <tr>
              <td>[@s.text name="home.dashboard.deadline.planning" /] - Project Leaders & Project Coordinators</td>
              <td>27th October 2015</td>
            </tr>
            <tr>
              <td>[@s.text name="home.dashboard.deadline.reporting" /]</td>
              <td>1st January 2016</td>
            </tr>
          </table>
        </div>
      </div>
    </div> <!-- End leftSide -->
    <div id="rightSide">
      [#-- DashBoard --]
      <div class="loadingBlock"></div>
      <div style="display:none">
        <div id="dashboardTitle" class="homeTitle"><b>[@s.text name="home.dashboard.name" /]</b></div> 
        <div id="dashboard">
          <ul class="dashboardHeaders">
            <li class=""><a href="#projects">[@s.text name="home.dashboard.projects" /]</a></li>
            <li class=""><a href="#ipGraph-content">[@s.text name="home.dashboard.impactPathway" /]</a></li>
          </ul> <!-- End dashboardHeaders -->
          [#-- My projects --]
          <div id="projects"> 
            [#if projects?has_content]
              [@projectList.projectsList projects=projects canValidate=true namespace="/reporting/projects" tableID="projects-table" /]
            [#else]
              <p class="emptyMessage">[@s.text name="home.dashboard.projects.empty"][@s.param][@s.url namespace="/planning" action="projectsList" /][/@s.param][/@s.text]<p>
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
[#include "/WEB-INF/global/pages/footer-logos.ftl"]