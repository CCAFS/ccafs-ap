[#ftl]
<nav id="stageMenu">
  <ul>
    <a [#if currentStage == "tlOutputSummary"] class="currentReportingSection" [/#if] href="
        [@s.url action='tlOutputs' includeParams='get'][/@s.url]
      "><li>TL Summary by output</li>
    </a>
    <a [#if currentStage == "rplSynthesisReport"] class="currentReportingSection" [/#if] href="
        [@s.url action='deliverables' includeParams='get'][/@s.url]
      "><li>RPL synthesis report</li>
    </a>
    <a [#if currentStage == "TLRPLMilestoneReport"] class="currentReportingSection" [/#if] href="
        [@s.url action='partners' includeParams='get'][/@s.url]
      "><li>TL/RPL Milestone report</li>
    </a>
  </ul>
</nav>