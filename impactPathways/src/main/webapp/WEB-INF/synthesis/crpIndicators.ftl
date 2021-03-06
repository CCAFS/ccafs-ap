[#ftl]
[#assign title = "CRP Indicators" /]
[#assign globalLibs = ["jquery", "dataTable", "noty","autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/synthesis/crpIndicators.js"] /]
[#assign customCSS = [] /]
[#assign currentSection = reportingCycle?string('reporting','planning') /]
[#assign currentCycleSection = "crpIndicators" /]
[#assign currentStage = "crpIndicators" /]
[#assign currentSubStage = "indicator-${indicatorTypeID}" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"crpIndicators", "nameSpace":"${currentSection}", "action":"crpIndicators"}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
[#import "/WEB-INF/global/macros/utils.ftl" as utilities/]

<section class="content">
  [#-- Help Message --]
  <div class="helpMessage"><img src="${baseUrl}/images/global/icon-help.png" /><p>[@s.text name="reporting.synthesis.crpIndicators.help" /]</p></div>
  
  <article class="fullBlock clearfix" id="crpIndicators">
    [@s.form action="crpIndicators" method="POST" enctype="multipart/form-data" cssClass="pure-form"]
    [#-- Program (Regions and Flagships) --]
    <ul id="liaisonInstitutions" class="horizontalSubMenu">
      [#list liaisonInstitutions as institution]
        [#assign isActive = (institution.id == liaisonInstitutionID)/]
        [#assign isCompleted = false /]
        [#assign hasPermission = action.hasSynthesisPermission('update', institution.id)/]
        <li class="${isActive?string('active','')} ${hasPermission?string('canEdit','')}">
          <a href="[@s.url][@s.param name ="liaisonInstitutionID"]${institution.id}[/@s.param][@s.param name ="edit"]true[/@s.param][/@s.url]">${institution.acronym}</a>
          [#if isCompleted] <p class="synthesisCompleted"> <img src="${baseUrl}/images/global/icon-check-tiny${isActive?string('-white','')}.png"/> </p> [/#if]
        </li>
      [/#list]
    </ul>
    
    <div class="fullContent">
      [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantProjectPlanningAccessInterceptor--]
      [#if submission?has_content]
        <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
      [#elseif !canEdit ]
        <p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]</p>
      [/#if]
      
      [#-- Title --]
      <h1 class="contentTitle">[@s.text name="reporting.synthesis.crpIndicators.title" /]</h1>
      
      <div id="crpIndicatorsTabs">
        <ul>
          [#list indicatorsType as indicatorType]  
            <li title="${indicatorType.name}"><a href="#indicatorType-${indicatorType.id}">[@utilities.wordCutter string=indicatorType.name maxPos=36 /]</a></li>
          [/#list]
        </ul>
  
        [#list indicatorsType as indicatorType]
          <div id="indicatorType-${indicatorType.id}" class="indicatorsByType">
            [#-- Button for edit this section --]
            [#if (!editable && canEdit)]
              <div class="editButton"><a href="[@s.url][@s.param name ="liaisonInstitutionID"]${liaisonInstitutionID}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
            [#elseif canEdit]
                <div class="viewButton"><a href="[@s.url][@s.param name ="liaisonInstitutionID"]${liaisonInstitutionID}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>
            [/#if]
            
            [#-- List of indicators by type --]
            [#list action.getCrpIndicatorsByType(indicatorType.id) as indicatorReport]
            <div class="simpleBox">
              <h6 class="title" style="font-size: 1.2em;margin-bottom: 5px;">${indicatorReport.indicator.id}.  ${indicatorReport.indicator.name}
                [#if indicatorReport.indicator.description?has_content]
                  <a id="showIndicatorDesc-${indicatorReport.indicator.id}" class="showIndicatorDesc" href="#"><img src="${baseUrl}/images/global/icon-info.png" title="Show indicator description" alt="" /></a>
                [/#if]
              </h6>
              [#if indicatorReport.indicator.description?has_content]
                <div class="fullPartBlock">
                  <p id="indicatorDesc-${indicatorReport.indicator.id}" style="display:none">${indicatorReport.indicator.description}</p>
                </div>
              [/#if]
              [#-- Targets --]
              <div class="fullPartBlock">
                <div class="thirdPartBlock">[@customForm.input name="indicatorReports[${action.getIndicatorIndex(indicatorReport.indicator.id,indicatorReport.indicator.type.id)}].target" type="text" i18nkey="reporting.synthesis.crpIndicators.target" className="isNumeric" help="form.message.numericValue" paramText="${currentReportingYear}" editable=false /]</div>
                <div class="thirdPartBlock">[@customForm.input name="indicatorReports[${action.getIndicatorIndex(indicatorReport.indicator.id,indicatorReport.indicator.type.id)}].actual" type="text" i18nkey="reporting.synthesis.crpIndicators.actual" className="isNumeric" help="form.message.numericValue" paramText="${currentReportingYear}" required=canEdit editable=editable /]</div>
                <div class="thirdPartBlock">[@customForm.input name="indicatorReports[${action.getIndicatorIndex(indicatorReport.indicator.id,indicatorReport.indicator.type.id)}].nextYearTarget" type="text" i18nkey="reporting.synthesis.crpIndicators.nextYearTarget" className="isNumeric" help="form.message.numericValue" paramText="${currentReportingYear+1}" required=canEdit editable=editable /]</div>
              </div>
              [#-- Link to supporting databases --]
              <div class="fullPartBlock">
                [@customForm.textArea name="indicatorReports[${action.getIndicatorIndex(indicatorReport.indicator.id,indicatorReport.indicator.type.id)}].supportLinks" i18nkey="reporting.synthesis.crpIndicators.links"  editable=editable /]
              </div>
              [#-- Deviation --]
              <div class="fullPartBlock">
                [@customForm.textArea name="indicatorReports[${action.getIndicatorIndex(indicatorReport.indicator.id,indicatorReport.indicator.type.id)}].deviation" i18nkey="reporting.synthesis.crpIndicators.deviation"  editable=editable /]
              </div>
            </div>
            [/#list]
          </div>
        [/#list]
      </div>
        <input type="hidden" name="liaisonInstitutionID" value="${liaisonInstitutionID}"  /> 
      [#if editable]
     
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
