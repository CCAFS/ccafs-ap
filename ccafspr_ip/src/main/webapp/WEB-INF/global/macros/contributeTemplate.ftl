[#ftl]

[#macro midOutcomes midOutcome_index="0" parent_index="0" value="-1" description="description" template=false canRemove=false ] 
  [#if template]
     <div id="contributeTemplate" class="contributions" style="display:none">  
        <input id="contributeId" type="hidden" name="id" value="" />
        <p></p>
  [#else] 
    <div class="contributions">  
      <input id="contributeId" type="hidden" name="midOutcomes[${midOutcome_index}].contributesTo[${parent_index}].id" value="${value}" />
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

[#macro midOutcomesRPL midOutcomeRPL_index="0" parent_index="0" midOutcomeRPL_value="-1" description="description" listName="" i18nkey=""  template=false canRemove=false ] 
  [#if template]
     <div id="contributeTemplate" class="contributions" style="display:none">  
        <input id="contributeId" type="hidden" name="id" value="" />
        <p></p>
  [#else] 
    <div id="midOutcome-${midOutcomeRPL_value}" class="contributions" style="display:none">  
      <input id="contributeId" type="hidden" name="midOutcomesRPL[${midOutcomeRPL_index}].contributesTo[${parent_index}].id" value="${midOutcomeRPL_value}" />
      <p>[@s.text name="midOutcomes[${parent_index}].description" /]</p>
      <h6>[@s.text name="preplanning.midOutcomesRPL.selectIndicators" /]</h6>
      [#-- Outcome Indicators --]
      <div id="midOutcomeIndicators" class="fullBlock">
        <p>[@s.text name="preplanning.midOutcomesRPL.midOutcomeIndicators" /]</p>
        <div class="checkboxGroup vertical">
          [@s.fielderror cssClass="fieldError" fieldName="indicatorsSelected"/]          
          [@s.checkboxlist name="midOutcomes[${parent_index}].indicators" list="midOutcomes[${parent_index}].indicators" listKey="id" listValue="description" cssClass="checkbox pure-form" /]
        </div>
      </div>
      [#-- Narrative explanation --]
      [@customForm.textArea showTitle=true name="midOutcomesRPL[${midOutcomeRPL_index}].contributesTo[${parent_index}].justification" i18nkey="${i18nkey}" required=true /]
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
        <input id="contributeId" type="hidden" value="-1" />
        <p></p>  
  [#else] 
    <div class="contributions">  
      <input type="hidden" name="outputs[${output_index}].contributesTo[${parent_index}].id" value="${value}" />
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