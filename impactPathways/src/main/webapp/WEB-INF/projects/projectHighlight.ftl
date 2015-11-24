[#ftl]
[#assign title = "Project Highlight" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/projects/projectHighlight.js"] /]
[#assign currentSection = cycleName?lower_case /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "outputs" /]
[#assign currentSubStage = "highlights" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projects", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projectOutputs", "nameSpace":"${currentSection}/projects", "action":"outputs", "param":"projectID=${project.id}"},
  {"label":"projectHighlights", "nameSpace":"${currentSection}/projects", "action":"highlights", "param":"projectID=${project.id}"},
  {"label":"projectHighlight", "nameSpace":"${currentSection}/projects", "action":"highlight", "param":"highlightID={highlight.id}"}
]/]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/usersPopup.ftl" as usersForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
[#import "/WEB-INF/projects/macros/projectDeliverablesTemplate.ftl" as deliverableTemplate/]
    
<section class="content">
  <div class="helpMessage">
    [#-- TODO: Change help text --]
    <img src="${baseUrl}/images/global/icon-help.png" /> <p>[@s.text name="reporting.projectHighlight.help" /]</p>
  </div>
  [#include "/WEB-INF/projects/projectsSubMenu.ftl" /]
  
  [@s.form action="highlight" cssClass="pure-form"]
  <article class="halfContent" id="projectHighlight">  
    [#include "/WEB-INF/projects/dataSheet.ftl" /]
    <br />
    [#-- Informing user that he-she does not have enough privileges to edit. See GrantProjectPlanningAccessInterceptor --]  
    [#if submission?has_content]
      <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
    [#elseif !canEdit ]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"][@s.param][@s.text name=title /][/@s.param][/@s.text]
      </p>
    [/#if]
    
    [#--  Highlight Information --] 
    <div id="highlight-information" class="borderBox clearfix"> 
      [#if !editable && canEdit]
        <div class="editButton"><a href="[@s.url][@s.param name ="HighlightID"]{highlight.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
          <div class="viewButton"><a href="[@s.url][@s.param name ="HighlightID"]{highlight.id}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if]
      <h1 class="contentTitle">[@s.text name="reporting.projectHighlight.information" /] </h1>  
      
      [#-- Title --]
      <div class="fullBlock">
        [@customForm.input name="highlight.title" type="text" i18nkey="reporting.projectHighlight.title" /]
      </div> 
      
      <div class="fullPartBlock">
        [#-- Author --]
        <div class="halfPartBlock" >
          [@customForm.input name="highlight.author" type="text" i18nkey="reporting.projectHighlight.author" /]
        </div>
      
        [#-- Subject --]
        <div class="halfPartBlock" >
          [@customForm.input name="highlight.subject"  type="text" i18nkey="reporting.projectHighlight.subject" help="reporting.projectHighlight.subject.help" /] 
        </div> 
      </div>

      <div class="fullPartBlock">
        [#-- Contributor --]
        <div class="halfPartBlock" > 
          [@customForm.input name="highlight.contributor"  type="text" i18nkey="reporting.projectHighlight.contributor" help="reporting.projectHighlight.contributor.help" /] 
        </div> 
      
        [#-- Publisher --]
        <div class="halfPartBlock" >
          [@customForm.input name="highlight.publisher" type="text" i18nkey="reporting.projectHighlight.publisher" help="reporting.projectHighlight.publisher.help" /]   
        </div>
      </div>
      
      <div class="fullPartBlock">
        [#-- Relation --]
        <div class="halfPartBlock" >
          [@customForm.input name="highlight.relation" type="text" i18nkey="reporting.projectHighlight.relation" help="reporting.projectHighlight.relation.help" /]
        </div>
      
        [#-- Coverage --]
        <div class="halfPartBlock" >
          [@customForm.input name="highlight.coverage" type="text" i18nkey="reporting.projectHighlight.coverage" help="reporting.projectHighlight.coverage.help" /]
        </div>
      </div>

      [#-- Rights --]
      <div class="fullBlock">
        [@customForm.textArea name="highlight.rights" i18nkey="reporting.projectHighlight.rights" help="reporting.projectHighlight.rights.help" /]
      </div>
      
      <div class="fullPartBlock">
        [#-- Start Date --]
        <div class="halfPartBlock">
          [@customForm.input name="highlight.startDate" type="text" i18nkey="reporting.projectHighlight.startDate" /]
        </div>
  
        [#-- End Date --]
        <div class="halfPartBlock">
          [@customForm.input name="highlight.endDate" type="text" i18nkey="reporting.projectHighlight.endDate" /]
        </div>
      </div>
      
      [#-- Types --]
      <div class="fullBlock">
        <h6>
          <label for="highlight.types">
            [@s.text name="reporting.projectHighlight.types" /]
            <span class="red">*</span>
          </label>
          <img src="${baseUrl}/images/global/icon-help2.png" title="[@s.text name="reporting.caseStudies.types.help"/]" />
        </h6>
        <div class="checkboxGroup">
          [@s.fielderror cssClass="fieldError" fieldName="highlight.types"/]
          [@s.checkboxlist name="highlight.types" list="caseStudyTypeList" listKey="id" listValue="name" value="highlight.typesIds" cssClass="checkbox" help="reporting.caseStudies.types.help" /]
        </div>
      </div>
      
      [#-- image --]
      <div class="fullBlock imageBlock">
        [#if (highlight.imageFileName??)!false]
          <div class="halfPartBlock browseInput">
            [@customForm.input name="highlight.image" type="file" i18nkey="reporting.projectHighlight.image" /]
          </div>                            
          <div id="highlight.image" class="halfPartBlock image">
            <img src="${(highlightsImagesUrl)!}/${(highlight.imageFileName)!'highlightTemplate.png'}" width="100%">
          </div>
        [#else]
          <div class="halfPartBlock browseInput">
            [@customForm.input name="highlight.image" type="file" i18nkey="reporting.projectHighlight.image" /]
          </div>
          <div id="highlight.image" class="halfPartBlock image"></div>
        [/#if]
        <div class="clear"> </div>
      </div>

      [#-- Is global --]
      <div class="halfPartBlock">
        [@customForm.checkbox  name="highlight.global" i18nkey="reporting.projectHighlight.isGlobal" checked=(highlight.global)!false value="true" /]
      </div>

      [#-- Countries --]
      <div class="fullBlock countriesBlock chosen">
        [@customForm.select name="highlight.countries" label="" i18nkey="reporting.projectHighlight.countries" listName="countryList" keyFieldName="id"  displayFieldName="name" value="highlight.countriesIds" multiple=true disabled="${(highlight.global?string('1', '0'))!0}"/]              
      </div>

      [#-- Keywords --]
      <div class="fullBlock">
        [@customForm.input name="highlight.keywords" type="text" i18nkey="reporting.projectHighlight.keywords" /]
      </div>

      [#-- Description --]
      <div class="fullBlock">
        [@customForm.textArea name="highlight.description" i18nkey="reporting.projectHighlight.descripition" /]
      </div>

      [#-- Objectives --]
      <div class="fullBlock">
        [@customForm.textArea name="highlight.objectives" i18nkey="reporting.projectHighlight.objectives" /]
      </div>

      [#-- Result --]
      <div class="fullBlock">
        [@customForm.textArea name="highlight.results" i18nkey="reporting.projectHighlight.results" /]
      </div>

      [#-- Partners --]
      <div class="fullBlock">
        [@customForm.textArea name="highlight.partners" i18nkey="reporting.projectHighlight.partners" /]
      </div>

      [#-- Links / resources --]
      <div class="fullBlock">
        [@customForm.textArea name="highlight.links" i18nkey="reporting.projectHighlight.links" /]
      </div>
      
    </div>
    
    [#if editable] 
      <input name="projectID" type="hidden" value="${project.id?c}" />
      <input name="HighlightID"type="hidden" value="{highlight.id}">
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
   
</section> 


[#include "/WEB-INF/global/pages/footer.ftl"]