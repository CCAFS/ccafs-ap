[#ftl]
[#-- validateProjectSection.do --]
<input type="hidden" id="currentCycle" value="${cycleName}" />
[#-- Submit controller --]
<script src="${baseUrl}/js/projects/projectSubmit.js"></script>

[#assign currCss= "currentSection"]
[#assign projectId=(project.id)!""]
[#assign projectStage = (currentSubStage)!"" /] 
[#assign projectSectionStatus= (action.getProjectSectionStatus(actionName))!{} /]
[#assign currentCycleYear= (reportingCycle?string(currentReportingYear,currentPlanningYear))?number /]
[#assign submission = (project.isSubmitted(currentCycleYear, cycleName))! /]
[#assign canSubmit = action.hasProjectPermission("submitProject", project.id, "manage") /]

<nav id="secondaryMenu" class="projectMenu ${(project.type)!''} ${(projectSectionStatus.missingFieldsWithPrefix?has_content)?string("hasMissingFields","")}">
<h1><center> 
  [#if project.coreProject]
    <div id="projectType-quote" class="aux-quote-core" title="[@s.text name="planning.projects.type.ccafs_core" /] project">
      <p><b>[@s.text name="planning.projects.type.ccafs_core" /]</b></p>
    </div>
  [/#if]
  [#if project.coFundedProject]
    <div id="projectType-quote" class="aux-quote-cofunded" title="[@s.text name="planning.projects.type.ccafs_cofunded" /] project">
      <p><b>[@s.text name="planning.projects.type.ccafs_cofunded" /]</b></p>
    </div>
  [/#if]
  [#if project.bilateralStandAlone]
    <div id="projectType-quote" class="aux-quote-bilateral" title="[@s.text name="planning.projects.type.bilateral" /] project">
      <p><b>[@s.text name="planning.projects.type.bilateral" /]</b></p>
    </div>
  [/#if]
  [#if project.bilateralProject && project.cofinancing]
    <div id="projectType-quote" class="aux-quote-bilateral" title="[@s.text name="planning.projects.type.bilateralCoFinancing" /] project">
      <p><b>[@s.text name="planning.projects.type.bilateralCoFinancing" /]</b></p>
    </div>
  [/#if]
</h1></center>
  [#--]<h3> <a class="goBack"  href="[@s.url namespace='/planning' action='projectsList'][/@s.url]"> [@s.text name="planning.project" /] Menu</a></h3>--]
  <ul> 
    <li class="[#if currentStage == "description"]${currCss}[/#if]">
      <p>[@s.text name="menu.planning.submenu.projectDescription" /]</p>
      <ul>
        [@menu actionName="description" stageName="description" textName="menu.secondary.planning.project.description" /] 
        [@menu actionName="partners" stageName="partners" textName="menu.planning.submenu.projectPartners"  /] 
        [@menu actionName="locations" stageName="locations" textName="menu.planning.submenu.projectLocations" /] 
      </ul>
    </li>
    <li class="[#if currentStage == "outcomes"]${currCss}[/#if]">
      <p>[@s.text name="menu.secondary.planning.project.outcome" /]</p>
      <ul>
        [@menu actionName="outcomes" stageName="outcomes" textName="menu.planning.submenu.projectOutcomes" /]
        [@menu actionName="ccafsOutcomes" stageName="ccafsOutcomes" textName="menu.planning.submenu.ccafsOutcomes" /]
        [@menu actionName="otherContributions" stageName="otherContributions" textName="menu.planning.submenu.otherContributions" /]
        [@menu actionName="caseStudies" stageName="caseStudies" textName="menu.reporting.submenu.caseStudies" subText="Previously called Outcome Stories" active=reportingCycle /]
      </ul>
    </li>
    <li class="[#if currentStage == "outputs"]${currCss}[/#if]">
      <p>[@s.text name="menu.secondary.planning.project.outputs" /]</p>
      <ul>
        [@menu actionName="outputs" stageName="overviewByMogs" textName="menu.planning.submenu.projectOutputs.overviewByMogs" /]
        [@menu actionName="deliverablesList" stageName="deliverables" textName="menu.planning.submenu.projectOutputs.deliverables" /]
        [@menu actionName="nextUsers" stageName="nextUsers" textName="menu.reporting.submenu.nextUsers" active=reportingCycle/]
        [@menu actionName="highlights" stageName="highlights" textName="menu.reporting.submenu.highlights" subText="Previously called Case studies" active=reportingCycle/]
      </ul>
    </li>
    <li class="[#if currentStage == "activities"]${currCss}[/#if]">
      <p>[@s.text name="menu.secondary.planning.project.activities" /]</p>
      <ul>
        [@menu actionName="activities" stageName="activities" textName="menu.planning.submenu.projectActivities.activitiesList" /]
      </ul>
    </li>
    <li class="[#if currentStage == "budget"]${currCss}[/#if]">
      <p>[@s.text name="menu.secondary.planning.project.budget" /]</p>
      <ul>
        [@menu actionName="leverages" stageName="leverages" textName="menu.reporting.submenu.projectBudget.leverages" active=reportingCycle /]
        [@menu actionName="budget" stageName="budgetByPartner" textName="menu.planning.submenu.projectBudget.budgetByPartner" active=!reportingCycle /]
        [@menu actionName="budgetByMog" stageName="budgetByMog" textName="menu.planning.submenu.projectBudget.budgetByMog" active=!reportingCycle /]
      </ul>
    </li>
  </ul>
  <br />
  
  [#if !submission?has_content && complete && !canSubmit]
    <p style="display:block">The project can be submitted now by Management liaison, Contact point or Project Leader.</p>
  [/#if]
  
  [#-- Check button --]
  [#if canEdit && !complete && !submission?has_content]
    <p class="projectValidateButton-message center">Check for missing fields.<br /></p>
    <div id="validateProject-${projectId}" class="projectValidateButton ${(project.type)!''}">[@s.text name="form.buttons.check" /]</div>
    <div id="progressbar-${projectId}" class="progressbar" style="display:none"></div>
  [/#if]
  
  [#-- Submit button --]
  [#if canEdit]
  [#assign showSubmit=(canSubmit && !submission?has_content && complete)]
    <a id="submitProject-${projectId}" class="projectSubmitButton" style="display:${showSubmit?string('block','none')}" href="[@s.url action="submit"][@s.param name='projectID']${projectId}[/@s.param][/@s.url]" >
      [@s.text name="form.buttons.submit" /]
    </a>
  [/#if]
  
  [#-- Report button --]
  [#if !reportingCycle && submission?has_content && !(config.reportingClosed) ]
    <a id="reportProject-${projectId}" class="projectReportButton" href="[@s.url action="description" namespace="/reporting/projects"][@s.param name='projectID']${projectId}[/@s.param][/@s.url]" >
      [@s.text name="form.buttons.reportProject" /]
    </a>
  [/#if]
  
</nav>


[#-- Menu element --]
[#macro menu actionName stageName textName disabled=false active=true subText=""]
  [#if active]
    <li id="menu-${actionName}" class="[#if projectStage == stageName]${currCss}[/#if] [#if canEdit]${sectionCompleted(actionName)?string('submitted','toSubmit')}[/#if]">
      [#if disabled]
        <a class="disabled" href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]">[@s.text name=textName /]</a>
      [#else]
        [#if action.hasProjectPermission("update" , project.id, actionName) ]
          <a href="[@s.url action=actionName][@s.param name='projectID']${projectId}[/@s.param][@s.param name='edit']true[/@s.param][/@s.url]" title="[#if subText != ""]${subText}[/#if]">
            [@s.text name=textName /] 
          </a> 
        [#else]
          <a href="[@s.url action=actionName][@s.param name='projectID']${projectId}[/@s.param][/@s.url]" title="[#if subText != ""]${subText}[/#if]">
            [@s.text name=textName /]
          </a> 
        [/#if]
      [/#if]
    </li>
  [/#if]
[/#macro]

[#-- Submitted CSS class for section status--]
[#function sectionCompleted actionName]
  [#assign status= (action.getProjectSectionStatus(actionName))!{} /]
  [#if status?has_content]
    [#if !(status.missingFieldsWithPrefix)?has_content]
      [#return true]
    [#else]
      [#return false]
    [/#if]
  [#else]
    [#return false]  
  [/#if]
[/#function]