[#ftl]
[#assign currCss= "class='currentSection'"]
<nav id="secondaryMenu">
<ul>
  <li><p>Pre-Planning</p>
  <ul>
    <li [#if currentPrePlanningSection == "impactPathways"]${currCss}[/#if]>
      <a href="[@s.url namespace="/pre-planning" action='outcomes'][/@s.url]">[@s.text name="menu.secondary.preplanning.impactPathways" /]</a>
    </li>
    <li [#if currentPrePlanningSection == "projects"]${currCss}[/#if]>
      <a href="[@s.url namespace="/pre-planning" action='projects'][/@s.url]">[@s.text name="menu.secondary.preplanning.projects" /]</a>
    </li>
  </ul>
  </li>
</ul>
  
</nav>