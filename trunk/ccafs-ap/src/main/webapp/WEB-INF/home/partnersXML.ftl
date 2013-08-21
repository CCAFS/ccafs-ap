[#ftl]
<?xml version="1.0" encoding="UTF8"?>
[#escape x as x?xml]
<partners>
  [#list partners as partner]
    <partner id="${partner.id}">
      <id>${partner.id}</id>
      <name>${partner.name}</name>
      <acronym>[#if partner.acronym?has_content]${partner.acronym}[/#if]</acronym>
      <website>[#if partner.website?has_content]${partner.website}[/#if]</website>
      <location>
        <country>
          <iso2>${partner.country.id}</iso2>
          <name>${partner.country.name}</name>
        </country>
        <city>[#if partner.city?has_content]${partner.city}[/#if]</city>
      </location>
      <type>
        <id>${partner.type.id}</id>
        <name>${partner.type.name}</name>
        <acronym>${partner.type.acronym}</acronym>
      </type>      
    </partner>
  [/#list]
</partners>
[/#escape]