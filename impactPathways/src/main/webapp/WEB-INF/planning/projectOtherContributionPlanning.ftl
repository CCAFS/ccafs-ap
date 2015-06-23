[#ftl]
[#assign title = "Impact Pathways - Other Contribution" /]
[#assign globalLibs = ["jquery", "noty", "cytoscape", "qtip","cytoscapePanzoom"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/ipGraph.js", "${baseUrl}/js/planning/ipOtherContributions.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "outcomes" /]
[#assign currentSubStage = "otherContributions" /]


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
    [#if !canEdit]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"]
          [@s.text name="planning.project" /]: ${project.composedId} - [@s.param][@s.text name="planning.impactPathways.otherContributions.title"/][/@s.param]
        [/@s.text]
      </p>
    [/#if]
    <div class="borderBox">
      [#-- Button for edit this section --]
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [/#if]
      <h1 class="contentTitle">[@s.text name="planning.impactPathways.otherContributions.title" /] </h1> 
      
      [#-- How are contributing to other CCAFS IP --]
      <div class="fullPartBlock">
        [@customForm.textArea name="project.ipOtherContribution.contribution" i18nkey="planning.impactPathways.otherContributions.contribution" editable=editable /]  
      </div>
      [#-- Contribution to another center activity --]
      <div class="fullPartBlock">
        [@customForm.textArea name="project.ipOtherContribution.additionalContribution" i18nkey="planning.impactPathways.otherContributions.additionalcontribution" editable=editable /]  
      </div>
      
      [#-- Collaborating with other CRPs --]
      <div class="fullPartBlock">      
        <div class="crpContribution panel tertiary">
          <div class="panel-head">[@customForm.text name="planning.impactPathways.otherContributions.collaboratingCRPs" readText=!editable /]</div> 
          <div class="panel-body"> 
            <ul class="list">  
              [#list list as crp]
                <li class="clearfix [#if !crp_has_next]last[/#if]">
                  <input class="id" type="hidden" name="project.ipOtherContribution.crps[${crp_index}].id" value="${crp.id}" />
                  <span class="name">${crp.name}</span> 
                  [#if editable]<span class="listButton remove">[@s.text name="form.buttons.remove" /]</span>[/#if]
                </li>
              [/#list]
            </ul>
            [#if editable]
              [@customForm.select name="" label="" disabled=!canEdit i18nkey="" listName="crps" keyFieldName="id"  displayFieldName="getComposedName()" className="crpsSelect" value="" /]
            [/#if] 
          </div>
        </div> 
    </div>
      
    </div>
    <!-- internal parameter -->
    <input name="projectID" type="hidden" value="${project.id?c}" />
    [#if project.ipOtherContribution?has_content]
      <input name="project.ipOtherContribution.id" type="hidden" value="${project.ipOtherContribution.id}"/>
    [/#if]
    [#if editable]
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