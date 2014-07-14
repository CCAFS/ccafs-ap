[#ftl]
[#macro outcomes outcome_index="0" indicator_index="0" value="-1" template=false]
  <div class="indicator" style="display:block">
  [#if template]
    <input type="hidden" name="id" value="${value}" />
    [@customForm.textArea showTitle=false name="description" i18nkey="preplanning.outcomes.outcome" required=true /]
    [@customForm.input name="target"  i18nkey="preplanning.outcomes.target" /]
  [#else]
    <input type="hidden" name="outcomes[${outcome_index}].indicators[${indicator_index}].id" value="${value}" />
    [@customForm.textArea showTitle=false name="outcomes[${outcome_index}].indicators[${indicator_index}].description" i18nkey="preplanning.outcomes.outcome" required=true /]
    [@customForm.input name="outcomes[${outcome_index}].indicators[${indicator_index}].target"  i18nkey="preplanning.outcomes.target" /]
  [/#if]
    [#-- remove link --]      
    <div class="removeLink">            
      <img src="${baseUrl}/images/global/icon-remove.png" />
      <a id="removeOutcomeIndicator" href="" class="removeOutcomeIndicator">[@s.text name="preplanning.outcomes.removeIndicator" /]</a>
    </div>
  </div> 
[/#macro]

[#macro midOutcomes midOutcome_index="0" indicator_index="0" value="-1" template=false]
  <div class="indicator" style="display:block">
  [#if template] 
    <input type="hidden" name="id" value="${value}" />
    [@customForm.textArea showTitle=false name="indicatorDescription" i18nkey="preplanning.midOutcomes.midOutcome" required=true /]
    [@customForm.input name="indicatorTarget"  i18nkey="preplanning.midOutcomes.target" /]
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