[#ftl]
<?xml version="1.0" encoding="UTF8"?>
[#escape x as x?xml]
<activities>
  [#list activities as activity]
    <activity id="${activity.id}">
      <id>${activity.id}</id>
      <publicURL>${baseUrl}/activity.do?id=${activity.id}</publicURL>
      <isCommissioned>${activity.commissioned?string}</isCommissioned>
      <title><![CDATA[${activity.title}]]></title>      
      <description><![CDATA[[#if activity.description?has_content]${activity.description}[/#if]]]></description>
      <startDate>[#if activity.startDate?has_content]${activity.startDate?string("MM/dd/yyyy")}[/#if]</startDate>
      <endDate>[#if activity.endDate?has_content]${activity.endDate?string("MM/dd/yyyy")}[/#if]</endDate>
      <milestone>${activity.milestone.code}</milestone>
      <leader>
        <name>${activity.leader.name}</name>
        <acronym>${activity.leader.acronym}</acronym>
      </leader>
      <budget>
        <usd>${activity.budget.usd}</usd>
      </budget>
      <contactPersons>
        [#if activity.contactPersons?has_content]
          [#list activity.contactPersons as cp]
            <contactPerson>
              <name>${cp.name}</name>
              <email>[#if cp.email?has_content]${cp.email}[/#if]</email>
            </contactPerson>
          [/#list]
        [/#if]
      </contactPersons>
      <locations>
        <isGlobal>${activity.global?string}</isGlobal>
        <location>
          <countryLocations>
            [#if activity.countries?has_content]
              [#list activity.countries as country]          
                <country>
                  <iso2>${country.id}</iso2>
                  <name>${country.name}</name>
                </country>
              [/#list]
            [/#if]
          </countryLocations>
          <ccafsLocations>
            [#if activity.bsLocations?has_content]
              [#list activity.bsLocations as ccafsSite]
                <ccafsSite>
                  <country>
                    <iso2>${ccafsSite.country.id}</iso2>
                    <name>${ccafsSite.country.name}</name>
                  </country>
                  <name>${ccafsSite.name}</name>
                  <latitude>${ccafsSite.latitude}</latitude>
                  <longitude>${ccafsSite.longitud}</longitude>
                </ccafsSite>
              [/#list]
            [/#if]
          </ccafsLocations>
          <otherSites>
            [#if activity.otherLocations?has_content]
              [#list activity.otherLocations as otherLocation]
                <otherSite>
                  <country>
                    <iso2>${otherLocation.country.id}</iso2>
                    <name>${otherLocation.country.name}</name>
                  </country>
                  <name>[#if otherLocation.name?has_content]${otherLocation.name}[/#if]</name>
                  <latitude>[#if otherLocation.latitude?has_content]${otherLocation.latitude}[/#if]</latitude>
                  <longitude>[#if otherLocation.longitude?has_content]${otherLocation.longitude}[/#if]</longitude>
                </otherSite>
              [/#list]
            [/#if]
          </otherSites>
        </location>
      </locations>
      <keywords>
        [#if activity.keywords?has_content]
          [#list activity.keywords as keyword]
            <keyword>
              [#if keyword.other?has_content]
                ${keyword.other}
              [#else]
                ${keyword.keyword.name}
              [/#if]
            </keyword>
          [/#list]
        [/#if]
      </keywords>
      <partners>
        [#if activity.activityPartners?has_content]
          [#list activity.activityPartners as activityPartner]
            <partner>
              <name>${activityPartner.partner.name}</name>
              <acronym>[#if activityPartner.partner.acronym?has_content]${activityPartner.partner.acronym}[/#if]</acronym>
              <country>
                <iso2>${activityPartner.partner.country.id}</iso2>
                <name>${activityPartner.partner.country.name}</name>
              </country>
              <contactPerson>
                <name>[#if activityPartner.contactName?has_content]${activityPartner.contactName}[/#if]</name>
                <email>[#if activityPartner.contactEmail?has_content]${activityPartner.contactEmail}[/#if]</email>
              </contactPerson>
            </partner>
          [/#list]
        [/#if]
      </partners>
    </activity>
  [/#list]
</activities>
[/#escape]