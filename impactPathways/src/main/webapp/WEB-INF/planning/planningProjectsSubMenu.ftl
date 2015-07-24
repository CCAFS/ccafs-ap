[#ftl] 
[#assign currCss= "class='currentSection'"]
[#assign projectId=(project.id)!""]
<nav id="secondaryMenu"> 
  <h3> <a class="goBack"  href="[@s.url namespace='/planning' action='projectsList'][/@s.url]"> [@s.text name="planning.project" /] Menu</a></h3>
  <ul> 
    <li [#if currentStage == "description"]${currCss}[/#if]>
      <p>[@s.text name="menu.secondary.planning.project.description" /]</p>
      <ul>
        <li [#if currentSubStage == "description"] class="currentSection" [/#if]>
          <a href="[@s.url action='description' includeParams='get'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">
            [@s.text name="menu.planning.submenu.projectDescription" /]
          </a>
        </li>
        <li [#if currentSubStage == "partners" ] class="currentSection" [/#if]>
          <a href="[@s.url action='partnerLead' includeParams='get'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">
            [@s.text name="menu.planning.submenu.projectPartners" /]
          </a>
        </li>
        <li [#if currentSubStage == "locations" ] class="currentSection" [/#if]>
          <a href="[@s.url action='locations' includeParams='get'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">
            [@s.text name="menu.planning.submenu.projectLocations" /]
          </a>
        </li> 
      </ul>
    </li>
    <li [#if currentStage == "outcomes"]${currCss}[/#if]>
      <p>[@s.text name="menu.secondary.planning.project.outcome" /]</p>
      <ul>
        <li [#if currentSubStage == "outcomes"] class="currentSection" [/#if]>
          <a href="[@s.url action='outcomes' includeParams='get'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectOutcomes" /]</a>
        </li>
        <li [#if currentSubStage == "ccafsOutcomes" ] class="currentSection" [/#if]>
          <a href="[@s.url action='ccafsOutcomes' includeParams='get'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.ccafsOutcomes" /]</a>
        </li> 
        <li [#if currentSubStage == "otherContributions" ] class="currentSection" [/#if]>
          <a href="[@s.url action='otherContributions' includeParams='get'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.otherContributions" /]</a>
        </li> 
      </ul>
    </li>
    <li [#if currentStage == "outputs"]${currCss}[/#if]>
      <p>[@s.text name="menu.secondary.planning.project.outputs" /]</p>
      <ul>
        <li [#if currentSubStage == "overviewByMogs"] class="currentSection" [/#if]>
          <a href="[@s.url action='outputs' includeParams='get'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectOutputs.overviewByMogs" /]</a>
        </li>
        <li [#if currentSubStage == "deliverables" ] class="currentSection" [/#if]>
          <a href="[@s.url action='deliverablesList' includeParams='get'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectOutputs.deliverables" /]</a>
        </li> 
      </ul>
    </li>
    <li [#if currentStage == "activities"]${currCss}[/#if]>
      <p>[@s.text name="menu.secondary.planning.project.activities" /]</p>
      <ul>
        <li [#if currentSubStage == "activities" ] class="currentSection" [/#if]>
          <a class="disabled" href="#[#--@s.url action='activities' includeParams='get'][@s.param name='projectID']${projectId}[/@s.param][/@s.url--]">[@s.text name="menu.planning.submenu.projectActivities.activitiesList" /]</a>
        </li> 
      </ul>
    </li>
    <li [#if currentStage == "budget"]${currCss}[/#if]>
      <p>[@s.text name="menu.secondary.planning.project.budget" /]</p>
      <ul>
        <li [#if currentSubStage == "budgetByPartner"] class="currentSection" [/#if]>
          <a href="[@s.url action='budget' includeParams='get'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectBudget.budgetByPartner" /]</a>
        </li>
        <li [#if currentSubStage == "budgetByMog" ] class="currentSection" [/#if]>
          <a href="[@s.url action='budgetByMog' includeParams='get'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectBudget.budgetByMog" /]</a>
        </li> 
      </ul>
    </li>
  </ul>
</nav>
