[#ftl]
<nav id="stageMenu" class="clearfix"> 
  <ul>  
    <li [#if currentSubStage == "description"] class="currentSection" [/#if]>
      <a href="[@s.url action='description' includeParams='get'][/@s.url]">
        [@s.text name="menu.planning.submenu.projectDescription" /]
      </a>
    </li>
    <li [#if currentSubStage == "partners" ] class="currentSection" [/#if]>
      <a href="[@s.url action='partnerLead' includeParams='get'][/@s.url]">
        [@s.text name="menu.planning.submenu.projectPartners" /]
      </a>
    </li>
    <li [#if currentSubStage == "locations" ] class="currentSection" [/#if]>
      <a href="[@s.url action='locations' includeParams='get'][/@s.url]">
        [@s.text name="menu.planning.submenu.projectLocations" /]
      </a>
    </li> 
  </ul>
</nav> 
