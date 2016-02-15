[#ftl]
[#assign title = "Outcome Synthesis" /]
[#assign globalLibs = ["jquery", "dataTable", "noty","autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/synthesis/outcomeSynthesis.js"] /]
[#assign customCSS = [] /]
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

<section class="content">
  [#-- Help Message --]
  <div class="helpMessage"><img src="${baseUrl}/images/global/icon-help.png" /><p>[@s.text name="reporting.synthesis.outcomeSynthesis.help" /]</p></div>
  
  <article class="fullBlock clearfix" id="">
    [@s.form action="outcomeSynthesis" method="POST" enctype="multipart/form-data" cssClass="pure-form"]
    [#-- Program (Regions and Flagships) --]
    <ul id="liaisonInstitutions" class="horizontalSubMenu">
      [#list liaisonInstitutions as institution]
        <li class="[#if institution.id == liaisonInstitutionID]active[/#if]"><a href="[@s.url][@s.param name ="liaisonInstitutionID"]${institution.id}[/@s.param][@s.param name='indicatorTypeID']${(indicatorTypeID)!}[/@s.param][@s.param name ="edit"]true[/@s.param][/@s.url]">${institution.acronym}</a></li>
      [/#list]
    </ul>
    
    <div class="fullContent">
      [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantProjectPlanningAccessInterceptor--]
      [#if submission?has_content]
        <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
      [#elseif !canEdit ]
        <p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]</p>
      [/#if]
    
      <h1 class="contentTitle">[@s.text name="reporting.synthesis.outcomeSynthesis.title" /]</h1>
      
      <div id="outcomeSynthesisBlock" class="borderBox">
        
      </div>
      
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
