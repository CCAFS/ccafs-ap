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
    <p> [@s.text name="planning.preplanning.midOutcomesRPL.help" /] </p>
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
          <div class="midOutcome" id="midOutcome-${midOutcome_index}">
            [#-- Mid outcome identifier --]
            <input id="id" type="hidden" name="midOutcomes[${midOutcome_index}].id" value="${midOutcome.id}" />
            [#-- Remove midOutcome --]      
            <div class="removeMidOutcomeBlock removeLink">            
              <img src="${baseUrl}/images/global/icon-remove.png" />
              <a id="removeMidOutcome" href="" class="removeContribute">[@s.text name="preplanning.midOutcomesRPL.removeMidOutcome" /]</a>
            </div> 
            [#-- Title --]
            [@customForm.textArea name="midOutcomes[${midOutcome_index}].description" i18nkey="preplanning.midOutcomesRPL.outcome" required=true /] 
            [@customForm.select name="flagships" label="" i18nkey="preplanning.outputsRPL.flagships" listName="flagshipsList" keyFieldName="id"  displayFieldName="name" /]
            <div class="contentElements parentsBlock">
              <div class="itemIndex">[@s.text name="preplanning.midOutcomesRPL.contributes" /] </div>
              [#-- midOutcome's parents --]  
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
    <div class="midOutcome" id="midOutcomeRPLTemplate" style="display:none">
      [#-- Mid outcome identifier --]
      <input id="id" type="hidden"  value="-1" />
      [#-- Remove midOutcome --]      
      <div class="removeMidOutcomeBlock removeLink">            
        <img src="${baseUrl}/images/global/icon-remove.png" />
        <a id="removeMidOutcome" href="" class="removeContribute">[@s.text name="preplanning.midOutcomesRPL.removeMidOutcome" /]</a>
      </div> 
      [#-- Title --]
      [@customForm.textArea name="description" i18nkey="preplanning.midOutcomesRPL.outcome" required=true /] 
      [@customForm.select name="flagships" label="" i18nkey="preplanning.outputsRPL.flagships" listName="flagshipsList" keyFieldName="id"  displayFieldName="name" /]
      <div class="contentElements parentsBlock">
        <div class="itemIndex">[@s.text name="preplanning.midOutcomesRPL.contributes" /] </div>
        [#-- midOutcome's parents --]  
        [#-- Add contribute --]
        <div class="fullBlock addContributeBlock">
          [@customForm.select name="midOutcomesFPL"  value="none" showTitle=false listName="midOutcomesFPL" keyFieldName="id"  displayFieldName="description" addButton=true className="contributes" /]
        </div>
      </div>  
    </div> 
    
    
   
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]