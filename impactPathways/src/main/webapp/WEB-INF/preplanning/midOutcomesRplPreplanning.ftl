[#ftl]

[#assign title = "Outcomes 2019 - Preplanning" /]
[#assign globalLibs = ["jquery", "noty", "chosen", "cytoscape", "qtip","cytoscapePanzoom"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/ipGraph.js", "${baseUrl}/js/preplanning/midOutcomesRPLPreplanning.js"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "impactPathways" /]
[#assign currentStage = "midOutcomes" /]

[#assign breadCrumb = [
  {"label":"preplanning", "nameSpace":"pre-planning", "action":"intro"},
  {"label":"impactPathways", "nameSpace":"pre-planning", "action":"outcomes", "param":"programID=${program.id}"},
  {"label":"midOutcomes", "nameSpace":"pre-planning", "action":"midOutcomesRPL", "param":"programID=${program.id}"}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/preplanning/macros/indicatorTemplate.ftl" as indicatorTemplate/]
[#import "/WEB-INF/preplanning/macros/contributeTemplate.ftl" as contributeTemplate/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="preplanning.midOutcomesRPL.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl" /]
   
  [@s.form action="midOutcomesRPL" cssClass="pure-form"]  
    <article class="halfContent" id="midOutcomes">
    [#include "/WEB-INF/preplanning/ipPreplanningSubMenu.ftl" /]
    <h1 class="contentTitle">
    [@s.text name="preplanning.midOutcomesRPL.title" /]  
    </h1>

    [#-- If there are no Outcomes 2025, users can not add midOutcomes --]
    [#if outcomesList?has_content]
      <div id="MidOutcomeBlocks"> 
          [#if midOutcomes?has_content]
            [#list midOutcomes as midOutcome]
              <div class="midOutcome borderBox" id="midOutcome-${midOutcome_index}">
                [#-- Mid outcome identifier --]
                <input type="hidden" id="id" name="midOutcomes[${midOutcome_index}].id" value="${midOutcome.id}" />
                <input type="hidden" id="midOutcomeProgramId" name="midOutcomes[${midOutcome_index}].program.id" value="${program.id}" />
                <input type="hidden" id="midOutcomeTypeId" name="midOutcomes[${midOutcome_index}].type.id" value="${elementTypeID}" />
                [#-- Contribution to the regional vision 2025 --]
                <input type="hidden" name="midOutcomes[${midOutcome_index}].contributesTo" value="${outcomesList[0].id}" /> 
                [#-- Remove Output --]
                <div id="removeMidOutcome" class="removeMidOutcome removeElement removeLink" title="[@s.text name="preplanning.midOutcomesRPL.removeMidOutcome" /]"></div>
                [#-- Title --]
                [#assign midOutcomeDescription]
                  [@s.text name="preplanning.midOutcomesRPL.outcome"] 
                    [@s.param name="0"]<span id="elementIndex">${midOutcome_index+1}</span>[/@s.param] 
                  [/@s.text]
                [/#assign]
                <legend>${midOutcomeDescription}</legend>
  
                [#-- Mid outcome description --]
                [@customForm.textArea name="midOutcomes[${midOutcome_index}].description" i18nkey="preplanning.midOutcomesRPL.outcomeDescription" required=true /] 
  
                [#-- Flagships list --]
                [#if midOutcome.translatedOf?has_content]
                  [@customForm.select name="midOutcomesRPL_flagships" label="" i18nkey="preplanning.midOutcomesRPL.flagships" listName="flagshipsList" keyFieldName="id"  displayFieldName="getComposedName()" value=midOutcome.translatedOf[0].program.id?string disabled=true  /]
                [#else]
                  [@customForm.select name="midOutcomesRPL_flagships" label="" i18nkey="preplanning.midOutcomesRPL.flagships" listName="flagshipsList" keyFieldName="id"  displayFieldName="getComposedName()" /]
                [/#if]
                
                [#-- midOutcome's parents --]
                [#--
                //  When I wrote this code God and me knew what we were doing, 
                //  NOW, ONLY HE KNOWS.
                --]
                <div class="contentElements parentsBlock">
                  <div class="itemIndex">[@s.text name="preplanning.midOutcomesRPL.contributes" /] </div>
                  [#if midOutcome.translatedOf?has_content]
                    [#list midOutcome.translatedOf as parent]
                    
                      <div id="" class="contributions">
                        <input id="contributeId" type="hidden" name="midOutcomes[${midOutcome_index}].translatedOf" value="${parent.id}" />
                        <h6 id="midOutcomeDescription">[@s.text name="preplanning.midOutcomesRPL.midOutcomeDescription" /]</h6>
                        <p id="description">[@s.text name="midOutcomes[${midOutcome_index}].translatedOf[${parent_index}].description" /]</p>      
                        [#-- midOutcome's Indicators --]
                        <h6>[@s.text name="preplanning.midOutcomesRPL.selectIndicators" /]</h6>
                        <div id="midOutcomeIndicators" class="midOutcomeIndicators fullBlock">
                          <div class="checkboxGroup vertical"> 
                            [#list parent.indicators as parentIndicator] 
  
                              <div class="elementIndicator">
                                [#if midOutcome.getIndicatorByParentID( parentIndicator.id )?has_content]
                                  [#assign midOutcomeIndicator = midOutcome.getIndicatorByParentID( parentIndicator.id ) ]
                                  <input type="checkbox" name="midOutcomes[${midOutcome_index}].indicators[${parentIndicator_index}].parent" value="${parentIndicator.id}" id="indicator-parent-${midOutcomeIndicator.id}" checked="checked" class="midOutcomeIndicator">
                                  <input type="hidden" name="midOutcomes[${midOutcome_index}].indicators[${parentIndicator_index}].id" value="${midOutcomeIndicator.id}" id="indicator-${parentIndicator_index}" >
                                  <label for="indicator-parent-${midOutcomeIndicator.id}" class="checkboxLabel"> ${parentIndicator.description} </label>
                                  <div class="fields">
                                    <div class="target">
                                      <div class="input">
                                        <h6> <label for="target"> [@s.text name="preplanning.midOutcomes.target" /] </label> </h6>
                                        <input type="text" id="target" name="__midOutcomes[0].indicators[-1].target" value="${midOutcomeIndicator.target}">
                                      </div>
                                    </div>
                                    <div class="narrative">
                                      <div class="textArea "> 
                                        <h6> <label for="description">Narrative explanation of indicator target contribution: <span class="red">*</span> </label> </h6> <textarea name="__midOutcomes[0].indicators[-1].description" id="description" class="ckeditor" placeholder="">${midOutcomeIndicator.description}</textarea>
                                      </div>
                                    </div>
                                  </div>
  
                                [#else]
                                  <input type="checkbox" name="midOutcomes[${midOutcome_index}].indicators[${parentIndicator_index}].parent" value="${parentIndicator.id}" id="indicator-parent-${parentIndicator_index}" class="midOutcomeIndicator">
                                  <input type="hidden" name="midOutcomes[${midOutcome_index}].indicators[${parentIndicator_index}].id" value="-1" id="indicator-${parentIndicator_index}" >
                                  <label for="indicator-parent-${parentIndicator_index}" class="checkboxLabel"> ${parentIndicator.description} </label>
                                [/#if]
  
                              </div>
                            [/#list]
  
                          </div>
                        </div>
                          [#-- remove link --]
                          <div class="removeLink">            
                            <img src="${baseUrl}/images/global/icon-remove.png" />
                            <a id="removeContribute-${parent_index}" href="" class="removeContribute">[@s.text name="preplanning.midOutcomesRPL.removeContribute" /]</a>
                          </div> 
                      </div>
  
                    [/#list] 
                  [/#if]  
                  [#-- Add contribute --]
                  <div class="fullBlock addContributeBlock">
                    [@customForm.select name="midOutcomesFPL" i18nkey="preplanning.midOutcomes.addContribute" showTitle=false listName="midOutcomesFPL" keyFieldName="id"  displayFieldName="description"  className="contributes" /]
                  </div>
                </div>  
  
              </div>  
            [/#list]
          [/#if]
      </div> 
      <div id="addMidOutcomeBlock" class="addLink"> 
        <a href="" class="addMidOutcome addButton" >[@s.text name="preplanning.midOutcomesRPL.addOutcome" /]</a>
      </div> 
      [#-- 
       <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
      --]
    [#else]
      [#-- To add midoutcomes, user should have outcomes 2025 --]
      <p>
        [@s.text name="preplanning.midOutcomesRPL.addOutcomes" /]
      </p>
    [/#if] 
  </article>
  [/@s.form] 
  
  [#-- Placeholder text for midOutcomes select --] 
  <input type="hidden" id="midOutcomeSelectPlaceholder" value="[@s.text name="preplanning.midOutcomesRPL.midOutcomes" /]"  />
  <input type="hidden" id="selectFlagshipFirstPlaceholder" value="[@s.text name="preplanning.midOutcomesRPL.selectFlagshipFirst" /]"  />
  
[#-- Mid Outcomes RPL TEMPLATE --]
<div class="midOutcome borderBox" id="midOutcomeRPLTemplate" style="display:none">
  [#-- Mid outcome identifier --]
  <input id="id" type="hidden"  value="-1" />
  <input type="hidden" id="midOutcomeProgramId" value="${program.id}" />
  <input type="hidden" id="midOutcomeTypeId" value="${elementTypeID}" />
  [#-- Contribution to the regional vision 2025 --]
  [#-- If there are no Outcomes 2025, users can not add midOutcomes --]
  [#if outcomesList?has_content]
    <input type="hidden" name="midOutcomes[0].contributesTo" value="${outcomesList[0].id}" />
  [/#if]
  [#-- Remove Output --]
  <div id="removeMidOutcome" class="removeMidOutcome removeElement removeLink" title="[@s.text name="preplanning.midOutcomesRPL.removeMidOutcome" /]"></div>
  [#-- Title --]
  [#assign midOutcomeDescription]
    [@s.text name="preplanning.midOutcomesRPL.outcome"] 
      [@s.param name="0"]<span id="elementIndex">{0}</span>[/@s.param] 
    [/@s.text]
  [/#assign]
  <legend>${midOutcomeDescription}</legend>
  [@customForm.textArea name="description" i18nkey="preplanning.midOutcomesRPL.outcomeDescription" required=true /] 
  [@customForm.select name="midOutcomesRPL_flagships" label="" i18nkey="preplanning.midOutcomesRPL.flagships" listName="flagshipsList" keyFieldName="id"  displayFieldName="getComposedName()" /]
  <div class="contentElements parentsBlock">
    <div class="itemIndex">[@s.text name="preplanning.midOutcomesRPL.contributes" /] </div>
    [#-- midOutcome's parents --]  
    [#-- Add contribute --]
    <div class="fullBlock addContributeBlock">
      [@customForm.select name="midOutcomesFPL"  showTitle=false listName="midOutcomesFPL" keyFieldName="id"  displayFieldName="description" className="contributes" /]
    </div>
  </div>  
</div> 
[#-- contributeTo RPL TEMPLATE --]
[@contributeTemplate.midOutcomesRPL template=true canRemove=true  /]

[#-- Indicator RPL TEMPLATE --]
[@indicatorTemplate.midOutcomesRPL template=true /]

[#-- Remove element modal  template --]
<div id="removeDialog" style="display:none" title="[@s.text name="preplanning.midOutcomesRPL.removeDialog.title" /]"> 
  [@s.text name="preplanning.midOutcomesRPL.removeDialog.content.part1" /]
  <strong><span class="elements"></span></strong>
  [@s.text name="preplanning.midOutcomesRPL.removeDialog.content.part2" /]
</div> 
    
   
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]