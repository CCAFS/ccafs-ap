[#ftl]
<nav id="stageMenu">
  <ul>
    <a [#if currentStage == "metadata"] class="currentReportingSection" [/#if] href="
        [@s.url action='deliverableMetadata' includeParams='get'][/@s.url]
      "><li>[@s.text name="menu.activitiesReporting.submenu.deliverable.metadata" /]</li>
    </a>
    <a [#if currentStage == "nextUsers"] class="currentReportingSection" [/#if] href="
        [@s.url action='deliverables' includeParams='get'][/@s.url]
      "><li>[@s.text name="menu.activitiesReporting.submenu.deliverable.nextUsers" /]</li>
    </a>
    <a [#if currentStage == "dataSharing"] class="currentReportingSection" [/#if] href="
        [@s.url action='partners' includeParams='get'][/@s.url]
      "><li>[@s.text name="menu.activitiesReporting.submenu.deliverable.dataSharing" /]</li>
    </a>
    <a [#if currentStage == "ranking"] class="currentReportingSection" [/#if] href="
        [@s.url action='partners' includeParams='get'][/@s.url]
      "><li>[@s.text name="menu.activitiesReporting.submenu.deliverable.ranking" /]</li>
    </a>
  </ul>
</nav>