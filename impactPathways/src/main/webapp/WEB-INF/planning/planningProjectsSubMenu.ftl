[#ftl]
<nav id="secondaryMenu"> 
  <h3> 
      <a class="goBack" href="[@s.url namespace='/planning' action='projectsList'][/@s.url]"> [@s.text name="planning.project" /] menu </a>
  </h3>
  
  <ul> 
    <a [#if currentStage == "description"] class="currentSection" [/#if] href="[@s.url action='description' includeParams='get'][/@s.url]">
      <li>[@s.text name="menu.secondary.planning.project.description" /]</li>
    </a> 
    <a [#if currentStage == "outcomes"] class="currentSection" [/#if] href="[@s.url action='outcomes'  includeParams='get'][/@s.url]">
      <li>[@s.text name="menu.secondary.planning.project.outcome" /]</li>
    </a>
    <a [#if currentStage == "outputs"] class="currentSection" [/#if] href="[@s.url action='outputs'  includeParams='get'][/@s.url]">
      <li>[@s.text name="menu.secondary.planning.project.outputs" /]</li>
    </a>
    <a [#if currentStage == "activities"] class="currentSection" [/#if] href="[@s.url action='activities' includeParams='get'][/@s.url]">
      <li>[@s.text name="menu.secondary.planning.project.activities" /]</li>
    </a>
    <a [#if currentStage == "budget"] class="currentSection" [/#if] href="[@s.url action='budget'  includeParams='get'][/@s.url]">
      <li>[@s.text name="menu.secondary.planning.project.budget" /]</li>
    </a>
  </ul>
</nav>
