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

[#assign title = "Publications Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/reporting/publicationsReporting.js", "${baseUrl}/js/global/utils.js"] /]
[#-- assign customCSS = ["${baseUrl}/css/reporting/partnersReporting.css"] / --]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "publications" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

[#macro publicationSection]
  [#list publications as publication]
    <div id="publication-${publication_index}" class="publication">

      [#-- Item index --]
      <div class="itemIndex">
        [@s.text name="reporting.publications.publication" /] ${publication_index +1}
      </div>

      [#-- Remove link for a publications --]
      <div class="removeLink">
        <img src="${baseUrl}/images/global/icon-remove.png" />
        <a id="removePublication-${publication_index}" href="" class="removePublication">[@s.text name="reporting.publications.removePublication" /]</a>
      </div>

      [#-- Publication identifier --]
      <input type="hidden" name="publications[${publication_index}].id" value="${publication.id?c}" />
      
      [#-- Publication type --]
      <div class="halfPartBlock">
        [@customForm.select name="publications[${publication_index}].type" label="" i18nkey="reporting.publications.type.name" listName="publicationTypes" keyFieldName="id"  displayFieldName="name" /]
      </div>

      [#-- Publication identifier --]
      <div class="halfPartBlock">
        [@customForm.input name="publications[${publication_index}].identifier" type="text" i18nkey="reporting.publications.identifier" help="reporting.publications.identifier.help" /]
      </div>

      [#-- Publication access --]
      [#if publicationTypeAccessNeed?seq_contains(publication.type.id)]
        <div class="fullBlock accessType">
      [#else]
        <div class="fullBlock accessType" style="display: none;">
      [/#if]
        [@customForm.radioButtonGroup name="publications[${publication_index}].access" label="" i18nkey="reporting.publications.access" listName="publicationAccessList" keyFieldName="id" displayFieldName="name" value="${publication.access.id}" help="reporting.publications.access.help"/]
      </div>

      [#-- Indicators for journal articles --]
      [#if publicationTypeAccessNeed?seq_contains(publication.type.id)]
        <div class="fullBlock journalArticlesIndicators" >
      [#else]
        <div class="fullBlock journalArticlesIndicators" style="display: none;">
      [/#if]
          <h6>[@s.text name="reporting.publications.indicators" /]</h6>
          <div>
            <div class="fullBlock">
              [@customForm.checkbox name="publications[${publication_index}].isiPublication" i18nkey="reporting.publications.isiPublication" checked=publication.isiPublication value="true" /]
            </div>
            <div class="fullBlock">
              [@customForm.checkbox name="publications[${publication_index}].narsCoauthor" i18nkey="reporting.publications.narsCoauthor" checked=publication.narsCoauthor value="true" /]
            </div>
            <div class="fullBlock">
              [@customForm.checkbox name="publications[${publication_index}].earthSystemCoauthor" i18nkey="reporting.publications.earthSystemCoauthor" checked=publication.earthSystemCoauthor value="true" /]
            </div>
          </div>
        </div>

      [#-- Publication citation --]
      <div class="fullBlock">
        [@customForm.textArea name="publications[${publication_index}].citation" i18nkey="reporting.publications.citation" help="reporting.publications.citation.help" /]
      </div> 

      [#-- CCAFS Acknowledge --]
      <div class="fullBlock" id="ccafsAcknowledge" >
        [@customForm.checkbox name="publications[${publication_index}].ccafsAcknowledge" i18nkey="reporting.publications.ccafsAcknowledge" checked=publication.ccafsAcknowledge value="true" /]
      </div>

      [#-- Publication themes related --]
      <div class="fullBlock">
        <h6>[@s.text name="reporting.publications.themeRelated" /]</h6>
        <div class="checkboxGroup">
          [@s.fielderror cssClass="fieldError" fieldName="publications[${publication_index}].themeRelated"/]
          [@s.checkboxlist name="publications[${publication_index}].relatedThemes" list="publicationThemeList" value="publications[${publication_index}].relatedThemesIds" cssClass="checkbox" /]
        </div>
      </div>

      [#-- Publication file url --]
      <div class="fullBlock" >
        [@customForm.input name="publications[${publication_index}].fileUrl" i18nkey="reporting.publications.fileUrl" help="reporting.publications.fileUrl.help" /]
      </div> 
    </div> <!-- End publications-${publication_index} -->
    <hr />
  [/#list]
[/#macro]

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="reporting.publications.help" /]</p>
  </div>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  [@s.form action="publications"]
    <article class="halfContent">
      <h1 class="contentTitle">
        [@s.text name="reporting.publications" /] - ${currentUser.leader.acronym} 
      </h1>
      <div id="items">      
        <fieldset id="publicationGroup">

          [@publicationSection /]
          <div class="addLink">
            <img src="${baseUrl}/images/global/icon-add.png" />        
            <a href="" class="addPublication">[@s.text name="reporting.publications.addNewPublication" /]</a>
          </div>
        </fieldset>
      </div>

      <!-- PUBLICATION TEMPLATE -->
      <div id="template">
        <div id="publication-9999" class="publication" style="display: none;">
          [#-- Item index --]
          <div class="itemIndex">
            [@s.text name="reporting.publications.publication" /] 
          </div>

          [#-- remove link --]
          <div class="removeLink">
            <img src="${baseUrl}/images/global/icon-remove.png" />
            <a id="removePublication-9999" href="" class="removePublication">[@s.text name="reporting.publications.removePublication" /]</a>
          </div>

          [#-- Publication identifier --]
          <input type="hidden" name="id" value="-1" />

          [#-- Publication type --]
          <div class="halfPartBlock">
            [@customForm.select name="type" label="" i18nkey="reporting.publications.type.name" listName="publicationTypes" keyFieldName="id"  displayFieldName="name" /]
          </div>

          [#-- Publication identifier --]
          <div class="halfPartBlock">
            [@customForm.input name="identifier" type="text" i18nkey="reporting.publications.identifier" help="reporting.publications.identifier.help" /]
          </div>

          [#-- Publication access --]
          <div class="fullBlock accessType">
            [@customForm.radioButtonGroup name="access" label="" i18nkey="reporting.publications.access" listName="publicationAccessList" keyFieldName="id" displayFieldName="name" /]
          </div>

          [#-- Indicators for journal articles --]
          <div class="fullBlock journalArticlesIndicators">
            <h6>[@s.text name="reporting.publications.indicators" /]</h6>
            <div></div>      
            <div class="fullBlock">
              [@customForm.checkbox name="isiPublication" i18nkey="reporting.publications.isiPublication" /]
            </div>
            <div class="fullBlock">
              [@customForm.checkbox name="narsCoauthor" i18nkey="reporting.publications.narsCoauthor" /]
            </div>
            <div class="fullBlock">
              [@customForm.checkbox name="earthSystemCoauthor" i18nkey="reporting.publications.earthSystemCoauthor" /]
            </div>
          </div>
          
          [#-- Publication citation --]
          <div class="fullBlock">
            [@customForm.textArea name="citation" i18nkey="reporting.publications.citation" help="reporting.publications.citation.help" /]
          </div>
          
          [#-- CCAFS Acknowledge --]
          <div class="fullBlock" id="ccafsAcknowledge" >
            [@customForm.checkbox name="ccafsAcknowledge" i18nkey="reporting.publications.ccafsAcknowledge" value="true" /]
          </div>

          [#-- Publication themes related --]
          <div class="fullBlock">
            <h6>[@s.text name="reporting.publications.themeRelated" /]</h6>
            <div class="checkboxGroup">
              [@s.fielderror cssClass="fieldError" fieldName="themeRelated"/]
              [@s.checkboxlist name="relatedThemes" list="publicationThemeList" cssClass="checkbox" /]
            </div>
          </div>
          
          [#-- Publication file url --]
          <div class="fullBlock">
            [@customForm.input name="fileUrl" i18nkey="reporting.publications.fileUrl" help="reporting.publications.fileUrl.help" /]
          </div>
        </div> <!-- End publication template -->
      </div> <!-- End template -->
      
      [#if canSubmit]
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