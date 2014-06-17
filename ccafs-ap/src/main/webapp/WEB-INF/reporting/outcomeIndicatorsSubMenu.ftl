[#ftl]
[#--
 
 * This file is part of CCAFS Planning and Reporting Platform.
 *
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 *
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see 
 * <http://www.gnu.org/licenses/>
  
--]

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