[#ftl]
[#assign title = "Project Evaluation" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "star-rating", "select2"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/projects/projectEvaluation.js"] /]
[#assign currentSection = cycleName?lower_case /]
[#assign currentCycleSection = "projectsEvaluation" /]
[#assign currentStage = "evaluation" /]
[#assign currentSubStage = "evaluation" /]

[#-- To Delete --]
[#function rand min max]
  [#local now = .now?long?c /]
  [#local randomNum = _rand +
    ("0." + now?substring(now?length-1) + now?substring(now?length-2))?number /]
  [#if (randomNum > 1)]
    [#assign _rand = randomNum % 1 /]
  [#else]
    [#assign _rand = randomNum /]
  [/#if]
  [#return (min + ((max - min) * _rand)) /]
[/#function]
[#assign _rand = 0.36 /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projectsEvaluation", "nameSpace":"${currentSection}", "action":"projectsEvaluation"},
  {"label":"evaluation", "nameSpace":"${currentSection}/projects", "action":"evaluation", "param":"projectID=${project.id}"}
] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" /><p>[@s.text name="${currentSection}.projectEvaluation.help" /]</p>
  </div>
  
  [@s.form action="evaluation" method="POST" enctype="multipart/form-data" cssClass="pure-form"]
  <article class="fullBlock" id="mainInformation">
    <div id="" class="borderBox">
      [#include "/WEB-INF/projects/dataSheet.ftl" /]
      <h1 class="contentTitle">[@s.text name="planning.projectDescription.title" /]</h1>
      <fieldset class="fullBlock">
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
        <div class="fullPartBlock">
          [#-- Project Leader --]
            <div class="dottedBox">
              <div class="halfPartBlock"><div class="select"><h6>Project Leader:</h6><p> ${projectLeader.institution.getComposedName()}</p></div></div>
              <div class="halfPartBlock"><div class="select"><h6>Contact:</h6> 
              [#list projectLeader.partnerPersons as partnerPerson]
                <p> ${partnerPerson.getComposedName()}</p>
              [/#list]
              </div></div>
          </div>
          
          <div class="fullPartBlock">
            <div class="dottedBox">
              <div class="halfPartBlock"><div class="select"><h6>W1/W2 Budget:</h6>
              <p>
                [#assign totalProjectBudget]${((!project.bilateralProject)?string(totalCCAFSBudget!0, totalBilateralBudget!0))}[/#assign]
                US$ <span>${(totalProjectBudget?number)?string(",##0.00")}</span>
              </p></div></div>
              <div class="halfPartBlock"><div class="select"><h6>W3/Bilateral Budget:</h6>
                <p>  
                  US$ <span>${((totalBilateralBudgetbyYear)!0)?string(",##0.00")}</span> 
                </p></div></div>
            </div>
          </div>
        </div>
      </fieldset>
      
    </div> 
    
    [#-- Project Evaluations --]
    <div id="" class="borderBox">
      <h1 class="contentTitle">Project Evaluations</h1>  
      [#list 1..3 as evaluation]
        <div class="simpleBox">
          [@projectEvaluation index=evaluation_index+1 editable=false  /]
        </div>
      [/#list] 
    </div>
    
    [#-- My Evaluation --]
    <div id="" class="borderBox">
      <h1 class="contentTitle">My Evaluation</h1>  
       [@projectEvaluation index=0 editable=true own=true /]
    </div>
    
    [#-- Project identifier --]
    <input name="projectID" type="hidden" value="${project.id?c}" />
   
     
  </article>
  [/@s.form] 
  [#-- Hidden values used by js --]
  <input id="minDateValue" value="${newProject?string(currentPlanningYear,startYear)}-01-01" type="hidden"/>
  <input id="maxDateValue" value="${endYear?c}-12-31" type="hidden"/> 
  <input id="programID" value="${project.liaisonInstitution.id?c}" type="hidden"/>
  <input id="projectsAction" type="hidden" value="${project.bilateralProject?string('coreProjects.do','bilateralCoFinancingProjects.do')}" />
  
  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]

[#macro projectEvaluation index editable=false own=false]

<div class="evaluationBlock">
  <table class="evaluationTable">
    <tr>
      [#if !own]<td class="statusCol">{status}</td>[/#if]
      <td class="rolCol">{rolEvaluation}</td>
      <td class="personCol">{person}</td>
      <td class="totalScoreCol"><p class="totalScore">Pending</p></td>
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
            <td class="center">[@customForm.rank name="project.evaluations[${index}].p1" editable=editable/]</td>
            <td class="center">[@customForm.rank name="project.evaluations[${index}].p2" editable=editable/]</td>
            <td class="center">[@customForm.rank name="project.evaluations[${index}].p3" editable=editable/]</td>
            <td class="center">[@customForm.rank name="project.evaluations[${index}].p4" editable=editable/]</td>
            <td class="center">[@customForm.rank name="project.evaluations[${index}].p5" editable=editable/]</td>
          </tr>
        </tbody>
      </table>
    </div>
    
    [#-- Communication products --]
    <div class="fullPartBlock">
      [@customForm.textArea name="project.evaluations[${index}].p6" i18nkey="project.evaluation.communicationProducts" className="communicationProducts limitWords-50" editable=editable/]
    </div>
    
    [#-- Project Highlight --]
    <div class="fullPartBlock">
      [@customForm.textArea name="project.evaluations[${index}].p7" i18nkey="project.evaluation.projectHighlights" className="projectHighlights limitWords-50" editable=editable/]
    </div>
    
    [#-- Outcome Case Studies --]
    <div class="fullPartBlock">
      [@customForm.textArea name="project.evaluations[${index}].p8" i18nkey="project.evaluation.OutcomeCaseStudies" className="OutcomeCaseStudies limitWords-50" editable=editable/]
    </div>
    
    [#-- General comments on the reporting and the project's progress and clarifying questions --]
    <div class="fullPartBlock">
      [@customForm.textArea name="project.evaluations[${index}].p9" i18nkey="project.evaluation.generalComments" className="generalComments limitWords-500" editable=editable/]
    </div>
    
    [#-- Recommendations to the project team --]
    <div class="fullPartBlock">
      [@customForm.textArea name="project.evaluations[${index}].p10" i18nkey="project.evaluation.recommendations" className="recommendations limitWords-500" editable=editable/]
    </div>
    
    [#-- Any action required. Please indicate a time period, e.g. within the next 6 months --]
    <div class="fullPartBlock">
      [@customForm.textArea name="project.evaluations[${index}].p11" i18nkey="project.evaluation.anyActionRequired" className="anyActionRequired limitWords-500" editable=editable/]
    </div>
    
    [#if editable]
      <hr />
      <div class="" >
        <div class="buttons">
          [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit] 
          [@s.submit type="button" name="submit"][@s.text name="form.buttons.submit" /][/@s.submit]
        </div>
      </div> 
    [/#if]
  </div>
</div>

[/#macro]

