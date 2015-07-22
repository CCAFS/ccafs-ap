[#ftl] 
[#assign currCss= "class='currentSection'"]
<nav id="secondaryMenu"> 
  <h3> 
    <a class="goBack" href="[@s.url namespace='/planning' action='projectsList'][/@s.url]"> [@s.text name="planning.project" /] [#if project??]${project.composedId}[/#if] </a>
  </h3> 
  <ul> 
    <li><a [#if currentStage == "description"]${currCss}[/#if] href="[@s.url action='description' includeParams='get'][#if project??][@s.param name='projectID']${project.id}[/@s.param][/#if][/@s.url]">
      [@s.text name="menu.secondary.planning.project.description" /]
    </a></li>
    <li>
    <a [#if currentStage == "outcomes"]${currCss}[/#if] href="[@s.url action='outcomes'  includeParams='get'][#if project??][@s.param name='projectID']${project.id}[/@s.param][/#if][/@s.url]">
      [@s.text name="menu.secondary.planning.project.outcome" /]
    </a></li>
    <li><a [#if currentStage == "outputs"]${currCss}[/#if] href="[@s.url action='outputs'  includeParams='get'][#if project??][@s.param name='projectID']${project.id}[/@s.param][/#if][/@s.url]">
      [@s.text name="menu.secondary.planning.project.outputs" /]
    </a></li>
    <li>
    <a class="disabled" href="#[#-- @s.url action='activities' includeParams='get'][#if project??][@s.param name='projectID']${project.id}[/@s.param][/#if][/@s.url --]">
      [@s.text name="menu.secondary.planning.project.activities" /]
    </a></li>
    <li><a [#if currentStage == "budget"]${currCss}[/#if] href="[@s.url action='budget'  includeParams='get'][#if project??][@s.param name='projectID']${project.id}[/@s.param][/#if][/@s.url]">
      [@s.text name="menu.secondary.planning.project.budget" /]
    </a></li>
  </ul>
</nav>
