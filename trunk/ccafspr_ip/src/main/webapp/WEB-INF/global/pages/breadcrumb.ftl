[#ftl]
  
<ul id="breadcrumb">
  [#if currentSection??] <li><a href="${baseUrl}/${currentSection}/"> [@s.text name="menu.${currentSection}" /]</a></li> [/#if]
  
  [#if currentPrePlanningSection??] <li><a href="#"> [@s.text name="menu.secondary.preplanning.${currentPrePlanningSection}" /] </a></li>
    [#if currentStage??] <li><a href="#"> [@s.text name="menu.preplanning.submenu.${currentStage}" /] </a></li>[/#if] 
  [/#if]
  [#if currentPlanningSection??] <li><a href="#">  [@s.text name="menu.secondary.planning.${currentPlanningSection}"/] </a></li>
    [#if currentStage??] <li><a href="#"> [@s.text name="menu.preplanning.submenu.${currentStage}"/] </a></li>[/#if] 
  [/#if]
  
  
</ul>
