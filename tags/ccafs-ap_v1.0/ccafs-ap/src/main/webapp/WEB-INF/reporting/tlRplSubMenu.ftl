[#ftl]
<nav id="stageMenu" class="tlRplSubMenu">
  <ul>
    [#if userRole == "TL" || userRole == "Admin"]
      <a [#if currentStage == "tlOutputSummary"] class="currentReportingSection" [/#if] href="
          [@s.url action='tlOutputs' includeParams='get'][/@s.url]
        "><li>[@s.text name="menu.reporting.submenu.outputSummary" /]</li>
      </a>
    [/#if]
    
    [#if userRole == "RPL" || userRole == "Admin"]
      <a [#if currentStage == "rplSynthesisReport"] class="currentReportingSection" [/#if] href="
          [@s.url action='rplSynthesis' includeParams='get'][/@s.url]
        "><li>[@s.text name="menu.reporting.submenu.rplSynthesisreport" /]</li>
      </a>
    [/#if]
    
    <a [#if currentStage == "TLRPLMilestoneReport"] class="currentReportingSection" [/#if] href="
        [@s.url action='tlRplMilestones' includeParams='get'][/@s.url]
      "><li>[@s.text name="menu.reporting.submenu.tlRplMilestoneReport" /]</li>
    </a>
  </ul>
</nav>