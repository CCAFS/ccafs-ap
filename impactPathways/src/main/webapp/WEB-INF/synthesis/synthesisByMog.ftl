[#ftl]
[#assign title = "Synthesis by MOG" /]
[#assign globalLibs = ["jquery", "dataTable", "noty","autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/synthesis/synthesisByMog.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable-flat.css"] /]
[#assign currentSection = reportingCycle?string('reporting','planning') /]
[#assign currentCycleSection = "synthesisByMog" /]
[#assign currentStage = "synthesisByMog" /]
[#assign currentSubStage = "synthesisByMog" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"synthesisByMog", "nameSpace":"${currentSection}", "action":"synthesisByMog"}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
[#import "/WEB-INF/global/macros/utils.ftl" as utilities/]

<section class="content">
  [#-- Help Message --]
  <div class="helpMessage"><img src="${baseUrl}/images/global/icon-help.png" /><p>[@s.text name="reporting.synthesis.synthesisByMog.help" /]</p></div>
  
  <article class="fullBlock clearfix" id="">
    [@s.form action="synthesisByMog" method="POST" enctype="multipart/form-data" cssClass="pure-form"]
    
    [#-- Program (Regions and Flagships) --]
    <ul id="liaisonInstitutions" class="horizontalSubMenu">
      [#list liaisonInstitutions as institution]
        <li class="[#if institution.id == liaisonInstitutionID]active[/#if]"><a href="[@s.url][@s.param name ="liaisonInstitutionID"]${institution.id}[/@s.param][@s.param name='indicatorTypeID']${(indicatorTypeID)!}[/@s.param][@s.param name ="edit"]true[/@s.param][/@s.url]">${institution.name}</a></li>
      [/#list]
    </ul>
    
    <div class="fullContent">
      [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantProjectPlanningAccessInterceptor--]
      [#if submission?has_content]
        <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
      [#elseif !canEdit ]
        <p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param]${title}[/@s.param][/@s.text]</p>
      [/#if]
      
      [#-- Title --]
      <h1 class="contentTitle">[@s.text name="reporting.synthesis.synthesisByMog.title" ][@s.param]${(currentLiaisonInstitution.name)!}[/@s.param][/@s.text]</h1>
      
      [#-- MOGs --]
      <div id="outcomeSynthesisBlock" class="">
        [#list mogs as mog]
        <div class="borderBox">
          [#-- MOG Name --]
          <div class="fullPartBlock">
            <h6 class="title">${mog.getComposedId()}</h6>
            <p>${mog.description}</p>
          </div>
          [#-- Synthesis report for MOG --]
          <div class="fullPartBlock">
            [@customForm.textArea name="" i18nkey="reporting.synthesis.synthesisByMog.synthesisReport" className="synthesisReport limitWords-100" required=canEdit editable=editable /]
          </div>
          [#-- Gender synthesis report for MOG --]
          <div class="fullPartBlock">
            [@customForm.textArea name="" i18nkey="reporting.synthesis.synthesisByMog.genderSynthesisReport" className="genderSynthesisReport limitWords-100" required=canEdit editable=editable /]
          </div>
          [#-- Synthesis reported by regions --]
          <div class="fullPartBlock">
            {Synthesis reported by regions HERE}
          </div>
          [#-- Projects contributions to this MOG --]
          <div class="fullPartBlock">
            {Projects contributions to this MOG HERE}
          </div>
        </div>
        [/#list]
      </div>
      
      [#-- Button save and Log history --]
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
