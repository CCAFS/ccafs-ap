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
      ${activity.leader.acronym}: [@s.text name="planning.mainInformation.activity" /] ${activity.activityId}
    </h1>
    
    [#-- Activity identifier --]
    <input name="activityID" value="${activity.id?c}" type="hidden"/>
    [#-- Hidden values --]
    <input id="countriesSelectDefault" value="[@s.text name="planning.locations.country.default" /]" type="hidden"/>
    
    [#-- Is global --]
    <div class="halfPartBlock">
      [@customForm.checkbox  name="activity.global" i18nkey="planning.locations.global" checked=activity.global value="true" /]
    </div> 

    [#-- Regions --]
    <fieldset class="fullblock" id="regionsLocations" [#if activity.global]style="display:none"[/#if]>
      <legend> <h6> [@s.text name="planning.locations.regions" /] </h6> </legend>
      <div class="regions">
        <h6>[@s.text name="planning.locations.regions" /]</h6>
        <div class="checkboxGroup">
          [@s.fielderror cssClass="fieldError" fieldName="regionsSelected"/]          
          [@s.checkboxlist name="region" list="regions" listKey="id" listValue="name" value="activeRegions" cssClass="checkbox" /]
        </div>
      </div>
    </fieldset>
    
    [#-- Countries --]
    <div id="countryLocations">
      [#list regions as region]
        <fieldset class="thirdPartBlock " id="countriesForRegion-${region.id}" [#if activity.global || !activeRegions?contains(region.id?string)]style="display:none"[/#if]>
          <legend> <h6> [@s.text name="planning.locations.region${region.id}" /] [@s.text name="planning.locations.countries" /] </h6> </legend>
          [#-- All region checkbox --]
          <input type="checkbox" class="countriesForRegion" id="allCountriesForRegion-${region_index}" name="activity.regions" value="${region.id}" [#if activity.regionsIds?contains(region.id?string)]checked="checked"[/#if] title="[@s.text name="planning.locations.allCountries.help" /]" />
          <label for="">[@s.text name="planning.locations.allCountries" /]</label>
          
          [#-- If the region is selected, the countries selector is not shown --]
          [#assign disableCountries = activity.regionsIds?contains(region.id?string) /]
          [@customForm.select name="activity.countries" label="" i18nkey="planning.locations.countries" listName="getCountriesByRegion(${region.id})" keyFieldName="id"  displayFieldName="name" value="activity.getCountriesIdsByRegion(${region.id})" multiple=true className="countries" disabled=disableCountries /]
        </fieldset>
      [/#list]
    </div>
    
    [#-- Benchmark sites --]
    [#assign showBsLocations = activity.bsLocations?has_content || benchmarkSites?has_content]
    <fieldset class="fullblock" id="bsLocations" [#if activity.global || !showBsLocations]style="display:none"[/#if]>
      <legend> <h6> [@s.text name="planning.locations.benchmarkSites" /] </h6> </legend>
      <div class="benchmarkSites">
        <h6>[@s.text name="planning.locations.benchmarkSites" /]</h6>
        <div class="checkboxGroup">
          [@s.fielderror cssClass="fieldError" fieldName="activity.bsLocations"/]

          [#if activity.bsLocations?has_content]
            [@s.checkboxlist name="activity.bsLocations" list="activity.bsLocations" listKey="id" listValue="name" value="activity.benchmarkSitesIds" cssClass="checkbox" /]
          [/#if]

          [#if benchmarkSites?has_content]
            [@s.checkboxlist name="activity.bsLocations" list="benchmarkSites" listKey="id" listValue="name" cssClass="checkbox" /]
          [/#if]
        </div>
      </div>
    </fieldset>
    
    [#-- Other locations --]    
    <fieldset class="locations" id="otherSites" [#if activity.global]style="display:none"[/#if]>
      <legend> <h6> [@s.text name="planning.locations.otherSites" /] </h6> </legend>
      
      [#if activity.otherLocations?has_content]
        [#list activity.otherLocations as otherSite]
          <div class="otherSite">
            [#-- Other site Identifier --]
            <input type="hidden" name="activity.otherLocations[${otherSite_index}].id" value="${otherSite.id?c}" />
            
            [#-- Remove link --]
            <div class="removeLink">
              <img src="${baseUrl}/images/global/icon-remove.png" />
              <a href="" class="removeOtherSite">
                [@s.text name="planning.locations.removeOtherSite" /]
              </a>
            </div>
            
            <div  class="fullBlock">
              <div  class="halfPartBlock">
                [@customForm.select name="activity.otherLocations[${otherSite_index}].country" label="" i18nkey="planning.locations.country" listName="countries" keyFieldName="id"  displayFieldName="name" value="activity.otherLocations[${otherSite_index}].country.id" className="countries" required=true /]
              </div>
              <div class="halfPartBlock">
                [@customForm.input name="activity.otherLocations[${otherSite_index}].details" type="text" i18nkey="planning.locations.details" required=true /]
              </div>
              [#-- Remove image --]
              <a href="#" >
                <img src="${baseUrl}/images/global/icon-help.png" alt="[@s.text name="planning.locations.otherSite.details.help" /]" title="[@s.text name="planning.locations.otherSite.details.help" /]" />
              </a>
            </div>
            <div  class="halfPartBlock">
              <div  class="halfPartBlock">
                [@customForm.input name="activity.otherLocations[${otherSite_index}].latitude" type="text" i18nkey="planning.locations.latitude" required=true /]
              </div>
              <div  class="halfPartBlock">
                [@customForm.input name="activity.otherLocations[${otherSite_index}].longitude" type="text" i18nkey="planning.locations.longitude" required=true /]
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
        
        [#-- Remove link --]
        <div class="removeLink">
          <img src="${baseUrl}/images/global/icon-remove.png" />
          <a href="" class="removeOtherSite">
            [@s.text name="planning.locations.removeOtherSite" /]
          </a>
        </div>
        
        <div  class="fullBlock">
          <div  class="halfPartBlock">
            [@customForm.select name="country" label="" i18nkey="planning.locations.country" listName="countries" keyFieldName="id"  displayFieldName="name" className="countries" required=true /]
          </div>
          <div class="halfPartBlock">
            [@customForm.input name="details" type="text" i18nkey="planning.locations.details" required=true /]
          </div>
          [#-- Remove image --]
          <a href="#" >
            <img src="${baseUrl}/images/global/icon-help.png" alt="[@s.text name="planning.locations.otherSite.details.help" /]" title="[@s.text name="planning.locations.otherSite.details.help" /]" />
          </a>
        </div>
            
        <div  class="halfPartBlock">
          <div  class="halfPartBlock">
            [@customForm.input name="latitude" type="text" i18nkey="planning.locations.latitude" required=true /]
          </div>
          <div  class="halfPartBlock">
            [@customForm.input name="longitude" type="text" i18nkey="planning.locations.longitude" required=true /]
          </div>
        </div>
        <div class="halfPartBlock">
          <a id="geoLocationLink" class="popup" href="[@s.url action='selectLocation'] [@s.param name='otherSiteID'][/@s.param][/@s.url]"> [@s.text name="planning.locations.selectOtherSite" /] </a>
        </div>
        [#-- Separator --]
        <hr/>
      </div>
    </div>
    
    [#-- Only the owner of the activity can see the action buttons --]
    [#if activity.leader.id == currentUser.leader.id && canSubmit]
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
    [/#if]
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]