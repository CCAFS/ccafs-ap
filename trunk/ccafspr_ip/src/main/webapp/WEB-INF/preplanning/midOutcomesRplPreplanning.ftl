[#ftl]
[#assign title = "Outcomes 2019 - Preplanning" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/preplanning/midOutcomesRPLPreplanning.js"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "impactPathways" /]
[#assign currentStage = "midOutcomes" /]

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
    <div id="MidOutcomeBlocks"> 
        [#if midOutcomes?has_content]
          [#list midOutcomes as midOutcome]
          <div class="midOutcome borderBox" id="midOutcome-${midOutcome_index}">
            [#-- Mid outcome identifier --]
            <input id="midOutcomeId" type="hidden" name="midOutcomes[${midOutcome_index}].id" value="${midOutcome.id}" />
            <input type="hidden" name="midOutcomes[${midOutcome_index}].program.id" value="${currentUser.currentInstitution.program.id}" />
            <input type="hidden" name="midOutcomes[${midOutcome_index}].type.id" value="${elementTypeID}" />
            [#-- Remove midOutcome --]
            <div class="removeMidOutcomeBlock removeLink">              
              <img src="${baseUrl}/images/global/icon-remove.png" />
              <a id="removeMidOutcome" href="" class="removeContribute">[@s.text name="preplanning.midOutcomesRPL.removeMidOutcome" /]</a>
            </div> 
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
            [@customForm.select name="flagships" label="" i18nkey="preplanning.midOutcomesRPL.flagships" listName="flagshipsList" keyFieldName="id"  displayFieldName="name" /]

            [#-- midOutcome's parents --]
            <div class="contentElements parentsBlock">
              <div class="itemIndex">[@s.text name="preplanning.midOutcomesRPL.contributes" /] </div>
              [#if midOutcome.translatedOf?has_content]
                [#list midOutcome.translatedOf as parent]
                  [@contributeTemplate.midOutcomesRPL midOutcomeRPL_index="${midOutcome_index}" midOutcomeRPL_value="${midOutcome.id}" parent_index="${parent_index}" parent_id="${parent.id}" canRemove=true  /]
                [/#list] 
              [/#if]  
              [#-- Add contribute --]
              <div class="fullBlock addContributeBlock">
                [@customForm.select name="midOutcomesFPL" value="none" showTitle=false listName="midOutcomesFPL" keyFieldName="id"  displayFieldName="description" addButton=true className="contributes" /]
              </div>
            </div>  
          </div>  
          [/#list]
        [/#if]
    </div>
    <div id="addMidOutcomeBlock" class="addLink">
      <img src="${baseUrl}/images/global/icon-add.png" />
      <a href="" class="addMidOutcome" >[@s.text name="preplanning.midOutcomesRPL.addOutcome" /]</a>
    </div> 
     <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
  </article>
  [/@s.form] 
  
  
[#-- Mid Outcomes RPL TEMPLATE --]
<div class="midOutcome borderBox" id="midOutcomeRPLTemplate" style="display:none">
  [#-- Mid outcome identifier --]
  <input id="id" type="hidden"  value="-1" />
  <input type="hidden" id="midOutcomeProgramId" value="${currentUser.currentInstitution.program.id}" />
  <input type="hidden" id="midOutcomeTypeId" value="${elementTypeID}" />
  [#-- Remove midOutcome --]      
  <div class="removeMidOutcomeBlock removeLink">            
    <img src="${baseUrl}/images/global/icon-remove.png" />
    <a id="removeMidOutcome" href="" class="removeContribute">[@s.text name="preplanning.midOutcomesRPL.removeMidOutcome" /]</a>
  </div> 
  [#-- Title --]
  [#assign midOutcomeDescription]
    [@s.text name="preplanning.midOutcomesRPL.outcome"] 
      [@s.param name="0"]<span id="elementIndex">{0}</span>[/@s.param] 
    [/@s.text]
  [/#assign]
  <legend>${midOutcomeDescription}</legend>
  [@customForm.textArea name="description" i18nkey="preplanning.midOutcomesRPL.outcomeDescription" required=true /] 
  [@customForm.select name="flagships" label="" i18nkey="preplanning.midOutcomesRPL.flagships" listName="flagshipsList" keyFieldName="id"  displayFieldName="name" /]
  <div class="contentElements parentsBlock">
    <div class="itemIndex">[@s.text name="preplanning.midOutcomesRPL.contributes" /] </div>
    [#-- midOutcome's parents --]  
    [#-- Add contribute --]
    <div class="fullBlock addContributeBlock">
      [@customForm.select name="midOutcomesFPL"  value="none" showTitle=false listName="midOutcomesFPL" keyFieldName="id"  displayFieldName="description" addButton=true className="contributes" /]
    </div>
  </div>  
</div> 
[#-- contributeTo RPL TEMPLATE --]
[@contributeTemplate.midOutcomesRPL template=true canRemove=true  /]

[#-- Indicator RPL TEMPLATE --]
[@indicatorTemplate.midOutcomesRPL template=true /]
    
   
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]