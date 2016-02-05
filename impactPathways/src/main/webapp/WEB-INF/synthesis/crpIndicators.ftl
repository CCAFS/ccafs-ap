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
    [#-- Program (Regions and Flagships) --]
    <ul id="liaisonInstitutions" class="horizontalSubMenu">
      [#list liaisonInstitutions as institution]
        <li class="[#if institution.id == liaisonInstitutionID]active[/#if]"><a href="[@s.url][@s.param name ="liaisonInstitutionID"]${institution.id}[/@s.param][/@s.url]">${institution.acronym}</a></li>
      [/#list]
    </ul>
    
    [#-- CRP Indicators Sub Menu --]
    [#include "/WEB-INF/synthesis/crpIndicators-subMenu.ftl" /]
    
    <div class="halfContent">
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
          ${indicatorReport}
        </div>
        [/#list]
        
      </div>
    </div>
  </article>
  
</section> 


[#include "/WEB-INF/global/pages/footer.ftl"]
