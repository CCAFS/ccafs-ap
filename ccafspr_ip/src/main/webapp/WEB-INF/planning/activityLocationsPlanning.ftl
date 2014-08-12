[#ftl]
[#assign title = "Activity locations" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/activityLocationPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "activities" /]
[#assign currentStage = "activityLocations" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.activities.locations.help" /] </p>
  </div>
  [#include "/WEB-INF/planning/activityPlanningSubMenu.ftl" /]
  
  [@s.form action="activityLocations" cssClass="pure-form"]  
  <article class="halfContent" id="activityLocations">
    <h1 class="contentTitle">
    [@s.text name="planning.activities.locations.title" /] 
    </h1> 
    
    <div id="locationsBlock" class="clearfix">
      <div id="map">  </div> 
      <div id="fields">
        [#if activity.locations?has_content]
          [#assign notApplicableText] [@s.text name="planning.activities.locations.notApplicable" /] [/#assign]
          <div id="location-head" class="row clearfix">
              <div id="locationIndex-head" class="locationIndex ">
                <strong># </strong>
              </div>
              <div id="locationLevel-head" class="locationLevel grid_2 ">
                [#-- Type/Level --]
                <strong>[@s.text name="planning.activities.locations.level" /]</strong>
              </div>
              <div id="locationLatitude-head" class="locationLatitude grid_2">
                [#-- Latitude --]
                <strong>[@s.text name="planning.activities.locations.latitude" /]</strong>
              </div>
              <div id="locationLongitude-head" class="locationLatitude grid_2">
                [#-- Longitude --]
                <strong>[@s.text name="planning.activities.locations.longitude" /]</strong>
              </div>
              <div id="locationName-head" class="locationName grid_3">
                [#-- Name --]
                <strong>[@s.text name="planning.activities.locations.name" /]</strong>
              </div>
            </div> 
          [#list activity.locations as location]
            [#if location.otherLocation][#assign location=location.otherLocationInstance /][/#if]
            <div id="location-${location_index}" class="location row borderBox clearfix">
              <div class="locationIndex ">
                <strong>${location_index+1}.</strong>
              </div>
              <div class="locationLevel grid_2 ">
                [#-- Type/Level --]
                [#if location.region]
                  [@customForm.select name="activity.locations[${location_index}].type.id" i18nkey="planning.activities.locations.level" listName="locationTypes" keyFieldName="id"  displayFieldName="name" showTitle=false value="${regionTypeID}"/]
                [#elseif location.country]
                  [@customForm.select name="activity.locations[${location_index}].type.id" i18nkey="planning.activities.locations.level" listName="locationTypes" keyFieldName="id"  displayFieldName="name" showTitle=false value="${countryTypeID}"/]
                [#else]
                  [@customForm.select name="activity.locations[${location_index}].type.id" i18nkey="planning.activities.locations.level" listName="locationTypes" keyFieldName="id"  displayFieldName="name" showTitle=false value="${location.type.id}"/]
                [/#if]
              </div>
              <div class="locationLatitude grid_2">
                [#-- Latitude --]
                [#if location.country || location.region]
                  [@customForm.input name="geoPosition.latitude" type="text" value=notApplicableText i18nkey="planning.activities.locations.latitude" showTitle=false disabled=true  /]
                [#else]
                  [@customForm.input name="activity.locations[${location_index}].geoPosition.latitude" type="text" i18nkey="planning.activities.locations.latitude" showTitle=false required=true  /]
                [/#if]
              </div>
              <div class="locationLongitude grid_2">
                [#-- Longitude --]
                [#if location.country || location.region]
                  [@customForm.input name="geoPosition.longitude" value=notApplicableText type="text" i18nkey="planning.activities.locations.longitude" showTitle=false disabled=true  /]
                [#else]
                  [@customForm.input name="activity.locations[${location_index}].geoPosition.longitude" type="text" i18nkey="planning.activities.locations.longitude" showTitle=false required=true  /]
                [/#if]
              </div>
              <div class="locationName grid_3">
                [#-- Name --]
                [#if location.country]
                  [@customForm.select name="activity.locations[${location_index}].id" i18nkey="planning.activities.locations.level" listName="countries" keyFieldName="id" showTitle=false  displayFieldName="name" value="${location.id?int}" /]
                [#elseif location.region]
                  [@customForm.select name="activity.locations[${location_index}].id" i18nkey="planning.activities.locations.level" listName="regions" keyFieldName="id" showTitle=false  displayFieldName="name" value="${location.id?int}" /]
                [#else]
                  <input type="hidden" name="activity.locations[${location_index}].id" value="${location.id?int}">
                  [@customForm.input name="activity.locations[${location_index}].name" type="text" i18nkey="planning.activities.locations.notApplicable" required=true showTitle=false  /]
                [/#if]
              </div>
              <img class="removeButton" src="${baseUrl}/images/global/icon-remove.png" />
            </div> 
          [/#list]
        [/#if]
      </div>
      <div id="addLocationBlock" class="addLink">
        <img src="${baseUrl}/images/global/icon-add.png" />
        <a href="" id="addLocationLink" class="addLocation" >[@s.text name="planning.activities.locations.addLocation" /]</a>
      </div>  
    </div> 
    <!-- internal parameter -->
    <input type="hidden" id="activityID" value="${activity.id}">
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
    <p id="addPartnerText" class="helpMessage">
      [@s.text name="planning.activities.locations.uploadMessage" /]
      <a class="popup" href="[@s.url action='partnerSave'][/@s.url]">
        [@s.text name="planning.activities.locations.uploadMessageLink" /]
      </a>       
    </p>
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