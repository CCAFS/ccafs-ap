[#ftl]
<nav id="stageMenu" class="clearfix"> 
  <ul> 
    <li [#if currentSubStage == "overviewByMogs"] class="currentSection" [/#if]>
      <a href="[@s.url action='outputs' includeParams='get'][/@s.url]">[@s.text name="menu.planning.submenu.projectOutputs.overviewByMogs" /]</a>
    </li>
    <li [#if currentSubStage == "deliverables" ] class="currentSection" [/#if]>
      <a href="[@s.url action='deliverables' includeParams='get'][/@s.url]">[@s.text name="menu.planning.submenu.projectOutputs.deliverables" /]</a>
    </li> 
  </ul>
</nav> 



  