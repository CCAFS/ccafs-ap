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
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"][@s.param][@s.text name="preplanning.project"/][/@s.param][/@s.text]
      </p>
    [/#if]
    
    [#-- Project Title --]
    <h1 class="contentTitle">
      ${project.composedId} - [@s.text name="preplanning.projectBudget.title" /]  
    </h1>
    [#if allYears?has_content]
      [#if invalidYear == false]
        [#if hasLeader == false]
          [#-- Accumulative Total W1 W2 Budget --]
          <div id="totalBudget" class="thirdPartBlock">
            <h6>[@s.text name="preplanning.projectBudget.totalBudget" /]</h6>
            <p id="projectTotalW1W2">US$ <span id="projectTotalW1W2Budget">${totalW1W2Budget?string(",##0.00")}</span></p>
            <input type="hidden" id="projectTotalW1W2Budget" value="${totalW1W2Budget?c}" />
            <input type="hidden" id="yearTotalW1W2Budget" value="${totalW1W2BudgetByYear?c}" />
          </div> 
          
          [#-- Tertiary Menu - All years --] 
          <div id="budgetTables" class="ui-tabs ui-widget ui-widget-content ui-corner-all" style="display:none"> 
            <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
              [#list allYears as yearMenu]
                <li id="year-${yearMenu}" class="yearTab ui-state-default ui-corner-top [#if yearMenu=year ]ui-tabs-active ui-state-active ui-state-hover[/#if]">
                  <a href="[@s.url action='budget' includeParams='get'][@s.param name='${projectRequest}']${project.id?c}[/@s.param][@s.param name='year']${yearMenu?c}[/@s.param][/@s.url]"> ${yearMenu?c} </a>
                </li>
              [/#list]
            </ul>
            [@s.set var="counter" value="0"/] 
              <div id="partnerTables-${year?c}" class="partnerTable ui-tabs-panel ui-widget-content ui-corner-bottom clearfix"> 
                <div class="fieldset clearfix">
                  <div id="totalw1w2BudgetByYear" class="BudgetByYear"> 
                    <p id="projectTotalByYear"><strong> [@s.text name="preplanning.projectBudget.totalYearW1W2Budget"][@s.param name="0"]${year}[/@s.param][/@s.text]</strong> 
                    <br>US$ <span id="projectTotalW1W2BudgetByYear">${totalW1W2BudgetByYear?string(",##0.00")}</span></p>
                  </div>
                </div> 
                <div class="ccafsBudget fullPartBlock clearfix">              
                  [#if project.leader?has_content]
                    <div id="partnerBudget-lead" class="partnerBudget row clearfix">
                    </div>
                  [/#if] 
                  [#list projectPartners as projectPartner ]
                    <div id="partnerBudget-${projectPartner_index}" class="partnerBudget row clearfix">
                    </div>
                  [/#list]   
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
        <p>[@s.text name="preplanning.projectBudget.message.invalidYear" /]</p>
      [/#if]
    [#else]
      [#-- If the project has not an start date and/or end date defined --]
      <p>[@s.text name="preplanning.projectBudget.message.dateUndefined" /]</p>
    [/#if]
    [#-- Showing buttons only to users with enough privileges. See GranProjectAccessInterceptor--]
    
    [#if saveable]
      [#if allYears?has_content && !invalidYear && hasLeader]
        <!-- internal parameter -->
        <input name="projectID" type="hidden" value="${project.id?c}" />
        <input name="year" type="hidden" value="${year?c}" /> 
        <input type="hidden" id="targetYear" name="targetYear" value="${year?c}" />
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
    <div id="amount" class="LEVERAGED   amount">
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