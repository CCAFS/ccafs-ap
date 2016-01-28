[#ftl]
[#assign title = "Project Leverages" /]
[#assign globalLibs = ["jquery", "noty", "select2", "cytoscape", "qtip","cytoscapePanzoom"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/ipGraph.js", "${baseUrl}/js/projects/projectLeverages.js"] /]
[#assign currentSection = cycleName?lower_case /]
[#assign currentCycleSection = "projects" /]
[#assign currentStage = "budget" /] 
[#assign currentSubStage = "leverages" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projects", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"leverages", "nameSpace":"${currentSection}/projects", "action":"leverages", "param":"projectID=${project.id}"}
]/]

[#assign params = {
  "leverages": {"id":"leveragesName", "name":"project.leverages"}
  }
/] 

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  [#-- Section help message --]
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" /><p>[@s.text name="reporting.projectLeverages.help" /]</p>
  </div>
  
  [#-- Project subMenu --]
  [#include "/WEB-INF/projects/projectsSubMenu.ftl" /]

  [@s.form action="leverages" cssClass="pure-form" enctype="multipart/form-data" ]  
  <article class="halfContent" id="activityImpactPathway">
    [#include "/WEB-INF/projects/dataSheet.ftl" /]
    
    [#-- Submission and privileges message --]
    [#if submission?has_content]
      <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
    [#elseif !canEdit ]
      <p class="readPrivileges">[@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]</p>
    [/#if]
     
    <h1 class="contentTitle">[@s.text name="reporting.projectLeverages.leveragestitle" /]</h1>
    [#-- Leverages List --]
    <div id="leveragesBlock" class="">
      [#if project.leverages?has_content ]
        [#list project.leverages as item] 
          [@leverage index=item_index /]
        [/#list]
      [#else]
        [@leverage index=0 /]
      [/#if]
    </div>
    
    [#if (editable && canEdit)]
      <div id="addLeverage"><a href="" class="addButton" >[@s.text name="reporting.projectLeverages.addLeverage" /]</a></div> 
    [/#if]
    
    <p id="addPartnerText" class="helpMessage">
      [@s.text name="preplanning.projectPartners.addPartnerMessage.first" /]
      <a class="popup" href="[@s.url action='partnerSave'][@s.param name='${projectRequest}']${project.id?c}[/@s.param][/@s.url]">
        [@s.text name="preplanning.projectPartners.addPartnerMessage.second" /]
      </a>
    </p>
    
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
      [#-- Display Log History --]
      [#if history??][@log.logList list=history /][/#if]
    [/#if]
    
  </article>
  [/@s.form]
  
</section>

[#-- Internal parameters --]
[#list params?keys as prop]<input id="${params[prop].id}" type="hidden" value="${params[prop].name}" />[/#list]

[#-- Leverage template --]
[@leverage template=true /]

[#macro leverage index="0" template=false]
  [#assign customName = "${params.leverages.name}[${template?string('-1',index)}]"/]
  [#--assign element = (customName?eval)! /--]
  [#assign customId = "leverage-${template?string('template',index)}" /] 
  <div id="${customId}" class="leverage borderBox" style="display:${template?string('none','block')}">
    [#-- Edit/Back/remove buttons --]
    [#if (!editable && canEdit)]
      <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#${customId}">[@s.text name="form.buttons.edit" /]</a></div>
    [#else]
      [#if canEdit]
        <div class="viewButton removeOption"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]#${customId}">[@s.text name="form.buttons.unedit" /]</a></div>
        <div class="removeElement" title="[@s.text name="reporting.projectLeverages.removeLeverage" /]"></div>
      [/#if] 
    [/#if]
    [#-- Index --]
    <div class="leftHead"><span class="index">${index?number+1}</span><span class="elementId">[@s.text name="reporting.projectLeverages.leverageTitle" /]</span></div>
    
    [#-- Leverage ID --]
    <input type="hidden" name="${customName}.id" class="leverageID" value="${(element.id)!-1}"/>
    
    [#-- Title --]
    <div class="fullBlock">
      [@customForm.input name="${customName}.title" i18nkey="reporting.projectLeverages.title" className="leverageTitle limitWords-15" required=true editable=editable /]
    </div> 
    
    [#-- Partner name  --]
    <div class="fullPartBlock partnerName chosen">
      <p class="fieldError"></p>
      [@customForm.select name="${customName}.institution" value="${(element.institution.id)!-1}" className="institutionsList" required=true  disabled=!editable i18nkey="reporting.projectLeverages.partner.name" listName="allInstitutions" keyFieldName="id"  displayFieldName="getComposedName()" editable=editable /]
      [#if editable && !template]
        <input class="institutionsList" type="hidden" name="${customName}.institution" value="${(element.institution.id)!-1}" />
      [/#if]
    </div>
    
    [#-- Start and End Date --]
    <div class="fullPartBlock clearfix"> 
      <div class="halfPartBlock">
        [@customForm.input name="${customName}.startDateText" className="startDate" type="text" i18nkey="reporting.projectLeverages.startDate" required=true editable=editable /]
      </div>   
      <div class="halfPartBlock">
        [@customForm.input name="${customName}.endDateText" className="endDate"  type="text" i18nkey="reporting.projectLeverages.endDate" required=true editable=editable /]
      </div>
    </div>
    
    [#-- Flagship and Budget --]
    <div class="fullPartBlock clearfix"> 
      <div class="halfPartBlock">
        [@customForm.select name="${customName}.flagship" className="flagship" label="" i18nkey="reporting.projectLeverages.flagship" listName="ipProgramFlagships" keyFieldName="id"  displayFieldName="getComposedName()" required=true editable=editable /]
      </div>   
      <div class="halfPartBlock">
        <h6> [@s.text name="reporting.projectLeverages.budget" /]: [@customForm.req required=true /]</h6>
        [#if editable]
          [@customForm.input name="${customName}.budget" className="budget budgetInput" showTitle=false value="${(element.budget)!0}"  type="text" i18nkey="" required=true editable=editable /]
        [#else]
          <div class="input"><p>US$ ${((element.budget)!0)?number?string(",##0.00")}</p></div>
        [/#if]
      </div>
    </div>
    
  </div>
[/#macro]
  
[#include "/WEB-INF/global/pages/footer.ftl"]