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