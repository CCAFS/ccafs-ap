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
  {"label":"evaluation", "nameSpace":"${currentSection}/evaluation", "action":"evaluation", "param":"projectID=${project.id}"}
] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" /><p>[@s.text name="${currentSection}.projectEvaluation.help" /]</p>
  </div>
  
  [#if !canEdit ]<p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param][@s.text name="planning.project"/][/@s.param][/@s.text]</p>[/#if]
  
  [@s.form action="evaluation" method="POST" enctype="multipart/form-data" cssClass="pure-form"]
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
          <div class="dottedBox select"><h6>Management Liaison:</h6><p> ${(project.liaisonInstitution.name)!} -  ${project.owner.composedOwnerName}</p></div>  
        </div>
        [#-- Flagships / Regions --] 
        <div class="thirdPartBlock">
          <div class="dottedBox">
            <h6>[@s.text name="preplanning.projectDescription.flagships" /] / [@s.text name="preplanning.projectDescription.regions" /]:</h6>
            <div class="checkboxGroup">
              [#if project.flagships?has_content][#list project.flagships as element]<p class="focus">${element.acronym}</p>[/#list][/#if] 
              [#if project.regions?has_content][#list project.regions as element]<p class="focus">${element.acronym}</p>[/#list][/#if]
              <div class="clearfix"></div>
            </div>
          </div>
        </div> 
      </div>
      
      <div class="halfPartBlock">
        [#-- Project Leader --]
        <div class="dottedBox">
          <div class="halfPartBlock">
            <div class="select"><h6>Project Leader:</h6>
              <p>${projectLeader.institution.acronym} - ${partnerPerson.getComposedName()}</p>
            </div>
          </div> 
        </div>    
      </div>
        [#-- Project Budget --]
        <div class="halfPartBlock">
          <div class="dottedBox">
            <div class="halfPartBlock select"><h6>W1/W2 Budget:</h6>
              <p>[#assign totalProjectBudget]${((!project.bilateralProject)?string(totalCCAFSBudget!0, totalBilateralBudget!0))}[/#assign]
              US$ <span>${(totalProjectBudget?number)?string(",##0.00")}</span></p>
            </div>
            <div class="halfPartBlock select">
              <h6>W3/Bilateral Budget:</h6><p>US$ <span>${(totalBilateralBudget!0)?string(",##0.00")}</span></p>
            </div>
          </div>
        </div>  
    </div> 
    
    [#-- Project Evaluations --]
    <h1 class="contentTitle">Project Evaluations</h1>  
    <div id="" class="borderBox">
      [#if project.evaluations?size >1]
        [#list project.evaluations as evaluation]
          [#if evaluation_index+1 !=project.evaluations?size]<div class="simpleBox">[@projectEvaluation index=evaluation_index+1 editable=false  /]</div>[/#if]
        [/#list]
      [#else]
        <p>There is no assessment for this project.</p>
      [/#if]
    </div>
    
    [#-- My Evaluation --]
    <h1 class="contentTitle">My Evaluation</h1>
    <div id="" class="borderBox">
        [#-- Button for edit this section --]
        [#if (!editable && canEdit)]
          <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
        [#else]
          [#if canEdit]
            <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>
          [/#if]
        [/#if]
        <br />
       [@projectEvaluation index=0 editable=(!project.evaluations[0].isSubmited && editable) own=true /]
    </div>
    
    [#-- Project identifier --]
    <input name="projectID" type="hidden" value="${project.id?c}" />
   
  </article>
  [/@s.form] 
 
 
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]

[#macro projectEvaluation index editable=false own=false]

<div class="evaluationBlock">
  <table class="evaluationTable">
    <tr>
      [#if !own]<td class="statusCol">[@s.text name="project.evaluations[${index}].status" /]</td>[/#if]
        [#assign userName = action.getUserName(project.evaluations[index].userId) /]
      <td class="rolCol">[@s.text name="project.evaluations[${index}].typeEvaluation" /] Evaluation</td>
      <td class="personCol">[@s.text name="${userName}" /]</td>
      <td class="totalScoreCol"><p class="totalScore">[@s.text name="project.evaluations[${index}].totalScore" /]</p></td>
      [#if !own]<td class="detailCol center"><p class="control-evaluation_${index}">[View Detailed]</p></td>[/#if]
    </tr>
  </table>
  
  <div id="evaluation_${index}" style="display:${own?string('block','none')}">
    <hr />
    <br />
    [#-- Evaluation ranking --]
    <div class="fullPartBlock">
      <table class="evaluationRank default" style="width:98%">
        <thead>
          <tr>
            <th class="center" style="width:20%">Project progress towards outputs (20%) </td>
            <th class="center" style="width:20%">Project progress towards outcomes (35%)</td>
            <th class="center" style="width:20%">Reflections of CCAFS principles: quality of partnerships, communications, gender (15%)</td>
            <th class="center" style="width:20%">Response of team to the unexpected, ability to adapt and self-reflect (15%)</td>
            <th class="center" style="width:20%">Quality of reporting, incl. submission of deliverables and making them accessible (15%)</td>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td class="center"><span class="weight" style="display:none">20</span>[@customForm.rank name="project.evaluations[${index}].rankingOutputs" editable=editable/][@s.text name="project.evaluations[${index}].rankingOutputs" /]</td>
            <td class="center"><span class="weight" style="display:none">35</span>[@customForm.rank name="project.evaluations[${index}].rankingOutcomes" editable=editable/][@s.text name="project.evaluations[${index}].rankingOutcomes" /]</td>
            <td class="center"><span class="weight" style="display:none">15</span>[@customForm.rank name="project.evaluations[${index}].rankingParternshipComunnication" editable=editable/][@s.text name="project.evaluations[${index}].rankingParternshipComunnication" /]</td>
            <td class="center"><span class="weight" style="display:none">15</span>[@customForm.rank name="project.evaluations[${index}].rankingResponseTeam" editable=editable/][@s.text name="project.evaluations[${index}].rankingResponseTeam" /]</td>
            <td class="center"><span class="weight" style="display:none">15</span>[@customForm.rank name="project.evaluations[${index}].rankingQuality" editable=editable/][@s.text name="project.evaluations[${index}].rankingQuality" /]</td>
          </tr>
        </tbody>
      </table>
    </div>
    
    [#-- Communication products --]
    <div class="fullPartBlock">
      [@customForm.textArea name="project.evaluations[${index}].communicationProducts" i18nkey="project.evaluation.communicationProducts" className="communicationProducts limitWords-50" editable=editable/]
    </div>
    
    [#-- Project Highlight --]
    <div class="fullPartBlock">
      [@customForm.textArea name="project.evaluations[${index}].projectHighlights" i18nkey="project.evaluation.projectHighlights" className="projectHighlights limitWords-50" editable=editable/]
    </div>
    
    [#-- Outcome Case Studies --]
    <div class="fullPartBlock">
      [@customForm.textArea name="project.evaluations[${index}].outcomeCaseStudies" i18nkey="project.evaluation.outcomeCaseStudies" className="outcomeCaseStudies limitWords-50" editable=editable/]
    </div>
    
    [#-- General comments on the reporting and the project's progress and clarifying questions --]
    <div class="fullPartBlock">
      [@customForm.textArea name="project.evaluations[${index}].generalComments" i18nkey="project.evaluation.generalComments" className="generalComments limitWords-500" editable=editable/]
    </div>
    
    [#-- Recommendations to the project team --]
    <div class="fullPartBlock">
      [@customForm.textArea name="project.evaluations[${index}].recommendations" i18nkey="project.evaluation.recommendations" className="recommendations limitWords-500" editable=editable/]
    </div>
    
    [#-- Any action required. Please indicate a time period, e.g. within the next 6 months --]
    <div class="fullPartBlock">
      [@customForm.textArea name="project.evaluations[${index}].anyActionRequeried" i18nkey="project.evaluation.anyActionRequired" className="anyActionRequired limitWords-500" editable=editable/]
    </div>
    
    [#if editable]
      <hr />
      <div class="" >
        <div class="buttons">
          [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
          <a class="addButton" href="[@s.url namespace="/${currentSection}/evaluation" action='submitEvaluation'][@s.param name="projectID"]${project.id?c}[/@s.param][/@s.url]">
            [@s.text name="form.buttons.submit" /]
          </a>
        </div>
      </div> 
    [/#if]
  </div>
</div>

[/#macro]
