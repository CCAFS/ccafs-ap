[#ftl]
[#assign title = "Project Budget" /]
[#assign globalLibs = ["jquery", "noty","autoSave"] /]
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
  
  [@s.form action="projectBudget" cssClass="pure-form"]  
  <article class="halfContent" id="projectBudget">
    [#include "/WEB-INF/preplanning/projectPreplanningSubMenu.ftl" /]
    [#-- Title --]
    <h1 class="contentTitle">
    [@s.text name="preplanning.projectBudget.title" /]  
    </h1>
    [#-- Total CCAFS budget--]
    <div id="totalBudget" class="halfPartBlock">
      <h6>[@s.text name="preplanning.projectBudget.totalBudget" /]</h6>
      {project.totalBudget}
    </div>
    [#-- Total overall project budget:--]
    <div id="totalBudget" class="halfPartBlock">
      <h6>[@s.text name="preplanning.projectBudget.totalOverallBudget" /]</h6>
      {project.totalOverallBudget}
    </div>  
    [#-- Tertiary Menu - All years --] 
    <div id="budgetTables" class=""> 
      <ul>
        [#list allYears as year]
        <li><a href="#activityTables-${year_index}"> ${year?c} </a></li>   
        [/#list]  
        </ul>
      [#list allYears as year]
       <div id="activityTables-${year_index}" class="activityTable"> 
         <table class="fullPartBlock"> 
           <tr id="" class="row">
            [#-- Partner Name --]
            <td id="" class="halfPartBlock"><h6>[@s.text name="preplanning.projectBudget.partner" /]</h6></td> 
            [#-- W1 title --]
            <td id="" class="sixthPartBlock"><h6>[@s.text name="preplanning.projectBudget.w3" /]</h6></td> 
            [#-- W2 title --] 
            <td id="" class="sixthPartBlock"><h6>[@s.text name="preplanning.projectBudget.w2" /]</h6></td> 
            [#-- W3 title --] 
            <td id="" class="sixthPartBlock"><h6>[@s.text name="preplanning.projectBudget.w1" /]</h6></td> 
           </tr>
           <tr id="" class="row">
            [#-- Partner Leader Name --]
            <td id="" class="halfPartBlock">${projectLeader.currentInstitution.name} <strong>( [@s.text name="preplanning.projectBudget.partnerLead" /] )</strong> </td> 
            [#-- W1 --]
            <td id="" class="sixthPartBlock">[@customForm.input name="" showTitle=false /]</td> 
            [#-- W2 --] 
            <td id="" class="sixthPartBlock">[@customForm.input name="" showTitle=false /]</td> 
            [#-- W3  --] 
            <td id="" class="sixthPartBlock">[@customForm.input name="" showTitle=false /]</td> 
           </tr>
         [#list projectPartners as projectPartner ] 
          <tr id="partnerBudget-${projectPartner_index}" class="row">
            [#-- Partner Name --]
            <td id="" class="halfPartBlock">${projectPartner.partner.name}</td> 
            [#-- W1 --]
            <td id="" class="sixthPartBlock">[@customForm.input name="" showTitle=false /]</td> 
            [#-- W2 --] 
            <td id="" class="sixthPartBlock">[@customForm.input name="" showTitle=false /]</td> 
            [#-- W3  --] 
            <td id="" class="sixthPartBlock">[@customForm.input name="" showTitle=false /]</td> 
          </tr>
        [/#list] 
        </table>  
        <div class="partnerListMsj">
          [@s.text name="preplanning.projectBudget.partnerNotList" /]
          <a href="[@s.url action='partners' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]"> 
            [@s.text name="preplanning.projectBudget.partnersLink" /] 
          </a>
        </div>
       </div>   
      [/#list]
    </div> <!-- End budgetTables --> 
    [#-- Bilateral --]
    <div id="bilateral" class="halfPartBlock halfPanel">
    <h6>[@s.text name="preplanning.projectBudget.bilateral" /]</h6>
      <table id="bilateralTable">
        <tr>
          [#-- Partner Name --]
          <td id="" class="name"><h6>[@s.text name="preplanning.projectBudget.partner" /]</h6></td> 
          [#-- Amount --]
          <td id="" class="amount"></td>
        </tr>   
      [#list projectPartners as projectPartner]
        <tr id="bilateralPartner-${projectPartner_index}" class="bilateralPartner row">
          [#-- Partner Name --]
          <td id="" class="name">${projectPartner.partner.name}</td> 
          [#-- W1 --]
          <td id="" class="amount">[@customForm.input name="" showTitle=false /]</td>
        </tr>   
      [/#list]
      </table>
      <div class="partnerListMsj">
        [@s.text name="preplanning.projectBudget.partnerNotList" /]
        <a href="[@s.url action='partners' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]"> 
          [@s.text name="preplanning.projectBudget.partnersLink" /] 
        </a>
      </div>
    </div>
    [#-- Leveraged --]
    <div id="leveraged" class="halfPartBlock halfPanel">
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
           <input type="hidden" id="id" name="leveragedInstitutions[${partner_index}].budgets.id" value="${partner.id}">
           [@customForm.input name="leveragedInstitutions[${partner_index}].budgets.amount" showTitle=false/] 
           <img class="removeButton" src="${baseUrl}/images/global/icon-remove.png" />
          </div> 
        </div>  
      [/#list] 
      [#-- Add Leveraged --]
      <div class="fullBlock addLeveragedBlock">
        [@customForm.select name="" value="" showTitle=false listName="leveragedInstitutions" keyFieldName="id"  displayFieldName="name" addButton=true className="leveraged" /]
      </div>
    </div>
    
  </article>
  [/@s.form]  
  
  [#-- Partner Leveraged Template --] 
  <div id="leveragedPartnerTemplate" class="row" style="display:none"> 
    <div id="partnerName" class="name"> Partner Name </div> 
    <div id="amount" class="amount">
      <input type="hidden" id="id" name="leveragedInstitutions[0].budgets.id" value="">
      <div class="input">
        <input type="text" id="amount" name="leveragedInstitutions[0].budgets.amount" value="">
      </div>
      <img class="removeButton" src="${baseUrl}/images/global/icon-remove.png">
    </div> 
  </div>
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]