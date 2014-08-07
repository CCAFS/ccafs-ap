[#ftl]
<nav id="stageMenu" class="clearfix">
  <ul>
    <li [#if currentStage == "description"] class="currentSection" [/#if]><a href="
        [@s.url action='description' includeParams='get'][@s.param name='${projectRequest}']${project.id?c}[/@s.param][/@s.url]
      ">[@s.text name="menu.preplanning.submenu.description" /]</a>
    </li>
    <li [#if currentStage == "partners"] class="currentSection" [/#if]><a href="
        [@s.url action='partners' includeParams='get'][@s.param name='${projectRequest}']${project.id?c}[/@s.param][/@s.url]
      ">[@s.text name="menu.preplanning.submenu.partners" /] </a>
    </li>
    <li [#if currentStage == "budget"] class="currentSection" [/#if]><a href="
        [@s.url action='budget' includeParams='get'][@s.param name='${projectRequest}']${project.id?c}[/@s.param][/@s.url]
      ">[@s.text name="menu.preplanning.submenu.budget" /]</a>
    </li>
  </ul>
</nav>