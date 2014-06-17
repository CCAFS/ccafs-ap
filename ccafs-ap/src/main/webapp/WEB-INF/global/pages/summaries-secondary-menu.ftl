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


<nav id="secondaryMenu">
  <ul>
    <a [#if currentSummariesSection == "activities"] class="currentReportingSection" [/#if] href="[@s.url action='activities' /]">
      <li>[@s.text name="menu.secondary.summaries.activities" /]</li>
    </a>
    <a [#if currentSummariesSection == "milestones"] class="currentReportingSection" [/#if] href="[@s.url action='milestones' /]">
      <li>[@s.text name="menu.secondary.summaries.milestones" /]</li>
    </a>
    <a [#if currentSummariesSection == "publications"] class="currentReportingSection" [/#if] href="[@s.url action='publications' /]">
      <li>[@s.text name="menu.secondary.summaries.publications" /]</li>
    </a>
    <a [#if currentSummariesSection == "caseStudies"] class="currentReportingSection" [/#if] href="[@s.url action='caseStudies' /]">
      <li>[@s.text name="menu.secondary.summaries.caseStudies" /]</li>
    </a>
    <a [#if currentSummariesSection == "outcomes"] class="currentReportingSection" [/#if] href="[@s.url action='outcomes' /]">
      <li>[@s.text name="menu.secondary.summaries.outcomes" /]</li>
    </a>
    <a [#if currentSummariesSection == "deliverables"] class="currentReportingSection" [/#if] href="[@s.url action='deliverables' /]">
      <li>[@s.text name="menu.secondary.summaries.deliverables" /]</li>
    </a>
  </ul>
</nav>