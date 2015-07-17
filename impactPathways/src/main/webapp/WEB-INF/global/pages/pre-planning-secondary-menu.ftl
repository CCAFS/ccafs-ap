[#ftl]
<nav id="secondaryMenu">
  <ul>
    [#if !securityContext.CU]
      <li><a [#if currentPrePlanningSection == "impactPathways"] class="currentSection" [/#if] href="[@s.url namespace="/pre-planning" action='outcomes'][/@s.url]">
        [@s.text name="menu.secondary.preplanning.impactPathways" /]
      </a></li>
    [/#if]
    <li><a [#if currentPrePlanningSection == "projects"] class="currentSection" [/#if] href="[@s.url namespace="/pre-planning" action='projects'][/@s.url]">
      [@s.text name="menu.secondary.preplanning.projects" /]
    </a></li>
  </ul>
</nav>