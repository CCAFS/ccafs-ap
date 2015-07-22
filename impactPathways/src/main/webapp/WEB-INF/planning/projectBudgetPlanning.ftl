[#ftl]
[#assign title = "Project Budget" /]
[#assign globalLibs = ["jquery", "noty","autoSave","chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/preplanning/projectBudget.js"] /]
[#assign currentSection = "planning" /]
[#assign currentStage = "budget" /]
[#assign currentSubStage = "budgetByPartner" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"planning", "action":"projectsList"},
  {"label":"budget", "nameSpace":"planning/projects", "action":""}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" /> 
    <p> [@s.text name="planning.projectBudget.help1" /] 
    <a href="[@s.url namespace="/" action='glossary'][/@s.url]#budget">[@s.text name="planning.projectBudget.budget" /]</a> [@s.text name="planning.projectBudget.help2" /]
    <a href="[@s.url namespace="/" action='glossary'][/@s.url]#partners">[@s.text name="planning.projectBudget.partners" /]</a> [@s.text name="planning.projectBudget.help3" /] 
    <a href="[@s.url namespace="/" action='glossary'][/@s.url]#managementLiaison"> [@s.text name="planning.projectBudget.managementLiaison" /]</a> [@s.text name="planning.projectBudget.help4" /]</p>
    <div class="quote">
      <p><strong>[@s.text name="preplanning.projectBudget.w1" /]: </strong> [@s.text name="preplanning.projectBudget.w1.tooltip" /]</p>
      <p><strong>[@s.text name="preplanning.projectBudget.w2" /]: </strong> [@s.text name="preplanning.projectBudget.w2.tooltip" /]</p>
      <p><strong>[@s.text name="preplanning.projectBudget.w3" /]: </strong> [@s.text name="preplanning.projectBudget.w3.tooltip" /]</p>
      <p><strong>[@s.text name="preplanning.projectBudget.bilateral" /]: </strong> [@s.text name="preplanning.projectBudget.bilateral.tooltip" /]</p>
      <p><strong>[@s.text name="preplanning.projectBudget.gender" /]: </strong> [@s.text name="preplanning.projectBudget.gender.tooltip" /]</p>
      <p><strong>[@s.text name="preplanning.projectBudget.leveraged" /]: </strong> [@s.text name="preplanning.projectBudget.leveraged.tooltip" /]</p>
      <p><strong>[@s.text name="preplanning.projectBudget.partnership" /]: </strong> [@s.text name="preplanning.projectBudget.partnership.tooltip" /]</p>
    </div>
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
  
  [@s.form action="budget" cssClass="pure-form"]
    <article class="halfContent" id="projectBudget">
    [#include "/WEB-INF/planning/projectBudget-sub-menu.ftl" /]
    [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GranProjectAccessInterceptor--]
    [#if !canEdit]
      <p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]</p>
    [/#if] 
    [#-- Project Title --]
    <h1 class="contentTitle">${project.type} [@s.text name="preplanning.projectBudget.title" /]</h1> 
    [#if allYears?has_content]
      [#if project.leader?has_content]
        [#-- Accumulative total project budget --]
        <div class="thirdPartBlock">
          <h6 class="subTitle">[@s.text name="preplanning.projectBudget.totalBudget"][@s.param]${(!project.bilateralProject)?string(w1W2BudgetLabel, w3BilateralBudgetLabel)}[/@s.param][/@s.text]</h6>
          <p id="totalProjectBudget">
            [#assign totalProjectBudget]${((!project.bilateralProject)?string(totalCCAFSBudget!0, totalBilateralBudget!0))}[/#assign]
            US$ <span>${(totalProjectBudget?number)?string(",##0.00")}</span>
            <input type="hidden" value="${totalProjectBudget?number}" />
          </p>
        </div> 
        [#-- The co-founded projects are CCAFS core, then we should show the bilateral budget --]
        [#if project.coFundedProject]
          <div class="thirdPartBlock">
            <h6 class="subTitle">[@s.text name="preplanning.projectBudget.totalBudget"][@s.param]${w3BilateralBudgetLabel}[/@s.param][/@s.text]</h6>
            <p id="totalBilateralBudget">
              US$ <span>${(totalBilateralBudget!0)?string(",##0.00")}</span>
              <input type="hidden" value="${(totalBilateralBudget!0)?number}" />
            </p>
          </div> 
        [/#if]
        
        [#-- Project Overhead (Only for bilateral projects) --]
        [#if project.bilateralProject]
        <div class="simpleBox">
          <div class="budget clearfix">
            <h6>[@s.text name="planning.projectBudget.overhead" /]</h6> 
            <p>[@s.text name="planning.projectBudget.fullyInstitutionalCost" /]</p>
            <div class="radios">
              <input type="radio" name="isfullyInstitutionalCost" value="1" id="isfullyInstitutionalCost_1" /><label for="isfullyInstitutionalCost_1">[@s.text name="form.options.yes" /]</label>
              <input type="radio" name="isfullyInstitutionalCost" value="0" id="isfullyInstitutionalCost_0" checked/><label for="isfullyInstitutionalCost_0" >[@s.text name="form.options.no" /]</label>
            </div>
            <div class="overhead-block" style="display:none">
              <div class="halfPartBlock">
                [@customForm.input name="project.budgets.amount" i18nkey="planning.projectBudget.whatIsTheContracted" value="" editable=editable/]
              </div>
              <div class="halfPartBlock">
                <p>[@s.text name="planning.projectBudget.yourInstitutionalOverhead" /] <span></span></p>
              </div>
            </div>
          </div><!-- End budget -->
        </div>
        [/#if]
        
        <div id="budgetTables" class="ui-tabs ui-widget ui-widget-content ui-corner-all" style="display:none"> 
          [#-- Tertiary Menu - All years --] 
          <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
            [#list allYears as yearMenu]
              <li id="year-${yearMenu}" class="yearTab ui-state-default ui-corner-top [#if yearMenu == year ]ui-tabs-active ui-state-active ui-state-hover[/#if]">
                <a href="[@s.url action='budget' includeParams='get'][@s.param name='${projectRequest}']${project.id?c}[/@s.param][@s.param name='year']${yearMenu?c}[/@s.param][/@s.url]"> ${yearMenu?c} </a>
              </li>
            [/#list]
          </ul>
          [@s.set var="counter" value="0"/] 
            [#-- Project budget content by year --]
            <div id="partnerTables-${year?c}" class="partnerTable ui-tabs-panel ui-widget-content ui-corner-bottom clearfix"> 
              [#if (!editable && canEdit)]
                <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
              [/#if]
              <div class="fieldset clearfix">
                [#-- Accumulative total project budget By year --]
                <div class="BudgetByYear"> 
                  <h6 class="subTitle"> [@s.text name="preplanning.projectBudget.totalYearBudget"][@s.param name="0"]${(!project.bilateralProject)?string(w1W2BudgetLabel, w3BilateralBudgetLabel)}[/@s.param][@s.param name="1"]${year}[/@s.param][/@s.text]</h6> 
                  <p id="totalProjectBudgetByYear">
                    [#assign totalProjectBudgetByYear]${(!project.bilateralProject)?string((project.totalCcafsBudget)!0, project.totalBilateralBudget)!0}[/#assign]
                    US$ <span>${totalProjectBudgetByYear?number?string(",##0.00")}</span>
                    <input type="hidden" value="${totalProjectBudgetByYear?number}" />
                  </p>
                </div>
                [#-- The co-founded projects are CCAFS core, then we should show the bilateral budget --]
                [#if project.coFundedProject]
                <div class="BudgetByYear"> 
                  <h6 class="subTitle"> [@s.text name="preplanning.projectBudget.totalYearBudget"][@s.param name="0"]${w3BilateralBudgetLabel}[/@s.param][@s.param name="1"]${year}[/@s.param][/@s.text]</h6> 
                  <p id="totalBilateralBudgetByYear">
                    US$ <span>${project.totalBilateralBudget?string(",##0.00")}</span>
                    <input type="hidden" value="${project.totalBilateralBudget?number}" />
                  </p>
                </div>
                [/#if]
              </div> <!-- End Budget by year  -->
              <div class="ccafsBudget fullPartBlock clearfix">
                [@s.set var="counter" value="0"/]
                [#-- Project Leader --]
                [#if project.leader?has_content]
                  [@projectBudget institution=project.leader.institution budget=project.getBudget(project.leader.institution.id, project.bilateralProject?string(2, 1)?number, year )! project=project isPL=true cofinancing_budgets=project.getCofinancingBudgets()! editable=editable /]
                [/#if]
                [#-- Project Partners --]
                [#if projectPPAPartners?has_content] 
                  [#list projectPPAPartners as partnerInstitution ]
                    [@projectBudget institution=partnerInstitution budget=project.getBudget(partnerInstitution.id, project.bilateralProject?string(2, 1)?number, year )! project=project pp_index="${partnerInstitution_index+1}" editable=editable /]
                  [/#list]  
                [/#if]
              </div>
              <div class="partnerListMsj">
                [@s.text name="preplanning.projectBudget.partnerNotList" /]
                <a href="[@s.url action='partners' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]"> 
                  [@s.text name="preplanning.projectBudget.partnersLink" /] 
                </a>
              </div>
           </div>
        </div> <!-- End budget -->
      [#else]
        [#-- If project leader is not defined --]
        <p>[@s.text name="preplanning.projectBudget.message.leaderUndefined" /]</p>
      [/#if] 
    [#else]
      [#-- If the project has not an start date and/or end date defined --]
      <p>[@s.text name="preplanning.projectBudget.message.dateUndefined" /]</p>
    [/#if] 
    
    [#if editable]
      <div class="borderBox"> 
        [#-- Project identifier --]
        <input name="projectID" type="hidden" value="${project.id?c}" />
        <input name="year" type="hidden" value="${year?c}" /> 
        <input name="targetYear" type="hidden" id="targetYear" value="${year?c}" />
        [@customForm.textArea name="justification" i18nkey="saving.justification" required=true className="justification"/]
        <div class="buttons">
          [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
          [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
          [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
        </div>
      </div>
    [#else]
        [#-- Display Log History --]
        [#if history??][@log.logList list=history /][/#if] 
    [/#if]
  </article>
  [/@s.form]
</section>

[#-- Dialog save confirmation --]
<div id="dialog-confirm" title="[@s.text name="planning.projectBudget.dialogConfirmation.title" /]" style="text-align:left;display:none">
  <div class="fullPartBlock">
    <p>[@s.text name="planning.projectBudget.dialogConfirmation.message" /]</p>
  </div>
  <div class="fullPartBlock pure-form">
    [@customForm.textArea name="" i18nkey="saving.justification" required=true className="justification"/]
  </div>
</div>

[#macro projectBudget institution budget project isPL=false pp_index="0" cofinancing_budgets="" editable=true]
<div id="partnerBudget-${pp_index}" class="partnerBudget simpleBox row clearfix">
  [#assign projectType=(!project.bilateralProject)?string("W1_W2", "W3_BILATERAL") /]
  <h6 class="title">${isPL?string('PL','PPA')} - ${institution.composedName}</h6>
  [#-- Hidden values --]
  <input type="hidden" name="project.budgets[${counter}].id" value="${budget.id!"-1"}" />
  <input type="hidden" name="project.budgets[${counter}].year" value="${(budget.year)!year}" />
  <input type="hidden" name="project.budgets[${counter}].institution.id" value="${(budget.institution.id)!institution.id}" />
  <input type="hidden" name="project.budgets[${counter}].type" value="${projectType}" />
  [#-- Project Budget --]
  <div class="halfPartBlock budget clearfix">
    <div class="title"><h6 class="subTitle">[@s.text name="planning.projectBudget.annualBudget" /]:</h6></div>
    <div class="content">
      <p class="inputTitle">[@s.text name="planning.projectBudget.totalAmount"][@s.param]${(!project.bilateralProject)?string(w1W2BudgetLabel, w3BilateralBudgetLabel)}[/@s.param][/@s.text]:
      [#if !editable]<br /><strong>US$ ${((budget.amount)!0)?number?string(",##0.00")}</strong>[/#if]
      </p>
      [#if editable] 
        [@customForm.input name="project.budgets[${counter}].amount" className="projectBudget ${projectType}" showTitle=false value="${(budget.amount)!0}"/] 
      [/#if]
    </div>
  </div><!-- End budget -->
  [#-- Project Gender Budget --]
  <div class="halfPartBlock budget clearfix">
    <div class="title"><h6 class="subTitle">[@s.text name="planning.projectBudget.genderPercentage" /]</h6></div>
    <div class="content">
      <p class="inputTitle">[@s.text name="planning.projectBudget.totalGendePercentage"][@s.param]${(!project.bilateralProject)?string(w1W2BudgetLabel, w3BilateralBudgetLabel)}[/@s.param][/@s.text]:  
      [#if !editable]<br /><strong> (${((budget.genderPercentage)!0)}%) </strong> [/#if] US$ <span>${(((budget.amount/100)*budget.genderPercentage)!0)?string(",##0.00")}</span> 
      </p>
      [#if editable]
        [@customForm.input name="project.budgets[${counter}].genderPercentage" className="projectGenderBudget" showTitle=false value="${(budget.genderPercentage)!0}"/]
      [/#if]
      [@s.set var="counter" value="${counter+1}"/]
    </div>
  </div><!-- End budget -->
  [#-- Project budget per bilateral linked project --]
  [#if project.linkedProjects?has_content && isPL ]
    <hr />
    <h6 class="subTitle">[@s.text name="planning.projectBudget.annualBudgetPerBilateralComponent" /]:</h6>
    <br />
    [#list project.linkedProjects as bilateralProject]
      [#assign cofinancingBudget = project.getCofinancingBudget(bilateralProject.id, year)! /]
      <div class="fullPartBlock budget clearfix">
        <p class="checked" >P${bilateralProject.id} -  ${bilateralProject.title}</p>
        <input type="hidden" name="project.budgets[${counter}].id" value="${cofinancingBudget.id!"-1"}" />
        <input type="hidden" name="project.budgets[${counter}].year" value="${year}" />
        <input type="hidden" name="project.budgets[${counter}].institution.id" value="${(cofinancingBudget.institution.id)!institution.id}" />
        <input type="hidden" name="project.budgets[${counter}].cofinancingProject.id" value="${bilateralProject.id}" />
        <input type="hidden" name="project.budgets[${counter}].type" value="W3_BILATERAL" />
        <div class="halfPartBlock">
          <div class="content">
          <p class="inputTitle">[@s.text name="planning.projectBudget.annualBudgetForProject"][@s.param]${w3BilateralBudgetLabel}[/@s.param][/@s.text]:
            [#if !editable]<strong>US$ ${((cofinancingBudget.amount)!0)?number?string(",##0.00")}</strong> [/#if]
          </p>
          [#if editable]
            [@customForm.input name="project.budgets[${counter}].amount" value="${cofinancingBudget.amount!0}" className="projectBudget W3_BILATERAL" showTitle=false/]
          [/#if]
          [@s.set var="counter" value="${counter+1}"/]
          </div>
        </div>
      </div><!-- End budget -->
    [/#list]
  [/#if]
</div>
[/#macro]

[#include "/WEB-INF/global/pages/footer.ftl"]
