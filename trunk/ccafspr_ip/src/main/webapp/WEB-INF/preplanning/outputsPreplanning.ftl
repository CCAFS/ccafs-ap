[#ftl]
[#assign title = "Major Output Group" /]
[#assign globalLibs = ["jquery", "noty", "autoSave","chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/preplanning/outputsPreplanning.js"] /]
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
    <p> [@s.text name="preplanning.outputs.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/pre-planning-secondary-menu.ftl" /]
  
  [@s.form action="outputs" cssClass="pure-form"]  
  <article class="halfContent" id="outputs">
    [#include "/WEB-INF/preplanning/ipPreplanningSubMenu.ftl" /]
    <h1 class="contentTitle">
    [@s.text name="preplanning.outputs.title" /]  
    </h1>
    
    [#if midOutcomesList?has_content ]
      <div id="outputBlocks"> 
          [#if outputs?has_content]
            [#list outputs as output]
            <div class="output borderBox" id="output-${output_index}">
              [#-- output identifier --] 
              <input id="outputId" name="outputs[${output_index}].id" value="${output.id}"  type="hidden"/>
              <input id="outputProgramID" name="outputs[${output_index}].program.id" value="${currentUser.currentInstitution.program.id}" type="hidden" />
              <input id="outputTypeID" name="outputs[${output_index}].type.id" value="${elementTypeID}" type="hidden" />
              [#-- Remove Output --]
              <div id="removeOutput" class="removeOutput removeElement removeLink" title="[@s.text name="preplanning.outputs.removeOutput" /]"></div>
              [#-- Title --]
              [#assign outputDescription]
                [@s.text name="preplanning.outputs.output"]
                  [@s.param name="0"]${currentUser.currentInstitution.program.id}[/@s.param] 
                  [@s.param name="1"]<span id="elementIndex">${output_index+1}</span>[/@s.param] 
                [/@s.text]
              [/#assign]
              <legend>${outputDescription}</legend>
              [@customForm.textArea name="outputs[${output_index}].description" i18nkey="preplanning.outputs.outputDescription" required=true /] 
              <div id="contributesBlock" class="contentElements parentsBlock">
                <div class="itemIndex">[@s.text name="preplanning.outputs.contributes" /] </div>
                [#-- output's parents --] 
                [#if output.contributesTo?has_content]
                  [#list output.contributesTo as parent] 
                    [@contributeTemplate.outputs output_index="${output_index}" parent_index="${parent_index}" value="${parent.id}" description="${parent.description}" canRemove=true /]
                  [/#list]
                [/#if]
                [#-- Add contribute --]
                <div class="fullBlock addContributeBlock">
                  [@customForm.select name="contributions" value="" showTitle=false listName="midOutcomesList" keyFieldName="id"  displayFieldName="description" className="contributes" i18nkey="preplanning.midOutcomes.addContribute" /]
                </div> 
              </div>   
            </div>  
            [/#list]          
          [/#if]
      </div>

      [#-- Add midOutcome button --]
      <div id="addOutputBlock" class="addLink">
        <a href="" class="addOutput addButton" >[@s.text name="preplanning.outputs.addOutput" /]</a>
      </div>

       <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [#--[@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]--]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
      
    [#else]
      [@s.text name="preplanning.outputs.addMidoutcomes" /]
    [/#if]
    
  </article>
  [/@s.form]
  [#----  Output TEMPLATE hidden ----]
    <div id="outputTemplate" class="output borderBox" style="display:none">
      [#-- Objective identifier --]
      <input id="outputId" type="hidden" value="-1" />
      <input id="outputProgramID" value="${currentUser.currentInstitution.program.id}" type="hidden" />
      <input id="outputTypeID" value="${elementTypeID}" type="hidden" />
      [#-- Remove Output --]
      <div id="removeOutput" class="removeOutput removeElement removeLink" title="[@s.text name="preplanning.outputs.removeOutput" /]"></div>
      [#-- Title --]
      [#assign outputDescription]
        [@s.text name="preplanning.outputs.output"]
          [@s.param name="0"]${currentUser.currentInstitution.program.id}[/@s.param] 
          [@s.param name="1"]<span id="elementIndex">{0}</span>[/@s.param] 
        [/@s.text]
      [/#assign]
      <legend>${outputDescription}</legend> 
      [@customForm.textArea name="outputDescription" i18nkey="preplanning.outputs.outputDescription" required=true /] 
      <div id="contributesBlock" class="contentElements">
        <div class="itemIndex">[@s.text name="preplanning.outputs.contributes" /] </div>
        [#-- Contribute area --]
        [#-- Add contribute --]
         <div class="fullBlock addContributeBlock">
          [@customForm.select name="contributions"  i18nkey="preplanning.midOutcomes.addContribute" showTitle=false listName="midOutcomesList" keyFieldName="id"  displayFieldName="description" className="contributes" /]
        </div> 
      </div>  
    </div> 
    [#-- End Output template  --]
    [#-- Contribute template --]
    [@contributeTemplate.outputs template=true canRemove=true /]
    [#-- End Contribute template --]   
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]
