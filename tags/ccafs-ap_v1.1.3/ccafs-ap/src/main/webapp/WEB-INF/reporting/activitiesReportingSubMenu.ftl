[#ftl]
<nav id="stageMenu">
  <ul>
    <a [#if currentStage == "status"] class="currentReportingSection" [/#if] href="
        [@s.url action='status' includeParams='get'][/@s.url]
      "><li>[@s.text name="menu.activitiesReporting.submenu.status" /]</li>
    </a>
    <a [#if currentStage == "deliverables"] class="currentReportingSection" [/#if] href="
        [@s.url action='deliverables' includeParams='get'][/@s.url]
      "><li>[@s.text name="menu.activitiesReporting.submenu.deliverables" /]</li>
    </a>
    <a [#if currentStage == "partners"] class="currentReportingSection" [/#if] href="
        [@s.url action='partners' includeParams='get'][/@s.url]
      "><li>[@s.text name="menu.activitiesReporting.submenu.partners" /]</li>
    </a>
  </ul>
</nav>