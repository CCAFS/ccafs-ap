[#ftl]

[#macro midOutcomes midOutcome_index="0" parent_index="0" value="-1" description="description" template=false] 
  [#if template]
     <div id="contributeTemplate" class="contributions" style="display:none">  
        <input type="hidden" name="id" value="" />
        <p></p> 
        [#-- remove link --]      
        <div class="removeLink">            
          <img src="${baseUrl}/images/global/icon-remove.png" />
          <a id="removeContribute" href="" class="removeContribute">[@s.text name="preplanning.midOutcomes.removeContribute" /]</a>
        </div>
    </div>
  [#else]
    <div class="contributions">  
      <input type="hidden" name="outputs[${midOutcome_index}].parents[${parent_index}].id" value="${value}" />
      <p>${description}</p> 
      [#-- remove link --]      
      <div class="removeLink">            
        <img src="${baseUrl}/images/global/icon-remove.png" />
        <a id="removeContribute" href="" class="removeContribute">[@s.text name="preplanning.midOutcomes.removeContribute" /]</a>
      </div>
    </div>
  [/#if] 
[/#macro]

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
