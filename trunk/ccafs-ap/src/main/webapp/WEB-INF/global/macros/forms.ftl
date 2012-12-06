[#ftl]
[#macro input name value="-NULL" type="text" i18nkey="" disabled=false required=false errorField=""]
<div class="input">
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
    <textarea name="${name}" cols="${cols}" rows="${rows}" id="${name}" [#if disabled]readonly="readonly"[/#if] />[#if value=="-NULL"][@s.property value="${name}"/][#else]${value}[/#if]</textarea>
  </div>
[/#macro]


[#macro checkbox name value="-NULL" id="" i18nkey="" disabled=false checked=false]
  <div class="checkbox">
    <label for="${name}">[#if i18nkey==""][@s.text name="${name}"/][#else][@s.text name="${i18nkey}"/][/#if]</label>
    <input type="checkbox" id="${name}" name="${name}" value="[#if value=="-NULL"][@s.property value="${name}"/][#else]${value}[/#if]" [#if checked]checked="true"[/#if] />
  </div>
[/#macro]

[#macro radioButtonGroup label name listName displayFieldName="" keyFieldName="" value="-NULL" i18nkey="" disabled=false required=false errorField="" checked=false]
  <div class="radio">
    <h6>[#if i18nkey==""]${label}[#else][@s.text name="${i18nkey}" /][/#if][#if required]<span class="red">*</span>[/#if]:</h6>
    [#if errorField==""][@s.fielderror cssClass="fieldError" fieldName="${name}"/][#else][@s.fielderror cssClass="fieldError" fieldName="${errorfield}"/][/#if]
    <div class="radiosList">
    [#if value=="-NULL"]
      [#assign customValue][@s.property value="${name}" /][/#assign]
    [#else]
      [#assign customValue]${value}[/#assign]
    [/#if]
    [#if keyFieldName == ""]
      [@s.radio name="${name}" list="${listName}" value="${customValue}" disabled="${disabled?string}" /]
    [#else]
      [@s.radio name="${name}" list="${listName}" listKey="${keyFieldName}" listValue="${displayFieldName}" value="${customValue}" disabled="${disabled?string}" /]
    [/#if]
    </div>    
  </div> 
[/#macro]


[#-- The following macros aren't tested yet. --]

[#macro radioButton name value="-NULL" i18nkey="" disabled=false checked=false]
  <div class="radio">
    <input type="radio" name="${name}" value="[#if value=="-NULL"][@s.property value="${name}"/][#else]${value}[/#if]" [#if checked]checked="true"[/#if] id="${name}">
    <label for="${name}"> [#if i18nkey==""][@s.text name="${name}"/][#else][@s.text name="${i18nkey}"/][/#if] </label>
  </div>
[/#macro]

[#macro addRemoveLists name id selectedList allOptionList i18nkey="" disabled=false required=false errorfield=""]
  <div id="${id}-lists">
    <div class="selectTo">
      <select name="${name}" id="${id}">
        [#list selectedList as item]
          <option value="${item.id}">${item.name}</option>
        [/#list]
      </select>
    </div>
    
    <a href="" id="btn-add">Add &raquo;</a>
    <a href="" id="btn-remove">&laquo; Remove</a>
    
    <div class="selectFrom">
      <select id="${id}">
        [#list allOptionsList as item]
          [#if test="%{ #item not in #selectedList}"]
            <option value="${item.id}">${item.name}</option>
          [/#if]
        [/#list]
      </select>
    </div>
  </div>
[/#macro]

