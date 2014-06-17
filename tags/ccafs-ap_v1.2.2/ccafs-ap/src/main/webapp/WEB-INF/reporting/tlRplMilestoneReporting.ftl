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

[#assign title = "Theme Leader / Regional Program Leader Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#-- assign customJS = ["", ""] / --]
[#-- assign customCSS = [""] / --]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "tlRpl" /]
[#assign currentStage = "TLRPLMilestoneReport" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]

[#-- number of theme in the legend of fieldset--]
[#assign theme = -1 /]
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="reporting.tlRplMilestoneReport.help" /]</p>
  </div>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  [@s.form action="tlRplMilestones"]
    <article class="halfContent">
      [#include "/WEB-INF/reporting/tlRplSubMenu.ftl" /]
      <h1 class="contentTitle">
        [@s.text name="reporting.tlRplMilestoneReport.milestoneReport" /] - ${currentUser.leader.acronym}
      </h1>

      <div id="items">
        [#list milestoneReports as milestoneReport]
          [#-- Open a fieldset every time the theme code changes --]
          [#if milestoneReport.milestone.output.objective.theme.code?number != theme]
            [#assign theme =  milestoneReport.milestone.output.objective.theme.code?number /]  
            <fieldset class="milestoneReportGroup">
              <legend>[@s.text name="reporting.tlRplMilestoneReport.milestonesTheme" /] ${theme}</legend>
          [/#if]
          
          <div class="milestoneReport-${milestoneReport_index}">
            [#-- Milestone report id and milestone id --]
            <input type="hidden" name="milestoneReports[${milestoneReport_index}].id" value="${milestoneReport.id}"/>
            <input type="hidden" name="milestoneReports[${milestoneReport_index}].milestone.id" value="${milestoneReport.milestone.id}" />
            
            [#-- Milestone title --]
            <div class="milestoneReportTitle">
              [@s.text name="reporting.tlRplMilestoneReport.milestone" /] ${milestoneReport.milestone.code}
            </div>
            
            [#-- Milestone description --]
            <div class="milestoneReportDescription">
              <p>
                ${milestoneReport.milestone.description}
              </p>
            </div>
          
            [#-- Milestone status --]
            [#-- Only the TL can set the milestone status --]
            [#if currentUser.TL || currentUser.admin]
              <div class="milestoneReportStatus">
                [@customForm.radioButtonGroup name="milestoneReports[${milestoneReport_index}].status.id" label="" i18nkey="reporting.tlRplMilestoneReport.milestoneStatus" listName="milestoneStatusList" keyFieldName="id" displayFieldName="name" value="${milestoneReport.status.id}" /]
              </div>           
            [/#if]
            
            [#-- TL/RPL Milestone description --]
            [#if currentUser.RPL || currentUser.admin]
              [#-- Theme leader description field disabled --]
              <div class="tlDescription disabled">
                [@customForm.textArea value="${milestoneReport.themeLeaderDescription}" name="milestoneReports[${milestoneReport_index}].themeLeaderDescription" i18nkey="reporting.tlRplMilestoneReport.TLdescription" disabled=true /]
              </div>
              <div class="rplDescription">
                [@customForm.textArea value="${milestoneReport.regionalLeaderDescription}" name="milestoneReports[${milestoneReport_index}].regionalLeaderDescription" i18nkey="reporting.tlRplMilestoneReport.RPLdescription" required=true /]
              </div>
            [#elseif currentUser.TL || currentUser.admin]
              <div class="tlDescription">
                [@customForm.textArea value="${milestoneReport.themeLeaderDescription}" name="milestoneReports[${milestoneReport_index}].themeLeaderDescription" i18nkey="reporting.tlRplMilestoneReport.TLdescription" required=true /]
              </div>
              [#-- Regional program leader description field disabled --]
              <div class="rplDescription disabled">
                [@customForm.textArea value="${milestoneReport.regionalLeaderDescription}" name="milestoneReports[${milestoneReport_index}].regionalLeaderDescription" i18nkey="reporting.tlRplMilestoneReport.RPLdescription" disabled=true /]
              </div>
            [/#if]              
            
            
            [#-- Separator --]
            <hr />
          </div>
          
          [#-- Close the fieldset if the item is the last or if the next item has different theme code --]
          [#if !milestoneReport_has_next]
            </fieldset>
          [#else]
            [#if milestoneReports[milestoneReport_index + 1].milestone.output.objective.theme.code?number != theme]
              </fieldset>
            [/#if]
          [/#if]
        [/#list]
      </div>
      
      [#if canSubmit]
        <div class="buttons">
          [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
          [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
        </div>
      [/#if]

    </article>
  [/@s.form]
 
  </section>
[#include "/WEB-INF/global/pages/footer.ftl" /]