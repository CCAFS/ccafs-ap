[#ftl]
[#assign title][@s.text name="preplanning.outcomes.title" /]  [/#assign]
[#assign globalLibs = ["jquery", "noty", "autoSave", "cytoscape", "qtip","cytoscapePanzoom"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js","${baseUrl}/js/global/ipGraph.js", "${baseUrl}/js/preplanning/outcomesPreplanning.js"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "impactPathways" /]
[#assign currentStage = "outcomes" /] 

[#assign breadCrumb = [
  {"label":"preplanning", "nameSpace":"pre-planning", "action":"intro"},
  {"label":"impactPathways", "nameSpace":"pre-planning", "action":"outcomes", "param":"programID=${program.id}"},
  {"label":"outcomes", "nameSpace":"pre-planning", "action":"outcomes", "param":"programID=${program.id}"}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/preplanning/macros/indicatorTemplate.ftl" as indicatorTemplate/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="preplanning.outcomes.help" /] </p>
    <p> [@s.text name="preplanning.outcomes.help2" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl" /]
  
  [@s.form action="outcomes" cssClass="pure-form"]
    <article class="halfContent" id="outcomes" class="impactPathway">
      [#include "/WEB-INF/preplanning/ipPreplanningSubMenu.ftl" /]

      [#if program.type.id == flagshipProgramTypeID]
        <h1 class="contentTitle">  [@s.text name="preplanning.outcomes.title" /]  </h1>
      [#elseif program.type.id == regionProgramTypeID]
        <h1 class="contentTitle">  [@s.text name="preplanning.outcomes.titleRPL" /]  </h1>
      [/#if]

      [#if outcomes?has_content]
        [#list outcomes as outcome]
          <div id="outcomesBlock" class="outcome borderBox">
            [#-- Outcome identifier --]
            <input type="hidden" name="outcomes[${outcome_index}].id" value="${outcome.id}" />
            <input type="hidden" name="outcomes[${outcome_index}].program.id" value="${program.id}" />
            <input type="hidden" name="outcomes[${outcome_index}].type.id" value="${elementTypeID}" />

            [#-- Title --]
            [#assign outcomeDescription]
              [#if program.type.id == flagshipProgramTypeID]
                [@s.text name="preplanning.outcomes.outcome"] 
                  [@s.param name="0"]${program.acronym}[/@s.param] 
                  [@s.param name="1"]<span id="elementIndex">${outcome_index+1}</span>[/@s.param] 
                [/@s.text]
              [#elseif securityContext.RPL]
                [@s.text name="preplanning.outcomes.outcomeRPL"] 
                  [@s.param name="0"]${program.acronym}[/@s.param] 
                  [@s.param name="1"]<span id="elementIndex">${outcome_index+1}</span>[/@s.param] 
                [/@s.text]
              [/#if]
            [/#assign]
            <legend>${outcomeDescription}</legend> 
            [@customForm.textArea name="outcomes[${outcome_index}].description" i18nkey="preplanning.outcomes.outcomeDescription" required=true /]
            
            [#if program.type.id == flagshipProgramTypeID]
            
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
              
              <div id="idosBlock" class="contentElements">
                <div class="itemIndex">[@s.text name="preplanning.outcomes.idos" /]</div>
                [#-- CCAFS IDOs --]
                [#if ccafsIDOs?has_content]
                  <h3 class="contentSubTitle">[@s.text name="preplanning.outcomes.idos.ccafs" /]</h3>
                  [#list ccafsIDOs as ido]
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
                [/#if]  
                [#-- Consortium IDOs --]
                [#if consortiumIDOs?has_content] 
                   <h3 class="contentSubTitle">[@s.text name="preplanning.outcomes.idos.consortium" /]</h3>
                    [#list consortiumIDOs as ido]
                      <div id="idoBlock-${ccafsIDOs?size+ido_index}" class="ido"> 
                      [#if outcome.contributesToIDs?seq_contains(ido.id) ] 
                         [#assign idoCheck = "checked='checked'" /]
                         [#assign indicatorsVisible = "style='display:block'" /]
                      [#else]  
                         [#assign idoCheck = "" /]
                         [#assign indicatorsVisible = "style='display:none'" /]
                      [/#if]
                          <input  id="ido-${ccafsIDOs?size+ido_index}" class="idosCheckbox" type="checkbox" name="outcomes[0].contributesTo" value="${ido.id}" ${idoCheck}>
                          <label for="ido-${ccafsIDOs?size+ido_index}" class="checkboxLabel" >${ido.description}</label>
                      [#if ido.indicators?has_content]
                        <div id="indicatorsBlock-${ccafsIDOs?size+ido_index}" class="idosIndicators checkboxGroup vertical" ${indicatorsVisible}> 
                          [@s.checkboxlist name="idoIndicator" list="idos[${ccafsIDOs?size+ido_index}].indicators" listKey="id" listValue="description" value="outcomes[0].parentIndicatorsIDs" cssClass="indicatorsCheckbox" /]
                        </div>
                      [/#if]
                      </div>
                    [/#list]
                [/#if] 
              [/#if]
              [#if securityContext.RPL]
                [#-- FPL Indicators --]
                <div id="idosBlock" class="contentElements">
                  <div class="itemIndex">[@s.text name="preplanning.outcomes.indicatorsFPL" /]</div>
                  <div id="indicatorsFPLBlock" class="indicatorsFPL checkboxGroup vertical clearfix"> 
                    [#if fplOutcomesIndicators?has_content]
                      [#list fplOutcomesIndicators as parentIndicator]
                        <div class="fplIndicatorBlock">
                          [#if outcome.getIndicatorByParentID(parentIndicator.id)?has_content]
                            [#assign indicator = outcome.getIndicatorByParentID(parentIndicator.id)/]
                            <input  type="hidden" name="outcomes[0].indicators[${parentIndicator_index+1}].id" value="${indicator.id}" />
                            <input  id="indicatorFPL-${parentIndicator_index}" class="indicatorFPLCheckbox" type="checkbox" name="outcomes[0].indicators[${parentIndicator_index+1}].parent" value="${parentIndicator.id}" checked="checked" />
                            <label for="indicatorFPL-${parentIndicator_index}" class="checkboxLabel" >
                              [#if parentIndicator.description?has_content]
                                ${parentIndicator.description} 
                              [/#if]
                            </label>
                            <div class="target fourthPartBlock">
                              [#assign targetLabel][@s.text name="preplanning.outcomes.target" /] (${parentIndicator.target})[/#assign]
                              [@customForm.input name="outcomes[0].indicators[${parentIndicator_index+1}].target" value="${indicator.target}"  i18nkey="${targetLabel}" /]
                            </div>
                          [#else]
                            <input  type="hidden" disabled="disabled" name="outcomes[0].indicators[${parentIndicator_index+1}].id" value="-1" />
                            <input  id="indicatorFPL-${parentIndicator_index}" class="indicatorFPLCheckbox" type="checkbox" name="outcomes[0].indicators[${parentIndicator_index+1}].parent" value="${parentIndicator.id}" />
                            <label for="indicatorFPL-${parentIndicator_index}" class="checkboxLabel" >
                              [#if parentIndicator.description?has_content]
                                ${parentIndicator.description}
                              [/#if]
                            </label>
                            <div class="target fourthPartBlock">
                              [@customForm.input name="outcomes[0].indicators[${parentIndicator_index+1}].target" value=""  i18nkey="preplanning.outcomes.target" disabled=true /]
                            </div>
                          [/#if]
                        </div>
                      [/#list]
                    [#else]
                      [#-- There are no flagships indicators to show yet. --]
                      [@s.text name="preplanning.outcomes.noIndicatorsFPL" /] 
                    [/#if]
                  </div>
                </div>
              [/#if]
            </div>
        [/#list]
      [#else]
        <div id="outcomesBlock" class="outcome borderBox">
          [#-- Outcome identifier --]
          <input type="hidden" name="outcomes[0].id" value="-1" /> 
          <input type="hidden" name="outcomes[0].program.id" value="${program.id}" />
          <input type="hidden" name="outcomes[0].type.id" value="${elementTypeID}" />
          [#-- Title --]
          [#assign outcomeDescription]
            [@s.text name="preplanning.outcomes.outcome"] 
              [@s.param name="0"]${program.acronym}[/@s.param] 
              [@s.param name="1"]<span id="elementIndex">1</span>[/@s.param] 
            [/@s.text]
          [/#assign]
          <legend>${outcomeDescription}</legend> 
          [@customForm.textArea name="outcomes[0].description" i18nkey="preplanning.outcomes.outcomeDescription" required=true /] 
          [#-- Indicators --] 
          <div class="contentElements outcomeIndicatorsBlock">
            <div class="itemIndex">[@s.text name="preplanning.outcomes.indicators" /] </div>
              [#@indicatorTemplate.outcomes template=true i18nkey="preplanning.outcomes.indicators.description" show_remove_link=false /]
          </div>
          [#if program.type.id == flagshipProgramTypeID]
            [#-- IDOs --]
            [#if idos?has_content]
              <div id="idosBlock" class="contentElements">
                <div class="itemIndex">[@s.text name="preplanning.outcomes.idos" /]</div>
                [#list idos as ido]
                  <div id="idoBlock-${ido_index}" class="ido">  
                    <input  id="ido-${ido_index}" class="idosCheckbox" type="checkbox" name="outcomes[0].contributesTo" value="${ido.id}" />
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
      [#-- 
      <div class="buttons"> 
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
      --]
    </article>
  [/@s.form] 
  
[#if program.type.id == flagshipProgramTypeID]
  <input type="hidden" id="isFPL" value="${program.id}" />
[/#if]  
[#-- Outcome 2025 template --]
<div id="outcomeTemplate" class="outcome borderBox" style="display:none">
  [#-- Outcome identifier --]
  <input type="hidden" name="id" value="-1" /> 
  <input type="hidden" id="programID" value="${programID}" />
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