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