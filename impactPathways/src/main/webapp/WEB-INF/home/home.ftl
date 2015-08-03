[#ftl]
[#assign title = "Dashboard - CCAFS P&R" /]
[#assign globalLibs = ["jquery", "noty", "jreject", "dataTable", "slidr", "cytoscape", "qtip","cytoscapePanzoom"] /]
[#assign customJS = ["${baseUrl}/js/home/login.js","${baseUrl}/js/global/ipGraph.js","${baseUrl}/js/home/dashboard.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = "home" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
[#import "/WEB-INF/global/templates/homeProjectsListTemplate.ftl" as projectList /]

<article id="dashboard">

  <div class="content">
    <h1>[@s.text name="home.dashboard.title" /]</h1>
    <div id="leftSide">
      [#-- DashBoard --]
      <div class="loadingBlock"></div>
      <div style="display:none">
        <div id="dashboardTitle" class="homeTitle"><b>[@s.text name="home.dashboard.name" /]</b></div> 
        <div id="dashboard">
          <ul class="dashboardHeaders">
            <li class="">
              <a href="#projects">[@s.text name="home.dashboard.projects" /]</a>
            </li>
            <li class="">
               <a href="#ipGraph-content">[@s.text name="home.dashboard.impactPathway" /]</a>
            </li>
          </ul> <!-- End dashboardHeaders -->
          <div id="projects"> 
            [#if projects?has_content]
              [@projectList.projectsList projects=projects canValidate=true namespace="/planning/projects" tableID="projects-table" /]
            [#else]
              <p class="emptyMessage">
                [@s.text name="home.dashboard.projects.empty"][@s.param][@s.url namespace="/planning" action="projects" /][/@s.param][/@s.text]
              <p>
            [/#if]
          </div>
          <div id="ipGraph-content" style="position: relative;">
            <div id="loading-ipGraph-content" style="display:none;position: absolute;top: 45%;right: 45%;">
                <img style="display: block; margin: 0 auto;" src="./images/global/loading.gif" alt="Loader" />
            </div>
          </div>
        </div> <!-- End dashboard -->
      </div><!-- End Ajax loader -->
      [#-- Deadline --]
      <div id="deadlineTitle"  class="homeTitle">
        <b>[@s.text name="home.dashboard.deadline.title" /]</b>
      </div>
      <div id="deadline" class="borderBox">
        <div id="deadlineGraph" >
          <div class="point active">1</div>
          <div class="point inactive">2</div>
          <div class="point inactive">3</div>
          <div class="point inactive">4</div>
        </div>
        <div id="deadlineGraph">
          <div class="textPoint">
            [@s.text name="home.dashboard.deadline.planning" /] 
          </div>
          <div class="textPoint">
            [@s.text name="home.dashboard.deadline.summaries" /] 
          </div>
          <div class="textPoint">
            [@s.text name="home.dashboard.deadline.reporting" /]
          </div>
          <div class="textPoint">
            [@s.text name="home.dashboard.deadline.learnValidation" /] 
          </div>
        </div>
        <div id="deadlineDates">
          <table>
            <tr>
              <td>[@s.text name="home.dashboard.deadline.planning" /]</td>
              <td>14th September 2015</td>
            </tr>
            <tr>
              <td>[@s.text name="home.dashboard.deadline.reporting" /]</td>
              <td>1st January 2016</td>
            </tr>
          </table>
        </div>
      </div> <!-- End deadline -->
      
    </div> <!-- End leftSide -->
    
    <div id="rightSide">
      [#-- P&R Description --]
      <div id="pandrDescription">
        <div id="pandrTitle" class="homeTitle">
          <b>[@s.text name="home.dashboard.description.title" /]</b>
        </div>
        <p>[@s.text name="home.dashboard.description.text" /]</p>
        <div id="imgPandR">
          <a href="#" onClick="workflowModal()"><img id="imgModal" src="${baseUrl}/images/global/pandrWorkflow.png"/></a>
        </div>
      </div> <!-- End pandrDescription -->
    </div> <!-- End rightSide -->
  </div> <!-- End content -->

</article>
[#-- Show P&R proccess workflow --]
<div id="showPandRWorkflowDialog" style="display:none; height:100%;  width: 100%;" title="[@s.text name="home.dashboard.workflow" /]"> 
  <div sytle="height:100%;  width: 100%;">
    <img id="imgBigModal" src="${baseUrl}/images/global/pandrWorkflow.png"/>
  </div>
</div> 
[#include "/WEB-INF/global/pages/footer-logos.ftl"]