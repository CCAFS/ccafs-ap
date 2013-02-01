[#ftl]
[#assign title = "Theme Leader / Regional Program Leader Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#-- assign customJS = ["", ""] / --]
[#-- assign customCSS = [""] / --]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "tlRpl" /]
[#assign currentStage = "TLRPLMilestoneReport" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]

[#-- number of theme in the legend of fieldset--]
[#assign theme = -1 /]
<section class="content">
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  [@s.form action="tlRplMilestones"]
    <article class="halfContent">
      <h1>[@s.text name="reporting.tlRplMilestoneReport.milestoneReport" /] - ${currentUser.leader.acronym}</h1>

        [#list milestoneReports as milestoneReport]
          [#-- Open a fieldset every time the theme code changes --]
          [#if milestoneReport.milestone.output.objective.theme.code?number != theme]
            [#assign theme =  milestoneReport.milestone.output.objective.theme.code?number /]  
            <fieldset>
              <legend>[@s.text name="reporting.tlRplMilestoneReport.theme" /] ${theme}</legend>
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
            [#if currentUser.TL]
              <div class="milestoneReportStatus">
                [@customForm.radioButtonGroup name="milestoneReports[${milestoneReport_index}].status.id" label="" i18nkey="reporting.tlRplMilestoneReport.milestoneStatus" listName="milestoneStatusList" keyFieldName="id" displayFieldName="name" value="${milestoneReport.status.id}" /]
              </div>  
            [/#if]
            
            [#-- TL/RPL Milestone description --]
            <div>
              [#if currentUser.RPL]
                [#-- Theme leader description field disabled --]
                [@customForm.textArea value="${milestoneReport.themeLeaderDescription}" name="milestoneReports[${milestoneReport_index}].themeLeaderDescription" i18nkey="reporting.tlRplMilestoneReport.TLdescription" disabled=true /]
                [@customForm.textArea value="${milestoneReport.regionalLeaderDescription}" name="milestoneReports[${milestoneReport_index}].regionalLeaderDescription" i18nkey="reporting.tlRplMilestoneReport.RPLdescription" required=true /]
              [#elseif currentUser.TL]
                [@customForm.textArea value="${milestoneReport.themeLeaderDescription}" name="milestoneReports[${milestoneReport_index}].themeLeaderDescription" i18nkey="reporting.tlRplMilestoneReport.TLdescription" required=true /]
                [#-- Regional program leader description field disabled --]
                [@customForm.textArea value="${milestoneReport.regionalLeaderDescription}" name="milestoneReports[${milestoneReport_index}].regionalLeaderDescription" i18nkey="reporting.tlRplMilestoneReport.RPLdescription" disabled=true /]
              [/#if]              
            </div>
            
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
      [@s.submit type="button" name="save"]SAVE[/@s.submit]
      
      [#include "/WEB-INF/reporting/tlRplSubMenu.ftl" /]  
    </article>
  [/@s.form]
 
  </section>
[#include "/WEB-INF/global/pages/footer.ftl" /]