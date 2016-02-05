[#ftl]
[#assign title = "CRP Indicators" /]
[#assign globalLibs = ["jquery", "dataTable", "noty","autoSave"] /]
[#assign customJS = ["${baseUrl}/js/crpIndicators.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
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


<section class="content">
  [#-- Help Message --]
  <div class="helpMessage"><img src="${baseUrl}/images/global/icon-help.png" /><p>[@s.text name="reporting.synthesis.crpIndicators.help" /]</p></div>
  
  <article class="fullBlock clearfix" id="crpIndicators">
    [@s.form action="description" method="POST" enctype="multipart/form-data" cssClass="pure-form"]
    [#-- Program (Regions and Flagships) --]
    <ul id="liaisonInstitutions" class="horizontalSubMenu">
      [#list liaisonInstitutions as institution]
        <li class="[#if institution.id == liaisonInstitutionID]active[/#if]"><a href="[@s.url][@s.param name ="liaisonInstitutionID"]${institution.id}[/@s.param][@s.param name='indicatorTypeID']${(indicatorTypeID)!}[/@s.param][/@s.url]">${institution.acronym}</a></li>
      [/#list]
    </ul>
    
    [#-- CRP Indicators Sub Menu --]
    [#include "/WEB-INF/synthesis/crpIndicators-subMenu.ftl" /]
    
    <div class="halfContent">
      [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantProjectPlanningAccessInterceptor--]
      [#if submission?has_content]
        <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
      [#elseif !canEdit ]
        <p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]</p>
      [/#if]
    
      <h1 class="contentTitle">[@s.text name="reporting.synthesis.crpIndicators.title" /]</h1>
      <div id="indicatorsList" class="borderBox">
        [#-- Button for edit this section --]
        [#if (!editable && canEdit)]
          <div class="editButton"><a href="[@s.url][@s.param name ="liaisonInstitutionID"]${liaisonInstitutionID}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
        [#else]
          [#if canEdit]
            <div class="viewButton"><a href="[@s.url][@s.param name ="liaisonInstitutionID"]${liaisonInstitutionID}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>
          [/#if]
        [/#if]
        
        [#-- Indicators --]
        [#list indicatorReports as indicatorReport]
        <div class="simpleBox">
          <h6 class="title" style="font-size: 1.2em;">${indicatorReport.indicator.name}</h6>
          [#-- Targets --]
          <div class="fullBlock">
            <div class="thirdPartBlock">[@customForm.input name="" type="text" i18nkey="reporting.synthesis.crpIndicators.target" editable=false /]</div>
            <div class="thirdPartBlock">[@customForm.input name="" type="text" i18nkey="reporting.synthesis.crpIndicators.actual" required=canEdit editable=editable /]</div>
            <div class="thirdPartBlock">[@customForm.input name="" type="text" i18nkey="reporting.synthesis.crpIndicators.nextYearTarget" required=canEdit editable=editable /]</div>
          </div>
          [#-- Link to supporting databases --]
          <div class="fullBlock">
            [@customForm.textArea name="" i18nkey="reporting.synthesis.crpIndicators.links" required=canEdit editable=editable /]
          </div>
          [#-- Deviation --]
          <div class="fullBlock">
            [@customForm.textArea name="" i18nkey="reporting.synthesis.crpIndicators.deviation" required=canEdit editable=editable /]
          </div>
        </div>
        [/#list]
        
      </div>
    </div>
    [/@s.form] 
  </article>
  
</section> 


[#include "/WEB-INF/global/pages/footer.ftl"]
