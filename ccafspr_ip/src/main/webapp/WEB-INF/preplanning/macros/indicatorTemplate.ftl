[#ftl]
[#macro outcomes outcome_index="0" indicator_index="0" value="-1" template=false i18nkey="" show_remove_link=true]
  <div class="indicator" style="display:block">
  [#if template]
    <input type="hidden" name="outcomes[${outcome_index}].indicators[0].id" value="${value}" />
    [@customForm.textArea showTitle=false value="" name="outcomes[${outcome_index}].indicators[${indicator_index}].description" i18nkey="preplanning.outcomes.outcome" required=true /]
    [@customForm.input name="outcomes[${outcome_index}].indicators[${indicator_index}].target"  i18nkey="preplanning.outcomes.target" /]
  [#else]
    <input type="hidden" name="outcomes[${outcome_index}].indicators[${indicator_index}].id" value="${value}" />
    [@customForm.textArea showTitle=true name="outcomes[${outcome_index}].indicators[${indicator_index}].description" i18nkey="${i18nkey}" required=true /]
    [@customForm.input name="outcomes[${outcome_index}].indicators[${indicator_index}].target"  i18nkey="preplanning.outcomes.target" /]
  [/#if]
  [#if show_remove_link]
    [#-- remove link --]      
    <div class="removeLink">            
      <img src="${baseUrl}/images/global/icon-remove.png" />
      <a id="removeOutcomeIndicator" href="" class="removeOutcomeIndicator">[@s.text name="preplanning.outcomes.removeIndicator" /]</a>
    </div> 
  [/#if]
  </div>
[/#macro]

[#macro midOutcomes midOutcome_index="0" indicator_index="0" value="-1" template=false]
  <div class="indicator" style="display:block">
  [#if template] 
    <input type="hidden" name="id" value="${value}" />
    [@customForm.textArea showTitle=false name="description" i18nkey="preplanning.midOutcomes.midOutcome" required=true /]
    [@customForm.input name="target"  i18nkey="preplanning.midOutcomes.target" /]
  [#else]
    <input type="hidden" name="midOutcomes[${midOutcome_index}].indicators[${indicator_index}].id" value="${value}" />
    [@customForm.textArea showTitle=false name="midOutcomes[${midOutcome_index}].indicators[${indicator_index}].description" i18nkey="preplanning.midOutcomes.midOutcome" required=true /]
    [@customForm.input name="midOutcomes[${midOutcome_index}].indicators[${indicator_index}].target"  i18nkey="preplanning.midOutcomes.target" /]
  [/#if]
    [#-- remove link --]      
    <div class="removeLink">            
      <img src="${baseUrl}/images/global/icon-remove.png" />
      <a id="removeMidOutcomeIndicator" href="" class="removeMidOutcomeIndicator">[@s.text name="preplanning.midOutcomes.removeIndicator" /]</a>
    </div>
  </div> 
[/#macro]

[#macro midOutcomesRPL midOutcomeRPL_index="0" parent_index="0" template=false canRemove=false]
  [#if template] 
    <div id="indicatorTemplate" class="elementIndicator" style="display:none">
      <input type="hidden" name="id" value="-1" />
      <input id="midOutcomes[${midOutcomeRPL_index}].indicators-${midOutcomeRPL_index}${parent_index}" class="midOutcomeIndicator" name="midOutcomes[${midOutcomeRPL_index}].indicators.parent" type="checkbox" value="" />
      <label for="midOutcomes[${midOutcomeRPL_index}].indicators-${midOutcomeRPL_index}${parent_index}" class="checkboxLabel"> Indicator Label</label>
      <div class="fields" style="display:none">
        [#-- Target --]
        <div class="target">
        [@customForm.input name="target"  i18nkey="preplanning.midOutcomes.target" /]
        </div>
        [#-- Narrative explanation --]
        <div class="narrative">
        [@customForm.textArea showTitle=true name="description" i18nkey="preplanning.midOutcomesRPL.midOutcomeIndicators.justification" required=true /]
        </div>
      </div> 
  [#else]
    <div class="elementIndicator">
      <input type="hidden" name="id" value="-1" />
      <input id="midOutcomes[${midOutcomeRPL_index}].indicators-${midOutcomeRPL_index}${parent_index}" class="midOutcomeIndicator" name="midOutcomes[${midOutcomeRPL_index}].indicators" type="checkbox" value="" />
      <label for="midOutcomes[${midOutcomeRPL_index}].indicators-${midOutcomeRPL_index}${parent_index}" class="checkboxLabel"> Indicator Label</label>
      [#-- Target --]
      [@customForm.input name="target"  i18nkey="preplanning.midOutcomes.target" value="${midOutcomes[midOutcomeRPL_index].indicators[parent_index].target}" /]
      [#-- Narrative explanation --]
      [@customForm.textArea showTitle=true name="midOutcomes[${midOutcomeRPL_index}].translatedOf[${parent_index}].justification" i18nkey="preplanning.midOutcomesRPL.midOutcomeIndicators.justification" required=true /]
    
  [/#if]
  [#if canRemove] 
    [#-- remove link --]      
    <div class="removeLink">            
      <img src="${baseUrl}/images/global/icon-remove.png" />
      <a id="removeMidOutcomeIndicator" href="" class="removeMidOutcomeIndicator">[@s.text name="preplanning.midOutcomes.removeIndicator" /]</a>
    </div>
  [/#if]
  </div>
[/#macro]

[#macro outputs output_index="0" indicator_index="0" value="-1" template=false]
  <div class="indicator" style="display:block">
  [#if template]
    <input type="hidden" name="id" value="${value}" />
    [@customForm.textArea showTitle=false name="description" i18nkey="preplanning.outputs.output" required=true /]
    [@customForm.input name="target"  i18nkey="preplanning.outputs.target" /]
  [#else]
    <input type="hidden" name="outputs[${output_index}].indicators[${indicator_index}].id" value="${value}" />
    [@customForm.textArea showTitle=false name="outputs[${output_index}].indicators[${indicator_index}].description" i18nkey="preplanning.outputs.output" required=true /]
    [@customForm.input name="outputs[${output_index}].indicators[${indicator_index}].target"  i18nkey="preplanning.outputs.target" /]
  [/#if]
    [#-- remove link --]      
    <div class="removeLink">            
      <img src="${baseUrl}/images/global/icon-remove.png" />
      <a id="removeOutputIndicator" href="" class="removeOutputIndicator">[@s.text name="preplanning.outputs.removeIndicator" /]</a>
    </div>
  </div> 
[/#macro]