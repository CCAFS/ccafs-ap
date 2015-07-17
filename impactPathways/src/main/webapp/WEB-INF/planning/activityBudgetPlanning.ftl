[#ftl]
[#assign title = "Activity Budget" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/activityBudget.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "activity" /]
[#assign currentStage = "activityBudget" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"project", "nameSpace":"planning/projects", "action":"description", "param":"projectID=${project.id}"},
  {"label":"activities", "nameSpace":"planning/projects", "action":"activities" ,"param":"projectID=${project.id}" },
  {"label":"activityBudget", "nameSpace":"planning/activities", "action":"activityBudget" }
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.activityBudget.help" /] </p>
    <div id="downloadGuidelineMessage">
      <a  href="${baseUrl}/resources/guidelines/FP_Guidelines_Budget_20141007_to Liaison.pptx">  
        <img class="icon" src="${baseUrl}/images/global/download-icon_636368.png" />
        <p>[@s.text name="preplanning.projectBudget.guideline.title" /]</p>
      </a>
    </div>
  </div>
  [#include "/WEB-INF/planning/activityPlanningSubMenu.ftl" /]
  [@s.form action="activityBudget" cssClass="pure-form"]  
    <article class="halfContent" id="activityBudget">
    [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
      [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantActivityPlanningAccessInterceptor--]
      [#if !saveable]
        <p class="readPrivileges">
          [@s.text name="saving.read.privileges"]
            [@s.param][@s.text name="planning.activityBudget.title"/][/@s.param]
          [/@s.text]
        </p>
      [/#if]
      <h1 class="contentTitle">
        [@s.text name="planning.activity" /]: ${activity.composedId} - [@s.text name="planning.activityBudget.title" /] 
      </h1> 
      [#if allYears?has_content]
        [#if invalidYear == false]
          [#if hasLeader]
            [#-- Total CCAFS budget--]
            <div id="totalBudget" class="halfPartBlock">
              <h6>[@s.text name="planning.activityBudget.totalBudget" /]</h6>
              <p id="activityTotal">US$ <span id="activityTotalBudget">${totalActivitiesBudget?string(",##0.00")}</span></p>
              <input type="hidden" id="activityTotalBudget" value="${totalActivitiesBudget?c}" />
              <input type="hidden" id="yearTotalBudget" value="${totalActivitiesBudgetByYear?c}" />
            </div> 
            [#-- Tertiary Menu - All years --] 
            <div id="budgetTables" class="ui-tabs ui-widget ui-widget-content ui-corner-all" style="display:none"> 
              <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
                [#list allYears as yearMenu]
                  <li id="yearTab-${yearMenu}" class="yearTab ui-state-default ui-corner-top [#if yearMenu=year ]ui-tabs-active ui-state-active ui-state-hover[/#if]">
                    <a href="[@s.url action='activityBudget' includeParams='get'][@s.param name='${activityRequest}']${activity.id?c}[/@s.param][@s.param name='year']${yearMenu?c}[/@s.param][/@s.url]"> ${yearMenu?c} </a>
                  </li>
                [/#list]
              </ul>
              [@s.set var="counter" value="0"/]
              <div id="partnerTables-${year?c}" class="partnerTable ui-tabs-panel ui-widget-content ui-corner-bottom clearfix">
                <div class="fieldset">
                  <h6>[@s.text name="planning.activityBudget.totalBudgetPerYear"][@s.param name="0"]${year}[/@s.param][/@s.text]</h6>
                  <p id="activityTotalPerYear">US$ <span id="activityTotalBudgetPerYear">${totalActivitiesBudgetByYear?string(",##0.00")}</span></p>
                </div>
                <table class="ccafsBudget fullPartBlock">
                  [#-- HEADERS --]
                  <tr class="ccafsBudget-head row">
                    [#-- Partner Name --]
                    <td class="grid_5">
                      <h6>[@s.text name="planning.activityBudget.partner" /]</h6>
                    </td> 
                    [#-- W1 W2 Budget title --]
                    <td id="" class="grid_2">
                      <h6>[@s.text name="planning.activityBudget.W1W2Budget" /]</h6>
                    </td> 
                    [#-- W3/Bilateral Budget title --]
                    <td id="" class="grid_2">
                      <h6>[@s.text name="planning.activityBudget.W3Bilateral" /]</h6>
                    </td> 
                  </tr>  
                  [#if activity.leader?has_content]
                    <tr class="row">
                      [#-- Partner Leader Name --]
                      <td class="grid_5">${activity.leader.currentInstitution.composedName} <strong>([@s.text name="planning.activityBudget.partnerLead" /])</strong> </td> 
                      [#--W1W2 Type --]
                      <td class="budgetContent grid_2">
                        <input type="hidden" name="activity.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+activity.leader.currentInstitution.id?c+'-ACTIVITY_W1_W2'].id?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].year" value="${year?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+activity.leader.currentInstitution.id?c+'-ACTIVITY_W1_W2'].institution.id?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].type" value="ACTIVITY_W1_W2" />
                        [@customForm.input name="activity.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+activity.leader.currentInstitution.id?c+'-ACTIVITY_W1_W2'].amount?c}" /]
                        [@s.set var="counter" value="${counter+1}"/]
                      </td>  
                      [#--W3Bilateral Type --]
                      <td class="budgetContent grid_2">
                        <input type="hidden" name="activity.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+activity.leader.currentInstitution.id?c+'-ACTIVITY_W3_BILATERAL'].id?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].year" value="${year?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+activity.leader.currentInstitution.id?c+'-ACTIVITY_W3_BILATERAL'].institution.id?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].type" value="ACTIVITY_W3_BILATERAL" />
                        [@customForm.input name="activity.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+activity.leader.currentInstitution.id?c+'-ACTIVITY_W3_BILATERAL'].amount?c}" /]
                        [@s.set var="counter" value="${counter+1}"/]
                      </td>
                    </tr>
                  [/#if]  
                  [#list activityPartners as activityPartner ]
                    <tr id="partnerBudget-${activityPartner_index}" class="row">
                      [#-- Partner Name --]
                      <td id="" class="grid_5">${activityPartner.partner.composedName}</td>
                      [#-- Activity Type --]
                      <td id="" class="budgetContent grid_2">
                        <input type="hidden" name="activity.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+activityPartner.partner.id?c+'-ACTIVITY_W1_W2'].id?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].year" value="${year?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+activityPartner.partner.id?c+'-ACTIVITY_W1_W2'].institution.id?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].type" value="ACTIVITY_W1_W2" />
                        [@customForm.input name="activity.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+activityPartner.partner.id?c+'-ACTIVITY_W1_W2'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </td>
                      [#-- Activity Type --]
                      <td id="" class="budgetContent grid_2">
                        <input type="hidden" name="activity.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+activityPartner.partner.id?c+'-ACTIVITY_W3_BILATERAL'].id?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].year" value="${year?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+activityPartner.partner.id?c+'-ACTIVITY_W3_BILATERAL'].institution.id?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].type" value="ACTIVITY_W3_BILATERAL" />
                        [@customForm.input name="activity.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+activityPartner.partner.id?c+'-ACTIVITY_W3_BILATERAL'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </td>
                    </tr>
                  [/#list]
                </table>
                <hr>
                [#if saveable]
                  <div class="partnerListMsj">
                    [@s.text name="planning.activityBudget.partnerNotList" /]
                    <a href="[@s.url action='activityPartners' includeParams='get'][@s.param name='activityID']${activity.id?c}[/@s.param][/@s.url]"> 
                      [@s.text name="planning.activityBudget.partnersLink" /]
                    </a>
                  </div>
                [/#if]
              </div> <!-- End partnerTables-${year?c} -->
            </div> <!-- End budgetTables -->
          [#else] [#-- Else - hasLeader --]
            [#-- If activity leader is not defined --]
            <p>[@s.text name="planning.activityBudget.message.leaderUndefined" /]</p>
          [/#if]
        [#else] [#-- Else - invalidYear --]
          <p>[@s.text name="planning.activityBudget.message.invalidYear" /]</p>
        [/#if]
        
        [#if saveable]
          <input type="hidden" name="activityID" value="${activity.id?c}">
          <input type="hidden" name="year" value="${year?c}">
          <input type="hidden" id="targetYear" name="targetYear" value="${year?c}" />
          <div class="buttons">
            [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
            [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
            [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
          </div>
        [/#if]
        
      [#else] [#-- Else - allYears has content --]
        [#-- If the activity has not an start date and/or end date defined --]
        <p>[@s.text name="planning.activityBudget.message.dateUndefined" /]</p>
      [/#if]
    </article>
  [/@s.form]
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]