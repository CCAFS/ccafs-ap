[#ftl]
<?xml version="1.0" encoding="UTF8"?>
[#escape x as x?xml]
<partners>
  [#list partners as partner]
    <partner >
      <id>${partner.id}</id>
      <name>${partner.name}</name>
      <acronym>[#if partner.acronym?has_content]${partner.acronym}[/#if]</acronym>
      <website>[#if partner.website?has_content]${partner.website}[/#if]</website>
      <location>
        <country>
          <iso2>${partner.country.id}</iso2>
          <name>${partner.country.name}</name>
        </country>
        <region>
          <id>${partner.country.region.id}</id>
          <name>${partner.country.region.name}</name>
        </region>
        <city>[#if partner.city?has_content]${partner.city}[/#if]</city>
      </location>
      <type>
        <id>${partner.type.id}</id>
        <name>${partner.type.name}</name>
        <acronym>${partner.type.acronym}</acronym>
      </type>
      <themes>
        [#if themesByPartner.get(partner.id)?has_content]
          [#assign themes = themesByPartner.get(partner.id) /]
          [#list themes as theme]
            <theme>
              <code>${theme.code}</code>
              <description>${theme.description}</description>
            </theme>
          [/#list]
        [#else]
          <theme>
            <code></code>
            <description></description>
          </theme>
        [/#if]
      </themes>
      <isActive>${action.isPartnerActive(partner.id)?string}</isActive>
    </partner>
  [/#list]
</partners>
[/#escape]