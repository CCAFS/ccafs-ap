[#ftl]
<nav id="stageMenu">
  <ul>
    <a [#if currentReportingSection == "activities"] class="currentReportingSection" [/#if] href=""><li>Status</li></a>
    <a [#if currentReportingSection == "outputs"] class="currentReportingSection" [/#if] href=""><li>Deliverables</li></a>
    <a [#if currentReportingSection == "publications"] class="currentReportingSection" [/#if] href=""><li>Partners</li></a>            
  </ul>
</nav>