[#ftl]
[#assign title = "Project Location" /]
[#assign globalLibs =["jquery", "noty","googleMaps"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/projectLocationsPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "description" /]
[#assign currentSubStage = "locations" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"planning", "action":"projectsList"},
  {"label":"description", "nameSpace":"planning/projects", "action":""}
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
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
  
  [@s.form action="location" cssClass="pure-form"]
  <article class="halfContent" id="activityLocations">
    [#include "/WEB-INF/planning/projectDescription-planning-sub-menu.ftl" /]
    [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantActivityPlanningAccessInterceptor--]
    [#if !saveable]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"][@s.param][@s.text name="planning.project.locations.title"/][/@s.param][/@s.text]
      </p>
    [/#if] 
    <div id="" class="borderBox"> 
      [#-- Can edit button --]
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [/#if]  
      [#-- Title --]
      <h1 class="contentTitle">
      [@s.text name="planning.project" /]: ${project.composedId} - [@s.text name="planning.project.locations.title" /] 
      </h1>  
      <div id="locationsGlobalBlock" class="clearfix">
        [@customForm.checkbox className="globalCheck" name="activity.global" i18nkey="planning.project.locations.checkbox.isGlobal" checked=false editable=editable /]
        [#-- [@customForm.checkbox name="activity.global" i18nkey="planning.project.locations.checkbox.isGlobal" checked=activity.global value="true" /]  --] 
      </div>
      <div id="activityLocations-map"></div> 
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
          [#if activity?has_content]
            [#if activity.locations?has_content]
              [#list activity.locations as location]
                [#if location.otherLocation]
                  [#assign location=location.otherLocationInstance /]
                [/#if]
                <div id="location-${location_index}" class="location row clearfix">
                  <div class="locationIndex "><strong>${location_index+1}.</strong></div>
                  [#-- Type/Level --]
                  <div class="locationLevel grid_2 "> 
                    [#if location.region]
                      [@customForm.select name="regionsSaved[${location_index}].type.id" i18nkey="planning.project.locations.level" listName="locationTypes" keyFieldName="id"  displayFieldName="name" showTitle=false value="${regionTypeID}"/]
                    [#elseif location.country]
                      [@customForm.select name="countriesSaved[${location_index}].type.id" i18nkey="planning.project.locations.level" listName="locationTypes" keyFieldName="id"  displayFieldName="name" showTitle=false value="${countryTypeID}"/]
                    [#elseif location.climateSmartVillage]
                      [@customForm.select name="csvSaved[${location_index}].type.id" i18nkey="planning.project.locations.level" listName="locationTypes" keyFieldName="id"  displayFieldName="name" showTitle=false value="${climateSmartVillageTypeID}"/]   
                    [#else]
                      [#if location.type.id == ccafsSiteTypeID] 
                        [@customForm.select name="type.id" i18nkey="planning.project.locations.level" listName="locationTypes" keyFieldName="id"  displayFieldName="name" showTitle=false value="${location.type.id}"/]               
                      [#else]
                        [@customForm.select name="otherLocationsSaved[${location_index}].type.id" i18nkey="planning.project.locations.level" listName="locationTypes" keyFieldName="id"  displayFieldName="name" showTitle=false value="${location.type.id?c}"/]
                      [/#if]
                    [/#if]
                  </div> 
                  [#-- Latitude --]
                  <div class="locationLatitude grid_2">
                    [#if location.country || location.region]
                      [@customForm.input name="geoPosition.latitude" className="notApplicable" type="text" value=notApplicableText i18nkey="planning.project.locations.latitude" showTitle=false disabled=true  /]
                    [#elseif location.climateSmartVillage] 
                      [#assign latitude] [#if location.geoPosition?has_content] ${location.geoPosition.latitude} [/#if] [/#assign]
                      [@customForm.input name="geoPosition.latitude" className="notApplicable" value="${latitude}" type="text" i18nkey="planning.project.locations.latitude" showTitle=false required=true disabled=true   /]
                    [#else] 
                      [#if location.type.id == ccafsSiteTypeID]
                        [@customForm.input name="geoPosition.latitude" className="notApplicable" value="${location.geoPosition.latitude}" type="text" i18nkey="planning.project.locations.latitude" showTitle=false required=true disabled=true   /]                
                      [#else] 
                        <input type="hidden" name="otherLocationsSaved[${location_index}].geoPosition.id" value="${location.geoPosition.id?c}" >
                        [@customForm.input name="otherLocationsSaved[${location_index}].geoPosition.latitude" value="${location.geoPosition.latitude}" type="text" i18nkey="planning.project.locations.latitude" showTitle=false required=true  /]
                      [/#if]
                    [/#if]
                  </div>
                  [#-- Longitude --]
                  <div class="locationLongitude grid_2">
                    [#if location.country || location.region]
                      [@customForm.input name="geoPosition.longitude" className="notApplicable" value=notApplicableText type="text" i18nkey="planning.project.locations.longitude" showTitle=false disabled=true  /]
                    [#elseif location.climateSmartVillage]
                      [#assign longitude] [#if location.geoPosition?has_content] ${location.geoPosition.longitude} [/#if] [/#assign]
                      [@customForm.input name="geoPosition.longitude" className="notApplicable" value="${longitude}" type="text" i18nkey="planning.project.locations.longitude" showTitle=false required=true disabled=true   /]
                    [#else]
                      [#if location.type.id == ccafsSiteTypeID]
                        [@customForm.input name="geoPosition.longitude" className="notApplicable" value="${location.geoPosition.longitude}" type="text" i18nkey="planning.project.locations.longitude" showTitle=false required=true disabled=true   /]                 
                      [#else]
                        [@customForm.input name="otherLocationsSaved[${location_index}].geoPosition.longitude" value="${location.geoPosition.longitude}" type="text" i18nkey="planning.project.locations.longitude" showTitle=false required=true  /]
                      [/#if] 
                    [/#if]
                  </div>
                  [#-- Name --]
                  <div class="locationName grid_3">
                    [#if location.country]
                      [@customForm.select name="countriesSaved[${location_index}].id" i18nkey="planning.project.locations.level" listName="countries" keyFieldName="id" showTitle=false  displayFieldName="name" value="${location.id}" /]
                    [#elseif location.region]
                      [@customForm.select name="regionsSaved[${location_index}].id" i18nkey="planning.project.locations.level" listName="regions" keyFieldName="id" showTitle=false  displayFieldName="name" value="${location.id}" /]
                    [#elseif location.climateSmartVillage]
                      [@customForm.select name="csvSaved[${location_index}].id" i18nkey="planning.project.locations.level" listName="climateSmartVillages" keyFieldName="id" showTitle=false  displayFieldName="name" value="${location.id}" /]
                    [#else]
                     [#if location.type.id == ccafsSiteTypeID] 
                      [@customForm.select name="activity.locations" i18nkey="planning.project.locations.level" listName="ccafsSites" keyFieldName="id" showTitle=false  displayFieldName="name" value="${location.id}" /]
                     [#else]
                      [@customForm.input name="otherLocationsSaved[${location_index}].name" value="${location.name}" type="text" i18nkey="planning.project.locations.notApplicable" required=true showTitle=false  /]                 
                      <input type="hidden" name="otherLocationsSaved[${location_index}].id" value="${location.id}">
                     [/#if]
                    [/#if]
                  </div>
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
    [#-- Only the user with enough privileges to save can upload the file --] 
    [#if editable]
      [#-- File upload --]
      <div class="uploadFileMessage">
        <p>
          [#if uploadFileName?has_content ] 
            [@s.text name="planning.project.locations.changeFileMessage" /]<br>
            <div id="excelTemplate-file-uploaded">
              <img class="icon-check" src="${baseUrl}/images/global/icon-check.png" /> <a href="${locationsFileURL}" >${uploadFileName} </a>
            </div>  
          [#else]
            [@s.text name="planning.project.locations.uploadMessage" /]
          [/#if]
        </p>
        <hr>  
        <div class="halfPartBlock left">
          <div id="step1" class="step" title="Step 1">1</div>
          <a href="${baseUrl}/resources/locationTemplate/Activity_Location_Template.xlsx">
            <img id="icon" src="${baseUrl}/images/global/icon-excel.png" />
            <p id="downloadMessage">[@s.text name="planning.project.locations.templateMessage" /]</p>
          </a>
        </div>
        <div class="halfPartBlock right">
          <div id="step2" class="step" title="Step 2">2</div>
            [@customForm.input name="excelTemplate" type="file" i18nkey="planning.project.locations.attachTemplate" /] 
            <div id="excelTemplate-file" style="position:relative;display:none">
              <span id="excelTemplate-text"></span>
              <img class="removeButton" src="${baseUrl}/images/global/icon-remove.png" />
            </div>    
        </div>
      </div>
    [/#if]
    [#if editable] 
      <div class="borderBox">
        <!-- internal parameter --> 
        [#-- Project identifier --]
        <input type="hidden" name="projectID" value="${project.id?c}">
        <input type="hidden" id="isGlobal" value="${project.global!'false'}">
        <input type="hidden" id="isGlobalText" value="[@s.text name="planning.project.locations.map.isGlobal" /]">
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
  [#-- Hidden values used by js --]
  <input id="minDateValue" value="${startYear?c}-01-01" type="hidden"/>
  <input id="maxDateValue" value="${endYear?c}-12-31" type="hidden"/> 
  <input id="programID" value="" type="hidden"/>
</section>

[#-- Location Existing Template --]
<div id="locationTemplateExisting" class="row clearfix" style="display:none;">
  <div class="locationIndex ">
    <strong>?.</strong>
  </div>
  <div class="locationLevel grid_2 ">
    [@customForm.select name="type.id" i18nkey="planning.locations.level" listName="locationTypes" keyFieldName="id"  displayFieldName="name" showTitle=false /] 
  </div>
  <div class="locationLatitude grid_2">
      [#-- Geo position ID --]
      <input type="hidden" name="geoPosition.id" value="-1" >
      [@customForm.input name="geoPosition.latitude" className="notApplicable" value=notApplicableText type="text" i18nkey="planning.locations.longitude" showTitle=false disabled=true  /]
  </div>
  <div class="locationLongitude grid_2">
      [@customForm.input name="geoPosition.longitude" className="notApplicable" value=notApplicableText type="text" i18nkey="planning.locations.longitude" showTitle=false disabled=true  /]
  </div>
  <div class="locationName grid_3">
    [@customForm.select name="].id" i18nkey="planning.locations.level" listName="regions" keyFieldName="id" showTitle=false  displayFieldName="name" value="-1" /]
  </div>
  <img class="removeButton" src="${baseUrl}/images/global/icon-remove.png">
</div>

[#-- List Template of Regions with id: 1 --]
<div id="selectTemplate-1" style="display:none">
  [@customForm.select name="].id" i18nkey="planning.locations.level" listName="regions" keyFieldName="id" showTitle=false  displayFieldName="name" /]
</div>
[#-- List Template of Countries with id: 2 --]
<div id="selectTemplate-2" style="display:none">
  [@customForm.select name="].id" i18nkey="planning.locations.level" listName="countries" keyFieldName="id" showTitle=false  displayFieldName="name" /]
</div>

[#-- List Template of Climate smart village with id: 10 --]
<div id="selectTemplate-10" style="display:none">
  [@customForm.select name="].id" i18nkey="planning.locations.level" listName="climateSmartVillages" keyFieldName="id" showTitle=false  displayFieldName="name" /]
</div>

[#-- List Template of CCAFS Sites with id: 11 --]
<div id="selectTemplate-11" style="display:none">
  [@customForm.select name="activity.locations" i18nkey="planning.locations.level" listName="ccafsSites" keyFieldName="id" showTitle=false  displayFieldName="name" /]
</div>

[#-- Input Template for name --]
<div id="inputNameTemplate" style="display:none">
  <input type="hidden" name="].id" value="-1">
  [@customForm.input name="name" type="text" i18nkey="planning.locations.notApplicable" required=true showTitle=false  /]
</div>
[#--  Not applicable Text --]
<input type="hidden" id="notApplicableText" value="[@s.text name="planning.locations.notApplicable" /]">

[#include "/WEB-INF/global/pages/footer.ftl"]