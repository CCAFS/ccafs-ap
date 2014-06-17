[#ftl]
[#--
 
 * This file is part of CCAFS Planning and Reporting Platform.
 *
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 *
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see 
 * <http://www.gnu.org/licenses/>
  
--]

[#macro input name value="-NULL" type="text" i18nkey="" disabled=false required=false errorField="" help="" display=true className="-NULL" showTitle=true]
  <div class="input" [#if !display]style="display: none;"[/#if]>
    [#if showTitle]
      <h6>
        <label for="${name}">[#if i18nkey==""][@s.text name="${name}"/][#else][@s.text name="${i18nkey}"/][/#if]
          [#if required]<span class="red">*</span>[/#if]
        </label>
        [#if help != ""]
          <img src="${baseUrl}/images/global/icon-help2.png" title="[@s.text name="${help}"/]" />
        [/#if]
      </h6>
    [/#if]
    [#if errorField==""][@s.fielderror cssClass="fieldError" fieldName="${name}"/][#else][@s.fielderror cssClass="fieldError" fieldName="${errorfield}"/][/#if]
    <input type="${type}" id="${name}" name="${name}" value="[#if value=="-NULL"][@s.property value="${name}"/][#else]${value}[/#if]" [#if disabled]disabled="disabled"[/#if] [#if className != "-NULL"] class="${className}" [/#if] />
  </div>
[/#macro]

[#macro textArea name value="-NULL" i18nkey="" disabled=false required=false errorfield="" help="" display=true]
  <div class="textArea" [#if !display]style="display: none;"[/#if]>
    <h6>
      <label for="${name}">[#if i18nkey==""][@s.text name="${name}"/][#else][@s.text name="${i18nkey}"/][/#if]
      [#if required]<span class="red">*</span>[/#if]
      </label>
      [#if help != ""]
        <img src="${baseUrl}/images/global/icon-help2.png" title="[@s.text name="${help}"/]" />
      [/#if]
    </h6>
    [#if errorfield==""][@s.fielderror cssClass="fieldError" fieldName="${name}"/][#else][@s.fielderror cssClass="fieldError" fieldName="${errorfield}"/][/#if]
    <textarea name="${name}" id="${name}" [#if disabled]disabled="disabled"[/#if] />[#if value=="-NULL"][@s.property value="${name}"/][#else]${value}[/#if]</textarea>
  </div>
[/#macro]


[#macro checkbox name value="-NULL" label="" i18nkey="" disabled=false checked=false required=false display=true help=""]
  <div class="checkbox" [#if !display]style="display: none;"[/#if]>
    <label for="${name}">
      <h6>[#if i18nkey==""]${label}[#else][@s.text name="${i18nkey}" /][/#if][#if required]<span class="red">*</span>[/#if]:</h6>
      [#if help != ""]
        <img src="${baseUrl}/images/global/icon-help2.png" title="[@s.text name="${help}"/]" />
      [/#if]
    </label>
    <input type="checkbox" id="${name}" name="${name}" value="${value}" [#if checked]checked="true"[/#if] />     
  </div>
[/#macro]

[#macro checkboxGroup label name listName displayFieldName="" keyFieldName="" value="-NULL" i18nkey="" disabled=false required=false errorField="" checked=false display=true]
  <div class="checkbox" [#if !display]style="display: none;"[/#if]>
    <h6>[#if i18nkey==""]${label}[#else][@s.text name="${i18nkey}" /][/#if][#if required]<span class="red">*</span>[/#if]:</h6>
    [#if errorField==""][@s.fielderror cssClass="fieldError" fieldName="${name}"/][#else][@s.fielderror cssClass="fieldError" fieldName="${errorfield}"/][/#if]
    <div class="checkboxList">
    [#if value=="-NULL"]
      [#assign customValue][@s.property value="${name}" /][/#assign]
    [#else]
      [#assign customValue]${value}[/#assign]
    [/#if]
    [#if keyFieldName == ""]
      [@s.checkboxlist name="${name}" list="${listName}" value="${customValue}" disabled="${disabled?string}" /]
    [#else]
      [@s.checkboxlist name="${name}" list="${listName}" listKey="${keyFieldName}" listValue="${displayFieldName}" value="${customValue}" disabled="${disabled?string}" /]
    [/#if]
    </div>    
  </div> 
[/#macro]

[#macro radioButtonGroup label name listName class="" displayFieldName="" keyFieldName="" value="-NULL" i18nkey="" disabled=false required=false errorField="" checked=false help="" display=true]
  <div class="radioGroup" [#if !display]style="display: none;"[/#if]>
    <h6>[#if i18nkey==""]${label}[#else][@s.text name="${i18nkey}" /][/#if][#if required]<span class="red">*</span>[/#if]:</h6>
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

[#macro select name listName label="" keyFieldName="" displayFieldName="" value="-NULL" i18nkey="" disabled=false required=false errorField="" selected=false className="" multiple=false help="" display=true showTitle=true]
  <div class="select" [#if !display]style="display: none;"[/#if]>
    [#if showTitle]
      <h6>
        [#if i18nkey==""]${label}[#else][@s.text name="${i18nkey}" /][/#if][#if required]<span class="red">*</span>[/#if]:
        [#if help != ""]
          <img src="${baseUrl}/images/global/icon-help2.png" title="[@s.text name="${help}"/]" />
        [/#if]
      </h6>
    [/#if]
    [#if errorField==""][@s.fielderror cssClass="fieldError" fieldName="${name}"/][#else][@s.fielderror cssClass="fieldError" fieldName="${errorfield}"/][/#if]
    <div class="selectList">
      [#if value=="-NULL"]
        [#assign customValue][@s.property value="${name}" /][/#assign]
      [#else]
        [#assign customValue]${value}[/#assign]
      [/#if]
      [#if help!=""]
        [#assign helpText][@s.text name="${help}" /][/#assign]
      [#else]
        [#assign helpText][/#assign]
      [/#if]
      [#if keyFieldName == ""]
        [#if multiple]
          [@s.select name="${name}" list="${listName}" value="${customValue}" disabled="${disabled?string}" cssClass="${className}" multiple="true" tooltip="${helpText}" /]
        [#else]
          [@s.select name="${name}" list="${listName}" value="${customValue}" disabled="${disabled?string}" cssClass="${className}" tooltip="${helpText}" /]
        [/#if]
      [#else]
        [#if multiple]
          [@s.select name="${name}" list="${listName}" listKey="${keyFieldName}" listValue="${displayFieldName}" value="${customValue}" disabled="${disabled?string}" cssClass="${className}" multiple="true" tooltip="${helpText}" /]
        [#else]
          [@s.select name="${name}" list="${listName}" listKey="${keyFieldName}" listValue="${displayFieldName}" value="${customValue}" disabled="${disabled?string}" cssClass="${className}" tooltip="${helpText}" /]
        [/#if]        
      [/#if]
    </div>
  </div>  
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