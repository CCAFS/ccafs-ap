[#ftl]
<nav id="stageMenu">
  <ul>
    <li class="goBack"><a href="
        [@s.url action='activities' includeParams='get'][/@s.url]
      ">[@s.text name="menu.secondary.summaries.activities" /]
    </a> </li>
    <li [#if currentStage == "status"] class="currentReportingSection" [/#if]><a href="
        [@s.url action='status' includeParams='get'][/@s.url]
      "><p>[@s.text name="menu.activitiesReporting.submenu.status" /]</p>
    </a></li>
    <li [#if currentStage == "deliverables"] class="currentReportingSection" [/#if]><a href="
        [@s.url action='deliverablesList' includeParams='get'][/@s.url]
      "><p>[@s.text name="menu.activitiesReporting.submenu.deliverables" /]</p>
    </a></li>
    <li [#if currentStage == "partners"] class="currentReportingSection" [/#if]><a href="
        [@s.url action='partners' includeParams='get'][/@s.url]
      "><p>[@s.text name="menu.activitiesReporting.submenu.partners" /]</p>
    </a></li>
  </ul>
<div class="clearfix"></div>
</nav>