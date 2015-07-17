[#ftl]
[#assign title = "Project Budget" /]
[#assign globalLibs = ["jquery", "noty","autoSave","chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/preplanning/projectBudgetByMog.js"] /]
[#assign currentSection = "planning" /]
[#assign currentStage = "budget" /]
[#assign currentSubStage = "budgetByMog" /]

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
      [#include "/WEB-INF/planning/projectBudget-sub-menu.ftl" /]
      [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
      [#-- Informing user that he/she doesn't have enough privileges to edit. See GranProjectAccessInterceptor--]
      [#if !canEdit]
        <p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]</p>
      [/#if] 
      [#-- Project Title --]
      <h1 class="contentTitle">${project.type} [@s.text name="preplanning.projectBudgetByMog.title" /]</h1> 
      [#assign allYears=2015..2017 /] 
      [#assign year=2015 /]
      <div id="budgetTables" class="ui-tabs ui-widget ui-widget-content ui-corner-all" > 
        [#-- Tertiary Menu - All years --] 
        <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
          [#list allYears as yearMenu]
            <li id="year-${yearMenu}" class="yearTab ui-state-default ui-corner-top [#if yearMenu=year ]ui-tabs-active ui-state-active ui-state-hover[/#if]">
              <a href="[@s.url action='budget' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][@s.param name='year']${yearMenu?c}[/@s.param][/@s.url]"> ${yearMenu?c} </a>
            </li>
          [/#list]
        </ul>
        [#-- Project budget content by year and MOG --]
        <div id="partnerTables-${year?c}" class="partnerTable ui-tabs-panel ui-widget-content ui-corner-bottom clearfix"> 
          [#if (!editable && canEdit)]
            <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
          [/#if]
          [#-- Title --]
          <div class="midOutcomeTitle"><h6 class="title">[@s.text name="planning.projectImpactPathways.mogs" /]</h6></div>
            [#list project.outputs as output]
            [#assign outputOverview = project.getOutputOverview(output.id, year)! /]
            <div class="simpleBox clearfix"> 
              <div class="fullPartBlock">
                <p class="checked">${output.program.acronym} - MOG #${action.getMOGIndex(output)}: ${output.description} </p>
              </div>
              <div class="halfPartBlock budget clearfix">
                <div class="title"><h6>[@s.text name="preplanning.projectBudgetByMog.percentageOfTotalBudget"][@s.param]${project.bilateralProject?string('W3/Bilateral', 'W1 W2')}[/@s.param][/@s.text]</h6></div>
                <div class="content">
                  [@customForm.input name="project.budgets.amount" className="percentage" value="" showTitle=false i18nkey="preplanning.projectBudgetByMog.percentageOfTotalBudget" editable=editable/] 
                </div>
              </div><!-- End budget -->
              <div class="halfPartBlock budget clearfix">
                <div class="title"><h6>[@s.text name="preplanning.projectBudgetByMog.percentageOfTotalGenderBudget"][@s.param]${project.bilateralProject?string('W3/Bilateral', 'W1 W2')}[/@s.param][/@s.text]</h6></div>
                <div class="content">
                  [@customForm.input name="project.budgets.amount" className="percentage" value="" showTitle=false i18nkey="preplanning.projectBudgetByMog.percentageOfTotalGenderBudget" editable=editable/] 
                </div>
              </div><!-- End budget --> 
            </div>
            [/#list]
          </div>
      </div>
       
      
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

[#macro projectBudget projectPartner pp_index="0" isBilateral=true isCofunded=true editable=true]
<div id="partnerBudget-${pp_index}" class="partnerBudget simpleBox row clearfix">
  <h6 class="title">${projectPartner.type} - ${projectPartner.institution.composedName}</h6>
  [#-- Project Budget --]
  <div class="budget clearfix">
    <div class="title"><h6>[@s.text name="planning.projectBudget.annualBudget" /]:</h6></div>
    <div class="content">
      <p>[@s.text name="planning.projectBudget.totalAmount"][@s.param]${isBilateral?string('W3/Bilateral', 'W1 W2')}[/@s.param][/@s.text]:</p> 
      [@customForm.input name="project.budgets[${pp_index}].amount" className="projectBudget" showTitle=false value="" editable=editable/] 
    </div>
  </div><!-- End budget -->
  [#-- Project budget per bilateral --]
  [#if isCofunded]
  <div class="budget clearfix">
    <div class="title"><h6>[@s.text name="planning.projectBudget.annualBudgetPerBilateral" /]:</h6></div>
    <div class="content">  
    </div>
  </div><!-- End budget -->
  [/#if]
  [#-- Project Gender Budget --]
  <div class="budget clearfix">
    <div class="title"><h6>[@s.text name="planning.projectBudget.genderPercentage" /]</h6></div>
    <div class="content"> 
      <p>[@s.text name="planning.projectBudget.totalGendePercentage"][@s.param]${isBilateral?string('W3/Bilateral', 'W1 W2')}[/@s.param][/@s.text]:</p>
      [@customForm.input name="project.budgets[${pp_index}].amount" showTitle=false value="" editable=editable/] 
    </div>
  </div><!-- End budget -->
</div>
[/#macro]

[#include "/WEB-INF/global/pages/footer.ftl"]