[#ftl]
[#assign title = "Main information" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/mainInformation.js"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "outcomes" /]
[#assign currentStage = "outcomes" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.mainInformation.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl" /]
  
  [@s.form action="mainInformation"]  
  <article class="halfContent" id="mainInformation">
  	[#include "/WEB-INF/preplanning/ipPreplanningSubMenu.ftl" /]
    <h1 class="contentTitle">
    [@s.text name="preplanning.outcome.title" /]  
    </h1>
    
    
    [#-- Outcome 2025 template --]
    <div id="outcomeTemplate" style="">
      [#-- Objective identifier --]
      <input type="hidden" name="id" value="-1" />
      
      [#-- remove link --]      
      <div class="removeLink">            
        <img src="${baseUrl}/images/global/icon-remove.png" />
        <a id="removeObjective" href="" class="removeObjective">
          [@s.text name="planning.objectives.removeObjective" /]
        </a>
      </div>      
      [#-- Title --]
      [@customForm.textArea name="description" i18nkey="preplanning.outcome.title" required=true /] 
      <hr>
    </div>
     
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]