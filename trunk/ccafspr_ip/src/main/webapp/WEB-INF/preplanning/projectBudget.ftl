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
  	[#-- Tertiary Menu - All years --]
  	<nav id="tertiaryMenu">  
      <div id="budgetTables" class="">
        <ul>
        [#list allYears as year]
        <li><a href="#activityTables-${year_index}"> ${year?c} </a></li>   
        [/#list]  
        </ul> 
        [#list allYears as year]
         <div id="activityTables-${year_index}" class="activityTable">
          Conntent by  ${year?c}
          
          [#--  --]
          <div id="" class="thirdPartBlock">
           
          </div> 
          [#--  --]
          <div id="" class="thirdPartBlock">
           
          </div> 
          [#--  --] 
          <div id="" class="thirdPartBlock">
            
          </div> 
          [#--   --] 
          <div id="" class="thirdPartBlock">
            
          </div> 
         </div>
        [/#list]
      </div> <!-- End tertiaryMenu -->
    </nav> <!-- End container-->
    
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]