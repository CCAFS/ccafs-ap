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
       [#assign partners = ["Partner 1", "Partner 2", "Partner 3", "Partner 4", "Partner 5"] /]
         <div id="" class="row">
          [#-- Partner Name --]
          <div id="" class="thirdPartBlock">
           <h6>[@s.text name="preplanning.projectBudget.partner" /]</h6>
          </div> 
          [#-- W1 title --]
          <div id="" class="fourthPartBlock">
            <h6>[@s.text name="preplanning.projectBudget.w3" /]</h6>
          </div> 
          [#-- W2 title --] 
          <div id="" class="fourthPartBlock">
             <h6>[@s.text name="preplanning.projectBudget.w2" /]</h6>
          </div> 
          [#-- W3 title --] 
          <div id="" class="fourthPartBlock">
             <h6>[@s.text name="preplanning.projectBudget.w1" /]</h6>
          </div> 
         </div>
         [#list partners as partner ] 
          <div id="partnerBudget-${partner_index}" class="row">
          [#-- Partner Name --]
          <div id="" class="thirdPartBlock">
           {partner.name}
          </div> 
          [#-- W1 --]
          <div id="" class="fourthPartBlock">
           [@customForm.input name=""  i18nkey="" /]
          </div> 
          [#-- W2 --] 
          <div id="" class="fourthPartBlock">
            [@customForm.input name=""  i18nkey="" /]
          </div> 
          [#-- W3  --] 
          <div id="" class="fourthPartBlock">
            [@customForm.input name=""  i18nkey="" /]
          </div> 
         </div> 
        [/#list] 
       </div>   
      [/#list]
    </div> <!-- End budgetTables -->
    [#assign partners = ["Partner 1", "Partner 2"] /]
    [#-- Bilateral --]
    <div id="bilateral" class="halfPartBlock halfPanel">
    <h6>[@s.text name="preplanning.projectBudget.bilateral" /]</h6>
      [#list partners as partner]
        <div id="bilateralPartner-${partner_index}" class="row">
          [#-- Partner Name --]
          <div id="" class="halfPartBlock">
           {partner.name}
          </div> 
          [#-- W1 --]
          <div id="" class="thirdPartBlock">
           [@customForm.input name=""  i18nkey="" /]
          </div>
        </div>   
      [/#list] 
    </div>
    [#-- Leveraged --]
    <div id="leveraged" class="halfPartBlock halfPanel">
    <h6>[@s.text name="preplanning.projectBudget.leveraged" /]</h6>
      [#list partners as partner]
        <div id="leveragedPartner-${partner_index}" class="row">
          [#-- Partner Name --]
          <div id="" class="halfPartBlock">
           {partner.name}
          </div> 
          [#-- W1 --]
          <div id="" class="thirdPartBlock">
           [@customForm.input name=""  i18nkey="" /]
          </div>
        </div>  
      [/#list] 
    </div>
    
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]