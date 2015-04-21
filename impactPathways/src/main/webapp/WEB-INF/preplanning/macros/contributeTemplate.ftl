[#ftl]

[#macro midOutcomes midOutcome_index="0" parent_index="0" value="-1" description="description" template=false canRemove=false ] 
  [#if template]
     <div id="contributeTemplate" class="contributions" style="display:none">  
        <input id="contributeId" type="hidden" name="id" value="" />
        <p></p>
  [#else] 
    <div class="contributions">  
      <input id="contributeId" type="hidden" name="midOutcomes[${midOutcome_index}].contributesTo" value="${value}" />
      <p>${description}</p>
  [/#if] 
  [#if canRemove]
      [#-- remove link --]
      <div class="removeLink">            
        <img src="${baseUrl}/images/global/icon-remove.png" />
        <a id="removeContribute-${parent_index}" href="" class="removeContribute">[@s.text name="preplanning.midOutcomes.removeContribute" /]</a>
      </div> 
  [/#if]
    </div>
[/#macro]

[#macro midOutcomesRPL midOutcomeRPL_index="0" midOutcomeRPL_value="-1" parent_index="0" parent_id="-1" description="description" listName="" i18nkey=""  template=false canRemove=false ] 
   
  [#if template] 
    <div id="contributeTemplate" class="contributions" style="display:none"> 
      <input id="contributeId" type="hidden" name="id" value="${parent_id}" />
      <h6 id="midOutcomeDescription">[@s.text name="preplanning.midOutcomesRPL.midOutcomeDescription" /]</h6>
      <p id="description"></p>
      [#-- Outcome Indicators --]
      <h6>[@s.text name="preplanning.midOutcomesRPL.selectIndicators" /]</h6>
      <div id="midOutcomeIndicators" class="midOutcomeIndicators fullBlock">
        <div class="checkboxGroup vertical">
          [@s.fielderror cssClass="fieldError" fieldName="indicatorsSelected"/]
        </div>
      </div>
  [#else]  
    <div id="" class="contributions">
      <input id="contributeId" type="hidden" name="midOutcomes[${midOutcomeRPL_index}].translatedOf" value="${parent_id}" />
      <h6 id="midOutcomeDescription">[@s.text name="preplanning.midOutcomesRPL.midOutcomeDescription" /]</h6>
      <p id="description">[@s.text name="midOutcomes[${midOutcomeRPL_index}].translatedOf[${parent_index}].description" /]</p>
      [#-- Outcome Indicators --]
      <h6>[@s.text name="preplanning.midOutcomesRPL.selectIndicators" /]</h6>
      <div id="midOutcomeIndicators" class="midOutcomeIndicators fullBlock">
        <div class="checkboxGroup vertical"> 
         
          <div class="elementIndicator">
            <input type="checkbox" name="__midOutcomes[0].indicators[-1].parent" value="277" id="indicators.parent-1" checked="checked" class="midOutcomeIndicator">
            <label for="indicators.parent-1" class="checkboxLabel">FPL 1 midOutcome #3 - Indicator 1</label><div class="fields">
            <div class="target">
              <div class="input">
                <h6> <label for="target">Target: </label> </h6>
                <input type="text" id="target" name="__midOutcomes[0].indicators[-1].target" value="">
              </div>
            </div>
            <div class="narrative">
              <div class="textArea "> 
                <h6> <label for="description">Narrative explanation of indicator target contribution: <span class="red">*</span> </label> </h6>
                <textarea name="__midOutcomes[0].indicators[-1].description" id="description" class="ckeditor" placeholder=""></textarea>
              </div>
            </div>
          </div>
          </div>
        
          <input type="checkbox" name="indicators.parent" value="278" id="indicators.parent-2" class="midOutcomeIndicator">
          <label for="indicators.parent-2" class="checkboxLabel">FPL 1 midOutcome #3 - Indicator 2</label>
          <input type="hidden" id="__multiselect_midOutcomesRPL_indicators_parent" name="__multiselect_indicators.parent" value="">
        
        
          [@s.fielderror cssClass="fieldError" fieldName="indicatorsSelected"/]
          [@s.checkboxlist value="midOutcomes[${midOutcomeRPL_index}].parentIndicatorsIDs" name="indicators.parent" list="midOutcomes[${midOutcomeRPL_index}].translatedOf[${parent_index}].indicators" listKey="id" listValue="description" cssClass="midOutcomeIndicator" /]
        </div>
      </div>
  [/#if]
  [#if canRemove]
    [#-- remove link --]
    <div class="removeLink">            
      <img src="${baseUrl}/images/global/icon-remove.png" />
      <a id="removeContribute-${parent_index}" href="" class="removeContribute">[@s.text name="preplanning.midOutcomesRPL.removeContribute" /]</a>
    </div> 
  [/#if]
  </div>
[/#macro]

[#macro outputs output_index="0" parent_index="0" value="-1" description="description" template=false canRemove=false ] 
  [#if template]
     <div id="contributeTemplate" class="contributions" style="display:none">  
        <input name="_contributesTo" type="hidden" value="-1" />
        <p></p>  
  [#else] 
    <div class="contributions">  
      <input type="hidden" name="outputs[${output_index}].contributesTo" value="${value}" />
      <p>${description}</p>
  [/#if] 
  [#if canRemove]
      [#-- remove link --]      
      <div class="removeLink">            
        <img src="${baseUrl}/images/global/icon-remove.png" />
        <a id="removeContribute-${parent_index}" href="" class="removeContribute">[@s.text name="preplanning.outputs.removeContribute" /]</a>
      </div> 
  [/#if]
    </div>
[/#macro]