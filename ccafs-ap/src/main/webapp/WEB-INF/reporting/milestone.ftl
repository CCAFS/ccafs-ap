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

[#assign title = "Milestone details" /]
[#assign customCSS = ["${baseUrl}/css/reporting/milestone.css"] /]

[#include "/WEB-INF/global/pages/popup-header.ftl" /]
  <section >
  
  <article class="content">
    <h1>[@s.text name="reporting.activityMilestone.theme" /] ${milestone.output.objective.theme.code}</h1>    
    <p>${milestone.output.objective.theme.description}</p>  
    <br /><br />
    <h6>[@s.text name="reporting.activityMilestone.objective" /] ${milestone.output.objective.code}</h6>
    <p>${milestone.output.objective.description}</p>
    <br /><br />
    <h6>[@s.text name="reporting.activityMilestone.output" /] ${milestone.output.code}</h6>
    <p>${milestone.output.description}</p>
    <br /><br />
    <h6>[@s.text name="reporting.activityMilestone.milestone" /] ${milestone.code}</h6>
    <p>${milestone.description}</p>
    
  </article>
  </section>
</body>