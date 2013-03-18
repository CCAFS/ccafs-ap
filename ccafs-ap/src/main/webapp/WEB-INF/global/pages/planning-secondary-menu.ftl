[#ftl]
<nav id="secondaryMenu">
  <ul>
    <a [#if currentPlanningSection == "mainInformation"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/activities.do"><li>[@s.text name="menu.secondary.planning.mainInformation" /]</li></a>
    <a [#if currentPlanningSection == "deliverables"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/outputSummary.do"><li>[@s.text name="menu.secondary.planning.deliverables" /]</li></a>
    <a [#if currentPlanningSection == "partners"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/publications.do"><li>[@s.text name="menu.secondary.planning.partners" /]</li></a>
    <a [#if currentPlanningSection == "locations"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/caseStudies.do"><li>[@s.text name="menu.secondary.planning.locations" /]</li></a>    
  </ul>
</nav>