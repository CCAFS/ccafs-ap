[#ftl]
[#assign title][@s.text name="preplanning.outcomes.title" /]  [/#assign]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/preplanning/outcomesPreplanning.js"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "impactPathways" /]
[#assign currentStage = "outcomes" /] 

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/preplanning/macros/indicatorTemplate.ftl" as indicatorTemplate/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="preplanning.outcomes.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl" /]
  
  [@s.form action="outcomes" cssClass="pure-form"]
  <article class="halfContent" id="outcomes" class="impactPathway">
  	[#include "/WEB-INF/preplanning/ipPreplanningSubMenu.ftl" /]
    <h1 class="contentTitle">
      [@s.text name="preplanning.outcomes.title" /] [#-- Pending to add the leader acronym, so it should say something like: Flagship 1 - Outcome 2025 --]
    </h1>
    
    <div id="outcomesBlock" class="outcome borderBox">
      [#if outcomes?has_content]
        [#list outcomes as outcome]
          [#-- Outcome identifier --]
          <input type="hidden" name="outcomes[${outcome_index}].id" value="${outcome.id}" />
          <input type="hidden" name="outcomes[${outcome_index}].program.id" value="${currentUser.currentInstitution.program.id}" />
          <input type="hidden" name="outcomes[${outcome_index}].type.id" value="${elementTypeID}" />
          [#-- Title --]
          [@customForm.textArea name="outcomes[${outcome_index}].description" i18nkey="preplanning.outcomes.outcome" required=true /]
          <div class="contentElements outcomeIndicatorsBlock"> 
            <div class="itemIndex">[@s.text name="preplanning.outcomes.indicators" /]</div>
            [#if outcome.indicators?has_content]
              [#-- Indicators --]
              [#list outcome.indicators as indicator]
                [#if !indicator.parent?has_content]
                  [@indicatorTemplate.outcomes outcome_index="${outcome_index}" indicator_index="${indicator_index}" value="${indicator.id}" i18nkey="preplanning.outcomes.indicators.description" show_remove_link=false /]
                [#else]
              tiene parent? ${indicator.parent}
                [/#if]
              [/#list]
            [#else]
              [@indicatorTemplate.outcomes outcome_index="${outcome_index}" i18nkey="preplanning.outcomes.indicators.description" show_remove_link=false /]
            [/#if]
            
            [#-- Add Indicator Button --]
            [#-- So far, there will be only 1 indicator per outcome 2025 --]
            [#-- 
            <div class="fullBlock" id="addIndicatorBlock">
              [@customForm.button i18nkey="preplanning.outcomes.addIndicator" class="addButton" /]
            </div>
            --] 
          </div>
        [/#list]
      [#else]
          [#-- Outcome identifier --]
          <input type="hidden" name="outcomes[0].id" value="-1" /> 
          <input type="hidden" name="outcomes[0].program.id" value="${currentUser.currentInstitution.program.id}" />
          <input type="hidden" name="outcomes[0].type.id" value="${elementTypeID}" />
          [#-- Title --]
          [@customForm.textArea name="outcomes[0].description" i18nkey="preplanning.outcomes.outcome" required=true /]  
          <div class="contentElements outcomeIndicatorsBlock">
            <div class="itemIndex">[@s.text name="preplanning.outcomes.indicators" /] </div>
              [@indicatorTemplate.outcomes /] 
              [#-- Add Indicator Button --]
              [#-- So far, there will be only 1 indicator per outcome 2025 --]
              [#-- 
                <div class="fullBlock" id="addIndicatorBlock">
                  [@customForm.button i18nkey="preplanning.outcomes.addIndicator" class="addButton" /]
                </div>
              --] 
            </div>
          </div> 
      [/#if]
    [#-- IDOs --]
    [#if idos?has_content]
      <div id="idosBlock" class="contentElements">
        <div class="itemIndex">[@s.text name="preplanning.outcomes.idos" /]</div>
        [#list idos as ido]
          <div id="idoBlock-${ido_index}" class="ido">
          <input  id="ido-${ido_index}" class="idosCheckbox" type="checkbox" name="idos[${ido_index}].id" value="${ido.id}">
          <label for="ido-${ido_index}" class="checkboxLabel">${ido.description}</label>
          [#if ido.indicators?has_content]
            <div id="indicatorsBlock-${ido_index}" class="idosIndicators checkboxGroup vertical"> 
              [@s.checkboxlist name="outcomes[0].indicators.parent" list="idos[${ido_index}].indicators" listKey="id" listValue="description" value="outcomes[0].indicators" cssClass="indicatorsCheckbox" /]
            </div>
          [/#if]
          </div>
        [/#list]
      </div>  
    [/#if]
    </div>
    
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
     
  </article>
  [/@s.form] 
[#-- Outcome 2025 template --]
<div id="outcomeTemplate" class="outcome borderBox" style="display:none">
  [#-- Outcome identifier --]
  <input type="hidden" name="id" value="-1" /> 
  <input type="hidden" id="programID" value="${currentUser.currentInstitution.program.id}" />
  <input type="hidden" id="typeID" value="${elementTypeID}" />
  [#-- Title --]
  [@customForm.textArea name="description" i18nkey="preplanning.outcomes.outcome" required=true /] 
  <div class="contentElements">
    <div class="itemIndex">[@s.text name="preplanning.outcomes.indicators" /] </div>
    [#-- Indicator template --] 
    [@indicatorTemplate.outcomes template=true /]
  </div>
</div>
[#-- End Outcome 2025 template --]   
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]