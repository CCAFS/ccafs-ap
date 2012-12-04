[#ftl]
[#macro input name value="-NULL" type="text" i18nkey="" disabled=false required=false errorField=""]
<div>
  <label for="${name}">[#if i18nkey==""][@s.text name="${name}"/][#else][@s.text name="${i18nkey}"/][/#if]
  [#if required]<span class="red">*</span>[/#if]
  </label>
  [#if errorField==""][@s.fielderror cssClass="fieldError" fieldName="${name}"/][#else][@s.fielderror cssClass="fieldError" fieldName="${errorfield}"/][/#if]
  <input type="${type}" id="${name}" name="${name}" value="[#if value=="-NULL"][@s.property value="${name}"/][#else]${value}[/#if]" [#if disabled]readonly="readonly"[/#if] />
</div>
[/#macro]

[#macro textArea name value="-NULL" i18nkey="" rows=10 cols=10 disabled=false required=false errorfield=""]
  <div class="textArea">
      <label for="${name}">[#if i18nkey==""][@s.text name="${name}"/][#else][@s.text name="${i18nkey}"/][/#if] 
        [#if required]<span class="red">*</span>[/#if]      
      </label>
      
      [#if errorfield==""][@s.fielderror cssClass="fieldError" fieldName="${name}"/][#else][@s.fielderror cssClass="fieldError" fieldName="${errorfield}"/][/#if]
      <textarea name="${name}" cols="${cols}" rows="${rows}" id="${name}" [#if disabled]readonly="readonly"[/#if] />[#if value=="-NULL"][@s.property value="${name}"/][#else] [@s.text name="${value}" /] [/#if]</textarea>
  </div>
[/#macro]


[#macro checkbox name value="-NULL" i18nkey="" disabled=false checked=false]
  <div>
    <label for="${name}">[#if i18nkey==""][@s.text name="${name}"/][#else][@s.text name="${i18nkey}"/][/#if]</label>
    <input type="checkbox" name="${name}" value="[#if value=="-NULL"][@s.property value="${name}"/][#else]${value}[/#if]" [#if checked]checked="true"[/#if]>
  </div>
[/#macro]

[#macro radioButton name value="-NULL" i18nkey="" disabled=false checked=false]
  <span> <input type="radio" name="${name}" value="[#if value=="-NULL"][@s.property value="${name}"/][#else]${value}[/#if]" [#if checked]checked="true"[/#if] id="${name}">
  <label for="${name}"> [#if i18nkey==""][@s.text name="${name}"/][#else][@s.text name="${i18nkey}"/][/#if] </label>  
  </span>
[/#macro]

[!-- We must check the radio button macro --]

[#macro radioBtnGroup name listOfValues fieldKeyName fieldValueName defaultValue="" i18nkey="" disabled=false required=false errorField="" checked=false]
  <div>
    <span class="radioButtonGruopTitle">
  </div>
  [#if i18nkey ==""]  
    [@s.radio label="${name}" name="${name}" list="${listOfValues}" listKey="${fieldKeyName}" listValue="${fieldValueName}" value="${defaultValue}" /]
  [#else]
    [@s.radio label="${i18nkey}" name="${name}" list="${listOfValues}" listKey="${fieldKeyName}" listValue="${fieldValueName}" value="${defaultValue}" /]
  [/#if]  
[/#macro]

