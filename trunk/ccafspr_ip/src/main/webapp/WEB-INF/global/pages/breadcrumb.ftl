[#ftl]
  
<ul id="breadcrumb">
  [#if currentSection??] <li><a href="#"> [@s.text name="menu.${currentSection}" /]</a></li> [/#if]
  [#if currentPrePlanningSection??] <li><a href="#"> [@s.text name="menu.secondary.preplanning.${currentPrePlanningSection}" /] </a></li>[/#if]
  [#if currentPlanningSection??] <li><a href="#">  [@s.text name="menu.secondary.planning.${currentPlanningSection}"/] </a></li>[/#if]
  [#if currentStage??] <li><a href="#"> ${currentStage} </a></li>[/#if] 
</ul>
