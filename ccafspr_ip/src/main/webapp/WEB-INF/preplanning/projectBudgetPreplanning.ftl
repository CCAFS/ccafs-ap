[#ftl]
[#assign title = "Project Budget" /]
[#assign globalLibs = ["jquery", "noty","autoSave","chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/preplanning/projectBudget.js"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "projects" /]
[#assign currentStage = "budget" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="preplanning.projectBudget.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl" /]
  
  [@s.form action="budget" cssClass="pure-form"]
    <article class="halfContent" id="projectBudget">
    [#include "/WEB-INF/preplanning/projectPreplanningSubMenu.ftl" /]
    
    [#-- Title --]
    <h1 class="contentTitle">
    ${project.composedId} - [@s.text name="preplanning.projectBudget.title" /]  
    </h1>
    [#if allYears?has_content]
      [#if hasLeader]
        [#-- Total CCAFS budget--]
        <div id="totalBudget" class="halfPartBlock">
          <h6>[@s.text name="preplanning.projectBudget.totalBudget" /]</h6>
          <p>{project.totalBudget}</p>
        </div>
        [#-- Total overall project budget:--]
        <div id="totalBudget" class="halfPartBlock">
          <h6>[@s.text name="preplanning.projectBudget.totalOverallBudget" /]</h6>
          
          <p id="projectTotalBudget">{project.totalBudget}</p>
        </div>  
        [#-- Tertiary Menu - All years --] 
        <div id="budgetTables" class=""> 
          <ul>
            [#list allYears as year]
            <li><a href="#partnerTables-${year_index}"> ${year?c} </a></li>
            [/#list]  
          </ul>
          [@s.set var="counter" value="0"/]
          [#list allYears as year]
            <div id="partnerTables-${year_index}" class="partnerTable"> 
              <table class="fullPartBlock">
                [#-- HEADERS --]
                <tr id="" class="row">
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
                  <td id="" class="grid_1">
                    <input type="hidden" name="project.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W1'].id}" />
                    <input type="hidden" name="project.budgets[${counter}].year" value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W1'].year?c}" />
                    <input type="hidden" name="project.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W1'].institution.id?c}" />
                    <input type="hidden" name="project.budgets[${counter}].type" value="W1" />
                    [@customForm.input name="project.budgets[${counter}].amount" showTitle=false value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W1'].amount?c}"/]
                    [@s.set var="counter" value="${counter+1}"/]
                  </td> 
                  [#-- W2 --]
                  <td id="" class="grid_1">
                    <input type="hidden" name="project.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W2'].id}" />
                    <input type="hidden" name="project.budgets[${counter}].year" value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W2'].year?c}" />
                    <input type="hidden" name="project.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W2'].institution.id?c}" />
                    <input type="hidden" name="project.budgets[${counter}].type" value="W2" />
                    [@customForm.input name="project.budgets[${counter}].amount" showTitle=false value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W2'].amount?c}" /]
                    [@s.set var="counter" value="${counter+1}"/] 
                  </td>
                  [#-- W3  --] 
                  <td id="" class="grid_1">
                    <input type="hidden" name="project.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W3'].id}" />
                    <input type="hidden" name="project.budgets[${counter}].year" value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W3'].year?c}" />
                    <input type="hidden" name="project.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W3'].institution.id?c}" />
                    <input type="hidden" name="project.budgets[${counter}].type" value="W3" />
                    [@customForm.input name="project.budgets[${counter}].amount" showTitle=false value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W3'].amount?c}" /]
                    [@s.set var="counter" value="${counter+1}"/]
                  </td>
                  [#-- Bilateral  --] 
                  <td id="" class="grid_1">
                    <input type="hidden" name="project.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-BILATERAL'].id}" />
                    <input type="hidden" name="project.budgets[${counter}].year" value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-BILATERAL'].year?c}" />
                    <input type="hidden" name="project.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-BILATERAL'].institution.id?c}" />
                    <input type="hidden" name="project.budgets[${counter}].type" value="BILATERAL" />
                    [@customForm.input name="project.budgets[${counter}].amount" showTitle=false value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-BILATERAL'].amount?c}" /]
                    [@s.set var="counter" value="${counter+1}"/]
                  </td> 
                </tr>
              [/#if] 
             [#list projectPartners as projectPartner ]              
              <tr id="partnerBudget-${projectPartner_index}" class="row">              
                [#-- Partner Name --]
                <td id="" class="grid_5">${projectPartner.partner.name}</td> 
                [#-- W1 --]                
                <td id="" class="grid_1">
                  <input type="hidden" name="project.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W1'].id}" />
                  <input type="hidden" name="project.budgets[${counter}].year" value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W1'].year?c}" />
                  <input type="hidden" name="project.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W1'].institution.id?c}" />
                  <input type="hidden" name="project.budgets[${counter}].type" value="W1" />
                  [@customForm.input name="project.budgets[${counter}].amount" showTitle=false value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W1'].amount?c}"/]
                  [@s.set var="counter" value="${counter+1}"/]
                </td>
                [#-- W2 --] 
                <td id="" class="grid_1">
                  <input type="hidden" name="project.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W2'].id}" />
                  <input type="hidden" name="project.budgets[${counter}].year" value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W2'].year?c}" />
                  <input type="hidden" name="project.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W2'].institution.id?c}" />
                  <input type="hidden" name="project.budgets[${counter}].type" value="W2" />
                  [@customForm.input name="project.budgets[${counter}].amount" showTitle=false value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W2'].amount?c}"/]
                  [@s.set var="counter" value="${counter+1}"/]
                </td> 
                [#-- W3  --] 
                <td id="" class="grid_1">
                  <input type="hidden" name="project.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W3'].id}" />
                  <input type="hidden" name="project.budgets[${counter}].year" value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W3'].year?c}" />
                  <input type="hidden" name="project.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W3'].institution.id?c}" />
                  <input type="hidden" name="project.budgets[${counter}].type" value="W3" />
                  [@customForm.input name="project.budgets[${counter}].amount" showTitle=false value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W3'].amount?c}"/]
                  [@s.set var="counter" value="${counter+1}"/]
                </td> 
                [#-- Bilateral  --] 
                <td id="" class="grid_1">
                  <input type="hidden" name="project.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-BILATERAL'].id}" />
                  <input type="hidden" name="project.budgets[${counter}].year" value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-BILATERAL'].year?c}" />
                  <input type="hidden" name="project.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-BILATERAL'].institution.id?c}" />
                  <input type="hidden" name="project.budgets[${counter}].type" value="BILATERAL" />
                  [@customForm.input name="project.budgets[${counter}].amount" showTitle=false value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-BILATERAL'].amount?c}"/]
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
            <div id="leveraged" class="fullPartBlock">
            <h6>[@s.text name="preplanning.projectBudget.leveraged" /]</h6> 
                <div>
                  [#-- Partner Name --]
                  <div id="" class=""><h6>[@s.text name="preplanning.projectBudget.partner" /]</h6></div> 
                  [#-- Amount --]
                  <div id="" class="amount"></div>
                </div>
              [#list leveragedInstitutions as partner]
                <div id="leveragedPartner-${partner_index}" class="leveragedPartner row"> 
                  [#-- Partner Name --]
                  <div id="partnerName" class="name">${partner.name}</div> 
                  [#-- Amount --]
                  <div id="amount" class="amount">
                    <input type="hidden" name="project.budgets[${counter}].id" value="${mapBudgets[year?c+'-'+partner.id?c+'-LEVERAGED'].id}" />
                    <input type="hidden" name="project.budgets[${counter}].year" value="${mapBudgets[year?c+'-'+partner.id?c+'-LEVERAGED'].year?c}" />
                    <input type="hidden" name="project.budgets[${counter}].institution.id" value="${mapBudgets[year?c+'-'+partner.id?c+'-LEVERAGED'].institution.id?c}" />
                    <input type="hidden" name="project.budgets[${counter}].type" value="LEVERAGED" />
                    [@customForm.input name="project.budgets[${counter}].amount" showTitle=false value="${mapBudgets[year?c+'-'+partner.id?c+'-LEVERAGED'].amount?c}"/]
                    [@s.set var="counter" value="${counter+1}"/] 
                    <img class="removeButton" src="${baseUrl}/images/global/icon-remove.png" />
                  </div> 
                </div>  
              [/#list] 
              [#-- Add Leveraged --]
              <div class="fullBlock addLeveragedBlock">
                [@customForm.select name="leveragedList" value="" showTitle=false listName="allInstitutions" keyFieldName="id"  displayFieldName="composedName" addButton=true className="leveraged" /]
              </div>
            </div>
           </div>   
          [/#list]
        </div> <!-- End budgetTables -->
      [#else]
        [#-- If project leader is not defined --]
        <p>[@s.text name="preplanning.projectBudget.message.leaderUndefined" /]</p>
      [/#if]
    [#else]
      [#-- If the project has not an start date and/or end date defined --]
      <p>[@s.text name="preplanning.projectBudget.message.dateUndefined" /]</p>
    [/#if]
    <!-- internal parameter -->
    <input name="projectID" type="hidden" value="${project.id?c}" />
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
  </article>
  
  [/@s.form]  
  
  [#-- Partner Leveraged Template --] 
  <div id="leveragedPartnerTemplate" class="row" style="display:none"> 
    <div id="partnerName" class="name"> Partner Name </div> 
    <div id="amount" class="amount">
      <input type="hidden" id="id" name="todo" value="">
      <div class="input">
        <input type="text" id="amount" name="todo" value="">
      </div>
      <img class="removeButton" src="${baseUrl}/images/global/icon-remove.png">
    </div> 
  </div>
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]