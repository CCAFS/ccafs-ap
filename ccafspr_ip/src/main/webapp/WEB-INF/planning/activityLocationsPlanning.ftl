[#ftl]
[#assign title = "Activity locations" /]
[#assign globalLibs = ["jquery", "noty","googleMaps"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/activityLocationPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "activities" /]
[#assign currentStage = "activityLocations" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projects"},
  {"label":"projects", "nameSpace":"planning", "action":"projects"},
  {"label":"activities", "nameSpace":"planning/projects", "action":"activities" ,"param":"projectID=${project.id}" },
  {"label":"activityLocations", "nameSpace":"planning/activities", "action":"activityLocations" }
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.activities.locations.help" /] </p>
  </div>
  [#include "/WEB-INF/planning/activityPlanningSubMenu.ftl" /]
  
  [@s.form action="activityLocations" cssClass="pure-form" enctype="multipart/form-data"]  
  <article class="halfContent" id="activityLocations">
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantActivityPlanningAccessInterceptor--]
    [#if !saveable]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"]
          [@s.param][@s.text name="planning.activities.locations.title"/][/@s.param]
        [/@s.text]
      </p>
    [/#if]
    [#-- Title --]
    <h1 class="contentTitle">
    [@s.text name="planning.activities.locations.title" /] 
    </h1>  
    <div id="activityGlobalBlock" class="borderBox">
      [@customForm.checkbox name="isGlobal" i18nkey="planning.activities.isGlobal" checked=isGlobal value="false"/]
    </div>
    <div id="activityLocations-map"></div> 
    <div id="locationsBlock" class="clearfix">
      <div id="fields">
        [#if activity.locations?has_content]
          [#assign notApplicableText] [@s.text name="planning.activities.locations.notApplicable" /] [/#assign]
          <div id="location-head" class="row clearfix">
            <div id="locationIndex-head" class="locationIndex">
              <strong>#</strong>
            </div>
            [#-- Type/Level --]
            <div id="locationLevel-head" class="locationLevel grid_2">
              <strong>[@s.text name="planning.activities.locations.level" /]</strong>
            </div>
            [#-- Latitude --]
            <div id="locationLatitude-head" class="locationLatitude grid_2">
              <strong>[@s.text name="planning.activities.locations.latitude" /]</strong>
            </div>
            [#-- Longitude --]
            <div id="locationLongitude-head" class="locationLatitude grid_2">
              <strong>[@s.text name="planning.activities.locations.longitude" /]</strong>
            </div>
            [#-- Name --]
            <div id="locationName-head" class="locationName grid_3">
              <strong>[@s.text name="planning.activities.locations.name" /]</strong>
            </div>
          </div> 
          [#list activity.locations as location]
            [#if location.otherLocation]
              [#assign location=location.otherLocationInstance /]
            [/#if]
            <div id="location-${location_index}" class="location row borderBox clearfix">
              <div class="locationIndex ">
                <strong>${location_index+1}.</strong>
              </div>
              <div class="locationLevel grid_2 ">
                [#-- Type/Level --]
                [#if location.region]
                  [@customForm.select name="regionsSaved[${location_index}].type.id" i18nkey="planning.activities.locations.level" listName="locationTypes" keyFieldName="id"  displayFieldName="name" showTitle=false value="${regionTypeID}"/]
                [#elseif location.country]
                  [@customForm.select name="countriesSaved[${location_index}].type.id" i18nkey="planning.activities.locations.level" listName="locationTypes" keyFieldName="id"  displayFieldName="name" showTitle=false value="${countryTypeID}"/]
                [#else]
                  [@customForm.select name="otherLocationsSaved[${location_index}].type.id" i18nkey="planning.activities.locations.level" listName="locationTypes" keyFieldName="id"  displayFieldName="name" showTitle=false value="${location.type.id?c}"/]
                [/#if]
              </div> 
              <div class="locationLatitude grid_2">
                [#-- Latitude --]
                [#if location.country || location.region]
                  [@customForm.input name="geoPosition.latitude" type="text" value=notApplicableText i18nkey="planning.activities.locations.latitude" showTitle=false disabled=true  /]
                [#else]
                  [#-- Geo position ID --]
                  <input type="hidden" name="otherLocationsSaved[${location_index}].geoPosition.id" value="${location.geoPosition.id?c}" >
                  [#-- Latitude --]
                  [@customForm.input name="otherLocationsSaved[${location_index}].geoPosition.latitude" value="${location.geoPosition.latitude}" type="text" i18nkey="planning.activities.locations.latitude" showTitle=false required=true  /]
                [/#if]
              </div>
              [#-- Longitude --]
              <div class="locationLongitude grid_2">
                [#if location.country || location.region]
                  [@customForm.input name="geoPosition.longitude" value=notApplicableText type="text" i18nkey="planning.activities.locations.longitude" showTitle=false disabled=true  /]
                [#else]
                  [@customForm.input name="otherLocationsSaved[${location_index}].geoPosition.longitude" value="${location.geoPosition.longitude}" type="text" i18nkey="planning.activities.locations.longitude" showTitle=false required=true  /]
                [/#if]
              </div>
              [#-- Name --]
              <div class="locationName grid_3">
                [#if location.country]
                  [@customForm.select name="countriesSaved[${location_index}].id" i18nkey="planning.activities.locations.level" listName="countries" keyFieldName="id" showTitle=false  displayFieldName="name" value="${location.id?int}" /]
                [#elseif location.region]
                  [@customForm.select name="regionsSaved[${location_index}].id" i18nkey="planning.activities.locations.level" listName="regions" keyFieldName="id" showTitle=false  displayFieldName="name" value="${location.id?int}" /]
                [#else]
                  <input type="hidden" name="otherLocationsSaved[${location_index}].id" value="${location.id?int}">
                  [@customForm.input name="otherLocationsSaved[${location_index}].name" value="${location.name}" type="text" i18nkey="planning.activities.locations.notApplicable" required=true showTitle=false  /]
                [/#if]
              </div>
              [#if saveable]
                <img class="removeButton" src="${baseUrl}/images/global/icon-remove.png" />
              [/#if]
            </div> 
          [/#list]
        [#else]
          [#-- Additional message for users without privileges. --]
          [#if !saveable]
            <p>[@s.text name="planning.activities.locations.empty" /]</p>
          [/#if]
        [/#if]
      </div>
      [#if saveable]
        <div id="addLocationBlock" class="addLink">
          <a href="" id="addLocationLink" class="addLocation addButton" >[@s.text name="planning.activities.locations.addLocation" /]</a>
        </div>
      [/#if]
    </div> 
    
    [#-- Only the user with enough privileges to save can upload the file --]
    [#if saveable]
      [#-- File upload --]
      <p id="addPartnerText" class="helpMessage">
        [@s.text name="planning.activities.locations.uploadMessage" /]
        <a id="fileBrowserLauncher" href=""> [@s.text name="planning.activities.locations.uploadMessageLink" /]</a>       
      </p>
      <div style="display:none">
        [@customForm.input name="excelTemplate" type="file" i18nkey="reporting.caseStudies.image" /]
      </div>
    
      <!-- internal parameter -->
      <input type="hidden" name="activityID" value="${activity.id?c}">
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
    [/#if]
  </article>
  [/@s.form]
</section>
[#-- Location Existing Template --]
<div id="locationTemplateExisting" class="row borderBox clearfix" style="display:none;">
  <div class="locationIndex ">
    <strong>?.</strong>
  </div>
  <div class="locationLevel grid_2 ">
    [@customForm.select name="type.id" i18nkey="planning.locations.level" listName="locationTypes" keyFieldName="id"  displayFieldName="name" showTitle=false /] 
  </div>
  <div class="locationLatitude grid_2">
    <div class="input">
      [#-- Geo position ID --]
      <input type="hidden" name="geoPosition.id" value="-1" >
      [@customForm.input name="geoPosition.latitude" value=notApplicableText type="text" i18nkey="planning.locations.longitude" showTitle=false disabled=true  /]
    </div>
  </div>
  <div class="locationLongitude grid_2">
    <div class="input">
      [@customForm.input name="geoPosition.longitude" value=notApplicableText type="text" i18nkey="planning.locations.longitude" showTitle=false disabled=true  /]
    </div>
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
[#-- Input Template for name --]
<div id="inputNameTemplate" style="display:none">
  <input type="hidden" name="].id" value="-1">
  [@customForm.input name="name" type="text" i18nkey="planning.locations.notApplicable" required=true showTitle=false  /]
</div>
[#--  Not applicable Text --]
<input type="hidden" id="notApplicableText" value="[@s.text name="planning.locations.notApplicable" /]">

[#include "/WEB-INF/global/pages/footer.ftl"]