[#ftl]
[#assign title = "Project Budget" /]
[#assign globalLibs = ["jquery", "noty","autoSave","chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/preplanning/projectBudget.js"] /]
[#assign currentSection = "planning" /]
[#assign currentStage = "budget" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projects"},
  {"label":"projects", "nameSpace":"planning", "action":"projects"},
  {"label":"budget", "nameSpace":"planning/projects", "action":""}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="planning.projectBudget.help" /]</p>
    <div class="quote">
      <p><strong>[@s.text name="preplanning.projectBudget.w1" /]: </strong> [@s.text name="preplanning.projectBudget.w1.tooltip" /]</p>
      <p><strong>[@s.text name="preplanning.projectBudget.w2" /]: </strong> [@s.text name="preplanning.projectBudget.w2.tooltip" /]</p>
      <p><strong>[@s.text name="preplanning.projectBudget.w3" /]: </strong> [@s.text name="preplanning.projectBudget.w3.tooltip" /]</p>
      <p><strong>[@s.text name="preplanning.projectBudget.bilateral" /]: </strong> [@s.text name="preplanning.projectBudget.bilateral.tooltip" /]</p>
    </div>
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
  
  [@s.form action="budget" cssClass="pure-form"]
    <article class="halfContent" id="projectBudget">
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GranProjectAccessInterceptor--]
    [#if saveable && !fullEditable]
      <p class="readPrivileges">
        [@s.text name="planning.projectBudget.privileges" /]
      </p>
    [#elseif !saveable && fullEditable]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"]
          [@s.param][@s.text name="preplanning.project"/][/@s.param]
        [/@s.text]
      </p>
    [/#if]
    
    [#-- Project Title --]
    <h1 class="contentTitle">
      ${project.composedId} - [@s.text name="preplanning.projectBudget.title" /]  
    </h1>
    [#if allYears?has_content]
      [#if invalidYear == false]
        [#if hasLeader]
          [#-- Total CCAFS budget--]
          <div id="totalBudget" class="halfPartBlock">
            <h6>[@s.text name="preplanning.projectBudget.totalBudget" /]</h6>
            <p id="projectTotalCCAFS">US$ <span id="projectTotalCCAFSBudget">${totalCCAFSBudget?string(",##0.00")}</span></p>
            <input type="hidden" id="projectTotalCCAFSBudget" value="${totalCCAFSBudget?c}" />
            <input type="hidden" id="yearTotalCCAFSBudget" value="${totalCCAFSBudgetByYear?c}" />
          </div>
          [#-- Total overall project budget:--]
          <div id="totalBudget" class="halfPartBlock">
            <h6>[@s.text name="preplanning.projectBudget.totalOverallBudget" /]</h6>
            <p id="projectTotal">US$ <span id="projectTotalBudget">${totalBudget?string(",##0.00")}</span></p>
            <input type="hidden" id="projectTotalBudget" value="${totalBudget?c}" />
            <input type="hidden" id="yearTotalBudget" value="${totalBudgetByYear?c}" />
          </div>     
          [#-- Tertiary Menu - All years --] 
          <div id="budgetTables" class="ui-tabs ui-widget ui-widget-content ui-corner-all" style="display:none"> 
            <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
              [#list allYears as yearMenu]
                <li class="ui-state-default ui-corner-top [#if yearMenu=year ]ui-tabs-active ui-state-active ui-state-hover[/#if]">
                  <a href="[@s.url action='budget' includeParams='get'][@s.param name='${projectRequest}']${project.id?c}[/@s.param][@s.param name='year']${yearMenu?c}[/@s.param][/@s.url]"> ${yearMenu?c} </a>
                </li>
              [/#list]
            </ul>
            [@s.set var="counter" value="0"/] 
              <div id="partnerTables-${year?c}" class="partnerTable ui-tabs-panel ui-widget-content ui-corner-bottom clearfix"> 
                <table class="ccafsBudget fullPartBlock">
                  [#-- HEADERS --]
                  <tr id="" class="ccafsBudget-head row">
                    [#-- Partner Name --]
                    <td id="" class="grid_5"><h6 >[@s.text name="preplanning.projectBudget.partner" /]</h6></td> 
                    [#-- W1 title --]
                    <td id="" class="grid_1"><h6 title="[@s.text name="preplanning.projectBudget.w1.tooltip" /]">[@s.text name="preplanning.projectBudget.w1" /]</h6></td> 
                    [#-- W2 title --] 
                    <td id="" class="grid_1"><h6 title="[@s.text name="preplanning.projectBudget.w2.tooltip" /]">[@s.text name="preplanning.projectBudget.w2" /]</h6></td> 
                    [#-- W3 title --] 
                    <td id="" class="grid_1"><h6 title="[@s.text name="preplanning.projectBudget.w3.tooltip" /]">[@s.text name="preplanning.projectBudget.w3" /]</h6></td> 
                    [#-- Bilateral title --] 
                    <td id="" class="grid_1"><h6 title="[@s.text name="preplanning.projectBudget.bilateral.tooltip" /]">[@s.text name="preplanning.projectBudget.bilateral" /]</h6></td> 
                  </tr>               
                  [#if project.leader?has_content]
                    <tr id="" class="row">
                      [#-- Partner Leader Name --]
                      <td id="" class="grid_5">${project.leader.currentInstitution.name} <strong>([@s.text name="preplanning.projectBudget.partnerLead" /])</strong> </td> 
                      [#-- W1 --]
                      <td id="" class="budgetContent grid_1">
                        <input type="hidden" name="project.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W1'].id?c}" />
                        <input type="hidden" name="project.budgets[${counter}].year" value="${year?c}" />
                        <input type="hidden" name="project.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W1'].institution.id?c}" />
                        <input type="hidden" name="project.budgets[${counter}].type" value="W1" />
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W1'].amount?c}" /]
                        [@s.set var="counter" value="${counter+1}"/]
                      </td> 
                      [#-- W2 --]
                      <td id="" class="budgetContent grid_1">
                        <input type="hidden" name="project.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W2'].id?c}" />
                        <input type="hidden" name="project.budgets[${counter}].year" value="${year?c}" />
                        <input type="hidden" name="project.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W2'].institution.id?c}" />
                        <input type="hidden" name="project.budgets[${counter}].type" value="W2" />
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W2'].amount?c}" /]
                        [@s.set var="counter" value="${counter+1}"/] 
                      </td>
                      [#-- W3  --] 
                      <td id="" class="budgetContent grid_1">
                        <input type="hidden" name="project.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W3'].id?c}" />
                        <input type="hidden" name="project.budgets[${counter}].year" value="${year?c}" />
                        <input type="hidden" name="project.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W3'].institution.id?c}" />
                        <input type="hidden" name="project.budgets[${counter}].type" value="W3" />
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W3'].amount?c}" /]
                        [@s.set var="counter" value="${counter+1}"/]
                      </td>
                      [#-- Bilateral  --] 
                      <td id="" class="budgetContent grid_1">
                        <input type="hidden" name="project.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-BILATERAL'].id?c}" />
                        <input type="hidden" name="project.budgets[${counter}].year" value="${year?c}" />
                        <input type="hidden" name="project.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-BILATERAL'].institution.id?c}" />
                        <input type="hidden" name="project.budgets[${counter}].type" value="BILATERAL" />
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-BILATERAL'].amount?c}" /]
                        [@s.set var="counter" value="${counter+1}"/]
                      </td> 
                    </tr>
                  [/#if] 
                  [#list projectPartners as projectPartner ]
                    <tr id="partnerBudget-${projectPartner_index}" class="row">
                      [#-- Partner Name --]
                      <td id="" class="grid_5">${projectPartner.partner.name}</td> 
                      [#-- W1 --]                
                      <td id="" class="budgetContent grid_1">
                        <input type="hidden" name="project.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W1'].id?c}" />
                        <input type="hidden" name="project.budgets[${counter}].year" value="${year?c}" />
                        <input type="hidden" name="project.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W1'].institution.id?c}" />
                        <input type="hidden" name="project.budgets[${counter}].type" value="W1" />
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W1'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </td>
                      [#-- W2 --] 
                      <td id="" class="budgetContent grid_1">
                        <input type="hidden" name="project.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W2'].id?c}" />
                        <input type="hidden" name="project.budgets[${counter}].year" value="${year?c}" />
                        <input type="hidden" name="project.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W2'].institution.id?c}" />
                        <input type="hidden" name="project.budgets[${counter}].type" value="W2" />
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W2'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </td> 
                      [#-- W3  --] 
                      <td id="" class="budgetContent grid_1">
                        <input type="hidden" name="project.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W3'].id?c}" />
                        <input type="hidden" name="project.budgets[${counter}].year" value="${year?c}" />
                        <input type="hidden" name="project.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W3'].institution.id?c}" />
                        <input type="hidden" name="project.budgets[${counter}].type" value="W3" />
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W3'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </td> 
                      [#-- Bilateral  --] 
                      <td id="" class="budgetContent grid_1">
                        <input type="hidden" name="project.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-BILATERAL'].id?c}" />
                        <input type="hidden" name="project.budgets[${counter}].year" value="${year?c}" />
                        <input type="hidden" name="project.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-BILATERAL'].institution.id?c}" />
                        <input type="hidden" name="project.budgets[${counter}].type" value="BILATERAL" />
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-BILATERAL'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </td> 
                    </tr>
                  [/#list] 
                </table>  
                <div class="partnerListMsj">
                  [@s.text name="preplanning.projectBudget.partnerNotList" /]
                  <a href="[@s.url action='partners' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]"> 
                    [@s.text name="preplanning.projectBudget.partnersLink" /] 
                  </a>
                </div>
                <hr>
                [#-- Leveraged --]
                <div id="leveraged" class="grid_7">
                  <h6>[@s.text name="preplanning.projectBudget.leveraged" /]</h6>
                  [#if leveragedInstitutions?has_content]
                    [#list leveragedInstitutions as partner]
                      <div id="leveragedPartner-${partner_index}" class="leveragedPartner budgetContent row"> 
                        [#-- Partner Name --]
                        <div id="partnerName" class="name">${partner.name}</div> 
                        [#-- Amount --]
                        <div id="amount" class="amount">
                          <input type="hidden" name="project.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+partner.id?c+'-LEVERAGED'].id?c}" />
                          <input type="hidden" name="project.budgets[${counter}].year" value="${year?c}" />
                          <input type="hidden" name="project.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+partner.id?c+'-LEVERAGED'].institution.id?c}" id="${counter}-institution.id" />
                          <input type="hidden" name="project.budgets[${counter}].type" value="LEVERAGED" />
                          [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+partner.id?c+'-LEVERAGED'].amount?c}"/]
                          [@s.set var="counter" value="${counter+1}"/]
                          [#if fullEditable && saveable]
                            <img class="removeButton" src="${baseUrl}/images/global/icon-remove.png" />
                          [/#if]
                        </div> 
                      </div>  
                    [/#list] 
                  [#else]
                    [#if fullEditable && saveable]
                      <p id="selectLeveraged" class="center">[@s.text name="preplanning.projectBudget.selectLeveraged" /]</p>
                    [#else]
                      <p id="selectLeveraged" class="center">[@s.text name="planning.projectBudget.notLeveraged" /]</p>
                    [/#if]
                  [/#if]
                  [#if fullEditable && saveable]
                    [#-- Add Leveraged --]
                    <div class="fullBlock addLeveragedBlock"> 
                      [@customForm.select name="" value="" listName="allInstitutions" keyFieldName="id"  displayFieldName="composedName" className="leveraged" /]
                    </div>
                  [/#if]
                </div>
             </div>
          </div> <!-- End budgetTables -->
        [#else]
          [#-- If project leader is not defined --]
          <p>[@s.text name="preplanning.projectBudget.message.leaderUndefined" /]</p>
        [/#if]
      [#else]
        <p>[@s.text name="preplanning.projectBudget.message.invalidYear" /]</p>
      [/#if]
    [#else]
      [#-- If the project has not an start date and/or end date defined --]
      <p>[@s.text name="preplanning.projectBudget.message.dateUndefined" /]</p>
    [/#if]
    [#-- Showing buttons only to users with enough privileges. See GranProjectAccessInterceptor--]
    
    [#if fullEditable && saveable]
      [#if allYears?has_content && !invalidYear && hasLeader]
        <!-- internal parameter -->
        <input name="projectID" type="hidden" value="${project.id?c}" />
        <input name="year" type="hidden" value="${year?c}" />
        <div class="buttons">
          [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
          [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
          [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
        </div>
      [/#if]
    [/#if]
  </article>
  
  [/@s.form]  
  
  [#-- Partner Leveraged Template --] 
  <div id="leveragedPartnerTemplate" class="row" style="display:none"> 
    <div id="partnerName" class="name"> Partner Name </div> 
    <div id="amount" class="amount">
      <input type="hidden" name="].id" value="-1" />
      <input type="hidden" name="year" value="${year?c}" />
      <input type="hidden" name="institution.id" value="-1" />
      <input type="hidden" name="type" value="LEVERAGED" />
      <div class="input">
        <input type="text" name="amount" />
      </div>
      <img class="removeButton" src="${baseUrl}/images/global/icon-remove.png">
    </div> 
  </div>
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]