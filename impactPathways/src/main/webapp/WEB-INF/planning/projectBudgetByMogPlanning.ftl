[#ftl]
[#assign title = "Project Budget by MOGs" /]
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
  [#-- 
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" /> 
    <p> [@s.text name="planning.projectBudget.help1" /] 
    <a href="[@s.url namespace="/" action='glossary'][/@s.url]">[@s.text name="planning.projectBudget.budget" /]</a> [@s.text name="planning.projectBudget.help2" /]
    <a href="[@s.url namespace="/" action='glossary'][/@s.url]">[@s.text name="planning.projectBudget.partners" /]</a> [@s.text name="planning.projectBudget.help3" /] 
    <a href="[@s.url namespace="/" action='glossary'][/@s.url]"> [@s.text name="planning.projectBudget.managementLiaison" /]</a> [@s.text name="planning.projectBudget.help4" /]</p>
  </div>
  --]

  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
  
  [@s.form action="budgetByMog" cssClass="pure-form"]
    <article class="halfContent" id="projectBudget">
      [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
      [#assign projectTypeLabel][@s.text name="${project.bilateralProject?string('planning.projectBudget.W3Bilateral', 'planning.projectBudget.W1W2')}" /][/#assign]
      [#assign projectType][@s.text name="${project.bilateralProject?string('W3_BILATERAL', 'W1_W2')}" /][/#assign]
      <br />
      [#-- Informing user that he/she doesn't have enough privileges to edit. See GranProjectAccessInterceptor--]
      [#if submission?has_content]
        <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
      [#elseif !canEdit ]
        <p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]</p>
      [/#if] 

      [#-- Project Title --]
      <h1 class="contentTitle">[@s.text name="preplanning.projectBudgetByMog.title" /]</h1> 
      [#if project.outputs?has_content]
        <div id="budgetTables" class="ui-tabs ui-widget ui-widget-content ui-corner-all" > 

          [#-- Tertiary Menu - All years --] 
          <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
            [#list allYears as yearMenu]
              <li id="year-${yearMenu}" class="yearTab ui-state-default ui-corner-top [#if yearMenu=year ]ui-tabs-active ui-state-active ui-state-hover[/#if]">
                <a href="[@s.url action='budgetByMog'][@s.param name='projectID']${project.id?c}[/@s.param][@s.param name='year']${yearMenu?c}[/@s.param][/@s.url]"> ${yearMenu?c} </a>
              </li>
            [/#list]
          </ul>

          [#-- Project budget content by year and MOG --]
          <div id="partnerTables-${year?c}" class="partnerTable ui-tabs-panel ui-widget-content ui-corner-bottom clearfix"> 
            [#if (!editable && canEdit)]
              <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name ="year"]${year}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
            [#else]
              [#if canEdit && !newProject]
                <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name ="year"]${year}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>
              [/#if]
            [/#if]

            <div class="fieldset clearfix">

              [#if !project.bilateralProject]
                [#-- Total budget amount W1/W2  --]
                <div class="BudgetByYear"> 
                  <h6 class="subTitle">Total ${year} ${projectTypeLabel} budget: US$ <span>${ccafsBudgetByYear?number?string(",##0.00")}</span></h6> 
                  <p id="budgetByYear">
                    ${projectTypeLabel} budget remaining: (<span class="percentage"></span>) US$ <span class="amount">${ccafsBudgetByYear?number?string(",##0.00")}</span>
                  </p>
                </div>
  
                [#-- Total W1/W2 gender budget amount --]
                <div class="BudgetByYear"> 
                  <h6 class="subTitle">Total ${year} ${projectTypeLabel} Gender budget: US$ <span>${ccafsGenderPercentage?number?string(",##0.00")}</span></h6> 
                  <p id="genderBudgetByYear">
                    ${projectTypeLabel} budget remaining: (<span class="percentage"></span>) US$ <span class="amount">${ccafsGenderPercentage?number?string(",##0.00")}</span>
                  </p>
                </div>
              [/#if]

              [#if project.bilateralProject || project.coFundedProject]
                [#-- Total budget amount W3/Bilateral for bilateral and Co-funded projects --]
                <div class="BudgetByYear">
                  <h6 class="subTitle">Total ${year} [@s.text name="planning.projectBudget.W3Bilateral" /] budget: US$ <span>${bilateralBudgetByYear?number?string(",##0.00")}</span></h6> 
                  <p id="coFundedBudgetByYear">
                    [@s.text name="planning.projectBudget.W3Bilateral" /] budget remaining: (<span class="percentage"></span>) US$ <span class="amount">${bilateralBudgetByYear?number?string(",##0.00")}</span>
                  </p>
                </div>

                [#-- Total gender budget amount (W3/Bilateral) for Co-funded projects --]
                <div class="BudgetByYear">
                  <h6 class="subTitle">Total ${year} [@s.text name="planning.projectBudget.W3Bilateral" /] Gender budget : US$ <span>${bilateralGenderPercentage?number?string(",##0.00")}</span></h6> 
                  <p id="coFundedGenderBudgetByYear">
                    [@s.text name="planning.projectBudget.W3Bilateral" /] budget remaining: (<span class="percentage"></span>) US$ <span class="amount">${bilateralGenderPercentage?number?string(",##0.00")}</span>
                  </p>
                </div>
              [/#if]
            </div> <!-- End Budget by year  -->

            <div class="midOutcomeTitle">
              [#-- Title --]
              <h6 class="title">[@s.text name="planning.projectImpactPathways.mogs" /]</h6></div>
              [@s.set var="counter" value="0"/]
              [#list project.outputs as output]
                <div class="budgetByMog simpleBox clearfix"> 
                  <div class="fullPartBlock">
                    <p class="checked">${output.program.acronym} - MOG #${action.getMOGIndex(output)}: ${output.description} </p>
                  </div>

                  [#if !project.bilateralProject]
                    [#assign mogBudget = action.getOutputBudget(output.id, ccafsBudgetType)!]
                    <div class="outputBudget">
                      [#-- Hidden inputs --]
                      <input type="hidden" name="project.outputsBudgets[${counter}].id" value="${mogBudget.id!"-1"}" />
                      <input type="hidden" name="project.outputsBudgets[${counter}].output.id" value="${output.id}" />
                      <input type="hidden" name="project.outputsBudgets[${counter}].year" value="${year}" />
                      <input type="hidden" name="project.outputsBudgets[${counter}].type" value="W1_W2" />
  
                      [#-- Total contribution --]
                      <div class="halfPartBlock budget clearfix">
                        <div class="title">
                          <p class="totalContribution">[#if !editable]${mogBudget.totalContribution!0}[/#if][@s.text name="preplanning.projectBudgetByMog.percentageOfTotalBudget"][@s.param]${projectTypeLabel}[/@s.param][/@s.text]: [@customForm.req required=true /]
                          <strong>US$ <span class="amount">${((ccafsBudgetByYear?number/100)*(mogBudget.totalContribution)!0)?number?string(",##0.00")}</span></strong></p>
                        </div>
                        <div class="content">
                        [#if editable]
                          [@customForm.input name="project.outputsBudgets[${counter}].totalContribution" className="percentage budgetInput" value="${(mogBudget.totalContribution)!0}" showTitle=false i18nkey="preplanning.projectBudgetByMog.percentageOfTotalBudget"/] 
                        [#else]
                          <input type="hidden" class="budgetInput"  value="${(mogBudget.totalContribution)!0}" />
                        [/#if]
                        </div>
                      </div>
                      [#-- Gender contribution --]
                      <div class="halfPartBlock budget clearfix">
                        <div class="title">
                          <p class="genderContribution">[#if !editable]${mogBudget.genderContribution!0}[/#if][@s.text name="preplanning.projectBudgetByMog.percentageOfTotalGenderBudget"][@s.param]${projectTypeLabel}[/@s.param][/@s.text]: [@customForm.req required=true /]
                          <strong>US$ <span class="amount">${(((ccafsBudgetByYear?number/100)*((mogBudget.totalContribution)!0)?number/100)*(mogBudget.genderContribution)!0)?number?string(",##0.00")}</span></strong></p>
                        </div>
                        <div class="content">
                        [#if editable]
                          [@customForm.input name="project.outputsBudgets[${counter}].genderContribution" className="percentage genderBudgetInput" value="${mogBudget.genderContribution!0}" showTitle=false i18nkey="preplanning.projectBudgetByMog.percentageOfTotalGenderBudget"/] 
                        [#else]
                          <input type="hidden" class="genderBudgetInput"  value="${(mogBudget.genderContribution)!0}" />
                        [/#if]
                        </div>
                      </div>
                      [@s.set var="counter" value="${counter+1}"/]
                    </div>
                  [/#if]

                  [#if project.bilateralProject || project.coFundedProject]
                    [#assign mogBudget = action.getOutputBudget(output.id, bilateralBudgetType)!]
                    <hr />
                    <div class="outputBudget">
                      [#-- Hidden inputs --]
                      <input type="hidden" name="project.outputsBudgets[${counter}].id" value="${(mogBudget.id)!"-1"}" />
                      <input type="hidden" name="project.outputsBudgets[${counter}].output.id" value="${output.id}" />
                      <input type="hidden" name="project.outputsBudgets[${counter}].year" value="${year}" />
                      <input type="hidden" name="project.outputsBudgets[${counter}].type" value="W3_BILATERAL" />
                      [#-- Total contribution --]
                      <div class="halfPartBlock budget clearfix">
                        <div class="title">
                          <p class="totalContribution">[#if !editable]${(mogBudget.totalContribution)!0}[/#if][@s.text name="preplanning.projectBudgetByMog.percentageOfTotalBudget"][@s.param][@s.text name="planning.projectBudget.W3Bilateral" /][/@s.param][/@s.text]: [@customForm.req required=true /]
                          <strong>US$ <span class="amount">${((bilateralBudgetByYear?number/100)*(mogBudget.totalContribution)!0)?number?string(",##0.00")}</span></strong></p>
                        </div>
                        <div class="content">
                        [#if editable]
                          [@customForm.input name="project.outputsBudgets[${counter}].totalContribution" className="percentage budgetCoFundedInput" value="${(mogBudget.totalContribution)!0}" showTitle=false i18nkey="preplanning.projectBudgetByMog.percentageOfTotalBudget"/] 
                        [#else]
                          <input type="hidden" class="budgetCoFundedInput"  value="${(mogBudget.totalContribution)!0}" />
                        [/#if]
                        </div>
                      </div>
                      [#-- Gender contribution --]
                      <div class="halfPartBlock budget clearfix">
                        <div class="title">
                          <p class="genderContribution">[#if !editable]${(mogBudget.genderContribution)!0}[/#if][@s.text name="preplanning.projectBudgetByMog.percentageOfTotalGenderBudget"][@s.param][@s.text name="planning.projectBudget.W3Bilateral" /][/@s.param][/@s.text]: [@customForm.req required=true /]
                          <strong>US$ <span class="amount">${(((bilateralBudgetByYear?number/100)*((mogBudget.totalContribution)!0)?number/100)*(mogBudget.genderContribution)!0)?number?string(",##0.00")}</span></strong></p>
                        </div>
                        <div class="content">
                        [#if editable]
                          [@customForm.input name="project.outputsBudgets[${counter}].genderContribution" className="percentage genderCoFundedBudgetInput" value="${(mogBudget.genderContribution)!0}" showTitle=false i18nkey="preplanning.projectBudgetByMog.percentageOfTotalGenderBudget"/] 
                        [#else]
                          <input type="hidden" class="genderCoFundedBudgetInput"  value="${(mogBudget.genderContribution)!0}" />
                        [/#if]
                        </div>
                      </div>
                      [@s.set var="counter" value="${counter+1}"/]
                    </div>
                  [/#if]
                </div>
              [/#list]
            </div>
        </div>
        [#if editable]
          [#-- Project identifier --]
          <input name="projectID" type="hidden" value="${project.id?c}" />
          <input name="year" type="hidden" value="${year?c}" /> 
          <input name="targetYear" type="hidden" id="targetYear" value="${year?c}" />
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
    [#else]
      <p class="simpleBox center">[@s.text name="planning.projectOutputs.empty1" /] 
      
      <a href="[@s.url namespace="/" action='planning/projects/ccafsOutcomes?projectID=${project.id?c}'][/@s.url]">Click Here</a>[@s.text name="planning.projectOutputs.empty2" /]</p>
    [/#if]
  </article>
  [/@s.form]
</section>

[#-- Hidden values used by js --]
<input type="hidden" id="budgetCanNotExcced" value="[@s.text name="planning.projectBudget.canNotExceedPercentage" /]" />
<input type="hidden" id="budgetCanNotRemain" value="[@s.text name="planning.projectBudget.canNotRemainPercentage" /]" />

[#include "/WEB-INF/global/pages/footer.ftl"]
