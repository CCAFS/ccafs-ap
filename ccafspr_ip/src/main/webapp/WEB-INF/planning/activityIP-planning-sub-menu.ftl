[#ftl]
<nav id="stageMenu" class="clearfix">
  <ul> 
    <li [#if currentStage == "activityImpactPathway"] class="currentSection" [/#if]><a href="
        [@s.url action='activityImpactPathway' includeParams='get'][/@s.url]
      ">[@s.text name="menu.planning.submenu.impactPathwayPrimary" /]</a></li>
    <li [#if currentStage == "activityIpOtherContributions" ] class="currentSection" [/#if]><a href="
        [@s.url action='activityIpOtherContributions' includeParams='get'][/@s.url]
      ">[@s.text name="menu.planning.submenu.impactPathwayOther" /]</a></li>  
  </ul>
</nav> 

  