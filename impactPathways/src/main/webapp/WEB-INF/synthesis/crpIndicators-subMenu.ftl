[#ftl]
[#-- Submit controller 
<script src="${baseUrl}/js/projects/projectSubmit.js"></script>
--]
[#assign currCss= "currentSection"]

<nav id="secondaryMenu" class="">
  <ul> 
    <li class="">
      <p>[@s.text name="reporting.synthesis.crpIndicators" /]</p>
      <ul>
        [#list 1..5 as indicator]  
          [@menu actionName="crpIndicators" stageName="indicator-${indicator_index+1}" index=indicator_index+1 textName="Indicator type ${indicator_index+1}" /]
        [/#list]
      </ul>
    </li>
  </ul>
</nav>

[#-- Menu element --]
[#macro menu actionName stageName index textName disabled=false active=true subText=""]
  [#if active]
    <li id="menu-${actionName}" class="[#if currentSubStage == stageName]${currCss}[/#if]">
      [#if disabled]
        <a class="disabled" href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]">[@s.text name=textName /]</a>
      [#else]
        [#if canEdit && !sectionCompleted(actionName)]
          <a href="[@s.url action=actionName][@s.param name='liaisonInstitutionID']${(liaisonInstitutionID)!}[/@s.param][@s.param name='indicatorTypeID']${(index)!}[/@s.param][@s.param name='edit']true[/@s.param][/@s.url]" title="[#if subText != ""]${subText}[/#if]">
            [@s.text name=textName /] 
          </a> 
        [#else]
          <a href="[@s.url action=actionName][@s.param name='liaisonInstitutionID']${(liaisonInstitutionID)!}[/@s.param][@s.param name='indicatorTypeID']${(index)!}[/@s.param][/@s.url]" title="[#if subText != ""]${subText}[/#if]">
            [@s.text name=textName /]
          </a> 
        [/#if]
      [/#if]
    </li>
  [/#if]
[/#macro]

[#-- Submitted CSS class for section status--]
[#function sectionCompleted actionName]
  [#assign status= (action.getProjectSectionStatus(actionName))!{} /]
  [#if status?has_content]
    [#if !(status.missingFieldsWithPrefix)?has_content]
      [#return true]
    [#else]
      [#return false]
    [/#if]
  [#else]
    [#return false]  
  [/#if]
[/#function]