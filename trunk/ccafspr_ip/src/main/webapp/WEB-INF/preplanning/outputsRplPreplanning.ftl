[#ftl]
[#assign title = "Regional Major output groups" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/preplanning/outputsRPLPreplanning.js"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "impactPathways" /]
[#assign currentStage = "outputs" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/preplanning/macros/indicatorTemplate.ftl" as indicatorTemplate/]
[#import "/WEB-INF/preplanning/macros/contributeTemplate.ftl" as contributeTemplate/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="preplanning.outputsRPL.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl" /]
  
  [@s.form action="outputsRPL" cssClass="pure-form"]  
  <article class="halfContent" id="outputs">
    [#include "/WEB-INF/preplanning/ipPreplanningSubMenu.ftl" /]
    <h1 class="contentTitle">
    [@s.text name="preplanning.outputsRPL.title" /]  
    </h1>
    <div id="outputBlocks"> 
        [#if outputs?has_content]
          [#list outputs as output]
          
            <div class="output borderBox" id="output-${output_index}">
              [#-- RPL output identifier --] 
              <input name="outputs[${output_index}].id" id="outputId" value="${output.id}" type="hidden" />
              <input name="outputs[${output_index}].program.id" id="outputProgramID" value="${currentUser.currentInstitution.program.id}" type="hidden" />
              <input name="outputs[${output_index}].type.id" id="outputTypeID" value="${elementTypeID}" type="hidden" />
            
              [#-- Remove RPL output --]
              <div class="removeOutputBlock removeLink">              
                <img src="${baseUrl}/images/global/icon-remove.png" />
                <a id="removeOutput" href="" class="">[@s.text name="preplanning.outputsRPL.removeOutput" /]</a>
              </div>  
              ${output}
              [#if output.contributesTo?has_content]
                [#-- MOG created by the user --]
                [#-- Title --]
                [#assign outputDescription]
                  [@s.text name="preplanning.outputsRPL.output"] 
                    [@s.param name="0"]${currentUser.currentInstitution.program.acronym}[/@s.param] 
                    [@s.param name="1"]<span id="elementIndex">${output_index+1}</span>[/@s.param] 
                  [/@s.text]
                [/#assign]
                [@customForm.textArea name="outputs[${output_index}].description" i18nkey="${outputDescription}" required=true /]
                <div id="contributesBlock" class="contentElements parentsBlock">
                  <div class="itemIndex">[@s.text name="preplanning.outputsRPL.contributes" /] </div>
                  [#-- RPL output's parents --] 
                  [#if output.contributesTo?has_content]
                    [#list output.contributesTo as parent] 
                      [@contributeTemplate.outputs output_index="${output_index}" parent_index="${parent_index}" value="${parent.id}" description="${parent.description}" canRemove=true /]
                    [/#list]
                  [/#if]
                  
                  [#-- Add contribute --]
                  <div class="fullBlock addContributeBlock">
                    [@customForm.select name="contributionId" value="" showTitle=false listName="midOutcomesList" keyFieldName="id"  displayFieldName="description" addButton=true className="contributes" /]
                  </div> 
                </div>   
                
              [#elseif output.translatedOf?has_content]
                [#-- MOG translated from other MOG  --]
                [#-- Title --]
                [#assign outputDescription]
                  [@s.text name="preplanning.outputsRPL.output"] 
                    [@s.param name="0"]${currentUser.currentInstitution.program.acronym}[/@s.param] 
                    [@s.param name="1"]<span id="elementIndex">${output_index+1}</span>[/@s.param] 
                  [/@s.text]
                [/#assign]
                [@customForm.textArea name="outputs[${output_index}].description" i18nkey="${outputDescription}" required=true /] 
                <div class="fullBlock chosen"> 
                  [@customForm.select name="flagships" label="" i18nkey="preplanning.outputsRPL.flagships" listName="flagshipsList" keyFieldName="id"  displayFieldName="name" value="${output.translatedOf[0].program.id}" /]
                </div>
                <div class="fullBlock chosen">
                  [@customForm.select name="midOutcomes" label="" i18nkey="preplanning.outputsRPL.midOutcomes" listName="" keyFieldName="id"  displayFieldName="name" /]
                </div>
                <div class="fullBlock chosen translations">
                  [@customForm.select name="outputs[${output_index}].translatedOf" label="" i18nkey="preplanning.outputsRPL.outputs" listName="" keyFieldName="id"  displayFieldName="name" /]
                </div>
              [/#if]
            
          
            
          </div>  
          [/#list]
        [#else]
          <h5 class="noOutputs message">[@s.text name="preplanning.outputsRPL.messageNoOutputs" /]</h5>
        [/#if]
      
    
    </div>
     <div id="addOutputBlock" class="addLink">
      <img src="${baseUrl}/images/global/icon-add.png" />
      <a href="" id="addNewOutput" class="addOutput" > [@s.text name="preplanning.outputsRPL.addNewOutput" /] </a>
       or 
      <img src="${baseUrl}/images/global/icon-add.png" />
      <a href="" id="addExistingOutput" class="addOutput" > [@s.text name="preplanning.outputsRPL.addExistingOutput" /] </a>
    </div>   
     
     <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
  </article>
  [/@s.form]  
  
  
  [#----  New RPL Output TEMPLATE hidden ----]
  <div id="outputTemplate" class="output borderBox" style="display:none">
    [#-- RPL Output identifier --]
    <input id="outputId" type="hidden" value="-1" />
    <input id="outputProgramID" value="${currentUser.currentInstitution.program.id}" type="hidden" />
    <input id="outputTypeID" value="${elementTypeID}" type="hidden" />
    [#-- Remove Output --]      
    <div class="removeLink removeOutputBlock">            
      <img src="${baseUrl}/images/global/icon-remove.png" />
      <a id="removeOutput" href="" class="removeContribute">[@s.text name="preplanning.outputsRPL.removeOutput" /]</a>
    </div> 
    [#-- Title --]
    [#assign outputDescription]
      [@s.text name="preplanning.outputsRPL.output"] 
        [@s.param name="0"]${currentUser.currentInstitution.program.acronym}[/@s.param] 
        [@s.param name="1"]<span id="elementIndex">{0}</span>[/@s.param] 
      [/@s.text]
    [/#assign]
    [@customForm.textArea name="description" i18nkey="${outputDescription}" required=true /] 
    <div id="contributesBlock" class="contentElements">
      <div class="itemIndex">[@s.text name="preplanning.outputsRPL.contributes" /] </div>
      [#-- Contribute area --] 
      [#-- Add contribute --]
       <div class="fullBlock addContributeBlock">
        [@customForm.select name="contributesTo" showTitle=false listName="midOutcomesList" keyFieldName="id"  displayFieldName="description" addButton=true className="contributes" /]
      </div> 
    </div>  
  </div>  
  
  [#---- Existent  RPL Output TEMPLATE hidden ----]
  <div id="newOutputTemplate" class="output borderBox" style="display:none">
    [#-- RPL output identifier --]
    <input id="outputProgramID" value="${currentUser.currentInstitution.program.id}" type="hidden" />
    <input id="outputTypeID" value="${elementTypeID}" type="hidden" />
    <input id="outputId" type="hidden" value="-1" />
    [#-- Remove Output --]      
    <div class="removeLink removeOutputBlock">            
      <img src="${baseUrl}/images/global/icon-remove.png" />
      <a id="removeOutput" href="" class="removeContribute">[@s.text name="preplanning.outputsRPL.removeOutput" /]</a>
    </div>
    [#-- Title --]
    [#assign outputDescription]
      [@s.text name="preplanning.outputsRPL.output"] 
        [@s.param name="0"]${currentUser.currentInstitution.program.acronym}[/@s.param] 
        [@s.param name="1"]<span id="elementIndex">{0}</span>[/@s.param] 
      [/@s.text]
    [/#assign]
    [@customForm.textArea name="description" i18nkey="${outputDescription}" required=true /] 
    <div class="fullBlock chosen"> 
      [@customForm.select name="outputsRPL_flagships" label="" i18nkey="preplanning.outputsRPL.flagships" listName="flagshipsList" keyFieldName="id"  displayFieldName="name" /]
    </div>
    <div class="fullBlock chosen">
      [@customForm.select name="outputsRPL_midOutcomes" label="" i18nkey="preplanning.outputsRPL.midOutcomes" listName="" keyFieldName="id"  displayFieldName="name" disabled=true /]
    </div>
    <div class="fullBlock chosen translations">
      [@customForm.select name="outputsRPL_outputs_" label="" i18nkey="preplanning.outputsRPL.outputs" listName="" keyFieldName="id"  displayFieldName="name" disabled=true /]
    </div> 
  </div> 
   
  [#-- Contribute template --]
  [@contributeTemplate.outputs template=true canRemove=true /]
  [#-- End Contribute template --] 
  <input id="midOutcomeTypeID" value="${midOutcomeTypeID}" type="hidden" />
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]
