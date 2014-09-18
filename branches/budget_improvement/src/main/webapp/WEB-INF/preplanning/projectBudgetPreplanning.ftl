[#ftl]
[#assign title = "Project Budget" /]
[#assign globalLibs = ["jquery", "noty","autoSave","chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/preplanning/projectBudgetRevamp.js"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "projects" /]
[#assign currentStage = "budget" /]

[#assign breadCrumb = [
  {"label":"preplanning", "nameSpace":"pre-planning", "action":"outcomes"},
  {"label":"projects", "nameSpace":"pre-planning", "action":"projects"},
  {"label":"budget", "nameSpace":"pre-planning/projects", "action":""}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="preplanning.projectBudget.help" /]</p>
    <div class="quote">
      <p><strong>[@s.text name="preplanning.projectBudget.w1" /]: </strong> [@s.text name="preplanning.projectBudget.w1.tooltip" /]</p>
      <p><strong>[@s.text name="preplanning.projectBudget.w2" /]: </strong> [@s.text name="preplanning.projectBudget.w2.tooltip" /]</p>
      <p><strong>[@s.text name="preplanning.projectBudget.w3" /]: </strong> [@s.text name="preplanning.projectBudget.w3.tooltip" /]</p>
      <p><strong>[@s.text name="preplanning.projectBudget.bilateral" /]: </strong> [@s.text name="preplanning.projectBudget.bilateral.tooltip" /]</p>
    </div>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl" /]
  
  [@s.form action="budget" cssClass="pure-form"]
    <article class="halfContent" id="projectBudget">
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GranProjectAccessInterceptor--]
    [#if !saveable]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"]
          [@s.param][@s.text name="preplanning.project"/][/@s.param]
        [/@s.text]
      </p>
    [/#if]
    
    [#include "/WEB-INF/preplanning/projectPreplanningSubMenu.ftl" /]
    [#-- Title --]
    [#if project.title?has_content]    
    <h1 class="contentTitle" title="${project.title}">
      ${project.composedId} - [@s.text name="preplanning.projectBudget.title" /]  
    </h1>
    [/#if]
    [#if allYears?has_content]
      [#if invalidYear == false]
        [#if hasLeader]
          [#-- Accumulative Total W1 W2 Budget --]
          <div id="totalBudget" class="thirdPartBlock">
            <h6>[@s.text name="preplanning.projectBudget.totalBudget" /]</h6>
            <p id="projectTotalW1W2">US$ <span id="projectTotalW1W2Budget">${totalW1W2Budget?string(",##0.00")}</span></p>
            <input type="hidden" id="projectTotalW1W2Budget" value="${totalW1W2Budget?c}" />
            <input type="hidden" id="yearTotalW1W2Budget" value="${totalW1W2BudgetByYear?c}" />
          </div> 
          [#-- Accumulative Total W1 W2 + W3+ Bilateral Budget --]
          <div id="totalBudget" class="thirdPartBlock">
            <h6>[@s.text name="preplanning.projectBudget.totalCCAFSBudget" /]</h6>
            <p id="projectTotalCCAFS">US$ <span id="projectTotalCCAFSBudget">${totalCCAFSBudget?string(",##0.00")}</span></p>
            <input type="hidden" id="projectTotalCCAFSBudget" value="${totalCCAFSBudget?c}" />
            <input type="hidden" id="yearTotalCCAFSBudget" value="${totalCCAFSBudgetByYear?c}" />
          </div>
          [#-- Accumulative Leverage Funds --]
          <div id="totalBudget" class="thirdPartBlock">
            <h6>[@s.text name="preplanning.projectBudget.totalLeveragedBudget" /]</h6>
            <p id="projectTotalLeveragedBudget">US$ <span id="projectTotalLeveragedBudget">0.00</span></p>
            <input type="hidden" id="projectTotalLeveragedBudget" value="0" />
            <input type="hidden" id="yearTotalLeveragedBudget" value="0" />
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
                  <div id="totalCCAFSBudgetByYear" class="BudgetByYear"> 
                    <p id="projectTotalByYear"><strong> [@s.text name="preplanning.projectBudget.totalYearCCAFSBudget"][@s.param name="0"]${year}[/@s.param][/@s.text]</strong> 
                    <br>US$ <span id="projectTotalCCAFSBudgetByYear">${totalCCAFSBudgetByYear?string(",##0.00")}</span></p>
                  </div>
                  <div id="totalYearLeveragedBudget" class="BudgetByYear"> 
                    <p id="projectTotalByYear"><strong> [@s.text name="preplanning.projectBudget.totalYearLeveragedBudget"][@s.param name="0"]${year}[/@s.param][/@s.text]</strong>
                    <br>US$ <span id="projectTotalLeveragedBudgetByYear">0.00</span></p>
                  </div>
                </div> 
                <div class="ccafsBudget fullPartBlock clearfix">              
                  [#if project.leader?has_content]
                    <div id="partnerBudget-lead" class="partnerBudget row clearfix">
                      [#-- Partner Leader Name --]
                      <div id="" class="grid_9 budgetPartnerTitle down">
                        ${project.leader.currentInstitution.name} <strong>([@s.text name="preplanning.projectBudget.partnerLead" /])</strong> 
                        <p class="totalBudget"><strong>[@s.text name="preplanning.projectBudget.partner.totalBudget"/]</strong> US$ <span class="totalBudgetByPartner"> 0.00 </span></p>
                        <div class="handlediv"><br></div>
                      </div> 
                      
                      [#-- ------------------------------ Budget types ---------------------------- --]
                      <div id="" class="grid_1 ">&nbsp;</div>
                      <div id="" class="grid_2 budgetTypeHead">[@s.text name="preplanning.projectBudget.budget.W1W2Budget"/] </div>
                      <div id="" class="grid_2 budgetTypeHead">[@s.text name="preplanning.projectBudget.budget.W3BilateralBudget"/] </div>
                      <div id="" class="grid_2 budgetTypeHead suffix_2">[@s.text name="preplanning.projectBudget.budget.leveragedBudget"/] </div>
                      [#-- Budget type title --]
                      <div id="" class="grid_1 budgetTypeTitle">[@s.text name="preplanning.projectBudget.budget.title"/]</div>
                      [#-- W1 W2 --]                
                      <div id="" class="budgetContent W1_W2 grid_2">
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W1_W2'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </div>
                      [#-- W3/Bilateral Budget --] 
                      <div id="" class="budgetContent W3_BILATERAL grid_2">
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W3_BILATERAL'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </div> 
                      [#-- Leveraged Budget  --] 
                      <div id="" class="budgetContent LEVERAGED grid_2 suffix_2">
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-LEVERAGED'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </div> 
                      
                      [#-- ------------------------------  Partnerships types ------------------------ --]
                      <div id="" class="grid_1 ">&nbsp;</div>
                      <div id="" class="grid_2 budgetTypeHead">[@s.text name="preplanning.projectBudget.partnership.W1W2CollaboratorPartners"/]</div>
                      <div id="" class="grid_2 budgetTypeHead">[@s.text name="preplanning.projectBudget.partnership.W1W2CollaboratorOtherCGIAR"/]</div>
                      <div id="" class="grid_2 budgetTypeHead">[@s.text name="preplanning.projectBudget.partnership.W3BilateralCollaboratorPartners"/]</div>
                      <div id="" class="grid_2 budgetTypeHead">[@s.text name="preplanning.projectBudget.partnership.W3BilateralCollaboratorOtherCGIAR"/]</div>
                      [#-- Partnership type title --]
                      <div id="" class="grid_1 budgetTypeTitle">[@s.text name="preplanning.projectBudget.partnership.title"/]</div>
                      [#-- W1 W2 Collaborator Cost-Partners --]                
                      <div id="" class="budgetContent grid_2">
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W1_W2_PARTNERS'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </div>
                      [#-- W1 W2 Collaborator Cost-Other CGIAR Center --] 
                      <div id="" class="budgetContent grid_2">
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W1_W2_OTHER'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </div> 
                      [#-- W3/Bilateral Collaborator Cost-Partners  --] 
                      <div id="" class="budgetContent grid_2">
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W3_BILATERAL_PARTNERS'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </div> 
                      [#-- W3/Bilateral Collaborator Cost-Other CGIAR Center  --] 
                      <div id="" class="budgetContent grid_2">
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W3_BILATERAL_OTHERS'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </div> 
                      
                      [#-- ----------------------------- Gender types ------------------------------ --]
                      <div id="" class="grid_1 ">&nbsp;</div>
                      <div id="" class="grid_2 budgetTypeHead">[@s.text name="preplanning.projectBudget.gender.W1W2Budget"/] </div>
                      <div id="" class="grid_2 budgetTypeHead suffix_4">[@s.text name="preplanning.projectBudget.gender.W3BilateralBudget"/]  </div>
                      [#-- Gender type title --]
                      <div id="" class="grid_1 budgetTypeTitle">[@s.text name="preplanning.projectBudget.gender.title"/] </div>
                      [#-- W1 W2 Budget --]                
                      <div id="" class="budgetContent GENDER_W1_W2 grid_2">
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W1_W2_GENDER'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </div> 
                      [#-- W3/Bilateral Budget  --] 
                      <div id="" class="budgetContent GENDER_W3_BILATERAL grid_2 suffix_4">
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+project.leader.currentInstitution.id?c+'-W3_BILATERAL_GENDER'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </div> 
                      
                      
                    </div>
                  [/#if] 
                  [#list projectPartners as projectPartner ]
                    <div id="partnerBudget-${projectPartner_index}" class="partnerBudget row clearfix">
                      [#-- Partner Name --]
                      <div id="" class="grid_9 budgetPartnerTitle down">
                        ${projectPartner.partner.name}
                        <p class="totalBudget"><strong>[@s.text name="preplanning.projectBudget.partner.totalBudget"/]</strong>  US$ <span class="totalBudgetByPartner"> 0.00</span></p>
                        <div class="handlediv"><br></div>
                      </div> 
                      
                      [#-- ------------------------------ Budget types ---------------------------- --]
                      <div id="" class="grid_1 ">&nbsp;</div>
                      <div id="" class="grid_2 budgetTypeHead">[@s.text name="preplanning.projectBudget.budget.W1W2Budget"/] </div>
                      <div id="" class="grid_2 budgetTypeHead">[@s.text name="preplanning.projectBudget.budget.W3BilateralBudget"/] </div>
                      <div id="" class="grid_2 budgetTypeHead suffix_2">[@s.text name="preplanning.projectBudget.budget.leveragedBudget"/] </div>
                      [#-- Budget type title --]
                      <div id="" class="grid_1 budgetTypeTitle">
                        [@s.text name="preplanning.projectBudget.budget.title"/] 
                      </div>
                      [#-- W1 W2 --]                
                      <div id="" class="budgetContent W1_W2 grid_2">
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W1_W2'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </div>
                      [#-- W3/Bilateral Budget --] 
                      <div id="" class="budgetContent W3_BILATERAL grid_2">
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W3_BILATERAL'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </div> 
                      [#-- Leveraged Budget  --] 
                      <div id="" class="budgetContent LEVERAGED grid_2 suffix_2">
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-LEVERAGED'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </div> 
                      
                      [#-- ------------------------------  Partnerships types ------------------------ --]
                      <div id="" class="grid_1 ">&nbsp;</div>
                      <div id="" class="grid_2 budgetTypeHead">[@s.text name="preplanning.projectBudget.partnership.W1W2CollaboratorPartners"/]</div>
                      <div id="" class="grid_2 budgetTypeHead">[@s.text name="preplanning.projectBudget.partnership.W1W2CollaboratorOtherCGIAR"/]</div>
                      <div id="" class="grid_2 budgetTypeHead">[@s.text name="preplanning.projectBudget.partnership.W3BilateralCollaboratorPartners"/]</div>
                      <div id="" class="grid_2 budgetTypeHead">[@s.text name="preplanning.projectBudget.partnership.W3BilateralCollaboratorOtherCGIAR"/]</div>
                      [#-- Partnership type title --]
                      <div id="" class="grid_1 budgetTypeTitle">[@s.text name="preplanning.projectBudget.partnership.title"/]</div>
                      [#-- W1 W2 Collaborator Cost-Partners --] 
                      <div id="" class="budgetContent  grid_2">
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W1_W2_PARTNERS'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </div>
                      [#-- W1 W2 Collaborator Cost-Other CGIAR Center --] 
                      <div id="" class="budgetContent  grid_2">
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W1_W2_OTHER'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </div> 
                      [#-- W3/Bilateral Collaborator Cost-Partners  --] 
                      <div id="" class="budgetContent  grid_2">
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W3_BILATERAL_PARTNERS'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </div> 
                      [#-- W3/Bilateral Collaborator Cost-Other CGIAR Center  --] 
                      <div id="" class="budgetContent  grid_2">
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W3_BILATERAL_OTHERS'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </div> 
                      
                      [#-- ----------------------------- Gender types ------------------------------ --]
                      <div id="" class="grid_1 ">&nbsp;</div>
                      <div id="" class="grid_2 budgetTypeHead">[@s.text name="preplanning.projectBudget.gender.W1W2Budget"/] </div>
                      <div id="" class="grid_2 budgetTypeHead suffix_4">[@s.text name="preplanning.projectBudget.gender.W3BilateralBudget"/]  </div>
                      [#-- Gender type title --]
                      <div id="" class="grid_1 budgetTypeTitle">[@s.text name="preplanning.projectBudget.gender.title"/] </div>
                      [#-- W1 W2 Budget --]                
                      <div id="" class="budgetContent GENDER_W1_W2 grid_2">
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W1_W2_GENDER'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </div> 
                      [#-- W3/Bilateral Budget  --] 
                      <div id="" class="budgetContent GENDER_W3_BILATERAL grid_2 suffix_4">
                        [@customForm.input name="project.budgets[${counter}].amount" showTitle=false disabled=!fullEditable value="${mapBudgets[year?c+'-'+projectPartner.partner.id?c+'-W3_BILATERAL_GENDER'].amount?c}"/]
                        [@s.set var="counter" value="${counter+1}"/]
                      </div> 
                      
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
          [@s.submit type="button" name="next"][@s.text name="form.buttons.nextAndFinish" /][/@s.submit]
          [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
        </div>
      [/#if]
    [/#if]
  </article>
  
  [/@s.form]  
  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]