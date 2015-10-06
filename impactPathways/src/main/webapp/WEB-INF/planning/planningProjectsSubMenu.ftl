[#ftl]
[#-- Submit controller --]
<script src="${baseUrl}/js/planning/projectSubmit.js"></script>

[#assign currCss= "currentSection"]
[#assign projectId=(project.id)!""]
<nav id="secondaryMenu" class="projectMenu ${(project.type)!''}">
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
        [@menu actionName="description" stageName="description" textName="menu.secondary.planning.project.description"/] 
        [@menu actionName="partners" stageName="partners" textName="menu.planning.submenu.projectPartners"/] 
        [@menu actionName="locations" stageName="locations" textName="menu.planning.submenu.projectLocations"/] 
      </ul>
    </li>
    <li class="[#if currentStage == "outcomes"]${currCss}[/#if]">
      <p>[@s.text name="menu.secondary.planning.project.outcome" /]</p>
      <ul>
        [@menu actionName="outcomes" stageName="outcomes" textName="menu.planning.submenu.projectOutcomes"/]
        [@menu actionName="ccafsOutcomes" stageName="ccafsOutcomes" textName="menu.planning.submenu.ccafsOutcomes"/]
        [@menu actionName="otherContributions" stageName="otherContributions" textName="menu.planning.submenu.otherContributions" disabled=true/]
      </ul>
    </li>
    <li class="[#if currentStage == "outputs"]${currCss}[/#if]">
      <p>[@s.text name="menu.secondary.planning.project.outputs" /]</p>
      <ul>
        [@menu actionName="outputs" stageName="overviewByMogs" textName="menu.planning.submenu.projectOutputs.overviewByMogs"/]
        [@menu actionName="deliverablesList" stageName="deliverables" textName="menu.planning.submenu.projectOutputs.deliverables"/]
      </ul>
    </li>
    <li class="[#if currentStage == "activities"]${currCss}[/#if]">
      <p>[@s.text name="menu.secondary.planning.project.activities" /]</p>
      <ul>
        [@menu actionName="activities" stageName="activities" textName="menu.planning.submenu.projectActivities.activitiesList"/]
      </ul>
    </li>
    <li class="[#if currentStage == "budget"]${currCss}[/#if]">
      <p>[@s.text name="menu.secondary.planning.project.budget" /]</p>
      <ul> 
        [@menu actionName="budget" stageName="budgetByPartner" textName="menu.planning.submenu.projectBudget.budgetByPartner"/]
        [@menu actionName="budgetByMog" stageName="budgetByMog" textName="menu.planning.submenu.projectBudget.budgetByMog"/]
      </ul>
    </li>
  </ul>
  <br />
  [#if securityContext.canSubmitProject()]
    <div id="submitProject-${projectId}" class="projectSubmitButton">Submit</div>
    <div id="progressbar-${projectId}" class="progressbar" style="display:none"></div>
  [/#if]
  
</nav>

[#-- Menu element --]
[#macro menu actionName stageName textName disabled=false]
  <li id="menu-${actionName}" class="[#if currentSubStage == stageName]${currCss}[/#if] [@sectionStatus actionName=actionName/]">
    [#if disabled]
      <a class="disabled" href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]">[@s.text name=textName /]</a>
    [#else]
      <a href="[@s.url action=actionName][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name=textName /]</a> 
    [/#if]
  </li> 
[/#macro]

[#-- Submitted CSS class for section status--]
[#macro sectionStatus actionName]
[#compress]
    [#if action.getProjectSectionStatus(actionName)??]
      [#if !((action.getProjectSectionStatus(actionName)).missingFieldsWithPrefix)?has_content]
        submitted
      [#else]
        toSubmit 
      [/#if]
    [/#if]
[/#compress]
[/#macro]
