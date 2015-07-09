[#ftl]
<nav id="stageMenu" class="clearfix"> 
  <ul> 
    <li [#if currentSubStage == "overviewByMogs"] class="currentSection" [/#if]>
      <a href="[@s.url action='outputs' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectOutputs.overviewByMogs" /]</a>
    </li>
    <li [#if currentSubStage == "deliverables" ] class="currentSection" [/#if]>
      <a href="[@s.url action='deliverablesList' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectOutputs.deliverables" /]</a>
    </li> 
  </ul>
</nav> 



  