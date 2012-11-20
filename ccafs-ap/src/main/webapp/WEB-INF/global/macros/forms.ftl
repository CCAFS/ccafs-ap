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