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
    <a href="[@s.url namespace="/" action='glossary'][/@s.url]">[@s.text name="planning.projectBudget.budget" /]</a> [@s.text name="planning.projectBudget.help2" /]
    <a href="[@s.url namespace="/" action='glossary'][/@s.url]">[@s.text name="planning.projectBudget.partners" /]</a> [@s.text name="planning.projectBudget.help3" /] 
    <a href="[@s.url namespace="/" action='glossary'][/@s.url]"> [@s.text name="planning.projectBudget.managementLiaison" /]</a> [@s.text name="planning.projectBudget.help4" /]</p>
    <div class="quote">
      <p><strong>[@s.text name="preplanning.projectBudget.w1" /]: </strong> [@s.text name="preplanning.projectBudget.w1.tooltip" /]</p>
      <p><strong>[@s.text name="preplanning.projectBudget.w2" /]: </strong> [@s.text name="preplanning.projectBudget.w2.tooltip" /]</p>
      <p><strong>[@s.text name="preplanning.projectBudget.w3" /]: </strong> [@s.text name="preplanning.projectBudget.w3.tooltip" /]</p>
      <p><strong>[@s.text name="preplanning.projectBudget.bilateral" /]: </strong> [@s.text name="preplanning.projectBudget.bilateral.tooltip" /]</p>
      <p><strong>[@s.text name="preplanning.projectBudget.gender" /]: </strong> [@s.text name="preplanning.projectBudget.gender.tooltip" /]</p>
    </div>
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
  
  [@s.form action="budget" cssClass="pure-form"]
    <article class="halfContent" id="projectBudget">
    [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
    [#assign projectType=(!project.bilateralProject)?string("W1_W2", "W3_BILATERAL") /]
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GranProjectAccessInterceptor--]
    [#if !canEdit]
      <p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]</p>
    [/#if] 
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
        <div id="overhead" class="simpleBox">
          [#if (!editable && canEdit)]
            <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
          [#else]
            [#if canEdit && !newProject]
              <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>
            [/#if]
          [/#if]
          <h6>[@s.text name="planning.projectBudget.overhead" /]</h6> 
          <p>[@s.text name="planning.projectBudget.fullyInstitutionalCost" /]</p>
          <div class="radios">
            <input type="radio" name="project.overhead.bilateralCostRecovered" value="true" id="isfullyInstitutionalCost_1" checked/><label for="isfullyInstitutionalCost_1">[@s.text name="form.options.yes" /]</label>
            <input type="radio" name="project.overhead.bilateralCostRecovered" value="false" id="isfullyInstitutionalCost_0" /><label for="isfullyInstitutionalCost_0" >[@s.text name="form.options.no" /]</label>
          </div>
          <div class="overhead-block" style="display:none">
            <div class="fullPartBlock">
              [@customForm.input name="project.overhead.contractedOverhead"  i18nkey="planning.projectBudget.whatIsTheContracted" editable=editable/]
            </div>
            [#if canEdit]
            <div class="note fullPartBlock"><p>[@s.text name="planning.projectBudget.yourInstitutionalOverhead" /] <span>13%</span></p></div>
            [/#if]
          </div>
        </div>
        [/#if]
        
        <div id="budgetTables" class="ui-tabs ui-widget ui-widget-content ui-corner-all" style="display:none"> 
          [#-- Tertiary Menu - All years --] 
          <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
            [#list allYears as yearMenu]
              <li id="year-${yearMenu}" class="yearTab ui-state-default ui-corner-top [#if yearMenu == year ]ui-tabs-active ui-state-active ui-state-hover[/#if]">
                <a href="[@s.url action='budget'][@s.param name='${projectRequest}']${project.id?c}[/@s.param][@s.param name='year']${yearMenu?c}[/@s.param][/@s.url]"> ${yearMenu?c} </a>
              </li>
            [/#list]
          </ul>
          [@s.set var="counter" value="0"/] 
            [#-- Project budget content by year --]
            <div id="partnerTables-${year?c}" class="partnerTable ui-tabs-panel ui-widget-content ui-corner-bottom clearfix"> 
              [#if (!editable && canEdit)]
                <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name ="year"]${year}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
              [#else]
                [#if canEdit && !newProject]
                  <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name ="year"]${year}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>
                [/#if]
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
              <div class="ccafsBudget fullPartBlock ${project.type}">
                <h1 class="contentTitle">[@s.text name="planning.projectBudget.${project.bilateralProject?string('annualBilateralPartnerBudget','annualCorePartnerBudget')}" /]</h1> 
                [@s.set var="counter" value="0"/]
                [#-- Project CCAFS Partners --]
                [#if projectPPAPartners?has_content] 
                  [#list projectPPAPartners as partnerInstitution ]
                    [@partnerBudget institution=partnerInstitution budget=project.getBudget(partnerInstitution.id, project.bilateralProject?string(2, 1)?number, year )! isPL=partnerInstitution==project.leader.institution pp_index="${partnerInstitution_index+1}" editable=editable /]
                  [/#list]  
                [/#if]
                [#-- Project budget per linked project --]
                <hr />
                <h1 class="contentTitle">[@s.text name="planning.projectBudget.${(!project.bilateralProject)?string('annualBudgetPerBilateralComponent', 'annualBudgetPerCoreComponent')}" /]</h1> 
                <br />
                <div id="linkedProjects">
                  [#if project.linkedProjects?has_content]
                    [#-- List of linkeds Projects --]
                    [#list project.linkedProjects as linkedProject]
                      [@projectBudget institution=project.leader.institution linkedProject=linkedProject editable=editable /]
                      [@s.set var="counter" value="${counter+1}"/]
                    [/#list]
                  [/#if]
                </div><!-- End linked projects -->
                [#if editable]
                  [#-- The values of this list are loaded via ajax --]
                  [@customForm.select name="" label="" disabled=!canEdit i18nkey="" listName="" keyFieldName="id" displayFieldName="" className="addProject" value="" /]
                [/#if]
              </div>
              [#if canEdit]
                <div class="partnerListMsj">
                  [@s.text name="preplanning.projectBudget.partnerNotList" /]
                  <a href="[@s.url action='partners'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]"> [@s.text name="preplanning.projectBudget.partnersLink" /] </a>
                </div>
              [/#if]
           </div>
        </div> 
      [#else]
        <br />
        [#-- If project leader is not defined --] 
        <p class="simpleBox center">[@s.text name="preplanning.projectBudget.message.leaderUndefined" /]</p>
      [/#if] 
    [#else]
      <br />
      [#-- If the project has not an start date and/or end date defined --]
      <p class="simpleBox center">[@s.text name="preplanning.projectBudget.message.dateUndefined" /]</p>
    [/#if]
    
    [#if editable]
      [#-- Project identifier --]
      <input name="projectID" type="hidden" value="${project.id?c}" />
      <input name="year" id="year" type="hidden" value="${year?c}" /> 
      <input name="targetYear" type="hidden" id="targetYear" value="${year?c}" />
      <input type="hidden" id="projectType" value="${projectType}"/>
      <div class="[#if !newProject]borderBox[/#if]" >
        [#if !newProject] [@customForm.textArea name="justification" i18nkey="saving.justification" required=true className="justification"/][/#if]
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
  
  [#-- Hidden values used by js --]
  <input type="hidden" id="budgetCanNotExcced" value="[@s.text name="planning.projectBudget.canNotExceed" /]" />
  <input id="projectsAction" type="hidden" value="${project.bilateralProject?string('coreProjects.do','bilateralCoFinancingProjects.do')}" />
  
  [#-- Linked Project Template --]
  [#if project.leader?has_content]
    [@projectBudget institution=project.leader.institution linkedProject={} /]
  [/#if]
  
  [#-- Dialog save confirmation --]
  <div id="dialog-confirm" title="[@s.text name="planning.projectBudget.dialogConfirmation.title" /]" style="text-align:left;display:none">
    <div class="fullPartBlock">
      <p>[@s.text name="planning.projectBudget.dialogConfirmation.message" /]</p>
    </div>
    <div class="fullPartBlock pure-form">
      [@customForm.textArea name="" i18nkey="saving.justification" required=true className="justification"/]
    </div>
  </div>
</section>


[#macro partnerBudget institution budget isPL=false pp_index="0" cofinancing_budgets="" editable=true]
<div id="partnerBudget-${pp_index}" class="partnerBudget ${isPL?string('partnerLeader','ccafsPartner')} simpleBox row clearfix">
  <h6 class="title">${institution.composedName} <span class="projectType ${isPL?string('pl','ppa')}">${isPL?string('Project Leader','CCAFS Partner')}</span> </h6>
  <div class="budget">
    [#-- Hidden values --]
    <input type="hidden" name="project.budgets[${counter}].id" value="${budget.id!"-1"}" />
    <input type="hidden" name="project.budgets[${counter}].year" value="${(budget.year)!year}" />
    <input type="hidden" name="project.budgets[${counter}].institution.id" value="${(budget.institution.id)!institution.id}" />
    <input type="hidden" name="project.budgets[${counter}].type" value="${projectType}" />
    [#-- Project Budget --]
    <div class="halfPartBlock clearfix">
      <div class="title">
        <h6 class="subTitle">
          [@s.text name="planning.projectBudget.annualBudget"][@s.param]${(!project.bilateralProject)?string(w1W2BudgetLabel, w3BilateralBudgetLabel)}[/@s.param][/@s.text]: [@customForm.req required=true /]
        </h6>
        <p class="inputTitle">
          [#if !editable]<strong>US$ ${((budget.amount)!0)?number?string(",##0.00")}</strong>[/#if]
        </p>
      </div>
      <div class="content">
        [#if editable] 
          [@customForm.input name="project.budgets[${counter}].amount" required=true className="partnerBudget plBudget ${projectType}" showTitle=false value="${(budget.amount)!0}"/] 
        [/#if]
      </div>
    </div>
    [#-- Project Gender Budget --]
    <div class="halfPartBlock clearfix">
      <div class="title">
        <h6 class="subTitle">[@s.text name="planning.projectBudget.genderPercentage"][@s.param]${(!project.bilateralProject)?string(w1W2BudgetLabel, w3BilateralBudgetLabel)}[/@s.param][/@s.text]: [@customForm.req required=true /]
        <span class="inputTitle">
          [#if !editable]<strong> (${((budget.genderPercentage)!0)}%) </strong> [/#if] US$ <span>${(((budget.amount/100)*budget.genderPercentage)!0)?string(",##0.00")}</span> 
        </span>
        </h6>
      </div>
      <div class="content">
        [#if editable]
          [@customForm.input name="project.budgets[${counter}].genderPercentage" className="projectGenderBudget" showTitle=false value="${(budget.genderPercentage)!0}"/]
        [/#if]
        [@s.set var="counter" value="${counter+1}"/]
      </div>
    </div>
  </div><!-- End budget -->
</div>
[/#macro]

[#macro projectBudget institution linkedProject editable=true]
  <div id="projectBudget-${(linkedProject.id)!'template'}" class="projectBudget budget" style="display:${linkedProject?has_content?string('block','none')}">
    [#assign budgetName = "project.budgets[${counter}]" /]
    [#if linkedProject?has_content]
      [#if project.bilateralProject]
        [#assign cofinancingBudget = action.getBilateralCofinancingBudget(linkedProject.id, project.id, year)! /]
      [#else]
        [#assign cofinancingBudget = project.getCofinancingBudget(linkedProject.id, year)! /]
      [/#if]
    [/#if]
    [#if editable]<span class="listButton remove">[@s.text name="form.buttons.remove" /]</span>[/#if] 
    <p class="title checked" >
      <a target="_blank" href="[@s.url action='description'][@s.param name='projectID']${(linkedProject.id)!'-1'}[/@s.param][/@s.url]">P${(linkedProject.id)!''} -  ${(linkedProject.title)!'Untitle'}</a>
    </p>
    <input type="hidden" class="linkedId"  name="project.linkedProjects" value="${(linkedProject.id)!'-1'}" />
    [#if project.bilateralProject]
    <input type="hidden" class="budgetId" name="${budgetName}.id" value="${(cofinancingBudget.id)!"-1"}" />
    <input type="hidden" class="budgetYear" name="${budgetName}.year" value="${year}" />
    <input type="hidden" class="budgetInstitutionId" name="${budgetName}.institution.id" value="${(cofinancingBudget.institution.id)!institution.id}" />
    <input type="hidden" class="budgetCofinancingProjectId" name="${budgetName}.cofinancingProject.id" value="${(linkedProject.id)!'-1'}" />
    <input type="hidden" class="budgetType" name="${budgetName}.type" value="W3_BILATERAL" />
    [/#if]
    <div class="halfPartBlock">
      <div class="content">
      <p class="inputTitle">[@s.text name="planning.projectBudget.annualBudgetForProject"][@s.param]${w3BilateralBudgetLabel}[/@s.param][/@s.text]: [@customForm.req required=project.bilateralProject /]
        [#if !editable || !project.bilateralProject]<strong>US$ ${((cofinancingBudget.amount)!0)?number?string(",##0.00")}</strong> [/#if]
      </p>
      [#if editable && project.bilateralProject]
        [@customForm.input name="${budgetName}.amount" value="${(cofinancingBudget.amount)!0}" className="budgetAmount projectBudget W3_BILATERAL" showTitle=false /]
      [/#if]
      </div>
    </div>
    <div class="clearfix"></div>
  </div><!-- End project-${(linkedProject.id)!''} budget -->
[/#macro]

[#include "/WEB-INF/global/pages/footer.ftl"]