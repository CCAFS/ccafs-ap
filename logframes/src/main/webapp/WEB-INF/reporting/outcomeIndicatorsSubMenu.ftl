[#ftl]
<nav id="stageMenu" class="tlRplSubMenu">
  <ul>
    <li [#if currentIndicatorsTheme == 1] class="currentReportingSection" [/#if]><a href="
        [@s.url action='themeOneOutcomeIndicators' includeParams='get'][/@s.url]
      ">[@s.text name="menu.reporting.outcomesInidcators.submenu.theme1" /]
    </a></li>
    
    <li [#if currentIndicatorsTheme == 2] class="currentReportingSection" [/#if]><a href="
        [@s.url action='themeTwoOutcomeIndicators' includeParams='get'][/@s.url]
      ">[@s.text name="menu.reporting.outcomesInidcators.submenu.theme2" /]
    </a></li>
    
    <li [#if currentIndicatorsTheme == 3] class="currentReportingSection" [/#if]><a href="
        [@s.url action='themeThreeOutcomeIndicators' includeParams='get'][/@s.url]
      ">[@s.text name="menu.reporting.outcomesInidcators.submenu.theme3" /]
    </a></li>
    
    <li [#if currentIndicatorsTheme == 4] class="currentReportingSection" [/#if]><a href="
        [@s.url action='themeFourOutcomeIndicators' includeParams='get'][/@s.url]
      ">[@s.text name="menu.reporting.outcomesInidcators.submenu.theme4" /]
    </a></li>
  </ul>
  <div class="clearFix"></div>
</nav>