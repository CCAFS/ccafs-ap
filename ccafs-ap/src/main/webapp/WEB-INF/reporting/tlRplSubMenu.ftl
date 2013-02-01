[#ftl]
<nav id="stageMenu" class="tlRplSubMenu">
  <ul>
    <a [#if currentStage == "tlOutputSummary"] class="currentReportingSection" [/#if] href="
        [@s.url action='tlOutputs' includeParams='get'][/@s.url]
      "><li>TL Summary by output</li>
    </a>
    <a [#if currentStage == "rplSynthesisReport"] class="currentReportingSection" [/#if] href="
        [@s.url action='rplSynthesis' includeParams='get'][/@s.url]
      "><li>RPL synthesis report</li>
    </a>
    <a [#if currentStage == "TLRPLMilestoneReport"] class="currentReportingSection" [/#if] href="
        [@s.url action='tlRplMilestones' includeParams='get'][/@s.url]
      "><li>TL/RPL Milestone report</li>
    </a>
  </ul>
</nav>