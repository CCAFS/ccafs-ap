[#ftl]
[#assign title = "Activity Budget" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/activityBudget.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "activity" /]
[#assign currentStage = "activityBudget" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projects"},
  {"label":"projects", "nameSpace":"planning", "action":"projects"},
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
  </div>
  [#include "/WEB-INF/planning/activityPlanningSubMenu.ftl" /]
  [@s.form action="activityBudget" cssClass="pure-form"]  
    <article class="halfContent" id="activityBudget">
      [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantActivityPlanningAccessInterceptor--]
      [#if !saveable]
        <p class="readPrivileges">
          [@s.text name="saving.read.privileges"]
            [@s.param][@s.text name="planning.activityBudget.title"/][/@s.param]
          [/@s.text]
        </p>
      [/#if]
      <h1 class="contentTitle">
      [@s.text name="planning.activityBudget.title" /] 
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
                <li class="ui-state-default ui-corner-top [#if yearMenu=year ]ui-tabs-active ui-state-active ui-state-hover[/#if]">
                  <a href="[@s.url action='activityBudget' includeParams='get'][@s.param name='${activityRequest}']${activity.id?c}[/@s.param][@s.param name='year']${yearMenu?c}[/@s.param][/@s.url]"> ${yearMenu?c} </a>
                </li>
              [/#list]
            </ul>
            [@s.set var="counter" value="0"/] 
              <div id="partnerTables-${year?c}" class="partnerTable ui-tabs-panel ui-widget-content ui-corner-bottom clearfix"> 
                <table class="ccafsBudget fullPartBlock">
                  [#-- HEADERS --]
                  <tr id="" class="ccafsBudget-head row">
                    [#-- Partner Name --]
                    <td id="" class="grid_8"><h6 >[@s.text name="planning.activityBudget.partner" /]</h6></td> 
                    [#-- ACTIVITY title --]
                    <td id="" class="grid_1"><h6 title="[@s.text name="planning.activityBudget.amount.tooltip" /]">[@s.text name="planning.activityBudget.amount" /]</h6></td> 
                  </tr>  
                  [#if activity.leader?has_content]
                    <tr id="" class="row">
                      [#-- Partner Leader Name --]
                      <td id="" class="grid_8">${activity.leader.currentInstitution.name} <strong>([@s.text name="planning.activityBudget.partnerLead" /])</strong> </td> 
                      [#--Activity Type --]
                      <td id="" class="budgetContent grid_1">
                        <input type="hidden" name="activity.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+activity.leader.currentInstitution.id?c+'-ACTIVITY'].id?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].year" value="${year?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+activity.leader.currentInstitution.id?c+'-ACTIVITY'].institution.id?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].type" value="W1" />
                        [@customForm.input name="activity.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+activity.leader.currentInstitution.id?c+'-ACTIVITY'].amount?c}" /]
                        [@s.set var="counter" value="${counter+1}"/]
                      </td>  
                    </tr>
                  [/#if]  
                  [#list activityPartners as activityPartner ]
                    <tr id="partnerBudget-${activityPartner_index}" class="row">
                      [#-- Partner Name --]
                      <td id="" class="grid_8">${activityPartner.partner.name}</td> 
                      [#-- Activity Type --]                
                      <td id="" class="budgetContent grid_1">
                        <input type="hidden" name="activity.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+activityPartner.partner.id?c+'-ACTIVITY'].id?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].year" value="${year?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+activityPartner.partner.id?c+'-ACTIVITY'].institution.id?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].type" value="ACTIVITY" />
                        [@customForm.input name="activity.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+activityPartner.partner.id?c+'-ACTIVITY'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </td>
                    </tr>
                  [/#list] 
                </table>  
                <div class="partnerListMsj">
                  [@s.text name="planning.activityBudget.partnerNotList" /]
                  <a href="[@s.url action='activityPartners' includeParams='get'][@s.param name='activityID']${activity.id?c}[/@s.param][/@s.url]"> 
                    [@s.text name="planning.activityBudget.partnersLink" /] 
                  </a>
                </div>
                <hr>
             </div>
          </div> <!-- End budgetTables -->
      [#else]
            [#-- If activity leader is not defined --]
            <p>[@s.text name="planning.activityBudget.message.leaderUndefined" /]</p>
          [/#if]
        [#else]
          <p>[@s.text name="planning.activityBudget.message.invalidYear" /]</p>
        [/#if]
      [#else]
        [#-- If the activity has not an start date and/or end date defined --]
        <p>[@s.text name="planning.activityBudget.message.dateUndefined" /]</p>
      [/#if]
      [#if saveable]
        <input type="hidden" name="activityID" value="">
        <div class="buttons">
          [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
          [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
          [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
        </div>
      [/#if]
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]