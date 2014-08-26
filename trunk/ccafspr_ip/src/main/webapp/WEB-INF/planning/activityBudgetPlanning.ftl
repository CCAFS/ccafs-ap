[#ftl]
[#assign title = "Activity Budget" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "activity" /]
[#assign currentStage = "activityBudget" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"activitys"},
  {"label":"activitys", "nameSpace":"planning", "action":"activitys"},
  {"label":"activities", "nameSpace":"planning/activities", "action":"activities" },
  {"label":"activityBudget", "nameSpace":"planning/activitys/activities", "action":"activityBudget" }
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
          [#-- Total CCAFS budget--]
          <div id="totalBudget" class="halfPartBlock">
            <h6>[@s.text name="planning.activityBudget.totalBudget" /]</h6>
            <p id="activityTotalCCAFS">US$ <span id="activityTotalCCAFSBudget">{totalActivityBudget?string(",##0.00")}</span></p>
            <input type="hidden" id="activityTotalBudget" value="{totalActivityBudget?c}" />
            <input type="hidden" id="yearTotalBudget" value="{totalActivityBudgetByYear?c}" />
          </div>
             
          [#-- Tertiary Menu - All years --] 
          <div id="budgetTables" class="ui-tabs ui-widget ui-widget-content ui-corner-all" style="display:none"> 
            <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
              [#list allYears as yearMenu]
                <li class="ui-state-default ui-corner-top [#if yearMenu=year ]ui-tabs-active ui-state-active ui-state-hover[/#if]">
                  <a href="[@s.url action='budget' includeParams='get'][@s.param name='{activityRequest}']{activity.id?c}[/@s.param][@s.param name='year']{yearMenu?c}[/@s.param][/@s.url]"> {yearMenu?c} </a>
                </li>
              [/#list]
            </ul>
            [@s.set var="counter" value="0"/] 
              <div id="partnerTables-${year?c}" class="partnerTable ui-tabs-panel ui-widget-content ui-corner-bottom clearfix"> 
                <table class="ccafsBudget fullPartBlock">
                  [#-- HEADERS --]
                  <tr id="" class="ccafsBudget-head row">
                    [#-- Partner Name --]
                    <td id="" class="grid_5"><h6 >[@s.text name="planning.activityBudget.partner" /]</h6></td> 
                    [#-- W1 title --]
                    <td id="" class="grid_1"><h6 title="[@s.text name="planning.activityBudget.w1.tooltip" /]">[@s.text name="planning.activityBudget.w1" /]</h6></td> 
                  </tr>               
                  [#if activity.leader?has_content]
                    <tr id="" class="row">
                      [#-- Partner Leader Name --]
                      <td id="" class="grid_5">${activity.leader.currentInstitution.name} <strong>([@s.text name="planning.activityBudget.partnerLead" /])</strong> </td> 
                      [#-- W1 --]
                      <td id="" class="budgetContent grid_1">
                        <input type="hidden" name="activity.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+activity.leader.currentInstitution.id?c+'-W1'].id?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].year" value="${year?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+activity.leader.currentInstitution.id?c+'-W1'].institution.id?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].type" value="W1" />
                        [@customForm.input name="activity.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+activity.leader.currentInstitution.id?c+'-W1'].amount?c}" /]
                        [@s.set var="counter" value="${counter+1}"/]
                      </td> 
                    </tr>
                  [/#if] 
                  [#list activityPartners as activityPartner ]
                    <tr id="partnerBudget-${activityPartner_index}" class="row">
                      [#-- Partner Name --]
                      <td id="" class="grid_5">${activityPartner.partner.name}</td> 
                      [#-- W1 --]                
                      <td id="" class="budgetContent grid_1">
                        <input type="hidden" name="activity.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+activityPartner.partner.id?c+'-W1'].id?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].year" value="${year?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+activityPartner.partner.id?c+'-W1'].institution.id?c}" />
                        <input type="hidden" name="activity.budgets[${counter}].type" value="W1" />
                        [@customForm.input name="activity.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+activityPartner.partner.id?c+'-W1'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </td>
                    </tr>
                  [/#list] 
                </table>  
                <div class="partnerListMsj">
                  [@s.text name="planning.activityBudget.partnerNotList" /]
                  <a href="[@s.url action='partners' includeParams='get'][@s.param name='activityID']${activity.id?c}[/@s.param][/@s.url]"> 
                    [@s.text name="planning.activityBudget.partnersLink" /] 
                  </a>
                </div>
                <hr>
             </div>
          </div> <!-- End budgetTables -->
  
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