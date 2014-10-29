[#ftl]
<nav id="secondaryMenu"> 
  <h3>[@s.text name="planning.project" /] menu </h3>
  <ul>
    <a [#if currentStage == "description"] class="currentSection" [/#if] href="[@s.url action='description' includeParams='get'][/@s.url]">
      <li>[@s.text name="menu.secondary.planning.project.description" /]</li>
    </a>
    <a [#if currentStage == "partners"] class="currentSection" [/#if] href="[@s.url action='partners' includeParams='get'] [/@s.url]">
      <li>[@s.text name="menu.secondary.planning.project.partners" /]</li>
    </a>
    <a [#if currentStage == "budget"] class="currentSection" [/#if] href="[@s.url action='budget'  includeParams='get'][/@s.url]">
      <li>[@s.text name="menu.secondary.planning.project.budget" /]</li>
    </a>
    <a [#if currentStage == "projectOutcomes"] class="currentSection" [/#if] href="[@s.url action='projectOutcomes'  includeParams='get'][/@s.url]">
      <li>[@s.text name="menu.secondary.planning.project.outcome" /]</li>
    </a>
    <a [#if currentStage == "impactPathway"] class="currentSection" [/#if] href="[@s.url action='impactPathway'  includeParams='get'][/@s.url]">
      <li>[@s.text name="menu.secondary.planning.project.impactPathway" /]</li>
    </a>
    <a [#if currentStage == "activities"] class="currentSection" [/#if] href="[@s.url action='activities' includeParams='get'][/@s.url]">
      <li>[@s.text name="menu.secondary.planning.project.activities" /]</li>
    </a> 
  </ul>
</nav>
