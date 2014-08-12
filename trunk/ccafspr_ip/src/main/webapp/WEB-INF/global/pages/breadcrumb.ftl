[#ftl]
<ul id="breadcrumb">
  [#if currentSection??] <li><a href="./"> [@s.text name="menu.${currentSection}" /]</a></li>  
  
    [#if currentPrePlanningSection??] <li><a href="#"> [@s.text name="menu.secondary.preplanning.${currentPrePlanningSection}" /] </a></li>
      [#if currentStage??] <li><a href="./${currentStage}.do"> [@s.text name="menu.preplanning.submenu.${currentStage}" /] </a></li>[/#if] 
    [/#if]
    [#if currentPlanningSection??] <li><a href="#">  [@s.text name="menu.secondary.planning.${currentPlanningSection}"/] </a></li>
      [#if currentStage??] <li><a href="./${currentStage}.do"> [@s.text name="menu.planning.submenu.${currentStage}"/] </a></li>[/#if] 
    [/#if] 
    
  [/#if]
</ul>
