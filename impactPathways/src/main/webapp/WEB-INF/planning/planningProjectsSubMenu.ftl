[#ftl] 
[#assign currCss= "class='currentSection'"]
<nav id="secondaryMenu"> 
  <h3> 
    <a class="goBack" href="[@s.url namespace='/planning' action='projectsList'][/@s.url]"> [@s.text name="planning.project" /] [#if project??]${project.composedId}[/#if] </a>
  </h3> 
  <ul> 
    <li [#if currentStage == "description"]${currCss}[/#if]>
      <a href="[@s.url action='description' includeParams='get'][#if project??][@s.param name='projectID']${project.id}[/@s.param][/#if][/@s.url]">
        [@s.text name="menu.secondary.planning.project.description" /]
      </a>
      <ul>
        <li [#if currentSubStage == "partners" ] class="currentSection" [/#if]>
          <a href="[@s.url action='partnerLead' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">
            [@s.text name="menu.planning.submenu.projectPartners" /]
          </a>
        </li>
        <li [#if currentSubStage == "locations" ] class="currentSection" [/#if]>
          <a href="[@s.url action='locations' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">
            [@s.text name="menu.planning.submenu.projectLocations" /]
          </a>
        </li> 
      </ul>
      <hr />
    </li>
    <li [#if currentStage == "outcomes"]${currCss}[/#if]>
      <a href="[@s.url action='outcomes'  includeParams='get'][#if project??][@s.param name='projectID']${project.id}[/@s.param][/#if][/@s.url]">
        [@s.text name="menu.secondary.planning.project.outcome" /]
      </a>
      <ul>
        <li [#if currentSubStage == "ccafsOutcomes" ] class="currentSection" [/#if]>
          <a href="[@s.url action='ccafsOutcomes' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.ccafsOutcomes" /]</a>
        </li> 
        <li [#if currentSubStage == "otherContributions" ] class="currentSection" [/#if]>
          <a href="[@s.url action='otherContributions' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.otherContributions" /]</a>
        </li> 
      </ul>
      <hr />
    </li>
    <li [#if currentStage == "outputs"]${currCss}[/#if]>
      <a href="[@s.url action='outputs'  includeParams='get'][#if project??][@s.param name='projectID']${project.id}[/@s.param][/#if][/@s.url]">
        [@s.text name="menu.secondary.planning.project.outputs" /]
      </a>
      <ul>
        <li [#if currentSubStage == "deliverables" ] class="currentSection" [/#if]>
          <a href="[@s.url action='deliverablesList' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectOutputs.deliverables" /]</a>
        </li> 
      </ul>
      <hr />
    </li>
    <li [#if currentStage == "activities"]${currCss}[/#if]>
      <a href="[@s.url action='activities' includeParams='get'][#if project??][@s.param name='projectID']${project.id}[/@s.param][/#if][/@s.url ]">
        [@s.text name="menu.secondary.planning.project.activities" /]
      </a>
      <hr />
    </li>
    <li [#if currentStage == "budget"]${currCss}[/#if]>
      <a href="[@s.url action='budget'  includeParams='get'][#if project??][@s.param name='projectID']${project.id}[/@s.param][/#if][/@s.url]">
        [@s.text name="menu.secondary.planning.project.budget" /]
      </a>
      <ul>
        <li [#if currentSubStage == "budgetByMog" ] class="currentSection" [/#if]>
          <a href="[@s.url action='budgetByMog' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectBudget.budgetByMog" /]</a>
        </li> 
      </ul>
    </li>
  </ul>
</nav>
