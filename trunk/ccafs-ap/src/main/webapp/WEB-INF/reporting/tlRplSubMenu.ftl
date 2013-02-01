[#ftl]
<nav id="stageMenu" class="tlRplSubMenu">
  <ul>
    [#if userRole == "TL" || userRole == "Admin"]
      <a [#if currentStage == "tlOutputSummary"] class="currentReportingSection" [/#if] href="
          [@s.url action='tlOutputs' includeParams='get'][/@s.url]
        "><li>TL Summary by output</li>
      </a>
    [/#if]
    
    [#if userRole == "RPL" || userRole == "Admin"]
      <a [#if currentStage == "rplSynthesisReport"] class="currentReportingSection" [/#if] href="
          [@s.url action='rplSynthesis' includeParams='get'][/@s.url]
        "><li>RPL synthesis report</li>
      </a>
    [/#if]
    
    <a [#if currentStage == "TLRPLMilestoneReport"] class="currentReportingSection" [/#if] href="
        [@s.url action='tlRplMilestones' includeParams='get'][/@s.url]
      "><li>TL/RPL Milestone report</li>
    </a>
  </ul>
</nav>