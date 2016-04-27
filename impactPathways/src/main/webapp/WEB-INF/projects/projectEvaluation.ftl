[#ftl]
[#assign title = "Project Evaluation" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "select2"] /]
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
          <div class="halfPartBlock">
            <div class="dottedBox select"><h6>Project Leader:</h6><p> {Project Leader Name} </p></div>
          </div>
          
          <div class="halfPartBlock">
            <div class="dottedBox">
              <div class="halfPartBlock"><div class="select"><h6>W1/W2 Budget:</h6><p> {US$ 0.00} </p></div>  </div>
              <div class="halfPartBlock"><div class="select"><h6>W3/Bilateral Budget:</h6><p> {US$ 0.00} </p></div>  </div>
            </div>
          </div>
        </div>
      </fieldset>
      
    </div> 
    
    [#-- Project Evaluations --]
    <div id="projectDescription" class="borderBox">
      <h1 class="contentTitle">Project Evaluations</h1>  
      [#list 1..5 as evaluation]
        <div class="simpleBox">
          <table class="evaluationTable">
          	<tr>
          		<td class="statusCol">{status}</td>
          		<td class="rolCol">{rolEvaluation}</td>
          		<td class="personCol">{person}</td>
          		<td class="totalScoreCol"><p class="totalScore">${rand(1, 5)?string["0.##"]}</p></td>
          		<td class="detailCol center"><p class="control-evaluation_${evaluation_index}">[View Detailed]</p></td>
          	</tr>
          </table> 
          <div id="evaluation_${evaluation_index}" style="display:none">
            Evaluation Details #${evaluation_index}
          </div>
        </div>
      [/#list] 
    </div>
    
    [#-- My Evaluation --]
    <div id="projectDescription" class="borderBox">
      <h1 class="contentTitle">My Evaluation</h1>  
       
    </div>
    
    [#-- Project identifier --]
    <input name="projectID" type="hidden" value="${project.id?c}" />
    [#if editable]
      <div class="[#if !newProject]borderBox[/#if]" >
        [#if !newProject] [@customForm.textArea name="justification" i18nkey="saving.justification" required=true className="justification"/][/#if]
        <div class="buttons">
          [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
          [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
          [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
        </div>
      </div>
    [#else]
      [#-- Display Log History --]
      [#if history??][@log.logList list=history /][/#if] 
    [/#if]
     
  </article>
  [/@s.form] 
  [#-- Hidden values used by js --]
  <input id="minDateValue" value="${newProject?string(currentPlanningYear,startYear)}-01-01" type="hidden"/>
  <input id="maxDateValue" value="${endYear?c}-12-31" type="hidden"/> 
  <input id="programID" value="${project.liaisonInstitution.id?c}" type="hidden"/>
  <input id="projectsAction" type="hidden" value="${project.bilateralProject?string('coreProjects.do','bilateralCoFinancingProjects.do')}" />
  
  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]
