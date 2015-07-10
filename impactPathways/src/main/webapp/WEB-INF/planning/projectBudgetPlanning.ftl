[#ftl]
[#assign title = "Project Budget" /]
[#assign globalLibs = ["jquery", "noty","autoSave","chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/preplanning/projectBudget.js"] /]
[#assign currentSection = "planning" /]
[#assign currentStage = "budget" /]

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
    <div id="downloadGuidelineMessage">
      <a  href="${baseUrl}/resources/guidelines/FP_Guidelines_Budget_20141007_to Liaison.pptx">  
        <img class="icon" src="${baseUrl}/images/global/download-icon_636368.png" />
        <p>[@s.text name="preplanning.projectBudget.guideline.title" /]</p>
      </a>
    </div>
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
  
  [@s.form action="budget" cssClass="pure-form"]
    <article class="halfContent" id="projectBudget">
    [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GranProjectAccessInterceptor--]
    [#if !canEdit]
      <p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]</p>
    [/#if] 
    [#-- Project Title --]
    <h1 class="contentTitle">${project.type} [@s.text name="preplanning.projectBudget.title" /]</h1> 
    [#assign allYears=2015..2017 /] 
    [#assign year=2015 /]
    [#if allYears?has_content]
      [#if project.leader?has_content]
        [#-- Accumulative Total W1 W2 Budget --]
        <div id="totalBudget" class="thirdPartBlock">
          <h6>[@s.text name="preplanning.projectBudget.totalBudget" /]</h6>
          <p id="projectTotalW1W2">US$ <span id="projectTotalW1W2Budget">{totalW1W2Budget?string(",##0.00")}</span></p>
          <input type="hidden" id="projectTotalW1W2Budget" value="{totalW1W2Budget?c}" />
          <input type="hidden" id="yearTotalW1W2Budget" value="{totalW1W2BudgetByYear?c}" />
        </div> 
        
        [#if project.bilateralProject]
          Overhead here
        [/#if]
        <div id="budgetTables" class="ui-tabs ui-widget ui-widget-content ui-corner-all" style="display:none"> 
          [#-- Tertiary Menu - All years --] 
          <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
            [#list allYears as yearMenu]
              <li id="year-${yearMenu}" class="yearTab ui-state-default ui-corner-top [#if yearMenu=year ]ui-tabs-active ui-state-active ui-state-hover[/#if]">
                <a href="[@s.url action='budget' includeParams='get'][@s.param name='${projectRequest}']${project.id?c}[/@s.param][@s.param name='year']${yearMenu?c}[/@s.param][/@s.url]"> ${yearMenu?c} </a>
              </li>
            [/#list]
          </ul>
          [@s.set var="counter" value="0"/] 
            [#-- Years Content--]
            <div id="partnerTables-${year?c}" class="partnerTable ui-tabs-panel ui-widget-content ui-corner-bottom clearfix"> 
              <div class="fieldset clearfix">
                <div id="totalw1w2BudgetByYear" class="BudgetByYear"> 
                  <p id="projectTotalByYear"><strong> [@s.text name="preplanning.projectBudget.totalYearW1W2Budget"][@s.param name="0"]${year}[/@s.param][/@s.text]</strong> 
                  <br>US$ <span id="projectTotalW1W2BudgetByYear">{totalW1W2BudgetByYear?string(",##0.00")}</span></p>
                </div>
              </div> 
              <div class="ccafsBudget fullPartBlock clearfix">
                [#-- Project Leader --]
                [#if project.leader?has_content]
                  <div id="partnerBudget-lead" class="partnerBudget simpleBox row clearfix">
                    <h6 class="title">${project.leader.type} - ${project.leader.institution.composedName}</h6>
                    <div class="grid_1">
                      <h6>Budget</h6>
                    </div>
                    <div class="grid_8">
                      <div class="grid_4">
                        <p>Total amount received W1 W2 Budget:</p>
                        [@customForm.input name="project.budgets[{counter}].amount" showTitle=false value=""/]
                      </div>
                    </div>
                  </div>
                [/#if]
                [#-- Project Partners --]
                [#if project.ppaPartners?has_content] 
                  [#list project.ppaPartners as projectPartner ]
                    <div id="partnerBudget-${projectPartner_index}" class="partnerBudget simpleBox row clearfix">
                      <h6 class="title">${projectPartner.type} - ${projectPartner.institution.composedName}</h6>
                      <div class="grid_1">
                        <h6>Budget</h6>
                      </div>
                      <div class="grid_8">
                        <div class="grid_4">
                          <p>Total amount received W1 W2 Budget:</p>
                          [@customForm.input name="project.budgets[{counter}].amount" showTitle=false value=""/]
                        </div>
                      </div>
                    </div>
                  [/#list]  
                [/#if]
              </div><!-- End partners list -->
              <div class="partnerListMsj">
                [@s.text name="preplanning.projectBudget.partnerNotList" /]
                <a href="[@s.url action='partners' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]"> 
                  [@s.text name="preplanning.projectBudget.partnersLink" /] 
                </a>
              </div>
           </div>
        </div> <!-- End budgetTables -->
      [#else]
        [#-- If project leader is not defined --]
        <p>[@s.text name="preplanning.projectBudget.message.leaderUndefined" /]</p>
      [/#if] 
    [#else]
      [#-- If the project has not an start date and/or end date defined --]
      <p>[@s.text name="preplanning.projectBudget.message.dateUndefined" /]</p>
    [/#if]
    
    [#if editable]
      <!-- internal parameter -->
      <input name="projectID" type="hidden" value="${project.id?c}" />
      <input name="year" type="hidden" value="${year?c}" /> 
      <input type="hidden" id="targetYear" name="targetYear" value="${year?c}" />
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
    [#else]
      [#-- Display Log History --]
      [#if history??][@log.logList list=history /][/#if] 
    [/#if]
  </article>
  [/@s.form]
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]