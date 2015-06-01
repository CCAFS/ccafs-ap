[#ftl]
<nav id="secondaryMenu">
  <ul>
    [#if !securityContext.CU]
      <a [#if currentPrePlanningSection == "impactPathways"] class="currentSection" [/#if] href="[@s.url namespace="/pre-planning" action='outcomes'][/@s.url]">
        <li>[@s.text name="menu.secondary.preplanning.impactPathways" /]</li>
      </a>
    [/#if]
    <a [#if currentPrePlanningSection == "projects"] class="currentSection" [/#if] href="[@s.url namespace="/pre-planning" action='projects'][/@s.url]">
      <li>[@s.text name="menu.secondary.preplanning.projects" /]</li>
    </a> 
  </ul>
</nav>