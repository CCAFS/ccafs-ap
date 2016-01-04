[#ftl]
[#assign title = "Project Case Studies" /]
[#assign globalLibs = ["jquery", "noty", "select2", "cytoscape", "qtip","cytoscapePanzoom"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/ipGraph.js", "${baseUrl}/js/projects/projectCaseStudies.js"] /]
[#assign currentSection = cycleName?lower_case /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "outcomes" /] 
[#assign currentSubStage = "caseStudies" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projects", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projectOutcomes", "nameSpace":"${currentSection}/projects", "action":"outcomes", "param":"projectID=${project.id}"},
  {"label":"caseStudies", "nameSpace":"${currentSection}/projects", "action":"caseStudies", "param":"projectID=${project.id}"}
]/]

[#assign params = {
  "caseStudies": {"id":"caseStudiesName", "name":"project.caseStudies"}
  }
/] 

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  [#-- Section help message --]
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" /><p>[@s.text name="reporting.projectCaseStudies.help" /]</p>
  </div>
  
  [#-- Project subMenu --]
  [#include "/WEB-INF/projects/projectsSubMenu.ftl" /]

  [@s.form action="caseStudies" cssClass="pure-form" enctype="multipart/form-data" ]  
  <article class="halfContent" id="activityImpactPathway">
    [#include "/WEB-INF/projects/dataSheet.ftl" /]
    [#include "/WEB-INF/projects/projectIP-sub-menu.ftl" /]
    
    [#-- Submission and privileges message --]
    [#if submission?has_content]
      <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
    [#elseif !canEdit ]
      <p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]</p>
    [/#if]
    
     
   <h1 class="contentTitle">[@s.text name="reporting.projectCaseStudies.caseStudiestitle" /]</h1>
    <div id="caseStudiesBlock" class="">
      [@caseStudy index=0 /]
    </div>
    
    [#if (editable && canEdit)]
      <div id="addCaseStudy"><a href="" class="addButton" >[@s.text name="reporting.projectCaseStudies.addCaseStudy" /]</a></div> 
    [/#if]
    
    
    [#if (editable && canEdit)] 
      [#-- Project identifier --]
      <input name="projectID" type="hidden" value="${project.id?c}" />
      <div class="borderBox" >
        [@customForm.textArea name="justification" i18nkey="saving.justification" required=true className="justification"/]
        <div class="buttons">
          [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
          [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
          [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
        </div>
      </div>
    [#else]
    
    [/#if]
    
  </article>
  [/@s.form]
  
</section>

[#-- Internal parameters --]
[#list params?keys as prop]<input id="${params[prop].id}" type="hidden" value="${params[prop].name}" />[/#list]

[#-- File upload Template--] 
[@customForm.inputFile name="annexesFile" template=true /]

[#-- Case Study template --]
[@caseStudy template=true /]

[#macro caseStudy index="0" template=false]
  [#assign customName = "${params.caseStudies.name}[${template?string('-1',index)}]"/]
  [#--assign study = (customName?eval)! /--]
  [#assign customId = "caseStudy-${template?string('template',index)}" /] 
  <div id="${customId}" class="caseStudy borderBox" style="display:${template?string('none','block')}">
    [#-- Edit/Back/remove buttons --]
    [#if (!editable && canEdit)]
      <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#${customId}">[@s.text name="form.buttons.edit" /]</a></div>
    [#else]
      [#if canEdit]
        <div class="viewButton removeOption"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]#${customId}">[@s.text name="form.buttons.unedit" /]</a></div>
      [/#if] 
      <div class="removeElement" title="[@s.text name="reporting.projectCaseStudies.removeCaseStuty" /]"></div>
    [/#if]
    [#-- Index --]
    <div class="leftHead"><span class="index">${index?number+1}</span><span class="elementId">[@s.text name="reporting.projectCaseStudies.caseStudyTitle" /]</span></div>
    [#-- Region indicators --]
    <div class="fullBlock"> 
      <div class="fullBlock caseStudyIndicators">
        <h6><label for="${customName}.caseStudyIndicators">[@customForm.text name="reporting.projectCaseStudies.caseStudyIndicators" readText=!editable /]:<span class="red">*</span></label></h6>
        <div class="checkboxGroup">
        [#if editable]
          [@s.fielderror cssClass="fieldError" fieldName="${customName}.caseStudyIndicatorsIds"/]
          [@s.checkboxlist name="${customName}.caseStudyIndicatorsIds" list="caseStudyIndicators" value="${(study.caseStudyIndicatorsIds)!}" itemKey="id"  cssClass="caseStudyIndicators checkbox" /]
        [#else]
          [#if (study.caseStudyIndicatorsIds?has_content)!false]
            [#list study.caseStudyIndicatorsIds as element]<p class="checked">${element.description}</p>[/#list]
          [#else]
            <div class="select"><p>Field is empty</p></div>
          [/#if]
        [/#if]
        </div>
      </div>
      [@customForm.textArea name="${customName}.explainIndicatorRelation" i18nkey="reporting.projectCaseStudies.explainIndicatorRelation" className="caseStudyExplainIndicatorRelation limitWords-50" required=true editable=editable /]
    </div>
    [#-- 1. Title --]
    <div class="fullBlock">
      [@customForm.input name="${customName}.title" i18nkey="reporting.projectCaseStudies.title" className="caseStudyTitle limitWords-15" required=true editable=editable /]
    </div>
    [#-- 2. Outcome statement --]
    <div class="fullBlock">
      [@customForm.textArea name="${customName}.outcomeStatement" i18nkey="reporting.projectCaseStudies.outcomeStatement" className="caseStudyOutcomeStatement limitWords-80" required=true editable=editable /]
    </div>
    [#-- 3. Research Outputs --]
    <div class="fullBlock">
      [@customForm.textArea name="${customName}.researchOutputs" i18nkey="reporting.projectCaseStudies.researchOutput" className="caseStudyResearchOutput limitWords-150" required=true editable=editable /]
    </div> 
    [#-- 4.  Research partners --]
    <div class="fullBlock">
      [@customForm.textArea name="${customName}.researchPartners" i18nkey="reporting.projectCaseStudies.researchPartners" className="caseStudyResearchPartners limitWords-150" required=true editable=editable /]
    </div>
    [#-- 5. Activities that contributed to the outcome --]
    <div class="fullBlock">
      [@customForm.textArea name="${customName}.activities" i18nkey="reporting.projectCaseStudies.activitiesContributed" className="caseStudyActivitiesContributed limitWords-150" required=true editable=editable /]
    </div>
    [#-- 6. Non-research partners --]
    <div class="fullBlock">
      [@customForm.textArea name="${customName}.nonResearchPartneres" i18nkey="reporting.projectCaseStudies.nonResearchPartners" className="caseStudyNonResearchPartners limitWords-80" required=true editable=editable /]
    </div>
    [#-- 7. Output Users --]
    <div class="fullBlock">
      [@customForm.textArea name="${customName}.outputUsers" i18nkey="reporting.projectCaseStudies.outputUsers" className="caseStudyOutputUsers limitWords-50" required=true editable=editable /]
    </div>
    [#-- 8. How the output was used --]
    <div class="fullBlock">
      [@customForm.textArea name="${customName}.outputUsed" i18nkey="reporting.projectCaseStudies.outputUsed" className="caseStudyOutputUsed limitWords-50" required=true editable=editable /]
    </div>
    [#-- 9. Evidence of the outcome --]
    <div class="fullBlock">
      [@customForm.textArea name="${customName}.evidenceOutcome" i18nkey="reporting.projectCaseStudies.evidence" className="caseStudyEvidence limitWords-50" required=true editable=editable /]
    </div>
    [#-- 10. References --]
    <div class="fullBlock">
      [@customForm.textArea name="${customName}.referencesCase" i18nkey="reporting.projectCaseStudies.references" className="caseStudyReferences" required=true editable=editable /]
    </div>
    [#-- Upload Annexes --]
    <div class="fullBlock fileUpload uploadAnnexes">
      <h6>[@customForm.text name="reporting.projectCaseStudies.uploadAnnexes" readText=!editable /]:</h6>
      <div class="uploadContainer">
        [#if (study.file?has_content)!false]
          [#if editable]<span id="remove-annexesFile" class="remove"></span>[/#if] 
          <p><a href="${CrossCuttingURL}${study.file}">${study.file}</a></p>
        [#else]
          [#if editable]
            [@customForm.inputFile name="${customName}.annexesFile" className="annexesFile"  /]
          [#else]  
            <span class="fieldError">[@s.text name="form.values.required" /]</span>  [@s.text name="form.values.notFileUploaded" /]
          [/#if] 
        [/#if]
      </div>  
    </div>
  </div>
[/#macro]
  
[#include "/WEB-INF/global/pages/footer.ftl"]