[#ftl]
<nav id="stageMenu" class="clearfix">
  <ul>
    <a [#if currentStage == "description"] class="currentReportingSection" [/#if] href="
        [@s.url action='description' includeParams='get'][/@s.url]
      "><li>[@s.text name="menu.preplanning.submenu.description" /]</li>
    </a>
    <a [#if currentStage == "partners"] class="currentReportingSection" [/#if] href="
        [@s.url action='partners' includeParams='get'][/@s.url]
      "><li>[@s.text name="menu.preplanning.submenu.partners" /]</li>
    </a>
    <a [#if currentStage == "budget"] class="currentReportingSection" [/#if] href="
        [@s.url action='budget' includeParams='get'][/@s.url]
      "><li>[@s.text name="menu.preplanning.submenu.budget" /]</li>
    </a>
  </ul>
</nav>