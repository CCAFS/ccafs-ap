[#ftl]
[#assign title = "Project Evaluation" /]
[#assign globalLibs = ["jquery", "noty", "star-rating"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/projects/projectEvaluation.js"] /]
[#assign currentSection = cycleName?lower_case /]
[#assign currentCycleSection = "projectsEvaluation" /]
[#assign currentStage = "evaluation" /]
[#assign currentSubStage = "evaluation" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projectsEvaluation", "nameSpace":"${currentSection}", "action":"projectsEvaluation"},
  {"label":"evaluation", "nameSpace":"${currentSection}/projects/evaluation", "action":"evaluation", "param":"projectID=${project.id}"}
] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  [#-- 
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" /><p>[@s.text name="${currentSection}.projectEvaluation.help" /]</p>
  </div>
  --]
  [#-- Privileges message --] 
  [#assign submission = (project.isSubmitted(currentReportingYear, 'Reporting'))!/]
  [#if !submission?has_content]
    <p class="readPrivileges">[@s.text name="project.evaluation.notReportedForevaluation" /]</p>
  [#elseif !canEdit ]
    <p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]</p>
  [/#if]
  
  
  <article class="fullBlock" id="mainInformation">
    [#include "/WEB-INF/projects/dataSheet.ftl" /]
    <h1 class="contentTitle">[@s.text name="planning.projectDescription.title" /]</h1>
    <div id="" class="borderBox">
      <div class="fullBlock">
        <div class="dottedBox">
          [#-- Project Title --]
          <div class="select"><h6>[@s.text name="planning.projectDescription.projectTitle" /]:</h6><p> ${(project.title)!}</p></div>
          <br />
          <p class="control-summaryBox">[View Summary]</p>
          [#-- Project Summary --]
          <div id="summaryBox" class="fullBlock" style="display:none">
            <br /><div class="select"><p>${(project.summary)!}</p></div>
          </div>
        </div>
      </div>
      <div class="fullBlock">
        <div class="thirdPartBlock">
          <div class="dottedBox">
            [#-- Start Date --]
            <div class="thirdPartBlock select"><h6>[@s.text name="preplanning.projectDescription.startDate" /]:</h6><p> ${(project.startDate?date)!}</p></div>
            [#-- End Date --]
            <div class=" thirdPartBlock select"><h6>[@s.text name="preplanning.projectDescription.endDate" /]:</h6><p> ${(project.endDate?date)!}</p></div>
          </div>
        </div>
        [#-- Management Liaison --]
        <div class="thirdPartBlock">
          <div class="dottedBox select"><h6>Management Liaison:</h6><p> ${(project.liaisonInstitution.name)!} -  ${project.owner.composedOwnerName?html}</p></div>  
        </div>
        [#-- Flagships / Regions --] 
        <div class="thirdPartBlock">
          <div class="dottedBox">
            <h6>[@s.text name="preplanning.projectDescription.flagships" /] / [@s.text name="preplanning.projectDescription.regions" /]:</h6>
            <div class="checkboxGroup clearfix">
              [#if project.flagships?has_content][#list project.flagships as element]<p class="focus">${element.acronym}</p>[/#list][/#if] 
              [#if project.regions?has_content][#list project.regions as element]<p class="focus">${element.acronym}</p>[/#list][/#if]
            </div>
          </div>
        </div> 
      </div>
      
      <div class="halfPartBlock">
        [#-- Project Leader --]
        <div class="dottedBox">
          <div class="select"><h6>Project Leader:</h6><p>${projectLeader.institution.acronym} - ${partnerPerson.getComposedName()?html}</p></div>
        </div>    
      </div>
        [#-- Project Budget --]
        <div class="halfPartBlock">
          <div class="dottedBox">
            <div class="halfPartBlock select">
              <h6>W1/W2 Budget:</h6>
              <p>[#assign totalProjectBudget]${((!project.bilateralProject)?string(totalCCAFSBudget!0, totalBilateralBudget!0))}[/#assign] US$ <span>${(totalProjectBudget?number)?string(",##0.00")}</span></p>
            </div>
            <div class="halfPartBlock select">
              <h6>W3/Bilateral Budget:</h6>
              <p>US$ <span>${(totalBilateralBudget!0)?string(",##0.00")}</span></p>
            </div>
          </div>
        </div>  
    </div> 
    
    [#-- Project Evaluations --]
    <h1 class="contentTitle">Project Evaluations</h1>  
    <div id="" class="borderBox">
      [#-- Button for edit this section --]
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [#elseif canEdit]
          <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>
      [/#if]
      <br />
      [#if project.evaluations?size >0]
        [#list project.evaluations as evaluation]
          [@projectEvaluation index=evaluation_index editable=(editable && action.checkEditByRole(evaluation)) canAccess=true /]
        [/#list]
      [#else]
        <p>[@s.text name="project.evaluation.isEmpty" /]</p>
      [/#if]
    </div> 
   
  </article>
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]

[#macro projectEvaluation index editable=false canAccess=true]
  [#assign customName = "project.evaluations[${index}]"/]
  [#assign element = (customName?eval)! /]
  
  [#assign canView = canAccess && (element.submited || editable) /]
  <div class="simpleBox evaluationBlock ${(canView)?string('visible','notVisible')}"> 
    [#-- Basic information --]
    <table class="evaluationTable">
      <tr>
        <td class="statusCol">[@s.text name="project.evaluation.status.${element.status}" /]</td>
        <td class="rolCol">[@s.text name="project.evaluation.evaluation" ][@s.param]${(action.getProgramEvaluation(element))!element.typeEvaluation}[/@s.param][/@s.text]</td>
        <td class="personCol">${action.getUserName(element.modifiedBy)?html}</td>
        <td class="totalScoreCol"><p class="totalScore">${canView?string(element.totalScore,'Pending')}</p></td>
        <td class="detailCol center"><p class="control-evaluation_${index}">[View Detailed]</p></td>
      </tr>
    </table>
    [#-- Evaluation Content --]
    <div id="evaluation_${index}" class="evaluationContent" style="display:${(editable)?string('block','none')}">
      [#if canView]
        [@s.form action="evaluation" method="POST" enctype="multipart/form-data" cssClass="pure-form"]
        <br />
        [#-- Evaluation ranking --]
        <div class="fullPartBlock" style="width:98%">
          <table class="evaluationRank default">
            <thead>
              <tr>
                <th class="center" style="width:20%" title="[@s.text name="project.evaluation.rankingOutputs.help" /]" >[@s.text name="project.evaluation.rankingOutputs" /]</td>
                <th class="center" style="width:20%" title="[@s.text name="project.evaluation.rankingOutcomes.help" /]">[@s.text name="project.evaluation.rankingOutcomes" /]</td>
                <th class="center" style="width:20%" title="[@s.text name="project.evaluation.rankingParternshipComunnication.help" /]">[@s.text name="project.evaluation.rankingParternshipComunnication" /]</td>
                <th class="center" style="width:20%" title="[@s.text name="project.evaluation.rankingResponseTeam.help" /]">[@s.text name="project.evaluation.rankingResponseTeam" /]</td>
                <th class="center" style="width:20%" title="[@s.text name="project.evaluation.rankingQuality.help" /]">[@s.text name="project.evaluation.rankingQuality" /]</td>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td class="center"><span class="weight" style="display:none">20</span>[@customForm.advancedRank name="${customName}.rankingOutputs" split=action.getStartsDiv() editable=editable/] </td>
                <td class="center"><span class="weight" style="display:none">35</span>[@customForm.advancedRank name="${customName}.rankingOutcomes" split=action.getStartsDiv() editable=editable/]</td>
                <td class="center"><span class="weight" style="display:none">15</span>[@customForm.advancedRank name="${customName}.rankingParternshipComunnication" split=action.getStartsDiv() editable=editable/]</td>
                <td class="center"><span class="weight" style="display:none">15</span>[@customForm.advancedRank name="${customName}.rankingResponseTeam" split=action.getStartsDiv() editable=editable/]</td>
                <td class="center"><span class="weight" style="display:none">15</span>[@customForm.advancedRank name="${customName}.rankingQuality" split=action.getStartsDiv() editable=editable/]</td>
              </tr>
            </tbody>
          </table>
        </div>
        
        <div class="fullBlock">
          [#-- Communication products --]
          <div class="fullPartBlock">
            [@customForm.textArea name="${customName}.communicationProducts" i18nkey="project.evaluation.communicationProducts" className="communicationProducts limitWords-50" editable=editable/]
          </div>
          
          [#-- Project Highlight --]
          <div class="fullPartBlock">
            [@customForm.textArea name="${customName}.projectHighlights" i18nkey="project.evaluation.projectHighlights" className="projectHighlights limitWords-50" editable=editable/]
          </div>
          
          [#-- Outcome Case Studies --]
          <div class="fullPartBlock">
            [@customForm.textArea name="${customName}.outcomeCaseStudies" i18nkey="project.evaluation.outcomeCaseStudies" className="outcomeCaseStudies limitWords-50" editable=editable/]
          </div>
          
          [#-- General comments on the reporting and the project's progress and clarifying questions --]
          <div class="fullPartBlock">
            [@customForm.textArea name="${customName}.generalComments" i18nkey="project.evaluation.generalComments" className="generalComments limitWords-500" editable=editable/]
          </div>
          
          [#-- Recommendations to the project team --]
          <div class="fullPartBlock">
            [@customForm.textArea name="${customName}.recommendations" i18nkey="project.evaluation.recommendations" className="recommendations limitWords-500" editable=editable/]
          </div>
          
          [#-- Any action required. Please indicate a time period, e.g. within the next 6 months --]
          <div class="fullPartBlock">
            [@customForm.textArea name="${customName}.anyActionRequeried" i18nkey="project.evaluation.anyActionRequired" className="anyActionRequired limitWords-500" editable=editable/]
          </div>
        </div>
        
        [#-- Save and Submit buttons --]
        [#if editable]
          <br />
          <div class="buttons">
            [#-- Project identifier --]
            <input name="projectID" type="hidden" value="${project.id?c}" />
            <input type="hidden" name="evaluationIndex" value="${index}" />
            [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
            [@s.submit type="button" name="submit"][@s.text name="form.buttons.submit" /][/@s.submit]
          </div>
          <br />
        [/#if]
        
        [/@s.form] 
      [#else]
        <br />
        <p class="center">[@s.text name="project.evaluation.notPrivileges" /]</p>
      [/#if]
    </div>
     
  </div>
[/#macro]
