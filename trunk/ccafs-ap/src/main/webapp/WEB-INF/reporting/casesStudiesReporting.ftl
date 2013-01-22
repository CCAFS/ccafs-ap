[#ftl]
[#assign title = "Case Studies" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/reporting/casesStudiesReporting.js"] /]
[#assign customCSS = ["${baseUrl}/css/reporting/casesStudiesReporting.css"] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "caseStudies" /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
  <section >
    [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]  
    <article class="halfContent">
      [@s.form action="casesStudies" enctype="multipart/form-data"]
        <h1>
          [@s.text name="reporting.casesStudies.casesStudies" /] - [#-- ${activityLeaderAcronym} --] 
        </h1>
        
        <div id="casesStudiesBlock">
        [#-- Saved cases studies --]        
        [#list casesStudies as caseStudy]
          <div id="caseStudy-${caseStudy_index}" class="caseStudy">
            [#-- CaseStudy identifier --]
            <input name="casesStudies[${caseStudy_index}].id" type="hidden" value="${caseStudy.id}">
          
            [#-- Remove link --]
            <div class="removeLink">
              <a id="removeCaseStudy-${caseStudy_index}" href="" class="removeCaseStudy">
                Remove case studies
              </a>
            </div>
          
            [#-- Title --]
            <div class="fullBlock">
              [@customForm.input name="casesStudies[${caseStudy_index}].title" type="text" i18nkey="reporting.casesStudies.title" /]
            </div>
            
            [#-- Type --]
            [#-- This section is missing because there is not a associated table in the db --]
            
            [#-- Author --]
            <div class="halfPartBlock">
              [@customForm.input name="casesStudies[${caseStudy_index}].author" type="text" i18nkey="reporting.casesStudies.author" /]              
            </div>
            
            [#-- Date --]
            <div class="halfPartBlock">
              [@customForm.input name="casesStudies[${caseStudy_index}].date" type="text" i18nkey="reporting.casesStudies.date" /]
            </div>
            
            [#-- Countries --]
            <div class="halfPartBlock countriesBlock">
              [@customForm.select name="casesStudies[${caseStudy_index}].countries" label="" i18nkey="reporting.casesStudies.country" listName="countriesList" keyFieldName="id"  displayFieldName="name" value="casesStudies[${caseStudy_index}].countriesIds" multiple=true /]              
            </div>
            
            [#-- Photo url --]
            <div class="halfPartBlock photoBlock">
              [#if caseStudy.photoFileName??]
                <div id="photo">
                  <img src="${baseUrl}/${photoPath}${caseStudy.photoFileName}" width="100%">
                </div>
                <div>
                  [@customForm.input name="casesStudies[${caseStudy_index}].photo" type="file" i18nkey="reporting.casesStudies.photo" /]
                </div>
              [#else]
                <div id="casesStudies[${caseStudy_index}].photo"></div>
                <div>
                  [@customForm.input name="casesStudies[${caseStudy_index}].photo" type="file" i18nkey="reporting.casesStudies.photo" /]
                </div>    
              [/#if]
            </div>
            
            [#-- Keywords --]
            <div class="halfPartBlock">
              [@customForm.input name="casesStudies[${caseStudy_index}].keywords" type="text" i18nkey="reporting.casesStudies.keywords" /]
            </div>
            
            
            [#-- Objectives --]
            <div class="fullBlock">
              [@customForm.textArea name="casesStudies[${caseStudy_index}].objectives" i18nkey="reporting.casesStudies.objectives" /]
            </div>
            
            [#-- Description --]
            <div class="fullBlock">
              [@customForm.textArea name="casesStudies[${caseStudy_index}].description" i18nkey="reporting.casesStudies.descripition" /]
            </div>
            
            [#-- Result --]
            <div class="fullBlock">
              [@customForm.textArea name="casesStudies[${caseStudy_index}].results" i18nkey="reporting.casesStudies.results" /]
            </div>
            
            [#-- Partners --]
            <div class="fullBlock">
              [@customForm.textArea name="casesStudies[${caseStudy_index}].partners" i18nkey="reporting.casesStudies.partners" /]
            </div>
            
            [#-- Links / resources --]
            <div class="fullBlock">
              [@customForm.textArea name="casesStudies[${caseStudy_index}].links" i18nkey="reporting.casesStudies.links" /]
            </div>
            
            [#-- separator --]
            <hr />
          </div>
        [/#list]
        
        
          [#-- In this section appear the new case studies after press the add link --]
          
          <div id="addCaseStudiesBlock">
            <a href="" class="addCaseStudies">Add Case Studies </a>
          </div>
        </div>
        
        [#-- Case Study Template --]
        <div id="template">
          <div id="caseStudy-999" class="caseStudy">
            [#-- CaseStudy identifier --]
            <input name="id" type="hidden" value="0">
            
            [#-- Remove link --]
            <div class="removeLink">
              <a id="removeCaseStudy-999" href="" class="removeCaseStudy">
                Remove case studies
              </a>
            </div>
          
            [#-- Title --]
            <div class="fullBlock">
              [@customForm.input name="title" type="text" i18nkey="reporting.casesStudies.title" /]
            </div>
            
            [#-- Type --]
            [#-- This section is missing because there is not a associated table in the db --]
            
            [#-- Author --]
            <div class="halfPartBlock">
              [@customForm.input name="author" type="text" i18nkey="reporting.casesStudies.author" /]              
            </div>
            
            [#-- Date --]
            <div class="halfPartBlock">
              [@customForm.input name="date" type="text" i18nkey="reporting.casesStudies.date" /]
            </div>            
            
            [#-- Countries --]
            <div class="halfPartBlock countriesBlock">
              [@customForm.select name="countries" label="" i18nkey="reporting.casesStudies.country" listName="countriesList" keyFieldName="id"  displayFieldName="name" value="" multiple=true /]              
            </div>
            
            [#-- Photo url --]
            <div class="halfPartBlock photoBlock">
              <div id="photo"></div>
              <div>
                [@customForm.input name="photo" type="file" i18nkey="reporting.casesStudies.photo" /]
              </div>
            </div>
            
            [#-- Keywords --]
            <div class="halfPartBlock">
              [@customForm.input name="keywords" type="text" i18nkey="reporting.casesStudies.keywords" /]
            </div>            
            
            [#-- Objectives --]
            <div class="fullBlock">
              [@customForm.textArea name="objectives" i18nkey="reporting.casesStudies.objectives" /]
            </div>
            
            [#-- Description --]
            <div class="fullBlock">
              [@customForm.textArea name="description" i18nkey="reporting.casesStudies.descripition" /]
            </div>
            
            [#-- Result --]
            <div class="fullBlock">
              [@customForm.textArea name="results" i18nkey="reporting.casesStudies.results" /]
            </div>
            
            [#-- Partners --]
            <div class="fullBlock">
              [@customForm.textArea name="partners" i18nkey="reporting.casesStudies.partners" /]
            </div>
            
            [#-- Links / resources --]
            <div class="fullBlock">
              [@customForm.textArea name="links" i18nkey="reporting.casesStudies.links" /]
            </div>
            
            [#-- separator --]
            <hr />
          </div>
        </div>
        [#-- Here endes Case Study Template --]
        [@s.submit type="button" name="save"]SAVE[/@s.submit]
      [/@s.form]
    </article>
  </section>
[#include "/WEB-INF/global/pages/footer.ftl"]