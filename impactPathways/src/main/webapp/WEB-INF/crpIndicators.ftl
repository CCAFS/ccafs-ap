[#ftl]
[#assign title = "CRP Indicators" /]
[#assign globalLibs = ["jquery", "dataTable", "noty","autoSave"] /]
[#assign customJS = ["${baseUrl}/js/crpIndicators.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = reportingCycle?string('reporting','planning') /]
[#assign currentCycleSection = "crpIndicators" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"crpIndicators", "nameSpace":"${currentSection}", "action":"crpIndicators"}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
    
<section class="content">
  [#-- Help Message --]
  <div class="helpMessage"><img src="${baseUrl}/images/global/icon-help.png" /><p>[@s.text name="reporting.crpIndicators.help" /]</p></div>
  <br> 
  <article class="fullBlock" id="mainInformation">
    <h3 class="projectSubTitle">[@s.text name="reporting.crpIndicators.title"/]</h3>
    <div class="halfContent">
      <div class="borderBox"> 
      </div>
    </div>
  </article>
  
</section> 


[#include "/WEB-INF/global/pages/footer.ftl"]
