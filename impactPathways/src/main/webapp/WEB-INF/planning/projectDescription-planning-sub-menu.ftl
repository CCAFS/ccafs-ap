[#ftl]
<nav id="stageMenu" class="clearfix"> 
  <ul> 
    <li [#if currentSubStage == "description"] class="currentSection" [/#if]>
      <a href="[@s.url action='description' includeParams='get'][/@s.url]">
        [@s.text name="menu.planning.submenu.projectDescription" /]
      </a>
    </li>
    <li [#if currentSubStage == "partners" ] class="currentSection" [/#if]>
      <a href="[@s.url action='partners' includeParams='get'][/@s.url]">
        [@s.text name="menu.planning.submenu.projectPartners" /]
      </a>
    </li>
    <li [#if currentSubStage == "projectLocations" ] class="currentSection" [/#if]>
      <a href="[@s.url action='projectLocations' includeParams='get'][/@s.url]">
        [@s.text name="menu.planning.submenu.projectLocations" /]
      </a>
    </li> 
  </ul>
</nav> 
