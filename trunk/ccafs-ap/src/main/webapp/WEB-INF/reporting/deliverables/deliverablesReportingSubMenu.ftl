[#ftl]

<nav id="stageMenu">
  <ul>
    <li [#if currentStage == "metadata"] class="currentReportingSection" [/#if]><a href="
        [@s.url action='deliverables' includeParams='get'][/@s.url]
      ">[@s.text name="menu.activitiesReporting.submenu.deliverable.metadata" /]
    </a> </li>
    <li [#if currentStage == "dataSharing"] class="currentReportingSection" [/#if]><a href="
        [@s.url action='deliverablesData' includeParams='get'][/@s.url]
      ">[@s.text name="menu.activitiesReporting.submenu.deliverable.dataSharing" /]
    </a></li>
    <li [#if currentStage == "ranking"] class="currentReportingSection" [/#if]><a href="
        [@s.url action='deliverablesRank' includeParams='get'][/@s.url]
      ">[@s.text name="menu.activitiesReporting.submenu.deliverable.ranking" /]
    </a></li>
  </ul>
  <div class="clearfix"></div>
</nav>
