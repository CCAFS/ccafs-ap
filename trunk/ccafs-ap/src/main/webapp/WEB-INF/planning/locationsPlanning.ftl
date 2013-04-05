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
  [#include "/WEB-INF/global/pages/planning-secondary-menu.ftl" /]
  
  [@s.form action="locations"]  
  <article class="halfContent activityLocations">
    <h1 class="contentTitle">
      [@s.text name="planning.mainInformation.activity" /] ${activity.id} - [@s.text name="planning.locations" /] 
    </h1>
    
    [#-- Activity identifier --]
    <input name="activityID" value="${activity.id}" type="hidden"/>
    
    <div class="halfPartBlock">
      [@customForm.checkbox  name="activity.global" i18nkey="planning.locations.global" checked=activity.global /]
    </div>
    
    <fieldset class="fullblock" id="regionsLocations">
      <legend> <h6> [@s.text name="planning.locations.regions" /] </h6> </legend>
      <div class="regions">
        <h6>[@s.text name="planning.locations.regions" /]</h6>
        <div class="checkboxGroup">
          [@s.fielderror cssClass="fieldError" fieldName="regionsSelected"/]
          [@s.checkboxlist name="regionsSelected" list="regions" listKey="id" listValue="name" value="" cssClass="checkbox" /]
        </div>
      </div>
    </fieldset>
    
    <fieldset class="fullBlock locations" id="countryLocations">
      <legend> <h6> [@s.text name="planning.locations.countries" /] </h6> </legend>
      [@customForm.select name="activity.countries" label="" i18nkey="planning.locations.countries" listName="countries" keyFieldName="id"  displayFieldName="name" value="activity.countriesIds" multiple=true className="countries" /]
    </fieldset>
    
    <fieldset class="fullblock" id="bsLocations">
      <legend> <h6> [@s.text name="planning.locations.benchmarkSites" /] </h6> </legend>
      <div class="benchmarkSites">
        <h6>[@s.text name="planning.locations.benchmarkSites" /]</h6>
        <div class="checkboxGroup">
          [@s.fielderror cssClass="fieldError" fieldName="activity.bsLocations"/]
          [@s.checkboxlist name="activity.bsLocations" list="benchmarkSites" listKey="id" listValue="name" value="activity.benchmarkSitesIds" cssClass="checkbox" /]
        </div>
      </div>
    </fieldset>
    
    <fieldset class="fullBlock locations" id="otherSites">
      <legend> <h6> [@s.text name="planning.locations.otherSites" /] </h6> </legend>
      
      [#if activity.otherLocations?has_content]
        [#list activity.otherLocations as otherSite]
          <div class="otherSite">
            <div  class="halfPartBlock">
              [@customForm.select name="activity.otherLocations[${otherSite_index}].country" label="" i18nkey="planning.locations.country" listName="countries" keyFieldName="id"  displayFieldName="name" value="activity.otherLocations[${otherSite_index}].country.id" className="countries" /]
            </div>
            <div  class="halfPartBlock">
              <div  class="halfPartBlock">
                [@customForm.input name="activity.otherLocations[${otherSite_index}].latitude" type="text" i18nkey="planning.locations.latitude" /]
              </div>
              <div  class="halfPartBlock">
                [@customForm.input name="activity.otherLocations[${otherSite_index}].longitude" type="text" i18nkey="planning.locations.longitude" /]
              </div>
            </div>
            [#-- Remove image --]
            <a href="#" >
              <img src="${baseUrl}/images/global/icon-remove.png" class="removeOtherSite" />
            </a>
            <div class="halfPartBlock">
              [@customForm.input name="activity.otherLocations[${otherSite_index}].details" type="text" i18nkey="planning.locations.details" /]
            </div>
            <div class="halfPartBlock">
              <a class="popup" href="[@s.url action='selectLocation'] [@s.param name='otherSiteID']${otherSite_index}[/@s.param][/@s.url]"> [@s.text name="planning.locations.selectOtherSite" /] </a>
            </div>
            [#-- Separator --]
            <hr/>
          </div>
        [/#list]
      [#else]
          <div class="otherSite">
            <div  class="halfPartBlock">
              [@customForm.select name="activity.otherLocations[0].country" label="" i18nkey="planning.locations.country" listName="countries" keyFieldName="id"  displayFieldName="name" className="countries" /]
            </div>
            <div  class="halfPartBlock">
              <div  class="halfPartBlock">
                [@customForm.input name="activity.otherLocations[0].latitude" type="text" i18nkey="planning.locations.latitude" /]
              </div>
              <div  class="halfPartBlock">
                [@customForm.input name="activity.otherLocations[0].longitude" type="text" i18nkey="planning.locations.longitude" /]
              </div>
            </div>
            [#-- Remove image --]
            <a href="#" >
              <img src="${baseUrl}/images/global/icon-remove.png" class="removeOtherSite" />
            </a>
            <div class="halfPartBlock">
              [@customForm.input name="activity.otherLocations[0].details" type="text" i18nkey="planning.locations.details" /]
            </div>
            <div class="halfPartBlock">
              <a class="popup" href="[@s.url action='selectLocation'] [@s.param name='otherSiteID']0[/@s.param][/@s.url]"> [@s.text name="planning.locations.selectOtherSite" /] </a>
            </div>
            [#-- Separator --]
            <hr/>
          </div>
      [/#if]
      <div id="addOtherSitesBlock" class="addLink">
        <img src="${baseUrl}/images/global/icon-add.png" />
        <a href="" class="addOtherSite" >[@s.text name="planning.locations.addOtherSite" /]</a>
      </div>
    </fieldset>
    
    <div id="otherSiteTemplate" style="display:none;">
      <div class="otherSite">
        <div  class="halfPartBlock">
          [@customForm.select name="country" label="" i18nkey="planning.locations.country" listName="countries" keyFieldName="id"  displayFieldName="name" className="countries" /]
        </div>
        <div  class="halfPartBlock">
          <div  class="halfPartBlock">
            [@customForm.input name="latitude" type="text" i18nkey="planning.locations.latitude" /]
          </div>
          <div  class="halfPartBlock">
            [@customForm.input name="longitude" type="text" i18nkey="planning.locations.longitude" /]
          </div>
        </div>
        [#-- Remove image --]
        <a href="#" >
          <img src="${baseUrl}/images/global/icon-remove.png" class="removeOtherSite" />
        </a>
        <div class="halfPartBlock">
          [@customForm.input name="details" type="text" i18nkey="planning.locations.details" /]
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