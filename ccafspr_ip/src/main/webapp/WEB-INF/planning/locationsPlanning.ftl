[#ftl]
[#assign title = "Activity locations" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js"] /]
[#assign currentSection = "planning" /]
[#assign currentStage = "locations" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.locations.help" /] </p>
  </div>
  [#include "/WEB-INF/planning/activityPlanningSubMenu.ftl" /]
  
  [@s.form action="locations" cssClass="pure-form"]  
  <article class="halfContent" id="mainInformation">
    <h1 class="contentTitle">
    [@s.text name="planning.locations.title" /] 
    </h1> 
    
    <div id="locationsBlock" class="borderBox">
      <div id="map">
      </div> 
      <div id="fields">
        [#if activity.locations?has_content]
          [#assign notApplicableText] [@s.text name="planning.locations.notApplicable" /] [/#assign]
          [#list activity.locations as location]
            [#if location.otherLocation]
              [#assign location]
                
              [/#assign]
            [/#if]
            <div id="location-${location_index}" class="location">
              [#-- Type/Level --]
              [#if location.region]
                [@customForm.select name="activity.locations[${location_index}].type.id" i18nkey="planning.locations.level" listName="locationTypes" keyFieldName="id"  displayFieldName="name" value="${regionTypeID}"/]
              [#elseif location.region]
                [@customForm.select name="activity.locations[${location_index}].type.id" i18nkey="planning.locations.level" listName="locationTypes" keyFieldName="id"  displayFieldName="name" value="${countryTypeID}"/]
              [#else]
                [@customForm.select name="activity.locations[${location_index}].type.id" i18nkey="planning.locations.level" listName="locationTypes" keyFieldName="id"  displayFieldName="name" value="${location.type.id}"/]
              [/#if]

              [#-- Latitude --]
              [#if location.country || location.region]
                [@customForm.input name="latitude" type="text" value=notApplicableText i18nkey="planning.locations.latitude" disabled=true  /]
              [#else]
                [@customForm.input name="longitude" type="text" i18nkey="planning.locations.latitude" required=true  /]
              [/#if]

              [#-- Longitude --]
              [#if location.country || location.region]
                [@customForm.input name="" value=notApplicableText type="text" i18nkey="planning.locations.latitude" disabled=true  /]
              [#else]
                [@customForm.input name="activity.locations[${location_index}].geoposition" type="text" i18nkey="planning.locations.latitude" required=true  /]
              [/#if]

              [#-- Name --]
              [#if location.country]
                [@customForm.select name="activity.locations[${location_index}].name" i18nkey="planning.locations.level" listName="countries" keyFieldName="id"  displayFieldName="name" value="${location.id?int}" /]
              [#elseif location.region]
                [@customForm.select name="activity.locations[${location_index}].name" i18nkey="planning.locations.level" listName="regions" keyFieldName="id"  displayFieldName="name" /]
              [#else]
                [@customForm.input name="activity.locations[${location_index}].name" type="text" i18nkey="planning.locations.notApplicable" required=true  /]
              [/#if]
              
            </div>
          [/#list]
        [/#if]
      </div> 
    </div> 
    
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
     
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]