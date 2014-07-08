[#ftl]
[#macro outputs output_index="0" parent_index="0" value="-1" description="description"] 
  <div class="contributions">  
    <input type="hidden" name="outputs[${output_index}].parents[${parent_index}].id" value="${value}" />
    <p>${description}</p> 
    [#-- remove link --]      
    <div class="removeLink">            
      <img src="${baseUrl}/images/global/icon-remove.png" />
      <a id="removeContribute" href="" class="removeContribute">[@s.text name="preplanning.outputs.removeContribute" /]</a>
    </div>
  </div>
[/#macro]

[#macro midOutcome midOutcome_index="0" parent_index="0" value="-1"] 
  <div class="contributions">  
    <input type="hidden" name="midOutcomes[${midOutcome_index}].parents[${parent_index}].id" value="${value}" />
    <p>${parent.description}</p> 
    [#-- remove link --]      
    <div class="removeLink">            
      <img src="${baseUrl}/images/global/icon-remove.png" />
      <a id="removeContribute" href="" class="removeContribute">[@s.text name="preplanning.midOutcomes.removeContribute" /]</a>
    </div>
  </div>
[/#macro]

