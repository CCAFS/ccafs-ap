[#ftl]
<nav id="secondaryMenu"> 
  <ul>
    <a [#if currentStage == "activityDescription"] class="currentSection" [/#if] href="[@s.url action='description' includeParams='get'] [/@s.url]">
      <li>[@s.text name="menu.secondary.planning.activity.description" /]</li>
    </a>
    <a [#if currentStage == "partners"] class="currentSection" [/#if] href="[@s.url action='partners' includeParams='get'] [/@s.url]">
      <li>[@s.text name="menu.secondary.planning.activity.partners" /]</li>
    </a>
    <a [#if currentStage == "budget"] class="currentSection" [/#if] href="[@s.url action='budget'  includeParams='get'][/@s.url]">
      <li>[@s.text name="menu.secondary.planning.activity.budget" /]</li>
    </a>
    <a [#if currentStage == "locations"] class="currentSection" [/#if] href="[@s.url action='locations'  includeParams='get'][/@s.url]">
      <li>[@s.text name="menu.secondary.planning.activity.locations" /]</li>
    </a>
    <a [#if currentStage == "impactPathways"] class="currentSection" [/#if] href="[@s.url action='impactPathways'  includeParams='get'][/@s.url]">
      <li>[@s.text name="menu.secondary.planning.activity.impactPathways" /]</li>
    </a> 
    <a [#if currentStage == "deliverables"] class="currentSection" [/#if] href="[@s.url action='deliverables'  includeParams='get'][/@s.url]">
      <li>[@s.text name="menu.secondary.planning.activity.deliverables" /]</li>
    </a> 
  </ul>
</nav>