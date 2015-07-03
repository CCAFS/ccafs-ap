[#ftl]
[#macro text name readText=false param="" ]
  [#assign customName][#if readText]${name}.readText[#else]${name}[/#if][/#assign]
  [@s.text name="${customName}"][@s.param]${param}[/@s.param][/@s.text]
[/#macro]

[#macro input name value="-NULL" type="text" i18nkey="" disabled=false required=false errorField="" help="" display=true className="-NULL" readOnly=false showTitle=true editable=true ]
  <div class="input" [#if !display]style="display: none;"[/#if]>
    [#if showTitle]
      <h6>
        <label for="${name}">[#if i18nkey==""][@s.text name="${name}"/]:[#else][@s.text name="${i18nkey}"/]:[/#if]
          [#if required]<span class="red">*</span>[/#if]
        </label>
        [#if help != ""]<img src="${baseUrl}/images/global/icon-help2.png" title="[@s.text name="${help}"/]" />[/#if]
      </h6>
    [/#if]
    [#if errorField==""][@s.fielderror cssClass="fieldError" fieldName="${name}"/][#else][@s.fielderror cssClass="fieldError" fieldName="${errorfield}"/][/#if]
    [#if editable]
      <input type="${type}" id="${name}" name="${name}" value="[#if value=="-NULL"][@s.property value="${name?string}"/][#else]${value}[/#if]"  [#if className != "-NULL"] class="${className}" [/#if][#if readOnly] readonly="readonly"[/#if] [#if disabled]disabled="disabled"[/#if] [#if required]required[/#if]/>
    [#else]
      <p>  
        [#if value=="-NULL"] 
          [#assign customValue][@s.property value="${name?string}"/][/#assign] 
          [#if !(customValue)?has_content] 
            [@s.text name="form.values.fieldEmpty" /]
          [#else]
            ${customValue}
          [/#if]
        [#else]
          [#if !value?has_content] 
            [@s.text name="form.values.fieldEmpty" /]
          [#else]
            ${value}
          [/#if] 
        [/#if]
      </p>
    [/#if]
  </div>
[/#macro]

[#macro textArea name editable value="-NULL" i18nkey="" disabled=false required=false errorfield="" help="" addButton=false showTitle=true display=true className="-NULL" editable=true ]
  <div class="textArea [#if addButton] button[/#if]" [#if !display]style="display: none;"[/#if]> 
    [#assign customName]${(i18nkey?has_content)?string(i18nkey,name)}[/#assign]  
    [#assign customLabel][#if !editable]${customName}.readText[#else]${customName}[/#if][/#assign]
  	[#if showTitle]
	    <h6> 
	      <label for="${name}"> [@s.text name="${customLabel}"/]:[#if required]<span class="red">*</span>[/#if]</label>
	      [#if help != ""]<img src="${baseUrl}/images/global/icon-help2.png" title="[@s.text name="${help}"/]" />[/#if]
	    </h6>
    [/#if]
    [#if errorfield==""][@s.fielderror cssClass="fieldError" fieldName="${name}"/][#else][@s.fielderror cssClass="fieldError" fieldName="${errorfield}"/][/#if]
    [#if editable]
      <textarea name="${name}" id="${name}" [#if disabled]disabled="disabled"[/#if] [#if className != "-NULL"] class="ckeditor ${className}" [/#if] [#if required]required[/#if]/>[#if value=="-NULL"][@s.property value="${name}"/][#else]${value}[/#if]</textarea>
    [#else]
      <p>
        [#if value=="-NULL"] 
          [#assign customValue][@s.property value="${name?string}"/][/#assign] 
          [#if !(customValue)?has_content] [@s.text name="form.values.fieldEmpty" /][#else]${customValue?html}[/#if]
        [#else]
          [#if !value?has_content] [@s.text name="form.values.fieldEmpty" /][#else]${value?html}[/#if] 
        [/#if]
      </p>
      
    [/#if] 
  </div>
  [#if addButton]
     <input type="button" class="addButton [@s.text name='${i18nkey}' /]" name="" value="Add [@s.text name='${i18nkey}' /]" />
  [/#if]
[/#macro]

[#macro button i18nkey class="" id="" editable=true]
  <input type="button" class="${class}" id="${id}" value="[@s.text name='${i18nkey}' /]" />
[/#macro]

[#macro checkbox name value="-NULL" label="" i18nkey="" disabled=false className="" checked=false required=false display=true help="" editable=true]
  <div class="checkbox" [#if !display]style="display: none;"[/#if]>
    [#if editable]
      <label for="${name}">
        <input type="checkbox" id="${name}" class="${className}" name="${name}" value="${value}" [#if checked]checked="checked"[/#if] [#if disabled]disabled="disabled[/#if] />
        <input type="hidden" id="__checkbox_${name}" name="__checkbox_${name}" value="${value}" />
        <h6>[#if i18nkey==""]${label}[#else][@s.text name="${i18nkey}" /][/#if][#if required]<span class="red">*</span>[/#if]</h6>
        [#if help != ""]<img src="${baseUrl}/images/global/icon-help2.png" title="[@s.text name="${help}"/]" />[/#if]
      </label>
    [#else]
      [#if checked]<h6 class="checked-${checked?string}">[#if i18nkey==""]${label}[#else][@s.text name="${i18nkey}.readText" /][/#if]</h6>[/#if]
    [/#if]
    
  </div>
[/#macro]

[#macro checkboxGroup label name listName displayFieldName="" keyFieldName="" value="-NULL" i18nkey="" disabled=false required=false errorField="" checked=false display=true editable=true]
  <div class="checkbox" [#if !display]style="display: none;"[/#if]>
    <h6>[#if i18nkey==""]${label}[#else][@s.text name="${i18nkey}" /][/#if][#if required]<span class="red">*</span>[/#if]</h6>
    [#if errorField==""][@s.fielderror cssClass="fieldError" fieldName="${name}"/][#else][@s.fielderror cssClass="fieldError" fieldName="${errorfield}"/][/#if]
    <div class="checkboxList">
    [#if value=="-NULL"]
      [#assign customValue][@s.property value="${name}" /][/#assign]
    [#else]
      [#assign customValue]${value}[/#assign]
    [/#if]
    [#if editable]
      [#if keyFieldName == ""]
        [@s.checkboxlist name="${name}" list="${listName}" value="${customValue}" disabled="${disabled?string}" /]
      [#else]
        [@s.checkboxlist name="${name}" list="${listName}" listKey="${keyFieldName}" listValue="${displayFieldName}" value="${customValue}" disabled="${disabled?string}" /]
      [/#if]
    [#elseif keyFieldName == ""]  
      [@s.text name="form.values.fieldEmpty" /]
    [#else]
      ${customValue}
    [/#if] 
    </div>    
  </div> 
[/#macro]

[#macro radioButtonGroup label name listName class="" displayFieldName="" keyFieldName="" value="-NULL" i18nkey="" disabled=false required=false errorField="" checked=false help="" display=true editable=true]
  <div class="radioGroup" [#if !display]style="display: none;"[/#if]>
    <h6>[#if i18nkey==""]${label}[#else][@s.text name="${i18nkey}" /][/#if][#if required]<span class="red">*</span>[/#if]</h6>
    [#if errorField==""][@s.fielderror cssClass="fieldError" fieldName="${name}"/][#else][@s.fielderror cssClass="fieldError" fieldName="${errorfield}"/][/#if]
    <div class="radiosList">
      [#if value=="-NULL"][#assign customValue][@s.property value="${name}" /][/#assign][#else][#assign customValue]${value}[/#assign][/#if]
      [#if class==""][#assign className="${name}"][#else][#assign className="${class}"][/#if]
      [#if help!=""][#assign helpTitle][@s.text name="${help}" /][/#assign][#else][#assign helpTitle=""][/#if]
        [#if keyFieldName == ""]
          [@s.radio name="${name}" cssClass="${className}" list="${listName}" value="${customValue}" disabled="${disabled?string}" title="${helpTitle}" /]
        [#else]
          [@s.radio name="${name}" cssClass="${className}" list="${listName}" listKey="${keyFieldName}" listValue="${displayFieldName}" value="${customValue}" disabled="${disabled?string}" title="${helpTitle}" /]
        [/#if]
      
    </div>
  </div>
[/#macro]

[#macro select name listName label="" keyFieldName="" displayFieldName="" value="-NULL" i18nkey="" disabled=false required=false errorField="" selected=false className="" multiple=false help="" headerKey="" headerValue="" display=true showTitle=true addButton=false editable=true]
  <div class="select[#if addButton] button[/#if]" [#if !display]style="display: none;"[/#if]>
    [#assign placeholderText][@s.text name="form.select.placeholder" /][/#assign]
    [#if showTitle]
      <h6>
        [#if i18nkey==""]${label} [#else][@s.text name="${i18nkey}" /]:[/#if]
        [#if required]<span class="red">*</span>[/#if]
        [#if help != ""]<img src="${baseUrl}/images/global/icon-help2.png" title="[@s.text name="${help}"/]" />[/#if]
      </h6>
    [/#if]
    [#if errorField==""][@s.fielderror cssClass="fieldError" fieldName="${name}"/][#else][@s.fielderror cssClass="fieldError" fieldName="${errorfield}"/][/#if]
    <div class="selectList">   
      [#if value=="-NULL"]
        [#assign customValue][@s.property value="${name}" /][/#assign]
      [#else]
        [#assign customValue]${value}[/#assign]        
      [/#if]
      [#-- Help text --]
      [#if help!=""][#assign helpText][@s.text name="${help}" /][/#assign][#else][#assign helpText][/#assign][/#if]
      [#if editable] 
        [#if keyFieldName == ""]
          [#if multiple]
            [@s.select name="${name}" list="${listName}" value="${customValue}" disabled="${disabled?string}" cssClass="${className}" multiple="true" tooltip="${helpText}" headerKey="-1" headerValue=placeholderText  /]
          [#else]
            [@s.select name="${name}" list="${listName}" value="${customValue}" disabled="${disabled?string}" cssClass="${className}" tooltip="${helpText}" headerKey="-1" headerValue=placeholderText  /]
          [/#if]
        [#else]
          [#if multiple]
            [@s.select name="${name}" list="${listName}" listKey="${keyFieldName}" listValue="${displayFieldName}" value="${customValue}" disabled="${disabled?string}" cssClass="${className}" multiple="true" tooltip="${helpText}" headerKey="-1" headerValue=placeholderText /]
          [#else]
            [@s.select name="${name}" list="${listName}" listKey="${keyFieldName}" listValue="${displayFieldName}" value="${customValue}" disabled="${disabled?string}" cssClass="${className}" tooltip="${helpText}" headerKey="-1" headerValue=placeholderText /]
          [/#if]
        [/#if] 
      [#else] 
        <p>  
        [#if value=="-NULL"] 
          [#assign customValue][@s.property value="${name}.${displayFieldName}"/][/#assign]  
          [#if !(customValue)?has_content] 
            [@s.text name="form.values.fieldEmpty" /]
          [#else]
            ${customValue}
          [/#if]
        [#else] 
          [#if value?has_content]
            [@s.property value="${name}.${displayFieldName}"/]
          [#else]
            [@s.text name="form.values.fieldEmpty" /]
          [/#if] 
        [/#if]
        </p>
      [/#if]  
    </div> 
  </div>  
  [#if addButton]
     <input type="button" class="addButton [@s.text name='${i18nkey}' /]" name="" value="Add [@s.text name='${i18nkey}' /]" />
  [/#if]
[/#macro]

[#macro inputFile name template=false ]
  [#assign customId][#if template]${name}-template[#else]${name}[/#if][/#assign]
  <!-- Input File ${customId} -->
  [@s.file name="${name}" id="${customId}" cssClass="upload" cssStyle="${template?string('display:none','')}"  /]
[/#macro] 

[#-- The following macros aren't tested yet. --]

[#macro radioButton name value="-NULL" i18nkey="" label="" disabled=false checked=false id="" errorField=""]
  <div class="radioList">
    [#if errorField==""][@s.fielderror cssClass="fieldError" fieldName="${name}"/][#else][@s.fielderror cssClass="fieldError" fieldName="${errorfield}"/][/#if]
    <input type="radio" id="${id}" name="${name}" value="[#if value=="-NULL"][@s.property value="${name}"/][#else]${value}[/#if]" [#if checked]checked="true"[/#if] />
    <label for="${id}"> [#if i18nkey==""]${label}[#else][@s.text name="${i18nkey}"/][/#if] </label>
  </div>
[/#macro]

[#macro addRemoveLists name id selectedList allOptionList i18nkey="" disabled=false required=false errorfield=""]
  <div id="${id}-lists">
    <div class="selectTo">
      <select name="${name}" id="${id?c}">
        [#list selectedList as item]
          <option value="${item.id?c}">${item.name}</option>
        [/#list]
      </select>
    </div>
    
    <a href="" id="btn-add">Add &raquo;</a>
    <a href="" id="btn-remove">&laquo; Remove</a>
    
    <div class="selectFrom">
      <select id="${id}">
        [#list allOptionsList as item]
          [#if test="%{ #item not in #selectedList}"]
            <option value="${item.id?c}">${item.name}</option>
          [/#if]
        [/#list]
      </select>
    </div>
  </div>
[/#macro]