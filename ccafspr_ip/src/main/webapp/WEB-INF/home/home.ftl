[#ftl]
[#assign title = "Welcome to CCAFS Activity Planning" /]
[#assign globalLibs = ["jquery", "jreject", "dataTable", "slidr", "cytoscape", "qtip","cytoscapePanzoom"] /]
[#assign customJS = ["${baseUrl}/js/home/login.js","${baseUrl}/js/global/ipGraph.js","${baseUrl}/js/home/dashboard.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = "home" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
[#import "/WEB-INF/global/templates/homeProjectsListTemplate.ftl" as projectList /]
[#import "/WEB-INF/global/templates/homeActivitiesListTemplate.ftl" as activitiesList /]

<article>
  <div class="content">
    <h1>[@s.text name="home.dashboard.title" /]</h1>

    [#-- Home introduction  
    <p> [@s.text name="home.dashboard.introduction" /] </p>
    
    <div id="loginFormContainer">
      <p class="alreadyLogged">[@s.text name="home.login.alreadyLogged" /]</p>
      <span class="alreadyLoggedEmail">${currentUser.email}</span>
    </div>--]
    
    <div id="leftSide">
      [#-- DashBoard --]
        <div id="dashboardTitle" class="homeTitle">
          <b>[@s.text name="home.dashboard.name" /]</b>
        </div>
      <div id="dashboard"> 
          <ul class="">
          [#if projects?has_content]
            <li class="">
              <a href="#projects">[@s.text name="home.dashboard.projects" /]</a>
            </li>
          [/#if]
          [#if activities?has_content]  
            <li class="">
               <a href="#activities">[@s.text name="home.dashboard.activities" /]</a>
            </li>
          [/#if] 
            <li class="">
               <a href="#ipGraph-content">[@s.text name="home.dashboard.impactPathway" /]</a>
            </li>
          </ul> 
          [#-- Test Variables  [#assign projects = []] - [#assign activities = []] [#-- End Test Variables --]
          [#if projects?has_content]
            <div id="projects"> 
                [@projectList.projectsList projects=projects canValidate=true namespace="/planning/projects" tableID="projects-table" /]
            </div>
          [/#if]
          [#if activities?has_content]
            <div id="activities"> 
                [@activitiesList.activitiesList activities=activities canValidate=true canEditProject=true namespace="/planning/projects/activities" tableID="activities-table" /]
            </div>
          [/#if]
          <div id="ipGraph-content">
          </div>  
      </div>
      [#-- End DashBoard --]
      [#-- Deadline --]
      <div id="deadline">
        <div id="deadlineTitle"  class="homeTitle">
          <b>[@s.text name="home.dashboard.deadline.title" /]</b>
        </div>
        <div id="deadlineGraph">
          <span class="deadlineCircle">1</span>
          <div id="deadlineLine"></div>
          <span class="deadlineCircle">2</span>
          <div id="deadlineLine"></div>
          <span class="deadlineCircle">3</span>
          <div id="preplanning" class"active step">
            [@s.text name="home.dashboard.deadline.preplanning" /] 
          </div>  
          <div id="planning" class"step">
            [@s.text name="home.dashboard.deadline.planning" /] 
          </div> 
          <div id="reporting" class"step">
            [@s.text name="home.dashboard.deadline.reporting" /]
          </div> 
        </div>
        <div id="deadlineDates">
          <table>
            <tr>
              <td>[@s.text name="home.dashboard.deadline.preplanning" /] LAM</td>
              <td>1st September</td>
            </tr>
            <tr>
              <td>[@s.text name="home.dashboard.deadline.planning" /] LAM</td>
              <td>2nd - 6th September</td>
            </tr>
            <tr>
              <td>[@s.text name="home.dashboard.deadline.planning" /] Others</td>
              <td>19th September</td>
            </tr>
          </table>
        </div>
      </div>
      [#-- End Deadline --]
    </div>
    
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
      </div>
      [#-- End P&R Description --]
      [#-- Roles
      <div id="roles">
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
          </div><!-- End Content Slider-->
          
          
         
      </div>
      [#-- End Roles --]
      [#-- General Announcements 
      <div id="generalAnnouncements">
        <div id="announcesTitle"  class="homeTitle">
          <b>[@s.text name="home.dashboard.generalannouncements" /]</b>
        </div>
        <div id="announces">
        </div>
      </div>
      [#-- End General Announcements --]
    </div>
    <br>
  </div>
</article>
[#-- Show P&R proccess workflow --]
<div id="showPandRWorkflowDialog" style="display:none; height:100%;  width: 100%;" title="[@s.text name="home.dashboard.workflow" /]"> 
  <div sytle="height:100%;  width: 100%;">
    <img id="imgBigModal" src="${baseUrl}/images/global/pandrWorkflow.png"/>
  </div>
</div> 
[#include "/WEB-INF/global/pages/footer-logos.ftl"]