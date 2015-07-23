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
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
  
  [@s.form action="budgetByMog" cssClass="pure-form"]
    <article class="halfContent" id="projectBudget">
      [#include "/WEB-INF/planning/projectBudget-sub-menu.ftl" /]
      [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
      [#-- Informing user that he/she doesn't have enough privileges to edit. See GranProjectAccessInterceptor--]
      [#if !canEdit]
        <p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]</p>
      [/#if] 
      [#-- Project Title --]
      <h1 class="contentTitle">[@s.text name="preplanning.projectBudgetByMog.title" /]</h1> 
      [#-- TODO Delete below static variables --]
      [#assign allYears=2015..2017 /] 
      [#assign year=2015 /]
      <div id="budgetTables" class="ui-tabs ui-widget ui-widget-content ui-corner-all" > 
        [#-- Tertiary Menu - All years --] 
        <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
          [#list allYears as yearMenu]
            <li id="year-${yearMenu}" class="yearTab ui-state-default ui-corner-top [#if yearMenu=year ]ui-tabs-active ui-state-active ui-state-hover[/#if]">
              <a href="[@s.url action='budgetByMog' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][@s.param name='year']${yearMenu?c}[/@s.param][/@s.url]"> ${yearMenu?c} </a>
            </li>
          [/#list]
        </ul>
        [#-- Project budget content by year and MOG --]
        <div id="partnerTables-${year?c}" class="partnerTable ui-tabs-panel ui-widget-content ui-corner-bottom clearfix"> 
          [#if (!editable && canEdit)]
            <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
          [/#if]
          <div class="fieldset clearfix">
            [#-- Total budget amount --]
            <div class="BudgetByYear"> 
              [#assign totalBudgetByYear]50000[/#assign]
              <h6 class="subTitle">Total ${year} budget remaining of US$ <span>${totalBudgetByYear?number?string(",##0.00")}</span> </h6> 
              <p id="budgetByYear">
                US$ <span>${totalBudgetByYear?number?string(",##0.00")}</span>
                <input type="hidden" value="${totalBudgetByYear?number}" />
              </p>
            </div>
            [#-- Total gender budget amount --]
            <div class="BudgetByYear"> 
              [#assign totalGenderBudgetByYear]20000[/#assign]
              <h6 class="subTitle">Total ${year} Gender budget remaining of US$ <span>${totalGenderBudgetByYear?number?string(",##0.00")}</span></h6> 
              <p id="genderBudgetByYear">
                US$ <span>${totalGenderBudgetByYear?number?string(",##0.00")}</span>
                <input type="hidden" value="${totalGenderBudgetByYear?number}" />
              </p>
            </div>
            
          </div> <!-- End Budget by year  -->
          <div class="midOutcomeTitle">
            [#-- Title --]
            <h6 class="title">[@s.text name="planning.projectImpactPathways.mogs" /]</h6></div>
            [#list project.outputs as output]
              [#assign mogBudget = action.getOutputBudget(output.id)!]
              [#assign projectTypeLabel][@s.text name="${project.bilateralProject?string('planning.projectBudget.W3Bilateral', 'planning.projectBudget.W1W2')}" /][/#assign]
              <div class="simpleBox clearfix"> 
                <div class="fullPartBlock">
                  <p class="checked">${output.program.acronym} - MOG #${action.getMOGIndex(output)}: ${output.description} </p>
                </div>
                [#-- Hidden inputs --]
                <input type="hidden" name="project.outputsBudgets[${output_index}].id" value="${mogBudget.id!"-1"}" />
                <input type="hidden" name="project.outputsBudgets[${output_index}].output.id" value="${output.id}" />

                [#-- Total contribution --]
                <div class="halfPartBlock budget clearfix">
                  <div class="title">
                    <p> <strong> [@s.text name="preplanning.projectBudgetByMog.percentageOfTotalBudget"][@s.param]${projectTypeLabel}[/@s.param][/@s.text]: [#if !editable](${mogBudget.totalContribution!0}%) [/#if]</strong>
                    US$ <span>${((totalBudgetByYear?number/100)*(mogBudget.totalContribution)!0)?number?string(",##0.00")}</span></p>
                  </div>
                  <div class="content">
                  [#if editable]
                    [@customForm.input name="project.outputsBudgets[${output_index}].totalContribution" className="percentage budgetInput" value="${mogBudget.totalContribution!0}" showTitle=false i18nkey="preplanning.projectBudgetByMog.percentageOfTotalBudget"/] 
                  [/#if]
                  </div>
                </div>

                [#-- Gender contribution --]
                <div class="halfPartBlock budget clearfix">
                  <div class="title">
                    <p> <strong> [@s.text name="preplanning.projectBudgetByMog.percentageOfTotalGenderBudget"][@s.param]${projectTypeLabel}[/@s.param][/@s.text]: [#if !editable](${mogBudget.genderContribution!0}%) [/#if]</strong>
                    US$ <span>${((totalGenderBudgetByYear?number/100)*(mogBudget.genderContribution)!0)?number?string(",##0.00")}</span></p>
                  </div>
                  <div class="content">
                  [#if editable]
                    [@customForm.input name="project.outputsBudgets[${output_index}].genderContribution" className="percentage genderBudgetInput" value="${mogBudget.genderContribution!0}" showTitle=false i18nkey="preplanning.projectBudgetByMog.percentageOfTotalGenderBudget"/] 
                  [/#if]
                  </div>
                </div>
                
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

[#include "/WEB-INF/global/pages/footer.ftl"]
