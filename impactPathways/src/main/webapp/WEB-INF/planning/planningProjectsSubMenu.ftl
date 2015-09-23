[#ftl] 
[#assign currCss= "currentSection"]
[#assign projectId=(project.id)!""]
<nav id="secondaryMenu" class="${(project.type)!''}">
<h1><center> 
  [#if project.coreProject]
    <div id="projectType-quote" class="aux-quote-core" title="[@s.text name="planning.projects.type.ccafs_core" /] project">
      <p><b>[@s.text name="planning.projects.type.ccafs_core" /]</b></p>
    </div>
  [/#if]
  [#if project.bilateralProject]
    <div id="projectType-quote" class="aux-quote-bilateral" title="[@s.text name="planning.projects.type.bilateral" /] project">
      <p><b>[@s.text name="planning.projects.type.bilateral" /]</b></p>
    </div>
  [/#if]
  [#if project.coFundedProject]
    <div id="projectType-quote" class="aux-quote-cofunded" title="[@s.text name="planning.projects.type.ccafs_cofunded" /] project">
      <p><b>[@s.text name="planning.projects.type.ccafs_cofunded" /]</b></p>
    </div>
  [/#if]
</h1></center>
  [#--]<h3> <a class="goBack"  href="[@s.url namespace='/planning' action='projectsList'][/@s.url]"> [@s.text name="planning.project" /] Menu</a></h3>--]
  <ul> 
    <li class="[#if currentStage == "description"]${currCss}[/#if]">
      <p>[@s.text name="menu.planning.submenu.projectDescription" /]</p>
      <ul>
        <li class="[#if currentSubStage == "description"]${currCss}[/#if]">
          <a href="[@s.url action='description'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">
            [@s.text name="menu.secondary.planning.project.description" /]
          </a>
        </li>
        <li class="[#if currentSubStage == "partners" ]${currCss}[/#if]">
          <a href="[@s.url action='partners'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">
            [@s.text name="menu.planning.submenu.projectPartners" /]
          </a>
        </li>
        <li class="[#if currentSubStage == "locations" ]${currCss}[/#if]">
          <a href="[@s.url action='locations'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">
            [@s.text name="menu.planning.submenu.projectLocations" /]
          </a>
        </li> 
      </ul>
    </li>
    <li class="[#if currentStage == "outcomes"]${currCss}[/#if]">
      <p>[@s.text name="menu.secondary.planning.project.outcome" /]</p>
      <ul>
        <li class="[#if currentSubStage == "outcomes"]${currCss}[/#if]">
          <a href="[@s.url action='outcomes'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectOutcomes" /]</a>
        </li>
        <li class="[#if currentSubStage == "ccafsOutcomes" ]${currCss}[/#if]">
          <a href="[@s.url action='ccafsOutcomes'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.ccafsOutcomes" /]</a>
        </li> 
        <li class="[#if currentSubStage == "otherContributions" ]${currCss}[/#if]">
          <a href="[@s.url action='otherContributions'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.otherContributions" /]</a>
        </li> 
      </ul>
    </li>
    <li class="[#if currentStage == "outputs"]${currCss}[/#if]">
      <p>[@s.text name="menu.secondary.planning.project.outputs" /]</p>
      <ul>
        <li class="[#if currentSubStage == "overviewByMogs"]${currCss}[/#if]">
          <a href="[@s.url action='outputs'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectOutputs.overviewByMogs" /]</a>
        </li>
        <li class="[#if currentSubStage == "deliverables" ]${currCss}[/#if]">
          <a href="[@s.url action='deliverablesList'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectOutputs.deliverables" /]</a>
        </li> 
      </ul>
    </li>
    <li class="[#if currentStage == "activities"]${currCss}[/#if]">
      <p>[@s.text name="menu.secondary.planning.project.activities" /]</p>
      <ul>
        <li class="[#if currentSubStage == "activities" ]${currCss}[/#if]">
          <a href="[@s.url action='activities'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectActivities.activitiesList" /]</a>
        </li> 
      </ul>
    </li>
    <li class="[#if currentStage == "budget"]${currCss}[/#if]">
      <p>[@s.text name="menu.secondary.planning.project.budget" /]</p>
      <ul>
        <li class="[#if currentSubStage == "budgetByPartner"]${currCss}[/#if]">
          <a href="[@s.url action='budget'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectBudget.budgetByPartner" /]</a>
        </li>
        <li class="[#if currentSubStage == "budgetByMog" ]${currCss}[/#if]">
          <a href="[@s.url action='budgetByMog'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectBudget.budgetByMog" /]</a>
        </li> 
      </ul>
    </li>
  </ul>
</nav>
