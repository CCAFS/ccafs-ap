[#ftl]
[#assign title = "Publications Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/reporting/publicationsReporting.js", "${baseUrl}/js/global/utils.js"] /]
[#-- assign customCSS = ["${baseUrl}/css/reporting/partnersReporting.css"] / --]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "publications" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

[#macro publicationSection]
  [#list publications as publication]
    <div id="publication-${publication_index}" class="publication">
      
      [#-- Remove link for a publications --]
      <div class="removeLink">
        <a id="removePublication-${publication_index}" href="" class="removePublication">Remove publication</a>
      </div>
      
      [#-- Publication identifier --]
      <input type="hidden" name="publications[${publication_index}].id" value="${publication.id?c}" />
      
      [#-- Publication type --]
      <div class="halfPartBlock">
        [@customForm.select name="publications[${publication_index}].type" label="" i18nkey="reporting.publications.type.name" listName="publicationTypes" keyFieldName="id"  displayFieldName="name" /]
      </div>
      
      [#-- Publication identifier --]
      <div class="halfPartBlock">
        [@customForm.input name="publications[${publication_index}].identifier" type="text" i18nkey="reporting.publications.identifier" /]
      </div>
      
      [#-- Publication ciation --]
      <div class="fullBlock">
        [@customForm.textArea name="publications[${publication_index}].citation" i18nkey="reporting.publications.citation" /]
      </div> 
    </div> <!-- End publications-${publication_index} -->
    <hr />
  [/#list]
[/#macro]

<section class="content">
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  [@s.form action="publications!save"]
    <article class="halfContent">
      <h1 class="contentTitle">
        [@s.text name="reporting.publications.publication" /] - ${currentUser.leader.acronym} 
      </h1>
      <div id="items">      
        <fieldset id="publicationGroup">
          <legend>[@s.text name="reporting.publications.publication" /]</legend>
          [@publicationSection /]
        </fieldset>
        <div>
          <a href="" class="addPublication">Add new publication</a>
        </div>   
      </div>
      
      <!-- PUBLICATION TEMPLATE -->
      <div id="template">
        <div id="publication-9999" class="publication" style="display: none;">      
          [#-- remove link --]
          <div class="removeLink">
            <a id="removePublication-9999" href="" class="removePublication">Remove publication</a>
          </div>
          
          [#-- Publication identifier --]
          <input type="hidden" name="id" value="-1" />
          
          [#-- Publication type --]
          <div class="halfPartBlock">
            [@customForm.select name="type" label="" i18nkey="reporting.publications.type.name" listName="publicationTypes" keyFieldName="id"  displayFieldName="name" /]
          </div>
      
          [#-- Publication identifier --]
          <div class="halfPartBlock">
            [@customForm.input name="identifier" type="text" i18nkey="reporting.publications.identifier" /]
          </div>
      
          [#-- Publication ciation --]
          <div class="fullBlock">
            [@customForm.textArea name="citation" i18nkey="reporting.publications.citation" /]
          </div>
        </div> <!-- End publication template -->
      </div> <!-- End template -->
      
      [@s.submit type="button" name="save"]SAVE[/@s.submit]
    </article>
  [/@s.form]
 
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]