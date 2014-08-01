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
      [#if outcomes?has_content]
        [#list outcomes as outcome]
        <div id="outcomesBlock" class="outcome borderBox">
          [#-- Outcome identifier --]
          <input type="hidden" name="outcomes[${outcome_index}].id" value="${outcome.id}" />
          <input type="hidden" name="outcomes[${outcome_index}].program.id" value="${currentUser.currentInstitution.program.id}" />
          <input type="hidden" name="outcomes[${outcome_index}].type.id" value="${elementTypeID}" />
          [#-- Title --]
          [#assign outcomeDescription]
            [@s.text name="preplanning.outcomes.outcome"] 
              [@s.param name="0"]${currentUser.currentInstitution.program.acronym}[/@s.param] 
              [@s.param name="1"]<span id="elementIndex">${outcome_index+1}</span>[/@s.param] 
            [/@s.text]
          [/#assign]
          <legend>${outcomeDescription}</legend> 
          [@customForm.textArea name="outcomes[${outcome_index}].description" i18nkey="preplanning.outcomes.outcomeDescription" required=true /]
          [#-- Indicators --]
          <div class="contentElements outcomeIndicatorsBlock"> 
            <div class="itemIndex">[@s.text name="preplanning.outcomes.indicators" /]</div>
            [#if outcome.getIndicators(false)?has_content]
              [#list outcome.getIndicators(false) as indicator]
                  [@indicatorTemplate.outcomes outcome_index="${outcome_index}" indicator_index="${indicator_index}" value="${indicator.id}" i18nkey="preplanning.outcomes.indicators.description" show_remove_link=false /] 
              [/#list]
            [#else]
              [@indicatorTemplate.outcomes template=true i18nkey="preplanning.outcomes.indicators.description" show_remove_link=false /]
            [/#if]
            
            [#-- Add Indicator Button --]
            [#-- So far, there will be only 1 indicator per outcome 2025 --]
            [#-- 
            <div class="fullBlock" id="addIndicatorBlock">
              [@customForm.button i18nkey="preplanning.outcomes.addIndicator" class="addButton" /]
            </div>
            --] 
          </div> 
          [#if currentUser.FPL]
            [#-- IDOs --]
            [#if idos?has_content]
              <div id="idosBlock" class="contentElements">
                <div class="itemIndex">[@s.text name="preplanning.outcomes.idos" /]</div>
                [#list idos as ido]
                  <div id="idoBlock-${ido_index}" class="ido"> 
                  [#if outcome.contributesToIDs?seq_contains(ido.id) ] 
                     [#assign idoCheck = "checked='checked'" /]
                     [#assign indicatorsVisible = "style='display:block'" /]
                  [#else]  
                     [#assign idoCheck = "" /]
                     [#assign indicatorsVisible = "style='display:none'" /]
                  [/#if]
                      <input  id="ido-${ido_index}" class="idosCheckbox" type="checkbox" name="outcomes[0].contributesTo" value="${ido.id}" ${idoCheck}>
                      <label for="ido-${ido_index}" class="checkboxLabel" >${ido.description}</label>
                  [#if ido.indicators?has_content]
                    <div id="indicatorsBlock-${ido_index}" class="idosIndicators checkboxGroup vertical" ${indicatorsVisible}> 
                      [@s.checkboxlist name="idoIndicator" list="idos[${ido_index}].indicators" listKey="id" listValue="description" value="outcomes[0].parentIndicatorsIDs" cssClass="indicatorsCheckbox" /]
                    </div>
                  [/#if]
                  </div>
                [/#list]
              </div>  
            [/#if] 
          [/#if] 
        </div>  
        [/#list]
      [#else]
      <div id="outcomesBlock" class="outcome borderBox">
          [#-- Outcome identifier --]
          <input type="hidden" name="outcomes[0].id" value="-1" /> 
          <input type="hidden" name="outcomes[0].program.id" value="${currentUser.currentInstitution.program.id}" />
          <input type="hidden" name="outcomes[0].type.id" value="${elementTypeID}" />
          [#-- Title --]
          [#assign outcomeDescription]
            [@s.text name="preplanning.outcomes.outcome"] 
              [@s.param name="0"]${currentUser.currentInstitution.program.acronym}[/@s.param] 
              [@s.param name="1"]<span id="elementIndex">1</span>[/@s.param] 
            [/@s.text]
          [/#assign]
          <legend>${outcomeDescription}</legend> 
          [@customForm.textArea name="outcomes[0].description" i18nkey="preplanning.outcomes.outcomeDescription" required=true /] 
          [#-- Indicators --] 
          <div class="contentElements outcomeIndicatorsBlock">
            <div class="itemIndex">[@s.text name="preplanning.outcomes.indicators" /] </div>
              [@indicatorTemplate.outcomes template=true i18nkey="preplanning.outcomes.indicators.description" show_remove_link=false /]
          </div>
          [#if currentUser.FPL]
            [#-- IDOs --]
            [#if idos?has_content]
              <div id="idosBlock" class="contentElements">
                <div class="itemIndex">[@s.text name="preplanning.outcomes.idos" /]</div>
                [#list idos as ido]
                  <div id="idoBlock-${ido_index}" class="ido">  
                      <input  id="ido-${ido_index}" class="idosCheckbox" type="checkbox" name="outcomes[0].contributesTo" value="${ido.id}">
                      <label for="ido-${ido_index}" class="checkboxLabel" >${ido.description}</label>
                  [#if ido.indicators?has_content]
                    <div id="indicatorsBlock-${ido_index}" class="idosIndicators checkboxGroup vertical"> 
                      [@s.checkboxlist name="idoIndicator" list="idos[${ido_index}].indicators" listKey="id" listValue="description" value="outcomes[0].parentIndicatorsIDs" cssClass="indicatorsCheckbox" /]
                    </div>
                  [/#if]
                  </div>
                [/#list]
               </div>
            [/#if] 
          [/#if] 
        </div>    
      [/#if]
    
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
  [#-- Indicator template --] 
  <div class="contentElements">
    <div class="itemIndex">[@s.text name="preplanning.outcomes.indicators" /] </div> 
    [@indicatorTemplate.outcomes template=true /]
  </div>
</div>
[#-- End Outcome 2025 template --]   
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]