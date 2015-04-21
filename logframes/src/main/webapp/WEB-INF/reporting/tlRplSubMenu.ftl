[#ftl]
<nav id="stageMenu" class="tlRplSubMenu">
  <ul>
    [#if userRole == "TL" || userRole == "Admin"]
      <li [#if currentStage == "tlOutputSummary"] class="currentReportingSection" [/#if]><a href="
          [@s.url action='tlOutputs' includeParams='get'][/@s.url]
        ">[@s.text name="menu.reporting.submenu.outputSummary" /]
      </a></li>
    [/#if]
    
    [#if userRole == "TL" || userRole == "Admin"]
      <li [#if currentStage == "tlOverallDeliverables"] class="currentReportingSection" [/#if]><a href="
          [@s.url action='overallDeliverables' includeParams='get'][/@s.url]
        ">[@s.text name="menu.reporting.submenu.overallDeliverables" /]
      </a></li>
    [/#if]
    
    [#if userRole == "RPL" || userRole == "Admin"]
      <li [#if currentStage == "rplSynthesisReport"] class="currentReportingSection" [/#if] ><a href="
          [@s.url action='rplSynthesis' includeParams='get'][/@s.url]
        ">[@s.text name="menu.reporting.submenu.rplSynthesisreport" /]
      </a></li>
    [/#if]
    
    <li [#if currentStage == "TLRPLMilestoneReport"] class="currentReportingSection" [/#if]><a href="
        [@s.url action='tlRplMilestones' includeParams='get'][/@s.url]
      ">[@s.text name="menu.reporting.submenu.tlRplMilestoneReport" /]
    </a></li>
  </ul>
  <div class="clearfix"></div>
</nav>