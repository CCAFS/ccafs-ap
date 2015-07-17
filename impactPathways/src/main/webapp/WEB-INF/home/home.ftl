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
[#import "/WEB-INF/global/templates/homeActivitiesListTemplate.ftl" as activitiesList /]

<article id="dashboard">
  <div class="content">
    <h1>[@s.text name="home.dashboard.title" /]</h1>
    <div id="leftSide">
      [#-- DashBoard --]
      <div id="dashboardTitle" class="homeTitle">
        <b>[@s.text name="home.dashboard.name" /]</b>
      </div>
      <div id="dashboard">
        <ul class="dashboardHeaders">
          [#-- if projects?has_content --]
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
                [@s.text name="home.dashboard.projects.empty"]
                  [@s.param][@s.url namespace="/planning" action="projects" /][/@s.param]
                [/@s.text]
              <p>
            [/#if]
          </div>
          <div id="ipGraph-content" style="position: relative;">
            <div id="loading-ipGraph-content" style="display:none;position: absolute;top: 45%;right: 45%;">
                <img style="display: block; margin: 0 auto;" src="./images/global/loading.gif" alt="Loader" />
            </div>
          </div>
      </div> <!-- End dashboard -->
      
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
              <td>15th September</td>
            </tr>
            <tr>
              <td>[@s.text name="home.dashboard.deadline.reporting" /]</td>
              <td>TBD</td>
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
      
      [#-- Roles --]
      <!-- div id="roles">
        <div id="roleTitle" class="homeTitle">
          <b>[@s.text name="home.dashboard.roles.title" /]</b>
        </div>
        <div id="slider"> 
          <div id="slide1" data-slidr="slide1" class="slide">
            <div id="title1" class="homeSubTitle">
              <h6>[@s.text name="home.dashboard.role.rpl" /]</h6>
            </div>
            <div id="content1" class="role-content">
              [@s.text name="home.dashboard.role.rpl.description" /]
            </div>
          </div>
          <div id="slide2" data-slidr="slide2"  class="slide">
            <div id="title2" class="homeSubTitle">
              <h6>[@s.text name="home.dashboard.role.fpl" /]</h6>
            </div>
            <div id="content2" class="role-content">
              [@s.text name="home.dashboard.role.fpl.description" /]
            </div>
          </div>
          <div id="slide3" data-slidr="slide3"  class="slide">
            <div id="title3" class="homeSubTitle">
              <h6>[@s.text name="home.dashboard.role.pl" /]</h6>
            </div>
            <div id="content3" class="role-content">
                [@s.text name="home.dashboard.role.pl.description" /]
            </div>
          </div>
          <div id="slide4" data-slidr="slide4"  class="slide">
            <div id="title4" class="homeSubTitle">
              <h6>[@s.text name="home.dashboard.role.po" /]</h6>
            </div>
            <div id="content4" class="role-content">
              [@s.text name="home.dashboard.role.po.description" /]
            </div>
          </div>
          <div id="slide5" data-slidr="slide5"  class="slide">
            <div id="title5" class="homeSubTitle">
              <h6>[@s.text name="home.dashboard.role.cu" /]</h6>
            </div>
            <div id="content5" class="role-content">
              [@s.text name="home.dashboard.role.cu.description" /]
            </div>
          </div>
          <div id="slide6" data-slidr="slide6"  class="slide">
            <div id="title6" class="homeSubTitle">
              <h6>[@s.text name="home.dashboard.role.al" /]</h6>
            </div>
            <div id="content6" class="role-content">
              [@s.text name="home.dashboard.role.al.description" /]
            </div>
          </div>
          <div id="slide7" data-slidr="slide7"  class="slide">
            <div id="title7" class="homeSubTitle">
              <h6>[@s.text name="home.dashboard.role.cp" /]</h6>
            </div>
            <div id="content7" class="role-content">
              [@s.text name="home.dashboard.role.cp.description" /]
            </div> 
          </div>  
        </div><!-- End #slider >
      </div> <!-- End roles -->
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