[#ftl]
[#assign title = "Outcome Synthesis" /]
[#assign globalLibs = ["jquery", "dataTable", "noty","autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/synthesis/outcomeSynthesis.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable-flat.css"] /]
[#assign currentSection = reportingCycle?string('reporting','planning') /]
[#assign currentCycleSection = "outcomeSynthesis" /]
[#assign currentStage = "outcomeSynthesis" /]
[#assign currentSubStage = "outcomeSynthesis" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"outcomeSynthesis", "nameSpace":"${currentSection}", "action":"outcomeSynthesis"}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
[#import "/WEB-INF/global/macros/utils.ftl" as utilities/]

<section class="content">
  [#-- Help Message --]
  <div class="helpMessage"><img src="${baseUrl}/images/global/icon-help.png" /><p>[@s.text name="reporting.synthesis.outcomeSynthesis.help" /]</p></div>
  
  <article class="fullBlock clearfix" id="">
    [@s.form action="outcomeSynthesis" method="POST" enctype="multipart/form-data" cssClass="pure-form"]
    
    [#-- Program (Regions and Flagships) --]
    <ul id="liaisonInstitutions" class="horizontalSubMenu">
      [#list liaisonInstitutions as institution]
        <li class="[#if institution.id == liaisonInstitutionID]active[/#if]"><a href="[@s.url][@s.param name ="liaisonInstitutionID"]${institution.id}[/@s.param][@s.param name='indicatorTypeID']${(indicatorTypeID)!}[/@s.param][@s.param name ="edit"]true[/@s.param][/@s.url]">${institution.name}</a></li>
      [/#list]
    </ul>
    
    <div class="fullContent">
      [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantProjectPlanningAccessInterceptor--]
      [#if submission?has_content]
        <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
      [#elseif !canEdit ]
        <p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param]${title}[/@s.param][/@s.text]</p>
      [/#if]
      [#-- Title --]
      <h1 class="contentTitle">[@s.text name="reporting.synthesis.outcomeSynthesis.title" ][@s.param]${(currentLiaisonInstitution.name)!}[/@s.param][/@s.text]</h1>
      
      [#-- Outcomes 2019 --]
      <div id="outcomeSynthesisBlock" class="">
        [#list midOutcomes as midOutcome]
        <div class="borderBox">
          <div class="fullPartBlock">
            <h6 class="title">${midOutcome.getComposedId()}</h6>
            <p>${midOutcome.description}</p>
          </div>
          <div class="fullPartBlock">
            <h6>[@s.text name="reporting.synthesis.outcomeSynthesis.indicators" /]:</h6> 
            [#list midOutcome.indicators as indicator]
              [#assign flagshipIndicator = (indicator.parent)!indicator /]
               [#assign index = action.getIndex(flagshipIndicator.id,midOutcome.id,program.id) /]
           
              <div class="simpleBox">
                <div class="fullPartBlock">
                  <p>${flagshipIndicator.description}</p>
                </div>
                [#-- Achieved target in current reporting period --]
                <div class="fullPartBlock">
                  <div class="thirdPartBlock">[@customForm.input name="synthesis[${index}].achieved" type="text" i18nkey="reporting.synthesis.outcomeSynthesis.targetAchieved" className="isNumeric" help="form.message.numericValue" required=canEdit editable=editable /]</div>
                </div>
                [#-- Synthesis of annual progress towards this indicator --]
                <div class="fullPartBlock">
                  [@customForm.textArea name="synthesis[${index}].synthesisAnual" i18nkey="reporting.synthesis.outcomeSynthesis.progressIndicator" className="progressIndicator limitWords-200" required=canEdit editable=editable /]
                </div>
                [#-- Synthesis of annual progress gender and social inclusion contribution towards this indicator --]
                <div class="fullPartBlock">
                  [@customForm.textArea name="synthesis[${index}].synthesisGender" i18nkey="reporting.synthesis.outcomeSynthesis.genderProgressIndicator" className="genderProgressIndicator limitWords-200" required=canEdit editable=editable /]
                </div>
                [#-- Explain any discrepancy  --]
                [#if midOutcome.regionalProgramType]
                <div class="fullPartBlock">
                  [@customForm.textArea name="synthesis[${index}].discrepancy" i18nkey="reporting.synthesis.outcomeSynthesis.discrepancy" className="discrepancy limitWords-100" editable=editable /]
                </div>
                [/#if] 
                [#-- Project Contributions --]
                <h6>[@s.text name="reporting.synthesis.outcomeSynthesis.projectContributions" /]:</h6> 
                [#if (action.getProjectIndicators(currentReportingYear, flagshipIndicator.id))?has_content]
                <div class="fullPartBlock projectContributions-block viewMore-block">
                  <table class="projectContributions">
                    <thead>
                      <tr class="header">
                        <th class="col-projectId" rowspan="2">Project ID</th>
                        <th colspan="2">Target of current reporting period</th>
                        <th rowspan="2">Narrative for your achieved targets, including evidence</th>
                      </tr>
                    	<tr class="subHeader"> 
                    		<th class="col-expected">Expected</th>
                    		<th class="col-achieved">Achieved</th> 
                    	</tr>
                  	</thead>
                  	<tbody>
                    [#list action.getProjectIndicators(currentReportingYear, flagshipIndicator.id) as projectIndicator]
                      <tr>
                      	<td class="center"><a href="[@s.url action="ccafsOutcomes" namespace="/reporting/projects"][@s.param name='projectID']${(projectIndicator.projectId)!}[/@s.param][@s.param name='edit']true[/@s.param][/@s.url]">P${(projectIndicator.projectId)!}</a></td>
                      	<td class="center" title="${(projectIndicator.target)!''}" >[@utilities.wordCutter string=(projectIndicator.target)!'Prefilled when available' maxPos=25 /]</td>
                      	<td class="center" title="${(projectIndicator.archived)!''}" >[@utilities.wordCutter string=(projectIndicator.archived)!'Prefilled when available' maxPos=25 /]</td>
                        <td class="">${(projectIndicator.narrativeTargets)!'Prefilled when available'} </td>
                      </tr>
                    [/#list]
                  	</tbody>
                  </table>
                  <div class="viewMore"></div>
                </div>
                [#else]
                  <p>There is not project contributing to this indicator</p>
                [/#if]
                
                [#-- Regions/Global contributions --]
                [#if midOutcome.flagshipProgramType]
                <h6>[@s.text name="reporting.synthesis.outcomeSynthesis.regionalContributions" /]:</h6> 
                <div class="fullPartBlock">
                  {Table regionalContributions to this indicator Here}
                </div>
                [/#if]
                
              </div>
            [/#list]
          </div>
        </div>
        [/#list]
      </div>
      
      [#-- Button save and Log history --]
      [#if editable]
        <input type="hidden" name="liaisonInstitutionID" value="${liaisonInstitutionID}"  />
        <div class="" >
          <div class="buttons">
            [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
            [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
          </div>
        </div>
      [#else]
        [#-- Display Log History --]
        [#if history??][@log.logList list=history /][/#if] 
      [/#if]
    </div>
    [/@s.form] 
  </article>
  
</section> 


[#include "/WEB-INF/global/pages/footer.ftl"]
