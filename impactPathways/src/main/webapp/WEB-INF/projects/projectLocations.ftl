[#ftl]
[#assign title = "Project Location" /]
[#assign globalLibs =["jquery", "noty","googleMaps"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/projects/projectLocations.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "description" /]
[#assign currentSubStage = "locations" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"planning", "action":"projectsList"},
  {"label":"description", "nameSpace":"planning/projects", "action":"description", "param":"projectID=${project.id}"},
  {"label":"locations", "nameSpace":"planning/projects", "action":"locations", "param":"projectID=${project.id}"}
] /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.projectLocation.help" /] </p>
  </div>
  [#include "/WEB-INF/projects/projectsSubMenu.ftl" /]
  
  [@s.form action="locations" cssClass="pure-form"]
  <article class="halfContent" id="projectLocations">
    [#include "/WEB-INF/projects/dataSheet.ftl" /]
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantActivityPlanningAccessInterceptor--]
    [#if submission?has_content]
      <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
    [#elseif !canEdit ]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]
      </p>
    [/#if]
    [#-- Title --]
    <h1 class="contentTitle">[@s.text name="planning.project.locations.title" /] </h1>  
    <div class="loadingBlock"></div>
    <div id="" class="borderBox" style="display:none"> 
      [#-- Can edit button --]
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
          <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if] 
      <div id="locationsGlobalBlock" class="clearfix">
        [@customForm.checkbox className="globalCheck" name="project.global" value="true" i18nkey="planning.project.locations.checkbox.isGlobal" checked=project.global editable=editable /]
        [#-- [@customForm.checkbox name="project.global" i18nkey="planning.project.locations.checkbox.isGlobal" checked=project.global value="true" /]  --] 
      </div>
      <div id="projectLocations-map" >
      [#if project.global && !editable]
        <img id="global" src="${baseUrl}/images/global/global-map.png">
        <p class="global">[@s.text name="planning.project.locations.map.isGlobal" /]</p>
      [/#if]
      </div> 
      <div id="locationsBlock" class="clearfix">
        <div id="fields">
          [#assign notApplicableText] [@s.text name="planning.project.locations.notApplicable" /] [/#assign]
          <div id="location-head" class="row clearfix">
            <div id="locationIndex-head" class="locationIndex"><strong>#</strong></div>
            [#-- Type/Level --]
            <div id="locationLevel-head" class="locationLevel grid_2"><strong>[@s.text name="planning.project.locations.level" /]</strong></div>
            [#-- Latitude --]
            <div id="locationLatitude-head" class="locationLatitude grid_2"><strong>[@s.text name="planning.project.locations.latitude" /]</strong></div>
            [#-- Longitude --]
            <div id="locationLongitude-head" class="locationLatitude grid_2"><strong>[@s.text name="planning.project.locations.longitude" /]</strong></div>
            [#-- Name --]
            <div id="locationName-head" class="locationName grid_3"><strong>[@s.text name="planning.project.locations.name" /]</strong></div>
          </div> 
          [#if project?has_content]
            [#if project.locations?has_content]
              [#list project.locations as location]
                [#if location.otherLocation]
                  [#assign location=location.otherLocationInstance /]
                [/#if]
                <div id="location-${location_index}" class="location row clearfix">
                  <div class="locationIndex "><strong>${location_index+1}.</strong></div>
                  [#-- Type/Level --]
                  <div class="locationLevel grid_2 "> 
                    [#assign valueType]
                      [#if location.region]${regionTypeID}
                      [#elseif location.country]${countryTypeID}
                      [#elseif location.climateSmartVillage]${climateSmartVillageTypeID}
                      [#else]
                        [#if location.type.id == ccafsSiteTypeID] ${location.type.id}[#else]${location.type.id?c}[/#if]
                      [/#if]
                    [/#assign]
                    [@customForm.select name="" className="locationType" i18nkey="planning.project.locations.level" listName="locationTypes"  keyFieldName="id"  displayFieldName="name" showTitle=false value="${valueType}" disabled=!editable/]
                  </div> 
                  [#-- Latitude --]
                  <div class="locationLatitude grid_2">
                    [#if location.country || location.region]
                      [@customForm.input name="" className="latitude notApplicable" type="text" value=notApplicableText i18nkey="planning.project.locations.latitude" showTitle=false disabled=true  /]
                    [#elseif location.climateSmartVillage] 
                      [#assign latitude] [#if location.geoPosition?has_content] ${location.geoPosition.latitude} [/#if] [/#assign]
                      [@customForm.input name="" className="latitude notApplicable" value="${latitude}" type="text" i18nkey="planning.project.locations.latitude" showTitle=false required=true disabled=true   /]
                    [#else] 
                      [#if location.type.id == ccafsSiteTypeID]
                        [@customForm.input name="" className="latitude notApplicable" value="${location.geoPosition.latitude}" type="text" i18nkey="planning.project.locations.latitude" showTitle=false required=true disabled=true   /]                
                      [#else] 
                        [@customForm.input name="" className="latitude" value="${location.geoPosition.latitude}" type="text" i18nkey="planning.project.locations.latitude" showTitle=false required=true disabled=!editable  /]
                      [/#if]
                    [/#if]
                  </div>
                  [#-- Longitude --]
                  <div class="locationLongitude grid_2">
                    [#if location.country || location.region]
                      [@customForm.input name="" className="longitude notApplicable" value=notApplicableText type="text" i18nkey="planning.project.locations.longitude" showTitle=false disabled=true  /]
                    [#elseif location.climateSmartVillage]
                      [#assign longitude] [#if location.geoPosition?has_content] ${location.geoPosition.longitude} [/#if] [/#assign]
                      [@customForm.input name="" className="longitude notApplicable" value="${longitude}" type="text" i18nkey="planning.project.locations.longitude" showTitle=false required=true disabled=true   /]
                    [#else]
                      [#if location.type.id == ccafsSiteTypeID]
                        [@customForm.input name="" className="longitude notApplicable" value="${location.geoPosition.longitude}" type="text" i18nkey="planning.project.locations.longitude" showTitle=false required=true disabled=true   /]
                      [#else]
                        [@customForm.input name="" className="longitude" value="${location.geoPosition.longitude}" type="text" i18nkey="planning.project.locations.longitude" showTitle=false required=true disabled=!editable  /]
                      [/#if] 
                    [/#if]
                  </div>
                  [#-- Name --]
                  <div class="locationName grid_3">
                    [#if location.country]
                      [@customForm.select name="project.locations" className="locationName" i18nkey="planning.project.locations.level" listName="countries" keyFieldName="id" showTitle=false  displayFieldName="name" value="${location.id}" disabled=!editable/]
                    [#elseif location.region]
                      [@customForm.select name="project.locations" className="locationName" i18nkey="planning.project.locations.level" listName="regions" keyFieldName="id" showTitle=false  displayFieldName="name" value="${location.id}" disabled=!editable/]
                    [#elseif location.climateSmartVillage]
                      [@customForm.select name="project.locations" className="locationName" i18nkey="planning.project.locations.level" listName="climateSmartVillages" keyFieldName="id" showTitle=false  displayFieldName="name" value="${location.id}" disabled=!editable/]
                    [#else]
                     [#if location.type.id == ccafsSiteTypeID] 
                      [@customForm.select name="project.locations" className="locationName" i18nkey="planning.project.locations.level" listName="ccafsSites" keyFieldName="id" showTitle=false  displayFieldName="name" value="${location.id}" disabled=!editable/]
                     [#else]
                      [@customForm.input name="location-${location_index}" className="locationName" i18nkey="planning.project.locations.name" required=true showTitle=false value="${location.name}" disabled=!editable/]
                      <input type="hidden" class="locationId" value="${location.id}"/>
                      <input type="hidden" name="project.locations" value="${location.type.id}|s|${location.geoPosition.latitude}|s|${location.geoPosition.longitude}|s|${location.name}|s|${location.id}"/>
                     [/#if]
                    [/#if]
                  </div>
                  [#-- Remove button --]
                  [#if editable]<img class="removeButton" src="${baseUrl}/images/global/icon-remove.png" />[/#if]
                </div> 
              [/#list] 
            [/#if]
          [#else]
            [#-- Additional message for users without privileges. --]
            [#if !editable]<p>[@s.text name="planning.project.locations.empty" /]</p>[/#if]
          [/#if]
        </div> <!-- End #locationsBlock -->
        [#if editable]
          <div id="addLocationBlock" class="addLink">
            <a href="" id="addLocationLink" class="addLocation addButton" >[@s.text name="planning.project.locations.addLocation" /]</a>
          </div> 
        [/#if]
      </div> 
    </div>
    
    [#if !newProject]
    <div id="lessons" class="borderBox">
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#lessons">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
          <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]#lessons">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if]
      <div class="fullBlock">
        <input type="hidden" name="projectLessons.id" value=${(projectLessons.id)!"-1"} />
        <input type="hidden" name="projectLessons.year" value=${currentPlanningYear} />
        <input type="hidden" name="projectLessons.componentName" value="${actionName}">
        [@customForm.textArea name="projectLessons.lessons" i18nkey="planning.project.locations.lessons" required=!project.bilateralProject editable=editable /]
      </div>
    </div>
    [/#if]
    
    [#-- Project identifier --]
    <input type="hidden" name="projectID" value="${project.id?c}">
    [#if editable] 
      <!-- internal parameter --> 
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
  [#if project.global]
    <input type="hidden" id="isGlobal" value="${project.global?string}">
  [/#if] 
  <input type="hidden" id="isGlobalText" value="[@s.text name="planning.project.locations.map.isGlobal" /]">
  <input type="hidden" id="isEditable" value="${editable?string('1','0')}">
</section>

[#-- Location Existing Template --]
<div id="locationTemplateExisting" class="row clearfix" style="display:none;">
  <div class="locationIndex ">
    <strong>?.</strong>
  </div>
  <div class="locationLevel grid_2 ">
    [#-- Default value Regions : 1 --]
    [@customForm.select name="" value="1" className="locationType" i18nkey="planning.locations.level" listName="locationTypes" keyFieldName="id"  displayFieldName="name" showTitle=false /] 
  </div>
  <div class="locationLatitude grid_2"> 
    [@customForm.input name="" className="latitude notApplicable" value=notApplicableText type="text" i18nkey="planning.locations.longitude" showTitle=false disabled=true  /]
  </div>
  <div class="locationLongitude grid_2">
    [@customForm.input name="" className="longitude notApplicable" value=notApplicableText type="text" i18nkey="planning.locations.longitude" showTitle=false disabled=true  /]
  </div>
  <div class="locationName grid_3">
    [@customForm.select name="project.locations" className="locationName" i18nkey="planning.locations.name" listName="regions" keyFieldName="id" showTitle=false  displayFieldName="name" /]
  </div>
  <img class="removeButton" src="${baseUrl}/images/global/icon-remove.png">
</div>

[#-- List Template of Regions with id: 1 --]
<div id="selectTemplate-1" style="display:none">
  [@customForm.select name="project.locations" className="locationName" i18nkey="planning.locations.level" listName="regions" keyFieldName="id" showTitle=false  displayFieldName="name" /]
</div>
[#-- List Template of Countries with id: 2 --]
<div id="selectTemplate-2" style="display:none">
  [@customForm.select name="project.locations" className="locationName" i18nkey="planning.locations.level" listName="countries" keyFieldName="id" showTitle=false  displayFieldName="name" /]
</div>

[#-- List Template of Climate smart village with id: 10 --]
<div id="selectTemplate-10" style="display:none">
  [@customForm.select name="project.locations" className="locationName" i18nkey="planning.locations.level" listName="climateSmartVillages" keyFieldName="id" showTitle=false  displayFieldName="name" /]
</div>

[#-- List Template of CCAFS Sites with id: 11 --]
<div id="selectTemplate-11" style="display:none">
  [@customForm.select name="project.locations" className="locationName" i18nkey="planning.locations.level" listName="ccafsSites" keyFieldName="id" showTitle=false  displayFieldName="name" /]
</div>

[#-- Input Template for name --]
<div id="inputNameTemplate" style="display:none">
  [@customForm.input name="" className="locationName" type="text" i18nkey="planning.locations.notApplicable" required=true showTitle=false  /]
  <input type="hidden" class="locationId" value="-1"/>
  <input type="hidden" name="project.locations" value="">
</div>
[#--  Not applicable Text --]
<input type="hidden" id="notApplicableText" value="[@s.text name="planning.locations.notApplicable" /]">

[#include "/WEB-INF/global/pages/footer.ftl"]
