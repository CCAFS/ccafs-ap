[#ftl]
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
      
      [#-- Publication ciation --]
      <div class="fullBlock">
        [@customForm.textArea name="publications[${publication_index}].citation" i18nkey="reporting.publications.citation" help="reporting.publications.citation.help" /]
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
      
          [#-- Publication ciation --]
          <div class="fullBlock">
            [@customForm.textArea name="citation" i18nkey="reporting.publications.citation" help="reporting.publications.citation.help" /]
          </div>
        </div> <!-- End publication template -->
      </div> <!-- End template -->
      
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
    </article>
  [/@s.form]
 
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]