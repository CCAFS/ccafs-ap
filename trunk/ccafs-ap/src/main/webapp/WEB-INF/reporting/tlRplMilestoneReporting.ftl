[#ftl]
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
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  [@s.form action="tlRplMilestones"]
    <article class="halfContent">
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
      
      <div class="buttons">
        [@s.submit type="button" name="save"]SAVE[/@s.submit]
        [@s.submit type="button" name="cancel"]CANCEL[/@s.submit]
      </div>
      
      [#include "/WEB-INF/reporting/tlRplSubMenu.ftl" /]  
    </article>
  [/@s.form]
 
  </section>
[#include "/WEB-INF/global/pages/footer.ftl" /]