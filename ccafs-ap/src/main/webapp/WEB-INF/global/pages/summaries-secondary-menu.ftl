[#ftl]
<div id="secondaryMenu">
  <ul>
    <a [#if currentSummariesSection == "activities"] class="currentReportingSection" [/#if] href="[@s.url action='activities' /]">
      <li>[@s.text name="menu.secondary.summaries.activities" /]</li>
    </a>
    <a [#if currentSummariesSection == "milestones"] class="currentReportingSection" [/#if] href="[@s.url action='milestones' /]">
      <li>[@s.text name="menu.secondary.summaries.milestones" /]</li>
    </a>
    <a [#if currentSummariesSection == "publications"] class="currentReportingSection" [/#if] href="[@s.url action='publications' /]">
      <li>[@s.text name="menu.secondary.summaries.publications" /]</li>
    </a>
    <a [#if currentSummariesSection == "caseStudies"] class="currentReportingSection" [/#if] href="[@s.url action='caseStudies' /]">
      <li>[@s.text name="menu.secondary.summaries.caseStudies" /]</li>
    </a>
    <a [#if currentSummariesSection == "outcomes"] class="currentReportingSection" [/#if] href="[@s.url action='outcomes' /]">
      <li>[@s.text name="menu.secondary.summaries.outcomes" /]</li>
    </a>
    <a [#if currentSummariesSection == "deliverables"] class="currentReportingSection" [/#if] href="[@s.url action='deliverables' /]">
      <li>[@s.text name="menu.secondary.summaries.deliverables" /]</li>
    </a>
  </ul>
</div>