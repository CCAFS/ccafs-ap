[#ftl]
[#assign title = "Additional Information Planning" /]
[#assign globalLibs = ["jquery", "noty", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/planning/additionalInformationPlanning.js"] /]
[#assign customCSS = [""] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "additionalInformation" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.additionalInformation.help" /] </p>
  </div>
  
  [#include "/WEB-INF/global/pages/planning-secondary-menu.ftl" /]
  [@s.form action="additionalInformation"]
  <article class="halfContent">
    <h1 class="contentTitle">
      ${activity.leader.acronym} - [@s.text name="planning.additionalInformation.activity" /] ${activity.id}      
    </h1>
    
    [#-- Activity keywords --]
    <fieldset class="keywordsBlock">
      <legend> <h6> [@s.text name="planning.additionalInformation.keywords" /] </h6> </legend>
      <div id="keywordsList">
        [#list activity.keywords as activityKeyword]
          [#if activityKeyword.keyword?has_content]
            <div id="keyword-${activityKeyword_index}" class="activityKeyword keyword thirdPartBlock">
              <input type="hidden" name="activity.keywords[${activityKeyword_index}].id" value="${activityKeyword.id?c}" class="activityKeywordId" />
              [@customForm.select name="activity.keywords[${activityKeyword_index}].keyword" label="" i18nkey="planning.additionalInformation.keywords" listName="keywords" keyFieldName="id"  displayFieldName="name" value="activity.keywords[${activityKeyword_index}].keyword.id" className="keywords" /]
            </div>
          [/#if]
        [/#list]
      </div>
      <hr />
      <div id="addOtherKeywordBlock" class="halfPartBlock">
        <img src="${baseUrl}/images/global/icon-add.png" />
        <a href="" class="addKeyword" >[@s.text name="planning.additionalInformation.addKeyword" /]</a>
      </div>
      <div id="removeKeywordBlock" class="halfPartBlock" [#if ! activity.keywords?has_content] style="display:none" [/#if] >
        <img src="${baseUrl}/images/global/icon-remove.png" />
        <a href="" class="removeKeyword" >[@s.text name="planning.additionalInformation.removeLast" /]</a>
      </div>
    </fieldset>
    
    [#-- Other keywords --]
    <fieldset class="otherKeywordsBlock">
      <legend> <h6> [@s.text name="planning.additionalInformation.otherKeywords" /] </h6> </legend>
      <div id="otherKeywordsList">
        [#list activity.keywords as activityKeyword]
          [#if activityKeyword.other??]
            <div id="otherKeyword-${activityKeyword_index}" class="activityKeyword other thirdPartBlock">
              <input type="hidden" class="activityKeywordId" name="activity.keywords[${activityKeyword_index}].id" value="${activityKeyword.id?c}" />
              [@customForm.input name="activity.keywords[${activityKeyword_index}].other" type="text" i18nkey="planning.additionalInformation.otherKeywords" className="keywords" /]
            </div>
          [/#if]
        [/#list]
      </div>
      <hr />
      <div id="addOtherKeywordBlock" class="halfPartBlock">
        <img src="${baseUrl}/images/global/icon-add.png" />
        <a href="" class="addOtherKeyword" >[@s.text name="planning.additionalInformation.addOtherKeyword" /]</a>
      </div>
      <div id="removeOtherKeywordBlock" class="halfPartBlock" [#if ! activity.keywords?has_content] style="display:none" [/#if] >
        <img src="${baseUrl}/images/global/icon-remove.png" />
        <a href="" class="removeOtherKeyword" >[@s.text name="planning.additionalInformation.removeLast" /]</a>
      </div>
    </fieldset>
    
    [#-- Resources --]
    <fieldset class="resourcesBlock">
      <legend> <h6> [@s.text name="planning.additionalInformation.resources" /] </h6> </legend>
      <div id="resourcesList">
        [#list activity.resources as resource]
          <div id="resource-${resource_index}" class="fullBlock resource">
            <input type="hidden" name="activity.resources[${resource_index}].id" value="${resource.id?c}" />
            [@customForm.input name="activity.resources[${resource_index}].name" type="text" i18nkey="planning.additionalInformation.resources" /]
          </div>
        [/#list]
      </div>
      <hr />
      <div id="addResourceBlock" class="halfPartBlock">
        <img src="${baseUrl}/images/global/icon-add.png" />
        <a href="" class="addResource" >[@s.text name="planning.additionalInformation.addResource" /]</a>
      </div>
      <div id="removeResourceBlock" class="halfPartBlock" [#if ! activity.resources?has_content] style="display:none" [/#if] >
        <img src="${baseUrl}/images/global/icon-remove.png" />
        <a href="" class="removeResource" >[@s.text name="planning.additionalInformation.removeLast" /]</a>
      </div>
    </fieldset>
    
    [#-- Keyword template --]
    <div id="keywordTemplate" class="thirdPartBlock" style="display:none" >
      <input type="hidden" name="keywordId" class="activityKeywordId" value="-1" />
      [@customForm.select name="id" label="" i18nkey="planning.additionalInformation.keywords" listName="keywords" keyFieldName="id"  displayFieldName="name" className="keywords" /]
    </div>
    
    [#-- Other keyword template --]
    <div id="otherKeywordTemplate" class="thirdPartBlock" style="display:none">
      <input type="hidden" class="activityKeywordId" value="-1" />
      [@customForm.input name="other" type="text" i18nkey="planning.additionalInformation.otherKeywords" /]
    </div>
    
    [#-- Resource template --]
    <div id="resourceTemplate" class="fullBlock" style="display:none">
      <input type="hidden" name="resourceId" value="-1" />
      [@customForm.input name="name" type="text" i18nkey="planning.additionalInformation.resources" /]
    </div>
    
    <!-- internal parameter -->
    <input name="activityID" type="hidden" value="${activity.id}" />
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
        
    </article>
  [/@s.form]
 
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]