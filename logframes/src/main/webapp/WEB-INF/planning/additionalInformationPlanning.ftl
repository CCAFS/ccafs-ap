[#ftl]
[#assign title = "Additional Information Planning" /]
[#assign globalLibs = ["jquery", "noty", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/planning/additionalInformationPlanning.js"] /]
[#assign customCSS = [""] /]
[#assign currentSection = "planning" /]
[#assign currentCycleSection = "additionalInformation" /]
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
    
    [#-- Hidden values --]
    <input id="keywordsDefaultText" value="[@s.text name="planning.additionalInformation.keyword.default" /]" type="hidden">
  
    <h1 class="contentTitle">
      ${activity.leader.acronym}: [@s.text name="planning.mainInformation.activity" /] ${activity.activityId} 
    </h1>
    
    <fieldset>
      <legend><h6>[@s.text name="planning.additionalInformation" /]</h6></legend>
    
    [#-- Activity keywords --]
    <div class="keywordsBlock fullBlock">
      <div id="keywordsList">
        [@customForm.select name="activity.keywords" label="" i18nkey="planning.additionalInformation.keywords" listName="keywords" keyFieldName="id"  displayFieldName="name" value="activity.keywordsIds" multiple=true className="keywords" /]
      </div>
    </div>
    
    [#-- Other keywords --]
    <div class="otherKeywordsBlock fullBlock">
      [#-- Title --]
      <div class="otherKeywordTitle">
        <h6>[@s.text name="planning.additionalInformation.otherKeywords" /]</h6>
      </div>
      
      [#-- Other keyword Help --]
      <div class="otherKeywordsHelp">
        [@s.text name="planning.additionalInformation.otherKeywords.help" /]
      </div>
      
      [#-- Other keywords field --]
      <div id="otherKeywordsList">
        [@customForm.textArea name="otherKeywordsString" i18nkey="planning.additionalInformation.otherKeywords" /]
      </div>
      <hr />
    </div>
    
    [#-- Resources --]
    <div class="resourcesBlock">
      <div id="resourcesList">
        [#if activity.resources?has_content]
          [#list activity.resources as resource]
            <div id="resource-${resource_index}" class="fullBlock resource">
              <input type="hidden" name="activity.resources[${resource_index}].id" value="${resource.id?c}" />
              [@customForm.input name="activity.resources[${resource_index}].name" type="text" i18nkey="planning.additionalInformation.resources" /]
              <img src="${baseUrl}/images/global/icon-remove.png" class="removeResource" id="removeResource-${resource_index}" />
            </div>
          [/#list]
        [/#if]
      </div>
      <div id="addResourceBlock" class="halfPartBlock">
        <img src="${baseUrl}/images/global/icon-add.png" />
        <a href="" class="addResource" >[@s.text name="planning.additionalInformation.addResource" /]</a>
      </div>
      <hr />
    </div>
    
    [#-- Keyword template --]
    <div id="keywordTemplate" class="thirdPartBlock" style="display:none" >
      <input type="hidden" name="keywordId" class="activityKeywordId" value="-1" />
      [@customForm.select name="id" label="" i18nkey="planning.additionalInformation.keywords" listName="keywords" keyFieldName="id"  displayFieldName="name" className="keywords" /]
    </div>
    
    [#-- Other keyword template --]
    <div id="otherKeywordTemplate" class="thirdPartBlock" style="display:none">
      <input type="hidden" class="activityKeywordId" value="-1" />
      [@customForm.input name="other" type="text" i18nkey="planning.additionalInformation.keyword" /]
    </div>
    
    [#-- Resource template --]
    <div id="resourceTemplate" class="fullBlock" style="display:none">
      <input type="hidden" name="resourceId" value="-1" />
      [@customForm.input name="name" type="text" i18nkey="planning.additionalInformation.resource" /]
      <img src="${baseUrl}/images/global/icon-remove.png" class="removeResource" />
    </div>
    
    <!-- internal parameter -->
    <input name="activityID" type="hidden" value="${activity.id?c}" />
    [#-- Only the owner of the activity can see the action buttons --]
    [#if activity.leader.id == currentUser.leader.id && canSubmit]
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
    [/#if]
    </fieldset>
    </article>
  [/@s.form]
 
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]