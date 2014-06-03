[#ftl]
<nav id="stageMenu" class="tlRplSubMenu">
  <ul>
    <a [#if currentIndicatorsTheme == 1] class="currentReportingSection" [/#if] href="
        [@s.url action='themeOneOutcomeIndicators' includeParams='get'][/@s.url]
      "><li>[@s.text name="menu.reporting.outcomesInidcators.submenu.theme1" /]</li>
    </a>
    
    <a [#if currentIndicatorsTheme == 2] class="currentReportingSection" [/#if] href="
        [@s.url action='themeTwoOutcomeIndicators' includeParams='get'][/@s.url]
      "><li>[@s.text name="menu.reporting.outcomesInidcators.submenu.theme2" /]</li>
    </a>
    
    <a [#if currentIndicatorsTheme == 3] class="currentReportingSection" [/#if] href="
        [@s.url action='themeThreeOutcomeIndicators' includeParams='get'][/@s.url]
      "><li>[@s.text name="menu.reporting.outcomesInidcators.submenu.theme3" /]</li>
    </a>
    
    <a [#if currentIndicatorsTheme == 4] class="currentReportingSection" [/#if] href="
        [@s.url action='themeFourOutcomeIndicators' includeParams='get'][/@s.url]
      "><li>[@s.text name="menu.reporting.outcomesInidcators.submenu.theme4" /]</li>
    </a>
  </ul>
</nav>