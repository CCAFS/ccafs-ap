[#ftl]
<nav id="secondaryMenu">
	 
  <ul>
    <a [#if currentPrePlanningSection == "impactPathways"] class="currentReportingSection" [/#if] href="[@s.url action='outcomes'][/@s.url]">
      <li><div class=""  id="i-mainInformation"></div>[@s.text name="menu.secondary.preplanning.impactPathways" /]</li>
    </a>
    <a [#if currentPrePlanningSection == "projects"] class="currentReportingSection" [/#if] href="[@s.url action='projects'][/@s.url]">
      <li><div class=""  id="i-objectives"></div>[@s.text name="menu.secondary.preplanning.projects" /]</li>
    </a> 
  </ul>
</nav>