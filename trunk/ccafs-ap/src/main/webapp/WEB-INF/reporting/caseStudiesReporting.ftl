[#ftl]
[#assign title = "Case Studies" /]
[#assign globalLibs = ["jquery", "noty", "jqueryUI"] /]
[#assign customJS = ["${baseUrl}/js/reporting/caseStudiesReporting.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/jqueryUI/jquery-ui-1.9.2.custom.css", ""] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "caseStudies" /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
  <section class="content">
    [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
    
    [@s.form action="caseStudies" enctype="multipart/form-data"]  
    <article class="halfContent">      
        <h1>
          ${currentUser.leader.acronym} - [@s.text name="reporting.caseStudies.caseStudies" /]  
        </h1>
        <div id="caseStudiesBlock">
        [#-- Saved cases studies --]        
        [#list caseStudies as caseStudy]
          <div id="caseStudy-${caseStudy_index}" class="caseStudy">
            [#-- CaseStudy identifier --]
            <input name="caseStudies[${caseStudy_index}].id" type="hidden" value="${caseStudy.id?c}">
          
            [#-- Remove link --]
            <div class="removeLink">
              <img src="${baseUrl}/images/global/icon-remove.png" />
              <a id="removeCaseStudy-${caseStudy_index}" href="" class="removeCaseStudy">
                Remove case study
              </a>
            </div>
          
            [#-- Title --]
            <div class="fullBlock">
              [@customForm.input name="caseStudies[${caseStudy_index}].title" type="text" i18nkey="reporting.caseStudies.title" /]
            </div>
            
            [#-- Type --]
            [#-- This section is missing because there is not a associated table in the db --]
            
            [#-- Author --]
            <div class="halfPartBlock">
              [@customForm.input name="caseStudies[${caseStudy_index}].author" type="text" i18nkey="reporting.caseStudies.author" /]              
            </div>
            
            [#-- Date --]
            <div class="halfPartBlock">
              [@customForm.input name="caseStudies[${caseStudy_index}].date" type="text" i18nkey="reporting.caseStudies.date" /]
            </div>
            
            [#-- Countries --]
            <div class="halfPartBlock countriesBlock">
              [@customForm.select name="caseStudies[${caseStudy_index}].countries" label="" i18nkey="reporting.caseStudies.country" listName="countryList" keyFieldName="id"  displayFieldName="name" value="caseStudies[${caseStudy_index}].countriesIds" multiple=true /]              
            </div>
            
            [#-- image --]
            <div class="halfPartBlock imageBlock">
              [#if caseStudy.imageFileName??]
                <div id="image">
                  <img src="${caseStudiesImagesUrl}/${caseStudy.imageFileName}" width="100%">
                </div>
                <div>
                  [@customForm.input name="caseStudies[${caseStudy_index}].image" type="file" i18nkey="reporting.caseStudies.image" /]
                </div>
              [#else]
                <div id="caseStudies[${caseStudy_index}].image"></div>
                <div>
                  [@customForm.input name="caseStudies[${caseStudy_index}].image" type="file" i18nkey="reporting.caseStudies.image" /]
                </div>    
              [/#if]
            </div>
            
            [#-- Keywords --]
            <div class="halfPartBlock">
              [@customForm.input name="caseStudies[${caseStudy_index}].keywords" type="text" i18nkey="reporting.caseStudies.keywords" /]
            </div>
            
            
            [#-- Objectives --]
            <div class="fullBlock">
              [@customForm.textArea name="caseStudies[${caseStudy_index}].objectives" i18nkey="reporting.caseStudies.objectives" /]
            </div>
            
            [#-- Description --]
            <div class="fullBlock">
              [@customForm.textArea name="caseStudies[${caseStudy_index}].description" i18nkey="reporting.caseStudies.descripition" /]
            </div>
            
            [#-- Result --]
            <div class="fullBlock">
              [@customForm.textArea name="caseStudies[${caseStudy_index}].results" i18nkey="reporting.caseStudies.results" /]
            </div>
            
            [#-- Partners --]
            <div class="fullBlock">
              [@customForm.textArea name="caseStudies[${caseStudy_index}].partners" i18nkey="reporting.caseStudies.partners" /]
            </div>
            
            [#-- Links / resources --]
            <div class="fullBlock">
              [@customForm.textArea name="caseStudies[${caseStudy_index}].links" i18nkey="reporting.caseStudies.links" /]
            </div>
            
            [#-- separator --]
            <hr />
          </div>
        [/#list]
        
        
          [#-- In this section appear the new case studies after press the add link --]
          
          <div id="addCaseStudiesBlock" class="addLink">
            <img src="${baseUrl}/images/global/icon-add.png" />
            <a href="" class="addCaseStudies">Add a new case study</a>
          </div>
        </div>
        
        [#-- Case Study Template --]
        <div id="template" style="display: none;">
          <div id="caseStudy-999" class="caseStudy">
            [#-- CaseStudy identifier --]
            <input name="id" type="hidden" value="-1">
            
            [#-- Remove link --]
            <div class="removeLink">
              <img src="${baseUrl}/images/global/icon-remove.png" />
              <a id="removeCaseStudy-999" href="" class="removeCaseStudy">
                Remove case study
              </a>
            </div>
          
            [#-- Title --]
            <div class="fullBlock">
              [@customForm.input name="title" type="text" i18nkey="reporting.caseStudies.title" /]
            </div>
            
            [#-- Type --]
            [#-- This section is missing because there is not a associated table in the db --]
            
            [#-- Author --]
            <div class="halfPartBlock">
              [@customForm.input name="author" type="text" i18nkey="reporting.caseStudies.author" /]              
            </div>
            
            [#-- Date --]
            <div class="halfPartBlock">
              [@customForm.input name="date" type="text" i18nkey="reporting.caseStudies.date" /]
            </div>            
            
            [#-- Countries --]
            <div class="halfPartBlock countriesBlock">
              [@customForm.select name="countries" label="" i18nkey="reporting.caseStudies.country" listName="countryList" keyFieldName="id"  displayFieldName="name" value="" multiple=true /]              
            </div>
            
            [#-- image url --]
            <div class="halfPartBlock imageBlock">
              <div id="image"></div>
              <div>
                [@customForm.input name="image" type="file" i18nkey="reporting.caseStudies.image" /]
              </div>
            </div>
            
            [#-- Keywords --]
            <div class="halfPartBlock">
              [@customForm.input name="keywords" type="text" i18nkey="reporting.caseStudies.keywords" /]
            </div>            
            
            [#-- Objectives --]
            <div class="fullBlock">
              [@customForm.textArea name="objectives" i18nkey="reporting.caseStudies.objectives" /]
            </div>
            
            [#-- Description --]
            <div class="fullBlock">
              [@customForm.textArea name="description" i18nkey="reporting.caseStudies.descripition" /]
            </div>
            
            [#-- Result --]
            <div class="fullBlock">
              [@customForm.textArea name="results" i18nkey="reporting.caseStudies.results" /]
            </div>
            
            [#-- Partners --]
            <div class="fullBlock">
              [@customForm.textArea name="partners" i18nkey="reporting.caseStudies.partners" /]
            </div>
            
            [#-- Links / resources --]
            <div class="fullBlock">
              [@customForm.textArea name="links" i18nkey="reporting.caseStudies.links" /]
            </div>
            
            [#-- separator --]
            <hr />
          </div>
        </div>
        [#-- Here endes Case Study Template --]
        <div class="buttons">
          [@s.submit type="button" name="save"]SAVE[/@s.submit]
          [@s.submit type="button" name="cancel"]CANCEL[/@s.submit]
        </div>
      [/@s.form]
    </article>
  </section>
[#include "/WEB-INF/global/pages/footer.ftl"]