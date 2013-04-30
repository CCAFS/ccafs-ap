[#ftl]
[#assign title = "Activity Locations Planning" /]
[#assign globalLibs = ["jquery", "noty", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/planning/locations.js", "${baseUrl}/js/global/utils.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "locations" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.locations.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/planning-secondary-menu.ftl" /]
  
  [@s.form action="locations"]  
  <article class="halfContent activityLocations">
    <h1 class="contentTitle">
      [@s.text name="planning.mainInformation.activity" /] ${activity.id} - [@s.text name="planning.locations" /] 
    </h1>
    
    [#-- Activity identifier --]
    <input name="activityID" value="${activity.id}" type="hidden"/>
    
    [#-- Is global --]
    <div class="halfPartBlock">
      [@customForm.checkbox  name="activity.global" i18nkey="planning.locations.global" checked=activity.global /]
    </div>
    
    [#-- Regions --]
    <fieldset class="fullblock" id="regionsLocations" [#if activity.global]style="display:none"[/#if]>
      <legend> <h6> [@s.text name="planning.locations.regions" /] </h6> </legend>
      <div class="regions">
        <h6>[@s.text name="planning.locations.regions" /]</h6>
        <div class="checkboxGroup">
          [@s.fielderror cssClass="fieldError" fieldName="regionsSelected"/]
          [@s.checkboxlist name="region" list="regions" listKey="id" listValue="name" value="regionsDisplayed" cssClass="checkbox" /]
        </div>
      </div>
    </fieldset>
    
    [#-- Countries --]
    <div>
      [#list regions as region]
        [#-- If there is a selected region show the countries option --]
        [#assign showRegion = regionsDisplayed((region_index+1)?int)?has_content /]
        <fieldset class="thirdPartBlock " id="countriesForRegion-${region_index+1}" [#if activity.global || !showRegion]style="display:none"[/#if]>
          <legend> <h6> [@s.text name="planning.locations.region${region_index+1}" /] [@s.text name="planning.locations.countries" /] </h6> </legend>
          [#-- All region checkbox --]
          <input type="checkbox" class="countriesForRegion" id="allCountriesForRegion-${region_index}" name="regionsSelected[${region_index}]" value="true" [#if regionsSelected(region_index)]checked="checked"[/#if] />
          <label for="">[@s.text name="planning.locations.allCountries" /]</label>
          
          [#-- If the region is selected, the countries selector is not shown --]
          [#assign displayCountries = !regionsSelected(region_index) /]
          [@customForm.select name="activity.countries" label="" i18nkey="planning.locations.countries" listName="getCountriesByRegion(${region_index+1 })" keyFieldName="id"  displayFieldName="name" value="activity.getCountriesIdsByRegion(${region_index + 1})" multiple=true className="countries" display=displayCountries /]
        </fieldset>
      [/#list]
    </div>
    
    [#-- Benchmark sites --]
    <fieldset class="fullblock" id="bsLocations" [#if activity.global]style="display:none"[/#if]>
      <legend> <h6> [@s.text name="planning.locations.benchmarkSites" /] </h6> </legend>
      <div class="benchmarkSites">
        <h6>[@s.text name="planning.locations.benchmarkSites" /]</h6>
        <div class="checkboxGroup">
          [@s.fielderror cssClass="fieldError" fieldName="activity.bsLocations"/]
          [@s.checkboxlist name="activity.bsLocations" list="benchmarkSites" listKey="id" listValue="name" value="activity.benchmarkSitesIds" cssClass="checkbox" /]
        </div>
      </div>
    </fieldset>
    
    [#-- Other locations --]    
    <fieldset class="fullBlock locations" id="otherSites" [#if activity.global]style="display:none"[/#if]>
      <legend> <h6> [@s.text name="planning.locations.otherSites" /] </h6> </legend>
      
      [#if activity.otherLocations?has_content]
        [#list activity.otherLocations as otherSite]
          <div class="otherSite">
            [#-- Other site Identifier --]
            <input type="hidden" name="activity.otherLocations[${otherSite_index}].id" value="${otherSite.id}" />
            
            <div  class="halfPartBlock">
              [@customForm.select name="activity.otherLocations[${otherSite_index}].country" label="" i18nkey="planning.locations.country" listName="countries" keyFieldName="id"  displayFieldName="name" value="activity.otherLocations[${otherSite_index}].country.id" className="countries" /]
            </div>
            <div class="halfPartBlock">
              [@customForm.input name="activity.otherLocations[${otherSite_index}].details" type="text" i18nkey="planning.locations.details" /]
            </div>
            [#-- Remove image --]
            <a href="#" >
              <img src="${baseUrl}/images/global/icon-remove.png" class="removeOtherSite" />
            </a>
            <div  class="halfPartBlock">
              <div  class="halfPartBlock">
                [@customForm.input name="activity.otherLocations[${otherSite_index}].latitude" type="text" i18nkey="planning.locations.latitude" /]
              </div>
              <div  class="halfPartBlock">
                [@customForm.input name="activity.otherLocations[${otherSite_index}].longitude" type="text" i18nkey="planning.locations.longitude" /]
              </div>
            </div>
            <div class="halfPartBlock">
              <a class="popup" href="[@s.url action='selectLocation'] [@s.param name='otherSiteID']${otherSite_index}[/@s.param][/@s.url]"> [@s.text name="planning.locations.selectOtherSite" /] </a>
            </div>
            [#-- Separator --]
            <hr/>
          </div>
        [/#list]
      [/#if]
      <div id="addOtherSitesBlock" class="addLink">
        <img src="${baseUrl}/images/global/icon-add.png" />
        <a href="" class="addOtherSite" >[@s.text name="planning.locations.addOtherSite" /]</a>
      </div>
    </fieldset>
    
    [#-- Other site template --]
    <div id="otherSiteTemplate" style="display:none;">
      <div class="otherSite">
        <input type="hidden" name="id" value="-1" />
        <div  class="halfPartBlock">
          [@customForm.select name="country" label="" i18nkey="planning.locations.country" listName="countries" keyFieldName="id"  displayFieldName="name" className="countries" /]
        </div>
        <div class="halfPartBlock">
          [@customForm.input name="details" type="text" i18nkey="planning.locations.details" /]
        </div>
        [#-- Remove image --]
        <a href="#" >
          <img src="${baseUrl}/images/global/icon-remove.png" class="removeOtherSite" />
        </a>
        <div  class="halfPartBlock">
          <div  class="halfPartBlock">
            [@customForm.input name="latitude" type="text" i18nkey="planning.locations.latitude" /]
          </div>
          <div  class="halfPartBlock">
            [@customForm.input name="longitude" type="text" i18nkey="planning.locations.longitude" /]
          </div>
        </div>
        <div class="halfPartBlock">
          <a id="geoLocationLink" class="popup" href="[@s.url action='selectLocation'] [@s.param name='otherSiteID'][/@s.param][/@s.url]"> [@s.text name="planning.locations.selectOtherSite" /] </a>
        </div>
        [#-- Separator --]
        <hr/>
      </div>
    </div>
    
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]