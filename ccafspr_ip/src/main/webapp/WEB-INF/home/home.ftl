[#ftl]
[#assign title = "Welcome to CCAFS Activity Planning" /]
[#assign globalLibs = ["jquery", "jreject"] /]
[#assign customJS = ["${baseUrl}/js/home/login.js"] /]
[#assign currentSection = "home" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
[#import "/WEB-INF/global/templates/projectsListTemplate.ftl" as projectList /]
[#import "/WEB-INF/planning/macros/activitiesListTemplate.ftl" as activitiesList /]

<article>
  <div class="content">
    <h1>[@s.text name="home.home.title" /]</h1>

    [#-- Home introduction  
    <p> [@s.text name="home.home.introduction" /] </p>
    
    <div id="loginFormContainer">
      <p class="alreadyLogged">[@s.text name="home.login.alreadyLogged" /]</p>
      <span class="alreadyLoggedEmail">${currentUser.email}</span>
    </div>--]
    
    <div id="leftSide">
      [#-- DashBoard --]
      <div id="dashboard">
        <div id="dashboardTitle" class="homeTitle">
          <b>[@s.text name="home.home.dashboard.title" /]</b>
        </div>
        <div id="dashboardTabs" class="ui-tabs ui-widget ui-widget-content ui-corner-all" >
          <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
            <li class="ui-state-default ui-corner-top ui-tabs-active ui-state-active ui-state-hover">
              [@s.text name="home.home.dashboard.projects" /]
            </li>
            <li class="ui-state-default ui-corner-top ui-tabs-active ui-state-active ui-state-hover">
              [@s.text name="home.home.dashboard.activities" /]
            </li>
            <li class="ui-state-default ui-corner-top ui-tabs-active ui-state-active ui-state-hover">
              [@s.text name="home.home.dashboard.impactPathway" /]
            </li>
          </ul>
          <div id="dashboardContent">
          [#-- Test Variables  [#assign projects = []] - [#assign activities = []] [#-- End Test Variables --]
            <div id="projects">
              [#if projects?size>0]
                [@projectList.projectsList projects=projects canValidate=true namespace="/planning/projects" /]
              [/#if]
            </div>
            <div id="activities">
              [#if activities?size > 0]
                [@activitiesList.activitiesList activities=activities canValidate=true canEditProject=true namespace="/planning/projects/activities" tableID="activities" /]
              [/#if]
            </div>
            <div id="impacthPathwayGraph">
            </div>
          </div>
        </div><!-- End DashboardTabs-->
      </div>
      [#-- End DashBoard --]
      [#-- Deadline --]
      <div id="deadline">
        <div id="deadlineTitle"  class="homeTitle">
          <b>[@s.text name="home.home.deadline.title" /]</b>
        </div>
        <div id="deadlineGraph">
          [@s.text name="home.home.deadline.preplanning" /] - [@s.text name="home.home.deadline.planning" /] - [@s.text name="home.home.deadline.reporting" /]
        </div>
        <div id="deadlineDates">
          <table>
            <tr>
              <td>[@s.text name="home.home.deadline.center" /]</td>
              <td>31 August</td>
              <td>Remaining 10 days</td>
            </tr>
            <tr>
              <td>[@s.text name="home.home.role.rpl" /]</td>
              <td>15th September</td>
              <td>Remaining 25 days</td>
            </tr>
            <tr>
              <td>[@s.text name="home.home.role.fpl" /]</td>
              <td>24th September</td>
              <td>Remaining 30 days</td>
            </tr>
            <tr>
              <td>[@s.text name="home.home.role.cu" /]</td>
              <td>24th September</td>
              <td>Remaining 34 days</td>
            </tr>
          </table>
        </div>
      </div>
      [#-- End Deadline --]
    </div>
    
    <div class="rightSide">
      [#-- P&R Description --]
      <div id="pandrDescription">
        <div id="pandrTitle" class="homeTitle">
          <b>[@s.text name="home.home.pAndR.title" /]</b>
        </div>
        [@s.text name="home.home.pAndR.description" /]
      </div>
      [#-- End P&R Description --]
      [#-- Roles --]
      <div id="roles">
        <div id="roleTitle" class="homeTitle">
          <b>[@s.text name="home.home.roles.title" /]</b>
        </div>
        <div id="slider">
          <div id="content">
            <div id="slide1">
              <div id="title1">
                [@s.text name="home.home.role.rpl" /]
              </div>
              <div id="content1">
                [@s.text name="home.home.role.rpl.description" /]
              </div>
            </div>
            <div id="slide2">
              <div id="title2">
                [@s.text name="home.home.role.fpl" /]
              </div>
              <div id="content2">
                [@s.text name="home.home.role.fpl.description" /]
              </div>
            </div>
            <div id="slide3">
              <div id="title3">
                [@s.text name="home.home.role.pl" /]
              </div>
              <div id="content3">
                [@s.text name="home.home.role.pl.description" /]
              </div>
            </div>
            <div id="slide4">
              <div id="title4">
                [@s.text name="home.home.role.po" /]
              </div>
              <div id="content4">
                [@s.text name="home.home.role.po.description" /]
              </div>
            </div>
            <div id="slide5">
              <div id="title5">
                [@s.text name="home.home.role.cu" /]
              </div>
              <div id="content5">
                [@s.text name="home.home.role.cu.description" /]
              </div>
            </div>
            <div id="slide6">
              <div id="title6">
                [@s.text name="home.home.role.al" /]
              </div>
              <div id="content6">
                [@s.text name="home.home.role.al.description" /]
              </div>
            </div>
            <div id="slide7">
              <div id="title7">
                [@s.text name="home.home.role.cp" /]
              </div>
              <div id="content7">
                [@s.text name="home.home.role.cp.description" /]
              </div>
            </div>
          </div><!-- End Content Slider-->
          <div id="">
            <ul>
              <li>
                <a href="#"><img src=""/></a>
              </li>
              <li>
                <a href="#"><img src=""/></a>
              </li>
              <li>
                <a href="#"><img src=""/></a>
              </li>
              <li>
                <a href="#"><img src=""/></a>
              </li>
              <li>
                <a href="#"><img src=""/></a>
              </li>
              <li>
                <a href="#"><img src=""/></a>
              </li>
              <li>
                <a href="#"><img src=""/></a>
              </li>
            </ul>
          </div>
        </div>
      </div>
      [#-- End Roles --]
      [#-- General Announcements --]
      <div id="generalAnnouncements">
        <div id="announcesTitle"  class="homeTitle">
          <b>[@s.text name="home.home.generalannouncements" /]</b>
        </div>
        <div id="announces">
        </div>
      </div>
      [#-- End General Announcements --]
    </div>
    <br>
  </div>
</article>
[#include "/WEB-INF/global/pages/footer-logos.ftl"]