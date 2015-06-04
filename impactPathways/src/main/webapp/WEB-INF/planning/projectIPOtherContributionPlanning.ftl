[#ftl]
[#assign title = "Impact Pathways - Other Contribution" /]
[#assign globalLibs = ["jquery", "noty", "cytoscape", "qtip","cytoscapePanzoom"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/ipGraph.js", "${baseUrl}/js/planning/ipOtherContributions.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "ipOtherContributions" /]


[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"planning", "action":"projectsList"},
  {"label":"description", "nameSpace":"planning/projects", "action":""}
] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.impactPathways.otherContributions.help" /] </p>
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
  
  [@s.form action="ipOtherContributions" cssClass="pure-form"]  
  <article class="halfContent" id=""> 
  [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
  [#include "/WEB-INF/planning/projectIP-planning-sub-menu.ftl" /]
  [#-- Informing user that he/she doesnt have enough privileges to edit. See GrantActivityPlanningAccessInterceptor--]
  [#if !saveable]
    <p class="readPrivileges">
      [@s.text name="saving.read.privileges"]
        [@s.text name="planning.project" /]: ${project.composedId} - [@s.param][@s.text name="planning.impactPathways.otherContributions.title"/][/@s.param]
      [/@s.text]
    </p>
  [/#if]
  <div class="borderBox">
    <h6 class="contentTitle">
    [@customForm.textArea name="project.ipOtherContribution.contribution" i18nkey="planning.impactPathways.otherContributions.contribution" /]  
    </h6> <br/>
    <h6 class="contentTitle">
    [@customForm.textArea name="project.ipOtherContribution.additionalContribution" i18nkey="planning.impactPathways.otherContributions.additionalcontribution" /]  
    </h6> 
    </div>
     <!-- internal parameter -->
    <input name="projectID" type="hidden" value="${project.id?c}" />
    [#if project.ipOtherContribution?has_content]
      <input name="project.ipOtherContribution.id" type="hidden" value="${project.ipOtherContribution.id}"/>
    [/#if]
    [#if saveable]
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
    [/#if]
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]