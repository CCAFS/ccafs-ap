[#ftl] 
[#assign currCss= "class='currentSection'"]
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
    <div id="projectType-quote" class="aux-quote-cofunded" title="[@s.text name="planning.projects.type.ccafs_coFunded" /] project">
      <p><b>[@s.text name="planning.projects.type.ccafs_cofunded" /]</b></p>
    </div>
  [/#if]
</h1></center>
  [#--]<h3> <a class="goBack"  href="[@s.url namespace='/planning' action='projectsList'][/@s.url]"> [@s.text name="planning.project" /] Menu</a></h3>--]
  <ul> 
    <li [#if currentStage == "description"]${currCss}[/#if]>
      <p>[@s.text name="menu.secondary.planning.project.description" /]</p>
      <ul>
        <li [#if currentSubStage == "description"] class="currentSection" [/#if]>
          <a href="[@s.url action='description'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">
            [@s.text name="menu.planning.submenu.projectDescription" /]
          </a>
        </li>
        <li [#if currentSubStage == "partners" ] class="currentSection" [/#if]>
          <a href="[@s.url action='partnerLead'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">
            [@s.text name="menu.planning.submenu.projectPartners" /]
          </a>
        </li>
        <li [#if currentSubStage == "locations" ] class="currentSection" [/#if]>
          <a href="[@s.url action='locations'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">
            [@s.text name="menu.planning.submenu.projectLocations" /]
          </a>
        </li> 
      </ul>
    </li>
    <li [#if currentStage == "outcomes"]${currCss}[/#if]>
      <p>[@s.text name="menu.secondary.planning.project.outcome" /]</p>
      <ul>
        <li [#if currentSubStage == "outcomes"] class="currentSection" [/#if]>
          <a href="[@s.url action='outcomes'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectOutcomes" /]</a>
        </li>
        <li [#if currentSubStage == "ccafsOutcomes" ] class="currentSection" [/#if]>
          <a href="[@s.url action='ccafsOutcomes'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.ccafsOutcomes" /]</a>
        </li> 
        <li [#if currentSubStage == "otherContributions" ] class="currentSection" [/#if]>
          <a href="[@s.url action='otherContributions'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.otherContributions" /]</a>
        </li> 
      </ul>
    </li>
    <li [#if currentStage == "outputs"]${currCss}[/#if]>
      <p>[@s.text name="menu.secondary.planning.project.outputs" /]</p>
      <ul>
        <li [#if currentSubStage == "overviewByMogs"] class="currentSection" [/#if]>
          <a href="[@s.url action='outputs'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectOutputs.overviewByMogs" /]</a>
        </li>
        <li [#if currentSubStage == "deliverables" ] class="currentSection" [/#if]>
          <a href="[@s.url action='deliverablesList'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectOutputs.deliverables" /]</a>
        </li> 
      </ul>
    </li>
    <li [#if currentStage == "activities"]${currCss}[/#if]>
      <p>[@s.text name="menu.secondary.planning.project.activities" /]</p>
      <ul>
        <li [#if currentSubStage == "activities" ] class="currentSection" [/#if]>
          <a href="[@s.url action='activities'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectActivities.activitiesList" /]</a>
        </li> 
      </ul>
    </li>
    <li [#if currentStage == "budget"]${currCss}[/#if]>
      <p>[@s.text name="menu.secondary.planning.project.budget" /]</p>
      <ul>
        <li [#if currentSubStage == "budgetByPartner"] class="currentSection" [/#if]>
          <a href="[@s.url action='budget'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectBudget.budgetByPartner" /]</a>
        </li>
        <li [#if currentSubStage == "budgetByMog" ] class="currentSection" [/#if]>
          <a href="[@s.url action='budgetByMog'][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectBudget.budgetByMog" /]</a>
        </li> 
      </ul>
    </li>
  </ul>
</nav>
