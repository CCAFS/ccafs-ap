[#ftl]
<nav id="secondaryMenu"> 
  <h3>[@s.text name="planning.activity" /] menu </h3>
  <ul>
    <a [#if currentStage == "activityDescription"] class="currentSection" [/#if] href="[@s.url action='activityDescription' includeParams='get'] [/@s.url]">
      <li>[@s.text name="menu.secondary.planning.activity.activityDescription" /]</li>
    </a>
    <a [#if currentStage == "activityPartners"] class="currentSection" [/#if] href="[@s.url action='activityPartners' includeParams='get'] [/@s.url]">
      <li>[@s.text name="menu.secondary.planning.activity.activityPartners" /]</li>
    </a>
    <a [#if currentStage == "activityBudget"] class="currentSection" [/#if] href="[@s.url action='activityBudget'  includeParams='get'][/@s.url]">
      <li>[@s.text name="menu.secondary.planning.activity.activityBudget" /]</li>
    </a>
    <a [#if currentStage == "activityLocations"] class="currentSection" [/#if] href="[@s.url action='activityLocations'  includeParams='get'][/@s.url]">
      <li>[@s.text name="menu.secondary.planning.activity.activityLocations" /]</li>
    </a>
    <a [#if currentStage == "activityDeliverables"] class="currentSection" [/#if] href="[@s.url action='activityDeliverables'  includeParams='get'][/@s.url]">
      <li>[@s.text name="menu.secondary.planning.activity.activityDeliverables" /]</li>
    </a> 
  </ul>
</nav>