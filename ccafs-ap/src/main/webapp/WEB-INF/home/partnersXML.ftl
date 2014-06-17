[#ftl]
[#--
 
 * This file is part of CCAFS Planning and Reporting Platform.
 *
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 *
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see 
 * <http://www.gnu.org/licenses/>
  
--]

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
    </partner>
  [/#list]
</partners>
[/#escape]