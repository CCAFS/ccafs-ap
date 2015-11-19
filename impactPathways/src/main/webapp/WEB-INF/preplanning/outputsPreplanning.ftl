[#ftl]
[#assign title = "Major Output Group" /]
[#assign globalLibs = ["jquery", "noty", "autoSave","chosen", "cytoscape", "qtip","cytoscapePanzoom"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/ipGraph.js", "${baseUrl}/js/preplanning/outputsPreplanning.js"] /]
[#assign currentSection = "preplanning" /]
[#assign currentPrePlanningSection = "impactPathways" /]
[#assign currentStage = "outputs" /]

[#assign breadCrumb = [
  {"label":"preplanning", "nameSpace":"pre-planning", "action":"intro"},
  {"label":"impactPathways", "nameSpace":"pre-planning", "action":"outcomes", "param":"programID=${program.id}"},
  {"label":"outputs", "nameSpace":"pre-planning", "action":"outputs", "param":"programID=${program.id}"}
]/]

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
      [#if midOutcomesList?size=1 &&  midOutcomesList[0].id == -1 ]
        [@s.text name="preplanning.outputs.addMidoutcomes" /]
      [#else]
        <div id="outputBlocks"> 
            [#if outputs?has_content]
              [#list outputs as output]
              <div class="output borderBox" id="output-${output_index}">
                [#-- output identifier --] 
                <input id="outputId" name="outputs[${output_index}].id" value="${output.id}"  type="hidden"/>
                <input id="outputProgramID" name="outputs[${output_index}].program.id" value="${program.id}" type="hidden" />
                <input id="outputTypeID" name="outputs[${output_index}].type.id" value="${elementTypeID}" type="hidden" />
                [#-- Remove Output --]
                <div id="removeOutput" class="removeOutput removeElement removeLink" title="[@s.text name="preplanning.outputs.removeOutput" /]"></div>
                [#-- Title --]
                [#assign outputDescription]
                  [@s.text name="preplanning.outputs.output"]
                    [@s.param name="0"]${program.id}[/@s.param] 
                    [@s.param name="1"]<span id="elementIndex">${output_index+1}</span>[/@s.param] 
                  [/@s.text]
                [/#assign]
                <legend>${outputDescription}</legend>
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
                [#-- Description --]
                [@customForm.textArea name="outputs[${output_index}].description" i18nkey="preplanning.outputs.outputDescription" required=true /] 
              </div>  
              [/#list]
            [/#if]   
        </div>
  
        [#-- Add midOutcome button --]
        <div id="addOutputBlock" class="addLink">
          <a href="" class="addOutput addButton" >[@s.text name="preplanning.outputs.addOutput" /]</a>
        </div>
        [#-- Program ID  --]
        <input type="hidden" id="programID" value="${program.id}" />
        [#-- 
         <div class="buttons">
          [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
          [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
          [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
        </div>
        --]
      [/#if]
    [#else]
      [@s.text name="preplanning.outputs.addMidoutcomes" /]
    [/#if]
    
  </article>
  [/@s.form]
  [#----  Output TEMPLATE hidden ----]
    <div id="outputTemplate" class="output borderBox" style="display:none">
      [#-- Objective identifier --]
      <input id="outputId" type="hidden" value="-1" />
      <input id="outputProgramID" value="${program.id}" type="hidden" />
      <input id="outputTypeID" value="${elementTypeID}" type="hidden" />
      [#-- Remove Output --]
      <div id="removeOutput" class="removeOutput removeElement removeLink" title="[@s.text name="preplanning.outputs.removeOutput" /]"></div>
      [#-- Title --]
      [#assign outputDescription]
        [@s.text name="preplanning.outputs.output"]
          [@s.param name="0"]${program.id}[/@s.param] 
          [@s.param name="1"]<span id="elementIndex">{0}</span>[/@s.param] 
        [/@s.text]
      [/#assign]
      <legend>${outputDescription}</legend> 
      <div id="contributesBlock" class="contentElements">
        <div class="itemIndex">[@s.text name="preplanning.outputs.contributes" /] </div>
        [#-- Contribute area --]
        [#-- Add contribute --]
         <div class="fullBlock addContributeBlock">
          [@customForm.select name="contributions"  i18nkey="preplanning.midOutcomes.addContribute" showTitle=false listName="midOutcomesList" keyFieldName="id"  displayFieldName="description" className="contributes" /]
        </div> 
      </div>  
      [#-- Description --]
      [@customForm.textArea name="outputDescription" i18nkey="preplanning.outputs.outputDescription" required=true /] 
    </div> 
    [#-- End Output template  --]
    [#-- Contribute template --]
    [@contributeTemplate.outputs template=true canRemove=true /]
    [#-- End Contribute template --]   
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]
