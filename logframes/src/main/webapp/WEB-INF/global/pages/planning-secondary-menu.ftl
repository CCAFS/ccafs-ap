[#ftl]
<nav id="secondaryMenu">
	 
  <ul>
    <a [#if currentPlanningSection == "mainInformation"] class="currentReportingSection" [/#if] href="[@s.url action='mainInformation'][@s.param name='${activityRequestParameter}']${activity.id?c}[/@s.param][/@s.url]">
      <li><div class="icon-s"  id="i-mainInformation"></div>[@s.text name="menu.secondary.planning.mainInformation" /]</li>
    </a>
    <a [#if currentPlanningSection == "objectives"] class="currentReportingSection" [/#if] href="[@s.url action='objectives'][@s.param name='${activityRequestParameter}']${activity.id?c}[/@s.param][/@s.url]">
      <li><div class="icon-s"  id="i-objectives"></div>[@s.text name="menu.secondary.planning.objectives" /]</li>
    </a>
    <a [#if currentPlanningSection == "deliverables"] class="currentReportingSection" [/#if] href="[@s.url action='deliverables'][@s.param name='${activityRequestParameter}']${activity.id?c}[/@s.param][/@s.url]">
      <li><div class="icon-s"  id="i-deliverables"></div>[@s.text name="menu.secondary.planning.deliverables" /]</li>
    </a>
    <a [#if currentPlanningSection == "partners"] class="currentReportingSection" [/#if] href="[@s.url action='partners'][@s.param name='${activityRequestParameter}']${activity.id?c}[/@s.param][/@s.url]">
      <li><div class="icon-s"  id="i-partners"></div>[@s.text name="menu.secondary.planning.partners" /]</li>
    </a>
    <a [#if currentPlanningSection == "locations"] class="currentReportingSection" [/#if] href="[@s.url action='locations'][@s.param name='${activityRequestParameter}']${activity.id?c}[/@s.param][/@s.url]">
      <li><div class="icon-s"  id="i-locations"></div>[@s.text name="menu.secondary.planning.locations" /]</li>
    </a>
    <a [#if currentPlanningSection == "additionalInformation"] class="currentReportingSection" [/#if] href="[@s.url action='additionalInformation'][@s.param name='${activityRequestParameter}']${activity.id?c}[/@s.param][/@s.url]">
      <li><div class="icon-s"  id="i-additionalInformation"></div>[@s.text name="menu.secondary.planning.additionalInformation" /]</li>
    </a>
  </ul>
</nav>