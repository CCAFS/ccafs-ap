[#ftl]
[#assign title = "Case Studies" /]
[#assign globalLibs = ["jquery", "noty", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/reporting/caseStudiesReporting.js"] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "caseStudies" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="reporting.caseStudies.help" /]</p>
  </div>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  [@s.form action="caseStudies" enctype="multipart/form-data"]  
  <article class="halfContent">
    <h1 class="contentTitle">
      ${currentUser.leader.acronym} - [@s.text name="reporting.caseStudies" /]  
    </h1>
    
    <div id="items">
      <fieldset id="caseStudiesGroup">
        
        [#-- Saved cases studies --]        
        [#list caseStudies as caseStudy]
              
        <div id="caseStudy-${caseStudy_index}" class="caseStudy">
          [#-- CaseStudy identifier --]
          <input name="caseStudies[${caseStudy_index}].id" type="hidden" value="${caseStudy.id?c}">
          
          [#-- Item index --]
          <div class="itemIndex">
            [@s.text name="reporting.caseStudies.caseStudy" /] ${caseStudy_index +1}
          </div>
      
          [#-- Remove link --]
          <div class="removeLink">
            <img src="${baseUrl}/images/global/icon-remove.png" />
            <a id="removeCaseStudy-${caseStudy_index}" href="" class="removeCaseStudy">
              [@s.text name="reporting.caseStudies.removeCaseStudy" /]
            </a>
          </div>
        
          [#-- Title --]
          <div class="fullBlock">
            [@customForm.input name="caseStudies[${caseStudy_index}].title" type="text" i18nkey="reporting.caseStudies.title" /]
          </div>
                    
          [#-- Author --]
          <div class="fullBlock authorBlock">
            [@customForm.input name="caseStudies[${caseStudy_index}].author" type="text" i18nkey="reporting.caseStudies.author" /]              
          </div>
          
          [#-- Types --]
          <div class="fullBlock">
            <h6>[@s.text name="reporting.caseStudies.types" /]</h6>
            <div class="checkboxGroup">
              [@s.fielderror cssClass="fieldError" fieldName="caseStudies[${caseStudy_index}].types"/]
              [@s.checkboxlist name="caseStudies[${caseStudy_index}].types" list="caseStudyTypeList" listKey="id" listValue="name" value="caseStudies[${caseStudy_index}].typesIds" cssClass="checkbox" /]
            </div>                          
          </div>
          
          [#-- Start Date --]
          <div class="halfPartBlock">
            [@customForm.input name="caseStudies[${caseStudy_index}].startDate" type="text" i18nkey="reporting.caseStudies.startDate" /]
          </div>
          
          [#-- End Date --]
          <div class="halfPartBlock">
            [@customForm.input name="caseStudies[${caseStudy_index}].endDate" type="text" i18nkey="reporting.caseStudies.endDate" /]
          </div>
                    
          [#-- image --]
          <div class="fullBlock imageBlock">
            [#if caseStudy.imageFileName??]
              <div class="halfPartBlock browseInput">
                [@customForm.input name="caseStudies[${caseStudy_index}].image" type="file" i18nkey="reporting.caseStudies.image" /]
              </div>                            
              <div id="caseStudies[${caseStudy_index}].image" class="halfPartBlock image">
                <img src="${caseStudiesImagesUrl}/${caseStudy.imageFileName}" width="100%">
              </div>
            [#else]
              <div class="halfPartBlock browseInput">
                [@customForm.input name="caseStudies[${caseStudy_index}].image" type="file" i18nkey="reporting.caseStudies.image" /]
              </div>                                
              <div id="caseStudies[${caseStudy_index}].image" class="halfPartBlock image"></div>
            [/#if]
            <div class="clear"> </div>
          </div>
          [#-- Is global --]
          <div class="halfPartBlock">          
            [@customForm.checkbox  name="caseStudies[${caseStudy_index}].global" i18nkey="reporting.caseStudies.isGlobal" checked=caseStudy.global /]
          </div>
          
          [#-- Countries --]
          <div class="fullBlock countriesBlock chosen">
            [@customForm.select name="caseStudies[${caseStudy_index}].countries" label="" i18nkey="reporting.caseStudies.countries" listName="countryList" keyFieldName="id"  displayFieldName="name" value="caseStudies[${caseStudy_index}].countriesIds" multiple=true disabled="${caseStudy.global?string('1', '0')}"/]              
          </div>
          
          [#-- Keywords --]
          <div class="fullBlock">
            [@customForm.input name="caseStudies[${caseStudy_index}].keywords" type="text" i18nkey="reporting.caseStudies.keywords" /]
          </div>
          
          [#-- Description --]
          <div class="fullBlock">
            [@customForm.textArea name="caseStudies[${caseStudy_index}].description" i18nkey="reporting.caseStudies.descripition" /]
          </div>
          
          [#-- Objectives --]
          <div class="fullBlock">
            [@customForm.textArea name="caseStudies[${caseStudy_index}].objectives" i18nkey="reporting.caseStudies.objectives" /]
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
          
          [#-- In this section appear the new case studies after press the add link --]
                  
       </div>    
      [/#list]
      
      <div id="addCaseStudiesBlock" class="addLink">
        <img src="${baseUrl}/images/global/icon-add.png" />
        <a href="" class="addCaseStudies">[@s.text name="reporting.caseStudies.addCaseStudy" /]</a>
      </div>
      
     </fieldset>
    </div>
    
    [#-- Case Study Template --]
    <div id="template" style="display: none;">
      <div id="caseStudy-999" class="caseStudy">
        [#-- CaseStudy identifier --]
        <input name="id" type="hidden" value="-1">
        
        [#-- Item index --]
        <div class="itemIndex">
          [@s.text name="reporting.caseStudies.caseStudy" /]
        </div>
            
        [#-- Remove link --]
        <div class="removeLink">
          <img src="${baseUrl}/images/global/icon-remove.png" />
          <a id="removeCaseStudy-999" href="" class="removeCaseStudy">
            [@s.text name="reporting.caseStudies.removeCaseStudy" /]
          </a>
        </div>
      
        [#-- Title --]
        <div class="fullBlock">
          [@customForm.input name="title" type="text" i18nkey="reporting.caseStudies.title" /]
        </div>
        
        [#-- Author --]
        <div class="fullBlock authorBlock">
          [@customForm.input name="author" type="text" i18nkey="reporting.caseStudies.author" /]              
        </div>
        
        [#-- Types --]
        <div class="fullBlock">
          <h6>[@s.text name="reporting.caseStudies.types" /]</h6>
          <div class="checkboxGroup">
            [@s.checkboxlist name="types" list="caseStudyTypeList" listKey="id" listValue="name" cssClass="checkbox" /]
          </div>                          
        </div>
        
        [#-- Start Date --]
        <div class="halfPartBlock">
          [@customForm.input name="startDate" type="text" i18nkey="reporting.caseStudies.startDate" /]
        </div>
        
        [#-- End Date --]
        <div class="halfPartBlock">
          [@customForm.input name="endDate" type="text" i18nkey="reporting.caseStudies.endDate" /]
        </div>            
        
        [#-- image url --]
        <div class="fullBlock imageBlock">
          <div class="halfPartBlock browseInput">
            [@customForm.input name="image" type="file" i18nkey="reporting.caseStudies.image" /]
          </div>
          <div id="image" class="halfPartBlock image"></div>
          <div class="clear"> </div>
        </div>
        
        [#-- Is global --]
        <div class="halfPartBlock">            
          [@customForm.checkbox  name="global" i18nkey="reporting.caseStudies.isGlobal" checked=false /]              
        </div>
        
        [#-- Countries --]
        <div class="fullBlock countriesBlock">
          [@customForm.select name="countries" label="" i18nkey="reporting.caseStudies.countries" listName="countryList" keyFieldName="id"  displayFieldName="name" value="" multiple=true /]              
        </div>
        
        [#-- Keywords --]
        <div class="fullBlock">
          [@customForm.input name="keywords" type="text" i18nkey="reporting.caseStudies.keywords" /]
        </div>            
        
        [#-- Description --]
        <div class="fullBlock">
          [@customForm.textArea name="description" i18nkey="reporting.caseStudies.descripition" /]
        </div>
        
        [#-- Objectives --]
        <div class="fullBlock">
          [@customForm.textArea name="objectives" i18nkey="reporting.caseStudies.objectives" /]
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
    [#-- Here ends Case Study Template --]
    
    [#-- Minimum and maximun values to the datepicker --]    
    <input type="hidden" id="minDateValue" value="${currentYear?c}-01-01"/>
    <input type="hidden" id="maxDateValue" value="${endYear?c}-12-31"/>
    
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
      
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]