[#ftl]
<nav id="stageMenu">
  <ul>
    <a [#if currentStage == "outcomes"] class="currentReportingSection" [/#if] href="
        [@s.url action='outcomes' includeParams='get'][/@s.url]
      "><li>[@s.text name="menu.preplanning.submenu.outcomes" /]</li>
    </a>
    <a [#if currentStage == "midOutcomes"] class="currentReportingSection" [/#if] href="
        [@s.url action='midOutcomes' includeParams='get'][/@s.url]
      "><li>[@s.text name="menu.preplanning.submenu.midOutcomes" /]</li>
    </a>
    <a [#if currentStage == "outputs"] class="currentReportingSection" [/#if] href="
        [@s.url action='outputs' includeParams='get'][/@s.url]
      "><li>[@s.text name="menu.preplanning.submenu.outputs" /]</li>
    </a>
    <a [#if currentStage == "actions"] class="currentReportingSection" [/#if] href="
        [@s.url action='actions' includeParams='get'][/@s.url]
      "><li>[@s.text name="menu.preplanning.submenu.actions" /]</li>
    </a>
  </ul>
</nav>