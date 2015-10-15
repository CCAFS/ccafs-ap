[#ftl]
[#-- Submit controller --]
<script src="${baseUrl}/js/planning/projectSubmit.js"></script>

[#assign currCss= "currentSection"]
[#assign projectId=(project.id)!""]
[#assign projectStage = (currentSubStage)!"" /] 
[#assign submission = (project.isSubmitted(currentPlanningYear, 'Planning'))!/]

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
        [@menu actionName="otherContributions" stageName="otherContributions" textName="menu.planning.submenu.otherContributions"/]
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
  
  [#if !submission?has_content && complete && !securityContext.canSubmitProject(project.id)]
    <p style="display:none">The project can be submitted now by Management liaison or Contact point.</p>
  [/#if]
  
  [#-- Check button --] 
  [#if canEdit && !complete && !submission?has_content]
    <p class="projectValidateButton-message center">Check for missing fields. <br /></p>
    <div id="validateProject-${projectId}" class="projectValidateButton ${(project.type)!''}">[@s.text name="form.buttons.check" /]</div>
    <div id="progressbar-${projectId}" class="progressbar" style="display:none"></div>
  [/#if]
  
  [#-- Submit button --]
  [#if securityContext.canSubmitProject(project.id) && !submission?has_content && complete]
    <a id="submitProject-${projectId}" class="projectSubmitButton" href="[@s.url action="submit"][@s.param name='projectID']${projectId}[/@s.param][/@s.url]" style="display:none">[@s.text name="form.buttons.submit" /]</a>
  [/#if]
  
</nav>



[#-- Menu element --]
[#macro menu actionName stageName textName disabled=false]
  <li id="menu-${actionName}" class="[#if projectStage == stageName]${currCss} [/#if]${sectionCompleted(actionName)?string('submitted','toSubmit')}">
    [#if disabled]
      <a class="disabled" href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]">[@s.text name=textName /]</a>
    [#else]
      [#if canEdit && !sectionCompleted(actionName)]
        <a href="[@s.url action=actionName][@s.param name='projectID']${projectId}[/@s.param][@s.param name='edit']true[/@s.param][/@s.url]">[@s.text name=textName /]</a> 
      [#else]
        <a href="[@s.url action=actionName][@s.param name='projectID']${projectId}[/@s.param][/@s.url]">[@s.text name=textName /]</a> 
      [/#if]
    [/#if]
  </li> 
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
