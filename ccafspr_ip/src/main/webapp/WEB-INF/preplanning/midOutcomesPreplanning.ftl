[#ftl]
[#assign title = "Outcomes 2019 - Preplanning" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/preplanning/midOutcomesPreplanning.js"] /]
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
    <p> [@s.text name="planning.mainInformation.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl" /]
  
  [@s.form action="midOutcomes" cssClass="pure-form"]  
    <article class="halfContent" id="midOutcomes">
    [#include "/WEB-INF/preplanning/ipPreplanningSubMenu.ftl" /]
    <h1 class="contentTitle">
    [@s.text name="preplanning.midOutcomes.title" /]  
    </h1>
    <div id="MidOutcomeBlocks"> 
        [#if midOutcomes?has_content]
          [#list midOutcomes as midOutcome]
          <div class="midOutcome borderBox" id="midOutcome-${midOutcome_index}">
            [#-- Mid outcome identifier --]
            <input id="midOutcomeId" type="hidden" name="midOutcomes[${midOutcome_index}].id" value="${midOutcome.id}" />
            <input id="midOutcomeProgramID" type="hidden" name="midOutcomes[${midOutcome_index}].program.id" value="${currentUser.currentInstitution.program.id}" />
            <input id="midOutcomeTypeID" type="hidden" name="midOutcomes[${midOutcome_index}].type.id" value="${elementTypeID}" />
            [#-- Remove midOutcome --]
            <div class="removeMidOutcomeBlock removeLink">              
              <img src="${baseUrl}/images/global/icon-remove.png" />
              <a id="removeMidOutcome" href="" class="removeContribute">[@s.text name="preplanning.midOutcomes.removeMidOutcome" /]</a>
            </div>  
            [#-- Title --] 
            [#assign midOutcomeDescription]
              [@s.text name="preplanning.midOutcomes.outcome"]
                [@s.param name="0"]${currentUser.currentInstitution.program.id}[/@s.param] 
                [@s.param name="1"]<span id="elementIndex">${midOutcome_index+1}</span>[/@s.param] 
              [/@s.text]
            [/#assign]
            [@customForm.textArea name="midOutcomes[${midOutcome_index}].description"  i18nkey="${midOutcomeDescription}" required=true /] 
            <div class="contentElements parentsBlock" style="display:none">
              <div class="itemIndex">[@s.text name="preplanning.midOutcomes.contributes" /] </div>
              [#-- midOutcome's parents --]
              [#if midOutcome.contributesTo?has_content]
                [#list midOutcome.contributesTo as parent]
                  [@contributeTemplate.midOutcomes midOutcome_index="${midOutcome_index}" parent_index="${parent_index}" value="${parent.id}" description="${parent.description}" /]
                [/#list]
              [/#if]
              [#-- Add contribute --]
              <div class="fullBlock addContributeBlock" style="display:none">
                [@customForm.select name="contributionId" value="none" showTitle=false listName="outcomesList" keyFieldName="id"  displayFieldName="description" addButton=true className="contributes" /]
              </div> 
            </div>  
            <div class="contentElements indicatorsBlock">
              <div class="itemIndex">[@s.text name="preplanning.midOutcomes.indicators" /] </div>
              [#-- midOutcome's indicators --]
              [#if midOutcome.indicators?has_content]
                [#list midOutcome.indicators as indicator]
                  [@indicatorTemplate.midOutcomes midOutcome_index="${midOutcome_index}" indicator_index="${indicator_index}" value="${indicator.id}" /]
                [/#list]
              [#else]
                [@indicatorTemplate.midOutcomes midOutcome_index="${midOutcome_index}" /] 
              [/#if]
              [#-- Add Indicator --]
              <div id="addIndicatorBlock" class="fullBlock">
                [@customForm.button i18nkey="preplanning.midOutcomes.addIndicator" class="addButton" /]
              </div> 
            </div>
          </div>  
          [/#list] 
        [/#if]
      
    
    </div>
    <div id="addMidOutcomeBlock" class="addLink">
      <img src="${baseUrl}/images/global/icon-add.png" />
      <a href="" class="addMidOutcome" >[@s.text name="preplanning.midOutcomes.addOutcome" /]</a>
    </div> 
     <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
  </article>
  [/@s.form] 
  
[#----  Mid Outcome TEMPLATE hidden ----]
<div id="midOutcomeTemplate" class="midOutcome borderBox" style="display:none">
  [#-- Objective identifier --]
  <input id="midOutcomeId" type="hidden" value="-1" />
  <input type="hidden" id="midOutcomeProgramID" value="${currentUser.currentInstitution.program.id}" />
  <input type="hidden" id="midOutcomeTypeID" value="${elementTypeID}" />
  [#-- Remove midOutcome --]      
  <div class="removeLink removeMidOutcomeBlock">            
    <img src="${baseUrl}/images/global/icon-remove.png" />
    <a id="removeMidOutcome" href="" class="removeContribute">[@s.text name="preplanning.midOutcomes.removeMidOutcome" /]</a>
  </div> 
  [#-- Title --]
  [#assign midOutcomeDescription]
    [@s.text name="preplanning.midOutcomes.outcome"]
      [@s.param name="0"]${currentUser.currentInstitution.program.id}[/@s.param] 
      [@s.param name="1"]<span id="elementIndex">{0}</span>[/@s.param] 
    [/@s.text]
  [/#assign]
  [@customForm.textArea name="description" i18nkey="${midOutcomeDescription}" required=true /] 
  [#-- Contribute area --]
  <div id="contributesBlock" class="contentElements" style="display:none">
    <div class="itemIndex">[@s.text name="preplanning.midOutcomes.contributes" /] </div>
    [@contributeTemplate.midOutcomes template=true /]
    [#-- Add contribute --]
     <div class="fullBlock addContributeBlock" style="display:none">
      [@customForm.select name="contributions" value="none" showTitle=false listName="outcomesList" keyFieldName="id"  displayFieldName="description" addButton=true className="contributes" /]
    </div> 
  </div>
  [#-- Indicator template --]
  <div id="indicatorsBlock" class="contentElements indicatorsBlock">
    <div class="itemIndex">[@s.text name="preplanning.midOutcomes.indicators" /] </div>
    [@indicatorTemplate.midOutcomes template=true /] 
    [#-- Add Indicator --]
    <div id="addIndicatorBlock" class="fullBlock">
      [@customForm.button i18nkey="preplanning.midOutcomes.addIndicator" class="addButton" /]
    </div> 
  </div>  
</div> 
[#-- End Mid Outcome template  --]
[#-- Contribute template --]
[@contributeTemplate.midOutcomes template=true /]
  
</section>
[#if outcomesList?has_content ]
<script type="text/javascript">
var outcome = {id:${outcomesList[0].id}, description:"${outcomesList[0].description}" };
</script>
[/#if]
[#include "/WEB-INF/global/pages/footer.ftl"]